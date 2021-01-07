package com.guillot.game;

import static java.lang.Math.sqrt;

import org.jbox2d.common.Vec2;

public class Sprite {

    private Vec2 position;

    private Images texture;

    public Sprite(float x, float y, Images texture) {
        this.position = new Vec2(x, y);
        this.texture = texture;
    }

    public Vec2 getPosition() {
        return position;
    }

    public Images getTexture() {
        return texture;
    }

    public double distanceFrom(Vec2 point) {
        return sqrt((position.x - point.x) * (position.x - point.x) + (position.y - point.y) * (position.y - point.y));
    }
}
