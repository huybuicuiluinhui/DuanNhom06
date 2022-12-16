/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Utilities.DBConnection;
//import ViewModels.KhachHangModel;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import DomainModels.LoaiPhong;
import ViewsModel.QLHoaDonChiTiet;
import ViewsModel.QLLoaiPhong;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class LoaiPhongRepositories implements LoaiPhongRepositoriesImpl {

    DBConnection Connection = new DBConnection();
    ArrayList<QLLoaiPhong> listViewKH;
    PreparedStatement pst = null;
    ResultSet rs = null;

    @Override
    public ArrayList<QLLoaiPhong> getAll() {
        ArrayList<QLLoaiPhong> list = new ArrayList<>();
        String sql = "Select MaLoaiPhong, TenLoaiPhong, Mota, TrangThai from LoaiPhong ";
        try ( Connection con = Connection.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                QLLoaiPhong kh = new QLLoaiPhong();
                kh.setMaLP(rs.getString(1));
                kh.setTenLP(rs.getString(2));
                kh.setMoTa(rs.getString(3));
                kh.setTrangThai(rs.getInt(4));
                list.add(kh);
            }
        } catch (Exception e) {
        }
        return list;
    }

    @Override
    public void insert(LoaiPhong lp) {
        String sql = "insert into LoaiPhong(MaLoaiPhong, TenLoaiPhong, Mota, TrangThai) values (?, ?, ?, ?)";
        if (lp.getTrangThai() == 1) {
            try (
                     Connection con = Connection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setObject(1, lp.getMaLP());
                ps.setObject(2, lp.getTenLP());
                ps.setObject(3, lp.getMoTa());
                ps.setObject(4, lp.getTrangThai());
                ps.executeUpdate();
            } catch (Exception e) {
            }
        } else {
            JOptionPane.showMessageDialog(null, "Thay đổi trạng thái");
            return;
        }
    }

    @Override
    public void update(String ma, LoaiPhong lp) {

        String query = "UPDATE LoaiPhong SET TenLoaiPhong=?,Mota=?, TrangThai=? where MaLoaiPhong=? ";
        try ( Connection con = Connection.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, lp.getTenLP());
            ps.setObject(2, lp.getMoTa());
            ps.setObject(3, lp.getTrangThai());
            ps.setObject(4, ma);
            ps.executeUpdate();
            System.out.println("Truy vấn update thành công");
        } catch (Exception e) {
        }
    }

    @Override
    public void delete(String ma) {
        String sql = "delete from LoaiPhong where MaLoaiPhong = ?";
        try (
                 Connection con = Connection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ma);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<QLLoaiPhong> getOne(int tt) {

        String query = " Select * from LoaiPhong where TrangThai = ?";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ps.setInt(1, tt);
            ResultSet rs = ps.executeQuery();

            ArrayList<QLLoaiPhong> list = new ArrayList<>();
            while (rs.next()) {
                QLLoaiPhong ctSanPham = new QLLoaiPhong(rs.getString(1), rs.getString("TenLoaiPhong"), rs.getString("MoTa"), rs.getInt("TrangThai"));
                list.add(ctSanPham);
            }
            System.out.println(list.size());
            return list;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

}
