/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;

import DomainModels.LoaiPhong;
import ViewsModel.QLLoaiPhong;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public interface LoaiPhongServicesImpl {
     ArrayList<QLLoaiPhong> getAll();
     ArrayList<QLLoaiPhong> getOne(int tt);

    void insert(LoaiPhong lp);

    void update(String ma, LoaiPhong lp);

    void delete(String ma); 
}
