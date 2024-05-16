package com.test.game.planes;


public class Enemy0 extends Plane {
    float Xspeed;// vitesse de mouvment sur l'axe x(l'avion ne bouger que sur une ligne droite)
    // et qui bougent de la gauche vers la droite

    public Enemy0(int Levelx, int Levely, int height, int width, float vitesse) {
        super(Levelx, Levely, height, width);
        Xspeed = vitesse;
        // texture = new
    }

    @Override
    public void update() {
        this.x0 = x0 - Xspeed;
    }

}
