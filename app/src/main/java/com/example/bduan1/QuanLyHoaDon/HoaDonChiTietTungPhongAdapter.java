package com.example.bduan1.QuanLyHoaDon;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bduan1.QuanLyHopDongAdmin.QuanLiHopDongModel;
import com.example.bduan1.R;
import com.example.bduan1.chucnangAdminQuanLyPhongTo.QuanLyPhongTroModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietTungPhongAdapter extends RecyclerView.Adapter<HoaDonChiTietTungPhongAdapter.HoaDonChiTietTungPhongViewHolder> {
    private List<HoaDonChiTiet> hoaDonChiTietList;
    private Context context;
    public HoaDonChiTietTungPhongAdapter(List<HoaDonChiTiet> hoaDonChiTietList, Context context) {
        this.hoaDonChiTietList = hoaDonChiTietList;
        this.context = context;
    }

    @NonNull
    @Override
    public HoaDonChiTietTungPhongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HoaDonChiTietTungPhongViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chi_tiet_hoa_don, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonChiTietTungPhongViewHolder holder, int position) {
        HoaDonChiTiet hoaDonChiTiet=hoaDonChiTietList.get(position);
        holder.tvTongTien.setText(hoaDonChiTiet.getSoTienThanhToan()+" VND");
        getTienDichVu(hoaDonChiTiet.getIdHoaDonDichVu(),holder.tvTienDien,holder.tvTienNuoc,holder.tvTienThangMay,holder.tvTienMang,holder.tvTienRac);
        getHopDong(hoaDonChiTiet.getIdPhong(),holder.tvSoNguoi );
        getPhongTro(hoaDonChiTiet.getIdPhong(),holder.tvPhong);

        List<String> trangThaiHoaDon=new ArrayList<>();
        trangThaiHoaDon.add("Chưa thanh toán");
        trangThaiHoaDon.add("Đã thanh toán");
        ArrayAdapter<String> adapter=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,trangThaiHoaDon);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spnTrangThaiHoaDon.setAdapter(adapter);
        final boolean[] isSpinnerFirstLoad = {true};

        getNgayXuatHoaDon(hoaDonChiTiet.getIdHoaDon(),holder.tvNgayXuatHoaDon,holder.spnTrangThaiHoaDon,isSpinnerFirstLoad);

        holder.spnTrangThaiHoaDon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (isSpinnerFirstLoad[0]) {
                    isSpinnerFirstLoad[0] = false;
                } else {
                    String trangThai = (String) parent.getItemAtPosition(i);
                    updateTrangThaiHoaDon(hoaDonChiTiet.getIdHoaDon(), trangThai.equals("Đã thanh toán"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return hoaDonChiTietList!=null?hoaDonChiTietList.size():0;
    }

    public class HoaDonChiTietTungPhongViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvInvoiceId, tvNgayXuatHoaDon, tvTinhTuNgay, tvDenNgay, tvSoNguoi, tvPhong,
                tvTienPhong, tvTienDien, tvTienNuoc, tvTienThangMay, tvTienMang,tvTienRac,tvTongTien;
        Spinner spnTrangThaiHoaDon;
        public HoaDonChiTietTungPhongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvNgayXuatHoaDon = itemView.findViewById(R.id.tv_ngayXuatHoaDon_cthd);
            tvTinhTuNgay = itemView.findViewById(R.id.tv_tinhTuNgay_cthd);
            tvDenNgay = itemView.findViewById(R.id.tv_denNgay_cthd);
            tvSoNguoi = itemView.findViewById(R.id.tv_soNguoi_cthd);
            tvPhong = itemView.findViewById(R.id.tv_phong_cthd);
            tvTienPhong = itemView.findViewById(R.id.tv_tienPhong_cthd);
            tvTienDien = itemView.findViewById(R.id.tv_tienDien_cthd);
            tvTienNuoc = itemView.findViewById(R.id.tv_tienNuoc_cthd);
            tvTienThangMay = itemView.findViewById(R.id.tv_tienThangMay_cthd);
            tvTienMang = itemView.findViewById(R.id.tv_TienMang_cthd);
            tvTongTien = itemView.findViewById(R.id.tv_tongTien_cthd);
            tvTienRac = itemView.findViewById(R.id.tv_tienRac_cthd);
            spnTrangThaiHoaDon=itemView.findViewById(R.id.spnTrangThaiHoaDon);

        }
    }
    private void updateTrangThaiHoaDon(String idHoaDon,boolean trangThai){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HoaDon")
                .document(idHoaDon)
                .update("trangThaiHoaDon",trangThai)
                .addOnSuccessListener(aVoid -> {
                    Log.d("updateTrangThaiHoaDon", "Cập nhật trạng thái hóa đơn thành công");
                })
                .addOnFailureListener(e -> {
                    Log.e("updateTrangThaiHoaDon", "Lỗi khi cập nhật trạng thái hóa đơn: " + e.getMessage());
                });
    }
    private void getNgayXuatHoaDon(String idHoaDon,TextView tvNgayXuatHoaDon,Spinner spnTrangThaiHoaDon,boolean[] isSpinnerFirstLoad){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HoaDon")
                .document(idHoaDon)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        HoaDon hoaDon = documentSnapshot.toObject(HoaDon.class);
                        tvNgayXuatHoaDon.setText(hoaDon.getNgayTao());
                        if (hoaDon.isTrangThaiHoaDon()){
                            spnTrangThaiHoaDon.setSelection(1);
                        }else {
                            spnTrangThaiHoaDon.setSelection(0);
                        }
                        isSpinnerFirstLoad[0] = false;
                };
                });
    }
    private void getHopDong(String idPhong,TextView tvSoNguoi){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HopDong")
                .whereEqualTo("idPhong",idPhong)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            QuanLiHopDongModel quanLiHopDongModel=documentSnapshot.toObject(QuanLiHopDongModel.class);
                            if (quanLiHopDongModel.getIdPhong().equals(idPhong)){
                                tvSoNguoi.setText(quanLiHopDongModel.getSoNguoi()+" người");
                                return;
                            }
                    }
                });
    }
    private void getPhongTro(String idPhong,TextView tvTenPhong){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("PhongTro")
                .document(idPhong)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        QuanLyPhongTroModel phongTroModel = documentSnapshot.toObject(QuanLyPhongTroModel.class);
                        tvTenPhong.setText(phongTroModel.getTenPhong());
                    }
                });
    }
    private void getTienDichVu(String idHoaDonDichVu,TextView tvTienDien,TextView tvTienNuoc,
                               TextView tvTienThangMay,TextView tvTienMang,TextView tvTienRac){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HoaDonDichVu")
                .document(idHoaDonDichVu)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        HoaDonDichVu hoaDonDichVu = documentSnapshot.toObject(HoaDonDichVu.class);
                        tvTienDien.setText(hoaDonDichVu.getTienDien() + " VND"+" /"+hoaDonDichVu.getSoDien()+" kWh");
                        tvTienNuoc.setText(hoaDonDichVu.getTienNuoc() + " VND"+" /"+hoaDonDichVu.getSoNuoc()+" m3");
                        tvTienThangMay.setText(hoaDonDichVu.getTienThangMay() + " VND");
                        tvTienMang.setText(hoaDonDichVu.getTienMang() + " VND");
                        tvTienRac.setText(hoaDonDichVu.getTienRac() + " VND");
                    }
                });
    }
}
