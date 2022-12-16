/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import DomainModels.TaiKhoan;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public interface TaiKhoanServicesImpl {
    ArrayList<TaiKhoan> getAll();

    void insert(TaiKhoan tk);

    void update(String ma, TaiKhoan tk);

    void delete(String ma);
}
