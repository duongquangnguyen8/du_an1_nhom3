package com.example.bduan1.QuanLyHopDongAdmin;

public class QuanLiHopDongChiTietModel {
    private String idHopDongChiTiet;
    private String idHopDong;
    private String idDichVu;

    public QuanLiHopDongChiTietModel(String idHopDong, String idDichVu) {
        this.idHopDong = idHopDong;
        this.idDichVu = idDichVu;
    }
    public QuanLiHopDongChiTietModel(){};
    public String getIdHopDongChiTiet() {
        return idHopDongChiTiet;
    }

    public void setIdHopDongChiTiet(String idHopDongChiTiet) {
        this.idHopDongChiTiet = idHopDongChiTiet;
    }

    public String getIdHopDong() {
        return idHopDong;
    }

    public void setIdHopDong(String idHopDong) {
        this.idHopDong = idHopDong;
    }

    public String getIdDichVu() {
        return idDichVu;
    }

    public void setIdDichVu(String idDichVu) {
        this.idDichVu = idDichVu;
    }
}
