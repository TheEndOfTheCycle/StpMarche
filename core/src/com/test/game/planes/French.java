package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.test.game.shoots.Bullets;
import com.test.game.shoots.Projectile;

public class French extends Plane {

    float speed;
    private static int SCORE_VALUE = 1;
    private float timeSinceLastShot = 0;
    private float shootingInterval = 0.5f;

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

    // getters et setters
    private float getSpeed() {
        return speed;
    }

    private void setSpeed(float speed) {
        this.speed = speed;
    }

    public static int getScoreValue() {
        return SCORE_VALUE;
    }

    public void shoot(Array<Projectile> projectiles) {
        Bullets bullet = new Bullets(getX(), getY() + this.getHeight() / 2, BULLET_SPEED * getSpeed());// (Bullet speed
                                                                                                       // * getspped a
                                                                                                       // fin que le
                                                                                                       // projectile
                                                                                                       // soit toujours
                                                                                                       // plus rapide
                                                                                                       // que l avion)
        projectiles.add(bullet);
    }

    public void updateFire(float delta, Array<Projectile> projectiles) {// permet de creer et mettre a jour la position
                                                                        // des balles
        timeSinceLastShot += delta;
        if (timeSinceLastShot >= shootingInterval) {
            shoot(projectiles);// on genere le tire
            timeSinceLastShot = 0;
        }

    }
}
