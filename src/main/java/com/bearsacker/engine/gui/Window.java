package com.bearsacker.engine.gui;

import static com.bearsacker.engine.configs.EngineConfig.HEIGHT;
import static com.bearsacker.engine.configs.EngineConfig.WIDTH;
import static com.bearsacker.engine.configs.GUIConfig.DIALOG_OVERLAY_COLOR;
import static com.bearsacker.engine.configs.GUIConfig.WINDOW_BODY_BORDER;
import static com.bearsacker.engine.configs.GUIConfig.WINDOW_BODY_SIZE;
import static com.bearsacker.engine.configs.GUIConfig.WINDOW_BODY_SPRITE;
import static com.bearsacker.engine.configs.GUIConfig.WINDOW_CLOSE_BUTTON_SIZE;
import static com.bearsacker.engine.configs.GUIConfig.WINDOW_CLOSE_BUTTON_SPRITE;
import static com.bearsacker.engine.configs.GUIConfig.WINDOW_HEADER_BORDER;
import static com.bearsacker.engine.configs.GUIConfig.WINDOW_HEADER_HEIGHT;
import static com.bearsacker.engine.configs.GUIConfig.WINDOW_HEADER_SPRITE;
import static com.bearsacker.engine.configs.GUIConfig.WINDOW_HEADER_TEXT_COLOR;
import static com.bearsacker.engine.configs.GUIConfig.WINDOW_HEADER_WIDTH;
import static com.bearsacker.engine.gui.GUI.drawColoredString;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Window extends SubView {

    private Image imageHeader;

    private Image imageBody;

    private Button closeButton;

    private int titleWidth;

    protected String title;

    protected boolean showOverlay;

    protected boolean showHeader;

    public Window(View parent, int x, int y, int width, int height) throws Exception {
        this(parent, x, y, width, height, null);
    }

    public Window(View parent, int x, int y, int width, int height, String title) throws Exception {
        super(parent);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setTitle(title);
        imageHeader = new Image(WINDOW_HEADER_SPRITE);
        imageHeader.setFilter(Image.FILTER_NEAREST);
        imageBody = new Image(WINDOW_BODY_SPRITE);
        imageBody.setFilter(Image.FILTER_NEAREST);
        showOverlay = true;
        showHeader = true;

        closeButton = new Button("", width - WINDOW_HEADER_BORDER / 2 - WINDOW_CLOSE_BUTTON_SIZE, WINDOW_HEADER_BORDER / 2,
                WINDOW_CLOSE_BUTTON_SIZE, WINDOW_CLOSE_BUTTON_SIZE);
        closeButton.setImage(new Image(WINDOW_CLOSE_BUTTON_SPRITE));
        closeButton.setVisible(false);
        closeButton.setEvent(new Event() {

            @Override
            public void perform() throws Exception {
                close();
            }
        });

        add(closeButton);
    }

    @Override
    public void update(int offsetX, int offsetY) throws Exception {
        closeButton.setVisible(showHeader && isShowCloseButton());

        super.update(offsetX, offsetY);
    }

    @Override
    public void paint(Graphics g) {
        if (showOverlay) {
            g.setColor(DIALOG_OVERLAY_COLOR);
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }

        g.pushTransform();
        g.translate(x, y);

        GUI.drawTiledImage(imageBody, filter, width, height, WINDOW_BODY_SIZE, WINDOW_BODY_SIZE, WINDOW_BODY_BORDER);
        if (showHeader) {
            int frame = title != null ? 1 : 0;
            Image imageHeader = this.imageHeader.getSubImage(0, frame * WINDOW_HEADER_HEIGHT, WINDOW_HEADER_WIDTH, WINDOW_HEADER_HEIGHT);
            GUI.drawTiledImage(imageHeader, filter, width, WINDOW_HEADER_HEIGHT, WINDOW_HEADER_WIDTH, WINDOW_HEADER_HEIGHT,
                    WINDOW_HEADER_BORDER);

            if (title != null) {
                drawColoredString(g, GUI.get().getFont(), width / 2 - titleWidth / 2,
                        WINDOW_HEADER_HEIGHT / 2 - GUI.get().getFont().getHeight() / 2, title, WINDOW_HEADER_TEXT_COLOR);
            }
        }

        super.paint(g);

        g.popTransform();
    }

    @Override
    public void onShow() throws Exception {}

    @Override
    public void onHide() throws Exception {}

    public void close() {
        setVisible(false);
    }

    public void setImageHeader(Image imageHeader) {
        this.imageHeader = imageHeader;
    }

    public void setImageBody(Image imageBody) {
        this.imageBody = imageBody;
    }

    public boolean isShowOverlay() {
        return showOverlay;
    }

    public void setShowOverlay(boolean showOverlay) {
        this.showOverlay = showOverlay;
    }

    public boolean isShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public void setShowCloseButton(boolean showCloseButton) {
        closeButton.setVisible(showCloseButton);
    }

    public boolean isShowCloseButton() {
        return closeButton.isVisible();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if (title != null) {
            titleWidth = GUI.getColoredStringWidth(GUI.get().getFont(), title);
        }
    }

}
