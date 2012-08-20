package com.dicent;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayerListAdapter extends BaseAdapter {
	private DicentState state = DicentState.instance();
	private boolean firstEd = true;
	private Activity activity;
	
	public PlayerListAdapter(Activity _activity) {
		activity = _activity;
		refreshDescentVersion();
	}
	
	@Override
	public int getCount() {
		return state.getPlayers().length;
	}

	@Override
	public Object getItem(int position) {
		return state.getPlayers()[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		int layoutId;
		if (firstEd) layoutId = R.layout.player_first_ed;
		else layoutId = R.layout.player_second_ed;
		LinearLayout layout = (LinearLayout)inflater.inflate(layoutId, null);
		TextView playerName = (TextView)layout.findViewById(R.id.playerName);
		playerName.setText(state.getPlayers()[position]);
		return layout;
	}
	
	public void preferencesChanged() {
		refreshDescentVersion();
		notifyDataSetChanged();
	}
	
	private void refreshDescentVersion() {
		//Log.d(null, )
		firstEd = state.getDescentVersion().equals(DicentPreferencesActivity.DESCENT_FIRST_EDITION);
	}
}
