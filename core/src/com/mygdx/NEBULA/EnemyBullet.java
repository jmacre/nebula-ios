package com.mygdx.NEBULA;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.NEBULA.GameElements.ENEMY_SHIP_WIDTH;
import static com.mygdx.NEBULA.GameElements.SCREEN_HEIGHT;


public class EnemyBullet {
    public Sprite enemyBulletSprite;
    Assets assets;
    public static final float SPEED = SCREEN_HEIGHT/1.56f;

    float ENEMY_BULLET_X, ENEMY_BULLET_Y;
    public static float ENEMY_BULLET_WIDTH = ENEMY_SHIP_WIDTH * (2/31f);
    public static float ENEMY_BULLET_HEIGHT = ENEMY_BULLET_WIDTH * 4f;
    public static float blueShipBulletThreshold = 1.25f;
    public static float greenShipBulletThreshold = 1.25f;
    public static float redShipBulletThreshold = 1.25f;
    public static float purpleShipBulletThreshold = 1.5f;
    public static float whiteShipBulletThreshold = 1.6f;

    public boolean remove = false;
    int bulletColor;
    Collision rect;

    public EnemyBullet(){}

    public void create (float ENEMY_BULLET_X, float ENEMY_BULLET_Y, int bulletColor, Assets assets) {
        this.assets = assets;
        this.ENEMY_BULLET_X = ENEMY_BULLET_X;
        this.ENEMY_BULLET_Y = ENEMY_BULLET_Y;

        this.bulletColor = bulletColor;

        this.rect = new Collision(ENEMY_BULLET_X, ENEMY_BULLET_Y, ENEMY_BULLET_WIDTH, ENEMY_BULLET_HEIGHT);
        enemyBulletSprite = new Sprite(assets.assetManager.get(Assets.bullet_red, Texture.class));
        enemyBulletSprite.setSize(ENEMY_BULLET_WIDTH, ENEMY_BULLET_HEIGHT);

    }

    public void update(float delta){
        ENEMY_BULLET_Y -= SPEED * delta;
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
        enemyBulletSprite.draw(batch);
    }

    public Collision getCollision() {
        return rect;
    }
}
