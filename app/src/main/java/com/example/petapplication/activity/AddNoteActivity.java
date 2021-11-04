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
import com.example.petapplication.databinding.ActivityAddNoteBinding;

public class AddNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE =
            "com.example.livedataroomviewmodel.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.livedataroomviewmodel.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.example.livedataroomviewmodel.EXTRA_PRIORITY";

    ActivityAddNoteBinding addNoteBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addNoteBinding = DataBindingUtil. setContentView(this,R.layout.activity_add_note);
        addNoteBinding.numberPickerPriority.setMinValue(1);
        addNoteBinding.numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {

        String title = addNoteBinding.editTitle.getText().toString().trim();
        String description = addNoteBinding.editDescription.getText().toString().trim();
        int priority = addNoteBinding.numberPickerPriority.getValue();

        if(title.isEmpty() || description.isEmpty()){
            Toast.makeText(this," Please insert a title and description",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);

        setResult(RESULT_OK,data);
        finish();

    }
}