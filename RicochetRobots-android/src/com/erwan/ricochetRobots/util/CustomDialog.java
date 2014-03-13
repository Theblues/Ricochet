package com.erwan.ricochetRobots.util;

import com.erwan.ricochetRobots.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class CustomDialog extends DialogFragment {

    public static CustomDialog newInstance(int title, int message) {
	CustomDialog frag = new CustomDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("message", message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	int title = getArguments().getInt("title");
	int message = getArguments().getInt("message");
	
        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.bt_ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    }
                )
                .create();
    }
}
