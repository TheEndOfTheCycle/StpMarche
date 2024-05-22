package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.test.game.shoots.Bullets;
import com.test.game.shoots.Projectile;

public class French extends Plane {

    float speed;
    private static int SCORE_VALUE = 1;
    private final static Texture TEXTURE_FRENCH = new Texture(Gdx.files.internal("Planes/french.png"));
    private float timeSinceLastShot = 0;
    private float shootingInterval = 2.0f; // Intervalle de tir en secondes

    // Constructeur de la classe
    public French(int x, int y, int height, int width, float speed) {
        super(x, y, height, width, TEXTURE_FRENCH);
        setSpeed(speed);
        setHp(1);
    }

    // Méthode pour mettre à jour la position de l'objet French
    @Override
    public void update() {
        setX(getX() - getSpeed());
    }

    public static int getScoreValue() {
        return SCORE_VALUE;
    }

    // Méthode pour tirer des projectiles en ligne droite (vers la gauche)
    public void shoot(Array<Projectile> projectiles) {
        Bullets bullet = new Bullets(getX(), getY(), 200f);
        projectiles.add(bullet);
    }

    public void update(float delta, Array<Projectile> projectiles) {
        timeSinceLastShot += delta;
        if (timeSinceLastShot >= shootingInterval) {
            shoot(projectiles);
            timeSinceLastShot = 0;
        }

        // Mise à jour des projectiles tirés par l'avion French
        for (Projectile projectile : projectiles) {
            if (projectile instanceof Bullets) {
                ((Bullets) projectile).updateForEnemy(delta);
            }
        }
    }
}
