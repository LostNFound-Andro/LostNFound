/* Anas M */
package com.example.sel.lostfound;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class ScriptRunner extends AsyncTask<String, Void, String> {
	
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;

	public static final String ENDPOINT = "http://52.38.30.3/";//addpost.php"
	
	private String objectString;
	private boolean isDone = false;
	private int code;
	private ScriptFinishListener listener;
	public ScriptRunner(ScriptFinishListener listener) {
		this.listener = listener;
	}
	
	@Override
	protected String doInBackground(String... s) {
		objectString = " ";
		try {
			URL url = new URL(s[0]);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);

			DataInputStream dis = new DataInputStream(conn.getInputStream());
			StringBuilder stringBuilder = new StringBuilder();
			String line;

			do {
				line = dis.readLine();
				stringBuilder.append(line);

			} while (line != null);


			objectString = stringBuilder.toString();

			conn.connect();

		}catch (Exception e){
			e.printStackTrace();
		}
		isDone = true;
		return objectString;
	}

	public boolean getIsDone(){
		return isDone;
	}

	@Override
    protected void onPostExecute(String result) {
		Log.e("check",result);
		if(isDone){
			listener.finish(result,SUCCESS);
		}else{
			listener.finish(result, FAIL);
		}
		
    }
	
	
	public interface ScriptFinishListener{
		public void finish(String result, int resultCode);
	}
}
