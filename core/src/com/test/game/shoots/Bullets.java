package com.test.game.shoots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullets extends Projectile {
    
    public Bullets(float x, float y, float speed) {
        super(x, y, speed, "Amo/bullets.png");
    }

    @Override
    public void update(float delta) {
        // Cette méthode peut être utilisée pour les projectiles tirés par le joueur
        x += speed * delta;
    }

    public void updateForEnemy(float delta) {
        // Déplacer le projectile vers la gauche
        x -= speed * delta;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
}
