package com.example.bduan1.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bduan1.QuanLyHoaDon.HoaDonChiTiet;
import com.example.bduan1.QuanLyHoaDon.HoaDonDichVu;
import com.example.bduan1.QuanLyHopDongAdmin.QuanLiHopDongModel;
import com.example.bduan1.R;
import com.example.bduan1.chucnangAdminQuanLyPhongTo.QuanLyPhongTroModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BillDetailUserActivity extends AppCompatActivity {

    private TextView tvNgayXuatHoaDon,tvTinhTuNgay,tvDenNgay,tvSoNguoi,tvTenPhong,tvTienThangMay,
            tvTienPhong,tvTienDien,tvTienNuoc,tvTienmang,tvTienRac,tvTongTien,tvTrangThai,tvAmount,tvtvTransferContent;
    private ImageView imgBackBillDetailuser;
    private FirebaseFirestore db;
    private String soNguoi=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill_detail_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.billdetailuser), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUi();
        db=FirebaseFirestore.getInstance();
        imgBackBillDetailuser.setOnClickListener(v -> finish());
        Intent intent=getIntent();
            String idHoaDon=intent.getStringExtra("hoaDonId");
            tvNgayXuatHoaDon.setText(intent.getStringExtra("ngayXuatHoaDon"));
            boolean trangThai=intent.getBooleanExtra("trangThai",false);
        if(trangThai){
            tvTrangThai.setText("Đã thanh toán");
            tvTrangThai.setTextColor(getResources().getColor(R.color.your_primary_color));
        }else{
            tvTrangThai.setText("Chưa thanh toán");
            tvTrangThai.setTextColor(getResources().getColor(R.color.red_status_bill));
        }
        getHoaDonChiTiet(idHoaDon,tvTongTien);

    }
    private void getHoaDonChiTiet(String idHoaDon,TextView tvTongTien){
        db.collection("HoaDonChiTiet")
                .whereEqualTo("idHoaDon",idHoaDon)
                .get()
                .addOnSuccessListener(documentSnapshots -> {
                    for(DocumentSnapshot documentSnapshot:documentSnapshots) {
                        HoaDonChiTiet hoaDonChiTiet = documentSnapshot.toObject(HoaDonChiTiet.class);
                        if (hoaDonChiTiet.getIdHoaDon().equals(idHoaDon)){
                            tvTongTien.setText(hoaDonChiTiet.getSoTienThanhToan()+" VND");
                            tvAmount.setText(hoaDonChiTiet.getSoTienThanhToan()+" VND");
                            getHoaDonDichVu(hoaDonChiTiet.getIdHoaDonDichVu());
                            getPhongTro(hoaDonChiTiet.getIdPhong());
                            return;
                        }
                    }
                });
    }
    private void getHopDong(String idPhongTro){
        db.collection("HopDong")
                .whereEqualTo("idPhong",idPhongTro)
                .get()
                .addOnSuccessListener(documentSnapshots -> {
                    for(DocumentSnapshot documentSnapshot:documentSnapshots) {
                        QuanLiHopDongModel hopDong = documentSnapshot.toObject(QuanLiHopDongModel.class);
                        if (hopDong.getIdPhong().equals(idPhongTro)){
                            tvSoNguoi.setText(hopDong.getSoNguoi());
                            return;
                        }
                    }
                });
    }
    private void getPhongTro(String idPhong){
        db.collection("PhongTro")
                .document(idPhong)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()) {
                    QuanLyPhongTroModel phongTro = documentSnapshot.toObject(QuanLyPhongTroModel.class);
                        if (phongTro != null) {
                            tvTienPhong.setText(phongTro.getTienPhong() + " VND");
                            tvTenPhong.setText(phongTro.getTenPhong());
                            tvtvTransferContent.setText("Thanh toán hoá đơn thuê nhà phòng "+phongTro.getTenPhong());
                            getHopDong(phongTro.getId());
                        }
                    }
                });
    }
    private void getHoaDonDichVu(String idHoaDonDichVu){
        db.collection("HoaDonDichVu")
                .document(idHoaDonDichVu)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()) {
                        HoaDonDichVu hoaDonDichVu = documentSnapshot.toObject(HoaDonDichVu.class);
                        if (hoaDonDichVu != null) {
                            tvTienDien.setText(hoaDonDichVu.getTienDien() + " VND"+" / "+hoaDonDichVu.getSoDien()+" kwh");
                            tvTienNuoc.setText(hoaDonDichVu.getTienNuoc() + " VND"+" / "+hoaDonDichVu.getSoNuoc()+" m3");
                            tvTienmang.setText(hoaDonDichVu.getTienMang() + " VND"+ "/ "+tvSoNguoi.getText().toString()+" người");
                            tvTienRac.setText(hoaDonDichVu.getTienRac() + " VND"+" / "+tvSoNguoi.getText().toString()+ " người");
                            tvTienThangMay.setText(hoaDonDichVu.getTienThangMay()+" VND" +"/ "+tvSoNguoi.getText().toString()+" người");
                        }
                    }
                });

    }
    private void initUi(){
        tvNgayXuatHoaDon = findViewById(R.id.tvNgayXuatHoaDon_billdetail_user);
        tvTinhTuNgay = findViewById(R.id.tv_tinhTuNgay_cthd);
        tvDenNgay = findViewById(R.id.tv_denNgay_cthd);
        tvSoNguoi = findViewById(R.id.tvsoNguoi_billdetail_user);
        tvTenPhong = findViewById(R.id.tvTenPhong_billdetail_user);
        tvTienPhong = findViewById(R.id.tvTienPhong_billdetail_user);
        tvTienDien = findViewById(R.id.tvTienDien_billdetail_user);
        tvTienNuoc = findViewById(R.id.tvTienNuoc_billdetail_user);
        tvTienmang = findViewById(R.id.tvTienmang_billdetail_user);
        tvTienRac = findViewById(R.id.tvTienRac_billdetail_user);
        tvTongTien = findViewById(R.id.tvTong_billdetail_user);
        tvTrangThai = findViewById(R.id.tvTrangThai);
        tvTienThangMay=findViewById(R.id.tvTienThangMay_billdetail_user);
        imgBackBillDetailuser = findViewById(R.id.imgBackBillDetailuser);
        tvAmount=findViewById(R.id.tvAmount);
        tvtvTransferContent=findViewById(R.id.tvTransferContent);

    }

}