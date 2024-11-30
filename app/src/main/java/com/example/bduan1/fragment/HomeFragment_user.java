package com.example.bduan1.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bduan1.QuanLyHopDongAdmin.QuanLiHopDongModel;
import com.example.bduan1.R;
import com.example.bduan1.User;
import com.example.bduan1.adapter.HopDongUserAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment_user extends Fragment {

    private RecyclerView recyclerView;
    private  List<QuanLiHopDongModel> listHopDong;
    private HopDongUserAdapter adapter;
    public HomeFragment_user() {
        // Required empty public constructor
    }


    public static HomeFragment_user newInstance(String param1, String param2) {
        HomeFragment_user fragment = new HomeFragment_user();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home2, container, false);
        recyclerView=view.findViewById(R.id.rcv_hopdong_user);

        listHopDong = new ArrayList<>();
        adapter=new HopDongUserAdapter(listHopDong,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        getHopDongUser(User.emailUser);


        return view;
    }
    private void getHopDongUser(String emailUser){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("HopDong")
                .whereEqualTo("emailKhachHang",emailUser)
                .get()
                .addOnCompleteListener(task->{
                    if(task.isSuccessful()) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            QuanLiHopDongModel quanLiHopDongModel = documentSnapshot.toObject(QuanLiHopDongModel.class);
                            if (quanLiHopDongModel != null){
                                listHopDong.add(quanLiHopDongModel);
                                Log.d("zzz",quanLiHopDongModel.getEmailKhachHang());
                            }
                        }
                        adapter.notifyDataSetChanged();
                        Log.d("zzz",listHopDong.size()+"");
                    }
                }).addOnFailureListener(e->{
                    Toast.makeText(getContext(), "Lỗi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                });
    }
}