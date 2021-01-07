package com.guillot.engine.particles;

import static org.apache.commons.math3.util.FastMath.cos;
import static org.apache.commons.math3.util.FastMath.random;
import static org.apache.commons.math3.util.FastMath.sin;

import java.io.Serializable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.guillot.engine.configs.ParticlesConfig;
import com.guillot.engine.utils.NumberGenerator;


public class Fire implements Particle, Serializable {

    private static final long serialVersionUID = -3757856673705062231L;

    private final static int SIZE = ParticlesConfig.get().getInt("fire.size");

    private final static Color COLOR = ParticlesConfig.getColor("fire.color");

    private final static double RATIODEGRAD = 0.0174533;

    private Generator generator;

    private float x;

    private float y;

    private int angle;

    private float remainingDuration;

    private float duration;

    private int speed;

    private boolean cinder;

    public Fire(Generator generator, float duration, int speed) {
        this.generator = generator;
        x = NumberGenerator.get().randomInt(-2, 2);
        y = NumberGenerator.get().randomInt(-2, 2);

        this.speed = speed;

        angle = generator.getAngle();
        if (random() < 0.5) {
            angle += (int) (random() * generator.getRadius() / 2);
        } else {
            angle -= (int) (random() * generator.getRadius() / 2);
        }
        angle %= 360;

        remainingDuration = (int) (random() * duration / 2) + .75f * duration;
        cinder = remainingDuration > duration;

        this.duration = remainingDuration;
    }

    @Override
    public Particle create() {
        return new Fire(generator, duration, speed);
    }

    @Override
    public boolean isDead() {
        return remainingDuration <= 0.0f;
    }

    @Override
    public void update(float delta) {
        remainingDuration -= delta;
        x += delta * speed * cos(RATIODEGRAD * angle);
        y += delta * speed * sin(RATIODEGRAD * angle);

        angle += random() > .5f ? 1 : -1;
    }

    @Override
    public void draw(Graphics g) {
        float alpha = remainingDuration / duration;
        if (cinder) {
            g.setColor(new Color(0f, 0f, 0f, 1f - alpha));
        } else {
            g.setColor(new Color(COLOR.r, COLOR.g * alpha, COLOR.b * alpha, .75f + alpha / 4f));
        }
        g.fillRect(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
    }
}
