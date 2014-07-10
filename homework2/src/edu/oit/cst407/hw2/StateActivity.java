package edu.oit.cst407.hw2;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class StateActivity extends Activity {

	String [] m_planetsArray;
	TextView m_textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_state);
		
		m_planetsArray =   getResources().getStringArray(R.array.planets_array); 
		updateTextView();
	}
	
	
	@Override
    protected void onResume() {
        super.onResume();
    }

    private void updateTextView() {
        TextView m_textView = (TextView)findViewById(R.id.randomTextView); 
        Random random = new Random();

        int maxIndex = m_planetsArray.length;
        int generatedIndex = random.nextInt(maxIndex);

        m_textView.setText(m_planetsArray[generatedIndex]);   
    }
}
