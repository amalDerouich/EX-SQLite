package com.example.tds1circuit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


public class tds1circuitActivity extends AppCompatActivity {
    Cursor cur;
    SQLiteDatabase db;
    LinearLayout layNaviguer, layRecherche, layR;
    EditText _txtIdCircuit, _txtVilleDepart, _txtVilleArivee, _txtPrix, _txtDuree, _txtRechercheCircuit, _txtPinf, _txtPsup;
    ImageButton _btnRecherche, _btnRPrix;
    Button _btnPrevious, _btnNext;
    Button _btnAdd, _btnUpdate, _btnDelete;
    Button _btnCancel,_btnSave;
    int op = 0;
    String x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tds1circuit);

        layNaviguer = (LinearLayout) findViewById(R.id.layNaviguer);
        layRecherche = (LinearLayout) findViewById(R.id.layRecherche);
        layR= (LinearLayout) findViewById(R.id.LayR);

        _txtPsup = (EditText) findViewById(R.id.txtPsup);
        _txtPinf = (EditText) findViewById(R.id.txtPinf);
        _btnRPrix = (ImageButton) findViewById(R.id.btnRPrix);

        _txtRechercheCircuit = (EditText) findViewById(R.id.txtRechercheCircuit);
        _txtIdCircuit = (EditText) findViewById(R.id.txtIdCircuit);
        _txtVilleDepart = (EditText) findViewById(R.id.txtVilleDepart);
        _txtVilleArivee = (EditText) findViewById(R.id.txtVilleArivee);
        _txtPrix = (EditText) findViewById(R.id.txtPrix);
        _txtDuree = (EditText) findViewById(R.id.txtDuree);

        _btnAdd = (Button) findViewById(R.id.btnAdd);
        _btnUpdate = (Button) findViewById(R.id.btnUpdate);
        _btnDelete = (Button) findViewById(R.id.btnDelete);

        _btnPrevious = (Button) findViewById(R.id.btnPrevious);
        _btnNext = (Button) findViewById(R.id.btnNext);

        _btnRecherche = (ImageButton) findViewById(R.id.btnRecherche);
        _btnCancel = (Button) findViewById(R.id.btnCancel);
        _btnSave = (Button) findViewById(R.id.btnSave);


        //Ouverture d'une connexion vers la base de données

        db = openOrCreateDatabase("bdcircuits", MODE_PRIVATE,null);

        //Création de la table Circuits

        db.execSQL("CREATE TABLE IF NOT EXISTS Circuits " +
                "(id integer primary key autoincrement, " +
                "villeDepart VARCHAR(255), " +
                "villeArrive VARCHAR(255), " +
                "prix REAL, " +
                "duree INTEGER);");
        layNaviguer.setVisibility(View.INVISIBLE);
        _btnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur = db.rawQuery("select * from circuits where villeDepart like ?", new String[]{ _txtRechercheCircuit.getText().toString()});
                try {
                    cur.moveToFirst();
                    _txtIdCircuit.setText(cur.getString(1));
                    _txtVilleDepart.setText(cur.getString(2));
                    _txtVilleArivee.setText(cur.getString(3));
                    _txtPrix.setText(cur.getString(4));
                    _txtDuree.setText(cur.getString(5));
                    if(cur.getCount() == 1){
                        layNaviguer.setVisibility(View.INVISIBLE);
                    } else {
                        layNaviguer.setVisibility(View.VISIBLE);
                        _btnPrevious.setEnabled(false);
                        _btnNext.setEnabled(true);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucun résultat.", Toast.LENGTH_SHORT).show();
                    _txtIdCircuit.setText("");
                    _txtVilleDepart.setText("");
                    _txtVilleArivee.setText("");
                    _txtPrix.setText("");
                    _txtDuree.setText("");
                    layNaviguer.setVisibility(View.INVISIBLE);
                }
            }
        });

        _btnRPrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cur = db.rawQuery("select * from circuits where prix between ? and ? ", new String[]{_txtPinf.getText().toString(),_txtPsup.getText().toString()});

                try {
                    cur.moveToFirst();
                    _txtVilleDepart.setText(cur.getString(1));
                    _txtVilleArivee.setText(cur.getString(2));
                    _txtPrix.setText(cur.getString(3));
                    _txtDuree.setText(cur.getString(4));
                    if (cur.getCount() == 1){
                        layNaviguer.setVisibility(View.INVISIBLE);
                    } else {
                        layNaviguer.setVisibility(View.VISIBLE);
                        _btnPrevious.setEnabled(false);
                        _btnNext.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucune résultat.",Toast.LENGTH_SHORT).show();
                    _txtVilleDepart.setText("");
                    _txtVilleArivee.setText("");
                    _txtPrix.setText("");
                    _txtDuree.setText("");
                    layNaviguer.setVisibility(View.INVISIBLE);
                }
            }
        });
        // Button Suivant

        _btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToNext();
                    _txtIdCircuit.setText(cur.getString(1));
                    _txtVilleDepart.setText(cur.getString(2));
                    _txtVilleArivee.setText(cur.getString(3));
                    _txtPrix.setText(cur.getString(4));
                    _txtDuree.setText(cur.getString(5));
                    _btnPrevious.setEnabled(true);
                    if (cur.isLast()){
                        _btnNext.setEnabled(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });

        // Button Precedent

        _btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToPrevious();
                    _txtIdCircuit.setText(cur.getString(1));
                    _txtVilleDepart.setText(cur.getString(2));
                    _txtVilleArivee.setText(cur.getString(3));
                    _txtPrix.setText(cur.getString(4));
                    _txtDuree.setText(cur.getString(5));
                    _btnNext.setEnabled(true);
                    if (cur.isFirst()){
                        _btnPrevious.setEnabled(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });

        //Button Ajouter

        _btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 1;
                _txtIdCircuit.setText("");
                _txtVilleDepart.setText("");
                _txtVilleArivee.setText("");
                _txtPrix.setText("");
                _txtDuree.setText("");
                _btnSave.setVisibility(View.VISIBLE);
                _btnCancel.setVisibility(View.VISIBLE);
                _btnUpdate.setVisibility(View.INVISIBLE);
                _btnDelete.setVisibility(View.INVISIBLE);
                _btnAdd.setEnabled(false);
                layNaviguer.setVisibility(View.INVISIBLE);
                layRecherche.setVisibility(View.INVISIBLE);
            }
        });

        //Button Modifier

        _btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tester si les champs ne sont pas vides
                try {
                    x = cur.getString(0);
                    op = 2;

                    _btnSave.setVisibility(View.VISIBLE);
                    _btnCancel.setVisibility(View.VISIBLE);

                    _btnDelete.setVisibility(View.INVISIBLE);
                    _btnUpdate.setEnabled(false);
                    _btnAdd.setVisibility(View.INVISIBLE);

                    layNaviguer.setVisibility(View.INVISIBLE);
                    layRecherche.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"vous devez Sélectionnez une circuit ",Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Button Enregistrer

        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (op == 1){

                    db.execSQL("insert into circuits (villeDepart,villeArrive,prix,duree) values (?,?,?,?);",
                            new String[] {_txtVilleDepart.getText().toString(),
                                    _txtVilleArivee.getText().toString(),_txtPrix.getText().toString(),
                                    _txtDuree.getText().toString()});
                } else if (op == 2) {

                    db.execSQL("update circuits set villeDepart=?, VilleArrive=?, prix=?, duree=? where id=?;",
                            new String[] {_txtVilleDepart.getText().toString(),
                                    _txtVilleArivee.getText().toString(),_txtPrix.getText().toString(),
                                    _txtDuree.getText().toString(),x});
                }

                _btnSave.setVisibility(View.INVISIBLE);
                _btnCancel.setVisibility(View.INVISIBLE);
                _btnUpdate.setVisibility(View.VISIBLE);
                _btnDelete.setVisibility(View.VISIBLE);

                _btnAdd.setVisibility(View.VISIBLE);
                _btnAdd.setEnabled(true);
                _btnUpdate.setEnabled(true);
                _btnRecherche.performClick();
                layRecherche.setVisibility(View.VISIBLE);
            }
        });

        //Button Annuler

        _btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 0;

                _btnSave.setVisibility(View.INVISIBLE);
                _btnCancel.setVisibility(View.INVISIBLE);
                _btnUpdate.setVisibility(View.VISIBLE);
                _btnDelete.setVisibility(View.VISIBLE);

                _btnAdd.setVisibility(View.VISIBLE);
                _btnAdd.setEnabled(true);
                _btnUpdate.setEnabled(true);

                layRecherche.setVisibility(View.VISIBLE);
            }
        });

        // Button Supprimer

        _btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    x=  cur.getString(0);
                    AlertDialog dial = MesOptions();
                    dial.show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "Sélectionner une circuit puis appyuer sur le bouton de suppresssion",
                            Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
    private AlertDialog MesOptions(){
        AlertDialog MiDia = new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Est ce que vous voulez supprimer cette circuit?")
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.execSQL("delete from circuit where id=?;",new String[] {cur.getString(0)});
                        _txtVilleDepart.setText("");
                        _txtVilleArivee.setText("");
                        _txtPrix.setText("");
                        _txtDuree.setText("");
                        layNaviguer.setVisibility(View.INVISIBLE);
                        cur.close();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        return MiDia;
    }

}