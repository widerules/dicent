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

import java.util.Random;
import java.util.Vector;

import com.dicent.dice.Die;
import com.dicent.dice.DieData;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
public class DieAdapter extends BaseAdapter {
	protected static Random generator;
	
	static {
		generator = new Random();
	}
	
	private Context context;
	private Vector<DieData> dice;
	
	public DieAdapter(Context _context) {
		context = _context;
		dice = new Vector<DieData>();
	}

	public int getCount() {
		return dice.size();
	}

	public DieData getItem(int position) {
		return dice.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public Die getView(int position, View convertView, ViewGroup parent) {
		Die returnedView;
		if (convertView != null) {
			returnedView = (Die)convertView;
			DieData dieData = dice.get(position);
			returnedView.setSide(dieData.side);
			returnedView.setDieSelected(dieData.isSelected);
		} else {
			returnedView = dice.get(position).createDie(context);
		}
		
		return returnedView;
	}
	
	public int getItemViewType(int position) {
		return dice.get(position).dieType;
	}
	
	public int getViewTypeCount() {
		return DieData.DIE_TYPES_COUNT;
	}
	
	public void addDie(int dieType) {
		DieData newDie = new DieData(dieType);
		dice.add(newDie);
		notifyDataSetChanged();
	}
	
	public void addRolledDie(int dieType) {
		DieData newDie = new DieData(dieType);
		newDie.side = randomSide();
		dice.add(newDie);
		notifyDataSetChanged();
	}
	
	public void clear() {
		dice.clear();
		notifyDataSetChanged();
	}
	
	public void setSelected(int position, boolean selected) {
		dice.get(position).isSelected = selected;
		notifyDataSetChanged();
	}
	
	public boolean isSelected(int position) {
		return dice.get(position).isSelected;
	}
	
	public void toggleSelected(int position) {
		DieData dieData = dice.get(position);
		dieData.isSelected = !dieData.isSelected;
		notifyDataSetChanged();
	}
	
	public void setSide(int position, int side) {
		dice.get(position).side = side;
		notifyDataSetChanged();
	}
	
	public int[] dieTypes() {
		int diceSize = dice.size();
		int[] dieTypes = new int[diceSize];
		for (int i = 0; i < diceSize; i++)
			dieTypes[i] = dice.get(i).dieType;
		return dieTypes;
	}
	
	public void rollDie(int position) {
		int randomSide = randomSide();
		dice.get(position).side = randomSide;
		dice.get(position).isSelected = false;
		notifyDataSetChanged();
	}
	
	public int randomSide() {
		int randomSide = generator.nextInt(Integer.MAX_VALUE);
		return randomSide % 6;
	}
	
	public int[] selectedIndices() {
		Vector<Integer> selectedDiceIndices = new Vector<Integer>();
		int diceSize = dice.size();
		for (int i = 0; i < diceSize; i++) {
			if (dice.get(i).isSelected) selectedDiceIndices.add(i);
		}
		int selectedDiceIndicesSize = selectedDiceIndices.size();
		int[] selectedIndices = new int[selectedDiceIndicesSize];
		for (int i = 0; i < selectedDiceIndicesSize; i++) {
			selectedIndices[i] = selectedDiceIndices.get(i);
		}
		return selectedIndices;
	}
	
	public int[] sides() {
		int diceCount = dice.size();
		int[] sides = new int[diceCount];
		for (int i = 0; i < diceCount; i++) {
			sides[i] = dice.get(i).side;
		}
		return sides;
	}
	
	public int[] selectedDieTypes() {
		Vector<Integer> selectedDiceVector = new Vector<Integer>();
		int diceCount = dice.size();
		for (int i = 0; i < diceCount; i++) {
			if (dice.get(i).isSelected) selectedDiceVector.add(dice.get(i).dieType);
		}
		
		int selectedDiceSize = selectedDiceVector.size();
		int[] selectedDice = new int[selectedDiceSize];
		for (int i = 0; i < selectedDiceSize; i++) selectedDice[i] = selectedDiceVector.get(i);
		return selectedDice;
	}
	
	public int[] selectedBaseDice() {
		Vector<Integer> selectedDiceVector = new Vector<Integer>();
		int diceCount = dice.size();
		for (int i = 0; i < diceCount; i++) {
			if (dice.get(i).isSelected && (dice.get(i).dieType < DieData.SILVER_DIE)) selectedDiceVector.add(i); //dirty hack!
		}
		
		int selectedDiceSize = selectedDiceVector.size();
		int[] selectedDice = new int[selectedDiceSize];
		for (int i = 0; i < selectedDiceSize; i++) selectedDice[i] = selectedDiceVector.get(i);
		return selectedDice;
	}
	
	public int[] selectedRTLDice() {
		int rtlStartIndex = -1;
		Vector<Integer> selectedDiceVector = new Vector<Integer>();
		int diceCount = dice.size();
		for (int i = 0; i < diceCount; i++) {
			if (dice.get(i).dieType == DieData.SILVER_DIE || dice.get(i).dieType == DieData.GOLD_DIE) {
				if (rtlStartIndex < 0) rtlStartIndex = i;
				
				if (dice.get(i).isSelected) selectedDiceVector.add(i - rtlStartIndex);
			}
		}
		
		int selectedDiceSize = selectedDiceVector.size();
		int[] selectedDice = new int[selectedDiceSize];
		for (int i = 0; i < selectedDiceSize; i++) selectedDice[i] = selectedDiceVector.get(i);
		return selectedDice;
	}
	
	public boolean transparentDieIsSelected() {
		for (DieData die : dice) {
			if (die.dieType == DieData.TRANSPARENT_DIE) {
				if (die.isSelected) return true;
				else return false;
			}
		}
		return false;
	}
	
	public int powerDiceCount() {
		int powerDiceCount = 0;
		int diceSize = dice.size();
		for (int i = 0; i < diceSize; i++) {
			if (isPowerDie(i)) powerDiceCount++; 
		}
		return powerDiceCount;
	}
	
	public int selectedDiceCount() {
		int selectedDice = 0;
		int diceSize = dice.size();
		for (int i = 0; i < diceSize; i++) {
			if (dice.get(i).isSelected) selectedDice++;
		}
		return selectedDice;
	}
	
	public int selectedPowerDiceCount() {
		int selectedPowerDiceCount = 0;
		int diceSize = dice.size();
		for (int i = 0; i < diceSize; i++) {
			if (isPowerDie(i) && dice.get(i).isSelected) selectedPowerDiceCount++; 
		}
		return selectedPowerDiceCount;
	}
	
	public boolean isPowerDie(int position) {
		boolean isPowerDie = false;
		int i = 0;
		while(!isPowerDie && i < DieData.POWER_DICE_TYPES_COUNT) {
			if (dice.get(position).dieType == DieData.POWER_DICE[i]) isPowerDie = true;
			i++;
		}
		return isPowerDie;
	}
}