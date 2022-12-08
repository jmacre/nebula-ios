package com.mygdx.NEBULA;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Anim{
    public static float DEFAULT_FRAME_DURATION = 0.0125f;
    TextureRegion currentFrame;
    Sprite sprite;

    public void drawAnim(Animation<TextureRegion> animation, float animSpeed, float x, float y, float width, float height, boolean looping, SpriteBatch batch, boolean hurt){
        currentFrame = animation.getKeyFrame(animSpeed, looping);
        sprite = new Sprite(currentFrame);
        sprite.setSize(width, height);
        sprite.setPosition(x,y);
        if(hurt) {
            sprite.setColor(Color.SLATE);
        }
        sprite.draw(batch);
    }
    public void drawAnim(Animation<TextureRegion> animation, float animSpeed, float x, float y, float width, float height, boolean looping, SpriteBatch batch){
        currentFrame = animation.getKeyFrame(animSpeed, looping);
        sprite = new Sprite(currentFrame);
        sprite.setSize(width, height);
        sprite.setPosition(x,y);
        sprite.draw(batch);
    }

    public static Animation<TextureRegion> createAnimation(Sprite sprite, int frameCount, float frameDuration){
        TextureRegion[][] tmp = TextureRegion.split(sprite.getTexture(), sprite.getTexture().getWidth() / frameCount, sprite.getTexture().getHeight());
        TextureRegion [] enemyFrames = new TextureRegion[frameCount];
        int index = 0;


        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < frameCount; j++) {
                enemyFrames[index++] = tmp[i][j];
            }
        }
        return new Animation<>(frameDuration, enemyFrames);
    }
}
