package com.dicent;

import com.dicent.dice.Die;
import com.dicent.dice.DieData;
import com.dicent.dice.firstEd.FirstEdDie;
import com.dicent.dice.firstEd.FirstEdDieData;
import com.dicent.dice.secondEd.SecondEdDie;
import com.dicent.dice.secondEd.SecondEdDieData;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

public abstract class DiceFragment extends Fragment implements PreferencesChangedNotifier {
	private DiceList diceList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DicentState.instance().registerPreferencesChangedNotifier(this);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		refreshDice();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		DicentState.instance().unregisterPreferencesChangedNotifier(this);
	}
	
	public View createDiceGrid(LayoutInflater inflater,
	                           ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dice_grid_table, null);
	}
	
	public void diceChanged() {
		refreshDice();
	}
	
	public void redraw() {
		TableLayout tl = (TableLayout)getView().findViewById(R.id.diceTable);
		for (int i = 0; i < tl.getChildCount(); i++) { //table rows
			TableRow row = (TableRow)tl.getChildAt(i);
			for (int j = 0; j < row.getChildCount(); j++) { //dice
				row.getChildAt(j).invalidate();
			}
		}
	}
	
	public void setDice(DiceList dice) {
		diceList = dice;
	}
	
	public DiceList getDice() {
		return diceList;
	}
	
	public void setVisibility(int visibility) {
		getView().setVisibility(visibility);
	}
	
	public boolean isDieSelectable(DieData die) {
		if (die instanceof FirstEdDieData) {
			if (((FirstEdDieData)die).isPowerDie() && diceList.selectedFirstEdPowerDiceCount() >= 5)
				return false; 
		}
		
		return true;
	}
	
	private void refreshDice() {
		if (diceList == null) return;
		DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
		int columns = metrics.widthPixels / (int)((Die.scale + Die.margin) * metrics.density);
		TableLayout tl = (TableLayout)getView().findViewById(R.id.diceTable);
		tl.removeAllViews();
		TableRow tr = null;
		int addedDice = 0;
		for (int i = 0; i < diceList.size(); i++) {
			if (!diceList.get(i).isVisible()) continue;
			if (addedDice % columns == 0) {
				tr = new TableRow(getActivity());
				tl.addView(tr);
			}
			Die die;
			if (diceList.get(i) instanceof FirstEdDieData)
				die = new FirstEdDie(getActivity(), (FirstEdDieData)diceList.get(i));
			else die = new SecondEdDie(getActivity(), (SecondEdDieData)diceList.get(i));
			die.setOnClickListener(new OnDieClickedListener(die));
			tr.addView(die);
			addedDice++;
		}
	}
	
	private class OnDieClickedListener implements View.OnClickListener {
		private Die die;
		
		public OnDieClickedListener(Die _die) {
			die = _die;
		}
		
		@Override
		public void onClick(View v) {
			DieData dieData = die.getDieData();
			if (dieData.isSelected) {
				dieData.isSelected = false;
			} else if (isDieSelectable(dieData)) {
				dieData.isSelected = true;
			}
			die.invalidate();
		}
	}
}
