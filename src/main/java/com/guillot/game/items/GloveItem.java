package com.guillot.game.items;

import org.jbox2d.common.Vec2;

import com.guillot.game.Images;
import com.guillot.game.Player;

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
