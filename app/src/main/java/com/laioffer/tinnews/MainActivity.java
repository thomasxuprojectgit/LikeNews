package com.laioffer.tinnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
//        use getSupportFragmentManager() to navigate, manage all screen
//        when app is created, system will create the manager
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

//        use for control navHostFragment (auto new)
        navController = navHostFragment.getNavController();

//        connect three button to navHostFragment
        NavigationUI.setupWithNavController(navView, navController);

        NavigationUI.setupActionBarWithNavController(this, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
//         use back to home
        return navController.navigateUp();
        }

}