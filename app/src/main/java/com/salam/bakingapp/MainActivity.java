package com.salam.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.salam.bakingapp.Adapters.ItemNamesAdapter;
import com.salam.bakingapp.Models.ItemNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ItemNames> itemNamesList = new ArrayList<>();
    private ItemNamesAdapter mAdapter;
    boolean isPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int columns = 1;
        //set layouts , gridview for tablet and linear for phpne
        isPhone = getResources().getBoolean(R.bool.is_phone);
        if (!isPhone) { //it's a tablet
            columns = 4;
        } else { //it's a phone, not a tablet
            columns = 1;
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, columns);
        recyclerView = findViewById(R.id.recipe_recycler);
        mAdapter = new ItemNamesAdapter(MainActivity.this, itemNamesList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setClickable(true);

        ShowItemsNames();

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
        RequestQueue Video_Queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i =0; i<20; i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        ItemNames VL = new ItemNames(jsonObject1.getString("id"),jsonObject1.getString("name"));
                        VL.setId(jsonObject1.getString("id"));
                        VL.setName(jsonObject1.getString("name"));
                        itemNamesList.add(VL);
                        recyclerView.setAdapter(mAdapter);
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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Video_Queue.add(stringRequest);
    }

}
