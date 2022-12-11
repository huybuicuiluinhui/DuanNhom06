/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repositories;

import DomainModels.HopDong;
import ViewsModel.QLHopDong;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public interface HopDongRepositoriesImpl {
    ArrayList<QLHopDong> getAllHD();
    ArrayList<QLHopDong> getMaPT();

    void insert(HopDong hd);

    void update(String ma, HopDong hd);

    void delete(String ma); 
}
