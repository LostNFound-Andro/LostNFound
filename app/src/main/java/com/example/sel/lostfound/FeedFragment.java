package com.example.sel.lostfound;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rohit G on 4/2/2016.
 */


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {

    String postAddress = "http://52.38.30.3/getpost.php";
    TextView itemListText;
    TextView jsonParsedOutput;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View myFragmentView;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FeedActivity) getActivity()).getSupportActionBar().setTitle("Feed");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    private String getPost() {
        String data = "";
        try {


            URL url = new URL(postAddress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            DataInputStream dis = new DataInputStream(conn.getInputStream());
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            do{
                line = dis.readLine();
                stringBuilder.append(line);

            }while (line != null);

            String output;
            output = stringBuilder.toString();

            try {
                JSONObject jsonRootObject = new JSONObject(output);
                JSONArray jsonArray = jsonRootObject.getJSONArray("postlist");
                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String title = jsonObject.optString("title").toString();
                    String description = jsonObject.optString("description").toString();
                    String categoryid = jsonObject.optString("cid").toString();
                    String emailid = jsonObject.optString("email").toString();
                    String time = jsonObject.optString("time").toString();
                    String date = jsonObject.optString("date").toString();
                    String location = jsonObject.optString("location").toString();

                    data += "\nPost"+i+" : \n title= "+ title +" \n description= "+ description +" \n emailid= "+ emailid +" \n\n ";
                }

            } catch (JSONException e) {e.printStackTrace();}

//            Log.d("Out", stringBuilder.toString());
            //tv.setText(output);
            conn.connect();

        }
        catch (Exception e){
            Log.e("Out", e.getMessage()); ;
        }
        return data;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new AsyncTask<Void, Void, Void>(){

            String stringdata;

            @Override
            protected Void doInBackground(Void... params) {
                stringdata = getPost();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                jsonParsedOutput.setText(stringdata);
            }
        }.execute();
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_feed, container, false);
        itemListText = (TextView) myFragmentView.findViewById(R.id.itemListText);
        jsonParsedOutput = (TextView) myFragmentView.findViewById(R.id.loadingText);
        return myFragmentView;
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
        public void onFeedFragmentInteraction(String string);
        void onFragmentInteraction(Uri uri);
    }
}