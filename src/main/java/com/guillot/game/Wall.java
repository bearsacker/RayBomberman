package com.guillot.game;

public class Wall {

    private double height;

    private Images texture;

    public Wall(double height, Images texture) {
        this.height = height;
        this.texture = texture;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Images getTexture() {
        return texture;
    }

    public void setTexture(Images texture) {
        this.texture = texture;
    }
}
