package com.example.admin.momoney;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SetBudget extends ActionBarActivity {

    EditText foodexp, rentexp, income;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_budget);

        button1 = (Button)(findViewById(R.id.button1));
        foodexp = (EditText)(findViewById(R.id.foodexp));
        rentexp = (EditText)(findViewById(R.id.rentexp));
        income = (EditText)(findViewById(R.id.income));

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult("" + (Double.parseDouble(foodexp.getText().toString()) +
                        Double.parseDouble(rentexp.getText().toString())) + " " + income.getText().toString());
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_budget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setResult(String result) {
        Intent intent = new Intent();
        intent.putExtra("result", result);
        setResult(RESULT_OK, intent);
        finish();
    }
}
