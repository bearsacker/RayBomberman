package com.guillot.game;

import org.jbox2d.common.Vec2;

public class Bomb extends Entity {

    private final static int TIME_BEFORE_EXPLODE = 5000;

    private int range;

    private long time;

    private boolean exploded;

    private Player owner;

    public Bomb(Player owner, Vec2 position, int range) {
        super(Images.BOMB, position);

        this.owner = owner;
        this.time = System.currentTimeMillis();
        this.range = range;
        this.exploded = false;
    }

    @Override
    public void update(Map map) {
        if (System.currentTimeMillis() - time > TIME_BEFORE_EXPLODE) {
            explode(map);
        }
    }

    @Override
    public boolean isToRemove() {
        return exploded;
    }

    public void explode(Map map) {
        map.pushEvent(new AddExplosion(position, range));
        exploded = true;
        owner.incrementBombs();
    }

}
