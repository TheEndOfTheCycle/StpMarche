package com.test.game.planes;


public class French extends Plane {

    float speed;

    // Constructeur de la classe
    public French(int x, int y, int height, int width, float speed) {
        super(x, y, height, width);
        setSpeed(speed);
    }

    // Méthode pour mettre à jour la position de l'objet French
    @Override
    public void update() {
        setX(getX() - getSpeed());
    }

    // getters et setters
    private float getSpeed(){
        return speed;
    }

    private void setSpeed(float speed){
        this.speed = speed;
    }

}
