package com.bearsacker.game.events;

import org.jbox2d.common.Vec2;

import com.bearsacker.game.Map;
import com.bearsacker.game.Player;
import com.bearsacker.game.Wall;
import com.bearsacker.game.entities.Fire;
import com.bearsacker.game.resources.Sounds;

public class AddExplosion implements Event {

    private Vec2 position;

    private int range;

    private boolean isPowerBomb;

    private boolean isRedBomb;

    public AddExplosion(Vec2 position, int range, boolean isPowerBomb, boolean isRedBomb) {
        this.position = position;
        this.range = range;
        this.isPowerBomb = isPowerBomb;
        this.isRedBomb = isRedBomb;
    }

    @Override
    public void perform(Map map, Player player) {
        float distanceFromPlayer = (player.getPosition().x - position.x) * (player.getPosition().x - position.x)
                + (player.getPosition().y - position.y) * (player.getPosition().y - position.y);
        if (distanceFromPlayer < 100f) {
            Sounds.EXPLOSION.getSound().play(1f, (100f - distanceFromPlayer) / 100f);
        }

        if (isPowerBomb) {
            range = Integer.MAX_VALUE;
        }

        for (int x = 0; x <= range; x++) {
            Vec2 p = new Vec2(position.x + x, position.y);
            map.getEntities().add(new Fire(p));

            Wall wall = map.getTile(p);
            if (wall != null) {
                if (wall.isBreakable()) {
                    map.breakWall(p);

                    if (!isRedBomb) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        for (int x = 0; x >= -range; x--) {
            Vec2 p = new Vec2(position.x + x, position.y);
            map.getEntities().add(new Fire(p));

            Wall wall = map.getTile(p);
            if (wall != null) {
                if (wall.isBreakable()) {
                    map.breakWall(p);

                    if (!isRedBomb) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        for (int y = 0; y <= range; y++) {
            Vec2 p = new Vec2(position.x, position.y + y);
            map.getEntities().add(new Fire(p));

            Wall wall = map.getTile(p);
            if (wall != null) {
                if (wall.isBreakable()) {
                    map.breakWall(p);

                    if (!isRedBomb) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        for (int y = 0; y >= -range; y--) {
            Vec2 p = new Vec2(position.x, position.y + y);
            map.getEntities().add(new Fire(p));

            Wall wall = map.getTile(p);
            if (wall != null) {
                if (wall.isBreakable()) {
                    map.breakWall(p);

                    if (!isRedBomb) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }
}
