package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.test.game.graphics.Wall;
import com.test.game.graphics.Zeppelin;

public abstract class Plane {

    private float x;           
    private float y;            
    private final int width; 
    private final int height;
    private Texture texture;

    private boolean gameOver = false;

    // Constructeur du Plane
    public Plane(float x, float y, int width, int height) {
        setX(x);
        setY(y);
        this.width = width;
        this.height = height;
        setTexture(new Texture(Gdx.files.internal("Planes/red_baron.png")));
    }

    // Getters and Setters
    public float getX() {
        return x;
    }

    public final void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public final void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Texture getTexture() {
        return texture;
    }

    public final void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean getGameOver() {
        return gameOver;
    }
    
    // Utilisez SpriteBatch pour dessiner la texture des avions
    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    }

    // Encore en teste pour les ennemies c'est des carres pour le moment 
    public void draw(ShapeRenderer shape) {
        shape.rect(getX(), getY(), getWidth(), getHeight());
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

    // Verification des collisions entre l'avion et le décore 
    public boolean collidesWith(Wall wall) {
        return (getX() + getWidth() >= wall.getX()) && (getX() <= (wall.getX() + wall.getWidth())) && (getY() + getHeight() >= wall.getY())
                && (getY() <= (wall.getY() + wall.getHeight()));
    }

    // Verification des collisions entre l'avion et le décore 
    public boolean collidesWith(Zeppelin zeppelin) {
        return (getX() + getWidth() >= zeppelin.getX()) && (getX() <= (zeppelin.getX() + zeppelin.getWidth())) && (getY() + getHeight() >= zeppelin.getY()) && (getY() <= (zeppelin.getY() + zeppelin.getHeight()));
    }

    // verification des collisions avec le Wall
    public boolean checkCollision(Wall wall) {
        if (collidesWith(wall)) {
            return true;
        }
        return false;
    }

    // verification des collisions avec les zeppelins
    public boolean checkCollision(Zeppelin zeppelins) {
        if (collidesWith(zeppelins)) {
            return true;
        }
        return false;
    }
}
