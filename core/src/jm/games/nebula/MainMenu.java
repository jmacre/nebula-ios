package jm.games.nebula;

import static jm.games.nebula.Assets.pause_sound;
import static jm.games.nebula.Assets.play_sound;
import static jm.games.nebula.Assets.title_song;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import java.io.Console;

import jdk.internal.net.http.common.Log;

public class MainMenu extends GameElements implements Screen {
    public Prefs prefs = new Prefs();
    Assets assets;

    public Background background;

    float fadeOutOpacity = 0;
    float fadeInOpacity = 1;

    float transitionSpeed = SCREEN_HEIGHT / 2.496f;

    Main game;

    float musicVolume = 0f;

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
    boolean isCreditsOpen = false;

    boolean soundEnabled, soundLoaded, playSoundHasPlayed;
    boolean isTitleSongPlaying = false;

    GameInterface gameInterface;
    float refreshRate;
    Array<Float> deltaList = new Array<>();
    float deltaSum;

    public MainMenu(Main game, Assets assets) {
        this.game = game;
        this.assets = assets;
        this.gameInterface = new GameInterface(assets, game, prefs);
        this.background = new Background(assets);
    }

    @Override
    public void show() {
        soundEnabled = prefs.isSoundEnabled();

        titleSong = game.miniAudio.createSound(title_song);

        playSound = assets.assetManager.get(play_sound);
        playSound.setVolume(0.4f);

        pauseSound = assets.assetManager.get(pause_sound);
        pauseSound.setVolume(0.4f);

        startButtonInactive = new Sprite(assets.assetManager.get(Assets.start_button_inactive, Texture.class));
        startButtonActive = new Sprite(assets.assetManager.get(Assets.start_button_active, Texture.class));

        blackTransition = new Sprite(assets.assetManager.get(Assets.black_transition, Texture.class));
        blackTransition.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        blackTransition.setColor(0, 0, 0, 0);
        textParameter.size = (int)((SCREEN_WIDTH / 40) * WIDTH_ADJUSTMENT);

        gemCountFont = generator.generateFont(textParameter);
        gemCountFont.setColor(1, 1, 1, 0.8f);

        creditsFont = generator.generateFont(textParameter);

        refreshRate = Gdx.graphics.getDisplayMode().refreshRate;

        ShopElement.createElements(assets);
        GemElement.createElements(assets);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.enableBlending();
        game.batch.begin();

        deltaList.add(delta);


        for (int i = 0; i < deltaList.size; i++) {
            deltaSum += deltaList.get(i);
        }
        delta = deltaSum / deltaList.size;
        deltaSum = 0;

        if (fadeInOpacity < 1) {

            if(musicVolume < 1f && titleSong != null && !transitionOutReady) {
                musicVolume = 1-fadeInOpacity;
                titleSong.setVolume(musicVolume);
            }
                playMusic();

        }

        if (deltaList.size >= 100 || canRenderBackground) {
            canRenderBackground = true;

            if (START_BUTTON_Y >= START_BUTTON_Y_TRANSITIONED && !switchScreens) {
                isTransitioningIn = true;
                transitionIn(delta);
            } else {
                isTransitioningIn = false;
                transitionInDone = true;
                if (!isShopOpen && !isGemScreenOpen && !isCreditsOpen) {
                    if (gameInterface.checkForStartButtonTap(switchScreens)) {
                        switchScreens = true;
                    }
                }

                if (gameInterface.checkForShopButtonTap(isShopOpen, isGemScreenOpen, isCreditsOpen, switchScreens, soundEnabled)) {
                    isShopOpen = true;
                }
                if (isShopOpen && gameInterface.checkForXButtonTap(true, isGemScreenOpen,false, soundEnabled)) {
                    isShopOpen = false;
                }
                if(gameInterface.checkForGemButtonTap(isGemScreenOpen, isShopOpen,  isCreditsOpen, switchScreens, soundEnabled)){
                    isGemScreenOpen = true;
                }
                if(isGemScreenOpen && !gameInterface.adRequested && gameInterface.checkForXButtonTap(isShopOpen, true, false, soundEnabled)){
                    isGemScreenOpen = false;
                }
                if(gameInterface.checkForCreditsButtonTap(isShopOpen, isGemScreenOpen, isCreditsOpen, switchScreens, soundEnabled)){
                    isCreditsOpen = true;
                }
                if(isCreditsOpen && gameInterface.checkForXButtonCreditsTap(true,  soundEnabled)){
                    isCreditsOpen = false;
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

        if (!isShopOpen && !isGemScreenOpen && !isCreditsOpen) {
            gameInterface.drawTitleScreen(game, transitionInDone, prefs);
        }
        else if(isShopOpen){
            gameInterface.drawShopScreen(game, soundEnabled, delta, game.batch, gemCountFont, prefs);
        }
        else if (isCreditsOpen){
            gameInterface.drawCreditsScreen(game);
        }
        else{
            gameInterface.drawGemScreen(game, delta, soundEnabled, game.batch, prefs, titleSong);
        }


        if (isTransitioningIn && !isFadingOut) {
            fadeIn(delta);
        }

        if (transitionOutReady) {
            if(musicVolume > 0f && titleSong != null) {
                musicVolume = 1-fadeOutOpacity;

                if(!gameInterface.adRequested)
                titleSong.setVolume(musicVolume);
            }

            fadeOut(delta);
        }

        if (!isFadingOut && !isTransitioningIn) {
            if (transitionInDone && !switchScreens && !isShopOpen && !isGemScreenOpen && !isCreditsOpen) {
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
            titleSong.stop();
            dispose();
        }
    }

    public void transitionIn(float delta) {
        START_BUTTON_Y -= (int) (transitionSpeed * delta);
        SHOP_BUTTON_Y -= (int) (transitionSpeed * delta);
        TITLE_LOGO_Y -= (int) (transitionSpeed * delta);
    }

    public void playMusic() {
        if (soundEnabled || !isTitleSongPlaying) {
            if(!titleSong.isPlaying()) {
                titleSong.play();
            }

        }
        else {
            musicVolume = 0f;
            titleSong.pause();
            isTitleSongPlaying = false;
        }
        if (!titleSong.isLooping()) {
            titleSong.setLooping(true);
        }

        isTitleSongPlaying = true;
    }

    @Override
    public void dispose() {
        game.setScreen(new MainGame(game, gameInterface, assets, background));
    }

    public void fadeOut(float delta) {
        isFadingOut = true;
        fadeOutOpacity += 0.8f * delta;
        blackTransition.setColor(0, 0, 0, fadeOutOpacity);
        blackTransition.draw(game.batch);
    }

    public void fadeIn(float delta) {
        beganFading = true;
        isFadingIn = true;
        fadeInOpacity -= 0.6f * delta;

        blackTransition.setColor(0, 0, 0, fadeInOpacity);

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
        if(Gdx.graphics.getHeight() != SCREEN_HEIGHT || Gdx.graphics.getWidth() != SCREEN_WIDTH) {
            GameElements.defineSizesAndPositions();
        }

        resetShipPositionOnResize();
        this.gameInterface = new GameInterface(assets, game, prefs);
        this.background = new Background(assets);
    }
}
