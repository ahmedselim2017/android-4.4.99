package com.android.ahmedselimuzum.havadurumu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GunlukBilgi {


    String sehir;
    String ulke;
    String tarih;

    int bolumBaslangici;

    SaatlikBilgi bolum1;
    SaatlikBilgi bolum2;
    SaatlikBilgi bolum3;
    SaatlikBilgi bolum4;
    SaatlikBilgi bolum5;
    SaatlikBilgi bolum6;
    SaatlikBilgi bolum7;
    SaatlikBilgi bolum8;

    SaatlikBilgi[] liste;

    public GunlukBilgi(String sehir, String ulke, String tarih, SaatlikBilgi bolum1, SaatlikBilgi bolum2, SaatlikBilgi bolum3, SaatlikBilgi bolum4, SaatlikBilgi bolum5, SaatlikBilgi bolum6, SaatlikBilgi bolum7, SaatlikBilgi bolum8,int bolumBaslangici) {
        this.sehir = sehir;
        this.ulke = ulke;
        this.tarih = tarihiformatla(tarih);
        this.bolumBaslangici=bolumBaslangici;
        this.bolum1 = bolum1;
        this.bolum2 = bolum2;
        this.bolum3 = bolum3;
        this.bolum4 = bolum4;
        this.bolum5 = bolum5;
        this.bolum6 = bolum6;
        this.bolum7 = bolum7;
        this.bolum8 = bolum8;
        SaatlikBilgi[] ls={bolum1,bolum2,bolum3,bolum4,bolum5,bolum6,bolum7,bolum8};
        liste=ls;
    }



    private String tarihiformatla(String tarih){
        String sadeceTarih=tarih.split(" ")[0];
        String ay=sadeceTarih.split("-")[1];
        String gun=sadeceTarih.split("-")[2];

        switch (ay){
            case "01":
                return gun+" Ocak";

            case "02":
                return gun+" Şubat";
                
            case "03":
                return gun+" Mart";
                
            case "04":
                return gun+" Nisan";
                
            case "05":
                return gun+" Mayıs";
                
            case "06":
                return gun+" Haziran";
                
            case "07":
                return gun+" Temmuz";
                
            case "08":
                return gun+" Ağustos";
                
            case "09":
                return gun+" Eylül";
                
            case "10":
                return gun+" Ekim";
                
            case "11":
                return gun+" Kasım";
                
            case "12":
                return gun+" Aralık";
        }
        return "";
    }

}

class SaatlikBilgi{

    public static final String HAVA_DURUMU_BULUTLU="Clouds";
    public static final String HAVA_DURUMU_ACIK="Clear";
    public static final String HAVA_DURUMU_YAGMURLU="Rain";
    public static final String HAVA_DURUMU_FIRTINALI="Thunderstorm";
    public static final String HAVA_DURUMU_KARLI="Snow";


    Double suankiSicaklik;
    Double enDusukSicaklik;
    Double enYuksekSicaklik;

    String havaDurumu;


    public SaatlikBilgi(Double suankiSicaklik, Double enDusukSicaklik, Double enYuksekSicaklik, String havaDurumu) {
        this.suankiSicaklik = suankiSicaklik;
        this.enDusukSicaklik = enDusukSicaklik;
        this.enYuksekSicaklik = enYuksekSicaklik;
        this.havaDurumu = havaDurumu;
    }


}
