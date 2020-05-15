package com.example.gestiondenotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import java.io.File;
import jxl.Sheet;
import jxl.Workbook;

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
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EtudiantAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment1 fragmentActivity1 ;
    private Fragment2 fragmentActivity2 ;
    private  Fragment3 fragmentActivity3 ;
    private Fragment4 fragmentActivity4 ;
    private Fragment5 fragmentActivity5 ;
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
    protected void onStart() {
        super.onStart();
        key_g = getIntent().getExtras().getString("ID");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        }
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



        fragmentActivity1 = new Fragment1();

        fragmentActivity2 = new Fragment2() ;

        fragmentActivity3 = new Fragment3() ;

        fragmentActivity4 = new Fragment4();
       fragmentActivity5 = new Fragment5();
        tabLayout.setupWithViewPager(viewPager);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(getSupportFragmentManager(),0) ;

        viewpageradapter.Addfragment(fragmentActivity2,"Liste des etudiants");
        viewpageradapter.Addfragment(fragmentActivity4,"Ajouter La participation");

        viewpageradapter.Addfragment(fragmentActivity3,"Marquer les Absence");
        viewpageradapter.Addfragment(fragmentActivity1,"Ajouter un etudiant");

        viewpageradapter.Addfragment(fragmentActivity5,"Statistiques");









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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.import_: {
                new MaterialFilePicker()
                        .withActivity(EtudiantAct.this)
                        .withFilter(Pattern.compile(".*\\.xls$"))
                        .withRequestCode(1)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
//                Intent i = new Intent (EtudiantAct.this,ImportActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                startActivityForResult(intent , 0 );
            }
//                Intent i = new Intent(EtudiantAct.this,ImportActivity.class);
//                startActivity(i);
//                finish();
        }
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String filePath = "" ;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            Toast.makeText(this, "" + filePath, Toast.LENGTH_LONG).show();
        }
        int row = 1000;
        int col = 1000;
        ArrayList<Etudiant> etu =  new ArrayList<Etudiant>();
        try {
            //AssetManager ar=getAssets();
            File file = new File(filePath);
            Workbook wb = Workbook.getWorkbook(file);
            Sheet s = wb.getSheet(0);
            row = s.getRows();
            col = s.getColumns();
            ArrayList<Etudiant>  e_a= new ArrayList<Etudiant>();
            String nom = "";
            String prenom = "" ;
            String NI = "";
            String email = "" ;
            for (int i = 0 ; i < row; i++) {
                for (int j = 0 ; j  < col ; j++) {
                    if (j == 0 ) {
                        NI = s.getCell(j,i).getContents();
                    }else if (j==1) {
                        nom = s.getCell(j,i).getContents();
                    }else if (j == 2) {
                        prenom = s.getCell(j,i).getContents();
                    } else if (j == 3) {
                        email = s.getCell(j,i).getContents();
                    }
                }
                e_a.add(new Etudiant(nom,prenom,NI,"0",email,"0","0",Fragment1.PHOTO_DEFAULT,"0"));
            }
            wb.close();
            for (Etudiant e : e_a) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                        child(Group_act.id_module).child("Groupes").child(EtudiantAct.key_g).
                        child("Etudiants").child(e.getNI()).setValue(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EtudiantAct.this,e.getNom() + " "+  e.getPrenom() + " :  ajouté avec succès",Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (Exception e) {
            Toast.makeText(EtudiantAct.this,e.getMessage(),Toast.LENGTH_LONG).show(); } }
}