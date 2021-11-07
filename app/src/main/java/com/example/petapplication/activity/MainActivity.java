package com.example.petapplication.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.petapplication.R;
import com.example.petapplication.adapter.NoteAdapter;
import com.example.petapplication.callbacks.OnItemClickListener;
import com.example.petapplication.databinding.ActivityMainBinding;
import com.example.petapplication.model.Note;
import com.example.petapplication.viewModel.NoteViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    ActivityMainBinding mainBinding;

    SharedPreferences pref ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil. setContentView(this,R.layout.activity_main);

        // Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
       mainBinding.buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult
                        (new Intent
                                (MainActivity.this,AddNoteActivity.class),ADD_NOTE_REQUEST);

            }
        });

        mainBinding. recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainBinding. recyclerView.setHasFixedSize(true);

        final NoteAdapter noteAdapter = new NoteAdapter(this);
        mainBinding. recyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                //update RecyclerView
                noteAdapter.submitList(notes);
            }
        });

        // Swipe Delete

//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
//                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
//                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//
//                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
//                Toast.makeText( MainActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();
//
//            }
//        }).attachToRecyclerView( mainBinding.recyclerView);



        noteAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, TaskDeatilsActivity.class);
                intent.putExtra(EditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(EditNoteActivity.EXTRA_TITLE, note.getTaskName());
                intent.putExtra(EditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(EditNoteActivity.EXTRA_STATUS, note.getStatus());
                intent.putExtra(EditNoteActivity.DEADLINE,note.getDeadline());
                intent.putExtra(EditNoteActivity.CREATED_DATE,note.getCreatedDate());
                intent.putExtra(EditNoteActivity.E_Mail,pref.getString("E-mail",""));
                intent.putExtra(EditNoteActivity.PHONE_NUMBER,pref.getString("Phone-Number",""));
                intent.putExtra(EditNoteActivity.URL,pref.getString("URL",""));
                intent.putExtra(EditNoteActivity.URL,note.getUrl());
                intent.putExtra("note",note);
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

       SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        if(pref.getString("status","").equals("Open")){
            mainBinding.open.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else if(pref.getString("status","").equals("In-Progress")){
            mainBinding.inProgress.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else if(pref.getString("status","").equals("Test")){
            mainBinding.test.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else if(pref.getString("status","").equals("Done")) {
            mainBinding.done.setTextColor(Color.parseColor("#FFFFFF"));
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){

            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            String status  = data.getStringExtra(AddNoteActivity.EXTRA_STATUS);
            String deadline = data.getStringExtra(AddNoteActivity.DEADLINE);
            String createdDate = data.getStringExtra(AddNoteActivity.CREATED_DATE);
            String email = data.getStringExtra(AddNoteActivity.E_Mail);
            String phoneNumber = data.getStringExtra(AddNoteActivity.PHONE_NUMBER);
            String url = data.getStringExtra(AddNoteActivity.URL);

            Note note = new Note(title,description,status,createdDate,deadline,email,phoneNumber,url);
            noteViewModel.insert(note);

           // Toast.makeText(this," Note Saved ",Toast.LENGTH_SHORT).show();


        }

        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EditNoteActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(EditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(EditNoteActivity.EXTRA_DESCRIPTION);
            String status  = data.getStringExtra(EditNoteActivity.EXTRA_STATUS);
            String deadline = data.getStringExtra(EditNoteActivity.DEADLINE);
            String createdDate = data.getStringExtra(EditNoteActivity.CREATED_DATE);
            String email = data.getStringExtra(EditNoteActivity.E_Mail);
            String phoneNumber = data.getStringExtra(EditNoteActivity.PHONE_NUMBER);
            String url = data.getStringExtra(EditNoteActivity.URL);

            Note note = new Note(title,description,status,createdDate,deadline,email,phoneNumber,url);
            note.setId(id);
            noteViewModel.update(note);

         //  Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

        }

       // else
          //  Toast.makeText(this," Note not Saved ",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this,"All notes Deleted",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }





}