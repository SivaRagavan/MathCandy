package com.ringaringagames.mathcandy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ringaringagames.mathcandy.models.Product;
import com.ringaringagames.mathcandy.models.Products;

import java.util.HashSet;
import java.util.Random;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    CountDownTimer timer;
    int cashSelected = 0;

    int denominations[] = {1, 2, 5, 10, 20, 50, 100};
    int denominationsCount = denominations.length;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        for (int i = 0; i < denominationsCount; i++) {
            final int denom = denominations[i];
            findViewById(this.getResources().getIdentifier("@id/a" + denom + "_button", null, this.getPackageName())).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textToChange = ((TextView) findViewById(MainActivity.this.getResources().getIdentifier("@id/a" + denom + "_text", null, MainActivity.this.getPackageName())));
                    textToChange.setText((Integer.parseInt(textToChange.getText().toString()) + 1) + "");
                    cashSelected += denom;
                    if (Integer.parseInt(textToChange.getText().toString()) > 0) {
                        findViewById(MainActivity.this.getResources().getIdentifier("@id/i" + denom, null, MainActivity.this.getPackageName())).setVisibility(View.VISIBLE);
                        textToChange.setVisibility(View.VISIBLE);
                    } else {
                        findViewById(MainActivity.this.getResources().getIdentifier("@id/i" + denom, null, MainActivity.this.getPackageName())).setVisibility(View.INVISIBLE);
                        textToChange.setVisibility(View.INVISIBLE);
                    }
                }
            });

            findViewById(this.getResources().getIdentifier("@id/i" + denom, null, this.getPackageName())).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textToChange = ((TextView) findViewById(MainActivity.this.getResources().getIdentifier("@id/a" + denom + "_text", null, MainActivity.this.getPackageName())));
                    textToChange.setText((Integer.parseInt(textToChange.getText().toString()) - 1) + "");
                    cashSelected -= denom;
                    if (Integer.parseInt(textToChange.getText().toString()) > 0) {
                        findViewById(MainActivity.this.getResources().getIdentifier("@id/i" + denom, null, MainActivity.this.getPackageName())).setVisibility(View.VISIBLE);
                        textToChange.setVisibility(View.VISIBLE);
                    } else {
                        findViewById(MainActivity.this.getResources().getIdentifier("@id/i" + denom, null, MainActivity.this.getPackageName())).setVisibility(View.INVISIBLE);
                        textToChange.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }

        timer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                ((TextView) findViewById(R.id.timerText)).setText(millisUntilFinished / 1000 + " Seconds left...");
            }

            public void onFinish() {
                ((TextView) findViewById(R.id.timerText)).setText("Timeout !!!");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                MainActivity.this);

                        alertDialogBuilder.setTitle("Timeout");
                        alertDialogBuilder
                                .setMessage("Please try again...")
                                .setCancelable(false)
                                .setPositiveButton("Next Customer", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        restart();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });

            }
        };

        startPlay();

    }

    public void startPlay() {

        timer.start();

        HashSet<Product> products = Products.getRandomProducts(3);
        int count = 0;
        String productNames = "";
        int totalCost = 0;
        for (Product p : products) {
            count++;


            int id = this.getResources().getIdentifier("@drawable/" + p.imageName, null, this.getPackageName());
            ImageView productImage = (ImageView) findViewById(this.getResources().getIdentifier("@id/imageView" + count, null, this.getPackageName()));
            productImage.setImageResource(id);

            Random randomGenerator = new Random();
            int prodCount = randomGenerator.nextInt(8) + 1;

            ((TextView) findViewById(this.getResources().getIdentifier("@id/p" + count + "_text", null, this.getPackageName()))).setText("x" + prodCount + "");

            productNames = productNames + " " + prodCount + " " + p.imageName + ",";
            totalCost = totalCost + (prodCount * p.price);
        }

        int custCash = 2000;
        if (totalCost < 900) custCash = 1000;
        if (totalCost < 400) custCash = 500;
        if (totalCost < 50) custCash = 100;

        final int cashToReturn = custCash - totalCost;
        Log.e("MainActivity", "Return Amount : " + cashToReturn);

        productNames = productNames.substring(0, productNames.length() - 1);

        AlertDialog.Builder custSpeech = new AlertDialog.Builder(
                MainActivity.this);
        custSpeech.setTitle("Customer :");
        final int finalCustCash = custCash;
        custSpeech
                .setMessage("I would like to buy " + productNames + ".\nHere is the cash : Rs." + custCash)
                .setCancelable(false)
                .setPositiveButton("Collect Cash", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((TextView) findViewById(R.id.collected_text)).setText("Collected Cash : " + finalCustCash);
                    }
                });
        AlertDialog custSpeechDialog = custSpeech.create();
        custSpeechDialog.show();

        findViewById(R.id.action_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder custSpeech = new AlertDialog.Builder(
                        MainActivity.this);

                custSpeech.setTitle("Customer :");

                custSpeech.setCancelable(false)
                        .setPositiveButton("Next Customer", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                restart();
                            }
                        });

                if (cashSelected == 0) {
                    custSpeech.setMessage("Please select proper change.");
                    custSpeech.setPositiveButton("Next Customer", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Do nothing.
                        }
                    });

                } else if (cashToReturn == cashSelected) {
                    custSpeech.setMessage("Good work. Thanks for the correct change. Rs." + cashToReturn);
                } else {
                    custSpeech.setMessage("Oops.. You returned the wrong change. I was supposed to receive Rs." + cashToReturn + ". Keep Practicing.");
                }


                Log.e("MainActivity", "Return Amount : " + cashToReturn);

                AlertDialog custSpeechDialog = custSpeech.create();
                custSpeechDialog.show();
            }
        });
    }

    public void restart() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }


    public void onDestroy() {
        timer.cancel();
        timer = null;
        super.onDestroy();
    }
}

