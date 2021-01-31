package com.bearsacker.game;

import static com.bearsacker.engine.configs.EngineConfig.WIDTH;
import static com.bearsacker.game.configs.GameConfig.PLAYER_SPEED;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static org.newdawn.slick.Input.KEY_D;
import static org.newdawn.slick.Input.KEY_E;
import static org.newdawn.slick.Input.KEY_Q;
import static org.newdawn.slick.Input.KEY_S;
import static org.newdawn.slick.Input.KEY_Z;
import static org.newdawn.slick.Input.MOUSE_LEFT_BUTTON;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Mouse;

import com.bearsacker.engine.gui.GUI;
import com.bearsacker.game.entities.Bomb;
import com.bearsacker.game.entities.Entity;
import com.bearsacker.game.entities.Fire;
import com.bearsacker.game.items.Item;
import com.bearsacker.game.resources.Sounds;

public class Player implements Playable {

    private final static double ANGLE_LEFT = toRadians(90f);

    private final static double ANGLE_RIGHT = toRadians(-90f);

    private Vec2 position;

    private Vec2 direction;

    private Vec2 plane;

    private int bombs;

    private int availableBombs;

    private int bombRange;

    private float speed;

    private boolean hasRedBomb;

    private boolean hasPowerBomb;

    private boolean hasGlove;

    private boolean hiting;

    private long lastUpdate;

    private boolean dead;

    public Player(Vec2 position) {
        this.position = new Vec2(position);
        direction = new Vec2(.71f, .71f);
        plane = new Vec2(.46f, -.46f);

        speed = PLAYER_SPEED;
        availableBombs = 1;
        bombs = 1;
        bombRange = 1;
    }

    public void reset() {
        lastUpdate = System.currentTimeMillis();
    }

    public void update(Map map) {
        long time = System.currentTimeMillis();
        float frameTime = (time - lastUpdate) / 1000f;
        lastUpdate = time;

        float moveSpeed = frameTime * speed;

        hiting = hasGlove && GUI.get().getInput().isMouseButtonDown(MOUSE_LEFT_BUTTON);

        List<Entity> entities = map.getEntitiesAt(position);
        for (Entity entity : entities) {
            if (hiting && entity instanceof Bomb) {
                entity.setDirection(direction);
            } else if (entity instanceof Fire) {
                dead = true;
            } else if (entity instanceof Item) {
                ((Item) entity).use(this);
                Sounds.PICKUP.getSound().play();
            }
        }

        double angle = -toRadians(Mouse.getDX() / (WIDTH / 2f) * 90f);
        rotate(angle);

        if (GUI.get().getInput().isKeyDown(KEY_Z)) {
            if (map.getTile((int) (position.x + direction.x * moveSpeed), (int) (position.y)) == null) {
                position.x += direction.x * moveSpeed;
            }
            if (map.getTile((int) (position.x), (int) (position.y + direction.y * moveSpeed)) == null) {
                position.y += direction.y * moveSpeed;
            }
        } else if (GUI.get().getInput().isKeyDown(KEY_S)) {
            if (map.getTile((int) (position.x - direction.x * moveSpeed), (int) (position.y)) == null) {
                position.x -= direction.x * moveSpeed;
            }
            if (map.getTile((int) (position.x), (int) (position.y - direction.y * moveSpeed)) == null) {
                position.y -= direction.y * moveSpeed;
            }
        }

        if (GUI.get().getInput().isKeyDown(KEY_Q)) {
            Vec2 newDirection = new Vec2(direction);
            float oldX = newDirection.x;
            newDirection.x = (float) (newDirection.x * cos(ANGLE_LEFT) - newDirection.y * sin(ANGLE_LEFT));
            newDirection.y = (float) (oldX * sin(ANGLE_LEFT) + newDirection.y * cos(ANGLE_LEFT));

            if (map.getTile((int) (position.x + newDirection.x * moveSpeed), (int) (position.y)) == null) {
                position.x += newDirection.x * moveSpeed;
            }
            if (map.getTile((int) (position.x), (int) (position.y + newDirection.y * moveSpeed)) == null) {
                position.y += newDirection.y * moveSpeed;
            }
        } else if (GUI.get().getInput().isKeyDown(KEY_D)) {
            Vec2 newDirection = new Vec2(direction);
            float oldX = newDirection.x;
            newDirection.x = (float) (newDirection.x * cos(ANGLE_RIGHT) - newDirection.y * sin(ANGLE_RIGHT));
            newDirection.y = (float) (oldX * sin(ANGLE_RIGHT) + newDirection.y * cos(ANGLE_RIGHT));

            if (map.getTile((int) (position.x + newDirection.x * moveSpeed), (int) (position.y)) == null) {
                position.x += newDirection.x * moveSpeed;
            }
            if (map.getTile((int) (position.x), (int) (position.y + newDirection.y * moveSpeed)) == null) {
                position.y += newDirection.y * moveSpeed;
            }
        }

        if (GUI.get().isKeyPressed(KEY_E) && bombs > 0) {
            bombs--;
            Sounds.PLANT.getSound().play();

            if (map.getTile((int) (position.x + direction.x / 2f), (int) (position.y)) == null
                    && map.getTile((int) (position.x), (int) (position.y + direction.y / 2f)) == null) {
                map.getEntities()
                        .add(new Bomb(this, new Vec2(position.x + direction.x / 2f, position.y + direction.y / 2f), bombRange, hasRedBomb));
            } else {
                map.getEntities().add(new Bomb(this, new Vec2(position), bombRange, hasRedBomb));
            }
        }
    }

    private void rotate(double angle) {
        float oldDirX = direction.x;
        direction.x = (float) (direction.x * cos(angle) - direction.y * sin(angle));
        direction.y = (float) (oldDirX * sin(angle) + direction.y * cos(angle));

        float oldPlaneX = plane.x;
        plane.x = (float) (plane.x * cos(angle) - plane.y * sin(angle));
        plane.y = (float) (oldPlaneX * sin(angle) + plane.y * cos(angle));
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

    public int getBombs() {
        return bombs;
    }

    public int getAvailableBombs() {
        return availableBombs;
    }

    public int getBombRange() {
        return bombRange;
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

    public Vec2 getPosition() {
        return position;
    }

    public Vec2 getDirection() {
        return direction;
    }

    public Vec2 getPlane() {
        return plane;
    }

    public boolean isHiting() {
        return hiting;
    }

    public boolean isDead() {
        return dead;
    }

}
