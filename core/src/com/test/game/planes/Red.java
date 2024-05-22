package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.buffs.Buff;

public class Red extends Plane {

    private final static int RED_WIDTH = 78;
    private final static int RED_HEIGHT = 30;
    private final static Texture TEXTURE_RED = new Texture(Gdx.files.internal("Planes/red_baron.png"));
    private final static int HEALTH_SPREAD_VARAIBLE = 10;
    private static int health_spread_x;
    public Texture HealthTexture;
    public Sound sonTire = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Shot.mp3"));
    public Sound sonBomb = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/Bomb.mp3"));

    // Constructeur de la classe
    public Red(int x, int y) {
        super(x, y, RED_WIDTH, RED_HEIGHT, TEXTURE_RED);
        HealthTexture = new Texture(Gdx.files.internal("Ui/health_shape.png"));
        setHp(3);
    }

    public void update() {
        // Mise à jour de la position
        setX(Gdx.input.getX() - (getWidth() / 2));
        setY((Gdx.graphics.getHeight() - Gdx.input.getY()) - (getHeight() / 2));

        // Pour ne pas sortir de l'écran sur l'axe des x
        if (Gdx.graphics.getWidth() < Gdx.input.getX() + (getWidth() / 2)) {
            setX(Gdx.graphics.getWidth() - getWidth());
        }
        if (Gdx.input.getX() - (getWidth() / 2) < 0) {
            setX(0);
        }
        // Pour ne pas sortir de l'écran sur l'axe des y
        if ((Gdx.graphics.getHeight() - Gdx.input.getY()) < getHeight() / 2) {
            setY(0);
        }
        if ((Gdx.graphics.getHeight() - Gdx.input.getY()) + (getHeight() / 2) > Gdx.graphics.getHeight()) {
            setY(Gdx.graphics.getHeight() - getHeight());
        }
    }

    public void drawHealth(SpriteBatch batch) {
        for (int i = 0; i < getHp(); i++) {

            batch.draw(HealthTexture, health_spread_x, Gdx.graphics.getHeight() - 20, 20, 20);
            health_spread_x += HEALTH_SPREAD_VARAIBLE;
        }
        health_spread_x = 0;// on remet l'espacment entre les coeurs a zero,sinon les coeurs continuront
                            // avancer a chaque render
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public boolean collidesWithBuff(Buff buffitem) {

        return (getX() + getWidth() >= buffitem.getX()) && (getX() <= (buffitem.getX() + buffitem.getWidth()))
                && (getY() + getHeight() >= buffitem.getY())
                && (getY() <= (buffitem.getY() + buffitem.getHeight()));

    }
}
