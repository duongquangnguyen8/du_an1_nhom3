package com.example.bduan1.AdminDanhSachPhong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bduan1.R;
import com.example.bduan1.chucnangAdminQuanLyPhongTo.QuanLyPhongTroModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PhongTrong extends Fragment {
    FirebaseFirestore db;
    List<QuanLyPhongTroModel> list;
    RecyclerView recyclerView;
    phongtrongAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_phong_trong, container, false);
        recyclerView = view.findViewById(R.id.rvphongphongtrong);
        list = new ArrayList<>();
        adapter = new phongtrongAdapter(requireContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        FirebaseApp.initializeApp(requireContext());  // Khởi tạo FirebaseApp
        db = FirebaseFirestore.getInstance();  // Khởi tạo Firestore

        layDuLieu();  // Gọi hàm lấy dữ liệu sau khi đã khởi tạo db
        return view;
    }

    public void layDuLieu() {
        db.collection("PhongTro")
                .whereEqualTo("trangThaiPhong", "Còn trống")  // Chỉ lấy các phòng có trạng thái "Còn trống"
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }
                    if (value != null) {
                        list.clear();  // Xóa danh sách hiện tại trước khi thêm dữ liệu mới
                        for (QueryDocumentSnapshot doc : value) {
                            QuanLyPhongTroModel phongTro = doc.toObject(QuanLyPhongTroModel.class);
                            phongTro.setId(doc.getId());
                            list.add(phongTro);
                        }
                        adapter.notifyDataSetChanged();  // Cập nhật RecyclerView sau khi dữ liệu thay đổi
                    }
                });
    }
}
