package com.amar.itay.takego.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.itay.takego.R;
import com.amar.itay.takego.model.datasource.MySQL_DBManager;

public class MainActivity_Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    /**
     * @param savedInstanceState contains the most recent data, specially contains
     * data of the activity's previous initialization part.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);// here we find the toolbar view.
        setSupportActionBar(toolbar);//we set the activity to support the toolbar.

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
           //             .setAction("Action", null).show();
                // if there is no invitation open so change the fragment, otherwise make a toast to say u already ordered a car.
                if(MySQL_DBManager.currentInvitation == null){
                    Fragment fragment = new newInvitationFragment();// not suporrt.v4 //create new fragment.
                    FragmentManager fragmentManager = getFragmentManager();//gets the fragment manager to begin the transaction.
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.FragLinearLayout, fragment);//replace the current fragment.
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();//commit the fragment to be display otherwise it won't work.
                }
                else
                {
                    Toast.makeText(MainActivity_Drawer.this.getBaseContext(),"u already order a car",Toast.LENGTH_LONG).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);//we getting the drawLayout view.
        //this meant to be the event of clicking the 3 dotes to open and close.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);//sets the drawer listener to listen the event of opening and closing.
        toggle.syncState();// sync the state to work.

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);//we getting the navigationView view.
        //listen to events by item one of the items in the left side when the navigation drawer open.
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();//create fragment manger.
        //gets the fragment manager and begin the transaction.
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new startFragment();//create the start fragment.
        fragmentTransaction.add(R.id.FragLinearLayout, fragment);//add the new fragment.
        fragmentTransaction.addToBackStack("home");//on click back return to "home'.
        fragmentTransaction.commit();//commit the fragment to be display.

    }

    /**
     * check the DrawerLayout open or not.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * mean to the right options side with the three points up down.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity__drawer, menu);
        return false;
    }

    /**
     *  to the right options with the three points up down.
     * @param item that has been selected.
     * @return true/false 1)can be by the father that we override from him
     *                    2)we can decide if occurred one of our oitem to return true.
     */
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

    /**
     *
     * @param title to set the action bar title.
     */
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    /**
     * mean to the left side options with the three lines "in a row".
     * @param item of the left side of the screen when the navigation opens.
     * @return true so it will work.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_add_invitation) {
             fragment = new newInvitationFragment();// not suporrt.v4

        } else if (id == R.id.nav_add_invitation_2) {
            fragment = new newInvitationFragment_OptionTwo();

        } else if (id == R.id.nav_Cars) {
            fragment = new CarsFragment();

        } else if (id == R.id.nav_Branches) {
            fragment = new branchFragment();

        } else if (id == R.id.open_invitation) {

        } else if (id == R.id.nav_send) {
               fragment = new InfoFragment();
        }
        if(fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.FragLinearLayout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
