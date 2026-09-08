package com.project.cap.UserInterface.ReycleAdapterPackage;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.cap.Entity.Interest;
import com.project.cap.Entity.InterestData;
import com.project.cap.Entity.Socials;
import com.project.cap.R;

import java.util.List;

public class RecyclerAdapterInterestOnMap  extends RecyclerView.Adapter<RecyclerAdapterInterestOnMap.ViewHolder>{

    private List<InterestData> values;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // data items
        //TODO
        public View layout;
        public InterestData item;

        public TextView interestName;
        public TextView startdate;
        public TextView profic;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            //TODO declare fields of view (row items)

            interestName = v.findViewById(R.id.textInterestName2);
            startdate = v.findViewById(R.id.onMapUserStartDate);
            profic = v.findViewById(R.id.onMapUserProficiency);
        }
    }

    public void add(int position, InterestData item) {
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
    public RecyclerAdapterInterestOnMap(List<InterestData> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapterInterestOnMap.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.on_map_interest_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RecyclerAdapterInterestOnMap.ViewHolder vh = new RecyclerAdapterInterestOnMap.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final InterestData item = values.get(position);
        //TODO set the values of the layout objects
        holder.item = item;
       holder.startdate.setText(item.getStartDate());
       holder.interestName.setText(item.getName());
       holder.profic.setText(item.getProficiency());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
