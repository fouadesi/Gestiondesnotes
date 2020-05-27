package com.example.gestiondenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Group_act extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static String id_module;
    FloatingActionButton btn;
    DatabaseReference mDatabase;
    FloatingActionButton add_formule;
   static String key;
    ListView listView;
    ArrayList<Groupes> groupes_users;
    static String _test1 ;static  String _test2 ;
  static   String _participation ;
  static  String _absence;
     String test1 ;
     String test2 ;
     String participation ;
     String absence ;
    private DrawerLayout drawer;
    private Toolbar toolbar ;
    static String nom_module ;

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_ac:
                Intent i = new Intent (Group_act.this,MainActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.deconnecter :
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                if (mAuth.getCurrentUser() == null) {
                    Intent j =  new Intent (Group_act.this,Login.class);
                    startActivity(j);
                    finish();
                }
            default:
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deconnecter :
                Toast.makeText(Group_act.this,"gjdfhj",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_group_act);
        toolbar = findViewById(R.id.toolbar_groupes);
      nom_module = getIntent().getExtras().getString("nomM");
        toolbar.setTitle("Groupes");
        toolbar.setSubtitle("Module : " + nom_module );
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_groupeAct);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        _test1 = getIntent().getExtras().getString("test1");
        _test2 = getIntent().getExtras().getString("test2");
        _participation = getIntent().getExtras().getString("participation");
        _absence = getIntent().getExtras().getString("absence");


        test1 = getIntent().getExtras().getString("test1");
        test2 = getIntent().getExtras().getString("test2");
        participation = getIntent().getExtras().getString("participation");
    absence = getIntent().getExtras().getString("absence");
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

         id_module   =  getIntent().getExtras().getString("id");
         listView    =  findViewById(R.id.list_view_groupes);
         add_formule = findViewById(R.id.item_ajouterformule);
         add_formule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Group_act.this);
                builder.setTitle("Ajouter une Formule");
                builder.setIcon(R.drawable.groupe_blackicon);
                builder.setBackground(getResources().getDrawable(R.drawable.design_alert_dialog));
                final View mview = getLayoutInflater().inflate(R.layout.add_formule, null);
                final TextInputEditText Test1 = mview.findViewById(R.id.formule_test1);
                final TextInputEditText Test2 = mview.findViewById(R.id.formule_test2);
                final TextInputEditText Absence = mview.findViewById(R.id.formule_absence);
                final TextInputEditText Participation = mview.findViewById(R.id.formule_participation);
                Test1.setText(test1);
                Test2.setText(test2);
                Absence.setText(absence);
                Participation.setText(participation);
                builder.setView(mview);
                builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String Test1_edi = Test1.getText().toString().trim();
                        final String Test2_edi = Test2.getText().toString().trim();
                        final String Absence_edi = Absence.getText().toString().trim();
                        final String Participation_edi = Participation.getText().toString();

                        if (Test1_edi.isEmpty() || Test2_edi.isEmpty() || Absence_edi.isEmpty() || Participation_edi.isEmpty()) {
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
                                    s.dismiss();
                                }
                            });
                            s.show();
                            return;

                        }
                        if (Double.valueOf(Test1_edi) + Double.valueOf(Test2_edi) != 100) {
                            final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                                    "La somme Entre le test1 et le test2 != 100 % ", Snackbar.LENGTH_LONG);
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
                            return;
                        }
                        final Formule f = new Formule(Test1_edi,Test2_edi,Absence_edi,Participation_edi) ;
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        mDatabase = FirebaseDatabase.getInstance().getReference() ;
                        mDatabase.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                child(id_module)
                                .child("formule").setValue(f).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()) {
                                 _test1= Test1_edi;
                                 _test2 = Test2_edi;
                                 _absence = Absence_edi;
                                 _participation = Participation_edi;
                                 test1 = Test1_edi ;
                                 test2 = Test2_edi ;
                                 absence = Absence_edi ;
                                 participation = Participation_edi  ;

                                 final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                                         "Bien inserer", Snackbar.LENGTH_LONG);
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
                             }  else {
                                 final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                                         task.getException().getMessage().toString(), Snackbar.LENGTH_LONG);
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
                             }
                            }
                        });
                     }
                });
                builder.show();
            }});




        DatabaseReference db_ref2 = FirebaseDatabase.getInstance().
                getReference().child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child(id_module).child("Groupes");
        db_ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupes_users = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Groupes m = dataSnapshot1.getValue(Groupes.class);
                    groupes_users.add(m);
                }
                groupesadapter adapter = new groupesadapter(Group_act.this, groupes_users);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        groupesadapter adapter = new groupesadapter(Group_act.this, groupes_users);
        adapter.notifyDataSetChanged();
        btn = findViewById(R.id.floating_action_button_add_groupe2);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroupe();
            }
        });
    }

    void addGroupe() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Group_act.this);
        builder.setTitle("Ajouter un Groupe");
        builder.setIcon(R.drawable.groupe_blackicon);
        builder.setBackground(getResources().getDrawable(R.drawable.design_alert_dialog));
        final View mview = getLayoutInflater().inflate(R.layout.dialog_add_groupe, null);
        final TextInputEditText nom_edite = mview.findViewById(R.id.nom_groupe_dialog_bar);
        final TextInputEditText niveau_edit = mview.findViewById(R.id.niveau_groupe_dialog_bar);
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nom = nom_edite.getText().toString();
                String niveau = niveau_edit.getText().toString();
                if (nom.isEmpty() || niveau.isEmpty()) {
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
                            s.dismiss();
                        }
                    });
                    s.show();
                    return;
                }
                insertGroupe(nom, niveau);

            }
        });
        builder.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setView(mview);
        builder.show();
    }
    void insertGroupe(String nom, String niveau) {
        Groupes g = new Groupes(nom, niveau);
        key = mDatabase.child("Module_users").push().getKey();
        g.setId(key);
        mDatabase.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(id_module)
                .child("Groupes").child(key).
                setValue(g).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    final Snackbar s = Snackbar.make(findViewById(android.R.id.content), "Module est inserer", Snackbar.LENGTH_LONG);
                    s.setDuration(10000);
                    s.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    s.setBackgroundTint(getResources().getColor(R.color.colorAccent));
                    s.setTextColor(getResources().getColor(R.color.colorPrimary));
                    s.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            s.dismiss();
                        }});
                    s.show();
                } else {
                    final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                            task.getException().getMessage(), Snackbar.LENGTH_LONG);
                    s.setDuration(10000);
                    s.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    s.setBackgroundTint(getResources().getColor(R.color.colorAccent));
                    s.setTextColor(getResources().getColor(R.color.colorPrimary));
                    s.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            s.dismiss();

                        }});
                    s.show();
                }
            }
        });
    }



}

