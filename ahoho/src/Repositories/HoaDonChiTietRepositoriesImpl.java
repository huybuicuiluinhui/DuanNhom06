/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repositories;


import DomainModels.HoaDonChiTiet;
import ViewsModel.QLHoaDonChiTiet;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public interface HoaDonChiTietRepositoriesImpl {
    ArrayList<QLHoaDonChiTiet> getAllHDCT();

    void insert(HoaDonChiTiet hdct);

    void update(String ma, HoaDonChiTiet hdct);

    void delete(String ma);
    
     ArrayList<QLHoaDonChiTiet> getAllHDCT(String ma);
      ArrayList<QLHoaDonChiTiet> getAllHThongKe(int trangthai, int month, int year);
}
