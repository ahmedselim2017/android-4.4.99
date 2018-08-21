package com.android.ahmedselimuzum.havadurumu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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

    private ImageView imgHava;
    private ImageView imgHavaKucuk;
    private TextView lblHavaGunduz;
    private TextView lblHavaGece;
    private TextView lblTarih;
    private TextView lblSehir;
    private TextView lblHavaDurumu;

    RaporAdaptesi adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);

        imgHava=findViewById(R.id.imgHava);
        imgHavaKucuk=findViewById(R.id.imgHavaKucuk);
        lblHavaGunduz=findViewById(R.id.lblHavaGunduz);
        lblHavaGece=findViewById(R.id.lblHavaGece);
        lblTarih=findViewById(R.id.lblTarih);
        lblSehir=findViewById(R.id.lblSehir);
        lblHavaDurumu=findViewById(R.id.lblHavaDurumu);

        RecyclerView recyclerView=findViewById(R.id.tabloGoruntuleyici);
        adaptor=new RaporAdaptesi(raporlar);
        recyclerView.setAdapter(adaptor);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);


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
                    String tarih;

                    JSONArray liste=response.getJSONArray("list");


                    JSONObject o;
                    JSONObject main;
                    JSONArray hava1;
                    JSONObject hava;


                    String saat;
                    String havaDurumu;

                    Double suankiSicaklik;
                    Double enDusukSicaklik;
                    Double enYuksekSicaklik;

                    int bolumBaslangici=0;

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
                        tarih=o.getString("dt_txt");
                        main=o.getJSONObject("main");
                        hava1=o.getJSONArray("weather");
                        hava=hava1.getJSONObject(0);

                        saat=tarih.split(" ")[1];
                        havaDurumu=hava.getString("main");

                        suankiSicaklik=main.getDouble("temp");
                        enDusukSicaklik=main.getDouble("temp_min");
                        enYuksekSicaklik=main.getDouble("temp_max");


                        switch (saat){
                            case "00:00:00":
                                bolum1=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);

                                if(bolumBaslangici==0) {
                                    bolumBaslangici = 1;
                                }
                                break;
                            case "03:00:00":
                                bolum2=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);

                                if(bolumBaslangici==0) {
                                    bolumBaslangici = 2;
                                }
                                break;
                            case "06:00:00":
                                bolum3=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);

                                if(bolumBaslangici==0) {
                                    bolumBaslangici = 3;
                                }
                                break;
                            case "09:00:00":
                                bolum4=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);

                                if(bolumBaslangici==0) {
                                    bolumBaslangici = 4;
                                }
                                break;
                            case "12:00:00":
                                bolum5=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);

                                if(bolumBaslangici==0) {
                                    bolumBaslangici = 5;
                                }
                                break;
                            case "15:00:00":
                                bolum6=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);

                                if(bolumBaslangici==0) {
                                    bolumBaslangici = 6;
                                }
                                break;
                            case "18:00:00":
                                bolum7=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);

                                if(bolumBaslangici==0) {
                                    bolumBaslangici = 7;
                                }
                                break;
                            case "21:00:00":
                                bolum8=new SaatlikBilgi(suankiSicaklik,enDusukSicaklik,enYuksekSicaklik,havaDurumu);

                                if(bolumBaslangici==0) {
                                    bolumBaslangici = 8;
                                }
                                rapor=new GunlukBilgi(sehirIsim,ulke,tarih,bolum1,bolum2,bolum3,bolum4,bolum5,bolum6,bolum7,bolum8,bolumBaslangici);
                                raporlar.add(rapor);
                                break;
                        }

                    }

                }
                catch (JSONException h){
                    Log.i("NEDEN",h.getLocalizedMessage());

                }
                gorunumleriAyarla();
                adaptor.notifyDataSetChanged();

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

    private void gorunumleriAyarla(){
        if(raporlar.size()>0){

            GunlukBilgi rapor=raporlar.get(0);

            SaatlikBilgi bolum=rapor.liste[rapor.bolumBaslangici-1];

            havaDurumuAyarla(imgHava,imgHavaKucuk,lblHavaDurumu,bolum);


            lblTarih.setText(rapor.tarih);
            lblHavaGunduz.setText(Math.round(bolum.suankiSicaklik)+"°");
            lblHavaGece.setText(Math.round(rapor.bolum8.suankiSicaklik)+"°");
            lblSehir.setText(rapor.sehir+" "+rapor.ulke);

        }
    }


    private void havaDurumuAyarla(ImageView imgHava,ImageView imgHavaKucuk,TextView lblHavaDurumu,SaatlikBilgi bolum){

        switch (bolum.havaDurumu){
            case SaatlikBilgi.HAVA_DURUMU_BULUTLU:
                imgHava.setImageResource(R.drawable.cloudy);
                lblHavaDurumu.setText("Bulutlu");
                break;
            case SaatlikBilgi.HAVA_DURUMU_KARLI:
                imgHava.setImageResource(R.drawable.snow);
                lblHavaDurumu.setText("Karlı");
                break;
            case SaatlikBilgi.HAVA_DURUMU_FIRTINALI:
                imgHava.setImageResource(R.drawable.thunder_lightning);
                lblHavaDurumu.setText("Fırtınalı");
                break;
            case SaatlikBilgi.HAVA_DURUMU_YAGMURLU:
                imgHava.setImageResource(R.drawable.rainy);
                lblHavaDurumu.setText("Yağmurlu");
                break;

            default:
                Log.v("HAVAYA BAK",bolum.havaDurumu);
                imgHava.setImageResource(R.drawable.sunny);
                lblHavaDurumu.setText("Açık");
                break;
        }

        if (imgHavaKucuk!=null){
            havaDurumuAyarla(imgHavaKucuk,null,lblHavaDurumu,bolum);
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


    public class RaporAdaptesi extends RecyclerView.Adapter<RaporViewTutucu>{
        private ArrayList<GunlukBilgi> gunlukRaporListe;

        public RaporAdaptesi(ArrayList<GunlukBilgi> gunlukRaporListe) {
            this.gunlukRaporListe = gunlukRaporListe;
        }

        @Override
        public void onBindViewHolder(RaporViewTutucu holder, int position) {
            GunlukBilgi rapor=gunlukRaporListe.get(position);
            holder.gorunumleriAyarla(rapor);
        }

        @Override
        public int getItemCount() {
            return gunlukRaporListe.size();
        }

        @Override
        public RaporViewTutucu onCreateViewHolder(ViewGroup parent, int viewType) {
            View kart= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_hava,parent,false);
            return new RaporViewTutucu(kart);
        }
    }

    public class RaporViewTutucu extends RecyclerView.ViewHolder{

        private ImageView imgHavaResim;
        private TextView lblGun;
        private TextView lblDurum;
        private TextView lblHavaSabahDerece;
        private TextView lblHavaGeceDerece;


        public RaporViewTutucu(View itemView){
            super(itemView);

            imgHavaResim=itemView.findViewById(R.id.imgHavaResim);
            lblGun=itemView.findViewById(R.id.lblGun);
            lblDurum=itemView.findViewById(R.id.lblDurum);
            lblHavaSabahDerece=itemView.findViewById(R.id.lblHavaSabahDerece);
            lblHavaGeceDerece=itemView.findViewById(R.id.lblHavaGeceDerece);
        }

        private void gorunumleriAyarla(GunlukBilgi rapor){


            SaatlikBilgi bolum=rapor.liste[4];
            SaatlikBilgi bolumGece=rapor.liste[7];

            lblGun.setText(rapor.tarih);
            lblHavaSabahDerece.setText(Math.round(bolum.suankiSicaklik)+"°");
            lblHavaGeceDerece.setText(Math.round(bolumGece.suankiSicaklik)+"°");

            havaDurumuAyarla(imgHavaResim,null,lblDurum,bolum);
        }

    }
}



