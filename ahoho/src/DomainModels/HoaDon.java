
package DomainModels;

import java.sql.Date;

public class HoaDon {
  
    private String MaHDon;
    private String maHopDong;
    private double tongTien;
    private Date NgayTao;
    private int TrangThai;

    public HoaDon(  String MaHDon,String maHopDong,  Date NgayTao,double tongTien,  int TrangThai) {
       
        this.maHopDong = maHopDong;
        this.MaHDon = MaHDon;
        this.tongTien = tongTien;
        this.NgayTao = NgayTao;
       
        this.TrangThai = TrangThai;
    }

    public HoaDon() {
    }

    public String getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(String maHopDong) {
        this.maHopDong = maHopDong;
    }

    

    public String getMaHDon() {
        return MaHDon;
    }

    public void setMaHDon(String MaHDon) {
        this.MaHDon = MaHDon;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
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
    
}
