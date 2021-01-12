package com.guillot.game.items;

import org.jbox2d.common.Vec2;

import com.guillot.game.Images;
import com.guillot.game.Player;

public class SpeedBonusItem extends Item {

    public SpeedBonusItem(Vec2 position) {
        super(Images.ITEM_SPEED_BONUS, position);

    }

    @Override
    public void use(Player player) {
        if (!used) {
            player.incrementSpeed();
            used = true;
        }
    }

}
