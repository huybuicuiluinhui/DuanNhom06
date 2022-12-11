/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import DomainModels.LoaiPhong;
import DomainModels.PhongTro;
import Utilities.DBConnection;

import ViewsModel.QLPhongTro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class PhongTroRepositories implements PhongTroRepositoriesImpl{

    @Override
    public ArrayList<QLPhongTro> getAll() {
         ArrayList<QLPhongTro> listSanPham = new ArrayList<>();
        String sql = "SELECT * FROM PhongTro";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                QLPhongTro sp = new QLPhongTro();
                sp.setMaPT(rs.getString(1));
           
                sp.setMaLoaiPhong(rs.getString(2));
                sp.setDienTich(rs.getDouble(3));
                sp.setGiaPhong(rs.getDouble(4));
                sp.setTang(rs.getInt(5));
                sp.setMoTa(rs.getString(6));
                sp.setTrangthai(rs.getInt(7));
               
                listSanPham.add(sp);
            }
        } catch (Exception e) {
        }
        return listSanPham;
    }

    @Override
    public void insert(PhongTro ph) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "insert into PhongTro (MaPT,MaLoaiPhong,DienTich,GiaTien,Tang,MoTa,TrangThai) values (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, ph.getMaPT());
            
            ps.setString(2, ph.getMaLoaiPhong());
            ps.setDouble(3, ph.getDienTich());
            ps.setDouble(4, ph.getGiaPhong());
            ps.setInt(5, ph.getTang());
            ps.setString(6, ph.getMoTa());
            ps.setInt(7, ph.getTrangthai());
             ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String ma, PhongTro ph) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE PhongTro SET MaLoaiPhong=?,DienTich=?,GiaTien=?,Tang=?,MoTa=? ,TrangThai=? where MaPT=?  ";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(7, ph.getMaPT());
            ps.setString(1, ph.getMaLoaiPhong());
            ps.setDouble(2, ph.getDienTich());
            ps.setDouble(3, ph.getGiaPhong());
            ps.setInt(4, ph.getTang());
            ps.setString(5, ph.getMoTa());
            ps.setInt(6, ph.getTrangthai());
             ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String ma) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "delete PhongTro where MaPT =?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ma);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
