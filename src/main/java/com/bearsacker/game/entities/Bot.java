package com.bearsacker.game.entities;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Map;
import com.bearsacker.game.resources.Images;

public class Bot extends Entity {

    private final static long FRAME_DURATION = 100;

    private Images[] images;

    private int frame;

    private boolean walking;

    private long lastFrameUpdate;

    private boolean alive;

    public Bot(Vec2 position) {
        super(Images.PLAYER_FRONT_1, position);

        images = new Images[6];
        images[0] = Images.PLAYER_FRONT_1;
        images[1] = Images.PLAYER_FRONT_2;
        images[2] = Images.PLAYER_FRONT_3;
        images[3] = Images.PLAYER_FRONT_4;
        images[4] = Images.PLAYER_FRONT_5;
        images[5] = Images.PLAYER_FRONT_6;

        alive = true;
        walking = true;
    }

    @Override
    public void update(Map map) {
        sprite = images[frame];

        if (walking) {
            long time = System.currentTimeMillis();
            if (time - lastFrameUpdate > FRAME_DURATION) {
                frame++;
                frame %= 6;
                lastFrameUpdate = time;
            }
        } else {
            frame = 0;
        }

        if (map.isBurningAt(position)) {
            // alive = false;
        }
    }

    @Override
    public boolean isToRemove() {
        return !alive;
    }

}
