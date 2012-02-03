package com.dicent;

import com.dicent.dice.Die;
import com.dicent.dice.DieData;
import com.dicent.dice.SideValues;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Results extends DicentActivity {
	private DieAdapter dieAdapter;
	
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
	
	SharedPreferences preferences;
	
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
		dieAdapter = new DieAdapter(this);
		rerollToast = Toast.makeText(this, getResources().getString(R.string.rerollNotification), Toast.LENGTH_SHORT);
		preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		float density = getResources().getDisplayMetrics().density;
    	diceGrid.setColumnWidth((int)(density * Die.scale));
		
		//set listeners
		diceGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dieAdapter.toggleSelected(position);
			}
		});
		
		rerollButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			if (dieAdapter.selectedDiceCount() > 0) {
    				rollEffects();
    			}
    			else rerollToast.show();
    			int resultsDieAdapterCount = dieAdapter.getCount();
    			for (int i = 0; i < resultsDieAdapterCount; i++) {
    				if (dieAdapter.getItem(i).isSelected) {
    					dieAdapter.rollDie(i);
    				}
    			}
    			
    			updateResults();
        	}
    	});
		
		addBlackButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			if (dieAdapter.powerDiceCount() >= 5) return;
    			dieAdapter.addRolledDie(DieData.BLACK_DIE);
    			
    			rollEffects();
    			updateResults();
        	}
    	});
    	
    	addSilverButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			if (dieAdapter.powerDiceCount() >= 5) return;
    			dieAdapter.addRolledDie(DieData.SILVER_DIE);
    			
    			rollEffects();
    			updateResults();
        	}
    	});
    	
    	addGoldButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			if (dieAdapter.powerDiceCount() >= 5) return;
    			dieAdapter.addRolledDie(DieData.GOLD_DIE);
    			
    			rollEffects();
    			updateResults();
        	}
    	});
		
    	//save state
    	if (savedInstanceState == null) {
    		//use dice from the intent
    		int[] sides = getIntent().getIntArrayExtra("dice");
    		if (sides != null) {
    			for (int dieType : sides) dieAdapter.addRolledDie(dieType);
    		}
    	} else {
    		//use dice from the saved state
    		int[] dieTypes = savedInstanceState.getIntArray("dieTypes");
        	for (int dieType : dieTypes) {
    			dieAdapter.addDie(dieType);
    		}
        	
        	dieAdapter.setSides(savedInstanceState.getIntArray("sides"));
        	
        	int[] selection = savedInstanceState.getIntArray("selectedDice");
        	for (int selectedIndex : selection) {
        		dieAdapter.setSelected(selectedIndex, true);
        	}
    	}
		
		diceGrid.setAdapter(dieAdapter);
		
		updateResults();
	}
	
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
	
	protected void onSaveInstanceState (Bundle outState) {
		outState.putIntArray("dieTypes", dieAdapter.dieTypes());
    	outState.putIntArray("sides", dieAdapter.sides());
    	outState.putIntArray("selectedDice", dieAdapter.selectedIndices());
	}
	
	private void updateResults() {
		
    	wounds = surges = range = enhancement = 0;
    	fail = false;
    	
    	int dieAdapterCount = dieAdapter.getCount();
    	for (int i = 0; i < dieAdapterCount; i++) {
    		SideValues currentSideValues = dieAdapter.getSideValues(i);
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
