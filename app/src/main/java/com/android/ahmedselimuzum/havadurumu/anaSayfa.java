package com.android.ahmedselimuzum.havadurumu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

public class anaSayfa extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener , GoogleApiClient.ConnectionCallbacks,LocationListener{

    private GoogleApiClient  mGoogleApiClient;
    private final int PERMISSION_LOCATION=111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);


        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this,this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();



    }


    public void havaDurumuVerisiniIndir(Location lokasyon){

        final String koordinatlar=Sabitler.KOORD_URL+lokasyon.getLatitude()+"&lon="+lokasyon.getLongitude();




        /* MARK: İSTEK ATMA*/
        final String url=Sabitler.TEMEL_URL+koordinatlar+Sabitler.API_ANAHTARI;

        final JsonObjectRequest jsonIstegi=new JsonObjectRequest( Request.Method.GET, url,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.v("HAVAS","SONUC "+url);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("HAVAS","HATA "+error.getLocalizedMessage());
            }
        });

        Volley.newRequestQueue(this).add(jsonIstegi);

    }

    private void lokasyonServisleriniBaslat(){
        try{
            LocationRequest ist=LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,ist,this);
        }
        catch (SecurityException h){
            Toast.makeText(this,"Lütfen İzin Verin",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        havaDurumuVerisiniIndir(location);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_LOCATION);
        }
        else {
            lokasyonServisleriniBaslat();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
