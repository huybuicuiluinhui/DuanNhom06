/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import DomainModels.HoaDonChiTiet;
import Repositories.HoaDonChiTietRepositories;
import Repositories.HoaDonChiTietRepositoriesImpl;

import ViewsModel.QLHoaDonChiTiet;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class HoaDonChiTietServices implements HoaDonChiTietServicesImpl{
   HoaDonChiTietRepositoriesImpl hdctRePo = new HoaDonChiTietRepositories();
    @Override
    public ArrayList<QLHoaDonChiTiet> getAllHDCT() {
        return hdctRePo.getAllHDCT();
    }

    @Override
    public void insert(HoaDonChiTiet hdct) {
        hdctRePo.insert(hdct);
    }

    @Override
    public void update(String ma, HoaDonChiTiet hdct) {
        hdctRePo.update(ma, hdct);
    }

    @Override
    public void delete(String ma) {
        hdctRePo.delete(ma);
    }

    @Override
    public ArrayList<QLHoaDonChiTiet> getAllHDCT(String ma) {
        return hdctRePo.getAllHDCT(ma);
    }

    @Override
    public ArrayList<QLHoaDonChiTiet> getAllHThongKe(int trangthai, int month, int year) {
       return hdctRePo.getAllHThongKe(trangthai, month, year);
    }
    
}
