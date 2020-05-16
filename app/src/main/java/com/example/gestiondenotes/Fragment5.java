package com.example.gestiondenotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment5 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Fragment5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment5.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment5 newInstance(String param1, String param2) {
        Fragment5 fragment = new Fragment5();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_5, container, false);
      double _test1P = Double.valueOf(EtudiantAct.test1);
      double _test2P = Double.valueOf(EtudiantAct.test2);
      double _absencePoi = Double.valueOf(EtudiantAct.absence);
       double _participationP = Double.valueOf(EtudiantAct.participation);
        TextView moyTest1 = v.findViewById(R.id.moyTest1);
        TextView moyTest2 = v.findViewById(R.id.moyTest2);
        TextView ttAbs = v.findViewById(R.id.nbrAbs);
        TextView ttPar = v.findViewById(R.id.nbrPar);
        TextView moy_g = v.findViewById(R.id.moyGen);
         int cptAbs = 0  ;
        for (Etudiant etu : Fragment2.etudiant_users) {
          cptAbs += Integer.parseInt(etu.getAbscence());
        }
        int cptParticipation = 0 ;
        for (Etudiant etu : Fragment2.etudiant_users) {
            cptParticipation += Integer.parseInt(etu.getParticipation());
        }
        double cptTest1 = 0 ;
        for (Etudiant etu : Fragment2.etudiant_users) {
            cptTest1 += Double.parseDouble(etu.getNote1());

        }
        cptTest1 = cptTest1 / Fragment2.etudiant_users.size();
        double cptTest2 = 0 ;
        for (Etudiant etu : Fragment2.etudiant_users) {
            cptTest2 += Double.parseDouble(etu.getNote2());

        }
        double moy  = 0 ;
         for (Etudiant e : Fragment2.etudiant_users) {
             moy += e.getMoy() ;
         }
         cptTest2 = cptTest2 / Fragment2.etudiant_users.size();
         moy = moy / Fragment2.etudiant_users.size();

        moyTest1.setText("Moyenne du Test1 : " + cptTest1);
        moyTest2.setText("Moyenne du Test2 : " + cptTest2);
        ttAbs.setText("Nombre  total d'absence  : "  + cptAbs);
        ttPar.setText("Nombre total de participation :" + cptParticipation);
        moy_g.setText("moyenne générale :"+ moy);









        return v ;
    }
}
