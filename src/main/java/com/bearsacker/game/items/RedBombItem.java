package com.bearsacker.game.items;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Player;
import com.bearsacker.game.resources.Images;

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
