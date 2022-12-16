package com.mygdx.NEBULA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GameElements {
    Button pauseButton, replayResumeButton, soundButton, homeButton;


    public static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();

    public static final int BLUE_ID = 0;
    public static final int GREEN_ID = 1;
    public static final int RED_ID = 2;
    public static final int PURPLE_ID = 3;
    public static final int WHITE_ID = 4;

    public static final String PURPLE_COLOR_HEX = "6a11f6";

    public static float SCORE_TICKER_TIMER = -.25f;
    public static float GEM_COUNT_UPDATE_TIMER = -.065f;

    public static final float MAIN_UI_HEIGHT = SCREEN_HEIGHT/12f;

    public static final float TITLE_LOGO_WIDTH = SCREEN_WIDTH / 1.1f;
    public static final int TITLE_LOGO_HEIGHT = (int) (TITLE_LOGO_WIDTH*.2197f);
    public static int TITLE_LOGO_Y = (int)(SCREEN_HEIGHT * .75f + SCREEN_HEIGHT);
    public static final float TITLE_LOGO_X = SCREEN_WIDTH / 2f - TITLE_LOGO_WIDTH/2;

    public static final float SHIP_WIDTH = SCREEN_WIDTH/6f;
    public static final float SHIP_HEIGHT = SHIP_WIDTH * (31f/27f);

    public static float SHIP_X = (SCREEN_WIDTH/2f - SHIP_WIDTH/2);
    public static float CURRENT_SHIP_X =  (SCREEN_WIDTH/2f - SHIP_WIDTH/2);;

    public static float SHIP_Y = 0.15f*SCREEN_HEIGHT;
    public float SHIP_START_Y = - 3 * SHIP_HEIGHT;

    public static float BULLET_WIDTH = SHIP_WIDTH*(2/27f);
    public static float BULLET_HEIGHT = BULLET_WIDTH*4f;
    public static float MISSILE_WIDTH = SHIP_WIDTH;
    public static float MISSILE_HEIGHT = MISSILE_WIDTH * (50f/27f);

    public static final float MIN_EYEBAT_SPAWN_TIME = 0.5f; //seconds
    public static final float MAX_EYEBAT_SPAWN_TIME = 1f;

    public static float BLUE_EYEBAT_WIDTH = SHIP_WIDTH * 38/27f;
    public static float BLUE_EYEBAT_HEIGHT = BLUE_EYEBAT_WIDTH * (25f/38f);

    public static float GREEN_EYEBAT_WIDTH = BLUE_EYEBAT_WIDTH * 43/38f;
    public static float GREEN_EYEBAT_HEIGHT = GREEN_EYEBAT_WIDTH * (27f/43f);

    public static float RED_EYEBAT_WIDTH = GREEN_EYEBAT_WIDTH;
    public static float RED_EYEBAT_HEIGHT = GREEN_EYEBAT_HEIGHT;

    public static float PURPLE_EYEBAT_WIDTH = BLUE_EYEBAT_WIDTH * (51f/38f);
    public static float PURPLE_EYEBAT_HEIGHT = PURPLE_EYEBAT_WIDTH * (32f/51f);

    public static float WHITE_EYEBAT_WIDTH = BLUE_EYEBAT_WIDTH * (60f/38f);
    public static float WHITE_EYEBAT_HEIGHT = WHITE_EYEBAT_WIDTH * (39f/60f);

    public static final float MIN_ENEMY_SHIP_SPAWN_TIME = 4.25f; //seconds
    public static final float MAX_ENEMY_SHIP_SPAWN_TIME = 7f;

    public static float ENEMY_SHIP_HEIGHT = SHIP_HEIGHT * (27/32f);
    public static float ENEMY_SHIP_WIDTH = ENEMY_SHIP_HEIGHT * (31/27f);

    public static final float MIN_LASER_TRAP_SPAWN_TIME = 1.5f; //seconds
    public static final float MAX_LASER_TRAP_SPAWN_TIME = 3f;

    public float LASER_TRAP_H_WIDTH =  SHIP_WIDTH * (48/27f);
    public float LASER_TRAP_H_HEIGHT = LASER_TRAP_H_WIDTH / 12;

    public static final float HEART_HEIGHT = SHIP_HEIGHT * (11/31f);
    public static final float HEART_WIDTH = HEART_HEIGHT * (13.0f/11.0f);

    public static final float HEART_ITEM_HEIGHT = HEART_HEIGHT * (17/11f);
    public static final float HEART_ITEM_WIDTH = HEART_WIDTH * (19/13f);

    public static float TOP_ELEM_Y_AND = SCREEN_HEIGHT - HEART_HEIGHT * 1.75f;
    public static float TOP_ELEM_Y_IOS = SCREEN_HEIGHT - HEART_HEIGHT * 2.5f;

    public static final float RIGHT_HEART_X = SCREEN_WIDTH - SCREEN_WIDTH / 4f;
    public static final float MIDDLE_HEART_X = RIGHT_HEART_X - HEART_WIDTH*1.1f;
    public static final float LEFT_HEART_X = MIDDLE_HEART_X - HEART_WIDTH*1.1f;

    public static final float BOMB_HEIGHT = HEART_HEIGHT * (24/11f);
    public static final float BOMB_WIDTH = BOMB_HEIGHT * (18/24f);

    public static final float GEM_HEIGHT = BOMB_HEIGHT / 2f;
    public static final float GEM_WIDTH = BOMB_WIDTH * (17/18f);

    public static final float RAPID_FIRE_HEIGHT = BOMB_HEIGHT * (16/23f);
    public static final float RAPID_FIRE_WIDTH = HEART_WIDTH;

    public static final float SMALL_EXPLOSION_WIDTH = 1.5f*BLUE_EYEBAT_WIDTH;
    public static final float SMALL_EXPLOSION_HEIGHT = SMALL_EXPLOSION_WIDTH;

    public static final float HOURGLASS_HEIGHT = RAPID_FIRE_HEIGHT;
    public static final float HOURGLASS_WIDTH = HOURGLASS_HEIGHT * (15/16f);

    public static final float MISSILE_ITEM_HEIGHT = HOURGLASS_HEIGHT * (21/16f);
    public static final float MISSILE_ITEM_WIDTH = MISSILE_ITEM_HEIGHT * (15/20f);

    public static final float SCORE_Y_AND = SCREEN_HEIGHT - (SCREEN_HEIGHT / 12f) /3f;
    public static final float SCORE_Y_IOS = SCREEN_HEIGHT - MAIN_UI_HEIGHT;
    public static final float SCORE_X = SCREEN_WIDTH / 2f - (SCREEN_WIDTH / 1.1f)/2;

    public static final float MENU_BACK_WIDTH = SCREEN_WIDTH*.9f;
    public static final float MENU_BACK_HEIGHT = MENU_BACK_WIDTH/1.733f;
    public static final float MENU_BACK_X = SCREEN_WIDTH/2f - MENU_BACK_WIDTH/2;
    public static final float MENU_BACK_Y = SCREEN_HEIGHT/2f - MENU_BACK_HEIGHT/2;

    public static final float SHOP_BACK_X = SCREEN_WIDTH/15f;
    public static final float SHOP_BACK_HEIGHT = .875f*SCREEN_WIDTH;
    public static final float SHOP_BACK_WIDTH = SHOP_BACK_HEIGHT;
    public static final float SHOP_BACK_Y = SCREEN_HEIGHT/2f - SHOP_BACK_HEIGHT/2;

    public static final float X_BUTTON_WIDTH = SHOP_BACK_WIDTH / 12;
    public static final float X_BUTTON_HEIGHT = X_BUTTON_WIDTH;
    public static final float X_BUTTON_X = SHOP_BACK_X + SHOP_BACK_WIDTH - X_BUTTON_WIDTH/2;
    public static final float X_BUTTON_Y = SHOP_BACK_Y + SHOP_BACK_HEIGHT - X_BUTTON_HEIGHT/2;

    public static final float PLAY_BUTTON_WIDTH = MENU_BACK_WIDTH/4f;
    public static final float PLAY_BUTTON_HEIGHT = MENU_BACK_WIDTH/4f;
    public static final float PLAY_BUTTON_X = SCREEN_WIDTH / 2f - PLAY_BUTTON_WIDTH/2f;
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

    public static final float START_BUTTON_WIDTH = SCREEN_WIDTH/2f;
    public static final float START_BUTTON_HEIGHT = START_BUTTON_WIDTH / 2;
    public static final float START_BUTTON_X = SCREEN_WIDTH / 2f - START_BUTTON_WIDTH*.5f;
    public static int START_BUTTON_Y = (int)(.3f*SCREEN_HEIGHT + SCREEN_HEIGHT);
    public static int START_BUTTON_Y_TRANSITIONED = (int)(SCREEN_HEIGHT * .3f);

    public static final float SHOP_BUTTON_WIDTH = SCREEN_WIDTH/3f;
    public static final float SHOP_BUTTON_HEIGHT = SHOP_BUTTON_WIDTH/2;
    public static final float SHOP_BUTTON_X = SCREEN_WIDTH / 2f - SHOP_BUTTON_WIDTH*.5f;
    public static int SHOP_BUTTON_Y = (int)(.3f * (.75f*START_BUTTON_Y) - START_BUTTON_HEIGHT + SCREEN_HEIGHT);
    public static int SHOP_BUTTON_Y_TRANSITIONED = (int)(.3f* (.7f*START_BUTTON_Y) - START_BUTTON_HEIGHT);

    public static final float LEFT_ARROW_WIDTH = SHOP_BACK_WIDTH / 4.5f;
    public static final float LEFT_ARROW_HEIGHT = LEFT_ARROW_WIDTH * (64f/88);
    public static final float LEFT_ARROW_Y = SHOP_BACK_Y * 1.05f;
    public static final float LEFT_ARROW_X = SHOP_BACK_X + SHOP_BACK_Y * .05f;

    public static final float RIGHT_ARROW_WIDTH = LEFT_ARROW_WIDTH;
    public static final float RIGHT_ARROW_HEIGHT = LEFT_ARROW_HEIGHT;
    public static final float RIGHT_ARROW_Y = SHOP_BACK_Y * 1.05f;
    public static final float RIGHT_ARROW_X = SHOP_BACK_X + SHOP_BACK_WIDTH - SHOP_BACK_Y * .05f - RIGHT_ARROW_WIDTH;

    public static final float SELECT_BUTTON_WIDTH = LEFT_ARROW_WIDTH*2;
    public static final float SELECT_BUTTON_HEIGHT = LEFT_ARROW_HEIGHT;
    public static final float SELECT_BUTTON_Y = SHOP_BACK_Y * 1.05f;
    public static final float SELECT_BUTTON_X = SHOP_BACK_X + SHOP_BACK_WIDTH/2f - SELECT_BUTTON_WIDTH/2f;

    public static final float TS_SOUND_BUTTON_WIDTH = SCREEN_WIDTH / 10f;
    public static final float TS_SOUND_BUTTON_HEIGHT = TS_SOUND_BUTTON_WIDTH;
    public static final float TS_SOUND_BUTTON_X = SCREEN_WIDTH - SCREEN_WIDTH / 8f;
    public static final float TS_SOUND_BUTTON_Y_AND = SCREEN_HEIGHT - MAIN_UI_HEIGHT/3f - TS_SOUND_BUTTON_HEIGHT/1.5f;
    public static final float TS_SOUND_BUTTON_Y_IOS = SCREEN_HEIGHT - MAIN_UI_HEIGHT - TS_SOUND_BUTTON_HEIGHT/1.5f;

    public static final float PAUSE_BUTTON_X = SCREEN_WIDTH - SCREEN_WIDTH / 7.5f;
    public static final float PAUSE_BUTTON_Y = SCREEN_HEIGHT - MAIN_UI_HEIGHT / 2.1f - (MAIN_UI_HEIGHT / 2.5f) / 2;
    public static final float PAUSE_BUTTON_HEIGHT = MAIN_UI_HEIGHT/1.2f;
    public static final float PAUSE_BUTTON_WIDTH = PAUSE_BUTTON_HEIGHT * 1.3f;

    public static final float MENU_SCORE_X = HOME_BUTTON_X;
    public static final float MENU_SCORE_Y = (MENU_BACK_Y + MENU_BACK_HEIGHT - .45f * HOME_BUTTON_HEIGHT);

    public static final float GEM_ICON_HEIGHT = HEART_HEIGHT * (6 / 11f);
    public static final float GEM_ICON_WIDTH = HEART_WIDTH * (7 / 13f);
    public static final float GEM_ICON_X = SCORE_X;

    public static final float GEM_ICON_Y_AND = SCORE_Y_AND - (SCREEN_HEIGHT * 0.03f);
    public static final float GEM_ICON_Y_IOS = SCORE_Y_IOS - (SCREEN_HEIGHT * 0.03f);

    public static final float GEM_ICON_MENU_X = PLAY_BUTTON_X + PLAY_BUTTON_WIDTH;
    public static final float GEM_ICON_MENU_Y = MENU_SCORE_Y - GEM_ICON_HEIGHT * 0.8f;

    public static final float GEM_COUNT_MENU_X = GEM_ICON_MENU_X + (GEM_ICON_WIDTH * 1.1f);
    public static final float GEM_COUNT_MENU_Y = GEM_ICON_MENU_Y + (GEM_ICON_HEIGHT * .8f);

    public static final float GEM_ICON_SHOP_X = LEFT_ARROW_X;
    public static final float GEM_ICON_SHOP_Y = SHOP_BACK_Y + (SHOP_BACK_HEIGHT* .9f);

    public static final float GEM_COUNT_SHOP_X = LEFT_ARROW_X + (GEM_ICON_WIDTH * 1.1f);
    public static final float GEM_COUNT_SHOP_Y = GEM_ICON_SHOP_Y + (GEM_ICON_HEIGHT * .8f);


    public static final float GEM_COUNT_X = GEM_ICON_X + (GEM_ICON_WIDTH * 1.1f);
    public static final float GEM_COUNT_Y_AND = GEM_ICON_Y_AND + (GEM_ICON_HEIGHT * .8f);
    public static final float GEM_COUNT_Y_IOS = GEM_ICON_Y_IOS + (GEM_ICON_HEIGHT * .8f);

    public static final float POWERUP_TIMER_HEIGHT = HEART_HEIGHT * (17/11f);
    public static final float POWERUP_TIMER_WIDTH = POWERUP_TIMER_HEIGHT;
    public static final float POWERUP_TIMER_X = SCORE_X;
    public static final float POWERUP_TIMER_Y_IOS = GEM_ICON_Y_IOS - (POWERUP_TIMER_HEIGHT * 1.2f);
    public static final float POWERUP_TIMER_Y_AND = GEM_ICON_Y_AND - (POWERUP_TIMER_HEIGHT * 1.2f);

    public static final float CONFIRM_LEAVE_FONT_Y = (MENU_BACK_Y + MENU_BACK_HEIGHT - .55f * HOME_BUTTON_HEIGHT);
    public static final float CONFIRM_LEAVE_FONT_X = YES_BUTTON_X;

    public static final float GAME_OVER_TEXT_Y = (SCREEN_HEIGHT - (MENU_BACK_Y/2));

    Anim shipAnim = new Anim();
    Anim missileAnim = new Anim();
    Anim eyebatAnim = new Anim();
    Anim enemyShipAnim = new Anim();
    Anim laserTrapHAnim = new Anim();
    Anim explosionAnim = new Anim();
    Anim powerupAnim = new Anim();
    Anim starsAnimFront = new Anim();
    Anim starsAnimBack = new Anim();


    Sprite blackTransition, whiteFlash, shipSS, shipBlinkingSS;
    Animation<TextureRegion> shipAnimation, shipBlinkingAnimation;
    Animation<TextureRegion> powerupTimerAnimation;

    Music mainMusic;
    Sound pauseSound;
    Sound playSound;
    Music bombSound;
    Sound missileSound;
    Sound bulletSound, itemSound, hitSound, gemSound, errorSound;

    public FreeTypeFontGenerator.FreeTypeFontParameter textParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    public FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("mainfont.ttf"));
    public BitmapFont menuScoreFont, gameOverFont, countdownFont, confirmScreenFont, storeFont, gemCountFont, buyFont;
}
