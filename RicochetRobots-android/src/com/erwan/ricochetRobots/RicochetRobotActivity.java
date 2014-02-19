package com.erwan.ricochetRobots;

import android.os.Bundle;
import android.view.KeyEvent;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class RicochetRobotActivity extends AndroidApplication {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
	config.useAccelerometer = false;
	config.useCompass = false;
	config.useWakelock = true;
	config.useGL20 = true;
	initialize(new RicochetRobots(), config);
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	        // on ne fait rien c'est gérer avec lib gdx.
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
}