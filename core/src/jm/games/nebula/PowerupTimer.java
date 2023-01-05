package jm.games.nebula;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PowerupTimer {
    Animation<TextureRegion> powerUpAnimation;
    public static Sprite powerUpTimerSheet;
    float x,y;
    float stateTime;
    float size;

    public boolean remove = false;

    public static void createPowerupSprite(Assets assets){
        powerUpTimerSheet = new Sprite(assets.assetManager.get(Assets.powerup_timer, Texture.class));
    }
    public void create(float x, float y, float size, float totalTimerLength){
        this.x = x;
        this.y = y;
        this.size = size;
        stateTime = 0;
        powerUpAnimation = Anim.createAnimation(powerUpTimerSheet, 9, Math.abs(totalTimerLength)/9f);

    }

    public void update(float delta){
        stateTime += delta;
        if (powerUpAnimation.isAnimationFinished(stateTime))
            remove = true;
    }

    public void render (Anim explosionAnim, SpriteBatch batch) {

        if(y < MainGame.SCREEN_HEIGHT)
            explosionAnim.drawAnim(powerUpAnimation, stateTime, x, y, size, size, false, batch);

    }
    public float getStateTime(){
        return stateTime;
    }

}
