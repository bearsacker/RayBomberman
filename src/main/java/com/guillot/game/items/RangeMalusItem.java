package com.guillot.game.items;

import org.jbox2d.common.Vec2;

import com.guillot.game.Images;
import com.guillot.game.Player;

public class RangeMalusItem extends Item {

    public RangeMalusItem(Vec2 position) {
        super(Images.ITEM_RANGE_MALUS, position);

    }

    @Override
    public void use(Player player) {
        if (!used) {
            player.decrementRange();
            used = true;
        }
    }

}