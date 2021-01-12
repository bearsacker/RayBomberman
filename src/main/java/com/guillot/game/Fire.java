package com.guillot.game;

import org.jbox2d.common.Vec2;

public class Fire extends Entity {

    private final static int LIFETIME = 1000;

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
        return System.currentTimeMillis() - time > LIFETIME;
    }

}
