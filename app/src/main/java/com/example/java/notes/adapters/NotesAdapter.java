package com.example.java.notes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.java.notes.R;
import com.example.java.notes.model.Note;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> mDataSource = null;
    private View.OnClickListener mOnItemClickListener = null;
    private View.OnLongClickListener mOnLongItemClickListener = null;

    public void setDataSource(List<Note> dataSource) {
        this.mDataSource = dataSource;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(View.OnClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnLongItemClickListener(View.OnLongClickListener mOnLongItemClickListener) {
        this.mOnLongItemClickListener = mOnLongItemClickListener;
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
        holder.bindView(note);
        holder.itemView.setOnClickListener(mOnItemClickListener);
        holder.itemView.setOnLongClickListener(mOnLongItemClickListener);
    }

    @Override
    public int getItemCount() {
        return (mDataSource == null) ? 0 : mDataSource.size();
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        private Note mNote = null;

        public Note getNote() {
            return mNote;
        }

        @BindView(R.id.title_note_view)
        protected TextView mPrimaryTextView = null;
        @BindView(R.id.content_note_view)
        protected TextView mSecondaryTextView = null;
        @BindView(R.id.date_text_view)
        protected TextView mDateTextView = null;



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
