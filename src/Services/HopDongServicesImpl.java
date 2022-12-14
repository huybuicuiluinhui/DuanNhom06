/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;

import DomainModels.HopDong;
import ViewsModel.QLHopDong;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public interface HopDongServicesImpl {
    ArrayList<QLHopDong> getAllHD();

    void insert(HopDong hd);

    void update(String ma, HopDong hd);

    void delete(String ma);  
    ArrayList<QLHopDong> getAllTrangThai(int tt);
    ArrayList<QLHopDong> getAllTK(String ten);
}
