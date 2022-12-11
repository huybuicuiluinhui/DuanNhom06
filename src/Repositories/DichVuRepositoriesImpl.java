/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repositories;

import DomainModels.DichVu;
import ViewsModel.QLDichVu;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public interface DichVuRepositoriesImpl {
     ArrayList<QLDichVu> getAllDV();

    void insert(DichVu dv);

    void update(String ma, DichVu dv);

    void delete(String ma);
}
