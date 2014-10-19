package com.epam.memorymanagement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.SparseArray;

public class BitmapProvider {

    private static BitmapProvider instance;

    private int selectedImageIndex = 0;

    private List<BitmapProviderListener> listeners = new ArrayList<BitmapProviderListener>();

    private BitmapProvider() {

        super();
    }

    public static BitmapProvider getInstance() {

        if (instance == null) {
            instance = new BitmapProvider();
        }
        return instance;
    }

    public void addListener(BitmapProviderListener listener) {

        if (!listeners.contains(listener)) {
            listeners.add(listener);
            Log.i(ImagesApplication.TAG, "added listener, " + listeners.size());
        }
    }

    public int getCount() {

        return 48;
    }

    public int getSelectedImageIndex() {

        return selectedImageIndex;
    }

    public void setSelectedImage(int index) {

        selectedImageIndex = index;
        for (BitmapProviderListener listener : listeners) {
            listener.onSelectedImageChanged();
        }
    }

}
