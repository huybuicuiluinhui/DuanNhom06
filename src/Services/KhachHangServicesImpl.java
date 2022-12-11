/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;

import DomainModels.KhachHang;
import ViewsModel.QLKhachHang;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public interface KhachHangServicesImpl {
     ArrayList<QLKhachHang> getAll();

    void insert(KhachHang kh);

    void update(String ma, KhachHang kh);

    void delete(String ma); 
}
