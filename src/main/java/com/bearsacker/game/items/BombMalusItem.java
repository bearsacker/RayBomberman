package com.bearsacker.game.items;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Playable;
import com.bearsacker.game.resources.Images;

public class BombMalusItem extends Item {

    public BombMalusItem(Vec2 position) {
        super(Images.ITEM_BOMB_MALUS, position);

    }

    @Override
    public void use(Playable player) {
        if (!used) {
            player.decrementBombs();
            used = true;
        }
    }

    @Override
    public boolean isBonus() {
        return false;
    }
}
