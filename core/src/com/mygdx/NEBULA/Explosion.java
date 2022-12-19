package com.mygdx.NEBULA;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.naming.Context;

public class Explosion {
    Animation<TextureRegion> explosionAnimation;
    public static Sprite explosionSheet;
    int x,y;
    float stateTime;
    float size;

    public boolean remove = false;

    public static void createExplosionSprite(Assets assets){
        explosionSheet = new Sprite(assets.assetManager.get(Assets.explosion, Texture.class));
    }
    public void create(int x, int y, float size){

        this.x = x;
        this.y = y;
        this.size = size;
        stateTime = 0;
        explosionAnimation = Anim.createAnimation(explosionSheet, 4, 0.075f);

    }

    public void update(float delta){
        stateTime += 2.5f*delta;
        if (explosionAnimation.isAnimationFinished(stateTime))
            remove = true;
    }

    public void render (Anim explosionAnim, SpriteBatch batch) {
        if(y < MainGame.SCREEN_HEIGHT)
            explosionAnim.drawAnim(explosionAnimation, stateTime, x, y, size, size, false, batch);

    }
    public float getStateTime(){
        return stateTime;
    }
}
