package jm.games.nebula;

import games.rednblack.miniaudio.MASound;

public interface IActivityRequestHandler {
    public void showAd(boolean inGame, boolean soundEnabled, Prefs prefs, MASound gemSound);
    public boolean isAdLoaded();
}
