package com.test.game.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class MapObjects {

    protected final Rectangle shape;
    protected final Texture texture;

    // Constructeur de la classe
    public MapObjects(float x, float y, float width, float height, Texture texture) {
        this.shape = new Rectangle(x, y, width, height);
        this.texture = texture;
    }

    // Méthode pour dessiner l'objet sur l'écran
    public void draw(SpriteBatch batch) {
        batch.draw(texture, shape.x, shape.y, shape.width, shape.height);
    }

    // ----- getters et setters -----s
    public float getX() {
        return shape.x;
    }

    public float getY() {
        return shape.y;
    }

    public void setX(float x){
        shape.x = x;
    }

    public void setY(float y){
        shape.y = y;
    }   

    public float getWidth() {
        return shape.width;
    }

    public float getHeight() {
        return shape.height;
    }

    public Rectangle getPosition() {
        return shape;
    }

    public void setPosition(float x, float y) {
        shape.setPosition(x, y);
    }
}
