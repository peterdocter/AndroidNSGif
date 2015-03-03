package com.hzy.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.hzy.nsgif.NSGifView;
import com.hzy.nsgif.R;
import com.hzy.nsgif.R.id;

public class MainActivity extends Activity implements OnClickListener {

	NSGifView v1, v2, v3, v4;
	LinearLayout llMain = null;
	String dataDir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		v1 = (NSGifView) findViewById(id.gifView1);
		v2 = (NSGifView) findViewById(id.gifView2);
		v3 = (NSGifView) findViewById(id.gifView3);
		llMain = (LinearLayout) findViewById(id.linearLayoutMain);

		dataDir = getFilesDir() + File.separator;
		try {
			unZip("gif.zip", dataDir, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		v1.setImagePath(dataDir + "nointerval.gif");
		v2.setImagePath(dataDir + "002.gif");
		v3.setImagePath(dataDir + "003.gif");
		v4 = new NSGifView(this, dataDir + "004.gif");
		llMain.addView(v4);
		
		v1.setOnClickListener(this);
		v2.setOnClickListener(this);
		v3.setOnClickListener(this);
		v4.setOnClickListener(this);
		
		v1.startAnimation();
		v3.startAnimation();
	}

	@Override
	public void onClick(View v) {
		NSGifView gif = (NSGifView) v;
		if (gif.isAnimating()) {
			gif.stopAnimation();
		} else {
			gif.startAnimation();
		}
	}

	/**
	 * unzip the gif files to test
	 * 
	 * @param assetName
	 *            zip file name
	 * @param outputDirectory
	 *            output directory
	 * @param isReWrite
	 *            do you want to rewrite
	 * @throws IOException
	 */
	private void unZip(String assetName, String outputDirectory, boolean isReWrite) throws IOException {
		File file = new File(outputDirectory);
		if (!file.exists()) {
			file.mkdirs();
		}
		InputStream inputStream = getAssets().open(assetName);
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		ZipEntry zipEntry = zipInputStream.getNextEntry();
		byte[] buffer = new byte[1024 * 1024];
		int count = 0;
		while (zipEntry != null) {
			if (zipEntry.isDirectory()) {
				file = new File(outputDirectory + File.separator + zipEntry.getName());
				if (isReWrite || !file.exists()) {
					file.mkdir();
				}
			} else {
				file = new File(outputDirectory + File.separator + zipEntry.getName());
				if (isReWrite || !file.exists()) {
					file.createNewFile();
					FileOutputStream fileOutputStream = new FileOutputStream(file);
					while ((count = zipInputStream.read(buffer)) > 0) {
						fileOutputStream.write(buffer, 0, count);
					}
					fileOutputStream.close();
				}
			}
			zipEntry = zipInputStream.getNextEntry();
		}
		zipInputStream.close();
	}
}
