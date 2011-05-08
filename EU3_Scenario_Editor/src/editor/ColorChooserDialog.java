
package editor;

import editor.mapmode.DiscreteScalingMapMode;
import eug.parser.EUGFileIO;
import eug.parser.ParserSettings;
import eug.shared.GenericList;
import eug.shared.GenericObject;
import eug.shared.Style;
import java.awt.Color;
import javax.swing.JColorChooser;

/**
 *
 * @author Michael
 */
public class ColorChooserDialog extends javax.swing.JDialog {

    private DiscreteScalingMapMode mapMode;
    private String colorsName;

    private Color origLowColor;
    private Color origMidColor;
    private Color origHighColor;

    /** Creates new form ColorChooserDialog */
    public ColorChooserDialog(EditorUI parent, DiscreteScalingMapMode mapMode) {
        super(parent, true);
        this.mapMode = mapMode;
        this.colorsName = mapMode.getName();
        this.origLowColor = mapMode.getMinColor();
        this.origMidColor = mapMode.getMidColor();
        this.origHighColor = mapMode.getMaxColor();
        setTitle("Adjust " + colorsName + " colors");
        initComponents();
        setLocationRelativeTo(parent);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lowValuePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lowValueColorButton = new javax.swing.JButton();
        midValuePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        midValueColorButton = new javax.swing.JButton();
        highValuePanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        highValueColorButton = new javax.swing.JButton();
        colorScalePanel = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                ((java.awt.Graphics2D)g).setPaint(new java.awt.GradientPaint(new java.awt.Point(0,0), mapMode.getMinColor(), new java.awt.Point(getWidth()/2, 0), mapMode.getMidColor()));
                g.fillRect(0, 0, getWidth()/2, getHeight());

                ((java.awt.Graphics2D)g).setPaint(new java.awt.GradientPaint(new java.awt.Point(getWidth()/2,0), mapMode.getMidColor(), new java.awt.Point(getWidth(), 0), mapMode.getMaxColor()));
                g.fillRect(getWidth()/2, 0, getWidth(), getHeight());
            }
        };
        jPanel3 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setLayout(new java.awt.BorderLayout(5, 5));

        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 5, 5));

        lowValuePanel.setLayout(new java.awt.GridLayout(0, 1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setLabelFor(lowValueColorButton);
        jLabel1.setText("Low values");
        lowValuePanel.add(jLabel1);

        lowValueColorButton.setBackground(origLowColor);
        lowValueColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowValueColorButtonActionPerformed(evt);
            }
        });
        lowValuePanel.add(lowValueColorButton);

        jPanel1.add(lowValuePanel);

        midValuePanel.setLayout(new java.awt.GridLayout(0, 1));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setLabelFor(midValueColorButton);
        jLabel2.setText("Medium values");
        midValuePanel.add(jLabel2);

        midValueColorButton.setBackground(origMidColor);
        midValueColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                midValueColorButtonActionPerformed(evt);
            }
        });
        midValuePanel.add(midValueColorButton);

        jPanel1.add(midValuePanel);

        highValuePanel.setLayout(new java.awt.GridLayout(0, 1));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setLabelFor(highValueColorButton);
        jLabel3.setText("High values");
        highValuePanel.add(jLabel3);

        highValueColorButton.setBackground(origHighColor);
        highValueColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highValueColorButtonActionPerformed(evt);
            }
        });
        highValuePanel.add(highValueColorButton);

        jPanel1.add(highValuePanel);

        jPanel2.add(jPanel1, java.awt.BorderLayout.CENTER);
        jPanel2.add(colorScalePanel, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        jPanel3.add(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel3.add(cancelButton);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lowValueColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowValueColorButtonActionPerformed
        Color ret = JColorChooser.showDialog(this, "Select color", mapMode.getMinColor());
        if (ret != null && !ret.equals(mapMode.getMinColor())) {
            mapMode.setMinColor(ret);
            lowValueColorButton.setBackground(ret);

            ((EditorUI)this.getParent()).repaint();
            this.repaint();
        }
    }//GEN-LAST:event_lowValueColorButtonActionPerformed

    private void midValueColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_midValueColorButtonActionPerformed
        Color ret = JColorChooser.showDialog(this, "Select color", mapMode.getMidColor());
        if (ret != null && !ret.equals(mapMode.getMidColor())) {
            mapMode.setMidColor(ret);
            midValueColorButton.setBackground(ret);

            ((EditorUI)this.getParent()).repaint();
            this.repaint();
        }
    }//GEN-LAST:event_midValueColorButtonActionPerformed

    private void highValueColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highValueColorButtonActionPerformed
        Color ret = JColorChooser.showDialog(this, "Select color", mapMode.getMaxColor());
        if (ret != null && !ret.equals(mapMode.getMaxColor())) {
            mapMode.setMaxColor(ret);
            highValueColorButton.setBackground(ret);

            ((EditorUI)this.getParent()).repaint();
            this.repaint();
        }
    }//GEN-LAST:event_highValueColorButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        GenericObject root = EUGFileIO.load("colors.txt", ParserSettings.getQuietSettings());
        GenericObject colors = root.getChild(colorsName);
        if (colors == null)
            colors = root.createChild(colorsName);
        else
            colors.clear();
        
        GenericList low = colors.createList("low");
        low.add(mapMode.getMinColor().getRed());
        low.add(mapMode.getMinColor().getGreen());
        low.add(mapMode.getMinColor().getBlue());

        GenericList mid = colors.createList("middle");
        mid.add(mapMode.getMidColor().getRed());
        mid.add(mapMode.getMidColor().getGreen());
        mid.add(mapMode.getMidColor().getBlue());

        GenericList max = colors.createList("high");
        max.add(mapMode.getMaxColor().getRed());
        max.add(mapMode.getMaxColor().getGreen());
        max.add(mapMode.getMaxColor().getBlue());

        EUGFileIO.save(root, "colors.txt", EUGFileIO.NO_COMMENT, true, Style.EU3_SAVE_GAME);
        this.dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        mapMode.setMinColor(origLowColor);
        mapMode.setMidColor(origMidColor);
        mapMode.setMaxColor(origHighColor);
        getParent().repaint();
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel colorScalePanel;
    private javax.swing.JButton highValueColorButton;
    private javax.swing.JPanel highValuePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton lowValueColorButton;
    private javax.swing.JPanel lowValuePanel;
    private javax.swing.JButton midValueColorButton;
    private javax.swing.JPanel midValuePanel;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables

}