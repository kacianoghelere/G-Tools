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
                file = playlist.getFirst();
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
     * Metodo de parada de reproducao
     */
    private void stop() {
        try {
            this.file = null;
            this.soundPlayer.stop();
            this.playerState = BasicPlayerEvent.STOPPED;
            this.jProgressBar.setValue(0);
            this.jProgressBar.setString("00:00");
            this.jBPlayPause.setIcon(playIcon);
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
        jBStop.setEnabled(playlist != null);
        jBPrev.setEnabled(playlist != null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPTotal = new javax.swing.JPanel();
        jPInfo = new javax.swing.JPanel();
        jPPlayer = new javax.swing.JPanel();
        display = new br.com.gmp.comps.audioplayer.display.GAudioPlayerDisplay();
        jProgressBar = new javax.swing.JProgressBar();
        jPControls = new javax.swing.JPanel();
        jBPrev = new javax.swing.JButton();
        jBPlayPause = new javax.swing.JButton();
        jBStop = new javax.swing.JButton();
        jBNext = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jTBRepeat = new javax.swing.JToggleButton();
        jTBShuffle = new javax.swing.JToggleButton();
        jPGain = new javax.swing.JPanel();
        jLGainMax = new javax.swing.JLabel();
        jLGainMin = new javax.swing.JLabel();
        jSldGain = new javax.swing.JSlider();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
        setMaximumSize(new java.awt.Dimension(32767, 160));
        setMinimumSize(new java.awt.Dimension(420, 160));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(420, 160));
        setRequestFocusEnabled(false);

        jPTotal.setMaximumSize(new java.awt.Dimension(2147483647, 160));
        jPTotal.setMinimumSize(new java.awt.Dimension(420, 160));
        jPTotal.setPreferredSize(new java.awt.Dimension(420, 160));
        jPTotal.setLayout(new java.awt.BorderLayout(2, 0));

        jPInfo.setLayout(new java.awt.BorderLayout());

        jPPlayer.setLayout(new java.awt.BorderLayout());

        display.setBorder(null);
        jPPlayer.add(display, java.awt.BorderLayout.CENTER);

        jProgressBar.setString("00:00");
        jProgressBar.setStringPainted(true);
        jPPlayer.add(jProgressBar, java.awt.BorderLayout.SOUTH);

        jPInfo.add(jPPlayer, java.awt.BorderLayout.CENTER);

        jBPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/24/media-previous.png"))); // NOI18N
        jBPrev.setToolTipText("Anterior");
        jBPrev.setFocusable(false);
        jBPrev.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBPrev.setMaximumSize(new java.awt.Dimension(33, 33));
        jBPrev.setMinimumSize(new java.awt.Dimension(33, 33));
        jBPrev.setPreferredSize(new java.awt.Dimension(33, 33));
        jBPrev.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPrevActionPerformed(evt);
            }
        });
        jPControls.add(jBPrev);

        jBPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/32/media-play.png"))); // NOI18N
        jBPlayPause.setToolTipText("Reproduzir");
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

        jBStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/24/media-stop.png"))); // NOI18N
        jBStop.setToolTipText("Parar");
        jBStop.setFocusable(false);
        jBStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBStop.setMaximumSize(new java.awt.Dimension(33, 33));
        jBStop.setMinimumSize(new java.awt.Dimension(33, 33));
        jBStop.setPreferredSize(new java.awt.Dimension(33, 33));
        jBStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBStopActionPerformed(evt);
            }
        });
        jPControls.add(jBStop);

        jBNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/24/media-next.png"))); // NOI18N
        jBNext.setToolTipText("Próximo");
        jBNext.setFocusable(false);
        jBNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBNext.setMaximumSize(new java.awt.Dimension(33, 33));
        jBNext.setMinimumSize(new java.awt.Dimension(33, 33));
        jBNext.setPreferredSize(new java.awt.Dimension(33, 33));
        jBNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNextActionPerformed(evt);
            }
        });
        jPControls.add(jBNext);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setMaximumSize(new java.awt.Dimension(32767, 20));
        jSeparator2.setMinimumSize(new java.awt.Dimension(2, 20));
        jSeparator2.setPreferredSize(new java.awt.Dimension(2, 20));
        jPControls.add(jSeparator2);

        jTBRepeat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/24/media-repeat.png"))); // NOI18N
        jTBRepeat.setToolTipText("Repetir");
        jTBRepeat.setFocusable(false);
        jTBRepeat.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jTBRepeat.setMaximumSize(new java.awt.Dimension(33, 33));
        jTBRepeat.setMinimumSize(new java.awt.Dimension(33, 33));
        jTBRepeat.setPreferredSize(new java.awt.Dimension(33, 33));
        jTBRepeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBRepeatActionPerformed(evt);
            }
        });
        jPControls.add(jTBRepeat);

        jTBShuffle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/24/media-shuffle.png"))); // NOI18N
        jTBShuffle.setToolTipText("Aleatório");
        jTBShuffle.setFocusable(false);
        jTBShuffle.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jTBShuffle.setMaximumSize(new java.awt.Dimension(33, 33));
        jTBShuffle.setMinimumSize(new java.awt.Dimension(33, 33));
        jTBShuffle.setPreferredSize(new java.awt.Dimension(33, 33));
        jTBShuffle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBShuffleActionPerformed(evt);
            }
        });
        jPControls.add(jTBShuffle);

        jPInfo.add(jPControls, java.awt.BorderLayout.PAGE_END);

        jPTotal.add(jPInfo, java.awt.BorderLayout.CENTER);

        jPGain.setToolTipText("");
        jPGain.setMaximumSize(new java.awt.Dimension(50, 2147483647));
        jPGain.setMinimumSize(new java.awt.Dimension(50, 80));
        jPGain.setPreferredSize(new java.awt.Dimension(50, 0));
        jPGain.setLayout(new java.awt.BorderLayout());

        jLGainMax.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLGainMax.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/24/media-volume-3.png"))); // NOI18N
        jLGainMax.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPGain.add(jLGainMax, java.awt.BorderLayout.PAGE_START);

        jLGainMin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLGainMin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ComponentIcons/multimedia/player/24/media-volume-1.png"))); // NOI18N
        jPGain.add(jLGainMin, java.awt.BorderLayout.PAGE_END);

        jSldGain.setMajorTickSpacing(50);
        jSldGain.setMinorTickSpacing(10);
        jSldGain.setOrientation(javax.swing.JSlider.VERTICAL);
        jSldGain.setPaintTicks(true);
        jSldGain.setToolTipText("Volume");
        jSldGain.setMaximumSize(new java.awt.Dimension(32767, 36));
        jSldGain.setPreferredSize(new java.awt.Dimension(100, 36));
        jSldGain.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSldGainStateChanged(evt);
            }
        });
        jPGain.add(jSldGain, java.awt.BorderLayout.CENTER);

        jPTotal.add(jPGain, java.awt.BorderLayout.LINE_END);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 406, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 146, Short.MAX_VALUE)
                .addGap(6, 6, 6))
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

    private void jBStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBStopActionPerformed
        stop();
    }//GEN-LAST:event_jBStopActionPerformed

    private void jSldGainStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSldGainStateChanged
        changeGain();
    }//GEN-LAST:event_jSldGainStateChanged

    private void jTBRepeatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBRepeatActionPerformed

    }//GEN-LAST:event_jTBRepeatActionPerformed

    private void jBNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNextActionPerformed
        this.file = playlist.getNext();
    }//GEN-LAST:event_jBNextActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.gmp.comps.audioplayer.display.GAudioPlayerDisplay display;
    private javax.swing.JButton jBNext;
    private javax.swing.JButton jBPlayPause;
    private javax.swing.JButton jBPrev;
    private javax.swing.JButton jBStop;
    private javax.swing.JLabel jLGainMax;
    private javax.swing.JLabel jLGainMin;
    private javax.swing.JPanel jPControls;
    private javax.swing.JPanel jPGain;
    private javax.swing.JPanel jPInfo;
    private javax.swing.JPanel jPPlayer;
    private javax.swing.JPanel jPTotal;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSlider jSldGain;
    private javax.swing.JToggleButton jTBRepeat;
    private javax.swing.JToggleButton jTBShuffle;
    // End of variables declaration//GEN-END:variables

}
