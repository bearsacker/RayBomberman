package com.guillot.engine.gui;

import static com.guillot.engine.configs.GUIConfig.BUTTON_BORDER;
import static com.guillot.engine.configs.GUIConfig.BUTTON_PADDING;
import static com.guillot.engine.configs.GUIConfig.BUTTON_SPRITE;
import static com.guillot.engine.configs.GUIConfig.BUTTON_SPRITE_SIZE;
import static com.guillot.engine.configs.GUIConfig.BUTTON_TEXT_COLOR;
import static com.guillot.engine.configs.GUIConfig.BUTTON_TEXT_HOVER_COLOR;
import static com.guillot.engine.configs.GUIConfig.COMPONENT_DISABLED_FILTER;
import static com.guillot.engine.configs.GUIConfig.COMPONENT_FILTER_COLOR;
import static org.newdawn.slick.Input.MOUSE_LEFT_BUTTON;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;


public class Button extends Component {

    private Image image;

    private Image icon;

    private Position iconAlignement;

    private String text;

    private Event event;

    private Color color;

    private Color colorHover;

    private TrueTypeFont font;

    private boolean enabled;

    private boolean widthAuto;

    public Button(String text, int x, int y, int width, int height) throws Exception {
        super();

        this.image = new Image(BUTTON_SPRITE);
        this.image.setFilter(Image.FILTER_NEAREST);
        this.x = x;
        this.y = y;
        this.text = text;
        this.color = new Color(BUTTON_TEXT_COLOR);
        this.colorHover = new Color(BUTTON_TEXT_HOVER_COLOR);
        this.enabled = true;
        this.font = GUI.get().getFont();
        this.width = width;
        this.height = height;
        this.widthAuto = false;
    }

    public Button(String text, int x, int y, int height) throws Exception {
        this(text, x, y, GUI.get().getFont().getWidth(text), height);

        widthAuto = true;
        resize();
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
        widthAuto = false;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isWidthAuto() {
        return widthAuto;
    }

    public void setWidthAuto(boolean widthAuto) {
        this.widthAuto = widthAuto;
        if (this.widthAuto) {
            resize();
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        if (widthAuto) {
            resize();
        }
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
        if (widthAuto) {
            resize();
        }
    }

    public void clearIcon() {
        icon = null;
    }

    public void setIcon(Image icon, Position alignement) {
        this.icon = icon;
        this.iconAlignement = alignement;
    }

    public void setIcon(Image icon) {
        setIcon(icon, Position.LEFT);
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

        int frame = 0;

        if (mouseOn && enabled && GUI.get().getInput().isMouseButtonDown(MOUSE_LEFT_BUTTON)) {
            frame = 1;
        }

        Image image = this.image.getSubImage(0, frame * BUTTON_SPRITE_SIZE, BUTTON_SPRITE_SIZE, BUTTON_SPRITE_SIZE);
        GUI.drawTiledImage(image, filter, width, height, BUTTON_SPRITE_SIZE, BUTTON_SPRITE_SIZE, BUTTON_BORDER);

        int lineHeight = font.getLineHeight();
        int lineWidth = GUI.getColoredStringWidth(font, text);
        GUI.drawColoredString(g, font, (width / 2) - (lineWidth / 2), (height / 2) - (lineHeight / 2), text,
                (mouseOn && enabled ? colorHover : color));

        if (icon != null) {
            int iconX = BUTTON_BORDER;
            int iconY = BUTTON_BORDER;

            switch (iconAlignement) {
            case CENTER:
                iconX = width / 2 - icon.getWidth() / 2;
                break;
            case RIGHT:
                iconX = width - 8 - icon.getWidth();
                break;
            case LEFT:
            case BOTTOM:
            case TOP:
                break;
            }

            g.drawImage(icon, iconX, iconY, iconX + height - BUTTON_BORDER * 2, iconY + height - BUTTON_BORDER * 2, 0, 0, icon.getWidth(),
                    icon.getHeight(), enabled && frame == 0 ? filter : COMPONENT_DISABLED_FILTER);
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

    private void resize() {
        width = GUI.getColoredStringWidth(this.font, text) + BUTTON_PADDING * 2;
    }
}
