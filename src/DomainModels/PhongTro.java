/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomainModels;

/**
 *
 * @author redstar
 */
public class PhongTro {

    private String MaPT;
    private String MaLoaiPhong;
    private Double dienTich;
    private int tang;
    private Double giaPhong;
    private String moTa;
    private int trangthai;

    public PhongTro() {
    }

    public PhongTro(String MaPT, String MaLoaiPhong, Double dienTich, int tang, Double giaPhong, String moTa, int trangthai) {
        this.MaPT = MaPT;
        this.MaLoaiPhong = MaLoaiPhong;
        this.dienTich = dienTich;
        this.tang = tang;
        this.giaPhong = giaPhong;
        this.moTa = moTa;
        this.trangthai = trangthai;
    }

    public String getMaPT() {
        return MaPT;
    }

    public void setMaPT(String MaPT) {
        this.MaPT = MaPT;
    }

    public String getMaLoaiPhong() {
        return MaLoaiPhong;
    }

    public void setMaLoaiPhong(String MaLoaiPhong) {
        this.MaLoaiPhong = MaLoaiPhong;
    }

    public Double getDienTich() {
        return dienTich;
    }

    public void setDienTich(Double dienTich) {
        this.dienTich = dienTich;
    }

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }

    public Double getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(Double giaPhong) {
        this.giaPhong = giaPhong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }


}
