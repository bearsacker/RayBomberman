package com.bearsacker.game.events;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Map;
import com.bearsacker.game.Playable;
import com.bearsacker.game.entities.Bomb;

public class PlantBomb implements Event {

    private Playable player;

    private Vec2 position;

    private int bombRange;

    private boolean isRedBomb;

    public PlantBomb(Playable player, Vec2 position, int bombRange, boolean isRedBomb) {
        this.player = player;
        this.position = position;
        this.bombRange = bombRange;
        this.isRedBomb = isRedBomb;
    }

    @Override
    public void perform(Map map) {
        map.getEntities().add(new Bomb(player, position, bombRange, isRedBomb));
    }
}
