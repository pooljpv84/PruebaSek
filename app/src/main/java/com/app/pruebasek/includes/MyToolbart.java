package com.app.pruebasek.includes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app.pruebasek.R;


public class MyToolbart
{
public static void show(AppCompatActivity activity, String title, boolean upButton)
{
    Toolbar toolbart = activity.findViewById(R.id.toolbart);
    activity.setSupportActionBar(toolbart);
    activity.getSupportActionBar().setTitle(title);
    //para el regreso
    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

}
}
