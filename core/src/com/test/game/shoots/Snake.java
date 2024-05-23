package com.test.game.shoots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Snake extends Projectile {
    private float angle;

    private final static int WIDTH = 20;
    private final static int HEIGHT = 30;

    public Snake(float x, float y, float speed, float angle) {
        super(x, y, speed, "Amo/drakeSnake.png");
        this.angle = angle;
    }

    @Override
    public void update(float delta) {
        x += speed * Math.cos(angle) * delta;
        y += speed * Math.sin(angle) * delta;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

}