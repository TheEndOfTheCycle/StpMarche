package com.test.game.buffs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.graphics.Wall;

/**
 * La classe Buff représente un objet de jeu qui peut être ramassé par le joueur.
 * Il se déplace horizontalement à travers l'écran et peut entrer en collision avec des murs.
 */
public class Buff {
    // La coordonnée x du buff
    protected float x;
    
    // La coordonnée y du buff
    protected float y;
    
    // La largeur du buff
    private static final int WIDTH = 40;
    
    // La hauteur du buff
    private static final int HEIGHT = 40;
    
    // La texture du buff
    protected Texture texture;
    
    // La vitesse à laquelle le buff se déplace horizontalement
    private final int ITEM_SPEED = 1;

    /**
     * Construit un nouveau Buff avec la position initiale spécifiée.
     *
     * @param Xlevel la coordonnée x initiale du buff
     * @param Ylevel la coordonnée y initiale du buff
     */
    public Buff(int Xlevel, int Ylevel) {
        this.x = Xlevel;
        this.y = Ylevel;
    }

    /**
     * Met à jour la position du buff en le déplaçant vers la gauche de la quantité de la vitesse.
     */
    public void update() {
        setX(getX() - getSpeed());
    }

    /**
     * Retourne la vitesse à laquelle le buff se déplace.
     *
     * @return la vitesse du buff
     */
    private float getSpeed() {
        return ITEM_SPEED;
    }

    /**
     * Retourne la coordonnée x du buff.
     *
     * @return la coordonnée x du buff
     */
    public float getX() {
        return x;
    }

    /**
     * Définit la coordonnée x du buff.
     *
     * @param x la nouvelle coordonnée x du buff
     */
    public final void setX(float x) {
        this.x = x;
    }

    /**
     * Retourne la coordonnée y du buff.
     *
     * @return la coordonnée y du buff
     */
    public float getY() {
        return y;
    }

    /**
     * Définit la coordonnée y du buff.
     *
     * @param y la nouvelle coordonnée y du buff
     */
    public final void setY(float y) {
        this.y = y;
    }

    /**
     * Retourne la largeur du buff.
     *
     * @return la largeur du buff
     */
    public int getWidth() {
        return WIDTH;
    }

    /**
     * Retourne la hauteur du buff.
     *
     * @return la hauteur du buff
     */
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * Retourne la texture du buff.
     *
     * @return la texture du buff
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Définit la texture du buff.
     *
     * @param texture la nouvelle texture du buff
     */
    public final void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * Dessine le buff à l'écran en utilisant le SpriteBatch fourni.
     *
     * @param batch le SpriteBatch utilisé pour dessiner le buff
     */
    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Vérifie si le buff entre en collision avec un mur spécifié.
     *
     * @param wall le mur avec lequel vérifier la collision
     * @return true si le buff entre en collision avec le mur, false sinon
     */
    public boolean collidesWith(Wall wall) {
        return (getX() + getWidth() >= wall.getX()) && (getX() <= (wall.getX() + wall.getWidth()))
                && (getY() + getHeight() >= wall.getY())
                && (getY() <= (wall.getY() + wall.getHeight()));
    }
}
