package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.buffs.Buff;
import com.test.game.shoots.Projectile;

/**
 * Cette classe représente l'avion Rouge dans le jeu.
 */
public class Red extends Plane {

    private final static int RED_WIDTH = 78;
    private final static int RED_HEIGHT = 30;
    private final static Texture TEXTURE_RED = new Texture(Gdx.files.internal("Planes/red_baron.png"));
    private final static int HEALTH_SPREAD_VARIABLE = 10;
    private static int healthSpreadX;
    public Texture healthTexture;
    public Sound sonTire = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Shot.mp3"));

    /**
     * Construit un objet avion Rouge avec une position donnée.
     *
     * @param x La coordonnée x de l'avion.
     * @param y La coordonnée y de l'avion.
     */
    public Red(int x, int y) {
        super(x, y, RED_WIDTH, RED_HEIGHT, TEXTURE_RED);
        healthTexture = new Texture(Gdx.files.internal("Ui/health_shape.png"));
        setHp(5);
    }

    /**
     * Met à jour la position de l'avion Rouge en fonction de l'entrée.
     */
    @Override
    public void update() {
        setX(Gdx.input.getX() - (getWidth() / 2));
        setY((Gdx.graphics.getHeight() - Gdx.input.getY()) - (getHeight() / 2));

        if (Gdx.graphics.getWidth() < Gdx.input.getX() + (getWidth() / 2)) {
            setX(Gdx.graphics.getWidth() - getWidth());
        }
        if (Gdx.input.getX() - (getWidth() / 2) < 0) {
            setX(0);
        }
        if ((Gdx.graphics.getHeight() - Gdx.input.getY()) < getHeight() / 2) {
            setY(0);
        }
        if ((Gdx.graphics.getHeight() - Gdx.input.getY()) + (getHeight() / 2) > Gdx.graphics.getHeight()) {
            setY(Gdx.graphics.getHeight() - getHeight());
        }
    }

    /**
     * Dessine la santé de l'avion Rouge à l'écran.
     *
     * @param batch Le SpriteBatch pour dessiner.
     */
    public void drawHealth(SpriteBatch batch) {
        for (int i = 0; i < getHp(); i++) {
            batch.draw(healthTexture, healthSpreadX, Gdx.graphics.getHeight() - 20, 20, 20);
            healthSpreadX += HEALTH_SPREAD_VARIABLE;
        }
        healthSpreadX = 0;
    }

    /**
     * Définit l'état de fin de jeu.
     *
     * @param gameOver Vrai si le jeu est terminé, faux sinon.
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Obtient l'état de fin de jeu.
     *
     * @return Vrai si le jeu est terminé, faux sinon.
     */
    public boolean getGameOver() {
        return gameOver;
    }

    /**
     * Vérifie la collision avec un objet buff.
     *
     * @param buffItem L'objet buff à vérifier.
     * @return Vrai si l'avion entre en collision avec l'objet buff, faux sinon.
     */
    public boolean collidesWithBuff(Buff buffItem) {
        return (getX() + getWidth() >= buffItem.getX()) && (getX() <= (buffItem.getX() + buffItem.getWidth()))
                && (getY() + getHeight() >= buffItem.getY())
                && (getY() <= (buffItem.getY() + buffItem.getHeight()));
    }

    /**
     * Vérifie la collision avec un projectile.
     *
     * @param projectile Le projectile à vérifier.
     * @return Vrai si l'avion entre en collision avec le projectile, faux sinon.
     */
    public boolean collidesWithProj(Projectile projectile) {
        return (getX() + getWidth() >= projectile.getX()) && (getX() <= (projectile.getX() + projectile.getWidth()))
                && (getY() + getHeight() >= projectile.getY())
                && (getY() <= (projectile.getY() + projectile.getHeight()));
    }
}
