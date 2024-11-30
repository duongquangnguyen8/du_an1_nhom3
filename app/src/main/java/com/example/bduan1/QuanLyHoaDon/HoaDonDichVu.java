package com.example.bduan1.QuanLyHoaDon;

public class HoaDonDichVu {
    private String idHoaDonDichVu;
    private String idDichVu;
    private String tienDien;
    private String tienNuoc;
    private String tienThangMay;
    private String tienMang;
    private String tienRac;
    private String soDien;
    private String soNuoc;
    private String tongTienDichVu;

    public HoaDonDichVu(String idDichVu, String tienDien, String tienNuoc, String tienThangMay, String tienMang, String tienRac,String soDien,String soNuoc, String tongTienDichVu) {
        this.idDichVu = idDichVu;
        this.tienDien = tienDien;
        this.tienNuoc = tienNuoc;
        this.tienThangMay = tienThangMay;
        this.tienMang = tienMang;
        this.tienRac = tienRac;
        this.soDien=soDien;
        this.soNuoc=soNuoc;
        this.tongTienDichVu = tongTienDichVu;
    }
    public HoaDonDichVu() {}
    public String getSoDien() {
        return soDien;
    }

    public void setSoDien(String soDien) {
        this.soDien = soDien;
    }

    public String getSoNuoc() {
        return soNuoc;
    }

    public void setSoNuoc(String soNuoc) {
        this.soNuoc = soNuoc;
    }

    public String getIdHoaDonDichVu() {
        return idHoaDonDichVu;
    }

    public void setIdHoaDonDichVu(String idHoaDonDichVu) {
        this.idHoaDonDichVu = idHoaDonDichVu;
    }

    public String getIdDichVu() {
        return idDichVu;
    }

    public void setIdDichVu(String idDichVu) {
        this.idDichVu = idDichVu;
    }

    public String getTienDien() {
        return tienDien;
    }

    public void setTienDien(String tienDien) {
        this.tienDien = tienDien;
    }

    public String getTienNuoc() {
        return tienNuoc;
    }

    public void setTienNuoc(String tienNuoc) {
        this.tienNuoc = tienNuoc;
    }

    public String getTienThangMay() {
        return tienThangMay;
    }

    public void setTienThangMay(String tienThangMay) {
        this.tienThangMay = tienThangMay;
    }

    public String getTienMang() {
        return tienMang;
    }

    public void setTienMang(String tienMang) {
        this.tienMang = tienMang;
    }

    public String getTienRac() {
        return tienRac;
    }

    public void setTienRac(String tienRac) {
        this.tienRac = tienRac;
    }

    public String getTongTienDichVu() {
        return tongTienDichVu;
    }

    public void setTongTienDichVu(String tongTienDichVu) {
        this.tongTienDichVu = tongTienDichVu;
    }
}
