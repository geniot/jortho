/*
 *  JOrtho
 *
 *  Copyright (C) 2005-2012 by i-net software
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
 * Created on 31.05.2012
 */
package io.github.geniot.jortho;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class implements some language specific methods. 
 * @author Volker Berlin
 */
public class LanguageBundle extends ResourceBundle {

    /**
     * Get a implementations of this class for the given locale
     * @param locale the current spell checking language
     * @return an instance, never null
     */
    static LanguageBundle get( Locale locale ){
        return (LanguageBundle)ResourceBundle.getBundle( LanguageBundle.class.getName(), locale );
    }
    
    /**
     * Check if the word is in the current dictionary. It verify the options isCaseSensitive, getIgnoreCapitalization and the first word of sentence
     * @param word current word
     * @param dictionary the current dictionary
     * @param options the current options
     * @param isFirstWordInSentence if the word is the first in a sentence
     * @return true, if the word is in the dictionary
     */
    boolean existInDictionary( String word, Dictionary dictionary, SpellCheckerOptions options, boolean isFirstWordInSentence ) {
        boolean exist = dictionary.exist( word );
        if( !exist && !options.isCaseSensitive() ) {
            exist = dictionary.exist( Utils.getInvertedCapitalizion( word ) );
        } else if( !exist && (isFirstWordInSentence || options.getIgnoreCapitalization()) && Character.isUpperCase( word.charAt( 0 ) ) ) {
            // Uppercase check on starting of sentence
            String capitalizeWord = word.substring( 0, 1 ).toLowerCase() + word.substring( 1 );
            exist = dictionary.exist( capitalizeWord );
        }
        return exist;
    }
    
    @Override
    protected Object handleGetObject( String key ) {
        return null;
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }
}
