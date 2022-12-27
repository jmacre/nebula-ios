package com.mygdx.NEBULA;

import static com.mygdx.NEBULA.Assets.gem_sound;
import static com.mygdx.NEBULA.Assets.pause_sound;
import static com.mygdx.NEBULA.Assets.play_sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import games.rednblack.miniaudio.MiniAudio;

public class MainMenu extends GameElements implements Screen {
    public Prefs prefs = new Prefs();
    Assets assets;

    public Background background;

    float transitionOutOpacity = 0;
    float transitionInOpacity = 1;

    float transitionSpeed = SCREEN_HEIGHT / 2.496f;

    Main game;

    Sprite startButtonInactive, startButtonActive, blackTransition;

    boolean switchScreens = false;
    boolean transitionOutReady = false;
    boolean transitionInDone = false;
    boolean isTransitioningIn = false;
    boolean isFadingOut = false;
    boolean isFadingIn = false;
    boolean beganFading = false;
    boolean canRenderBackground = false;
    boolean isShopOpen = false;
    boolean isGemScreenOpen = false;
    boolean soundEnabled, soundLoaded, playSoundHasPlayed;
    GameInterface gameInterface;
    float refreshRate;
    Array<Float> deltaList = new Array<>();
    float deltaSum;

    public MainMenu(Main game, Assets assets) {
        this.game = game;
        this.assets = assets;
        gameInterface = new GameInterface(assets, game);
        this.background = new Background(assets);
    }

    @Override
    public void show() {
        soundEnabled = prefs.hasSound();
        playSound = assets.assetManager.get(play_sound);
        playSound.setVolume(0.2f);

        pauseSound = assets.assetManager.get(pause_sound);
        pauseSound.setVolume(0.2f);

        startButtonInactive = new Sprite(assets.assetManager.get(Assets.start_button_inactive, Texture.class));
        startButtonActive = new Sprite(assets.assetManager.get(Assets.start_button_active, Texture.class));

        blackTransition = new Sprite(assets.assetManager.get(Assets.black_transition, Texture.class));
        blackTransition.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        blackTransition.setColor(0, 0, 0, 0);
        textParameter.size = SCREEN_WIDTH / 40;

        gemCountFont = generator.generateFont(textParameter);
        gemCountFont.setColor(1, 1, 1, 0.8f);
        ShopElement.createElements(assets);
        refreshRate = Gdx.graphics.getDisplayMode().refreshRate;

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.enableBlending();

        game.batch.begin();
        if(Gdx.graphics.getFramesPerSecond() > refreshRate * .95f && Gdx.graphics.getFramesPerSecond() < refreshRate * 1.05f) {
            deltaList.add(delta);
        }

        for (int i = 0; i < deltaList.size; i++) {
            deltaSum += deltaList.get(i);
        }
        delta = deltaSum / deltaList.size;
        deltaSum = 0;


        if (deltaList.size >= 100 || canRenderBackground) {
            if (START_BUTTON_Y >= START_BUTTON_Y_TRANSITIONED && !switchScreens) {
                isTransitioningIn = true;
                transitionIn(delta);
            } else {
                isTransitioningIn = false;
                transitionInDone = true;
                if (!isShopOpen && !isGemScreenOpen) {
                    if (gameInterface.checkForStartButtonTap(switchScreens)) {
                        switchScreens = true;
                    }
                }

                if (gameInterface.checkForShopButtonTap(isShopOpen, isGemScreenOpen, switchScreens, soundEnabled)) {
                    isShopOpen = true;
                }
                if (isShopOpen && gameInterface.checkForXButtonTap(true, isGemScreenOpen, soundEnabled)) {
                    isShopOpen = false;
                }
                if(gameInterface.checkForGemButtonTap(isGemScreenOpen, isShopOpen, switchScreens, soundEnabled)){
                    isGemScreenOpen = true;
                }
                if(isGemScreenOpen && gameInterface.checkForXButtonTap(isShopOpen, true,soundEnabled)){
                    isGemScreenOpen = false;
                }
            }
        }

        if (beganFading || (START_BUTTON_Y <= START_BUTTON_Y_TRANSITIONED))
            canRenderBackground = true;

        if (canRenderBackground)
            background.updateAndRender(delta, false, false, 0, starsAnimFront, starsAnimBack, game.batch, true, false, false, true);

        if (switchScreens) {
            if (soundEnabled && !playSoundHasPlayed) {
                playSound.play();
                playSoundHasPlayed = true;
            }
            transitionOut(delta);
            transitionInDone = false;

        }

        if (!soundLoaded) {
            soundLoaded = true;
        }

        if (!isShopOpen && !isGemScreenOpen) {
            gameInterface.drawTitleScreen(game, transitionInDone, prefs);
        }
        else if(isShopOpen){
            gameInterface.drawShopScreen(game, soundEnabled, delta, game.batch, gemCountFont, prefs);
        }
        else{
            gameInterface.drawGemScreen(game, soundEnabled, game.batch, prefs);
        }


        if (isTransitioningIn && !isFadingOut) {
            fadeIn(delta);
        }

        if (transitionOutReady) {
            fadeOut(delta);
        }

        if (!isFadingOut && !isTransitioningIn) {
            if (transitionInDone && !switchScreens && !isShopOpen && !isGemScreenOpen) {
                soundEnabled = gameInterface.checkForTSSoundButtonTap(game, soundEnabled, prefs);
            }
        }
        game.batch.end();
    }

    public void transitionOut(float delta) {
        transitionOutReady = true;

        if (START_BUTTON_Y < SCREEN_HEIGHT * 1.2f) {
            START_BUTTON_Y += (int) (transitionSpeed * delta);
            TITLE_LOGO_Y += (int) (transitionSpeed * delta);
            SHOP_BUTTON_Y += (int) (transitionSpeed * delta);
        }
        if (START_BUTTON_Y > SCREEN_HEIGHT * 1.2f) {
            dispose();
        }
    }

    public void transitionIn(float delta) {
        START_BUTTON_Y -= (int) (transitionSpeed * delta);
        SHOP_BUTTON_Y -= (int) (transitionSpeed * delta);
        TITLE_LOGO_Y -= (int) (transitionSpeed * delta);
    }

    @Override
    public void dispose() {
        game.setScreen(new MainGame(game, gameInterface, assets, background));
    }

    public void fadeOut(float delta) {
        isFadingOut = true;
        transitionOutOpacity += 0.8f * delta;
        blackTransition.setColor(0, 0, 0, transitionOutOpacity);
        blackTransition.draw(game.batch);
    }

    public void fadeIn(float delta) {
        beganFading = true;
        isFadingIn = true;
        transitionInOpacity -= 0.6f * delta;
        blackTransition.setColor(0, 0, 0, transitionInOpacity);

        blackTransition.draw(game.batch);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        if (Gdx.graphics.getDisplayMode().refreshRate != refreshRate) {
            refreshRate = Gdx.graphics.getDisplayMode().refreshRate;
            deltaList.clear();

        }
    }

    @Override
    public void hide() {
    }

    @Override
    public void resize(int width, int height) {

    }
}
