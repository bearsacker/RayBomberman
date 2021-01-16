package com.bearsacker.engine;

import static com.bearsacker.engine.configs.EngineConfig.FPS;
import static com.bearsacker.engine.configs.EngineConfig.FULLSCREEN;
import static com.bearsacker.engine.configs.EngineConfig.HEIGHT;
import static com.bearsacker.engine.configs.EngineConfig.ICON;
import static com.bearsacker.engine.configs.EngineConfig.SHOW_FPS;
import static com.bearsacker.engine.configs.EngineConfig.TITLE;
import static com.bearsacker.engine.configs.EngineConfig.WIDTH;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.bearsacker.engine.gui.GUI;
import com.bearsacker.engine.gui.View;

public class Game extends BasicGame {

    private View initialView;

    public Game(View initialView) throws SlickException {
        super(TITLE);

        this.initialView = initialView;

        AppGameContainer app = new AppGameContainer(this);
        app.setDisplayMode(WIDTH, HEIGHT, FULLSCREEN);
        app.setShowFPS(SHOW_FPS);
        if (ICON != null) {
            app.setIcon(ICON);
        }
        if (FPS > 0) {
            app.setTargetFrameRate(FPS);
        }
        app.start();
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        GUI.get().switchView(initialView);
    }

    @Override
    public void render(GameContainer container, Graphics g) {
        GUI.get().paint(g);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        GUI.get().update(container);
    }

}
