package com.example.bduan1.AdminDanhSachPhong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bduan1.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FragmentDanhSachPhong extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 pager2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(com.example.bduan1.R.layout.fragment_danh_sach_phong, container, false);
        requireActivity().getWindow().setStatusBarColor(
                ContextCompat.getColor(requireContext(), R.color.your_primary_dark_color)
        );

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        tabLayout = view.findViewById(R.id.tab_layout);
        pager2 = view.findViewById(R.id.view_pager);

        FragmentActivity activity = getActivity();
        if(activity != null) {
            HoatDong hoatDong = new HoatDong(activity);
            pager2.setAdapter(hoatDong);

            new TabLayoutMediator(tabLayout, pager2, (tab, position) -> {
                switch (position) {
                    case 0:
                        tab.setText("Phòng đã thuê");
                        break;
                    case 1:
                        tab.setText("Phòng trống");
                        break;
                }
            }).attach();

        }


        return view;
    }
}
