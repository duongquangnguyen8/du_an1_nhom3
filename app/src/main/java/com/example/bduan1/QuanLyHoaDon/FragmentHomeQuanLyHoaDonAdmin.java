package com.example.bduan1.QuanLyHoaDon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bduan1.R;
import com.example.bduan1.chucnangAdminQuanLyPhongTo.FragmentQuanLyPhongTro;

public class FragmentHomeQuanLyHoaDonAdmin extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_hoa_don_admin, container, false);

        LinearLayout llxuathoadon = view.findViewById(R.id.ll_xuat_hoa_don);
        LinearLayout llQuanLiHoaDon=view.findViewById(R.id.ll_quan_li_hoa_don);
        llQuanLiHoaDon.setOnClickListener(v->{
            addFragment(new QuanLiHoaDonAdminFragment());
        });
        llxuathoadon.setOnClickListener(v -> {

            addFragment(new XuatHoaDonAdmin());
        });
       return view;
    }
    private void addFragment(Fragment fragment){
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
