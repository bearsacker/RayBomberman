package com.guillot.engine.gui;

import static com.guillot.engine.configs.GUIConfig.VIEW_BACKGROUND_COLOR;

import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public abstract class View {

    protected Color backgroundColor;

    protected boolean focused;

    private LinkedList<Component> components;

    public View() {
        components = new LinkedList<Component>();
        backgroundColor = VIEW_BACKGROUND_COLOR;
        focused = true;
    }

    public abstract void start() throws Exception;

    public void stop(boolean closingWindow) throws Exception {
        components.forEach(Component::destroy);
        components.clear();
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = new Color(backgroundColor);
    }

    public void add(Component... components) {
        for (Component component : components) {
            this.components.add(component);
        }
    }

    public void update() throws Exception {
        for (Component component : components) {
            if ((component.isVisible() && focused) || (!focused && component instanceof SubView && component.isVisible())) {
                component.update(0, 0);
            }
        }
    }

    public void paint(Graphics g) throws Exception {
        g.setBackground(backgroundColor);

        components.stream().filter(x -> x.isVisible()).forEach(x -> x.paint(g));
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }
}
