package com.bearsacker.game.items;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Playable;
import com.bearsacker.game.resources.Images;

public class RedBombItem extends Item {

    public RedBombItem(Vec2 position) {
        super(Images.ITEM_REDBOMB, position);

    }

    @Override
    public void use(Playable player) {
        if (!used) {
            player.pickUpRedBomb();
            used = true;
        }
    }

    @Override
    public boolean isBonus() {
        return true;
    }
}
