package com.dicent;

import com.dicent.dice.DieData;
import com.dicent.dice.SideValues;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class StatsFragment extends Fragment {
	private ViewGroup surgesGroup;
	private ViewGroup rangeGroup;
	private ViewGroup enhancementGroup;
	
	private TextView statsMinus;
	private TextView statsEquals;
	private ImageView shieldsLabel;
	
	private TextView woundsText;
	private TextView shieldsText;
	private TextView effectiveWoundsText;
	private TextView surgesText;
	private TextView rangeText;
	private TextView enhancementText;
	
	private int mode = DicentActivity.MODE_FIRSTED;
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.result_stats, null);
		
		surgesGroup = (ViewGroup)view.findViewById(R.id.statsSurgesGroup);
		rangeGroup = (ViewGroup)view.findViewById(R.id.statsRangeGroup);
		enhancementGroup = (ViewGroup)view.findViewById(R.id.statsEnhancementGroup);
		
		statsMinus = (TextView)view.findViewById(R.id.statsMinus);
		statsEquals = (TextView)view.findViewById(R.id.statsEquals);
		shieldsLabel = (ImageView)view.findViewById(R.id.shieldsLabel);
		
		woundsText = (TextView)view.findViewById(R.id.resultWounds);
		effectiveWoundsText = (TextView)view.findViewById(R.id.statsEffectiveWounds);
		shieldsText = (TextView)view.findViewById(R.id.resultShields);
		surgesText = (TextView)view.findViewById(R.id.resultSurges);
		rangeText = (TextView)view.findViewById(R.id.resultRange);
		enhancementText = (TextView)view.findViewById(R.id.resultEnhancement);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if (mode != DicentActivity.MODE_FIRSTED) enhancementGroup.setVisibility(View.GONE);
		
		if (mode == DicentActivity.MODE_FIRSTED || mode == DicentActivity.MODE_SECONDED_ATTACK) {
			shieldsText.setVisibility(View.GONE);
			shieldsLabel.setVisibility(View.GONE);
		}
		
		if (mode == DicentActivity.MODE_SECONDED_DEFENSE) {
			woundsText.setVisibility(View.GONE);
			surgesGroup.setVisibility(View.GONE);
			rangeGroup.setVisibility(View.GONE);
		}
		
		if (mode != DicentActivity.MODE_EXPERIMENTAL) {
			statsMinus.setVisibility(View.GONE);
			statsEquals.setVisibility(View.GONE);
			effectiveWoundsText.setVisibility(View.GONE);
		}
		
		update();
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		
		surgesGroup = rangeGroup = enhancementGroup = null;
		statsMinus = statsEquals = null;
		shieldsLabel = null;
		woundsText = shieldsText = effectiveWoundsText = surgesText = rangeText = enhancementText = null;
	}
	
	public void setMode(int _mode) {
		mode = _mode;
	}
	
	public void update() {
		int wounds = 0, shields = 0, effectiveWounds = 0, surges = 0, range = 0, enhancement = 0;
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
		
		effectiveWounds = wounds - shields;
		if (effectiveWounds < 0) effectiveWounds = 0;
		
		if (fail) wounds = surges = shields = range = enhancement = effectiveWounds = 0;
		woundsText.setText(Integer.toString(wounds));
		surgesText.setText(Integer.toString(surges));
		shieldsText.setText(Integer.toString(shields));
		rangeText.setText(Integer.toString(range));
		enhancementText.setText(Integer.toString(enhancement));
		effectiveWoundsText.setText(Integer.toString(effectiveWounds));
	}
}
