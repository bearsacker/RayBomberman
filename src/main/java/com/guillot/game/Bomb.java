package com.guillot.game;

import static java.lang.Math.sqrt;

import org.jbox2d.common.Vec2;

public class Bomb implements Drawable {

    private Vec2 position;

    private long time;

    private Images texture;

    public Bomb(Vec2 position) {
        this.position = position;
        this.time = System.currentTimeMillis();
        this.texture = Images.BOMB;
    }

    @Override
    public Images getSprite() {
        return texture;
    }

    @Override
    public boolean isToRemove() {
        return System.currentTimeMillis() - time > 5000;
    }

    @Override
    public void update() {

    }

    public Vec2 getPosition() {
        return position;
    }

    public double distanceFrom(Vec2 point) {
        return sqrt((position.x - point.x) * (position.x - point.x) + (position.y - point.y) * (position.y - point.y));
    }

}
