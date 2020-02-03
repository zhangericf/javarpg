package com.zhange.rpg.states;

import com.zhange.rpg.util.KeyHandler;
import com.zhange.rpg.util.MouseHandler;

import java.awt.*;

public abstract class State {

    protected StateManager SM;

    public State(StateManager SM) {
        this.SM = SM;
    }

    public abstract void update(double time);
    public abstract void input(MouseHandler mouse, KeyHandler key);
    public abstract void render(Graphics2D g);
}
