package com.guillot.game;

import static com.guillot.engine.opengl.OpenGL.createTextureFromBuffer;
import static com.guillot.engine.opengl.OpenGL.drawRectangle;
import static com.guillot.game.Images.CEILING;
import static com.guillot.game.Images.FLOOR;
import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

import org.jbox2d.common.Vec2;
import org.lwjgl.util.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureImpl;

import com.guillot.engine.Game;
import com.guillot.engine.gui.GUI;
import com.guillot.engine.gui.View;


public class GameView extends View {

    public final static int SCREEN_WIDTH = 320;

    public final static int SCREEN_HEIGHT = 200;

    public final static int TILE_SIZE = 64;

    private Map map;

    private Player player;

    private ImageBuffer imageBuffer;

    private double[] zBuffer;

    @Override
    public void start() throws Exception {
        imageBuffer = new ImageBuffer(SCREEN_WIDTH, SCREEN_HEIGHT, true);
        zBuffer = new double[SCREEN_WIDTH];

        player = new Player();
        map = new Map("01.map");
    }

    @Override
    public void update() throws Exception {
        super.update();

        player.update(map);
        map.update();
    }

    @Override
    public void paint(Graphics g) throws Exception {
        super.paint(g);

        // Floor casting
        for (int y = 0; y < SCREEN_HEIGHT; y++) {
            // rayDir for leftmost ray (x = 0) and rightmost ray (x = w)
            Vec2 rayDirection0 = player.getDirection().sub(player.getPlane());
            Vec2 rayDirection1 = player.getDirection().add(player.getPlane());

            // Current y position compared to the center of the screen (the horizon)
            int p = y - SCREEN_HEIGHT / 2;

            // Vertical position of the camera.
            float posZ = .5f * SCREEN_HEIGHT;

            // Horizontal distance from the camera to the floor for the current row.
            // 0.5 is the z position exactly in the middle between floor and ceiling.
            float rowDistance = posZ / p;

            // calculate the real world step vector we have to add for each x (parallel to camera plane)
            // adding step by step avoids multiplications with a weight in the inner loop
            float floorStepX = rowDistance * (rayDirection1.x - rayDirection0.x) / SCREEN_WIDTH;
            float floorStepY = rowDistance * (rayDirection1.y - rayDirection0.y) / SCREEN_WIDTH;

            // real world coordinates of the leftmost column. This will be updated as we step to the right.
            Vec2 floor = rayDirection0.mul(rowDistance).add(player.getPosition());

            for (int x = 0; x < SCREEN_WIDTH; ++x) {
                // the cell coord is simply got from the integer parts of floorX and floorY
                int cellX = (int) (floor.x);
                int cellY = (int) (floor.y);

                // get the texture coordinate from the fractional part
                int tx = (int) (TILE_SIZE * (floor.x - cellX)) & (TILE_SIZE - 1);
                int ty = (int) (TILE_SIZE * (floor.y - cellY)) & (TILE_SIZE - 1);

                floor.x += floorStepX;
                floor.y += floorStepY;

                Color floorColor = FLOOR.getPixel(tx, ty);
                imageBuffer.setPixel(x, y, floorColor);

                Color ceilingColor = CEILING.getPixel(tx, ty);
                imageBuffer.setPixel(x, SCREEN_HEIGHT - y - 1, ceilingColor);
            }
        }

        // Wall casting
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            // calculate ray position and direction
            float cameraX = 2 * x / (float) SCREEN_WIDTH - 1; // x-coordinate in camera space
            Vec2 rayDirection = new Vec2(player.getPlane().mul(cameraX).add(player.getDirection()));

            // which box of the map we're in
            int mapX = (int) player.getPosition().x;
            int mapY = (int) player.getPosition().y;

            // length of ray from current position to next x or y-side
            double sideDistX;
            double sideDistY;

            // length of ray from one x or y-side to next x or y-side
            double deltaDistX = sqrt(1 + (rayDirection.y * rayDirection.y) / (rayDirection.x * rayDirection.x));
            double deltaDistY = sqrt(1 + (rayDirection.x * rayDirection.x) / (rayDirection.y * rayDirection.y));
            double perpWallDist;

            // what direction to step in x or y-direction (either +1 or -1)
            int stepX;
            int stepY;

            // calculate step and initial sideDist
            if (rayDirection.x < 0) {
                stepX = -1;
                sideDistX = (player.getPosition().x - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - player.getPosition().x) * deltaDistX;
            }
            if (rayDirection.y < 0) {
                stepY = -1;
                sideDistY = (player.getPosition().y - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - player.getPosition().y) * deltaDistY;
            }

            // perform DDA
            int side = 0;
            Wall wall = null;
            while (wall == null) {
                // jump to next map square, OR in x-direction, OR in y-direction
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }

                wall = map.getTile(mapX, mapY);
            }

            // Calculate distance projected on camera direction (Euclidean distance will give fisheye effect!)
            if (side == 0) {
                perpWallDist = (mapX - player.getPosition().x + (1 - stepX) / 2) / rayDirection.x;
            } else {
                perpWallDist = (mapY - player.getPosition().y + (1 - stepY) / 2) / rayDirection.y;
            }

            // Calculate height of line to draw on screen
            int lineHeight = (int) (SCREEN_HEIGHT / perpWallDist);

            // calculate lowest and highest pixel to fill in current stripe
            int drawStart = -lineHeight / 2 + SCREEN_HEIGHT / 2;
            if (drawStart < 0) {
                drawStart = 0;
            }
            int drawEnd = lineHeight / 2 + SCREEN_HEIGHT / 2;
            if (drawEnd >= SCREEN_HEIGHT) {
                drawEnd = SCREEN_HEIGHT - 1;
            }

            // calculate value of wallX
            float wallX;
            if (side == 0) {
                wallX = (float) (player.getPosition().y + perpWallDist * rayDirection.y);
            } else {
                wallX = (float) (player.getPosition().x + perpWallDist * rayDirection.x);
            }
            wallX -= floor(wallX);

            // x coordinate on the texture
            int tx = (int) (wallX * TILE_SIZE);
            if (side == 0 && rayDirection.x > 0) {
                tx = TILE_SIZE - tx - 1;
            }
            if (side == 1 && rayDirection.y < 0) {
                tx = TILE_SIZE - tx - 1;
            }

            // How much to increase the texture coordinate per screen pixel
            double step = 1.0 * TILE_SIZE / lineHeight;
            // Starting texture coordinate
            double texPos = (drawStart - SCREEN_HEIGHT / 2 + lineHeight / 2) * step;
            for (int y = drawStart; y < drawEnd; y++) {
                // Cast the texture coordinate to integer, and mask with (texHeight - 1) in case of overflow
                int ty = (int) texPos & (TILE_SIZE - 1);
                texPos += step;

                Color wallColor = wall.getTexture().getPixel(tx, ty);
                if (side == 1) {
                    wallColor.set((wallColor.getRedByte() >> 1) & 8355711, (wallColor.getGreenByte() >> 1) & 8355711,
                            (wallColor.getBlueByte() >> 1) & 8355711, wallColor.getAlphaByte());
                }
                imageBuffer.setPixel(x, y, wallColor);
            }

            zBuffer[x] = perpWallDist;
        }

        int textureId = createTextureFromBuffer(imageBuffer.getBuffer(), SCREEN_WIDTH, SCREEN_HEIGHT, imageBuffer.hasAlpha());
        drawRectangle(0, 0, SCREEN_WIDTH * 2, SCREEN_HEIGHT * 2, textureId);

        // Sprite casting
        map.getEntities().sort((s1, s2) -> {
            return Double.compare(s2.distanceFrom(player.getPosition()), s1.distanceFrom(player.getPosition()));
        });

        for (Entity entity : map.getEntities()) {
            // translate sprite position to relative to camera
            Vec2 sPosition = entity.getPosition().sub(player.getPosition());

            // transform sprite with the inverse camera matrix
            float invDet = 1f / (player.getPlane().x * player.getDirection().y - player.getDirection().x * player.getPlane().y);

            float transformX = invDet * (player.getDirection().y * sPosition.x - player.getDirection().x * sPosition.y);
            float transformY = invDet * (-player.getPlane().y * sPosition.x + player.getPlane().x * sPosition.y);

            int spriteScreenX = (int) ((SCREEN_WIDTH / 2) * (1 + transformX / transformY));

            // calculate width and height of the sprite on screen
            // using 'transformY' instead of the real distance prevents fisheye
            int spriteHeight = (int) abs(SCREEN_HEIGHT / (transformY));
            int drawStartY = -spriteHeight / 2 + SCREEN_HEIGHT / 2;
            if (drawStartY < 0) {
                drawStartY = 0;
            }
            int drawEndY = spriteHeight / 2 + SCREEN_HEIGHT / 2;
            if (drawEndY >= SCREEN_HEIGHT) {
                drawEndY = SCREEN_HEIGHT - 1;
            }

            int spriteWidth = (int) abs(SCREEN_HEIGHT / (transformY));
            int drawStartX = -spriteWidth / 2 + spriteScreenX;
            if (drawStartX < 0) {
                drawStartX = 0;
            }
            int drawEndX = spriteWidth / 2 + spriteScreenX;
            if (drawEndX >= SCREEN_WIDTH) {
                drawEndX = SCREEN_WIDTH - 1;
            }

            // loop through every vertical stripe of the sprite on screen
            for (int stripe = drawStartX; stripe < drawEndX; stripe++) {
                if (transformY > 0 && stripe > 0 && stripe < SCREEN_WIDTH && transformY < zBuffer[stripe]) {
                    int tx = (stripe - (-spriteWidth / 2 + spriteScreenX)) * TILE_SIZE / spriteWidth;

                    for (int y = drawStartY; y < drawEndY; y++) {
                        int d = y - SCREEN_HEIGHT / 2 + spriteHeight / 2;
                        int ty = (d * TILE_SIZE) / spriteHeight;

                        Color spriteColor = entity.getSprite().getPixel(tx, ty);
                        imageBuffer.setPixel(stripe, y, spriteColor);
                    }
                }
            }
        }

        textureId = createTextureFromBuffer(imageBuffer.getBuffer(), SCREEN_WIDTH, SCREEN_HEIGHT, imageBuffer.hasAlpha());
        drawRectangle(0, 0, SCREEN_WIDTH * 2, SCREEN_HEIGHT * 2, textureId);

        TextureImpl.bindNone();

        g.setColor(org.newdawn.slick.Color.white);
        GUI.get().getFont().drawString(10, 30, Integer.toString(player.getBombs()));
    }

    public static void main(String[] args) throws SlickException {
        new Game(new GameView());
    }
}
