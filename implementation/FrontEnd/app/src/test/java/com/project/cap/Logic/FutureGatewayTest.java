package com.project.cap.Logic;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.cap.Backend.BackendController;
import com.project.cap.Backend.retrofit.Responses.GetAllSocialsResponse;
import com.project.cap.Backend.retrofit.RetrofitController;
import com.project.cap.Entity.Interest;
import com.project.cap.Entity.InterestData;
import com.project.cap.Entity.SocialData;
import com.project.cap.Entity.Socials;
import com.project.cap.Entity.UserOnMapMarkerData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FutureGatewayTest {

    @Mock
    RetrofitController retroCon;

    @Mock
    BackendController backCon;

    FutureGateway futureGateway;

    @Before
    public void setUp() {
        futureGateway = LogicController.getInstance().getFutureGateway();
        retroCon = mock(RetrofitController.class);
        setMock(retroCon);
    }

    private void setMock(Object mock) {
        try {
            Field instance = mock.getClass().getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void getAllInterestsAsyncTest() throws ExecutionException, InterruptedException {
        var interestList = new ArrayList<Interest>();

        Interest interest1 = new InterestData(1, 1, "name", "desc", "date", "profi");
        Interest interest2 = new InterestData(2, 2, "name2", "desc2", "date2", "profi2");
        int hashRef = interest2.hashCode();

        interestList.add(interest1);
        interestList.add(interest2);

        when(retroCon.getAllInterests()).thenReturn(interestList);
        List<Interest> testReturn = futureGateway.getAllInterestsAsync().get();

        assertTrue(testReturn.contains(interest1));
        assertTrue(testReturn.get(1).hashCode() == hashRef);
        verify(retroCon, atLeast(1)).getAllInterests();
    }

    @Test
    public void getAllSocialsAsync() throws ExecutionException, InterruptedException {
        var socialList = new ArrayList<GetAllSocialsResponse>();

        GetAllSocialsResponse social1 = new GetAllSocialsResponse(1, "name3");
        GetAllSocialsResponse social2 = new GetAllSocialsResponse(2, "name2");
        int hashRef = social2.hashCode();

        socialList.add(social1);
        socialList.add(social2);

        when(retroCon.getAllSocials()).thenReturn(socialList);
        List<GetAllSocialsResponse> testReturn = futureGateway.getAllSocialsAsync().get();

        assertTrue(testReturn.contains(social1));
        assertTrue(testReturn.get(1).hashCode() == hashRef);
    }

    @Test
    public void getLocationInfoOfUsersAsync() throws ExecutionException, InterruptedException {
        var locs = new ArrayList<UserOnMapMarkerData>();
        locs.add(new UserOnMapMarkerData(12d, 12d, "string", "string2"));
        when(retroCon.getLocationInfoOfUsers()).thenReturn(locs);

        List<UserOnMapMarkerData> testReturn = futureGateway.getLocationInfoOfUsersAsync().get();

        assertFalse(testReturn.isEmpty());
        assertTrue(testReturn.get(0).getLat() == 12);
    }

    @Test
    public void getInterestsByIdAsync() throws ExecutionException, InterruptedException {
        var interestList = new ArrayList<InterestData>();

        InterestData interest1 = new InterestData(1, 1, "name", "desc", "date", "profi");
        InterestData interest2 = new InterestData(2, 2, "name2", "desc2", "date2", "profi2");
        int hashRef = interest2.hashCode();

        interestList.add(interest1);
        interestList.add(interest2);

        when(retroCon.getInterestsById("rightID")).thenReturn(interestList);
        List<InterestData> falseReturn = futureGateway.getInterestsByIdAsync("false").get();
        List<InterestData> rightReturn = futureGateway.getInterestsByIdAsync("rightID").get();

        assertTrue(falseReturn.isEmpty());
        assertFalse(rightReturn.isEmpty());
        assertTrue(rightReturn.contains(interest1));
    }

    @Test
    public void getSocialsByIdAsync() throws ExecutionException, InterruptedException {
        var socialList = new ArrayList<SocialData>();

        SocialData social1 = new SocialData(1, "name3", "username1");
        SocialData social2 = new SocialData(2, "name2", "User2");
        int hashRef = social2.hashCode();

        socialList.add(social1);
        socialList.add(social2);

        when(retroCon.getSocialsById("rightID")).thenReturn(socialList);
        List<SocialData> falseReturn = futureGateway.getSocialsByIdAsync("false").get();
        List<SocialData> rightReturn = futureGateway.getSocialsByIdAsync("rightID").get();

        assertTrue(falseReturn.isEmpty());
        assertFalse(rightReturn.isEmpty());
        assertTrue(rightReturn.contains(social2));
    }

    @After
    public void resetSingleton() throws Exception {
        Field instance = BackendController.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }
}