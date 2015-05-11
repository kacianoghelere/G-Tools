package br.com.gmp.comps;

import br.com.gmp.comps.model.GListModel;
import br.com.gmp.utils.audio.file.AudioConverter;
import br.com.gmp.utils.audio.file.AudioFile;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kaciano
 */
public class AudioTest extends javax.swing.JFrame {

    GListModel<AudioFile> model;

    public AudioTest() {
        try {
            List<AudioFile> convert = AudioConverter.convert("/home/kaciano/mp3/");
            model = new GListModel<>(convert);
            initComponents();
        } catch (Exception ex) {
            Logger.getLogger(AudioTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        gPlaylist1 = new br.com.gmp.comps.audioplayer.playlist.GPlaylist();
        gAudioPlayerDisplay1 = new br.com.gmp.comps.audioplayer.display.GAudioPlayerDisplay();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Teste de audio");
        setResizable(false);

        gPlaylist1.setModel(model     );
        gPlaylist1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                gPlaylist1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(gPlaylist1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gAudioPlayerDisplay1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(gAudioPlayerDisplay1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void gPlaylist1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_gPlaylist1ValueChanged
        if (gPlaylist1.getSelectedValue() != null) {
            AudioFile selected = (AudioFile) gPlaylist1.getSelectedValue();
            gAudioPlayerDisplay1.setText(selected);
        }
    }//GEN-LAST:event_gPlaylist1ValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc="Look & Feel">
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AudioTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AudioTest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.gmp.comps.audioplayer.display.GAudioPlayerDisplay gAudioPlayerDisplay1;
    private br.com.gmp.comps.audioplayer.playlist.GPlaylist gPlaylist1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
