package com.swe.videoflexpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.swe.videoflexpro.R;
import com.swe.videoflexpro.models.VideoModel;
import com.swe.videoflexpro.viewholders.VideoViewHolder;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    String name,url;//delete data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        recyclerView = findViewById(R.id.videoitemsRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("freevideo");
    }
    private void firebaseSearch(String searchtext){
        String query = searchtext.toLowerCase();
        Query firebaseQuery = databaseReference.orderByChild("search").startAt(query).endAt(query + "\uf8ff");

        FirebaseRecyclerOptions<VideoModel> options =
                new FirebaseRecyclerOptions.Builder<VideoModel>()
                        .setQuery(databaseReference,VideoModel.class)
                        .build();

        FirebaseRecyclerAdapter<VideoModel, VideoViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<VideoModel, VideoViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull VideoViewHolder freevideoViewHolder, int i, @NonNull VideoModel model) {
                        freevideoViewHolder.setExoplayer(getApplication(),model.getName(),model.getVideourl());
                        //delete data
                        freevideoViewHolder.setOnClickListener(new VideoViewHolder.Clicklistener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //next activity(Item click)
                                name=getItem(position).getName();
                                url=getItem(position).getVideourl();
                                Intent intent = new Intent(MainActivity.this,FullScreenVideo.class);
                                intent.putExtra("nam", name);
                                intent.putExtra("url",url);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                                name=getItem(position).getName();
                                showDeleteDialog(name);

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.videoitem,parent,false);
                        return  new VideoViewHolder(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }




    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<VideoModel> options =
                new FirebaseRecyclerOptions.Builder<VideoModel>()
                        .setQuery(databaseReference,VideoModel.class)
                        .build();

        FirebaseRecyclerAdapter<VideoModel,VideoViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<VideoModel, VideoViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull VideoViewHolder freevideoViewHolder, int i, @NonNull VideoModel model) {
                        freevideoViewHolder.setExoplayer(getApplication(),model.getName(),model.getVideourl());
                        //delete data
                        freevideoViewHolder.setOnClickListener(new VideoViewHolder.Clicklistener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //next activity(Item click)
                                name=getItem(position).getName();
                                url=getItem(position).getVideourl();
                                Intent intent = new Intent(MainActivity.this,FullScreenVideo.class);
                                intent.putExtra("nam", name);
                                intent.putExtra("url",url);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                                name=getItem(position).getName();
                                showDeleteDialog(name);

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.videoitem,parent,false);
                        return  new VideoViewHolder(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.videosearch,menu);
        MenuItem item = menu.findItem(R.id.search_firebase);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    //delete data
    private void showDeleteDialog(String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you want to delete this data");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Query query = databaseReference.orderByChild("name").equalTo(name);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            dataSnapshot1.getRef().removeValue();
                        }
                        Toast.makeText(MainActivity.this, "Video Deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
