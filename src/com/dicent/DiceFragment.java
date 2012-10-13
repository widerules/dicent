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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class DiceFragment extends Fragment {
	public static final int ATTACK = 0;
	public static final int DEFENSE = 1;
	private int type = ATTACK;
	private DiceList diceList;
	
	private boolean enabled = true;
	private boolean showCheckbox = false;
	private String checkboxText = "";
	
	private CheckBox enabledCheckbox;
	private TableLayout diceTable;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		refreshDice();
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState) {
		LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.dice_fragment, null);
		enabledCheckbox = (CheckBox)layout.findViewById(R.id.diceEndabledCheckBox);
		enabledCheckbox.setOnCheckedChangeListener(new OnEnabledListener());
		enabledCheckbox.setText(checkboxText);
		diceTable = (TableLayout)layout.findViewById(R.id.diceTable);
		refreshCheckbox();
		return layout;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		
		enabledCheckbox = null;
		diceTable = null;
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
		if (isAdded()) refreshDice();
	}
	
	public DiceList getDice() {
		return diceList;
	}
	
	public void setVisibility(int visibility) {
		getView().setVisibility(visibility);
	}
	
	public void setShowCheckbox(boolean _showCheckbox) {
		showCheckbox = _showCheckbox;
		refreshCheckbox();
	}
	
	public void setCheckboxText(String text) {
		checkboxText = text;
		if (enabledCheckbox != null) enabledCheckbox.setText(checkboxText);
	}
	
	public void setEnabled(boolean _enabled) {
		enabled = _enabled;
		DicentState state = DicentState.instance();
		if (type == ATTACK) state.setAttackEnabled(enabled);
		else if (type == DEFENSE) state.setDefenseEnabled(enabled);
		refreshCheckbox();
	}
	
	public void setType(int _type) {
		type = _type;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean isDieSelectable(DieData die) {
		if (die instanceof FirstEdDieData) {
			if (((FirstEdDieData)die).isPowerDie() && diceList.selectedFirstEdPowerDiceCount() >= 5)
				return false; 
		}
		
		return true;
	}
	
	public void refreshDice() {
		if (diceTable == null || diceList == null) return;
		DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
		int columns = metrics.widthPixels / (int)((Die.scale + Die.margin) * metrics.density);
		diceTable.removeAllViews();
		TableRow tr = null;
		int addedDice = 0;
		for (int i = 0; i < diceList.size(); i++) {
			if (!diceList.get(i).isVisible()) continue;
			if (addedDice % columns == 0) {
				tr = new TableRow(getActivity());
				diceTable.addView(tr);
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
	
	private void refreshCheckbox() {
		if (enabledCheckbox == null || diceTable == null) return;
		if (showCheckbox) {
			enabledCheckbox.setVisibility(View.VISIBLE);
			enabledCheckbox.setChecked(enabled);
			if (enabled) diceTable.setVisibility(View.VISIBLE);
			else diceTable.setVisibility(View.INVISIBLE);
		} else {
			enabledCheckbox.setVisibility(View.GONE);
			diceTable.setVisibility(View.VISIBLE);
		}
	}
	
	private class OnEnabledListener implements CompoundButton.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			setEnabled(isChecked);
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
