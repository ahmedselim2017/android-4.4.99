package com.android.ahmedselimuzum.havadurumu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class anaSayfa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);

        final String url=Sabitler.TEMEL_URL+Sabitler.KOORD_URL+Sabitler.API_ANAHTARI;

        final JsonObjectRequest jsonIstegi=new JsonObjectRequest( Request.Method.GET, url,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.v("HAVA","SONUC "+response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("HAVA","HATA "+error.getLocalizedMessage());
            }
        });

        Volley.newRequestQueue(this).add(jsonIstegi);
    }


}
