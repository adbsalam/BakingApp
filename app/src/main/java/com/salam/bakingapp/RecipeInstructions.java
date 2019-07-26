package com.salam.bakingapp;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.VISIBLE;


public class RecipeInstructions extends Fragment {

    String id;
    String passedid;
    TextView instructions;
    TextView title;
    RelativeLayout previous_setep;
    PlayerView playerView;
    ExoPlayer player;
    boolean playWhenReady;
    int playbackPosition;
    int currentWindow;
    String PassURL;
    boolean isPhone;
    RelativeLayout nextStep;
    String passItemID;
    TextView Novideos;


    public RecipeInstructions() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null){

        }
        //getting item id and step id to pursue the query
        else {
            passedid = getArguments().getString("id");
            passItemID = getArguments().getString("itemid");
            ShowInstructions();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipe_instructions, container, false);
        instructions = view.findViewById(R.id.instructions);
        playerView = view.findViewById(R.id.exo_player);
        title = view.findViewById(R.id.instruc);
        Novideos = view.findViewById(R.id.no_video);

        //setting next and previous step buttons for phone so it wont load in the tablet
        isPhone = getResources().getBoolean(R.bool.is_phone);
        if (!isPhone) { //it's a tablet

        } else { //it's a phone, not a tablet
            nextStep = view.findViewById(R.id.Next_step);
            previous_setep = view.findViewById(R.id.previous_step);
            nextStep.setVisibility(View.VISIBLE);
            nextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer newint;
                    newint = Integer.parseInt(passedid) +1;
                    passedid = String.valueOf(newint);
                    ShowInstructions();

                }
            });
            previous_setep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer newint;
                    newint = Integer.parseInt(passedid) - 1;
                    passedid = String.valueOf(newint);
                    ShowInstructions();
                }
            });
        }


        return view;


    }

    private void initializePlayer( String url) {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());
        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        Uri uri = Uri.parse(url);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = (int) player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private void ShowInstructions() {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(getString(R.string.auth))
                .authority(getString(R.string.authority))
                .appendPath(getString(R.string.topher))
                .appendPath(getString(R.string.year))
                .appendPath(getString(R.string.month))
                .appendPath(getString(R.string.link)).appendPath(getString(R.string.jsonname));
        String URL = builder.toString();
        RequestQueue Video_Queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i =0; i<20; i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        id = jsonObject1.getString("id");
                        if (id.equals(passItemID) ){
                            JSONArray JSONARRAY = jsonObject1.getJSONArray("steps");
                            for(int k =0; k<JSONARRAY.length(); k++){
                                JSONObject stepsobject = JSONARRAY.getJSONObject(k);
                                if (stepsobject.getString("id").equals(passedid)) {
                                    instructions.setText(stepsobject.getString("description"));
                                    if (stepsobject.getString("videoURL").equals("")) {
                                        PassURL = stepsobject.getString("thumbnailURL");
                                    }else
                                    {
                                        PassURL = stepsobject.getString("videoURL");
                                    }
                                }
                        }
                            //setting visibility for next and last step on last items of the steps.
                            //previous step button should hide on first element
                            //next button should hide for the last element
                            if (!isPhone) { //it's a tablet
                            } else {
                                //it's a phone, not a tablet
                                Integer arraylength = JSONARRAY.length()-1 ;
                                if (passedid.equals(String.valueOf(arraylength))){
                                    nextStep.setVisibility(View.INVISIBLE);
                                }
                                else {
                                    nextStep.setVisibility(VISIBLE);
                                }
                                if (passedid.equals("0")){
                                    previous_setep.setVisibility(View.INVISIBLE);
                                }else if (passedid != "0") {
                                    previous_setep.setVisibility(VISIBLE);
                                }
                            }

                            //show no videos tag when no video link is available
                            if (PassURL.equals("")){
                                Novideos.setVisibility(VISIBLE);
                                initializePlayer(PassURL);
                            }
                            else if (!PassURL.equals("")){
                                initializePlayer(PassURL);
                                Novideos.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Video_Queue.add(stringRequest);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
            //configuration for full screen exoplayer PHONE only
        if (getContext().getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE && isPhone) {
            playerView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            hideSystemUi();
            instructions.setVisibility(View.INVISIBLE);
            title.setVisibility(View.INVISIBLE);
            nextStep.setVisibility(View.INVISIBLE);
            nextStep.setVisibility(View.INVISIBLE);
        }

        //Setting screen back to normal after fullscreen exoplayer exits
        if (getContext().getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT && isPhone) {
            instructions.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
            params.height = getResources().getDimensionPixelSize(R.dimen.player_height);
            playerView.setLayoutParams(params);
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE);
            nextStep.setVisibility(View.VISIBLE);
            nextStep.setVisibility(View.VISIBLE);

        }
    }
}
