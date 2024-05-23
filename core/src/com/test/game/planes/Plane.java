/**
 * Représente un avion dans le jeu.
 */
package com.test.game.planes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.graphics.Wall;
import com.test.game.graphics.Zeppelin;
import com.test.game.shoots.Projectile;

public class Plane {

    protected float x; // Coordonnée x de l'avion
    protected float y; // Coordonnée y de l'avion
    private final int width; // Largeur de l'avion
    private final int height; // Hauteur de l'avion
    private Texture texture; // Texture de l'avion
    private boolean isFireHit = false; // Indique si l'avion a été touché par un projectile
    protected boolean deadByFireHit = false; // Indique si l'avion est détruit par un projectile
    protected boolean gameOver = false; // Indique si le jeu est terminé
    private int hp; // Points de vie de l'avion

    /**
     * Construit un nouvel avion avec les coordonnées, les dimensions et la texture spécifiées.
     *
     * @param x Coordonnée x de l'avion
     * @param y Coordonnée y de l'avion
     * @param width Largeur de l'avion
     * @param height Hauteur de l'avion
     * @param texture Texture de l'avion
     */
    public Plane(float x, float y, int width, int height, Texture texture) {
        setX(x);
        setY(y);
        this.width = width;
        this.height = height;
        setTexture(texture);
    }

    /**
     * Définit si l'avion a été touché par un projectile.
     *
     * @param etat true si l'avion a été touché, false sinon
     */
    public void setIsFireHit(boolean etat) {
        this.isFireHit = etat;
    }

    /**
     * Retourne true si l'avion a été touché par un projectile, false sinon.
     *
     * @return true si l'avion a été touché, false sinon
     */
    public boolean getIsFireHit() {
        return isFireHit;
    }

    /**
     * Définit si l'avion est détruit par un projectile.
     *
     * @param etat true si l'avion est détruit, false sinon
     */
    public void setIsDeadByFireHit(boolean etat) {
        this.deadByFireHit = etat;
    }

    /**
     * Retourne true si l'avion est détruit par un projectile, false sinon.
     *
     * @return true si l'avion est détruit, false sinon
     */
    public boolean getIsDeadByFireHit() {
        return isFireHit;
    }

    /**
     * Retourne la coordonnée x de l'avion.
     *
     * @return Coordonnée x de l'avion
     */
    public float getX() {
        return x;
    }

    /**
     * Définit la coordonnée x de l'avion.
     *
     * @param x Coordonnée x de l'avion
     */
    public final void setX(float x) {
        this.x = x;
    }

    /**
     * Retourne la coordonnée y de l'avion.
     *
     * @return Coordonnée y de l'avion
     */
    public float getY() {
        return y;
    }

    /**
     * Définit la coordonnée y de l'avion.
     *
     * @param y Coordonnée y de l'avion
     */
    public final void setY(float y) {
        this.y = y;
    }

    /**
     * Retourne la largeur de l'avion.
     *
     * @return Largeur de l'avion
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retourne la hauteur de l'avion.
     *
     * @return Hauteur de l'avion
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retourne la texture de l'avion.
     *
     * @return Texture de l'avion
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Définit la texture de l'avion.
     *
     * @param texture Texture de l'avion
     */
    public final void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * Définit les points de vie de l'avion.
     *
     * @param hp Points de vie de l'avion
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Retourne les points de vie de l'avion.
     *
     * @return Points de vie de l'avion
     */
    public int getHp() {
        return hp;
    }

    /**
     * Dessine l'avion sur le SpriteBatch spécifié.
     *
     * @param batch SpriteBatch sur lequel dessiner l'avion
     */
    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Met à jour l'état de l'avion.
     */
    public void update() {}

    /**
     * Vérifie si l'avion entre en collision avec un mur.
     *
     * @param wall Mur à vérifier
     * @return true si l'avion entre en collision avec le mur, false sinon
     */
    public boolean collidesWith(Wall wall) {
        float planeRight = getX() + getWidth();
        float planeBottom = getY() + getHeight();
        float wallRight = wall.getX() + wall.getWidth();
        float wallBottom = wall.getY() + wall.getHeight();

        return planeRight >= wall.getX() && getX() <= wallRight && planeBottom >= wall.getY() && getY() <= wallBottom;
    }

    /**
     * Vérifie si l'avion entre en collision avec un zeppelin.
     *
     * @param zeppelin Zeppelin à vérifier
     * @return true si l'avion entre en collision avec le zeppelin, false sinon
     */
    public boolean collidesWith(Zeppelin zeppelin) {
        float planeRight = getX() + getWidth();
        float planeBottom = getY() + getHeight();
        float zeppelinRight = zeppelin.getX() + zeppelin.getWidth();
        float zeppelinBottom = zeppelin.getY() + zeppelin.getHeight();

        return planeRight >= zeppelin.getX() && getX() <= zeppelinRight && planeBottom >= zeppelin.getY() && getY() <= zeppelinBottom;
    }

    /**
     * Vérifie si l'avion entre en collision avec un autre avion.
     *
     * @param other Autre avion à vérifier
     * @return true si l'avion entre en collision avec l'autre avion, false sinon
     */
    public boolean collidesWith(Plane other) {
        float planeRight = getX() + getWidth();
        float planeBottom = getY() + getHeight();
        float otherRight = other.getX() + other.getWidth();
        float otherBottom = other.getY() + other.getHeight();

        return planeRight >= other.getX() && getX() <= otherRight && planeBottom >= other.getY() && getY() <= otherBottom;
    }

  /**
     * Vérifie si l'avion entre en collision avec un projectile.
     *
     * @param projectile Projectile à vérifier
     * @return true si l'avion entre en collision avec le projectile, false sinon
     */
    public boolean collidesWith(Projectile projectile) {
        float planeRight = getX() + getWidth();
        float planeBottom = getY() + getHeight();

        return planeRight >= projectile.getX() && getX() <= projectile.getX() && planeBottom >= projectile.getY() && getY() <= projectile.getY();
    }

    /**
     * Vérifie si l'avion entre en collision avec un mur.
     * Cette méthode appelle la méthode collidesWith(Wall).
     *
     * @param wall Mur à vérifier
     * @return true si l'avion entre en collision avec le mur, false sinon
     */
    public boolean checkCollision(Wall wall) {
        return collidesWith(wall);
    }

    /**
     * Vérifie si l'avion entre en collision avec un zeppelin.
     * Cette méthode appelle la méthode collidesWith(Zeppelin).
     *
     * @param zeppelin Zeppelin à vérifier
     * @return true si l'avion entre en collision avec le zeppelin, false sinon
     */
    public boolean checkCollision(Zeppelin zeppelin) {
        return collidesWith(zeppelin);
    }

    /**
     * Vérifie si l'avion entre en collision avec un autre avion.
     * Cette méthode appelle la méthode collidesWith(Plane).
     *
     * @param other Autre avion à vérifier
     * @return true si l'avion entre en collision avec l'autre avion, false sinon
     */
    public boolean checkCollision(Plane other) {
        return collidesWith(other);
    }
}