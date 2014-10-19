package com.epam.memorymanagement;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageGridFragment extends Fragment implements AdapterView.OnItemClickListener, BitmapProviderListener {

    private ImageAdapter imageAdapter;

    private BitmapProvider bitmapProvider;

    private ImageLoader imageLoader;

    private final int imageHeightPx = 600;
    private int imageHeightDp;

    public ImageGridFragment() {

        super();
        bitmapProvider = BitmapProvider.getInstance();
        imageLoader = ImageLoader.getInstance();
        bitmapProvider.addListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.i(ImagesApplication.TAG, "ImageGridFragment create");
        super.onCreate(savedInstanceState);

        imageHeightDp = (int) (imageHeightPx / getResources().getDisplayMetrics().density);

        imageAdapter = new ImageAdapter(getActivity());
    }

    @Override
    public void onDestroy() {

        Log.i(ImagesApplication.TAG, "ImageGridFragment destroy");
        super.onDestroy();
    }

    @Override
    protected void finalize() throws Throwable {

        Log.i(ImagesApplication.TAG, "ImageGridFragment finalize");
        super.finalize();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

        bitmapProvider.setSelectedImage(position);
        final Intent i = new Intent(getActivity(), ImagePagerActivity.class);
        startActivity(i);
    }

    @Override
    public void onSelectedImageChanged() {

        imageAdapter.notifyDataSetChanged();
    }

    private class ImageAdapter extends BaseAdapter {

        private final Context mContext;

        public ImageAdapter(Context context) {

            super();
            mContext = context;
        }

        @Override
        public int getCount() {

            return bitmapProvider.getCount();
        }

        @Override
        public String getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {

            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setPadding(8, 8, 8, 8);
                imageView.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT, imageHeightDp));
            } else {
                imageView = (ImageView) convertView;
            }
            imageLoader.loadImage(ImageUtil.getAssetName(position), imageView);

            int color = position == bitmapProvider.getSelectedImageIndex() ? R.color.grid_state_selected : android.R.color.transparent;
            imageView.setBackgroundColor(getResources().getColor(color));
            return imageView;
        }
    }


}
