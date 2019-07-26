package com.salam.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.salam.bakingapp.Models.Ingredients_Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class BakingWidgetScreen extends AppWidgetProvider {
    public static String UPDATE_ACTION = "ActionUpdateSinglenoteWidget";
    static String titleofrecipie;
    static String idofitem;
    private List<String> ingredients_items = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String ingredients, String title) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_screen);
        views.setTextViewText(R.id.appwidget_text, ingredients);
        views.setTextViewText(R.id.title_rec, title);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        Bundle extras = intent.getExtras();
       titleofrecipie = extras.getString("name");
       idofitem = extras.getString("id");

        if (action != null && action.equals(UPDATE_ACTION)) {
               // updateAppWidget(context, appWidgetManager, id , titleofrecipie);
                ShowItemsNames(context, idofitem);
        } else { super.onReceive(context, intent); }
    }

    private void ShowItemsNames(final Context context, final String idtocheck) {

        String url2 = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        RequestQueue Video_Queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i =0; i<20; i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String itemid = jsonObject1.getString("id");
                        if (itemid.equals(idtocheck) ){
                            JSONArray JSONARRAY = jsonObject1.getJSONArray("ingredients");
                            for(int k =0; k<20; k++){
                                JSONObject stepsobject = JSONARRAY.getJSONObject(k);
                                Ingredients_Model VL = new Ingredients_Model(stepsobject.getString("quantity"),stepsobject.getString("measure"), stepsobject.getString("ingredient"));
                                VL.setQuantity(stepsobject.getString("quantity"));
                                VL.setMeasure(stepsobject.getString("measure"));
                                VL.setIngredients(stepsobject.getString("ingredient"));
                                ingredients_items.add("-" + VL.getQuantity() + VL.getMeasure() + " " + VL.getIngredients());
                                final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                                ComponentName name = new ComponentName(context, BakingWidgetScreen.class);
                                int[] appWidgetId = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
                                final int N = appWidgetId.length;
                                if (N < 1)
                                {
                                    return ;
                                }
                                else {
                                    int id = appWidgetId[N-1];
                                    StringBuffer text = new StringBuffer();
                                    for (String element: ingredients_items) {
                                        text.append(element).append('\n');
                                    }
                                    updateAppWidget(context, appWidgetManager, id , text.toString(), titleofrecipie);
                                }
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
            }
        });
        Video_Queue.add(stringRequest);
    }
}

