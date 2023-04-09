package com.example.parking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class PollActivity extends AppCompatActivity {
    public TextView winner;
    public LinearLayout shylu, tharun, thiru, siva;
    public TextView count1, count2, count3, count4, tv;
    public int clickCount1, clickCount2, clickCount3, clickCount4;
    public Button holdMe;
    public SharedPreferences sharedPreferences;
    String[] index = {"Shylander", "Tharun", "Thirumalai", "Sivanmani"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        // initialize views
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Vote your Fav");
        shylu = findViewById(R.id.layout1);
        tharun = findViewById(R.id.layout2);
        thiru = findViewById(R.id.layout3);
        siva = findViewById(R.id.layout4);
        winner = findViewById(R.id.winner);
        tv = findViewById(R.id.textView);

        count1 = findViewById(R.id.shylander_count);
        count2 = findViewById(R.id.tharun_count);
        count3 = findViewById(R.id.thiru_count);
        count4 = findViewById(R.id.sivanmani_count);

        holdMe = findViewById(R.id.holdme);
        registerForContextMenu(holdMe);

//        layout = findViewById(R.id.layout100);

        // set click listeners
        shylu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount1++;
                count1.setText(String.valueOf(clickCount1));
                sharedPreferences.edit().putInt("count1", clickCount1).apply();
                largest(clickCount1, clickCount2, clickCount3, clickCount4);
            }
        });
        tharun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount2++;
                count2.setText(String.valueOf(clickCount2));
                sharedPreferences.edit().putInt("count2", clickCount2).apply();
                largest(clickCount1, clickCount2, clickCount3, clickCount4);
            }
        });
        thiru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount3++;
                count3.setText(String.valueOf(clickCount3));
                sharedPreferences.edit().putInt("count3", clickCount3).apply();
                largest(clickCount1, clickCount2, clickCount3, clickCount4);
            }
        });
        siva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount4++;
                count4.setText(String.valueOf(clickCount4));
                sharedPreferences.edit().putInt("count4", clickCount4).apply();
                largest(clickCount1, clickCount2, clickCount3, clickCount4);
            }
        });

        sharedPreferences = getSharedPreferences("image_counts", MODE_PRIVATE);

        // retrieve saved counts from SharedPreferences
        clickCount1 = sharedPreferences.getInt("count1", 0);
        clickCount2 = sharedPreferences.getInt("count2", 0);
        clickCount3 = sharedPreferences.getInt("count3", 0);
        clickCount4 = sharedPreferences.getInt("count4", 0);

        // update counts on TextViews
        count1.setText(String.valueOf(clickCount1));
        count2.setText(String.valueOf(clickCount2));
        count3.setText(String.valueOf(clickCount3));
        count4.setText(String.valueOf(clickCount4));

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(PollActivity.this, tv);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.second, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        Toast.makeText(PollActivity.this, "You Clicked " + menuItem.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

    }

    public void largest(int a, int b, int c, int d) {
        int high = Math.max(Math.max(Math.max(a, b), c), d);
        if (high == a) {
            winner.setText("Winner : Shylander");
            System.out.println("Shylander");
        } else if (high == b) {
            winner.setText("Winner : Tharun");

        } else if (high == c) {
            winner.setText("Winner : Thirumalai");

        } else if (high == d) {
            winner.setText("Winner : Sivanmani");
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // you can set menu header with title icon etc
        menu.setHeaderTitle("Reset Poll?");
        // add menu items
        menu.add(0, v.getId(), 0, "Reset");
    }

    // menu item select listener
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int c1 = clickCount1;
        int c2 = clickCount2;
        int c3 = clickCount3;
        int c4 = clickCount4;
        if (item.getTitle() == "Reset") {
            clickCount1 = 0;
            clickCount2 = 0;
            clickCount3 = 0;
            clickCount4 = 0;
            // update counts on TextViews
            count1.setText(String.valueOf(clickCount1));
            count2.setText(String.valueOf(clickCount2));
            count3.setText(String.valueOf(clickCount3));
            count4.setText(String.valueOf(clickCount4));
            winner.setText("Winner : ");
//            View view = findViewById(android.R.id.content);
//
//            Snackbar snackbar = Snackbar
//                    .make(view ,"Poll Restored",Snackbar.LENGTH_LONG)
//                    .setAction(
//                            "UNDO",
//                            new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view)
//                                {
//                                    clickCount1 = c1;
//                                    clickCount2 = c2;
//                                    clickCount3 = c3;
//                                    clickCount4 = c4;
//                                    Toast
//                                            .makeText(
//                                                    PollActivity.this,
//                                                    "Poll Undone",
//                                                    Toast.LENGTH_SHORT)
//                                            .show();
//                                }
//                            });
//
//            snackbar.show();
        }
        return true;
    }
}