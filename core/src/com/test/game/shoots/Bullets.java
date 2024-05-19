package com.test.game.shoots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullets extends Projectile {
    private float angle;

    public Bullets(float x, float y, float speed, float angle) {
        super(x, y, speed, "Amo/bullets.png");
        this.angle = angle;
    }

    @Override
    public void update(float delta) {
        x += speed * Math.cos(angle) * delta;
        y += speed * Math.sin(angle) * delta;
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Dessiner le projectile ici
        batch.draw(texture, x, y);
    }
}

