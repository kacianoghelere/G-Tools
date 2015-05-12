package br.com.gmp.comps.audioplayer.playlist;

import br.com.gmp.comps.audioplayer.playlist.renderer.GPlaylistRenderer;
import br.com.gmp.comps.list.GList;
import br.com.gmp.comps.model.GListModel;
import br.com.gmp.utils.audio.file.AudioFile;
import java.util.List;
import java.util.Random;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

/**
 * Lista de reproducao de arquivos de audio
 *
 * @author kaciano
 */
public class GPlaylist extends GList {

    private GListModel<AudioFile> playlistModel;
    private final Random random = new Random();
    private int current = -1;

    /**
     * Cria nova instancia de GPlaylist
     */
    public GPlaylist() {
        initialize();
    }

    /**
     * Metodo de inicializacao
     */
    private void initialize() {
        this.playlistModel = new GListModel<>();
        initComponents();
    }

    /**
     * Retorna o arquivo selecionado
     *
     * @return {@code AudioFile} Arquivo de Audio
     */
    public AudioFile getSelected() {
        if (this.playlistModel.getSelectedValue() != null) {
            setCurrent(getSelectedIndex());
            return (AudioFile) this.playlistModel.getElementAt(getCurrent());
        }
        return null;
    }

    /**
     * Retorna o arquivo anterior
     *
     * @return {@code AudioFile} Arquivo de Audio
     */
    public AudioFile getPrevious() {
        if (getSelectedIndex() >= 0) {
            setCurrent(getSelectedIndex() - 1);
            return (AudioFile) this.playlistModel.getElementAt(getCurrent());
        }
        return null;
    }

    /**
     * Retorna o proximo arquivo da lista
     *
     * @return {@code AudioFile} Arquivo de Audio
     */
    public AudioFile getNext() {
        if (getSelectedIndex() < this.playlistModel.getSize() - 1) {
            setCurrent(getSelectedIndex() + 1);
            return (AudioFile) this.playlistModel.getElementAt(getCurrent());
        }
        return null;
    }

    /**
     * Retorna um arquivo aleatorio
     *
     * @return {@code AudioFile} Arquivo de Audio
     */
    public AudioFile getShuffle() {
        setCurrent(random.nextInt(this.playlistModel.getSize()));
        return (AudioFile) this.playlistModel.getElementAt(getCurrent());
    }

    /**
     * Retorna o modelo da lista
     *
     * @return {@code GListModel(AudioFile)} Modelo da lista
     */
    public GListModel<AudioFile> getPlaylistModel() {
        return this.playlistModel;
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

    /**
     * Carrega os arquivos no modelo de dados
     *
     * @param list {@code List(AudioFile)} Lista de dados
     */
    public void loadData(List<AudioFile> list) {
        this.playlistModel = new GListModel<>(list);
        this.repaint();
        this.revalidate();
        SwingUtilities.updateComponentTreeUI(this);
    }

    /**
     * Retorna o indice em reproducao
     *
     * @return {@code int} Indice em reproducao
     */
    public int getCurrent() {
        return current;
    }

    /**
     * Modifica o indice em reproducao
     *
     * @param current {@code int} Indice em reproducao
     */
    public void setCurrent(int current) {
        this.current = current;
        this.setSelectedIndex(getCurrent());
        for (int i = 0; i < playlistModel.getSize(); i++) {
            playlistModel.getElementAt(i).setExecuting(i == current);
        }
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
