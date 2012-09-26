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

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class DieAdapter extends BaseAdapter implements PreferencesChangedNotifier {
	private DiceList dice;;
	private DiceList relevantDice = new DiceList();
	
	public void setDice(DiceList newDice) {
		dice = newDice;
		diceChanged();
	}
	
	@Override
	public int getCount() {
		return relevantDice.size();
	}
	
	@Override
	public DieData getItem(int position) {
		return relevantDice.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Die returnedView = null;
		DieData dieData = relevantDice.get(position);
		if (convertView != null) {
			returnedView = (Die)convertView;
			returnedView.setDieData(dieData);
		} else if (dieData instanceof FirstEdDieData) {
			//returnedView = new FirstEdDie(parent.getContext(), (FirstEdDieData)dieData, this);
		} else if (dieData instanceof SecondEdDieData) {
			//returnedView = new SecondEdDie(parent.getContext(), (SecondEdDieData)dieData, this);
		}
		
		return returnedView;
	}

	@Override
	public void diceChanged() {
		if (dice == null) return;
		relevantDice.clear();
		for (DieData die : dice)
			if (die.isVisible()) relevantDice.add(die);
		
		notifyDataSetChanged();
	}
	
	public boolean isDieSelectable(DieData die) {
		if (die instanceof FirstEdDieData) {
			if (((FirstEdDieData)die).isPowerDie() && relevantDice.selectedFirstEdPowerDiceCount() >= 5)
				return false; 
		}
		
		return true;
	}
}