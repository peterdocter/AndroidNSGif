package com.hzy.nsgif;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hzy.nsgif.GifDecoder.Frame;

public class NSGifView extends ImageView implements Runnable {

	private GifDecoder gifDecoder = null;
	private Frame curFrame = null;
	private boolean animating = false;
	private Runnable updateAction = null;

	public NSGifView(Context context, final AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public NSGifView(Context context) {
		super(context);
		init();
	}

	public NSGifView(Context context, String filePath) {
		this(context);
		setImagePath(filePath);
	}

	private void init() {
		updateAction = new Runnable() {
			@Override
			public void run() {
				curFrame = gifDecoder.getNextFrame();
				setImageBitmap(curFrame.bitmap);
			}
		};
	}

	public void setImagePath(String filePath) {
		if (animating == true) {
			animating = false;
		}
		if (gifDecoder != null) {
			gifDecoder.recycle();
		}
		gifDecoder = new GifDecoder(filePath);
		curFrame = gifDecoder.getCurrentFrame();
		setImageBitmap(curFrame.bitmap);
	}

	public void startAnimation() {
		if (!animating) {
			animating = true;
			new Thread(this).start();
		}
	}

	public void stopAnimation() {
		animating = false;
	}

	public boolean isAnimating() {
		return this.animating;
	}

	@Override
	public void run() {
		do {
			if (!animating)
				break;
			post(updateAction);
			try {
				Thread.sleep(curFrame.delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (animating);
	}
}
