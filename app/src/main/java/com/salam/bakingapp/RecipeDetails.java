package com.salam.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class RecipeDetails extends AppCompatActivity {
    String id;
    boolean isPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        // getting item ID to check for the related steps
        Intent getExtras = getIntent();
        id = getExtras.getStringExtra("id");
        String Name = getExtras.getStringExtra("name");
        getSupportActionBar().setTitle(Name);

        //adding fragments for tablet and one for the phone
        isPhone = getResources().getBoolean(R.bool.is_phone);
        if (!isPhone) { //it's a tablet
            addFragment(new Detailsfrag(),false,"one");
        } else { //it's a phone, not a tablet
            addFragment(new Detailsfrag(),false,"one");
        }

    }

    public void addFragment(Detailsfrag fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.frame_container, fragment, tag);
        ft.commitAllowingStateLoss();
        Bundle bundle = new Bundle();
        bundle.putString("newid", id);
        fragment.setArguments(bundle);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isPhone) { //it's a tablet
            finish();
        } else { //it's a phone, not a tablet
            addFragment(new Detailsfrag(),false,"one");
        }

    }
}
