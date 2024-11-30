package com.example.bduan1.AdminDanhSachPhong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bduan1.QuanLyHopDongAdmin.QuanLiHopDongModel;
import com.example.bduan1.R;
import com.example.bduan1.chucnangAdminQuanLyPhongTo.QuanLyPhongTroModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class phongdathueAdapter extends RecyclerView.Adapter<phongdathueAdapter.phongdathueHolder> {
    private Context context;
    private List<QuanLyPhongTroModel> list;
    private FirebaseFirestore db;

    public phongdathueAdapter(Context context, List<QuanLyPhongTroModel> list) {
        this.context = context;
        this.list = list;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public phongdathueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danh_sach_phong_da_thue, parent, false);
        return new phongdathueHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull phongdathueHolder holder, int position) {
        QuanLyPhongTroModel model = list.get(position);
        holder.tvTenPhong.setText(model.getTenPhong());
        getNgay(model.getId(),holder);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class phongdathueHolder extends RecyclerView.ViewHolder {
        TextView tvTenPhong, tvNgayBatDau, tvNgayKetThuc;

        public phongdathueHolder(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvtenphong_dspdt);
            tvNgayBatDau = itemView.findViewById(R.id.tvngaybatdau_dspdt);
            tvNgayKetThuc = itemView.findViewById(R.id.tvngayketthuc_dspdt);

        }
    }private void getNgay(String idPhong,phongdathueAdapter.phongdathueHolder holder){
        db.collection("HopDong")
                .whereEqualTo("idPhong",idPhong)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            QuanLiHopDongModel model = document.toObject(QuanLiHopDongModel.class);
                            holder.tvNgayBatDau.setText(model.getNgayBatDau());
                            holder.tvNgayKetThuc.setText(model.getNgayKetThuc());

                        }
                    }
                });
    }
}
