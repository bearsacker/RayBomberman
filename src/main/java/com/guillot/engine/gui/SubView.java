package com.guillot.engine.gui;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

public abstract class SubView extends Component {

    private ArrayList<Component> components;

    private View parent;

    public SubView(View parent) {
        this.parent = parent;
        this.components = new ArrayList<Component>();
        this.visible = false;
    }

    public abstract void onShow() throws Exception;

    public abstract void onHide() throws Exception;

    public void add(Component... components) {
        for (Component component : components) {
            this.components.add(component);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        try {
            if (!this.visible && visible) {
                onShow();
                if (parent != null) {
                    parent.setFocused(false);
                }
            } else if (this.visible && !visible) {
                onHide();
                if (parent != null) {
                    parent.setFocused(true);
                }
            }

            this.visible = visible;
        } catch (Exception e) {
            GUI.get().switchView(new ViewException(e));
        }
    }

    @Override
    public void update(int offsetX, int offsetY) throws Exception {
        super.update(offsetX, offsetY);

        for (Component c : components) {
            c.update(x, y);
        }
    }

    @Override
    public void paint(Graphics g) {
        paintCompoments(g);
    }

    protected void paintCompoments(Graphics g) {
        for (Component c : components) {
            if (c.isVisible()) {
                c.paint(g);
            }
        }
    }
}
