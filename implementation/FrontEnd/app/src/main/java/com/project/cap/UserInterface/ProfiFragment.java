package com.project.cap.UserInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.cap.Entity.InterestData;
import com.project.cap.Entity.SocialData;
import com.project.cap.Entity.UserDetails;
import com.project.cap.Logic.LogicController;
import com.project.cap.Logic.Util.UserCache;
import com.project.cap.R;
import com.project.cap.UserInterface.ReycleAdapterPackage.RecyclerAdapterInterestsOnEditProfile;
import com.project.cap.UserInterface.ReycleAdapterPackage.RecyclerAdapterSocialsOnEditProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfiFragment extends Fragment{
    //UI ELEMENTS
    private TextView forename;
    private TextView lastname;
    private ImageView profilePic;
    private RadioGroup genderGroup;
    private RecyclerView socialRecycler;
    private RecyclerView interestRecycler;

    //

    /**
     * What needs to be instantiated at the creation of the view  (first time open/use).
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.own_account_page, container, false);

        //get access to all fields to be edited later
        profilePic = root.findViewById(R.id.profileAvatar);
        forename = root.findViewById(R.id.ForenameField);
        lastname = root.findViewById(R.id.LastnameField);
        genderGroup = root.findViewById(R.id.radioGroupeGenderEdit);
        socialRecycler = root.findViewById(R.id.socialeRecyclerProfile);
        interestRecycler = root.findViewById(R.id.interestRecyclerProfile);
        //
        //Load the data into the view
        UserDetails me = null;
        List<SocialData> socialDataList = null;
        List<InterestData> interestDataList = null;

        try {
            me = LogicController.getInstance()
                    .getFutureGateway()
                    .getUserDetailsAsync(LogicController.getInstance().getUserCache().getLocalUid()).get();

            socialDataList = (ArrayList<SocialData>) LogicController.getInstance()
                    .getFutureGateway()
                    .getSocialsByIdAsync(LogicController.getInstance().getUserCache().getLocalUid()).get();

            interestDataList = (ArrayList<InterestData>) LogicController.getInstance()
                    .getFutureGateway()
                    .getInterestsByIdAsync(LogicController.getInstance().getUserCache().getLocalUid()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        byte[] decodedString = Base64.decode(me.getProfilePicture(), Base64.NO_WRAP);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //TODO uncomment when every single user has a pic
        profilePic.setImageBitmap(decodedByte);
        forename.setText(me.getForename());
        lastname.setText(me.getLastname());
        switch (me.getGender().toLowerCase()) {
            case "female":
                genderGroup.check(R.id.femaleButton);
                break;
            case "male":
                genderGroup.check(R.id.maleButton);
                break;
            case "divers":
                genderGroup.check(R.id.diversButton);
                break;
        }
        this.recyclerInitHelper(socialRecycler,new RecyclerAdapterSocialsOnEditProfile(socialDataList));
        this.recyclerInitHelper(interestRecycler,new RecyclerAdapterInterestsOnEditProfile(interestDataList));
        //
        return root;
    }
    /**
     * Simplifies init of recyclers.
     * @param recycler
     * @param adapter
     */
    private void recyclerInitHelper(RecyclerView recycler, RecyclerView.Adapter adapter){
        RecyclerView.LayoutManager layoutManagerSocials = new LinearLayoutManager(this.getContext());
        recycler.setLayoutManager(layoutManagerSocials);
        recycler.setAdapter(adapter);
    }

}
