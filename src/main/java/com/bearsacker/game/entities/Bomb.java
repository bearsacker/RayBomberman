package com.bearsacker.game.entities;

import static com.bearsacker.game.configs.GameConfig.BOMB_TIME;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Map;
import com.bearsacker.game.Player;
import com.bearsacker.game.events.AddExplosion;
import com.bearsacker.game.resources.Images;

public class Bomb extends Entity {

    private int range;

    private long time;

    private boolean exploded;

    private boolean isRedbomb;

    private Player owner;

    public Bomb(Player owner, Vec2 position, int range, boolean isRedbomb) {
        super(Images.BOMB, position);

        this.owner = owner;
        this.time = System.currentTimeMillis();
        this.range = range;
        this.exploded = false;
        this.isRedbomb = isRedbomb;
    }

    @Override
    public void update(Map map) {
        if (System.currentTimeMillis() - time > BOMB_TIME) {
            explode(map);
        }
    }

    @Override
    public boolean isToRemove() {
        return exploded;
    }

    @Override
    public Images getSprite() {
        if (isRedbomb) {
            return Images.REDBOMB;
        }

        return super.getSprite();
    }

    public void explode(Map map) {
        map.pushEvent(new AddExplosion(position, range, owner.hasPowerBomb(), owner.hasRedBomb()));
        exploded = true;
        owner.retrieveBomb();
    }

}
