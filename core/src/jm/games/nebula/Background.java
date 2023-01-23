package jm.games.nebula;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static jm.games.nebula.GameElements.SCREEN_HEIGHT;
import static jm.games.nebula.GameElements.SCREEN_WIDTH;
import static jm.games.nebula.GameElements.WIDTH_ADJUSTMENT;
import static jm.games.nebula.ItemDrop.HOURGLASS_SPEED_MULTIPLIER;


public class Background {
    Assets assets;
    public static final float DEFAULT_SPEED = SCREEN_HEIGHT / 4f;

    Animation<TextureRegion> starsFrontAnimation, starsBackAnimation, starsFrontAnimation1, starsBackAnimation1;
    Sprite backgroundSprite, backgroundSprite1;
    Sprite defaultBackgroundSprite, blueBackgroundSprite, greenBackgroundSprite;
    Sprite redBackgroundSprite, purpleBackgroundSprite, blackBackgroundSprite, whiteBackgroundSprite;

    Sprite starsFront, starsBack, starsFront1, starsBack1;
    float background_y1, background_y2, stars_front_y1, stars_front_y2, stars_back_y1, stars_back_y2;

    Sprite backgroundTransitionSprite, backgroundTransitionSprite1;
    float transitionOpacity = 0f;
    float stateTime = 0f;
    float titleScreenSpeedModifier = 0f;

    float hourglassMultiplier = 1;

    float speed;
    float goalSpeed;

    public Background(Assets assets) {
        this.assets = assets;

        backgroundSprite = new Sprite(assets.assetManager.get(Assets.default_background, Texture.class));
        blueBackgroundSprite = new Sprite(assets.assetManager.get(Assets.blue_background, Texture.class));
        greenBackgroundSprite = new Sprite(assets.assetManager.get(Assets.green_background, Texture.class));
        redBackgroundSprite = new Sprite(assets.assetManager.get(Assets.red_background, Texture.class));
        purpleBackgroundSprite = new Sprite(assets.assetManager.get(Assets.purple_background, Texture.class));
        blackBackgroundSprite = new Sprite(assets.assetManager.get(Assets.black_background, Texture.class));
        whiteBackgroundSprite = new Sprite(assets.assetManager.get(Assets.white_background, Texture.class));

        backgroundSprite.setBounds(0, 0, SCREEN_WIDTH * WIDTH_ADJUSTMENT, (SCREEN_WIDTH  * WIDTH_ADJUSTMENT) * 6);
        backgroundTransitionSprite = backgroundSprite;

        defaultBackgroundSprite = backgroundSprite;
        background_y1 = 0;
        background_y2 = backgroundSprite.getHeight();

        starsFront = new Sprite(assets.assetManager.get(Assets.stars_front, Texture.class));
        starsBack = new Sprite(assets.assetManager.get(Assets.stars_back, Texture.class));

        if(WIDTH_ADJUSTMENT != 1f){
            backgroundSprite1 = new Sprite(assets.assetManager.get(Assets.default_background, Texture.class));

            backgroundSprite1.setBounds(SCREEN_WIDTH * WIDTH_ADJUSTMENT, 0, SCREEN_WIDTH * WIDTH_ADJUSTMENT, (SCREEN_WIDTH  * WIDTH_ADJUSTMENT) * 6);
            backgroundTransitionSprite1 = backgroundSprite1;

            starsFront1 = new Sprite(assets.assetManager.get(Assets.stars_front, Texture.class));
            starsBack1 = new Sprite(assets.assetManager.get(Assets.stars_back, Texture.class));

            starsFront1.flip(true, false);
            starsBack1.flip(true, false);

            starsFront1.setBounds(SCREEN_WIDTH * WIDTH_ADJUSTMENT, 0, SCREEN_WIDTH * WIDTH_ADJUSTMENT, (SCREEN_WIDTH * WIDTH_ADJUSTMENT) * 12);
            starsBack1.setBounds(SCREEN_WIDTH * WIDTH_ADJUSTMENT, 0, SCREEN_WIDTH * WIDTH_ADJUSTMENT, (SCREEN_WIDTH * WIDTH_ADJUSTMENT) * 12);

            starsFrontAnimation1 = Anim.createAnimation(starsFront1, 4, 0.1f);
            starsBackAnimation1 = Anim.createAnimation(starsBack1, 4, 0.1f);
        }

        starsFront.setBounds(SCREEN_WIDTH * WIDTH_ADJUSTMENT, 0, SCREEN_WIDTH * WIDTH_ADJUSTMENT, (SCREEN_WIDTH * WIDTH_ADJUSTMENT) * 12);
        starsBack.setBounds(SCREEN_WIDTH * WIDTH_ADJUSTMENT, 0, SCREEN_WIDTH * WIDTH_ADJUSTMENT, (SCREEN_WIDTH * WIDTH_ADJUSTMENT) * 12);

        stars_back_y1 = 0;
        stars_back_y2 = starsFront.getHeight();

        stars_front_y1 = 0;
        stars_front_y2 = starsFront.getHeight();

        speed = DEFAULT_SPEED;

        goalSpeed = SCREEN_HEIGHT / 1.78f;
        starsFrontAnimation = Anim.createAnimation(starsFront, 4, 0.1f);
        starsBackAnimation = Anim.createAnimation(starsBack, 4, 0.1f);

    }

    public void updateAndRender(float delta, boolean isAlive, boolean isHourglass, int score, Anim starsAnimFront, Anim starsAnimBack, SpriteBatch batch, boolean constantSpeed, boolean isResettingScreen, boolean stayStill, boolean isOnTitleScreen) {
        if (isHourglass) {
            hourglassMultiplier = HOURGLASS_SPEED_MULTIPLIER;
        } else {
            hourglassMultiplier = 1;
        }

        if (isOnTitleScreen) {
            titleScreenSpeedModifier = .33f;
        } else {
            titleScreenSpeedModifier = 1f;
        }

        if (constantSpeed) {
            if (backgroundSprite.getTexture() != assets.assetManager.get(Assets.default_background)) {
                backgroundSprite = defaultBackgroundSprite;
            }
        }

        if (backgroundTransitionSprite != null && transitionOpacity >= 1f) {
            backgroundSprite = backgroundTransitionSprite;
            transitionOpacity = 0f;

            if(WIDTH_ADJUSTMENT != 1F){
                backgroundSprite1 = backgroundTransitionSprite1;
            }
        }

        if (speed < goalSpeed && !constantSpeed && !isResettingScreen) {
            speed += (SCREEN_HEIGHT / 416f) * delta * hourglassMultiplier;
        } else if (isResettingScreen && speed > DEFAULT_SPEED) {
            speed -= (SCREEN_HEIGHT / 3.12f) * delta * hourglassMultiplier;

        } else if (constantSpeed) {
            speed = DEFAULT_SPEED;
        }
        if (!stayStill) {
            background_y1 -= 0.3 * speed * delta * titleScreenSpeedModifier * hourglassMultiplier;
            background_y2 -= 0.3 * speed * delta * titleScreenSpeedModifier * hourglassMultiplier;

            stars_back_y1 -= 0.5f * speed * delta * titleScreenSpeedModifier * hourglassMultiplier;
            stars_back_y2 -= 0.5f * speed * delta * titleScreenSpeedModifier * hourglassMultiplier;

            stars_front_y1 -= .8f * speed * delta * titleScreenSpeedModifier * hourglassMultiplier;
            stars_front_y2 -= .8f * speed * delta * titleScreenSpeedModifier * hourglassMultiplier;
        }
        if (background_y1 + backgroundSprite.getHeight() <= 0) {
            background_y1 = background_y2 + backgroundSprite.getHeight();
        }

        if (background_y2 + backgroundSprite.getHeight() <= 0) {
            background_y2 = background_y1 + backgroundSprite.getHeight();
        }


        if (stars_front_y1 + starsFront.getHeight() <= 0) {
            stars_front_y1 = stars_front_y2 + starsFront.getHeight();
        }

        if (stars_front_y2 + starsFront.getHeight() <= 0) {
            stars_front_y2 = stars_front_y1 + starsFront.getHeight();
        }

        if (stars_back_y1 + starsBack.getHeight() <= 0) {
            stars_back_y1 = stars_back_y2 + starsBack.getHeight();
        }

        if (stars_back_y2 + starsBack.getHeight() <= 0) {
            stars_back_y2 = stars_back_y1 + starsBack.getHeight();
        }

        backgroundSprite.setSize(SCREEN_WIDTH * WIDTH_ADJUSTMENT, backgroundSprite.getHeight());
        starsFront.setSize(SCREEN_WIDTH * WIDTH_ADJUSTMENT, starsFront.getHeight());
        starsBack.setSize(SCREEN_WIDTH * WIDTH_ADJUSTMENT, starsBack.getHeight());

        if(WIDTH_ADJUSTMENT != 1f) {
            backgroundSprite1.setSize(SCREEN_WIDTH * WIDTH_ADJUSTMENT, backgroundSprite.getHeight());
            starsFront1.setSize(SCREEN_WIDTH * WIDTH_ADJUSTMENT, starsFront.getHeight());
            starsBack1.setSize(SCREEN_WIDTH * WIDTH_ADJUSTMENT, starsBack.getHeight());

            backgroundSprite1.setPosition(SCREEN_WIDTH * WIDTH_ADJUSTMENT, background_y1);
            backgroundSprite1.draw(batch);

            backgroundSprite1.setPosition(SCREEN_WIDTH * WIDTH_ADJUSTMENT, background_y2);
            backgroundSprite1.draw(batch);
        }

        backgroundSprite.setPosition(0, background_y1);
        backgroundSprite.draw(batch);

        backgroundSprite.setPosition(0, background_y2);
        backgroundSprite.draw(batch);

        changeBackgroundColor(delta, score, batch, isAlive);

        if (!isHourglass)
            stateTime += delta / 10 * hourglassMultiplier;

        starsFront.setPosition(0, stars_front_y1);
        starsAnimFront.drawAnim(starsFrontAnimation, stateTime, 0, stars_front_y1, SCREEN_WIDTH * WIDTH_ADJUSTMENT, starsFront.getHeight(), true, batch);

        starsFront.setPosition(0, stars_front_y2);
        starsAnimFront.drawAnim(starsFrontAnimation, stateTime, 0, stars_front_y2, SCREEN_WIDTH * WIDTH_ADJUSTMENT, starsFront.getHeight(), true, batch);

        starsBack.setPosition(0, stars_back_y1);
        starsAnimBack.drawAnim(starsBackAnimation, stateTime, 0, stars_back_y1, SCREEN_WIDTH * WIDTH_ADJUSTMENT, starsBack.getHeight(), true, batch);

        starsBack.setPosition(0, stars_back_y2);
        starsAnimBack.drawAnim(starsBackAnimation, stateTime, 0, stars_back_y2, SCREEN_WIDTH * WIDTH_ADJUSTMENT, starsBack.getHeight(), true, batch);

        if(WIDTH_ADJUSTMENT != 1){

            starsFront1.setPosition(SCREEN_WIDTH * WIDTH_ADJUSTMENT, stars_front_y1);
            starsAnimFront.drawAnim(starsFrontAnimation1, stateTime, SCREEN_WIDTH * WIDTH_ADJUSTMENT, stars_front_y1, SCREEN_WIDTH * WIDTH_ADJUSTMENT, starsFront.getHeight(), true, batch);

            starsFront1.setPosition(SCREEN_WIDTH * WIDTH_ADJUSTMENT, stars_front_y2);
            starsAnimFront.drawAnim(starsFrontAnimation1, stateTime, SCREEN_WIDTH * WIDTH_ADJUSTMENT, stars_front_y2, SCREEN_WIDTH * WIDTH_ADJUSTMENT, starsFront.getHeight(), true, batch);

            starsBack1.setPosition(SCREEN_WIDTH * WIDTH_ADJUSTMENT, stars_back_y1);
            starsAnimBack.drawAnim(starsBackAnimation1, stateTime, SCREEN_WIDTH * WIDTH_ADJUSTMENT, stars_back_y1, SCREEN_WIDTH * WIDTH_ADJUSTMENT, starsBack.getHeight(), true, batch);

            starsBack1.setPosition(SCREEN_WIDTH * WIDTH_ADJUSTMENT, stars_back_y2);
            starsAnimBack.drawAnim(starsBackAnimation1, stateTime, SCREEN_WIDTH * WIDTH_ADJUSTMENT, stars_back_y2, SCREEN_WIDTH * WIDTH_ADJUSTMENT, starsBack.getHeight(), true, batch);
        }
    }

    public void changeBackgroundColor(float delta, int score, Batch batch, boolean isAlive) {

        if (backgroundTransitionSprite.getTexture() != backgroundSprite.getTexture() && transitionOpacity < 1) {

            transitionOpacity += 0.2f * delta * hourglassMultiplier;

            backgroundTransitionSprite.setAlpha(transitionOpacity);
            backgroundTransitionSprite.setSize(SCREEN_WIDTH * WIDTH_ADJUSTMENT, backgroundSprite.getHeight());

            backgroundTransitionSprite.setPosition(0, background_y1);
            backgroundTransitionSprite.draw(batch);

            backgroundTransitionSprite.setPosition(0, background_y2);
            backgroundTransitionSprite.draw(batch);

            if(WIDTH_ADJUSTMENT != 1){
                backgroundTransitionSprite1.setAlpha(transitionOpacity);
                backgroundTransitionSprite1.setSize(SCREEN_WIDTH * WIDTH_ADJUSTMENT, backgroundSprite.getHeight());

                backgroundTransitionSprite1.setPosition(SCREEN_WIDTH * WIDTH_ADJUSTMENT, background_y1);
                backgroundTransitionSprite1.draw(batch);

                backgroundTransitionSprite1.setPosition(SCREEN_WIDTH * WIDTH_ADJUSTMENT, background_y2);
                backgroundTransitionSprite1.draw(batch);
            }

        } else if (score < 100 || !isAlive) {
            backgroundTransitionSprite = defaultBackgroundSprite;
        } else if (score < 1000) {
            backgroundTransitionSprite = blueBackgroundSprite;
        } else if (score < 2000) {
            backgroundTransitionSprite = greenBackgroundSprite;
        } else if (score < 3000) {
            backgroundTransitionSprite = redBackgroundSprite;
        } else if (score < 4000) {
            backgroundTransitionSprite = purpleBackgroundSprite;
        } else if (score < 5000) {
            backgroundTransitionSprite = blackBackgroundSprite;
        } else {
            backgroundTransitionSprite = whiteBackgroundSprite;
        }
        if(WIDTH_ADJUSTMENT != 1){
            backgroundTransitionSprite1 = backgroundTransitionSprite;
        }
    }
}