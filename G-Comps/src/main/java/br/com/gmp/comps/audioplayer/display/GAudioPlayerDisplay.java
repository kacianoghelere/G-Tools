/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        jLAlbum.setText(file.getAlbum());
        jLArtist.setText(file.getArtist());
        jLLength.setText(file.getLength());
        jLTitle.setText(file.getTitle());
        jLTrack.setText(file.getTrack());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLLength = new javax.swing.JLabel();
        jLAlbum = new javax.swing.JLabel();
        jLArtist = new javax.swing.JLabel();
        jLTrack = new javax.swing.JLabel();
        jLTitle = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(213, 213, 213)));
        setToolTipText("");
        setLayout(new java.awt.BorderLayout());

        jLLength.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLLength.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLLength.setText("00:00");
        jLLength.setMaximumSize(new java.awt.Dimension(45, 15));
        jLLength.setMinimumSize(new java.awt.Dimension(45, 15));
        jLLength.setOpaque(true);
        jLLength.setPreferredSize(new java.awt.Dimension(45, 15));
        add(jLLength, java.awt.BorderLayout.LINE_END);

        jLAlbum.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLAlbum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLAlbum.setText("Album");
        jLAlbum.setAlignmentX(0.5F);
        jLAlbum.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jLAlbum.setMinimumSize(new java.awt.Dimension(35, 20));
        jLAlbum.setOpaque(true);
        jLAlbum.setPreferredSize(new java.awt.Dimension(35, 20));
        add(jLAlbum, java.awt.BorderLayout.PAGE_END);

        jLArtist.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLArtist.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLArtist.setText("Artist");
        jLArtist.setAlignmentX(0.5F);
        jLArtist.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jLArtist.setMinimumSize(new java.awt.Dimension(35, 20));
        jLArtist.setOpaque(true);
        jLArtist.setPreferredSize(new java.awt.Dimension(35, 20));
        add(jLArtist, java.awt.BorderLayout.PAGE_START);

        jLTrack.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLTrack.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLTrack.setText("1");
        jLTrack.setMaximumSize(new java.awt.Dimension(25, 15));
        jLTrack.setMinimumSize(new java.awt.Dimension(25, 15));
        jLTrack.setName(""); // NOI18N
        jLTrack.setOpaque(true);
        jLTrack.setPreferredSize(new java.awt.Dimension(25, 15));
        add(jLTrack, java.awt.BorderLayout.WEST);

        jLTitle.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLTitle.setText("Title");
        jLTitle.setOpaque(true);
        add(jLTitle, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLAlbum;
    private javax.swing.JLabel jLArtist;
    private javax.swing.JLabel jLLength;
    private javax.swing.JLabel jLTitle;
    private javax.swing.JLabel jLTrack;
    // End of variables declaration//GEN-END:variables
}
