package com.bearsacker.game.items;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Player;
import com.bearsacker.game.resources.Images;

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
