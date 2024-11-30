package com.example.bduan1.QuanLyHoaDon;


public class HoaDon {
    private String idHoaDon;
    private String idKhachHang;
    private boolean trangThaiHoaDon;
    private String ngayTao;

    public HoaDon(String idKhachHang,  boolean trangThaiHoaDon, String ngayTao) {
        this.idKhachHang = idKhachHang;
        this.trangThaiHoaDon = trangThaiHoaDon;
        this.ngayTao = ngayTao;
    }
    public HoaDon() {}
    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public boolean isTrangThaiHoaDon() {
        return trangThaiHoaDon;
    }

    public void setTrangThaiHoaDon(boolean trangThaiHoaDon) {
        this.trangThaiHoaDon = trangThaiHoaDon;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
}

