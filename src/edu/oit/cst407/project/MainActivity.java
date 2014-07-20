package edu.oit.cst407.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		MenuInflater mif = getMenuInflater();
		mif.inflate(R.menu.activity_main_action, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
