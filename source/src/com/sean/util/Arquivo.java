package com.sean.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class Arquivo {

	public String gravaArquivo(InputStream inPut, String url, String image_name) {
		File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
		File file = new File(SDCardRoot, image_name);
		try {
			if(file.createNewFile())
				file.createNewFile();
			FileOutputStream fileOutput = new FileOutputStream(file);
			Bitmap bm = BitmapFactory.decodeStream(inPut); 
			bm.compress(CompressFormat.PNG, 100, fileOutput); 
			fileOutput.flush(); 
			fileOutput.close();
			//bm = BitmapFactory.decodeFile(filepath+image_name);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.i("Hub", "FileNotFoundException: "+ e.toString());

		} catch (IOException e) {
			e.printStackTrace();
			Log.i("Hub", "IOException: "+ e.toString());
		}
		return file.getAbsolutePath();
	}
}
