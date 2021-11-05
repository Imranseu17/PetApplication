package com.example.petapplication.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.petapplication.R;
import com.example.petapplication.adapter.NoteAdapter;
import com.example.petapplication.callbacks.OnItemClickListener;
import com.example.petapplication.databinding.ActivityMainBinding;
import com.example.petapplication.model.Note;
import com.example.petapplication.viewModel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil. setContentView(this,R.layout.activity_main);
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

        final NoteAdapter noteAdapter = new NoteAdapter();
        mainBinding. recyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                //update RecyclerView
                noteAdapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText( MainActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView( mainBinding.recyclerView);

        noteAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                intent.putExtra(EditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(EditNoteActivity.EXTRA_TITLE, note.getTaskName());
                intent.putExtra(EditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(EditNoteActivity.EXTRA_STATUS, note.getStatus());
                intent.putExtra(EditNoteActivity.DEADLINE,note.getDeadline());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
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

            Note note = new Note(title,description,status,createdDate,deadline);
            noteViewModel.insert(note);

            Toast.makeText(this," Note Saved ",Toast.LENGTH_SHORT).show();

        }

        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EditNoteActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(EditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(EditNoteActivity.EXTRA_DESCRIPTION);
            String status  = data.getStringExtra(AddNoteActivity.EXTRA_STATUS);
            String deadline = data.getStringExtra(AddNoteActivity.DEADLINE);
            String createdDate = data.getStringExtra(AddNoteActivity.CREATED_DATE);

            Note note = new Note(title,description,status,createdDate,deadline);
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        }

        else
            Toast.makeText(this," Note not Saved ",Toast.LENGTH_SHORT).show();
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