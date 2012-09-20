package com.dicent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class SimpleDiceFragment extends DiceFragment {
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState) {
		GridView diceGrid = (GridView)createDiceGrid(inflater, container, savedInstanceState);
		diceGrid.setAdapter(dieAdapter);
		return diceGrid;
	}
}
