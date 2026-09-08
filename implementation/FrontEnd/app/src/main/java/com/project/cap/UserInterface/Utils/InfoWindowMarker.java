package com.project.cap.UserInterface.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.cap.Entity.InterestData;
import com.project.cap.Entity.SocialData;
import com.project.cap.Logic.LogicController;
import com.project.cap.Logic.Util.InterestSocialLookUp;
import com.project.cap.R;
import com.project.cap.UserInterface.ReycleAdapterPackage.RecyclerAdapterInterestOnMap;
import com.project.cap.UserInterface.ReycleAdapterPackage.RecyclerAdapterSocialOnMap;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 * Custom info window of marker
 *
 */
public class InfoWindowMarker extends MarkerInfoWindow {

    ImageView profilePic;
    TextView name;
    TextView birthdate;
    RecyclerView interests;
    RecyclerView socials;
    Button getInContact;

    public InfoWindowMarker(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }

    public InfoWindowMarker(int layoutResId, MapView mapView, UserOnMap marker) {
        super(layoutResId, mapView);
        setRelatedObject(marker);
        getView().setId(View.generateViewId());
    }

    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void onOpen(Object item) {
        //TODO send request to backend to fill out infoWindow
        //this.mView;//the layout to
        //setting objects
        getView().setId(View.generateViewId());

        profilePic = mView.findViewById(R.id.onMapPic);
        name = mView.findViewById(R.id.onMapName);
        birthdate = mView.findViewById(R.id.onMapBirthdate);
        interests = mView.findViewById(R.id.onMapInterestRecycler);
        socials = mView.findViewById(R.id.onMapSocialRecycler);
        getInContact = mView.findViewById(R.id.onMapRequestContact2);

        UserOnMap userOnM = (UserOnMap) item;
        try {

            var interestResult = (ArrayList<InterestData>) LogicController.getInstance().getFutureGateway().getInterestsByIdAsync(userOnM.getUID()).get();
            var socialResult = (ArrayList<SocialData>) LogicController.getInstance().getFutureGateway().getSocialsByIdAsync(userOnM.getUID()).get();
            var userDetails = LogicController.getInstance().getFutureGateway().getUserDetailsAsync(userOnM.getUID()).get();


            if(interestResult.isEmpty() || socialResult.isEmpty() || userDetails==null) {
                //throw new IllegalArgumentException();
                System.out.println("+++ LACK OF INFORMATION +++ \n \n \n ");
                System.out.println("Interests: " + interestResult.size());
                System.out.println("Socials: " + socialResult.size());
                System.out.println("Details: " + userDetails);
            }

            interestResult.forEach(i -> {
                i.setName(InterestSocialLookUp.interestDict.get(i.getType()));
                i.setStartDateToFormattedDate();
            });

            socialResult.forEach(s -> {
                s.setName(InterestSocialLookUp.socialDict.get(s.getId()));
            });

            byte[] decodedString = Base64.decode(userDetails.getProfilePicture(), Base64.NO_WRAP);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profilePic.setImageBitmap(decodedByte);
            name.setText(userDetails.getForename());


            LocalDate birthDate = userDetails.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int age = Period.between(birthDate, LocalDate.now()).getYears();

            //String justDate = userDetails.getBirthDate().toString();
            //String[] splitDate = justDate.split(" ");
            //String finalDate = splitDate[1] + "/" + splitDate[2] + "/" + splitDate[splitDate.length-1];
            String finalDate = ""+age;
            birthdate.setText(finalDate);

            //ToDo use lists from above

            recyclerInitHelper(interests, new RecyclerAdapterInterestOnMap(interestResult));
            recyclerInitHelper(socials, new RecyclerAdapterSocialOnMap(socialResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //setting fields

        //send request to user to get into contact
        getInContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO request user info/ get in contact procedure
                LogicController.getInstance().getFutureGateway().sendRequestAsync(userOnM.getUID());
            }
        });

    }
    /**
     * Simplifies init of recyclers.
     * @param recycler
     * @param adapter
     */
    private void recyclerInitHelper(RecyclerView recycler, RecyclerView.Adapter adapter){
        RecyclerView.LayoutManager layoutManagerSocials = new LinearLayoutManager(mView.getContext());//mView.getContext()?
        recycler.setLayoutManager(layoutManagerSocials);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onClose() {
    }

    @Override
    public void setRelatedObject(Object relatedObject) {
        super.setRelatedObject(relatedObject);
    }
}
