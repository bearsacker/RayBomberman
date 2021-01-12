package com.guillot.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.jbox2d.common.Vec2;

public class Map {

    private int width;

    private int height;

    private Wall[][] tiles;

    private ArrayList<Entity> entities;

    private LinkedList<Event> events;

    public Map(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = reader.lines().collect(Collectors.toList());
        reader.close();

        width = lines.get(0).length();
        height = lines.size();
        tiles = new Wall[width][height];
        entities = new ArrayList<>();
        events = new LinkedList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Wall wall = null;
                switch (lines.get(y).charAt(x)) {
                case '#':
                    wall = new Wall(64, Images.UNBREAKABLE, false);
                    break;
                case 'X':
                    wall = new Wall(64, Images.WALL, true);
                    break;
                }

                tiles[x][y] = wall;
            }
        }
    }

    public void update() {
        if (!events.isEmpty()) {
            Event event = events.pop();
            event.perform(this);
        }

        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            entity.update(this);
            if (entity.isToRemove()) {
                iterator.remove();
            }
        }
    }

    public Wall getTile(int x, int y) {
        return tiles[x][y];
    }

    public Wall getTile(Vec2 position) {
        return tiles[(int) position.x][(int) position.y];
    }

    public void setTile(Vec2 position, Wall value) {
        tiles[(int) position.x][(int) position.y] = value;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void pushEvent(Event event) {
        events.push(event);
    }
}
