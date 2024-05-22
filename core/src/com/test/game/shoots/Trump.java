package com.test.game.shoots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Trump extends Projectile {
    private float angle;
    public int HEIGHT = 30;
    public int WIDTH = 30;
    ShapeRenderer shape;

    public Trump(float x, float y, float speed, float angle) {
        super(x, y, speed, "Amo/trump.png");
        this.angle = angle;

    }

    @Override
    public void update(float delta) {
        x += speed * Math.cos(angle) * delta;
        y += speed * Math.sin(angle) * delta;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
        // drawFollow(shape);
    }

    public void drawFollow(ShapeRenderer shape) {
        shape.rect(getX(), getY(), WIDTH, HEIGHT);

    }

}