/** This file is part of Dicent.
 *
 *  Dicent is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  Dicent is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with Dicent.  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.dicent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class DicentActivity extends Activity {
	public static final int DIALOG_ABOUT = 0;
	public static final int DIALOG_CHANGE_PLAYER_NAME = 1;
	public static final int DIALOG_RESET = 2;
	
	protected DicentState state;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		state = DicentState.init(this);
	}
	
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
