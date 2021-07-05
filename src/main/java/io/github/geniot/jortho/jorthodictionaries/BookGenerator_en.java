/*
 *  JOrtho
 *
 *  Copyright (C) 2005-2009 by i-net software
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
 * Created on 13.12.2007
 */
package io.github.geniot.jortho.jorthodictionaries;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Volker Berlin
 */
public class BookGenerator_en extends BookGenerator {

    private final Pattern english = Pattern.compile( "^==\\s*English\\s*==$", Pattern.MULTILINE );
    private final Pattern languages = Pattern.compile( "^==[^=]*==$", Pattern.MULTILINE );
    
    @Override
    boolean isValidLanguage( String word, String wikiText ) {
        Matcher engMatch = english.matcher( wikiText );
        if( !engMatch.find() || wikiText.indexOf( "{{rare}}" ) > 0 ) {
            return false;
        }

        Matcher matcher = languages.matcher( wikiText );
        if( matcher.find( engMatch.end() ) ) {
            wikiText = wikiText.substring( engMatch.end(), matcher.start() );
        }

        Properties props = BookUtils.parseRule( wikiText, "misspelling of", 0 );
        if( props != null ) {
            //we need to split between real misspelling words 
            //and sometime is a misspelling word
            //we use the diff and the size of the article
            String correctWord = props.getProperty( "1" );
            if(correctWord != null && BookUtils.calcDiff( word, correctWord ) <= 3 && wikiText.length() < 250){
                return false;
            }
        }

        if( wikiText.indexOf( "{{en-noun}}" ) > 0 || wikiText.indexOf( "{{en-proper noun}}" ) > 0 ) {
            // http://www.englishclub.com/grammar/nouns-possessive.htm
            String genetiv = word + "'s";
            if( isValidWord( genetiv ) ) {
                addWord( genetiv );
            }
            String pluralGenetiv = word + "s'";
            if( isValidWord( pluralGenetiv ) ) {
                addWord( pluralGenetiv );
            }
        }

        int idx = wikiText.indexOf( "{{en-noun|pl=" );
        if( idx > 0 ) {
            // http://www.englishclub.com/grammar/nouns-possessive.htm
            idx += "{{en-noun|pl=".length();
            int idx2 = wikiText.indexOf( "}}", idx );
            if( idx2 > 0 ) {
                String plural = wikiText.substring( idx, idx2 );
                plural = trim( plural );
                if( isValidWord( plural ) ) {
                    addWord( plural );
                    plural += "'s";
                    if( isValidWord( plural ) ) {
                        addWord( plural );
                    }
                }
            }
        }
        return true;
    }

    /**
     * Removes quotes and parenthesis
     * 
     * @param word
     *            the trimming word
     * @return the new word
     */
    private String trim( String word ) {
        word = word.trim();
        if( word.length() >= 6 && word.startsWith( "'''" ) && word.endsWith( "'''" ) ) {
            word = word.substring( 3, word.length() - 3 );
        }
        word = word.trim();
        if( word.length() >= 4 && word.startsWith( "[[" ) && word.endsWith( "]]" ) ) {
            word = word.substring( 2, word.length() - 2 );
        }
        word = word.trim();
        return word;
    }
}
