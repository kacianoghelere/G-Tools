package br.com.gmp.comps.audioplayer.display;

import br.com.gmp.utils.audio.file.AudioFile;
import javax.swing.JPanel;

/**
 *
 * @author kaciano
 */
public class GAudioPlayerDisplay extends JPanel {

    /**
     * Creates new form GAudioPlayerDisplay
     */
    public GAudioPlayerDisplay() {
        initComponents();
    }

    public void setText(AudioFile file) {
        String album = file != null ? file.getAlbum() : "Desconhecido";
        String artist = file != null ? file.getArtist() : "Desconhecido";
        jLInfo.setText(artist + " - " + album);
        jLLength.setText(file != null ? file.getLength() : "--:--");
        jLTitle.setText(file != null ? file.getTitle() : "Desconhecido");
        jLTrack.setText(file != null ? file.getTrack() : "--");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLLength = new javax.swing.JLabel();
        jLInfo = new javax.swing.JLabel();
        jLTrack = new javax.swing.JLabel();
        jLTitle = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(213, 213, 213)));
        setToolTipText("");
        setLayout(new java.awt.BorderLayout());

        jLLength.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLLength.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLLength.setText("00:00");
        jLLength.setMaximumSize(new java.awt.Dimension(45, 15));
        jLLength.setMinimumSize(new java.awt.Dimension(45, 15));
        jLLength.setOpaque(true);
        jLLength.setPreferredSize(new java.awt.Dimension(45, 15));
        add(jLLength, java.awt.BorderLayout.LINE_END);

        jLInfo.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLInfo.setText("Artist - Album");
        jLInfo.setAlignmentX(0.5F);
        jLInfo.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jLInfo.setMinimumSize(new java.awt.Dimension(35, 20));
        jLInfo.setOpaque(true);
        jLInfo.setPreferredSize(new java.awt.Dimension(35, 20));
        add(jLInfo, java.awt.BorderLayout.PAGE_END);

        jLTrack.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLTrack.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLTrack.setText("1");
        jLTrack.setMaximumSize(new java.awt.Dimension(25, 15));
        jLTrack.setMinimumSize(new java.awt.Dimension(25, 15));
        jLTrack.setName(""); // NOI18N
        jLTrack.setOpaque(true);
        jLTrack.setPreferredSize(new java.awt.Dimension(25, 15));
        add(jLTrack, java.awt.BorderLayout.WEST);

        jLTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLTitle.setText("Title");
        jLTitle.setOpaque(true);
        add(jLTitle, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLInfo;
    private javax.swing.JLabel jLLength;
    private javax.swing.JLabel jLTitle;
    private javax.swing.JLabel jLTrack;
    // End of variables declaration//GEN-END:variables
}
