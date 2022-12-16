
package DomainModels;

public class GoiDichVu {
    private String id;
    private String idDichVu;
    private String MaGoiDichVu;
    private String TenGoiDichVu;
    private String CacGDV;
    private int TrangThai;

    public GoiDichVu() {
    }

    public GoiDichVu(String id, String idDichVu, String MaGoiDichVu, String TenGoiDichVu, String CacGDV, int TrangThai) {
        this.id = id;
        this.idDichVu = idDichVu;
        this.MaGoiDichVu = MaGoiDichVu;
        this.TenGoiDichVu = TenGoiDichVu;
        this.CacGDV = CacGDV;
        this.TrangThai = TrangThai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdDichVu() {
        return idDichVu;
    }

    public void setIdDichVu(String idDichVu) {
        this.idDichVu = idDichVu;
    }

    public String getMaGoiDichVu() {
        return MaGoiDichVu;
    }

    public void setMaGoiDichVu(String MaGoiDichVu) {
        this.MaGoiDichVu = MaGoiDichVu;
    }

    public String getTenGoiDichVu() {
        return TenGoiDichVu;
    }

    public void setTenGoiDichVu(String TenGoiDichVu) {
        this.TenGoiDichVu = TenGoiDichVu;
    }

    public String getCacGDV() {
        return CacGDV;
    }

    public void setCacGDV(String CacGDV) {
        this.CacGDV = CacGDV;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }
    
}
