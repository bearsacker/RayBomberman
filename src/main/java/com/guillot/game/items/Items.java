package com.guillot.game.items;

import com.guillot.engine.utils.RandomCollection;

public enum Items {
    SPEED_BONUS(2), //
    SPEED_MALUS(2), //
    BOMB_BONUS(2), //
    BOMB_MALUS(2), //
    RANGE_BONUS(2), //
    RANGE_MALUS(2), //
    REDBOMB(1), //
    GLOVE(1), //
    POWERBOMB(1);

    private int weight;

    private Items(int weight) {
        this.weight = weight;
    }

    public static Items getRandomItem() {
        RandomCollection<Items> items = new RandomCollection<>();
        for (Items item : Items.values()) {
            items.add(item.weight, item);
        }

        return items.next();
    }
}
