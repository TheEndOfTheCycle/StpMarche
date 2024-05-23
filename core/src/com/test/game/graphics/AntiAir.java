package com.test.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.test.game.planes.Plane;
import com.test.game.shoots.Projectile;
import com.test.game.shoots.SolAir;

/**
 * La classe AntiAir représente une unité anti-aérienne dans le jeu.
 * Elle est capable de tirer sur des avions ennemis et gère ses tirs à intervalles réguliers.
 */
public class AntiAir extends MapObjects {
    // Intervalle de tir en secondes
    private final float shootingInterval = 1.0f;
    
    // Temps écoulé depuis le dernier tir
    private float timeSinceLastShot = 0.0f;
    
    // Largeur de l'unité anti-aérienne
    public final static int RED_WIDTH = 100;
    
    // Hauteur de l'unité anti-aérienne
    private final static int RED_HEIGHT = 76;
    
    // Valeur de score attribuée pour la destruction de l'unité anti-aérienne
    private static final int SCORE_VALUE = 3;
    
    // Texture de l'unité anti-aérienne
    private final static Texture TEXTURE_ANTI_AIR = new Texture(Gdx.files.internal("BackGroundImages/anti.png"));

    /**
     * Constructeur de la classe AntiAir.
     *
     * @param x la coordonnée x initiale de l'unité anti-aérienne
     * @param y la coordonnée y initiale de l'unité anti-aérienne
     */
    public AntiAir(int x, int y) {
        super(x, y, RED_WIDTH, RED_HEIGHT, TEXTURE_ANTI_AIR);
    }

    /**
     * Méthode pour tirer vers l'avion cible.
     *
     * @param target l'avion cible
     * @param projectiles la liste des projectiles à laquelle ajouter le nouveau projectile
     */
    public void shootAt(Plane target, Array<Projectile> projectiles) {
        float angle = calculateAngle(getX(), getY(), target.getX(), target.getY()); // on calcule l'angle de tir
        SolAir bullet = new SolAir(getX(), getY() + RED_HEIGHT, 400f, angle); // on crée une balle et on ajuste ses coordonnées selon l'angle de tir
        projectiles.add(bullet);
    }

    /**
     * Calcule l'angle entre deux points.
     *
     * @param startX la coordonnée x de départ
     * @param startY la coordonnée y de départ
     * @param targetX la coordonnée x de la cible
     * @param targetY la coordonnée y de la cible
     * @return l'angle en radians entre les deux points
     */
    private float calculateAngle(float startX, float startY, float targetX, float targetY) {
        return (float) Math.atan2(targetY - startY, targetX - startX); // Conversion en flottant
    }

    /**
     * Met à jour l'état de l'unité anti-aérienne et tire sur la cible à intervalles réguliers.
     *
     * @param delta le temps écoulé depuis la dernière mise à jour
     * @param target l'avion cible
     * @param projectiles la liste des projectiles à laquelle ajouter les nouveaux projectiles
     */
    public void update(float delta, Plane target, Array<Projectile> projectiles) {
        timeSinceLastShot += delta;
        if (timeSinceLastShot >= shootingInterval) {
            shootAt(target, projectiles);
            timeSinceLastShot = 0;
        }
    }

    /**
     * Vérifie si l'unité anti-aérienne entre en collision avec un projectile.
     *
     * @param projectile le projectile à vérifier
     * @return true si l'unité anti-aérienne entre en collision avec le projectile, false sinon
     */
    public boolean collidesWith(Projectile projectile) {
        return (getX() + getWidth() >= projectile.getX()) && (getX() <= (projectile.getX()))
                && (getY() + getHeight() >= projectile.getY()) && (getY() <= (projectile.getY()));
    }

    /**
     * Retourne la valeur de score attribuée pour la destruction de l'unité anti-aérienne.
     *
     * @return la valeur de score de l'unité anti-aérienne
     */
    public static int getScoreValue() {
        return SCORE_VALUE;
    }
}
