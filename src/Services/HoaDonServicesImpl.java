/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;

import DomainModels.HoaDon;
import ViewsModel.QLHoaDon;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public interface HoaDonServicesImpl {
     ArrayList<QLHoaDon> getAllHD();

    void insert(HoaDon dv);

    void update(String ma, HoaDon dv);

    void delete(String ma); 
}
