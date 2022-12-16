/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;

import DomainModels.PhongTro;
import ViewsModel.QLPhongTro;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public interface PhongTroServicesImpl {
    ArrayList<QLPhongTro> getAll();

    void insert(PhongTro ph);

    void update(String ma, PhongTro lp);

    void delete(String ma); 
     ArrayList<PhongTro> comboBox(int trangThai);
}
