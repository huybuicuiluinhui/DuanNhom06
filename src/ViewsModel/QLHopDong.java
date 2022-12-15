/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewsModel;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author PC
 */
public class QLHopDong {

    private String MaHD;
    private String tenKH;
    private String MaPT;
    private String tenPhong;
    private double giaPhong;
    private double dienTich;
    private Date NgayKy;
    private Date NgayHetHan;
    private Double TienCoc;
    private String MoTa;
    private int TrangThai;

    public QLHopDong() {
    }

    public QLHopDong(String MaHD, String tenKH, String MaPT, String tenPhong, double giaPhong, double dienTich, Date NgayKy, Date NgayHetHan, Double TienCoc, String MoTa, int TrangThai) {
        this.MaHD = MaHD;
        this.tenKH = tenKH;
        this.MaPT = MaPT;
        this.tenPhong = tenPhong;
        this.giaPhong = giaPhong;
        this.dienTich = dienTich;
        this.NgayKy = NgayKy;
        this.NgayHetHan = NgayHetHan;
        this.TienCoc = TienCoc;
        this.MoTa = MoTa;
        this.TrangThai = TrangThai;
    }

    public String getMaPT() {
        return MaPT;
    }

    public void setMaPT(String MaPT) {
        this.MaPT = MaPT;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public double getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(double giaPhong) {
        this.giaPhong = giaPhong;
    }

    public double getDienTich() {
        return dienTich;
    }

    public void setDienTich(double dienTich) {
        this.dienTich = dienTich;
    }

    public Date getNgayKy() {
        return NgayKy;
    }

    public void setNgayKy(Date NgayKy) {
        this.NgayKy = NgayKy;
    }

    public Date getNgayHetHan() {
        return NgayHetHan;
    }

    public void setNgayHetHan(Date NgayHetHan) {
        this.NgayHetHan = NgayHetHan;
    }

    public Double getTienCoc() {
        return TienCoc;
    }

    public void setTienCoc(Double TienCoc) {
        this.TienCoc = TienCoc;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String gettrangthai() {
        if (TrangThai == 1) {
            return "Còn hạn";
        } else {
            return "Đã hết hạn";
        }
    }

    public Object[] toDataRow() {
        BigDecimal bigDecimal = new BigDecimal(TienCoc);
        String Tien = bigDecimal.toString();
        return new Object[]{MaHD, tenKH, MaPT, tenPhong, giaPhong + " VNĐ", dienTich + " m2", NgayKy, NgayHetHan, Tien + " VNĐ", MoTa, gettrangthai()};
    }

    @Override
    public String toString() {
        return MaPT;
    }

}
