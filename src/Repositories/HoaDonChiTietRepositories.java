/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import DomainModels.HoaDonChiTiet;
import Utilities.DBConnection;
import ViewsModel.QLHoaDonChiTiet;
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
public class HoaDonChiTietRepositories implements HoaDonChiTietRepositoriesImpl {

    @Override
    public ArrayList<QLHoaDonChiTiet> getAllHDCT() {
        String query = "SELECT        dbo.HoaDonChiTiet.MaHoaDon, dbo.DichVu.TenDichVu, dbo.DichVu.Gia, dbo.HoaDonChiTiet.DaSuDung, dbo.DichVu.DonVi, dbo.HoaDonChiTiet.TongTien\n"
                + "FROM            dbo.HoaDonChiTiet INNER JOIN\n"
                + "                         dbo.DichVu ON dbo.HoaDonChiTiet.MaDichVu = dbo.DichVu.MaDichVu INNER JOIN\n"
                + "                         dbo.HoaDon ON dbo.HoaDonChiTiet.MaHoaDon = dbo.HoaDon.MaHDon";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();

            ArrayList<QLHoaDonChiTiet> listCTSanPham = new ArrayList<>();
            while (rs.next()) {
                QLHoaDonChiTiet ctSanPham = new QLHoaDonChiTiet(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5), rs.getDouble(6));
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
    public void insert(HoaDonChiTiet hdct) {
        String sql = "insert into HoaDonChiTiet(MaHoaDon, MaDichVu, TongTien, DaSuDung) values (?, ?, ?, ?)";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, hdct.getMaHoaDon());
            ps.setObject(2, hdct.getMaDichVu());
            ps.setObject(3, hdct.getTongTien());
            ps.setObject(4, hdct.getDaSuDung());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void update(String ma, HoaDonChiTiet hdct) {
        String sql = "update HoaDonChiTiet set TongTien=?, DaSuDung=?  where MaDichVu =?";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setObject(1, hdct.getMaHoaDon());
//            ps.setObject(2, hdct.getMaDichVu());
            ps.setObject(1, hdct.getTongTien());
            ps.setObject(2, hdct.getDaSuDung());
            ps.setObject(3, hdct.getMaDichVu());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void delete(String ma) {
        String sql = "delete from HoaDonChiTiet where DaSuDung = ?";
        try (
                 Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ma);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<QLHoaDonChiTiet> getAllHDCT(String ma) {
        String query = " SELECT        dbo.HoaDon.MaHDon, dbo.DichVu.TenDichVu, dbo.DichVu.Gia, dbo.HoaDonChiTiet.DaSuDung, dbo.DichVu.DonVi,dbo.HoaDonChiTiet.TongTien\n"
                + "FROM            dbo.HoaDon INNER JOIN\n"
                + "                         dbo.HoaDonChiTiet ON dbo.HoaDon.MaHDon = dbo.HoaDonChiTiet.MaHoaDon INNER JOIN\n"
                + "                         dbo.DichVu ON dbo.HoaDonChiTiet.MaDichVu = dbo.DichVu.MaDichVu\n"
                + "where MaHoaDon= ?";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();

            ArrayList<QLHoaDonChiTiet> listCTSanPham = new ArrayList<>();
            while (rs.next()) {
                QLHoaDonChiTiet ctSanPham = new QLHoaDonChiTiet(ma, rs.getString("TenDichVu"), rs.getDouble("Gia"), rs.getString("DaSuDung"), rs.getString("DonVi"), rs.getDouble("TongTien"));
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
    public ArrayList<QLHoaDonChiTiet> getAllHThongKe(int trangthai, int month, int year) {
        String query = "SELECT        dbo.HoaDonChiTiet.MaHoaDon, dbo.HopDong.MaPT, dbo.KhachHang.TenKH, dbo.KhachHang.SDT, Sum(dbo.HoaDonChiTiet.TongTien) as TongTienDV , dbo.PhongTro.GiaTien as TienPhong,dbo.HoaDon.NgayTao, dbo.HoaDon.TrangThai\n"
                + "FROM            dbo.HoaDonChiTiet INNER JOIN\n"
                + "                         dbo.HoaDon ON dbo.HoaDonChiTiet.MaHoaDon = dbo.HoaDon.MaHDon INNER JOIN\n"
                + "                         dbo.DichVu ON dbo.HoaDonChiTiet.MaDichVu = dbo.DichVu.MaDichVu INNER JOIN\n"
                + "                         dbo.HopDong ON dbo.HoaDon.MaHD = dbo.HopDong.MaHD INNER JOIN\n"
                + "                         dbo.KhachHang ON dbo.HopDong.MaKH = dbo.KhachHang.MaKH INNER JOIN\n"
                + "                         dbo.PhongTro ON dbo.HopDong.MaPT = dbo.PhongTro.MaPT\n"
                + "						 where \n"
                + " Month(HoaDon.NgayTao) = ?\n"
                + "and Year(HoaDon.NgayTao) = ?\n"
                + "and HoaDon.TrangThai = ?\n"
                + "GROUP BY dbo.HoaDonChiTiet.MaHoaDon, dbo.HopDong.MaPT, dbo.KhachHang.TenKH, dbo.KhachHang.SDT,dbo.PhongTro.GiaTien,dbo.HoaDon.NgayTao, dbo.HoaDon.TrangThai";

        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ps.setInt(1, month);
            ps.setInt(2, year);
            ps.setInt(3, trangthai);
            ResultSet rs = ps.executeQuery();

            ArrayList<QLHoaDonChiTiet> listCTSanPham = new ArrayList<>();
            while (rs.next()) {
                QLHoaDonChiTiet ctSanPham = new QLHoaDonChiTiet();
                ctSanPham.setMaHoaDon(rs.getString(1));
                ctSanPham.setMaPongTro(rs.getString(2));
                ctSanPham.setTenKh(rs.getString(3));
                ctSanPham.setSdt(rs.getString(4));
                ctSanPham.setSoTienDV(rs.getDouble(5));
                ctSanPham.setTienPhong(rs.getDouble(6));
                ctSanPham.setNgayTao(rs.getDate(7));
                ctSanPham.setTrangThai(rs.getInt(8));

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
