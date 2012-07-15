package com.ringaringagames.mathcandy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        findViewById(R.id.splashPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        HomeActivity.this);

                alertDialogBuilder.setTitle("Welcome to Math Candy");
                alertDialogBuilder
                        .setMessage("In this level, you play the role of a shop keeper. Please calculate customer's bill amount, collect money from the customer and return him the change.")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intentToGo = new Intent();
                                intentToGo.setClass(HomeActivity.this, MainActivity.class);
                                startActivity(intentToGo);
                                finish();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
    }
}
