package com.salam.bakingapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.salam.bakingapp.Models.RecipieDetailModel;
import com.salam.bakingapp.R;
import com.salam.bakingapp.RecipeInstructions;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.RecyclerViewHolder> {

    private List<RecipieDetailModel> steps_list;
    private final Context mContext;
    boolean isPhone;
    private int selectedPosition=-1;

    public StepsAdapter(Context mContext, List<RecipieDetailModel> ItemsList) {
        this.steps_list = ItemsList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {


        final RecipieDetailModel itemsList = steps_list.get(position);
        holder.step_description.setText(itemsList.getShortdescription());
        if(selectedPosition==position)
            holder.itemView.setBackgroundColor(Color.parseColor( "#00BCD4"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#DAF3DB"));

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                isPhone = mContext.getResources().getBoolean(R.bool.is_phone);
                if (!isPhone) { //it's a tablet
                    selectedPosition=position;
                    notifyDataSetChanged();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new RecipeInstructions();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container2, myFragment).addToBackStack(null).commit();
                    Bundle args = new Bundle();
                    args.putString("id", itemsList.getId());
                    args.putString("itemid", itemsList.getItemID());
                    myFragment.setArguments(args);
                } else { //it's a phone, not a tablet
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new RecipeInstructions();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, myFragment).addToBackStack(null).commit();
                    Bundle args = new Bundle();
                    args.putString("id", itemsList.getId());
                    args.putString("itemid", itemsList.getItemID());
                    myFragment.setArguments(args);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return steps_list.size();
    }

    public void addItems(List<RecipieDetailModel> ItemsList) {
        this.steps_list = ItemsList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        final TextView step_description;


        RecyclerViewHolder(View view) {
            super(view);
            step_description =  view.findViewById(R.id.steps_description);
        }
    }


}
