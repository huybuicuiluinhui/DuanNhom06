/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewsModel;

/**
 *
 * @author PC
 */
public class QLLoaiPhong {
    private String maLP;
    private String tenLP;
    private String moTa;
    private int TrangThai;

    public QLLoaiPhong( String maLP, String tenLP, String moTa, int TrangThai) {
        this.maLP = maLP;
        this.tenLP = tenLP;
        this.moTa = moTa;
        this.TrangThai = TrangThai;
    }
    public QLLoaiPhong() {
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

    @Override
    public String toString() {
        return maLP;
    }
     private String gettrangThai() {
       if(TrangThai==0){
           return "Còn";
       }
       else{
           return "Hết";
       }
    }
    public Object[] toDataRow(){
        return new Object[]{maLP,tenLP,moTa,gettrangThai()};
    }

   
}
