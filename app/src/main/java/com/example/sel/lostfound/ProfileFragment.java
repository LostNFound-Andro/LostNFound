package com.example.sel.lostfound;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohit G on 4/2/2016.
 */


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
	String postAddress = "http://52.38.30.3/getprofilepost.php";
    PostAdapter postAdapter;
    ListView listView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
	
	private View myFragmentView;
    private de.hdodenhof.circleimageview.CircleImageView user_image;

    private OnFragmentInteractionListener mListener;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FeedActivity) getActivity()).getSupportActionBar().setTitle("Profile");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String data = "";

        listView = (ListView) myFragmentView.findViewById(R.id.listView);


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
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String title = jsonObject.optString("title").toString();
                            String description = jsonObject.optString("description").toString();
                            String categoryid = jsonObject.optString("cid").toString();
                            String emailid = jsonObject.optString("email").toString();
                            String time = jsonObject.optString("time").toString();
                            String date = jsonObject.optString("date").toString();
                            String location = jsonObject.optString("location").toString();

                            Posts posts = new Posts(title,categoryid,date,description,emailid,location,time);
                            p.add(posts);


                            data += "\nPost"+i+" : \n title= "+ title +" \n description= "+ description +" \n emailid= "+ emailid +" \n\n ";
                            Log.e("c",data);
                        }
                        Log.e("List size",""+p.size());
                        postAdapter = new PostAdapter(getActivity(),R.layout.row_layout,p);
                        listView.setAdapter(postAdapter);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        user_image = (de.hdodenhof.circleimageview.CircleImageView) myFragmentView.findViewById(R.id.profileImage);
        TextView user_name = (TextView) myFragmentView.findViewById(R.id.profileNameText);
        TextView user_email = (TextView) myFragmentView.findViewById(R.id.profileEmailText);
        user_name.setText(MainActivity.userName);
        user_email.setText(MainActivity.userEmail);
        if(MainActivity.userImage != null) {
            new DownloadImageTask(user_image)
                    .execute(MainActivity.userImage.toString());
        }
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
        public void onProfileFragmentInteraction(String string);
        void onFragmentInteraction(Uri uri);
    }
	
	    private class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap userDp = null;
            try {
                InputStream IS = new java.net.URL(urldisplay).openStream();
                userDp = BitmapFactory.decodeStream(IS);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return userDp;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
