package com.guillot.game;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.newdawn.slick.Input.KEY_D;
import static org.newdawn.slick.Input.KEY_E;
import static org.newdawn.slick.Input.KEY_Q;
import static org.newdawn.slick.Input.KEY_S;
import static org.newdawn.slick.Input.KEY_Z;

import java.util.List;

import org.jbox2d.common.Vec2;

import com.guillot.engine.gui.GUI;
import com.guillot.game.entities.Bomb;
import com.guillot.game.entities.Entity;
import com.guillot.game.entities.Fire;
import com.guillot.game.items.Item;

public class Player {

    public final static float SPEED_MIN = 4f;

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

    private long lastUpdate;

    public Player() {
        position = new Vec2(1.5f, 1.5f);
        direction = new Vec2(-1f, 0f);
        plane = new Vec2(0f, .66f);

        speed = SPEED_MIN;
        availableBombs = 1;
        bombs = 1;
        bombRange = 1;
    }

    public void update(Map map) {
        long time = System.currentTimeMillis();
        float frameTime = (time - lastUpdate) / 1000f;
        lastUpdate = time;

        float moveSpeed = frameTime * speed;

        List<Entity> entities = map.getEntitiesAt(position);
        for (Entity entity : entities) {
            if (entity instanceof Fire) {
                // TODO Death
            } else if (entity instanceof Item) {
                ((Item) entity).use(this);
            }
        }

        if (GUI.get().getInput().isKeyDown(KEY_Z)) {
            if (map.getTile((int) (position.x + direction.x * moveSpeed), (int) (position.y)) == null) {
                position.x += direction.x * moveSpeed;
            }
            if (map.getTile((int) (position.x), (int) (position.y + direction.y * moveSpeed)) == null) {
                position.y += direction.y * moveSpeed;
            }
        }

        if (GUI.get().getInput().isKeyDown(KEY_S)) {
            if (map.getTile((int) (position.x - direction.x * moveSpeed), (int) (position.y)) == null) {
                position.x -= direction.x * moveSpeed;
            }
            if (map.getTile((int) (position.x), (int) (position.y - direction.y * moveSpeed)) == null) {
                position.y -= direction.y * moveSpeed;
            }
        }

        if (GUI.get().getInput().isKeyDown(KEY_Q)) {
            float oldDirX = direction.x;
            direction.x = (float) (direction.x * cos(.01f) - direction.y * sin(.01f));
            direction.y = (float) (oldDirX * sin(.01f) + direction.y * cos(.01f));

            float oldPlaneX = plane.x;
            plane.x = (float) (plane.x * cos(.01f) - plane.y * sin(.01f));
            plane.y = (float) (oldPlaneX * sin(.01f) + plane.y * cos(.01f));
        }

        if (GUI.get().getInput().isKeyDown(KEY_D)) {
            float oldDirX = direction.x;
            direction.x = (float) (direction.x * cos(-.01f) - direction.y * sin(-.01f));
            direction.y = (float) (oldDirX * sin(-.01f) + direction.y * cos(-.01f));

            float oldPlaneX = plane.x;
            plane.x = (float) (plane.x * cos(-.01f) - plane.y * sin(-.01f));
            plane.y = (float) (oldPlaneX * sin(-.01f) + plane.y * cos(-.01f));
        }

        if (GUI.get().isKeyPressed(KEY_E) && bombs > 0) {
            bombs--;
            map.getEntities().add(new Bomb(this, new Vec2(position), bombRange));
        }
    }

    public void pickUpRedBomb() {
        hasRedBomb = true;
    }

    public boolean hasRedBomb() {
        return hasRedBomb;
    }

    public void pickUpPowerBomb() {
        hasPowerBomb = true;
    }

    public boolean hasPowerBomb() {
        return hasPowerBomb;
    }

    public void pickUpGlove() {
        hasGlove = true;
    }

    public boolean hasGlove() {
        return hasGlove;
    }

    public int getBombs() {
        return bombs;
    }

    public void decrementBombs() {
        if (availableBombs > 1) {
            availableBombs--;
            if (bombs > 0) {
                bombs--;
            }
        }
    }

    public void incrementBombs() {
        availableBombs++;
        bombs++;
    }

    public void decrementRange() {
        if (bombRange > 1) {
            bombRange--;
        }
    }

    public void retrieveBomb() {
        bombs++;
    }

    public void incrementRange() {
        bombRange++;
    }

    public void decrementSpeed() {
        if (speed > SPEED_MIN) {
            speed -= SPEED_MIN / 2f;
        }
    }

    public void incrementSpeed() {
        speed += SPEED_MIN / 2f;
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

}
