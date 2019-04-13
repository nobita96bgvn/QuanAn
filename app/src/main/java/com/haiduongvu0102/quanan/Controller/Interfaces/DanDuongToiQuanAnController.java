package com.haiduongvu0102.quanan.Controller.Interfaces;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.haiduongvu0102.quanan.Model.ParserPolyline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DanDuongToiQuanAnController {
    GoogleMap googleMap;

    public DanDuongToiQuanAnController(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void HienThiDanDuongToiQuanAn(String duongdan) {
        new DownloadPolyLine().execute(duongdan);
    }

    class DownloadPolyLine extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuffer stringBuffer = new StringBuffer();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuffer.toString();
        }

        @Override
        protected void onPostExecute(String dataJSON) {
            super.onPostExecute(dataJSON);
            drawLine(dataJSON);
        }
    }

    private void drawLine(String dataJSON) {
        Log.e("Check json", dataJSON);
        List<LatLng> latLngList = new ParserPolyline().LayDanhSachToaDo(dataJSON);
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.width(5).color(Color.BLUE).geodesic(true);
        for (LatLng toado : latLngList) {
            polylineOptions.add(toado);
        }
        googleMap.addPolyline(polylineOptions);
    }
}
