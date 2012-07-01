package com.dicent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * can't use this class yet because the compatibility package
 * doesn't allow an fragment-capable PreferenceActivity
 *
 */
public class ResetDialogFragment extends DialogFragment {
	private DicentPreferencesActivity prefActivity;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if (activity instanceof DicentPreferencesActivity)
			prefActivity = (DicentPreferencesActivity)activity;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		prefActivity = null;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getResources().getString(R.string.preferencesResetDialog));
    	builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			prefActivity.resetEverything();
    			prefActivity.onContentChanged();
    		}
        });
    	builder.setNegativeButton(getResources().getString(R.string.cancel), null);
    	return builder.create();
	}
}
