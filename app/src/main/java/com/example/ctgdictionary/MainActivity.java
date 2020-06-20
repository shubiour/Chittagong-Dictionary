package com.example.ctgdictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText editTextSearch;
    Button btnSearch;
    TextView txtReasults;
    Button btnCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        btnSearch = (Button) findViewById(R.id.btnSubmit);
        txtReasults = (TextView) findViewById(R.id.textViewReasult);
        btnCopy = (Button) findViewById(R.id.copyBtn);



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextSearch.getText().toString())) {
                    Toast.makeText(MainActivity.this, "No empty keyword allowed",Toast.LENGTH_SHORT).show();
                }
                else {
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("meaning");
                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String str = editTextSearch.getText().toString();
                            String searchKeyword = str.substring(0, 1).toUpperCase() + str.substring(1); //capitalized for En

                            if(dataSnapshot.child(searchKeyword).exists())
                            {
                                txtReasults.setText(dataSnapshot.child(searchKeyword).getValue().toString());
                            }else {
                                Toast.makeText(MainActivity.this, "No search results found",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("TextView", txtReasults.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(MainActivity.this, "Copied.",Toast.LENGTH_SHORT).show();
            }

        });

    }
}
