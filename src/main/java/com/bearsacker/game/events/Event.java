package com.bearsacker.game.events;

import com.bearsacker.game.Map;
import com.bearsacker.game.Player;

public interface Event {

    void perform(Map map, Player player);

}
