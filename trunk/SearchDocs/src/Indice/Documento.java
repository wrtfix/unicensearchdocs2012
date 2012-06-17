/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Indice;

/**
 *
 * @author alumno
 */
public class Documento {
private String contenido;
	private String extension;
	private String fecha;
	private String tageo;
	private String path;


	public Documento (String contenido, String extension, String fecha, String tageo){
		this.contenido = contenido;
		this.extension =extension;
		this.fecha = fecha;
		this.tageo=tageo;

	}

	public String getContenido(){
		return this.contenido;
	}
	public String getExtension(){
		return this.extension;
	}
	public String getFecha(){
		return this.fecha;
	}
	public String getTageo(){
		return this.tageo;
	}
	public String getPath(){
		return this.path;
	}
}
