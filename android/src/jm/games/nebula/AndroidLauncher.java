package jm.games.nebula;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.pay.android.googlebilling.PurchaseManagerGoogleBilling;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Arrays;
import java.util.List;

import games.rednblack.miniaudio.MASound;
import pl.mk5.gdx.fireapp.GdxFIRCrash;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {
    AdRequest adRequest;
    private RewardedAd mRewardedAd;
    private boolean adFinished = false;
    private boolean adFailedToLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this);

        List<String> testDeviceIds = Arrays.asList("21D5A8FE5E36C5B87CE8DB9820C8BD88");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        RelativeLayout layout = new RelativeLayout(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;

        Main game = new Main(getContext().getAssets(), this);
        game.purchaseManager = new PurchaseManagerGoogleBilling(this);

        View gameView = initializeForView(game, config);

        adRequest = new AdRequest.Builder().build();


        // Add the libGDX view
        layout.addView(gameView);

        // Add the AdMob view
        RelativeLayout.LayoutParams adParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

//		layout.addView(adView, adParams);

        // Hook it all up
        setContentView(layout);

    }

    @Override
    public void showAd(boolean inGame, boolean soundEnabled, Prefs prefs, MASound gemSound) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showRewardedVideo(inGame, soundEnabled, prefs, gemSound);
            }
        });

    }

    @Override
    public boolean isAdLoaded() {
        return mRewardedAd != null;
    }

    @Override
    public boolean isAdFinished() {
        return adFinished;
    }

    @Override
    public void setAdFinished(boolean adFinished) {
        this.adFinished = adFinished;
    }

    @Override
    public void loadAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadOnUiThread();
            }
        });

    }

    @Override
    public boolean adFailedToLoad() {
        return adFailedToLoad;
    }

    @Override
    public void setAdFailedToLoad(boolean adFailedToLoad) {
        this.adFailedToLoad = adFailedToLoad;
    }

    private void loadOnUiThread(){
        if (mRewardedAd == null) {
            RewardedAd.load(this, "ca-app-pub-8689816410492919/3317793905",
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            mRewardedAd = null;
                            adFailedToLoad = true;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            mRewardedAd = rewardedAd;
                            adFailedToLoad = false;

                        }
                    });
        }
    }

    private void showRewardedVideo(boolean inGame, boolean soundEnabled, Prefs prefs, MASound gemSound) {
        if (mRewardedAd != null) {

            mRewardedAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdShowedFullScreenContent() {
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            mRewardedAd = null;
                            adFinished = true;
                            adFailedToLoad = true;
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            mRewardedAd = null;
                            adFinished = true;
                        }
                    });
            Activity activityContext = AndroidLauncher.this;
            mRewardedAd.show(
                    activityContext,
                    new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            mRewardedAd = null;
                            if (!inGame) {

                                int gemCount = prefs.getGemCount();
                                int reward = 100;
                                prefs.setGemCount(gemCount + reward);


                                if (soundEnabled) {
                                    gemSound.stop();
                                    gemSound.play();
                                }
                            }
                            adFinished = true;
                        }
                    });
        }
    }
}
