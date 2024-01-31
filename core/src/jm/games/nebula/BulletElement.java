package jm.games.nebula;

import static jm.games.nebula.Anim.DEFAULT_FRAME_DURATION;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BulletElement extends GameElements{
    public static int bulletCount = 12; // one less than actual count for cycling between bullets

    public static final String YELLOW_BULLET_ID = "0"; //this determines bullet ownership in prefs
    public static final String RED_BULLET_ID = "1";
    public static final String GREEN_BULLET_ID = "2";
    public static final String BLUE_BULLET_ID = "3";
    public static final String ORANGE_BULLET_ID = "5";
    public static final String PURPLE_BULLET_ID = "4";
    public static final String PINK_BULLET_ID = "6";
    public static final String COTTON_CANDY_BULLET_ID = "7";
    public static final String ROCKETPOP_BULLET_ID = "8";
    public static final String SMOKE_BULLET_ID = "9";
    public static final String CHERRY_BULLET_ID = "A";
    public static final String AQUAMARINE_BULLET_ID = "B";
    public static final String RAINBOW_BULLET_ID = "C";

    public static final int YELLOW_BULLET_SLOT = 0; //this determines the display order in the shop
    public static final int RED_BULLET_SLOT = 1;
    public static final int GREEN_BULLET_SLOT = 2;
    public static final int BLUE_BULLET_SLOT = 3;
    public static final int PURPLE_BULLET_SLOT = 4;
    public static final int ORANGE_BULLET_SLOT = 5;
    public static final int PINK_BULLET_SLOT = 6;
    public static final int COTTON_CANDY_BULLET_SLOT = 7;
    public static final int ROCKETPOP_BULLET_SLOT = 8;
    public static final int SMOKE_BULLET_SLOT = 9;
    public static final int CHERRY_BULLET_SLOT = 10;
    public static final int AQUAMARINE_BULLET_SLOT = 11;
    public static final int RAINBOW_BULLET_SLOT = 12;

    public static final int RED_BULLET_PRICE = 150;
    public static final int GREEN_BULLET_PRICE = 150;
    public static final int BLUE_BULLET_PRICE = 150;
    public static final int PURPLE_BULLET_PRICE = 250;
    public static final int ORANGE_BULLET_PRICE = 250;
    public static final int PINK_BULLET_PRICE = 250;
    public static final int COTTON_CANDY_BULLET_PRICE = 500;
    public static final int ROCKETPOP_BULLET_PRICE = 500;
    public static final int SMOKE_BULLET_PRICE = 500;
    public static final int CHERRY_BULLET_PRICE = 500;
    public static final int AQUAMARINE_BULLET_PRICE = 500;
    public static final int RAINBOW_BULLET_PRICE = 500;

    float x, y, width, height;

    static Sprite yellowBullet, redBullet, greenBullet, blueBullet, purpleBullet, orangeBullet;
    static Sprite pinkBullet, cottonCandyBullet, rocketPopBullet, smokeBullet, cherryBullet;
    static Sprite aquamarineBullet, rainbowBullet, nebulaBullet;

    private int colorId;

    private Sprite elementSheet;
    private String title;

    Animation<TextureRegion> elementAnimation;
    Anim elementAnim = new Anim();

    public BulletElement(int id, int colorId, float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colorId = colorId;

        if(String.valueOf(id).equals(YELLOW_BULLET_ID)) {

            setElementAnimation(colorId);

            if(elementSheet != null) {
                elementAnimation = Anim.createAnimation(elementSheet, 1, DEFAULT_FRAME_DURATION * 1.5f);
                elementAnimation.setPlayMode(Animation.PlayMode.LOOP);
            }
        }
    }
    public void render(float stateTime, SpriteBatch batch){
        elementAnim.drawAnim(elementAnimation, stateTime, x, y, width, height, true, batch);
    }

    public static void createElements(Assets assets){
        yellowBullet = new Sprite(assets.assetManager.get(Assets.bullet_yellow, Texture.class));
        redBullet = new Sprite(assets.assetManager.get(Assets.bullet_red, Texture.class));
        greenBullet = new Sprite(assets.assetManager.get(Assets.bullet_green, Texture.class));
        blueBullet = new Sprite(assets.assetManager.get(Assets.bullet_blue, Texture.class));
        purpleBullet = new Sprite(assets.assetManager.get(Assets.bullet_purple, Texture.class));
        orangeBullet = new Sprite(assets.assetManager.get(Assets.bullet_orange, Texture.class));
        pinkBullet = new Sprite(assets.assetManager.get(Assets.bullet_pink, Texture.class));
        cottonCandyBullet = new Sprite(assets.assetManager.get(Assets.bullet_cotton_candy, Texture.class));
        rocketPopBullet = new Sprite(assets.assetManager.get(Assets.bullet_rocketPop, Texture.class));
        smokeBullet = new Sprite(assets.assetManager.get(Assets.bullet_smoke, Texture.class));
        cherryBullet = new Sprite(assets.assetManager.get(Assets.bullet_cherry, Texture.class));
        aquamarineBullet = new Sprite(assets.assetManager.get(Assets.bullet_aquamarine, Texture.class));
        rainbowBullet = new Sprite(assets.assetManager.get(Assets.bullet_rainbow, Texture.class));
    }

    public String getTitle(){
        return title;
    }
    public static String getIdBySlot(int slot) {
        switch (slot) {
            case YELLOW_BULLET_SLOT:
                return YELLOW_BULLET_ID;
            case RED_BULLET_SLOT:
                return RED_BULLET_ID;
            case GREEN_BULLET_SLOT:
                return GREEN_BULLET_ID;
            case BLUE_BULLET_SLOT:
                return BLUE_BULLET_ID;
            case PURPLE_BULLET_SLOT:
                return PURPLE_BULLET_ID;
            case ORANGE_BULLET_SLOT:
                return ORANGE_BULLET_ID;
            case PINK_BULLET_SLOT:
                return PINK_BULLET_ID;
            case COTTON_CANDY_BULLET_SLOT:
                return COTTON_CANDY_BULLET_ID;
            case ROCKETPOP_BULLET_SLOT:
                return ROCKETPOP_BULLET_ID;
            case SMOKE_BULLET_SLOT:
                return SMOKE_BULLET_ID;
            case CHERRY_BULLET_SLOT:
                return CHERRY_BULLET_ID;
            case AQUAMARINE_BULLET_SLOT:
                return AQUAMARINE_BULLET_ID;
            case RAINBOW_BULLET_SLOT:
                return RAINBOW_BULLET_ID;
        }
        return "";
    }


        public void setElementAnimation(int itemSlot){
        switch (itemSlot) {

            case YELLOW_BULLET_SLOT:
                elementSheet = yellowBullet;
                title = "YELLOW";
                break;
            case RED_BULLET_SLOT:
                elementSheet = redBullet;
                title = "RED";
                break;
            case GREEN_BULLET_SLOT:
                elementSheet = greenBullet;
                title = "GREEN";
                break;
            case BLUE_BULLET_SLOT:
                elementSheet = blueBullet;
                title = "BLUE";
                break;
            case PURPLE_BULLET_SLOT:
                elementSheet = purpleBullet;
                title = "PURPLE";
                break;
            case ORANGE_BULLET_SLOT:
                elementSheet = orangeBullet;
                title = "ORANGE";
                break;
            case PINK_BULLET_SLOT:
                elementSheet = pinkBullet;
                title = "PINK";
                break;
            case COTTON_CANDY_BULLET_SLOT:
                elementSheet = cottonCandyBullet;
                    title = "COTTON CANDY";
                break;
            case ROCKETPOP_BULLET_SLOT:
                elementSheet = rocketPopBullet;
                title = "ROCKET POP";
                break;
            case SMOKE_BULLET_SLOT:
                elementSheet = smokeBullet;
                title = "SMOKE";
                break;
            case CHERRY_BULLET_SLOT:
                elementSheet = cherryBullet;
                title = "WHITE CHERRY";
                break;
            case AQUAMARINE_BULLET_SLOT:
                elementSheet = aquamarineBullet;
                title = "AQUAMARINE";
                break;
            case RAINBOW_BULLET_SLOT:
                elementSheet = rainbowBullet;
                title = "RAINBOW";
                break;
        }

        if(elementSheet != null) {
            elementAnimation = Anim.createAnimation(elementSheet, 1, 1f);
            elementAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }
    }
    public static int getPriceByBulletSlot(int bulletSlot){
        if(bulletSlot == RED_BULLET_SLOT){
            return RED_BULLET_PRICE;
        }
        else if(bulletSlot == GREEN_BULLET_SLOT){
            return GREEN_BULLET_PRICE;
        }
        else if(bulletSlot == BLUE_BULLET_SLOT){
            return BLUE_BULLET_PRICE;
        }
        else if(bulletSlot == PURPLE_BULLET_SLOT){
            return PURPLE_BULLET_PRICE;
        }
        else if(bulletSlot == ORANGE_BULLET_SLOT){
            return ORANGE_BULLET_PRICE;
        }
        else if(bulletSlot == PINK_BULLET_SLOT){
            return PINK_BULLET_PRICE;
        }
        else if(bulletSlot == COTTON_CANDY_BULLET_SLOT){
            return COTTON_CANDY_BULLET_PRICE;
        }
        else if(bulletSlot == ROCKETPOP_BULLET_SLOT){
            return ROCKETPOP_BULLET_PRICE;
        }
        else if(bulletSlot == SMOKE_BULLET_SLOT){
            return SMOKE_BULLET_PRICE;
        }
        else if(bulletSlot == CHERRY_BULLET_SLOT){
            return CHERRY_BULLET_PRICE;
        }
        else if(bulletSlot == AQUAMARINE_BULLET_SLOT){
            return AQUAMARINE_BULLET_PRICE;
        }
        else if(bulletSlot == RAINBOW_BULLET_SLOT){
            return RAINBOW_BULLET_PRICE;
        }
        return 0;
    }
}
