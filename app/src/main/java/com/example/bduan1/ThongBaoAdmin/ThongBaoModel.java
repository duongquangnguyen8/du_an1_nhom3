package com.example.bduan1.ThongBaoAdmin;

public class ThongBaoModel {
    int id;
    String tieuDe;
    String chiTiet;
    String ngay;

    public ThongBaoModel() {
    }

    public ThongBaoModel(int id, String tieuDe, String chiTiet, String ngay) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.chiTiet = chiTiet;
        this.ngay = ngay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
