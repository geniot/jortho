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

/**
 * Implementation for French
 * @author Volker Berlin
 */
public class LanguageBundle_fr extends LanguageBundle {

    @Override
    boolean existInDictionary( String word, Dictionary dictionary, SpellCheckerOptions options, boolean isFirstWordInSentence ) {
        if( super.existInDictionary( word, dictionary, options, isFirstWordInSentence ) ) {
            return true;
        }
        int idx = word.indexOf( '\'' );
        if( idx > 0 && idx < word.length() - 1 ) {
            char ch = word.charAt( idx + 1 );
            switch( ch ) {
                case 'a':
                case 'o':
                case 'u':
                case 'e':
                case 'i':
                case 'h':
                    String word1 = word.substring( 0, idx + 1 );
                    String word2 = word.substring( idx + 1 );
                    return super.existInDictionary( word1, dictionary, options, isFirstWordInSentence )
                                    && super.existInDictionary( word2, dictionary, options, isFirstWordInSentence );
            }
        }

        return false;
    }
}
