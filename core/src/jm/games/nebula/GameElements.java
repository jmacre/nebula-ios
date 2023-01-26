package jm.games.nebula;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import games.rednblack.miniaudio.MASound;

public class GameElements {
    Button pauseButton, replayResumeButton, soundButton, homeButton;

    public static int SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static int SCREEN_HEIGHT = Gdx.graphics.getHeight();

    public static final int BLUE_ID = 0;
    public static final int GREEN_ID = 1;
    public static final int RED_ID = 2;
    public static final int PURPLE_ID = 3;
    public static final int WHITE_ID = 4;

    public static final String GEM_1K_SKU = "1k_g";
    public static final String GEM_10K_SKU = "10k_g";
    public static final String GEM_30K_SKU = "30k_g";
    public static final String GEM_100K_SKU = "100k_g";

    public static String PURPLE_COLOR_HEX = "6a11f6";

    public static float SCORE_TICKER_TIMER = -.25f;
    public static float GEM_COUNT_UPDATE_TIMER = -.065f;
    public static float GEM_COUNT_TIMER_DELAY = -0.5f;
    public static float TAP_TO_CONTINUE_BLINKING_TIMER = -0.5f;

    public static float MAIN_UI_HEIGHT;

    public static float TITLE_LOGO_WIDTH;
    public static int TITLE_LOGO_HEIGHT;
    public static int TITLE_LOGO_Y;
    public static float TITLE_LOGO_X;

    public static int SHIP_WIDTH;
    public static int SHIP_HEIGHT;

    public static float SHIP_X = (SCREEN_WIDTH/2f - SHIP_WIDTH/2f);
    public static float CURRENT_SHIP_X =  (SCREEN_WIDTH/2f - SHIP_WIDTH/2f);

    public static float SHIP_Y = 0.15f*SCREEN_HEIGHT;
    public static float SHIP_START_Y = - 3 * SHIP_HEIGHT;

    public static float BULLET_WIDTH;
    public static float BULLET_HEIGHT;
    public static float MISSILE_WIDTH;
    public static float MISSILE_HEIGHT;

    public static float MIN_EYEBAT_SPAWN_TIME;
    public static float MAX_EYEBAT_SPAWN_TIME;

    public static float BLUE_EYEBAT_WIDTH;
    public static float BLUE_EYEBAT_HEIGHT;

    public static float GREEN_EYEBAT_WIDTH;
    public static float GREEN_EYEBAT_HEIGHT;

    public static float RED_EYEBAT_WIDTH;
    public static float RED_EYEBAT_HEIGHT;

    public static float PURPLE_EYEBAT_WIDTH;
    public static float PURPLE_EYEBAT_HEIGHT;

    public static float WHITE_EYEBAT_WIDTH;
    public static float WHITE_EYEBAT_HEIGHT;

    public static float MIN_ENEMY_SHIP_SPAWN_TIME; //seconds
    public static float MAX_ENEMY_SHIP_SPAWN_TIME;

    public static float ENEMY_SHIP_HEIGHT;
    public static float ENEMY_SHIP_WIDTH;

    public static float MIN_LASER_TRAP_SPAWN_TIME; //seconds
    public static float MAX_LASER_TRAP_SPAWN_TIME;

    public static float LASER_TRAP_H_WIDTH;
    public static float LASER_TRAP_H_HEIGHT;

    public static float HEART_HEIGHT;
    public static float HEART_WIDTH;

    public static float HEART_ITEM_HEIGHT;
    public static float HEART_ITEM_WIDTH;

    public static float TOP_ELEM_Y;

    public static float RIGHT_HEART_X;
    public static float MIDDLE_HEART_X;
    public static float LEFT_HEART_X;

    public static float BOMB_HEIGHT;
    public static float BOMB_WIDTH;

    public static float SPREAD_HEIGHT;
    public static float SPREAD_WIDTH;

    public static float GEM_HEIGHT;
    public static float GEM_WIDTH;

    public static float GEM_SHOP_HEIGHT;
    public static float GEM_SHOP_WIDTH;

    public static float RAPID_FIRE_HEIGHT;
    public static float RAPID_FIRE_WIDTH;

    public static float SMALL_EXPLOSION_WIDTH;
    public static float SMALL_EXPLOSION_HEIGHT;

    public static float HOURGLASS_HEIGHT;
    public static float HOURGLASS_WIDTH;

    public static float MISSILE_ITEM_HEIGHT;
    public static float MISSILE_ITEM_WIDTH;

    public static float SCORE_Y;
    public static float SCORE_X;

    public static float MENU_BACK_WIDTH;
    public static float MENU_BACK_HEIGHT;
    public static float MENU_BACK_X;
    public static float MENU_BACK_Y;

    public static float SHOP_BACK_X;
    public static float SHOP_BACK_HEIGHT;
    public static float SHOP_BACK_WIDTH;
    public static float SHOP_BACK_Y;

    public static float X_BUTTON_WIDTH;
    public static float X_BUTTON_HEIGHT;
    public static float X_BUTTON_X;
    public static float X_BUTTON_Y;
    
    public static float PLAY_BUTTON_WIDTH;
    public static float PLAY_BUTTON_HEIGHT;
    public static float PLAY_BUTTON_X;
    public static float PLAY_BUTTON_Y;

    public static float HOME_BUTTON_HEIGHT;
    public static float HOME_BUTTON_WIDTH;
    public static float HOME_BUTTON_X;
    public static float HOME_BUTTON_Y;

    public static float SOUND_BUTTON_HEIGHT;
    public static float SOUND_BUTTON_WIDTH;
    public static float SOUND_BUTTON_X;
    public static float SOUND_BUTTON_Y;

    public static float YES_BUTTON_WIDTH;
    public static float YES_BUTTON_HEIGHT;
    public static float YES_BUTTON_X;
    public static float YES_BUTTON_Y;

    public static float NO_BUTTON_WIDTH;
    public static float NO_BUTTON_HEIGHT;
    public static float NO_BUTTON_X;
    public static float NO_BUTTON_Y;

    public static float START_BUTTON_WIDTH;
    public static float START_BUTTON_HEIGHT;
    public static float START_BUTTON_X;
    public static int START_BUTTON_Y;
    public static int START_BUTTON_Y_TRANSITIONED;

    public static float SHOP_BUTTON_WIDTH;
    public static float SHOP_BUTTON_HEIGHT;
    public static float SHOP_BUTTON_X;
    public static int SHOP_BUTTON_Y;
    public static int SHOP_BUTTON_Y_TRANSITIONED;

    public static float TITLE_SCREEN_Y_ADJUSTMENT;

    public static float WIDTH_ADJUSTMENT;
    public static float SHOP_FONT_WIDTH_ADJUSTMENT;

    public static void resetShipPositionOnResize(){
        SHIP_X = (SCREEN_WIDTH/2f - SHIP_WIDTH/2f);
        CURRENT_SHIP_X =  (SCREEN_WIDTH/2f - SHIP_WIDTH/2f);

        SHIP_Y = 0.15f*SCREEN_HEIGHT;
        SHIP_START_Y = - 3 * SHIP_HEIGHT;
    }

    public static void defineSizesAndPositions(){
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        SCREEN_WIDTH = Gdx.graphics.getWidth();

        MAIN_UI_HEIGHT = SCREEN_HEIGHT/12f;


        if(SCREEN_WIDTH / (float)SCREEN_HEIGHT >= 0.75f){
            TITLE_SCREEN_Y_ADJUSTMENT = 1.05f;
            WIDTH_ADJUSTMENT = 0.7f;
            SHOP_FONT_WIDTH_ADJUSTMENT = 0.85f;
        }
        else{
            TITLE_SCREEN_Y_ADJUSTMENT = 1f;
            WIDTH_ADJUSTMENT = 1f;
            SHOP_FONT_WIDTH_ADJUSTMENT = 1f;
        }

        TITLE_LOGO_WIDTH = (SCREEN_WIDTH / 1.1f) * WIDTH_ADJUSTMENT;
        TITLE_LOGO_HEIGHT = (int) (TITLE_LOGO_WIDTH*.2197f);
        TITLE_LOGO_Y = (int) (((SCREEN_HEIGHT * .75f + SCREEN_HEIGHT) - TITLE_LOGO_HEIGHT) * TITLE_SCREEN_Y_ADJUSTMENT);
        TITLE_LOGO_X = SCREEN_WIDTH / 2f - TITLE_LOGO_WIDTH/2;

        SHIP_WIDTH = (int)((SCREEN_WIDTH/6f)  * WIDTH_ADJUSTMENT);
        SHIP_HEIGHT = (int)(SHIP_WIDTH * (31f/27f));

        BULLET_WIDTH = SHIP_WIDTH*(2/27f);
        BULLET_HEIGHT = BULLET_WIDTH*4f;
        MISSILE_WIDTH = SHIP_WIDTH;
        MISSILE_HEIGHT = MISSILE_WIDTH * (50f/27f);

        MIN_EYEBAT_SPAWN_TIME = 0.5f; //seconds
        MAX_EYEBAT_SPAWN_TIME = 1f;

        BLUE_EYEBAT_WIDTH = SHIP_WIDTH * 38/27f;
        BLUE_EYEBAT_HEIGHT = BLUE_EYEBAT_WIDTH * (25f/38f);

        GREEN_EYEBAT_WIDTH = BLUE_EYEBAT_WIDTH * 43/38f;
        GREEN_EYEBAT_HEIGHT = GREEN_EYEBAT_WIDTH * (27f/43f);

        RED_EYEBAT_WIDTH = GREEN_EYEBAT_WIDTH;
        RED_EYEBAT_HEIGHT = GREEN_EYEBAT_HEIGHT;

        PURPLE_EYEBAT_WIDTH = BLUE_EYEBAT_WIDTH * (51f/38f);
        PURPLE_EYEBAT_HEIGHT = PURPLE_EYEBAT_WIDTH * (32f/51f);

        WHITE_EYEBAT_WIDTH = BLUE_EYEBAT_WIDTH * (60f/38f);
        WHITE_EYEBAT_HEIGHT = WHITE_EYEBAT_WIDTH * (39f/60f);

        MIN_ENEMY_SHIP_SPAWN_TIME = 4.25f; //seconds
        MAX_ENEMY_SHIP_SPAWN_TIME = 7f;

        ENEMY_SHIP_HEIGHT = SHIP_HEIGHT * (27/32f);
        ENEMY_SHIP_WIDTH = ENEMY_SHIP_HEIGHT * (31/27f);

        MIN_LASER_TRAP_SPAWN_TIME = 1.5f; //seconds
        MAX_LASER_TRAP_SPAWN_TIME = 3f;

        LASER_TRAP_H_WIDTH =  SHIP_WIDTH * (48/27f);
        LASER_TRAP_H_HEIGHT = LASER_TRAP_H_WIDTH / 12;

        HEART_HEIGHT = SHIP_HEIGHT * (11/31f);
        HEART_WIDTH = HEART_HEIGHT * (13.0f/11.0f);

        HEART_ITEM_HEIGHT = HEART_HEIGHT * (17/11f);
        HEART_ITEM_WIDTH = HEART_WIDTH * (19/13f);

        TOP_ELEM_Y = SCREEN_HEIGHT - HEART_HEIGHT * 1.75f;

        RIGHT_HEART_X = SCREEN_WIDTH - SCREEN_WIDTH / 4f;
        MIDDLE_HEART_X = RIGHT_HEART_X - HEART_WIDTH*1.1f;
        LEFT_HEART_X = MIDDLE_HEART_X - HEART_WIDTH*1.1f;

        BOMB_HEIGHT = HEART_HEIGHT * (24/11f);
        BOMB_WIDTH = BOMB_HEIGHT * (18/24f);

        SPREAD_HEIGHT = BOMB_HEIGHT * (17/24f);
        SPREAD_WIDTH = SPREAD_HEIGHT;

        GEM_HEIGHT = BOMB_HEIGHT / 2f;
        GEM_WIDTH = BOMB_WIDTH * (17/18f);

        GEM_SHOP_HEIGHT = WHITE_EYEBAT_HEIGHT * (48 / 39f);
        GEM_SHOP_WIDTH = WHITE_EYEBAT_WIDTH * (69 / 60f);

        RAPID_FIRE_HEIGHT = BOMB_HEIGHT * (16/23f);
        RAPID_FIRE_WIDTH = HEART_WIDTH;

        SMALL_EXPLOSION_WIDTH = 1.5f*BLUE_EYEBAT_WIDTH;
        SMALL_EXPLOSION_HEIGHT = SMALL_EXPLOSION_WIDTH;

        HOURGLASS_HEIGHT = RAPID_FIRE_HEIGHT;
        HOURGLASS_WIDTH = HOURGLASS_HEIGHT * (15/16f);

        MISSILE_ITEM_HEIGHT = HOURGLASS_HEIGHT * (21/16f);
        MISSILE_ITEM_WIDTH = MISSILE_ITEM_HEIGHT * (15/20f);

        SCORE_Y = SCREEN_HEIGHT - (SCREEN_HEIGHT / 12f) /3f;
        SCORE_X = SCREEN_WIDTH / 2f - (SCREEN_WIDTH / 1.1f)/2;

        MENU_BACK_WIDTH = (SCREEN_WIDTH*.9f) * WIDTH_ADJUSTMENT;
        MENU_BACK_HEIGHT = MENU_BACK_WIDTH/1.733f;
        MENU_BACK_X = SCREEN_WIDTH/2f - MENU_BACK_WIDTH/2;
        MENU_BACK_Y = SCREEN_HEIGHT/2f - MENU_BACK_HEIGHT/2;

        SHOP_BACK_WIDTH = (.875f*SCREEN_WIDTH) * WIDTH_ADJUSTMENT;
        SHOP_BACK_HEIGHT = SHOP_BACK_WIDTH;
        SHOP_BACK_X = SCREEN_WIDTH/2f - SHOP_BACK_WIDTH/2;
        SHOP_BACK_Y = SCREEN_HEIGHT/2f - SHOP_BACK_HEIGHT/2;

        X_BUTTON_WIDTH = SHOP_BACK_WIDTH / 10f;
        X_BUTTON_HEIGHT = X_BUTTON_WIDTH;
        X_BUTTON_X = SHOP_BACK_X + SHOP_BACK_WIDTH - (X_BUTTON_WIDTH/2f) - (SHOP_BACK_WIDTH * .0065f);
        X_BUTTON_Y = SHOP_BACK_Y + SHOP_BACK_HEIGHT - (X_BUTTON_HEIGHT/2f) - (SHOP_BACK_WIDTH * .0065f);

        PLAY_BUTTON_WIDTH = MENU_BACK_WIDTH/4f;
        PLAY_BUTTON_HEIGHT = MENU_BACK_WIDTH/4f;
        PLAY_BUTTON_X = SCREEN_WIDTH / 2f - PLAY_BUTTON_WIDTH/2f;
        PLAY_BUTTON_Y = MENU_BACK_Y + MENU_BACK_HEIGHT/2 - PLAY_BUTTON_HEIGHT/2;

        HOME_BUTTON_HEIGHT = 0.7f * PLAY_BUTTON_HEIGHT;
        HOME_BUTTON_WIDTH = 0.7f * PLAY_BUTTON_HEIGHT;
        HOME_BUTTON_X = (PLAY_BUTTON_X - MENU_BACK_X)/2 + MENU_BACK_X - HOME_BUTTON_WIDTH/2;
        HOME_BUTTON_Y = MENU_BACK_Y + MENU_BACK_HEIGHT/2 - HOME_BUTTON_HEIGHT/2;

        SOUND_BUTTON_HEIGHT = 0.7f * PLAY_BUTTON_HEIGHT;
        SOUND_BUTTON_WIDTH = 0.7f * PLAY_BUTTON_HEIGHT;
        SOUND_BUTTON_X = (MENU_BACK_X + MENU_BACK_WIDTH + (PLAY_BUTTON_X + PLAY_BUTTON_WIDTH))/2 - SOUND_BUTTON_WIDTH/2;
        SOUND_BUTTON_Y = MENU_BACK_Y + MENU_BACK_HEIGHT/2 - SOUND_BUTTON_HEIGHT/2;

        YES_BUTTON_WIDTH = HOME_BUTTON_WIDTH*1.5f;
        YES_BUTTON_HEIGHT = YES_BUTTON_WIDTH * (39f/64f);
        YES_BUTTON_X = HOME_BUTTON_X;
        YES_BUTTON_Y = MENU_BACK_Y + MENU_BACK_HEIGHT*.15f;

        NO_BUTTON_WIDTH = SOUND_BUTTON_WIDTH*1.5f;
        NO_BUTTON_HEIGHT = NO_BUTTON_WIDTH * (39f/64f);
        NO_BUTTON_X = SCREEN_WIDTH   - YES_BUTTON_X-NO_BUTTON_WIDTH;
        NO_BUTTON_Y = YES_BUTTON_Y;

        START_BUTTON_WIDTH = (SCREEN_WIDTH/2f) * WIDTH_ADJUSTMENT;
        START_BUTTON_HEIGHT = START_BUTTON_WIDTH / 2;
        START_BUTTON_X = SCREEN_WIDTH / 2f - START_BUTTON_WIDTH*.5f;
        START_BUTTON_Y = (int) ((.3f*SCREEN_HEIGHT + SCREEN_HEIGHT) * TITLE_SCREEN_Y_ADJUSTMENT);
        START_BUTTON_Y_TRANSITIONED = (int) ((SCREEN_HEIGHT * .3f) * TITLE_SCREEN_Y_ADJUSTMENT);

        SHOP_BUTTON_WIDTH = (SCREEN_WIDTH/3f) * WIDTH_ADJUSTMENT;
        SHOP_BUTTON_HEIGHT = SHOP_BUTTON_WIDTH/2;
        SHOP_BUTTON_X = SCREEN_WIDTH / 2f - SHOP_BUTTON_WIDTH*.5f;
        SHOP_BUTTON_Y = (int)((.3f * (.75f*START_BUTTON_Y) - START_BUTTON_HEIGHT + SCREEN_HEIGHT) * TITLE_SCREEN_Y_ADJUSTMENT);
        SHOP_BUTTON_Y_TRANSITIONED = (int)((.3f* (.7f*START_BUTTON_Y) - START_BUTTON_HEIGHT) * TITLE_SCREEN_Y_ADJUSTMENT);

        SELECT_BUTTON_WIDTH = (SHOP_BACK_WIDTH / 4.5f) * 2;
        SELECT_BUTTON_HEIGHT = (SHOP_BACK_WIDTH / 4.5f) * (64f / 88);
        SELECT_BUTTON_Y = SHOP_BACK_Y + SHOP_BACK_HEIGHT * .0375f;
        SELECT_BUTTON_X = SHOP_BACK_X + SHOP_BACK_WIDTH/2f - SELECT_BUTTON_WIDTH/2f;

        LEFT_ARROW_BTN_WIDTH = SHOP_BACK_WIDTH / 4.5f;
        LEFT_ARROW_BTN_HEIGHT = SELECT_BUTTON_HEIGHT;
        LEFT_ARROW_BTN_Y = SELECT_BUTTON_Y;
        LEFT_ARROW_BTN_X = SELECT_BUTTON_X - LEFT_ARROW_BTN_WIDTH - (SELECT_BUTTON_X - LEFT_ARROW_BTN_WIDTH - MENU_BACK_X)/4f;

        RIGHT_ARROW_BTN_WIDTH = LEFT_ARROW_BTN_WIDTH;
        RIGHT_ARROW_BTN_HEIGHT = LEFT_ARROW_BTN_HEIGHT;
        RIGHT_ARROW_BTN_Y = SELECT_BUTTON_Y;
        RIGHT_ARROW_BTN_X = SHOP_BACK_X + SHOP_BACK_WIDTH - (LEFT_ARROW_BTN_X - SHOP_BACK_X) - LEFT_ARROW_BTN_WIDTH;

        TS_SOUND_BUTTON_WIDTH = (SCREEN_WIDTH / 9f) * WIDTH_ADJUSTMENT;
        TS_SOUND_BUTTON_HEIGHT = TS_SOUND_BUTTON_WIDTH;
        TS_SOUND_BUTTON_X = SCORE_X;
        TS_SOUND_BUTTON_Y = TS_SOUND_BUTTON_HEIGHT * 1.05f;

        GEM_BUTTON_WIDTH = GEM_WIDTH;
        GEM_BUTTON_HEIGHT = GEM_HEIGHT;
        GEM_BUTTON_X = SCREEN_WIDTH - TS_SOUND_BUTTON_X - GEM_BUTTON_WIDTH;
        GEM_BUTTON_Y = TS_SOUND_BUTTON_Y + (TS_SOUND_BUTTON_HEIGHT - GEM_BUTTON_HEIGHT)/2f;

        PAUSE_BUTTON_X = SCREEN_WIDTH - SCREEN_WIDTH / 7.5f;
        PAUSE_BUTTON_HEIGHT = MAIN_UI_HEIGHT/1.2f;
        PAUSE_BUTTON_WIDTH = PAUSE_BUTTON_HEIGHT * 1.3f;
        PAUSE_BUTTON_Y = SCORE_Y - PAUSE_BUTTON_HEIGHT;

        MENU_SCORE_X = MENU_BACK_X + MENU_BACK_WIDTH * 0.05f;
        MENU_SCORE_Y = (MENU_BACK_Y + MENU_BACK_HEIGHT - .45f * HOME_BUTTON_HEIGHT);

        GEM_ICON_HEIGHT = HEART_HEIGHT * (6 / 11f);
        GEM_ICON_WIDTH = HEART_WIDTH * (7 / 13f);
        GEM_ICON_X = SCORE_X;

        GEM_ICON_Y = TOP_ELEM_Y - GEM_ICON_HEIGHT*1.75f;

        GEM_ICON_MENU_X = PLAY_BUTTON_X + PLAY_BUTTON_WIDTH;
        GEM_ICON_MENU_Y = MENU_SCORE_Y - GEM_ICON_HEIGHT * 0.8f;

        GEM_COUNT_MENU_X = GEM_ICON_MENU_X + (GEM_ICON_WIDTH * 1.1f);
        GEM_COUNT_MENU_Y = GEM_ICON_MENU_Y + (GEM_ICON_HEIGHT * .8f);

        GEM_ICON_SHOP_X = SHOP_BACK_X + SHOP_BACK_WIDTH * 0.05f;
        GEM_ICON_SHOP_Y = SHOP_BACK_Y + (SHOP_BACK_HEIGHT* .9f);

        GEM_COUNT_SHOP_X = GEM_ICON_SHOP_X + GEM_ICON_WIDTH;
        GEM_COUNT_SHOP_Y = GEM_ICON_SHOP_Y + (GEM_ICON_HEIGHT * .8f);

        GEM_COUNT_X = GEM_ICON_X + (GEM_ICON_WIDTH * 1.1f);
        GEM_COUNT_Y = GEM_ICON_Y + (GEM_ICON_HEIGHT * .8f);

        POWERUP_TIMER_HEIGHT = HEART_HEIGHT * (17/11f);
        POWERUP_TIMER_WIDTH = POWERUP_TIMER_HEIGHT;
        POWERUP_TIMER_X = SCORE_X;
        POWERUP_TIMER_Y= GEM_ICON_Y - (POWERUP_TIMER_HEIGHT * 1.2f);

        CONFIRM_LEAVE_FONT_Y = (MENU_BACK_Y + MENU_BACK_HEIGHT - .55f * HOME_BUTTON_HEIGHT);

        GAME_OVER_TEXT_Y = (MENU_BACK_Y + MENU_BACK_HEIGHT) + (GEM_ICON_Y - MENU_BACK_Y - MENU_BACK_HEIGHT)/2;
    }

    public static float SELECT_BUTTON_WIDTH = (SHOP_BACK_WIDTH / 4.5f) * 2;
    public static float SELECT_BUTTON_HEIGHT = (SHOP_BACK_WIDTH / 4.5f) * (64f / 88);
    public static float SELECT_BUTTON_Y = SHOP_BACK_Y + SHOP_BACK_HEIGHT * .0375f;
    public static float SELECT_BUTTON_X = SHOP_BACK_X + SHOP_BACK_WIDTH/2f - SELECT_BUTTON_WIDTH/2f;

    public static float LEFT_ARROW_BTN_WIDTH = SHOP_BACK_WIDTH / 4.5f;
    public static float LEFT_ARROW_BTN_HEIGHT = SELECT_BUTTON_HEIGHT;
    public static float LEFT_ARROW_BTN_Y = SELECT_BUTTON_Y;
    public static float LEFT_ARROW_BTN_X = SELECT_BUTTON_X - LEFT_ARROW_BTN_WIDTH - (SELECT_BUTTON_X - LEFT_ARROW_BTN_WIDTH - MENU_BACK_X)/4f;

    public static float RIGHT_ARROW_BTN_WIDTH = LEFT_ARROW_BTN_WIDTH;
    public static float RIGHT_ARROW_BTN_HEIGHT = LEFT_ARROW_BTN_HEIGHT;
    public static float RIGHT_ARROW_BTN_Y = SELECT_BUTTON_Y;
    public static float RIGHT_ARROW_BTN_X = SHOP_BACK_X + SHOP_BACK_WIDTH - (LEFT_ARROW_BTN_X - SHOP_BACK_X) - LEFT_ARROW_BTN_WIDTH;

    public static float TS_SOUND_BUTTON_WIDTH = SCREEN_WIDTH / 9f;
    public static float TS_SOUND_BUTTON_HEIGHT = TS_SOUND_BUTTON_WIDTH;
    public static float TS_SOUND_BUTTON_X = SCORE_X;
    public static float TS_SOUND_BUTTON_Y = TS_SOUND_BUTTON_HEIGHT * 1.05f;

    public static float GEM_BUTTON_WIDTH = GEM_WIDTH;
    public static float GEM_BUTTON_HEIGHT = GEM_HEIGHT;
    public static float GEM_BUTTON_X = SCREEN_WIDTH - TS_SOUND_BUTTON_X - GEM_BUTTON_WIDTH;
    public static float GEM_BUTTON_Y = TS_SOUND_BUTTON_Y + (TS_SOUND_BUTTON_HEIGHT - GEM_BUTTON_HEIGHT)/2f;

    public static float PAUSE_BUTTON_X = SCREEN_WIDTH - SCREEN_WIDTH / 7.5f;
    public static float PAUSE_BUTTON_HEIGHT = MAIN_UI_HEIGHT/1.2f;
    public static float PAUSE_BUTTON_WIDTH = PAUSE_BUTTON_HEIGHT * 1.3f;
    public static float PAUSE_BUTTON_Y = SCORE_Y - PAUSE_BUTTON_HEIGHT;

    public static float MENU_SCORE_X = MENU_BACK_X + MENU_BACK_WIDTH * 0.05f;
    public static float MENU_SCORE_Y = (MENU_BACK_Y + MENU_BACK_HEIGHT - .45f * HOME_BUTTON_HEIGHT);

    public static float GEM_ICON_HEIGHT = HEART_HEIGHT * (6 / 11f);
    public static float GEM_ICON_WIDTH = HEART_WIDTH * (7 / 13f);
    public static float GEM_ICON_X = SCORE_X;

    public static float GEM_ICON_Y = TOP_ELEM_Y - GEM_ICON_HEIGHT*1.75f;

    public static float GEM_ICON_MENU_X = PLAY_BUTTON_X + PLAY_BUTTON_WIDTH;
    public static float GEM_ICON_MENU_Y = MENU_SCORE_Y - GEM_ICON_HEIGHT * 0.8f;

    public static float GEM_COUNT_MENU_X = GEM_ICON_MENU_X + (GEM_ICON_WIDTH * 1.1f);
    public static float GEM_COUNT_MENU_Y = GEM_ICON_MENU_Y + (GEM_ICON_HEIGHT * .8f);

    public static float GEM_ICON_SHOP_X = SHOP_BACK_X + SHOP_BACK_WIDTH * 0.05f;
    public static float GEM_ICON_SHOP_Y = SHOP_BACK_Y + (SHOP_BACK_HEIGHT* .9f);

    public static float GEM_COUNT_SHOP_X = GEM_ICON_SHOP_X + GEM_ICON_WIDTH;
    public static float GEM_COUNT_SHOP_Y = GEM_ICON_SHOP_Y + (GEM_ICON_HEIGHT * .8f);

    public static float GEM_COUNT_X = GEM_ICON_X + (GEM_ICON_WIDTH * 1.1f);
    public static float GEM_COUNT_Y = GEM_ICON_Y + (GEM_ICON_HEIGHT * .8f);

    public static float POWERUP_TIMER_HEIGHT = HEART_HEIGHT * (17/11f);
    public static float POWERUP_TIMER_WIDTH = POWERUP_TIMER_HEIGHT;
    public static float POWERUP_TIMER_X = SCORE_X;
    public static float POWERUP_TIMER_Y= GEM_ICON_Y - (POWERUP_TIMER_HEIGHT * 1.2f);

    public static float CONFIRM_LEAVE_FONT_Y = (MENU_BACK_Y + MENU_BACK_HEIGHT - .55f * HOME_BUTTON_HEIGHT) * WIDTH_ADJUSTMENT;

    public static float GAME_OVER_TEXT_Y = (MENU_BACK_Y + MENU_BACK_HEIGHT) + (GEM_ICON_Y - MENU_BACK_Y - MENU_BACK_HEIGHT)/2;

    Anim shipAnim = new Anim();
    Anim missileAnim = new Anim();
    Anim enemyBulletAnim = new Anim();
    Anim eyebatAnim = new Anim();
    Anim enemyShipAnim = new Anim();
    Anim laserTrapHAnim = new Anim();
    Anim explosionAnim = new Anim();
    Anim powerupAnim = new Anim();
    Anim starsAnimFront = new Anim();
    Anim starsAnimBack = new Anim();


    Sprite blackTransition, whiteFlash, shipSS;
    Animation<TextureRegion> shipAnimation;

    MASound mainMusic;
    MASound pauseSound;
    MASound playSound;
    MASound bombSound;
    MASound missileSound, missileSound1;
    MASound bulletSound, bulletSound1, itemSound, hitSound, hitSound1, gemSound, errorSound;

    public FreeTypeFontGenerator.FreeTypeFontParameter textParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    public FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
    public BitmapFont menuScoreFont, gameOverFont, countdownFont, confirmScreenFont, storeFont, gemCountFont, buyFont;
}
