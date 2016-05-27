package br.com.gmp.comps.audioplayer.display;

import br.com.gmp.utils.audio.file.AudioFile;
import java.awt.Image;
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

    /**
     * Modifica o texto do display
     *
     * @param file {@code AudioFile} Arquivo de audio
     */
    public void setText(AudioFile file) {
        String album = file != null ? file.getAlbum() : "Desconhecido";
        String artist = file != null ? file.getArtist() : "Desconhecido";
        if (file != null && file.getArtwork() != null) {
            jLArtwork.setIcon(new javax.swing.ImageIcon(file.getArtwork()
                    .getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        } else {
            jLArtwork.setIcon(new javax.swing.ImageIcon(getClass()
                    .getResource("/br/com/gmp/comps/icons/48/audio-file-48.png")));
        }
        jLInfo.setText(artist + " - " + album);
        jLLength.setText(file != null ? file.getLength() : "--:--");
        jLTitle.setText(file != null ? file.getTitle() : "Desconhecido");
        jLTrack.setText(file != null ? file.getTrack() : "--");
    }

    @Override
    public String getToolTipText() {
        return jLTrack.getText() + " - " + jLTitle.getText() + " - " + jLLength.getText()
                + "\n" + jLInfo.getText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPInfo = new javax.swing.JPanel();
        jLTrack = new javax.swing.JLabel();
        jLTitle = new javax.swing.JLabel();
        jLInfo = new javax.swing.JLabel();
        jLLength = new javax.swing.JLabel();
        jLArtwork = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(213, 213, 213)));
        setToolTipText("");
        setMaximumSize(new java.awt.Dimension(2147483647, 98));
        setMinimumSize(new java.awt.Dimension(350, 98));
        setPreferredSize(new java.awt.Dimension(350, 98));
        setLayout(new java.awt.BorderLayout(1, 0));

        jPInfo.setLayout(new java.awt.BorderLayout(1, 1));

        jLTrack.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLTrack.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLTrack.setText("1");
        jLTrack.setMaximumSize(new java.awt.Dimension(25, 15));
        jLTrack.setMinimumSize(new java.awt.Dimension(25, 15));
        jLTrack.setName(""); // NOI18N
        jLTrack.setOpaque(true);
        jLTrack.setPreferredSize(new java.awt.Dimension(25, 15));
        jPInfo.add(jLTrack, java.awt.BorderLayout.LINE_START);

        jLTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLTitle.setText("Title");
        jLTitle.setOpaque(true);
        jPInfo.add(jLTitle, java.awt.BorderLayout.CENTER);

        jLInfo.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLInfo.setText("Artist - Album");
        jLInfo.setAlignmentX(0.5F);
        jLInfo.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jLInfo.setMinimumSize(new java.awt.Dimension(35, 20));
        jLInfo.setOpaque(true);
        jLInfo.setPreferredSize(new java.awt.Dimension(35, 20));
        jPInfo.add(jLInfo, java.awt.BorderLayout.PAGE_END);

        jLLength.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLLength.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLLength.setText("00:00");
        jLLength.setMaximumSize(new java.awt.Dimension(45, 15));
        jLLength.setMinimumSize(new java.awt.Dimension(45, 15));
        jLLength.setOpaque(true);
        jLLength.setPreferredSize(new java.awt.Dimension(45, 15));
        jPInfo.add(jLLength, java.awt.BorderLayout.LINE_END);

        add(jPInfo, java.awt.BorderLayout.CENTER);

        jLArtwork.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLArtwork.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/gmp/comps/icons/48/audio-file-48.png"))); // NOI18N
        jLArtwork.setMaximumSize(new java.awt.Dimension(98, 98));
        jLArtwork.setMinimumSize(new java.awt.Dimension(98, 98));
        jLArtwork.setPreferredSize(new java.awt.Dimension(98, 98));
        add(jLArtwork, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLArtwork;
    private javax.swing.JLabel jLInfo;
    private javax.swing.JLabel jLLength;
    private javax.swing.JLabel jLTitle;
    private javax.swing.JLabel jLTrack;
    private javax.swing.JPanel jPInfo;
    // End of variables declaration//GEN-END:variables
}
