/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import DomainModels.DichVu;

import Repositories.DichVuRepositoriesImpl;

import ViewsModel.QLDichVu;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class DichVuServices implements DichVuServicesImpl{
     DichVuRepositoriesImpl dvRePo = new Repositories.DichVuRepositories();
    @Override
    public ArrayList<QLDichVu> getAllDV() {
        return dvRePo.getAllDV();
    }

    @Override
    public void insert(DichVu dv) {
         dvRePo.insert(dv);
    }

    @Override
    public void update(String ma, DichVu dv) {
        dvRePo.update(ma, dv);
    }

    @Override
    public void delete(String ma) {
        dvRePo.delete(ma);
    }
    
}
