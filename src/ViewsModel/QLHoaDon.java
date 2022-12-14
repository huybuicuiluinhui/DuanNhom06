/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewsModel;

import java.sql.Date;

/**
 *
 * @author PC
 */
public class QLHoaDon {

    private String MaHDon;
    private String tenKH;
    private String maDV;
    private String soDT;
    private String maPhong;
    private double giaPhong;
    private Date NgayTao;
    private double TongTien;
    private int TrangThai;

    public QLHoaDon(String MaHDon, String maPhong,String tenKH,String soDT, double giaPhong, Date NgayTao, double TongTien, int TrangThai) {
        this.MaHDon = MaHDon;
        this.maPhong = maPhong;
        this.giaPhong = giaPhong;
        this.NgayTao = NgayTao;
        this.TongTien = TongTien;
        this.TrangThai = TrangThai;
        this.tenKH = tenKH;
        this.soDT = soDT;
    }

    public QLHoaDon() {
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    
    public double getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(double giaPhong) {
        this.giaPhong = giaPhong;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double TongTien) {
        this.TongTien = TongTien;
    }

    public String getMaHDon() {
        return MaHDon;
    }

    public void setMaHDon(String MaHDon) {
        this.MaHDon = MaHDon;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String gettrangthai() {
        if (TrangThai == 1) {
            return "Đã thanh toán";
        } else {
            return "Chưa thanh toán";
        }
    }

    public Object[] toDataRow() {
        return new Object[]{MaHDon, tenKH,soDT,maPhong, giaPhong, NgayTao, TongTien=giaPhong, gettrangthai()};
    }

    @Override
    public String toString() {
        return MaHDon;
    }
    
    
}
