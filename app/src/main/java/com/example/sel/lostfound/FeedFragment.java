package com.example.sel.lostfound;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 /**
 * A class implementing fragment of feed when its clicked on navigation bar
 **/
public class FeedFragment extends Fragment {

    String postAddress = "http://52.38.30.3/getpost.php"; /*!< variable postaddress stores the address of web server  */
    TextView itemListText; /*!<   */
    TextView jsonParsedOutput; /*!<   */
    PostAdapter postAdapter; /*!<   */
    ListView listView; /*!< list view variable to display feeds in list form */
    ArrayAdapter<String> adapter; /*!<   */
    private Spinner mySpinner; /*!< spinner variable for dropdown list implementation */


    List<String> posttype = new ArrayList<>(Arrays.asList("lost", "found")); 



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
    //! new instance of fragment 
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    //! function on clicking 'Feed' on the navigation bar
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FeedActivity) getActivity()).getSupportActionBar().setTitle("Feed");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    //! function on opening feed page
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String data = "";

        listView = (ListView) myFragmentView.findViewById(R.id.listView);  /*!<list view instance */

        mySpinner = (Spinner) myFragmentView.findViewById(R.id.feedtype);	/*!< spinner for dropdown list of found or lost type */

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,posttype)
        {
            @Override
            //! dropdown lost
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView label = new TextView(getActivity());

                label.setText(posttype.get(position));
                label.setTextSize(24);
                return label;
            }
            @Override
            //! dropdown found
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView label = new TextView(getActivity());

                label.setText(posttype.get(position));
                label.setTextSize(24);
                return label;
            }
        };
        mySpinner.setAdapter(adapter);
        //! function on selecting type in dropdown list
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            //! function on selecting type of feed you want to browse(found/lost)
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //! Here you get the current item (a User object) that is selected by its position
                FeedActivity.ptype = adapter.getItem(position);
                // Here you can do the action you want to...
                
		//!display type
                Toast.makeText(getActivity(), "\ntype: " + FeedActivity.ptype,
                        Toast.LENGTH_SHORT).show();

                ScriptRunner run = new ScriptRunner(new ScriptRunner.ScriptFinishListener() {
                    @Override
                    public void finish(String result, int resultCode) {
                        if(resultCode==ScriptRunner.SUCCESS){
                            //parse json
                            Log.d("Yup","Came");
                            String data = "";

                            List<Posts> p = new ArrayList<Posts>();
                            try {
                                JSONObject jsonRootObject = new JSONObject(result);
                                JSONArray jsonArray = jsonRootObject.getJSONArray("postlist");
                                //Iterate the jsonArray and print the info of JSONObjects
                                for(int i=0; i < jsonArray.length(); i++){
                                //!extracting information from database table 'post'
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
				    
                                    String title = jsonObject.optString("title").toString();
                                    String description = jsonObject.optString("description").toString();
                                    String categoryid = jsonObject.optString("cid").toString();
                                    String emailid = jsonObject.optString("email").toString();
                                    String time = jsonObject.optString("time").toString();
                                    String date = jsonObject.optString("date").toString();
                                    String location = jsonObject.optString("location").toString();
                                    String postid = jsonObject.optString("post_id").toString();
                                    int count = Integer.parseInt(jsonObject.optString("count").toString());
				    //!adding a new post in the 'feed' page	
                                    Posts posts = new Posts(postid,title,categoryid,date,description,emailid,location,time,count);
                                    p.add(posts);


                                    data += "\nPost"+i+" : \n title= "+ title +" \n description= "+ description +" \n emailid= "+ emailid +" \n\n ";
                                    Log.e("c",data);
                                }
                                Log.e("List size",""+p.size());
                                postAdapter = new PostAdapter(getActivity(),R.layout.row_layout,p);
                                listView.setAdapter(postAdapter);
                                jsonParsedOutput.setVisibility(View.GONE);
                                listView.invalidate();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                run.execute(postAddress);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });






    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


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
