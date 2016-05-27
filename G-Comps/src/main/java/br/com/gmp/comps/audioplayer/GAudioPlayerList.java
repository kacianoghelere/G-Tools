package br.com.gmp.comps.audioplayer;

import br.com.gmp.comps.audioplayer.playlist.GPlaylist;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author kaciano
 */
public class GAudioPlayerList extends JPanel {

    private static final Logger LOGGER = Logger.getLogger(GAudioPlayerList.class.getName());

    /**
     * Creates new form GPlayerUI
     */
    public GAudioPlayerList() {
        initComponents();
        player.setPlaylist(playlist);
    }

    /**
     * Retorna o componente de reproducao de audio
     *
     * @return {@code GAudioPlayer} Reprodutor de audio
     */
    public GAudioPlayer getPlayer() {
        return player;
    }

    /**
     * Retorna a lista de arquivos para reproducao
     *
     * @return {@code GPlaylist} Lista de arquivos para reproducao
     */
    public GPlaylist getPlaylist() {
        return playlist;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        playlist = new br.com.gmp.comps.audioplayer.playlist.GPlaylist();
        player = new br.com.gmp.comps.audioplayer.GAudioPlayer();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));

        jScrollPane1.setViewportView(playlist);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(player, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(player, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private br.com.gmp.comps.audioplayer.GAudioPlayer player;
    private br.com.gmp.comps.audioplayer.playlist.GPlaylist playlist;
    // End of variables declaration//GEN-END:variables

}
