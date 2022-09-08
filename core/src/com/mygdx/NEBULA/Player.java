package com.mygdx.NEBULA;

public class Player {
    private final Collision collision;

    public Player() {
        this.collision = new Collision(true, MainGame.SHIP_X, MainGame.SHIP_Y, MainGame.SHIP_WIDTH, GameElements.SHIP_HEIGHT);
    }

    public void update(){
        collision.move(true, MainGame.SHIP_X, MainGame.SHIP_Y);
    }

    public Collision getCollision() {
        return collision;
    }

    public static float getWidth(){
        return MainGame.SHIP_WIDTH;
    }
    public static float getHeight(){
        return GameElements.SHIP_HEIGHT;
    }
}
