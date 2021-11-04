package com.example.petapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.adapters.AdapterViewBindingAdapter;
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
            return oldNoteItem.getTitle().equals(newNoteItem.getTitle())
                    && oldNoteItem.getDescription().equals(newNoteItem.getDescription())
                    && oldNoteItem.getPriority() == newNoteItem.getPriority();
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
        noteHolder.title.setText(note.getTitle());
        noteHolder.description.setText(note.getDescription());
        noteHolder.priority.setText(String.valueOf(note.getPriority()));

    }



    public Note getNoteAt(int position){

        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_view_title)
        TextView title;
        @BindView(R.id.text_view_description)
        TextView description;
        @BindView(R.id.text_view_priority)
        TextView priority;


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
