package com.example.bduan1;

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

import com.example.bduan1.AdminDanhSachPhong.FragmentDanhSachPhong;
import com.example.bduan1.Login.DangNhap;
import com.example.bduan1.QuanLyHoaDon.FragmentHomeQuanLyHoaDonAdmin;
import com.example.bduan1.QuanLyHopDongAdmin.FragmentQuanLyHopDong;
import com.example.bduan1.R;
import com.example.bduan1.ThongBaoAdmin.FragmentThongBaoAdmin;
import com.example.bduan1.XuLyYeuCauAdmin.FragmentXuLyYeuCauAdmin;
import com.example.bduan1.chucnangAdminQuanLyPhongTo.FragmentQuanLyPhongTro;
import com.example.bduan1.fragment.FragmentDichVu_Admin;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        LinearLayout llQuanLyPhongTro = view.findViewById(R.id.llQuanLyPhongTro);
        LinearLayout llDanhSachPhong = view.findViewById(R.id.llDanhSachPhong);
        LinearLayout llThongBao = view.findViewById(R.id.llThongBao);
        LinearLayout llQuanLyHoaDon = view.findViewById(R.id.llQuanLyHoaDon);
        LinearLayout llXuLyYeuCau = view.findViewById(R.id.llXuLyYeuCauHoTro);
        LinearLayout llHopDong = view.findViewById(R.id.llQuanLyHopDong);
        LinearLayout llDichVu = view.findViewById(R.id.llQuanLiDichVu);
        LinearLayout llDangXuat = view.findViewById(R.id.llDangXuat);

        llHopDong.setOnClickListener(view1 -> {
            FragmentTransaction HopDongTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            HopDongTransaction.replace(R.id.fragmentContainer, new FragmentQuanLyHopDong());
            HopDongTransaction.addToBackStack(null);
            HopDongTransaction.commit();

        });

        llXuLyYeuCau.setOnClickListener(view1 -> {
            FragmentTransaction xuLyYeuCauTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            xuLyYeuCauTransaction.replace(R.id.fragmentContainer, new FragmentXuLyYeuCauAdmin());
            xuLyYeuCauTransaction.addToBackStack(null);
            xuLyYeuCauTransaction.commit();

        });

        llQuanLyHoaDon.setOnClickListener(view1 -> {
            FragmentTransaction hoaDonTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            hoaDonTransaction.replace(R.id.fragmentContainer, new FragmentHomeQuanLyHoaDonAdmin());
            hoaDonTransaction.addToBackStack(null);
            hoaDonTransaction.commit();
        });

        llThongBao.setOnClickListener(view1 -> {
            FragmentTransaction thongbaoTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            thongbaoTransaction.replace(R.id.fragmentContainer, new FragmentThongBaoAdmin());
            thongbaoTransaction.addToBackStack(null);
            thongbaoTransaction.commit();
        });


        llDanhSachPhong.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, new FragmentDanhSachPhong());
            transaction.addToBackStack(null);
            transaction.commit();
        });


        llQuanLyPhongTro.setOnClickListener(v -> {

            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, new FragmentQuanLyPhongTro());
            transaction.addToBackStack(null);
            transaction.commit();
        });
        llDichVu.setOnClickListener(view1 -> {
            FragmentTransaction dichVuTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            dichVuTransaction.replace(R.id.fragmentContainer, new FragmentDichVu_Admin());
            dichVuTransaction.addToBackStack(null);
            dichVuTransaction.commit();

        });
        llDangXuat.setOnClickListener(view1 -> {
            mAuth.signOut();
            Intent intent = new Intent(getContext(), DangNhap.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            requireActivity().finish();
        });
        return view;
    }
}
