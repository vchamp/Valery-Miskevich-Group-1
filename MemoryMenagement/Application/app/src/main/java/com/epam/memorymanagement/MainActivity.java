package com.epam.memorymanagement;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new ImageGridFragment()).commit();
		}
	}

}
