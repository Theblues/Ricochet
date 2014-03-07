package com.erwan.ricochetRobots;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.erwan.interfaces.BluetoothInterface;

public class RicochetRobotActivity extends AndroidApplication implements
	BluetoothInterface {
    private static final int REQUEST_ENABLE_BT = 0;

    private BluetoothAdapter mBluetoothAdapter;
    private RicochetRobots rb;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
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
    public boolean possedeBluetooth() {
	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	// Device does not support Bluetooth
	if (mBluetoothAdapter == null)
	{
	    Builder builder = new Builder(this);
		builder.setMessage(
			"Votre téléphone ne possède pas le Bluetooth")
			.setCancelable(false)
			.setNeutralButton("OK",
				new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,
					    int id) {
					dialog.cancel();
					finish();
				    }
				});
		AlertDialog alert = builder.create();
		alert.show();
	    return false;
	}

	return true;
    }
    
    public void activeBluetooth()
    {
	if (!mBluetoothAdapter.isEnabled()) {
	    Intent enableBtIntent = new Intent(
		    BluetoothAdapter.ACTION_REQUEST_ENABLE);
	    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	}
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// Check which request we're responding to
	if (requestCode == REQUEST_ENABLE_BT) {
	    // Make sure the request was successful
	    if (resultCode == RESULT_CANCELED) {
		Builder builder = new Builder(this);
		builder.setMessage(
			"Vous devez activer le bluetooth pour jouer à 2")
			.setCancelable(false)
			.setNeutralButton("OK",
				new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,
					    int id) {
					dialog.cancel();
					finish();
				    }
				});
		AlertDialog alert = builder.create();
		alert.show();
	    }
	}
    }
}