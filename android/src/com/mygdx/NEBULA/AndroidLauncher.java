package com.mygdx.NEBULA;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidAudio;
import com.badlogic.gdx.backends.android.AsynchronousAndroidAudio;

import games.rednblack.miniaudio.MiniAudio;

public class AndroidLauncher extends AndroidApplication {
	MiniAudio miniAudio = new MiniAudio();
	@Override
	protected void onCreate (Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		miniAudio.setupAndroid(getContext().getAssets());
		initialize(new Main(miniAudio), config);
	}
}
