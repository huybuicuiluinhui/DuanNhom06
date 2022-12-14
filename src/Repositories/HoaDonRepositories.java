/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import DomainModels.HoaDon;
import Utilities.DBConnection;
import ViewsModel.QLHoaDon;
import ViewsModel.QLHopDong;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class HoaDonRepositories implements HoaDonRepositoriesImpl {

    @Override
    public ArrayList<QLHoaDon> getAllHD() {
        String query = "SELECT        dbo.HoaDon.MaHDon, dbo.HopDong.MaPT,TenKH,SDT, dbo.PhongTro.GiaTien, dbo.HoaDon.NgayTao, dbo.HoaDon.TongTien, dbo.HoaDon.TrangThai\n"
                + "FROM            dbo.HoaDon INNER JOIN\n"
                + "                         dbo.HopDong ON dbo.HoaDon.MaHD = dbo.HopDong.MaHD INNER JOIN\n"
                + "                         dbo.KhachHang ON dbo.HopDong.MaKH = dbo.KhachHang.MaKH INNER JOIN\n"
                + "                         dbo.PhongTro ON dbo.HopDong.MaPT = dbo.PhongTro.MaPT";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            ArrayList<QLHoaDon> listCTSanPham = new ArrayList<>();
            while (rs.next()) {
                QLHoaDon ctSanPham = new QLHoaDon(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getDate(6), rs.getDouble(7), rs.getInt(8));
                listCTSanPham.add(ctSanPham);
            }
            System.out.println(listCTSanPham.size());
            return listCTSanPham;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public void insert(HoaDon dv) {
        String sql = "insert into HoaDon(MaHDon, MaHD, NgayTao, TongTien,TrangThai) values (?, ?, ?, ?, ?)";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, dv.getMaHDon());
            ps.setObject(2, dv.getMaHopDong());
            ps.setObject(3, dv.getNgayTao());
            ps.setObject(4, dv.getTongTien());
            ps.setObject(5, dv.getTrangThai());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void update(String ma, HoaDon dv) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String ma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<QLHoaDon> getAllHD(String trangthai, String month, String year) {
//        //select * from HoaDon where 
// Month(NgayTao) = '4'
//and Year(NgayTao) = '2022'
//and TrangThai = 1
        return null;
    }

    @Override
    public ArrayList<QLHoaDon> getAllMaHD(String maHoaDon) {
        String query = "SELECT  dbo.HopDong.MaPT,TenKH,SDT, dbo.PhongTro.GiaTien, dbo.HoaDon.NgayTao, dbo.HoaDon.TongTien, dbo.HoaDon.TrangThai\n"
                + "                FROM            dbo.HoaDon INNER JOIN\n"
                + "                                      dbo.HopDong ON dbo.HoaDon.MaHD = dbo.HopDong.MaHD INNER JOIN\n"
                + "                                         dbo.KhachHang ON dbo.HopDong.MaKH = dbo.KhachHang.MaKH INNER JOIN\n"
                + "                                         dbo.PhongTro ON dbo.HopDong.MaPT = dbo.PhongTro.MaPT\n"
                + "where MaHDon= ?";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            // ps.setString(1, maHoaDon);
            ResultSet rs = ps.executeQuery();
            ArrayList<QLHoaDon> listCTSanPham = new ArrayList<>();
            while (rs.next()) {
                QLHoaDon ctSanPham = new QLHoaDon(rs.getString(maHoaDon), rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDate(5), rs.getDouble(6), rs.getInt(7));
                listCTSanPham.add(ctSanPham);
            }
            System.out.println(listCTSanPham.size());
            return listCTSanPham;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

}
