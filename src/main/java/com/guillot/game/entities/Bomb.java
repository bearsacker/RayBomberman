package com.guillot.game.entities;

import org.jbox2d.common.Vec2;

import com.guillot.game.Images;
import com.guillot.game.Map;
import com.guillot.game.Player;
import com.guillot.game.events.AddExplosion;

public class Bomb extends Entity {

    private final static int TIME_BEFORE_EXPLODE = 3000;

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
        map.pushEvent(new AddExplosion(position, range, owner.hasPowerBomb(), owner.hasRedBomb()));
        exploded = true;
        owner.incrementBombs();
    }

}
