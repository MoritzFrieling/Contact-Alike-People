package com.project.cap.UserInterface.ReycleAdapterPackage;

import java.util.List;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.project.cap.Backend.retrofit.Responses.GetAllSocialsResponse;
import com.project.cap.Entity.Socials;
import com.project.cap.R;

public class RecyclerAdapterSocials extends RecyclerView.Adapter<RecyclerAdapterSocials.ViewHolderSocials>{
    private List<GetAllSocialsResponse> values;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolderSocials extends RecyclerView.ViewHolder {
        // data items
        //TODO
        public CheckBox checky;
        public EditText userInput;
        public View layout;
        public GetAllSocialsResponse item;
        public ViewHolderSocials(View v) {
            super(v);
            layout = v;
            //TODO declare fields of view (row items)
            checky = v.findViewById(R.id.boxSocialname);
            userInput = v.findViewById(R.id.editTextSocialUserInput);//disabled on default
        }
    }

    public void add(int position, GetAllSocialsResponse item) {
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
    public RecyclerAdapterSocials(List<GetAllSocialsResponse> myDataset) {
        myDataset.forEach(s -> {
            System.out.println(s.getId());
        });
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapterSocials.ViewHolderSocials onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.socials_item_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RecyclerAdapterSocials.ViewHolderSocials vh = new RecyclerAdapterSocials.ViewHolderSocials(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderSocials holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final GetAllSocialsResponse item = values.get(position);
        //TODO
        holder.item = item;
        holder.checky.setText(item.getName());//name of social
        holder.checky.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // make swoosh and enabled
                if(holder.userInput.getVisibility() == View.VISIBLE){
                    holder.userInput.setVisibility(View.GONE);
                }else {//making them appear again (on click)
                    holder.userInput.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }


}
