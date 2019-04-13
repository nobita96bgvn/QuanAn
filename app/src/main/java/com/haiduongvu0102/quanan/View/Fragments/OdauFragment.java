package com.haiduongvu0102.quanan.View.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haiduongvu0102.quanan.Controller.Interfaces.OdauController;
import com.haiduongvu0102.quanan.R;

public class OdauFragment  extends Fragment {
    RecyclerView recyclerOdau;
    SharedPreferences sharedPreferences;
    NestedScrollView nestedScrollViewOdau;
    
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_odau,container,false);
        recyclerOdau = view.findViewById(R.id.recyclerOdau);

        nestedScrollViewOdau = view.findViewById(R.id.nestScrollViewODau);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        //22:04
        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        Log.d("ktodau",sharedPreferences.getString("latitude","")+"");
        Location vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));

        OdauController odauController = new OdauController(getContext());
        odauController.getDanhSachQuanAnController( nestedScrollViewOdau,recyclerOdau,vitrihientai);



    }

}
