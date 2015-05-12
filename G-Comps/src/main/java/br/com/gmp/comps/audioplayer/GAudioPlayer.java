package br.com.gmp.comps.audioplayer;

import br.com.gmp.basicplayer.BasicController;
import br.com.gmp.basicplayer.BasicPlayer;
import br.com.gmp.basicplayer.BasicPlayerEvent;
import br.com.gmp.basicplayer.BasicPlayerException;
import br.com.gmp.basicplayer.BasicPlayerListener;
import br.com.gmp.comps.audioplayer.playlist.GPlaylist;
import br.com.gmp.utils.audio.file.AudioFile;
import br.com.gmp.utils.image.ImageUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author kaciano
 */
public class GAudioPlayer extends JPanel implements BasicPlayerListener {

    private static final Logger LOGGER = Logger.getLogger(GAudioPlayer.class.getName());
    private final ImageIcon playIcon = new ImageUtil().getResource("/ComponentIcons/multimedia/player/32/media-play.png");
    private final ImageIcon pauseIcon = new ImageUtil().getResource("/ComponentIcons/multimedia/player/32/media-pause.png");
    /*-- Sound player --*/
    private BasicController soundPlayer = null;
    private Map audioInfo = null;
    private int playerState = BasicPlayerEvent.OPENING;
    private GPlaylist playlist;
    /*-- Sound player --*/
    private AudioFile file;

    /**
     * Creates new form GPlayerUI
     */
    public GAudioPlayer() {
        initialize();
    }

    /**
     * Metodo de inicializacao
     */
    private void initialize() {
        initComponents();
        setPlaylist(null);
    }

    @Override
    public void opened(Object stream, Map properties) {
        this.audioInfo = properties;
        this.jProgressBar.setMinimum(0);
        this.jProgressBar.setMaximum(getTimeLengthEstimation(audioInfo).intValue() / 1000);
    }

    @Override
    public void progress(int bytesread, long micros, byte[] pcmdata, Map properties) {
        long total = (long) Math.round(getTimeLengthEstimation(audioInfo) / 1000);
        Long secondsAmount = 0l;
        int byteslength = -1;
        if (audioInfo.containsKey("audio.length.bytes")) {
            byteslength = ((Integer) audioInfo.get("audio.length.bytes"));
        }
        float progress = -1.0f;
        if ((bytesread > 0) && ((byteslength > 0))) {
            progress = bytesread * 1.0f / byteslength * 1.0f;
        }
        if (total > 0) {
            secondsAmount = (long) (total * progress);
        } else {
            secondsAmount = -1l;
        }

        long time = secondsAmount * 1000;
        long minutes = time / (60 * 1000);
        long seconds = (time / 1000) % 60;
        String str = String.format("%02d:%02d", minutes, seconds);
        jProgressBar.setValue(secondsAmount.intValue());
        jProgressBar.setString(str);
    }

    @Override
    public void stateUpdated(BasicPlayerEvent event) {
        playerState = event.getCode();
        LOGGER.log(Level.INFO, "STATE CHANGED: {0}", event.getCode());
    }

    @Override
    public void setController(BasicController soundPlayer) {
        this.soundPlayer = soundPlayer;
    }

    /**
     * Retorna um arquivo a partir de um nome
     *
     * @param file {@code String} Nome do arquivo
     * @return {@code File} Arquivo
     */
    protected File openFile(String file) {
        return new File(file);
    }

    /**
     * Acao padrao do botao de reproducao/pausa
     */
    private void playToggle() {
        try {
            if (file == null && playlist != null && playlist.getPlaylistModel().getSize() > 0) {
                file = playlist.getNext();
            }
            if (file != null) {
                if (this.playerState == BasicPlayerEvent.OPENING || this.playerState == BasicPlayerEvent.STOPPED) {
                    if (soundPlayer == null) {
                        this.soundPlayer = new BasicPlayer();
                        ((BasicPlayer) soundPlayer).addBasicPlayerListener(this);
                    }
                    this.soundPlayer.open(openFile(file.getPath()));
                    this.soundPlayer.play();
                    this.display.setText(file);
                    this.playerState = BasicPlayerEvent.PLAYING;
                    this.jBPlayPause.setIcon(pauseIcon);
                } else if (this.playerState == BasicPlayerEvent.PLAYING || this.playerState == BasicPlayerEvent.RESUMED) {
                    this.soundPlayer.pause();
                    this.playerState = BasicPlayerEvent.PAUSED;
                    this.jBPlayPause.setIcon(playIcon);
                } else if (this.playerState == BasicPlayerEvent.PAUSED) {
                    this.soundPlayer.resume();
                    this.playerState = BasicPlayerEvent.RESUMED;
                    this.jBPlayPause.setIcon(pauseIcon);
                }
                this.jBPlayPause.repaint();
                this.jBPlayPause.revalidate();
                SwingUtilities.updateComponentTreeUI(jBPlayPause);
            }
        } catch (BasicPlayerException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Computa o tempo em milisegundos
     *
     * @param properties {@code Map} Mapa de propriedades
     * @return {@code long} Tempo estimado em milisegundos
     */
    private Long getTimeLengthEstimation(Map properties) {
        Long milliseconds = -1l;
        int byteslength = -1;
        if (properties != null) {
            if (properties.containsKey("audio.length.bytes")) {
                byteslength = ((Integer) properties.get("audio.length.bytes"));
            }
            if (properties.containsKey("duration")) {
                milliseconds = (long) (((Long) properties.get("duration"))) / 1000;
            } else {
                // Try to compute duration
                int bitspersample = -1;
                int channels = -1;
                float samplerate = -1.0f;
                int framesize = -1;
                if (properties.containsKey("audio.samplesize.bits")) {
                    bitspersample = ((Integer) properties.get("audio.samplesize.bits"));
                }
                if (properties.containsKey("audio.channels")) {
                    channels = ((Integer) properties.get("audio.channels"));
                }
                if (properties.containsKey("audio.samplerate.hz")) {
                    samplerate = ((Float) properties.get("audio.samplerate.hz"));
                }
                if (properties.containsKey("audio.framesize.bytes")) {
                    framesize = ((Integer) properties.get("audio.framesize.bytes"));
                }
                if (bitspersample > 0) {
                    milliseconds = (long) (1000.0f * byteslength / (samplerate * channels * (bitspersample / 8)));
                } else {
                    milliseconds = (long) (1000.0f * byteslength / (samplerate * framesize));
                }
            }
        }
        return milliseconds;
    }

    /**
     * Modifica o volume conforme o valor atribuido no JSlider
     */
    private void changeGain() {
        try {
            if (soundPlayer != null) {
                soundPlayer.setGain(jSldGain.getValue() / 100d);
            }
        } catch (BasicPlayerException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retorna o arquivo de audio
     *
     * @return {@code AudioFile} Arquivo de audio
     */
    public AudioFile getFile() {
        return file;
    }

    /**
     * Modifica o arquivo de audio
     *
     * @param file {@code AudioFile} Arquivo de audio
     */
    public void setFile(AudioFile file) {
        this.file = file;
    }

    /**
     * Retorna a lista de reproducao do player
     *
     * @return {@code GPlaylist} Lista de reproducao
     */
    public GPlaylist getPlaylist() {
        return playlist;
    }

    /**
     * Modifica a lista de reproducao do player (tambem libera/bloqueia os
     * botoes de navegacao)
     *
     * @param playlist {@code GPlaylist} Lista de reproducao
     */
    public void setPlaylist(GPlaylist playlist) {
        this.playlist = playlist;
        jBNext.setEnabled(playlist != null);
        jBPrev.setEnabled(playlist != null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPPlayer = new javax.swing.JPanel();
        display = new br.com.gmp.comps.audioplayer.display.GAudioPlayerDisplay();
        jPControls = new javax.swing.JPanel();
        jBPrev = new javax.swing.JButton();
        jBPlayPause = new javax.swing.JButton();
        jBNext = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jPTrack = new javax.swing.JPanel();
        jProgressBar = new javax.swing.JProgressBar();
        jTBRepeat = new javax.swing.JToggleButton();
        jTBShuffle = new javax.swing.JToggleButton();
        jLGainMin = new javax.swing.JLabel();
        jSldGain = new javax.swing.JSlider();
        jLGainMax = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
        setMaximumSize(new java.awt.Dimension(32767, 100));
        setMinimumSize(new java.awt.Dimension(420, 110));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(420, 110));
        setRequestFocusEnabled(false);

        display.setBorder(null);

        jBPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/24/media-previous.png"))); // NOI18N
        jBPrev.setFocusable(false);
        jBPrev.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBPrev.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPrevActionPerformed(evt);
            }
        });
        jPControls.add(jBPrev);

        jBPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/32/media-play.png"))); // NOI18N
        jBPlayPause.setFocusable(false);
        jBPlayPause.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBPlayPause.setMaximumSize(new java.awt.Dimension(43, 43));
        jBPlayPause.setMinimumSize(new java.awt.Dimension(43, 43));
        jBPlayPause.setPreferredSize(new java.awt.Dimension(43, 43));
        jBPlayPause.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBPlayPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPlayPauseActionPerformed(evt);
            }
        });
        jPControls.add(jBPlayPause);

        jBNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/24/media-next.png"))); // NOI18N
        jBNext.setFocusable(false);
        jBNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNextActionPerformed(evt);
            }
        });
        jPControls.add(jBNext);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPPlayerLayout = new javax.swing.GroupLayout(jPPlayer);
        jPPlayer.setLayout(jPPlayerLayout);
        jPPlayerLayout.setHorizontalGroup(
            jPPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPlayerLayout.createSequentialGroup()
                .addComponent(display, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPControls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPPlayerLayout.setVerticalGroup(
            jPPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPlayerLayout.createSequentialGroup()
                .addGroup(jPPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPControls, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(display, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPPlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2)
                .addContainerGap())
        );

        jPTrack.setMaximumSize(new java.awt.Dimension(32768, 40));
        jPTrack.setMinimumSize(new java.awt.Dimension(418, 40));
        jPTrack.setPreferredSize(new java.awt.Dimension(418, 40));

        jProgressBar.setString("00:00");
        jProgressBar.setStringPainted(true);

        jTBRepeat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/16/media-repeat.png"))); // NOI18N
        jTBRepeat.setFocusable(false);
        jTBRepeat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTBRepeat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jTBRepeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBRepeatActionPerformed(evt);
            }
        });

        jTBShuffle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/16/media-shuffle.png"))); // NOI18N
        jTBShuffle.setFocusable(false);
        jTBShuffle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTBShuffle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jTBShuffle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBShuffleActionPerformed(evt);
            }
        });

        jLGainMin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/24/media-volume-1.png"))); // NOI18N

        jSldGain.setMajorTickSpacing(50);
        jSldGain.setMinorTickSpacing(10);
        jSldGain.setPaintTicks(true);
        jSldGain.setMaximumSize(new java.awt.Dimension(32767, 40));
        jSldGain.setPreferredSize(new java.awt.Dimension(100, 40));
        jSldGain.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSldGainStateChanged(evt);
            }
        });

        jLGainMax.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/24/media-volume-3.png"))); // NOI18N

        javax.swing.GroupLayout jPTrackLayout = new javax.swing.GroupLayout(jPTrack);
        jPTrack.setLayout(jPTrackLayout);
        jPTrackLayout.setHorizontalGroup(
            jPTrackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTrackLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTBRepeat)
                .addGap(0, 0, 0)
                .addComponent(jTBShuffle)
                .addGap(12, 12, 12)
                .addComponent(jLGainMin)
                .addGap(0, 0, 0)
                .addComponent(jSldGain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLGainMax)
                .addGap(0, 0, 0))
        );
        jPTrackLayout.setVerticalGroup(
            jPTrackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPTrackLayout.createSequentialGroup()
                .addGroup(jPTrackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPTrackLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPTrackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTBShuffle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTBRepeat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jProgressBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPTrackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLGainMin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLGainMax, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSldGain, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPPlayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addComponent(jPTrack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPTrack, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBPlayPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPlayPauseActionPerformed
        playToggle();
    }//GEN-LAST:event_jBPlayPauseActionPerformed

    private void jTBShuffleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBShuffleActionPerformed
        this.file = playlist.getShuffle();
    }//GEN-LAST:event_jTBShuffleActionPerformed

    private void jBPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPrevActionPerformed
        this.file = playlist.getPrevious();
    }//GEN-LAST:event_jBPrevActionPerformed

    private void jBNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNextActionPerformed
        this.file = playlist.getNext();
    }//GEN-LAST:event_jBNextActionPerformed

    private void jSldGainStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSldGainStateChanged
        changeGain();
    }//GEN-LAST:event_jSldGainStateChanged

    private void jTBRepeatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBRepeatActionPerformed

    }//GEN-LAST:event_jTBRepeatActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.gmp.comps.audioplayer.display.GAudioPlayerDisplay display;
    private javax.swing.JButton jBNext;
    private javax.swing.JButton jBPlayPause;
    private javax.swing.JButton jBPrev;
    private javax.swing.JLabel jLGainMax;
    private javax.swing.JLabel jLGainMin;
    private javax.swing.JPanel jPControls;
    private javax.swing.JPanel jPPlayer;
    private javax.swing.JPanel jPTrack;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSlider jSldGain;
    private javax.swing.JToggleButton jTBRepeat;
    private javax.swing.JToggleButton jTBShuffle;
    // End of variables declaration//GEN-END:variables

}
