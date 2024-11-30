package com.example.bduan1;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.bduan1.ThongBaoAdmin.FragmentThongBaoAdmin;
import com.example.bduan1.chucnangAdminQuanLyPhongTo.FragmentQuanLyPhongTro;
import com.google.android.play.core.integrity.v;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView imgHome = findViewById(R.id.imgHome);
        ImageView imgThongBao = findViewById(R.id.imgThongBao);


        //home mặc định
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, new HomeFragment());
        transaction.commit();
        // Xử lý sự kiện nhấn vào imgHome
        imgHome.setOnClickListener(v -> {
            // Chuyển đổi sang HomeFragment
            FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
            homeTransaction.replace(R.id.fragmentContainer, new HomeFragment());
            homeTransaction.commit();
        });

        imgThongBao.setOnClickListener(view -> {
            FragmentTransaction thongbaoTransaction = getSupportFragmentManager().beginTransaction();
            thongbaoTransaction.replace(R.id.fragmentContainer, new FragmentThongBaoAdmin());
            thongbaoTransaction.commit();
        });
    }




}