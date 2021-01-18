package com.bearsacker.game.entities;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Map;
import com.bearsacker.game.resources.Images;

public abstract class Entity {

    protected Vec2 position;

    protected Vec2 direction;

    protected Images sprite;

    public Entity(Images sprite, Vec2 position) {
        this.sprite = sprite;
        this.position = new Vec2(((int) position.x) + .5f, ((int) position.y) + .5f);
    }

    public double distanceFrom(Vec2 point) {
        return (position.x - point.x) * (position.x - point.x) + (position.y - point.y) * (position.y - point.y);
    }

    public Vec2 getDirection() {
        return direction;
    }

    public void setDirection(Vec2 direction) {
        this.direction = new Vec2(direction);
    }

    public Vec2 getPosition() {
        return position;
    }

    public Images getSprite() {
        return sprite;
    }

    public abstract void update(Map map);

    public abstract boolean isToRemove();

}
