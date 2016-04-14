package com.example.sel.lostfound;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sel.lostfound.dummy.SpinnerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rohit G on 4/2/2016.
 */


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubscribeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubscribeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubscribeFragment extends Fragment {


    String postAddress = "http://52.38.30.3/getallcat.php";
    private Spinner mySpinner;

    private SpinnerAdapter adapter;
    private View myFragmentView;
    private String catID;

    Button subscribe_button;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SubscribeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubscribeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubscribeFragment newInstance(String param1, String param2) {
        SubscribeFragment fragment = new SubscribeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FeedActivity) getActivity()).getSupportActionBar().setTitle("Subscribe");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mySpinner = (Spinner) myFragmentView.findViewById(R.id.myspinner);

        ScriptRunner run = new ScriptRunner(new ScriptRunner.ScriptFinishListener() {
            @Override
            public void finish(String result, int resultCode) {
                if(resultCode==ScriptRunner.SUCCESS){
                    //parse json
                    Log.d("Yup", "Came");
                    String data = "";

                    List<Category> c = new ArrayList<Category>();
                    try {
                        JSONObject jsonRootObject = new JSONObject(result);
                        JSONArray jsonArray = jsonRootObject.getJSONArray("category_list");
                        //Iterate the jsonArray and print the info of JSONObjects
                        for(int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String categoryName = jsonObject.optString("CategoryName").toString();
                            String cid = jsonObject.optString("CategoryID").toString();

                            Category category = new Category(categoryName,cid);
                            c.add(category);

                        }
                        Log.e("List size", "" + c.size());

                        adapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item,c);
                        mySpinner.setAdapter(adapter);

                        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view,
                                                       int position, long id) {
                                // Here you get the current item (a User object) that is selected by its position
                                Category cat = adapter.getItem(position);
                                // Here you can do the action you want to...
                                catID = cat.getCid();
                                Toast.makeText(getActivity(), "ID: " + catID + "\ncat: " + cat.getCategory(),
                                        Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapter) {
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        run.execute(postAddress);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_subscribe, container, false);

        subscribe_button = (Button)myFragmentView.findViewById(R.id.subscribe_button);
        subscribe_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "subscribing", Toast.LENGTH_LONG).show();

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        String email = MainActivity.userEmail;
                        String checkUrl = "http://52.38.30.3/addsubscription.php";
                        try {
                            URL url = new URL(checkUrl);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setReadTimeout(10000);
                            httpURLConnection.setConnectTimeout(15000);
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.setDoInput(true);
                            OutputStream OS = httpURLConnection.getOutputStream();
                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                            ContentValues data = new ContentValues();
                            data.put("email", email);
                            data.put("catid", catID);
                            bufferedWriter.write(getQuery(data));
                            bufferedWriter.flush();
                            bufferedWriter.close();
                            OS.close();

                            InputStream IS = httpURLConnection.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                            String response = "";
                            String line = "";
                            while ((line = bufferedReader.readLine()) != null) {
                                response += line;
                            }
                            bufferedReader.close();
                            IS.close();
                            httpURLConnection.disconnect();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute();


            }

        });

        return myFragmentView;
    }


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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onSubscribeFragmentInteraction(String string);
        void onFragmentInteraction(Uri uri);
    }
}
