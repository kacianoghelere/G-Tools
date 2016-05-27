package br.com.gmp.comps.selector;

import java.awt.Image;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Seletor de imagens (Por padrão, as imagens devem ser quadradas)
 *
 * @author kaciano
 */
public class GImageSelector extends JPanel {

    private final static Logger logger = Logger.getLogger(GImageSelector.class.getName());
    private String image;
    private int minSize;
    private int maxSize;

    /**
     * Cria nova instancia de GImageSelector
     */
    public GImageSelector() {
        this.minSize = 64;
        this.maxSize = 256;
        initComponents();
    }

    /**
     * Retorna uma instancia da imagem
     *
     * @return {@code ImageIcon} Imagem
     */
    public ImageIcon getImageIcon() {
        ImageIcon icon = new ImageIcon(image);
        return icon;
    }

    /**
     * Retorna uma instancia redimensionada da imagem
     *
     * @param w {@code int} Largura da imagem
     * @param h {@code int} Altura da imagem
     * @return {@code ImageIcon} Imagem
     */
    public ImageIcon getScaledImage(int w, int h) {
        ImageIcon icon = getImageIcon();
        Image img = icon.getImage();
        img = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        icon.setImage(img);
        return icon;
    }

    /**
     * Valida se a imagem é quadrada e respeita o tamanho minimo
     *
     * @param img {@code ImageIcon} Icone da imagem
     * @return {@code boolean} Verificação da imagem
     */
    private boolean validateImage(ImageIcon img) {
        if (img != null) {
            if (img.getIconWidth() != img.getIconHeight()) {
                JOptionPane.showMessageDialog(this, "A imagem deve ser quadrada! (Min.:"
                        + minSize + "x" + minSize + ")");
                return false;
            }
            if (img.getIconWidth() < minSize) {
                JOptionPane.showMessageDialog(this, "A imagem não respeita o limite minimo de tamanho! (Min.:"
                        + minSize + "x" + minSize + ")");
                return false;
            }
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "A imagem não é valida!");
            return false;
        }
    }

    /**
     * Abre o seletor do arquivo da imagem
     *
     * @throws Exception Exceção lançada
     */
    public void open() throws Exception {
        this.jFileChooser.showOpenDialog(this);
        if (this.jFileChooser.getSelectedFile() != null) {
            this.setImage(jFileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * Retorna o caminho da imagem
     *
     * @return {@code String} Caminho da imagem
     */
    public String getImage() {
        return image;
    }

    /**
     * Modifica o caminho da imagem
     *
     * @param image {@code String} Caminho da imagem
     */
    public void setImage(String image) {
        this.image = image;
        ImageIcon icon = getImageIcon();
        if (validateImage(icon)) {
            int size = (icon.getIconWidth() > 128) ? 128 : icon.getIconWidth();
            this.jBImage.setIcon(getScaledImage(size, size));
            logger.log(Level.INFO, "SELECTED IMAGE: {0}", image);
            if (jFileChooser.getSelectedFile() == null) {
                jFileChooser.setSelectedFile(new File(image));
            }
        } else {
            this.image = null;
            this.jFileChooser.setSelectedFile(null);
        }
    }

    /**
     * Retorna o tamanho minimo das imagens
     *
     * @return {@code int} Tamanho minimo das imagens
     */
    public int getMinSize() {
        return minSize;
    }

    /**
     * Modifica o tamanho minimo das imagens
     *
     * @param minSize {@code int} Tamanho minimo das imagens
     */
    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser = new javax.swing.JFileChooser();
        jBImage = new javax.swing.JButton();

        jFileChooser.setApproveButtonText("Selecionar");
        jFileChooser.setApproveButtonToolTipText("Selecionar");
        jFileChooser.setDialogTitle("Selecionar imagem");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos de imagem", "png", "jpeg", "gif"));
        jFileChooser.setToolTipText("");

        setMaximumSize(new java.awt.Dimension(256, 256));
        setMinimumSize(new java.awt.Dimension(64, 64));
        setPreferredSize(new java.awt.Dimension(128, 128));
        setLayout(new java.awt.GridLayout(1, 0));

        jBImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImageActionPerformed(evt);
            }
        });
        add(jBImage);
    }// </editor-fold>//GEN-END:initComponents

    private void jBImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImageActionPerformed
        try {
            this.open();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBImageActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBImage;
    private javax.swing.JFileChooser jFileChooser;
    // End of variables declaration//GEN-END:variables
}
