/*
package com.maxlore.edumanage.Activities.AdminActivities;

*/
/**
 * Created by maxlore on 27-Mar-17.
 *//*


import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.MapUtility.LatLngInterpolator;
import com.maxlore.edumanage.MapUtility.MapUtility;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTrackride.BusLocation;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTrackride.BusLocationResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTrackride.StopNames;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Admintrackridepagetwo extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final long TRIP_HISTORY_INTERVAL = 1000 * 4;
    private static final long TIME_OUT = 1000 * 2;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private ArrayList<BusLocation> busLocationArrayList;
    private String nextTopTime, address, curr;
    private ArrayList<StopNames> stopNamesArrayList;
    private String tripId, runningbus;
    private Marker markerBus, markerCurrent;
    private boolean polyLineVisible;
    private Double lat, longi;
    private Handler handler;
    private String drinumber, attennumber;
    private ImageView calldriver, callattendant, messagedriver, messageattendant;
    private TextView attendantname, drivername, tvmaxspeed, tvavgspeed;
    private boolean isMarkerRotating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admintrackridepagetwo);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.adminmappagetwo);
        attendantname = (TextView) findViewById(R.id.tvattendantname);
        calldriver = (ImageView) findViewById(R.id.call_driver);
        callattendant = (ImageView) findViewById(R.id.call_attendant);
        messagedriver = (ImageView) findViewById(R.id.message_driver);
        messageattendant = (ImageView) findViewById(R.id.message_attendant);

        drivername = (TextView) findViewById(R.id.tvDriverName);
        tvmaxspeed = (TextView) findViewById(R.id.tvmaxspeed);
        tvavgspeed = (TextView) findViewById(R.id.tvavgspeed);
        calldriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDriver();
            }
        });
        callattendant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAttendant();
            }
        });
        messagedriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageConfirmationdriver();
            }
        });
        messageattendant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageConfirmationAttendant();
            }
        });
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        runningbus = getIntent().getStringExtra("busno");
        busLocationArrayList = new ArrayList<>();
        stopNamesArrayList = new ArrayList<>();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        startRepeatingTask();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkForLocationEnable(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForLocationEnable(this);
    }

    private void messageConfirmationAttendant() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setTitle("Message:");


        edittext.setHint("Enter your message here");
        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String YouEditTextValue = edittext.getText().toString();
                submitMessageAttendant(String.valueOf(edittext.getText()));

            }
        });

        alert.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }


    private void messageConfirmationdriver() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setTitle("Message:");


        edittext.setHint("Enter your message here");
        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String YouEditTextValue = edittext.getText().toString();
                submitMessage(String.valueOf(edittext.getText()));

            }
        });

        alert.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    private void submitMessage(String ed) {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                final JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("mobile_no", drinumber);
                jsonObject.addProperty("message", ed);
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().sendmess(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());

                            Log.e("Json ", "Hhd --- " + object.toString());
                            Log.e("Json ", "Response --- " + response.getBody());
                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                Toast.makeText(getApplicationContext(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitMessageAttendant(String ed) {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("mobile_no", attennumber);
                jsonObject.addProperty("message", ed);
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().sendmess(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());

                            Log.e("Json ", "Hhd --- " + object.toString());
                            Log.e("Json ", "Response --- " + response.getBody());
                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                Toast.makeText(getApplicationContext(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        startLocationUpdates();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

    protected void startLocationUpdates() {
        Log.e("Location", "startLocationUpdates ----- startLocationUpdates");
        createLocationRequest();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler mHandler = new Handler();

    Runnable mHandlerTask = new Runnable() {
        @Override
        public void run() {
            getSingleTrack();
            mHandler.postDelayed(mHandlerTask, TRIP_HISTORY_INTERVAL);

        }
    };

    private void getSingleTrack() {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("running_bus", runningbus);
            if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            } else {
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
            }
            RetrofitAPI.getInstance(this).getApi().getsinglebustracking(params, new Callback<BusLocationResponse>() {


                @Override
                public void success(BusLocationResponse busLocationResponse, retrofit.client.Response response) {
                    try {
                        busLocationArrayList.clear();
                        stopNamesArrayList.clear();
                        stopNamesArrayList.addAll(busLocationResponse.getStops());
                        for (int i = 0; i < stopNamesArrayList.size(); i++) {
                            drawMarker(new LatLng(Double.parseDouble(String.valueOf(stopNamesArrayList.get(i).getLatitude())), Double.parseDouble(String.valueOf(stopNamesArrayList.get(i).getLongitude()))), i);
                        }
                        currentbusLocation(busLocationResponse.getBusLocation());
                        attendantname.setText("Attendant : " + busLocationResponse.getAttendant().getName());
                        drivername.setText("Driver : " + busLocationResponse.getDriver().getName());
                        drinumber = busLocationResponse.getDriver().getPhone();
                        attennumber = busLocationResponse.getAttendant().getPhone();
                        Log.e("attendantname", "---------" + attendantname);
                        Log.e("drivername", "---------" + drivername);
                        Log.e("dfata", "---------" + stopNamesArrayList);

                        updateMarkersingle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callConfirmation() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle("Confirmation");
        String message = "Are you sure to Call " + " ?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /// TODO call Api here
                callDriver();

            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void callDriver() {
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + drinumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    private void callConfirmationAttendant() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle("Confirmation");
        String message = "Are you sure to Call " + " ?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /// TODO call Api here
                callAttendant();

            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void callAttendant() {
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + attennumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    private void currentbusLocation(BusLocation busLocation) {
        busLocationArrayList.add(busLocation);
    }

    private void updateMarkersingle() {
        try {
            for (int i = 0; i < busLocationArrayList.size(); i++) {

                BusLocation busLocation = busLocationArrayList.get(i);
                tvavgspeed.setText("Avg speed : " + busLocationArrayList.get(i).getAvgSpeed() + "km/hr");
                tvmaxspeed.setText("Max speed : " + busLocationArrayList.get(i).getMaxSpeed() + "km/hr");
              */
/*  BusStop lon=busStopArrayList.get(i+1);*//*
*/
/*
            mMap.addMarker(new LatLng(Double.parseDouble(String.valueOf(adminBusDetail.getLatitude())), Double.parseDouble(String.valueOf(adminBusDetail.getLongitude())));*//*

                lat = Double.valueOf(busLocation.getLatitude());
                longi = Double.valueOf(busLocation.getLongitutde());
                LatLng latLng = new LatLng(Double.parseDouble(String.valueOf(busLocation.getLatitude())), Double.parseDouble(String.valueOf(busLocation.getLongitutde())));
                mMap.addMarker(new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car_top_view)));

            }
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (busLocationArrayList.size() > 1) {
                        BusLocation busLocation = busLocationArrayList.get(1);
                        LatLng latLng2 = new LatLng(Double.parseDouble(String.valueOf(busLocation.getLatitude())), Double.parseDouble(String.valueOf(busLocation.getLongitutde())));
                        animateMarker(latLng2, markerBus);
                    }
                    getRoute();
                }
            }, TIME_OUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void animateMarker(final LatLng destination, final Marker marker) {
        try {
            if (marker != null) {
                final LatLng startPosition = marker.getPosition();
                final LatLng endPosition = new LatLng(destination.latitude, destination.longitude);

                final float startRotation = marker.getRotation();

                final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(2000); // duration 5 second
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        try {
                            float v = animation.getAnimatedFraction();
                            LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);
                            marker.setPosition(newPosition);
//                        marker.setRotation(destination.getBearing());
//                        MapUtility.rotateMarker(marker, destination.getBearing(), googleMap);
//                        marker.setRotation(MapUtility.computeRotation(v, destination.getBearing(), 180));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                valueAnimator.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawMarker(LatLng point, int i) {
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();
        // Setting latitude and longitude for the marker
        markerOptions.position(point);
        // Adding marker on the Google Map
        markerOptions.title(String.valueOf(i + 1));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        mMap.addMarker(markerOptions);
    }

    void startRepeatingTask() {
        mHandlerTask.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mHandlerTask);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (markerCurrent == null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode != Constants.LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mGoogleApiClient.connect();
        } else {
            Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkForLocationEnable(final Context context) {

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (!gps_enabled && !network_enabled) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("Alert");
            dialog.setMessage("Please enable GPS.");
            dialog.setPositiveButton("GPS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                }
            });
            dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    finish();
                }
            });
            dialog.setCancelable(false);
            dialog.show();
        } else {
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= Build.VERSION_CODES.M) {
                checkForLocationPermission();
                // Do something for lollipop and above versions
            } else {
                mGoogleApiClient.connect();
                // do something for phones running an SDK before lollipop
            }


        }
        return gps_enabled;
    }

    private void checkForLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mGoogleApiClient.connect();
        }
    }

    private void getRoute() {
        String url;
        for (int i = 0; i < stopNamesArrayList.size(); i++) {

            url = "http://maps.googleapis.com/maps/api/directions/json?origin=" + lat + "," + longi + "&destination="
                    + stopNamesArrayList.get(i).getLatitude() + "," + stopNamesArrayList.get(i).getLongitude();
            Log.e("Url", "Url --- " + url);
            lat = Double.valueOf(stopNamesArrayList.get(i).getLatitude());
            longi = Double.valueOf(stopNamesArrayList.get(i).getLongitude());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,
                    url, null,
                    new com.android.volley.Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Success", response.toString());

                            bindPolyLine(response);
                        }

                    }, new com.android.volley.Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("Error", " E -----------" + error.getMessage());
                    VolleyLog.d("", "Error: " + error.getMessage());
                }
            });
            Volley.newRequestQueue(getApplicationContext()).add(jsonObjReq);
        }
    }

    private void bindPolyLine(JSONObject jsonObject) {

        try {

            JSONArray routeArray = jsonObject.getJSONArray("routes");
            JSONObject firstRouteObject = routeArray.getJSONObject(0).getJSONArray("legs").getJSONObject(0);
            String poliline = routeArray.getJSONObject(0).getJSONObject("overview_polyline").getString("points");
            String totalDistanc = firstRouteObject.getJSONObject("distance").getString("text");
            nextTopTime = firstRouteObject.getJSONObject("duration").getString("text");
            address = firstRouteObject.getString("end_address");

            Log.e("Lat", "totalDistanc --: " + totalDistanc + "  totalTime --:  " + nextTopTime + "  Address --:  " + address);

            List<LatLng> polyz = MapUtility.decodePolyLat(poliline);

            for (int i = 0; i < polyz.size() - 1; i++) {

                LatLng src = polyz.get(i);
                LatLng dest = polyz.get(i + 1);

                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude, dest.longitude))
                        .width(5).color(getResources().getColor(R.color.black)).geodesic(true));

            }
            polyLineVisible = true;
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
        stopLocationUpdates();
    }
}

*/
