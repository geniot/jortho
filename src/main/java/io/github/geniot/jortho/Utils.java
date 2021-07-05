/*
 *  JOrtho
 *
 *  Copyright (C) 2005-2008 by i-net software
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License as 
 *  published by the Free Software Foundation; either version 2 of the
 *  License, or (at your option) any later version. 
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 *  USA.
 *  
 *  Created on 10.11.2005
 */
package io.github.geniot.jortho;

import java.awt.Dialog;
import java.awt.Image;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 * @author Volker Berlin
 */
public class Utils {

    /**
     * Translate a GUI string in one of the supported languages. If the value was not find then the key is returned.
     * 
     * @param value
     *            the key of the language resource.
     * @return the translation result
     */
    static String getResource( String value ) {
        try {
            ResourceBundle resource = ResourceBundle.getBundle( "resource" );
            return resource.getString( value );
        } catch( Exception e ) {
            if( !value.endsWith( ".tooltip" ) ) { // Tooltip is something extra. Ignore it if not there
                SpellChecker.getMessageHandler().handleException( e );
            }
        }
        return value;
    }

    /**
     * Creates a JButton with a text, tooltip and a key modifier as needed
     * @param resource the key of the language resource.
     * @return a new JButton based on the resource
     */
    static JButton getButton( String resource ) {
        JButton button;

        CustomUIProvider customProvider = SpellChecker.getCustomUIProvider();
        if( customProvider != null ) {
            button = customProvider.getButton( resource );
        } else {
            button = new JButton( Utils.getResource( resource ) );

            String tooltipKey = resource + ".tooltip";
            String tooltip = Utils.getResource( tooltipKey );
            if( tooltip != tooltipKey ) {
                button.setToolTipText( tooltip );
            }
        }

        return button;
    }

    /**
     * Creates a JTextField as needed
     * @return a new JTextField based on the resource
     */
    static JTextField getTextField() {
        JTextField textField;

        CustomUIProvider customProvider = SpellChecker.getCustomUIProvider();
        if( customProvider != null ) {
            textField = customProvider.getTextField();
        } else {
            textField = new JTextField();
        }

        return textField;
    }

    /**
     * Creates a JLabel as needed
     *
     * @param text The text to be on the label
     * @return a new JLabel based on the resource
     */
    static JLabel getLabel( String text ) {
        JLabel label;

        CustomUIProvider customProvider = SpellChecker.getCustomUIProvider();
        if( customProvider != null ) {
            label = customProvider.getLabel( text );
        } else {
            label = new JLabel( text );
        }

        return label;
    }

    
    /**
     * Creates a JList as needed
     *
     * @return a new JList based on the resource
     */
    static JList getList() {
        CustomUIProvider customProvider = SpellChecker.getCustomUIProvider();
        if( customProvider != null ) {
            return customProvider.getList();
        } else {
            return new JList();
        }
    }
    
    /**
     * Set the Icon for an dialog
     * @param dlg the dialog
     */
    static void setDialogIcon(JDialog dlg) {
        try {
            Image image = ImageIO.read( Thread.currentThread().getContextClassLoader().getResourceAsStream( "icon.png" ) );
            // setIconImage appeared in Java 6.0 so use reflection to be compatible
            // with earlier JVMs. Equivalent to calling setIcomImage(image);
            Class cls = Dialog.class;
            java.lang.reflect.Method m = cls.getMethod( "setIconImage", new Class[] { Image.class } );
            m.invoke( dlg, new Object[] { image } );
        } catch( Throwable e1 ) {
            // can occur in Java 5 or if the icon was removed, then use the default
        }
    }
    
    /**
     * Create a String where the first letter is written with a uppercase.
     * 
     * @param word
     *            the word that should be change
     * @return the new String if needed
     */
    static String getCapitalized( String word ) {
        if( (word.length() > 0) && Character.isLowerCase( word.charAt( 0 ) ) ) {
            return word.substring( 0, 1 ).toUpperCase() + word.substring( 1 );
        }
        return word;
    }

    /**
     * Create a String with inverted case for the first letter. If it is lowercase then it will change to uppercase and
     * vice versa.
     * 
     * @param word
     *            the word that should be change
     * @return the new String if needed
     */
    static String getInvertedCapitalizion( String word ) {
        if( word.length() > 0 ) {
            if( Character.isLowerCase( word.charAt( 0 ) ) ) {
                return word.substring( 0, 1 ).toUpperCase() + word.substring( 1 );
            }
            if( Character.isUpperCase( word.charAt( 0 ) ) ) {
                return word.substring( 0, 1 ).toLowerCase() + word.substring( 1 );
            }
        }
        return word;
    }

    /**
     * Check if the first character is a uppcase letter
     * 
     * @param word
     *            the word that should be check. It can not be null.
     * @return true if the first character is a uppercase letter
     */
    static boolean isFirstCapitalized( String word ) {
        return (word.length() > 0) && Character.isUpperCase( word.charAt( 0 ) );
    }

    /**
     * Check if all letter are uppercase. Character that are not letters are ignored.
     * 
     * @param word
     *            the word that should be check. It can not be null or empty.
     * @return if all character are a uppercase letter
     */
    static boolean isAllCapitalized( String word ) {
        for( int i = 0; i < word.length(); i++ ) {
            char ch = word.charAt( i );

            if( Character.isLetter( ch ) && !Character.isUpperCase( ch ) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the word include a digit.
     * 
     * @param word
     *            the word that should be check. It can not be null.
     * @return if there is any number in the word.
     */
    static boolean isIncludeNumbers( String word ) {
        for( int i = 0; i < word.length(); i++ ) {
            char ch = word.charAt( i );
            if( Character.isDigit( ch ) ) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check and replace Unicode variants of quotation marks and hyphens. Unicode characters for general punctuation can
     * be seen here: http://www.unicodemap.org/range/40/General_Punctuation/
     * 
     * @param word
     *            the word that should be check. It can not be null.
     * @return A new string of the same length as the original.
     */
    public static String replaceUnicodeQuotation( String word ) {
        char[] newWord = null;

        for( int i = 0; i < word.length(); i++ ) {
            char ch = word.charAt( i );

            switch( ch ) {
                case '\u2018': // LEFT SINGLE QUOTATION MARK
                case '\u2019': // RIGHT SINGLE QUOTATION MARK
                case '\u201a': // SINGLE LOW-9 QUOTATION MARK
                case '\u201b': // SINGLE HIGH-REVERSED-9 QUOTATION MARK
                case '´': // These last two should probably not be included,
                case '`': // they are not really quotation marks.
                    if( newWord == null ){
                        newWord = word.toCharArray();
                    }
                    newWord[i] = '\'';
                    break;
                case '\u2011': // NON-BREAKING HYPHEN
                case '\u2012': // FIGURE DASH
                case '\u2013': // EN DASH
                case '\u2014': // EM DASH
                case '\u2015': // HORIZONTAL BAR
                    if( newWord == null ){
                        newWord = word.toCharArray();
                    }
                    newWord[i] = '-';
                    break;
            }
        }
        return ( newWord == null ) ? word : new String( newWord );
    }
}
