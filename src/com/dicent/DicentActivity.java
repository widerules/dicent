package com.dicent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class DicentActivity extends Activity {
	public static final int DIALOG_ABOUT = 0;
	public static final int DIALOG_CHANGE_PLAYER_NAME = 1;
	public static final int DIALOG_RESET = 2;
	
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.menuPreferencesButton:
    		Intent dicentPreferences = new Intent(getBaseContext(), DicentPreferences.class);
    		startActivity(dicentPreferences);
    		return true;
    	case R.id.menuAboutButton:
    		showDialog(DIALOG_ABOUT);
    		return true;
    	}
    	return false;
    }
    
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(id) {
        case DIALOG_ABOUT:
        	builder.setTitle("About");
        	TextView aboutTextView = new TextView(this);
        	aboutTextView.setText(R.string.about);
        	builder.setMessage(R.string.about);
        	
        	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int which) {
        			dialog.dismiss();
        		}
        	});
        	dialog = builder.create();
        	break;
        default:
            dialog = null;
        }
        return dialog;
    }
}
