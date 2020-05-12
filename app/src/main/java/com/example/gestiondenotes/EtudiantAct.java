package com.example.gestiondenotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar ;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class EtudiantAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment1 fragmentActivity1 ;
    private Fragment2 fragmentActivity2 ;
    private  Fragment3 fragmentActivity3 ;
    private Fragment4 fragmentActivity4 ;
    private Toolbar toolbar ;
    private ViewPager viewPager ;
    private TabLayout tabLayout ;
    public static String key_g ;
    DrawerLayout drawer ;

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ex_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.import_: {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent , 0 );

            }


//                Intent i = new Intent(EtudiantAct.this,ImportActivity.class);
//                startActivity(i);
//                finish();
            }
        return true;
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            String path  = data.getData().getPath();
            Toast.makeText(EtudiantAct.this,path,Toast.LENGTH_LONG).show();

        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);

        String gr = getIntent().getExtras().getString("nom");
        toolbar = findViewById(R.id.toolbar_etu);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Étudiants");
        toolbar.setSubtitle("Groupes : " + gr);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_etu);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        viewPager = findViewById(R.id.view_pager) ;

        tabLayout = findViewById(R.id.tab_layout);

        key_g = getIntent().getExtras().getString("ID");

        fragmentActivity1 = new Fragment1();

        fragmentActivity2 = new Fragment2() ;

        fragmentActivity3 = new Fragment3() ;

        fragmentActivity4 = new Fragment4();

        tabLayout.setupWithViewPager(viewPager);
        NavigationView navigationView = findViewById(R.id.nav_view);
      navigationView.setNavigationItemSelectedListener(this);


        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(getSupportFragmentManager(),0) ;

        viewpageradapter.Addfragment(fragmentActivity2,"Liste des etudiants");

        viewpageradapter.Addfragment(fragmentActivity1,"Ajouter un etudiant");

        viewpageradapter.Addfragment(fragmentActivity3,"Marquer les Absence");

        viewpageradapter.Addfragment(fragmentActivity4,"Ajouter La participation");


        viewPager.setAdapter(viewpageradapter);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_ac:
              onBackPressed();
               finish();

                break;
            case R.id.deconnecter :
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
            default:
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragment = new ArrayList<>();

        private List<String> FragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public int getCount() {
            return fragment.size();
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragment.get(position);
        }
        public void Addfragment(Fragment fra, String title) {
            fragment.add(fra);
            FragmentTitle.add(title);
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return FragmentTitle.get(position);
        }
    }
}
