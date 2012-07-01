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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DicentActivity extends FragmentActivity {
	public static final String FRAGMENT_ABOUTDIALOG = "aboutDialog";
	
	protected DicentState state;
	
	private AboutDialogFragment aboutDialogFragment;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		state = DicentState.init(this);
		
		aboutDialogFragment = (AboutDialogFragment)getSupportFragmentManager().findFragmentByTag(FRAGMENT_ABOUTDIALOG);
		if (aboutDialogFragment == null) aboutDialogFragment = new AboutDialogFragment();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.menuPreferencesButton:
    		Intent dicentPreferences = new Intent(getBaseContext(), DicentPreferencesActivity.class);
    		startActivity(dicentPreferences);
    		return true;
    	case R.id.menuAboutButton:
    		aboutDialogFragment.show(getSupportFragmentManager(), FRAGMENT_ABOUTDIALOG);
    		return true;
    	}
    	return false;
    }
}
