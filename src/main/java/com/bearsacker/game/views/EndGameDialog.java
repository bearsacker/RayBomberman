package com.bearsacker.game.views;


import static com.bearsacker.engine.configs.EngineConfig.HEIGHT;
import static com.bearsacker.engine.configs.EngineConfig.WIDTH;
import static com.bearsacker.engine.configs.GUIConfig.DIALOG_OVERLAY_COLOR;
import static org.newdawn.slick.Input.KEY_ESCAPE;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.bearsacker.engine.gui.Event;
import com.bearsacker.engine.gui.FadeTransition;
import com.bearsacker.engine.gui.GUI;
import com.bearsacker.engine.gui.LinkButton;
import com.bearsacker.engine.gui.SubView;
import com.bearsacker.engine.gui.Text;


public class EndGameDialog extends SubView {

    private LinkButton backToMenuButton;

    private LinkButton retryButton;

    public EndGameDialog(GameView parent, String textValue) throws Exception {
        super(parent);

        Text text = new Text(textValue, 0, 64, GUI.get().getFont(3), Color.yellow);
        text.setX(WIDTH / 2 - text.getWidth() / 2);

        retryButton = new LinkButton("Retry", WIDTH / 2 - 144, 208);
        retryButton.setColor(Color.yellow);
        retryButton.setX(WIDTH / 2 - retryButton.getWidth() / 2);
        retryButton.setEvent(new Event() {

            @Override
            public void perform() throws Exception {
                GUI.get().switchView(new FadeTransition(new GameView(parent.getMapPath(), parent.isArmageddon()), 1000));
            }
        });

        backToMenuButton = new LinkButton("Back to menu", WIDTH / 2 - 144, 272);
        backToMenuButton.setX(WIDTH / 2 - backToMenuButton.getWidth() / 2);
        backToMenuButton.setEvent(new Event() {

            @Override
            public void perform() throws Exception {
                GUI.get().switchView(new MenuView());
            }
        });

        add(text, backToMenuButton, retryButton);
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
