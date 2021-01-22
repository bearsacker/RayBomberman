package com.bearsacker.game.views;


import static com.bearsacker.engine.configs.EngineConfig.HEIGHT;
import static com.bearsacker.engine.configs.EngineConfig.WIDTH;
import static com.bearsacker.engine.configs.GUIConfig.DIALOG_OVERLAY_COLOR;
import static org.newdawn.slick.Input.KEY_ESCAPE;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;

import com.bearsacker.engine.gui.Event;
import com.bearsacker.engine.gui.GUI;
import com.bearsacker.engine.gui.LinkButton;
import com.bearsacker.engine.gui.SubView;


public class MenuDialog extends SubView {

    private LinkButton backToMenuButton;

    private LinkButton quitButton;

    public MenuDialog(GameView parent) throws Exception {
        super(parent);

        backToMenuButton = new LinkButton("Back to menu", WIDTH / 2 - 144, 144);
        backToMenuButton.setX(WIDTH / 2 - backToMenuButton.getWidth() / 2);
        backToMenuButton.setEvent(new Event() {

            @Override
            public void perform() throws Exception {
                GUI.get().switchView(new MenuView());
            }
        });

        quitButton = new LinkButton("Quit", WIDTH / 2 - 144, 208);
        quitButton.setX(WIDTH / 2 - quitButton.getWidth() / 2);
        quitButton.setEvent(new Event() {

            @Override
            public void perform() throws Exception {
                GUI.get().close();
            }
        });

        add(backToMenuButton, quitButton);
    }

    @Override
    public void onShow() throws Exception {
        Mouse.setGrabbed(false);
    }

    @Override
    public void onHide() throws Exception {
        Mouse.setGrabbed(true);
    }

    @Override
    public void update(int offsetX, int offsetY) throws Exception {
        super.update(offsetX, offsetY);

        if (GUI.get().isKeyPressed(KEY_ESCAPE)) {
            setVisible(false);
            GUI.get().clearKeysPressed();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(DIALOG_OVERLAY_COLOR);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        super.paint(g);
    }

}
