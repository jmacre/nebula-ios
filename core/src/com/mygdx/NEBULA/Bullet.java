package com.mygdx.NEBULA;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.mygdx.NEBULA.ItemDrop.HOURGLASS_SPEED_MULTIPLIER;

public class Bullet extends GameElements {
    public Sprite bulletSprite, missileSheet;
    Animation<TextureRegion> missileAnimation;

    public static final float SPEED = SCREEN_HEIGHT/1.56f;

    public static float BULLET_WIDTH = SHIP_WIDTH*(2/27f);
    public static float BULLET_HEIGHT = BULLET_WIDTH*4f;
    public static float MISSILE_WIDTH = SHIP_WIDTH;
    public static float MISSILE_HEIGHT = MISSILE_WIDTH * (50f/27f);

    float BULLET_X, BULLET_Y;
    float stateTime = 0f;

    private final boolean isMissile;
    public boolean remove = false;
    Collision rect;

    public Bullet (float BULLET_X, boolean isMissile, boolean isRapidFire, Assets assets) {
        this.BULLET_X = BULLET_X;
        this.BULLET_Y = SHIP_Y + SHIP_HEIGHT - BULLET_HEIGHT/2f;
        this.isMissile = isMissile;

        if(isMissile) {
            this.rect = new Collision(BULLET_X, BULLET_Y, MISSILE_WIDTH, MISSILE_HEIGHT);
            missileSheet = new Sprite(assets.assetManager.get(Assets.missile_ss, Texture.class));
            missileSheet.setSize(MISSILE_WIDTH, MISSILE_HEIGHT);
            missileAnimation = Anim.createAnimation(missileSheet, 4, Anim.DEFAULT_FRAME_DURATION*1.5f);
        }
        else if(isRapidFire){
            this.rect = new Collision(BULLET_X, BULLET_Y, BULLET_WIDTH, BULLET_HEIGHT);
            bulletSprite = new Sprite(assets.assetManager.get(Assets.bullet_blue, Texture.class));
            bulletSprite.setSize(BULLET_WIDTH, BULLET_HEIGHT);
        }

        else {
            this.rect = new Collision(BULLET_X, BULLET_Y, BULLET_WIDTH, BULLET_HEIGHT);
            bulletSprite = new Sprite(assets.assetManager.get(Assets.bullet_yellow, Texture.class));
            bulletSprite.setSize(BULLET_WIDTH, BULLET_HEIGHT);
        }
    }


    public void update(float delta, boolean isHourglass){
        if(isMissile){
            BULLET_Y += (SPEED * delta) * .75f;
        }
        else if(!isHourglass)
            BULLET_Y += SPEED * delta;

        else {
            BULLET_Y += (SPEED * delta) * .75f  * HOURGLASS_SPEED_MULTIPLIER;;
        }

        if (BULLET_Y > SCREEN_HEIGHT)
            remove = true;

        rect.move(this.BULLET_X, this.BULLET_Y);

        if(bulletSprite != null) {
            bulletSprite.setPosition(BULLET_X, BULLET_Y);
        }
    }

    public float getBulletY(){
        return BULLET_Y;
    }

    public float getBulletX(){
        return BULLET_X;
    }

    public static float getBulletWidth() {
        return BULLET_WIDTH;
    }

    public static float getBulletHeight() {
        return BULLET_HEIGHT;
    }

    public void render (SpriteBatch batch) {
        bulletSprite.draw(batch);
    }

    public void render (Anim missileAnim, float delta, float width, float height, SpriteBatch batch) {
        stateTime += delta / 6;
        missileAnim.drawAnim(missileAnimation, stateTime, BULLET_X, BULLET_Y, width, height, true, batch);
    }

    public Collision getCollision() {
        return rect;
    }

    public boolean isMissile(){
        return isMissile;
    }
}

