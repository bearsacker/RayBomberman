package com.bearsacker.game.resources;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public enum Sounds {
    END("sounds/end.wav"), //
    PICKUP("sounds/pickup.wav"), //
    EXPLOSION("sounds/explosion.wav"), //
    PLANT("sounds/plant.wav");

    private Sound sound;

    private Sounds(String path) {
        try {
            sound = new Sound(path);
        } catch (SlickException e) {
        }
    }

    public Sound getSound() {
        return sound;
    }
}
