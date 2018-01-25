/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package renamer.SRC;

import java.io.File;

/**
 *
 * @author baltasarq
 */
public class Model {
     public static final String HtmlTagForDirsBegin = "<html><b>";
     public static final String HtmlTagForDirsEnd   = "</b></html>";
     public static final String DefaultNameFmt      = "_${number}_file";

     /**
     * @return the language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    boolean getRecurse() {
        return this.recurse;
    }

    /**
     * @return the eliminateSpaces
     */
    public boolean isEliminatingSpaces() {
        return eliminateSpaces;
    }

    /**
     * @param eliminateSpaces the eliminateSpaces to set
     */
    public void setEliminateSpaces(boolean eliminateSpaces) {
        this.eliminateSpaces = eliminateSpaces;
    }

    /**
     * @return the substituteSpaces
     */
    public boolean isSubstitutingSpaces() {
        return substituteSpaces;
    }

    /**
     * @param substituteSpaces the substituteSpaces to set
     */
    public void setSubstituteSpaces(boolean substituteSpaces) {
        this.substituteSpaces = substituteSpaces;
    }

    /**
     * @return the forceNumbering
     */
    public boolean isForcingNumbering() {
        return forceNumbering;
    }

    /**
     * @param forceNumbering the forceNumbering to set
     */
    public void setForceNumbering(boolean forceNumbering) {
        this.forceNumbering = forceNumbering;
    }
    public enum Language { EN, ES };

    private File currentDir = null;
    private String ext = "";
    private String prefix = "";
    private String nameFmt = DefaultNameFmt;
    private boolean recurse;
    private boolean eliminateSpaces;
    private boolean substituteSpaces;
    private boolean forceNumbering;
    private int start;
    private Language language;

    /**
     * @return the currentDir
     */
    public File getCurrentDir() {
        return currentDir;
    }

    /**
     * @param currentDir the currentDir to set
     */
    public void setCurrentDir(File currentDir) {
        this.currentDir = currentDir;
    }

    public Model(File f)
    {
        this();
        currentDir = f;
    }

    public Model()
    {
        start = 1;
        forceNumbering = true;
    }

    /**
     * @return the ext
     */
    public String getExt() {
        return ext;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param ext the ext to set
     */
    public void setExt(String ext) {
        this.ext = ext;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the recurse
     */
    public boolean isRecurse() {
        return recurse;
    }

    /**
     * @param recurse the recurse to set
     */
    public void setRecurse(boolean recurse) {
        this.recurse = recurse;
    }

    /**
     * @return the nameFmt
     */
    public String getNameFmt() {
        return nameFmt;
    }

    /**
     * @param nameFmt the nameFmt to set
     */
    public void setNameFmt(String nameFmt) {
        this.nameFmt = nameFmt;
    }

    public static String encodeDirs(String dirName)
    {
        return HtmlTagForDirsBegin + dirName + HtmlTagForDirsEnd;
    }

    public static String decode(String dirName)
    {
        String toret = dirName;

        if ( toret.startsWith( HtmlTagForDirsBegin ) ) {
            toret = toret.substring( HtmlTagForDirsBegin.length() );
        }

        if ( toret.endsWith( HtmlTagForDirsEnd ) ) {
            toret = toret.substring( 0, toret.length() - HtmlTagForDirsEnd.length() );
        }

        return toret;
    }

    public static String encodeFiles(String fileName)
    {
        return fileName;
    }

    public static String decodeFiles(String fileName)
    {
        return fileName;
    }
}
