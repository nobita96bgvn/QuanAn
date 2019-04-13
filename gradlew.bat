package com.haiduongvu0102.food.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.haiduongvu0102.food.Model.QuanAnModel;
import com.haiduongvu0102.food.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Adapters.AdapterBinhLuan;

public class ChiTietQuanAnActivity extends AppCompatActivity {
    TextView txtTenQuanAn, txtDiaChiQuanAn, TongSoHinhAnh, TongSoBinhLuan, TongSoCheckin, TongSoLuuLai, txtThoiGianHoatDong, txtTrangThaiHoatDong, txtTieuDeToolBar;
    ImageView imageHinhAnhQuanAn;
    QuanAnModel quanAnModel;
    RecyclerView recyclerViewBinhLuan;
    AdapterBinhLuan adapterBinhLuan;

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);


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
        txtTrangThaiHo