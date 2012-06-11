/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author k
 */
class PdfFilter implements  FileFilter {
    public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith(".pdf");
    }
    public String getDescription() {
        return "*.pdf";
    }
    }
