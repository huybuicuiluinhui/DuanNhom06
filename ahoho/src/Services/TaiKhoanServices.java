/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import DomainModels.TaiKhoan;
import Repositories.TaiKhoanRepositories;
import Repositories.TaiKhoanRepositoriesImpl;

import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class TaiKhoanServices implements TaiKhoanServicesImpl{
    TaiKhoanRepositoriesImpl tkRP = new TaiKhoanRepositories();
    @Override
    public ArrayList<TaiKhoan> getAll() {
      return tkRP.getAll();
    }

    @Override
    public void insert(TaiKhoan tk) {
        tkRP.insert(tk);
    }

    @Override
    public void update(String ma, TaiKhoan tk) {
        tkRP.update(ma, tk);
    }

    @Override
    public void delete(String ma) {
        tkRP.delete(ma);
    }
    
}
