package com.example.bduan1.chucnangAdminQuanLyPhongTo;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bduan1.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class QuanLyPhongTroAdapter extends RecyclerView.Adapter<QuanLyPhongTroAdapter.ViewHolder> {
    private final Context context;
    private final List<QuanLyPhongTroModel> list;
    private final FirebaseFirestore db;

    public QuanLyPhongTroAdapter(Context context, List<QuanLyPhongTroModel> list) {
        this.context = context;
        this.list = list;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quanlyphongtro, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuanLyPhongTroModel phongTro=list.get(position);
        holder.tvTenPhong.setText(phongTro.getTenPhong());
        holder.tvTrangThai.setText(phongTro.getTrangThaiPhong());
        Toast.makeText(context, ""+phongTro.getId(), Toast.LENGTH_SHORT).show();
        holder.btnDelete.setOnClickListener(v -> {
            String documentId = phongTro.getId();
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("Xác nhận");
            builder.setMessage("Bạn có muốn xóa phòng này?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                if (documentId == null || documentId.isEmpty()) {
                    Toast.makeText(context, "Document ID is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                db.collection("PhongTro").document(documentId)
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            if (position >= 0 && position < list.size()) {
                                list.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(context, "Đã xóa phòng " + phongTro.getTenPhong(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Invalid position for deletion", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show());
            });
            builder.setNegativeButton("Không", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog dialog=builder.create();
            dialog.show();

        });

        holder.btnUpdate.setOnClickListener(v -> {
            openUpdateDialog(phongTro, position);
        });

    }

    private void openUpdateDialog(QuanLyPhongTroModel phongTro, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.item_updatephongtro, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText edtTenPhong = dialogView.findViewById(R.id.edtTenPhong_update);
        Spinner spinnerTrangThai = dialogView.findViewById(R.id.spnTrangThaiPhong_update);
        EditText edtTienPhong = dialogView.findViewById(R.id.edtTienPhong_update);
        EditText edtMoTaPhong = dialogView.findViewById(R.id.edtMoTaPhong_update);
        Button btncapnhatphong = dialogView.findViewById(R.id.btnCapNhatPhong_update);

        edtTenPhong.setText(phongTro.getTenPhong());
        edtTienPhong.setText(String.valueOf(phongTro.getTienPhong()));
        edtMoTaPhong.setText(phongTro.getMoTaPhong());
        ArrayList<String> listSpiner=new ArrayList<>();
        listSpiner.add("Còn trống");
        listSpiner.add("Đã thuê");
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listSpiner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrangThai.setAdapter(adapterSpinner);
        if (phongTro.getTrangThaiPhong().equals("Còn trống")){
            spinnerTrangThai.setSelection(0);
        }else{
            spinnerTrangThai.setSelection(1);
        }

        btncapnhatphong.setOnClickListener(view -> {
            String tenPhong = edtTenPhong.getText().toString();
            String trangThai = spinnerTrangThai.getSelectedItem().toString();
            String mota=edtMoTaPhong.getText().toString();
            String tienPhongText = edtTienPhong.getText().toString();


            if (tenPhong.isEmpty() || tienPhongText.isEmpty()|| mota.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                double tienPhong = Double.parseDouble(tienPhongText);

                phongTro.setTenPhong(tenPhong);
                phongTro.setTrangThaiPhong(trangThai);
                phongTro.setTienPhong(tienPhong);
                phongTro.setMoTaPhong(mota);

                db.collection("PhongTro").document(phongTro.getId())
                        .set(phongTro)
                        .addOnSuccessListener(aVoid -> {
                            list.set(position, phongTro);
                            notifyItemChanged(position);
                            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        })
                        .addOnFailureListener(e -> Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show());

            } catch (NumberFormatException e) {
                Toast.makeText(context, "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    @Override
    public int getItemCount() {
        return list!=null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenPhong, tvTrangThai;
        ImageButton btnDelete, btnUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvtenphong_qlpt);
            tvTrangThai = itemView.findViewById(R.id.tvtrangthaiqlpt);
            btnDelete = itemView.findViewById(R.id.btnxoaqlpt);
            btnUpdate = itemView.findViewById(R.id.btnsuaqlpt);
        }
    }
}
