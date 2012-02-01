package com.dicent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectDice extends DicentActivity {
	public static final int BASE_DICE_COUNT = 12;
	public static final int RTL_DICE_COUNT = 10;
	public static final String SAVED_BASE_DICE_SHARED_PREFERENCE = "savedBaseDice";
	public static final String SAVED_RTL_DICE_SHARED_PREFERENCE = "savedRTLDice";
	public static final String SAVED_TRANSPARENT_DIE_SHARED_PREFERENCE = "savedTransparentDie";
	
	private DieAdapter dieAdapter;
	
	private int playerIndex;
	private String playerIndexString;
	private boolean isOverlord;
	
	private Intent resultsIntent;
	
	private Vibrator vibrator;
	private MediaPlayer rollSound;
	
	SharedPreferences preferences;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_dice);
		
		//collect objects created in XML
		GridView diceGrid = (GridView)findViewById(R.id.selectDiceGrid);
    	Button rollButton = (Button)findViewById(R.id.selectDiceRollButton);
    	
    	//create stuff
    	dieAdapter = new DieAdapter(this);
    	playerIndex = getIntent().getIntExtra("playerIndex", 0);
    	playerIndexString = Integer.toString(playerIndex);
    	isOverlord = getIntent().getBooleanExtra("isOverlord", false);
    	vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    	rollSound = MediaPlayer.create(this, R.raw.rollsound);
    	resultsIntent = new Intent(getBaseContext(), Results.class);
    	preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	
    	//set listeners
    	rollButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			if (preferences.getBoolean("vibration", true)) vibrator.vibrate(150);
    			if (preferences.getBoolean("sounds", true)) rollSound.start();
    			resultsIntent.removeExtra("dice");
    			resultsIntent.putExtra("dice", dieAdapter.selectedDieTypes());
    			startActivity(resultsIntent);
        	}
    	});
    	
    	diceGrid.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    			if (dieAdapter.isPowerDie(position) &&
    					dieAdapter.selectedPowerDiceCount() >= 5 &&
    					!dieAdapter.isSelected(position))
    				return;
    			
    			dieAdapter.toggleSelected(position);
    		}
		});
		
    	diceGrid.setAdapter(dieAdapter);
	}
	
	protected void onStart() {
		super.onStart();
		
		dieAdapter.clear();
		//base game
    	dieAdapter.addDie(DieData.RED_DIE);
    	dieAdapter.addDie(DieData.BLUE_DIE);
    	dieAdapter.addDie(DieData.WHITE_DIE);
    	dieAdapter.addDie(DieData.GREEN_DIE);
    	dieAdapter.addDie(DieData.GREEN_DIE);
    	dieAdapter.addDie(DieData.YELLOW_DIE);
    	dieAdapter.addDie(DieData.YELLOW_DIE);
    	dieAdapter.addDie(DieData.BLACK_DIE);
    	dieAdapter.addDie(DieData.BLACK_DIE);
    	dieAdapter.addDie(DieData.BLACK_DIE);
    	dieAdapter.addDie(DieData.BLACK_DIE);
    	dieAdapter.addDie(DieData.BLACK_DIE);
    	
	    //restore saved base dice
		SharedPreferences savedBaseSelection = getSharedPreferences(SAVED_BASE_DICE_SHARED_PREFERENCE + playerIndexString, Context.MODE_PRIVATE);
		int selectedBaseDiceCount = savedBaseSelection.getInt("count", 0);
		for (int i = 0; i < selectedBaseDiceCount; i++)
			dieAdapter.setSelected(savedBaseSelection.getInt(Integer.toString(i), 0), true);
		
		if (preferences.getBoolean("roadToLegend", false)) {
			//road to legend
	    	dieAdapter.addDie(DieData.SILVER_DIE);
	    	dieAdapter.addDie(DieData.SILVER_DIE);
	    	dieAdapter.addDie(DieData.SILVER_DIE);
	    	dieAdapter.addDie(DieData.SILVER_DIE);
	    	dieAdapter.addDie(DieData.SILVER_DIE);
	    	dieAdapter.addDie(DieData.GOLD_DIE);
	    	dieAdapter.addDie(DieData.GOLD_DIE);
	    	dieAdapter.addDie(DieData.GOLD_DIE);
	    	dieAdapter.addDie(DieData.GOLD_DIE);
	    	dieAdapter.addDie(DieData.GOLD_DIE);
	    	

		   	//restore saved RTL dice
		   	int rtlStartIndex = -1;
			int dieAdapterCount = dieAdapter.getCount();
			for (int i = 0; i < dieAdapterCount; i++) {
				if (dieAdapter.getItemViewType(i) == DieData.SILVER_DIE || dieAdapter.getItemViewType(i) == DieData.GOLD_DIE) {
					rtlStartIndex = i;
					i = dieAdapterCount;
				}
			}
			
			SharedPreferences savedRTLSelection = getSharedPreferences(SAVED_RTL_DICE_SHARED_PREFERENCE + playerIndexString, Context.MODE_PRIVATE);
			int selectedRTLDiceCount = savedRTLSelection.getInt("count", 0);
			for (int i = 0; i < selectedRTLDiceCount; i++)
				dieAdapter.setSelected(savedRTLSelection.getInt(Integer.toString(i), 0) + rtlStartIndex, true);
		} 
		
		if (preferences.getBoolean("tombOfIce", false)) {
			//tomb of ice
	    	dieAdapter.addDie(DieData.TRANSPARENT_DIE);
	    	
	    	//restore transparent die
		    SharedPreferences savedTransparentDieSelection = getSharedPreferences(SAVED_TRANSPARENT_DIE_SHARED_PREFERENCE + playerIndexString, Context.MODE_PRIVATE);
		    if (savedTransparentDieSelection.getBoolean("transparentDie", false)) {
		    	int dieAdapterCount = dieAdapter.getCount();
		    	for (int i = 0; i < dieAdapterCount; i++) {
		    		if (dieAdapter.getItemViewType(i) == DieData.TRANSPARENT_DIE) {
		    			dieAdapter.setSelected(i, true);
		    			i = dieAdapterCount;
		    		}
		    	}
		    }
		}
	}
	
	protected void onStop() {
		super.onStop();
		
		//save selected dice
		//base dice
		SharedPreferences savedBaseSelection = getSharedPreferences(SAVED_BASE_DICE_SHARED_PREFERENCE + playerIndexString, Context.MODE_PRIVATE);
		SharedPreferences.Editor savedBaseSelectionEditor = savedBaseSelection.edit();
		savedBaseSelectionEditor.clear();
		
		int[] selectedBaseDice = dieAdapter.selectedBaseDice();
		int selectedBaseDiceCount = selectedBaseDice.length;
		savedBaseSelectionEditor.putInt("count", selectedBaseDiceCount);
		
		for (int i = 0; i < selectedBaseDiceCount; i++)
			savedBaseSelectionEditor.putInt(Integer.toString(i), selectedBaseDice[i]);
		savedBaseSelectionEditor.commit();
		
		//road to legend dice
		if (preferences.getBoolean("roadToLegend", false)) {
			SharedPreferences savedRTLSelection = getSharedPreferences(SAVED_RTL_DICE_SHARED_PREFERENCE + playerIndexString, Context.MODE_PRIVATE);
			SharedPreferences.Editor savedRTLSelectionEditor = savedRTLSelection.edit();
			savedRTLSelectionEditor.clear();
			
			int[] selectedRTLDice = dieAdapter.selectedRTLDice();
			int selectedRTLDiceCount = selectedRTLDice.length;
			savedRTLSelectionEditor.putInt("count", selectedRTLDiceCount);
			
			for (int i = 0; i < selectedRTLDiceCount; i++)
				savedRTLSelectionEditor.putInt(Integer.toString(i), selectedRTLDice[i]);
			savedRTLSelectionEditor.commit();
		}
		
		//tomb of ice dice
		if (preferences.getBoolean("tombOfIce", false)) {
			SharedPreferences savedTransparentDieSelection = getSharedPreferences(SAVED_TRANSPARENT_DIE_SHARED_PREFERENCE + playerIndexString, Context.MODE_PRIVATE);
			SharedPreferences.Editor savedTransparentDieSelectionEditor = savedTransparentDieSelection.edit();
			savedTransparentDieSelectionEditor.clear();
			
			savedTransparentDieSelectionEditor.putBoolean("transparentDie", dieAdapter.transparentDieIsSelected());
			savedTransparentDieSelectionEditor.commit();
		}
	}
	
	protected void onDestroy() {
		super.onDestroy();
		
		if (!isOverlord || !isFinishing()) return;
		
		SharedPreferences savedBaseSelection = getSharedPreferences(SAVED_BASE_DICE_SHARED_PREFERENCE + playerIndexString, Context.MODE_PRIVATE);
		SharedPreferences.Editor savedBaseSelectionEditor = savedBaseSelection.edit();
		savedBaseSelectionEditor.clear();
		savedBaseSelectionEditor.commit();
		
		SharedPreferences savedRTLSelection = getSharedPreferences(SAVED_RTL_DICE_SHARED_PREFERENCE + playerIndexString, Context.MODE_PRIVATE);
		SharedPreferences.Editor savedRTLSelectionEditor = savedRTLSelection.edit();
		savedRTLSelectionEditor.clear();
		savedRTLSelectionEditor.commit();
		
		SharedPreferences savedTransparentDieSelection = getSharedPreferences(SAVED_TRANSPARENT_DIE_SHARED_PREFERENCE + playerIndexString, Context.MODE_PRIVATE);
		SharedPreferences.Editor savedTransparentDieSelectionEditor = savedTransparentDieSelection.edit();
		savedTransparentDieSelectionEditor.clear();
		savedTransparentDieSelectionEditor.commit();
	}
}
