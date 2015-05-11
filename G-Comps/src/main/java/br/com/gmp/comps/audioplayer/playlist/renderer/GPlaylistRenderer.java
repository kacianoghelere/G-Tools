package br.com.gmp.comps.audioplayer.playlist.renderer;

import br.com.gmp.comps.audioplayer.playlist.GPlaylistItem;
import br.com.gmp.utils.audio.file.AudioFile;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * Renderer de itens de playlists
 *
 * @author kaciano
 */
public class GPlaylistRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected, boolean focus) {
        AudioFile file = (AudioFile) value;
        GPlaylistItem item = new GPlaylistItem(file);
        System.out.println("Converteno");
        item.setVisible(true);
        return item;
    }

}
