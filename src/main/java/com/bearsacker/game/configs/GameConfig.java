package com.bearsacker.game.configs;

import org.apache.commons.configuration2.Configuration;

import com.bearsacker.engine.configs.Config;

public final class GameConfig extends Config {

    private static final GameConfig INSTANCE = new GameConfig("game.properties");

    public final static int SCREEN_WIDTH = get().getInt("screen.width", 320);

    public final static int SCREEN_HEIGHT = get().getInt("screen.height", 200);

    public final static int TILE_SIZE = get().getInt("tile.size", 64);

    public final static float ITEM_PROBABILITY = get().getFloat("item.probability");

    public final static float PLAYER_SPEED = get().getFloat("player.speed");

    public final static float BOMB_SPEED = get().getFloat("bomb.speed");

    public final static int BOMB_TIME = get().getInt("bomb.time", 3000);

    public final static int FIRE_TIME = get().getInt("fire.time", 1000);

    private GameConfig(String file) {
        super(file);
    }

    public static Configuration get() {
        return INSTANCE.configuration;
    }
}
