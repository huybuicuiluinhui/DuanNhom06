
package DomainModels;

import java.util.Date;

public class HopDong {
    private String MaHD;
    private String MaKH;
    private String MaPT;
    private Date NgayKy;
    private Date NgayHetHan;

    private Double TienCoc;
    private String MoTa;
    private int TrangThai;

    public HopDong() {
    }

    public HopDong(String MaHD, String MaKH, String MaPT, Date NgayKy, Date NgayHetHan,  Double TienCoc, String MoTa, int TrangThai) {
        this.MaHD = MaHD;
        this.MaKH = MaKH;
        this.MaPT = MaPT;
        this.NgayKy = NgayKy;
        this.NgayHetHan = NgayHetHan;
    
        this.TienCoc = TienCoc;
        this.MoTa = MoTa;
        this.TrangThai = TrangThai;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getMaPT() {
        return MaPT;
    }

    public void setMaPT(String MaPT) {
        this.MaPT = MaPT;
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

 
}
