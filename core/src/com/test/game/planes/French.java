package com.test.game.planes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.test.game.shoots.Bullets;
import com.test.game.shoots.Projectile;

public class French extends Plane {

    float speed;
    private static final int SCORE_VALUE = 1;
    private float timeSinceLastShot = 0;
    private final float shootingInterval = 0.5f;

    private final int BULLET_SPEED = 100;

    // Constructeur de la classe
    public French(int x, int y, int height, int width, float speed, Texture TEXTURE_FRENCH) {
        super(x, y, height, width, TEXTURE_FRENCH);
        setSpeed(speed);
        setHp(1);
    }

    // Méthode pour mettre à jour la position de l'objet French
    @Override
    public void update() {
        setX(getX() - getSpeed());
    }

    // Getters et setters
    private float getSpeed() {
        return speed;
    }

    private void setSpeed(float speed) {
        this.speed = speed;
    }

    public static int getScoreValue() {
        return SCORE_VALUE;
    }

    /**
     * Fait tirer l'objet French en ajoutant un projectile à la liste des projectiles.
     *
     * @param projectiles La liste de projectiles où ajouter le nouveau projectile.
     */
    public void shoot(Array<Projectile> projectiles) {
        Bullets bullet = new Bullets(getX(), getY() + this.getHeight() / 2, BULLET_SPEED * getSpeed());
        projectiles.add(bullet);
    }

    /**
     * Met à jour le tir de l'objet French en générant de nouveaux projectiles.
     *
     * @param delta        Le temps écoulé depuis la dernière mise à jour.
     * @param projectiles  La liste de projectiles où ajouter de nouveaux projectiles.
     */
    public void updateFire(float delta, Array<Projectile> projectiles) {
        timeSinceLastShot += delta;
        if (timeSinceLastShot >= shootingInterval) {
            shoot(projectiles);
            timeSinceLastShot = 0;
        }
    }
}
