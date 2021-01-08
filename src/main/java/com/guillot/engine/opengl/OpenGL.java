package com.guillot.engine.opengl;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.nio.ByteBuffer;

public class OpenGL {

    public static void drawRectangle(int x, int y, int width, int height, int textureID) {
        glBindTexture(GL_TEXTURE_2D, textureID);
        glBegin(GL_QUADS);
        glTexCoord2f(0.0f, 0.0f);
        glVertex2f(x, y);
        glTexCoord2f(1.0f, 0.0f);
        glVertex2f(x + width, y);
        glTexCoord2f(1.0f, 1.0f);
        glVertex2f(x + width, y + height);
        glTexCoord2f(0.0f, 1.0f);
        glVertex2f(x, y + height);
        glEnd();
    }

    public static int createTextureFromBuffer(byte[] buffer, int width, int height, boolean hasAlpha) {
        int textureId = glGenTextures();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buffer.length);
        byteBuffer.put(buffer);
        byteBuffer.flip();

        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, hasAlpha ? GL_RGBA : GL_RGB, width, height, 0, hasAlpha ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE,
                byteBuffer);

        return textureId;
    }

}
