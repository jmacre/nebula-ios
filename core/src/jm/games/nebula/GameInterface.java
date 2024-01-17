package jm.games.nebula;

import static jm.games.nebula.Anim.DEFAULT_FRAME_DURATION;
import static jm.games.nebula.Assets.ad_button_inactive;
import static jm.games.nebula.Assets.pause_sound;
import static jm.games.nebula.Assets.play_sound;
import static jm.games.nebula.GemElement.AD_ID;
import static jm.games.nebula.ShopElement.SHIP_ID;
import static jm.games.nebula.ShopElement.SHIP_SLOT;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.pay.Offer;
import com.badlogic.gdx.pay.OfferType;
import com.badlogic.gdx.pay.PurchaseManagerConfig;
import com.badlogic.gdx.pay.PurchaseObserver;
import com.badlogic.gdx.pay.Transaction;
import com.badlogic.gdx.utils.Align;

import games.rednblack.miniaudio.MASound;

public class GameInterface extends GameElements {
    Main game;

    Assets assets;
    Button pauseButton;
    Button homeButton;
    Button playButton;
    Button replayButton;
    Button ingameShopButton;
    Button startButton;
    Button shopButton;
    Button leftArrowBtn;
    Button rightArrowBtn;
    Button selectButton;
    Button backButton;

    Float glx;

    Button gemButton;
    Button shipButton;

    Sprite gemButtonSprite;
    Sprite shipButtonSprite;

    ShopElement ship;
    GemElement gemElement;

    Sprite titleTexture;
    Button tsSoundButton;
    Button questionButton;

    Button xButton;
    Button xButtonCredits;

    Button yesButton;
    Button noButton;
    BitmapFont scoreFont, creditsFont;
    Sprite soundOnButton;
    Sprite soundOffButton;
    Sprite heart1;
    Sprite heart2;
    Sprite heart3;
    Sprite heartMissing1, heartMissing2, heartMissing3, gemIcon, pauseMenuBack, shopBack;
    GlyphLayout gl;
    Boolean confirmLeaveScreenOpen = false;
    Boolean continueScreenOpen = false;

    boolean isAdLoaded = false;
    boolean adRequested = false;

    int totalGemsEarned;

    float tapToContinueBlinkingTimer = TAP_TO_CONTINUE_BLINKING_TIMER * 2;

    float gemPurchaseTimer = GEM_COUNT_UPDATE_TIMER;

    boolean gemsPurchased = false;
    int purchasedGemCount = 0;

    int gemCount = 0;
    int prePurchaseGemCount = 0;

    boolean isRecapScreenOpen = true;
    boolean isReplayScreenOpen = false;
    boolean isPauseScreenOpen = false;

    boolean isShipMenuOpen;
    boolean isGemMenuOpen;

    float scoreY;
    float gemIconY;
    float pauseButtonY;
    float gameOverTextY;

    float topElemY;
    float stateTime = 0f;

    int selectedShopElement = 0;
    int selectedGemScreenElement = 0;

    int topInset = 0;

    Prefs prefs;

    public FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    public GameInterface(Assets assets, Main game, Prefs prefs) {
        this.assets = assets;
        this.game = game;
        this.prefs = prefs;

        initPurchaseManager();

        if (Gdx.graphics.getSafeInsetTop() > 0) {
            topInset = (int) (0.6 * Gdx.graphics.getSafeInsetTop());
        }

        parameter.size = (int) ((SCREEN_WIDTH / 40) * WIDTH_ADJUSTMENT);
        startButton = new Button(assets.assetManager.get(Assets.start_button_inactive_clear, Texture.class), START_BUTTON_X, START_BUTTON_Y_TRANSITIONED, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);

        shopButton = new Button(assets.assetManager.get(Assets.shop_button_inactive_clear, Texture.class), SHOP_BUTTON_X, SHOP_BUTTON_Y_TRANSITIONED, SHOP_BUTTON_WIDTH, SHOP_BUTTON_HEIGHT);

        if (prefs.isSoundEnabled())
            soundButton = new Button(assets.assetManager.get(Assets.sound_on_button_inactive, Texture.class), SOUND_BUTTON_X, SOUND_BUTTON_Y, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);
        else
            soundButton = new Button(assets.assetManager.get(Assets.sound_off_button_inactive, Texture.class), SOUND_BUTTON_X, SOUND_BUTTON_Y, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);

        shipButton = new Button(assets.assetManager.get(Assets.blank_shop_button_inactive, Texture.class), SHIP_BUTTON_X, SHIP_BUTTON_Y, SHIP_BUTTON_WIDTH, SHIP_BUTTON_HEIGHT);
        gemButton = new Button(assets.assetManager.get(Assets.blank_shop_button_inactive, Texture.class), GEM_BUTTON_X, GEM_BUTTON_Y, GEM_BUTTON_WIDTH, GEM_BUTTON_HEIGHT);

        leftArrowBtn = new Button(assets.assetManager.get(Assets.left_arrow_btn_inactive, Texture.class), LEFT_ARROW_BTN_X, LEFT_ARROW_BTN_Y, LEFT_ARROW_BTN_WIDTH, LEFT_ARROW_BTN_HEIGHT);
        rightArrowBtn = new Button(assets.assetManager.get(Assets.right_arrow_btn_inactive, Texture.class), RIGHT_ARROW_BTN_X, RIGHT_ARROW_BTN_Y, RIGHT_ARROW_BTN_WIDTH, RIGHT_ARROW_BTN_HEIGHT);
        selectButton = new Button(assets.assetManager.get(Assets.select_button_inactive, Texture.class), SELECT_BUTTON_X, SELECT_BUTTON_Y, SELECT_BUTTON_WIDTH, SELECT_BUTTON_HEIGHT);

        backButton = new Button(assets.assetManager.get(Assets.back_button, Texture.class), BACK_BUTTON_X, BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT, BACK_BUTTON_WIDTH / 4f, BACK_BUTTON_HEIGHT / 4f);

        xButton = new Button(assets.assetManager.get(Assets.x_button, Texture.class), X_BUTTON_X, X_BUTTON_Y, X_BUTTON_WIDTH, X_BUTTON_HEIGHT, X_BUTTON_WIDTH / 4f, X_BUTTON_HEIGHT / 4f);
        xButtonCredits = new Button(assets.assetManager.get(Assets.x_button, Texture.class), X_BUTTON_X, X_BUTTON_CREDITS_Y, X_BUTTON_WIDTH, X_BUTTON_HEIGHT, X_BUTTON_WIDTH / 4f, X_BUTTON_HEIGHT / 4f);

        yesButton = new Button(assets.assetManager.get(Assets.yes_button_inactive, Texture.class), YES_BUTTON_X, YES_BUTTON_Y, YES_BUTTON_WIDTH, YES_BUTTON_HEIGHT);
        noButton = new Button(assets.assetManager.get(Assets.no_button_inactive, Texture.class), NO_BUTTON_X, NO_BUTTON_Y, NO_BUTTON_WIDTH, NO_BUTTON_HEIGHT);

        textParameter.size = (int) ((SCREEN_WIDTH / 15) * WIDTH_ADJUSTMENT);
        storeFont = generator.generateFont(textParameter);

        gemSound = assets.assetManager.get(Assets.gem_sound, MASound.class);
        gemSound.setVolume(0.4f);

        errorSound = assets.assetManager.get(Assets.error_sound, MASound.class);
        errorSound.setVolume(0.4f);

        playSound = assets.assetManager.get(play_sound, MASound.class);
        playSound.setVolume(0.4f);

        pauseSound = assets.assetManager.get(pause_sound, MASound.class);
        pauseSound.setVolume(0.4f);

        textParameter.size = (int) ((SELECT_BUTTON_WIDTH / 8.75f) * SHOP_FONT_WIDTH_ADJUSTMENT);
        buyFont = generator.generateFont(textParameter);

        scoreY = SCORE_Y - topInset;
        topElemY = TOP_ELEM_Y - topInset;
        gemIconY = GEM_ICON_Y - topInset;
        pauseButtonY = PAUSE_BUTTON_Y - topInset;
        gameOverTextY = GAME_OVER_TEXT_Y - topInset;

        tsSoundButton = new Button(assets.assetManager.get(Assets.sound_off_button_ts, Texture.class), TS_SOUND_BUTTON_X, TS_SOUND_BUTTON_Y, TS_SOUND_BUTTON_WIDTH, TS_SOUND_BUTTON_HEIGHT, TS_SOUND_BUTTON_WIDTH / 4f, TS_SOUND_BUTTON_HEIGHT / 4f);
        questionButton = new Button(assets.assetManager.get(Assets.question_button, Texture.class), QUESTION_BUTTON_X, QUESTION_BUTTON_Y, QUESTION_BUTTON_WIDTH, QUESTION_BUTTON_HEIGHT, QUESTION_BUTTON_WIDTH / 4f, QUESTION_BUTTON_HEIGHT / 4f);
        pauseButton = new Button(assets.assetManager.get(Assets.pause_button, Texture.class), PAUSE_BUTTON_X, pauseButtonY, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT, PAUSE_BUTTON_WIDTH / 4f, PAUSE_BUTTON_HEIGHT / 4f);
        homeButton = new Button(assets.assetManager.get(Assets.home_button_inactive, Texture.class), HOME_BUTTON_X, HOME_BUTTON_Y, HOME_BUTTON_WIDTH, HOME_BUTTON_HEIGHT);
        playButton = new Button(assets.assetManager.get(Assets.play_button_inactive, Texture.class), PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        replayButton = new Button(assets.assetManager.get(Assets.replay_button_inactive, Texture.class), PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        ingameShopButton = new Button(assets.assetManager.get(Assets.ingame_shop_button_inactive, Texture.class), SOUND_BUTTON_X, SOUND_BUTTON_Y, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);
        soundOffButton = new Sprite(assets.assetManager.get(Assets.sound_off_button_inactive, Texture.class));
        titleTexture = new Sprite(assets.assetManager.get(Assets.title_logo_clear, Texture.class));
        soundOnButton = new Sprite(assets.assetManager.get(Assets.sound_on_button_inactive, Texture.class));
//        gemButton = new Button(new Sprite(assets.assetManager.get(Assets.gem_button_ss, Texture.class)), 2, DEFAULT_FRAME_DURATION, GEM_BUTTON_X, GEM_BUTTON_Y, GEM_WIDTH, GEM_HEIGHT, GEM_WIDTH / 2f, GEM_HEIGHT / 2f);

        shipButtonSprite = new Sprite(assets.assetManager.get(Assets.ship_purple_ss, Texture.class));
        gemButtonSprite = new Sprite(assets.assetManager.get(Assets.gem_ss, Texture.class), (int) (GEM_BUTTON_X + GEM_BUTTON_WIDTH/2 - GEM_WIDTH/2), (int) (GEM_BUTTON_Y + GEM_BUTTON_HEIGHT/2 - GEM_HEIGHT/2), (int) GEM_WIDTH, (int) GEM_HEIGHT);

        gemButtonAnimation = Anim.createAnimation(gemButtonSprite, 2, 0.034f);
        shipButtonAnimation = Anim.createAnimation(shipButtonSprite, 4, (1.5f * DEFAULT_FRAME_DURATION));

        heart1 = new Sprite(assets.assetManager.get(Assets.heart, Texture.class));
        heart2 = heart1;
        heart3 = heart1;

        heartMissing1 = new Sprite(assets.assetManager.get(Assets.heart_missing, Texture.class));
        heartMissing2 = heartMissing1;
        heartMissing3 = heartMissing1;

        gemIcon = new Sprite(assets.assetManager.get(Assets.gem_icon, Texture.class));

        pauseMenuBack = new Sprite(assets.assetManager.get(Assets.pause_menu_back, Texture.class));
        shopBack = new Sprite(assets.assetManager.get(Assets.shop_back, Texture.class));

        scoreFont = generator.generateFont(parameter);
        scoreFont.setColor(Color.valueOf(PURPLE_COLOR_HEX));

        storeFont.setColor(Color.valueOf(PURPLE_COLOR_HEX));

        gemCountFont = generator.generateFont(parameter);
        creditsFont = generator.generateFont(parameter);

        parameter.size = (int) ((SCREEN_WIDTH / 30) * WIDTH_ADJUSTMENT);

        creditsFont = generator.generateFont(parameter);
        creditsFont.setColor(Color.WHITE);

        gl = new GlyphLayout();
    }


    public void drawTopUI(Main game, boolean isPaused, int health, boolean isAlive, boolean isTransitionedIn) {
        if (!isPaused && isAlive && isTransitionedIn)
            game.batch.draw(pauseButton.getTexture(), SCREEN_WIDTH - SCREEN_WIDTH / 7.5f, topElemY, HEART_WIDTH * 1.3f, HEART_HEIGHT);

        if (health == 3) {
            game.batch.draw(heart1, RIGHT_HEART_X, topElemY, HEART_WIDTH, HEART_HEIGHT);
            game.batch.draw(heart2, MIDDLE_HEART_X, topElemY, HEART_WIDTH, HEART_HEIGHT);
            game.batch.draw(heart3, LEFT_HEART_X, topElemY, HEART_WIDTH, HEART_HEIGHT);
        }
        if (health == 2) {
            game.batch.draw(heartMissing1, RIGHT_HEART_X, topElemY, HEART_WIDTH, HEART_HEIGHT);
            game.batch.draw(heart2, MIDDLE_HEART_X, topElemY, HEART_WIDTH, HEART_HEIGHT);
            game.batch.draw(heart3, LEFT_HEART_X, topElemY, HEART_WIDTH, HEART_HEIGHT);
        }
        if (health == 1) {
            game.batch.draw(heartMissing1, RIGHT_HEART_X, topElemY, HEART_WIDTH, HEART_HEIGHT);
            game.batch.draw(heartMissing2, MIDDLE_HEART_X, topElemY, HEART_WIDTH, HEART_HEIGHT);
            game.batch.draw(heart3, LEFT_HEART_X, topElemY, HEART_WIDTH, HEART_HEIGHT);
        }
        if (health == 0) {
            game.batch.draw(heartMissing1, RIGHT_HEART_X, topElemY, HEART_WIDTH, HEART_HEIGHT);
            game.batch.draw(heartMissing2, MIDDLE_HEART_X, topElemY, HEART_WIDTH, HEART_HEIGHT);
            game.batch.draw(heartMissing3, LEFT_HEART_X, topElemY, HEART_WIDTH, HEART_HEIGHT);
        }
        game.batch.draw(gemIcon, GEM_ICON_X, gemIconY, GEM_ICON_WIDTH, GEM_ICON_HEIGHT);
    }

    public void drawTitleScreen(Main game, float delta, boolean transitionInDone, Prefs prefs) {
        skipGemPurchaseCount();

        if (START_BUTTON_Y < SCREEN_HEIGHT)
            game.batch.draw(startButton.getTexture(), START_BUTTON_X, START_BUTTON_Y, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);

        if (SHOP_BUTTON_Y < SCREEN_HEIGHT)
            game.batch.draw(shopButton.getTexture(), SHOP_BUTTON_X, SHOP_BUTTON_Y, SHOP_BUTTON_WIDTH, SHOP_BUTTON_HEIGHT);

        if (TITLE_LOGO_Y < SCREEN_HEIGHT)
            game.batch.draw(titleTexture, TITLE_LOGO_X, TITLE_LOGO_Y, TITLE_LOGO_WIDTH, TITLE_LOGO_HEIGHT);

        if (prefs.isSoundEnabled())
            tsSoundButton.setTexture(assets.assetManager.get(Assets.sound_on_button_ts, Texture.class));
        else
            tsSoundButton.setTexture(assets.assetManager.get(Assets.sound_off_button_ts, Texture.class));

        if (transitionInDone) {
            titleTexture.setTexture(assets.assetManager.get(Assets.title_logo, Texture.class));

//            gemButton.render(gemButtonAnim, delta, game.batch);

            game.batch.draw(tsSoundButton.getTexture(), TS_SOUND_BUTTON_X, TS_SOUND_BUTTON_Y, TS_SOUND_BUTTON_WIDTH, TS_SOUND_BUTTON_HEIGHT);
            game.batch.draw(questionButton.getTexture(), QUESTION_BUTTON_X, QUESTION_BUTTON_Y, QUESTION_BUTTON_WIDTH, QUESTION_BUTTON_HEIGHT);

            scoreFont.draw(game.batch, "HIGH SCORE: " + prefs.getHighScore(), SCORE_X, scoreY);

            gl.setText(gemCountFont, " x " + prefs.getGemCount(), Color.WHITE, SCREEN_WIDTH, Align.center, true);
            gemCountFont.draw(game.batch, " x " + prefs.getGemCount(), 0.95f * (SCREEN_WIDTH - gl.width), scoreY);

            game.batch.draw(gemIcon, 0.945f * (SCREEN_WIDTH - gl.width) - GEM_ICON_WIDTH, scoreY - GEM_ICON_HEIGHT * .8f, GEM_ICON_WIDTH, GEM_ICON_HEIGHT);
        }
    }

    public boolean checkForTSSoundButtonTap(Main game, boolean soundEnabled, Prefs prefs) {
        if (tsSoundButton.getTappedBefore()) {
            if (prefs.isSoundEnabled()) {
                tsSoundButton.setTexture(assets.assetManager.get(Assets.sound_off_button_ts, Texture.class));
            } else {
                tsSoundButton.setTexture(assets.assetManager.get(Assets.sound_on_button_ts, Texture.class));
            }
        }
        if (tsSoundButton.getReleased()) {
            if (prefs.isSoundEnabled()) {
                prefs.setSound(false);
                soundEnabled = false;
            } else {
                prefs.setSound(true);
                soundEnabled = true;
                playSound.stop();
                playSound.play();
            }
        }
        return soundEnabled;
    }

    public boolean checkForStartButtonTap(boolean switchScreens) {
        if (!switchScreens) {
            if (startButton.getTappedBefore()) {
                startButton.setTexture(assets.assetManager.get(Assets.start_button_active, Texture.class));
            } else {
                startButton.setTexture(assets.assetManager.get(Assets.start_button_inactive, Texture.class));
            }
            return startButton.getReleased();
        }

        return false;
    }

    public boolean checkForShopButtonTap(boolean isShopOpen, boolean isGemScreenOpen, boolean isCreditsOpen, boolean switchScreens, boolean soundEnabled) {
        if (!isShopOpen && !switchScreens && !isGemScreenOpen && !isCreditsOpen) {
            if (shopButton.getTappedBefore()) {
                shopButton.setTexture(assets.assetManager.get(Assets.shop_button_active, Texture.class));
            } else {
                shopButton.setTexture(assets.assetManager.get(Assets.shop_button_inactive, Texture.class));
            }
            if (shopButton.getReleased()) {
                if (soundEnabled) {
                    playSound.stop();
                    playSound.play();
                }

                return true;

            }
        }
        return false;
    }

    public boolean checkForQuestionButtonTap(boolean isShopOpen, boolean isGemScreenOpen, boolean isCreditsOpen, boolean switchScreens, boolean soundEnabled) {
        if (!isShopOpen && !switchScreens && !isGemScreenOpen && !isCreditsOpen) {

            questionButton.getTappedBefore();

            if (questionButton.getReleased()) {
                if (soundEnabled) {
                    playSound.stop();
                    playSound.play();
                }

                return true;

            }
        }
        return false;
    }

    public boolean checkForSoundButtonTap(boolean soundEnabled, boolean isAlive) {
        if (isAlive || isReplayScreenOpen && !isConfirmLeaveScreenOpen()) {
            if (soundEnabled) {
                if (soundButton.getTappedBefore()) {
                    soundButton.setTexture(assets.assetManager.get(Assets.sound_on_button_active, Texture.class));
                } else {
                    soundButton.setTexture(assets.assetManager.get(Assets.sound_on_button_inactive, Texture.class));
                }
            } else {
                if (soundButton.getTappedBefore()) {
                    soundButton.setTexture(assets.assetManager.get(Assets.sound_off_button_active, Texture.class));

                } else {
                    soundButton.setTexture(assets.assetManager.get(Assets.sound_off_button_inactive, Texture.class));
                }
            }
        }
        if (soundButton.getReleased()) {
            if (!soundEnabled) {
                pauseSound.play();
            }
            return true;
        }
        return false;
    }


    public boolean checkForReplayButtonTap() {

        if (!isConfirmLeaveScreenOpen()) {
            if (replayButton.getTappedBefore()) {
                replayButton.setTexture(assets.assetManager.get(Assets.replay_button_active, Texture.class));
            } else {
                replayButton.setTexture(assets.assetManager.get(Assets.replay_button_inactive, Texture.class));
            }
        }
        if (replayButton.getReleased()) {
            replayButton.setTexture(assets.assetManager.get(Assets.replay_button_inactive, Texture.class));
            isPauseScreenOpen = false;

            return true;
        }
        return false;
    }

    public boolean checkForIngameShopButtonTap(boolean soundEnabled) {

        if (!isConfirmLeaveScreenOpen()) {
            if (ingameShopButton.getTappedBefore()) {
                ingameShopButton.setTexture(assets.assetManager.get(Assets.ingame_shop_button_active, Texture.class));
            } else {
                ingameShopButton.setTexture(assets.assetManager.get(Assets.ingame_shop_button_inactive, Texture.class));
            }
        }
        if (ingameShopButton.getReleased()) {
            ingameShopButton.setTexture(assets.assetManager.get(Assets.replay_button_inactive, Texture.class));
            isPauseScreenOpen = false;

            if (soundEnabled) {
                playSound.play();

            }
            return true;
        }
        return false;
    }


    public boolean checkForPlayButtonTap(boolean soundEnabled) {
        if (playButton.getTappedBefore()) {
            playButton.setTexture(assets.assetManager.get(Assets.play_button_active, Texture.class));

        } else {
            playButton.setTexture(assets.assetManager.get(Assets.play_button_inactive, Texture.class));
        }
        if (playButton.getReleased()) {
            isPauseScreenOpen = false;

            if (soundEnabled) {
                playSound.play();

            }
            return true;
        }
        return false;
    }

    public boolean checkForPauseButtonTap() {
        return pauseButton.getTapped();
    }

    public boolean checkForXButtonTap(boolean isShopOpen, boolean isGemScreenOpen, boolean isCreditsScreenOpen, boolean soundEnabled) {
        if (isShopOpen) {
            if (xButton.getTapped()) {
                isShopOpen = false;

                if (soundEnabled) {
                    pauseSound.stop();
                    pauseSound.play();
                }
            }
            return !isShopOpen;

        } else if (isGemScreenOpen) {
            if (xButton.getTapped()) {
                isGemScreenOpen = false;
                isAdLoaded = false;

                if (soundEnabled) {
                    pauseSound.stop();
                    pauseSound.play();
                }
                gemCount = prefs.getGemCount();
            }
            return !isGemScreenOpen;
        }

        return false;
    }

    public boolean checkForXButtonCreditsTap(boolean isCreditsScreenOpen, boolean soundEnabled) {
        if (isCreditsScreenOpen) {
            if (xButtonCredits.getTapped()) {
                isCreditsScreenOpen = false;

                if (soundEnabled) {
                    pauseSound.stop();
                    pauseSound.play();
                }
                gemCount = prefs.getGemCount();
            }
            return !isCreditsScreenOpen;
        }

        return false;
    }

    public boolean checkForYesButtonTap() {
        if (yesButton.getTappedBefore()) {
            yesButton.setTexture(assets.assetManager.get(Assets.yes_button_active, Texture.class));
        } else {
            yesButton.setTexture(assets.assetManager.get(Assets.yes_button_inactive, Texture.class));
        }
        return yesButton.getReleased();
    }

    public boolean checkForNoButtonTap(boolean soundEnabled) {
        if (noButton.getTappedBefore()) {
            noButton.setTexture(assets.assetManager.get(Assets.no_button_active, Texture.class));
        } else {
            noButton.setTexture(assets.assetManager.get(Assets.no_button_inactive, Texture.class));
        }
        if (noButton.getReleased()) {
            if (soundEnabled) {
                pauseSound.stop();
                pauseSound.play();
            }
            return true;
        }
        return false;
    }

    public boolean checkForHomeButtonTap(boolean soundEnabled) {
        if (!isConfirmLeaveScreenOpen()) {
            if (homeButton.getTappedBefore()) {
                homeButton.setTexture(assets.assetManager.get(Assets.home_button_active, Texture.class));
            } else {
                homeButton.setTexture(assets.assetManager.get(Assets.home_button_inactive, Texture.class));
            }
        }
        if (homeButton.getReleased()) {
            if (soundEnabled) {
                pauseSound.play();
            }
            return true;
        }
        return false;
    }

    public boolean checkForShipButtonTap(boolean soundEnabled) {
        if (!isConfirmLeaveScreenOpen()) {
            if (shipButton.getTappedBefore()) {
                shipButton.setTexture(assets.assetManager.get(Assets.blank_shop_button_active, Texture.class));
            } else {
                shipButton.setTexture(assets.assetManager.get(Assets.blank_shop_button_inactive, Texture.class));
            }
        }
        if (shipButton.getReleased()) {
            if (soundEnabled) {
                pauseSound.stop();
                pauseSound.play();
            }
            return true;
        }
        return false;
    }

    public boolean checkForGemButtonTap(boolean soundEnabled) {
        if (!isConfirmLeaveScreenOpen()) {
            if (gemButton.getTappedBefore()) {
                gemButton.setTexture(assets.assetManager.get(Assets.blank_shop_button_active, Texture.class));
            } else {
                gemButton.setTexture(assets.assetManager.get(Assets.blank_shop_button_inactive, Texture.class));
            }
        }
        if (gemButton.getTapped()) {
            if (soundEnabled) {
                pauseSound.stop();
                pauseSound.play();
            }
            return true;
        }
        return false;
    }

    public boolean checkForBackButtonTap(boolean soundEnabled) {
        if (backButton.getTapped()) {

            if (soundEnabled) {
                pauseSound.stop();
                pauseSound.play();
            }
            return true;

        }
        return false;
    }

    public void checkForLeftArrowBtnTap(boolean soundEnabled, boolean isShopOpen, boolean isGemScreenOpen) {
        if (leftArrowBtn.getTappedBefore()) {
            leftArrowBtn.setTexture(assets.assetManager.get(Assets.left_arrow_btn_active, Texture.class));
        } else {
            leftArrowBtn.setTexture(assets.assetManager.get(Assets.left_arrow_btn_inactive, Texture.class));
        }
        if (leftArrowBtn.getReleased()) {
            if (isShopOpen && !isGemScreenOpen) {

                if (selectedShopElement == 0) {
                    selectedShopElement = ShopElement.shipCount;
                } else {
                    selectedShopElement -= 1;
                }

                ship.setElementAnimation(selectedShopElement);
            }
            if (isGemScreenOpen && !isShopOpen) {
                isAdLoaded = false;

                if (selectedGemScreenElement == 0) {
                    selectedGemScreenElement = GemElement.gemOptionsCount;
                } else {
                    selectedGemScreenElement -= 1;
                }
            }
            if (soundEnabled) {
                pauseSound.stop();
                pauseSound.play();
            }
        }
    }

    public void checkForRightArrowBtnTap(boolean soundEnabled, boolean isShopOpen, boolean isGemScreenOpen) {
        if (rightArrowBtn.getTappedBefore()) {
            rightArrowBtn.setTexture(assets.assetManager.get(Assets.right_arrow_btn_active, Texture.class));
        } else {
            rightArrowBtn.setTexture(assets.assetManager.get(Assets.right_arrow_btn_inactive, Texture.class));
        }
        if (rightArrowBtn.getReleased()) {
            if (isShopOpen && !isGemScreenOpen) {

                if (selectedShopElement == ShopElement.shipCount)
                    selectedShopElement = 0;
                else {
                    selectedShopElement += 1;
                }

                ship.setElementAnimation(selectedShopElement);
            }
            if (isGemScreenOpen && !isShopOpen) {
                isAdLoaded = false;
                if (selectedGemScreenElement == GemElement.gemOptionsCount) {
                    selectedGemScreenElement = 0;
                } else {
                    selectedGemScreenElement += 1;
                }
            }

            if (soundEnabled) {
                pauseSound.stop();
                pauseSound.play();
            }

        }
    }

    public void checkForSelectButtonTap(boolean soundEnabled, boolean isShopOpen, boolean isGemScreenOpen, Prefs prefs) {
        if (isShopOpen) {
            if (selectButton.getTappedBefore()) {
                selectButton.setTexture(assets.assetManager.get(Assets.select_button_active, Texture.class));
            } else {
                selectButton.setTexture(assets.assetManager.get(Assets.select_button_inactive, Texture.class));
            }
            if (selectButton.getReleased() && !isGemScreenOpen) {
                prefs.setShip(selectedShopElement);
                if (soundEnabled) {
                    playSound.stop();
                    playSound.play();
                }
            }
        } else if (isGemScreenOpen) {
            if (selectButton.getTappedBefore()) {
                if (gemsPurchased) {
                    gemCount = prefs.getGemCount();
                    prePurchaseGemCount = gemCount;
                }

                selectButton.setTexture(assets.assetManager.get(Assets.blank_active, Texture.class));
            } else {
                selectButton.setTexture(assets.assetManager.get(Assets.blank_inactive, Texture.class));
            }
            if (selectButton.getReleased()) {

                buyItem(gemElement.getSKU());

            }
        }
    }

    public void checkForAdButtonTap(Main game, boolean soundEnabled, Prefs prefs, MASound activeSong) {
        gemCount = prefs.getGemCount();

        if (selectButton.getTappedBefore()) {
            selectButton.setTexture(assets.assetManager.get(Assets.ad_button_active, Texture.class));

        } else {
            selectButton.setTexture(assets.assetManager.get(ad_button_inactive, Texture.class));
        }
        if (selectButton.getReleased()) {
            adRequested = true;

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                game.requestHandlerAndroid.setAdFinished(false);
                game.requestHandlerAndroid.loadAd();
                isAdLoaded = true;
            } else if (Gdx.app.getType() == Application.ApplicationType.iOS) {
                game.requestHandlerIOS.setAdFinished(false);
                game.requestHandlerIOS.loadAd();
                isAdLoaded = true;

                activeSong.setVolume(0);
            }
        }

        if (Gdx.app.getType() == Application.ApplicationType.Android && game.requestHandlerAndroid.isAdLoaded() && isAdLoaded) {
            game.requestHandlerAndroid.showAd(false, soundEnabled, prefs, gemSound);
            isAdLoaded = false;
        } else if (Gdx.app.getType() == Application.ApplicationType.iOS && game.requestHandlerIOS.isAdLoaded() && isAdLoaded) {
            game.requestHandlerIOS.showAd(false, soundEnabled, prefs, gemSound);
            isAdLoaded = false;
        }
        if (Gdx.app.getType() == Application.ApplicationType.Android && game.requestHandlerAndroid.isAdFinished()) {
            adRequested = false;
        } else if (Gdx.app.getType() == Application.ApplicationType.iOS && game.requestHandlerIOS.isAdFinished()) {
            adRequested = false;
            activeSong.setVolume(1);
        }
    }

    public void checkForShopBuyButtonTap(boolean soundEnabled, int itemPrice, Prefs prefs) {
        if (selectButton.getTappedBefore()) {
            selectButton.setTexture(assets.assetManager.get(Assets.blank_active, Texture.class));

        }
        else {
            selectButton.setTexture(assets.assetManager.get(Assets.blank_inactive, Texture.class));
        }
        if (selectButton.getReleased()) {

            if (prefs.getGemCount() >= itemPrice) {
                selectButton.setTexture(assets.assetManager.get(Assets.select_button_inactive, Texture.class));
                prefs.setUnlockedShips(prefs.getUnlockedShips() + ShopElement.getIdBySlot(selectedShopElement));
                prefs.setGemCount(prefs.getGemCount() - itemPrice);

                if (soundEnabled) {
                    gemSound.stop();
                    gemSound.play();
                }
            } else {
                if (soundEnabled) {
                    errorSound.stop(); // stops static sound from playing when spam-tapping
                    errorSound.play();
                }
            }
        }
    }

    public void drawContinueScreen(Main game, BitmapFont confirmScreenFont) {
        continueScreenOpen = true;

        game.batch.draw(pauseMenuBack, MENU_BACK_X, MENU_BACK_Y, MENU_BACK_WIDTH, MENU_BACK_HEIGHT);
        game.batch.draw(yesButton.getTexture(), YES_BUTTON_X, YES_BUTTON_Y, YES_BUTTON_WIDTH, YES_BUTTON_HEIGHT);
        game.batch.draw(noButton.getTexture(), NO_BUTTON_X, NO_BUTTON_Y, NO_BUTTON_WIDTH, NO_BUTTON_HEIGHT);

        gl.setText(confirmScreenFont, "WATCH AN AD \n\nTO CONTINUE?", Color.valueOf(PURPLE_COLOR_HEX), SCREEN_WIDTH, Align.center, true);
        confirmScreenFont.draw(game.batch, "WATCH AN AD \n\nTO CONTINUE?", SCREEN_WIDTH / 2f - (gl.width / 2), CONFIRM_LEAVE_FONT_Y);
    }

    public void skipGemPurchaseCount() {
        if (gemsPurchased) {
            prePurchaseGemCount = prefs.getGemCount();
            gemsPurchased = false;
        }
    }

    public void drawShopScreen(Main game, boolean soundEnabled, float delta, SpriteBatch batch, BitmapFont gemCountFont, Prefs prefs, MASound activeSong) {

        if(isShipMenuOpen){
            drawShipMenu(game, soundEnabled, delta, batch, gemCountFont, prefs);
        }
        else if(isGemMenuOpen){
            drawGemMenu(game, soundEnabled, delta, batch, prefs, activeSong);
        }
        else{
            stateTime += delta / 6;

            game.batch.draw(shopBack, SHOP_BACK_X, SHOP_BACK_Y, SHOP_BACK_WIDTH, SHOP_BACK_HEIGHT);

            gl.setText(storeFont, "SHOP", Color.valueOf(PURPLE_COLOR_HEX), SCREEN_WIDTH, Align.center, true);
            storeFont.draw(game.batch, "SHOP", (SCREEN_WIDTH - gl.width) / 2, SHOP_BACK_Y + SHOP_BACK_HEIGHT * .8f + gl.height / 2);

            game.batch.draw(xButton.getTexture(), X_BUTTON_X, X_BUTTON_Y, X_BUTTON_WIDTH, X_BUTTON_HEIGHT);

            game.batch.draw(shipButton.getTexture(), SHIP_BUTTON_X, SHIP_BUTTON_Y, SHIP_BUTTON_WIDTH, SHIP_BUTTON_HEIGHT);
            game.batch.draw(gemButton.getTexture(), GEM_BUTTON_X, GEM_BUTTON_Y, GEM_BUTTON_WIDTH, GEM_BUTTON_HEIGHT);

            gemButtonAnim.drawAnim(gemButtonAnimation, stateTime, (int) (GEM_BUTTON_X + GEM_BUTTON_WIDTH/2 - GEM_WIDTH/2), (int) (GEM_BUTTON_Y + GEM_BUTTON_HEIGHT/2 - GEM_HEIGHT/2), (int) GEM_WIDTH, (int) GEM_HEIGHT, true, game.batch);
            shipButtonAnim.drawAnim(shipButtonAnimation, stateTime, (int) (SHIP_BUTTON_X + SHIP_BUTTON_WIDTH/2 - SHIP_WIDTH/2), (int) (SHIP_BUTTON_Y + SHIP_BUTTON_HEIGHT/2 - SHIP_HEIGHT/2), (int) SHIP_WIDTH, (int) SHIP_HEIGHT, true, game.batch);

            gl.setText(gemCountFont, " x " + prefs.getGemCount(), Color.WHITE, SCREEN_WIDTH, Align.center, true);
            gemCountFont.draw(game.batch, " x " + prefs.getGemCount(), SHOP_BACK_X + SHOP_BACK_WIDTH*.95f - gl.width, GEM_COUNT_SHOP_Y);

            glx = 0.945f * (SHOP_BACK_X + SHOP_BACK_WIDTH - gl.width) - GEM_ICON_WIDTH;


            game.batch.draw(gemIcon, glx, GEM_ICON_SHOP_Y, GEM_ICON_WIDTH, GEM_ICON_HEIGHT);
        }

        if(checkForShipButtonTap(soundEnabled)){
            isShipMenuOpen = true;
        }
        if(checkForGemButtonTap(soundEnabled)) {
            isGemMenuOpen = true;
        }
    }

    public void drawShipMenu(Main game, boolean soundEnabled, float delta, SpriteBatch batch, BitmapFont gemCountFont, Prefs prefs) {
        skipGemPurchaseCount();

        stateTime += delta / 6;

        game.batch.draw(shopBack, SHOP_BACK_X, SHOP_BACK_Y, SHOP_BACK_WIDTH, SHOP_BACK_HEIGHT);
        game.batch.draw(xButton.getTexture(), X_BUTTON_X, X_BUTTON_Y, X_BUTTON_WIDTH, X_BUTTON_HEIGHT);

        game.batch.draw(leftArrowBtn.getTexture(), LEFT_ARROW_BTN_X, LEFT_ARROW_BTN_Y, LEFT_ARROW_BTN_WIDTH, LEFT_ARROW_BTN_HEIGHT);
        game.batch.draw(rightArrowBtn.getTexture(), RIGHT_ARROW_BTN_X, RIGHT_ARROW_BTN_Y, RIGHT_ARROW_BTN_WIDTH, RIGHT_ARROW_BTN_HEIGHT);
        game.batch.draw(selectButton.getTexture(), SELECT_BUTTON_X, SELECT_BUTTON_Y, SELECT_BUTTON_WIDTH, SELECT_BUTTON_HEIGHT);

        game.batch.draw(backButton.getTexture(), BACK_BUTTON_X, BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);

        gl.setText(gemCountFont, " x " + prefs.getGemCount(), Color.WHITE, SCREEN_WIDTH, Align.center, true);
        glx = SHOP_BACK_X + SHOP_BACK_WIDTH*.95f - gl.width;

        gemCountFont.draw(game.batch, " x " + prefs.getGemCount(), SHOP_BACK_X + SHOP_BACK_WIDTH*.95f - gl.width, GEM_COUNT_SHOP_Y);
        game.batch.draw(gemIcon, glx*.945f, GEM_ICON_SHOP_Y, GEM_ICON_WIDTH, GEM_ICON_HEIGHT);

        checkForLeftArrowBtnTap(soundEnabled, true, false);
        checkForRightArrowBtnTap(soundEnabled, true, false);

        if(checkForBackButtonTap(soundEnabled)){
            isShipMenuOpen = false;
        }

        if (!prefs.getUnlockedShips().contains(ShopElement.getIdBySlot(selectedShopElement)) && !String.valueOf(selectedShopElement).equals(SHIP_ID)) {
            selectButton.setTexture(assets.assetManager.get(Assets.blank_inactive, Texture.class));
            gl.setText(buyFont, Integer.toString(ShopElement.getPriceByShipSlot(selectedShopElement)), Color.valueOf(PURPLE_COLOR_HEX), SCREEN_WIDTH, Align.center, true);
            buyFont.draw(game.batch, Integer.toString(ShopElement.getPriceByShipSlot(selectedShopElement)), (SCREEN_WIDTH - gl.width) / 2, SELECT_BUTTON_Y + gl.height / 2 + SELECT_BUTTON_HEIGHT / 2);
            game.batch.draw(gemIcon, (SCREEN_WIDTH + gl.width) / 2 + GEM_ICON_WIDTH / 2, SELECT_BUTTON_Y + SELECT_BUTTON_HEIGHT / 2 - GEM_ICON_HEIGHT / 2, GEM_ICON_WIDTH, GEM_ICON_HEIGHT);

            checkForShopBuyButtonTap(soundEnabled, ShopElement.getPriceByShipSlot(selectedShopElement), prefs);

        } else {
            if (prefs.getShip() == selectedShopElement) {
                selectButton.setTexture(assets.assetManager.get(Assets.active_button, Texture.class));
            } else {
                checkForSelectButtonTap(soundEnabled, true, false, prefs);
            }
        }

        if (ship == null) {
            ship = new ShopElement(SHIP_SLOT, selectedShopElement, SHOP_BACK_X + SHOP_BACK_WIDTH / 2 - SHIP_WIDTH / 2f, SHOP_BACK_Y + SHOP_BACK_HEIGHT / 2 - SHIP_HEIGHT / 2f, SHIP_WIDTH, SHIP_HEIGHT);
        }

        ship.render(stateTime, batch);

        gl.setText(storeFont, ship.getTitle(), Color.valueOf(PURPLE_COLOR_HEX), SCREEN_WIDTH, Align.center, true);
        storeFont.draw(game.batch, ship.getTitle(), (SCREEN_WIDTH - gl.width) / 2, SHOP_BACK_Y + SHOP_BACK_HEIGHT * .8f + gl.height / 2);

    }

    public void drawGemMenu(Main game, boolean soundEnabled, float delta, SpriteBatch batch, Prefs prefs, MASound activeSong) {
        gemCountFont.setColor(1, 1, 1, 0.8f);

        if (gemCount == 0) {
            gemCount = prefs.getGemCount();
        }
        else if (prePurchaseGemCount != 0 && gemCount < prefs.getGemCount()) {
            gemCount = prePurchaseGemCount;
        }

        game.batch.draw(shopBack, SHOP_BACK_X, SHOP_BACK_Y, SHOP_BACK_WIDTH, SHOP_BACK_HEIGHT);

        if ((Gdx.app.getType() == Application.ApplicationType.Android && game.requestHandlerAndroid.adFailedToLoad()) || (Gdx.app.getType() == Application.ApplicationType.iOS && game.requestHandlerIOS.adFailedToLoad())) {
            adRequested = false;
        }
        if (!adRequested || (Gdx.app.getType() == Application.ApplicationType.Android && game.requestHandlerAndroid.adFailedToLoad()) || Gdx.app.getType() == Application.ApplicationType.iOS && game.requestHandlerIOS.adFailedToLoad()) {
            game.batch.draw(xButton.getTexture(), X_BUTTON_X, X_BUTTON_Y, X_BUTTON_WIDTH, X_BUTTON_HEIGHT);
            game.batch.draw(backButton.getTexture(), BACK_BUTTON_X, BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);

            gl.setText(gemCountFont, " x " + prefs.getGemCount(), Color.WHITE, SCREEN_WIDTH, Align.center, true);
            glx = SHOP_BACK_X + SHOP_BACK_WIDTH*.95f - gl.width;

            gemCountFont.draw(game.batch, " x " + prefs.getGemCount(), SHOP_BACK_X + SHOP_BACK_WIDTH*.95f - gl.width, GEM_COUNT_SHOP_Y);
            game.batch.draw(gemIcon, glx*.945f, GEM_ICON_SHOP_Y, GEM_ICON_WIDTH, GEM_ICON_HEIGHT);


            game.batch.draw(leftArrowBtn.getTexture(), LEFT_ARROW_BTN_X, LEFT_ARROW_BTN_Y, LEFT_ARROW_BTN_WIDTH, LEFT_ARROW_BTN_HEIGHT);
            game.batch.draw(rightArrowBtn.getTexture(), RIGHT_ARROW_BTN_X, RIGHT_ARROW_BTN_Y, RIGHT_ARROW_BTN_WIDTH, RIGHT_ARROW_BTN_HEIGHT);
            game.batch.draw(selectButton.getTexture(), SELECT_BUTTON_X, SELECT_BUTTON_Y, SELECT_BUTTON_WIDTH, SELECT_BUTTON_HEIGHT);

            checkForLeftArrowBtnTap(soundEnabled, false, true);
            checkForRightArrowBtnTap(soundEnabled, false, true);

            if(checkForBackButtonTap(soundEnabled)){
                isGemMenuOpen = false;
            }

            if (gemElement == null) {
                gemElement = new GemElement(AD_ID, selectedGemScreenElement, SHOP_BACK_X + SHOP_BACK_WIDTH / 2 - GEM_SHOP_WIDTH / 2f, SHOP_BACK_Y + SHOP_BACK_HEIGHT / 2 - GEM_SHOP_HEIGHT / 2f, GEM_SHOP_WIDTH, GEM_SHOP_HEIGHT);
            }
            gemElement.render(stateTime, batch);
            gemElement.setElementAnimation(selectedGemScreenElement);

            gl.setText(storeFont, gemElement.getTitle(), Color.valueOf(PURPLE_COLOR_HEX), SCREEN_WIDTH, Align.center, true);
            storeFont.draw(game.batch, gemElement.getTitle(), (SCREEN_WIDTH - gl.width) / 2, SHOP_BACK_Y + SHOP_BACK_HEIGHT * .8f + gl.height / 2);

        }
        else{
            gl.setText(storeFont, "LOADING...", Color.valueOf(PURPLE_COLOR_HEX), SCREEN_WIDTH, Align.center, true);
            storeFont.draw(game.batch, "LOADING...", (SCREEN_WIDTH - gl.width) / 2, SHOP_BACK_Y + SHOP_BACK_HEIGHT * .8f + gl.height / 2);

        }

        if (selectedGemScreenElement == AD_ID) {
            checkForAdButtonTap(game, soundEnabled, prefs, activeSong);

        } else {
            checkForSelectButtonTap(soundEnabled, false, true, prefs);
            gl.setText(buyFont, '$' + Float.toString(GemElement.getPriceByElementId(selectedGemScreenElement)), Color.valueOf(PURPLE_COLOR_HEX), SCREEN_WIDTH, Align.center, true);
            buyFont.draw(game.batch, '$' + Float.toString(GemElement.getPriceByElementId(selectedGemScreenElement)), (SCREEN_WIDTH - gl.width) / 2, SELECT_BUTTON_Y + gl.height / 2 + SELECT_BUTTON_HEIGHT / 2);
        }

        if (gemsPurchased) {
            getPurchasedGems(delta, gemCount, purchasedGemCount, soundEnabled);
        }
    }

    public void drawCreditsScreen(Main game) {
        game.batch.draw(pauseMenuBack, SHOP_BACK_X, MENU_BACK_Y, SHOP_BACK_WIDTH, MENU_BACK_HEIGHT);
        game.batch.draw(xButtonCredits.getTexture(), X_BUTTON_X, X_BUTTON_CREDITS_Y, X_BUTTON_WIDTH, X_BUTTON_HEIGHT);

        gl.setText(creditsFont, "Designed and programmed by\nJames Macre\n\nwith special thanks to\nthe musical talents of\nSamuel Mossa", Color.valueOf(PURPLE_COLOR_HEX), SCREEN_WIDTH, Align.center, true);
        creditsFont.draw(game.batch, "Designed and programmed by\nJames Macre\n\nwith special thanks to\nthe musical talents of\nSamuel Mossa", (SCREEN_WIDTH - gl.width) / 2, MENU_BACK_Y + MENU_BACK_HEIGHT * .5f + gl.height / 2);
    }

    public void drawPauseScreen(Main game, BitmapFont scoreFont, BitmapFont gemCountFont, Prefs prefs) {
        isPauseScreenOpen = true;
        game.batch.draw(pauseMenuBack, MENU_BACK_X, MENU_BACK_Y, MENU_BACK_WIDTH, MENU_BACK_HEIGHT);

        game.batch.draw(soundButton.getTexture(), SOUND_BUTTON_X, SOUND_BUTTON_Y, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);
        game.batch.draw(playButton.getTexture(), PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        game.batch.draw(homeButton.getTexture(), HOME_BUTTON_X, HOME_BUTTON_Y, HOME_BUTTON_WIDTH, HOME_BUTTON_HEIGHT);

        scoreFont.draw(game.batch, "HIGH SCORE: " + prefs.getHighScore(), MENU_SCORE_X, MENU_SCORE_Y);

        gl.setText(gemCountFont, " x " + prefs.getGemCount(), Color.WHITE, SCREEN_WIDTH, Align.center, true);
        gemCountFont.draw(game.batch, " x " + prefs.getGemCount(), 0.94f * (MENU_BACK_X + MENU_BACK_WIDTH - gl.width), GEM_COUNT_MENU_Y);
        game.batch.draw(gemIcon, 0.945f * (MENU_BACK_X + MENU_BACK_WIDTH - gl.width) - GEM_ICON_WIDTH, GEM_ICON_MENU_Y, GEM_ICON_WIDTH, GEM_ICON_HEIGHT);
    }

    public void drawConfirmLeave(Main game, BitmapFont confirmScreenFont) {
        game.batch.draw(pauseMenuBack, MENU_BACK_X, MENU_BACK_Y, MENU_BACK_WIDTH, MENU_BACK_HEIGHT);
        game.batch.draw(yesButton.getTexture(), YES_BUTTON_X, YES_BUTTON_Y, YES_BUTTON_WIDTH, YES_BUTTON_HEIGHT);
        game.batch.draw(noButton.getTexture(), NO_BUTTON_X, NO_BUTTON_Y, NO_BUTTON_WIDTH, NO_BUTTON_HEIGHT);

        gl.setText(confirmScreenFont, "ARE YOU SURE YOU \n\n  WANT TO LEAVE?", Color.valueOf(PURPLE_COLOR_HEX), SCREEN_WIDTH, Align.center, true);
        confirmScreenFont.draw(game.batch, "ARE YOU SURE YOU \n\n  WANT TO LEAVE?", SCREEN_WIDTH / 2 - (gl.width / 2), CONFIRM_LEAVE_FONT_Y);


    }

    public void setConfirmLeaveScreenOpen(boolean isOpen) {
        confirmLeaveScreenOpen = isOpen;
    }

    public boolean isConfirmLeaveScreenOpen() {
        return confirmLeaveScreenOpen;
    }

    public void setContinueScreenOpen(boolean isOpen) {
        continueScreenOpen = isOpen;
    }

    public boolean isContinueScreenOpen() {
        return continueScreenOpen;
    }

    public void drawReplayScreen(Main game, BitmapFont scoreFont, BitmapFont gameOverFont, BitmapFont gemCountFont, boolean newHighScore,
                                 int replayScreenGemCount, int gemsFromScore, int gemsCaught, int totalGemsEarned, boolean gemCountStarted, int finalScore, int highScore, boolean scoreCountStarted, boolean recapStarted, boolean recapComplete, float deltaP) {
        scoreFont.setColor(Color.valueOf(PURPLE_COLOR_HEX));

        game.batch.draw(pauseMenuBack, MENU_BACK_X, MENU_BACK_Y, MENU_BACK_WIDTH, MENU_BACK_HEIGHT);

        if (!confirmLeaveScreenOpen) {
            if (isReplayScreenOpen) {
                game.batch.draw(replayButton.getTexture(), PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
                game.batch.draw(homeButton.getTexture(), HOME_BUTTON_X, HOME_BUTTON_Y, HOME_BUTTON_WIDTH, HOME_BUTTON_HEIGHT);
                game.batch.draw(ingameShopButton.getTexture(), SOUND_BUTTON_X, SOUND_BUTTON_Y, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);
            }

            if (newHighScore) {
                gl.setText(gameOverFont, "HIGH SCORE", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                gameOverFont.draw(game.batch, "HIGH SCORE", (SCREEN_WIDTH - gl.width) / 2, GAME_OVER_TEXT_Y - Gdx.graphics.getSafeInsetTop() + gl.height / 2);


            } else {
                gl.setText(gameOverFont, "GAME OVER", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                gameOverFont.draw(game.batch, "GAME OVER", (SCREEN_WIDTH - gl.width) / 2, GAME_OVER_TEXT_Y + gl.height / 2 - Gdx.graphics.getSafeInsetTop());

            }

            scoreFont.draw(game.batch, "HIGH SCORE: " + highScore, MENU_SCORE_X, MENU_SCORE_Y);

            gl.setText(gemCountFont, " x " + replayScreenGemCount, Color.WHITE, SCREEN_WIDTH, Align.center, true);
            gemCountFont.draw(game.batch, " x " + replayScreenGemCount, 0.95f * (MENU_BACK_X + MENU_BACK_WIDTH - gl.width), GEM_COUNT_MENU_Y);
            game.batch.draw(gemIcon, 0.945f * (MENU_BACK_X + MENU_BACK_WIDTH - gl.width) - GEM_ICON_WIDTH, GEM_ICON_MENU_Y, GEM_ICON_WIDTH, GEM_ICON_HEIGHT);
        }

        if (isRecapScreenOpen) {
            gl.setText(gemCountFont, "FINAL SCORE: " + finalScore, Color.WHITE, SCREEN_WIDTH, Align.center, false);
            gemCountFont.draw(game.batch, "FINAL SCORE: " + finalScore, (SCREEN_WIDTH - gl.width) / 2, (SCREEN_HEIGHT - gl.height) / 2 + gemCountFont.getLineHeight() * 3f);
            if (gemCountStarted || recapComplete) {
                gemCountFont.draw(game.batch, "GEMS CAUGHT:  " + gemsCaught, (SCREEN_WIDTH - gl.width) / 2, (SCREEN_HEIGHT - gl.height) / 2 + (gemCountFont.getLineHeight()));
            }

            if (scoreCountStarted || recapComplete) {
                gemCountFont.draw(game.batch, "SCORE BONUS:  " + gemsFromScore, (SCREEN_WIDTH - gl.width) / 2, (SCREEN_HEIGHT - gl.height) / 2 - gemCountFont.getLineHeight());

            }

            if (recapStarted || recapComplete) {
                gemCountFont.draw(game.batch, "GEMS EARNED:  " + totalGemsEarned, (SCREEN_WIDTH - gl.width) / 2, (SCREEN_HEIGHT - gl.height) / 2 - gemCountFont.getLineHeight() * 3f);

                if (recapComplete) {
                    runTapToContinueBlinking(game, deltaP);
                }
            }
        }
    }

    public void runTapToContinueBlinking(Main game, float deltaP) {
        tapToContinueBlinkingTimer += deltaP;

        if (tapToContinueBlinkingTimer < 0f && tapToContinueBlinkingTimer >= -0.5f) {
            gemCountFont.draw(game.batch, "TAP TO CONTINUE", (SCREEN_WIDTH - gl.width) / 2, MENU_BACK_Y + gemCountFont.getLineHeight() * 2.5f);
        } else if (tapToContinueBlinkingTimer > 0) {
            tapToContinueBlinkingTimer = TAP_TO_CONTINUE_BLINKING_TIMER * 2f;
        }
    }

    public int getPurchasedGems(float deltaP, int gemCount, int purchasedGemCount, boolean soundEnabled) {
        if (prePurchaseGemCount == 0) {
            prePurchaseGemCount = gemCount;
        }

        if (gemPurchaseTimer < 0) {
            gemPurchaseTimer += deltaP;

        } else {
            if (prePurchaseGemCount < prefs.getGemCount()) {
                if (purchasedGemCount == 1000) {
                    prePurchaseGemCount += 10;
                } else if (purchasedGemCount == 10000) {
                    prePurchaseGemCount += 50;
                } else {
                    prePurchaseGemCount += 500;
                }

                if (soundEnabled) {
                    playSound.play();
                }
            } else {
                gemsPurchased = false;
                prePurchaseGemCount = 0;
            }
        }
        return prePurchaseGemCount;
    }

    private void initPurchaseManager() {
        // the purchase manager config here in the core project works if your SKUs are the same in every
        // payment system. If this is not the case, inject them like the PurchaseManager is injected
        PurchaseManagerConfig pmc = new PurchaseManagerConfig();
        pmc.addOffer(new Offer().setType(OfferType.CONSUMABLE).setIdentifier(GEM_10K_SKU));
        pmc.addOffer(new Offer().setType(OfferType.CONSUMABLE).setIdentifier(GEM_75K_SKU));
        pmc.addOffer(new Offer().setType(OfferType.CONSUMABLE).setIdentifier(GEM_200K_SKU));
        pmc.addOffer(new Offer().setType(OfferType.CONSUMABLE).setIdentifier(GEM_500K_SKU));

        game.purchaseManager.install(new MyPurchaseObserver(), pmc, true);
    }

    private void updateGuiWhenPurchaseManInstalled(String errorMessage) {
//        buyConsumable.updateFromManager();
    }

    private void buyItem(String sku) {
        game.purchaseManager.purchase(sku);
    }

    private class MyPurchaseObserver implements PurchaseObserver {

        @Override
        public void handleInstall() {
            Gdx.app.log("IAP", "Installed");

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    updateGuiWhenPurchaseManInstalled(null);
                }
            });
        }

        @Override
        public void handleInstallError(final Throwable e) {
            Gdx.app.error("IAP", "Error when trying to install PurchaseManager", e);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    updateGuiWhenPurchaseManInstalled(e.getMessage());
                }
            });
        }

        @Override
        public void handleRestore(Transaction[] transactions) {
            if (transactions != null && transactions.length > 0) {
                for (Transaction t : transactions) {
                    handlePurchase(t);
                }
            }
        }


        @Override
        public void handleRestoreError(Throwable e) {

        }

        @Override
        public void handlePurchase(final Transaction transaction) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    if (transaction.isPurchased()) {
                        switch (transaction.getIdentifier()) {
                            case GEM_10K_SKU:
                                gemsPurchased = true;
                                purchasedGemCount = 10000;
                                prefs.setGemCount(prefs.getGemCount() + purchasedGemCount);
                                break;

                            case GEM_75K_SKU:
                                purchasedGemCount = 75000;
                                gemsPurchased = true;
                                prefs.setGemCount(prefs.getGemCount() + purchasedGemCount);
                                break;

                            case GEM_200K_SKU:
                                purchasedGemCount = 200000;
                                gemsPurchased = true;
                                prefs.setGemCount(prefs.getGemCount() + purchasedGemCount);
                                break;

                            case GEM_500K_SKU:
                                purchasedGemCount = 500000;
                                gemsPurchased = true;
                                prefs.setGemCount(prefs.getGemCount() + purchasedGemCount);
                                break;
                        }
                    }
                }
            });
        }

        @Override
        public void handlePurchaseError(Throwable e) {
            showErrorOnMainThread("Error on buying:\n" + e.getMessage());
        }

        @Override
        public void handlePurchaseCanceled() {

        }

        private void showErrorOnMainThread(final String message) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    // show a dialog here...
                }
            });
        }
    }
}

