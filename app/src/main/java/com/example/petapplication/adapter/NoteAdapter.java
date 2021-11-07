package com.example.petapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petapplication.R;
import com.example.petapplication.activity.EditNoteActivity;
import com.example.petapplication.activity.MainActivity;
import com.example.petapplication.callbacks.OnItemClickListener;
import com.example.petapplication.model.Note;
import com.example.petapplication.viewModel.NoteViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {


    private OnItemClickListener listener;
    private NoteViewModel noteViewModel;
    private Context context;
    SharedPreferences pref ;


    public NoteAdapter(Context context) {
        super(DIFE_CALLBACK);
        this.context = context;
    }



    public static final DiffUtil.ItemCallback<Note> DIFE_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldNoteItem, @NonNull Note newNoteItem) {
            return oldNoteItem.getId() == newNoteItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldNoteItem, @NonNull Note newNoteItem) {
            return oldNoteItem.getTaskName().equals(newNoteItem.getTaskName())
                    && oldNoteItem.getDescription().equals(newNoteItem.getDescription())
                    && oldNoteItem.getStatus().equals(newNoteItem.getStatus())
                    && oldNoteItem.getCreatedDate().equals(newNoteItem.getCreatedDate())
                    && oldNoteItem.getDeadline().equals(newNoteItem.getDeadline());
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.note_view,viewGroup,false);

        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {

        Note note = getItem(i);
        noteHolder.title.setText(note.getTaskName());
        noteHolder.deadlineDate.setText("Deadline:   "+String.valueOf(note.getDeadline()));
        noteHolder.createdDate.setText("Created Date:   "+String.valueOf(note.getCreatedDate()));
        noteViewModel = ViewModelProviders.of((FragmentActivity) context).get(NoteViewModel.class);
        noteHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteViewModel.delete(getNoteAt(noteHolder.getAdapterPosition()));
                Toast.makeText( context,"Note Deleted",Toast.LENGTH_SHORT).show();
            }
        });

        noteHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditNoteActivity.class);
                intent.putExtra(EditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(EditNoteActivity.EXTRA_TITLE, note.getTaskName());
                intent.putExtra(EditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(EditNoteActivity.EXTRA_STATUS, note.getStatus());
                intent.putExtra(EditNoteActivity.DEADLINE,note.getDeadline());
                intent.putExtra(EditNoteActivity.E_Mail,note.getEmail());
                intent.putExtra(EditNoteActivity.PHONE_NUMBER,note.getPhoneNumber());
                intent.putExtra(EditNoteActivity.URL,note.getUrl());
                 context.startActivity(intent);
            }
        });
        pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("status", note.getStatus());
        editor.commit();

    }



    public Note getNoteAt(int position){

        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.createdDate)
        TextView createdDate;
        @BindView(R.id.deadlineDate)
        TextView deadlineDate;
        @BindView(R.id.editButton)
        ImageView editButton;
        @BindView(R.id.deleteButton)
        ImageView deleteButton;



        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                    {
                        listener.onItemClick(getItem(position));
                    }

                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){

            this.listener = listener;

    }


}
