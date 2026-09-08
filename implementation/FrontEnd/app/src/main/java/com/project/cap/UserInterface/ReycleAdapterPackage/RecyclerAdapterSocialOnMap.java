package com.project.cap.UserInterface.ReycleAdapterPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.cap.Entity.InterestData;
import com.project.cap.Entity.SocialData;
import com.project.cap.Entity.Socials;
import com.project.cap.R;

import java.util.List;

public class RecyclerAdapterSocialOnMap extends RecyclerView.Adapter<RecyclerAdapterSocialOnMap.ViewHolder>{

    private List<SocialData> values;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // data items
        //TODO
        public View layout;
        public SocialData item;
        public TextView socialName;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            //TODO declare fields of view (row items)
            socialName = v.findViewById(R.id.onMapSocialName);
        }
    }

    public void add(int position, SocialData item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)

    /**
     * Constructor uses a list of AdapterItems. No methods need to be implemented. Its just so we can reuse this.
     * @param myDataset
     */
    public RecyclerAdapterSocialOnMap(List<SocialData> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapterSocialOnMap.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.on_map_social_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RecyclerAdapterSocialOnMap.ViewHolder vh = new RecyclerAdapterSocialOnMap.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final SocialData item = values.get(position);
        //TODO set the values of the layout objects
        holder.item = item;
        holder.socialName.setText(item.getName());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
