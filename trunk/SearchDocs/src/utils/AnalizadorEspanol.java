/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author k
 */


import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

/**
 * Esta clase extiende el analizador de Lucene con un fichero de palabras en
 * español.
 *
 * @author Sunav
 *
 */
public class AnalizadorEspanol extends Analyzer {

        /**
         * Analizador estandar.
         */
        private static StandardAnalyzer analyzer;

        /**
         * Localización del fichero de palabras vacias en español.
         */
        final private static String FICHERO_PALABRAS_VACIAS = "ficheros/PalabrasEspanol.txt";

        /**
         * Constructor.
         */
        public AnalizadorEspanol() {
                try {
                        analyzer = new StandardAnalyzer(Version.LUCENE_30, new File(
                                        FICHERO_PALABRAS_VACIAS));
                } catch (IOException e) {
                        e.toString();
                }
        }

        /**
         * Función que devuelve un Token Stream usando el texto a tokenizar y el
         * fichero de palabras vacias.
         */
        public TokenStream tokenStream(String fieldName, Reader reader) {
                TokenStream result;
                result = analyzer.tokenStream(fieldName, reader);
                return result;
        }

}


