package com.tudresden.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class SuspectNavigationAdapter extends RecyclerView.Adapter<SuspectNavigationAdapter.ViewHolder> {

    // Create an interface to expose onClick functionality to the calling code
    private ISuspectItemClickListener mSuspectItemClickListener;
    public interface ISuspectItemClickListener {
        void onSuspectItemClick(View v, int position);
    }
    private ArrayList<Suspect> suspectList;

    public SuspectNavigationAdapter(ArrayList<Suspect> list, Context mContext) {
        this.suspectList = list;
        mSuspectItemClickListener = (ISuspectItemClickListener) mContext;
    }


    // Creates views for the RecyclerView by inflating the layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.suspect_navigation_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Suspect suspect = suspectList.get(position);

        // Set the data to the views here
        viewHolder.name.setText(suspect.getSuspectName());
        viewHolder.image.setImageResource(suspect.getSuspectImage());

        viewHolder.itemView.setOnClickListener(view -> mSuspectItemClickListener.onSuspectItemClick(view, viewHolder.getAdapterPosition()));


    }

    @Override
    public int getItemCount() {
        return suspectList == null ? 0 : suspectList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView image;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.titleSuspectNavigationItem);
            image = view.findViewById(R.id.iconSuspectNavigationItem);

        }

    }


}
