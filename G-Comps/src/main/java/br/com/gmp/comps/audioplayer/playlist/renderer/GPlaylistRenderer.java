package br.com.gmp.comps.audioplayer.playlist.renderer;

import br.com.gmp.comps.audioplayer.playlist.GPlaylistItem;
import br.com.gmp.utils.audio.file.AudioFile;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Renderer de itens de playlists
 *
 * @author kaciano
 */
public class GPlaylistRenderer extends JLabel implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        AudioFile file = (AudioFile) value;
        GPlaylistItem item = new GPlaylistItem(file);        
        if (isSelected) {
            item.setBackground(Color.lightGray);
        }
        return item;
    }

}
