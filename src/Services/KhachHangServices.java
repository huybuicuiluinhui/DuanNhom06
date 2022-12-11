/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import DomainModels.KhachHang;
import Repositories.KhachHangRepositories;
import Repositories.KhachHangRepositoriesImpl;
import ViewsModel.QLKhachHang;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class KhachHangServices implements KhachHangServicesImpl{
     KhachHangRepositoriesImpl khRP = new KhachHangRepositories();
    @Override
    public ArrayList<QLKhachHang> getAll() {
        return khRP.getAll();
    }

    @Override
    public void insert(KhachHang kh) {
         khRP.insert(kh);
    }

    @Override
    public void update(String ma, KhachHang kh) {
        khRP.update(ma, kh);
    }

    @Override
    public void delete(String ma) {
        khRP.delete(ma);
    }
    
}
