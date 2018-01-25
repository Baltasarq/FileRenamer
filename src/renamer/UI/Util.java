/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package renamer.UI;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.w3c.dom.Node;
import renamer.SRC.AppInfo;
import renamer.UI.RenamerApp;

/**
 *
 * @author baltasarq
 */
public class Util {
    /// @brief Puts an error message on top of the main window
    /// @param msg The message to display
    public static void msgError(String msg)
    {
        JOptionPane.showMessageDialog( RenamerView.getJFrame(), msg, AppInfo.Name + " - Error",
                                       JOptionPane.ERROR_MESSAGE
        );
    }
    
    /// @brief Puts an info message on top of the main window
    /// @param msg The message to display
    public static void msgInfo(String msg)
    {
        JOptionPane.showMessageDialog( RenamerView.getJFrame(), msg, AppInfo.Name + " - Info",
                                       JOptionPane.INFORMATION_MESSAGE
        );
    }

    /// @brief returns the name of a file, without path and extension
    /// @param file The file of which to obtain its name
    /// @return An string with that name
    public static String getFileNameWithoutExtension(File file) {
        String toret = file.getName();
        int pos = toret.lastIndexOf( '.' );
        
        if ( pos > -1 ) {
            toret = toret.substring( 0, pos );
        }
        
        return toret;
    }
    
    /// @brief retrieves a node, child of another node, by its name
    /// @param node The parent node of which the looked for node hangs
    /// @param name The name of the child node to look for
    /// @return The child node if found, null otherwise
    public static Node getXmlChildByName(Node node, String name)
    {
        Node toret = null;
        node = node.getFirstChild();
        
        while( node != null ) {
            if ( node.getNodeName().compareToIgnoreCase( name ) == 0 ) {
                toret = node;
                break;
            }
            
            node = node.getNextSibling();
        }   
        
        return toret;
    }
    
    public static File openFileDlg(FileNameExtensionFilter[] filters, File f) {
        // Create open dialog
        File toret = null;
        JFileChooser fileSelector = new JFileChooser();
        fileSelector.setDialogType( JFileChooser.OPEN_DIALOG );
        
        // Add filters
        for( FileNameExtensionFilter filter : filters) {
            fileSelector.addChoosableFileFilter( filter );
        }
        fileSelector.setFileFilter( filters[ 0 ] );  
        
        // Locate the last directory, if any
        if ( f != null ) {
            fileSelector.setSelectedFile( f );
        }
        
        // run
        if ( fileSelector.showOpenDialog( RenamerView.getJFrame() ) == JFileChooser.APPROVE_OPTION ) {
            toret = fileSelector.getSelectedFile();
        }
        
        return toret;        
        
    }

    public static File openFileDlg(String ext, String extExpl, File f) {
        FileNameExtensionFilter[] filters = {
            new FileNameExtensionFilter( extExpl, ext )
        };
        
        return openFileDlg( filters, f );
    }
    
    public static File openFileDlg(String[] ext, String[] extExpl, File f) {
        FileNameExtensionFilter[] filters =
            new FileNameExtensionFilter[ ext.length ]
        ;
        
        for(int i = 0; i < ext.length; ++i) {
            filters[ i ] = new FileNameExtensionFilter( extExpl[ i ], ext[ i ] );
        }
        
        return openFileDlg( filters, f );
    }

    public static File openDirDlg(File f) {
        File toret = null;
        JFileChooser fileSelector = new JFileChooser( f );
        fileSelector.setDialogType( JFileChooser.OPEN_DIALOG );
        fileSelector.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

        // run
        if ( fileSelector.showOpenDialog( RenamerView.getJFrame() ) == JFileChooser.APPROVE_OPTION ) {
            toret = fileSelector.getSelectedFile();
        }

        return toret;
    }
    
    public static boolean askYesNo(String msg)
    {
        int result = JOptionPane.showConfirmDialog(
                RenamerView.getJFrame(),
                msg,
                AppInfo.Name + " options",
                JOptionPane.OK_CANCEL_OPTION
        );
        
        return ( result == JOptionPane.YES_OPTION );
    }

    public static String askFor(String msg, String answer)
    {
        String s = (String) JOptionPane.showInputDialog(
                            RenamerView.getJFrame(),
                            msg + "\n",
                            AppInfo.Name,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            answer
        );

        // if a string was returned, say so.
        if ( s != null ) {
            s = s.trim();
            if ( !s.isEmpty() )  {
                return s;
            }
        }

        return null;
    }
}
