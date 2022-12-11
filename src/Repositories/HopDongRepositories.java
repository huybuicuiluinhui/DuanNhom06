/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import DomainModels.HopDong;
import Utilities.DBConnection;
import ViewsModel.QLHopDong;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class HopDongRepositories implements HopDongRepositoriesImpl {

    @Override
    public ArrayList<QLHopDong> getAllHD() {
        String query = "select MaHD, TenKH, PhongTro.MaPT,TenLoaiPhong,GiaTien,DienTich, NgayKy, NgayHetHan, TienCoc, HopDong.Mota, HopDong.TrangThai from HopDong \n"
                + "join KhachHang on KhachHang.MaKH = HopDong.MaKH\n"
                + "join PhongTro on PhongTro.MaPT = HopDong.MaPT\n"
                + "join LoaiPhong on LoaiPhong.MaLoaiPhong = PhongTro.MaLoaiPhong";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            ArrayList<QLHopDong> listCTSanPham = new ArrayList<>();
            while (rs.next()) {
                QLHopDong ctSanPham = new QLHopDong(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getDouble(6),
                        rs.getDate(7), rs.getDate(8), rs.getDouble(9), rs.getString(10), rs.getInt(11));
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
    public void insert(HopDong hd) {
        String sql = "insert into HopDong(MaHD, MaKH, MaPT, NgayKy, NgayHetHan,TienCoc,MoTa,TrangThai) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, hd.getMaHD());
            ps.setObject(2, hd.getMaKH());
            ps.setObject(3, hd.getMaPT());
            ps.setObject(4, hd.getNgayKy());
            ps.setObject(5, hd.getNgayHetHan());
            ps.setObject(6, hd.getTienCoc());
            ps.setObject(7, hd.getMoTa());
            ps.setObject(8, hd.getTrangThai());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void update(String ma, HopDong hd) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String ma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<QLHopDong> getMaPT() {
         String query = "select MaHD, TenKH, PhongTro.MaPT,TenLoaiPhong,GiaTien,DienTich, NgayKy, NgayHetHan, TienCoc, HopDong.Mota, HopDong.TrangThai from HopDong \n"
                + "join KhachHang on KhachHang.MaKH = HopDong.MaKH\n"
                + "join PhongTro on PhongTro.MaPT = HopDong.MaPT\n"
                + "join LoaiPhong on LoaiPhong.MaLoaiPhong = PhongTro.MaLoaiPhong";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            ArrayList<QLHopDong> listCTSanPham = new ArrayList<>();
            while (rs.next()) {
                QLHopDong ctSanPham = new QLHopDong(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getDouble(6),
                        rs.getDate(7), rs.getDate(8), rs.getDouble(9), rs.getString(10), rs.getInt(11));
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
