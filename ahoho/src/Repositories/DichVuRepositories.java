/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repositories;

import DomainModels.DichVu;
import Utilities.DBConnection;
import ViewsModel.QLDichVu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class DichVuRepositories implements DichVuRepositoriesImpl{

    @Override
    public ArrayList<QLDichVu> getAllDV() {
        ArrayList<QLDichVu> listSanPham = new ArrayList<>();
        String sql = "SELECT * FROM DichVu";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                QLDichVu sp = new QLDichVu();
                sp.setMaDV(rs.getString(1));
                sp.setTenDV(rs.getString(2));
                sp.setGiaTien(rs.getDouble(3));
                sp.setDonVi(rs.getString(4));
                sp.setTrangThai(rs.getInt(5));
               
                listSanPham.add(sp);
            }
        } catch (Exception e) {
        }
        return listSanPham;
    }

    @Override
    public void insert(DichVu dv) {
             try {
            Connection con = DBConnection.getConnection();
            String sql = "insert into DichVu (MaDichVu,TenDichVu,Gia,DonVi,TrangThai) values (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, dv.getMaDV());
            ps.setString(2, dv.getTenDV());
            ps.setDouble(3, dv.getGiaTien());
            ps.setString(4, dv.getDonVi());
            ps.setInt(5, dv.getTrangThai());
             ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String ma, DichVu dv) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE DichVu SET TenDichVu=?,Gia=?,DonVi=?,TrangThai=? where MaDichVu=?  ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, dv.getTenDV());
            ps.setDouble(2, dv.getGiaTien());
            ps.setString(3, dv.getDonVi());
            ps.setInt(4, dv.getTrangThai());
            ps.setString(5, ma);
       
          ps.executeUpdate();
          
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String ma) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "delete DichVu where MaDichVu =?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ma);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
