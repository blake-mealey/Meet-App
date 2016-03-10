package com.example.aidin.meetapp;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Blake on 3/9/2016.
 */
public class Test extends ActivityInstrumentationTestCase2<MainPage> {
    public Test() {
        super(MainPage.class);
    }

    public void testActivityExists() {
        MainPage activity = getActivity();
        assertNotNull(activity);
    }

    public void testEquidistantNoLocations() {
        MainPage activity = getActivity();
        MeetUp meetUp = activity.getMeetUp();
        meetUp.clearMeetUpLocations();

        assertNull(meetUp.getMeetUpLocation());
        assertEquals(0, meetUp.getMemberCount());
    }

    public void testEquidistantOneLocation() {
        MainPage activity = getActivity();
        MeetUp meetUp = activity.getMeetUp();
        meetUp.clearMeetUpLocations();

        meetUp.addLocation(new LatLng(0, 0));

        assertLatLngEquals("", new LatLng(0, 0), meetUp.getMeetUpLocation());
        assertEquals(1, meetUp.getMemberCount());
    }

    public void testEquidistantTwoLocations() {
        MainPage activity = getActivity();
        MeetUp meetUp = activity.getMeetUp();
        meetUp.clearMeetUpLocations();

        meetUp.addLocation(new LatLng(1, 1));
        meetUp.addLocation(new LatLng(2, 2));

        assertLatLngEquals("", new LatLng(3.0 / 2.0, 3.0 / 2.0), meetUp.getMeetUpLocation());
        assertEquals(2, meetUp.getMemberCount());
    }

    public void testEquidistantThreeLocations() {
        MainPage activity = getActivity();
        MeetUp meetUp = activity.getMeetUp();
        meetUp.clearMeetUpLocations();

        meetUp.addLocation(new LatLng(0, 0));
        meetUp.addLocation(new LatLng(1, 2));
        meetUp.addLocation(new LatLng(3, 4));

        assertLatLngEquals("", new LatLng(4.0 / 3.0, 2.0), meetUp.getMeetUpLocation());
        assertEquals(3, meetUp.getMemberCount());
    }

    public void testEquidistantThreeLocationsTwoIdentical() {
        MainPage activity = getActivity();
        MeetUp meetUp = activity.getMeetUp();
        meetUp.clearMeetUpLocations();

        meetUp.addLocation(new LatLng(0, 0));
        meetUp.addLocation(new LatLng(0, 0));
        meetUp.addLocation(new LatLng(5, 7));

        assertLatLngEquals("", new LatLng(5.0 / 3.0, 7.0 / 3.0), meetUp.getMeetUpLocation());
        assertEquals(3, meetUp.getMemberCount());
    }

    public void testEquidistantTenLocations() {
        MainPage activity = getActivity();
        MeetUp meetUp = activity.getMeetUp();
        meetUp.clearMeetUpLocations();

        meetUp.addLocation(new LatLng(0, 9));
        meetUp.addLocation(new LatLng(1, 8));
        meetUp.addLocation(new LatLng(2, 7));
        meetUp.addLocation(new LatLng(3, 6));
        meetUp.addLocation(new LatLng(4, 5));
        meetUp.addLocation(new LatLng(0, 4));
        meetUp.addLocation(new LatLng(1, 3));
        meetUp.addLocation(new LatLng(2, 2));
        meetUp.addLocation(new LatLng(3, 1));
        meetUp.addLocation(new LatLng(4, 0));

        assertLatLngEquals("", new LatLng(2.0, 4.5), meetUp.getMeetUpLocation());
        assertEquals(10, meetUp.getMemberCount());
    }

    public void testEquidistantNegativesQuad1() {
        MainPage activity = getActivity();
        MeetUp meetUp = activity.getMeetUp();
        meetUp.clearMeetUpLocations();

        meetUp.addLocation(new LatLng(-20, 100));
        meetUp.addLocation(new LatLng(15, 110));

        assertLatLngEquals("", new LatLng(-2.5, 105), meetUp.getMeetUpLocation());
        assertEquals(2, meetUp.getMemberCount());
    }

    public void testEquidistantNegativesQuad2() {
        MainPage activity = getActivity();
        MeetUp meetUp = activity.getMeetUp();
        meetUp.clearMeetUpLocations();

        meetUp.addLocation(new LatLng(-20, -170));
        meetUp.addLocation(new LatLng(15, 110));

        System.out.println("quad2: ");
        LatLng v = meetUp.getMeetUpLocation();


        assertLatLngEquals(v.toString(), new LatLng(-2.5, 150), v);
        assertEquals(2, meetUp.getMemberCount());
    }

    public void testEquidistantNegativesQuad3() {
        MainPage activity = getActivity();
        MeetUp meetUp = activity.getMeetUp();
        meetUp.clearMeetUpLocations();

        meetUp.addLocation(new LatLng(-20, -80));
        meetUp.addLocation(new LatLng(15, 110));

        LatLng v = meetUp.getMeetUpLocation();

        assertLatLngEquals(v.toString(), new LatLng(-2.5, -165), v);
        assertEquals(2, meetUp.getMemberCount());
    }

    public void testEquidistantNegativesQuad4() {
        MainPage activity = getActivity();
        MeetUp meetUp = activity.getMeetUp();
        meetUp.clearMeetUpLocations();

        meetUp.addLocation(new LatLng(-20, 10));
        meetUp.addLocation(new LatLng(15, 110));

        System.out.println("quad4: ");
        LatLng v = meetUp.getMeetUpLocation();

        assertLatLngEquals(v.toString(), new LatLng(-2.5, 60), v);
        assertEquals(2, meetUp.getMemberCount());
    }

    private void assertLatLngEquals(String message, LatLng pos1, LatLng pos2) {
        assertEquals(message, true, Math.abs(pos1.latitude - pos2.latitude) < .1);
        assertEquals(message, true, Math.abs(pos1.longitude - pos2.longitude) < .1);
    }
}