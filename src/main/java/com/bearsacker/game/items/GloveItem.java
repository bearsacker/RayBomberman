package com.bearsacker.game.items;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Player;
import com.bearsacker.game.resources.Images;

public class GloveItem extends Item {

    public GloveItem(Vec2 position) {
        super(Images.ITEM_GLOVE, position);

    }

    @Override
    public void use(Player player) {
        if (!used) {
            player.pickUpGlove();
            used = true;
        }
    }

}
