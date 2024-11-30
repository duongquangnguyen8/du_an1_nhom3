package com.example.bduan1.chucnangAdminQuanLyPhongTo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bduan1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentQuanLyPhongTro extends Fragment {
    private FirebaseFirestore db;
    private List<QuanLyPhongTroModel> list;
    private RecyclerView recyclerView;
    private QuanLyPhongTroAdapter adapter;
    private Button btnAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_phong_tro, container, false);
        db=FirebaseFirestore.getInstance();
        list=new ArrayList<>();
        recyclerView=view.findViewById(R.id.rvDanhSachPhong);
        btnAdd=view.findViewById(R.id.btnThemPhong);
        getPhongTro();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });
        return view;
    }
    private void getPhongTro(){
        db.collection("PhongTro").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    list.clear();
                    for (QueryDocumentSnapshot document:task.getResult()){
                        QuanLyPhongTroModel phongTro=document.toObject(QuanLyPhongTroModel.class);
                        phongTro.setId(document.getId());
                        list.add(phongTro);
                    }
                    Log.d("phongtro",list.size()+"");
                    adapter=new QuanLyPhongTroAdapter(getContext(),list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    Log.d("phongtro","fail"+task.getException());
                }
            }
        });
    }


    private void openAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View dialogView = inflater.inflate(R.layout.item_themphongtro, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        EditText edtTenPhong = dialogView.findViewById(R.id.edtTenPhong_Them);
        EditText edtTienPhong = dialogView.findViewById(R.id.edtTienPhong_them);
        EditText edtMotaPhong = dialogView.findViewById(R.id.edtMoTaPhong);
        Button btnLuuPhong = dialogView.findViewById(R.id.btnLuuPhong_them);

        btnLuuPhong.setOnClickListener(view -> {
            String edtTenPhongText = edtTenPhong.getText().toString();
            String edtTienPhongText = edtTienPhong.getText().toString();
            String edtMotaPhongText = edtMotaPhong.getText().toString();
            if (edtTenPhongText.isEmpty() || edtTienPhongText.isEmpty() || edtMotaPhongText.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                addPhongTroToFirestore(edtTenPhongText, edtMotaPhongText, Double.parseDouble(edtTienPhongText));
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void showDatePickerDialog(EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(editText.getContext(), (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            editText.setText(selectedDate);
        }, 2024, 0, 1);
        datePickerDialog.show();
    }

    private void addPhongTroToFirestore(String tenPhong, String moTaPhong, double tienPhong) {
        db=FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("tenPhong", tenPhong);
        map.put("moTaPhong", moTaPhong);
        map.put("tienPhong", tienPhong);
        map.put("trangThaiPhong", "Còn trống");
        db.collection("PhongTro")
                .add(map)
                .addOnSuccessListener(documentReference -> {
                    QuanLyPhongTroModel quanLyPhongTroModel=new QuanLyPhongTroModel();
                    quanLyPhongTroModel.setId(documentReference.getId());
                    Toast.makeText(requireContext(), "Thêm thành công! ", Toast.LENGTH_SHORT).show();
                    getPhongTro();
                })
                .addOnFailureListener(e -> {
                    Log.d("phongtro", "Error adding document" + e.getMessage());
                });
    }
}
