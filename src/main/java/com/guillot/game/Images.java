package com.guillot.game;

import org.lwjgl.util.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum Images {
    WALL("sprites/wall.png"), //
    BOMB("sprites/bomb.png"), //
    FLOOR("sprites/floor.png"), //
    CEILING("sprites/ceil.png"), //
    UNBREAKABLE("sprites/unbreakable.png"), //
    PILLAR("sprites/pillar.png");

    private Image image;

    private int frameWidth;

    private int frameHeight;

    private byte[] data;

    private Images(String path) {
        this(path, 0, 0);
    }

    private Images(String path, int frameWidth, int frameHeight) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

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
