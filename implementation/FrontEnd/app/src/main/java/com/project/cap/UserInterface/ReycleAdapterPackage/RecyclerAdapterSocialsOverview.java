package com.project.cap.UserInterface.ReycleAdapterPackage;

import java.util.HashMap;
import java.util.List;
import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.cap.Entity.Interest;
import com.project.cap.Entity.SocialData;
import com.project.cap.R;

public class RecyclerAdapterSocialsOverview  extends RecyclerView.Adapter<RecyclerAdapterSocialsOverview.ViewHolderSocialsOverview> {
    private List<SocialData> values;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolderSocialsOverview extends RecyclerView.ViewHolder {
        // data items
        //TODO
        public TextView socialName;
        public TextView userName;
        public View layout;

        public ViewHolderSocialsOverview(View v) {
            super(v);
            layout = v;
            //TODO declare fields of view (row items)
            socialName = v.findViewById(R.id.overVItemSocialName);
            userName = v.findViewById(R.id.ovSocialItemUsername);
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
    public RecyclerAdapterSocialsOverview(List<SocialData> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapterSocialsOverview.ViewHolderSocialsOverview onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.overview_socials_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RecyclerAdapterSocialsOverview.ViewHolderSocialsOverview vh = new RecyclerAdapterSocialsOverview.ViewHolderSocialsOverview(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSocialsOverview holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final SocialData item = values.get(position);
        //TODO
        holder.socialName.setText(item.getName());
        holder.userName.setText(item.getUsername());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
