/*
 * SearchDocsView.java
 */

package searchdocs;


import org.apache.lucene.store.LockObtainFailedException;
import utils.DefaultIndex;
import java.awt.Component;
import java.awt.Desktop;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
;
import utils.GenerarColeccionArchivos;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;

import utils.AnalizadorEspanol;

/**
 * The application's main frame.
 */
public class SearchDocsView extends FrameView {

    private List documentos;
    private Component MainPanel;
    private String nombreIndice;

    public SearchDocsView(SingleFrameApplication app) {
        super(app);
        initComponents();
        jMenuItemAgregarCarpeta.setEnabled(false);
        jMenuItemEliminarCarpeta.setEnabled(false);
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = SearchDocsApp.getApplication().getMainFrame();
            aboutBox = new SearchDocsAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        SearchDocsApp.getApplication().show(aboutBox);
    }

    public DefaultIndex getIndice() {
        return indice;
    }

    public void setIndice(DefaultIndex indice) {
        this.indice = indice;
    }

    public String getNombreIndice() {
        return nombreIndice;
    }

    public void setNombreIndice(String nombreIndice) {
        this.getFrame().setTitle("SearchDocs Aplication - Usando el indice "+nombreIndice);
        jMenuItemAgregarCarpeta.setEnabled(true);
        jMenuItemEliminarCarpeta.setEnabled(true);
        this.nombreIndice = nombreIndice;
    }



    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldTodalasPalabras = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldFraseExacta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldAlgunadeEstasPalabras = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldNingunaEstasPalabras = new javax.swing.JTextField();
        jRadioButtonPDF = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jRadioButtonTXT = new javax.swing.JRadioButton();
        jRadioButtonDOC = new javax.swing.JRadioButton();
        jRadioButtonHTML = new javax.swing.JRadioButton();
        jFormattedTextFieldDesde = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jFormattedTextFieldHasta = new javax.swing.JFormattedTextField();
        jButtonBusquedaAvanzada = new javax.swing.JButton();
        jComboBoxCampos = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldBusquedaPorCampo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItemAgregarCarpeta = new javax.swing.JMenuItem();
        jMenuItemEliminarCarpeta = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(searchdocs.SearchDocsApp.class).getContext().getResourceMap(SearchDocsView.class);
        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jPanel2.setBackground(resourceMap.getColor("jPanel2.background")); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jList1.setName("jList1"); // NOI18N
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 974, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextFieldTodalasPalabras.setText(resourceMap.getString("jTextFieldTodalasPalabras.text")); // NOI18N
        jTextFieldTodalasPalabras.setName("jTextFieldTodalasPalabras"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldFraseExacta.setName("jTextFieldFraseExacta"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextFieldAlgunadeEstasPalabras.setName("jTextFieldAlgunadeEstasPalabras"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldNingunaEstasPalabras.setName("jTextFieldNingunaEstasPalabras"); // NOI18N

        jRadioButtonPDF.setText(resourceMap.getString("jRadioButtonPDF.text")); // NOI18N
        jRadioButtonPDF.setName("jRadioButtonPDF"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jRadioButtonTXT.setText(resourceMap.getString("jRadioButtonTXT.text")); // NOI18N
        jRadioButtonTXT.setName("jRadioButtonTXT"); // NOI18N

        jRadioButtonDOC.setText(resourceMap.getString("jRadioButtonDOC.text")); // NOI18N
        jRadioButtonDOC.setName("jRadioButtonDOC"); // NOI18N

        jRadioButtonHTML.setText(resourceMap.getString("jRadioButtonHTML.text")); // NOI18N
        jRadioButtonHTML.setName("jRadioButtonHTML"); // NOI18N

        jFormattedTextFieldDesde.setText(resourceMap.getString("jFormattedTextFieldDesde.text")); // NOI18N
        jFormattedTextFieldDesde.setName("jFormattedTextFieldDesde"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jFormattedTextFieldHasta.setName("jFormattedTextFieldHasta"); // NOI18N

        jButtonBusquedaAvanzada.setText(resourceMap.getString("jButtonBusquedaAvanzada.text")); // NOI18N
        jButtonBusquedaAvanzada.setName("jButtonBusquedaAvanzada"); // NOI18N
        jButtonBusquedaAvanzada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBusquedaAvanzadaActionPerformed(evt);
            }
        });

        jComboBoxCampos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "titulo", "autor", "contenido", "fecha Creacion", "extension", "path", " ", " " }));
        jComboBoxCampos.setName("jComboBoxCampos"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jTextFieldBusquedaPorCampo.setName("jTextFieldBusquedaPorCampo"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap(826, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addGap(186, 186, 186)
                                .addComponent(jTextFieldBusquedaPorCampo, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                                    .addComponent(jTextFieldTodalasPalabras, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                                    .addComponent(jTextFieldFraseExacta, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                                    .addComponent(jTextFieldNingunaEstasPalabras, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                                    .addComponent(jTextFieldAlgunadeEstasPalabras, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70)
                                .addComponent(jLabel9))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonPDF)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonTXT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonDOC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonHTML)
                                .addGap(157, 157, 157))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addComponent(jComboBoxCampos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                                .addComponent(jButtonBusquedaAvanzada)
                                .addGap(104, 104, 104))))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldTodalasPalabras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jRadioButtonPDF)
                    .addComponent(jRadioButtonTXT)
                    .addComponent(jRadioButtonDOC)
                    .addComponent(jRadioButtonHTML))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldFraseExacta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextFieldDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jFormattedTextFieldHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(10, 10, 10)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldAlgunadeEstasPalabras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldNingunaEstasPalabras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51))
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonBusquedaAvanzada)
                        .addComponent(jComboBoxCampos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(jTextFieldBusquedaPorCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseClicked(evt);
            }
        });
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem1);

        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem2);

        jMenuItemAgregarCarpeta.setText(resourceMap.getString("jMenuItemAgregarCarpeta.text")); // NOI18N
        jMenuItemAgregarCarpeta.setName("jMenuItemAgregarCarpeta"); // NOI18N
        jMenuItemAgregarCarpeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAgregarCarpetaActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemAgregarCarpeta);

        jMenuItemEliminarCarpeta.setText(resourceMap.getString("jMenuItemEliminarCarpeta.text")); // NOI18N
        jMenuItemEliminarCarpeta.setName("jMenuItemEliminarCarpeta"); // NOI18N
        jMenuItemEliminarCarpeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEliminarCarpetaActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemEliminarCarpeta);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(searchdocs.SearchDocsApp.class).getContext().getActionMap(SearchDocsView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setBackground(resourceMap.getColor("statusPanel.background")); // NOI18N
        statusPanel.setName("statusPanel"); // NOI18N
        statusPanel.setPreferredSize(new java.awt.Dimension(1022, 35));

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                        .addComponent(statusMessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 676, Short.MAX_VALUE)
                        .addComponent(statusAnimationLabel)
                        .addContainerGap())
                    .addComponent(progressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusAnimationLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents


    private void ejecutarElemnto(String ruta){
        statusMessageLabel.setText("Se va a ejecutar el archivo: "+ruta);
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.BROWSE))
                    {
                        desktop.open(new File(ruta));
                    }
                }
            } catch (Exception e) {
            e.printStackTrace();
            }
    }

    private void listarResultado(Vector<Document> resultado){

        DefaultListModel listModel = new DefaultListModel();
            statusMessageLabel.setText("De su busqueda se encontraron " + resultado.size() + " elementos");
            for (Document doc : resultado) {
                //jTextArea1.append(doc.get("path") + "\n");
                listModel.addElement(doc.get("path"));

            }
            jList1.setModel(listModel);
    }
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        try {
            TermQuery termino = new TermQuery(new Term("contenido", jTextField1.getText().toString()));
            // Para abrir los documentos encontrados

            Vector<Document> resultado = indice.buscarIndice(termino);
            this.listarResultado(resultado);
        } catch (CorruptIndexException ex) {
            Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
        }

      
        
    }//GEN-LAST:event_jButton1MouseClicked

    private void jMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseClicked
        // TODO add your handling code here:
        if (fileBox== null) {
            fileBox = new JFileChooser();
            fileBox.showOpenDialog(MainPanel);
        }
        
    }//GEN-LAST:event_jMenuItem1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // Abre el box de nuevo indice
        JFrame mainFrame = SearchDocsApp.getApplication().getMainFrame();
        nuevoBox = new SearchDocsNuevoIndiceBox(mainFrame, this);
        nuevoBox.setLocationRelativeTo(mainFrame);
        nuevoBox.setTitle("Nuevo indice");
        SearchDocsApp.getApplication().show(nuevoBox);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        this.ejecutarElemnto(jList1.getSelectedValue().toString());
    }//GEN-LAST:event_jList1ValueChanged

    private void jMenuItemAgregarCarpetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAgregarCarpetaActionPerformed
        try {
            // Agergar carpeta al indice ya seleccionado
            fileBox = new JFileChooser();
            fileBox.setDialogTitle("Seleccionar la carpeta a agregar al indice");
            fileBox.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileBox.setApproveButtonText("Agregar Directorio");
            statusMessageLabel.setText("Agregando carpeta en el indice...");
            fileBox.showOpenDialog(MainPanel);
            GenerarColeccionArchivos coleccion = new GenerarColeccionArchivos(fileBox.getSelectedFile());
            coleccion.generarColeccion();
            documentos = coleccion.parseColeccion();
            indice.agregarDocuments(documentos);
            statusMessageLabel.setText("Carpeta agregada");
            
        } catch (CorruptIndexException ex) {
            Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LockObtainFailedException ex) {
            Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemAgregarCarpetaActionPerformed

    private void jMenuItemEliminarCarpetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEliminarCarpetaActionPerformed
        // Eliminar una carpeta del indice
            fileBox=new JFileChooser();
            fileBox.setDialogTitle("Seleccionar la carpeta a eliminar del indice");
            fileBox.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileBox.setApproveButtonText("Eliminar Directorio");
            statusMessageLabel.setText("Eliminar carpeta en el indice...");
            fileBox.showOpenDialog(MainPanel);
            Term term;
            File file = fileBox.getSelectedFile();
            GenerarColeccionArchivos coleccionFiles= new GenerarColeccionArchivos(file);
            coleccionFiles.generarColeccion();
            List files=coleccionFiles.getColeccion();
            for(int i=0;i<files.size();i++){
            try {
                term = new Term("path", ((File) files.get(i)).getAbsolutePath());
                indice.eliminarCarpeta(term);
            } catch (CorruptIndexException ex) {
                Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            statusMessageLabel.setText("Se elimino con exito!");

    }//GEN-LAST:event_jMenuItemEliminarCarpetaActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            // Abrir indice existente
            fileBox = new JFileChooser();
            fileBox.setDialogTitle("Seleccionar un indice ya existente");
            fileBox.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileBox.setApproveButtonText("Abrir Indice");
            statusMessageLabel.setText("Abriendo indice ya existente...");
            fileBox.showOpenDialog(MainPanel);
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
            //Analyzer analyzer = new AnalizadorEspanol();
            //Analyzer analyzer = new SimpleAnalyzer();
            indice = new DefaultIndex(analyzer);
            indice.abrirIndice(fileBox.getSelectedFile());
            statusMessageLabel.setText("Indice abierto exitosamente");
            this.setNombreIndice(fileBox.getSelectedFile().getName());
        } catch (IOException ex) {
            Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
            statusMessageLabel.setText("No se pudo abrir el indice");
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed


    private String getTodas(){
        String qstr = jTextFieldTodalasPalabras.getText().replace(" ", " AND ");
        System.out.println(qstr);
        return qstr;
    }
    private String getExacta(){
        String qstr = "\"";
        qstr.concat(jTextFieldTodalasPalabras.getText());
        qstr.concat ("\"");
        System.out.println(qstr);
        return qstr;
    }


    private String getAlgunas(){
        String qstr = jTextFieldAlgunadeEstasPalabras.getText().replace(" ", " OR ");
        System.out.println(qstr);
        return qstr;
    }


    private void jButtonBusquedaAvanzadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBusquedaAvanzadaActionPerformed
        try {
            // This project search documents whith diferent criteries
             TermQuery termino = null;
            if (jTextFieldTodalasPalabras.getText() != null) {
                termino =  new TermQuery(new Term("contenido", this.getTodas()));
                //termino = new TermQuery(new Term("contenido", jTextFieldTodalasPalabras.getText().toString()));
            }
            if (jTextFieldFraseExacta.getText() != null) {
                termino =  new TermQuery(new Term("contenido", this.getExacta()));
            }

            if (jTextFieldAlgunadeEstasPalabras.getText() != null)
            {
                termino = new TermQuery(new Term("contenido", this.getAlgunas()));
            }
            if (jTextFieldNingunaEstasPalabras.getText() != null)
            {
                termino = new TermQuery(new Term("contenido", this.getAlgunas()));
            }


            Vector<Document> resultado = indice.buscarIndice(termino);
            this.listarResultado(resultado);
            
        } catch (CorruptIndexException ex) {
            Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonBusquedaAvanzadaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonBusquedaAvanzada;
    private javax.swing.JComboBox jComboBoxCampos;
    private javax.swing.JFormattedTextField jFormattedTextFieldDesde;
    private javax.swing.JFormattedTextField jFormattedTextFieldHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItemAgregarCarpeta;
    private javax.swing.JMenuItem jMenuItemEliminarCarpeta;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButtonDOC;
    private javax.swing.JRadioButton jRadioButtonHTML;
    private javax.swing.JRadioButton jRadioButtonPDF;
    private javax.swing.JRadioButton jRadioButtonTXT;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextFieldAlgunadeEstasPalabras;
    private javax.swing.JTextField jTextFieldBusquedaPorCampo;
    private javax.swing.JTextField jTextFieldFraseExacta;
    private javax.swing.JTextField jTextFieldNingunaEstasPalabras;
    private javax.swing.JTextField jTextFieldTodalasPalabras;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private DefaultIndex indice;
    private JDialog aboutBox;
    private JFileChooser fileBox;
    private JDialog nuevoBox;
}
