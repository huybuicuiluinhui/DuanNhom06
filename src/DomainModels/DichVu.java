
package DomainModels;

public class DichVu {
    private String maDV;
    private String tenDV;
    private Double giaTien;
    private String donVi;
    private int trangThai;

    public DichVu(String maDV, String tenDV, Double giaTien, String donVi, int trangThai) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.giaTien = giaTien;
        this.donVi = donVi;
        this.trangThai = trangThai;
    }

    public DichVu() {
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    public Double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(Double giaTien) {
        this.giaTien = giaTien;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

  
    
}
