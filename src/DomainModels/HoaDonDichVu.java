
package DomainModels;

public class HoaDonDichVu {
    private String id;
    private String IdGoiDV;
    private String IdHoaDon;
    private String GoiSuDung;
    private String MoTa;
    private int TrangThai;

    public HoaDonDichVu() {
    }

    public HoaDonDichVu(String id, String IdGoiDV, String IdHoaDon, String GoiSuDung, String MoTa, int TrangThai) {
        this.id = id;
        this.IdGoiDV = IdGoiDV;
        this.IdHoaDon = IdHoaDon;
        this.GoiSuDung = GoiSuDung;
        this.MoTa = MoTa;
        this.TrangThai = TrangThai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdGoiDV() {
        return IdGoiDV;
    }

    public void setIdGoiDV(String IdGoiDV) {
        this.IdGoiDV = IdGoiDV;
    }

    public String getIdHoaDon() {
        return IdHoaDon;
    }

    public void setIdHoaDon(String IdHoaDon) {
        this.IdHoaDon = IdHoaDon;
    }

    public String getGoiSuDung() {
        return GoiSuDung;
    }

    public void setGoiSuDung(String GoiSuDung) {
        this.GoiSuDung = GoiSuDung;
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
