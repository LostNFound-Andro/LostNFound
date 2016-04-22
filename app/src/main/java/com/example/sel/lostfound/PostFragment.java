package com.example.sel.lostfound;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.List;
import java.util.Map;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
/**
 * \class PostFragment
* 		\brief class implementing post page
**/ 
public class PostFragment extends Fragment {


    TextView txtTitle;	/*!< Variable for storing title input by user */
    TextView txtDesc;	/*!< Variable for storing description input by user */
    TextView txtLoc;	/*!< Variable for storing location input by user */
    public static  TextView timePicker;	/*!< Variable for storing time selected by user */
    public static TextView datePicker;	/*!< Variable for storing date selected by user */


    String postAddress = "http://52.38.30.3/getallcat.php"; /*!< address to access the category stored in database via php */
    private Spinner mySpinner; /*!< spinnner instance for dropdown */

    private SpinnerAdapter adapter;
    private View myFragmentView;
    private String catID; /*!< stores category id */

    protected static String postType = "";



    private static String title,description,location,time,date; /*!< variables to store values to be inserted into the database */


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    protected static Button postButton; /*!< variable for post button */

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    /**
    * function implementing a new post page 
    */
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    //! functions to be done after opening new instance of post fragment.
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FeedActivity) getActivity()).getSupportActionBar().setTitle("Post");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    /** \fn public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
     *  \brief function implementing task of extracting input by user into database
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_post, container, false);
        new AlertDialog.Builder(getActivity())
                .setTitle("Post")
                .setMessage("What are you posting today?")
                .setPositiveButton("I've found an item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        postType = "found"; /*!< \var postType \brief postType contains decision by user if found or lost item to be posted */
                        //! initialisation of the variables extracting the input
                        TextView titleCap = (TextView) myFragmentView.findViewById(R.id.titleCap);
                        txtTitle = (TextView) myFragmentView.findViewById(R.id.txtTitle);
                        TextView descCap = (TextView) myFragmentView.findViewById(R.id.descCap);
                        txtDesc = (TextView) myFragmentView.findViewById(R.id.txtDesc);
                        TextView locCap = (TextView) myFragmentView.findViewById(R.id.locCap);
                        txtLoc = (TextView) myFragmentView.findViewById(R.id.txtLoc);
                        TextView catCap = (TextView) myFragmentView.findViewById(R.id.catCap);
                        mySpinner = (Spinner) myFragmentView.findViewById(R.id.spinner);
                        TextView dateCap = (TextView) myFragmentView.findViewById(R.id.dateCap);
                        datePicker = (TextView) myFragmentView.findViewById(R.id.datePicker);
                        TextView timeCap = (TextView) myFragmentView.findViewById(R.id.timeCap);
                        timePicker = (TextView) myFragmentView.findViewById(R.id.timePicker);
                        postButton = (Button) myFragmentView.findViewById(R.id.postButton);
                        titleCap.setVisibility(View.VISIBLE);
                        txtTitle.setVisibility(View.VISIBLE);
                        descCap.setVisibility(View.VISIBLE);
                        txtDesc.setVisibility(View.VISIBLE);
                        locCap.setVisibility(View.VISIBLE);
                        txtLoc.setVisibility(View.VISIBLE);
                        catCap.setVisibility(View.VISIBLE);
                        mySpinner.setVisibility(View.VISIBLE);
                        //displaying categories in dropdown
                        ScriptRunner run = new ScriptRunner(new ScriptRunner.ScriptFinishListener() {
                            @Override
                            public void finish(String result, int resultCode) {
                                if(resultCode==ScriptRunner.SUCCESS){
                                    //parse json

				//! fetching category list from database to be displayed using json
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
                        dateCap.setVisibility(View.VISIBLE);
                        datePicker.setVisibility(View.VISIBLE);
                        /**
                         * \fn datePicker.setOnClickListener
                         * \brief function to be executed on selecting a date via datepicker
                         */
                        datePicker.setOnClickListener(new View.OnClickListener()
                        {

                            @Override
                            public void onClick(View v) {
                                DialogFragment dateFragment = new DatePickerFragment();
                                dateFragment.show(getFragmentManager(),"datePicker");
                            }
                        });
                        timeCap.setVisibility(View.VISIBLE);
                        timePicker.setVisibility(View.VISIBLE);
                        /** \fn timePicker.setOnClickListener
                         * \brief function to be executed on selecting a time via timepicker
                         */
                        timePicker.setOnClickListener(new View.OnClickListener()
                        {

                            @Override
                            public void onClick(View v) {
                                DialogFragment timeFragment = new TimePickerFragment();
                                timeFragment.show(getFragmentManager(),"timePicker");
                            }
                        });

                        postButton.setVisibility(View.VISIBLE);
                        /** 
                         * \fn postButton.setOnClickListener
                         * \brief functions to be executed on clicking post button
                         */
                        postButton.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {

                                    //Toast.makeText(getActivity(),"Posting",Toast.LENGTH_LONG).show();
                                    title = txtTitle.getText().toString(); /*!< feeding input title into the variable */
                                    description = txtDesc.getText().toString(); /*!< feeding input description into the variable*/
                                    location = txtLoc.getText().toString(); /*!< feeding input location into the variable */
                                if (title.trim().length() != 0 && time.trim().length() != 0 && date.trim().length() != 0) {
                                    new AsyncTask<Void, Void, Void>() {

                                        @Override
                                        /**
                                        * function to implement posting of user input into database.
                                        **/
                                        protected Void doInBackground(Void... params) {
                                            String email = MainActivity.userEmail; /*!< store the user email id */
                                            String checkUrl = "http://52.38.30.3/addpost.php"; /*!< url of webserver*/
                                            //! establishing connection
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
                                                data.put("postType", postType); /*!< writing (lost/found) type into db */
                                                data.put("email", email); /*!< writing email into db */
                                                data.put("title", title); /*!< writing title into db */
                                                data.put("description", description); /*!< writing description into db */
                                                data.put("time", time); /*!< writing time into db */
                                                data.put("date", date); /*!< writing date into db */
                                                data.put("location", location); /*!< writing location into db */
                                                data.put("cid", catID); /*!< writing category id into db */
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

                                        @Override
                                        protected void onPostExecute(Void aVoid) {
                                            ProfileFragment profileFragment = new ProfileFragment();
                                            android.support.v4.app.FragmentTransaction fragmentTransaction = ((FeedActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                                            fragmentTransaction.replace(R.id.fragment_container, profileFragment);
                                            fragmentTransaction.commit();
                                        }
                                    }.execute();


                                }
                                else
                                {
                                    Toast.makeText(getActivity(),"All fields are mandatory",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                })
                /** \fn  public void onClick(DialogInterface dialog, int which)
                 * \brief posting lost item
                 */
                .setNegativeButton("I've lost an item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        postType = "lost"; /*!< postType set to lost */
                        //! initialisation of variables to extract user inputs
                        TextView titleCap = (TextView) myFragmentView.findViewById(R.id.titleCap);
                        txtTitle = (TextView) myFragmentView.findViewById(R.id.txtTitle);
                        TextView descCap = (TextView) myFragmentView.findViewById(R.id.descCap);
                        txtDesc = (TextView) myFragmentView.findViewById(R.id.txtDesc);
                        TextView locCap = (TextView) myFragmentView.findViewById(R.id.locCap);
                        txtLoc = (TextView) myFragmentView.findViewById(R.id.txtLoc);
                        TextView catCap = (TextView) myFragmentView.findViewById(R.id.catCap);
                        mySpinner = (Spinner) myFragmentView.findViewById(R.id.spinner);
                        TextView dateCap = (TextView) myFragmentView.findViewById(R.id.dateCap);
                        datePicker = (TextView) myFragmentView.findViewById(R.id.datePicker);
                        TextView timeCap = (TextView) myFragmentView.findViewById(R.id.timeCap);
                        timePicker = (TextView) myFragmentView.findViewById(R.id.timePicker);
                        postButton = (Button) myFragmentView.findViewById(R.id.postButton);
                        titleCap.setText("Sorry for your loss, let us help you find it.What did you lose?");
                        titleCap.setVisibility(View.VISIBLE);
                        txtTitle.setVisibility(View.VISIBLE);

                        descCap.setVisibility(View.VISIBLE);
                        txtDesc.setVisibility(View.VISIBLE);
                        locCap.setText("The location of where you lost might help you to get it back faster");
                        locCap.setVisibility(View.VISIBLE);
                        txtLoc.setVisibility(View.VISIBLE);
                        catCap.setVisibility(View.VISIBLE);
                        mySpinner.setVisibility(View.VISIBLE);
                        //!displaying categories in dropdown
                        ScriptRunner run = new ScriptRunner(new ScriptRunner.ScriptFinishListener() {
                            @Override
                            public void finish(String result, int resultCode) {
                                if(resultCode==ScriptRunner.SUCCESS){
                                    //!parse json


                                    List<Category> c = new ArrayList<Category>();
                                    try {
                                        JSONObject jsonRootObject = new JSONObject(result);
                                        JSONArray jsonArray = jsonRootObject.getJSONArray("category_list");
                                        //!Iterate the jsonArray and print the info of JSONObjects
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
                        dateCap.setText("When did you lose it?");
                        dateCap.setVisibility(View.VISIBLE);
                        datePicker.setVisibility(View.VISIBLE);
                        datePicker.setOnClickListener(new View.OnClickListener()
                        {

                            @Override
                            public void onClick(View v) {
                                DialogFragment dateFragment = new DatePickerFragment();
                                dateFragment.show(getFragmentManager(),"datePicker");
                            }
                        });
                        timeCap.setVisibility(View.VISIBLE);
                        timePicker.setVisibility(View.VISIBLE);
                        timePicker.setOnClickListener(new View.OnClickListener()
                        {

                            @Override
                            public void onClick(View v) {
                                DialogFragment timeFragment = new TimePickerFragment();
                                timeFragment.show(getFragmentManager(),"timePicker");
                            }
                        });

                        postButton.setVisibility(View.VISIBLE);
                        //@see postButton.setOnClickListener for 'found'
                        
                        postButton.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {

                                    //Toast.makeText(getActivity(),"Posting",Toast.LENGTH_LONG).show();
                                    title = txtTitle.getText().toString();
                                    description = txtDesc.getText().toString();
                                    location = txtLoc.getText().toString();
                                if (title.trim().length() != 0 && time.trim().length() != 0 && date.trim().length() != 0) {
                                    new AsyncTask<Void, Void, Void>() {

                                        @Override
                                        protected Void doInBackground(Void... params) {
                                            String email = MainActivity.userEmail;
                                            String checkUrl = "http://52.38.30.3/addpost.php";
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
                                                data.put("postType", postType);
                                                data.put("email", email);
                                                data.put("title", title);
                                                data.put("description", description);
                                                data.put("time", time);
                                                data.put("date", date);
                                                data.put("location", location);
                                                data.put("cid", catID);
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

                                        @Override
                                        protected void onPostExecute(Void aVoid) {
                                            ProfileFragment profileFragment = new ProfileFragment();
                                            android.support.v4.app.FragmentTransaction fragmentTransaction = ((FeedActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                                            fragmentTransaction.replace(R.id.fragment_container, profileFragment);
                                            fragmentTransaction.commit();
                                        }
                                    }.execute();


                                }
                                else
                                {
                                    Toast.makeText(getActivity(),"All fields are mandatory",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                })
                .setIcon(android.R.drawable.ic_input_add)
                .show();
        return myFragmentView;
    }
    //! A class implementing time picker 
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //! Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            //! Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time = hourOfDay+":"+minute;
            timePicker.setText(time);
        }
    }
   /**	\class public static class DatePickerFragment
   *    \brief a class implementing date picker
   */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //! Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            //! Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            date = day+"/"+month+"/"+year;
            datePicker.setText(date);
        }
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
        public void onPostFragmentInteraction(String string);
        void onFragmentInteraction(Uri uri);
    }
}
