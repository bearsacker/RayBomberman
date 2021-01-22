package com.bearsacker.engine.configs;

import org.apache.commons.configuration2.Configuration;

public final class EngineConfig extends Config {

    private static final EngineConfig INSTANCE = new EngineConfig("engine.properties");

    public final static int WIDTH = get().getInt("engine.width", 800);

    public final static int HEIGHT = get().getInt("engine.height", 600);

    public final static float SCALE = get().getFloat("engine.scale", 1f);

    public final static String TITLE = get().getString("engine.title", "Application");

    public final static String ICON = get().getString("engine.icon", null);

    public final static boolean FULLSCREEN = get().getBoolean("engine.fullscreen", false);

    public final static int FPS = get().getInt("engine.fps", 0);

    public final static boolean SHOW_FPS = get().getBoolean("engine.fps.show", true);

    private EngineConfig(String file) {
        super(file);
    }

    public static Configuration get() {
        return INSTANCE.configuration;
    }
}
