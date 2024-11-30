package com.example.bduan1.QuanLyHoaDon;

public class HoaDonChiTiet {
    private String id;
    private String idHoaDon;
    private String idPhong;
    private String idHoaDonDichVu;
    private String soTienThanhToan;

    public HoaDonChiTiet(String idHoaDon, String idPhong, String idHoaDonDichVu, String soTienThanhToan) {
        this.idHoaDon = idHoaDon;
        this.idPhong = idPhong;
        this.idHoaDonDichVu = idHoaDonDichVu;
        this.soTienThanhToan = soTienThanhToan;
    }
    public HoaDonChiTiet(){};
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(String idPhong) {
        this.idPhong = idPhong;
    }

    public String getIdHoaDonDichVu() {
        return idHoaDonDichVu;
    }

    public void setIdHoaDonDichVu(String idHoaDonDichVu) {
        this.idHoaDonDichVu = idHoaDonDichVu;
    }

    public String getSoTienThanhToan() {
        return soTienThanhToan;
    }

    public void setSoTienThanhToan(String soTienThanhToan) {
        this.soTienThanhToan = soTienThanhToan;
    }
}
