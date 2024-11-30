package com.example.bduan1.XuLyYeuCauAdmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bduan1.R;
import com.example.bduan1.chucnangAdminQuanLyPhongTo.QuanLyPhongTroModel;

import java.util.List;

public class XuLyYeuCauAdapter extends RecyclerView.Adapter<XuLyYeuCauAdapter.ViewHolder> {
    private final Context context;
    private final List<QuanLyPhongTroModel> list;

    public XuLyYeuCauAdapter(Context context, List<QuanLyPhongTroModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_xu_ly_yeu_cau, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuanLyPhongTroModel phongTro = list.get(position);

        // Kiểm tra và gán giá trị an toàn
        holder.tvtenphong.setText(phongTro.getTenPhong() != null ? phongTro.getTenPhong() : "Không có tên phòng");
//        holder.tvyeucau.setText(phongTro.get() != null ? phongTro.getYeuCau() : "Không có yêu cầu");
//        holder.sptrangthai.setText(
//                phongTro.getTrangThaiYeuCau() != null && !phongTro.getTrangThaiYeuCau().isEmpty()
//                        ? phongTro.getTrangThaiYeuCau()
//                        : "Chưa xử lý"
//        );

        // Khi người dùng nhấn vào item
//        holder.itemView.setOnClickListener(view -> {
//            // Hiển thị dialog xử lý
//            View dialogView = LayoutInflater.from(context).inflate(R.layout.item_chi_tiet_xu_ly, null);
//            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
//            builder.setView(dialogView);
//            android.app.AlertDialog dialog = builder.create();
//
//            // Ánh xạ các view trong dialog
//            TextView tvYeuCau = dialogView.findViewById(R.id.tv_yeu_cau_xl);
//            TextView tvTrangThai = dialogView.findViewById(R.id.tv_trang_thai_xl);
//            EditText edPhanHoi = dialogView.findViewById(R.id.ed_phan_hoi_xl);
//            Button btnHuy = dialogView.findViewById(R.id.btn_huy_xl);
//            Button btnGui = dialogView.findViewById(R.id.btn_gui_xl);
//
//            // Gán dữ liệu từ item vào dialog
//            tvYeuCau.setText("Yêu cầu: " + (phongTro.getYeuCau() != null ? phongTro.getYeuCau() : "Không có yêu cầu"));
//            tvTrangThai.setText("Trạng thái: " + (phongTro.getTrangThaiYeuCau() != null ? phongTro.getTrangThaiYeuCau() : "Chưa xử lý"));
//
//            // Xử lý nút Hủy
//            btnHuy.setOnClickListener(v -> dialog.dismiss());
//
//            // Xử lý nút Gửi
//            btnGui.setOnClickListener(v -> {
//                String phanHoi = edPhanHoi.getText().toString().trim();
//                if (!phanHoi.isEmpty()) {
//                    // Cập nhật trạng thái và dữ liệu phản hồi
//                    phongTro.setTrangThaiYeuCau("Đã xử lý");
//                    notifyItemChanged(holder.getAdapterPosition());
//                    dialog.dismiss();
//                } else {
//                    edPhanHoi.setError("Phản hồi không được để trống!");
//                }
//            });
//
//            // Hiển thị dialog
//            dialog.show();
//        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvtenphong, tvyeucau, sptrangthai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtenphong = itemView.findViewById(R.id.tvtenphong_xlyc);
            tvyeucau = itemView.findViewById(R.id.tvyeucau_xlyc);
            sptrangthai = itemView.findViewById(R.id.sptrangthai_xlyc);
        }
    }
}
