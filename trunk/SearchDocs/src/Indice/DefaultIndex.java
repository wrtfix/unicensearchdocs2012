/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



package Indice;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

/**
 *  Este codigo es para analizar e indexar documentos
 *
 * @author Wrtfix
 */
public class DefaultIndex {
	private static FSDirectory dir;
	private static Analyzer analyzer;

        public DefaultIndex(String nombre) throws IOException{
            dir = new SimpleFSDirectory(new File(nombre));
	    analyzer = new StandardAnalyzer(Version.LUCENE_30);
        }

        public void agregarDocuments(List<Document> elementos) throws CorruptIndexException, LockObtainFailedException, IOException{
            IndexWriter writer = new IndexWriter(dir, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
            writer.setUseCompoundFile(false);
            for(Document doc: elementos){
                writer.addDocument(doc);
                writer.optimize();
                writer.commit();
            }
            writer.close();

        }
        /**
	 * Este metodo solicita los documentos los criterios de indexacion
	 *
	 * @param analyzer
	 * @param Documents
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 */
	public static void createIndex(Vector<Documento>  Documents) throws CorruptIndexException, LockObtainFailedException, IOException {

		IndexWriter writer = new IndexWriter(dir, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
		writer.setUseCompoundFile(false);

		 // Obtener todos los campos del documento a analizar
		for (Documento documento : Documents)
		{

			   Document doc = new Document();
			   // Esto deberia esta generado a partir de un criterio de busqueda
			   doc.add(new Field("contenido", documento.getContenido(), Field.Store.YES, Field.Index.ANALYZED));
			   doc.add(new Field("extension", documento.getExtension(), Field.Store.YES, Field.Index.NO));
			   doc.add(new Field("fecha", documento.getFecha(), Field.Store.YES, Field.Index.NO));
			   //doc.add(new Field("carpetas", documento.getPath(), Field.Store.YES, Field.Index.NO));
			   writer.addDocument(doc);
			   writer.optimize();
			   writer.commit();

		}

	}

        /**
	 * Muestra el indice
	 *
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	public static void mostrarIndice() throws CorruptIndexException, IOException{
		IndexReader r = IndexReader.open(dir);
		int num = r.numDocs();
		for ( int i = 0; i < num; i++)
		{
		    if (!r.isDeleted(i))
		    {
		        Document d = r.document(i);
		        System.out.println(i+1+". "+d.get("contenido"));
		    }
		}
		r.close();
	}
        /**
	 * Este metodo elimina un documento indexado
	 * @param dir archivo en el que se encuentra el indice
	 * @param term consulta por la cual se considerar la eliminacion de documentos
	 * @throws CorruptIndexException
	 * @throws IOException
	 */

	public static void removerIndice(Term term) throws CorruptIndexException, IOException{
		IndexWriter writer = new IndexWriter(dir, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
		writer.deleteDocuments(term);
		writer.close();

	}

        /**
	 * Busca los documentos que se encuentran indexados
	 * a partir de los siguientes parametros:
	 *
	 * @param dir que seria la direccion donde esta el indice
	 * @param analyzer esto seria la forma de analisis de los documentos
	 * @param args seria el texto que se debe buscar dentro del indice
	 * @param criterio esto serian por que propiedades sera buscado el documento
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException
	 *
	 */

	public static Vector<Document> buscarIndice(TermQuery termino) throws CorruptIndexException, IOException, ParseException{


		//Query q = new QueryParser(Version.LUCENE_30, criterio, analyzer).parse(querystr);
		Query q = termino;
		//QueryParser q = new QueryParser("contenido",new SimpleAnalyser(analyzer));


		int hitsPerPage = 10;
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		// Muestro los resultados obtenidos
		System.out.println("Resultados de la busqueda de "+termino.toString());
		System.out.println("Cantidad de Resultados " + hits.length + ".");
		System.out.println("---------------------------------------------");
		Vector<Document> resultado = new Vector<Document>();
                
                for(int i=0;i<hits.length;++i) {
		    int docId = hits[i].doc;
		    Document d = searcher.doc(docId);
                    resultado.add(d);
		}
                reader.close();
                return resultado;
		

	}

}
