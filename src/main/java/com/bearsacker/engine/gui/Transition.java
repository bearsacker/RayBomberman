package com.bearsacker.engine.gui;


public abstract class Transition extends View {

    protected View from;

    protected View to;

    public Transition(View to) {
        this.from = GUI.get().getCurrentView();
        this.from.focused = false;
        this.to = to;
        this.to.focused = false;
    }

    @Override
    public void start() throws Exception {}
}
