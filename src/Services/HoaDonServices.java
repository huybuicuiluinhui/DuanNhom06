/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;

import DomainModels.HoaDon;
import Repositories.HoaDonRepositories;

import Repositories.HoaDonRepositoriesImpl;
import ViewsModel.QLHoaDon;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class HoaDonServices implements HoaDonServicesImpl{
    HoaDonRepositoriesImpl hdRP = new HoaDonRepositories();
    @Override
    public ArrayList<QLHoaDon> getAllHD() {
        return hdRP.getAllHD();
    }

    @Override
    public void insert(HoaDon dv) {
        hdRP.insert(dv);
    }

    @Override
    public void update(String ma, HoaDon dv) {
        hdRP.update(ma, dv);
    }

    @Override
    public void delete(String ma) {
        hdRP.delete(ma);
    }
    
}
