package com.project.cap.UserInterface.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.cap.Entity.Interest;
import com.project.cap.Entity.InterestData;
import com.project.cap.Entity.SocialData;
import com.project.cap.Entity.UserDetails;
import com.project.cap.Logic.LogicController;
import com.project.cap.Logic.Util.InterestSocialLookUp;
import com.project.cap.R;
import com.project.cap.UserInterface.CoreActivity;
import com.project.cap.UserInterface.ReycleAdapterPackage.RecyclerAdapterInterestOnMap;
import com.project.cap.UserInterface.ReycleAdapterPackage.RecyclerAdapterSocialOnMap;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import java.util.ArrayList;


public class ProfileWindow {

    ImageView profilePicture;
    TextView name;
    TextView age;
    TextView distance;
    TextView bioText;
    RecyclerView interests;
    Button getInContact;
    ScrollView infoWindowRoot;


    public ProfileWindow() {
    }

    public ImageView getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ImageView profilePicture) {
        this.profilePicture = profilePicture;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getAge() {
        return age;
    }

    public void setAge(TextView age) {
        this.age = age;
    }

    public TextView getDistance() {
        return distance;
    }

    public void setDistance(TextView distance) {
        this.distance = distance;
    }

    public TextView getBioText() {
        return bioText;
    }

    public void setBioText(TextView bioText) {
        this.bioText = bioText;
    }

    public RecyclerView getInterests() {
        return interests;
    }

    public void setInterests(RecyclerView interests) {
        this.interests = interests;
    }

    public Button getGetInContact() {
        return getInContact;
    }

    public void setGetInContact(Button getInContact) {
        this.getInContact = getInContact;
    }

    public ScrollView getInfoWindowRoot() {
        return infoWindowRoot;
    }

    public void setInfoWindowRoot(ScrollView infoWindowRoot) {
        this.infoWindowRoot = infoWindowRoot;
    }

    public void setVisible(){
        infoWindowRoot.setVisibility(View.VISIBLE);
        System.out.println("PW after setVisible: " + infoWindowRoot.getVisibility());
    }

    public void setUnVisible(){
        infoWindowRoot.setVisibility(View.GONE);
    }
}
