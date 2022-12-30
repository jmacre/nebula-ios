package com.mygdx.NEBULA;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

import static com.mygdx.NEBULA.ItemDrop.HOURGLASS_SPEED_MULTIPLIER;

public class Bullet extends GameElements {
    public Sprite missileSprite = new Sprite();
    public Sprite bulletSprite = new Sprite();
    Animation<TextureRegion> missileAnimation;

    public static final float SPEED = SCREEN_HEIGHT/1.56f;

    int BULLET_X, BULLET_Y;
    float stateTime = 0f;

    private boolean isMissile = false;
    private boolean isRapidFire = false;
    private boolean isSpreadFire = false;
    private boolean isLeftSpread = false;
    private boolean isRightSpread = false;

    Collision rect;

    public Bullet(){
    }
    public void create(int BULLET_X, boolean isMissile, boolean isRapidFire, boolean isSpreadFire, boolean isLeftSpread, boolean isRightSpread, boolean isHourglass, Assets assets){
        this.BULLET_X = BULLET_X;
        this.BULLET_Y = (int) (SHIP_Y + SHIP_HEIGHT - BULLET_HEIGHT/2f);
        this.isMissile = isMissile;
        this.isRapidFire = isRapidFire;

        this.isSpreadFire = isSpreadFire;
        this.isLeftSpread = isLeftSpread;
        this.isRightSpread = isRightSpread;

        if(isMissile) {
            this.rect = new Collision(BULLET_X - MISSILE_WIDTH, BULLET_Y, MISSILE_WIDTH, MISSILE_HEIGHT);
            missileSprite.setTexture(assets.assetManager.get(Assets.missile_ss,Texture.class));
            missileSprite.setSize(MISSILE_WIDTH, MISSILE_HEIGHT);
            missileAnimation = Anim.createAnimation(missileSprite, 4, Anim.DEFAULT_FRAME_DURATION*1.5f);
        }
        else if(isRapidFire){
            bulletSprite.setTexture(assets.assetManager.get(Assets.bullet_blue,Texture.class));
        }
        else if(isHourglass){
            this.rect = new Collision(BULLET_X, BULLET_Y, BULLET_WIDTH, BULLET_HEIGHT);
            bulletSprite.setTexture(assets.assetManager.get(Assets.bullet_purple,Texture.class));
        }

        else if (isSpreadFire){
            bulletSprite.setTexture(assets.assetManager.get(Assets.bullet_green,Texture.class));
        }
        else{
            bulletSprite.setTexture(assets.assetManager.get(Assets.bullet_yellow,Texture.class));
        }

        if(!isMissile) {
            this.rect = new Collision(BULLET_X, BULLET_Y, BULLET_WIDTH, BULLET_HEIGHT);
            bulletSprite.setSize(BULLET_WIDTH, BULLET_HEIGHT);
        }
    }


    public void update(float delta, boolean isHourglass, boolean isSpreadFire, boolean isLeftSpread, boolean isRightSpread){
        if(isSpreadFire) {
            if (isLeftSpread) {
                BULLET_X -= (int)((SPEED * delta) * .15f);
            }
            if (isRightSpread) {
                BULLET_X += (int)((SPEED * delta) * .15f);
            }
        }

        if(isMissile){
            BULLET_Y += (SPEED * delta) * .75f;
        }
        else if(isHourglass) {
            BULLET_Y += (SPEED * delta) * .75f * HOURGLASS_SPEED_MULTIPLIER;
        }

        if(!isHourglass && !isMissile) {
            BULLET_Y += SPEED * delta;
        }

        rect.move(this.BULLET_X, this.BULLET_Y);

        if(bulletSprite != null) {
            bulletSprite.setPosition(BULLET_X, BULLET_Y);
        }
    }

    public int getBulletY(){
        return BULLET_Y;
    }

    public int getBulletX(){
        return BULLET_X;
    }

    public static float getBulletWidth() {
        return BULLET_WIDTH;
    }

    public static float getBulletHeight() {
        return BULLET_HEIGHT;
    }

    public void render (SpriteBatch batch) {
        if(BULLET_Y < SCREEN_HEIGHT)
            bulletSprite.draw(batch);
    }

    public void render (Anim missileAnim, float delta, float width, float height, SpriteBatch batch) {
        stateTime += delta;
        if(BULLET_Y < SCREEN_HEIGHT)
            missileAnim.drawAnim(missileAnimation, stateTime, BULLET_X, BULLET_Y, width, height, true, batch);
    }

    public Collision getCollision() {
        return rect;
    }

    public boolean isMissile(){
        return isMissile;
    }
    public boolean isRapidFire() {
        return isRapidFire;
    }
    public boolean isSpreadFire(){
        return isSpreadFire;
    }

    public boolean isLeftSpread(){
        return isLeftSpread;
    }
    public boolean isRightSpread(){
        return isRightSpread;
    }
}

