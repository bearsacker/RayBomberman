package com.guillot.game.items;

import org.jbox2d.common.Vec2;

import com.guillot.game.Images;
import com.guillot.game.Player;

public class RedBombItem extends Item {

    public RedBombItem(Vec2 position) {
        super(Images.ITEM_REDBOMB, position);

    }

    @Override
    public void use(Player player) {
        if (!used) {
            player.pickUpRedBomb();
            used = true;
        }
    }

}
