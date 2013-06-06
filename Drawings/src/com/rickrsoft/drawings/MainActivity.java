package com.rickrsoft.drawings;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.rickrsoft.drawings.views.PieChart;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Resources res = getResources();

//        final PieChart pie = (PieChart) this.findViewById(R.id.Pie);
//        pie.addItem("Agamemnon", 2, res.getColor(R.color.seafoam));
//        pie.addItem("Bocephus", 3.5f, res.getColor(R.color.chartreuse));
//        pie.addItem("Calliope", 2.5f, res.getColor(R.color.emerald));
//        pie.addItem("Daedalus", 3, res.getColor(R.color.bluegrass));
//        pie.addItem("Euripides", 1, res.getColor(R.color.turquoise));
//        pie.addItem("Ganymede", 3, res.getColor(R.color.slate));
//
//        ((Button) findViewById(R.id.Reset)).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                pie.setCurrentItem(0);
//            }
//        });
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
