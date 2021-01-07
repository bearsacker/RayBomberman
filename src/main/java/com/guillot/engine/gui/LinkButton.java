package com.guillot.engine.gui;

import static com.guillot.engine.configs.GUIConfig.COMPONENT_DISABLED_FILTER;
import static com.guillot.engine.configs.GUIConfig.COMPONENT_FILTER_COLOR;
import static com.guillot.engine.configs.GUIConfig.LINK_BUTTON_TEXT_COLOR;
import static com.guillot.engine.configs.GUIConfig.LINK_BUTTON_TEXT_HOVER_COLOR;
import static com.guillot.engine.configs.GUIConfig.TEXTFIELD_PADDING;
import static org.newdawn.slick.Input.MOUSE_LEFT_BUTTON;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;


public class LinkButton extends Component {

    private String text;

    private Event event;

    private Color color;

    private Color colorHover;

    private TrueTypeFont font;

    private boolean enabled;

    private Position alignement;

    public LinkButton(String text, int x, int y, int width) throws Exception {
        super();

        this.x = x;
        this.y = y;
        this.text = text;
        this.width = width;
        color = new Color(LINK_BUTTON_TEXT_COLOR);
        colorHover = new Color(LINK_BUTTON_TEXT_HOVER_COLOR);
        enabled = true;
        font = GUI.get().getFont();
        height = font.getLineHeight();
        alignement = Position.LEFT;
    }

    public LinkButton(String text, int x, int y) throws Exception {
        this(text, x, y, GUI.get().getFont().getWidth(text));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = new Color(color);
    }

    public Color getColorHover() {
        return colorHover;
    }

    public void setColorHover(Color colorHover) {
        this.colorHover = new Color(colorHover);
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setOpacity(float opacity) {
        super.setOpacity(opacity);
        color.a = opacity;
        colorHover.a = opacity;
    }

    public TrueTypeFont getFont() {
        return font;
    }

    public void setFont(TrueTypeFont font) {
        this.font = font;
        height = this.font.getLineHeight();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        float opacity = filter.a;
        filter = enabled ? new Color(COMPONENT_FILTER_COLOR) : new Color(COMPONENT_DISABLED_FILTER);
        filter.a = opacity;
    }

    @Override
    public void paint(Graphics g) {
        g.pushTransform();
        g.translate(x, y);

        Color textColor = enabled && mouseOn ? colorHover : (enabled ? color : new Color(color).darker());
        int lineWidth = GUI.get().getFont().getWidth(text);

        switch (alignement) {
        case CENTER:
            font.drawString(width / 2 - lineWidth / 2, 0, text, textColor);
            break;
        case RIGHT:
            font.drawString(width - lineWidth - TEXTFIELD_PADDING, 0, text, textColor);
            break;
        case LEFT:
        case BOTTOM:
        case TOP:
            font.drawString(TEXTFIELD_PADDING, 0, text, textColor);
            break;
        }

        g.popTransform();
    }

    @Override
    public void update(int offsetX, int offsetY) throws Exception {
        super.update(offsetX, offsetY);

        if (enabled && mouseOn && GUI.get().isMouseButtonReleased(MOUSE_LEFT_BUTTON) && event != null) {
            event.perform();
        }
    }

    public void setEvent(Event e) {
        event = e;
    }
}
