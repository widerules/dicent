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
import com.dicent.dice.SideValues;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ResultsActivity extends DicentActivity {
	private DieAdapter dieAdapter = new DieAdapter();

	private int wounds;
	private int surges;
	private int range;
	private int enhancement;
	private boolean fail;

	private TextView woundsText;
	private TextView surgesText;
	private TextView rangeText;
	private TextView enhancementText;

	private Button addSilverButton;
	private Button addGoldButton;

	private Toast rerollToast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);

		//collect objects created in XML
		GridView diceGrid = (GridView)findViewById(R.id.resultsDiceGrid);
		woundsText = (TextView)findViewById(R.id.resultWounds);
		surgesText = (TextView)findViewById(R.id.resultSurges);
		rangeText = (TextView)findViewById(R.id.resultRange);
		enhancementText = (TextView)findViewById(R.id.resultEnhancement);
		Button rerollButton = (Button)findViewById(R.id.resultsRerollButton);
		Button addBlackButton = (Button)findViewById(R.id.resultsAddBlackButton);
		addSilverButton = (Button)findViewById(R.id.resultsAddSilverButton);
		addGoldButton = (Button)findViewById(R.id.resultsAddGoldButton);

		//create stuff
		rerollToast = Toast.makeText(this, getResources().getString(R.string.rerollNotification), Toast.LENGTH_SHORT);

		float density = getResources().getDisplayMetrics().density;
		diceGrid.setColumnWidth((int)(density * Die.scale));

		//set listeners
		diceGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				DieData die = state.getResultDice().get(position);
				die.isSelected = !die.isSelected;
				dieAdapter.notifyDataSetChanged();
			}
		});

		rerollButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (state.getResultDice().selectedDiceCount() > 0) {
					rollEffects();
				}
				else rerollToast.show();
				for (DieData data : state.getResultDice()) {
					if (data.isSelected) {
						data.isSelected = false;
						data.roll();
					}
				}
				dieAdapter.notifyDataSetChanged();
				updateResults();
			}
		});

		addBlackButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (state.getResultDice().powerDiceCount() >= 5) return;
				addRolledDie(DieData.BLACK_DIE);

				rollEffects();
				updateResults();
			}
		});

		addSilverButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (state.getResultDice().powerDiceCount() >= 5) return;
				addRolledDie(DieData.SILVER_DIE);

				rollEffects();
				updateResults();
			}
		});

		addGoldButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (state.getResultDice().powerDiceCount() >= 5) return;
				addRolledDie(DieData.GOLD_DIE);

				rollEffects();
				updateResults();
			}
		});

		dieAdapter.setDice(state.getResultDice());
		state.registerPreferencesChangedNotifier(dieAdapter);
		diceGrid.setAdapter(dieAdapter);

		updateResults();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if (preferences.getBoolean("roadToLegend", false)) {
			addSilverButton.setVisibility(View.VISIBLE);
			addGoldButton.setVisibility(View.VISIBLE);
		} else {
			addSilverButton.setVisibility(View.GONE);
			addGoldButton.setVisibility(View.GONE);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		state.unregisterPreferencesChangedNotifier(dieAdapter);
	}
	
	private void addRolledDie(int dieType) {
		DieData newDie = DieData.create(dieType);
		newDie.roll();
		state.getResultDice().add(newDie);
		dieAdapter.notifyDataSetChanged();
	}

	private void updateResults() {

		wounds = surges = range = enhancement = 0;
		fail = false;
		
		for (DieData die : state.getResultDice()) {
			SideValues currentSideValues = die.getSideValues();
			if (currentSideValues.fail) fail = true;
			else {
				wounds += currentSideValues.wounds;
				surges += currentSideValues.surges;
				range += currentSideValues.range;
				enhancement += currentSideValues.enhancement;
			}
		}
		if (fail) wounds = surges = range = enhancement = 0;
		woundsText.setText(Integer.toString(wounds));
		surgesText.setText(Integer.toString(surges));
		rangeText.setText(Integer.toString(range));
		enhancementText.setText(Integer.toString(enhancement));
	}

	private void rollEffects() {
		if (preferences.getBoolean("vibration", true)) vibrator.vibrate(150);
		if (preferences.getBoolean("sounds", true)) rollSound.start();
	}
}