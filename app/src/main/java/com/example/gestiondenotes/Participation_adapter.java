package com.example.gestiondenotes;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Participation_adapter extends ArrayAdapter<Etudiant> {
    public Participation_adapter(@NonNull Context context, ArrayList<Etudiant> etudiant) {
        super(context, 0, etudiant);
    }

    View v ;

    public View getView(int position, View convertView, ViewGroup parent) {
        final Etudiant etudiant = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.participation_layout, parent, false);
        }
        CircleImageView profil_image_participation = convertView.findViewById(R.id.profil_image_participation);
        TextView name_participation = convertView.findViewById(R.id.name_participation);
        TextView prenom_participation = convertView. findViewById(R.id.prenom_participation) ;
        TextView email_participation = convertView.findViewById(R.id.email_participation);
        TextView ni_text_participation= convertView.findViewById(R.id.ni_text_participation);
        TextView participation_textView = convertView.findViewById(R.id.participation_textView);



        ImageButton Ajouter_participation  = convertView.findViewById(R.id.Ajouter_participation);
        Ajouter_participation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ajouter_par(etudiant);
            }
        });
        ImageButton supprimer_participation = convertView.findViewById(R.id.supprimer_participation);
        supprimer_participation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Supprimer_par(etudiant) ;
            }
        });
        Glide.with(getContext()).load(etudiant.getPicture()).into(profil_image_participation);
        if (Integer.parseInt(etudiant.getParticipation()) <=  2) {
            profil_image_participation.setBorderColor(Color.parseColor("#ff1744"));
        }
        name_participation.setText("Nom : " + etudiant.getNom());
        prenom_participation.setText("Prenom :" + etudiant.getPrenom());
        email_participation.setText("Email :" + etudiant.getEmail());
        ni_text_participation.setText("NI :" + etudiant.getNI());
        participation_textView.setText("Participation " + etudiant.getParticipation());
        v = convertView ;
        return convertView ;

    }
    void Ajouter_par(final Etudiant e) {
        MaterialAlertDialogBuilder m = new MaterialAlertDialogBuilder(getContext());
        m.setTitle("Ajouter un point");
        m.setMessage("êtes vous sûr de vouloir ajouter un point ");
        m.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int i = Integer.parseInt(e.getParticipation()) +1 ;
                e.setParticipation(String.valueOf(i));
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                        child(Group_act.id_module).child("Groupes").child(EtudiantAct.key_g).
                        child("Etudiants").child(e.getNI()).setValue(e);

            }});
        m.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        m.show();

    }
    void Supprimer_par(final Etudiant e) {
        MaterialAlertDialogBuilder m = new MaterialAlertDialogBuilder(getContext());
        m.setTitle("Retirer un point de partcipation");
        m.setMessage("êtes vous sûr de vouloir retirer une participation ");
        m.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (e.getAbscence().equals("0")) {
                    Toast.makeText(getContext(),"Impossible",Toast.LENGTH_LONG).show();
                    return;
                }
                if (Integer.parseInt(e.getParticipation()) != 0 ) {
                    int i = Integer.parseInt(e.getParticipation()) - 1;
                    e.setParticipation(String.valueOf(i));
                    DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                            child(Group_act.id_module).child("Groupes").child(EtudiantAct.key_g).
                            child("Etudiants").child(e.getNI()).setValue(e);
                } else {
                    final Snackbar s = Snackbar.make(v,
                            "Erreur.", Snackbar.LENGTH_LONG);
                    s.setDuration(10000);
                    s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    s.setBackgroundTint(v.getResources().getColor(R.color.colorAccent));
                    s.setTextColor(v.getResources().getColor(R.color.colorPrimary));
                    s.setActionTextColor(v.getResources().getColor(R.color.colorPrimary));
                    s.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            s.dismiss();
                        }
                    });
                    s.show();
                }

            }});
        m.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        m.show();
    }


}

