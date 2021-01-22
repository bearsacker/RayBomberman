package com.bearsacker.engine.gui;

import static com.bearsacker.engine.configs.EngineConfig.HEIGHT;
import static com.bearsacker.engine.configs.EngineConfig.WIDTH;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class FadeTransition extends Transition {

    private long duration;

    private long currentTime;

    private long beginTime;

    private int step;

    public FadeTransition(View to, long duration) {
        super(to);
        this.duration = duration;
        this.beginTime = System.currentTimeMillis();
        this.step = 0;
    }

    @Override
    public void update() throws Exception {
        currentTime = System.currentTimeMillis();
        if (currentTime - beginTime > duration) {
            step++;
            if (step == 1) {
                from.stop(false);
                to.start();
            }
            beginTime = currentTime;
        }

        switch (step) {
        case 0:
            from.update();
            break;
        case 1:
            to.update();
            break;
        case 2:
            to.focused = true;
            GUI.get().switchView(to);
            break;
        }
    }

    @Override
    public void paint(Graphics g) throws Exception {
        switch (step) {
        case 0:
            from.paint(g);
            break;
        case 1:
            to.paint(g);
            break;
        }

        float alpha = (currentTime - beginTime) / (float) duration;

        g.setColor(new Color(0f, 0f, 0f, step == 0 ? alpha : 1f - alpha));
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

}
