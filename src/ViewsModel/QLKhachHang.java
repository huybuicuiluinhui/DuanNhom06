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
public class QLKhachHang {
     private String maKH;
    private String tenKH;
    private Date ngaySinh;
    private int gioiTinh;
    private String SDT;
    private String diaChi;
    private String CCCD;
    private String Email;
    private int TrangThai;
    public QLKhachHang() {
    }

    public QLKhachHang(String maKH, String tenKH, Date ngaySinh, int gioiTinh, String SDT, String diaChi, String CCCD, String Email, int TrangThai) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.SDT = SDT;
        this.diaChi = diaChi;
        this.CCCD = CCCD;
        this.Email = Email;
        this.TrangThai = TrangThai;
    }

  
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }
    
}
