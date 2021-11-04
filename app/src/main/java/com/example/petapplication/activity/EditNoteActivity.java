package com.example.petapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.petapplication.R;
import com.example.petapplication.databinding.ActivityEditNoteBinding;

public class EditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.codinginflow.architectureexample.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.codinginflow.architectureexample.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.codinginflow.architectureexample.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.codinginflow.architectureexample.EXTRA_PRIORITY";

    ActivityEditNoteBinding editNoteBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editNoteBinding = DataBindingUtil. setContentView(this,R.layout.activity_edit_note);

        editNoteBinding. numberPickerPriority.setMinValue(1);
        editNoteBinding. numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editNoteBinding.editTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editNoteBinding.editDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editNoteBinding. numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }
    }

    private void saveNote(){

        String title = editNoteBinding.editTitle.getText().toString().trim();
        String description = editNoteBinding.editDescription.getText().toString().trim();
        int priority = editNoteBinding. numberPickerPriority.getValue();

        if(title.isEmpty() || description.isEmpty()){
            Toast.makeText(this," Please insert a title and description",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id != -1)
            data.putExtra(EXTRA_ID,id);

        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}