/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import DomainModels.HopDong;
import Repositories.HopDongRepositories;
import Repositories.HopDongRepositoriesImpl;
import ViewsModel.QLHopDong;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class HopDongServices implements HopDongServicesImpl{
   HopDongRepositoriesImpl hdRP = new  HopDongRepositories();
    @Override
    public ArrayList<QLHopDong> getAllHD() {
        return hdRP.getAllHD();
    }

    @Override
    public void insert(HopDong hd) {
        hdRP.insert(hd);
    }

    @Override
    public void update(String ma, HopDong hd) {
        hdRP.update(ma, hd);
    }

    @Override
    public void delete(String ma) {
        hdRP.delete(ma);
    }
    
}
