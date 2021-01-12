package com.guillot.game.items;

import org.jbox2d.common.Vec2;

import com.guillot.game.Images;
import com.guillot.game.Player;

public class SpeedMalusItem extends Item {

    public SpeedMalusItem(Vec2 position) {
        super(Images.ITEM_SPEED_MALUS, position);

    }

    @Override
    public void use(Player player) {
        if (!used) {
            player.decrementSpeed();
            used = true;
        }
    }

}
