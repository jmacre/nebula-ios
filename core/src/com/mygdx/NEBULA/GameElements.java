package com.mygdx.NEBULA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;

public class GameElements {
    Button pauseButton, replayResumeButton, soundButton, homeButton;

    public static final float SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static final float SCREEN_HEIGHT = Gdx.graphics.getHeight();

    public static final int BLUE_ID = 0;
    public static final int GREEN_ID = 1;
    public static final int RED_ID = 2;
    public static final int PURPLE_ID = 3;
    public static final int WHITE_ID = 4;

    public static float SCORE_TICKER_TIMER = -.25f;

    public static final float MAIN_UI_HEIGHT = SCREEN_HEIGHT/12;

    public static final float TITLE_LOGO_WIDTH = Gdx.graphics.getWidth() / 1.1f;
    public static final float TITLE_LOGO_HEIGHT = TITLE_LOGO_WIDTH*.2197f;
    public static float TITLE_LOGO_Y = Gdx.graphics.getHeight() * .75f + SCREEN_HEIGHT;
    public static final float TITLE_LOGO_X = SCREEN_WIDTH / 2 - TITLE_LOGO_WIDTH/2;

    public static final float SHIP_WIDTH = SCREEN_WIDTH/6.5f;
    public static final float SHIP_HEIGHT = SHIP_WIDTH * (51.0f/43.0f);

    public static float SHIP_X = SCREEN_WIDTH/2 - SHIP_WIDTH/2;
    public static float CURRENT_SHIP_X = SCREEN_WIDTH/2 - SHIP_WIDTH/2;;

    public static float SHIP_Y = 0.1f*SCREEN_HEIGHT;
    public float SHIP_START_Y = - 3*SHIP_HEIGHT;

    public static final float MIN_EYEBAT_SPAWN_TIME = 0.5f; //seconds
    public static final float MAX_EYEBAT_SPAWN_TIME = 1f;

    public static float BLUE_EYEBAT_WIDTH = SHIP_WIDTH * 38/27f;
    public static float BLUE_EYEBAT_HEIGHT = BLUE_EYEBAT_WIDTH * (25f/38f);

    public static float GREEN_EYEBAT_WIDTH = BLUE_EYEBAT_WIDTH * 43/38f;
    public static float GREEN_EYEBAT_HEIGHT = GREEN_EYEBAT_WIDTH * (27f/43f);

    public static float RED_EYEBAT_WIDTH = GREEN_EYEBAT_WIDTH;
    public static float RED_EYEBAT_HEIGHT = GREEN_EYEBAT_HEIGHT;

    public static final float MIN_ENEMY_SHIP_SPAWN_TIME = 4.25f; //seconds
    public static final float MAX_ENEMY_SHIP_SPAWN_TIME = 7f;

    public static float ENEMY_SHIP_HEIGHT = SHIP_HEIGHT * (27/32f);
    public static float ENEMY_SHIP_WIDTH = ENEMY_SHIP_HEIGHT * (31/27f);

    public static final float MIN_LASER_TRAP_SPAWN_TIME = 3f; //seconds
    public static final float MAX_LASER_TRAP_SPAWN_TIME = 6f;

    public float LASER_TRAP_H_WIDTH =  BLUE_EYEBAT_WIDTH *1.2f;
    public float LASER_TRAP_H_HEIGHT = LASER_TRAP_H_WIDTH / 12;
    public static final float HEART_HEIGHT = SCREEN_WIDTH/14f;
    public static final float HEART_WIDTH = HEART_HEIGHT * (13.0f/11.0f);
    public static float TOP_ELEM_Y = SCREEN_HEIGHT - HEART_HEIGHT * 2.5f;

    public static final float RIGHT_HEART_X = SCREEN_WIDTH - SCREEN_WIDTH / 4f;
    public static final float MIDDLE_HEART_X = RIGHT_HEART_X - HEART_WIDTH*1.1f;
    public static final float LEFT_HEART_X = MIDDLE_HEART_X - HEART_WIDTH*1.1f;


    public static final float SCORE_Y = SCREEN_HEIGHT - MAIN_UI_HEIGHT/3.175f;
    public static final float SCORE_X = TITLE_LOGO_X;

    public static final float MENU_BACK_WIDTH = SCREEN_WIDTH*.9f;
    public static final float MENU_BACK_HEIGHT = MENU_BACK_WIDTH/1.733f;
    public static final float MENU_BACK_X = SCREEN_WIDTH/2 - MENU_BACK_WIDTH/2;
    public static final float MENU_BACK_Y = SCREEN_HEIGHT/2 - MENU_BACK_HEIGHT/2;

    public static final float SHOP_BACK_X = SCREEN_WIDTH/15;
    public static final float SHOP_BACK_HEIGHT = .875f*SCREEN_WIDTH;
    public static final float SHOP_BACK_WIDTH = SHOP_BACK_HEIGHT;
    public static final float SHOP_BACK_Y = SCREEN_HEIGHT/2 - SHOP_BACK_HEIGHT/2;

    public static final float X_BUTTON_WIDTH = SHOP_BACK_WIDTH / 12;
    public static final float X_BUTTON_HEIGHT = X_BUTTON_WIDTH;
    public static final float X_BUTTON_X = SHOP_BACK_X + SHOP_BACK_WIDTH - X_BUTTON_WIDTH/2;
    public static final float X_BUTTON_Y = SHOP_BACK_Y + SHOP_BACK_HEIGHT - X_BUTTON_HEIGHT/2;

    public static final float UPGRADE_DIM_WIDTH = 760;
    public static final float UPGRADE_DIM_HEIGHT = 725;

    public static final float PLAY_BUTTON_WIDTH = MENU_BACK_WIDTH/4f;
    public static final float PLAY_BUTTON_HEIGHT = MENU_BACK_WIDTH/4f;
    public static final float PLAY_BUTTON_X = SCREEN_WIDTH / 2 - PLAY_BUTTON_WIDTH/2f;
    public static final float PLAY_BUTTON_Y = MENU_BACK_Y + MENU_BACK_HEIGHT/2 - PLAY_BUTTON_HEIGHT/2;

    public static final float HOME_BUTTON_HEIGHT = 0.7f * PLAY_BUTTON_HEIGHT;
    public static final float HOME_BUTTON_WIDTH = 0.7f * PLAY_BUTTON_HEIGHT;
    public static final float HOME_BUTTON_X = (PLAY_BUTTON_X - MENU_BACK_X)/2 + MENU_BACK_X - HOME_BUTTON_WIDTH/2;
    public static final float HOME_BUTTON_Y = MENU_BACK_Y + MENU_BACK_HEIGHT/2 - HOME_BUTTON_HEIGHT/2;

    public static final float SOUND_BUTTON_HEIGHT = 0.7f * PLAY_BUTTON_HEIGHT;
    public static final float SOUND_BUTTON_WIDTH = 0.7f * PLAY_BUTTON_HEIGHT;
    public static final float SOUND_BUTTON_X = (MENU_BACK_X + MENU_BACK_WIDTH + (PLAY_BUTTON_X + PLAY_BUTTON_WIDTH))/2 - SOUND_BUTTON_WIDTH/2;
    public static final float SOUND_BUTTON_Y = MENU_BACK_Y + MENU_BACK_HEIGHT/2 - SOUND_BUTTON_HEIGHT/2;

    public static final float YES_BUTTON_WIDTH = HOME_BUTTON_WIDTH*1.5f;
    public static final float YES_BUTTON_HEIGHT = YES_BUTTON_WIDTH * (39f/64f);
    public static final float YES_BUTTON_X = HOME_BUTTON_X;
    public static final float YES_BUTTON_Y = HOME_BUTTON_Y*.9f;

    public static final float NO_BUTTON_WIDTH = SOUND_BUTTON_WIDTH*1.5f;
    public static final float NO_BUTTON_HEIGHT = NO_BUTTON_WIDTH * (39f/64f);
    public static final float NO_BUTTON_X = SCREEN_WIDTH - YES_BUTTON_X-NO_BUTTON_WIDTH;
    public static final float NO_BUTTON_Y = SOUND_BUTTON_Y*.9f;

    public static final float START_BUTTON_WIDTH = Gdx.graphics.getWidth()/2f;
    public static final float START_BUTTON_HEIGHT = START_BUTTON_WIDTH / 2;
    public static final float START_BUTTON_X = SCREEN_WIDTH / 2 - START_BUTTON_WIDTH*.5f;
    public static float START_BUTTON_Y = .3f*Gdx.graphics.getHeight() + SCREEN_HEIGHT;
    public static float START_BUTTON_Y_TRANSITIONED = Gdx.graphics.getHeight() * .3f;

    public static final float SHOP_BUTTON_WIDTH = Gdx.graphics.getWidth()/3f;
    public static final float SHOP_BUTTON_HEIGHT = SHOP_BUTTON_WIDTH/2;
    public static final float SHOP_BUTTON_X = SCREEN_WIDTH / 2 - SHOP_BUTTON_WIDTH*.5f;
    public static float SHOP_BUTTON_Y = .3f * (.75f*START_BUTTON_Y) - START_BUTTON_HEIGHT + SCREEN_HEIGHT;
    public static float SHOP_BUTTON_Y_TRANSITIONED = .3f* (.7f*START_BUTTON_Y) - START_BUTTON_HEIGHT;

    public static final float TS_SOUND_BUTTON_WIDTH = SCREEN_WIDTH / 12;
    public static final float TS_SOUND_BUTTON_HEIGHT = TS_SOUND_BUTTON_WIDTH;
    public static final float TS_SOUND_BUTTON_X = SCREEN_WIDTH - SCREEN_WIDTH / 10;
    public static final float TS_SOUND_BUTTON_Y = SCORE_Y - TS_SOUND_BUTTON_HEIGHT/1.5f;

    public static final float PAUSE_BUTTON_X = SCREEN_WIDTH - SCREEN_WIDTH / 7.5f;
    public static final float PAUSE_BUTTON_Y = SCREEN_HEIGHT - MAIN_UI_HEIGHT / 2.1f - (MAIN_UI_HEIGHT / 2.5f) / 2;
    public static final float PAUSE_BUTTON_HEIGHT = MAIN_UI_HEIGHT/1.2f;
    public static final float PAUSE_BUTTON_WIDTH = PAUSE_BUTTON_HEIGHT * 1.3f;

    public static final float MENU_SCORE_X = HOME_BUTTON_X;
    public static final float MENU_SCORE_Y = MENU_BACK_Y + MENU_BACK_HEIGHT - .45f * HOME_BUTTON_HEIGHT;

    public static final float CONFIRM_LEAVE_FONT_Y = MENU_BACK_Y + MENU_BACK_HEIGHT - .55f * HOME_BUTTON_HEIGHT;

    public static final float GAME_OVER_TEXT_Y = SCREEN_HEIGHT - (MENU_BACK_Y/2);

    Anim shipAnim = new Anim();
    Anim missileAnim = new Anim();
    Anim eyebatAnim = new Anim();
    Anim enemyShipAnim = new Anim();
    Anim laserTrapHAnim = new Anim();
    Anim explosionAnim = new Anim();
    Anim starsAnim = new Anim();


    Sprite blackTransition, whiteFlash, shipSS, shipBlinkingSS;
    Animation<TextureRegion> shipAnimation, shipBlinkingAnimation;
    ArrayList<Enemy> enemies = new ArrayList<>();

    Music mainMusic;
    Sound pauseSound;
    Sound playSound;
    Music bombSound;
    Sound missileSound;
    Sound bulletSound, heartSound, hitSound;

    public FreeTypeFontGenerator.FreeTypeFontParameter textParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    public FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("mainfont.ttf"));
    public BitmapFont menuScoreFont, gameOverFont, countdownFont, confirmScreenFont, storeFont;
}
