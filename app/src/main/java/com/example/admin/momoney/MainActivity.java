package com.example.admin.momoney;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.*;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {
    ArrayList<Due> dues;
    EditText editText;
    Button button, button2;
    ListView listView;
    String[] values;
    TextView label;
    double balance;
    double rentexp, foodexp, monthlyexp;
    ArrayAdapter<String> adapter;

    DecimalFormat df = new DecimalFormat("#.00");
    boolean DEBUGGING = false;

    public MainActivity() {
    }

    public final static String EXTRA_MESSAGE = "com.example.admin.momoney.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dues = new ArrayList<Due>();
        balance = 0;

        loadSavedPreferences();

        if (DEBUGGING) {
            dues.add(new Due(50, "ass"));
            dues.add(new Due(60, "nose"));
            dues.add(new Due(2, "candy"));
        }

        updateList();

        editText = (EditText) findViewById(R.id.editText1);
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
               Intent intent = new Intent(v.getContext(), SetBudget.class);
               startActivityForResult(intent, 1);
            }
        });
        listView = (ListView) findViewById(R.id.list);
        label = (TextView) findViewById(R.id.textView1);

        label.setText("Current balance: $" + df.format(balance));

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_SHORT)
                        .show();

            }

        });




        //loadSavedPreferences();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String str = "";
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                str = data.getStringExtra("expenses");
            }


        }
        int n = str.indexOf(" ");
        rentexp = Double.parseDouble(str.substring(0, n));
        foodexp = Double.parseDouble(str.substring(n + 1));

        savePreferences("expenses", str);
    }

    private void updateList() {
        if (dues.isEmpty()) {
            values = new String[1];
            values[0] = "no items";
        }
        else {
            values = new String[dues.size()];
            for (int i = 0; i < dues.size(); i++) {
                values[i] = fancyParse(dues.get(i));
                balance += dues.get(i).getAmt();
            }
        }

    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        //String name = sharedPreferences.getString("storedDues", "YourName");


        int size = sharedPreferences.getInt("Status_size", 0);

        String name = sharedPreferences.getString("Status_" + 0, null);


        for(int i = 0; i < size; i++) {
            dues.add(unparse(sharedPreferences.getString("Status_" + i, null)));
        }
    }

    private void loadSavedPreferences(String key) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        String s = sharedPreferences.getString(key, null);
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private void savePreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private void savePreferences() {
        dues.add(unparse(editText.getText().toString()));

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Status_size", dues.size());

        for(int i = 0; i < dues.size(); i++) {
            editor.remove("Status_" + i);
            editor.putString("Status_" + i, parse(dues.get(i)));
        }

        editor.commit();
    }

    private String fancyParse(Due d) {
        String s = "";
        if (d.getAmt() < 0) s += "-";
        s += "$" + df.format(Math.abs(d.getAmt())) + " " + d.getDesc();
        return s;
    }

    private String parse(Due d) {
        String s = "";
        s += d.getAmt() + " " + d.getDesc();
        return s;
    }

    private Due unparse(String s) {
        int ind = s.indexOf(" ");
        double n = Double.parseDouble(s.substring(0, ind));
        String d = s.substring(ind);
        return new Due(n, d);
    }

    private void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        //savePreferences("storedDues", editText.getText().toString() + " $5");

        savePreferences();
        updateList();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        adapter.notifyDataSetChanged();

    }

    /** Called when the user clicks the Send button *//*
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}

class Due {
    private double amount;
    private String desc;

    public Due(double a, String d) {
        amount = a;
        desc = d;
    }

    public double getAmt() {
        return amount;
    }

    public String getDesc() {
        return desc;
    }
}