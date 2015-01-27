package com.hzy.nsgif;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class GifDecoder {
	int frameCount;
	int imageWidth;
	int imageHeight;
	Frame curFrame = null;
	int decoderHandler = 0;

	/**
	 * constructor
	 * 
	 * @param path
	 *            the gif file path
	 */
	public GifDecoder(String path) {
		int[] params = new int[4];
		if (nInitParams(path, params) == 0) {
			this.frameCount = params[0];
			this.imageWidth = params[1];
			this.imageHeight = params[2];
			this.decoderHandler = params[3];
			curFrame = new Frame();
			curFrame.bitmap = Bitmap.createBitmap(imageWidth, imageHeight, Config.ARGB_8888);
			curFrame.index = 0;
		} else {
			throw new RuntimeException("Gif file decode error");
		}
	}

	/**
	 * release the resources
	 */
	public void recycle() {
		curFrame.bitmap.recycle();
		if (this.nDestory(this.decoderHandler) != 0) {
			throw new RuntimeException("native destory failed");
		}
	}

	/**
	 * get total frame count of the gif
	 */
	public int getFrameCount() {
		return this.frameCount;
	}

	public int getWidth() {
		return this.imageWidth;
	}

	public int getHeight() {
		return this.imageHeight;
	}

	/**
	 * 
	 * @return the first frame
	 */
	public Frame getFirstFrame() {
		return getFrame(0);
	}

	/**
	 * 
	 * @return the current frame
	 */
	public Frame getCurrentFrame() {
		return getFrame(curFrame.index);
	}

	/**
	 * 
	 * @return the next frame
	 */
	public Frame getNextFrame() {
		return getFrame((curFrame.index + 1) % frameCount);
	}

	/**
	 * get frame by index
	 * 
	 * @param index
	 * @return
	 */
	public Frame getFrame(int index) {
		if (index < 0 || index >= this.frameCount) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		if (curFrame.bitmap != null) {
			int delay = nGetFrameBitmap(index, curFrame.bitmap, this.decoderHandler);
			if (delay > 0) {
				curFrame.delay = delay;
				curFrame.index = index;
				return curFrame;
			} else {
				throw new RuntimeException("Gif file decode error");
			}
		}
		return null;
	}

	/**
	 * One frame of the gif
	 * 
	 * @author felix
	 * 
	 */
	public class Frame {
		public int index = 0;
		public Bitmap bitmap = null;
		public int delay = 0;
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		this.recycle();
		super.finalize();
	}

	/**
	 * init the params, get the frame count, width,height
	 * 
	 * @param path
	 *            gif image file path
	 * @param params
	 *            returned gif parameters
	 * @return error code ,0 if no error
	 */
	private native int nInitParams(String path, int[] params);

	/**
	 * write image data to bitmap
	 * 
	 * @param index
	 *            frame index
	 * @param bmp
	 *            target bitmap
	 * @return frame delay time(ms) if <= 0 means failed
	 */
	private native int nGetFrameBitmap(int index, Bitmap bmp, int handler);

	/**
	 * destory the native resources
	 * 
	 * @return
	 */
	private native int nDestory(int handler);

	/**
	 * load the native library
	 */
	static {
		System.loadLibrary("nsgif");
	}

}
