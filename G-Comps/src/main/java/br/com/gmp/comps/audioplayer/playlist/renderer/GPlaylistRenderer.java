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
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean focus) {
        AudioFile file = (AudioFile) value;
        GPlaylistItem item = new GPlaylistItem(file);
        item.setBackground(selected ? Color.lightGray : item.getBackground());
        item.setForeground(file.isExecuting() ? Color.GREEN.darker() : item.getBackground());
        return item;
    }

}
