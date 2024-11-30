package com.example.bduan1.ThongBaoAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bduan1.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentThongBaoAdmin extends Fragment {
    List<ThongBaoModel> list;
    RecyclerView recyclerView;
    ThongBaoAdapter adapter;
    FirebaseFirestore db;
    ImageView btnthem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_bao_admin, container, false);
        recyclerView = view.findViewById(R.id.rvThongBao);
        list = new ArrayList<>();
        adapter = new ThongBaoAdapter(requireContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        FirebaseApp.initializeApp(requireContext());
        db = FirebaseFirestore.getInstance();
        btnthem = view.findViewById(R.id.btnThem_thongBao);

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        loadDataFromFirebase();

        return view;
    }

    public void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View dialogView = inflater.inflate(R.layout.item_them_thong_bao_admin, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText edtTieude = dialogView.findViewById(R.id.etTieuDe);
        EditText edtNoiDung = dialogView.findViewById(R.id.etChiTiet);
        Button btnHuy = dialogView.findViewById(R.id.btnHuy);
        Button btnthem = dialogView.findViewById(R.id.btnThem_tb);

        btnthem.setOnClickListener(v -> {
            String tieude = edtTieude.getText().toString().trim();
            String noidung = edtNoiDung.getText().toString().trim();

            if (!tieude.isEmpty() && !noidung.isEmpty()) {
                SimpleDateFormat time = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String date = time.format(new Date());

                ThongBaoModel thongBao = new ThongBaoModel(0, tieude, noidung, date);

                db.collection("ThongBao")
                        .add(thongBao)
                        .addOnSuccessListener(documentReference -> {
                            list.add(thongBao);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        })
                        .addOnFailureListener(e -> {

                        });
            }
        });

        btnHuy.setOnClickListener(v -> dialog.dismiss());
    }

    private void loadDataFromFirebase() {
        CollectionReference collectionReference = db.collection("ThongBao");
        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                list.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    ThongBaoModel thongBao = document.toObject(ThongBaoModel.class);
                    list.add(thongBao);
                }
                adapter.notifyDataSetChanged();
            } else {
                // Xử lý lỗi
            }
        });
    }
}
