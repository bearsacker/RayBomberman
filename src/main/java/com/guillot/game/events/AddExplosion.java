package com.guillot.game.events;

import org.jbox2d.common.Vec2;

import com.guillot.game.Map;
import com.guillot.game.Wall;
import com.guillot.game.entities.Fire;

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
    public void perform(Map map) {
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
