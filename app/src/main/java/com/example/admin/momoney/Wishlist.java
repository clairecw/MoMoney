package com.example.admin.momoney;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Wishlist extends ActionBarActivity implements View.OnClickListener {

    Button button1, add;
    EditText query, price, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        button1 = (Button)findViewById(R.id.search);
        query = (EditText)findViewById(R.id.item);
        price = (EditText)findViewById(R.id.price);
        add = (Button)findViewById(R.id.add);
        time = (EditText)findViewById(R.id.time);

        button1.setOnClickListener(this);
        add.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wishlist, menu);
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

    public void onClick(View v) {
        if (v == button1) {
            Uri uri = Uri.parse("https://www.google.com/#q=" + query.getText().toString() + "&tbm=shop");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        if (v == add) {
            Intent intent = new Intent();
            intent.putExtra("wish item", query.getText().toString() + "  " + price.getText().toString()
                            + "  " + time.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}
