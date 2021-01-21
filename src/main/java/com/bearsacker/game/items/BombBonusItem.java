package com.bearsacker.game.items;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Playable;
import com.bearsacker.game.resources.Images;

public class BombBonusItem extends Item {

    public BombBonusItem(Vec2 position) {
        super(Images.ITEM_BOMB_BONUS, position);
    }

    @Override
    public void use(Playable player) {
        if (!used) {
            player.incrementBombs();
            used = true;
        }
    }

    @Override
    public boolean isBonus() {
        return true;
    }

}
