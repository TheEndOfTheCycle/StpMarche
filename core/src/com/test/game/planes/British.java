package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.test.game.shoots.Bullets;
import com.test.game.shoots.Projectile;

/**
 * Cette classe représente l'avion British dans le jeu.
 */
public class British extends Plane {
    private float speed;
    private float timeSinceLastShot = 0;
    private final float shootingInterval = 1.5f;
    private final static int SCORE_VALUE = 2;
    private final float Yspeed = 100; // Vitesse verticale (arbitraire, peut être ajustée)
    private final float AltitudeInitiale; // Altitude de départ de l'avion
    private final float VariationAltitude = 50; // Variation d'altitude pour le zigzag
    private final float MAX; // Altitude maximale
    private final float MIN; // Altitude minimale
    private boolean movingUp; // Indicateur de direction

    /**
     * Constructeur de la classe British.
     *
     * @param x              La position en x de l'avion.
     * @param y              La position en y de l'avion.
     * @param height         La hauteur de l'avion.
     * @param width          La largeur de l'avion.
     * @param speed          La vitesse de l'avion.
     * @param TEXTURE_BRITISH La texture de l'avion.
     */
    public British(int x, int y, int height, int width, float speed, Texture TEXTURE_BRITISH) {
        super(x, y, height, width, TEXTURE_BRITISH);
        this.speed = speed;
        setHp(1);
        AltitudeInitiale = y;
        MAX = AltitudeInitiale + VariationAltitude;
        MIN = AltitudeInitiale - VariationAltitude;
        movingUp = false; // Initialement, l'avion descend
    }

    /**
     * Met à jour la position de l'avion British.
     */
    @Override
    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Déplacement vertical en zigzag
        if (movingUp) {
            setY(getY() + Yspeed * deltaTime);
            if (getY() >= MAX) {
                movingUp = false; // Changer de direction pour descendre
            }
        } else {
            setY(getY() - Yspeed * deltaTime);
            if (getY() <= MIN) {
                movingUp = true; // Changer de direction pour monter
            }
        }

        // Déplacement horizontal vers la gauche
        if (getSpeed() > 0) {
            setX(getX() - getSpeed());
        } else {
            setX(getX() + getSpeed());
        }
    }

    /**
     * Met à jour le tir de l'avion British et évite les collisions avec le sol.
     *
     * @param delta       Le temps écoulé depuis la dernière mise à jour.
     * @param projectiles La liste de projectiles où ajouter de nouveaux projectiles.
     */
    public void updateFire(float delta, Array<Projectile> projectiles) {
        // Mise à jour du temps depuis le dernier tir
        timeSinceLastShot += delta; //déterminer quand un nouvel projectile peut être tiré en vérifiant si le temps écoulé dépasse l'intervalle de tir spécifié (shootingInterval)
        if (timeSinceLastShot >= shootingInterval) {
            shoot(projectiles);
            timeSinceLastShot = 0;
        }

        // Mise à jour de la position de l'avion en zigzag
        update();

        // Éviter les collisions avec le sol
        // Vous pouvez ajouter le code pour éviter les collisions avec le sol ici
    }

    /**
     * Obtient la vitesse de l'avion British.
     *
     * @return La vitesse de l'avion.
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Définit la vitesse de l'avion British.
     *
     * @param speed La vitesse de l'avion.
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Obtient la valeur de score de l'avion British.
     *
     * @return La valeur de score de l'avion.
     */
    public static int getScoreValue() {
        return SCORE_VALUE;
    }

    /**
     * Tire des projectiles en ligne droite (vers la gauche).
     *
     * @param projectiles La liste de projectiles où ajouter les nouveaux projectiles.
     */
    public void shoot(Array<Projectile> projectiles) {
        Bullets bullet = new Bullets(getX(), getY(), 200f);
        projectiles.add(bullet);
    }
}
