package com.example.bduan1.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bduan1.QuanLyHoaDon.HoaDon;
import com.example.bduan1.R;
import com.example.bduan1.screens.BillDetailUserActivity;

import java.util.List;

public class BillDetailUserAdapter extends RecyclerView.Adapter<BillDetailUserAdapter.BillDetailUserViewHolder> {
    private List<HoaDon> hoaDonList;
    private Context context;
    public BillDetailUserAdapter(List<HoaDon> hoaDonList, Context context) {
        this.hoaDonList = hoaDonList;
        this.context = context;
    }

    @NonNull
    @Override
    public BillDetailUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillDetailUserViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_bill, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillDetailUserViewHolder holder, int position) {
        HoaDon hoaDon = hoaDonList.get(position);
        holder.tvTitleBill.setText("Hoá đơn xuất ngày: " + hoaDon.getNgayTao());
        if (hoaDon.isTrangThaiHoaDon()) {
            holder.tv_unpaid_bill.setText("Đã thanh toán");
            holder.tv_unpaid_bill.setTextColor(context.getResources().getColor(R.color.your_primary_color));
        }else{
            holder.tv_unpaid_bill.setText("Chưa thanh toán");
            holder.tv_unpaid_bill.setTextColor(context.getResources().getColor(R.color.red_status_bill));

        }
        holder.tvSeeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, BillDetailUserActivity.class);
                intent.putExtra("hoaDonId",hoaDon.getIdHoaDon());
                intent.putExtra("trangThai",hoaDon.isTrangThaiHoaDon());
                intent.putExtra("ngayXuatHoaDon",hoaDon.getNgayTao());
                context.startActivity(intent);
             }
        });
    }

    @Override
    public int getItemCount() {
        return hoaDonList!=null?hoaDonList.size():0;
    }

    public class BillDetailUserViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleBill, tvSeeDetail, tv_unpaid_bill;

        public BillDetailUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleBill = itemView.findViewById(R.id.tvTitleBill);
            tvSeeDetail = itemView.findViewById(R.id.tvSeeDetail);
            tv_unpaid_bill = itemView.findViewById(R.id.tv_unpaid_bill);

        }
    }
}