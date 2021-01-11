package com.guillot.game;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.newdawn.slick.Input.KEY_D;
import static org.newdawn.slick.Input.KEY_E;
import static org.newdawn.slick.Input.KEY_Q;
import static org.newdawn.slick.Input.KEY_S;
import static org.newdawn.slick.Input.KEY_Z;

import org.jbox2d.common.Vec2;

import com.guillot.engine.gui.GUI;

public class Player {

    private Vec2 position;

    private Vec2 direction;

    private Vec2 plane;

    private int bombs;

    private int bombRange;

    private float speed;

    public Player() {
        position = new Vec2(1.5f, 1.5f);
        direction = new Vec2(-1f, 0f);
        plane = new Vec2(0f, .66f);

        speed = .01f;
        bombs = 1;
        bombRange = 1;
    }

    public void update(Map map) {
        if (GUI.get().getInput().isKeyDown(KEY_Z)) {
            if (map.getTile((int) (position.x + direction.x * speed * 20f), (int) (position.y)) == null) {
                position.x += direction.x * speed;
            }
            if (map.getTile((int) (position.x), (int) (position.y + direction.y * speed * 20f)) == null) {
                position.y += direction.y * speed;
            }
        }

        if (GUI.get().getInput().isKeyDown(KEY_S)) {
            if (map.getTile((int) (position.x - direction.x * speed * 20f), (int) (position.y)) == null) {
                position.x -= direction.x * speed;
            }
            if (map.getTile((int) (position.x), (int) (position.y - direction.y * speed * 20f)) == null) {
                position.y -= direction.y * speed;
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
            decrementBombs();
            map.getEntities().add(new Bomb(this, new Vec2(position), bombRange));
        }
    }

    public int getBombs() {
        return bombs;
    }

    public void decrementBombs() {
        bombs--;
    }

    public void incrementBombs() {
        bombs++;
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
