package com.salam.bakingapp.Adapters;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salam.bakingapp.Models.Ingredients_Model;
import com.salam.bakingapp.Models.ItemNames;
import com.salam.bakingapp.R;
import com.salam.bakingapp.RecipeDetails;

import java.util.List;

public class Ingredients_Adapter extends RecyclerView.Adapter<Ingredients_Adapter.RecyclerViewHolder> {

    private List<Ingredients_Model> ItemsList;
    private final Activity activity;

    public Ingredients_Adapter(Activity activity, List<Ingredients_Model> ItemsList) {
        this.ItemsList = ItemsList;
        this.activity = activity;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final Ingredients_Model itemsList = ItemsList.get(position);
        holder.title.setText(itemsList.getQuantity()+ " " +  itemsList.getMeasure() + " "+ itemsList.getIngredients());

    }

    @Override
    public int getItemCount() {
        return ItemsList.size();
    }

    public void addItems(List<Ingredients_Model> ItemsList) {
        this.ItemsList = ItemsList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        final TextView title;


        RecyclerViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.ingredients_item_n);
        }
    }
}
