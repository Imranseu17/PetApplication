package com.example.petapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.petapplication.R;
import com.example.petapplication.databinding.ActivityTaskDeatilsBinding;
import com.example.petapplication.model.Note;
import com.example.petapplication.viewModel.NoteViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class TaskDeatilsActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE =
            "com.codinginflow.architectureexample.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.codinginflow.architectureexample.EXTRA_DESCRIPTION";
    public static final String EXTRA_STATUS =
            "com.example.livedataroomviewmodel.EXTRA_STATUS";
    public static final String CREATED_DATE =
            "com.example.livedataroomviewmodel.CREATED_DATE";
    public static final String DEADLINE =
            "com.example.livedataroomviewmodel.DEADLINE";

    ActivityTaskDeatilsBinding deatilsBinding;
    private NoteViewModel noteViewModel;
    public static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deatilsBinding = DataBindingUtil. setContentView(this,R.layout.activity_task_deatils);
        // Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        Intent intent = getIntent();

        deatilsBinding.createdDate.setText("Created date: "+intent.getStringExtra(CREATED_DATE));
        deatilsBinding.status.setText(intent.getStringExtra(EXTRA_STATUS));
        deatilsBinding.taskName.setText(intent.getStringExtra(EXTRA_TITLE));
        deatilsBinding.description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        deatilsBinding.deadlineDate.setText(intent.getStringExtra(DEADLINE));

        Note note = intent.getParcelableExtra("note");

        deatilsBinding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteViewModel.delete(note);
                Toast.makeText( TaskDeatilsActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        deatilsBinding.buttonEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskDeatilsActivity.this, EditNoteActivity.class);
                intent.putExtra(EditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(EditNoteActivity.EXTRA_TITLE, note.getTaskName());
                intent.putExtra(EditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(EditNoteActivity.EXTRA_STATUS, note.getStatus());
                intent.putExtra(EditNoteActivity.DEADLINE,note.getDeadline());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        deatilsBinding.emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailShow();
            }
        });

        deatilsBinding.phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumberShow();
            }
        });

        deatilsBinding.urlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlSave();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
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
            Toast.makeText(this," Note not Updated ",Toast.LENGTH_SHORT).show();
    }

    private void emailShow(){
        Dialog dialog = new Dialog(TaskDeatilsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.email_save);

        TextInputEditText emailText = dialog.findViewById(R.id.edit_mail);
        AppCompatButton saveEmail = dialog.findViewById(R.id.emailSave);

        String email = emailText.getText().toString();

        saveEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.isEmpty()){
                    Toast.makeText(TaskDeatilsActivity.this," Please give E-mail",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
            }

        });

        dialog.show();


    }

    private void phoneNumberShow(){
        Dialog dialog = new Dialog(TaskDeatilsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.phone_number_save);

        TextInputEditText phoneNumberText = dialog.findViewById(R.id.edit_phone_number);
        AppCompatButton savePhone = dialog.findViewById(R.id.phoneSave);

        String phoneNumber = phoneNumberText.getText().toString();

        savePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneNumber.isEmpty()){
                    Toast.makeText(TaskDeatilsActivity.this," Please give Phone Number",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    private void urlSave(){
        Dialog dialog = new Dialog(TaskDeatilsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.url_save);

        TextInputEditText urlText = dialog.findViewById(R.id.edit_url);
        AppCompatButton saveURL = dialog.findViewById(R.id.saveURL);

        String url = urlText.getText().toString();

        saveURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(url.isEmpty()){
                    Toast.makeText(TaskDeatilsActivity.this," Please give URL",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
            }
        });

        dialog.show();


    }
}