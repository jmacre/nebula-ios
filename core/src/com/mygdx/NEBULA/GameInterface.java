package com.mygdx.NEBULA;

import static com.mygdx.NEBULA.ShopElement.SHIP_ID;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;

public class GameInterface extends GameElements {
    Assets assets;
    Button pauseButton;
    Button homeButton;
    Button playButton;
    Button replayButton;
    Button startButton;

    Button shopButton;
    Button leftArrow;
    Button rightArrow;
    Button selectButton;

    ShopElement ship;

    Sprite titleTexture;
    Button tsSoundButton;
    Button xButton;
    Button yesButton;
    Button noButton;
    BitmapFont scoreFont;
    Sprite soundOnButton;
    Sprite soundOffButton;
    Sprite heart1;
    Sprite heart2;
    Sprite heart3;
    Sprite heartMissing1, heartMissing2, heartMissing3, gemIcon, pauseMenuBack, shopBack;
    GlyphLayout gl;
    Boolean confirmLeaveScreenOpen = false;

    public Prefs prefs = new Prefs();

    float tsSoundButtonY;
    float scoreY;
    float gemIconY;

    float topElemY;
    float stateTime = 0f;
    int selectedElement = prefs.getShip();

    public FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    public GameInterface(Assets assets) {
        this.assets = assets;
        parameter.size = SCREEN_WIDTH / 40;
        startButton = new Button(assets.assetManager.get(Assets.start_button_inactive_clear, Texture.class), START_BUTTON_X, START_BUTTON_Y_TRANSITIONED, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);

        shopButton = new Button(assets.assetManager.get(Assets.shop_button_inactive_clear, Texture.class), SHOP_BUTTON_X, SHOP_BUTTON_Y_TRANSITIONED, SHOP_BUTTON_WIDTH, SHOP_BUTTON_HEIGHT);
        soundButton = new Button(assets.assetManager.get(Assets.sound_on_button_active, Texture.class), SOUND_BUTTON_X, SOUND_BUTTON_Y, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);

        leftArrow = new Button(assets.assetManager.get(Assets.left_arrow_inactive, Texture.class), LEFT_ARROW_X, LEFT_ARROW_Y, LEFT_ARROW_WIDTH, LEFT_ARROW_HEIGHT);
        rightArrow = new Button(assets.assetManager.get(Assets.right_arrow_inactive, Texture.class), RIGHT_ARROW_X, RIGHT_ARROW_Y, RIGHT_ARROW_WIDTH, RIGHT_ARROW_HEIGHT);
        selectButton = new Button(assets.assetManager.get(Assets.select_button_inactive, Texture.class), SELECT_BUTTON_X, SELECT_BUTTON_Y, SELECT_BUTTON_WIDTH, SELECT_BUTTON_HEIGHT);

        xButton = new Button(assets.assetManager.get(Assets.x_button, Texture.class), X_BUTTON_X, X_BUTTON_Y, X_BUTTON_WIDTH, X_BUTTON_HEIGHT, X_BUTTON_WIDTH / 2, X_BUTTON_HEIGHT / 2);
        yesButton = new Button(assets.assetManager.get(Assets.yes_button_inactive, Texture.class), YES_BUTTON_X, YES_BUTTON_Y, YES_BUTTON_WIDTH, YES_BUTTON_HEIGHT);
        noButton = new Button(assets.assetManager.get(Assets.no_button_inactive, Texture.class), NO_BUTTON_X, NO_BUTTON_Y, NO_BUTTON_WIDTH, NO_BUTTON_HEIGHT);

        textParameter.size = SCREEN_WIDTH / 14;
        storeFont = generator.generateFont(textParameter);
        gemSound = assets.assetManager.get(Assets.gem_sound, Sound.class);
        errorSound = assets.assetManager.get(Assets.error_sound, Sound.class);

        textParameter.size = (int) SELECT_BUTTON_WIDTH/8;
        buyFont = generator.generateFont(textParameter);

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            tsSoundButtonY = TS_SOUND_BUTTON_Y_AND;
            scoreY = SCORE_Y_AND;
            topElemY = TOP_ELEM_Y_AND;
            gemIconY = GEM_ICON_Y_AND;
        }
        else {
            tsSoundButtonY = TS_SOUND_BUTTON_Y_IOS;
            scoreY = SCORE_Y_IOS;
            topElemY = TOP_ELEM_Y_IOS;
            gemIconY = GEM_ICON_Y_IOS;
        }

        tsSoundButton = new Button(assets.assetManager.get(Assets.sound_off_button_ts, Texture.class), TS_SOUND_BUTTON_X, tsSoundButtonY, TS_SOUND_BUTTON_WIDTH, TS_SOUND_BUTTON_HEIGHT, TS_SOUND_BUTTON_WIDTH / 2, TS_SOUND_BUTTON_HEIGHT / 2);
        pauseButton = new Button(assets.assetManager.get(Assets.pause_button, Texture.class), PAUSE_BUTTON_X, PAUSE_BUTTON_Y, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);
        homeButton = new Button(assets.assetManager.get(Assets.home_button_inactive, Texture.class), HOME_BUTTON_X, HOME_BUTTON_Y, HOME_BUTTON_WIDTH, HOME_BUTTON_HEIGHT);
        playButton = new Button(assets.assetManager.get(Assets.play_button_inactive, Texture.class), PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        replayButton = new Button(assets.assetManager.get(Assets.replay_button_inactive, Texture.class), PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        soundOffButton = new Sprite(assets.assetManager.get(Assets.sound_off_button_inactive, Texture.class));
        titleTexture = new Sprite(assets.assetManager.get(Assets.title_logo_clear, Texture.class));
        soundOnButton = new Sprite(assets.assetManager.get(Assets.sound_on_button_inactive, Texture.class));

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

    public void drawTitleScreen(Main game, boolean transitionInDone) {
        game.batch.draw(startButton.getTexture(), START_BUTTON_X, START_BUTTON_Y, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);
        game.batch.draw(shopButton.getTexture(), SHOP_BUTTON_X, SHOP_BUTTON_Y, SHOP_BUTTON_WIDTH, SHOP_BUTTON_HEIGHT);
        game.batch.draw(titleTexture, TITLE_LOGO_X, TITLE_LOGO_Y - TITLE_LOGO_HEIGHT, TITLE_LOGO_WIDTH, TITLE_LOGO_HEIGHT);

        if (prefs.hasSound())
            tsSoundButton.setTexture(assets.assetManager.get(Assets.sound_on_button_ts, Texture.class));
        else
            tsSoundButton.setTexture(assets.assetManager.get(Assets.sound_off_button_ts, Texture.class));

        if (transitionInDone) {
            titleTexture.setTexture(assets.assetManager.get(Assets.title_logo, Texture.class));
            game.batch.draw(tsSoundButton.getTexture(), TS_SOUND_BUTTON_X, tsSoundButtonY, TS_SOUND_BUTTON_WIDTH, TS_SOUND_BUTTON_HEIGHT);

            scoreFont.draw(game.batch, "HIGH SCORE: " + prefs.getHighScore(), SCORE_X, scoreY);
        }
    }

    public boolean checkForTSSoundButtonTap(Main game, boolean soundEnabled) {
        if (tsSoundButton.getTappedBefore()) {
            if (prefs.hasSound()) {
                tsSoundButton.setTexture(assets.assetManager.get(Assets.sound_off_button_ts, Texture.class));
            } else {
                tsSoundButton.setTexture(assets.assetManager.get(Assets.sound_on_button_ts, Texture.class));
            }
        }
        if (tsSoundButton.getReleased()) {
            if (prefs.hasSound()) {
                prefs.setSound(false);
                soundEnabled = false;
            } else {
                prefs.setSound(true);
                soundEnabled = true;
                game.playSound.play(0.2f);
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
//        startButton.setTexture(assets.assetManager.get(Assets.start_button_active_clear, Texture.class));
//        shopButton.setTexture(assets.assetManager.get(Assets.shop_button_inactive_clear, Texture.class));
//        titleTexture.setTexture(assets.assetManager.get(Assets.title_logo_clear, Texture.class));


        return false;
    }

    public boolean checkForShopButtonTap(Main game, boolean isShopOpen, boolean switchScreens, boolean soundEnabled) {
        if (!isShopOpen && !switchScreens) {
            if (shopButton.getTappedBefore()) {
                shopButton.setTexture(assets.assetManager.get(Assets.shop_button_active, Texture.class));
            } else {
                shopButton.setTexture(assets.assetManager.get(Assets.shop_button_inactive, Texture.class));
            }
            if (shopButton.getReleased()) {
                if (soundEnabled)
                    game.playSound.play(0.2f);

                return true;
            }
        }
        return false;
    }

    public boolean checkForSoundButtonTap(boolean soundEnabled, int gemCount, boolean isAlive) {
        if (gemCount == 0 || isAlive) {
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
        return soundButton.getReleased();
    }


    public boolean checkForReplayButtonTap(int gemCount) {
        if (gemCount == 0 && !getConfirmLeaveScreenOpen()) {
            if (replayButton.getTappedBefore()) {
                replayButton.setTexture(assets.assetManager.get(Assets.replay_button_active, Texture.class));
            } else {
                replayButton.setTexture(assets.assetManager.get(Assets.replay_button_inactive, Texture.class));
            }
        }
        return replayButton.getReleased();
    }

    public boolean checkForPlayButtonTap() {
        if (playButton.getTappedBefore()) {
            playButton.setTexture(assets.assetManager.get(Assets.play_button_active, Texture.class));

        } else {
            playButton.setTexture(assets.assetManager.get(Assets.play_button_inactive, Texture.class));
        }
        return playButton.getReleased();
    }

    public boolean checkForPauseButtonTap() {
        return pauseButton.getTapped();
    }

    public boolean checkForXButtonTap(Main game, boolean isShopOpen, boolean soundEnabled) {

        if (isShopOpen) {
            if (xButton.getTapped()) {
                isShopOpen = false;

                if (soundEnabled)
                    game.pauseSound.play(0.2f);
            }
        }
        return !isShopOpen;
    }

    public boolean checkForYesButtonTap() {
        if (yesButton.getTappedBefore()) {
            yesButton.setTexture(assets.assetManager.get(Assets.yes_button_active, Texture.class));
        } else {
            yesButton.setTexture(assets.assetManager.get(Assets.yes_button_inactive, Texture.class));
        }
        return yesButton.getReleased();
    }

    public void checkForNoButtonTap(Main game, boolean soundEnabled) {
        if (noButton.getTappedBefore()) {
            noButton.setTexture(assets.assetManager.get(Assets.no_button_active, Texture.class));
        } else {
            noButton.setTexture(assets.assetManager.get(Assets.no_button_inactive, Texture.class));
        }
        if (noButton.getReleased()) {
            setConfirmLeaveScreenOpen(false);
            if (soundEnabled)
                game.pauseSound.play(0.2f);
        }
    }

    public boolean checkForHomeButtonTap(int gemCount, boolean isAlive) {
        if (isAlive || gemCount == 0) {
            if (homeButton.getTappedBefore()) {
                homeButton.setTexture(assets.assetManager.get(Assets.home_button_active, Texture.class));
            } else {
                homeButton.setTexture(assets.assetManager.get(Assets.home_button_inactive, Texture.class));
            }
        }
        return homeButton.getReleased();
    }

    public void checkForLeftArrowTap(Main game, boolean soundEnabled) {
        if (leftArrow.getTappedBefore()) {
            leftArrow.setTexture(assets.assetManager.get(Assets.left_arrow_active, Texture.class));
        } else {
            leftArrow.setTexture(assets.assetManager.get(Assets.left_arrow_inactive, Texture.class));
        }
        if (leftArrow.getReleased()) {

            if (selectedElement == 0) {
                selectedElement = ShopElement.shipCount;
            } else {
                selectedElement -= 1;
            }

            ship.setElementAnimation(selectedElement);
            if (soundEnabled)
                game.pauseSound.play(0.2f);
        }
    }

    public void checkForRightArrowTap(Main game, boolean soundEnabled) {
        if (rightArrow.getTappedBefore()) {
            rightArrow.setTexture(assets.assetManager.get(Assets.right_arrow_active, Texture.class));
        } else {
            rightArrow.setTexture(assets.assetManager.get(Assets.right_arrow_inactive, Texture.class));
        }
        if (rightArrow.getReleased()) {
            if (selectedElement == ShopElement.shipCount)
                selectedElement = 0;
            else {
                selectedElement += 1;
            }
            ship.setElementAnimation(selectedElement);

            if (soundEnabled)
                game.pauseSound.play(0.2f);
        }
    }

    public void checkForSelectButtonTap(Main game, boolean soundEnabled) {
        if (selectButton.getTappedBefore()) {
            selectButton.setTexture(assets.assetManager.get(Assets.select_button_active, Texture.class));
        } else {
            selectButton.setTexture(assets.assetManager.get(Assets.select_button_inactive, Texture.class));
        }
        if (selectButton.getReleased()) {
            prefs.setShip(selectedElement);

            if (soundEnabled)
                game.playSound.play(0.2f);
        }
    }
    public void checkForBuyButtonTap(Main game, boolean soundEnabled, int itemPrice){
        if (selectButton.getTappedBefore()) {
            selectButton.setTexture(assets.assetManager.get(Assets.buy_active, Texture.class));

        } else {
            selectButton.setTexture(assets.assetManager.get(Assets.buy_inactive, Texture.class));
        }
        if (selectButton.getReleased()) {
            if(prefs.getGemCount() >= itemPrice) {
                selectButton.setTexture(assets.assetManager.get(Assets.select_button_inactive, Texture.class));
                prefs.setUnlockedShips(prefs.getUnlockedShips() + selectedElement);
                prefs.setGemCount(prefs.getGemCount() - itemPrice);

                if (soundEnabled)
                    gemSound.play(0.2f);
            }
            else{
                if(soundEnabled){
                    errorSound.play(0.2f);
                }
            }


        }
    }

    public void drawShopScreen(Main game, boolean soundEnabled, float delta, SpriteBatch batch, BitmapFont gemCountFont) {
        stateTime += delta / 6;

        game.batch.draw(shopBack, SHOP_BACK_X, SHOP_BACK_Y, SHOP_BACK_WIDTH, SHOP_BACK_HEIGHT);
        game.batch.draw(xButton.getTexture(), X_BUTTON_X, X_BUTTON_Y, X_BUTTON_WIDTH, X_BUTTON_HEIGHT);

        game.batch.draw(leftArrow.getTexture(), LEFT_ARROW_X, LEFT_ARROW_Y, LEFT_ARROW_WIDTH, LEFT_ARROW_HEIGHT);
        game.batch.draw(rightArrow.getTexture(), RIGHT_ARROW_X, RIGHT_ARROW_Y, RIGHT_ARROW_WIDTH, RIGHT_ARROW_HEIGHT);
        game.batch.draw(selectButton.getTexture(), SELECT_BUTTON_X, SELECT_BUTTON_Y, SELECT_BUTTON_WIDTH, SELECT_BUTTON_HEIGHT);

        game.batch.draw(gemIcon, GEM_ICON_SHOP_X, GEM_ICON_SHOP_Y, GEM_ICON_WIDTH, GEM_ICON_HEIGHT);
        gemCountFont.draw(game.batch, " x " + prefs.getGemCount(), GEM_COUNT_SHOP_X, GEM_COUNT_SHOP_Y);

        checkForLeftArrowTap(game, soundEnabled);
        checkForRightArrowTap(game, soundEnabled);

        if (!prefs.getUnlockedShips().contains(Integer.toString(selectedElement)) && selectedElement != SHIP_ID) {
            selectButton.setTexture(assets.assetManager.get(Assets.buy_inactive, Texture.class));

            gl.setText(buyFont, Integer.toString(ShopElement.getPriceByShipId(selectedElement)), Color.valueOf(PURPLE_COLOR_HEX), SCREEN_WIDTH, Align.center, true);
            buyFont.draw(game.batch, Integer.toString(ShopElement.getPriceByShipId(selectedElement)), (SCREEN_WIDTH - gl.width) / 2, SELECT_BUTTON_Y + gl.height / 2 + SELECT_BUTTON_HEIGHT/2);
            game.batch.draw(gemIcon, (SCREEN_WIDTH + gl.width) / 2 + GEM_ICON_WIDTH/2, SELECT_BUTTON_Y + SELECT_BUTTON_HEIGHT/2 - GEM_ICON_HEIGHT/2, GEM_ICON_WIDTH, GEM_ICON_HEIGHT);

            checkForBuyButtonTap(game, soundEnabled, ShopElement.getPriceByShipId(selectedElement));
        }
        else {
            if (prefs.getShip() == selectedElement) {
                selectButton.setTexture(assets.assetManager.get(Assets.active_button, Texture.class));
            } else {
                checkForSelectButtonTap(game, soundEnabled);
            }
        }

        if (ship == null) {
            ship = new ShopElement(SHIP_ID, selectedElement, SHOP_BACK_X + SHOP_BACK_WIDTH / 2 - SHIP_WIDTH / 2, SHOP_BACK_Y + SHOP_BACK_HEIGHT / 2 - SHIP_HEIGHT / 2, SHIP_WIDTH, SHIP_HEIGHT);
        }

        ship.render(stateTime, batch);

        gl.setText(storeFont, ship.getTitle(), Color.valueOf(PURPLE_COLOR_HEX), SCREEN_WIDTH, Align.center, true);
        storeFont.draw(game.batch, ship.getTitle(), (SCREEN_WIDTH - gl.width) / 2, SHOP_BACK_Y + SHOP_BACK_HEIGHT * .8f + gl.height / 2);
    }

    public void drawPauseScreen(Main game, BitmapFont scoreFont, BitmapFont gemCountFont) {
        game.batch.draw(pauseMenuBack, MENU_BACK_X, MENU_BACK_Y, MENU_BACK_WIDTH, MENU_BACK_HEIGHT);

        if (!confirmLeaveScreenOpen) {
            game.batch.draw(soundButton.getTexture(), SOUND_BUTTON_X, SOUND_BUTTON_Y, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);
            game.batch.draw(playButton.getTexture(), PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            game.batch.draw(homeButton.getTexture(), HOME_BUTTON_X, HOME_BUTTON_Y, HOME_BUTTON_WIDTH, HOME_BUTTON_HEIGHT);

            scoreFont.draw(game.batch, "HIGH SCORE: " + prefs.getHighScore(), MENU_SCORE_X, MENU_SCORE_Y);

            game.batch.draw(gemIcon, GEM_ICON_MENU_X, GEM_ICON_MENU_Y, GEM_ICON_WIDTH, GEM_ICON_HEIGHT);
            gemCountFont.draw(game.batch, " x " + prefs.getGemCount(), GEM_COUNT_MENU_X, GEM_COUNT_MENU_Y);
        }
    }

    public void drawConfirmLeave(Main game, BitmapFont confirmScreenFont) {
        game.batch.draw(yesButton.getTexture(), YES_BUTTON_X, YES_BUTTON_Y, YES_BUTTON_WIDTH, YES_BUTTON_HEIGHT);
        game.batch.draw(noButton.getTexture(), NO_BUTTON_X, NO_BUTTON_Y, NO_BUTTON_WIDTH, NO_BUTTON_HEIGHT);

        gl.setText(confirmScreenFont, "ARE YOU SURE YOU \n\n WANT TO LEAVE?", Color.valueOf(PURPLE_COLOR_HEX), MENU_BACK_WIDTH, Align.left, true);
        confirmScreenFont.draw(game.batch, "ARE YOU SURE YOU \n\n WANT TO LEAVE?", CONFIRM_LEAVE_FONT_X, CONFIRM_LEAVE_FONT_Y);
    }

    public void setConfirmLeaveScreenOpen(boolean isOpen) {
        confirmLeaveScreenOpen = isOpen;
    }

    public boolean getConfirmLeaveScreenOpen() {
        return confirmLeaveScreenOpen;
    }

    public void drawReplayScreen(Main game, BitmapFont scoreFont, BitmapFont gameOverFont, BitmapFont gemCountFont, boolean newHighScore, int replayScreengemCount, int gemCount, int score, boolean soundEnabled) {
        scoreFont.setColor(Color.valueOf(PURPLE_COLOR_HEX));

        game.batch.draw(pauseMenuBack, MENU_BACK_X, MENU_BACK_Y, MENU_BACK_WIDTH, MENU_BACK_HEIGHT);
        if (!confirmLeaveScreenOpen) {
            if (gemCount != 0 || score != 0) {

                replayButton.setTexture(assets.assetManager.get(Assets.replay_button_active, Texture.class));
                homeButton.setTexture(assets.assetManager.get(Assets.home_button_active, Texture.class));

                if (!soundEnabled)
                    soundButton.setTexture(assets.assetManager.get(Assets.sound_off_button_active, Texture.class));
                else
                    soundButton.setTexture(assets.assetManager.get(Assets.sound_on_button_active, Texture.class));
            }

            game.batch.draw(replayButton.getTexture(), PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            game.batch.draw(homeButton.getTexture(), HOME_BUTTON_X, HOME_BUTTON_Y, HOME_BUTTON_WIDTH, HOME_BUTTON_HEIGHT);
            game.batch.draw(soundButton.getTexture(), SOUND_BUTTON_X, SOUND_BUTTON_Y, SOUND_BUTTON_WIDTH, SOUND_BUTTON_HEIGHT);


            if (newHighScore) {
                scoreFont.draw(game.batch, "HIGH SCORE: " + prefs.getHighScore(), MENU_SCORE_X, MENU_SCORE_Y);
                gl.setText(gameOverFont, "HIGH SCORE", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                gameOverFont.draw(game.batch, "HIGH SCORE", (SCREEN_WIDTH - gl.width) / 2, GAME_OVER_TEXT_Y);

            } else {
                scoreFont.draw(game.batch, "HIGH SCORE: " + prefs.getHighScore(), MENU_SCORE_X, MENU_SCORE_Y);
                gl.setText(gameOverFont, "GAME OVER", Color.WHITE, SCREEN_WIDTH, Align.center, true);
                gameOverFont.draw(game.batch, "GAME OVER", (SCREEN_WIDTH - gl.width) / 2, GAME_OVER_TEXT_Y);

            }
            game.batch.draw(gemIcon, GEM_ICON_MENU_X, GEM_ICON_MENU_Y, GEM_ICON_WIDTH, GEM_ICON_HEIGHT);
            gemCountFont.draw(game.batch, " x " + replayScreengemCount, GEM_COUNT_MENU_X, GEM_COUNT_MENU_Y);
        }
    }
}
