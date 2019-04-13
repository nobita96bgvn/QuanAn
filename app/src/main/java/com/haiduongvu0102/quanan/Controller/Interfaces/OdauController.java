package com.haiduongvu0102.quanan.Controller.Interfaces;

import android.content.Context;
import android.location.Location;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haiduongvu0102.quanan.Model.QuanAnModel;
import com.haiduongvu0102.quanan.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.AdapterRecyclerOdau;

public class OdauController {
    Context context;
    QuanAnModel quanAnModel;
    AdapterRecyclerOdau adapterRecyclerOdau;
    int itemdaco = 3;

    public OdauController(Context context) {
        this.context = context;
        quanAnModel = new QuanAnModel();
    }

    public void getDanhSachQuanAnController(NestedScrollView nestedScrollView, RecyclerView recyclerOdau, final Location vitrihientai) {
        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerOdau.setLayoutManager(layoutManager);

        adapterRecyclerOdau = new AdapterRecyclerOdau(context, quanAnModelList, R.layout.custom_layout_recyclerview_odau);
        recyclerOdau.setAdapter(adapterRecyclerOdau);
        final OdauInterface odauInterface = new OdauInterface() {

            @Override
            public void getDanhSachQuanAnModel(final QuanAnModel quanAnModel) {
                quanAnModelList.add(quanAnModel);
                adapterRecyclerOdau.notifyDataSetChanged();

            }
        };


        quanAnModel.getDanhSachQuanAn(odauInterface, vitrihientai, itemdaco, 0);
    }

}
