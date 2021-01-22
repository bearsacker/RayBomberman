package com.bearsacker.engine.opengl;

import static java.nio.ByteBuffer.allocateDirect;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

public class ImageBuffer {

    private int textureId;

    private int width;

    private int height;

    private boolean hasAlpha;

    private ByteBuffer buffer;

    public ImageBuffer(int width, int height, boolean hasAlpha) {
        this.textureId = GL11.glGenTextures();
        this.width = width;
        this.height = height;
        this.hasAlpha = hasAlpha;
        this.buffer = allocateDirect(width * height * (hasAlpha ? 4 : 3));
    }

    public void setPixel(int x, int y, Color color) {
        buffer.put(width * (hasAlpha ? 4 : 3) * y + x * (hasAlpha ? 4 : 3), color.getRedByte());
        buffer.put(width * (hasAlpha ? 4 : 3) * y + x * (hasAlpha ? 4 : 3) + 1, color.getGreenByte());
        buffer.put(width * (hasAlpha ? 4 : 3) * y + x * (hasAlpha ? 4 : 3) + 2, color.getBlueByte());
        if (hasAlpha) {
            buffer.put(width * (hasAlpha ? 4 : 3) * y + x * (hasAlpha ? 4 : 3) + 3, color.getAlphaByte());
        }
    }

    public Color getPixel(int x, int y) {
        Color color = new Color();
        color.setRed(buffer.get(width * (hasAlpha ? 4 : 3) * y + x * (hasAlpha ? 4 : 3)));
        color.setGreen(buffer.get(width * (hasAlpha ? 4 : 3) * y + x * (hasAlpha ? 4 : 3) + 1));
        color.setBlue(buffer.get(width * (hasAlpha ? 4 : 3) * y + x * (hasAlpha ? 4 : 3) + 2));
        if (hasAlpha) {
            color.setAlpha(buffer.get(width * (hasAlpha ? 4 : 3) * y + x * (hasAlpha ? 4 : 3) + 3));
        }

        return color;
    }

    public void addToPixel(int x, int y, Color c2) {
        Color c1 = getPixel(x, y);
        float a1 = c1.getAlpha() / 255f;
        float a2 = c2.getAlpha() / 255f;
        float a3 = 1f - (1f - a2) * (1f - a1);

        int red = Math.round((c2.getRed() * a2 / a3) + (c1.getRed() * a1 * (1 - a2) / a3));
        int green = Math.round((c2.getGreen() * a2 / a3) + (c1.getGreen() * a1 * (1 - a2) / a3));
        int blue = Math.round((c2.getBlue() * a2 / a3) + (c1.getBlue() * a1 * (1 - a2) / a3));

        setPixel(x, y, new Color(red, green, blue, (int) (a3 * 255)));
    }

    public int getTextureId() {
        return textureId;
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

    public ByteBuffer getBuffer() {
        return buffer;
    }

}
