package com.android.ahmedselimuzum.hesapmakinesi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    public enum Islem{
        TOPLAMA,CIKARMA,CARPMA,BOLME,ESITLE
    }

    //Sonuc Label'ındaki sayı
    String suAnkiSayi = "";

    //Sonucun Int Hali
    Double sonuc;

    //İşlemlerde kullanılan değerler (solDeger+sagDeger=sonuc gibi)
    String solDeger;
    String sagDeger;



    //Suanki işlemi saklayan değişken
    Islem suAnkiIslem;

    //Sonuc Label'ının boş olarak tanımlanması
    TextView lblSonuc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Widget Tanımlamaları

        //Sayı Butonları
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);
        Button btn0 = findViewById(R.id.btn0);

        //İşlem Butonları
        ImageButton btnEsittir = findViewById(R.id.btnEsittir);
        ImageButton btnToplama = findViewById(R.id.btnToplama);
        ImageButton btnCikarma = findViewById(R.id.btnCikarma);
        ImageButton btnCarpma = findViewById(R.id.btnCarpma);
        ImageButton btnBolme = findViewById(R.id.btnBolme);


        //Nokta Butonu
        Button btnNokta=findViewById(R.id.btnNokta);

        //Silme Butonu
        Button btnSil= findViewById(R.id.btnSil);

        //Sonuc Label'ının yeniden(dolu olarak ) tanımlanması
        lblSonuc=findViewById(R.id.lblSonuc);

        //Butonlara Listener Atanması

        //Sayı Butonları
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);

        //İşlem Butonları
        btnEsittir.setOnClickListener(this);
        btnToplama.setOnClickListener(this);
        btnCikarma.setOnClickListener(this);
        btnCarpma.setOnClickListener(this);
        btnBolme.setOnClickListener(this);

        //Nokta Butonu
        btnNokta.setOnClickListener(this);

        //Silme Butonu
        btnSil.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //switch-case ile tıklanan butonun bulunması
        switch (v.getId()){

            //btn1 ise
            case R.id.btn1:
                sayiBasildi(1);
                break;

            //btn2 ise
            case R.id.btn2:
                sayiBasildi(2);
                break;

            //btn3 ise
            case R.id.btn3:
                sayiBasildi(3);
                break;

            //btn4 ise
            case R.id.btn4:
                sayiBasildi(4);
                break;

            //btn5 ise
            case R.id.btn5:
                sayiBasildi(5);
                break;

            //btn6 ise
            case R.id.btn6:
                sayiBasildi(6);
                break;

            //btn7 ise
            case R.id.btn7:
                sayiBasildi(7);
                break;

            //btn8 ise
            case R.id.btn8:
                sayiBasildi(8);
                break;

            //btn9 ise
            case R.id.btn9:
                sayiBasildi(9);
                break;

            //btn0 ise
            case R.id.btn0:
                sayiBasildi(0);
                break;

            //btnEsittir ise
            case R.id.btnEsittir:
                islemiGerceklestir(Islem.ESITLE);
                break;


            //btnToplama ise
            case R.id.btnToplama:
                islemiGerceklestir(Islem.TOPLAMA);
                break;

            //btnCarpma ise
            case R.id.btnCarpma:
                islemiGerceklestir(Islem.CARPMA);
                break;

            //btnCikarma ise
            case R.id.btnCikarma:
                islemiGerceklestir(Islem.CIKARMA);
                break;

            //btnBolme ise
            case R.id.btnBolme:
                islemiGerceklestir(Islem.BOLME);
                break;

            //brnNokta ise
            case R.id.btnNokta:

                suAnkiSayi = suAnkiSayi+ ".";
                lblSonuc.setText(suAnkiSayi);
                break;

                //btnSil ise
            case R.id.btnSil:

                //herseyi sıfırlama
                solDeger="";
                sagDeger="";
                suAnkiSayi="";
                sonuc=0.0;
                suAnkiIslem=null;

                //Label'ın temilenmesi
                lblSonuc.setText("0");

                break;
        }
    }

    /**
     *Gerekli İşlemleri yapan bir fonksiyon
     *
     * @param islem yapılacak işlem
     */
    private void islemiGerceklestir(Islem islem) {

        try {
            if (suAnkiIslem != null) {
                if (suAnkiSayi != null) {
                    //sagDeger Ayarlanması ve suAnkiSayi'nın sıfırlanması
                    sagDeger = suAnkiSayi;
                    suAnkiSayi = "";

                    //switch-case ile işlemin belirlenmesi
                    switch (suAnkiIslem) {

                        //TOPLAMA ise
                        case TOPLAMA:
                            sonuc = Double.parseDouble(solDeger) + Double.parseDouble(sagDeger);
                            break;

                        //CIKARMA ise
                        case CIKARMA:
                            sonuc = Double.parseDouble(solDeger) - Double.parseDouble(sagDeger);
                            break;

                        //CARPMA ise
                        case CARPMA:
                            sonuc = Double.parseDouble(solDeger) * Double.parseDouble(sagDeger);
                            break;

                        //BOLME  ise
                        case BOLME:
                            sonuc = Double.parseDouble(solDeger) / Double.parseDouble(sagDeger);
                            break;
                        }

                        //Sonucu solDegere Eşitlenmesi
                        solDeger = sonuc + "";
                        lblSonuc.setText(solDeger);
                    }

                suAnkiIslem = islem;

            } else {
                    if(suAnkiSayi != "") {
                        solDeger = suAnkiSayi;
                        suAnkiSayi = "";
                        suAnkiIslem = islem;
                    }

                }
        }catch (Exception h){}

            //suAnkiIslemiGuncelleme


    }


    /**
     * Her sayı basıldığında çalışacak olan fonksiyon
     *
     * @param sayi basılan sayı
     */
    private void sayiBasildi(int sayi){

        //suAnkiSayi sayıyı güncelleme
        suAnkiSayi += sayi + "";

        //Sonuc Label'ının güncellenmesi
        lblSonuc.setText(suAnkiSayi);
    }

}
