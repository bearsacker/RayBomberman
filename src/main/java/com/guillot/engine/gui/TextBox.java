package com.guillot.engine.gui;

import static com.guillot.engine.configs.GUIConfig.TEXTBOX_BORDER;
import static com.guillot.engine.configs.GUIConfig.TEXTBOX_PADDING;
import static com.guillot.engine.configs.GUIConfig.TEXTBOX_SPRITE;
import static com.guillot.engine.configs.GUIConfig.TEXTBOX_SPRITE_SIZE;
import static com.guillot.engine.configs.GUIConfig.TEXTBOX_TEXT_COLOR;
import static com.guillot.engine.gui.GUI.drawColoredString;
import static java.lang.Math.max;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


public class TextBox extends Component {

    private Image image;

    private String text;

    private ArrayList<String> lines;

    private Color textColor;

    private boolean drawBox;

    private boolean autoWidth;

    public TextBox() throws Exception {
        this(0, 0, 0, "");

        drawBox = true;
        autoWidth = true;

        resizeWidth();
        resizeHeight();
    }

    public TextBox(int x, int y, int width, String text) throws Exception {
        super();

        image = new Image(TEXTBOX_SPRITE);
        image.setFilter(Image.FILTER_NEAREST);
        textColor = new Color(TEXTBOX_TEXT_COLOR);
        lines = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.width = width;
        setText(text);
        drawBox = true;
    }

    @Override
    public void paint(Graphics g) {
        g.pushTransform();
        g.translate(x, y);

        if (drawBox) {
            GUI.drawTiledImage(image, filter, width, height, TEXTBOX_SPRITE_SIZE, TEXTBOX_SPRITE_SIZE, TEXTBOX_BORDER);
        }

        for (int i = 0; i < lines.size(); i++) {
            drawColoredString(g, GUI.get().getFont(), TEXTBOX_PADDING, i * GUI.get().getFont().getHeight() + TEXTBOX_PADDING, lines.get(i),
                    TEXTBOX_TEXT_COLOR);
        }

        g.popTransform();
    }

    private void resizeWidth() {
        int maxWidth = 0;
        for (String line : lines) {
            maxWidth = max(maxWidth, GUI.getColoredStringWidth(GUI.get().getFont(), line) + TEXTBOX_PADDING * 2);
        }

        width = maxWidth;
    }

    private void resizeHeight() {
        height = lines.size() * GUI.get().getFont().getHeight() + TEXTBOX_PADDING * 2;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;

        lines.clear();
        String[] temp = text.split("\n");
        for (String line : temp) {
            lines.add(line);
        }

        if (autoWidth) {
            resizeWidth();
        }
        resizeHeight();
    }

    public void clear() {
        setText("");
    }

    @Override
    public void setOpacity(float opacity) {
        super.setOpacity(opacity);
        textColor.a = opacity;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = new Color(textColor);
    }

    public boolean isDrawBox() {
        return drawBox;
    }

    public void setDrawBox(boolean drawBox) {
        this.drawBox = drawBox;
    }

    public boolean isAutoWidth() {
        return autoWidth;
    }

    public void setAutoWidth(boolean autoWidth) {
        this.autoWidth = autoWidth;
    }

}
