package com.bearsacker.game.entities;

import static com.bearsacker.game.configs.GameConfig.FIRE_TIME;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Map;
import com.bearsacker.game.resources.Images;

public class Fire extends Entity {

    private long time;

    public Fire(Vec2 position) {
        super(Images.FIRE, position);

        this.time = System.currentTimeMillis();
    }

    @Override
    public void update(Map map) {

    }

    @Override
    public boolean isToRemove() {
        return System.currentTimeMillis() - time > FIRE_TIME;
    }

}
