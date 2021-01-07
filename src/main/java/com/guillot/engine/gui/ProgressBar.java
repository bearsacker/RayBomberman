package com.guillot.engine.gui;

import static com.guillot.engine.configs.GUIConfig.PROGRESSBAR_BORDER;
import static com.guillot.engine.configs.GUIConfig.PROGRESSBAR_COLOR;
import static com.guillot.engine.configs.GUIConfig.PROGRESSBAR_SPRITE;
import static com.guillot.engine.configs.GUIConfig.PROGRESSBAR_SPRITE_SIZE;
import static org.apache.commons.math3.util.FastMath.max;
import static org.apache.commons.math3.util.FastMath.min;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ProgressBar extends Component {

    private Image image;

    private int value;

    private Color valueColor;

    public ProgressBar(int x, int y, int width, int height, float value) throws Exception {
        this(x, y, width, height, 0);
        setValue(value);
    }

    public ProgressBar(int x, int y, int width, int height, int value) throws Exception {
        super();

        image = new Image(PROGRESSBAR_SPRITE);
        image.setFilter(Image.FILTER_NEAREST);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        valueColor = new Color(PROGRESSBAR_COLOR);
        setValue(value);
    }

    @Override
    public void paint(Graphics g) {
        g.pushTransform();
        g.translate(x, y);

        GUI.drawTiledImage(image, filter, width, height, PROGRESSBAR_SPRITE_SIZE, PROGRESSBAR_SPRITE_SIZE, PROGRESSBAR_BORDER);

        int valueWidth = (int) (value / 100f * (width - PROGRESSBAR_BORDER * 2));

        g.setColor(valueColor);
        g.fillRect(PROGRESSBAR_BORDER, PROGRESSBAR_BORDER, valueWidth, height - PROGRESSBAR_BORDER * 2);

        g.popTransform();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = max(0, min(100, value));
    }

    public void setValue(float value) {
        this.value = (int) (max(0.0f, min(1.0f, value)) * 100);
    }

    public Color getValueColor() {
        return valueColor;
    }

    public void setValueColor(Color valueColor) {
        this.valueColor = valueColor;
    }

}
