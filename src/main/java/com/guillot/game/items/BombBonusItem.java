package com.guillot.game.items;

import org.jbox2d.common.Vec2;

import com.guillot.game.Images;
import com.guillot.game.Player;

public class BombBonusItem extends Item {

    public BombBonusItem(Vec2 position) {
        super(Images.ITEM_BOMB_BONUS, position);
    }

    @Override
    public void use(Player player) {
        if (!used) {
            player.incrementBombs();
            used = true;
        }
    }

}
