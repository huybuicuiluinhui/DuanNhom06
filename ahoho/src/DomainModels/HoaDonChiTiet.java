/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DomainModels;

/**
 *
 * @author PC
 */
public class HoaDonChiTiet {
    private String maHoaDon;
    private String maDichVu;
    private double tongTien;
    private String daSuDung;

    public HoaDonChiTiet(String maHoaDon, String maDichVu, double tongTien, String daSuDung) {
        this.maHoaDon = maHoaDon;
        this.maDichVu = maDichVu;
        this.tongTien = tongTien;
        this.daSuDung = daSuDung;
    }

    public HoaDonChiTiet() {
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getDaSuDung() {
        return daSuDung;
    }

    public void setDaSuDung(String daSuDung) {
        this.daSuDung = daSuDung;
    }
    
}
