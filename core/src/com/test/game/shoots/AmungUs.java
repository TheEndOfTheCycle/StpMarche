package com.test.game.shoots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AmungUs extends Projectile {

    public AmungUs(float x, float y, float speed) {
        super(x, y, speed, "Amo/amungUs.png");
    }

    @Override
    public void update(float delta) {
        // Cette méthode peut être utilisée pour les projectiles tirés par le joueur
        x -= speed * delta;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, getWidth(), getHeight());
    }

}
