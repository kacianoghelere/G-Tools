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
        jLTitle.setText(title);
        jLLength.setText(length);
        jLInfo.setText(info);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPTrack = new javax.swing.JPanel();
        jLArtwork = new javax.swing.JLabel();
        JPInfo = new javax.swing.JPanel();
        jLInfo = new javax.swing.JLabel();
        jLTitle = new javax.swing.JLabel();
        jLLength = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(32767, 50));
        setMinimumSize(new java.awt.Dimension(265, 50));
        setPreferredSize(new java.awt.Dimension(265, 50));

        jPTrack.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        jPTrack.setOpaque(false);
        jPTrack.setPreferredSize(new java.awt.Dimension(0, 50));
        jPTrack.setLayout(new java.awt.BorderLayout(3, 0));

        jLArtwork.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/gmp/comps/icons/48/audio-file-48.png"))); // NOI18N
        jLArtwork.setMaximumSize(new java.awt.Dimension(50, 50));
        jLArtwork.setMinimumSize(new java.awt.Dimension(50, 50));
        jLArtwork.setPreferredSize(new java.awt.Dimension(50, 50));
        jPTrack.add(jLArtwork, java.awt.BorderLayout.LINE_START);

        JPInfo.setMaximumSize(new java.awt.Dimension(2147483647, 30));
        JPInfo.setOpaque(false);
        JPInfo.setLayout(new java.awt.BorderLayout(1, 1));

        jLInfo.setFont(new java.awt.Font("Ubuntu", 2, 12)); // NOI18N
        jLInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLInfo.setText("Artist - Album");
        JPInfo.add(jLInfo, java.awt.BorderLayout.SOUTH);

        jLTitle.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLTitle.setText("Title");
        JPInfo.add(jLTitle, java.awt.BorderLayout.CENTER);

        jLLength.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLLength.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLLength.setText("00:00");
        JPInfo.add(jLLength, java.awt.BorderLayout.LINE_END);

        jPTrack.add(JPInfo, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPTrack, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPTrack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    private javax.swing.JPanel JPInfo;
    private javax.swing.JLabel jLArtwork;
    private javax.swing.JLabel jLInfo;
    private javax.swing.JLabel jLLength;
    private javax.swing.JLabel jLTitle;
    private javax.swing.JPanel jPTrack;
    // End of variables declaration//GEN-END:variables
}
