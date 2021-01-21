package com.bearsacker.game;


public interface Playable {

    boolean hasPowerBomb();

    boolean hasRedBomb();

    boolean hasGlove();

    void retrieveBomb();

    void pickUpGlove();

    void pickUpPowerBomb();

    void pickUpRedBomb();

    void incrementRange();

    void decrementRange();

    void incrementBombs();

    void decrementBombs();

    void incrementSpeed();

    void decrementSpeed();
}
