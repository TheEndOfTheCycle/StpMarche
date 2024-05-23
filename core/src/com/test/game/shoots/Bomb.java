package com.test.game.shoots;

/**
 * Cette classe représente un projectile de type Bomb dans le jeu.
 */
public class Bomb extends Projectile {
    public static final int WIDTH = 20; // Largeur de la bombe
    public static final int HEIGHT = 36; // Hauteur de la bombe

    /**
     * Constructeur de la classe Bomb.
     *
     * @param x     la position horizontale initiale de la bombe
     * @param y     la position verticale initiale de la bombe
     * @param speed la vitesse de déplacement de la bombe
     */
    public Bomb(float x, float y, float speed) {
        super(x, y, speed, "Amo/bomb.png"); // Assumant que vous avez une texture différente pour les bombes
    }

    /**
     * Met à jour la position de la bombe en fonction du temps écoulé.
     *
     * @param delta le temps écoulé depuis la dernière mise à jour
     */
    @Override
    public void update(float delta) {
        y -= speed * delta;
        x -= 100f * delta;
    }

    /**
     * Obtient la largeur de la bombe.
     *
     * @return la largeur de la bombe
     */
    @Override
    public int getWidth() {
        return WIDTH;
    }

    /**
     * Obtient la hauteur de la bombe.
     *
     * @return la hauteur de la bombe
     */
    @Override
    public int getHeight() {
        return HEIGHT;
    }
}
