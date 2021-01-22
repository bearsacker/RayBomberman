package com.bearsacker.game.entities;

import static com.bearsacker.game.configs.GameConfig.PLAYER_SPEED;
import static com.bearsacker.game.entities.BotAction.HIDE;
import static com.bearsacker.game.entities.BotAction.MOVE;
import static com.bearsacker.game.entities.BotAction.NOTHING;
import static com.bearsacker.game.entities.BotAction.PLANT;
import static com.bearsacker.game.entities.BotAction.WAIT;

import java.util.List;

import org.jbox2d.common.Vec2;

import com.bearsacker.engine.utils.NumberGenerator;
import com.bearsacker.game.Map;
import com.bearsacker.game.Playable;
import com.bearsacker.game.ai.Path;
import com.bearsacker.game.ai.Point;
import com.bearsacker.game.events.PlantBomb;
import com.bearsacker.game.items.Item;
import com.bearsacker.game.resources.Images;

public class Bot extends Entity implements Playable {

    private final static long FRAME_DURATION = 100;

    private Images[] images;

    private int frame;

    private long lastUpdate;

    private long lastFrameUpdate;

    private boolean alive;

    private int bombs;

    private int availableBombs;

    private int bombRange;

    private float speed;

    private boolean hasGlove;

    private boolean hasRedBomb;

    private boolean hasPowerBomb;

    private BotAction action;

    private Bomb sawBomb;

    private long timeToWait;

    private Path destination;

    private int currentStep;

    public Bot(Vec2 position) {
        super(Images.PLAYER_FRONT_1, position);

        images = new Images[6];
        images[0] = Images.PLAYER_FRONT_1;
        images[1] = Images.PLAYER_FRONT_2;
        images[2] = Images.PLAYER_FRONT_3;
        images[3] = Images.PLAYER_FRONT_4;
        images[4] = Images.PLAYER_FRONT_5;
        images[5] = Images.PLAYER_FRONT_6;

        direction = new Vec2(1f, 0f);
        speed = PLAYER_SPEED;
        availableBombs = 1;
        bombs = 1;
        bombRange = 1;

        alive = true;
        action = NOTHING;
    }

    @Override
    public void update(Map map) {
        long time = System.currentTimeMillis();
        float frameTime = (time - lastUpdate) / 1000f;
        lastUpdate = time;

        float moveSpeed = frameTime * speed;

        sprite = images[frame];

        if (destination != null) {
            if (time - lastFrameUpdate > FRAME_DURATION) {
                frame++;
                frame %= 6;
                lastFrameUpdate = time;
            }
        } else {
            frame = 0;
        }

        List<Entity> entities = map.getEntitiesAt(position);
        for (Entity entity : entities) {
            if (entity instanceof Fire) {
                alive = false;
            } else if (entity instanceof Item) {
                ((Item) entity).use(this);
            }
        }

        Point currentPosition = new Point((int) position.x, (int) position.y);
        Bomb bomb = isSeeingBomb(map);
        if (bomb != null && bomb != sawBomb) {
            action = HIDE;
            sawBomb = bomb;

            destination = findDestinationToHide(map, position, sawBomb.position);
            currentStep = 0;
        }

        if (destination != null) {
            Point newPosition = destination.getStep(currentStep);
            if (position.x >= newPosition.x + .25f && position.x <= newPosition.x + .75f && position.y >= newPosition.y + .25f
                    && position.y <= newPosition.y + .75f) {
                currentStep++;
            }

            if (currentPosition.x - newPosition.x == 1) {
                direction = new Vec2(-1f, 0f);
            } else if (currentPosition.x - newPosition.x == -1) {
                direction = new Vec2(1f, 0f);
            } else if (currentPosition.y - newPosition.y == 1) {
                direction = new Vec2(0f, -1f);
            } else if (currentPosition.y - newPosition.y == -1) {
                direction = new Vec2(0f, 1f);
            }

            if (map.getTile((int) (position.x + direction.x * moveSpeed), (int) (position.y)) == null) {
                position.x += direction.x * moveSpeed;
            }

            if (map.getTile((int) (position.x), (int) (position.y + direction.y * moveSpeed)) == null) {
                position.y += direction.y * moveSpeed;
            }

            if (currentStep >= destination.getLength()) {
                destination = null;
            }
        }

        switch (action) {
        case HIDE:
            if (destination == null && sawBomb.isToRemove()) {
                action = WAIT;
                timeToWait = 1000;
                sawBomb = null;
            }
            break;
        case WAIT:
            timeToWait -= frameTime * 1000f;
            if (timeToWait <= 0) {
                action = NOTHING;
            }
            break;
        case NOTHING:
            if (bombs > 0) {
                destination = findDestinationToPlant(map);
                if (destination != null) {
                    currentStep = 0;
                    action = PLANT;
                } else {
                    destination = findRandomDestination(map);
                    if (destination != null) {
                        currentStep = 0;
                        action = MOVE;
                    }
                }
            }
            break;
        case MOVE:
            action = NOTHING;
            break;
        case PLANT:
            if (destination == null) {
                bombs--;
                map.pushEvent(
                        new PlantBomb(this, new Vec2(position.x + direction.x / 2f, position.y + direction.y / 2f), bombRange, hasRedBomb));
            }
            break;
        }
    }

    private Bomb isSeeingBomb(Map map) {
        Vec2 p = new Vec2(position);
        while (map.getTile(p) == null) {
            Bomb bomb = map.getBombAt(p);
            if (bomb != null) {
                return bomb;
            }

            p = p.add(direction);
            System.out.println("seeingBomb");
        }

        return null;
    }

    private Path findDestinationToHide(Map map, Vec2 from, Vec2 bomb) {
        Point player = new Point((int) from.x, (int) from.y);
        int tries = 0;

        Point p = new Point(player.x + NumberGenerator.get().randomInt(-3, 3), player.y + NumberGenerator.get().randomInt(-3, 3));
        Path path = map.getAStar().findPath(player, p, false);
        while (path == null || p.x == (int) bomb.x || p.y == (int) bomb.y || map.getTile(p.x, p.y) != null) {
            p = new Point(player.x + NumberGenerator.get().randomInt(-3, 3), player.y + NumberGenerator.get().randomInt(-3, 3));
            path = map.getAStar().findPath(player, p, false);
            tries++;

            if (tries > 100) {
                return null;
            }
        }

        return path;
    }

    private Path findDestinationToPlant(Map map) {
        Point player = new Point((int) position.x, (int) position.y);

        Point p = new Point(player.x + NumberGenerator.get().randomInt(-5, 5), player.y + NumberGenerator.get().randomInt(-5, 5));
        if (map.getTile(p.x, p.y) != null || (!map.isDestroyed() && !map.hasBreakableNeighboors(p.x, p.y))) {
            return null;
        }

        return map.getAStar().findPath(player, p, false);
    }

    private Path findRandomDestination(Map map) {
        Point player = new Point((int) position.x, (int) position.y);

        Point p = new Point(player.x + NumberGenerator.get().randomInt(-10, 10), player.y + NumberGenerator.get().randomInt(-10, 10));
        if (map.getTile(p.x, p.y) != null) {
            return null;
        }

        return map.getAStar().findPath(player, p, false);
    }

    @Override
    public boolean isToRemove() {
        return !alive;
    }

    @Override
    public void pickUpRedBomb() {
        hasRedBomb = true;
    }

    @Override
    public boolean hasRedBomb() {
        return hasRedBomb;
    }

    @Override
    public void pickUpPowerBomb() {
        hasPowerBomb = true;
    }

    @Override
    public boolean hasPowerBomb() {
        return hasPowerBomb;
    }

    @Override
    public void pickUpGlove() {
        hasGlove = true;
    }

    @Override
    public boolean hasGlove() {
        return hasGlove;
    }

    @Override
    public void decrementBombs() {
        if (availableBombs > 1) {
            availableBombs--;
            if (bombs > 0) {
                bombs--;
            }
        }
    }

    @Override
    public void incrementBombs() {
        availableBombs++;
        bombs++;
    }

    @Override
    public void decrementRange() {
        if (bombRange > 1) {
            bombRange--;
        }
    }

    @Override
    public void retrieveBomb() {
        bombs++;
    }

    @Override
    public void incrementRange() {
        bombRange++;
    }

    @Override
    public void decrementSpeed() {
        if (speed > PLAYER_SPEED) {
            speed -= PLAYER_SPEED / 4f;
        }
    }

    @Override
    public void incrementSpeed() {
        speed += PLAYER_SPEED / 4f;
    }
}
