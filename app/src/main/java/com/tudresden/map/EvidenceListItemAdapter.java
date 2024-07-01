package com.tudresden.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class EvidenceListItemAdapter extends RecyclerView.Adapter<EvidenceListItemAdapter.ViewHolder> {


    // Create an interface to expose onClick functionality to the calling code
    private IEvidenceItemClickListener mEvidenceItemClickListener;
    private ArrayList<Integer> locations;

    public interface IEvidenceItemClickListener {
        void onEvidenceItemClick(View v, int position);
    }

    // end of interface
    private ArrayList<EvidenceListItem> evidenceList;

    public EvidenceListItemAdapter(ArrayList<EvidenceListItem> list, Context mContext, ArrayList<Integer> locations) {
        this.evidenceList = list;
        this.locations = locations;
        mEvidenceItemClickListener = (IEvidenceItemClickListener) mContext;
    }


    // Creates views for the RecyclerView by inflating the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.evidence_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        System.out.println(evidenceList);
        System.out.println(locations);
        EvidenceListItem item = evidenceList.get(position);
        // checking if item should be active or not
        boolean isItemActive;
        if (locations == null) {
            locations = MapActivity.locationStatusArray;
            isItemActive = locations.get(position) == 1;
        } else {
            isItemActive = locations.get(position) == 1;
        }


        // Set the data to the views
        viewHolder.image.setImageResource(item.getLocationImage());
        viewHolder.title.setText(item.getLocationTitle());
        viewHolder.number.setText(item.getLocationNumber());

        // Change the styling depending on whether item should be active or not
        if (!isItemActive) {
            // Change the background color dynamically
            View element = viewHolder.itemView.findViewById(R.id.evidence_list_item_layout);
            View title = viewHolder.itemView.findViewById(R.id.location_title);
            de.hdodenhof.circleimageview.CircleImageView image = viewHolder.itemView.findViewById(R.id.location_image);

            element.setBackgroundResource(R.drawable.background_evidence_item_disabled);
            ((TextView) title).setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.item_title_disabled));
            image.setImageResource(R.drawable.question_icon);

            // Disable click if the item is not active
            viewHolder.itemView.setOnClickListener(null);
        } else {

            viewHolder.itemView.setOnClickListener(view -> mEvidenceItemClickListener.onEvidenceItemClick(view, viewHolder.getAdapterPosition()));
        }


    }


    @Override
    public int getItemCount() {
        return evidenceList == null ? 0 : evidenceList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView number;
        private final ImageView image;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.location_title);
            number = view.findViewById(R.id.location_number);
            image = view.findViewById(R.id.location_image);

        }

    }

}
