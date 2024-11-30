package com.example.bduan1.AdminDanhSachPhong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bduan1.R;
import com.example.bduan1.chucnangAdminQuanLyPhongTo.QuanLyPhongTroModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class phongtrongAdapter extends RecyclerView.Adapter<phongtrongAdapter.phongtrongHolder> {
    private Context context;
    private List<QuanLyPhongTroModel> list;
    private FirebaseFirestore db;
    public phongtrongAdapter(Context context, List<QuanLyPhongTroModel> list) {
        this.context = context;
        this.list = list;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public phongtrongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quan_ly_danh_sach_trong, parent, false);
        return new phongtrongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull phongtrongHolder holder, int position) {
        QuanLyPhongTroModel model = list.get(position);
        holder.TvTenPhong.setText(model.getTenPhong());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class phongtrongHolder extends RecyclerView.ViewHolder {
        private TextView TvTenPhong;
        public phongtrongHolder(@NonNull View itemView) {
            super(itemView);
            TvTenPhong = itemView.findViewById(R.id.tvtenphong_dspct);
        }
    }
}
