/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package renamer.SRC;

import javax.swing.JFrame;

/**
 *
 * @author baltasarq
 */
public class AppInfo {
    public static final String Author = "baltasarq@yahoo.es";
    public static final String Url = "http://baltasarq.info/dev/";
    public static final String Version = "0.3 Serial 20110802";
    public static final String Name = "FileRenamer";
    public static String AppDesc_EN = "A simple, multiplatform file renamer.";
    public static String AppDesc_ES = "Un pequeño renombrador de ficheros multiplataforma.";
    public static String Help_EN = "A few variables can be used:\n"
                                    + "\tnumber: The number of files renamed. It is inserted at the beginning of the name if missing.\n"
                                    + "\tdate: The date of today, in format yyyy_mm_dd\n"
                                    + "\ttime: The time right now, in format hh_mm_ss\n"
                                    + "\tname: The current name of the file\n"
                                    + "\tnamenoext: The name of the file, without extension\n"
                                    + "Use them in braces, and with a dollar sign before.\n"
                                    + "for example: doc_${number}_${date}_${time}.txt\n"
                                    + "NOTE: Extensions will be kept, unless if included explictely."
    ;
    public static String Help_ES = "Se pueden usar algunas variables:\n"
                                    + "\tnumber: El número de archivos renombrados. Se inserta al principio del nuevo nombre, si fallara.\n"
                                    + "\tdate: La fecha de hoy, en formato yyyy_mm_dd\n"
                                    + "\ttime: La hora al renombrar, en formato hh_mm_ss\n"
                                    + "\tname: El nombre actual del archivo\n"
                                    + "\tnamenoext: El nombre actual del archivo, sin extensión\n"
                                    + "Deben usarse entre llaves, con un signo de dolar delante.\n"
                                    + "por ejemplo: doc_${number}_${date}_${time}.txt\n"
                                    + "NOTA: Las extensiones de los archivos a renombrar se conservan,"
                                    + "a no ser que se incluyan explícitamente."
    ;

    public static final String[] MsgRename = {
        "Rename file:",
        "Renombrar archivo:"
    };

    public static final String[] MsgNoFiles = {
        "<html><i>&lt;no files&gt;</i></html>",
        "<html><i>&lt;no hay archivos&gt;</i></html>"
    };
}
