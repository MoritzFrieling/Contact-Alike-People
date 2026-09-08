package com.project.cap.UserInterface.ReycleAdapterPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.cap.Entity.SocialData;
import com.project.cap.R;

import java.util.List;

public class RecyclerAdapterSocialsOnEditProfile extends RecyclerView.Adapter<RecyclerAdapterSocialsOnEditProfile.ViewHolderSocials>{
    private List<SocialData> values;


    public RecyclerAdapterSocialsOnEditProfile(List<SocialData> myDataset) {
        values = myDataset;
    }
    public class ViewHolderSocials extends RecyclerView.ViewHolder {
        public EditText socialUserName;
        public EditText socialName;
        public ViewHolderSocials(@NonNull View itemView) {
            super(itemView);
            socialName = itemView.findViewById(R.id.socialNameProfile);
            socialUserName = itemView.findViewById(R.id.userNameProfile);
        }
    }

    @NonNull
    @Override
    public ViewHolderSocials onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.profile_socials, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RecyclerAdapterSocialsOnEditProfile.ViewHolderSocials vh = new RecyclerAdapterSocialsOnEditProfile.ViewHolderSocials(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSocials holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final SocialData item = values.get(position);
        //TODO
        holder.socialName.setText(item.getName());
        holder.socialUserName.setText(item.getUsername());
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public void add(int position, SocialData item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }
}
