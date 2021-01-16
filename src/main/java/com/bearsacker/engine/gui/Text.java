package com.bearsacker.engine.gui;

import static com.bearsacker.engine.configs.GUIConfig.COMPONENT_FILTER_COLOR;
import static com.bearsacker.engine.gui.GUI.drawColoredString;
import static java.lang.Math.max;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

public class Text extends Component {

    private String text;

    private ArrayList<String> lines;

    private TrueTypeFont font;

    public Text(String text) {
        this(text, 0, 0);
    }

    public Text(String text, int x, int y) {
        this(text, x, y, GUI.get().getFont(), COMPONENT_FILTER_COLOR);
    }

    public Text(String text, int x, int y, Color color) {
        this(text, x, y, GUI.get().getFont(), color);
    }

    public Text(String text, int x, int y, TrueTypeFont font) {
        this(text, x, y, font, COMPONENT_FILTER_COLOR);
    }

    public Text(String text, int x, int y, TrueTypeFont font, Color color) {
        super();

        this.x = x;
        this.y = y;
        this.font = font;
        lines = new ArrayList<>();
        filter = new Color(color);
        setText(text);
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

        resize();
    }

    public Color getColor() {
        return filter;
    }

    public void setColor(Color color) {
        this.filter = new Color(color);
    }

    public TrueTypeFont getFont() {
        return font;
    }

    public void setFont(TrueTypeFont font) {
        this.font = font;
    }

    private void resize() {
        int maxWidth = 0;
        for (String line : lines) {
            maxWidth = max(maxWidth, GUI.getColoredStringWidth(font, line));
        }

        width = maxWidth;
        height = lines.size() * font.getHeight();
    }

    @Override
    public void paint(Graphics g) {
        g.pushTransform();
        g.translate(x, y);

        for (int i = 0; i < lines.size(); i++) {
            drawColoredString(g, font, 0, i * font.getHeight(), lines.get(i), filter);
        }

        g.popTransform();
    }
}
