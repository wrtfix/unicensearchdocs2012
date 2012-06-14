/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import searchdocs.SearchDocsView;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 *
 * @author k
 */
public class GenerarColeccionArchivos {

    private static class ContentHandler {

        public ContentHandler() {
       }
    }
    private boolean pdfFiltro=true;
    private boolean txtFiltro=true;
    private boolean docFiltro=true;
    private boolean htmlFiltro=true;
    private List pdfColeccion;
    private List txtColeccion;
    private List docColeccion;
    private List htmlColeccion;
    private List coleccion;
    private DocFilter docFilter;
    private PdfFilter pdfFilter;
    private TxtFilter txtFilter;
    private HtmlFilter htmlFilter;
    public static  boolean NO_FILTRAR=false;
    private File directorioPrincipal;
    private List<Document> documents;


    public GenerarColeccionArchivos(File directorio) {
        docColeccion=new ArrayList();
        pdfColeccion=new ArrayList();
        txtColeccion=new ArrayList();
        htmlColeccion=new ArrayList();
        coleccion=new ArrayList();
        documents=new ArrayList<Document>();
        this.directorioPrincipal=directorio;
        docFilter= new DocFilter();
        txtFilter=new TxtFilter();
        pdfFilter=new PdfFilter();
        htmlFilter=new HtmlFilter();
    }

    public boolean isDocFiltro() {
        return docFiltro;
    }

    public void setDocFiltro(boolean docFiltro) {
        this.docFiltro = docFiltro;
    }

    public boolean isHtmlFiltro() {
        return htmlFiltro;
    }

    public void setHtmlFiltro(boolean htmlFiltro) {
        this.htmlFiltro = htmlFiltro;
    }

    public boolean isPdfFiltro() {
        return pdfFiltro;
    }

    public void setPdfFiltro(boolean pdfFiltro) {
        this.pdfFiltro = pdfFiltro;
    }

    public boolean isTxtFiltro() {
        return txtFiltro;
    }

    public void setTxtFiltro(boolean txtFiltro) {
        this.txtFiltro = txtFiltro;
    }

    public List getDocColeccion() {
        return docColeccion;
    }

    public List getHtmlColeccion() {
        return htmlColeccion;
    }

    public List getPdfColeccion() {
        return pdfColeccion;
    }

    public List getTxtColeccion() {
        return txtColeccion;
    }

    public List getColeccion() {
        return coleccion;
    }

    public void setColeccion(List coleccion) {
        this.coleccion = coleccion;
    }

    

    public List parseToList(File[] files){

        ArrayList list=new ArrayList();
        for (File file : files) {
             list.add(file);
        }
        return list;
    }

    public int generarColeccion() {
        File[] files = directorioPrincipal.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                //Aplicar Filtro
                GenerarColeccionArchivos nuevaColeccion = new GenerarColeccionArchivos(file);
                nuevaColeccion.generarColeccion();
                docColeccion.addAll(nuevaColeccion.getDocColeccion());
                txtColeccion.addAll(nuevaColeccion.getTxtColeccion());
                pdfColeccion.addAll(nuevaColeccion.getPdfColeccion());
                htmlColeccion.addAll(nuevaColeccion.getHtmlColeccion());
            }
        }
        docColeccion.addAll(parseToList(directorioPrincipal.listFiles(docFilter)));
        txtColeccion.addAll(parseToList(directorioPrincipal.listFiles(txtFilter)));
        pdfColeccion.addAll(parseToList(directorioPrincipal.listFiles(pdfFilter)));
        htmlColeccion.addAll(parseToList(directorioPrincipal.listFiles(htmlFilter)));
        coleccion.addAll(docColeccion);
        coleccion.addAll(txtColeccion);
        coleccion.addAll(pdfColeccion);
        coleccion.addAll(htmlColeccion);
        return docColeccion.size() + txtColeccion.size() + pdfColeccion.size() + htmlColeccion.size();

    }
    public void parseColeccionPdf(){
         InputStream input;

        try {
            for (int i = 0; i < pdfColeccion.size(); i++) {
                File file = (File) pdfColeccion.get(i);
                input = new FileInputStream(file);
                BodyContentHandler textHandler = new BodyContentHandler();
                Metadata metadata = new Metadata();
                PDFParser parser = new PDFParser();
                try {
                    try {
                        parser.parse(input, textHandler, metadata);
                    } catch (TikaException ex) {
                        Logger.getLogger(GenerarColeccionArchivos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GenerarColeccionArchivos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(GenerarColeccionArchivos.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(GenerarColeccionArchivos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //System.out.println("Title: " + metadata.get("title"));
                
                //System.out.println("Author: " + metadata.get("Author"));
                
                //System.out.println("Fecha: " + metadata.get(Metadata.CREATION_DATE));
                
                //System.out.println("Extension: " + metadata.get(Metadata.CONTENT_TYPE));
                
                //System.out.println("Path: " + file.getAbsolutePath());
                //System.out.println("<============================================>");
                //System.out.println("Contenido" +textHandler.toString());
                //System.out.println("<============================================>");
                Document doc = new Document();
                
                if (textHandler.toString() != null) {
                    doc.add(new Field("contenido", textHandler.toString(), Field.Store.YES, Field.Index.ANALYZED));
                } else {
                    doc.add(new Field("contenido", "", Field.Store.YES, Field.Index.ANALYZED));
                }

                if (metadata.get(Metadata.CONTENT_TYPE) != null) {
                    doc.add(new Field("extension", metadata.get(Metadata.CONTENT_TYPE), Field.Store.YES, Field.Index.NO));
                } else {
                    doc.add(new Field("extension", "", Field.Store.YES, Field.Index.NO));
                }
                if (metadata.get(Metadata.CREATION_DATE) != null) {
                    doc.add(new Field("fecha", metadata.get(Metadata.CREATION_DATE), Field.Store.YES, Field.Index.NO));
                } else {
                    doc.add(new Field("fecha", "", Field.Store.YES, Field.Index.NO));
                }
                if (file.getAbsolutePath() != null) {
                    doc.add(new Field("path", file.getAbsolutePath(), Field.Store.YES, Field.Index.NO));
                } else {
                    doc.add(new Field("path", "", Field.Store.YES, Field.Index.NO));
                }
                if (metadata.get("title") != null) {
                    doc.add(new Field("title", metadata.get("title"), Field.Store.YES, Field.Index.NO));
                } else {
                    doc.add(new Field("title", "", Field.Store.YES, Field.Index.NO));
                }
                if (metadata.get("author") != null) {
                    doc.add(new Field("author", metadata.get("author"), Field.Store.YES, Field.Index.NO));
                } else {
                    doc.add(new Field("author", "", Field.Store.YES, Field.Index.NO));
                }
                documents.add(doc);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SearchDocsView.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
    
}
