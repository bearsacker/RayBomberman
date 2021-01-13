package com.guillot.game.items;

import org.jbox2d.common.Vec2;

import com.guillot.game.Player;
import com.guillot.game.resources.Images;

public class PowerBombItem extends Item {

    public PowerBombItem(Vec2 position) {
        super(Images.ITEM_POWERBOMB, position);

    }

    @Override
    public void use(Player player) {
        if (!used) {
            player.pickUpPowerBomb();
            used = true;
        }
    }

}
