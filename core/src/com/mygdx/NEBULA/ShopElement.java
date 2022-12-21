package com.mygdx.NEBULA;

import static com.mygdx.NEBULA.Anim.DEFAULT_FRAME_DURATION;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShopElement extends GameElements{
    public static final int SHIP_ID = 0; //this determines the order in the shop
    public static final int BLUE_SHIP_ID = 1;
    public static final int RED_SHIP_ID = 2;
    public static final int CYAN_SHIP_ID = 3;
    public static final int YELLOW_SHIP_ID = 4;
    public static final int BLACK_SHIP_ID = 5;
    public static final int GREEN_SHIP_ID = 6;
    public static final int ORANGE_SHIP_ID = 7;
    public static final int BRED_SHIP_ID = 8;
    public static final int PURPLE_SHIP_ID = 9;

    public static final int BLUE_SHIP_PRICE = 50;
    public static final int RED_SHIP_PRICE = 100;
    public static final int CYAN_SHIP_PRICE = 250;
    public static final int YELLOW_SHIP_PRICE = 500;
    public static final int BLACK_SHIP_PRICE = 1000;
    public static final int GREEN_SHIP_PRICE = 1500;
    public static final int ORANGE_SHIP_PRICE = 2500;
    public static final int BRED_SHIP_PRICE = 5000;
    public static final int PURPLE_SHIP_PRICE = 9999;


    float x, y, width, height;

    static Sprite mainShip, blackShip, redShip, purpleShip, yellowShip, cyanShip;
    static Sprite bredShip, greenShip, orangeShip, blueShip;

    public static int shipCount = 9; // one less than actual count for cycling between ships
    private int colorId;

    private Sprite elementSheet;
    private String title;

    Animation<TextureRegion> elementAnimation;
    Anim elementAnim = new Anim();

    public ShopElement(int id, int colorId, float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colorId = colorId;

        if(id == SHIP_ID) {

            setElementAnimation(colorId);

            if(elementSheet != null) {
                elementAnimation = Anim.createAnimation(elementSheet, 4, DEFAULT_FRAME_DURATION * 1.5f);
                elementAnimation.setPlayMode(Animation.PlayMode.LOOP);
            }
        }
    }
    public void render(float stateTime, SpriteBatch batch){
        elementAnim.drawAnim(elementAnimation, stateTime, x, y, width, height, true, batch);
    }

    public static void createElements(Assets assets){
        mainShip = new Sprite(assets.assetManager.get(Assets.ship_ss, Texture.class));
        redShip = new Sprite(assets.assetManager.get(Assets.ship_red_ss, Texture.class));
        blackShip = new Sprite(assets.assetManager.get(Assets.ship_black_ss, Texture.class));
        purpleShip = new Sprite(assets.assetManager.get(Assets.ship_purple_ss, Texture.class));
        yellowShip = new Sprite(assets.assetManager.get(Assets.ship_yellow_ss, Texture.class));
        cyanShip = new Sprite((assets.assetManager.get(Assets.ship_cyan_ss, Texture.class)));
        blueShip = new Sprite((assets.assetManager.get(Assets.ship_blue_ss, Texture.class)));
        bredShip = new Sprite((assets.assetManager.get(Assets.ship_bred_ss, Texture.class)));
        orangeShip = new Sprite((assets.assetManager.get(Assets.ship_orange_ss, Texture.class)));
        greenShip = new Sprite((assets.assetManager.get(Assets.ship_green_ss, Texture.class)));
    }

    public String getTitle(){
        return title;
    }

    public void setElementAnimation(int colorId){
        switch (colorId) {

            case SHIP_ID:
                elementSheet = mainShip;
                title = "CLASSIC";
                break;
            case RED_SHIP_ID:
                elementSheet = redShip;
                title = "CRIMSON";
                break;
            case BLACK_SHIP_ID:
                elementSheet = blackShip;
                title = "PHANTOM";
                break;
            case PURPLE_SHIP_ID:
                elementSheet = purpleShip;
                title = "NEBULA";
                break;
            case YELLOW_SHIP_ID:
                elementSheet = yellowShip;
                title = "FLASH";
                break;
            case CYAN_SHIP_ID:
                elementSheet = cyanShip;
                title = "FROZEN";
                break;
            case BLUE_SHIP_ID:
                elementSheet = blueShip;
                title = "NEPTUNE";
                break;
            case BRED_SHIP_ID:
                elementSheet = bredShip;
                    title = "NEMESIS";
                break;
            case GREEN_SHIP_ID:
                elementSheet = greenShip;
                title = "LIME";
                break;
            case ORANGE_SHIP_ID:
                elementSheet = orangeShip;
                title = "TROPICAL";
                break;
        }

        if(elementSheet != null) {
            elementAnimation = Anim.createAnimation(elementSheet, 4, DEFAULT_FRAME_DURATION * 1.5f);
            elementAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }
    }
    public static int getPriceByShipId(int shipId){
        if(shipId == RED_SHIP_ID){
            return RED_SHIP_PRICE;
        }
        else if(shipId == BLACK_SHIP_ID){
            return BLACK_SHIP_PRICE;
        }
        else if(shipId == PURPLE_SHIP_ID){
            return PURPLE_SHIP_PRICE;
        }
        else if(shipId == YELLOW_SHIP_ID){
            return YELLOW_SHIP_PRICE;
        }
        else if(shipId == CYAN_SHIP_ID){
            return CYAN_SHIP_PRICE;
        }
        else if(shipId == BLUE_SHIP_ID){
            return BLUE_SHIP_PRICE;
        }
        else if(shipId == BRED_SHIP_ID){
            return BRED_SHIP_PRICE;
        }
        else if(shipId == ORANGE_SHIP_ID){
            return ORANGE_SHIP_PRICE;
        }
        else if(shipId == GREEN_SHIP_ID){
            return GREEN_SHIP_PRICE;
        }
        return 0;
    }

    public void setColorId(int colorId){
        this.colorId = colorId;
    }
    public int getColorId(){
        return colorId;
    }
}
