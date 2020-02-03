package com.zhange.rpg.graphics;

import java.awt.image.BufferedImage;

/**
 * Gere le les animations
 */

public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;
    private int numFrames;

    private int count;
    private int delay;

    private int timesPlayed;

    public Animation(BufferedImage[] frames) {
        timesPlayed = 0;
        setFrames(frames);
    }

    public Animation() {
        timesPlayed = 0;
    }

    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        count = 0;
        timesPlayed = 0;
        delay = 2;
        numFrames = frames.length;
    }

    public void setDelay(int i) {
        this.delay = i;
    }

    public void setFrame(int i) {
        this.currentFrame = i;
    }

    public void setNumFrames(int i) {
        this.numFrames = i;
    }

    public void update() {
        if (delay == -1) return;

        count++;

        if (count == delay) {
            currentFrame++;
            count = 0;
        }
        if (currentFrame == numFrames) {
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public BufferedImage getImage() {
        return frames[currentFrame];
    }
    public int getCurrentFrame() {
        return currentFrame;
    }
    public int getCount() {
        return count;
    }
    public int getDelay() {
        return delay;
    }
    public boolean hasBeenPlayed() {
        return timesPlayed > 0;
    }
    public boolean hasPlayed(int i) {
        return timesPlayed == i;
    }
}
