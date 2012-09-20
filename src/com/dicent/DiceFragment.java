package com.dicent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class DiceFragment extends Fragment {
	protected DieAdapter dieAdapter = new DieAdapter();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DicentState.instance().registerPreferencesChangedNotifier(dieAdapter);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		DicentState.instance().unregisterPreferencesChangedNotifier(dieAdapter);
	}
	
	public View createDiceGrid(LayoutInflater inflater,
	                           ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dice_grid, null);
	}
	
	public void notifyDataSetChanged() {
		dieAdapter.notifyDataSetChanged();
	}
	
	public void diceChanged() {
		dieAdapter.diceChanged();
	}
	
	public void setDice(DiceList dice) {
		dieAdapter.setDice(dice);
	}
}
