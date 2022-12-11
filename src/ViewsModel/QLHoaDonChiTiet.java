/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewsModel;

import java.awt.print.Printable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author PC
 */
public class QLHoaDonChiTiet {

    private String maHoaDon;
    private String tenKh;
    private String maPongTro;
    private double soTienDV;
    private double tienPhong;
    private Date ngayTao;
    private int TrangThai;
    private String sdt;
    private String tenDichVu;
    private double giaDV;
    private String daSuDung;
    private double tongTien;
    private String donVi;

    public QLHoaDonChiTiet(String maHoaDon, String tenDichVu, double giaDV, String daSuDung, String donVi, double tongTien) {
        this.maHoaDon = maHoaDon;
        this.giaDV = giaDV;
        this.tenDichVu = tenDichVu;
        this.tongTien = tongTien;
        this.daSuDung = daSuDung;
        this.donVi = donVi;
    }

    public QLHoaDonChiTiet() {
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getMaPongTro() {
        return maPongTro;
    }

    public void setMaPongTro(String maPongTro) {
        this.maPongTro = maPongTro;
    }

    public double getSoTienDV() {
        return soTienDV;
    }

    public void setSoTienDV(double soTienDV) {
        this.soTienDV = soTienDV;
    }

    public double getTienPhong() {
        return tienPhong;
    }

    public void setTienPhong(double tienPhong) {
        this.tienPhong = tienPhong;
    }

    

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    
    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public double getGiaDV() {
        return giaDV;
    }

    public void setGiaDV(double giaDV) {
        this.giaDV = giaDV;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
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

    public Object[] toDataRow() {
         BigDecimal bigDecimal = new BigDecimal(tongTien);
            String str=bigDecimal.toString();
        return new Object[]{maHoaDon, tenDichVu, giaDV + " VNĐ", daSuDung + " " + donVi, str+ " VNĐ"};
    }
    public String gettrangthai() {
        if (TrangThai == 1) {
            return "Đã thanh toán";
        } else if(TrangThai==0){
            return "Chưa thanh toán";
        }
        else{
            return "";
        }
    }
    
        public Object[] toDataRowThongKe() {
            BigDecimal bigDecimal = new BigDecimal(soTienDV+tienPhong);
            String str=bigDecimal.toString();
            
        return new Object[]{maHoaDon, maPongTro, tenKh, sdt,str,ngayTao,gettrangthai()};
    }
}
