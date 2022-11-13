package com.mygdx.NEBULA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenu extends GameElements implements Screen {
    public Prefs prefs = new Prefs();
    Assets assets;

    public Background background;

    float transitionOutOpacity = 0;
    float transitionInOpacity = 1;

    float transitionSpeed = SCREEN_HEIGHT / 2.496f;

    Array<Float> deltaList = new Array<>();
    float deltaSum;

    Main game;
    int score;

    Sprite startButtonInactive, startButtonActive,  blackTransition;

    boolean switchScreens = false;
    boolean transitionOutReady = false;
    boolean transitionInDone = false;
    boolean isTransitioningIn = false;
    boolean isFadingOut = false;
    boolean isFadingIn = false;
    boolean beganFading = false;
    boolean canRenderBackground = false;
    boolean isShopOpen = false;
    boolean soundEnabled, soundLoaded, playSoundHasPlayed;
    GameInterface gameInterface;

    public MainMenu(Main game, int score, Assets assets) {
        this.game = game;
        this.score = score;
        this.assets = assets;
        gameInterface = new GameInterface(assets);
        this.background = new Background(assets);
        prefs.setHighScore(score);
    }

    @Override
    public void show() {
        soundEnabled = prefs.hasSound();

        startButtonInactive = new Sprite(assets.assetManager.get(Assets.start_button_inactive, Texture.class));
        startButtonActive = new Sprite(assets.assetManager.get(Assets.start_button_active, Texture.class));

        blackTransition = new Sprite(assets.assetManager.get(Assets.black_transition, Texture.class));
        blackTransition.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        blackTransition.setColor(0, 0, 0, 0);
        ShopElement.createElements(assets);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        game.batch.enableBlending();

        game.batch.begin();
        deltaList.add(delta);

        if(deltaList.size > 100) {
            for(int i = 0; i < deltaList.size; i++){
                deltaSum += deltaList.get(i);
            }

            delta = deltaSum / deltaList.size;
            deltaList.removeIndex(0);
            deltaSum = 0;
        }

        if(deltaList.size >= 100 || canRenderBackground) {
            if (START_BUTTON_Y >= START_BUTTON_Y_TRANSITIONED && !switchScreens) {
                isTransitioningIn = true;
                transitionIn(delta);
            }
            else {
                isTransitioningIn = false;
                transitionInDone = true;
                if(!isShopOpen) {
                    if (gameInterface.checkForStartButtonTap(switchScreens)) {
                        switchScreens = true;
                    }
                }

                if(gameInterface.checkForShopButtonTap(game, isShopOpen, switchScreens, soundEnabled)) {
                    isShopOpen = true;
                }
                if(isShopOpen && gameInterface.checkForXButtonTap(game, true, soundEnabled)){
                    isShopOpen = false;
                }
            }
        }

        if(beganFading || (START_BUTTON_Y <= START_BUTTON_Y_TRANSITIONED))
            canRenderBackground = true;


        if(canRenderBackground)
            background.updateAndRender(delta, false, false, 0, starsAnimFront, starsAnimBack, game.batch, true, false, false, true);

        if(switchScreens){
            if(soundEnabled && !playSoundHasPlayed) {
                game.playSound.play(0.2f);
                playSoundHasPlayed = true;
            }
                transitionOut(delta);
                transitionInDone = false;

        }

        if (!soundLoaded) {
            soundLoaded = true;
        }

        if(!isShopOpen) {
            gameInterface.drawTitleScreen(game, transitionInDone);
        }
        else {
            gameInterface.drawShopScreen(game, soundEnabled, delta, game.batch);

        }

        if(isTransitioningIn && !isFadingOut) {
            fadeIn(delta);
        }

        if(transitionOutReady) {
            fadeOut(delta);
        }

        if(!isFadingOut && !isTransitioningIn){
            if(transitionInDone  && !switchScreens && !isShopOpen) {
                soundEnabled = gameInterface.checkForTSSoundButtonTap(game, soundEnabled);
            }
        }
        game.batch.end();
    }
    public void transitionOut(float delta) {
        transitionOutReady = true;

        if (START_BUTTON_Y < SCREEN_HEIGHT * 1.2f) {
            START_BUTTON_Y += transitionSpeed * delta;
            TITLE_LOGO_Y += transitionSpeed * delta;
            SHOP_BUTTON_Y += transitionSpeed * delta;
        }
        if (START_BUTTON_Y > SCREEN_HEIGHT * 1.2f) {
            dispose();
        }
    }

    public void transitionIn(float delta){
        START_BUTTON_Y -= transitionSpeed * delta;
        SHOP_BUTTON_Y -= transitionSpeed * delta;
        TITLE_LOGO_Y -= transitionSpeed * delta;
    }

    @Override
    public void dispose() {
        game.setScreen(new MainGame(game, assets, background));
    }

    public void fadeOut(float delta){
        isFadingOut = true;
        transitionOutOpacity += 0.8f * delta;
        blackTransition.setColor(0,0,0,transitionOutOpacity);
        blackTransition.draw(game.batch);
    }

    public void fadeIn(float delta){
        beganFading = true;
        isFadingIn = true;
        transitionInOpacity -= 0.6f * delta;
        blackTransition.setColor(0,0,0, transitionInOpacity);

        blackTransition.draw(game.batch);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void resize(int width, int height) {
    }
}
