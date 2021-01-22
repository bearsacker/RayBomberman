package com.bearsacker.game.views;

import static com.bearsacker.engine.configs.EngineConfig.HEIGHT;
import static com.bearsacker.engine.configs.EngineConfig.WIDTH;
import static com.bearsacker.engine.opengl.OpenGL.createTextureFromBuffer;
import static com.bearsacker.engine.opengl.OpenGL.drawRectangle;
import static com.bearsacker.game.configs.GameConfig.SCREEN_HEIGHT;
import static com.bearsacker.game.configs.GameConfig.SCREEN_WIDTH;
import static com.bearsacker.game.configs.GameConfig.TILE_SIZE;
import static com.bearsacker.game.resources.Images.CEILING;
import static com.bearsacker.game.resources.Images.FLOOR;
import static org.newdawn.slick.Input.KEY_ESCAPE;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureImpl;

import com.bearsacker.engine.Game;
import com.bearsacker.engine.gui.Event;
import com.bearsacker.engine.gui.FadeTransition;
import com.bearsacker.engine.gui.GUI;
import com.bearsacker.engine.gui.LinkButton;
import com.bearsacker.engine.gui.View;
import com.bearsacker.engine.opengl.ImageBuffer;
import com.bearsacker.game.resources.Images;


public class MenuView extends View {

    private LinkButton playButton;

    private LinkButton playArmageddonButton;

    private LinkButton quitButton;

    private Vec2 direction;

    private Vec2 plane;

    private Vec2 position;

    private ImageBuffer imageBuffer;

    @Override
    public void start() throws Exception {
        imageBuffer = new ImageBuffer(SCREEN_WIDTH, SCREEN_HEIGHT, true);

        position = new Vec2();
        direction = new Vec2(.71f, .71f);
        plane = new Vec2(.46f, -.46f);

        Mouse.setGrabbed(false);

        playButton = new LinkButton("Normal mode", WIDTH / 2, HEIGHT - 128);
        playButton.setColor(org.newdawn.slick.Color.yellow);
        playButton.setX(WIDTH / 2 - playButton.getWidth() / 2);
        playButton.setEvent(new Event() {

            @Override
            public void perform() throws Exception {
                GUI.get().switchView(new FadeTransition(new GameView("maps/01.map", false), 1000));
            }
        });

        playArmageddonButton = new LinkButton("Armageddon mode", WIDTH / 2, HEIGHT - 96);
        playArmageddonButton.setColor(org.newdawn.slick.Color.yellow);
        playArmageddonButton.setX(WIDTH / 2 - playArmageddonButton.getWidth() / 2);
        playArmageddonButton.setEvent(new Event() {

            @Override
            public void perform() throws Exception {
                GUI.get().switchView(new FadeTransition(new GameView("maps/02.map", true), 1000));
            }
        });

        quitButton = new LinkButton("Quit", WIDTH / 2, HEIGHT - 48);
        quitButton.setX(WIDTH / 2 - quitButton.getWidth() / 2);
        quitButton.setEvent(new Event() {

            @Override
            public void perform() throws Exception {
                GUI.get().close();
            }
        });

        add(playButton, playArmageddonButton, quitButton);
    }

    @Override
    public void update() throws Exception {
        super.update();

        if (GUI.get().isKeyPressed(KEY_ESCAPE)) {
            GUI.get().close();
        }
    }

    @Override
    public void paint(Graphics g) throws Exception {
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

                Color floorColor = FLOOR.getPixel(tx, ty);
                imageBuffer.setPixel(x, y, floorColor);

                Color ceilingColor = CEILING.getPixel(tx, ty);
                imageBuffer.setPixel(x, SCREEN_HEIGHT - y - 1, ceilingColor);
            }
        }

        int textureId = createTextureFromBuffer(imageBuffer.getBuffer(), SCREEN_WIDTH, SCREEN_HEIGHT, imageBuffer.hasAlpha());
        drawRectangle(0, 0, WIDTH, HEIGHT, textureId);

        TextureImpl.bindNone();

        Images.LOGO.getImage().draw(WIDTH / 2 - Images.LOGO.getImage().getWidth() * 2, 0, 4f);
        Images.BOMB.getImage().draw(0, 64, 4f);

        super.paint(g);
    }

    public static void main(String[] args) throws SlickException {
        new Game(new MenuView());
    }
}
