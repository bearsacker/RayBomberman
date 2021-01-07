package com.guillot.engine.gui;

import static com.guillot.engine.configs.GUIConfig.TEXTFIELD_BORDER;
import static com.guillot.engine.configs.GUIConfig.TEXTFIELD_PADDING;
import static com.guillot.engine.configs.GUIConfig.TEXTFIELD_SPRITE;
import static com.guillot.engine.configs.GUIConfig.TEXTFIELD_SPRITE_SIZE;
import static com.guillot.engine.configs.GUIConfig.TEXTFIELD_TEXT_COLOR;
import static org.newdawn.slick.Input.KEY_BACK;
import static org.newdawn.slick.Input.MOUSE_LEFT_BUTTON;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;


public class TextField extends Component implements KeyListener {

    private Image image;

    private boolean focus;

    private String value;

    private Color textColor;

    private String invalidChars;

    private boolean added;

    private Position alignement;

    private boolean enabled;

    public TextField(int x, int y, int width, int height) throws Exception {
        super();

        image = new Image(TEXTFIELD_SPRITE);
        image.setFilter(Image.FILTER_NEAREST);
        this.x = x;
        this.y = y;
        this.value = "";
        this.width = width;
        this.height = height;
        focus = false;
        textColor = new Color(TEXTFIELD_TEXT_COLOR);
        invalidChars = "";
        alignement = Position.LEFT;
        enabled = true;
    }

    public TextField(int x, int y, int width, int height, String value) throws Exception {
        this(x, y, width, height);

        this.value = value;
    }

    public void setSprite(String sprite) throws Exception {
        this.image = new Image(sprite);
    }

    @Override
    public void setOpacity(float opacity) {
        super.setOpacity(opacity);
        textColor.a = opacity;
    }

    @Override
    public void destroy() {
        GUI.get().getInput().removeKeyListener(this);
    }

    @Override
    public void update(int offsetX, int offsetY) throws Exception {
        super.update(offsetX, offsetY);

        if (enabled) {
            if (GUI.get().getInput() != null && !added) {
                GUI.get().getInput().addKeyListener(this);
                added = true;
            }

            if (GUI.get().isMouseButtonReleased(MOUSE_LEFT_BUTTON)) {
                focus = mouseOn;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.pushTransform();
        g.translate(x, y);

        int frame = !focus && !mouseOn ? 0 : 1;
        Image image = this.image.getSubImage(0, frame * TEXTFIELD_SPRITE_SIZE, TEXTFIELD_SPRITE_SIZE, TEXTFIELD_SPRITE_SIZE);
        GUI.drawTiledImage(image, filter, width, height, TEXTFIELD_SPRITE_SIZE, TEXTFIELD_SPRITE_SIZE, TEXTFIELD_BORDER);

        int lineHeight = GUI.get().getFont().getLineHeight();
        int lineWidth = GUI.get().getFont().getWidth(value);

        switch (alignement) {
        case CENTER:
            GUI.get().getFont().drawString(width / 2 - lineWidth / 2, height / 2 - lineHeight / 2, value, textColor);
            break;
        case RIGHT:
            GUI.get().getFont().drawString(width - lineWidth - TEXTFIELD_PADDING, height / 2 - lineHeight / 2, value, textColor);
            break;
        case LEFT:
        case BOTTOM:
        case TOP:
            GUI.get().getFont().drawString(TEXTFIELD_PADDING, height / 2 - lineHeight / 2, value, textColor);
            break;
        }

        g.popTransform();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = new Color(textColor);
    }

    public void clear() {
        this.value = "";
    }

    public String getInvalidChars() {
        return invalidChars;
    }

    public void setInvalidChars(String invalidChars) {
        this.invalidChars = invalidChars;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Position getAlignement() {
        return alignement;
    }

    public void setAlignement(Position alignement) {
        this.alignement = alignement;
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public void keyPressed(int key, char c) {
        if (key == KEY_BACK) {
            if (value.length() > 0) {
                value = value.substring(0, value.length() - 1);
            }
        } else if (c != '\0' && GUI.get().getFont().getWidth(value) + TEXTFIELD_PADDING * 2 < width && !invalidChars.contains("" + c)) {
            value += c;
        }
    }

    @Override
    public void keyReleased(int key, char c) {}

    @Override
    public void setInput(Input input) {}

    @Override
    public boolean isAcceptingInput() {
        return focus;
    }

    @Override
    public void inputEnded() {}

    @Override
    public void inputStarted() {}
}
