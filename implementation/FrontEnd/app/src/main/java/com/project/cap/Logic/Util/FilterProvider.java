package com.project.cap.Logic.Util;

import android.location.Location;

import com.project.cap.Entity.InterestData;
import com.project.cap.Entity.UserOnMapMarkerData;
import com.project.cap.Logic.LogicController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *
 * LocationFilter:
 *      Calculate the distance between local user and provided ones.
 *      Check, whether the calculated results fits within the allowed distance (filterValue).
 *      Return allowed users.
 *
 * InterestFilter:
 *      Show only users on the map, who share at least one interest with the local user.
 */
public class FilterProvider {

    private float MAX_DISTANCE_LOADED = 100;

    public List<UserOnMapMarkerData> applyLocationFilter(float filterValue) {
        if(filterValue < MAX_DISTANCE_LOADED) {
            return applyLocationFilter(LogicController.getInstance().getUserCache().getUserLocations(), filterValue);
        }

        try {
            var userLocations = LogicController.getInstance().getFutureGateway().getLocationInfoOfUsersAsync().get();
            var resultList = applyLocationFilter(userLocations, filterValue);

            LogicController.getInstance().getUserCache().clearUserLocations();
            resultList.forEach(loc -> {
                LogicController.getInstance().getUserCache().addUserLocationsAndIdentifier(loc);
            });
            MAX_DISTANCE_LOADED = filterValue;
            return resultList;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<UserOnMapMarkerData> applyLocationFilter(List<UserOnMapMarkerData> userOnMapMarkerData, float allowedDistance) {
        var legalUserLocations = new ArrayList<UserOnMapMarkerData>();
        var mappedDistance = calculateDistanceToLocalUserForEachEntry(userOnMapMarkerData);
        mappedDistance.keySet()
                .forEach(userLocation -> {
                    if(mappedDistance.get(userLocation) <= allowedDistance) {
                        legalUserLocations.add(userLocation);
                    }
                });
        return legalUserLocations;
    }
    /**
     *
     * @param userOnMapMarkerData list of locations
     * @return array that holds each distance to own location in km
     */
    private HashMap<UserOnMapMarkerData, Float> calculateDistanceToLocalUserForEachEntry(List<UserOnMapMarkerData> userOnMapMarkerData) {
        HashMap<UserOnMapMarkerData, Float> locationsDistance = new HashMap<>();
        for (int i = 0; i < userOnMapMarkerData.size(); i++) {
            locationsDistance.put(userOnMapMarkerData.get(i), calculateDistanceToLocalUser(userOnMapMarkerData.get(i)));
        }

        return locationsDistance;
    }

    private float calculateDistanceToLocalUser(UserOnMapMarkerData uLoc) {
        float[] result = new float[1];
        Location.distanceBetween(LogicController.getInstance().getUserCache().getOwnUserLocation().getLatitude(),
                LogicController.getInstance().getUserCache().getOwnUserLocation().getLongitude(),
                uLoc.getLat(),
                uLoc.getLng(),
                result
        );
        return result[0] / 1000;
    }

    public List<UserOnMapMarkerData> filterForCommonInterests(List<UserOnMapMarkerData> listOfMarkers){
        ArrayList<UserOnMapMarkerData> toReturn = new ArrayList<>();
        ArrayList<InterestData> listOfInterests = new ArrayList<>();
        try {
            listOfInterests.addAll(LogicController.getInstance()
                    .getFutureGateway()
                    .getInterestsByIdAsync(LogicController.getInstance()
                            .getUserCache()
                            .getLocalUid())
                    .get());
            System.out.println("OWN INTEREST ID " + listOfInterests.get(0).getType());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listOfMarkers.stream().forEach(u -> {
            List<InterestData> interests = null;
            try {
                interests = LogicController.getInstance().getFutureGateway().getInterestsByIdAsync(u.getUid()).get();

                if(interests == null){//not succesfull
                    return;
                }
                //search for same ids
                interests.stream().forEach(i -> {
                    System.out.println("OTHER INTEREST ID " + i.getType());
                    if(listOfInterests.stream().anyMatch(i2 -> i2.getType() == i.getType())){
                        System.out.println("OTHER INTEREST ID " + i.getType());
                        toReturn.add(u);
                    }
                });
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        return toReturn;
    }
}
