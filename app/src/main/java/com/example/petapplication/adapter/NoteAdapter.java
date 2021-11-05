package com.example.petapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petapplication.R;
import com.example.petapplication.callbacks.OnItemClickListener;
import com.example.petapplication.model.Note;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {


    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFE_CALLBACK);
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
        noteHolder.title.setText("Task Name: "+note.getTaskName());
        noteHolder.description.setText("Description: "+note.getDescription());
        noteHolder.status.setText("Status: "+String.valueOf(note.getStatus()));
        noteHolder.deadlineDate.setText("Deadline: "+String.valueOf(note.getDeadline()));
        noteHolder.createdDate.setText("Created Date: "+String.valueOf(note.getCreatedDate()));


    }



    public Note getNoteAt(int position){

        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.createdDate)
        TextView createdDate;
        @BindView(R.id.deadlineDate)
        TextView deadlineDate;



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
