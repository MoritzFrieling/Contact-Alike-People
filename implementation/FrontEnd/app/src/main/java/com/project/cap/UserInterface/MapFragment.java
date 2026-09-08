package com.project.cap.UserInterface;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.cap.Entity.UserOnMapMarkerData;
import com.project.cap.Logic.LogicController;
import com.project.cap.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import com.project.cap.UserInterface.Utils.InfoWindowMarker;
import com.project.cap.UserInterface.Utils.UserOnMap;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {
    private MapView map = null;
    private View root;
    private SeekBar bar;
    private Switch switchInterests;
    private TextView txtV;
    public MapEventsOverlay closeWindowHandler = new MapEventsOverlay(getActivity(), new MapEventsReceiver() {
        @Override
        public boolean singleTapConfirmedHelper(GeoPoint p) {
            InfoWindow.closeAllInfoWindowsOn(map);
            return true;
        }

        @Override
        public boolean longPressHelper(GeoPoint p) {
            return false;
        }
    });
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private LocationManager locationManager;
    // location Manager
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    continueCreation();
                } else {
                    System.out.println("Problems occurred during permissions gathering");
                }
            });
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.map_fragment_layuout, container, false);
        Context ctx = getActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        map = root.findViewById(R.id.map);
        bar = root.findViewById(R.id.seekBar);
        switchInterests = root.findViewById(R.id.switch5);
        txtV = root.findViewById(R.id.textCurrentValue);
        FloatingActionButton  floatingButton = root.findViewById(R.id.buttonOfFilters);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtersOnClickButton(view);
            }
        });
        if (ContextCompat.checkSelfPermission(
                ctx, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            continueCreation();
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        this.seekBarHelper();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root=view;
        root.setId(View.generateViewId());
    }

    //Core Activity Methods

    private void seekBarHelper(){

        txtV.setText(String.valueOf(bar.getProgress()));
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtV.setText(String.valueOf(bar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                loadUsers();
            }
        });
    }


    private void initMap() {
        map.getController().setZoom(10.0d);
        map.setMinZoomLevel(6.0d);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(0, closeWindowHandler);
    }

    private void continueCreation() {
        if (ContextCompat.checkSelfPermission(
                getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            initMap();
            // You can use the API that requires the permission.

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, (LocationListener) getActivity());
            //Set own location
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            /**
             * Doc: https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Java)
             */
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LogicController.getInstance().getFutureGateway().updateLocationAsync(loc);
            LogicController.getInstance().getUserCache().setOwnUserLocation(loc);
            //TODO send device token to luca

            //Load users from backend
            loadUsers();
            map.getController().setCenter(selfMarker.getPoint());
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void setSelfAndOtherUsersOnMap() {

        selfMarker = new UserOnMap(map,  LogicController.getInstance().getUserCache().getLocalUid(),LogicController.getInstance().getUserCache().getOwnGender());
        selfMarker.setPosition(new GeoPoint(LogicController.getInstance().getUserCache().getOwnUserLocation().getLatitude(),
                LogicController.getInstance().getUserCache().getOwnUserLocation().getLongitude())
        );

        configureMarkerAndInfoWindow(selfMarker);
        userObjects.add(selfMarker);

        userObjects.stream()
                .forEach(userOnMap -> {
                    configureMarkerAndInfoWindow(userOnMap);
                    map.getOverlays().add(userOnMap);
                });
    }

    private void loadUsers(){
        map.getOverlays().clear();
        userObjects.clear();

        map.getOverlays().add(closeWindowHandler);
        List<UserOnMapMarkerData> userLocations;

        //Filter for Location Distance
        userLocations = LogicController.getInstance().getFilterProvider().applyLocationFilter(bar.getProgress());
        if(userLocations == null) return;
        if(switchInterests.isChecked()){
            userLocations = LogicController.getInstance().getFilterProvider().filterForCommonInterests(userLocations);
        }
        //setting object markers
        userLocations.stream()
                .forEach(uLoc -> {
                    var tempUserMarker = new UserOnMap(map, uLoc.getUid(),uLoc.getGender());
                    tempUserMarker.setPosition(new GeoPoint(uLoc.getLat(), uLoc.getLng()));
                    userObjects.add(tempUserMarker);
                });
        //LAST CALL
        setSelfAndOtherUsersOnMap(); //ToDo configure each Marker/InfoWindow with received data, celebrate

        map.invalidate();
    }

    private void configureMarkerAndInfoWindow(UserOnMap marker) {
        var customInfoWindow = new InfoWindowMarker(R.layout.on_map_item, map, marker); //ToDo set content of infoWindow in UserOnMap.class
        //customInfoWindow.setName(customInfoWindow.getView().findViewById(R.id.TextViewInfoWindow));

        var gender = marker.getUser().getGender();
        Bitmap bm;
        if(gender == null) gender = "Diverse";
        switch (gender){
            case "Male":    bm = resizeBitmap(R.drawable.marker_m, 150, 150);
                break;
            case "Female":  bm = resizeBitmap(R.drawable.marker_f, 150, 150);
                break;
            case "Diverse": bm = resizeBitmap(R.drawable.marker_d, 150, 150);
                break;
            default: bm = resizeBitmap(R.drawable.pin_default, 100, 100);
                break;

        }
        marker.setIcon(new BitmapDrawable(getResources(), bm));
        marker.setInfoWindow(customInfoWindow);

    }

    public Bitmap resizeBitmap(int drawableId,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),drawableId);
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }
    //HELPER for map

    @Override
    public void onResume() {
        super.onResume();
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSIONS_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    continueCreation();
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }

    /**
     * Reload Users with current filters
     * @param view
     */
    public void filterSwitchMethod(View view) {
        this.loadUsers();
    }

    //List of markers/other users
    ArrayList<UserOnMap> userObjects = new ArrayList<>();
    private UserOnMap selfMarker;

    /**
     * OnClick method for the floating button. Shows the layout when clicked and hides it when it is visible.
     * @param view
     */
    @SuppressLint("ResourceType")
    public void filtersOnClickButton(View view) {
        CardView CardLayout =root.findViewById(R.id.filterCardView);
        FloatingActionButton actionButton = root.findViewById(R.id.buttonOfFilters);
        if(CardLayout.getVisibility() == View.GONE){
            CardLayout.setVisibility(View.VISIBLE);
            //actionButton.setImageDrawable(this.getDrawable(android.R.attr.actionModeFindDrawable));
            //?android:attr/actionModeFindDrawable
            switchInterests.setOnClickListener(x -> {
                filterSwitchMethod(x);
            });
        }else{
            CardLayout.setVisibility(View.GONE);
            //Reference:	@android:drawable/ic_menu_close_clear_cancel
            // actionButton.setImageDrawable(this.getDrawable(android.R.drawable.ic_menu_close_clear_cancel));
        }
    }



}
