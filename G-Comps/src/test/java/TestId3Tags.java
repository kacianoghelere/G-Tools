
import br.com.gmp.utils.image.ImageUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import junit.framework.TestCase;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.audio.mp3.MP3FileReader;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;

/**
 *
 * @author kaciano
 */
public class TestId3Tags extends TestCase {

    public TestId3Tags(String testName) {
        super(testName);
        testHello();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testHello() {
        try {
            MP3FileReader reader = new MP3FileReader();
            URL stream = getClass().getResource("/audio/test.mp3");
            MP3File mp3 = new MP3File(stream.getFile());
            ID3v1Tag v1Tag = mp3.getID3v1Tag();
            System.out.println(v1Tag.getFirstTitle());
            AbstractID3v2Tag v2Tag = mp3.getID3v2Tag();
            Artwork artwork = v2Tag.getFirstArtwork();
            BufferedImage image = artwork.getImage();
            System.out.println(image.toString());            
            MP3AudioHeader header = mp3.getMP3AudioHeader();
            System.out.println(header.getFormat());
            ImageUtil imageUtil = new ImageUtil();
            
//            JFrame frame = new JFrame("Titulo");
//            frame.setSize(200, 200);
//            frame.setIconImage(image);
//            frame.setVisible(true);                       
        } catch (IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            Logger.getLogger(TestId3Tags.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
