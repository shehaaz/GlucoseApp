package com.example.glucoseapp;

import java.io.InputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class GraphActivity extends Activity {

	private ImageView graph;
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		
		graph = (ImageView) findViewById(R.id.graph);
		
		new DownloadGraphTask().execute("http://198.61.177.186:5000/static/glucose.png");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graph, menu);
		return true;
	}

	private class DownloadGraphTask extends AsyncTask<String, Void, Bitmap> {

		

		protected void onPreExecute() {
			
			pDialog = new ProgressDialog(GraphActivity.this);
			pDialog.setMessage("Drawing Graph...");
			pDialog.show();
		}

		protected Bitmap doInBackground(String... urls) {

			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			graph.setImageBitmap(result);
			pDialog.dismiss();
		}
	}

}
