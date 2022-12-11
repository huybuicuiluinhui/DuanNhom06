/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;


import DomainModels.PhongTro;
import Repositories.PhongTroRepositories;
import Repositories.PhongTroRepositoriesImpl;

import ViewsModel.QLPhongTro;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class PhongTroServices implements PhongTroServicesImpl{
   PhongTroRepositoriesImpl phRP = new PhongTroRepositories();

    @Override
    public ArrayList<QLPhongTro> getAll() {
        return phRP.getAll();
    }

    @Override
    public void insert(PhongTro ph) {
        phRP.insert(ph);
    }

    @Override
    public void update(String ma, PhongTro lp) {
        phRP.update(ma, lp);
    }

    @Override
    public void delete(String ma) {
        phRP.delete(ma);
    }
 
    
}
