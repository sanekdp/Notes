package com.example.java.notes.adapters;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.java.notes.Notes;
import com.example.java.notes.R;
import com.example.java.notes.model.Note;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by java on 31.10.2016.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> mDataSource = null;

    public void setDataSource(List<Note> dataSource) {
        this.mDataSource = dataSource;
        notifyDataSetChanged();
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
    }

    @Override
    public int getItemCount() {
        if (mDataSource == null)
            return 0;
        return mDataSource.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_text_view)
        protected TextView titleTextView = null;
        @BindView(R.id.card_view)
        protected CardView cardView = null;

        public NotesViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
        }

        void bindView(final Note note) {
            titleTextView.setText(note.getTitle());
            //CardView cardView = (CardView) itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackBar = Snackbar.make(view, note.getTitle(), Snackbar.LENGTH_SHORT);
                    snackBar.getView().setBackgroundColor(Color.GRAY);
                    snackBar.show();
                }
            });

        }
    }

}
