/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import DomainModels.LoaiPhong;
import Repositories.LoaiPhongRepositories;
import Repositories.LoaiPhongRepositoriesImpl;
import ViewsModel.QLLoaiPhong;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class LoaiPhongServices implements LoaiPhongServicesImpl{
   LoaiPhongRepositoriesImpl lpRP = new LoaiPhongRepositories();
        
    @Override
    public ArrayList<QLLoaiPhong> getAll() {
        return lpRP.getAll();
    }

    @Override
    public void insert(LoaiPhong lp) {
        lpRP.insert(lp);
    }

    @Override
    public void update(String ma, LoaiPhong lp) {
        lpRP.update(ma, lp);
    }

    @Override
    public void delete(String ma) {
        lpRP.delete(ma);
    }

    @Override
    public  ArrayList<QLLoaiPhong> getOne(String tenLP) {
                return lpRP.getOne(tenLP);
    }
    
}
