package com.stefanus.firebase.dbfirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    private ArrayList<String> nama;
    private ArrayAdapter<String> adapterNama;

    private DatabaseReference root;
    private FirebaseDatabase firebaseDatabase;

    private FloatingActionButton fabTambah;
    private ListView lvNama;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabTambah = (FloatingActionButton) findViewById(R.id.fabTambah);
        lvNama = (ListView) findViewById(R.id.lvNama);
        pbLoading = (ProgressBar)findViewById(R.id.pbLoading);

        firebaseDatabase = FirebaseDatabase.getInstance();

        root = firebaseDatabase.getReference().getRoot();

        nama = new ArrayList<>();
        adapterNama = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, nama);
        lvNama.setAdapter(adapterNama);

        fabTambah.setOnClickListener(onFabClicked);

        lvNama.setOnItemClickListener(onListClicked);

        root.addValueEventListener(valueListName);
    }

    private View.OnClickListener onFabClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), BuatBiodata.class));
        }
    };

    private AdapterView.OnItemClickListener onListClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String selected = ((TextView) view).getText().toString();
            final CharSequence[] dialogItem = {"Lihat Biodata", "Update Biodata", "Hapus Biodata"};
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Pilihan");
            builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: //Lihat Biodata
                            Intent intent = new Intent(MainActivity.this, LihatBiodata.class);
                            intent.putExtra(BiodataModel.TAG_NAMA, selected);
                            startActivity(intent);
                            break;
                        case 1: //Update Biodata
                            Intent intent1 = new Intent(MainActivity.this, UpdateBiodata.class);
                            intent1.putExtra(BiodataModel.TAG_NAMA, selected);
                            startActivity(intent1);
                            break;
                        case 2: //Hapus Biodata
                            root.child(selected).removeValue();
                            break;
                    }
                }
            });
            builder.create().show();
        }
    };

    private ValueEventListener valueListName = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            nama.clear();
            Iterator i = dataSnapshot.getChildren().iterator();
            while (i.hasNext()) {
                nama.add(String.valueOf(((DataSnapshot) i.next()).getKey()));
            }
            adapterNama.notifyDataSetChanged();
            pbLoading.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e("Data Error", databaseError.toString());
        }
    };
}
