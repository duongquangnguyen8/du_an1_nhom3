package com.example.bduan1.AdminDanhSachPhong;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HoatDong extends FragmentStateAdapter {
    public HoatDong(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PhongDaThue();
            case 1:
                return new PhongTrong();
            default:
                return new PhongDaThue();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
