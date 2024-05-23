package com.test.game.shoots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullets extends Projectile {

    public static final int WIDTH = 23;
    public static final int HEIGHT = 5;

    public Bullets(float x, float y, float speed) {
        super(x, y, speed, "Amo/bullets.png");
    }

    @Override
    public void update(float delta) {// on update la position du projectile par rapport au temps delta qui represente
                                     // elapser depuis le dernier frame
        x += speed * delta;
    }

    public void updateForEnemy(float delta) {
        // Déplacer le projectile vers la gauche
        x -= speed * delta;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }
}
