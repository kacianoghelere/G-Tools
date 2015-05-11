package br.com.gmp.comps.audioplayer.playlist;

import br.com.gmp.comps.audioplayer.playlist.renderer.GPlaylistRenderer;
import br.com.gmp.comps.list.GList;
import br.com.gmp.comps.model.GListModel;
import br.com.gmp.utils.audio.file.AudioFile;
import javax.swing.ListCellRenderer;

/**
 * Lista de reproducao de arquivos de audio
 *
 * @author kaciano
 */
public class GPlaylist extends GList {

    private GListModel<AudioFile> playlistModel;

    /**
     * Cria nova instancia de GPlaylist
     */
    public GPlaylist() {
        initialize();
    }

    private void initialize() {
        this.playlistModel = new GListModel<>();
        initComponents();   
    }

    /**
     * Retorna o modelo da lista
     *
     * @return {@code GListModel(AudioFile)} Modelo da lista
     */
    public GListModel<AudioFile> getPlaylistModel() {
        return playlistModel;
    }

    /**
     * Modifica o modelo da lista
     *
     * @param model {@code GListModel(AudioFile)} Modelo da lista
     */
    public void setPlaylistModel(GListModel<AudioFile> model) {
        this.playlistModel = model;
        this.repaint();
        this.revalidate();
    }

    @Override
    public ListCellRenderer getCellRenderer() {
        return new GPlaylistRenderer();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
