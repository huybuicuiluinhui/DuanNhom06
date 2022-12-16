/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomainModels;


public class TaiKhoan {
    private String username;
    private String password;
    private String hoVaTen;
    private String SDT;
    private String diaChi;
    private String CCCD;
    private String Email;
    private int trangThai;

    public TaiKhoan(String username, String password, String hoVaTen, String SDT, String diaChi, String CCCD, String Email, int trangThai) {
        this.username = username;
        this.password = password;
        this.hoVaTen = hoVaTen;
        this.SDT = SDT;
        this.diaChi = diaChi;
        this.CCCD = CCCD;
        this.Email = Email;
        this.trangThai = trangThai;
    }

    public TaiKhoan() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
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
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    
}
