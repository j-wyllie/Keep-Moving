package com.mygdx.game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.tool.AdHandler;

public class AndroidLauncher extends AndroidApplication implements AdHandler {
	private static final String TAG = "AndroidLauncher";
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;
	protected AdView adView;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case SHOW_ADS:
					adView.setVisibility(View.VISIBLE);
					break;
				case HIDE_ADS:
					adView.setVisibility(View.GONE);
					break;
			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		//initialize(new ZombieTrain(), config);
        MobileAds.initialize(this, "ca-app-pub-9721517265393915~9208987096");

        RelativeLayout layout = new RelativeLayout(this);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        View gameView = initializeForView(new ZombieTrain(this), config);
        layout.addView(gameView);

        adView = new AdView(this);
        adView.setVisibility(View.VISIBLE);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                int visiblity = adView.getVisibility();
                adView.setVisibility(AdView.GONE);
                adView.setVisibility(visiblity);
                Log.i(TAG, "Ad Loaded...");
            }
        });
        adView.setAdSize(AdSize.SMART_BANNER);
        //http://www.google.com/admob
        adView.setAdUnitId("ca-app-pub-9721517265393915/5816536996");

        AdRequest.Builder builder = new AdRequest.Builder();
        //run once before uncommenting the following line. Get TEST device ID from the logcat logs.
        builder.addTestDevice("36B83DBB394D43CD0319F2A19B74489B");
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        layout.addView(adView, adParams);
        adView.loadAd(builder.build());

        setContentView(layout);
	}

	@Override
	public void showAds(Boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}
}
