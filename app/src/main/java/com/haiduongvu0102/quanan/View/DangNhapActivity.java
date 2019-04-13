package com.haiduongvu0102.quanan.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.haiduongvu0102.quanan.R;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener, FirebaseAuth.AuthStateListener {
    TextView txtDangKyMoi, txtquenMatKhau;
    Button btnDangNhap;
    EditText edemailDN, edPassDN;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        txtDangKyMoi = findViewById(R.id.txtDangKyMoi);
        txtDangKyMoi.setOnClickListener(this);

        edemailDN = findViewById(R.id.edEmailDN);
        edPassDN = findViewById(R.id.edPasswordDN);

        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangNhap.setOnClickListener(this);

        txtquenMatKhau = findViewById(R.id.txtQuenMatKhau);
        txtquenMatKhau.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.txtDangKyMoi:
                Intent iDangKy = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(iDangKy);
                break;
            case R.id.btnDangNhap:
                DangNhap();
                break;
            case R.id.txtQuenMatKhau :
                Intent iQuenMK = new Intent(DangNhapActivity.this,KhoiPhucMatKhauActivity.class);
                startActivity(iQuenMK);
                break;

        }

    }


    // Xu li dang nhap
    private void DangNhap() {
        String email = edemailDN.getText().toString();
        String pass = edPassDN.getText().toString();
//        String thongbaoloi = getString(R.string.thongbaoloi);



        if(email.trim().length()==0){
//            thongbaoloi += ""+getString(R.string.email);
            Toast.makeText(this,getString(R.string.chuanhapemail),Toast.LENGTH_SHORT).show();
        }
        else if(pass.trim().length()==0)
        {

            Toast.makeText(this,getString(R.string.chuanhapmatkhau),Toast.LENGTH_SHORT).show();
        }else{
            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(DangNhapActivity.this, getString(R.string.dangnhapthatbai), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            Intent iTrangChu = new Intent(this, TrangChuActivity.class);
            startActivity(iTrangChu);
        } else {

        }
    }
}

