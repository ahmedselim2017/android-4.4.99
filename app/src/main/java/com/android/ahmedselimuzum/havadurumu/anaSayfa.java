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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class anaSayfa extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener , GoogleApiClient.ConnectionCallbacks,LocationListener{

    private GoogleApiClient  mGoogleApiClient;
    private final int PERMISSION_LOCATION=111;
    private ArrayList<GunlukBilgi> raporlar=new ArrayList<>();

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

                try{
                    GunlukBilgi rapor;
                    JSONObject sehir=response.getJSONObject("city");

                    String sehirIsim=sehir.getString("name");
                    String ulke=sehir.getString("country");
                    String tarih=response.getString("dt_txt");

                    JSONArray liste=response.getJSONArray("list");


                    JSONObject o;
                    JSONObject main;
                    JSONObject hava;

                    String saat;
                    String havaDurumu;

                    Double suankiSicaklik;
                    Double enDusukSicaklik;
                    Double enYuksekSicaklik;

                    SaatlikBilgi bolum1=null;
                    SaatlikBilgi bolum2=null;
                    SaatlikBilgi bolum3=null;
                    SaatlikBilgi bolum4=null;
                    SaatlikBilgi bolum5=null;
                    SaatlikBilgi bolum6=null;
                    SaatlikBilgi bolum7=null;
                    SaatlikBilgi bolum8=null;

                    for(int i=0;i<=39;i++){

                        if(raporlar.size()==5){
                            break;
                        }

                        o=liste.getJSONObject(i);
                        main=o.getJSONObject("main");
                        hava=o.getJSONObject("weather");

                        saat=tarih.split(" ")[1];
                        havaDurumu=hava.getString("main");

                        suankiSicaklik=o.getDouble("temp");
                        enDusukSicaklik=o.getDouble("temp_min");
                        enYuksekSicaklik=o.getDouble("temp_max");


                        switch (saat){
                            case "00:00:00":
                                bolum1=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);
                                break;
                            case "03:00:00":
                                bolum2=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);
                                break;
                            case "06:00:00":
                                bolum3=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);
                                break;
                            case "09:00:00":
                                bolum4=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);
                                break;
                            case "12:00:00":
                                bolum5=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);
                                break;
                            case "15:00:00":
                                bolum6=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);
                                break;
                            case "18:00:00":
                                bolum7=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);
                                break;
                            case "21:00:00":
                                bolum8=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);
                                rapor=new GunlukBilgi(sehirIsim,ulke,tarih,bolum1,bolum2,bolum3,bolum4,bolum5,bolum6,bolum7,bolum8);
                                raporlar.add(rapor);
                                break;
                        }

                    }

                }
                catch (JSONException h){}

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
