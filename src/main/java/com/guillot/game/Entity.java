package com.guillot.game;

import org.jbox2d.common.Vec2;

public abstract class Entity {

    protected Vec2 position;

    protected Images sprite;

    public Entity(Images sprite, Vec2 position) {
        this.sprite = sprite;
        this.position = position;
    }

    public double distanceFrom(Vec2 point) {
        return (position.x - point.x) * (position.x - point.x) + (position.y - point.y) * (position.y - point.y);
    }

    public Vec2 getPosition() {
        return position;
    }

    public Images getSprite() {
        return sprite;
    }

    abstract void update(Map map);

    abstract boolean isToRemove();

}
