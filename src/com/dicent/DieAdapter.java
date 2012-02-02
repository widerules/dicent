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

import com.dicent.dice.DieData;
import com.dicent.dice.Die;
import com.dicent.dice.DieData.Side;
import com.dicent.dice.SideValues;

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

	public View getView(int position, View convertView, ViewGroup parent) {
		Die returnedView;
		DieData dieData = dice.get(position);
		if (convertView instanceof Die) {
			returnedView = (Die)convertView;
			returnedView.setDieData(dieData);
		} else {
			returnedView = new Die(context, dieData);
		}
		
		return returnedView;
	}
	
	public SideValues getSideValues(int position) {
		return dice.get(position).getSideValues();
	}
	
	public int getItemViewType(int position) {
		return dice.get(position).dieType;
	}
	
	public int getViewTypeCount() {
		return DieData.DIE_TYPES_COUNT;
	}
	
	
	public void addDie(int dieType) {
		DieData newDie = DieData.create(dieType);
		dice.add(newDie);
		notifyDataSetChanged();
	}
	
	public void addRolledDie(int dieType) {
		DieData newDie = DieData.create(dieType);
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
	
	public void setSides(int[] sides) {
		for (int i = 0; i < dice.size(); i++) 
			dice.get(i).side = Side.values()[sides[i]];
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
		dice.get(position).side = randomSide();
		dice.get(position).isSelected = false;
		notifyDataSetChanged();
	}
	
	public Side randomSide() {
		int randomSide = generator.nextInt(Integer.MAX_VALUE);
		return Side.values()[randomSide % 6];
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
			sides[i] = dice.get(i).side.ordinal();
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
		for (DieData die : dice)
			if (die.powerDie) powerDiceCount++;
		return powerDiceCount;
	}
	
	public int selectedDiceCount() {
		int selectedDiceCount = 0;
		for (DieData die : dice)
			if (die.isSelected) selectedDiceCount++;
		return selectedDiceCount;
	}
	
	public int selectedPowerDiceCount() {
		int selectedPowerDiceCount = 0;
		for (DieData die : dice)
			if (die.isSelected && die.powerDie) selectedPowerDiceCount++;
		return selectedPowerDiceCount;
	}
	
	public boolean isPowerDie(int position) {
		return dice.get(position).powerDie;
	}
}