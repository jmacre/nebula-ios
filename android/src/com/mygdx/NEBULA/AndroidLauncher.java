package com.mygdx.NEBULA;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidAudio;
import com.badlogic.gdx.backends.android.AsynchronousAndroidAudio;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
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

import games.rednblack.miniaudio.MiniAudio;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler{
	private final int showAds = 1;
	private final int hideAds = 0;
	AdView adView;
	AdRequest adRequest;
	private RewardedAd mRewardedAd;


	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case showAds:
				{
					adView.setVisibility(View.VISIBLE);
					break;
				}
				case hideAds:
				{
					adView.setVisibility(View.GONE);
					break;
				}
			}
		}
	};


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobileAds.initialize(this);

		List<String> testDeviceIds = Arrays.asList("21D5A8FE5E36C5B87CE8DB9820C8BD88");
		RequestConfiguration configuration =
				new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
		MobileAds.setRequestConfiguration(configuration);

		RelativeLayout layout = new RelativeLayout(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;

		View gameView = initializeForView(new Main(getContext().getAssets(), this), config);

		// Create and setup the AdMob view
//		adView = new AdView(this);
//		adView.setAdSize(AdSize.BANNER);
//		adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111"); // Put in your secret key here


		adRequest = new AdRequest.Builder().build();
		RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
				adRequest, new RewardedAdLoadCallback() {
					@Override
					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
						// Handle the error.
						Log.d(TAG, loadAdError.toString());
						mRewardedAd = null;
					}

					@Override
					public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
						mRewardedAd = rewardedAd;
						Log.d(TAG, "Ad was loaded.");
						showRewardedVideo();
					}
				});




//		adView.loadAd(adRequest);

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
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? showAds : hideAds);
	}

	private void showRewardedVideo() {

		if (mRewardedAd == null) {
			Log.d("TAG", "The rewarded ad wasn't ready yet.");
			return;
		}
//		showVideoButton.setVisibility(View.INVISIBLE);

		mRewardedAd.setFullScreenContentCallback(
				new FullScreenContentCallback() {
					@Override
					public void onAdShowedFullScreenContent() {
						// Called when ad is shown.
						Log.d(TAG, "onAdShowedFullScreenContent");
						Toast.makeText(AndroidLauncher.this, "onAdShowedFullScreenContent", Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onAdFailedToShowFullScreenContent(AdError adError) {
						// Called when ad fails to show.
						Log.d(TAG, "onAdFailedToShowFullScreenContent");
						// Don't forget to set the ad reference to null so you
						// don't show the ad a second time.
						mRewardedAd = null;
						Toast.makeText(
										AndroidLauncher.this, "onAdFailedToShowFullScreenContent", Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onAdDismissedFullScreenContent() {
						// Called when ad is dismissed.
						// Don't forget to set the ad reference to null so you
						// don't show the ad a second time.
						mRewardedAd = null;
						Log.d(TAG, "onAdDismissedFullScreenContent");
						Toast.makeText(AndroidLauncher.this, "onAdDismissedFullScreenContent", Toast.LENGTH_SHORT)
								.show();
						// Preload the next rewarded ad.
//						AndroidLauncher.this.loadRewardedAd();
					}
				});
		Activity activityContext = AndroidLauncher.this;
		mRewardedAd.show(
				activityContext,
				new OnUserEarnedRewardListener() {
					@Override
					public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
						// Handle the reward.
						Log.d("TAG", "The user earned the reward.");
						int rewardAmount = rewardItem.getAmount();
						String rewardType = rewardItem.getType();
					}
				});
	}
}
