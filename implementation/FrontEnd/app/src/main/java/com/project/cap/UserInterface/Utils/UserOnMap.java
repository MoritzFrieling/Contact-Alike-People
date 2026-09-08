package com.project.cap.UserInterface.Utils;

import android.graphics.drawable.Drawable;

import com.project.cap.Entity.UserReference;
import com.project.cap.UserInterface.CoreActivity;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class UserOnMap extends Marker {

    private UserReference localUser;
    private String UID;

    public UserOnMap(MapView mapView, UserReference user) {
        super(mapView);
        this.localUser = user;
    }

    public UserOnMap(MapView mapView, String uId, String gender) {
        super(mapView);
        localUser = new UserReference();
        localUser.setGender(gender);
        this.UID = uId;

    }

    public UserReference getUser() {
        return localUser;
    }

    public IGeoPoint getPoint() {
        return super.getPosition();
    }

    //No cool infoWindow ;(
    /*@Override
    protected boolean onMarkerClickDefault(Marker marker, MapView mapView) {
        CoreActivity.pw.setVisible();
        System.out.println("UserOnMap " + CoreActivity.pw.getInfoWindowRoot().getVisibility());
        if (super.mPanToView) {
            mapView.getController().animateTo(marker.getPosition());
        }
        return true;
    }*/

    @Override
    public void setIcon(Drawable icon) {
        super.setIcon(icon);
        setAnchor(super.ANCHOR_CENTER, super.ANCHOR_BOTTOM);
    }

    // todo more methods...

    public String getUID() {
        return UID;
    }
}
