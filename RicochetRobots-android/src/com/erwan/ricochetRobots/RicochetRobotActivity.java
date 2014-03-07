package com.erwan.ricochetRobots;

import java.util.Set;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.erwan.ricochetRobots.bluetooth.BluetoothConnexion;
import com.erwan.ricochetRobots.bluetooth.BluetoothInterface;

public class RicochetRobotActivity extends AndroidApplication implements
	BluetoothInterface {
    private static final int REQUEST_ENABLE_BT = 0;

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

    @Override
    public void possedeBluetooth() {
	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	// Device does not support Bluetooth
	if (mBluetoothAdapter == null) {
	    Builder builder = new Builder(this);
	    builder.setTitle(R.string.error_bluetooth);
	    builder.setMessage(R.string.support_bluetooth);
	    builder.setCancelable(false);
	    builder.setNeutralButton(R.string.bt_ok,
		    new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			    dialog.cancel();
			}
		    });
	    AlertDialog alert = builder.create();
	    alert.show();
	}

	BluetoothConnexion.possedeBluetooth();
    }

    public void activeBluetooth() {
	if (!mBluetoothAdapter.isEnabled()) {
	    Intent enableBtIntent = new Intent(
		    BluetoothAdapter.ACTION_REQUEST_ENABLE);
	    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	} else {
	    BluetoothConnexion.actif();
	}
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// Check which request we're responding to
	if (requestCode == REQUEST_ENABLE_BT) {
	    // Make sure the request was successful
	    if (resultCode == RESULT_CANCELED) {
		// popup erreur
		Builder builder = new Builder(this);
		builder.setTitle(R.string.error_bluetooth);
		builder.setMessage(R.string.active_bluetooth);
		builder.setCancelable(false);
		builder.setNeutralButton(R.string.bt_ok,
			new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			    }
			});
		AlertDialog alert = builder.create();
		alert.show();
	    } else if (resultCode == RESULT_OK)
		BluetoothConnexion.actif();
	}
    }

    public void findDevice() {
	Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
		.getBondedDevices();
	// If there are paired devices
	if (pairedDevices.size() > 0) {
	    // Loop through paired devices
	    for (BluetoothDevice device : pairedDevices) {
		// Add the name and address to an array adapter to show in a
		// ListView
		Set<String> mArrayAdapter = null;
		mArrayAdapter
			.add(device.getName() + "\n" + device.getAddress());
	    }
	}
    }
}