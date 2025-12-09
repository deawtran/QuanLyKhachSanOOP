package MainApplication;

import controller.QuanLyGiaoDien;
import javax.swing.SwingUtilities;

public class MainApplication {
    public static void main(String[] args) {
        // We use SwingUtilities.invokeLater to ensure the GUI runs safely on the Event Dispatch Thread (EDT).
        SwingUtilities.invokeLater(() -> new QuanLyGiaoDien()); 
    }
}