package com.project.cap.UserInterface.ReycleAdapterPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.cap.Entity.InterestData;
import com.project.cap.Entity.SocialData;
import com.project.cap.R;

import java.util.List;

public class RecyclerAdapterInterestsOnEditProfile extends RecyclerView.Adapter<RecyclerAdapterInterestsOnEditProfile.ViewHolderInterests>{

    private List<InterestData> values;


    public RecyclerAdapterInterestsOnEditProfile(List<InterestData> myDataset) {
        values = myDataset;
    }
    public class ViewHolderInterests extends RecyclerView.ViewHolder {
        public Spinner proficiency;
        public EditText interestName;
        public ViewHolderInterests(@NonNull View itemView) {
            super(itemView);
            interestName = itemView.findViewById(R.id.interestNameProfile);
            proficiency = itemView.findViewById(R.id.spinner);
        }
    }


    @NonNull
    @Override
    public ViewHolderInterests onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.profile_interests, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RecyclerAdapterInterestsOnEditProfile.ViewHolderInterests vh = new RecyclerAdapterInterestsOnEditProfile.ViewHolderInterests(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInterests holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final InterestData item = values.get(position);
        //TODO
        holder.interestName.setText(item.getName());
        switch (item.getProficiency().toLowerCase()) {
            case "pro":
                holder.proficiency.setSelection(2);
                break;
            case "advanced":
                holder.proficiency.setSelection(1);
                break;
            case "beginner":
                holder.proficiency.setSelection(0);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public void add(int position, InterestData item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }
}
