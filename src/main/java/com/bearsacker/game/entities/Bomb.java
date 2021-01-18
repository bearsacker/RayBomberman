package com.bearsacker.game.entities;

import static com.bearsacker.game.configs.GameConfig.BOMB_SPEED;
import static com.bearsacker.game.configs.GameConfig.BOMB_TIME;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Map;
import com.bearsacker.game.Player;
import com.bearsacker.game.events.AddExplosion;
import com.bearsacker.game.resources.Images;

public class Bomb extends Entity {

    private int range;

    private long plantTime;

    private boolean exploded;

    private boolean isRedbomb;

    private Player owner;

    private long lastUpdate;

    public Bomb(Player owner, Vec2 position, int range, boolean isRedbomb) {
        super(Images.BOMB, position);

        this.owner = owner;
        this.plantTime = System.currentTimeMillis();
        this.range = range;
        this.exploded = false;
        this.isRedbomb = isRedbomb;
    }

    @Override
    public void update(Map map) {
        long time = System.currentTimeMillis();
        float frameTime = (time - lastUpdate) / 1000f;
        lastUpdate = time;

        float moveSpeed = frameTime * BOMB_SPEED;

        if (time - plantTime > BOMB_TIME) {
            explode(map);
        } else if (direction != null) {
            if (map.getTile((int) (position.x + direction.x * moveSpeed), (int) (position.y)) == null) {
                position.x += direction.x * moveSpeed;
            } else {
                direction.x = 0f;
            }

            if (map.getTile((int) (position.x), (int) (position.y + direction.y * moveSpeed)) == null) {
                position.y += direction.y * moveSpeed;
            } else {
                direction.y = 0f;
            }
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