package com.erwan.ricochetRobots;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.erwan.ricochetRobots.util.RicochetInterface;

public class RicochetRobotActivity extends AndroidApplication implements
	RicochetInterface {

    private static final String TAG = "RicochetRobotActivity";
    private static final boolean D = true;
    
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // type de message envoyé par le hundle
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    
    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;

    private AndroidApplicationConfiguration config;
    private RicochetRobots rb;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothRicochet mBluetoothRicochet;

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

		if (mBluetoothRicochet == null)
		    mBluetoothRicochet = new BluetoothRicochet(this, mHandler);
		mBluetoothRicochet.connect(device);
	    }
	    break;
	default:
	    break;
	}
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
	public void handleMessage(Message msg) {
	    switch (msg.what) {
	    case MESSAGE_STATE_CHANGE:
		if (D)
		    Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
		switch (msg.arg1) {
		case BluetoothRicochet.STATE_CONNECTED:
		   /* mTitle.setText(R.string.title_connected_to);
		    mTitle.append(mConnectedDeviceName);
		    mConversationArrayAdapter.clear();*/
		    break;
		case BluetoothRicochet.STATE_CONNECTING:
		   // mTitle.setText(R.string.title_connecting);
		    break;
		case BluetoothRicochet.STATE_LISTEN:
		case BluetoothRicochet.STATE_NONE:
		   // mTitle.setText(R.string.title_not_connected);
		    break;
		}
		break;
	    case MESSAGE_WRITE:
		byte[] writeBuf = (byte[]) msg.obj;
		// construct a string from the buffer
		String writeMessage = new String(writeBuf);
		mConversationArrayAdapter.add("Me: " + writeMessage);
		break;
	    case MESSAGE_READ:
		byte[] readBuf = (byte[]) msg.obj;
		// construct a string from the valid bytes in the buffer
		String readMessage = new String(readBuf, 0, msg.arg1);
		mConversationArrayAdapter.add(mConnectedDeviceName + ": "
			+ readMessage);
		break;
	    case MESSAGE_DEVICE_NAME:
		// save the connected device's name
		mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
		Toast.makeText(getApplicationContext(),
			"Connected to " + mConnectedDeviceName,
			Toast.LENGTH_LONG).show();
		break;
	    case MESSAGE_TOAST:
		Toast.makeText(getApplicationContext(),
			msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
			.show();
		break;
	    }
	}
    };

    protected void onDestroy() {
	super.onDestroy();
	mBluetoothAdapter.cancelDiscovery();
    }

}