package com.example.gestiondenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Editgroupe extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextInputEditText nom_groupe , niveau_groupe ;
    String nom , niveau, id_groupe ;
    Button edit_groupe ;
    DatabaseReference db_ref ;
    Toolbar toolbar ;
    DrawerLayout drawer ;
    String ID_MODULE ;
    String test1 ;
    String test2 ;
    String participation ;
    String absence ;
    String nom_module ;
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_ac:
                Intent i = new Intent (Editgroupe.this,MainActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.deconnecter :
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                if (mAuth.getCurrentUser() == null) {
                    Intent j =  new Intent (Editgroupe.this,Login.class);
                    startActivity(j);
                    finish();
                }
            default:
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editgroupe);
        test1 = getIntent().getExtras().getString("test1");
        test2 = getIntent().getExtras().getString("test2");
        participation = getIntent().getExtras().getString("participation");
        absence =  getIntent().getExtras().getString("absence") ;
        nom_module = getIntent().getExtras().getString("nomM");
        ID_MODULE = getIntent().getExtras().getString("ID_M");
        toolbar = findViewById(R.id.toolbar_edit_groupe);
        nom =getIntent().getExtras().getString("nom");
        toolbar.setTitle("Modifier le Groupes");
        toolbar.setSubtitle("Groupe : " + nom);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_edit_groupe);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nom_groupe = findViewById(R.id.nom_du_groupe_edit);
        niveau_groupe =findViewById(R.id.niveau_du_groupe_edit);
        niveau = getIntent().getExtras().getString("niveau");
        id_groupe = getIntent().getExtras().getString("id");
        nom_groupe.setText(nom);
        niveau_groupe.setText(niveau);
        edit_groupe = findViewById(R.id.edit_groupe);
        edit_groupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nom = nom_groupe.getText().toString();
                niveau = niveau_groupe.getText().toString();
                if (nom.isEmpty() || niveau.isEmpty()) {
                    Toast.makeText(Editgroupe.this,"Vous devez remplir ce champ",Toast.LENGTH_SHORT).show();
                } else {
                    Groupes gr = new Groupes(nom,niveau);
                    gr.setId(id_groupe);
                    db_ref = FirebaseDatabase.getInstance().getReference();
                    db_ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child
                            (Group_act.id_module).child("Groupes").child(id_groupe).child("niveau").setValue(niveau).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                                    "niveau modifier avec succee", Snackbar.LENGTH_LONG);
                            s.setDuration(10000);
                            s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                            s.setBackgroundTint(getResources().getColor(R.color.colorAccent));
                            s.setTextColor(getResources().getColor(R.color.colorPrimary));
                            s.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                            s.setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    s.dismiss();
                                }
                            });
                            s.show();
                        }else {
                            final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                                    task.getException().getMessage(), Snackbar.LENGTH_LONG);
                            s.setDuration(10000);
                            s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                            s.setBackgroundTint(getResources().getColor(R.color.colorAccent));
                            s.setTextColor(getResources().getColor(R.color.colorPrimary));
                            s.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                            s.setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    s.dismiss();
                                }
                            });} }
                    });
                    db_ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child
                            (Group_act.id_module).child("Groupes").child(id_groupe).child("nom").setValue(nom).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                                        "nom modifier avec succees", Snackbar.LENGTH_LONG);
                                s.setDuration(10000);
                                s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                                s.setBackgroundTint(getResources().getColor(R.color.colorAccent));
                                s.setTextColor(getResources().getColor(R.color.colorPrimary));
                                s.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                                s.setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        s.dismiss();
                                    }
                                });
                                s.show();
                            }else {
                                final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                                        task.getException().getMessage(), Snackbar.LENGTH_LONG);
                                s.setDuration(10000);
                                s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                                s.setBackgroundTint(getResources().getColor(R.color.colorAccent));
                                s.setTextColor(getResources().getColor(R.color.colorPrimary));
                                s.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                                s.setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        s.dismiss();
                                    }
                                });} }
                    });
                }

            }
        });


    }



    @Override
    public void onBackPressed() {
        Intent i = new Intent(Editgroupe.this,Group_act.class);
        i.putExtra("id",ID_MODULE);
        i.putExtra("test1",test1);
        i.putExtra("test2",test2);
        i.putExtra("participation",participation);
        i.putExtra("absence",absence);
        i.putExtra("nomM",nom_module);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
