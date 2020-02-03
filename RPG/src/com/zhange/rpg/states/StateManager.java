package com.zhange.rpg.states;

import com.zhange.rpg.GamePanel;
import com.zhange.rpg.util.KeyHandler;
import com.zhange.rpg.util.MouseHandler;
import com.zhange.rpg.math.Vector2f;

import java.awt.*;
import java.util.ArrayList;

/**
 * Pour passer de state en state
 */

public class StateManager {

    private ArrayList<State> states;

    public static Vector2f map;
    protected static int Score = 0;

    public static final int Play = 0;
    public static final int Win = 1;
    public static final int Pause = 2;
    public static final int GameOver = 3;
    public static final int StartMenu = 4;
    public static final int HelpMenu = 5;

    public StateManager() {
        map = new Vector2f(GamePanel.width, GamePanel.height);
        Vector2f.setWorldVar(map.x, map.y);

        states = new ArrayList<>();
        states.add(new StartMenuState(this));
    }

    public void pop(int state) {
        states.remove(state);
    }

    public void add(int state) {
        if (state == Play) {
            Score = 0;
            states.add(new PlayState(this));
        }
        if (state == Win) {
            states.add(new WinState(this));
        }
        if (state == Pause) {
            states.add(new PauseState(this));
        }
        if (state == GameOver) {
            states.add(new GameOverState(this));
        }
        if (state == StartMenu) {
            states.add(new StartMenuState(this));
        }
        if (state == HelpMenu) {
            states.add(new HelpState(this));
        }
    }

    public void addAndPop(int state) {
        states.remove(0);
        add(state);
    }

    public static void addScore(int point) {
        Score += point;
    }

    public void update(double time) {
        Vector2f.setWorldVar(map.x, map.y);
        //uiManager.update();
        for (State state : states) {
            state.update(time);
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        for (State state : states) {
            state.input(mouse, key);
        }
    }

    public void render(Graphics2D g) {
        //uiManager.render(g);
        for (State state : states) {
            state.render(g);
        }
    }
}
