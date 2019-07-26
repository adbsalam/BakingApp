package com.salam.bakingapp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salam.bakingapp.BakingWidgetScreen;
import com.salam.bakingapp.Models.ItemNames;
import com.salam.bakingapp.R;
import com.salam.bakingapp.RecipeDetails;

import java.util.List;

public class ItemNamesAdapter extends RecyclerView.Adapter<ItemNamesAdapter.RecyclerViewHolder> {

    private List<ItemNames> ItemsList;
    private final Activity activity;

    public ItemNamesAdapter(Activity activity, List<ItemNames> ItemsList) {
        this.ItemsList = ItemsList;
        this.activity = activity;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final ItemNames itemsList = ItemsList.get(position);
        holder.title.setText(itemsList.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent newintent = new Intent(activity, RecipeDetails.class);
                newintent.putExtra("id", itemsList.getId());
                newintent.putExtra("name", itemsList.getName());
                Intent i = new Intent(activity, BakingWidgetScreen.class);
                i.setAction(BakingWidgetScreen.UPDATE_ACTION);
                i.putExtra("name",itemsList.getName());
                i.putExtra("id",itemsList.getId());
                activity.sendBroadcast(i);
                activity.startActivity(newintent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemsList.size();
    }

    public void addItems(List<ItemNames> ItemsList) {
        this.ItemsList = ItemsList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        final TextView title;


        RecyclerViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.recipe_name);
        }
    }
}

