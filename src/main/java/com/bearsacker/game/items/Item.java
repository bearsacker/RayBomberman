package com.bearsacker.game.items;

import static com.bearsacker.game.configs.GameConfig.FIRE_TIME;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Map;
import com.bearsacker.game.Playable;
import com.bearsacker.game.entities.Entity;
import com.bearsacker.game.resources.Images;

public abstract class Item extends Entity {

    private long time;

    protected Items type;

    protected boolean used;

    public Item(Images sprite, Vec2 position) {
        super(sprite, position);

        time = System.currentTimeMillis();
    }

    @Override
    public boolean isToRemove() {
        return used;
    }

    @Override
    public void update(Map map) {
        if (System.currentTimeMillis() - time > FIRE_TIME && map.isBurningAt(position)) {
            used = true;
        }
    }

    public abstract void use(Playable player);

    public abstract boolean isBonus();

}
