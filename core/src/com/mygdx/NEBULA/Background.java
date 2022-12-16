package com.mygdx.NEBULA;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import static com.mygdx.NEBULA.GameElements.SCREEN_HEIGHT;
import static com.mygdx.NEBULA.GameElements.SCREEN_WIDTH;
import static com.mygdx.NEBULA.ItemDrop.HOURGLASS_SPEED_MULTIPLIER;


public class Background {
    Assets assets;
    public static final float DEFAULT_SPEED = SCREEN_HEIGHT/4f;

    Animation<TextureRegion> starsFrontAnimation, starsBackAnimation;
    Sprite backgroundSprite, defaultBackgroundSprite, blueBackgroundSprite, greenBackgroundSprite;
    Sprite redBackgroundSprite, purpleBackgroundSprite, blackBackgroundSprite, starsFront, starsBack;
    float background_y1, background_y2, stars_front_y1, stars_front_y2, stars_back_y1, stars_back_y2;

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

        backgroundSprite.setBounds(0,0, SCREEN_WIDTH, SCREEN_WIDTH*6);
        transitionSprite = backgroundSprite;

        defaultBackgroundSprite = backgroundSprite;
        background_y1 = 0;
        background_y2 = backgroundSprite.getHeight();

        starsFront = new Sprite(assets.assetManager.get(Assets.stars_front, Texture.class));
        starsBack = new Sprite(assets.assetManager.get(Assets.stars_back, Texture.class));

        starsFront.setBounds(0,0, SCREEN_WIDTH, SCREEN_WIDTH*12);
        starsBack.setBounds(0,0, SCREEN_WIDTH, SCREEN_WIDTH*12);

        stars_back_y1 = 0;
        stars_back_y2 = starsFront.getHeight();

        stars_front_y1 = 0;
        stars_front_y2 = starsFront.getHeight();

        speed = DEFAULT_SPEED;

        goalSpeed = SCREEN_HEIGHT/1.78f;
        starsFrontAnimation = Anim.createAnimation(starsFront, 4, 0.1f);
        starsBackAnimation = Anim.createAnimation(starsBack, 4, 0.1f);

    }

    public void updateAndRender(float delta, boolean isAlive, boolean isHourglass, int score, Anim starsAnimFront, Anim starsAnimBack, SpriteBatch batch, boolean constantSpeed, boolean isResettingScreen, boolean stayStill, boolean isOnTitleScreen){
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
            System.out.println(SCREEN_HEIGHT);
            speed += (SCREEN_HEIGHT/416f) * delta * hourglassMultiplier; // CHANGE SPEED TO UPDATE BASED ON SCREEN HEIGHT (NOT 1000 OR 7.5)
        }
        else if(isResettingScreen && speed > DEFAULT_SPEED){
            speed -= (SCREEN_HEIGHT/3.12f) * delta * hourglassMultiplier;

        }

        else if(constantSpeed) {
            speed = DEFAULT_SPEED;
        }
        if(!stayStill) {
            background_y1 -= 0.3*speed * delta * titleScreenSpeedModifier * hourglassMultiplier;
            background_y2 -= 0.3*speed * delta * titleScreenSpeedModifier * hourglassMultiplier;

            stars_back_y1 -= 0.5f*speed * delta * titleScreenSpeedModifier * hourglassMultiplier;
            stars_back_y2 -= 0.5f*speed * delta * titleScreenSpeedModifier * hourglassMultiplier;

            stars_front_y1 -= .8f*speed * delta * titleScreenSpeedModifier * hourglassMultiplier;
            stars_front_y2 -= .8f*speed * delta * titleScreenSpeedModifier * hourglassMultiplier;
        }
        if (background_y1 + backgroundSprite.getHeight()  <= 0) {
            background_y1 = background_y2 + backgroundSprite.getHeight();
        }

        if (background_y2 + backgroundSprite.getHeight()  <= 0) {
            background_y2 = background_y1 + backgroundSprite.getHeight();
        }


        if (stars_front_y1 + starsFront.getHeight()  <= 0) {
            stars_front_y1 = stars_front_y2 + starsFront.getHeight();
        }

        if (stars_front_y2 + starsFront.getHeight()  <= 0) {
            stars_front_y2 = stars_front_y1 + starsFront.getHeight();
        }

        if (stars_back_y1 + starsBack.getHeight()  <= 0) {
            stars_back_y1 = stars_back_y2 + starsBack.getHeight();
        }

        if (stars_back_y2 + starsBack.getHeight()  <= 0) {
            stars_back_y2 = stars_back_y1 + starsBack.getHeight();
        }

        backgroundSprite.setSize(SCREEN_WIDTH, backgroundSprite.getHeight());
        starsFront.setSize(SCREEN_WIDTH, starsFront.getHeight());
        starsBack.setSize(SCREEN_WIDTH, starsBack.getHeight());

        backgroundSprite.setPosition(0, background_y1);
        backgroundSprite.draw(batch);

        backgroundSprite.setPosition(0, background_y2);
        backgroundSprite.draw(batch);

        changeBackgroundColor(delta, score, batch, isAlive);

        if(!isHourglass)
            stateTime += delta / 10 * hourglassMultiplier;

        starsFront.setPosition(0, stars_front_y1);
        starsAnimFront.drawAnim(starsFrontAnimation, stateTime, 0, stars_front_y1, SCREEN_WIDTH, starsFront.getHeight(), true,batch);

        starsFront.setPosition(0, stars_front_y2);
        starsAnimFront.drawAnim(starsFrontAnimation, stateTime, 0, stars_front_y2, SCREEN_WIDTH, starsFront.getHeight(), true, batch);

        starsBack.setPosition(0, stars_back_y1);
        starsAnimBack.drawAnim(starsBackAnimation, stateTime, 0, stars_back_y1, SCREEN_WIDTH, starsBack.getHeight(), true,batch);

        starsBack.setPosition(0, stars_back_y2);
        starsAnimBack.drawAnim(starsBackAnimation, stateTime, 0, stars_back_y2, SCREEN_WIDTH, starsBack.getHeight(), true, batch);
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