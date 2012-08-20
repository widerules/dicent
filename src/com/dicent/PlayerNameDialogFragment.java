package com.dicent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.BaseAdapter;
import android.widget.EditText;

public class PlayerNameDialogFragment extends DialogFragment {
	private BaseAdapter playerAdapter;
	private int playerIndex = 0;
	private String playerName;
	
	private EditText changePlayerNameEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if (activity instanceof PlayerListActivity)
			playerAdapter = ((PlayerListActivity)activity).getPlayerAdapter();
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		playerName = changePlayerNameEditText.getText().toString();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		changePlayerNameEditText = new EditText(getActivity());
	    changePlayerNameEditText.setSingleLine();
	    if (playerName != null) {
	    	changePlayerNameEditText.setText(playerName);
	    	playerName = null;
	    }
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getResources().getString(R.string.changePlayerNameDialog));
    	builder.setView(changePlayerNameEditText);
    	builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			DicentState.instance().getPlayers()[playerIndex] = changePlayerNameEditText.getText().toString();
        		if (playerAdapter != null) playerAdapter.notifyDataSetChanged();
    		}
        });
    	builder.setNegativeButton(getResources().getString(R.string.cancel), null);
    	return builder.create();
	}
	
	//dirty hack to circumvent a bug in the compatibility package
	@Override
	public void onDestroyView() {
	  if (getDialog() != null && getRetainInstance())
	    getDialog().setOnDismissListener(null);
	  super.onDestroyView();
	}
	
	public void setPlayerIndex(int _playerIndex) {
		playerIndex = _playerIndex;
	}
	
	public void setPlayerName(String _playerName) {
		playerName = _playerName;
	}
}
