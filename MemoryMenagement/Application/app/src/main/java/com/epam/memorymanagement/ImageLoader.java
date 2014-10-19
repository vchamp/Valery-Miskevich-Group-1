package com.epam.memorymanagement;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

/**
 * Created by ilya.shknaj on 18.10.14.
 */
public class ImageLoader {

    private static final int CACHE_SIZE = 5 * 1024 * 1024;

    private static ImageLoader instance;

    private LinkedList<ImageLoadTask> mRequests;

    private LruCache<String, Bitmap> mCache;

    private int mActiveTaskCount;

    public static ImageLoader getInstance() {

        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    private ImageLoader() {

        mCache = new LruCache<String, Bitmap>(CACHE_SIZE) {

            @Override
            protected int sizeOf(String key, Bitmap bitmap) {

                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
        mRequests = new LinkedList<ImageLoadTask>();
    }

    public void loadImage(final String assetName, ImageView imageView) {

        Bitmap bitmap = getFromCache(assetName);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            mRequests.add(new ImageLoadTask(assetName, imageView));
        }
        flushRequests();

    }

    private Bitmap getFromCache(String name) {

        return mCache.get(name);
    }

    private void putToCache(String name, Bitmap bitmap) {

        if (mCache.get(name) == null) {
            mCache.put(name, bitmap);
        }
    }

    private void flushRequests() {

        if (!mRequests.isEmpty()) {
            mRequests.poll().execute();
            incrementTaskCount();
        }
    }

    private synchronized void incrementTaskCount() {

        mActiveTaskCount++;
    }

    private synchronized void decrementTaskCount() {

        mActiveTaskCount--;
    }


    public static class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private ImageView imageView;

        private String imageName;

        public ImageLoadTask(String imageName, ImageView imageView) {

            this.imageView = imageView;
            this.imageName = imageName;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            imageView.setImageResource(R.drawable.empty_photo);
            imageView.setTag(imageName);
        }

        @Override
        protected Bitmap doInBackground(Void... args) {

            AssetManager assetManager = ImagesApplication.CONTEXT.getAssets();


            InputStream inputStream = null;
            try {
                inputStream = assetManager.open(imageName);
            } catch (IOException e) {
                Log.d("BitmapProvider", "error loading image " + imageName);
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            System.gc();

            options.inSampleSize = 3;

            options.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.gc();
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {

            super.onPostExecute(bitmap);
            if (imageView.getTag().equals(imageName)) {
                imageView.setImageBitmap(bitmap);
                ImageLoader.getInstance().putToCache(imageName, bitmap);
            }

            imageView = null;
            imageName = null;

            ImageLoader.getInstance().decrementTaskCount();
            ImageLoader.getInstance().flushRequests();
        }

    }

}
