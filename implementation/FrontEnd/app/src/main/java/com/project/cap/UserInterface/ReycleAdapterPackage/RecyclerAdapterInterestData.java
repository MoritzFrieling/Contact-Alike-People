package com.project.cap.UserInterface.ReycleAdapterPackage;

import java.util.List;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.cap.Entity.InterestData;
import com.project.cap.R;

public class RecyclerAdapterInterestData extends RecyclerView.Adapter<RecyclerAdapterInterestData.ViewHolderString>{
        private List<InterestData> values;
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolderString extends RecyclerView.ViewHolder {
            // data items
            //TODO
            public TextView interestName;
            public View layout;
            public InterestData item;
            public ViewHolderString(View v) {
                super(v);
                layout = v;
                //TODO declare fields of view (row items)
                interestName = (TextView) v.findViewById(R.id.itemTextHobby);

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
    public RecyclerAdapterInterestData(List<InterestData> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapterInterestData.ViewHolderString onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.overview_hobbies_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RecyclerAdapterInterestData.ViewHolderString vh = new RecyclerAdapterInterestData.ViewHolderString(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderString holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final InterestData item = values.get(position);
        //TODO
        holder.item = item;
        holder.interestName.setText(item.getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }


}

