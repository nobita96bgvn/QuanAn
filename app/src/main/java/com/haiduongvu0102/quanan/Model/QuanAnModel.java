package com.haiduongvu0102.quanan.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haiduongvu0102.quanan.Controller.Interfaces.OdauInterface;

import java.util.ArrayList;
import java.util.List;

public class QuanAnModel {

    boolean giaohang;
    String giodongcua, giomocua, tenquanan, videogioithieu, maquanan;
    List<String> tienich;
    List<String> hinhanhquanan;
    long luotthich;
    transient List<Bitmap> bitmapList;

    List<BinhLuanModel> binhLuanModelList;


    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public List<ChiNhanhQuanAnModel> getChiNhanhQuanAnModelList() {
        return chiNhanhQuanAnModelList;
    }

    public void setChiNhanhQuanAnModelList(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList) {
        this.chiNhanhQuanAnModelList = chiNhanhQuanAnModelList;
    }

    List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList;


    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }



    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }


    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    transient DatabaseReference nodeRoot;
    transient   DatabaseReference dataQuanAn;

    public QuanAnModel() {
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }


    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }


    transient private DataSnapshot dataRoot;

    public void getDanhSachQuanAn(final OdauInterface odauInterface, final Location vitrihientai, final int itemtieptheo, final int itemdaco) {
//        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                LayDanhSachQuanAn(dataSnapshot, odauInterface, vitrihientai, itemtieptheo, itemdaco);


            }

            public void onCancelled( DatabaseError databaseError) {

            }
        };
        if (dataRoot != null) {
            LayDanhSachQuanAn(dataRoot, odauInterface, vitrihientai, itemtieptheo, itemdaco);
        } else {
            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        }


    }

    private void LayDanhSachQuanAn(DataSnapshot dataSnapshot, OdauInterface odauInterface, Location vitrihientai, int itemtieptheo, int itemdaco) {
        DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");
        // Lay danh sach quan an, dataSnapshot la node root
        int i = 0;
        for (DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren()) {
            QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
            quanAnModel.setMaquanan(valueQuanAn.getKey());

            DataSnapshot dataSnapshotHinhQuanAn = dataSnapshot.child("hinhanhquanans").child(valueQuanAn.getKey());
            Log.d("kiemtraMa", valueQuanAn.getKey() + "");

            // load hinh quan an theo ma
            List<String> hinhanhList = new ArrayList<>();

            for (DataSnapshot valueHinhQuanAn : dataSnapshotHinhQuanAn.getChildren()) {
                Log.d("kiemtra", valueHinhQuanAn.getValue() + "");
                hinhanhList.add(valueHinhQuanAn.getValue(String.class));
            }
            quanAnModel.setHinhanhquanan(hinhanhList);



            // Lay danh sach binh luan cua quan an
            DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quanAnModel.getMaquanan());
            List<BinhLuanModel> binhLuanModels = new ArrayList<>();
            for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                Log.d("ktma", valueBinhLuan + "");
                BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);


                binhLuanModels.add(binhLuanModel);

            }
            quanAnModel.setBinhLuanModelList(binhLuanModels);



            // Lay chi nhanh quan an

            DataSnapshot snapshotChiNhanhQuanAn = dataSnapshot.child("chinhanhquanans").child(quanAnModel.getMaquanan());
            List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();

            for (DataSnapshot valueChiNhanhQuanAn : snapshotChiNhanhQuanAn.getChildren()) {
                ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);
                Location vitriquanan = new Location("");
                vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());

                double khoangcach = vitrihientai.distanceTo(vitriquanan) / 1000;
                Log.d("khoangcach", khoangcach + "-" + chiNhanhQuanAnModel.getDiachi());

                chiNhanhQuanAnModel.setKhoangcach(khoangcach);
                chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
            }


            quanAnModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);

            odauInterface.getDanhSachQuanAnModel(quanAnModel);

        }
    }


}

