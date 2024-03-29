

package searchdocs;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.jdesktop.application.Action;
import utils.AnalizadorEspanol;
import utils.DefaultIndex;
import utils.GenerarColeccionArchivos;

public class SearchDocsNuevoIndiceBox extends javax.swing.JDialog {
    private SearchDocsView vistaPrincipal;

    public SearchDocsNuevoIndiceBox(java.awt.Frame parent, SearchDocsView vistaPrincipal) {
        super(parent);
        initComponents();
        getRootPane().setDefaultButton(closeButton);
        jButtonAceptar.setEnabled(false);
        this.vistaPrincipal=vistaPrincipal;
    }

    @Action public void closeAboutBox() {
        dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closeButton = new javax.swing.JButton();
        javax.swing.JLabel appTitleLabel = new javax.swing.JLabel();
        jButtonAceptar = new javax.swing.JButton();
        jTextFieldNombre = new javax.swing.JTextField();
        javax.swing.JLabel vendorLabel = new javax.swing.JLabel();
        javax.swing.JLabel vendorLabel1 = new javax.swing.JLabel();
        jTextFieldCarpetaPath = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(searchdocs.SearchDocsApp.class).getContext().getResourceMap(SearchDocsNuevoIndiceBox.class);
        setTitle(resourceMap.getString("title")); // NOI18N
        setModal(true);
        setName("aboutBox"); // NOI18N
        setResizable(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(searchdocs.SearchDocsApp.class).getContext().getActionMap(SearchDocsNuevoIndiceBox.class, this);
        closeButton.setAction(actionMap.get("closeAboutBox")); // NOI18N
        closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getStyle() | java.awt.Font.BOLD, appTitleLabel.getFont().getSize()+4));
        appTitleLabel.setText(resourceMap.getString("Application.title")); // NOI18N
        appTitleLabel.setName("appTitleLabel"); // NOI18N

        jButtonAceptar.setText(resourceMap.getString("jButtonAceptar.text")); // NOI18N
        jButtonAceptar.setName("jButtonAceptar"); // NOI18N
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });

        jTextFieldNombre.setText(resourceMap.getString("jTextFieldNombre.text")); // NOI18N
        jTextFieldNombre.setToolTipText(resourceMap.getString("jTextFieldNombre.toolTipText")); // NOI18N
        jTextFieldNombre.setName("jTextFieldNombre"); // NOI18N

        vendorLabel.setFont(vendorLabel.getFont().deriveFont(vendorLabel.getFont().getStyle() | java.awt.Font.BOLD));
        vendorLabel.setText(resourceMap.getString("vendorLabel.text")); // NOI18N
        vendorLabel.setName("vendorLabel"); // NOI18N

        vendorLabel1.setFont(vendorLabel1.getFont().deriveFont(vendorLabel1.getFont().getStyle() | java.awt.Font.BOLD));
        vendorLabel1.setText(resourceMap.getString("vendorLabel1.text")); // NOI18N
        vendorLabel1.setName("vendorLabel1"); // NOI18N

        jTextFieldCarpetaPath.setEditable(false);
        jTextFieldCarpetaPath.setName("jTextFieldCarpetaPath"); // NOI18N

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(closeButton)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(vendorLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(appTitleLabel)
                        .addContainerGap(449, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(vendorLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldCarpetaPath, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(31, 31, 31))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(appTitleLabel)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vendorLabel)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vendorLabel1)
                    .addComponent(jTextFieldCarpetaPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(jButtonAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (fileBox== null) {
            fileBox=new JFileChooser();
            fileBox.setDialogTitle("Seleccionar la carpeta a adjuntar");
            fileBox.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileBox.setApproveButtonText("Seleccionar Directorio");
            fileBox.showOpenDialog(this);
            //FileUtils.selectFiles(fileBox.getSelectedFile());
            GenerarColeccionArchivos coleccion=new GenerarColeccionArchivos(fileBox.getSelectedFile());
            coleccion.generarColeccion();
            documentos=coleccion.parseColeccion();
            /*for (int i = 0; i < documentos.size(); i++) {
                Document doc = (Document) documentos.get(i);
                System.out.println(i+"<============================================>");
                System.out.println(doc.get("title"));
                System.out.println(doc.get("path"));
                System.out.println(doc.get("fecha"));
                System.out.println(doc.get("extension"));
                System.out.println(doc.get("author"));
                System.out.println(doc.get("fechaModificacion"));
                System.out.println("<============================================>");
                System.out.println(doc.get("contenido"));
                System.out.println("<============================================>");
            }*/
            jTextFieldCarpetaPath.setText(fileBox.getSelectedFile().getPath());
            jButtonAceptar.setEnabled(true);


        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed
        // Crear un nuevo indice
         try {
            //Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
              Analyzer analyzer = new AnalizadorEspanol();
            //Analyzer analyzer = new SimpleAnalyzer();
            DefaultIndex indice = new DefaultIndex(analyzer);
            indice.nuevoIndice(jTextFieldNombre.getText());
            //System.out.println(jTextFieldNombre.getText());
            indice.agregarDocuments(documentos);
            vistaPrincipal.setIndice(indice);
            vistaPrincipal.setNombreIndice(jTextFieldNombre.getText());
            dispose();

        } catch (IOException ex) {
            Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonAceptarActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JTextField jTextFieldCarpetaPath;
    private javax.swing.JTextField jTextFieldNombre;
    // End of variables declaration//GEN-END:variables
    
    private JFileChooser fileBox;
    private List documentos;
}
