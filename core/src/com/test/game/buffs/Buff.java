package com.test.game.buffs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Buff {
    protected float x;
    protected float y;
    private static int width = 40;
    private static int height = 40;
    protected Texture texture;

    public Buff(int Xlevel, int Ylevel) {
        this.x = Xlevel;
        this.y = Ylevel;
    }

    public float getX() {
        return x;
    }

    public final void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public final void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Texture getTexture() {
        return texture;
    }

    public final void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    }
}
