package com.example.instagram;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView ivResult;
	Bitmap bmp;
	Bitmap operation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ivResult = (ImageView) findViewById(R.id.ivResult);
		BitmapDrawable abmp = (BitmapDrawable) ivResult.getDrawable();
		bmp = abmp.getBitmap();
	}

	public void SaturationFilter(View view) {
		// get image size
		int level = 0;
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] pixels = new int[width * height];
		float[] HSV = new float[3];
		// get pixel array from source
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);

		int index = 0;
		// iteration through pixels
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				// get current index in 2D-matrix
				index = y * width + x;
				// convert to HSV
				Color.colorToHSV(pixels[index], HSV);
				// increase Saturation level
				HSV[1] *= level;
				HSV[1] = (float) Math.max(0.0, Math.min(HSV[1], 1.0));
				// take color back
				pixels[index] |= Color.HSVToColor(HSV);
			}
		}
		// output bitmap
		operation = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		operation.setPixels(pixels, 0, width, 0, 0, width, height);
		ivResult.setImageBitmap(operation);
	}

	public void bright(View view) {
		final double GS_RED = 0.299;
		final double GS_GREEN = 0.587;
		final double GS_BLUE = 0.114;

		// create output bitmap
		operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),
				bmp.getConfig());
		// pixel information
		int A, R, G, B;
		int pixel;

		// get image size
		int width = bmp.getWidth();
		int height = bmp.getHeight();

		// scan through every single pixel
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get one pixel color
				pixel = bmp.getPixel(x, y);
				// retrieve color of all channels
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);
				// take conversion up to one single value
				R = G = B = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);
				// set new pixel color to output bitmap
				operation.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}
		ivResult.setImageBitmap(operation);
	}

	public void dark(View view) {
		operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),
				bmp.getConfig());

		for (int i = 0; i < bmp.getWidth(); i++) {
			for (int j = 0; j < bmp.getHeight(); j++) {
				int p = bmp.getPixel(i, j);
				int r = Color.red(p);
				int g = Color.green(p);
				int b = Color.blue(p);
				int alpha = Color.alpha(p);

				r = r - 50;
				g = g - 50;
				b = b - 50;
				alpha = alpha - 50;
				operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));

			}
		}

		ivResult.setImageBitmap(operation);
	}
}
