package com.guillot.game;

public class Wall {

    private double height;

    private Images texture;

    private boolean breakable;

    public Wall(double height, Images texture, boolean breakable) {
        this.height = height;
        this.texture = texture;
        this.breakable = breakable;
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

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

}
