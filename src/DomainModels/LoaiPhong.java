
package DomainModels;

public class LoaiPhong {
    private String maLP;
    private String tenLP;
    private String moTa;
    private int TrangThai;

    public LoaiPhong( String maLP, String tenLP, String moTa, int TrangThai) {
        this.maLP = maLP;
        this.tenLP = tenLP;
        this.moTa = moTa;
        this.TrangThai = TrangThai;
    }
    public LoaiPhong() {
    }
    public String getMaLP() {
        return maLP;
    }

    public void setMaLP(String maLP) {
        this.maLP = maLP;
    }

    public String getTenLP() {
        return tenLP;
    }

    public void setTenLP(String tenLP) {
        this.tenLP = tenLP;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }
    
}
