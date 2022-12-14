package com.mygdx.NEBULA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.List;

public class Prefs {
    private final Preferences prefs ;

    public Prefs(){
        prefs = Gdx.app.getPreferences("nebula");
    }
    public int getHighScore(){
        return prefs.getInteger("highScore", 0);
    }
    public void setHighScore(int currentScore){
        prefs.putInteger("highScore", currentScore);
        prefs.flush();
    }
    public int getGemCount(){
        return prefs.getInteger("gemCount", 0);
    }
    public void setGemCount(int gemCount){
        prefs.putInteger("gemCount", gemCount);
        prefs.flush();
    }

    public void setSound(boolean hasSound){
        prefs.putBoolean("hasSound",hasSound);
        prefs.flush();
    }

    public void setShip(int selectedShip){
        prefs.putInteger("shipColor", selectedShip);
        prefs.flush();
    }
    public int getShip(){
        return prefs.getInteger("shipColor", 0);
    }
    public boolean hasSound(){
        return prefs.getBoolean("hasSound", true);
    }
    public String getUnlockedShips(){
        return prefs.getString("unlockedShips");
    }
    public void setUnlockedShips(String unlockedShips){
        prefs.putString("unlockedShips", unlockedShips + ' ');
        //blank char is to ensure that 10 isn't confused with 1 and 0
        prefs.flush();
    }
}