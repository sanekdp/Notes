package com.example.java.notes.adapters;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.java.notes.R;

import java.util.List;

/**
 * Created by java on 31.10.2016.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<String> dataSource = null;

    public void setDataSource(List<String> dataSource) {
        this.dataSource = dataSource;
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

        String title = dataSource.get(position);
        holder.bindView(title);
    }

    @Override
    public int getItemCount() {
        if (dataSource == null)
            return 0;
        return dataSource.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView = null;

        public NotesViewHolder(final View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
        }

        void bindView(final String title) {
            titleTextView.setText(title);
            CardView cardView = (CardView) itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackBar = Snackbar.make(view, title, Snackbar.LENGTH_SHORT);
                    snackBar.getView().setBackgroundColor(Color.GRAY);
                    snackBar.show();
                }
            });

        }
    }

}
