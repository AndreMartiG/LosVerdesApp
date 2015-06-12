package com.andremartinez.losverdes;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class LosVerdesCl extends Activity {

    static private DataBaseManager manager;

    static public DataBaseManager getManager(){    return manager;    }

    public static void setManager(DataBaseManager manager) {
        LosVerdesCl.manager = manager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_los_verdes_cl);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DbHelper helper = new DbHelper(this);
        Inicio fragment = new Inicio();

        manager = new DataBaseManager(this);

        manager.insertar("UdeA","6.262708", "-75.568370");
        manager.insertar("Robledo-Pilarica","6.273887", "-75.579601");
        manager.insertar("Bulerias","6.239013", "-75.589667");

        SQLiteDatabase db = helper.getWritableDatabase();
        fragmentTransaction.replace(android.R.id.content, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_los_verdes_cl, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.act_inicio) {
            Inicio fragment = new Inicio();
            fragmentTransaction.replace(android.R.id.content, fragment).commit();
        }

        if (id == R.id.act_resto) {
            ActionsFragment fragment = new ActionsFragment();
            fragmentTransaction.replace(android.R.id.content, fragment).commit();
        }

        if (id == R.id.act_maps) {
            Intent i = new Intent(this, MapLosVerdes.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
