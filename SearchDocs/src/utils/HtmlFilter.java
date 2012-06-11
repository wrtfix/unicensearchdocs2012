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
public class HtmlFilter implements  FileFilter {
    public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith(".html");
    }
    public String getDescription() {
        return "*.html";
    }
    }
