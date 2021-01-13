package com.guillot.game.items;

import org.jbox2d.common.Vec2;

import com.guillot.game.Player;
import com.guillot.game.resources.Images;

public class BombMalusItem extends Item {

    public BombMalusItem(Vec2 position) {
        super(Images.ITEM_BOMB_MALUS, position);

    }

    @Override
    public void use(Player player) {
        if (!used) {
            player.decrementBombs();
            used = true;
        }
    }

}
