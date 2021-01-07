package com.guillot.game;

import static com.guillot.game.Images.CEIL;
import static com.guillot.game.Images.FLOOR;
import static com.guillot.game.Images.PILLAR;
import static com.guillot.game.Images.UNBREAKABLE;
import static com.guillot.game.Images.WALL;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.guillot.engine.Game;
import com.guillot.engine.gui.GUI;
import com.guillot.engine.gui.View;
import com.guillot.engine.opengl.OpenGL;


public class GameView extends View {

    public final static int SCREEN_WIDTH = 320;

    public final static int SCREEN_HEIGHT = 200;

    public final static int TILE_SIZE = 64;

    private Wall[][] map;

    private ArrayList<Sprite> sprites;

    private Vec2 position;

    private Vec2 direction;

    private Vec2 plane;

    private byte[] buffer;

    private double[] zBuffer;

    @Override
    public void start() throws Exception {
        setBackgroundColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));

        position = new Vec2(7f, 7.5f);
        direction = new Vec2(-1f, 0f);
        plane = new Vec2(0f, .66f);

        buffer = new byte[SCREEN_WIDTH * SCREEN_HEIGHT * 4];
        zBuffer = new double[SCREEN_WIDTH];

        sprites = new ArrayList<>();
        sprites.add(new Sprite(1.5f, 1.5f, PILLAR));
        sprites.add(new Sprite(2.5f, 2.5f, PILLAR));
        sprites.add(new Sprite(1.5f, 14.5f, PILLAR));
        sprites.add(new Sprite(14.5f, 1.5f, PILLAR));
        sprites.add(new Sprite(14.5f, 14.5f, PILLAR));

        map = new Wall[16][16];
        for (int i = 0; i < 16; i++) {
            map[i][0] = new Wall(1, WALL);
            map[0][i] = new Wall(1, WALL);
            map[i][15] = new Wall(1, WALL);
            map[15][i] = new Wall(1, WALL);
        }

        map[12][12] = new Wall(1, UNBREAKABLE);
    }

    @Override
    public void update() throws Exception {
        super.update();

        if (GUI.get().getInput().isKeyDown(Input.KEY_Z)) {
            if (map[(int) (position.x + direction.x * .01f)][(int) (position.y)] == null) {
                position.x += direction.x * .01f;
            }
            if (map[(int) (position.x)][(int) (position.y + direction.y * .01f)] == null) {
                position.y += direction.y * .01f;
            }
        }

        if (GUI.get().getInput().isKeyDown(Input.KEY_S)) {
            if (map[(int) (position.x - direction.x * .01f)][(int) (position.y)] == null) {
                position.x -= direction.x * .01f;
            }
            if (map[(int) (position.x)][(int) (position.y - direction.y * .01f)] == null) {
                position.y -= direction.y * .01f;
            }
        }

        if (GUI.get().getInput().isKeyDown(Input.KEY_Q)) {
            float oldDirX = direction.x;
            direction.x = (float) (direction.x * cos(.01f) - direction.y * sin(.01f));
            direction.y = (float) (oldDirX * sin(.01f) + direction.y * cos(.01f));

            float oldPlaneX = plane.x;
            plane.x = (float) (plane.x * cos(.01f) - plane.y * sin(.01f));
            plane.y = (float) (oldPlaneX * sin(.01f) + plane.y * cos(.01f));
        }

        if (GUI.get().getInput().isKeyDown(Input.KEY_D)) {
            float oldDirX = direction.x;
            direction.x = (float) (direction.x * cos(-.01f) - direction.y * sin(-.01f));
            direction.y = (float) (oldDirX * sin(-.01f) + direction.y * cos(-.01f));

            float oldPlaneX = plane.x;
            plane.x = (float) (plane.x * cos(-.01f) - plane.y * sin(-.01f));
            plane.y = (float) (oldPlaneX * sin(-.01f) + plane.y * cos(-.01f));
        }
    }

    @Override
    public void paint(Graphics g) throws Exception {
        super.paint(g);

        // Floor casting
        for (int y = 0; y < SCREEN_HEIGHT; y++) {
            // rayDir for leftmost ray (x = 0) and rightmost ray (x = w)
            Vec2 rayDirection0 = direction.sub(plane);
            Vec2 rayDirection1 = direction.add(plane);

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
            Vec2 floor = rayDirection0.mul(rowDistance).add(position);

            for (int x = 0; x < SCREEN_WIDTH; ++x) {
                // the cell coord is simply got from the integer parts of floorX and floorY
                int cellX = (int) (floor.x);
                int cellY = (int) (floor.y);

                // get the texture coordinate from the fractional part
                int tx = (int) (TILE_SIZE * (floor.x - cellX)) & (TILE_SIZE - 1);
                int ty = (int) (TILE_SIZE * (floor.y - cellY)) & (TILE_SIZE - 1);

                floor.x += floorStepX;
                floor.y += floorStepY;

                buffer[SCREEN_WIDTH * 3 * y + x * 3] = FLOOR.getTextureData()[TILE_SIZE * 3 * ty + tx * 3];
                buffer[SCREEN_WIDTH * 3 * y + x * 3 + 1] = FLOOR.getTextureData()[TILE_SIZE * 3 * ty + tx * 3 + 1];
                buffer[SCREEN_WIDTH * 3 * y + x * 3 + 2] = FLOOR.getTextureData()[TILE_SIZE * 3 * ty + tx * 3 + 2];

                buffer[SCREEN_WIDTH * 3 * (SCREEN_HEIGHT - y - 1) + x * 3] = CEIL.getTextureData()[TILE_SIZE * 3 * ty + tx * 3];
                buffer[SCREEN_WIDTH * 3 * (SCREEN_HEIGHT - y - 1) + x * 3 + 1] = CEIL.getTextureData()[TILE_SIZE * 3 * ty + tx * 3 + 1];
                buffer[SCREEN_WIDTH * 3 * (SCREEN_HEIGHT - y - 1) + x * 3 + 2] = CEIL.getTextureData()[TILE_SIZE * 3 * ty + tx * 3 + 2];
            }
        }

        // Wall casting
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            // calculate ray position and direction
            float cameraX = 2 * x / (float) SCREEN_WIDTH - 1; // x-coordinate in camera space
            Vec2 rayDirection = new Vec2(plane.mul(cameraX).add(direction));

            // which box of the map we're in
            int mapX = (int) position.x;
            int mapY = (int) position.y;

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
                sideDistX = (position.x - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - position.x) * deltaDistX;
            }
            if (rayDirection.y < 0) {
                stepY = -1;
                sideDistY = (position.y - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - position.y) * deltaDistY;
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

                wall = map[mapX][mapY];
            }

            // Calculate distance projected on camera direction (Euclidean distance will give fisheye effect!)
            if (side == 0) {
                perpWallDist = (mapX - position.x + (1 - stepX) / 2) / rayDirection.x;
            } else {
                perpWallDist = (mapY - position.y + (1 - stepY) / 2) / rayDirection.y;
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
                wallX = (float) (position.y + perpWallDist * rayDirection.y);
            } else {
                wallX = (float) (position.x + perpWallDist * rayDirection.x);
            }
            wallX -= floor(wallX);

            // x coordinate on the texture
            int texX = (int) (wallX * TILE_SIZE);
            if (side == 0 && rayDirection.x > 0) {
                texX = TILE_SIZE - texX - 1;
            }
            if (side == 1 && rayDirection.y < 0) {
                texX = TILE_SIZE - texX - 1;
            }

            // How much to increase the texture coordinate per screen pixel
            double step = 1.0 * TILE_SIZE / lineHeight;
            // Starting texture coordinate
            double texPos = (drawStart - SCREEN_HEIGHT / 2 + lineHeight / 2) * step;
            for (int y = drawStart; y < drawEnd; y++) {
                // Cast the texture coordinate to integer, and mask with (texHeight - 1) in case of overflow
                int texY = (int) texPos & (TILE_SIZE - 1);
                texPos += step;

                buffer[SCREEN_WIDTH * 3 * y + x * 3] = wall.getTexture().getTextureData()[TILE_SIZE * 3 * texY + texX * 3];
                buffer[SCREEN_WIDTH * 3 * y + x * 3 + 1] = wall.getTexture().getTextureData()[TILE_SIZE * 3 * texY + texX * 3 + 1];
                buffer[SCREEN_WIDTH * 3 * y + x * 3 + 2] = wall.getTexture().getTextureData()[TILE_SIZE * 3 * texY + texX * 3 + 2];
            }

            zBuffer[x] = perpWallDist;
        }

        // Sprite casting
        sprites.sort((s1, s2) -> {
            return Double.compare(s2.distanceFrom(position), s1.distanceFrom(position));
        });

        for (Sprite sprite : sprites) {
            // translate sprite position to relative to camera
            Vec2 sPosition = sprite.getPosition().sub(position);

            // transform sprite with the inverse camera matrix
            float invDet = 1f / (plane.x * direction.y - direction.x * plane.y); // required for correct matrix multiplication

            float transformX = invDet * (direction.y * sPosition.x - direction.x * sPosition.y);
            float transformY = invDet * (-plane.y * sPosition.x + plane.x * sPosition.y); // this is actually the depth inside the
                                                                                          // screen, that what Z is in 3D

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
                    int texX = (stripe - (-spriteWidth / 2 + spriteScreenX)) * TILE_SIZE / spriteWidth;

                    for (int y = drawStartY; y < drawEndY; y++) {
                        int d = y - SCREEN_HEIGHT / 2 + spriteHeight / 2;
                        int texY = (d * TILE_SIZE) / spriteHeight;

                        buffer[SCREEN_WIDTH * 3 * y + stripe * 3] = sprite.getTexture().getTextureData()[TILE_SIZE * 4 * texY + texX * 4];
                        buffer[SCREEN_WIDTH * 3 * y + stripe * 3 + 1] =
                                sprite.getTexture().getTextureData()[TILE_SIZE * 4 * texY + texX * 4 + 1];
                        buffer[SCREEN_WIDTH * 3 * y + stripe * 3 + 2] =
                                sprite.getTexture().getTextureData()[TILE_SIZE * 4 * texY + texX * 4 + 2];
                    }
                }
            }
        }

        int textureId = OpenGL.createTextureFromBuffer(buffer, SCREEN_WIDTH, SCREEN_HEIGHT);
        OpenGL.drawRectangle(0, SCREEN_HEIGHT * 2, SCREEN_WIDTH * 2, -SCREEN_HEIGHT * 2, textureId);
    }

    public static void main(String[] args) throws SlickException {
        new Game(new GameView());
    }
}
