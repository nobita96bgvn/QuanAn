package com.haiduongvu0102.quanan.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.haiduongvu0102.quanan.Model.QuanAnModel;
import com.haiduongvu0102.quanan.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Adapter.AdapterBinhLuan;

public class ChiTietQuanAnActivity extends AppCompatActivity
        implements OnMapReadyCallback, View.OnClickListener {


    TextView txtTenQuanAn, txtDiaChiQuanAn, TongSoHinhAnh, TongSoBinhLuan, TongSoCheckin, TongSoLuuLai, txtThoiGianHoatDong, txtTrangThaiHoatDong, txtTieuDeToolBar;
    ImageView imageHinhAnhQuanAn;
    QuanAnModel quanAnModel;
    RecyclerView recyclerViewBinhLuan;
    AdapterBinhLuan adapterBinhLuan;

    GoogleMap googleMap;
    MapFragment mapFragment;
    View khungTinhNang;

    Toolbar toolbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitiet_quanan);


//        quanAnModel = getIntent().getParcelableExtra("quanan");
        String json = getIntent().getStringExtra("quanan");
        quanAnModel = new Gson().fromJson(json, QuanAnModel.class);
        if (quanAnModel == null) {
            Log.e("ChiTiet", "model null");
            return;
        }

        txtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        txtDiaChiQuanAn = findViewById(R.id.txtDiaChiQuanAn);
        TongSoBinhLuan = findViewById(R.id.TongSoBinhLuan);
        TongSoCheckin = findViewById(R.id.TongSoCheckin);
        TongSoHinhAnh = findViewById(R.id.TongSoHinhAnh);
        TongSoLuuLai = findViewById(R.id.TongSoLuuLai);
        txtThoiGianHoatDong = findViewById(R.id.txtThoiGianHoatDong);
        txtTrangThaiHoatDong = findViewById(R.id.txtTrangThaiHoatDong);
        txtTieuDeToolBar = findViewById(R.id.txtTieuDeToolBar);
        imageHinhAnhQuanAn = findViewById(R.id.imageHinhAnh);
        recyclerViewBinhLuan = findViewById(R.id.recyclerBLChiTietQuanAn);


        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        khungTinhNang = findViewById(R.id.khungTinhNang);


        khungTinhNang.setOnClickListener(this);
        HienThiChiTietQuanAn();

    }

    public void setSupportActionBar(Toolbar toolbar) {
    }

    private void HienThiChiTietQuanAn() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String giohientai = dateFormat.format(calendar.getTime());
        String giomocua = quanAnModel.getGiomocua();
        String giodongcua = quanAnModel.getGiodongcua();
        Log.e("ktgio", giohientai + "");

        try {
            Date dateHienTai = dateFormat.parse(giohientai);
            Date dateMoCua = dateFormat.parse(giomocua);
            Date dateDongCua = dateFormat.parse(giodongcua);

            if (dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)) {
                //gio mo cua
                txtTrangThaiHoatDong.setText(getString(R.string.dangmocua));
            } else {
                //dong cua
                txtTrangThaiHoatDong.setText(getString(R.string.dadongcua));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtTieuDeToolBar.setText(quanAnModel.getTenquanan());
        txtTenQuanAn.setText(quanAnModel.getTenquanan());
        txtDiaChiQuanAn.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        txtThoiGianHoatDong.setText(quanAnModel.getGiomocua() + "-" + quanAnModel.getGiodongcua());
        TongSoHinhAnh.setText(quanAnModel.getHinhanhquanan().size() + "");
        TongSoBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");

        //lay hinh anh
        StorageReference storageHinhanh = FirebaseStorage.getInstance().getReference().child("hinhanhquan").child(quanAnModel.getHinhanhquanan().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {

            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageHinhAnhQuanAn.setImageBitmap(bitmap);
            }
        });

        // Load danh sach binh luan
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBinhLuan.setLayoutManager(layoutManager);
        adapterBinhLuan = new AdapterBinhLuan(this, R.layout.custom_layout_binhluan, quanAnModel.getBinhLuanModelList());
        recyclerViewBinhLuan.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();

        NestedScrollView nestedScrollViewChiTiet = findViewById(R.id.nestScrollViewChiTiet);
        nestedScrollViewChiTiet.smoothScrollTo(0, 0);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        double latitude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude();
        double longitude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(quanAnModel.getTenquanan());

        googleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.khungTinhNang:
//                Intent iDanDuong = new Intent(this, DanDuongToiQuanAnActivity.class);
//                iDanDuong.putExtra("latitude",quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude());
//                iDanDuong.putExtra("longitude",quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude());
//                startActivity(iDanDuong);
//                break;

                double lat1 = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude();
                double lng1 = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude();
                SharedPreferences sharedPreferences = getSharedPreferences("toado", Context.MODE_PRIVATE);
                double lat0 = Double.parseDouble(sharedPreferences.getString("latitude", "0"));
                double lng0 = Double.parseDouble(sharedPreferences.getString("longitude", "0"));

                String url = "http://maps.google.com/maps?saddr=" +
                        lat0 + "," + lng0 + "&daddr=" + lat1 + "," + lng1 + "";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
        }
    }
}