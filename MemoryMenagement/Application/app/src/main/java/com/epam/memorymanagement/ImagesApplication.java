package com.epam.memorymanagement;

import android.app.Application;
import android.content.Context;

public class ImagesApplication extends Application {

	public static final String TAG = "ImagesApp";
	
	public static Context CONTEXT;

	public ImagesApplication() {
		super();

		CONTEXT = this;
	}
}
