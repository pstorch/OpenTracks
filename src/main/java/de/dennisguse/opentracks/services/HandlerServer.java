package de.dennisguse.opentracks.services;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import de.dennisguse.opentracks.content.data.TrackPoint;

public class HandlerServer {
    private String TAG = HandlerServer.class.getSimpleName();

    private List<HandlerSubscriber> subscriberList;

    private static HandlerServer handlerServerInstance;
    private final LocationHandler locationHandler;
    private final Context context;

    public static HandlerServer getInstance(Context context) {
        if (handlerServerInstance == null) {
            handlerServerInstance = new HandlerServer(context);
        }
        return handlerServerInstance;
    }

    private HandlerServer(Context context) {
        this.subscriberList = new ArrayList<>();
        this.locationHandler = new LocationHandler();
        this.context = context;
    }

    public void subscribe(HandlerSubscriber s) {
        if (subscriberList.isEmpty()) {
            locationHandler.onStart(context);
        }
        if (!subscriberList.contains(s)) {
            subscriberList.add(s);
        }
    }

    public void unsubscribe(HandlerSubscriber s) {
        if (subscriberList.contains(s)) {
            subscriberList.remove(s);
            if (subscriberList.isEmpty()) {
                locationHandler.onStop();
            }
        }
    }

    public void sendTrackPoint(TrackPoint trackPoint, int recordingGpsAccuracy) {
        for (HandlerSubscriber s : subscriberList) {
            s.newTrackPoint(trackPoint, recordingGpsAccuracy);
        }
    }

    public interface HandlerSubscriber {
        void newTrackPoint(TrackPoint trackPoint, int gpsAccuracy);
    }
}

