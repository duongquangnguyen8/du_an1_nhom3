package com.example.bduan1.QuanLyHoaDon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bduan1.R;
import com.example.bduan1.chucnangAdminQuanLyPhongTo.QuanLyPhongTroModel;

import java.util.List;

public class QuanLiHoaDonAdminAdapter extends RecyclerView.Adapter<QuanLiHoaDonAdminAdapter.QuanLiHoaDonAdminViewHolder> {
    private List<QuanLyPhongTroModel> listPhongTro;
    private Context context;
    private FragmentManager fragmentManager;
    public QuanLiHoaDonAdminAdapter(List<QuanLyPhongTroModel> listPhongTro, Context context) {
        this.listPhongTro = listPhongTro;
        this.context = context;
    }

    @NonNull
    @Override
    public QuanLiHoaDonAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuanLiHoaDonAdminViewHolder(LayoutInflater.from(context).inflate(R.layout.item_xuat_hoa_don,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull QuanLiHoaDonAdminViewHolder holder, int position) {
        QuanLyPhongTroModel quanLyPhongTroModel=listPhongTro.get(position);
        holder.tvTenPhong.setText(quanLyPhongTroModel.getTenPhong());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HoaDonChiTietTungPhongActivity.class);
            intent.putExtra("tenPhong", quanLyPhongTroModel.getTenPhong());
            intent.putExtra("maPhong",quanLyPhongTroModel.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listPhongTro!=null? listPhongTro.size():0;
    }


    public class QuanLiHoaDonAdminViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTenPhong;
        public QuanLiHoaDonAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenPhong=itemView.findViewById(R.id.tvtenphong_hoadon);
        }
    }
}