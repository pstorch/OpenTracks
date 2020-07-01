package de.dennisguse.opentracks.services.handlers;

import android.content.Context;

import de.dennisguse.opentracks.content.data.TrackPoint;

public class HandlerServer {
    private String TAG = HandlerServer.class.getSimpleName();

    private LocationHandler locationHandler;
    private HandlerServerInterface service;
    private Context context;

    public HandlerServer(Context context, HandlerServerInterface service) {
        this.locationHandler = new LocationHandler(this);
        this.service = service;
        this.context = context;
    }

    public void start() {
        locationHandler.onStart();
    }

    public void stop() {
        locationHandler.onStop();
    }

    public Context getContext() {
        return context;
    }

    public void sendTrackPoint(TrackPoint trackPoint, int recordingGpsAccuracy) {
        service.newTrackPoint(trackPoint, recordingGpsAccuracy);
    }

    public interface HandlerServerInterface {
        void newTrackPoint(TrackPoint trackPoint, int gpsAccuracy);
    }
}

