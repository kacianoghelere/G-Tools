package br.com.gmp.comps.audioplayer.playlist;

import br.com.gmp.utils.audio.file.AudioFile;
import javax.swing.JPanel;

/**
 * Item de lista de audio
 *
 * @author kaciano
 */
public class GPlaylistItem extends JPanel {

    private String title;
    private String length;
    private String info;
    private AudioFile file;

    /**
     * Cria nova instancia de GPlaylistItem
     */
    public GPlaylistItem() {
        this("", "", " - ", null);
        initialize();
    }

    /**
     * Cria nova instancia de GPlaylistItem
     *
     * @param file {@code String} Arquivo de audio do item de playlist
     */
    public GPlaylistItem(AudioFile file) {
        this(file.getTitle(), file.getLength(), file.getArtist() + " - " + file.getAlbum(), file);
        initialize();
    }

    /**
     * Cria nova instancia de GPlaylistItem
     *
     * @param title {@code String} Titulo do item de playlist
     * @param length {@code String} Comprimento do item de playlist
     * @param info {@code String} Informacoes extras do item de playlist
     * @param file {@code String} Arquivo de audio do item de playlist
     */
    public GPlaylistItem(String title, String length, String info, AudioFile file) {
        this.title = title;
        this.length = length;
        this.info = info;
        this.file = file;
        initialize();
    }

    /**
     * Metodo de inicializacao
     */
    private void initialize() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLTitle = new javax.swing.JLabel();
        jLLength = new javax.swing.JLabel();
        jLInfo = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(220, 42));
        setPreferredSize(new java.awt.Dimension(220, 42));

        jLTitle.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLTitle.setText("Title");

        jLLength.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLLength.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLLength.setText("00:00");

        jLInfo.setFont(new java.awt.Font("Ubuntu", 2, 12)); // NOI18N
        jLInfo.setText("Artist - Album");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jLLength, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                    .addComponent(jLLength, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jLInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Retorna o titulo do item de playlist
     *
     * @return {@code String} Titulo do item de playlist
     */
    public String getTitle() {
        return title;
    }

    /**
     * Modifica titulo do item de playlist
     *
     * @param title {@code String} Titulo do item de playlist
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retorna o comprimento da faixa do item de playlist
     *
     * @return {@code String} Comprimento do item de playlist
     */
    public String getLength() {
        return length;
    }

    /**
     * Modifica comprimento da faixa do item de playlist
     *
     * @param length {@code String} Comprimento do item de playlist
     */
    public void setLength(String length) {
        this.length = length;
    }

    /**
     * Retorna as informacoes extras do item de playlist
     *
     * @return {@code String} Informacoes extras do item de playlist
     */
    public String getInfo() {
        return info;
    }

    /**
     * Modifica as informacoes extras do item de playlist
     *
     * @param info {@code String} Informacoes extras do item de playlist
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Retorna o arquivo de audio do item de playlist
     *
     * @return {@code String} Arquivo de audio do item de playlist
     */
    public AudioFile getFile() {
        return file;
    }

    /**
     * Modifica o arquivo de audio do item de playlist
     *
     * @param file {@code String} Arquivo de audio do item de playlist
     */
    public void setFile(AudioFile file) {
        this.file = file;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLInfo;
    private javax.swing.JLabel jLLength;
    private javax.swing.JLabel jLTitle;
    // End of variables declaration//GEN-END:variables
}
