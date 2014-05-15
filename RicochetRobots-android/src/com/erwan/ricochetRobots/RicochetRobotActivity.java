package com.erwan.ricochetRobots;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.erwan.ricochetRobots.util.RicochetInterface;

public class RicochetRobotActivity extends AndroidApplication implements
	RicochetInterface {

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    private AndroidApplicationConfiguration config;
    private RicochetRobots rb;

    private BluetoothAdapter mBluetoothAdapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	config = new AndroidApplicationConfiguration();
	config.useAccelerometer = false;
	config.useCompass = false;
	config.useWakelock = true;
	config.useGL20 = true;
	rb = new RicochetRobots(this);
	initialize(rb, config);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
	    // on ne fait rien c'est gérer avec lib gdx.
	    return true;
	return super.onKeyDown(keyCode, event);
    }

    public void possedeBluetooth() {
	handler.post(new Runnable() {
	    public void run() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    }
	});
	// Device does not support Bluetooth
	if (mBluetoothAdapter == null) {
	    handler.post(new Runnable() {
		public void run() {
		    Toast.makeText(getApplicationContext(),
			    R.string.support_bluetooth, Toast.LENGTH_LONG)
			    .show();
		}
	    });
	} else
	    activeBluetooth();
    }

    public void activeBluetooth() {
	if (!mBluetoothAdapter.isEnabled()) {
	    Intent enableBtIntent = new Intent(
		    BluetoothAdapter.ACTION_REQUEST_ENABLE);
	    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	} else {
	    Log.d("Ricochet", "scan");
	    scanning();
	}
    }

    public void scanning() {
	Intent serverIntent = new Intent(this, DeviceListActivity.class);
	startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }

    public void popup_indisponible() {
	handler.post(new Runnable() {
	    public void run() {
		Toast.makeText(getApplicationContext(), R.string.indispo_txt,
			Toast.LENGTH_LONG).show();
	    }
	});
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// Check which request we're responding to
	switch (requestCode) {
	case REQUEST_ENABLE_BT:
	    // Make sure the request was successful
	    if (resultCode == RESULT_CANCELED)
		handler.post(new Runnable() {
		    public void run() {
			Toast.makeText(getApplicationContext(),
				R.string.active_bluetooth, Toast.LENGTH_LONG)
				.show();
		    }
		});
	    else if (resultCode == RESULT_OK)
		scanning();
	    break;
	case REQUEST_CONNECT_DEVICE:
	    // When DeviceListActivity returns with a device to connect
	    if (resultCode == Activity.RESULT_OK) {
		// Get the device MAC address
		String address = data.getExtras().getString(
			DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		// Get the BLuetoothDevice object
		BluetoothDevice device = mBluetoothAdapter
			.getRemoteDevice(address);
		// Attempt to connect to the device

		// TODO A CONTINUER
		// mChatService.connect(device);
	    }
	    break;
	default:
	    break;
	}
    }

    protected void onDestroy() {
	super.onDestroy();
	mBluetoothAdapter.cancelDiscovery();
    }
}