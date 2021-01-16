package com.bearsacker.engine.gui;

import static com.bearsacker.engine.configs.EngineConfig.HEIGHT;
import static com.bearsacker.engine.configs.EngineConfig.WIDTH;
import static com.bearsacker.engine.configs.GUIConfig.DIALOG_BUTTON_TEXT;
import static com.bearsacker.engine.configs.GUIConfig.DIALOG_OVERLAY_COLOR;

import org.newdawn.slick.Graphics;

public class Dialog extends SubView {

    protected TextBox textBox;

    protected Button buttonClose;

    public Dialog(View parent, int width, String text, String buttonText) throws Exception {
        super(parent);

        textBox = new TextBox(WIDTH / 2 - width / 2, y, width, text);
        textBox.setHeight(textBox.getHeight() + 60);
        textBox.setY(HEIGHT / 2 - textBox.getHeight() / 2);

        buttonClose = new Button(buttonText, WIDTH / 2 - 100 / 2, textBox.getY() + textBox.getHeight() - 50, 100, 30);
        buttonClose.setEvent(new Event() {

            public void perform() throws Exception {
                setVisible(false);
            }
        });

        add(textBox, buttonClose);
    }

    public Dialog(View parent, int width, String text) throws Exception {
        this(parent, width, text, DIALOG_BUTTON_TEXT);
    }

    public String getText() {
        return textBox.getText();
    }

    public void setText(String text) {
        textBox.setText(text);
        textBox.setHeight(textBox.getHeight() + 60);

        textBox.setY(HEIGHT / 2 - textBox.getHeight() / 2);
        buttonClose.setY(textBox.getY() + textBox.getHeight() - 50);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(DIALOG_OVERLAY_COLOR);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        super.paint(g);
    }

    @Override
    public void onShow() throws Exception {}

    @Override
    public void onHide() throws Exception {}
}
