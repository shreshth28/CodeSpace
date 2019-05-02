package com.example.shreshth.googleProject;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class LessonsActivity extends AppCompatActivity {


    TextToSpeech t1;
    FloatingActionButton playAudioBtn;
    TextView sentenceText;
    ScrollView sv;
    ImageView img2;
    String passage="";
    int dots=4;
    ArrayList<String> story;
//    EditText ed1;
//    Button b1;
    int count=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lessons);

        FirebaseFirestore db;
        sv=findViewById(R.id.sv);
        final CollectionReference docRef;
        sentenceText=findViewById(R.id.sentenceText);

        playAudioBtn=findViewById(R.id.playAudioBtn);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        

        
        Intent intent=getIntent();
        img2=findViewById(R.id.imageView2);
        String id=intent.getStringExtra("id");
        final ArrayList<String> images=new ArrayList<>();
        final ArrayList<String> keywords=new ArrayList<>();
        final ArrayList<String> sentence=new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        docRef = db.collection("database").document(id).collection("nlp");
        db.collection("database").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    dots=Integer.parseInt(task.getResult().get("dots").toString());
                }
            }
        });

        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document:task.getResult()){
                        if(document.getId().equals("image")){
                            for(int i = 1; i<= dots; i++){
                                images.add(document.getString(String.valueOf(i)));
                            }
                        }
                        else if(document.getId().equals("keywords")){
                            for(int i = 1; i<= dots; i++){
                                keywords.add(document.getString(String.valueOf(i)));
                            }
                        }
                        else{
                            for(int i = 1; i<= dots; i++){
                                passage=passage+document.getString(String.valueOf(i))+".";
                                sentence.add(document.getString(String.valueOf(i)));
                                if(i==dots){
                                    sentenceText.setText(passage);
                                }
                            }

                        }
//                        Toast.makeText(LessonsActivity.this, document.getId(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot document=task.getResult();
//                    if(document.exists()){
//                        String title= document.getString("title");
//                        String author=document.getString("author");
//                        ArrayList<String> story= (ArrayList<String>) document.get("sentences");
//
//                        int length=story.size();
//                        String passage="";
//                        for(int i=0;i<length;i++)
//                        {
//                            passage=passage+story.get(i);
//                        }
//                        sentenceText.setText(passage);
//                    }
//                }
//            }
//        });


//        playAudioBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                count++;
//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if(task.isSuccessful()){
//                            DocumentSnapshot document=task.getResult();
//                            if(document.exists()){
//                                String title= document.getString("title");
//                                String author=document.getString("author");
//                                story= (ArrayList<String>) document.get("sentences");
//                                ArrayList<String> words= (ArrayList<String>) document.get("story");
//                                int length=story.size();
//                                if(count<length){
//                                    Picasso.get().load("https://source.unsplash.com/category/"+words.get(count)).into(img2);
//                                    t1.speak(story.get(count),TextToSpeech.QUEUE_FLUSH,null);
//                                }
//                            }
//                        }
//                    }
//                });
//
//            }
//        });
        playAudioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count<dots){
                    Picasso.get().load(images.get(count)).into(img2);
                    t1.speak(sentence.get(count),TextToSpeech.QUEUE_FLUSH,null);
                }
            }
        });




    }

//    private void ss(String s)
//    {
//
//    }
//
//    private void speakSentence(String s) {
//        t1.speak(s, TextToSpeech.QUEUE_ADD, null);
//
//    }
}
