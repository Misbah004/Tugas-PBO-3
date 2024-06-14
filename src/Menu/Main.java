package Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DatabaseConnection;

public class Main {
    private JFrame frame;
    private JTextField namaJenisInformasiField, namaPelayananField, namaPemohonField, alamatPemohonField, namaSidangField, tanggalSidangField, waktuSidangField;
    private JButton createButton, readButton, updateButton, deleteButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Main window = new Main();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Main() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(9, 2));

        frame.getContentPane().add(new JLabel("Nama Jenis Informasi:"));
        namaJenisInformasiField = new JTextField();
        frame.getContentPane().add(namaJenisInformasiField);

        frame.getContentPane().add(new JLabel("Nama Pelayanan:"));
        namaPelayananField = new JTextField();
        frame.getContentPane().add(namaPelayananField);

        frame.getContentPane().add(new JLabel("Nama Pemohon:"));
        namaPemohonField = new JTextField();
        frame.getContentPane().add(namaPemohonField);

        frame.getContentPane().add(new JLabel("Alamat Pemohon:"));
        alamatPemohonField = new JTextField();
        frame.getContentPane().add(alamatPemohonField);

        frame.getContentPane().add(new JLabel("Nama Sidang:"));
        namaSidangField = new JTextField();
        frame.getContentPane().add(namaSidangField);

        frame.getContentPane().add(new JLabel("Tanggal Sidang (YYYY-MM-DD):"));
        tanggalSidangField = new JTextField();
        frame.getContentPane().add(tanggalSidangField);

        frame.getContentPane().add(new JLabel("Waktu Sidang (HH:MM):"));
        waktuSidangField = new JTextField();
        frame.getContentPane().add(waktuSidangField);

        createButton = new JButton("Create");
        createButton.addActionListener(new CreateButtonListener());
        frame.getContentPane().add(createButton);

        readButton = new JButton("Read");
        readButton.addActionListener(new ReadButtonListener());
        frame.getContentPane().add(readButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new UpdateButtonListener());
        frame.getContentPane().add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeleteButtonListener());
        frame.getContentPane().add(deleteButton);
    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO JenisInformasi (nama) VALUES (?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, namaJenisInformasiField.getText());
                    stmt.executeUpdate();
                    
                    ResultSet rs = stmt.getGeneratedKeys();
                    int jenisInformasiId = 0;
                    if (rs.next()) {
                        jenisInformasiId = rs.getInt(1);
                    }

                    sql = "INSERT INTO Pelayanan (nama, jenis_informasi_id) VALUES (?, ?)";
                    try (PreparedStatement stmt2 = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        stmt2.setString(1, namaPelayananField.getText());
                        stmt2.setInt(2, jenisInformasiId);
                        stmt2.executeUpdate();

                        rs = stmt2.getGeneratedKeys();
                        int pelayananId = 0;
                        if (rs.next()) {
                            pelayananId = rs.getInt(1);
                        }

                        sql = "INSERT INTO Pemohon (nama, alamat) VALUES (?, ?)";
                        try (PreparedStatement stmt3 = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                            stmt3.setString(1, namaPemohonField.getText());
                            stmt3.setString(2, alamatPemohonField.getText());
                            stmt3.executeUpdate();

                            rs = stmt3.getGeneratedKeys();
                            int pemohonId = 0;
                            if (rs.next()) {
                                pemohonId = rs.getInt(1);
                            }

                            sql = "INSERT INTO Sidang (nama, tanggal, waktu, pelayanan_id, pemohon_id) VALUES (?, ?, ?, ?, ?)";
                            try (PreparedStatement stmt4 = conn.prepareStatement(sql)) {
                                stmt4.setString(1, namaSidangField.getText());
                                stmt4.setString(2, tanggalSidangField.getText());
                                stmt4.setString(3, waktuSidangField.getText());
                                stmt4.setInt(4, pelayananId);
                                stmt4.setInt(5, pemohonId);
                                stmt4.executeUpdate();
                            }
                        }
                    }
                }

                JOptionPane.showMessageDialog(frame, "Data successfully created!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error creating data: " + ex.getMessage());
            }
        }
    }

    private class ReadButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT s.nama, s.tanggal, s.waktu, p.nama AS pelayanan_nama, j.nama AS jenis_informasi_nama, pm.nama AS pemohon_nama, pm.alamat " +
                         "FROM Sidang s " +
                         "JOIN Pelayanan p ON s.pelayanan_id = p.id " +
                         "JOIN JenisInformasi j ON p.jenis_informasi_id = j.id " +
                         "JOIN Pemohon pm ON s.pemohon_id = pm.id";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    // Bersihkan data pada form sebelum menampilkan data baru
                    clearForm();

                    // Tampilkan data pada form
                    while (rs.next()) {
                        String namaSidang = rs.getString("nama");
                        String tanggalSidang = rs.getString("tanggal");
                        String waktuSidang = rs.getString("waktu");
                        String namaPelayanan = rs.getString("pelayanan_nama");
                        String namaJenisInformasi = rs.getString("jenis_informasi_nama");
                        String namaPemohon = rs.getString("pemohon_nama");
                        String alamatPemohon = rs.getString("alamat");

                        // Tambahkan data ke dalam TextArea atau TextField
                        appendToForm(namaSidang, tanggalSidang, waktuSidang, namaPelayanan, namaJenisInformasi, namaPemohon, alamatPemohon);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error reading data: " + ex.getMessage());
        }
    }

    // Method untuk membersihkan dan menampilkan data pada form
    private void clearForm() {
        namaSidangField.setText("");
        tanggalSidangField.setText("");
        waktuSidangField.setText("");
        namaPelayananField.setText("");
        namaJenisInformasiField.setText("");
        namaPemohonField.setText("");
        alamatPemohonField.setText("");
    }

    private void appendToForm(String namaSidang, String tanggalSidang, String waktuSidang,
                              String namaPelayanan, String namaJenisInformasi,
                              String namaPemohon, String alamatPemohon) {
        // Tampilkan data pada TextField atau TextArea
        namaSidangField.setText(namaSidang);
        tanggalSidangField.setText(tanggalSidang);
        waktuSidangField.setText(waktuSidang);
        namaPelayananField.setText(namaPelayanan);
        namaJenisInformasiField.setText(namaJenisInformasi);
        namaPemohonField.setText(namaPemohon);
        alamatPemohonField.setText(alamatPemohon);
    }
}


    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "UPDATE Sidang SET tanggal = ?, waktu = ? WHERE nama = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, tanggalSidangField.getText());
                    stmt.setString(2, waktuSidangField.getText());
                    stmt.setString(3, namaSidangField.getText());
                    stmt.executeUpdate();
                }

                JOptionPane.showMessageDialog(frame, "Data successfully updated!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error updating data: " + ex.getMessage());
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "DELETE FROM Sidang WHERE nama = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, namaSidangField.getText());
                    stmt.executeUpdate();
                }

                JOptionPane.showMessageDialog(frame, "Data successfully deleted!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error deleting data: " + ex.getMessage());
            }
        }
    }
}
