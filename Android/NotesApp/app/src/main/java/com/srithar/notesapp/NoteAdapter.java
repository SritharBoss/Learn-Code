package com.srithar.notesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    public interface OnNoteListener{
        void onItemLongClick(int position);
    }

    private OnNoteListener mOnNoteListener;

    NoteAdapter(OnNoteListener onNoteListener){
        mOnNoteListener=onNoteListener;
    }

    List<NotesEntity> allNotes=new ArrayList<>();

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view,parent,false);
        return new NoteHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        NotesEntity  currentNote = allNotes.get(position);
        holder.title.setText(currentNote.getTitle());
        holder.desc.setText(currentNote.getDescription());

    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    public void setNotes(List<NotesEntity> notes) {
        this.allNotes = notes;
        notifyDataSetChanged();
    }

    public static class NoteHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        OnNoteListener mOnNoteListener;

        public NoteHolder(@NonNull View itemView, final OnNoteListener onNoteListener) {
            super(itemView);
            title=itemView.findViewById(R.id.card_view_title);
            desc=itemView.findViewById(R.id.card_view_desc);
            mOnNoteListener=onNoteListener;

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(mOnNoteListener!=null){
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            mOnNoteListener.onItemLongClick(position);
                        }
                    }
                    return true;
                }
            });
        }
    }
}
