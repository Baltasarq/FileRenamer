/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package renamer.SRC;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Iterates over a directory, recursing it if needed
 * @author baltasarq
 */
public class RenamingEngine {

    public static void sysDeleteFile(File f) throws IOException {
        if ( !f.delete() ) {
            throw new IOException(
                "Impossible to delete '"
                + f.getAbsolutePath()
                + "'"
            );
        }
    }
    
    private Model model = null;
    private boolean changeExtension = false;

    public static final String NumberVar = "number";
    public static final String DateVar = "date";
    public static final String TimeVar = "time";
    public static final String NameVar = "name";
    public static final String NameNoExtVar = "namenoext";
    public static String[] vars = {
        NumberVar, "",
        DateVar, "",
        TimeVar, "",
        NameVar, "",
        NameNoExtVar, "",
    };
    public static final String VarPrefix = "${";
    public static final String VarPostfix = "}";
    private File[] currentFileList = null;

    public File[] getCurrentFileList() {
        if ( currentFileList == null )
                return this.getFileList();
        else    return currentFileList;
    }

    public Model getModel() {
        return model;
    }


    public RenamingEngine(Model model) {
        this.model = model;
    }

    public RenamingEngine(File d, String ext, String p, String name, boolean r, int s) {
        this.model = new Model( d );

        model.setExt( ext );
        model.setPrefix( p );
        model.setNameFmt( name );
        model.setRecurse( r );
        model.setStart( s );
    }

    public File[] getFileList() {
        return this.getFileList( model.getCurrentDir() );
    }

    /**
     * Gets the files in this directory that conform to the filter
     * @param dir The directory from which extract files with current filter
     * @return a vector of files, also stored in currentFileList
     * @see currentFileList, getCurrentFileList
     */
    public File[] getFileList(File dir) {
        // Create filter for selecting files
        FilenameFilter fileFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return ( !name.startsWith( "." )
                      && name.endsWith( model.getExt() )
                      && name.startsWith( model.getPrefix() )
                );
            }
        };

        return ( currentFileList = dir.listFiles( fileFilter  ) );
    }

    /**
     * Sorts a directory, separating its files according with their extensions,
     * in subdirectories.
     * Call getFileList() before, if you are not running interactively
     * @see getFileList
     */
    public void sort() throws IOException
    {
        sort( model.getCurrentDir() );
    }

    public void sort(File dir) throws IOException {
        File[] files = this.getFileList( dir );

        for(File f : files) {
            if ( f.isDirectory() ) {
                if ( model.getRecurse() ) {
                    sort( f );
                }
            } else {
                File subDir = this.getDirForExt( dir, this.getFileExt( f ) );
                sysMoveFileTo( f, subDir );
            }
        }

        getFileList( model.getCurrentDir() );
    }

    public void chkFmtName() {
        String fmtName = model.getNameFmt();

        if ( model.isForcingNumbering()
          && !fmtName.contains( VarPrefix + NumberVar + VarPostfix ) )
        {
            model.setNameFmt( fmtName + '_' + VarPrefix + NumberVar + VarPostfix );
        }

        changeExtension = true;
        if ( fmtName.contains( "." ) ) {
            changeExtension = false;
        }
    }

    private File getDirForExt(File dir, String fileExt) throws IOException {
        File toret = RenamingEngine.composeFile( dir, fileExt );

        if ( !toret.exists() ) {
            if ( !toret.mkdirs() ) {
                throw new IOException(
                        "Impossible to create '" + toret.getAbsolutePath() + "'"
                );
            }
        }

        return toret;
    }

    private String getFileExt(File f) {
        String toret = "";
        int pos = f.getAbsolutePath().lastIndexOf( '.' );

        if ( pos >= 0 ) {
            toret = f.getAbsolutePath().substring( pos + 1 );
        }

        return toret;
    }

    private void iterateOverDir(File dir) throws IOException
    {
        int numFile = model.getStart();
        File[] files = this.getFileList( dir );

        for(File f : files) {
            if ( f.isDirectory() ) {
                if ( model.getRecurse() ) {
                    this.iterateOverDir( f );
                }
            } else {
                this.renameFile( f, model.getNameFmt(), numFile );
                ++numFile;
            }
        }

        this.getFileList( model.getCurrentDir() );
    }

    /**
     * Renames the current directory in model
     * Call getFileList() before, if you are not running interactively
     * @see getFileList
     * @throws java.io.IOException
     */
    public void rename() throws IOException
    {
        setVars();

        // chk
        chkFmtName();

        if ( !model.getCurrentDir().exists() ) {
            throw new IOException(
                        model.getCurrentDir().toString() + " does not exist"
            );
        }

         if ( !model.getCurrentDir().isDirectory() ) {
            throw new IOException(
                        model.getCurrentDir().toString() + " is not a directory"
            );
        }

        iterateOverDir( model.getCurrentDir() );
    }

    private void renameFile(File f, String fmtName, int numFile) throws IOException
    {
        String newName = createName( f, fmtName, numFile );

        sysRenameFile( f, newName );
    }

    public static File composeFile(File f, String name)
    {
        return new File( f.getAbsolutePath() + File.separator +  name );
    }

    public static void sysRenameFile(File f, String newName) throws IOException
    {
        if ( !f.renameTo( composeFile( f.getParentFile(), newName ) ) ) {
            throw new IOException(
                "Imposible to rename: '" + f.getAbsolutePath() + "'"
                + "\nto: '" + newName + "'"
            );
        }
    }

    public static void sysMoveFileTo(File f, File subDir) throws IOException {
        String fileName  = f.getName();
        File newFile = RenamingEngine.composeFile( subDir, fileName );

        if ( !f.renameTo( newFile ) ) {
            throw new IOException(
                "Imposible to move: '" + f.getAbsolutePath() + "'"
                + "\nto: '" + newFile.getAbsolutePath() + "'"
            );
        }
    }

    private void setVars()
    {
        // Prepare
        Date now = new Date();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime( now );
        String dateNow = Integer.toString( cal.get( Calendar.YEAR ) );
        dateNow = dateNow + '_' + Integer.toString( cal.get( Calendar.MONTH ) );
        dateNow = dateNow + '_' + Integer.toString( cal.get( Calendar.DAY_OF_MONTH ) );

        String timeNow = Integer.toString( cal.get( Calendar.HOUR ) );
        timeNow = timeNow + '_' + Integer.toString( cal.get( Calendar.MINUTE ) );
        timeNow = timeNow + '_' + Integer.toString( cal.get( Calendar.SECOND ) );

        // Number
        vars[ 1 ] = Integer.toString( model.getStart() );

        // Date
        vars[ 3 ] = dateNow;

        // Time
        vars[ 5 ] = timeNow;
    }

    private void updateVars(int numFile, String currentFileName)
    {
        // Update the 1 position in variables for file number
        vars[ 1 ] = Integer.toString( numFile );

        // Update the file name
        vars[ 7 ] = currentFileName;

        // Update the file name without extension
        String fileNameNoExt = currentFileName;
        int pos = currentFileName.lastIndexOf( '.' );
        if ( pos >= 0 ) {
            fileNameNoExt = fileNameNoExt.substring( 0, pos );
        }
        
        vars[ 9 ] = fileNameNoExt;
    }

    public String createName(File f, String fmtName, int numFile)
    {
        String toret = fmtName;
        updateVars( numFile, f.getName() );

        // Substituting variables
        for(int i = 0; i < vars.length; ++i) {
            toret = toret.replace( VarPrefix + vars[ i ] + VarPostfix, vars[ i + 1 ] );
            ++i;
        }

        // Spaces
        if ( model.isEliminatingSpaces() ) {
            toret = toret.replace( " ", "" );
        }
        else
        if ( model.isSubstitutingSpaces() ) {
            toret = toret.replace( " - ", "-" );
            toret = toret.replace( "- ", "-" );
            toret = toret.replace( " -", "-" );
            toret = toret.replace( " ", "_" );
        }

        if ( changeExtension ) {
            toret = toret + '.' + this.getFileExt( f );
        }

        return toret;
    }
}
