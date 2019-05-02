package com.example.shreshth.googleProject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DashBoardActivity extends AppCompatActivity {
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("database");

    private NoteAdapter adapter;
    CardView connectDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        setContentView(R.layout.activity_dash_board);
        connectDots=findViewById(R.id.cardView2);
        setUpRecylerView();
        connectDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashBoardActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });
        adapter.startListening();
    }

    private void setUpRecylerView() {
        Query query=notebookRef.orderBy("priority",Query.Direction.DESCENDING);
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        FirestoreRecyclerOptions<Note> options=new FirestoreRecyclerOptions.Builder<Note>().setQuery(query,Note.class).build();
        adapter=new NoteAdapter(options);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView lessonsRecyclerView =findViewById(R.id.lessonsRecyclerView);
        lessonsRecyclerView.setHasFixedSize(true);
        lessonsRecyclerView.setLayoutManager(layoutManager);
        lessonsRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Note note=documentSnapshot.toObject(Note.class);
                String id=documentSnapshot.getId();
                String path=documentSnapshot.getReference().getPath();
                Toast.makeText(DashBoardActivity.this, path, Toast.LENGTH_SHORT).show();
                Intent lessonsIntent=new Intent(DashBoardActivity.this,LessonsActivity.class);
                lessonsIntent.putExtra("id",id);
                lessonsIntent.putExtra("path",path);
                startActivity(lessonsIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

}
