package Views;

import DomainModels.DichVu;
import DomainModels.HoaDon;
import DomainModels.HoaDonChiTiet;
import DomainModels.HopDong;
import DomainModels.KhachHang;
import DomainModels.LoaiPhong;
import DomainModels.PhongTro;
import Services.DichVuServices;
import Services.HoaDonChiTietServices;
import Services.HoaDonServices;
import Services.HopDongServices;
import Services.KhachHangServices;
import Services.LoaiPhongServices;
import Services.LoaiPhongServicesImpl;
import Services.KhachHangServicesImpl;
import Services.PhongTroServices;
import Utilities.DBConnection;
import ViewsModel.QLKhachHang;

import ViewsModel.QLDichVu;
import ViewsModel.QLHoaDon;
import ViewsModel.QLHoaDonChiTiet;
import ViewsModel.QLHopDong;
import ViewsModel.QLLoaiPhong;
import ViewsModel.QLPhongTro;

import java.awt.Color;
import java.awt.Font;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Connection;
import java.util.Hashtable;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Main extends javax.swing.JFrame {

    private LoaiPhongServicesImpl lpsv;
    private KhachHangServicesImpl khsv;
    private DichVuServices dichvuSV;
    private PhongTroServices phongSV;
    private HopDongServices hopDongSV;
    private HoaDonServices hoaDonDV;
    private HoaDonChiTietServices hdctSV;
    int tt = 0;

    public Main() {
        initComponents();
        this.lpsv = new LoaiPhongServices();
        this.dichvuSV = new DichVuServices();
        this.khsv = new KhachHangServices();
        this.phongSV = new PhongTroServices();
        this.hopDongSV = new HopDongServices();
        this.hoaDonDV = new HoaDonServices();
        this.hdctSV = new HoaDonChiTietServices();

        //showDataComboBoxKhachHang();
        //  showDataComboBoxPhong(tt);
        tblPhong.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblPhong.getTableHeader().setOpaque(false);
        tblPhong.getTableHeader().setBackground(Color.BLACK);
        tblPhong.getTableHeader().setForeground(new Color(255, 255, 255));
        tblPhong.setRowHeight(25);
        this.loadTableHopDong();
        showDataComboBoxDichVu();
        showDataComboBoxThongke();
        showDataComboBoxHoaDonChiTiet();
        showDataComboBoxKhachHang();
        showDataHD();
        showDataComboBoxPhong(tt);

    }
    int with = 210;
    int hieght = 711;

    public void openMenuBar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < with; i++) {
                    jplSileMune.setSize(i, hieght);

                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //jplSileMune.setVisible(true);
                }
            }
        }).start();
    }

    public void closeMenuBar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = with; i > 0; i--) {
                    jplSileMune.setSize(i, hieght);
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

///---============================================================Loại phòng======================================================================================================    
    private void loadTableLoaiPhong() {
        DefaultTableModel dtm = (DefaultTableModel) this.tblLoaiPhong.getModel();
        dtm.setRowCount(0);
        for (QLLoaiPhong tb : this.lpsv.getAll()) {
            Object[] rowData = {
                tb.getMaLP(),
                tb.getTenLP(),
                tb.getMoTa(),
                tb.getTrangThai() == 1 ? "Còn" : "Hết",};
            dtm.addRow(rowData);
        }
    }

    public void TKloaiPhong(int tenLP) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblLoaiPhong.getModel();
        dtm.setRowCount(0);
        for (QLLoaiPhong lp : this.lpsv.getOne(tenLP)) {
            dtm.addRow(lp.toDataRow());
        }
    }

    private boolean checkMa(String ma) {
        Pattern p = Pattern.compile("^L+[0-9]+$");
        Matcher m = p.matcher(ma);
        return m.matches();
    }

    private boolean checkSo(String SDT) {
        Pattern p = Pattern.compile("^0+[0-9]+$");
        Matcher m = p.matcher(SDT);
        return m.matches();
    }

    private boolean checkTrungMa(String kh) {
        ArrayList<QLLoaiPhong> ds = lpsv.getAll();
        for (QLLoaiPhong d : ds) {
            if (d.getMaLP().equalsIgnoreCase(kh)) {
                return true;
            }
        }
        return false;
    }

    private LoaiPhong getFormDataLoaiPhong() {
        String Ma = this.txtMaLP.getText().trim();
        String Ten = this.txtLP.getText().trim();
        String MoTa = this.txtMoTa.getText().trim();
        int gt = 1;
        if (rdoHet.isSelected()) {
            gt = 0;
        }
        if (Ma.length() == 0 || Ten.length() == 0 || MoTa.length() == 0) {
            JOptionPane.showMessageDialog(this, "Dữ liệu cần nhập đầy đủ");
            return null;
        } else if (!checkMa(Ma)) {
            JOptionPane.showMessageDialog(this, "Sai định dạng mã");
            return null;
        }
        LoaiPhong lp = new LoaiPhong();
        lp.setMaLP(Ma);
        lp.setTenLP(Ten);
        lp.setMoTa(MoTa);
        lp.setTrangThai(gt);
        return lp;
    }

    private void clearFormLoaiPhong() {
        this.txtMaLP.setText("");
        this.txtMoTa.setText("");
        this.txtLP.setText("");
        this.rdoCon.isSelected();
    }
    ///---============================================================================Dịch vụ====================================================================================== 

    public void loadTableDichVu() {
        DefaultTableModel dtm = (DefaultTableModel) this.tblDichVu.getModel();
        dtm.setRowCount(0);
        for (QLDichVu dv : this.dichvuSV.getAllDV()) {
            dtm.addRow(dv.toDataRow());
        }
    }

    private DichVu getFormDataDichVu() {
        String Ma = this.txtMaDichVu.getText().trim();
        String Ten = this.txtTenDV.getText().trim();
        String Gia = this.txtGiaDichVu.getText().trim();
        String donVi = this.txtDonViDichVu.getText().trim();

        int tt = 1;
        if (rdoHoatDong.isSelected()) {
            tt = 0;
        }
        if (Ma.length() == 0 || Ten.length() == 0 || Gia.length() == 0 || donVi.length() == 0) {
            JOptionPane.showMessageDialog(this, "Dữ liệu cần nhập đầy đủ");
            return null;
        }
        Pattern p = Pattern.compile("^DV+[0-9]+$");
        if (p.matcher(Ma).find()) {
        } else {
            JOptionPane.showMessageDialog(this, "Mã sai định dạng DV...");
            return null;
        }
        try {
            Double.parseDouble(Gia);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sai định dạng, phải là số");
            return null;
        }
        if (Double.parseDouble(Gia) < 0) {
            JOptionPane.showMessageDialog(this, "Giá tiền phải là số dương");
            return null;
        }

        DichVu dv = new DichVu();
        dv.setMaDV(Ma);
        dv.setTenDV(Ten);
        dv.setGiaTien(Double.parseDouble(Gia));
        dv.setDonVi(donVi);
        dv.setTrangThai(tt);
        return dv;
    }

    private boolean checkLoiDichVu() {

        int row = tblDichVu.getRowCount();
        for (int i = 0; i < row; ++i) {
            DefaultTableModel dtm = (DefaultTableModel) this.tblDichVu.getModel();
            if (txtMaDichVu.getText().equalsIgnoreCase(dtm.getValueAt(i, 0).toString())) {
                JOptionPane.showMessageDialog(this, "Mã bị trùng. Xin kiểm tra lại");
                txtMaDichVu.grabFocus();
                return false;
            }
        }
        return true;
    }

    public void cleraFormDichVu() {
        txtMaDichVu.setText("");
        txtTenDV.setText("");
        txtGiaDichVu.setText("");
        txtDonViDichVu.setText("");
        rdoHoatDong.setSelected(true);
    }

    ///---===========================================================================Khách hàng=====================================================================================    
    private void clearFormKhachHang() {
        this.txtMaKH.setText("");
        this.txtTenKH.setText("");
        this.jDateChNgaySinh.setDate(null);
        // this.rdoChuaThue.isSelected();
        this.rdoNam.isSelected();
        this.txtSDT.setText("");
        this.txtDiaChi.setText("");
        this.txtCCCD.setText("");
        this.txtEmail.setText("");
    }

    private KhachHang getFormDataKhachHang() {
        String Ma = this.txtMaKH.getText().trim();
        String Ten = this.txtTenKH.getText().trim();
        String ngaySinh_ = (new SimpleDateFormat("yyyy-MM-dd")).format(jDateChNgaySinh.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("YYYY-MM-DD");
        java.util.Date NgayD;
        try {
            NgayD = sdf.parse(ngaySinh_);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Sai định dạng ngày");
            e.printStackTrace();
            return null;
        }
        String SDT = this.txtSDT.getText().trim();
        String diaChi = this.txtDiaChi.getText().trim();
        String CCCD = this.txtCCCD.getText().trim();
        String email = this.txtEmail.getText().trim();
        int gt = 1;
        if (rdoNu.isSelected()) {
            gt = 0;
        }
        int tt = 0; // đang thuê
        if (rdoChuaThue.isSelected()) {
            tt = 1; // đã hết hạn
        }
        if (Ma.length() == 0 || Ten.length() == 0 || SDT.length() == 0 || email.length() == 0 || diaChi.length() == 0 || ngaySinh_.length() == 0 || CCCD.length() == 0) {
            JOptionPane.showMessageDialog(this, "Dữ liệu cần nhập đầy đủ");
            return null;
        } else if (SDT.length() < 10 || SDT.length() > 10) {
            JOptionPane.showMessageDialog(this, "SDT 10 kí tự");
            return null;
        } else if (!checkMaKhachHang(Ma)) {
            JOptionPane.showMessageDialog(this, "Sai định dạng mã khách hàng");
            return null;
        } else if (!checkSo(SDT)) {
            JOptionPane.showMessageDialog(this, "Sai định dạng số điện thoại");
            return null;
        }
        KhachHang kh = new KhachHang();
        kh.setMaKH(Ma);
        kh.setTenKH(Ten);
        kh.setNgaySinh(NgayD);
        kh.setGioiTinh(gt);
        kh.setSDT(SDT);
        kh.setDiaChi(diaChi);
        kh.setCCCD(CCCD);
        kh.setEmail(email);
        kh.setTrangThai(tt);

        return kh;
    }

    private void loadTableKhachHangTK(String tenKH) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblKhachHang.getModel();
        dtm.setRowCount(0);
        // ten = txtTimKiemKhacHang.getText();
        for (QLKhachHang tb : this.khsv.getAllTK(tenKH)) {
            Object[] rowData = {
                tb.getMaKH(),
                tb.getTenKH(),
                tb.getNgaySinh(),
                tb.getGioiTinh() == 1 ? "Nam" : "Nữ",
                tb.getSDT(),
                tb.getDiaChi(),
                tb.getCCCD(),
                tb.getEmail(),
                tb.getTrangThai() == 0 ? "Đang thuê" : "Chưa thuê"};
            dtm.addRow(rowData);
        }
    }

    private void loadTableKhachHang() {
        DefaultTableModel dtm = (DefaultTableModel) this.tblKhachHang.getModel();
        dtm.setRowCount(0);
        for (QLKhachHang tb : this.khsv.getAll()) {
            Object[] rowData = {
                tb.getMaKH(),
                tb.getTenKH(),
                tb.getNgaySinh(),
                tb.getGioiTinh() == 1 ? "Nam" : "Nữ",
                tb.getSDT(),
                tb.getDiaChi(),
                tb.getCCCD(),
                tb.getEmail(),
                tb.getTrangThai() == 0 ? "Đang thuê" : "Chưa Thuê",};
            dtm.addRow(rowData);
        }
    }

    private boolean checkMaKhachHang(String ma) {
        Pattern p = Pattern.compile("^KH+[0-9]+$");
        Matcher m = p.matcher(ma);
        return m.matches();
    }

    private boolean checkTrungMaKhachHang(String kh) {
        ArrayList<QLKhachHang> ds = khsv.getAll();
        for (QLKhachHang d : ds) {
            if (d.getMaKH().equalsIgnoreCase(kh)) {
                return true;
            }
        }
        return false;
    }
////---===================================================================================================================================================================

    public void loadComboLoaiPhong() {
        cbbLoaiPhong.removeAllItems();
        ArrayList<QLLoaiPhong> list = this.lpsv.getAll();

        for (QLLoaiPhong x : list) {
            cbbLoaiPhong.addItem(x.getMaLP());
            //cbbLoaiPhong.setName(x.getTenLP());

        }
    }

    public void loadTablePhongTro() {
        DefaultTableModel dtm = (DefaultTableModel) this.tblPhong.getModel();
        dtm.setRowCount(0);
        for (QLPhongTro dv : this.phongSV.getAll()) {
            dtm.addRow(dv.toDataRow());
        }
    }

    private PhongTro getFormDataPhongTro() {
        String MaPH = this.txtMaPhong.getText().trim();
        String maLP = this.cbbLoaiPhong.getSelectedItem().toString();
        //String tenPhong = maLP.getMaLP();
        String dienTich = this.txtDienTich.getText().trim();
        String giaPhong = this.txtGiaTienPhong.getText().trim();
        String tang = this.cbbTang.getSelectedItem().toString();
        String moTa = this.txtMoTaPhong.getText().trim();
        int tt = 0;
        if (rdochuachoThue.isSelected()) {
            tt = 1;
        }
        if (MaPH.length() == 0 || dienTich.length() == 0 || giaPhong.length() == 0) {
            JOptionPane.showMessageDialog(this, "Không bỏ được bỏ trống");
            return null;
        }

        PhongTro pt = new PhongTro();
        pt.setMaPT(MaPH);
        pt.setMaLoaiPhong(maLP);
        pt.setDienTich(Double.parseDouble(dienTich));
        pt.setGiaPhong(Double.parseDouble(giaPhong));
        pt.setMoTa(moTa);
        pt.setTang(Integer.parseInt(tang));
        pt.setTrangthai(tt);
        return pt;
    }

    ////---===================================================================================================================================================================
    public void loadTableHopDong() {

        DefaultTableModel dtm = (DefaultTableModel) this.tblHopDong.getModel();
        dtm.setRowCount(0);
        for (QLHopDong dv : this.hopDongSV.getAllHD()) {

            dtm.addRow(dv.toDataRow());
        }
    }

    public void showDataComboBoxKhachHang() {
        DefaultComboBoxModel dcm = (DefaultComboBoxModel) this.cbbTenKhachHang.getModel();
        dcm.removeAllElements();
        ArrayList<QLKhachHang> list = this.khsv.getAll();
        for (QLKhachHang x : list) {
            dcm.addElement(x.getTenKH());
        }

    }

    public void showDataComboBoxPhong(int tt) {
        DefaultComboBoxModel dcm = (DefaultComboBoxModel) this.cbbTenPhong.getModel();
        dcm.removeAllElements();
        ArrayList<PhongTro> list = this.phongSV.comboBox(tt);
        for (PhongTro x : list) {
            dcm.addElement(x.getMaPT());
        }
    }

    private HopDong getDataHopDong() {
        String maHD = txtMaHopDong.getText();
        String moTa = txtMoTaHopDong.getText();
        String tienCoc = txtTienCoc.getText();
        int tt = 0;
        if (rdoConHan.isSelected()) {
            tt = 1;
        }

        String ngayKy = txtNgayKi.getText();
        String ngayHH = txtNgayHetHan.getText();
        int indexKhachHang = cbbTenKhachHang.getSelectedIndex();

        int indexLoaiPhong = cbbTenPhong.getSelectedIndex();

        if (maHD.trim().length() == 0 || moTa.trim().length() == 0 || tienCoc.trim().length() == 0 || ngayKy.trim().length() == 0 || ngayHH.trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Không bỏ được bỏ trống");
            return null;
        }
        try {
            Double.parseDouble(tienCoc);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Sai định dạng phải là số");
            return null;
        }
        if (Double.parseDouble(tienCoc) < 0) {
            JOptionPane.showMessageDialog(this, "Phải là số dương");
            return null;

        }
        ArrayList<QLKhachHang> listKhachHang = this.khsv.getAll();
        QLKhachHang kh = listKhachHang.get(indexKhachHang);
        ArrayList<PhongTro> listLoaiPhong = this.phongSV.comboBox(indexLoaiPhong);
        PhongTro ten = listLoaiPhong.get(indexLoaiPhong);
        HopDong hd = new HopDong(maHD, kh.getMaKH(), ten.getMaPT(), Date.valueOf(ngayKy), Date.valueOf(ngayHH), Double.parseDouble(tienCoc), moTa, tt);
        return hd;
    }

    public void clearFormHopDong() {
        txtMaHopDong.setText("");
        cbbTenKhachHang.setSelectedIndex(0);
        cbbTenPhong.setSelectedIndex(0);
        txtTienCoc.setText("");
        txtNgayHetHan.setText("");
        txtNgayKi.setText("");
        txtMoTaHopDong.setText("");
        rdoConHan.setSelected(true);
    }

    private boolean checkLoiMaHopDong() {

        int row = tblHopDong.getRowCount();
        for (int i = 0; i < row; ++i) {
            DefaultTableModel dtm = (DefaultTableModel) this.tblHopDong.getModel();
            if (txtMaHopDong.getText().equalsIgnoreCase(dtm.getValueAt(i, 0).toString())) {
                JOptionPane.showMessageDialog(this, "Mã bị trùng. Xin kiểm tra lại");
                txtMaHopDong.grabFocus();
                return false;
            }
        }
        return true;
    }
    ////---===================================================================================================================================================================

    public void loadTableHoaDon() {
        DefaultTableModel dtm = (DefaultTableModel) this.tblHoaDon.getModel();
        dtm.setRowCount(0);
        for (QLHoaDon dv : this.hoaDonDV.getAllHD()) {
            dtm.addRow(dv.toDataRow());

        }
    }

    public void showDataComboBoxHoaDonMaPhong() {
        DefaultComboBoxModel dcm = (DefaultComboBoxModel) this.cbbMaPhong.getModel();
        dcm.removeAllElements();
        ArrayList<QLHopDong> list = this.hopDongSV.getAllHD();
        for (QLHopDong x : list) {
            dcm.addElement(x.getMaHD());

        }
    }

    private HoaDon getDataHoaDon() {
        String maHD = txtMaHoaDon.getText();
        String ngayTao = txtNgayTao.getText();
        String tongTien = txtTongTien.getText();
        int tt = 0;
        if (rdoDaThanhToan.isSelected()) {
            tt = 1;
        }
        int indexPhong = cbbMaPhong.getSelectedIndex();
        ArrayList<QLHopDong> listLoaiPhong = this.hopDongSV.getAllHD();
        QLHopDong ten = listLoaiPhong.get(indexPhong);
        HoaDon hd = new HoaDon(maHD, ten.getMaHD(), Date.valueOf(ngayTao), Double.parseDouble(tongTien), tt);
        // HoaDon hd = new HoaDon(maHD, ten.getMaHD(), Date.valueOf(ngayTao), Double.parseDouble(tongTien),  tt);
        return hd;
    }

    ////---===================================================================================================================================================================
    public void showDataComboBoxDichVu() {
        DefaultComboBoxModel dcm = (DefaultComboBoxModel) this.cbbTenDichVu.getModel();
        dcm.removeAllElements();
        ArrayList<QLDichVu> list = this.dichvuSV.getAllDV();
        for (QLDichVu x : list) {
            dcm.addElement(x);
        }
    }

    public void loadTableHoaDonChitiet(String maHD, Double giaphong) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        for (QLHoaDonChiTiet dv : this.hdctSV.getAllHDCT(maHD)) {
            dtm.addRow(dv.toDataRow());
        }
        BigDecimal tong = new BigDecimal(getTongTien(this.hdctSV.getAllHDCT(maHD), giaphong));
        lbTongTien.setText(String.valueOf(tong) + " VNĐ");
    }

    ////---===================================================================================================================================================================
    public void showDataComboBoxHoaDonChiTiet() {
        DefaultComboBoxModel dcm = (DefaultComboBoxModel) this.cbbMaHoaDonBangHDCT.getModel();
        dcm.removeAllElements();
        ArrayList<QLHoaDon> list = this.hoaDonDV.getAllHD();
        for (QLHoaDon x : list) {
            dcm.addElement(x);
        }
    }

    public HoaDonChiTiet getFormDataChiTiet() {
        String tt = lblTongTienDichVu.getText().trim();
        String used = txtDaSuDung.getText().trim();
        int indexHoaDon = cbbMaHoaDonBangHDCT.getSelectedIndex();
        ArrayList<QLHoaDon> listLoaiPhong = this.hoaDonDV.getAllHD();
        QLHoaDon ten = listLoaiPhong.get(indexHoaDon);
        int indexDichVu = cbbTenDichVu.getSelectedIndex();
        ArrayList<QLDichVu> list = this.dichvuSV.getAllDV();
        QLDichVu dv = list.get(indexDichVu);
        HoaDonChiTiet hd = new HoaDonChiTiet(ten.getMaHDon(), dv.getMaDV(), Double.parseDouble(tt), used);
        return hd;
    }
    ////---===================================================================================================================================================================

    public void showDataComboBoxThongke() {
        DefaultComboBoxModel dcm = (DefaultComboBoxModel) this.cbbTrangThai.getModel();
        dcm.removeAllElements();
        ArrayList<QLHoaDon> list = this.hoaDonDV.getAllHD();
        for (QLHoaDon x : list) {
            dcm.addElement(x.gettrangthai());
        }
    }

    public void loadTableThongKe(int trangthai, int month, int year) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblThongKe.getModel();
        dtm.setRowCount(0);
        for (QLHoaDonChiTiet dv : this.hdctSV.getAllHThongKe(trangthai, month, year)) {
            dtm.addRow(dv.toDataRowThongKe());
        }

    }

    ///////////-=====================================================================================================================================================================
//      public void showDataLoaiPhong() {
//        DefaultComboBoxModel dcm = (DefaultComboBoxModel) this.cbbSearchLP.getModel();
//        ArrayList<QLLoaiPhong> list = this.lpsv.getAll();
//        for (QLLoaiPhong x : list) {
//            dcm.addElement(x.getTenLP());
//        }
//    }
    public void showDataHD() {
        DefaultComboBoxModel dcm = (DefaultComboBoxModel) this.cbbTenKhachHang.getModel();
        dcm.removeAllElements();
        ArrayList<QLDichVu> list = this.dichvuSV.getAllDV();
        for (QLDichVu x : list) {
            dcm.addElement(null);
        }
    }
//    public void loadTableLoaiPhongSearch(int trangthai) {
//        DefaultTableModel dtm = (DefaultTableModel) this.tblLoaiPhong.getModel();
//        dtm.setRowCount(0);
//        for (QLLoaiPhong dv : this.lpsv.getAllHThongKe(trangthai)) {
//            dtm.addRow(dv.toDataRowThongKe());
//        }
//
//    }

    ////---===================================================================================================================================================================
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jpanl1 = new javax.swing.JPanel();
        jplSileMune = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        close = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        HopDong = new javax.swing.JLabel();
        KhachHang = new javax.swing.JLabel();
        Phong = new javax.swing.JLabel();
        LoaiPhong = new javax.swing.JLabel();
        DichVu = new javax.swing.JLabel();
        HoaDon = new javax.swing.JLabel();
        HDCT = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        Japnel = new javax.swing.JPanel();
        QLHopDong = new javax.swing.JPanel();
        QLPhong1 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        DuLieu7 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        txtMaHopDong = new javax.swing.JTextField();
        txtTienCoc = new javax.swing.JTextField();
        rdoConHan = new javax.swing.JRadioButton();
        rdoDaHetHan = new javax.swing.JRadioButton();
        jLabel58 = new javax.swing.JLabel();
        txtMoTaHopDong = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        cbbTenPhong = new javax.swing.JComboBox<>();
        cbbTenKhachHang = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtNgayKi = new javax.swing.JTextField();
        txtNgayHetHan = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblHopDong = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        jTextField12 = new javax.swing.JTextField();
        btnThemHopDong = new javax.swing.JButton();
        btnInHopDong = new javax.swing.JButton();
        btnHienThiHopDong1 = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        QLPhong = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        DuLieu1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtMaPhong = new javax.swing.JTextField();
        txtDienTich = new javax.swing.JTextField();
        txtGiaTienPhong = new javax.swing.JTextField();
        rdoDaThue = new javax.swing.JRadioButton();
        rdochuachoThue = new javax.swing.JRadioButton();
        jLabel19 = new javax.swing.JLabel();
        cbbTang = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        txtMoTaPhong = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbbLoaiPhong = new javax.swing.JComboBox<>();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPhong = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jTextField7 = new javax.swing.JTextField();
        btnThemPhon = new javax.swing.JButton();
        btnXoaPhong = new javax.swing.JButton();
        btnSuaPhong = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        QLLoaiPhong = new javax.swing.JPanel();
        DuLieu = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaLP = new javax.swing.JTextField();
        rdoCon = new javax.swing.JRadioButton();
        rdoHet = new javax.swing.JRadioButton();
        txtLP = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        PanelRight = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLoaiPhong = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        BtnThem = new javax.swing.JPanel();
        btnThem = new javax.swing.JLabel();
        btnSua = new javax.swing.JPanel();
        BtnSua = new javax.swing.JLabel();
        btnXoa = new javax.swing.JPanel();
        btnXoaLoaiPhong = new javax.swing.JLabel();
        txtTimKiemLP = new javax.swing.JTextField();
        btnTimkiemLP = new javax.swing.JButton();
        cbbSearchLP = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        QLKhachHang = new javax.swing.JPanel();
        DuLieu3 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        rdoDangThue = new javax.swing.JRadioButton();
        rdoChuaThue = new javax.swing.JRadioButton();
        txtTenKH = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel35 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jDateChNgaySinh = new com.toedter.calendar.JDateChooser();
        jSeparator18 = new javax.swing.JSeparator();
        jSeparator19 = new javax.swing.JSeparator();
        jSeparator20 = new javax.swing.JSeparator();
        jSeparator21 = new javax.swing.JSeparator();
        jSeparator22 = new javax.swing.JSeparator();
        jSeparator23 = new javax.swing.JSeparator();
        PanelRight2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        txtTimKiemKH = new javax.swing.JTextField();
        btnThemKH = new javax.swing.JButton();
        btnXoaKH = new javax.swing.JButton();
        btnSuaKH = new javax.swing.JButton();
        btnTimKiemKH = new javax.swing.JButton();
        txtClearKH = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        HoaDonChiTiet = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        cbbTenDichVu = new javax.swing.JComboBox<>();
        txtDaSuDung = new javax.swing.JTextField();
        lblTienDichVu = new javax.swing.JLabel();
        lblTongTienDichVu = new javax.swing.JLabel();
        lbDonvi = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        lblVND = new javax.swing.JLabel();
        jSeparator24 = new javax.swing.JSeparator();
        lblVND1 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();
        btnThemHDCT = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        lblGiaPhongHoaDon = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        lblTenKhachHangHoaDon = new javax.swing.JLabel();
        cbbMaHoaDonBangHDCT = new javax.swing.JComboBox<>();
        jLabel57 = new javax.swing.JLabel();
        lblSDTHDCT = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        lblPhongHDCT = new javax.swing.JLabel();
        lbTongTien = new javax.swing.JLabel();
        btnXoaHDCT = new javax.swing.JButton();
        btnSuaHDCT = new javax.swing.JButton();
        btnInHoaDon = new javax.swing.JButton();
        btnSendMail = new javax.swing.JButton();
        ThongKe = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblThongKe = new javax.swing.JTable();
        cbbTrangThai = new javax.swing.JComboBox<>();
        cbbThang = new javax.swing.JComboBox<>();
        cbbNam = new javax.swing.JComboBox<>();
        btnXem = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        lblDuNo = new javax.swing.JLabel();
        lblTongTienThanhToan = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        QLDichVu = new javax.swing.JPanel();
        PanelRight1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        txtTimKiemDV = new javax.swing.JTextField();
        btnThemDV = new javax.swing.JButton();
        btnXoaDV = new javax.swing.JButton();
        btnSuaDV = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnHienThiDV = new javax.swing.JButton();
        txtClearDV = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDichVu = new javax.swing.JTable();
        DuLieu2 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtMaDichVu = new javax.swing.JTextField();
        rdoHoatDong = new javax.swing.JRadioButton();
        rdoNgungHD = new javax.swing.JRadioButton();
        txtTenDV = new javax.swing.JTextField();
        txtGiaDichVu = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtDonViDichVu = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        QLHoaDon = new javax.swing.JPanel();
        QLDichVu2 = new javax.swing.JPanel();
        DuLieu5 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        rdoDaThanhToan = new javax.swing.JRadioButton();
        txtChuaThanhToan = new javax.swing.JRadioButton();
        jLabel71 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        cbbMaPhong = new javax.swing.JComboBox<>();
        txtNgayTao = new javax.swing.JTextField();
        PanelRight4 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jTextField10 = new javax.swing.JTextField();
        btnThemHoaDon = new javax.swing.JButton();
        btnXoaHoaDon = new javax.swing.JButton();
        btnSuaHoaDon = new javax.swing.JButton();
        btnTKHoaDon = new javax.swing.JButton();
        btnHienThiHoaDon = new javax.swing.JButton();
        txtClearHoaDon = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        hienMenu = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jpanl1.setBackground(new java.awt.Color(71, 120, 197));
        jpanl1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jplSileMune.setBackground(new java.awt.Color(51, 255, 255));
        jplSileMune.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/x-circle.png"))); // NOI18N
        close.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/person-circle.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("nhóm 6");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(close))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(close)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jplSileMune.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 60));

        HopDong.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        HopDong.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        HopDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconContract.png"))); // NOI18N
        HopDong.setText("Hợp đồng");
        HopDong.setToolTipText("2");
        HopDong.setIconTextGap(40);
        HopDong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HopDongMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                HopDongMouseEntered(evt);
            }
        });
        jplSileMune.add(HopDong, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 210, 40));

        KhachHang.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        KhachHang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        KhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconClient.png"))); // NOI18N
        KhachHang.setText("Quản lí khách hàng");
        KhachHang.setIconTextGap(40);
        KhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                KhachHangMouseClicked(evt);
            }
        });
        jplSileMune.add(KhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 210, 40));

        Phong.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Phong.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Phong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconRoom.png"))); // NOI18N
        Phong.setText("Quản lí phòng");
        Phong.setIconTextGap(40);
        Phong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PhongMouseClicked(evt);
            }
        });
        jplSileMune.add(Phong, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 210, 40));

        LoaiPhong.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        LoaiPhong.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LoaiPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconRoomQL.png"))); // NOI18N
        LoaiPhong.setText("Quản lí loại phòng");
        LoaiPhong.setIconTextGap(40);
        LoaiPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LoaiPhongMouseClicked(evt);
            }
        });
        jplSileMune.add(LoaiPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 210, 40));

        DichVu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        DichVu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        DichVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconService.png"))); // NOI18N
        DichVu.setText("Quản lí dịch vụ");
        DichVu.setIconTextGap(40);
        DichVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DichVuMouseClicked(evt);
            }
        });
        jplSileMune.add(DichVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 210, 40));

        HoaDon.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        HoaDon.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        HoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconReceipt.png"))); // NOI18N
        HoaDon.setText("Quản lí hóa đơn");
        HoaDon.setIconTextGap(40);
        HoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HoaDonMouseClicked(evt);
            }
        });
        jplSileMune.add(HoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 210, 40));

        HDCT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        HDCT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        HDCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconReceipt01.png"))); // NOI18N
        HDCT.setText("Hóa đơn chi tiết");
        HDCT.setIconTextGap(40);
        HDCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HDCTMouseClicked(evt);
            }
        });
        jplSileMune.add(HDCT, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 210, 50));

        jLabel45.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconOUt.png"))); // NOI18N
        jLabel45.setText("Thoát");
        jLabel45.setIconTextGap(40);
        jplSileMune.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 210, 40));

        jLabel68.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconStatistical.png"))); // NOI18N
        jLabel68.setText("Thống kê");
        jLabel68.setIconTextGap(40);
        jLabel68.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel68MouseClicked(evt);
            }
        });
        jplSileMune.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 210, 50));

        jpanl1.add(jplSileMune, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 736));

        Japnel.setLayout(new java.awt.CardLayout());

        QLHopDong.setBackground(new java.awt.Color(255, 255, 255));

        QLPhong1.setBackground(new java.awt.Color(255, 255, 255));
        QLPhong1.setForeground(new java.awt.Color(0, 204, 204));
        QLPhong1.setPreferredSize(new java.awt.Dimension(600, 739));

        DuLieu7.setBackground(new java.awt.Color(71, 120, 197));
        DuLieu7.setForeground(new java.awt.Color(153, 204, 255));
        DuLieu7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Mã HD");
        jLabel53.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        DuLieu7.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 20, 70, 31));

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Tên khách hàng");
        jLabel54.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        DuLieu7.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 96, 34));

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Tiền cọc");
        jLabel55.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        DuLieu7.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 87, 29));

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Trạng thái");
        jLabel56.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        DuLieu7.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 70, 29));

        txtMaHopDong.setBackground(new java.awt.Color(71, 120, 197));
        txtMaHopDong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMaHopDong.setForeground(new java.awt.Color(255, 255, 255));
        txtMaHopDong.setBorder(null);
        DuLieu7.add(txtMaHopDong, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 200, 30));

        txtTienCoc.setBackground(new java.awt.Color(71, 120, 197));
        txtTienCoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTienCoc.setForeground(new java.awt.Color(255, 255, 255));
        txtTienCoc.setBorder(null);
        DuLieu7.add(txtTienCoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 339, 194, 29));

        rdoConHan.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup1.add(rdoConHan);
        rdoConHan.setForeground(new java.awt.Color(255, 255, 255));
        rdoConHan.setText("Còn Hạn");
        DuLieu7.add(rdoConHan, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 480, -1, -1));

        rdoDaHetHan.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup1.add(rdoDaHetHan);
        rdoDaHetHan.setForeground(new java.awt.Color(255, 255, 255));
        rdoDaHetHan.setText("Đã hết hạn");
        DuLieu7.add(rdoDaHetHan, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 480, 89, -1));

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Mô tả");
        jLabel58.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        DuLieu7.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 55, 29));

        txtMoTaHopDong.setBackground(new java.awt.Color(71, 120, 197));
        txtMoTaHopDong.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMoTaHopDong.setForeground(new java.awt.Color(255, 255, 255));
        DuLieu7.add(txtMoTaHopDong, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 394, 194, 66));

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Tên phòng");
        jLabel59.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        DuLieu7.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, 40));

        cbbTenPhong.setBackground(new java.awt.Color(71, 120, 197));
        cbbTenPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTenPhongActionPerformed(evt);
            }
        });
        DuLieu7.add(cbbTenPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 155, 194, 31));

        cbbTenKhachHang.setBackground(new java.awt.Color(71, 120, 197));
        DuLieu7.add(cbbTenKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 89, 194, 34));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Ngày ký");
        jLabel22.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        DuLieu7.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 79, 29));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Ngày hết hạn");
        jLabel25.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        DuLieu7.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 79, 34));

        txtNgayKi.setBackground(new java.awt.Color(71, 120, 197));
        txtNgayKi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNgayKi.setForeground(new java.awt.Color(255, 255, 255));
        txtNgayKi.setBorder(null);
        DuLieu7.add(txtNgayKi, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 194, 29));

        txtNgayHetHan.setBackground(new java.awt.Color(71, 120, 197));
        txtNgayHetHan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNgayHetHan.setForeground(new java.awt.Color(255, 255, 255));
        txtNgayHetHan.setBorder(null);
        txtNgayHetHan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayHetHanActionPerformed(evt);
            }
        });
        DuLieu7.add(txtNgayHetHan, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 194, 30));
        DuLieu7.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, 190, 10));
        DuLieu7.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 190, 10));
        DuLieu7.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 250, 190, 10));
        DuLieu7.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 320, 190, 10));

        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHopDong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HĐ", "Tên khách hàng", "Mã phòng", "Tên phòng", "Giá phòng", "Diện tích", "Ngày ký", "Ngày hêt hạn", "Tiền cọc", "Mô tả", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHopDong.setFocusable(false);
        tblHopDong.setGridColor(new java.awt.Color(255,255,255));
        tblHopDong.setRowHeight(25);
        tblHopDong.setSelectionBackground(new java.awt.Color(71, 120, 197));
        tblHopDong.setShowVerticalLines(false);
        tblHopDong.getTableHeader().setReorderingAllowed(false);
        tblHopDong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHopDongMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tblHopDong);
        if (tblHopDong.getColumnModel().getColumnCount() > 0) {
            tblHopDong.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblHopDong.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblHopDong.getColumnModel().getColumn(8).setPreferredWidth(100);
        }

        jPanel24.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 38, 860, 370));

        jPanel25.setBackground(new java.awt.Color(0, 255, 204));

        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });

        btnThemHopDong.setBackground(new java.awt.Color(153, 204, 255));
        btnThemHopDong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemHopDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconAdd.png"))); // NOI18N
        btnThemHopDong.setText("Thêm");
        btnThemHopDong.setToolTipText("10");
        btnThemHopDong.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnThemHopDong.setBorderPainted(false);
        btnThemHopDong.setIconTextGap(10);
        btnThemHopDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHopDongActionPerformed(evt);
            }
        });

        btnInHopDong.setBackground(new java.awt.Color(153, 204, 255));
        btnInHopDong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInHopDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconPrint.png"))); // NOI18N
        btnInHopDong.setText("In");
        btnInHopDong.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnInHopDong.setBorderPainted(false);
        btnInHopDong.setIconTextGap(10);
        btnInHopDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHopDongActionPerformed(evt);
            }
        });

        btnHienThiHopDong1.setBackground(new java.awt.Color(153, 204, 255));
        btnHienThiHopDong1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHienThiHopDong1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconSearch.png"))); // NOI18N
        btnHienThiHopDong1.setText("Search");
        btnHienThiHopDong1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnHienThiHopDong1.setBorderPainted(false);
        btnHienThiHopDong1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHienThiHopDong1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap(231, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(btnThemHopDong, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(btnInHopDong, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98)
                .addComponent(btnHienThiHopDong1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemHopDong, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInHopDong, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHienThiHopDong1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );

        jPanel24.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 410, 870, 260));

        jPanel26.setBackground(new java.awt.Color(186, 79, 84));

        jLabel60.setBackground(new java.awt.Color(186, 79, 84));
        jLabel60.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("Quản lý  hợp đồng");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jPanel24.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, -1));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(DuLieu7, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DuLieu7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 58, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout QLPhong1Layout = new javax.swing.GroupLayout(QLPhong1);
        QLPhong1.setLayout(QLPhong1Layout);
        QLPhong1Layout.setHorizontalGroup(
            QLPhong1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        QLPhong1Layout.setVerticalGroup(
            QLPhong1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout QLHopDongLayout = new javax.swing.GroupLayout(QLHopDong);
        QLHopDong.setLayout(QLHopDongLayout);
        QLHopDongLayout.setHorizontalGroup(
            QLHopDongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QLPhong1, javax.swing.GroupLayout.PREFERRED_SIZE, 1245, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        QLHopDongLayout.setVerticalGroup(
            QLHopDongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QLHopDongLayout.createSequentialGroup()
                .addComponent(QLPhong1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Japnel.add(QLHopDong, "card3");

        QLPhong.setBackground(new java.awt.Color(153, 153, 153));
        QLPhong.setForeground(new java.awt.Color(0, 204, 204));

        DuLieu1.setBackground(new java.awt.Color(71, 120, 197));
        DuLieu1.setForeground(new java.awt.Color(153, 204, 255));
        DuLieu1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Mã Phòng ");
        DuLieu1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 27, -1, 29));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Diện tích");
        DuLieu1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 89, 61, 40));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Giá tiền");
        DuLieu1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 55, 29));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Trạng thái");
        DuLieu1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 376, 71, 29));

        txtMaPhong.setBackground(new java.awt.Color(71, 120, 197));
        txtMaPhong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMaPhong.setForeground(new java.awt.Color(255, 255, 255));
        txtMaPhong.setBorder(null);
        DuLieu1.add(txtMaPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 194, 29));

        txtDienTich.setBackground(new java.awt.Color(71, 120, 197));
        txtDienTich.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDienTich.setForeground(new java.awt.Color(255, 255, 255));
        txtDienTich.setBorder(null);
        txtDienTich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDienTichActionPerformed(evt);
            }
        });
        DuLieu1.add(txtDienTich, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 160, 29));

        txtGiaTienPhong.setBackground(new java.awt.Color(71, 120, 197));
        txtGiaTienPhong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtGiaTienPhong.setForeground(new java.awt.Color(255, 255, 255));
        txtGiaTienPhong.setBorder(null);
        DuLieu1.add(txtGiaTienPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 160, 29));

        rdoDaThue.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup1.add(rdoDaThue);
        rdoDaThue.setForeground(new java.awt.Color(255, 255, 255));
        rdoDaThue.setText("Đã thuê");
        DuLieu1.add(rdoDaThue, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, -1, -1));

        rdochuachoThue.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup1.add(rdochuachoThue);
        rdochuachoThue.setForeground(new java.awt.Color(255, 255, 255));
        rdochuachoThue.setText("Chưa thuê");
        DuLieu1.add(rdochuachoThue, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 380, 78, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Tầng");
        DuLieu1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 239, 55, 29));

        cbbTang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5" }));
        DuLieu1.add(cbbTang, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 247, 194, 34));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Mô tả");
        DuLieu1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 299, 55, 29));

        txtMoTaPhong.setBackground(new java.awt.Color(71, 120, 197));
        txtMoTaPhong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMoTaPhong.setForeground(new java.awt.Color(255, 255, 255));
        DuLieu1.add(txtMoTaPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 299, 194, 66));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tên phòng");
        DuLieu1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 156, -1, -1));

        cbbLoaiPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLoaiPhongActionPerformed(evt);
            }
        });
        DuLieu1.add(cbbLoaiPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 148, 200, 30));
        DuLieu1.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 200, -1));
        DuLieu1.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 200, 10));
        DuLieu1.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 200, 10));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("m2");
        DuLieu1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 20, 40));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("VNĐ");
        DuLieu1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 205, -1, 30));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblPhong.setFont(new java.awt.Font("Times New Roman", 3, 12)); // NOI18N
        tblPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phòng", "Mã Loại Phòng", "Diện tích", "Giá tiền", "Tầng", "Mô tả", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhong.setFocusable(false);
        tblPhong.setGridColor(new java.awt.Color(128,128,128));
        tblPhong.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblPhong.setRowHeight(25);
        tblPhong.setSelectionBackground(new java.awt.Color(232, 57, 95));
        tblPhong.setShowVerticalLines(false);
        tblPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhongMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPhong);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 38, 920, 341));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        btnThemPhon.setBackground(new java.awt.Color(153, 204, 255));
        btnThemPhon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemPhon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconAdd.png"))); // NOI18N
        btnThemPhon.setText("Thêm");
        btnThemPhon.setBorderPainted(false);
        btnThemPhon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemPhonActionPerformed(evt);
            }
        });

        btnXoaPhong.setBackground(new java.awt.Color(153, 204, 255));
        btnXoaPhong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconDelete.png"))); // NOI18N
        btnXoaPhong.setText("Xóa");
        btnXoaPhong.setBorderPainted(false);
        btnXoaPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaPhongActionPerformed(evt);
            }
        });

        btnSuaPhong.setBackground(new java.awt.Color(153, 204, 255));
        btnSuaPhong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSuaPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconEdit.png"))); // NOI18N
        btnSuaPhong.setText("Sửa");
        btnSuaPhong.setBorderPainted(false);
        btnSuaPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaPhongActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(153, 204, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconSearch.png"))); // NOI18N
        jButton7.setText("Search");
        jButton7.setBorderPainted(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(btnThemPhon)
                        .addGap(61, 61, 61)
                        .addComponent(btnXoaPhong)
                        .addGap(63, 63, 63)
                        .addComponent(btnSuaPhong)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)))
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(268, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemPhon, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(94, 94, 94)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(97, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 379, 920, 290));

        jPanel9.setBackground(new java.awt.Color(186, 79, 84));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Quản lý  phòng");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, -1));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(DuLieu1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DuLieu1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout QLPhongLayout = new javax.swing.GroupLayout(QLPhong);
        QLPhong.setLayout(QLPhongLayout);
        QLPhongLayout.setHorizontalGroup(
            QLPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QLPhongLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        QLPhongLayout.setVerticalGroup(
            QLPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Japnel.add(QLPhong, "card7");

        QLLoaiPhong.setBackground(new java.awt.Color(255, 255, 255));
        QLLoaiPhong.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                QLLoaiPhongAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        DuLieu.setBackground(new java.awt.Color(71, 120, 197));
        DuLieu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Mã loại phòng");
        DuLieu.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 27, 90, 26));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tên loại phòng");
        DuLieu.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 90, 29));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Mô tả");
        DuLieu.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 172, 60, 29));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Trạng thái");
        DuLieu.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 311, 70, 29));

        txtMaLP.setBackground(new java.awt.Color(71, 120, 197));
        txtMaLP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMaLP.setForeground(new java.awt.Color(255, 255, 255));
        txtMaLP.setBorder(null);
        DuLieu.add(txtMaLP, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 210, 29));

        rdoCon.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup1.add(rdoCon);
        rdoCon.setForeground(new java.awt.Color(255, 255, 255));
        rdoCon.setText("Còn ");
        DuLieu.add(rdoCon, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 314, 78, -1));

        rdoHet.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup1.add(rdoHet);
        rdoHet.setForeground(new java.awt.Color(255, 255, 255));
        rdoHet.setText("Hết");
        DuLieu.add(rdoHet, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 314, 78, -1));

        txtLP.setBackground(new java.awt.Color(71, 120, 197));
        txtLP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtLP.setForeground(new java.awt.Color(255, 255, 255));
        txtLP.setBorder(null);
        DuLieu.add(txtLP, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 210, 29));

        txtMoTa.setBackground(new java.awt.Color(71, 120, 197));
        txtMoTa.setColumns(20);
        txtMoTa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMoTa.setForeground(new java.awt.Color(255, 255, 255));
        txtMoTa.setRows(5);
        jScrollPane3.setViewportView(txtMoTa);

        DuLieu.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 163, 219, 84));
        DuLieu.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 210, 10));
        DuLieu.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 210, 10));

        tblLoaiPhong.setBackground(new java.awt.Color(204, 255, 255));
        tblLoaiPhong.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tblLoaiPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã loại phòng", "Tên loại phòng", "Mô tả", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLoaiPhong.setFocusable(false);
        tblLoaiPhong.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblLoaiPhong.setRowHeight(25);
        tblLoaiPhong.setSelectionBackground(new java.awt.Color(232, 57, 95));
        tblLoaiPhong.getTableHeader().setReorderingAllowed(false);
        tblLoaiPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiPhongMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLoaiPhong);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BtnThem.setBackground(new java.awt.Color(153, 204, 255));

        btnThem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconAdd.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setIconTextGap(10);
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout BtnThemLayout = new javax.swing.GroupLayout(BtnThem);
        BtnThem.setLayout(BtnThemLayout);
        BtnThemLayout.setHorizontalGroup(
            BtnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BtnThemLayout.createSequentialGroup()
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        BtnThemLayout.setVerticalGroup(
            BtnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BtnThemLayout.createSequentialGroup()
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnSua.setBackground(new java.awt.Color(153, 204, 255));

        BtnSua.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnSua.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BtnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconEdit.png"))); // NOI18N
        BtnSua.setText("Sửa");
        BtnSua.setIconTextGap(10);
        BtnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnSuaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btnSuaLayout = new javax.swing.GroupLayout(btnSua);
        btnSua.setLayout(btnSuaLayout);
        btnSuaLayout.setHorizontalGroup(
            btnSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSuaLayout.createSequentialGroup()
                .addComponent(BtnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        btnSuaLayout.setVerticalGroup(
            btnSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BtnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnXoa.setBackground(new java.awt.Color(153, 204, 255));

        btnXoaLoaiPhong.setBackground(new java.awt.Color(153, 204, 255));
        btnXoaLoaiPhong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaLoaiPhong.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnXoaLoaiPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconDelete.png"))); // NOI18N
        btnXoaLoaiPhong.setText("Xóa");
        btnXoaLoaiPhong.setIconTextGap(10);
        btnXoaLoaiPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaLoaiPhongMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btnXoaLayout = new javax.swing.GroupLayout(btnXoa);
        btnXoa.setLayout(btnXoaLayout);
        btnXoaLayout.setHorizontalGroup(
            btnXoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnXoaLayout.createSequentialGroup()
                .addComponent(btnXoaLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        btnXoaLayout.setVerticalGroup(
            btnXoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnXoaLoaiPhong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        txtTimKiemLP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        btnTimkiemLP.setBackground(new java.awt.Color(153, 204, 255));
        btnTimkiemLP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTimkiemLP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconSearch.png"))); // NOI18N
        btnTimkiemLP.setText("Tìm kiếm");
        btnTimkiemLP.setBorderPainted(false);
        btnTimkiemLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemLPActionPerformed(evt);
            }
        });

        cbbSearchLP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn", "Hết" }));
        cbbSearchLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSearchLPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(BtnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTimKiemLP)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addComponent(cbbSearchLP, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTimkiemLP, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTimKiemLP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTimkiemLP, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(cbbSearchLP))
                .addGap(82, 82, 82))
        );

        jPanel7.setBackground(new java.awt.Color(255, 204, 204));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setBackground(new java.awt.Color(255, 204, 204));
        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Quản lý loại phòng");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 950, 40));

        javax.swing.GroupLayout PanelRightLayout = new javax.swing.GroupLayout(PanelRight);
        PanelRight.setLayout(PanelRightLayout);
        PanelRightLayout.setHorizontalGroup(
            PanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRightLayout.createSequentialGroup()
                .addGroup(PanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        PanelRightLayout.setVerticalGroup(
            PanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRightLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout QLLoaiPhongLayout = new javax.swing.GroupLayout(QLLoaiPhong);
        QLLoaiPhong.setLayout(QLLoaiPhongLayout);
        QLLoaiPhongLayout.setHorizontalGroup(
            QLLoaiPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QLLoaiPhongLayout.createSequentialGroup()
                .addComponent(DuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(PanelRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        QLLoaiPhongLayout.setVerticalGroup(
            QLLoaiPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, QLLoaiPhongLayout.createSequentialGroup()
                .addGroup(QLLoaiPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DuLieu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        Japnel.add(QLLoaiPhong, "card2");

        QLKhachHang.setBackground(new java.awt.Color(255, 255, 255));

        DuLieu3.setBackground(new java.awt.Color(71, 120, 197));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Mã KH");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Tên KH");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Ngày Sinh");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Trạng thái");

        txtMaKH.setBackground(new java.awt.Color(71, 120, 197));
        txtMaKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMaKH.setForeground(new java.awt.Color(255, 255, 255));
        txtMaKH.setBorder(null);

        rdoDangThue.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup1.add(rdoDangThue);
        rdoDangThue.setForeground(new java.awt.Color(255, 255, 255));
        rdoDangThue.setText("Đang thuê");
        rdoDangThue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDangThueActionPerformed(evt);
            }
        });

        rdoChuaThue.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup1.add(rdoChuaThue);
        rdoChuaThue.setForeground(new java.awt.Color(255, 255, 255));
        rdoChuaThue.setText("Chưa Thuê");

        txtTenKH.setBackground(new java.awt.Color(71, 120, 197));
        txtTenKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTenKH.setForeground(new java.awt.Color(255, 255, 255));
        txtTenKH.setBorder(null);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("SĐT");

        txtSDT.setBackground(new java.awt.Color(71, 120, 197));
        txtSDT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSDT.setForeground(new java.awt.Color(255, 255, 255));
        txtSDT.setBorder(null);
        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Giới tính");

        rdoNam.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup2.add(rdoNam);
        rdoNam.setForeground(new java.awt.Color(255, 255, 255));
        rdoNam.setText("Nam");

        rdoNu.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup2.add(rdoNu);
        rdoNu.setForeground(new java.awt.Color(255, 255, 255));
        rdoNu.setText("Nữ");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Địa chỉ");

        txtDiaChi.setBackground(new java.awt.Color(71, 120, 197));
        txtDiaChi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDiaChi.setForeground(new java.awt.Color(255, 255, 255));
        txtDiaChi.setBorder(null);

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("CCCD");

        txtCCCD.setEditable(false);
        txtCCCD.setBackground(new java.awt.Color(71, 120, 197));
        txtCCCD.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCCCD.setForeground(new java.awt.Color(255, 255, 255));
        txtCCCD.setBorder(null);

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Email");

        txtEmail.setBackground(new java.awt.Color(71, 120, 197));
        txtEmail.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(255, 255, 255));
        txtEmail.setBorder(null);

        jDateChNgaySinh.setBackground(new java.awt.Color(71, 120, 197));

        javax.swing.GroupLayout DuLieu3Layout = new javax.swing.GroupLayout(DuLieu3);
        DuLieu3.setLayout(DuLieu3Layout);
        DuLieu3Layout.setHorizontalGroup(
            DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DuLieu3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DuLieu3Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(237, 237, 237))
                    .addGroup(DuLieu3Layout.createSequentialGroup()
                        .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))
                    .addGroup(DuLieu3Layout.createSequentialGroup()
                        .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(DuLieu3Layout.createSequentialGroup()
                                    .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel30)
                                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtCCCD, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DuLieu3Layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(txtEmail)))))
                            .addGroup(DuLieu3Layout.createSequentialGroup()
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DuLieu3Layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(DuLieu3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdoDangThue, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdoChuaThue, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(28, Short.MAX_VALUE))))
            .addGroup(DuLieu3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DuLieu3Layout.createSequentialGroup()
                        .addComponent(jDateChNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(DuLieu3Layout.createSequentialGroup()
                        .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator18)
                            .addComponent(txtMaKH)
                            .addComponent(jSeparator19)
                            .addComponent(txtTenKH))
                        .addGap(39, 39, 39))
                    .addGroup(DuLieu3Layout.createSequentialGroup()
                        .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DuLieu3Layout.createSequentialGroup()
                                .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(rdoNu, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DuLieu3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDiaChi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                    .addComponent(jSeparator20, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(23, 23, 23))))
        );
        DuLieu3Layout.setVerticalGroup(
            DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DuLieu3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(DuLieu3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))
                    .addGroup(DuLieu3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)))
                .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jDateChNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(DuLieu3Layout.createSequentialGroup()
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DuLieu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoDangThue)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoChuaThue))
                .addGap(50, 50, 50))
        );

        tblKhachHang.setBackground(new java.awt.Color(204, 255, 255));
        tblKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã KH", "Tên KH", "Ngày sinh", "Giới tính", "SĐT", "Địa chỉ", "CCCD", "Email", "TrangThai"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.setFocusable(false);
        tblKhachHang.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblKhachHang.setRowHeight(25);
        tblKhachHang.setSelectionBackground(new java.awt.Color(102, 102, 255));
        tblKhachHang.getTableHeader().setReorderingAllowed(false);
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblKhachHang);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtTimKiemKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKHKeyReleased(evt);
            }
        });

        btnThemKH.setBackground(new java.awt.Color(153, 204, 255));
        btnThemKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconAdd.png"))); // NOI18N
        btnThemKH.setText("Thêm");
        btnThemKH.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnThemKH.setBorderPainted(false);
        btnThemKH.setIconTextGap(10);
        btnThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKHActionPerformed(evt);
            }
        });

        btnXoaKH.setBackground(new java.awt.Color(153, 204, 255));
        btnXoaKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconDelete.png"))); // NOI18N
        btnXoaKH.setText("Xóa");
        btnXoaKH.setBorderPainted(false);
        btnXoaKH.setIconTextGap(10);
        btnXoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKHActionPerformed(evt);
            }
        });

        btnSuaKH.setBackground(new java.awt.Color(153, 204, 255));
        btnSuaKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSuaKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconEdit.png"))); // NOI18N
        btnSuaKH.setText("Sửa");
        btnSuaKH.setBorderPainted(false);
        btnSuaKH.setIconTextGap(10);
        btnSuaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKHActionPerformed(evt);
            }
        });

        btnTimKiemKH.setBackground(new java.awt.Color(153, 204, 255));
        btnTimKiemKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTimKiemKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconSearch.png"))); // NOI18N
        btnTimKiemKH.setText("Tìm kiếm");
        btnTimKiemKH.setBorderPainted(false);
        btnTimKiemKH.setIconTextGap(10);
        btnTimKiemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemKHActionPerformed(evt);
            }
        });

        txtClearKH.setBackground(new java.awt.Color(153, 204, 255));
        txtClearKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtClearKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconClear.png"))); // NOI18N
        txtClearKH.setText("Clear form");
        txtClearKH.setBorderPainted(false);
        txtClearKH.setIconTextGap(10);
        txtClearKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClearKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(btnXoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(btnThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(btnSuaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTimKiemKH))
                .addGap(49, 49, 49)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTimKiemKH)
                    .addComponent(txtClearKH))
                .addGap(342, 342, 342))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClearKH, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(255, 204, 204));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Quản lý khách hàng");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 855, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelRight2Layout = new javax.swing.GroupLayout(PanelRight2);
        PanelRight2.setLayout(PanelRight2Layout);
        PanelRight2Layout.setHorizontalGroup(
            PanelRight2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRight2Layout.createSequentialGroup()
                .addGroup(PanelRight2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 859, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        PanelRight2Layout.setVerticalGroup(
            PanelRight2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRight2Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout QLKhachHangLayout = new javax.swing.GroupLayout(QLKhachHang);
        QLKhachHang.setLayout(QLKhachHangLayout);
        QLKhachHangLayout.setHorizontalGroup(
            QLKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QLKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DuLieu3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(PanelRight2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 33, Short.MAX_VALUE))
        );
        QLKhachHangLayout.setVerticalGroup(
            QLKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DuLieu3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PanelRight2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Japnel.add(QLKhachHang, "card4");

        HoaDonChiTiet.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(71, 120, 197));
        jPanel10.setForeground(new java.awt.Color(255, 255, 255));
        jPanel10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel10KeyPressed(evt);
            }
        });

        cbbTenDichVu.setToolTipText("");
        cbbTenDichVu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbTenDichVuItemStateChanged(evt);
            }
        });

        txtDaSuDung.setBackground(new java.awt.Color(71, 120, 197));
        txtDaSuDung.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDaSuDung.setForeground(new java.awt.Color(255, 255, 255));
        txtDaSuDung.setText("0");
        txtDaSuDung.setBorder(null);
        txtDaSuDung.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtDaSuDungCaretUpdate(evt);
            }
        });
        txtDaSuDung.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtDaSuDungInputMethodTextChanged(evt);
            }
        });

        lblTienDichVu.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lblTienDichVu.setForeground(new java.awt.Color(255, 255, 255));
        lblTienDichVu.setText("50000");

        lblTongTienDichVu.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lblTongTienDichVu.setForeground(new java.awt.Color(255, 255, 255));
        lblTongTienDichVu.setText("0.0");

        lbDonvi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbDonvi.setForeground(new java.awt.Color(255, 255, 255));
        lbDonvi.setText("_");

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Tên dịch vụ: ");

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Đã sử dụng");

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("Giá dịch vụ");

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("Tổng");

        lblVND.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblVND.setForeground(new java.awt.Color(255, 255, 255));
        lblVND.setText("VNĐ");

        lblVND1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblVND1.setForeground(new java.awt.Color(255, 255, 255));
        lblVND1.setText("VNĐ");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel50))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel61)
                            .addComponent(jLabel63)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel52)))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addComponent(lblTienDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblVND, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cbbTenDichVu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jSeparator24, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDaSuDung, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbDonvi, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(lblTongTienDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblVND1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbTenDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50))
                .addGap(49, 49, 49)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDaSuDung, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                            .addComponent(lbDonvi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTienDichVu)
                    .addComponent(jLabel61)
                    .addComponent(lblVND))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTongTienDichVu)
                        .addComponent(jLabel63))
                    .addComponent(lblVND1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setForeground(new java.awt.Color(123, 156, 225));

        jLabel43.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconReceipt.png"))); // NOI18N
        jLabel43.setText("Thông tin hóa đơn");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(123, 156, 225));
        jLabel44.setText("Mã hóa đơn: ");

        tblHoaDonChiTiet.setBackground(new java.awt.Color(204, 255, 255));
        tblHoaDonChiTiet.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tblHoaDonChiTiet.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Tên dịch vụ", "Giá dịch vụ", "Đã sử dụng", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonChiTiet.setFocusable(false);
        tblHoaDonChiTiet.setGridColor(new java.awt.Color(255, 255, 255));
        tblHoaDonChiTiet.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblHoaDonChiTiet.setRowHeight(25);
        tblHoaDonChiTiet.setSelectionBackground(new java.awt.Color(102, 102, 255));
        tblHoaDonChiTiet.getTableHeader().setReorderingAllowed(false);
        tblHoaDonChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonChiTietMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblHoaDonChiTiet);

        btnThemHDCT.setBackground(new java.awt.Color(123, 156, 225));
        btnThemHDCT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemHDCT.setForeground(new java.awt.Color(255, 255, 255));
        btnThemHDCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconAdd.png"))); // NOI18N
        btnThemHDCT.setText("Thêm");
        btnThemHDCT.setBorderPainted(false);
        btnThemHDCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHDCTActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(123, 156, 225));
        jLabel46.setText("Giá phòng");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(123, 156, 225));
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconSumMoney.png"))); // NOI18N
        jLabel47.setText("Tổng tiền : ");

        lblGiaPhongHoaDon.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblGiaPhongHoaDon.setForeground(new java.awt.Color(123, 156, 225));
        lblGiaPhongHoaDon.setText("_");

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(123, 156, 225));
        jLabel51.setText("Tên khách hàng");

        lblTenKhachHangHoaDon.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTenKhachHangHoaDon.setForeground(new java.awt.Color(123, 156, 225));
        lblTenKhachHangHoaDon.setText("_");

        cbbMaHoaDonBangHDCT.setForeground(new java.awt.Color(123, 156, 225));
        cbbMaHoaDonBangHDCT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbMaHoaDonBangHDCTItemStateChanged(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(123, 156, 225));
        jLabel57.setText("SDT");

        lblSDTHDCT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSDTHDCT.setForeground(new java.awt.Color(123, 156, 225));
        lblSDTHDCT.setText("_");

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(123, 156, 225));
        jLabel62.setText("Phòng");

        lblPhongHDCT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPhongHDCT.setForeground(new java.awt.Color(123, 156, 225));
        lblPhongHDCT.setText("_");

        lbTongTien.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        lbTongTien.setForeground(new java.awt.Color(123, 156, 225));
        lbTongTien.setText("_");

        btnXoaHDCT.setBackground(new java.awt.Color(123, 156, 225));
        btnXoaHDCT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaHDCT.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaHDCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconDelete.png"))); // NOI18N
        btnXoaHDCT.setText("Xóa");
        btnXoaHDCT.setBorderPainted(false);
        btnXoaHDCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHDCTActionPerformed(evt);
            }
        });

        btnSuaHDCT.setBackground(new java.awt.Color(123, 156, 225));
        btnSuaHDCT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSuaHDCT.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaHDCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconEdit.png"))); // NOI18N
        btnSuaHDCT.setText("Sửa");
        btnSuaHDCT.setBorderPainted(false);
        btnSuaHDCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaHDCTActionPerformed(evt);
            }
        });

        btnInHoaDon.setBackground(new java.awt.Color(123, 156, 225));
        btnInHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnInHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconPrint.png"))); // NOI18N
        btnInHoaDon.setText("In hóa Đơn");
        btnInHoaDon.setBorderPainted(false);
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });

        btnSendMail.setBackground(new java.awt.Color(123, 156, 225));
        btnSendMail.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSendMail.setForeground(new java.awt.Color(255, 255, 255));
        btnSendMail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconEdit.png"))); // NOI18N
        btnSendMail.setText("Send mail");
        btnSendMail.setBorderPainted(false);
        btnSendMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendMailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addGap(18, 18, 18)
                .addComponent(cbbMaHoaDonBangHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenKhachHangHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPhongHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSDTHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGiaPhongHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(230, 230, 230))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(btnThemHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102)
                        .addComponent(btnXoaHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(114, 114, 114)
                        .addComponent(btnSuaHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnInHoaDon)
                    .addComponent(btnSendMail, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 909, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 862, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(cbbMaHoaDonBangHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel62)
                            .addComponent(lblPhongHDCT))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51)
                            .addComponent(lblTenKhachHangHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(lblGiaPhongHoaDon))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSDTHDCT)
                            .addComponent(jLabel57))))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(lbTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(79, 79, 79))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jLabel47)
                                .addGap(78, 78, 78))))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnSendMail, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(109, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout HoaDonChiTietLayout = new javax.swing.GroupLayout(HoaDonChiTiet);
        HoaDonChiTiet.setLayout(HoaDonChiTietLayout);
        HoaDonChiTietLayout.setHorizontalGroup(
            HoaDonChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HoaDonChiTietLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HoaDonChiTietLayout.setVerticalGroup(
            HoaDonChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Japnel.add(HoaDonChiTiet, "card8");

        ThongKe.setBackground(new java.awt.Color(255, 255, 255));

        tblThongKe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã phòng", "Tên KH", "SDT", "Tiền thanh toán", "Ngày Tạo", "Trạng Thái"
            }
        ));
        jScrollPane7.setViewportView(tblThongKe);

        cbbTrangThai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chưa thanh toán", "Đã thanh toán" }));
        cbbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTrangThaiActionPerformed(evt);
            }
        });

        cbbThang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbbThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        cbbNam.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbbNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2023", "2022", "2021", "2020" }));

        btnXem.setBackground(new java.awt.Color(71, 120, 197));
        btnXem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXem.setForeground(new java.awt.Color(255, 255, 255));
        btnXem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconSearch.png"))); // NOI18N
        btnXem.setText("Xem");
        btnXem.setBorderPainted(false);
        btnXem.setIconTextGap(20);
        btnXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemActionPerformed(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconSumMoney.png"))); // NOI18N
        jLabel48.setText("Tổng");
        jLabel48.setIconTextGap(10);

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconDebet.png"))); // NOI18N
        jLabel49.setText("Dư nợ");
        jLabel49.setIconTextGap(15);

        lblDuNo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDuNo.setForeground(new java.awt.Color(255, 51, 51));
        lblDuNo.setText("_");

        lblTongTienThanhToan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTongTienThanhToan.setForeground(new java.awt.Color(51, 51, 255));
        lblTongTienThanhToan.setText("_");

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel66.setText("Tháng");

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel67.setText("Năm");

        jPanel2.setBackground(new java.awt.Color(71, 120, 197));

        jLabel72.setBackground(new java.awt.Color(71, 120, 197));
        jLabel72.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel72.setText("Thống kê");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout ThongKeLayout = new javax.swing.GroupLayout(ThongKe);
        ThongKe.setLayout(ThongKeLayout);
        ThongKeLayout.setHorizontalGroup(
            ThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThongKeLayout.createSequentialGroup()
                .addGroup(ThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(ThongKeLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblDuNo, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(362, 362, 362)
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTongTienThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(340, 340, 340)))
                .addGap(0, 37, Short.MAX_VALUE))
            .addGroup(ThongKeLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(ThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ThongKeLayout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addGap(18, 18, 18)
                        .addComponent(cbbThang, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(107, 107, 107)
                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(ThongKeLayout.createSequentialGroup()
                        .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXem, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(201, 201, 201))))
        );
        ThongKeLayout.setVerticalGroup(
            ThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThongKeLayout.createSequentialGroup()
                .addGroup(ThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ThongKeLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ThongKeLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(btnXem, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(ThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbThang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(ThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel48)
                        .addComponent(lblTongTienThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel49)
                        .addComponent(lblDuNo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        Japnel.add(ThongKe, "card9");

        QLDichVu.setBackground(new java.awt.Color(255, 255, 255));
        QLDichVu.setPreferredSize(new java.awt.Dimension(300, 670));
        QLDichVu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelRight1.setPreferredSize(new java.awt.Dimension(500, 400));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel11.setPreferredSize(new java.awt.Dimension(710, 220));

        txtTimKiemDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemDVActionPerformed(evt);
            }
        });

        btnThemDV.setBackground(new java.awt.Color(153, 204, 255));
        btnThemDV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconAdd.png"))); // NOI18N
        btnThemDV.setText("Thêm");
        btnThemDV.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnThemDV.setBorderPainted(false);
        btnThemDV.setIconTextGap(10);
        btnThemDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDVActionPerformed(evt);
            }
        });

        btnXoaDV.setBackground(new java.awt.Color(153, 204, 255));
        btnXoaDV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconDelete.png"))); // NOI18N
        btnXoaDV.setText("Xóa");
        btnXoaDV.setBorderPainted(false);
        btnXoaDV.setIconTextGap(10);
        btnXoaDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDVActionPerformed(evt);
            }
        });

        btnSuaDV.setBackground(new java.awt.Color(153, 204, 255));
        btnSuaDV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSuaDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconEdit.png"))); // NOI18N
        btnSuaDV.setText("Sửa");
        btnSuaDV.setToolTipText("4");
        btnSuaDV.setBorderPainted(false);
        btnSuaDV.setIconTextGap(10);
        btnSuaDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaDVActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(153, 204, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconSearch.png"))); // NOI18N
        jButton4.setText("Search");
        jButton4.setBorderPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnHienThiDV.setBackground(new java.awt.Color(153, 204, 255));
        btnHienThiDV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHienThiDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconShowTable.png"))); // NOI18N
        btnHienThiDV.setText("Hiển thị");
        btnHienThiDV.setBorderPainted(false);
        btnHienThiDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHienThiDVActionPerformed(evt);
            }
        });

        txtClearDV.setBackground(new java.awt.Color(153, 204, 255));
        txtClearDV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtClearDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconClear.png"))); // NOI18N
        txtClearDV.setText("Clear form");
        txtClearDV.setBorderPainted(false);
        txtClearDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClearDVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtTimKiemDV)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(btnThemDV, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnXoaDV)
                        .addGap(36, 36, 36)
                        .addComponent(btnSuaDV)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(btnHienThiDV, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(txtClearDV)))
                .addGap(85, 85, 85))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemDV, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHienThiDV, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnXoaDV, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSuaDV, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtClearDV, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiemDV, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
        );

        jPanel13.setBackground(new java.awt.Color(186, 79, 84));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Quản lý dịch vụ");

        tblDichVu.setBackground(new java.awt.Color(204, 255, 255));
        tblDichVu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã dịch vụ", "Tên dịch vụ", "Giá", "Đơn vị", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDichVu.setFocusable(false);
        tblDichVu.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblDichVu.setRowHeight(25);
        tblDichVu.setSelectionBackground(new java.awt.Color(102, 102, 255));
        tblDichVu.getTableHeader().setReorderingAllowed(false);
        tblDichVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDichVuMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblDichVu);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 944, Short.MAX_VALUE)
            .addComponent(jScrollPane4)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout PanelRight1Layout = new javax.swing.GroupLayout(PanelRight1);
        PanelRight1.setLayout(PanelRight1Layout);
        PanelRight1Layout.setHorizontalGroup(
            PanelRight1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRight1Layout.createSequentialGroup()
                .addGroup(PanelRight1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 944, Short.MAX_VALUE)
                    .addGroup(PanelRight1Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelRight1Layout.setVerticalGroup(
            PanelRight1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRight1Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        QLDichVu.add(PanelRight1, new org.netbeans.lib.awtextra.AbsoluteConstraints(324, 0, 920, 640));

        DuLieu2.setBackground(new java.awt.Color(71, 120, 197));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Mã dịch vụ");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Tên dịch vụ");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Giá");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Trạng thái");

        txtMaDichVu.setBackground(new java.awt.Color(71, 120, 197));
        txtMaDichVu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMaDichVu.setForeground(new java.awt.Color(255, 255, 255));
        txtMaDichVu.setBorder(null);

        rdoHoatDong.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup1.add(rdoHoatDong);
        rdoHoatDong.setForeground(new java.awt.Color(255, 255, 255));
        rdoHoatDong.setText("Hoạt động");
        rdoHoatDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHoatDongActionPerformed(evt);
            }
        });

        rdoNgungHD.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup1.add(rdoNgungHD);
        rdoNgungHD.setForeground(new java.awt.Color(255, 255, 255));
        rdoNgungHD.setText("Ngừng hoạt động");

        txtTenDV.setBackground(new java.awt.Color(71, 120, 197));
        txtTenDV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTenDV.setForeground(new java.awt.Color(255, 255, 255));
        txtTenDV.setBorder(null);
        txtTenDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenDVActionPerformed(evt);
            }
        });

        txtGiaDichVu.setBackground(new java.awt.Color(71, 120, 197));
        txtGiaDichVu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtGiaDichVu.setForeground(new java.awt.Color(255, 255, 255));
        txtGiaDichVu.setBorder(null);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Đơn vị");

        txtDonViDichVu.setBackground(new java.awt.Color(71, 120, 197));
        txtDonViDichVu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDonViDichVu.setForeground(new java.awt.Color(255, 255, 255));
        txtDonViDichVu.setBorder(null);
        txtDonViDichVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonViDichVuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DuLieu2Layout = new javax.swing.GroupLayout(DuLieu2);
        DuLieu2.setLayout(DuLieu2Layout);
        DuLieu2Layout.setHorizontalGroup(
            DuLieu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DuLieu2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(DuLieu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DuLieu2Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(DuLieu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DuLieu2Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(33, 33, 33)
                        .addGroup(DuLieu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenDV, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DuLieu2Layout.createSequentialGroup()
                        .addGroup(DuLieu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(45, 45, 45)
                        .addGroup(DuLieu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DuLieu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDonViDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                                .addComponent(jSeparator9)
                                .addComponent(txtGiaDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DuLieu2Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(rdoHoatDong, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(rdoNgungHD)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DuLieu2Layout.setVerticalGroup(
            DuLieu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DuLieu2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(DuLieu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DuLieu2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DuLieu2Layout.createSequentialGroup()
                        .addComponent(txtMaDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40)
                .addGroup(DuLieu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DuLieu2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DuLieu2Layout.createSequentialGroup()
                        .addComponent(txtTenDV, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(DuLieu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(DuLieu2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel28))
                    .addGroup(DuLieu2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(txtGiaDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDonViDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addGroup(DuLieu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(DuLieu2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(DuLieu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoHoatDong)
                            .addComponent(rdoNgungHD))))
                .addContainerGap(259, Short.MAX_VALUE))
        );

        QLDichVu.add(DuLieu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        Japnel.add(QLDichVu, "card5");

        QLHoaDon.setBackground(new java.awt.Color(255, 255, 255));

        QLDichVu2.setBackground(new java.awt.Color(255, 255, 255));

        DuLieu5.setBackground(new java.awt.Color(71, 120, 197));
        DuLieu5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("Mã hóa đơn");
        DuLieu5.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 26, 70, 26));

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("Mã hợp đồng");
        DuLieu5.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, 29));

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Ngày tạo");
        DuLieu5.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 55, 29));

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Trạng thái");
        DuLieu5.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 339, 70, 40));

        txtMaHoaDon.setBackground(new java.awt.Color(71, 120, 197));
        txtMaHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMaHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        txtMaHoaDon.setBorder(null);
        txtMaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHoaDonActionPerformed(evt);
            }
        });
        DuLieu5.add(txtMaHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 203, 30));

        rdoDaThanhToan.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup1.add(rdoDaThanhToan);
        rdoDaThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        rdoDaThanhToan.setText("Đã thanh toán");
        DuLieu5.add(rdoDaThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, -1, -1));

        txtChuaThanhToan.setBackground(new java.awt.Color(71, 120, 197));
        buttonGroup1.add(txtChuaThanhToan);
        txtChuaThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        txtChuaThanhToan.setText("Chưa thanh toán");
        DuLieu5.add(txtChuaThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, -1, -1));

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("Tổng tiền");
        DuLieu5.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        txtTongTien.setBackground(new java.awt.Color(71, 120, 197));
        txtTongTien.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTongTien.setForeground(new java.awt.Color(255, 255, 255));
        txtTongTien.setBorder(null);
        DuLieu5.add(txtTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 254, 203, 30));

        DuLieu5.add(cbbMaPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 203, 29));

        txtNgayTao.setBackground(new java.awt.Color(71, 120, 197));
        txtNgayTao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNgayTao.setForeground(new java.awt.Color(255, 255, 255));
        txtNgayTao.setBorder(null);
        txtNgayTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayTaoActionPerformed(evt);
            }
        });
        DuLieu5.add(txtNgayTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 203, 31));

        PanelRight4.setBackground(new java.awt.Color(255, 255, 255));

        tblHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Tên khách hàng", "Số điện thoại", "Mã phòng", "Giá phòng", "Ngày tạo", "Tổng tiền", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setFocusable(false);
        tblHoaDon.setGridColor(new java.awt.Color(255, 255, 255));
        tblHoaDon.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblHoaDon.setRowHeight(25);
        tblHoaDon.setSelectionBackground(new java.awt.Color(71, 120, 197));
        tblHoaDon.getTableHeader().setReorderingAllowed(false);
        jScrollPane10.setViewportView(tblHoaDon);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnThemHoaDon.setBackground(new java.awt.Color(153, 204, 255));
        btnThemHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconAdd.png"))); // NOI18N
        btnThemHoaDon.setText("Thêm");
        btnThemHoaDon.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnThemHoaDon.setBorderPainted(false);
        btnThemHoaDon.setIconTextGap(10);
        btnThemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHoaDonActionPerformed(evt);
            }
        });

        btnXoaHoaDon.setBackground(new java.awt.Color(153, 204, 255));
        btnXoaHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconDelete.png"))); // NOI18N
        btnXoaHoaDon.setText("Xóa");
        btnXoaHoaDon.setBorderPainted(false);
        btnXoaHoaDon.setIconTextGap(10);
        btnXoaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHoaDonActionPerformed(evt);
            }
        });

        btnSuaHoaDon.setBackground(new java.awt.Color(153, 204, 255));
        btnSuaHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSuaHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconEdit.png"))); // NOI18N
        btnSuaHoaDon.setText("Sửa");
        btnSuaHoaDon.setBorderPainted(false);
        btnSuaHoaDon.setIconTextGap(10);

        btnTKHoaDon.setBackground(new java.awt.Color(153, 204, 255));
        btnTKHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTKHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconSearch.png"))); // NOI18N
        btnTKHoaDon.setText("Search");
        btnTKHoaDon.setBorderPainted(false);
        btnTKHoaDon.setIconTextGap(10);

        btnHienThiHoaDon.setBackground(new java.awt.Color(153, 204, 255));
        btnHienThiHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHienThiHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconShowTable.png"))); // NOI18N
        btnHienThiHoaDon.setText("Hiển thị");
        btnHienThiHoaDon.setBorderPainted(false);
        btnHienThiHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHienThiHoaDonActionPerformed(evt);
            }
        });

        txtClearHoaDon.setBackground(new java.awt.Color(153, 204, 255));
        txtClearHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtClearHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconClear.png"))); // NOI18N
        txtClearHoaDon.setText("Clear form");
        txtClearHoaDon.setBorderPainted(false);
        txtClearHoaDon.setIconTextGap(10);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTKHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btnThemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120)
                        .addComponent(btnSuaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(112, 112, 112)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtClearHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHienThiHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(57, 57, 57))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtClearHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTKHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHienThiHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(171, Short.MAX_VALUE))
        );

        jPanel18.setBackground(new java.awt.Color(255, 204, 204));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Quản lý hóa đơn");
        jPanel18.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 40));

        javax.swing.GroupLayout PanelRight4Layout = new javax.swing.GroupLayout(PanelRight4);
        PanelRight4.setLayout(PanelRight4Layout);
        PanelRight4Layout.setHorizontalGroup(
            PanelRight4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelRight4Layout.setVerticalGroup(
            PanelRight4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRight4Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        DuLieu5.add(PanelRight4, new org.netbeans.lib.awtextra.AbsoluteConstraints(343, 0, -1, -1));
        DuLieu5.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, -1, -1));
        DuLieu5.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 200, 10));
        DuLieu5.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 200, 10));
        DuLieu5.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 200, 10));

        javax.swing.GroupLayout QLDichVu2Layout = new javax.swing.GroupLayout(QLDichVu2);
        QLDichVu2.setLayout(QLDichVu2Layout);
        QLDichVu2Layout.setHorizontalGroup(
            QLDichVu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DuLieu5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        QLDichVu2Layout.setVerticalGroup(
            QLDichVu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QLDichVu2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(DuLieu5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout QLHoaDonLayout = new javax.swing.GroupLayout(QLHoaDon);
        QLHoaDon.setLayout(QLHoaDonLayout);
        QLHoaDonLayout.setHorizontalGroup(
            QLHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QLHoaDonLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(QLDichVu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        QLHoaDonLayout.setVerticalGroup(
            QLHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QLHoaDonLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(QLDichVu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Japnel.add(QLHoaDon, "card6");

        jpanl1.add(Japnel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 66, -1, 670));

        jPanel1.setBackground(new java.awt.Color(23, 35, 51));
        jPanel1.setForeground(new java.awt.Color(102, 102, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(180, 66));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Menu");

        hienMenu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hienMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconMenu.png"))); // NOI18N
        hienMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hienMenuMouseClicked(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconCloseWhite.png"))); // NOI18N
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(hienMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1059, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hienMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jpanl1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanl1, javax.swing.GroupLayout.PREFERRED_SIZE, 1251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jpanl1, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void hienMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hienMenuMouseClicked
        openMenuBar();

        //  DuLieu7.setVisible(false);
    }//GEN-LAST:event_hienMenuMouseClicked

    private void btnThemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHoaDonActionPerformed
        HoaDon hd = this.getDataHoaDon();
        if (hd == null) {
            return;
        }
        hoaDonDV.insert(hd);
        JOptionPane.showMessageDialog(this, "Đã thêm thành công");
        this.loadTableHoaDon();
    }//GEN-LAST:event_btnThemHoaDonActionPerformed

    private void txtNgayTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayTaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayTaoActionPerformed

    private void txtMaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHoaDonActionPerformed

    private void btnHienThiHopDong1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHienThiHopDong1ActionPerformed
//        String maDV = this.txtTimKiemDV.getText();
//        int check = 0;
//        if (maDV.trim().length() == 0) {
//            JOptionPane.showMessageDialog(this, "Hãy nhập tên dịch vụ cần tìm kiếm");
//            return;
//        } else {
//            for (QLDichVu sv : this.dichvuSV.getAllDV()) {
//                if (sv.getMaDV().equalsIgnoreCase(txtTimKiemDV.getText())) {
//                    check++;
//                    this.txtMaDichVu.setText(sv.getMaDV());
//                    this.txtTenDV.setText(sv.getTenDV());
//                    this.txtDonViDichVu.setText(sv.getDonVi());
//                    JOptionPane.showMessageDialog(this, "Tìm thấy dịch vụ");
//                }
//            }
//        }
//        if (check == 0) {
//            this.clearFormKhachHang();
//            JOptionPane.showMessageDialog(this, "Không tìm thấy dịch vụ");
//        }
//        try {
//            Connection conn = DBConnection.getConnection();
//            if (conn != null) {
//                DefaultTableModel dtm = (DefaultTableModel) this.tblDichVu.getModel();
//                dtm.setRowCount(0);
//                String sql = "SELECT * FROM DichVu WHERE MaDichVu= ?";
//                java.sql.CallableStatement ps = conn.prepareCall(sql);
//                ps.setString(1, txtMaDichVu.getText());
//                ps.execute();
//                java.sql.ResultSet rs = ps.getResultSet();
//                while (rs.next()) {
//                    String MaDichVu = rs.getString("MaDichVu");
//                    String TenDichVu = rs.getString("TenDichVu");
//                    String Gia = rs.getString("Gia");
//                    String DonVi = rs.getString("DonVi");
//                    int tt = rs.getInt("TrangThai");
//                    if (tt == 1) {
//                        this.rdoHoatDong.setSelected(true);
//                    } else {
//                        this.rdoNgungHD.setSelected(true);
//
//                    }
//                    dtm.addRow(new Object[]{MaDichVu, TenDichVu, Gia, DonVi, tt == 1 ? "Hoạt động" : "Ngừng hoạt động",});
//                }
//
//            } else {
//
//                JOptionPane.showMessageDialog(this, "Hãy nhập mã dịch vụ cần tìm");
//                return;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
    }//GEN-LAST:event_btnHienThiHopDong1ActionPerformed

    private void btnInHopDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHopDongActionPerformed

        try {
            Hashtable map = new Hashtable();
            map.put("MaHD", txtMaHopDong.getText());
            JasperReport rpt = JasperCompileManager.compileReport("src/Report/InHopDong.jrxml");
            //map.put("MaHoaDon", cbbMaHoaDonBangHDCT.getSelectedItem());
            // Connection conn = (Connection) DriverManager.getConnection(USERNAME, USERNAME, PASSWORD);
            java.sql.Connection con = DBConnection.getConnection();
            //  java.sql.Connection connection = null;
            JasperPrint p = JasperFillManager.fillReport(rpt, map, con);
            JasperViewer.viewReport(p, false);
        } catch (JRException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
//        String path=" ";
//        JFileChooser j = new JFileChooser();
//        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        int x=j.showSaveDialog(Phong);
//        if(x==JFileChooser.APPROVE_OPTION){
//            path =j.getSelectedFile().getPath();
//        }
//        Document doc = new Document();
//        try {
//            PdfWriter.getInstance(doc, new FileOutputStream(path+"HopDong.pdf"));
//
//            doc.open();
//            PdfPTable tbl = new PdfPTable(11);
//            //Adding header
//            tbl.addCell("Mã hợp đồng");
//            tbl.addCell("Tên khách hàng");
//            tbl.addCell("Mã phòng");
//            tbl.addCell("Tên phòng");
//            tbl.addCell("Giá phòng");
//            tbl.addCell("Diện tích");
//            tbl.addCell("Ngày ký");
//            tbl.addCell("Ngày hết hạn");
//            tbl.addCell("Tiền cọc");
//            tbl.addCell("Mô tả");
//            tbl.addCell("Trạng thái");
//            for (int i = 0; i < tblHopDong.getRowCount(); i++) {
//                String ma = tblHopDong.getValueAt(i, 0).toString();
//                String ten = tblHopDong.getValueAt(i, 1).toString();
//                String maPhong = tblHopDong.getValueAt(i, 2).toString();
//                String tenPhong = tblHopDong.getValueAt(i, 3).toString();
//                String giaPhong = tblHopDong.getValueAt(i, 4).toString();
//                String dienTich = tblHopDong.getValueAt(i, 5).toString();
//                String ngayKy = tblHopDong.getValueAt(i, 6).toString();
//                String ngayHH = tblHopDong.getValueAt(i, 7).toString();
//                String tienCoc = tblHopDong.getValueAt(i, 8).toString();
//                String moTa = tblHopDong.getValueAt(i, 9).toString();
//                String trangThai = tblHopDong.getValueAt(i, 10).toString();
//
//                tbl.addCell(ma);
//                tbl.addCell(ten);
//                tbl.addCell(maPhong);
//                tbl.addCell(tenPhong);
//                tbl.addCell(giaPhong);
//                tbl.addCell(dienTich);
//                tbl.addCell(ngayKy);
//                tbl.addCell(ngayHH);
//                tbl.addCell(tienCoc);
//                tbl.addCell(moTa);
//                tbl.addCell(trangThai);
//            }
//            doc.add(tbl);
//            JOptionPane.showMessageDialog(this, "File pdf đã tải xuống");
//        } catch (FileNotFoundException ex) {
//            JOptionPane.showMessageDialog(this, "Không tìm thấy file");
//            ex.printStackTrace();
//        } catch (DocumentException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        doc.close();
    }//GEN-LAST:event_btnInHopDongActionPerformed

    private void btnThemHopDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHopDongActionPerformed
        HopDong hd = this.getDataHopDong();
        if (hd == null) {
            return;
        }
        //int indexPhongTro = cbbTenPhong.getSelectedIndex();
        // /ArrayList<QLPhongTro> listPhong = this.phongSV.getAll();
        //  QLPhongTro kh = listPhong.get(indexPhongTro);
        // ArrayList<QLHopDong> list = this.hopDongSV.getAllHD();
        hopDongSV.insert(hd);
        this.loadTableHopDong();
        JOptionPane.showMessageDialog(this, "Tạo hợp đồng thành công");
        clearFormHopDong();

    }//GEN-LAST:event_btnThemHopDongActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void tblHopDongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHopDongMouseClicked
        int row = this.tblHopDong.getSelectedRow();
        String ma = this.tblHopDong.getValueAt(row, 0).toString();
        txtMaHopDong.setText(ma);
    }//GEN-LAST:event_tblHopDongMouseClicked

    private void txtNgayHetHanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayHetHanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayHetHanActionPerformed

    private void cbbTenPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTenPhongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbTenPhongActionPerformed

    private void txtDonViDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonViDichVuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonViDichVuActionPerformed

    private void txtTenDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenDVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenDVActionPerformed

    private void rdoHoatDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHoatDongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoHoatDongActionPerformed

    private void txtClearDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClearDVActionPerformed
        cleraFormDichVu();
    }//GEN-LAST:event_txtClearDVActionPerformed

    private void btnHienThiDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHienThiDVActionPerformed
        loadTableDichVu();
    }//GEN-LAST:event_btnHienThiDVActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String maDV = this.txtTimKiemDV.getText();
        int check = 0;
        if (maDV.trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Hãy nhập tên dịch vụ cần tìm kiếm");
            return;
        } else {
            for (QLDichVu sv : this.dichvuSV.getAllDV()) {
                if (sv.getMaDV().equalsIgnoreCase(txtTimKiemDV.getText())) {
                    check++;
                    this.txtMaDichVu.setText(sv.getMaDV());
                    this.txtTenDV.setText(sv.getTenDV());
                    this.txtDonViDichVu.setText(sv.getDonVi());
                    JOptionPane.showMessageDialog(this, "Tìm thấy dịch vụ");
                }
            }
        }
        if (check == 0) {
            this.clearFormKhachHang();
            JOptionPane.showMessageDialog(this, "Không tìm thấy dịch vụ");
        }
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                DefaultTableModel dtm = (DefaultTableModel) this.tblDichVu.getModel();
                dtm.setRowCount(0);
                String sql = "SELECT * FROM DichVu WHERE MaDichVu= ?";
                java.sql.CallableStatement ps = conn.prepareCall(sql);
                ps.setString(1, txtMaDichVu.getText());
                ps.execute();
                java.sql.ResultSet rs = ps.getResultSet();
                while (rs.next()) {
                    String MaDichVu = rs.getString("MaDichVu");
                    String TenDichVu = rs.getString("TenDichVu");
                    String Gia = rs.getString("Gia");
                    String DonVi = rs.getString("DonVi");
                    int tt = rs.getInt("TrangThai");
                    if (tt == 1) {
                        this.rdoHoatDong.setSelected(true);
                    } else {
                        this.rdoNgungHD.setSelected(true);

                    }
                    dtm.addRow(new Object[]{MaDichVu, TenDichVu, Gia, DonVi, tt == 1 ? "Hoạt động" : "Ngừng hoạt động",});
                }

            } else {

                JOptionPane.showMessageDialog(this, "Hãy nhập mã dịch vụ cần tìm");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnSuaDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaDVActionPerformed
        int row = tblDichVu.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn dòng cần sửa");
            return;
        }

        DichVu kh = this.getFormDataDichVu();
        if (kh == null) {
            return;
        }
        String MaDV = this.tblDichVu.getValueAt(row, 0).toString();
        this.dichvuSV.update(MaDV, kh);
        JOptionPane.showMessageDialog(this, "Đã sửa thành công");
        this.loadTableDichVu();
        this.cleraFormDichVu();

    }//GEN-LAST:event_btnSuaDVActionPerformed

    private void btnXoaDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDVActionPerformed
        int row = tblDichVu.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng cần xóa");
            return;
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa");
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }
        String MaDV = this.tblDichVu.getValueAt(row, 0).toString();
        this.dichvuSV.delete(MaDV);
        JOptionPane.showMessageDialog(this, "Đã xóa thành công");
        this.loadTableDichVu();
        this.cleraFormDichVu();
    }//GEN-LAST:event_btnXoaDVActionPerformed

    private void btnThemDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDVActionPerformed
        boolean check = checkLoiDichVu();
        if (check) {
            DichVu dv = this.getFormDataDichVu();
            if (dv == null) {
                return;
            }
            dichvuSV.insert(dv);
            JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công");
            this.loadTableDichVu();
            cleraFormDichVu();
        }
    }//GEN-LAST:event_btnThemDVActionPerformed

    private void txtTimKiemDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemDVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemDVActionPerformed

    private void tblDichVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDichVuMouseClicked
        int row = this.tblDichVu.getSelectedRow();
        String ma = this.tblDichVu.getValueAt(row, 0).toString();
        String ten = this.tblDichVu.getValueAt(row, 1).toString();
        String gia = this.tblDichVu.getValueAt(row, 2).toString();
        String donVi = this.tblDichVu.getValueAt(row, 3).toString();
        txtMaDichVu.setText(ma);
        txtTenDV.setText(ten);
        txtGiaDichVu.setText(gia);
        txtDonViDichVu.setText(donVi);
        if (tblDichVu.getValueAt(row, 4).toString().equalsIgnoreCase("Hoạt động")) {
            this.rdoHoatDong.setSelected(true);
        } else {
            this.rdoNgungHD.setSelected(true);
        }
        // txtMaDichVu.setEditable(false);
    }//GEN-LAST:event_tblDichVuMouseClicked

    private void btnSuaHDCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaHDCTActionPerformed
        HoaDonChiTiet hdct = this.getFormDataChiTiet();
        if (hdct == null) {
            return;
        }
        int row = tblHoaDonChiTiet.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn dòng cần sửa");
            return;
        }

        String maHD = this.tblHoaDonChiTiet.getValueAt(row, 0).toString();
        hdctSV.update(maHD, hdct);
        QLHoaDon dv = (QLHoaDon) cbbMaHoaDonBangHDCT.getSelectedItem();
        loadTableHoaDonChitiet(dv.getMaHDon(), dv.getGiaPhong());
        JOptionPane.showMessageDialog(this, "Sửa thành công");
    }//GEN-LAST:event_btnSuaHDCTActionPerformed

    private void btnXoaHDCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHDCTActionPerformed
        int row = tblHoaDonChiTiet.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn dòng cần Xóa");
            return;
        }

        String maHD = this.tblHoaDonChiTiet.getValueAt(row, 0).toString();
        hdctSV.delete(maHD);
        QLHoaDon dv = (QLHoaDon) cbbMaHoaDonBangHDCT.getSelectedItem();
        loadTableHoaDonChitiet(dv.getMaHDon(), dv.getGiaPhong());
        JOptionPane.showMessageDialog(this, "Xóa thành công");
    }//GEN-LAST:event_btnXoaHDCTActionPerformed

    private void cbbMaHoaDonBangHDCTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbMaHoaDonBangHDCTItemStateChanged
        QLHoaDon dv = (QLHoaDon) cbbMaHoaDonBangHDCT.getSelectedItem();
        // String maDV = dv.getMaDV();
        lblGiaPhongHoaDon.setText(String.valueOf(dv.getGiaPhong()).toString() + " VNĐ");
        lblPhongHDCT.setText(dv.getMaPhong());
        lblSDTHDCT.setText(dv.getSoDT());
        lblTenKhachHangHoaDon.setText(dv.getTenKH());
        loadTableHoaDonChitiet(dv.getMaHDon(), dv.getGiaPhong());
    }//GEN-LAST:event_cbbMaHoaDonBangHDCTItemStateChanged

    private void btnThemHDCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHDCTActionPerformed
        HoaDonChiTiet hdct = this.getFormDataChiTiet();
        if (hdct == null) {
            return;
        }
        QLHoaDon dv = (QLHoaDon) cbbMaHoaDonBangHDCT.getSelectedItem();
        hdctSV.insert(hdct);
        loadTableHoaDonChitiet(dv.getMaHDon(), dv.getGiaPhong());
        JOptionPane.showMessageDialog(this, "Thêm thành công");
    }//GEN-LAST:event_btnThemHDCTActionPerformed

    private void tblHoaDonChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonChiTietMouseClicked
        int row = this.tblHoaDonChiTiet.getSelectedRow();
        //    String maHD = this.tblHoaDonChiTiet.getValueAt(row, 0).toString();
        String tenDV = this.tblHoaDonChiTiet.getValueAt(row, 1).toString();
        String used = this.tblHoaDonChiTiet.getValueAt(row, 3).toString();
        String price = this.tblHoaDonChiTiet.getValueAt(row, 2).toString();
        String sum = this.tblHoaDonChiTiet.getValueAt(row, 4).toString();
        // String tt = this.tblHoaDonChiTiet.getValueAt(row, 4).toString();
        // lblTongTienDichVu.setText(tt);
        //QLHoaDon dv = (QLHoaDon) cbbMaHoaDonBangHDCT.getSelectedItem();
        txtDaSuDung.setText(used.substring(0, used.indexOf(" ")));
        lblTienDichVu.setText(price.substring(0, price.indexOf(" ")));
        lblTongTienDichVu.setText(sum.substring(0, sum.indexOf(" ")));
        //  lbDonvi.setText(used.substring(used.lastIndexOf(" ")));
        // cbbMaHoaDonBangHDCT.setSelectedItem(dv.getMaHDon());
        //        int indexHoaDon = cbbMaHoaDonBangHDCT.getSelectedIndex();
        //        ArrayList<QLHoaDon> listLoaiPhong = this.hoaDonDV.getAllHD();
        //        QLHoaDon ten = listLoaiPhong.get(indexHoaDon);
        //
        //        int indexDichVu = cbbTenDichVu.getSelectedIndex();
        //        ArrayList<QLDichVu> list = this.dichvuSV.getAllDV();
        //         QLDichVu dv = list.get(indexDichVu);
        //  QLDichVu dv = list.get(indexDichVu);
        //   QLDichVu dv1 = (QLDichVu) cbbTenDichVu.getSelectedItem();
        cbbTenDichVu.setSelectedItem(tenDV);
    }//GEN-LAST:event_tblHoaDonChiTietMouseClicked

    private void jPanel10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel10KeyPressed
        // Long tien = Long.valueOf(lblTienDichVu.getText().toString());
        // lblTongTien.setText(String.valueOf( Long.valueOf(txtDaSuDung.getText().toString()) * tien));
    }//GEN-LAST:event_jPanel10KeyPressed

    private void txtDaSuDungInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtDaSuDungInputMethodTextChanged
        //  Long tien = Long.valueOf(lblTienDichVu.getText().toString());
        // lblTongTien.setText(String.valueOf( Long.valueOf(txtDaSuDung.getText().toString()) * tien));
    }//GEN-LAST:event_txtDaSuDungInputMethodTextChanged

    private void txtDaSuDungCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtDaSuDungCaretUpdate
        Double tien = Double.valueOf(lblTienDichVu.getText());
        Double daSDDB = Double.valueOf(txtDaSuDung.getText());
        BigDecimal daSD = new BigDecimal(daSDDB * tien);

        lblTongTienDichVu.setText(String.valueOf(daSD).toString());
    }//GEN-LAST:event_txtDaSuDungCaretUpdate

    private void cbbTenDichVuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbTenDichVuItemStateChanged
        QLDichVu dv = (QLDichVu) cbbTenDichVu.getSelectedItem();
        String maDV = dv.getMaDV();
        lblTienDichVu.setText(dv.getGiaTien().toString());
        lbDonvi.setText(dv.getDonVi().toString());

    }//GEN-LAST:event_cbbTenDichVuItemStateChanged

    private void txtClearKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClearKHActionPerformed
        this.clearFormKhachHang();
    }//GEN-LAST:event_txtClearKHActionPerformed

    private void btnTimKiemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemKHActionPerformed
        String ten = txtTimKiemKH.getText();
        if (ten.trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng");
            return;
        }
        loadTableKhachHangTK(ten);
    }//GEN-LAST:event_btnTimKiemKHActionPerformed

    private void btnSuaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKHActionPerformed
        int row = tblKhachHang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn dòng cần sửa");
            return;
        }
        KhachHang kh = this.getFormDataKhachHang();
        if (kh == null) {
            return;
        }
        String MaKH = this.tblKhachHang.getValueAt(row, 0).toString();
        this.khsv.update(MaKH, kh);
        JOptionPane.showMessageDialog(this, "Đã sửa thành công");
        this.loadTableKhachHang();
        this.clearFormKhachHang();
    }//GEN-LAST:event_btnSuaKHActionPerformed

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
        int row = tblKhachHang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng cần xóa");
            return;
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa");
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            String ma = (String) this.tblKhachHang.getValueAt(row, 0);
            this.khsv.delete(ma);
            this.loadTableKhachHang();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        }
    }//GEN-LAST:event_btnXoaKHActionPerformed

    private void btnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKHActionPerformed
        KhachHang kh = this.getFormDataKhachHang();
        if (kh == null) {
            JOptionPane.showMessageDialog(this, "Cần nhập đầy đủ thông tin");
            return;
        } else if (checkTrungMaKhachHang(kh.getMaKH())) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng không được trùng");
            return;
        }

        khsv.insert(kh);
        this.loadTableKhachHang();
        this.clearFormKhachHang();
    }//GEN-LAST:event_btnThemKHActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        int row = this.tblKhachHang.getSelectedRow();
        String ma = this.tblKhachHang.getValueAt(row, 0).toString();
        String tenKH = this.tblKhachHang.getValueAt(row, 1).toString();
        String sdt = this.tblKhachHang.getValueAt(row, 4).toString();
        String adr = this.tblKhachHang.getValueAt(row, 5).toString();
        String cccd = this.tblKhachHang.getValueAt(row, 6).toString();
        String email = this.tblKhachHang.getValueAt(row, 7).toString();
        String ngaySinh = this.tblKhachHang.getValueAt(row, 2).toString();
        txtMaKH.setText(ma);
        txtTenKH.setText(tenKH);
        txtSDT.setText(sdt);
        txtCCCD.setText(cccd);
        txtDiaChi.setText(adr);
        txtEmail.setText(email);
        if (tblKhachHang.getValueAt(row, 3).toString().equalsIgnoreCase("Nam")) {
            this.rdoNam.setSelected(true);
        } else {
            this.rdoNu.setSelected(true);
        }
        if (tblKhachHang.getValueAt(row, 8).toString().equalsIgnoreCase("Đang thuê")) {
            this.rdoDangThue.setSelected(true);
        } else if (tblKhachHang.getValueAt(row, 8).toString().equalsIgnoreCase("Chưa Thuê")) {
            this.rdoChuaThue.setSelected(true);
        }
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();

        jDateChNgaySinh.setDate(java.sql.Date.valueOf(ngaySinh));
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void rdoDangThueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDangThueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoDangThueActionPerformed

    private void btnXoaLoaiPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaLoaiPhongMouseClicked
        int row = tblLoaiPhong.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng cần xóa");
            return;
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa");
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            String ma = (String) this.tblLoaiPhong.getValueAt(row, 0);
            this.lpsv.delete(ma);
            this.loadTableLoaiPhong();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        }
    }//GEN-LAST:event_btnXoaLoaiPhongMouseClicked

    private void BtnSuaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnSuaMouseClicked
        int row = tblLoaiPhong.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn dòng cần sửa");
            return;
        }
        LoaiPhong kh = this.getFormDataLoaiPhong();
        if (kh == null) {
            return;
        }
        String MaKH = this.tblLoaiPhong.getValueAt(row, 0).toString();
        this.lpsv.update(MaKH, kh);
        JOptionPane.showMessageDialog(this, "Đã sửa thành công");
        this.loadTableLoaiPhong();
        this.clearFormLoaiPhong();
    }//GEN-LAST:event_BtnSuaMouseClicked

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        LoaiPhong lp = this.getFormDataLoaiPhong();
        if (lp == null) {

            return;
        } else if (checkTrungMa(lp.getMaLP())) {
            JOptionPane.showMessageDialog(this, "mã ko được trùng");
            return;
        }

        lpsv.insert(lp);
        this.loadTableLoaiPhong();
        JOptionPane.showMessageDialog(this, "Thêm thành công");
        this.clearFormLoaiPhong();
    }//GEN-LAST:event_btnThemMouseClicked

    private void tblLoaiPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiPhongMouseClicked
        int row = this.tblLoaiPhong.getSelectedRow();
        String ma = this.tblLoaiPhong.getValueAt(row, 0).toString();
        String ten = this.tblLoaiPhong.getValueAt(row, 1).toString();
        String moTa = this.tblLoaiPhong.getValueAt(row, 2).toString();
        txtMaLP.setText(ma);
        txtLP.setText(ten);
        txtMoTa.setText(moTa);
        if (tblLoaiPhong.getValueAt(row, 3).toString().equalsIgnoreCase("Còn")) {
            this.rdoCon.setSelected(true);
        } else {
            this.rdoHet.setSelected(true);
        }
    }//GEN-LAST:event_tblLoaiPhongMouseClicked

    private void btnSuaPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaPhongActionPerformed
        int row = tblPhong.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn dòng cần sửa");
            return;
        }
        PhongTro kh = this.getFormDataPhongTro();
        if (kh == null) {
            return;
        }
        String MaKH = this.tblPhong.getValueAt(row, 0).toString();
        this.phongSV.update(MaKH, kh);
        JOptionPane.showMessageDialog(this, "Đã sửa thành công");
        this.loadTablePhongTro();
        //this.clearFormKhachHang();
    }//GEN-LAST:event_btnSuaPhongActionPerformed

    private void btnXoaPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaPhongActionPerformed
        int row = tblPhong.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng cần xóa");
            return;
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa");
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            String ma = (String) this.tblPhong.getValueAt(row, 0);
            this.phongSV.delete(ma);
            loadTablePhongTro();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        }
    }//GEN-LAST:event_btnXoaPhongActionPerformed

    private void btnThemPhonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPhonActionPerformed
        PhongTro dv = this.getFormDataPhongTro();
        if (dv == null) {
            return;
        }
        phongSV.insert(dv);
        JOptionPane.showMessageDialog(this, "Thêm phòng thành công");
        this.loadTablePhongTro();
    }//GEN-LAST:event_btnThemPhonActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void tblPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhongMouseClicked
        int row = this.tblPhong.getSelectedRow();
        String ma = this.tblPhong.getValueAt(row, 0).toString();
        String maLP = this.tblPhong.getValueAt(row, 1).toString();

        String gia = this.tblPhong.getValueAt(row, 3).toString();
        String dienTich = this.tblPhong.getValueAt(row, 2).toString();
        String tang = this.tblPhong.getValueAt(row, 4).toString();
        String moTa = this.tblPhong.getValueAt(row, 5).toString();
        String tt = this.tblPhong.getValueAt(row, 6).toString();
        txtMaPhong.setText(ma);

        // pt.setMaLoaiPhong(ten);
        cbbLoaiPhong.setSelectedItem(maLP);
        txtDienTich.setText(dienTich);
        txtGiaTienPhong.setText(gia);
        cbbTang.setSelectedItem(tang);
        txtMoTaPhong.setText(moTa);
        if (tt.equalsIgnoreCase("Đã thuê")) {
            this.rdoDaThue.setSelected(true);
        } else {
            this.rdochuachoThue.setSelected(true);
        }
        //        int soluong = cbbLoaiPhong.getItemCount();
        //        for (int i = 0; i < soluong; i++) {
        //            QLLoaiPhong plp = cbbLoaiPhong.getItemAt(i);
        //            if(plp.getTenLP().equals(maLP.getTenLP())){
        //                cbbLoaiPhong.setSelectedIndex(i);
        //            }
        //        }
    }//GEN-LAST:event_tblPhongMouseClicked

    private void cbbLoaiPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLoaiPhongActionPerformed

    }//GEN-LAST:event_cbbLoaiPhongActionPerformed

    private void txtDienTichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDienTichActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDienTichActionPerformed

    private void jLabel68MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel68MouseClicked
        QLHopDong.setVisible(false);
        QLKhachHang.setVisible(false);
        QLLoaiPhong.setVisible(false);
        QLHoaDon.setVisible(false);
        QLDichVu.setVisible(false);
        QLPhong.setVisible(false);
        HoaDonChiTiet.setVisible(false);
        ThongKe.setVisible(true);
    }//GEN-LAST:event_jLabel68MouseClicked

    private void HDCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HDCTMouseClicked
        QLHopDong.setVisible(false);
        QLKhachHang.setVisible(false);
        QLLoaiPhong.setVisible(false);
        QLHoaDon.setVisible(false);
        QLDichVu.setVisible(false);
        QLPhong.setVisible(false);
        HoaDonChiTiet.setVisible(true);
        ThongKe.setVisible(false);
        showDataComboBoxHoaDonChiTiet();
        showDataComboBoxDichVu();
    }//GEN-LAST:event_HDCTMouseClicked

    private void HoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HoaDonMouseClicked
        QLHopDong.setVisible(false);
        QLKhachHang.setVisible(false);
        QLLoaiPhong.setVisible(false);
        QLHoaDon.setVisible(true);
        QLDichVu.setVisible(false);
        QLPhong.setVisible(false);
        HoaDonChiTiet.setVisible(false);
        ThongKe.setVisible(false);
        showDataComboBoxHoaDonMaPhong();
    }//GEN-LAST:event_HoaDonMouseClicked

    private void DichVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DichVuMouseClicked
        QLHopDong.setVisible(false);
        QLKhachHang.setVisible(false);
        QLLoaiPhong.setVisible(false);
        QLHoaDon.setVisible(false);
        QLDichVu.setVisible(true);
        QLPhong.setVisible(false);
        HoaDonChiTiet.setVisible(false);
        ThongKe.setVisible(false);
    }//GEN-LAST:event_DichVuMouseClicked

    private void LoaiPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LoaiPhongMouseClicked
        QLHopDong.setVisible(false);
        QLKhachHang.setVisible(false);
        QLLoaiPhong.setVisible(true);
        QLHoaDon.setVisible(false);
        QLDichVu.setVisible(false);
        QLPhong.setVisible(false);
        HoaDonChiTiet.setVisible(false);
        ThongKe.setVisible(false);
        this.loadTableLoaiPhong();
    }//GEN-LAST:event_LoaiPhongMouseClicked

    private void PhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PhongMouseClicked
        QLHopDong.setVisible(false);
        QLKhachHang.setVisible(false);
        QLLoaiPhong.setVisible(false);
        QLHoaDon.setVisible(false);
        QLDichVu.setVisible(false);
        QLPhong.setVisible(true);
        HoaDonChiTiet.setVisible(false);
        ThongKe.setVisible(false);
        loadComboLoaiPhong();
        this.loadTablePhongTro();
    }//GEN-LAST:event_PhongMouseClicked

    private void KhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KhachHangMouseClicked
        QLHopDong.setVisible(false);
        QLKhachHang.setVisible(true);
        QLLoaiPhong.setVisible(false);
        QLHoaDon.setVisible(false);
        QLDichVu.setVisible(false);
        QLPhong.setVisible(false);
        HoaDonChiTiet.setVisible(false);
        ThongKe.setVisible(false);
        this.showDataComboBoxKhachHang();
        this.showDataComboBoxPhong(tt);
        this.loadTableKhachHang();
    }//GEN-LAST:event_KhachHangMouseClicked

    private void HopDongMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HopDongMouseEntered
        HopDong.setBackground(Color.BLUE);
    }//GEN-LAST:event_HopDongMouseEntered

    private void HopDongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HopDongMouseClicked
        QLHopDong.setVisible(true);
        QLKhachHang.setVisible(false);
        QLLoaiPhong.setVisible(false);
        QLHoaDon.setVisible(false);
        QLDichVu.setVisible(false);
        QLPhong.setVisible(false);
        HoaDonChiTiet.setVisible(false);
        ThongKe.setVisible(false);
        this.loadTableHopDong();
        showDataComboBoxKhachHang();
        showDataComboBoxPhong(tt);
    }//GEN-LAST:event_HopDongMouseClicked

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        closeMenuBar();
    }//GEN-LAST:event_closeMouseClicked

    private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHoaDonActionPerformed
        try {
            Hashtable map = new Hashtable();
            map.put("MaHoaDon", cbbMaHoaDonBangHDCT.getSelectedItem().toString());//
            JasperReport rpt = JasperCompileManager.compileReport("src/Report/xuatHoaDon.jrxml");
            //map.put("MaHoaDon", cbbMaHoaDonBangHDCT.getSelectedItem());
            // Connection conn = (Connection) DriverManager.getConnection(USERNAME, USERNAME, PASSWORD);
            java.sql.Connection con = DBConnection.getConnection();
            //  java.sql.Connection connection = null;
            JasperPrint p = JasperFillManager.fillReport(rpt, map, con);
            JasperViewer.viewReport(p, false);
        } catch (JRException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInHoaDonActionPerformed

    private void btnXoaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaHoaDonActionPerformed

    private void btnHienThiHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHienThiHoaDonActionPerformed
        loadTableHoaDon();
    }//GEN-LAST:event_btnHienThiHoaDonActionPerformed

    private void btnXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemActionPerformed
        // TODO : call reponsioty: Load table

        //      QLHoaDon dv = (QLHoaDon) cbbTrangThai.getSelectedItem();
        // int row = this.tblThongKe.getRowCount();
        int tt = cbbTrangThai.getSelectedIndex();
        String thang = cbbThang.getSelectedItem().toString();
        String nam = cbbNam.getSelectedItem().toString();
        loadTableThongKe(tt, Integer.parseInt(thang), Integer.parseInt(nam));
        DefaultTableModel dtm = (DefaultTableModel) this.tblThongKe.getModel();
        if (tt == 0) {
            double TongTien1 = 0;
            for (int i = 0; i < tblThongKe.getRowCount(); i++) {
                if (dtm.getValueAt(i, 4) != null) {
                    String viTri = dtm.getValueAt(i, 4).toString();
                    TongTien1 += Double.parseDouble(viTri);
                }
            }
            lblDuNo.setText(String.valueOf(TongTien1).toString() + "VNĐ");
            lblTongTienThanhToan.setText("");
        } else if (tt == 1) {
            double TongTien1 = 0;
            for (int i = 0; i < tblThongKe.getRowCount(); i++) {

                String viTri = dtm.getValueAt(i, 4).toString();
                TongTien1 += Double.parseDouble(viTri);

            }
            lblTongTienThanhToan.setText(BigDecimal.valueOf(TongTien1).toString() + "VNĐ");
            lblDuNo.setText("");
        }

        //        }else{
        //            double TongTien =0;
        //            for (int i = 0; i < row; i++) {
        //                TongTien+= Double.parseDouble(tblThongKe.getValueAt(i, 4).toString());
        //            }
        //             lblDuNo.setText(String.valueOf(TongTien));
        //        }
    }//GEN-LAST:event_btnXemActionPerformed

    private void btnSendMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendMailActionPerformed
        SendMail sen = new SendMail();
        sen.setVisible(true);
    }//GEN-LAST:event_btnSendMailActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnTimkiemLPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemLPActionPerformed
//    String index = cbbSearchLP.getSelectedItem().toString();
//        System.out.println(index);
//    DefaultTableModel dtm = (DefaultTableModel) this.tblLoaiPhong.getModel();
//    dtm.setRowCount(0);
//        for (QLLoaiPhong tb : this.lpsv.getOne(index)) {
//            if(index.equals(tb)){
//                System.out.println("đã tìm đc");
//                Object[] rowData = {
//                tb.getMaLP(),
//                tb.getTenLP(),
//                tb.getMoTa(),
//                tb.getTrangThai() == 1 ? "Còn" : "Hết",};
//            dtm.addRow(rowData);
//        }
//            }

        int tt = cbbSearchLP.getSelectedIndex();
        TKloaiPhong(tt);
    }//GEN-LAST:event_btnTimkiemLPActionPerformed

    private void cbbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbTrangThaiActionPerformed

    private void QLLoaiPhongAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_QLLoaiPhongAncestorAdded

    }//GEN-LAST:event_QLLoaiPhongAncestorAdded

    private void cbbSearchLPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSearchLPActionPerformed

    }//GEN-LAST:event_cbbSearchLPActionPerformed

    private void txtTimKiemKHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKHKeyReleased
        String ten = txtTimKiemKH.getText();
        loadTableKhachHangTK(ten);
    }//GEN-LAST:event_txtTimKiemKHKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BtnSua;
    private javax.swing.JPanel BtnThem;
    private javax.swing.JLabel DichVu;
    private javax.swing.JPanel DuLieu;
    private javax.swing.JPanel DuLieu1;
    private javax.swing.JPanel DuLieu2;
    private javax.swing.JPanel DuLieu3;
    private javax.swing.JPanel DuLieu5;
    private javax.swing.JPanel DuLieu7;
    private javax.swing.JLabel HDCT;
    private javax.swing.JLabel HoaDon;
    private javax.swing.JPanel HoaDonChiTiet;
    private javax.swing.JLabel HopDong;
    private javax.swing.JPanel Japnel;
    private javax.swing.JLabel KhachHang;
    private javax.swing.JLabel LoaiPhong;
    private javax.swing.JPanel PanelRight;
    private javax.swing.JPanel PanelRight1;
    private javax.swing.JPanel PanelRight2;
    private javax.swing.JPanel PanelRight4;
    private javax.swing.JLabel Phong;
    private javax.swing.JPanel QLDichVu;
    private javax.swing.JPanel QLDichVu2;
    private javax.swing.JPanel QLHoaDon;
    private javax.swing.JPanel QLHopDong;
    private javax.swing.JPanel QLKhachHang;
    private javax.swing.JPanel QLLoaiPhong;
    private javax.swing.JPanel QLPhong;
    private javax.swing.JPanel QLPhong1;
    private javax.swing.JPanel ThongKe;
    private javax.swing.JButton btnHienThiDV;
    private javax.swing.JButton btnHienThiHoaDon;
    private javax.swing.JButton btnHienThiHopDong1;
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnInHopDong;
    private javax.swing.JButton btnSendMail;
    private javax.swing.JPanel btnSua;
    private javax.swing.JButton btnSuaDV;
    private javax.swing.JButton btnSuaHDCT;
    private javax.swing.JButton btnSuaHoaDon;
    private javax.swing.JButton btnSuaKH;
    private javax.swing.JButton btnSuaPhong;
    private javax.swing.JButton btnTKHoaDon;
    private javax.swing.JLabel btnThem;
    private javax.swing.JButton btnThemDV;
    private javax.swing.JButton btnThemHDCT;
    private javax.swing.JButton btnThemHoaDon;
    private javax.swing.JButton btnThemHopDong;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnThemPhon;
    private javax.swing.JButton btnTimKiemKH;
    private javax.swing.JButton btnTimkiemLP;
    private javax.swing.JButton btnXem;
    private javax.swing.JPanel btnXoa;
    private javax.swing.JButton btnXoaDV;
    private javax.swing.JButton btnXoaHDCT;
    private javax.swing.JButton btnXoaHoaDon;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.JLabel btnXoaLoaiPhong;
    private javax.swing.JButton btnXoaPhong;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbbLoaiPhong;
    private javax.swing.JComboBox<String> cbbMaHoaDonBangHDCT;
    private javax.swing.JComboBox<String> cbbMaPhong;
    private javax.swing.JComboBox<String> cbbNam;
    private javax.swing.JComboBox<String> cbbSearchLP;
    private javax.swing.JComboBox<String> cbbTang;
    private javax.swing.JComboBox<String> cbbTenDichVu;
    private javax.swing.JComboBox<String> cbbTenKhachHang;
    private javax.swing.JComboBox<String> cbbTenPhong;
    private javax.swing.JComboBox<String> cbbThang;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private javax.swing.JLabel close;
    private javax.swing.JLabel hienMenu;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private com.toedter.calendar.JDateChooser jDateChNgaySinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JPanel jpanl1;
    private javax.swing.JPanel jplSileMune;
    private javax.swing.JLabel lbDonvi;
    private javax.swing.JLabel lbTongTien;
    private javax.swing.JLabel lblDuNo;
    private javax.swing.JLabel lblGiaPhongHoaDon;
    private javax.swing.JLabel lblPhongHDCT;
    private javax.swing.JLabel lblSDTHDCT;
    private javax.swing.JLabel lblTenKhachHangHoaDon;
    private javax.swing.JLabel lblTienDichVu;
    private javax.swing.JLabel lblTongTienDichVu;
    private javax.swing.JLabel lblTongTienThanhToan;
    private javax.swing.JLabel lblVND;
    private javax.swing.JLabel lblVND1;
    private javax.swing.JRadioButton rdoChuaThue;
    private javax.swing.JRadioButton rdoCon;
    private javax.swing.JRadioButton rdoConHan;
    private javax.swing.JRadioButton rdoDaHetHan;
    private javax.swing.JRadioButton rdoDaThanhToan;
    private javax.swing.JRadioButton rdoDaThue;
    private javax.swing.JRadioButton rdoDangThue;
    private javax.swing.JRadioButton rdoHet;
    private javax.swing.JRadioButton rdoHoatDong;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNgungHD;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdochuachoThue;
    private javax.swing.JTable tblDichVu;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet;
    private javax.swing.JTable tblHopDong;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblLoaiPhong;
    private javax.swing.JTable tblPhong;
    private javax.swing.JTable tblThongKe;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JRadioButton txtChuaThanhToan;
    private javax.swing.JButton txtClearDV;
    private javax.swing.JButton txtClearHoaDon;
    private javax.swing.JButton txtClearKH;
    private javax.swing.JTextField txtDaSuDung;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtDienTich;
    private javax.swing.JTextField txtDonViDichVu;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtGiaDichVu;
    private javax.swing.JTextField txtGiaTienPhong;
    private javax.swing.JTextField txtLP;
    private javax.swing.JTextField txtMaDichVu;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaHopDong;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaLP;
    private javax.swing.JTextField txtMaPhong;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtMoTaHopDong;
    private javax.swing.JTextField txtMoTaPhong;
    private javax.swing.JTextField txtNgayHetHan;
    private javax.swing.JTextField txtNgayKi;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenDV;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTienCoc;
    private javax.swing.JTextField txtTimKiemDV;
    private javax.swing.JTextField txtTimKiemKH;
    private javax.swing.JTextField txtTimKiemLP;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables

    private Double getTongTien(ArrayList<QLHoaDonChiTiet> allHDCT, Double giaphong) {
        Double tong = 0.0;
        for (QLHoaDonChiTiet object : allHDCT) {
            tong += object.getTongTien();
        }
        return tong + giaphong;
    }
}
