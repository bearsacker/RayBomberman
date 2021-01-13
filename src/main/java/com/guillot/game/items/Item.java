package com.guillot.game.items;

import org.jbox2d.common.Vec2;

import com.guillot.game.Map;
import com.guillot.game.Player;
import com.guillot.game.entities.Entity;
import com.guillot.game.resources.Images;

public abstract class Item extends Entity {

    protected Items type;

    protected boolean used;

    public Item(Images sprite, Vec2 position) {
        super(sprite, position);
    }

    @Override
    public boolean isToRemove() {
        return used;
    }

    @Override
    public void update(Map map) {}

    public abstract void use(Player player);

}
