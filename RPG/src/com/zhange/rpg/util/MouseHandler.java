package com.zhange.rpg.util;

import com.zhange.rpg.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    private boolean isLeftPressed, isRightPressed, isMiddleClicked;
    private int x, y;

    public MouseHandler(GamePanel GP) {
        GP.addMouseListener(this);
        GP.addMouseMotionListener(this);
    }

    public int getX() {
        return x;
    }

    public boolean isLeftPressed() {
        return isLeftPressed;
    }

    public boolean isRightPressed() {
        return isRightPressed;
    }

    public boolean isMiddleClicked() {
        return isMiddleClicked;
    }

    public int getY() {
        return y;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            isLeftPressed = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            isRightPressed = true;
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            isMiddleClicked = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            isLeftPressed = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            isRightPressed = false;
        }  else if (e.getButton() == MouseEvent.BUTTON2) {
            isMiddleClicked = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}
