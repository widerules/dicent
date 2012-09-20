package com.dicent;

import com.dicent.dice.DieData;
import com.dicent.dice.SideValues;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatsFragment extends Fragment {
	private View view;
	private TextView woundsText;
	private TextView surgesText;
	private TextView shieldsText;
	private TextView rangeText;
	private TextView enhancementText;
	
	private int mode = DicentActivity.MODE_FIRSTED;
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		Log.d(null, "CREATE VIEW ====================================");
		view = inflater.inflate(R.layout.result_stats, null);
		
		woundsText = (TextView)view.findViewById(R.id.resultWounds);
		surgesText = (TextView)view.findViewById(R.id.resultSurges);
		shieldsText = (TextView)view.findViewById(R.id.resultShields);
		rangeText = (TextView)view.findViewById(R.id.resultRange);
		enhancementText = (TextView)view.findViewById(R.id.resultEnhancement);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if (mode != DicentActivity.MODE_FIRSTED) {
			view.findViewById(R.id.enhancementLabel).setVisibility(View.GONE);
			view.findViewById(R.id.enhancementSeparator).setVisibility(View.GONE);
			enhancementText.setVisibility(View.GONE);
		}
		
		if (mode == DicentActivity.MODE_SECONDED_DEFENSE) {
			view.findViewById(R.id.woundsLabel).setVisibility(View.GONE);
			view.findViewById(R.id.woundsSeparator).setVisibility(View.GONE);
			woundsText.setVisibility(View.GONE);
			
			view.findViewById(R.id.surgesLabel).setVisibility(View.GONE);
			view.findViewById(R.id.surgesSeparator).setVisibility(View.GONE);
			surgesText.setVisibility(View.GONE);
			
			view.findViewById(R.id.rangeLabel).setVisibility(View.GONE);
			view.findViewById(R.id.rangeSeparator).setVisibility(View.GONE);
			rangeText.setVisibility(View.GONE);
		} else {
			view.findViewById(R.id.shieldsLabel).setVisibility(View.GONE);
			view.findViewById(R.id.shieldsSeparator).setVisibility(View.GONE);
			shieldsText.setVisibility(View.GONE);
		}
		
		updateStats();
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		
		view = woundsText = surgesText = shieldsText = rangeText = enhancementText = null;
	}
	
	public void setMode(int _mode) {
		mode = _mode;
	}
	
	public void updateStats() {
		int wounds = 0, surges = 0, shields = 0, range = 0, enhancement = 0;
		boolean fail = false;
		
		for (DieData die : DicentState.instance().getResultDice()) {
			SideValues currentSideValues = die.getSideValues();
			if (currentSideValues.isFail()) fail = true;
			else {
				wounds += currentSideValues.getWounds();
				surges += currentSideValues.getSurges();
				shields += currentSideValues.getShields();
				range += currentSideValues.getRange();
				enhancement += currentSideValues.getEnhancement();
			}
		}
		if (fail) wounds = surges = shields = range = enhancement = 0;
		woundsText.setText(Integer.toString(wounds));
		surgesText.setText(Integer.toString(surges));
		shieldsText.setText(Integer.toString(shields));
		rangeText.setText(Integer.toString(range));
		enhancementText.setText(Integer.toString(enhancement));
	}
}
