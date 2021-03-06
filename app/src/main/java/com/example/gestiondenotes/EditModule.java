package com.example.gestiondenotes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class EditModule extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer ;
    Toolbar toolbar ;
    DatabaseReference ref;
    TextInputEditText nom_module_edit_text, coef_du_module_edit_text, note_eliminatoire_du_module_edit_text;
    String get_nom, get_coeff, get_note_elim, id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_module);
        toolbar = findViewById(R.id.toolbar_edit_module);
        get_nom = getIntent().getExtras().getString("nom");
        toolbar.setTitle("Modifier le module");
        toolbar.setSubtitle("Module : " + get_nom);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_edit_module);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ref = FirebaseDatabase.getInstance().getReference();
        get_coeff = getIntent().getExtras().getString("coeff");
        get_note_elim = getIntent().getExtras().getString("note eliminatoire");
        id = getIntent().getExtras().getString("id");
        nom_module_edit_text = findViewById(R.id.nom_du_module_edit);
        coef_du_module_edit_text = findViewById(R.id.coefficient_edit);
        note_eliminatoire_du_module_edit_text = findViewById(R.id.note_eliminatoire_edit);
        nom_module_edit_text.setText(get_nom);
        note_eliminatoire_du_module_edit_text.setText(get_note_elim);
        coef_du_module_edit_text.setText(get_coeff);
    }

    public void Update(View view) {
        String new_nom, new_coef, new_note ;
        new_nom = nom_module_edit_text.getText().toString();
        new_coef = coef_du_module_edit_text.getText().toString();
        new_note = note_eliminatoire_du_module_edit_text.getText().toString();
        if (new_nom.equals(get_nom) && new_coef.equals(get_coeff) && new_note.equals(get_note_elim)) {
            final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
              "Vous devez effectuez un changement", Snackbar.LENGTH_LONG);
            s.setDuration(10000);
            s.setActionTextColor(getResources().getColor(R.color.colorAccent));
            s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            s.setBackgroundTint(getResources().getColor(R.color.colorPrimary));
            s.setTextColor(getResources().getColor(R.color.colorPrimary));
            s.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    s.dismiss();

                }});
            s.show();
            return;
        }

        if (new_nom.isEmpty() || new_coef.isEmpty() || new_note.isEmpty()) {
            final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                    "Vous devez remplir les champs", Snackbar.LENGTH_LONG);
            s.setDuration(10000);
            s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            s.setBackgroundTint(getResources().getColor(R.color.colorAccent));
            s.setTextColor(getResources().getColor(R.color.colorPrimary));
            s.setActionTextColor(getResources().getColor(R.color.colorPrimary));
            s.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            s.show();
            return;
        }
        ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child(id).child("note_eliminatoire").setValue(new_note);

        ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child(id).child("coef").setValue(new_coef);

        ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child(id).child("nom").setValue(new_nom).addOnCompleteListener(
                        new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                            "Modifier avec succès", Snackbar.LENGTH_LONG);
                    s.setDuration(10000);
                    s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    s.setBackgroundTint(getResources().getColor(R.color.colorAccent));
                    s.setTextColor(getResources().getColor(R.color.colorPrimary));
                    s.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                    s.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                    s.show();
                    return;
                } else {
                    Toast.makeText(EditModule.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });

    }



    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_ac:
                Intent i = new Intent (EditModule.this,MainActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.deconnecter :
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                if (mAuth.getCurrentUser() == null) {
                    Intent j =  new Intent (EditModule.this,Login.class);
                    startActivity(j);
                    finish();
                }
            default:
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}


