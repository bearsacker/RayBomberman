package com.guillot.game.items;

import org.jbox2d.common.Vec2;

import com.guillot.game.Player;
import com.guillot.game.resources.Images;

public class RangeBonusItem extends Item {

    public RangeBonusItem(Vec2 position) {
        super(Images.ITEM_RANGE_BONUS, position);

    }

    @Override
    public void use(Player player) {
        if (!used) {
            player.incrementRange();
            used = true;
        }
    }

}
