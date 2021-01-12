package com.guillot.game.events;

import org.jbox2d.common.Vec2;

import com.guillot.game.Map;
import com.guillot.game.items.BombBonusItem;
import com.guillot.game.items.BombMalusItem;
import com.guillot.game.items.GloveItem;
import com.guillot.game.items.Item;
import com.guillot.game.items.Items;
import com.guillot.game.items.PowerBombItem;
import com.guillot.game.items.RangeBonusItem;
import com.guillot.game.items.RangeMalusItem;
import com.guillot.game.items.RedBombItem;
import com.guillot.game.items.SpeedBonusItem;
import com.guillot.game.items.SpeedMalusItem;

public class AddItem implements Event {

    private Vec2 position;

    public AddItem(Vec2 position) {
        this.position = position;
    }

    @Override
    public void perform(Map map) {
        Items type = Items.getRandomItem();
        Item item = null;

        switch (type) {
        case BOMB_BONUS:
            item = new BombBonusItem(position);
            break;
        case BOMB_MALUS:
            item = new BombMalusItem(position);
            break;
        case GLOVE:
            item = new GloveItem(position);
            break;
        case POWERBOMB:
            item = new PowerBombItem(position);
            break;
        case RANGE_BONUS:
            item = new RangeBonusItem(position);
            break;
        case RANGE_MALUS:
            item = new RangeMalusItem(position);
            break;
        case REDBOMB:
            item = new RedBombItem(position);
            break;
        case SPEED_BONUS:
            item = new SpeedBonusItem(position);
            break;
        case SPEED_MALUS:
            item = new SpeedMalusItem(position);
            break;
        }

        map.getEntities().add(item);
    }
}
