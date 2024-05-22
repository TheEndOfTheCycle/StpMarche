package com.test.game.buffs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.graphics.Wall;

public class Buff {
    protected float x;
    protected float y;
    private static int width = 40;
    private static int height = 40;
    protected Texture texture;
    private final int ITEM_SPEED = 1;

    public Buff(int Xlevel, int Ylevel) {
        this.x = Xlevel;
        this.y = Ylevel;
    }

    public void update() {
        setX(getX() - getSpeed());
    }

    // getters et setters
    private float getSpeed() {
        return ITEM_SPEED;
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

    public boolean collidesWith(Wall wall) {
        return (getX() + getWidth() >= wall.getX()) && (getX() <= (wall.getX() + wall.getWidth()))
                && (getY() + getHeight() >= wall.getY())
                && (getY() <= (wall.getY() + wall.getHeight()));
    }
}
