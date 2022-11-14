package com.mygdx.NEBULA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {
    private final Preferences prefs ;

    public Prefs(){
        prefs = Gdx.app.getPreferences("nebula");
    }
    public int getHighScore(){
        return prefs.getInteger("highscore", 0);
    }
    public void setHighScore(int currentScore){
        if(currentScore > getHighScore()) {
            prefs.putInteger("highscore", currentScore);
            prefs.flush();
        }
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
}