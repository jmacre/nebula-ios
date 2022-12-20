package com.mygdx.NEBULA;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.NEBULA.GameElements.ENEMY_SHIP_WIDTH;
import static com.mygdx.NEBULA.GameElements.SCREEN_HEIGHT;


public class EnemyBullet {
    public Sprite enemyBulletSprite;
    Assets assets;
    float speed;
    int ENEMY_BULLET_X, ENEMY_BULLET_Y;

    public static float ENEMY_BULLET_WIDTH = ENEMY_SHIP_WIDTH * (2/31f);
    public static float ENEMY_BULLET_HEIGHT = ENEMY_BULLET_WIDTH * 4f;

    public static float blueShipBulletThreshold = 1.5f;
    public static float greenShipBulletThreshold = 1.4f;
    public static float redShipBulletThreshold = 1.35f;
    public static float purpleShipBulletThreshold = 1.4f;
    public static float whiteShipBulletThreshold = 1.5f;

    public static float blueShipBulletSpeed = SCREEN_HEIGHT/2.25f;
    public static float greenShipBulletSpeed = SCREEN_HEIGHT/2.05f;
    public static float redShipBulletSpeed = SCREEN_HEIGHT/1.85f;
    public static float purpleShipBulletSpeed = SCREEN_HEIGHT/1.7f;
    public static float whiteShipBulletSpeed = SCREEN_HEIGHT/1.55f;

    public boolean remove = false;
    int bulletColor;
    Collision rect;

    public void create (int ENEMY_BULLET_X, int ENEMY_BULLET_Y, float speed, int bulletColor, Assets assets) {
        this.assets = assets;
        this.ENEMY_BULLET_X = (int)ENEMY_BULLET_X;
        this.ENEMY_BULLET_Y = (int)ENEMY_BULLET_Y;
        this.speed = speed;

        this.bulletColor = bulletColor;

        this.rect = new Collision(ENEMY_BULLET_X, ENEMY_BULLET_Y, ENEMY_BULLET_WIDTH, ENEMY_BULLET_HEIGHT);
        enemyBulletSprite = new Sprite(assets.assetManager.get(Assets.bullet_red, Texture.class));
        enemyBulletSprite.setSize(ENEMY_BULLET_WIDTH, ENEMY_BULLET_HEIGHT);

    }

    public void update(float delta){
        ENEMY_BULLET_Y -= (int)(speed * delta);
        if (ENEMY_BULLET_Y < 0){
            remove = true;
        }
        rect.move(ENEMY_BULLET_X, ENEMY_BULLET_Y);
        if(enemyBulletSprite != null) {
            enemyBulletSprite.setPosition(ENEMY_BULLET_X, ENEMY_BULLET_Y);
        }
    }

    public float getY(){
        return ENEMY_BULLET_Y;
    }

    public float getX(){
        return ENEMY_BULLET_X;
    }

    public void render (SpriteBatch batch) {
        if (ENEMY_BULLET_Y + ENEMY_BULLET_HEIGHT> 0) {
            enemyBulletSprite.draw(batch);
        }
    }

    public Collision getCollision() {
        return rect;
    }
}
