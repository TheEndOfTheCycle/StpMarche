// Wall.java
package com.test.game.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Wall {
    public static final int WIDTH = 14;
    public static final int HEIGHT = 14;

    public Rectangle bounds;
    private final Texture texture;

    public Wall(int x, int y, Texture texture) {
        this.bounds = new Rectangle(x, y, WIDTH, HEIGHT);
        this.texture = texture;
    }
    
    public float getWidth() {
        return WIDTH;
    }

    public float getHeight() {
        return HEIGHT;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public float getX() {
        return bounds.x;
    }
}

