/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repositories;

import DomainModels.TaiKhoan;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public interface TaiKhoanRepositoriesImpl {
     ArrayList<TaiKhoan> getAll();

    void insert(TaiKhoan tk);

    void update(String ma, TaiKhoan tk);

    void delete(String ma);
}
