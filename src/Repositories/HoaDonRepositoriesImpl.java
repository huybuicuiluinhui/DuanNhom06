/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repositories;


import DomainModels.HoaDon;

import ViewsModel.QLHoaDon;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public interface HoaDonRepositoriesImpl {
   ArrayList<QLHoaDon> getAllHD();

    void insert(HoaDon dv);

    void update(String ma, HoaDon dv);

    void delete(String ma); 
    ArrayList<QLHoaDon> getAllMaHD(String maHoaDon);
    ArrayList<QLHoaDon> getAllHD(String trangthai, String month, String year);
}
