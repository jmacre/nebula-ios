package com.mygdx.NEBULA;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;

import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSCoder;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.gamecontroller.GCSystemGestureState;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.apple.uikit.UIRectEdge;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.google.mobileads.GADFullScreenContentDelegateAdapter;
import org.robovm.pods.google.mobileads.GADFullScreenPresentingAd;
import org.robovm.pods.google.mobileads.GADMobileAds;
import org.robovm.pods.google.mobileads.GADRequest;
import org.robovm.pods.google.mobileads.GADRewardedAd;

import java.util.List;

import games.rednblack.miniaudio.MASound;

public class IOSLauncher extends IOSApplication.Delegate implements IActivityRequestHandler {
    IOSApplication iosApplication;
    NSString testDeviceIdentifier = new NSString("752d10da8f6c7207f94659417c6ac2cf");
    private GADRewardedAd mRewardedAd;
    GADRequest request = new GADRequest();

    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.screenEdgesDeferringSystemGestures = UIRectEdge.All;

        GADMobileAds.sharedInstance().getRequestConfiguration().setTestDeviceIdentifiers(new NSArray<>(testDeviceIdentifier));
        GADMobileAds.sharedInstance().start(status -> {
            System.out.println("GADMobileAds started with status == " + status);
        });


        request = new GADRequest();
        loadAd();

        iosApplication = new IOSApplication(new Main(this), config);

    }

    public static void main(String[] argv) {

        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);

        pool.close();
    }

    @Override
    public void showAd(boolean soundEnabled, Prefs prefs, MASound gemSound) {
        if(mRewardedAd == null) {
            loadAd();
        }
        else{
            mRewardedAd.setFullScreenContentDelegate(new GADFullScreenContentDelegateAdapter() {
                @Override
                public void adDidDismissFullScreenContent(GADFullScreenPresentingAd ad) {
                    mRewardedAd = null;
                    loadAd();
                    int gemCount = prefs.getGemCount();
                    int reward = 25;
                    prefs.setGemCount(gemCount + reward);


                    if (soundEnabled) {
                        gemSound.stop();
                        gemSound.play();
                    }
                    System.out.println("adDidDismissFullScreenContent");
                }

                @Override
                public void didFailToPresentFullScreenContent(GADFullScreenPresentingAd ad, NSError error) {
                    mRewardedAd = null;
                }
            });
            mRewardedAd.present(iosApplication.getUIViewController(), new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }


    public void loadAd() {
        if (mRewardedAd == null) {
            GADRewardedAd.load("ca-app-pub-8689816410492919/2589576727", request,
                    new VoidBlock2<GADRewardedAd, NSError>() {
                @Override
                public void invoke(GADRewardedAd ad, NSError error) {
                    if (error != null) {
                        System.out.println("failed to load ad due to " + error);
                    } else {
                        mRewardedAd = ad;
                    }
                }
            });
        }
    }

}