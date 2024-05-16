
package com.test.game.planes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.test.game.graphics.Wall;

public class Plane {
    float x0; // position du plan x
    int y0; // position sur le plan y
    int width; // largeur
    int height; // hauteur
    public Texture texture;
    //Color color = Color.WHITE;
    int nb_bom = 0;
    private boolean gameOver = false;

    public Plane(int a, int b, int c, int d) {
        this.x0 = a;
        this.y0 = b;
        this.width = c;
        this.height = d;
        this.texture = new Texture(Gdx.files.internal("Planes/red_baron.png"));
    }

    // Utilisez SpriteBatch pour dessiner la texture
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x0, y0, width, height);
    }

    public void update() {
        // Mise à jour de la position
        this.x0 = Gdx.input.getX() - (width / 2);
        this.y0 = (Gdx.graphics.getHeight() - Gdx.input.getY()) - (height / 2);
        // Reste du code pour empêcher de sortir de l'écran...
        if (Gdx.graphics.getWidth() < Gdx.input.getX() + (width / 2)) {
            this.x0 = Gdx.graphics.getWidth() - (width);
        }
        if (Gdx.input.getX() - (width / 2) < 0) {
            this.x0 = 0;
        }
        // pour ne pas sortir sur l'écran sur l axe des y
        if ((Gdx.graphics.getHeight() - Gdx.input.getY()) < height / 2) {
            this.y0 = 0;
        }
        if ((Gdx.graphics.getHeight() - Gdx.input.getY()) + (height / 2) > Gdx.graphics.getHeight()) {
            this.y0 = Gdx.graphics.getHeight() - height;
        }
    }

    public boolean collidesWith(Wall wall) {
        return (x0 + width >= wall.bounds.x) && (x0 <= (wall.bounds.x + wall.getWidth())) && (y0 + height >= wall.bounds.y)
                && (y0 <= (wall.bounds.y + wall.getHeight()));
    }

    public void checkCollision(Wall wall) {
        if (collidesWith(wall)) {
            setGameOver(true);
           } 
    }

    public boolean collidesWith(Zeppelin zeppelin) {
        return (x0 + width >= zeppelin.getX()) && (x0 <= (zeppelin.getX() + zeppelin.getWidth())) && (y0 + height >= zeppelin.getY()) && (y0 <= (zeppelin.getY() + zeppelin.getHeight()));
    }
    
    public void checkCollision(Zeppelin zeppelins) {
       
            if (collidesWith(zeppelins)) {
                System.out.println("bom!" + ++nb_bom);
                setGameOver(true);
                // Ajoutez ici le code à exécuter en cas de collision avec un zeppelin
            }
        
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver; 
    }
    public boolean getGameOver(){
        return gameOver;
    }
    

    public void draw(ShapeRenderer shape) {
        shape.rect(x0, y0, width, height);
        // shape.setColor(Color.RED);
    }
}
