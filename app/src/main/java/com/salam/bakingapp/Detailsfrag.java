package com.salam.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.exoplayer2.util.Util;
import com.salam.bakingapp.Adapters.StepsAdapter;
import com.salam.bakingapp.Models.RecipieDetailModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class Detailsfrag extends Fragment {

    private RecyclerView recyclerView;
    private List<RecipieDetailModel> steps_list = new ArrayList<>();
    private StepsAdapter mAdapter;
    String id;
    String passedid;
    TextView Ingredients;
    boolean isPhone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle=getArguments();
        assert bundle != null;
        passedid = bundle.getString("newid");

        View view  = inflater.inflate(R.layout.fragment_detailsfrag,container, false);
        recyclerView = view.findViewById(R.id.recipe_details_recycler);
        Ingredients = view.findViewById(R.id.ingredients);
        mAdapter = new StepsAdapter(getContext(), steps_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ShowItemsNames();
        isPhone = getResources().getBoolean(R.bool.is_phone);
        Ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPhone) { //it's a tablet
                    IngredientsFrag nextFrag= new IngredientsFrag();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container2, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                    Bundle args = new Bundle();
                    args.putString("id", passedid);
                    nextFrag.setArguments(args);
                } else { //it's a phone, not a tablet
                    IngredientsFrag nextFrag= new IngredientsFrag();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                    Bundle args = new Bundle();
                    args.putString("id", passedid);

                    nextFrag.setArguments(args);
                }
            }
        });
        return view;
    }


    private void ShowItemsNames() {

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
                        if (id.equals(passedid) ){
                            JSONArray JSONARRAY = jsonObject1.getJSONArray("steps");
                            for(int k =0; k<20; k++){
                                JSONObject stepsobject = JSONARRAY.getJSONObject(k);
                                RecipieDetailModel VL = new RecipieDetailModel(stepsobject.getString("id"),stepsobject.getString("shortDescription"), jsonObject1.getString("id"));
                                VL.setShortdescription(stepsobject.getString("shortDescription"));
                                VL.setId(stepsobject.getString("id"));
                                VL.setItemID(jsonObject1.getString("id"));
                                steps_list.add(VL);
                                recyclerView.setAdapter(mAdapter);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    mAdapter.notifyDataSetChanged();
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
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            steps_list.clear();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            steps_list.clear();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        steps_list.clear();
    }
}
