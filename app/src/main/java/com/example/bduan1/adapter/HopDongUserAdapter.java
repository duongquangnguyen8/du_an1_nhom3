package com.example.bduan1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bduan1.QuanLyHopDongAdmin.QuanLiHopDongChiTietModel;
import com.example.bduan1.QuanLyHopDongAdmin.QuanLiHopDongModel;
import com.example.bduan1.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.w3c.dom.Document;

import java.util.List;

public class HopDongUserAdapter extends RecyclerView.Adapter<HopDongUserAdapter.HopDongUserViewHolder> {

    private List<QuanLiHopDongModel> listHopDong;
    private Context context;
    private List<String> listidPhong;
    public HopDongUserAdapter(List<QuanLiHopDongModel> listHopDong, Context context) {
        this.listHopDong = listHopDong;
        this.context = context;
    }

    @NonNull
    @Override
    public HopDongUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HopDongUserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_hopdong_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HopDongUserViewHolder holder, int position) {
        QuanLiHopDongModel quanLiHopDongModel = listHopDong.get(position);
        if (quanLiHopDongModel == null) {
            return;
        }
        Log.d("zzz","hihi"+ quanLiHopDongModel.getTienCoc());
        holder.ngayBatDauUser.setText("Ngày bắt đầu: "+quanLiHopDongModel.getNgayBatDau());
        holder.ngayKetThucUser.setText("Ngày kết thúc: "+quanLiHopDongModel.getNgayKetThuc());
        holder.tienCocUser.setText(quanLiHopDongModel.getTienCoc()+"đ");
        holder.tvEmaiUser.setText(quanLiHopDongModel.getEmailKhachHang());
        holder.tvTenUser.setText(quanLiHopDongModel.getTenKhachHang());
        holder.tvSoNguoiThue.setText(quanLiHopDongModel.getSoNguoi()+" người");
        getPhong(quanLiHopDongModel.getIdPhong(),holder);
        getDichVu(holder);
    }

    @Override
    public int getItemCount() {
        return listHopDong.size();
    }

    public class HopDongUserViewHolder extends RecyclerView.ViewHolder {
        private TextView tenPhongUser, ngayBatDauUser, ngayKetThucUser, tienPhongUser, tienCocUser,tvSoNguoiThue,
                tienDienUser, tienNuocUser, tienMangUser, tienThangMayUser, tienRacUser,tvEmaiUser,tvTenUser;
        public HopDongUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tenPhongUser = itemView.findViewById(R.id.tvTenPhongUser);
            ngayBatDauUser = itemView.findViewById(R.id.tvNgayBatDauUser);
            ngayKetThucUser = itemView.findViewById(R.id.tvNgayKetThucUser);
            tienPhongUser = itemView.findViewById(R.id.tvTienPhongUser);
            tienCocUser = itemView.findViewById(R.id.tvTienCocUser);
            tienDienUser = itemView.findViewById(R.id.tvTienDienUser);
            tienNuocUser = itemView.findViewById(R.id.tvTienNuocUser);
            tienMangUser = itemView.findViewById(R.id.tvTienMangUser);
            tienThangMayUser = itemView.findViewById(R.id.tvTienThangMayUser);
            tienRacUser = itemView.findViewById(R.id.tvTienRacUser);
            tvEmaiUser = itemView.findViewById(R.id.tvEmailUser);
            tvTenUser = itemView.findViewById(R.id.tvTenUser);
            tvSoNguoiThue = itemView.findViewById(R.id.tvSoNguoiThueUser);
        }
    }
    private void getDichVu(HopDongUserViewHolder holder){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DichVu")
                .document("vdYaZn0fm5owoxA1IvoA")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            holder.tienDienUser.setText(document.getString("tienDien")+"đ/kwh");
                            holder.tienNuocUser.setText(document.getString("tienNuoc")+"đ/khối");
                            holder.tienMangUser.setText(document.getString("tienMang")+"đ/phòng/tháng");
                            holder.tienThangMayUser.setText(document.getString("tienThangMay")+"đ/người/tháng");
                            holder.tienRacUser.setText(document.getString("tienRac")+"đ/người/tháng");


                        }
                    }
                });
    }
    private void getPhong(String idPhong,HopDongUserViewHolder holder){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PhongTro")
                .document(idPhong)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            holder.tienPhongUser.setText(document.getDouble("tienPhong")+"đ/tháng");
                            holder.tenPhongUser.setText("Tên phòng: "+document.getString("tenPhong"));
                        }
                    }
                });
    }
};
