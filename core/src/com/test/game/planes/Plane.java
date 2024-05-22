package com.test.game.planes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.test.game.graphics.Wall;
import com.test.game.graphics.Zeppelin;
import com.test.game.shoots.Projectile;

public class Plane {

    protected float x;
    protected float y;
    private final int width;
    private final int height;
    private Texture texture;
    private boolean isFireHit = false;// cette variable permet de savoir si l'avion est touche par un projectile
    private boolean deadByFireHit = false;// mort par projectile du joueur ou pas
    private boolean gameOver = false;
    private int hp;
    private float speed;

    // Constructeur du Plane
    public Plane(float x, float y, int width, int height, Texture texture) {
        setX(x);
        setY(y);
        this.width = width;
        this.height = height;
        setTexture(texture);
    }

    public void setIsFireHit(boolean etat) {
        this.isFireHit = etat;
    }

    public boolean getIsFireHit() {
        return isFireHit;
    }

    public void setIsDeadByFireHit(boolean etat) {
        this.deadByFireHit = etat;
    }

    public boolean getIsDeadByFireHit() {
        return isFireHit;
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

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
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

    }

    // Verification des collisions entre l'avion et le décore
    public boolean collidesWith(Wall wall) {
        return (getX() + getWidth() >= wall.getX()) && (getX() <= (wall.getX() + wall.getWidth()))
                && (getY() + getHeight() >= wall.getY())
                && (getY() <= (wall.getY() + wall.getHeight()));
    }

    public boolean collidesWith(Plane avion) {
        return (getX() + getWidth() >= avion.getX()) && (getX() <= (avion.getX() + avion.getWidth()))
                && (getY() + getHeight() >= avion.getY())
                && (getY() <= (avion.getY() + avion.getHeight()));
    }

    // Verification des collisions entre l'avion et le décore
    public boolean collidesWith(Zeppelin zeppelin) {
        return (getX() + getWidth() >= zeppelin.getX()) && (getX() <= (zeppelin.getX() + zeppelin.getWidth()))
                && (getY() + getHeight() >= zeppelin.getY()) && (getY() <= (zeppelin.getY() + zeppelin.getHeight()));
    }

    public boolean collidesWith(Projectile projectile) {
        return (getX() + getWidth() >= projectile.getX()) && (getX() <= (projectile.getX()))
                && (getY() + getHeight() >= projectile.getY()) && (getY() <= (projectile.getY()));
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

    public boolean checkCollision(Plane avion) {
        if (collidesWith(avion)) {
            return true;
        }
        return false;
    }

     // Check and avoid collision with the ground (walls)
     public void avoidGroundCollision(Wall ground) {
        if (collidesWith(ground)) {
            setY(getY() + 5); // Adjust this value as needed for how quickly the plane should rise
        }
    }

     // Getters et setters
     public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}