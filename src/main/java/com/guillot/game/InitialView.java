package com.guillot.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.guillot.engine.Game;
import com.guillot.engine.gui.View;

public class InitialView extends View {

    @Override
    public void start() throws Exception {
        setBackgroundColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
    }

    @Override
    public void update() throws Exception {
        super.update();
    }

    @Override
    public void paint(Graphics g) throws Exception {
        super.paint(g);
    }

    public static void main(String[] args) throws SlickException {
        new Game(new InitialView());
    }
}
