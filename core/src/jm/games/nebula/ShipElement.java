package jm.games.nebula;

import static jm.games.nebula.Anim.DEFAULT_FRAME_DURATION;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Map;

public class ShipElement extends GameElements{
    public static int shipCount = 14; // one less than actual count for cycling between ships

    public static final String SHIP_ID = "0"; //this determines ship ownership in prefs
    public static final String BLUE_SHIP_ID = "1";
    public static final String RED_SHIP_ID = "2";
    public static final String CYAN_SHIP_ID = "3";
    public static final String YELLOW_SHIP_ID = "4";
    public static final String BLACK_SHIP_ID = "5";
    public static final String GREEN_SHIP_ID = "6";
    public static final String ORANGE_SHIP_ID = "7";
    public static final String BRED_SHIP_ID = "8";
    public static final String PURPLE_SHIP_ID = "9";
    public static final String NEGATIVE_SHIP_ID = "A";
    public static final String BLACK_GREEN_SHIP_ID = "B";
    public static final String MINT_SHIP_ID = "C";
    public static final String NEON_SHIP_ID = "D";
    public static final String BLUE_ORANGE_SHIP_ID = "E";


    public static final int SHIP_SLOT = 0; //this determines the display order in the shop
    public static final int BLUE_SHIP_SLOT = 1;
    public static final int BLACK_GREEN_SHIP_SLOT = 2;
    public static final int RED_SHIP_SLOT = 3;
    public static final int CYAN_SHIP_SLOT = 4;
    public static final int MINT_SHIP_SLOT = 5;
    public static final int YELLOW_SHIP_SLOT = 6;
    public static final int BLACK_SHIP_SLOT = 7;
    public static final int NEON_SHIP_SLOT = 8;
    public static final int GREEN_SHIP_SLOT = 9;
    public static final int BLUE_ORANGE_SHIP_SLOT = 10;
    public static final int ORANGE_SHIP_SLOT = 11;
    public static final int NEGATIVE_SHIP_SLOT = 12;
    public static final int BRED_SHIP_SLOT = 13;
    public static final int PURPLE_SHIP_SLOT = 14;

    public static final int BLUE_SHIP_PRICE = 100;
    public static final int BLACK_GREEN_SHIP_PRICE = 250;
    public static final int RED_SHIP_PRICE = 500;
    public static final int CYAN_SHIP_PRICE = 1000;
    public static final int MINT_SHIP_PRICE = 1500;
    public static final int YELLOW_SHIP_PRICE = 2500;
    public static final int BLACK_SHIP_PRICE = 5000;
    public static final int NEON_SHIP_PRICE = 7500;
    public static final int GREEN_SHIP_PRICE = 10000;
    public static final int BLUE_ORANGE_SHIP_PRICE = 12500;
    public static final int ORANGE_SHIP_PRICE = 15000;
    public static final int NEGATIVE_SHIP_PRICE = 99999;
    public static final int BRED_SHIP_PRICE = 99999;
    public static final int PURPLE_SHIP_PRICE = 99999;


    float x, y, width, height;

    static Sprite mainShip, blackShip, redShip, purpleShip, yellowShip, cyanShip;
    static Sprite bredShip, greenShip, orangeShip, blueShip, negativeShip;
    static Sprite blueOrangeShip, neonShip, mintShip, blackGreenShip;

    private int colorId;

    private Sprite elementSheet;
    private String title;

    Animation<TextureRegion> elementAnimation;
    Anim elementAnim = new Anim();

    public ShipElement(int id, int colorId, float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colorId = colorId;

        if(String.valueOf(id).equals(SHIP_ID)) {

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
        cyanShip = new Sprite(assets.assetManager.get(Assets.ship_cyan_ss, Texture.class));
        blueShip = new Sprite(assets.assetManager.get(Assets.ship_blue_ss, Texture.class));
        bredShip = new Sprite(assets.assetManager.get(Assets.ship_bred_ss, Texture.class));
        orangeShip = new Sprite(assets.assetManager.get(Assets.ship_orange_ss, Texture.class));
        greenShip = new Sprite(assets.assetManager.get(Assets.ship_green_ss, Texture.class));
        negativeShip = new Sprite(assets.assetManager.get(Assets.ship_negative_ss, Texture.class));
        blueOrangeShip = new Sprite(assets.assetManager.get(Assets.ship_blue_orange_ss, Texture.class));
        neonShip = new Sprite(assets.assetManager.get(Assets.ship_neon_ss, Texture.class));
        mintShip = new Sprite(assets.assetManager.get(Assets.ship_mint_ss, Texture.class));
        blackGreenShip = new Sprite(assets.assetManager.get(Assets.ship_black_green_ss, Texture.class));
    }

    public String getTitle(){
        return title;
    }
    public static String getIdBySlot(int slot) {
        switch (slot) {
            case SHIP_SLOT:
                return SHIP_ID;
            case RED_SHIP_SLOT:
                return RED_SHIP_ID;
            case BLACK_SHIP_SLOT:
                return BLACK_SHIP_ID;
            case PURPLE_SHIP_SLOT:
                return PURPLE_SHIP_ID;
            case YELLOW_SHIP_SLOT:
                return YELLOW_SHIP_ID;
            case CYAN_SHIP_SLOT:
                return CYAN_SHIP_ID;
            case BLUE_SHIP_SLOT:
                return BLUE_SHIP_ID;
            case BRED_SHIP_SLOT:
                return BRED_SHIP_ID;
            case GREEN_SHIP_SLOT:
                return GREEN_SHIP_ID;
            case ORANGE_SHIP_SLOT:
                return ORANGE_SHIP_ID;
            case NEGATIVE_SHIP_SLOT:
                return NEGATIVE_SHIP_ID;
            case BLACK_GREEN_SHIP_SLOT:
                return BLACK_GREEN_SHIP_ID;
            case MINT_SHIP_SLOT:
                return MINT_SHIP_ID;
            case NEON_SHIP_SLOT:
                return NEON_SHIP_ID;
            case BLUE_ORANGE_SHIP_SLOT:
                return BLUE_ORANGE_SHIP_ID;
        }
        return "";
    }


        public void setElementAnimation(int itemSlot){
        switch (itemSlot) {

            case SHIP_SLOT:
                elementSheet = mainShip;
                title = "CLASSIC";
                break;
            case RED_SHIP_SLOT:
                elementSheet = redShip;
                title = "CRIMSON";
                break;
            case BLACK_SHIP_SLOT:
                elementSheet = blackShip;
                title = "PHANTOM";
                break;
            case PURPLE_SHIP_SLOT:
                elementSheet = purpleShip;
                title = "NEBULA";
                break;
            case YELLOW_SHIP_SLOT:
                elementSheet = yellowShip;
                title = "FLASH";
                break;
            case CYAN_SHIP_SLOT:
                elementSheet = cyanShip;
                title = "FROZEN";
                break;
            case BLUE_SHIP_SLOT:
                elementSheet = blueShip;
                title = "NEPTUNE";
                break;
            case BRED_SHIP_SLOT:
                elementSheet = bredShip;
                    title = "NEMESIS";
                break;
            case GREEN_SHIP_SLOT:
                elementSheet = greenShip;
                title = "LIME";
                break;
            case ORANGE_SHIP_SLOT:
                elementSheet = orangeShip;
                title = "OASIS";
                break;
            case NEGATIVE_SHIP_SLOT:
                elementSheet = negativeShip;
                title = "NEGATIVE";
                break;
            case MINT_SHIP_SLOT:
                elementSheet = mintShip;
                title = "MINT";
                break;
            case NEON_SHIP_SLOT:
                elementSheet = neonShip;
                title = "NEON";
                break;
            case BLACK_GREEN_SHIP_SLOT:
                elementSheet = blackGreenShip;
                title = "ACID";
                break;
            case BLUE_ORANGE_SHIP_SLOT:
                elementSheet = blueOrangeShip;
                title = "NOVA";
                break;
        }

        if(elementSheet != null) {
            elementAnimation = Anim.createAnimation(elementSheet, 4, DEFAULT_FRAME_DURATION * 1.5f);
            elementAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }
    }
    public static int getPriceByShipSlot(int shipSlot){
        if(shipSlot == RED_SHIP_SLOT){
            return RED_SHIP_PRICE;
        }
        else if(shipSlot == BLACK_SHIP_SLOT){
            return BLACK_SHIP_PRICE;
        }
        else if(shipSlot == PURPLE_SHIP_SLOT){
            return PURPLE_SHIP_PRICE;
        }
        else if(shipSlot == YELLOW_SHIP_SLOT){
            return YELLOW_SHIP_PRICE;
        }
        else if(shipSlot == CYAN_SHIP_SLOT){
            return CYAN_SHIP_PRICE;
        }
        else if(shipSlot == BLUE_SHIP_SLOT){
            return BLUE_SHIP_PRICE;
        }
        else if(shipSlot == BRED_SHIP_SLOT){
            return BRED_SHIP_PRICE;
        }
        else if(shipSlot == ORANGE_SHIP_SLOT){
            return ORANGE_SHIP_PRICE;
        }
        else if(shipSlot == GREEN_SHIP_SLOT){
            return GREEN_SHIP_PRICE;
        }
        else if(shipSlot == NEGATIVE_SHIP_SLOT){
            return NEGATIVE_SHIP_PRICE;
        }
        else if(shipSlot == BLUE_ORANGE_SHIP_SLOT){
            return BLUE_ORANGE_SHIP_PRICE;
        }
        else if(shipSlot == NEON_SHIP_SLOT){
            return NEON_SHIP_PRICE;
        }
        else if(shipSlot == BLACK_GREEN_SHIP_SLOT){
            return BLACK_GREEN_SHIP_PRICE;
        }
        else if(shipSlot == MINT_SHIP_SLOT){
            return MINT_SHIP_PRICE;
        }
        return 0;
    }
}
