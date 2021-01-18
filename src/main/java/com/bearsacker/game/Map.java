package com.bearsacker.game;

import static com.bearsacker.game.configs.GameConfig.ITEM_PROBABILITY;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.jbox2d.common.Vec2;

import com.bearsacker.engine.utils.NumberGenerator;
import com.bearsacker.game.entities.Bot;
import com.bearsacker.game.entities.Entity;
import com.bearsacker.game.entities.Fire;
import com.bearsacker.game.events.AddItem;
import com.bearsacker.game.events.Event;
import com.bearsacker.game.resources.Images;

public class Map {

    private int width;

    private int height;

    private Wall[][] tiles;

    private ArrayList<Vec2> spawns;

    private ArrayList<Entity> entities;

    private LinkedList<Event> events;

    public Map(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = reader.lines().collect(Collectors.toList());
        reader.close();

        width = lines.get(0).length();
        height = lines.size();
        tiles = new Wall[width][height];
        spawns = new ArrayList<>();
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
                case 'S':
                    spawns.add(new Vec2(x + .5f, y + .5f));
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
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return null;
        }

        return tiles[x][y];
    }

    public Wall getTile(Vec2 position) {
        if (position.x < 0 || position.y < 0 || position.x >= width || position.y >= height) {
            return null;
        }

        return tiles[(int) position.x][(int) position.y];
    }

    public void breakWall(Vec2 position) {
        tiles[(int) position.x][(int) position.y] = null;
        if (NumberGenerator.get().randomDouble() < ITEM_PROBABILITY) {
            pushEvent(new AddItem(position));
        }
    }

    public void addBot(Vec2 position) {
        entities.add(new Bot(position));
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getEntitiesAt(Vec2 position) {
        return entities.stream()
                .filter(x -> ((int) x.getPosition().x) == ((int) position.x) && ((int) x.getPosition().y) == ((int) position.y))
                .collect(Collectors.toList());
    }

    public boolean isBurningAt(Vec2 position) {
        return entities.parallelStream().anyMatch(x -> x instanceof Fire && ((int) x.getPosition().x) == ((int) position.x)
                && ((int) x.getPosition().y) == ((int) position.y));
    }

    public ArrayList<Vec2> getSpawns() {
        return spawns;
    }

    public void pushEvent(Event event) {
        events.push(event);
    }
}
