package Repositories;

import DomainModels.KhachHang;
import Utilities.DBConnection;
import ViewsModel.QLKhachHang;
//import ViewModels.KhachHangModel;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class KhachHangRepositories implements KhachHangRepositoriesImpl {

    DBConnection Connection = new DBConnection();
    ArrayList<QLKhachHang> listViewKH;
    PreparedStatement pst = null;
    ResultSet rs = null;

    @Override
    public ArrayList<QLKhachHang> getAll() {
        ArrayList<QLKhachHang> list = new ArrayList<>();
        String sql = "Select MaKH, TenKH, NgaySinh, GioiTinh, SDT, DiaChi, CCCD, Email,TrangThai from KhachHang ";
        try ( Connection con = Connection.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                QLKhachHang kh = new QLKhachHang();
                kh.setMaKH(rs.getString(1));
                kh.setTenKH(rs.getString(2));
                kh.setNgaySinh(rs.getDate(3));
                kh.setGioiTinh(rs.getInt(4));
                kh.setSDT(rs.getString(5));
                kh.setDiaChi(rs.getString(6));
                kh.setCCCD(rs.getString(7));
                kh.setEmail(rs.getString(8));
                kh.setTrangThai(rs.getInt(9));
                list.add(kh);
            }
        } catch (Exception e) {
        }
        return list;
    }

    @Override
    public void insert(KhachHang kh) {
        if (kh.getTrangThai() == 1) {
            String sql = "insert into KhachHang(MaKH, TenKH, NgaySinh, GioiTinh, SDT,DiaChi,CCCD,Email,TrangThai) values (?, ?, ?, ?, ?, ?, ?, ?,?)";
            try (
                     Connection con = Connection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setObject(1, kh.getMaKH());
                ps.setObject(2, kh.getTenKH());
                ps.setObject(3, kh.getNgaySinh());
                ps.setObject(4, kh.getGioiTinh());
                ps.setObject(5, kh.getSDT());
                ps.setObject(6, kh.getDiaChi());
                ps.setObject(7, kh.getCCCD());
                ps.setObject(8, kh.getEmail());
                ps.setObject(9, kh.getTrangThai());
                ps.executeUpdate();
            } catch (Exception e) {
            }
            JOptionPane.showMessageDialog(null, "Thêm thành công");

        } else {
            JOptionPane.showMessageDialog(null, "cần thêm trạng thái chưa thuê");
            return;
        }
    }

    @Override
    public void update(String ma, KhachHang kh) {
        String query = "update KhachHang set TenKH= ?,NgaySinh=?,GioiTinh=?,SDT=?,DiaChi=?,CCCD=?,Email=?,TrangThai=?\n"
                + "where MaKH = ?";
        try ( Connection con = Connection.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, kh.getTenKH());
            ps.setObject(2, kh.getNgaySinh());
            ps.setObject(3, kh.getGioiTinh());
            ps.setObject(4, kh.getSDT());
            ps.setObject(5, kh.getDiaChi());
            ps.setObject(6, kh.getCCCD());
            ps.setObject(7, kh.getEmail());
            ps.setObject(8, kh.getTrangThai());
            ps.setObject(9, kh.getMaKH());

            ps.executeUpdate();
            System.out.println("Truy vấn update thành công");
        } catch (Exception e) {
        }
    }

    @Override
    public void delete(String ma) {
        String sql = "delete from KhachHang where MaKH = ?";
        try (
                 Connection con = Connection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ma);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<QLKhachHang> getAllTK(String tenKH) {
          String query = " Select * from KhachHang where TenKH like N'%"+tenKH+"%' ";
        try ( Connection con = DBConnection.getConnection();  Statement ps = con.createStatement();) {
           // ps.setI(1, tenKH);
            ResultSet rs = ps.executeQuery(query);

            ArrayList<QLKhachHang> list = new ArrayList<>();
            while (rs.next()) {
                QLKhachHang kh = new QLKhachHang();
                kh.setMaKH(rs.getString(1));
                kh.setTenKH(rs.getString(2));
                kh.setNgaySinh(rs.getDate(3));
                kh.setGioiTinh(rs.getInt(4));
                kh.setSDT(rs.getString(5));
                kh.setDiaChi(rs.getString(6));
                kh.setCCCD(rs.getString(7));
                kh.setEmail(rs.getString(8));
                kh.setTrangThai(rs.getInt(9));
             //   kh.setTenKH(tenKH);
                list.add(kh);
            }
            System.out.println(list.size());
            return list;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

}
