package com.example.java.notes.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.java.notes.R;
import com.example.java.notes.activity.EditNoteActivity;
import com.example.java.notes.model.Note;
import com.tjeannin.provigen.ProviGenBaseContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> mDataSource = null;

    public void setDataSource(List<Note> dataSource) {
        this.mDataSource = dataSource;
        notifyDataSetChanged();
    }

    private View.OnClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(View.OnClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_notes_item, parent, false);

        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {

        Note note = mDataSource.get(position);
        holder.itemView.setOnClickListener(mOnItemClickListener);
        holder.bindView(note);
    }

    @Override
    public int getItemCount() {
        if (mDataSource == null)
            return 0;
        return mDataSource.size();
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        private Note mNote = null;

        public Note getNote() {
            return mNote;
        }

        @BindView(R.id.title_text_view)
        protected TextView mPrimaryTextView = null;
        @BindView(R.id.secondary_text_view)
        protected TextView mSecondaryTextView = null;
        @BindView(R.id.date_text_view)
        protected TextView mDateTextView = null;
        @BindView(R.id.card_view)
        protected CardView cardView = null;



        public NotesViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(final Note note) {
            mNote = note;
            mPrimaryTextView.setText(note.getTitle());
            mSecondaryTextView.setText(note.getText());
            mDateTextView.setText(note.getTime());
        }
    }

}
