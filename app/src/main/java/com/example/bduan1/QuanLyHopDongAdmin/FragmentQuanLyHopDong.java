package com.example.bduan1.QuanLyHopDongAdmin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bduan1.R;
import com.example.bduan1.chucnangAdminQuanLyPhongTo.FragmentQuanLyPhongTro;
import com.example.bduan1.chucnangAdminQuanLyPhongTo.QuanLyPhongTroAdapter;
import com.example.bduan1.chucnangAdminQuanLyPhongTo.QuanLyPhongTroModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentQuanLyHopDong extends Fragment {
    FirebaseFirestore db;
    List<QuanLiHopDongModel> list;
    RecyclerView recyclerView;
    QuanLyHopDongAdapter adapter;
    FloatingActionButton fab_add;
    List<String> listPhongTro;
    private List<String> listIdPhongTro;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_hop_dong, container, false);
        recyclerView = view.findViewById(R.id.rcv_hopdong);
        fab_add=view.findViewById(R.id.fab_add_hopDong);
        FirebaseApp.initializeApp(requireContext());
        db=FirebaseFirestore.getInstance();

        list = new ArrayList<>();
        listPhongTro=new ArrayList<>();
        listIdPhongTro=new ArrayList<>();
        getHopDong();

        adapter = new QuanLyHopDongAdapter(requireContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);


        fab_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });
        return view;

    }
    private void getHopDong(){
        db=FirebaseFirestore.getInstance();
        list.clear();
        if (db!=null ){
            db.collection("HopDong")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                QuanLiHopDongModel hopDong = document.toObject(QuanLiHopDongModel.class);
                                hopDong.setIdHopDong(document.getId());
                                list.add(hopDong);
                                Log.d("hopdong", hopDong.getIdHopDong() + "");
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }else{
            Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
        }
    }
    private void showDialogAdd(){

        listPhongTro.add("Chọn phòng");
        getPhongTroTrong();

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_them_hop_dong,null);
        builder.setView(view);
        EditText edtTenKhachHang=view.findViewById(R.id.edtTenKhachHang);
        EditText edtEmail_KhachHang=view.findViewById(R.id.edtEmail_KhachHang);
        Spinner spnPhong=view.findViewById(R.id.spnPhong);
        EditText edtNgayBatDau=view.findViewById(R.id.edtNgayBatDau);
        EditText edtNgayKetThuc=view.findViewById(R.id.edtNgayKetThuc);
        EditText edtCCCD=view.findViewById(R.id.edtCCCD);
        EditText edtSoNguoi=view.findViewById(R.id.edtSoNguoi);
        EditText edtTienCoc=view.findViewById(R.id.edtTienCoc);
        Button btnLuuHopDong=view.findViewById(R.id.btnLuuHopDong);

        edtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentQuanLyPhongTro.showDatePickerDialog(edtNgayBatDau);
            }
            });
        edtNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentQuanLyPhongTro.showDatePickerDialog(edtNgayKetThuc);
            }
        });
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listPhongTro);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpinner.notifyDataSetChanged();
        spnPhong.setAdapter(adapterSpinner);
        AlertDialog dialog=builder.create();
        dialog.show();
        btnLuuHopDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenKhachHang=edtTenKhachHang.getText().toString();
                String emailKhachHang=edtEmail_KhachHang.getText().toString();
                String phong=spnPhong.getSelectedItem().toString();
                String ngayBatDau=edtNgayBatDau.getText().toString();
                String ngayKetThuc=edtNgayKetThuc.getText().toString();
                String cccd=edtCCCD.getText().toString();
                String soNguoi=edtSoNguoi.getText().toString();
                String tienCoc=edtTienCoc.getText().toString();
                if (tenKhachHang.isEmpty()||tienCoc.isEmpty()||soNguoi.isEmpty()||emailKhachHang.isEmpty()||phong.isEmpty()||ngayBatDau.isEmpty()||cccd.isEmpty()||ngayKetThuc.isEmpty()||spnPhong.getSelectedItemPosition()==0){
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(!isValidEmail(emailKhachHang)){
                        Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng email!", Toast.LENGTH_SHORT).show();
                        return;
                    }if(!isEndDateAfterStartDate(ngayBatDau,ngayKetThuc)){
                        Toast.makeText(getContext(), "Ngày kết thúc phải sau ngày bắt đầu!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    addHopDong(tenKhachHang,soNguoi,emailKhachHang,cccd,listIdPhongTro,ngayBatDau,ngayKetThuc,listPhongTro,spnPhong,tienCoc);
                    dialog.dismiss();
                }

            }
        });


    }
    private void addHopDong(String tenKhachHang,String soNguoi,String emailKhachHang,
                            String cccd,List<String> listIdPhongTro,String ngayBatDau,String ngayKetThuc,List<String> listPhongTro,Spinner spinner,String tienCoc){
        String idPhong=null;
        for (int i=1;i<listPhongTro.size();i++){
            if (spinner.getSelectedItem().toString().equals(listPhongTro.get(i))){
                    idPhong=listIdPhongTro.get(i-1);
                   Log.d("zzz",listIdPhongTro.get(i-1));
                   break;
            }
        }
        if (idPhong==null){
            Toast.makeText(getContext(), "Vui lòng chọn phòng!", Toast.LENGTH_SHORT).show();
            return;
        }else{
            QuanLiHopDongModel hopDong=new QuanLiHopDongModel(tenKhachHang,emailKhachHang,cccd,soNguoi,idPhong,ngayBatDau,ngayKetThuc,tienCoc);
            String finalIdPhong = idPhong;
            db.collection("HopDong")
                    .add(hopDong)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        addHopDongChiTie(documentReference.getId(),"vdYaZn0fm5owoxA1IvoA");
                        updateStatusPhong(finalIdPhong);
                        getHopDong();
                        adapter.notifyDataSetChanged();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                    });
        }
    }
    private void updateStatusPhong(String idPhong){
        db.collection("PhongTro").document(idPhong)
                .update("trangThaiPhong", "Đã thuê")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Cập nhật trạng thái thành công!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Cập nhật trạng thái thất bại!", Toast.LENGTH_SHORT).show();
                });

    }
    private void addHopDongChiTie(String idHopDong,String idDichVu){
        QuanLiHopDongChiTietModel quanLiHopDongChiTietModel=new QuanLiHopDongChiTietModel(idHopDong,idDichVu);
        db.collection("HopDongChiTiet")
                .add(quanLiHopDongChiTietModel)
                .addOnSuccessListener(documentReference -> {
                    quanLiHopDongChiTietModel.setIdHopDongChiTiet(documentReference.getId());
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                });
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
    public static boolean isEndDateAfterStartDate(String startDateStr, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);
            Log.d("DateCheck", "Start Date: " + startDate + ", End Date: " + endDate);
            return endDate.after(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void getPhongTroTrong(){
        db.collection("PhongTro").whereEqualTo("trangThaiPhong","Còn trống").addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                for (DocumentChange doc : value.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        QuanLyPhongTroModel phongTro = doc.getDocument().toObject(QuanLyPhongTroModel.class);
                        Log.d("id",doc.getDocument().getId());
                        phongTro.setId(doc.getDocument().getId()); // Set document ID
                        listPhongTro.add(phongTro.getTenPhong());
                        listIdPhongTro.add(phongTro.getId());
                    }
                }
            }
        });
    }

}
