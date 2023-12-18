/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package TUGASRUMAHBAB10;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author michaeldandy
 */
public class guipengeluaranbulanan extends javax.swing.JFrame {

    /**
     * Creates new form guipengeluaranbulanan
     */
    public guipengeluaranbulanan() {
        initComponents();
        tampil();
        tampil_ph();
        tampil_pm();
        
    }
public void batal() {
        txt_income.setText("");
        txt_spend.setText("");
        txt_sering.setText("");
        txt_jns.setText("");
        txt_time.setText("");
        txt_profit.setText("");
    }
public Connection conn;
String pendb, pengb, ptg, fre, jpn, wkt, ssu;
public void koneksi() throws SQLException {
        try {
            conn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/oop_2218067?user=root&password=");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(guipengeluaranbulanan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            Logger.getLogger(guipengeluaranbulanan.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception es) {
            Logger.getLogger(guipengeluaranbulanan.class.getName()).log(Level.SEVERE, null, es);
        }
    }
public void tampil() {
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("Pendapatan");
        tabelhead.addColumn("Pengeluaran");
        tabelhead.addColumn("Prioritas");
        tabelhead.addColumn("Frekuensi");
        tabelhead.addColumn("Jenis Bayar");
        tabelhead.addColumn("Waktu");
        tabelhead.addColumn("Sisa Uang");
        try {
            koneksi();
            String sql = "SELECT * FROM tb_pengeluaranbulanan";
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                tabelhead.addRow(new Object[]{res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8)});
            }
            tabel_pb.setModel(tabelhead);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "BELUM TERKONEKSI");
        }        
    }
public void tampil_pm() {
        try {
            koneksi();
            String sql = "SELECT `Uang Digunakan Untuk` FROM tb_pengeluaranharian  ";
            Statement stt = conn.createStatement();
            ResultSet res = stt.executeQuery(sql);
            while (res.next()) {
                Object[] ob = new Object[3];
                ob[0] = res.getString(1);
                cmbpb.addItem((String) ob[0]);
            }
            res.close();
            stt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
public void tampil_ph() {
        try {
            koneksi();
            String sql = "SELECT `Uang Digunakan Untuk` FROM tb_pengeluaranharian ";
            Statement stt = conn.createStatement();
            ResultSet res = stt.executeQuery(sql);
            while (res.next()) {
                Object[] ob = new Object[3];
                ob[0] = res.getString(1);
                cmbpb.addItem((String) ob[0]);
            }
            res.close();
            stt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
public void refresh() {
        new gui_pengeluaranmingguan().setVisible(true);
        this.setVisible(false);
    }
public void delete() {
        int ok = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin akan menghapus data ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            try {
                String sql = "DELETE FROM tb_pengeluaranharian WHERE Pendapatan='" + txt_income.getText() + "'";
                java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil di hapus");
                batal();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data gagal di hapus");
            }
        }
        refresh();
    }
public void insert() {
        String pb = txt_income.getText();
        String peb= txt_spend.getText();
        String prio = (String) cmbpb.getSelectedItem();
        String fr = txt_sering.getText();
        String jpb = txt_jns.getText();
        String wkt = txt_time.getText();
        String siu = txt_profit.getText();
        try {
            koneksi();
            Statement statement = conn.createStatement(); 
            statement.executeUpdate("INSERT INTO tb_pengeluaranbulanan(`pendapatan`, `pengeluaran`, `Prioritas`, `Frekuensi`, `Jenis Bayar`,`Waktu`,`Sisa Uang`)"
                    + "VALUES('" + pb + "','" + peb + "','" + prio + "','" + fr + "','" + jpb + "','" + wkt + "',"
                    + "'" + siu + "')");
            statement.close();
            JOptionPane.showMessageDialog(null, "Berhasil Memasukan Pengeluaran Bulanan" + "\n" + prio);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Input!");
        }
        refresh();
    }
public void update() {
        String pb = txt_income.getText();
        String peb= txt_spend.getText();
        String prio = (String) cmbpb.getSelectedItem();
        String fr = txt_sering.getText();
        String jpb = txt_jns.getText();
        String wkt = txt_time.getText();
        String siu = txt_profit.getText();
        try {
        Statement statement = conn.createStatement();
        statement.executeUpdate("UPDATE tb_pengeluaranbulanan SET `Pendapatan`='" + pb + "'," + "`Pengeluaran`='" + peb + "'"
        + "`Prioritas`='" + prio + "'`Frekuensi`='" + fr + "`Jenis Bayar`='" + jpb + "`Waktu`='" + wkt + "`Sisa Uang`='" + siu +  "'");
        statement.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Update Data Pengeluaran Berhasil!");
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        refresh();
    }
public void itempilih() {
        txt_income.setText(pendb);
        txt_spend.setText(pengb);
        cmbpb.setSelectedItem(ptg);
        txt_sering.setText(fre);
        txt_jns.setText(jpn);
        txt_time.setText(wkt);
        txt_profit.setText(ssu);
    }
 public double uangAkhir() {
        double pdb, pgb, sib;
        pdb = Integer.parseInt(txt_income.getText());
        pgb = Integer.parseInt(txt_spend.getText());
        sib = pdb - pgb;
        txt_profit.setText(Double.toString(sib));
        return sib;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jLabel1 = new javax.swing.JLabel();
        pb1 = new javax.swing.JLabel();
        pb2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_income = new javax.swing.JTextField();
        txt_spend = new javax.swing.JTextField();
        txt_sering = new javax.swing.JTextField();
        txt_jns = new javax.swing.JTextField();
        txt_time = new javax.swing.JTextField();
        txt_profit = new javax.swing.JTextField();
        rekap = new javax.swing.JButton();
        close = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_pb = new javax.swing.JTable();
        hapus = new javax.swing.JButton();
        cmbpb = new javax.swing.JComboBox<>();
        up = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Pengeluaran Bulanan");

        pb1.setText("Pendapatan Bulanan");

        pb2.setText("Pengeluaran Bulanan");

        jLabel4.setText("Prioritas Pengeluaran");

        jLabel5.setText("Frekuensi Pengeluaran");

        jLabel6.setText("Jenis Pembayaran");

        jLabel7.setText("Waktu Transaksi");

        jLabel8.setText("Sisa Uang");

        txt_income.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_incomeActionPerformed(evt);
            }
        });

        txt_spend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_spendActionPerformed(evt);
            }
        });

        txt_jns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jnsActionPerformed(evt);
            }
        });

        txt_time.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_timeActionPerformed(evt);
            }
        });

        txt_profit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_profitActionPerformed(evt);
            }
        });

        rekap.setText("Rekap");
        rekap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rekapActionPerformed(evt);
            }
        });

        close.setText("Close");
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeActionPerformed(evt);
            }
        });

        tabel_pb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Pendapatan", "Pengeluaran", "Prioritas", "Frekuensi", "Jenis Bayar", "Waktu", "Sisa Uang"
            }
        ));
        jScrollPane2.setViewportView(tabel_pb);

        hapus.setText("Hapus");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });

        cmbpb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pengeluaran Bulanan", "Pengeluaran Mingguan", "Pengeluaran Harian" }));
        cmbpb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbpbActionPerformed(evt);
            }
        });

        up.setText("Update");
        up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upActionPerformed(evt);
            }
        });

        jButton1.setText("Hitung");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pb1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pb2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_profit)
                                .addComponent(txt_time)
                                .addComponent(txt_jns)
                                .addComponent(txt_sering)
                                .addComponent(txt_spend)
                                .addComponent(txt_income, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbpb, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(rekap)
                                .addGap(34, 34, 34)
                                .addComponent(close)
                                .addGap(28, 28, 28)
                                .addComponent(hapus)
                                .addGap(18, 18, 18)
                                .addComponent(up)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rekap)
                            .addComponent(close)
                            .addComponent(hapus)
                            .addComponent(up)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txt_jns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(pb1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_income, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(pb2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_spend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbpb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_sering, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_profit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_profitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_profitActionPerformed
        
    }//GEN-LAST:event_txt_profitActionPerformed

    private void txt_spendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_spendActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_spendActionPerformed

    private void txt_timeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_timeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_timeActionPerformed

    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_closeActionPerformed

    private void rekapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rekapActionPerformed
insert();
    }//GEN-LAST:event_rekapActionPerformed

    private void txt_jnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jnsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jnsActionPerformed

    private void txt_incomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_incomeActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_incomeActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_hapusActionPerformed

    private void cmbpbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbpbActionPerformed
    new gui_pengeluaranharian().setVisible(true);
    new gui_pengeluaranmingguan().setVisible(true);
    }//GEN-LAST:event_cmbpbActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        uangAkhir();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void upActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_upActionPerformed

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
            java.util.logging.Logger.getLogger(guipengeluaranbulanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(guipengeluaranbulanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(guipengeluaranbulanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(guipengeluaranbulanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new guipengeluaranbulanan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton close;
    private javax.swing.JComboBox<String> cmbpb;
    private javax.swing.JButton hapus;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel pb1;
    private javax.swing.JLabel pb2;
    private javax.swing.JButton rekap;
    private javax.swing.JTable tabel_pb;
    private javax.swing.JTextField txt_income;
    private javax.swing.JTextField txt_jns;
    private javax.swing.JTextField txt_profit;
    private javax.swing.JTextField txt_sering;
    private javax.swing.JTextField txt_spend;
    private javax.swing.JTextField txt_time;
    private javax.swing.JButton up;
    // End of variables declaration//GEN-END:variables

    
}
