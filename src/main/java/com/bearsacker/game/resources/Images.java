package com.bearsacker.game.resources;

import org.lwjgl.util.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum Images {
    ITEM_BOMB_BONUS("sprites/item_bomb_bonus.png"), //
    ITEM_BOMB_MALUS("sprites/item_bomb_malus.png"), //
    ITEM_RANGE_BONUS("sprites/item_range_bonus.png"), //
    ITEM_RANGE_MALUS("sprites/item_range_malus.png"), //
    ITEM_SPEED_BONUS("sprites/item_speed_bonus.png"), //
    ITEM_SPEED_MALUS("sprites/item_speed_malus.png"), //
    ITEM_REDBOMB("sprites/item_redbomb.png"), //
    ITEM_POWERBOMB("sprites/item_powerbomb.png"), //
    ITEM_GLOVE("sprites/item_glove.png"), //

    HUD_RANGE("sprites/hud_range.png"), //
    HUD_GLOVE("sprites/hud_glove.png"), //

    PLAYER_FRONT_1("sprites/player_front_1.png"), //
    PLAYER_FRONT_2("sprites/player_front_2.png"), //
    PLAYER_FRONT_3("sprites/player_front_3.png"), //
    PLAYER_FRONT_4("sprites/player_front_4.png"), //
    PLAYER_FRONT_5("sprites/player_front_5.png"), //
    PLAYER_FRONT_6("sprites/player_front_6.png"), //

    WALL("sprites/wall.png"), //
    BOMB("sprites/bomb.png"), //
    REDBOMB("sprites/redbomb.png"), //
    FIRE("sprites/fire.png"), //
    FLOOR("sprites/floor.png"), //
    CEILING("sprites/ceil.png"), //
    UNBREAKABLE("sprites/unbreakable.png");

    private Image image;

    private int frameWidth;

    private int frameHeight;

    private byte[] data;

    private Images(String path) {
        try {
            image = new Image(path);
            image.setFilter(Image.FILTER_NEAREST);
            data = image.getTexture().getTextureData();
        } catch (SlickException e) {
        }
    }

    private Images(String path, int frameWidth, int frameHeight, int x, int y) {
        try {
            image = new Image(path).getSubImage(x * frameWidth, y * frameHeight, frameWidth, frameHeight);
            image.setFilter(Image.FILTER_NEAREST);
            data = image.getTexture().getTextureData();
        } catch (SlickException e) {
        }
    }

    public Image getImage() {
        return image;
    }

    public Image getSubImage(int x, int y) {
        return image.getSubImage(x * frameWidth, y * frameHeight, frameWidth, frameHeight);
    }

    public boolean hasAlpha() {
        return image.getTexture().hasAlpha();
    }

    public Color getPixel(int x, int y) {
        byte r = data[getImage().getWidth() * (hasAlpha() ? 4 : 3) * y + x * (hasAlpha() ? 4 : 3)];
        byte g = data[getImage().getWidth() * (hasAlpha() ? 4 : 3) * y + x * (hasAlpha() ? 4 : 3) + 1];
        byte b = data[getImage().getWidth() * (hasAlpha() ? 4 : 3) * y + x * (hasAlpha() ? 4 : 3) + 2];

        if (!hasAlpha()) {
            return new Color(r, g, b);
        }

        byte a = data[getImage().getWidth() * 4 * y + x * 4 + 3];

        return new Color(r, g, b, a);
    }

    public byte[] getTextureData() {
        return data;
    }
}
