package com.dicent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

public class AboutDialogFragment extends DialogFragment {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("About");
    	TextView aboutTextView = new TextView(getActivity());
    	aboutTextView.setText(R.string.about);
    	builder.setMessage(R.string.about);
    	
    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			dialog.dismiss();
    		}
    	});
    	return builder.create();
	}
}
