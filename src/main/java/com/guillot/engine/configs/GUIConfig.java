package com.guillot.engine.configs;

import static java.lang.Integer.parseInt;

import org.apache.commons.configuration2.Configuration;
import org.newdawn.slick.Color;

public final class GUIConfig extends Config {

    private static final GUIConfig INSTANCE = new GUIConfig("gui.properties");

    // General

    public final static String FONT = get().getString("gui.font", "fonts/roboto.ttf");

    public final static String[] FONT_SIZES = toStringArray(get().getString("gui.font-sizes", "24"));

    public final static Color DEFAULT_TEXT_COLOR = toColor(get().getString("gui.text-color", "255,255,255,255"));

    public final static Color EXCEPTION_TEXT_COLOR = toColor(get().getString("gui.exception.text-color", "255,0,0,255"));

    // View

    public final static Color VIEW_BACKGROUND_COLOR = toColor(get().getString("gui.view.background-color", "0,0,0,255"));

    // Component

    public final static Color COMPONENT_FILTER_COLOR = toColor(get().getString("gui.component.filter-color", "255,255,255,255"));

    public final static Color COMPONENT_DISABLED_FILTER =
            toColor(get().getString("gui.component.disabled.filter-color", "204,204,204,255"));

    // Dialog

    public final static Color DIALOG_OVERLAY_COLOR = toColor(get().getString("gui.dialog.overlay-color", "0,0,0,192"));

    public final static String DIALOG_BUTTON_TEXT = get().getString("gui.dialog.button.text", "Close");

    // Window

    public final static String WINDOW_HEADER_SPRITE = get().getString("gui.window.header.sprite", "gui/default_window_header.png");

    public final static int WINDOW_HEADER_BORDER = get().getInt("gui.window.header.border", 2);

    public final static int WINDOW_HEADER_WIDTH = get().getInt("gui.window.header.width", 32);

    public final static int WINDOW_HEADER_HEIGHT = get().getInt("gui.window.header.height", 32);

    public final static Color WINDOW_HEADER_TEXT_COLOR = toColor(get().getString("gui.window.header.text-color", "0,0,0,255"));

    public final static String WINDOW_BODY_SPRITE = get().getString("gui.window.body.sprite", "gui/default_window_body.png");

    public final static int WINDOW_BODY_BORDER = get().getInt("gui.window.body.border", 2);

    public final static int WINDOW_BODY_SIZE = get().getInt("gui.window.body.size", 32);

    public final static String WINDOW_CLOSE_BUTTON_SPRITE = get().getString("gui.window.button.sprite", "gui/default_window_button.png");

    public final static int WINDOW_CLOSE_BUTTON_SIZE = get().getInt("gui.window.button.size", 32);

    // Textbox

    public final static String TEXTBOX_SPRITE = get().getString("gui.textbox.sprite", "gui/default_textbox.png");

    public final static Color TEXTBOX_TEXT_COLOR = toColor(get().getString("gui.textbox.text-color", "0,0,0,255"));

    public final static int TEXTBOX_BORDER = get().getInt("gui.textbox.border", 2);

    public final static int TEXTBOX_SPRITE_SIZE = get().getInt("gui.textbox.sprite.size", 32);

    public final static int TEXTBOX_PADDING = get().getInt("gui.textbox.padding", 10);

    // Button

    public final static String BUTTON_SPRITE = get().getString("gui.button.sprite", "gui/default_button.png");

    public final static Color BUTTON_TEXT_COLOR = toColor(get().getString("gui.button.text-color", "128,128,128,255"));

    public final static Color BUTTON_TEXT_HOVER_COLOR = toColor(get().getString("gui.button.text-hover-color", "0,0,0,255"));

    public final static int BUTTON_BORDER = get().getInt("gui.button.border", 2);

    public final static int BUTTON_SPRITE_SIZE = get().getInt("gui.button.sprite.size", 32);

    public final static int BUTTON_PADDING = get().getInt("gui.button.padding", 10);

    // Progress Bar

    public final static String PROGRESSBAR_SPRITE = get().getString("gui.progressbar.sprite", "gui/default_progressbar.png");

    public final static int PROGRESSBAR_SPRITE_SIZE = get().getInt("gui.progressbar.sprite.size", 32);

    public final static int PROGRESSBAR_BORDER = get().getInt("gui.progressbar.border", 2);

    public final static Color PROGRESSBAR_COLOR = toColor(get().getString("gui.progressbar.color", "0,202,0,255"));

    // Check Box

    public final static String CHECKBOX_SPRITE = get().getString("gui.checkbox.sprite", "gui/default_checkbox.png");

    public final static int CHECKBOX_SPRITE_SIZE = get().getInt("gui.checkbox.sprite.size", 32);

    // Textfield

    public final static String TEXTFIELD_SPRITE = get().getString("gui.textfield.sprite", "gui/default_textfield.png");

    public final static Color TEXTFIELD_TEXT_COLOR = toColor(get().getString("gui.textfield.text-color", "255,255,255,255"));

    public final static int TEXTFIELD_BORDER = get().getInt("gui.textfield.border", 2);

    public final static int TEXTFIELD_SPRITE_SIZE = get().getInt("gui.textfield.sprite.size", 32);

    public final static int TEXTFIELD_PADDING = get().getInt("gui.textfield.padding", 10);

    // Link Button

    public final static Color LINK_BUTTON_TEXT_COLOR = toColor(get().getString("gui.link-button.text-color", "128,128,128,255"));

    public final static Color LINK_BUTTON_TEXT_HOVER_COLOR = toColor(get().getString("gui.link-button.text-hover-color", "0,0,0,255"));

    private GUIConfig(String file) {
        super(file);
    }

    public static Configuration get() {
        return INSTANCE.configuration;
    }

    private static Color toColor(String value) {
        String[] channels = value.split(",");
        if (channels.length != 4) {
            return null;
        }

        return new Color(parseInt(channels[0]), parseInt(channels[1]), parseInt(channels[2]), parseInt(channels[3]));
    }
}
