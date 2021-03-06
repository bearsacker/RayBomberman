package com.bearsacker.game.items;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Playable;
import com.bearsacker.game.resources.Images;

public class GloveItem extends Item {

    public GloveItem(Vec2 position) {
        super(Images.ITEM_GLOVE, position);

    }

    @Override
    public void use(Playable player) {
        if (!used) {
            player.pickUpGlove();
            used = true;
        }
    }

    @Override
    public boolean isBonus() {
        return true;
    }
}
