package com.guillot.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Map {

    private int width;

    private int height;

    private Wall[][] tiles;

    private ArrayList<Bomb> entities;

    public Map(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = reader.lines().collect(Collectors.toList());
        reader.close();

        width = lines.get(0).length();
        height = lines.size();
        tiles = new Wall[width][height];
        entities = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Wall wall = null;
                switch (lines.get(y).charAt(x)) {
                case '#':
                    wall = new Wall(64, Images.UNBREAKABLE);
                    break;
                case 'X':
                    wall = new Wall(64, Images.WALL);
                    break;
                }

                tiles[x][y] = wall;
            }
        }
    }

    public void update() {
        entities.forEach(x -> x.update());
    }

    public Wall getTile(int x, int y) {
        return tiles[x][y];
    }

    public ArrayList<Bomb> getEntities() {
        return entities;
    }
}
