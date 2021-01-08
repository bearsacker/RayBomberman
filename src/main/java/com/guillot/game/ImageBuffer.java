package com.guillot.game;

import org.lwjgl.util.Color;

public class ImageBuffer {

    private int width;

    private int height;

    private boolean hasAlpha;

    private byte[] buffer;

    public ImageBuffer(int width, int height, boolean hasAlpha) {
        this.width = width;
        this.height = height;
        this.hasAlpha = hasAlpha;
        this.buffer = new byte[width * height * (hasAlpha ? 4 : 3)];
    }

    public void setPixel(int x, int y, Color color) {
        buffer[width * (hasAlpha ? 4 : 3) * y + x * (hasAlpha ? 4 : 3)] = color.getRedByte();
        buffer[width * (hasAlpha ? 4 : 3) * y + x * (hasAlpha ? 4 : 3) + 1] = color.getGreenByte();
        buffer[width * (hasAlpha ? 4 : 3) * y + x * (hasAlpha ? 4 : 3) + 2] = color.getBlueByte();
        if (hasAlpha) {
            buffer[width * (hasAlpha ? 4 : 3) * y + x * (hasAlpha ? 4 : 3) + 3] = color.getAlphaByte();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean hasAlpha() {
        return hasAlpha;
    }

    public byte[] getBuffer() {
        return buffer;
    }

}
