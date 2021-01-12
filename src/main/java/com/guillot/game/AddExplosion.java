package com.guillot.game;

import org.jbox2d.common.Vec2;

public class AddExplosion implements Event {

    private Vec2 position;

    private int range;

    public AddExplosion(Vec2 position, int range) {
        this.position = position;
        this.range = range;
    }

    @Override
    public void perform(Map map) {
        for (int x = 0; x <= range; x++) {
            Vec2 p = new Vec2(position.x + x, position.y);
            map.getEntities().add(new Fire(p));

            Wall wall = map.getTile(p);
            if (wall != null) {
                if (wall.isBreakable()) {
                    map.setTile(p, null);
                }

                break;
            }
        }

        for (int x = -range; x <= 0; x++) {
            Vec2 p = new Vec2(position.x + x, position.y);
            map.getEntities().add(new Fire(p));

            Wall wall = map.getTile(p);
            if (wall != null) {
                if (wall.isBreakable()) {
                    map.setTile(p, null);
                }

                break;
            }
        }

        for (int y = 0; y <= range; y++) {
            Vec2 p = new Vec2(position.x, position.y + y);
            map.getEntities().add(new Fire(p));

            Wall wall = map.getTile(p);
            if (wall != null) {
                if (wall.isBreakable()) {
                    map.setTile(p, null);
                }

                break;
            }
        }

        for (int y = -range; y <= 0; y++) {
            Vec2 p = new Vec2(position.x, position.y + y);
            map.getEntities().add(new Fire(p));

            Wall wall = map.getTile(p);
            if (wall != null) {
                if (wall.isBreakable()) {
                    map.setTile(p, null);
                }

                break;
            }
        }
    }
}
