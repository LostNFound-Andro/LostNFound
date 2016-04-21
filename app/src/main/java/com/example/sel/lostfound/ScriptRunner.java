
package com.example.sel.lostfound;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;
/**
 *	Thie program connects android app to database to post the post. (post use case) 
 */
 
/** \class  ScriptRunner 
 * 	\extends AsyncTask<String, Void, String>
 * 	\brief contains success and failure counts, webserver address, object string to store value of the post, isdone boolean to describe process status (complete/incomplete) also functions to copy database entries from the given query.
 */
 
public class ScriptRunner extends AsyncTask<String, Void, String> {
/**
 *  \var public static final int SUCCESS
 * 		\brief static variable for success count.
 */
	public static final int SUCCESS = 0;
/**
 *  \var public static final int FAIL
 * 		\brief static variable for failure count.
 */

	public static final int FAIL = 1;
/**
 *  \var public static final String ENDPOINT
 * 		\brief static variable for webserver address, where database is stored.
 */
	public static final String ENDPOINT = "http://52.38.30.3/";//addpost.php"
	
	private String objectString;
/**
 *  \var public boolean isDone
 * 		\brief public variable to take if process is complete or not.
 */	
	private boolean isDone = false;

	private int code;
	
/** \class  ScriptFinishListener
 * 	\brief Displays success or failure in login
*/	
	private ScriptFinishListener listener;
	public ScriptRunner(ScriptFinishListener listener) {
		this.listener = listener;
	}
/**
 *	\fn	protected String doInBackground(String... s)
 * 		\brief email validation is done.
 * 		\exception	printStackTrace**
 * 			\returns ObjectString
 */
	
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
 			String email = MainActivity.userEmail;
 			OutputStream OS = conn.getOutputStream();
    		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
 			ContentValues data = new ContentValues();
			data.put("email", email);
  			data.put("posttype",FeedActivity.ptype);
  			Log.d("c", email);
 			bufferedWriter.write(getQuery(data));
 			bufferedWriter.flush();
  			bufferedWriter.close();
 			OS.close();
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

/**
 *	\fn	private String getQuery(ContentValues params)
 * 	\exception	UnsupportedEncodingException
 */
	private String getQuery(ContentValues params) throws UnsupportedEncodingException
	{
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (Map.Entry<String, Object> entry : params.valueSet())
		{
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
		}

		return result.toString();
	}



/**
 *	\fn  public boolean getIsDone()
 * \brief checks process status
 */
	public boolean getIsDone(){
		return isDone;
	}

/**
 *	\fn  protected void onPostExecute(String result)
 *  \param result
 * 	\brief Checks if post is added 
 */

	@Override
 
    protected void onPostExecute(String result) {
		Log.e("check",result);
		
		
  /*! 
   *  \if isDone
   *    Only included if process is complete, gives result as success.
   *  \endif
   *  \else
   *  	give result as fail.
   */
		if(isDone){
			listener.finish(result,SUCCESS);
		}else{
			listener.finish(result, FAIL);
		}
		
    }
	
/**
 *	finishes process. 
 */
	public interface ScriptFinishListener{
		public void finish(String result, int resultCode);
	}
}
