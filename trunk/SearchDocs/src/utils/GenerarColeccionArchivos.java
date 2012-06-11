/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author k
 */
public class GenerarColeccionArchivos {
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


    public GenerarColeccionArchivos(File directorio) {
        docColeccion=new ArrayList();
        pdfColeccion=new ArrayList();
        txtColeccion=new ArrayList();
        htmlColeccion=new ArrayList();
        coleccion=new ArrayList();
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

    

    public List ParseToList(File[] files){
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
        docColeccion.addAll(ParseToList(directorioPrincipal.listFiles(docFilter)));
        txtColeccion.addAll(ParseToList(directorioPrincipal.listFiles(txtFilter)));
        pdfColeccion.addAll(ParseToList(directorioPrincipal.listFiles(pdfFilter)));
        htmlColeccion.addAll(ParseToList(directorioPrincipal.listFiles(htmlFilter)));
        coleccion.addAll(docColeccion);
        coleccion.addAll(txtColeccion);
        coleccion.addAll(pdfColeccion);
        coleccion.addAll(htmlColeccion);
        return docColeccion.size() + txtColeccion.size() + pdfColeccion.size() + htmlColeccion.size();
    }
}
