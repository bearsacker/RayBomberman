package com.bearsacker.engine.gui;

import static com.bearsacker.engine.configs.GUIConfig.CHECKBOX_SPRITE;
import static com.bearsacker.engine.configs.GUIConfig.CHECKBOX_SPRITE_SIZE;
import static com.bearsacker.engine.configs.GUIConfig.COMPONENT_DISABLED_FILTER;
import static com.bearsacker.engine.configs.GUIConfig.COMPONENT_FILTER_COLOR;
import static org.newdawn.slick.Input.MOUSE_LEFT_BUTTON;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class CheckBox extends Component {

    private Image image;

    private boolean enabled;

    private boolean value;

    public CheckBox(int x, int y, int width, int height, boolean value) throws Exception {
        super();

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        image = new Image(CHECKBOX_SPRITE);
        image.setFilter(Image.FILTER_NEAREST);
        enabled = true;
        this.value = value;
    }

    @Override
    public void paint(Graphics g) {
        int frame = value ? 1 : 0;

        g.pushTransform();
        g.translate(x, y);

        Image image = this.image.getSubImage(0, frame * CHECKBOX_SPRITE_SIZE, CHECKBOX_SPRITE_SIZE, CHECKBOX_SPRITE_SIZE);
        image.draw(0, 0, width, height);

        g.popTransform();
    }

    @Override
    public void update(int offsetX, int offsetY) throws Exception {
        super.update(offsetX, offsetY);

        if (enabled && mouseOn && GUI.get().getInput().isMousePressed(MOUSE_LEFT_BUTTON)) {
            value = !value;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        float opacity = this.filter.a;
        this.filter = enabled ? new Color(COMPONENT_FILTER_COLOR) : new Color(COMPONENT_DISABLED_FILTER);
        this.filter.a = opacity;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
