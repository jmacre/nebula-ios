package com.mygdx.NEBULA;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.mygdx.NEBULA.GameElements.SCREEN_HEIGHT;
import static com.mygdx.NEBULA.GameElements.SCREEN_WIDTH;
import static com.mygdx.NEBULA.ItemDrop.HOURGLASS_SPEED_MULTIPLIER;


public class Background {
    Assets assets;
    public static final float DEFAULT_SPEED = SCREEN_HEIGHT/3.12f;

    Animation<TextureRegion> starsAnimation;
    Sprite backgroundSprite, defaultBackgroundSprite, blueBackgroundSprite, greenBackgroundSprite;
    Sprite redBackgroundSprite, purpleBackgroundSprite, blackBackgroundSprite, starsSheet;
    float background_y1, background_y2, stars_y1, stars_y2;

    Sprite transitionSprite;
    float transitionOpacity = 0f;
    float stateTime = 0f;
    float titleScreenSpeedModifier = 0f;

    float hourglassMultiplier = 1;

    float speed;
    float goalSpeed;

    public Background(Assets assets){
        this.assets = assets;

        backgroundSprite = new Sprite(assets.assetManager.get(Assets.default_background, Texture.class));
        blueBackgroundSprite = new Sprite(assets.assetManager.get(Assets.blue_background, Texture.class));
        greenBackgroundSprite = new Sprite(assets.assetManager.get(Assets.green_background, Texture.class));
        redBackgroundSprite = new Sprite(assets.assetManager.get(Assets.red_background, Texture.class));
        purpleBackgroundSprite = new Sprite(assets.assetManager.get(Assets.purple_background, Texture.class));
        blackBackgroundSprite = new Sprite(assets.assetManager.get(Assets.black_background, Texture.class));

        backgroundSprite.setBounds(0,0, SCREEN_WIDTH, SCREEN_WIDTH*12);
        transitionSprite = backgroundSprite;

        defaultBackgroundSprite = backgroundSprite;
        background_y1 = 0;
        background_y2 = backgroundSprite.getHeight();

        starsSheet = new Sprite(assets.assetManager.get(Assets.stars, Texture.class));

        starsSheet.setBounds(0,0, SCREEN_WIDTH, SCREEN_WIDTH*12);

        stars_y1 = 0;
        stars_y2 = starsSheet.getHeight();

        speed = DEFAULT_SPEED;

        goalSpeed = SCREEN_HEIGHT/1.78f;
        starsAnimation = Anim.createAnimation(starsSheet, 4, 0.1f);
    }

    public void updateAndRender(float delta, boolean isAlive, boolean isHourglass, int score, Anim starsAnim, SpriteBatch batch, boolean constantSpeed, boolean isResettingScreen, boolean stayStill, boolean isOnTitleScreen){
        if(isHourglass){
            hourglassMultiplier = HOURGLASS_SPEED_MULTIPLIER;
        }
        else{
            hourglassMultiplier = 1;
        }

        if(isOnTitleScreen){
            titleScreenSpeedModifier = .33f;
        }
        else{
            titleScreenSpeedModifier = 1f;
        }

        if(constantSpeed){
            if(backgroundSprite.getTexture() != assets.assetManager.get(Assets.default_background)) {
                backgroundSprite = defaultBackgroundSprite;
            }
        }

        if(transitionSprite != null && transitionOpacity >= 1f) {
            backgroundSprite = transitionSprite;
            transitionOpacity = 0f;
        }

        if (speed < goalSpeed && !constantSpeed && !isResettingScreen) {
            speed += 7.5 * delta * hourglassMultiplier;
        }
        else if(isResettingScreen && speed > DEFAULT_SPEED){
            speed -= 1000 * delta * hourglassMultiplier;
        }

        else if(constantSpeed) {
            speed = DEFAULT_SPEED;
        }
        if(!stayStill) {
            background_y1 -= speed*.7 * delta * titleScreenSpeedModifier * hourglassMultiplier;
            background_y2 -= speed*.7 * delta * titleScreenSpeedModifier * hourglassMultiplier;

            stars_y1 -= speed * delta * titleScreenSpeedModifier * hourglassMultiplier;
            stars_y2 -= speed * delta * titleScreenSpeedModifier * hourglassMultiplier;
        }
        if (background_y1 + backgroundSprite.getHeight()  <= 0) {
            background_y1 = background_y2 + backgroundSprite.getHeight();
        }

        if (background_y2 + backgroundSprite.getHeight()  <= 0) {
            background_y2 = background_y1 + backgroundSprite.getHeight();
        }

        if (stars_y1 + starsSheet.getHeight()  <= 0) {
            stars_y1 = stars_y2 + starsSheet.getHeight();
        }

        if (stars_y2 + starsSheet.getHeight()  <= 0) {
            stars_y2 = stars_y1 + starsSheet.getHeight();
        }

        backgroundSprite.setSize(SCREEN_WIDTH, backgroundSprite.getHeight());
        starsSheet.setSize(SCREEN_WIDTH, starsSheet.getHeight());

        backgroundSprite.setPosition(0, background_y1);
        backgroundSprite.draw(batch);

        backgroundSprite.setPosition(0, background_y2);
        backgroundSprite.draw(batch);

        changeBackgroundColor(delta, score, batch, isAlive);

        if(!isHourglass)
            stateTime += delta / 10 * hourglassMultiplier;

        starsSheet.setPosition(0, stars_y1);
        starsAnim.drawAnim(starsAnimation, stateTime, 0, stars_y1, SCREEN_WIDTH, starsSheet.getHeight(), true,batch);

        starsSheet.setPosition(0, stars_y2);
        starsAnim.drawAnim(starsAnimation, stateTime, 0, stars_y2, SCREEN_WIDTH, starsSheet.getHeight(), true, batch);
    }

    public void changeBackgroundColor(float delta, int score, Batch batch, boolean isAlive){

        if(transitionSprite.getTexture() != backgroundSprite.getTexture() && transitionOpacity < 1){

            transitionSprite.setAlpha(transitionOpacity);
            transitionSprite.setSize(SCREEN_WIDTH, backgroundSprite.getHeight());

            transitionOpacity += 0.2f * delta * hourglassMultiplier;
            transitionSprite.setPosition(0, background_y1);
            transitionSprite.draw(batch);

            transitionSprite.setPosition(0, background_y2);
            transitionSprite.draw(batch);

        }
        else if (score < 100 || !isAlive) {
            transitionSprite = defaultBackgroundSprite;
        }
        else if (score < 1000) {
            transitionSprite = blueBackgroundSprite;
        }
        else if (score < 2000) {
            transitionSprite = greenBackgroundSprite;
        }
        else if (score < 3000) {
            transitionSprite = redBackgroundSprite;
        }
        else if (score < 4000) {
            transitionSprite = purpleBackgroundSprite;
        }
        else{
            transitionSprite = blackBackgroundSprite;

        }
    }
}