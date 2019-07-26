package com.salam.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.salam.bakingapp.Adapters.Ingredients_Adapter;
import com.salam.bakingapp.Models.Ingredients_Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class IngredientsFrag extends Fragment {
    String ItemID;
    String id;
    private List<Ingredients_Model> ingredients_items = new ArrayList<>();
    private Ingredients_Adapter mAdapter;
    RecyclerView recyclerView;

    public IngredientsFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        ItemID = bundle.getString("id");
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ingredients, container, false);
        recyclerView = view.findViewById(R.id.ingredients_recycler);
        mAdapter = new Ingredients_Adapter(getActivity(), ingredients_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ShowItemsNames();

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
        RequestQueue Video_Queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i =0; i<20; i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        id = jsonObject1.getString("id");
                        if (id.equals(ItemID) ){
                            JSONArray JSONARRAY = jsonObject1.getJSONArray("ingredients");
                            for(int k =0; k<20; k++){
                                JSONObject stepsobject = JSONARRAY.getJSONObject(k);
                                Ingredients_Model VL = new Ingredients_Model(stepsobject.getString("quantity"),stepsobject.getString("measure"), stepsobject.getString("ingredient"));
                                VL.setQuantity(stepsobject.getString("quantity"));
                                VL.setMeasure(stepsobject.getString("measure"));
                                VL.setIngredients(stepsobject.getString("ingredient"));
                                ingredients_items.add(VL);
                                recyclerView.setAdapter(mAdapter);
                            }
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


}
