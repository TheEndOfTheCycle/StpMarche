package com.test.game.shoots;

public class Bullets extends Projectile {
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
}
