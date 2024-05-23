package com.test.game.shoots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Trump extends Projectile {
    private float angle;

    private final static int WIDTH = 80;
    private final static int HEIGHT = 80;

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
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

}