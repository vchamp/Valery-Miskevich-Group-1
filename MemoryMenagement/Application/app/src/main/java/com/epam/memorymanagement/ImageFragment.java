package com.epam.memorymanagement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends Fragment {

	private static final String IMAGE_INDEX_EXTRA = "imageIndex";

	private int imageIndex;
	private ImageView imageView;

    private ImageLoader imageLoader;

	static ImageFragment newInstance(int imageIndex) {
		final ImageFragment f = new ImageFragment();
		final Bundle args = new Bundle();
		args.putInt(IMAGE_INDEX_EXTRA, imageIndex);
		f.setArguments(args);
		return f;
	}

	public ImageFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imageIndex = getArguments() != null ? getArguments().getInt(
				IMAGE_INDEX_EXTRA) : -1;
        imageLoader = ImageLoader.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_image, container,
				false);
		imageView = (ImageView) v.findViewById(R.id.imageView);
		return v;
	}

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        imageLoader = null;
    }

    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Load image into ImageView
        imageLoader.loadImage(ImageUtil.getAssetName(imageIndex),imageView);
	}
}
