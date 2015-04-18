package com.example.android.sunshine.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SunshineService extends IntentService {

    public static final String LOCATION_QUERY_EXTRA = "lqe";

    private final FetchWeatherTask fetchWeatherTask;

    public SunshineService() {
        super("Sunshine");
        fetchWeatherTask = new FetchWeatherTask(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String locationQuery = intent.getStringExtra(LOCATION_QUERY_EXTRA);
        fetchWeatherTask.doInBackground(locationQuery);
    }

    static public class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent sendIntent = new Intent(context, SunshineService.class);
            sendIntent.putExtra(LOCATION_QUERY_EXTRA, intent.getStringExtra(LOCATION_QUERY_EXTRA));
            context.startService(sendIntent);
        }
    }
}