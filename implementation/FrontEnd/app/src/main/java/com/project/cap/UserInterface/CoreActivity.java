package com.project.cap.UserInterface;

import android.Manifest;
import android.annotation.SuppressLint;


import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.project.cap.Entity.UserOnMapMarkerData;
import com.project.cap.Logic.LogicController;
import com.project.cap.R;
import com.project.cap.UserInterface.Utils.InfoWindowMarker;
import com.project.cap.UserInterface.Utils.UserOnMap;
import com.project.cap.UserInterface.Utils.ProfileWindow;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *
 * Heart of Frontend
 *
 * Handling GPS permissions & fragment instantiation (Map/Notification)
 *
 */
public class CoreActivity extends AppCompatActivity implements LocationListener {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    private BottomNavigationView navigation = null;
    private Fragment selected;
    //public static ProfileWindow pw = new ProfileWindow();
    //List of markers/other users
    ArrayList<UserOnMap> userObjects = new ArrayList<>();
    private UserOnMap selfMarker;
    public Context cxt;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.core_activity_layout);
        /*
        //pw = new ProfileWindow();
        pw.setProfilePicture(findViewById(R.id.profilePicture));
        pw.setName(findViewById(R.id.name));
        pw.setAge(findViewById(R.id.age));
        pw.setDistance(findViewById(R.id.distance));
        pw.setBioText(findViewById(R.id.bioText));
        pw.setInterests(findViewById(R.id.interests));
        pw.setGetInContact(findViewById(R.id.getInContact));
        pw.setInfoWindowRoot(findViewById(R.id.infoWindowRoot));
        pw.getInfoWindowRoot().setVisibility(8);
        System.out.println("CORE at onCreate: " + pw.getInfoWindowRoot().getVisibility());
         */
                //navigation bar:
        //Intent intent = new Intent(this, MapFragment.class);
        //startActivity(intent);


        //

        navigation = findViewById(R.id.bottomNavigation);
        navigation.setSelectedItemId(findViewById(R.id.bottomNavigation).getId());
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profi:
                        com.project.cap.UserInterface.ProfiFragment ProfiFragment = new ProfiFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, ProfiFragment).commit();

                        break;
                    case R.id.noti:
                        //replace another fragment
                        com.project.cap.UserInterface.NotiFragment notiFragment = new NotiFragment();
                        notiFragment.setCxt(cxt);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, notiFragment).commit();
                        break;
                    case R.id.mapItem:
                        //replace another fragment
                        com.project.cap.UserInterface.MapFragment mapFragment = new MapFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, mapFragment).commit();
                        selected = mapFragment;
                        break;

                    }
                    return true;

                }
            });
        cxt = this.getApplicationContext();
        }
        // LocationListener

        @Override
        public void onLocationChanged (Location location){
            //ToDo
            //Change own location within markerList
        }

        @Override
        public void onProviderDisabled (String provider){
            Log.d("Latitude", "disable");
        }

        @Override
        public void onProviderEnabled (String provider){
            Log.d("Latitude", "enable");
        }

        @Override
        public void onStatusChanged (String provider,int status, Bundle extras){
            Log.d("Latitude", "status");
        }


        /*private List<UserOnMap> transformUserIntoMarkers(List<User> users){
        List<UserOnMap> toReturn = new ArrayList<>();//return this as finished
        //for each user create the display marker
        users.stream().forEach((u) -> {
            UserOnMap markerVar = new UserOnMap(map,u);//create the object with map and user
            var customInfoWindow = new InfoWindowMarker(R.layout.on_map_item, map, markerVar);//create the info window
            markerVar.setInfoWindow(customInfoWindow);//assign the info window
            toReturn.add(markerVar);
        });


        return toReturn;//satisfy java return statement
    }*/

}



