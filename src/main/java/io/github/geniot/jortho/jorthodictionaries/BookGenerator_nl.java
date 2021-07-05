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
 * Created on 12.05.2009
 */
package io.github.geniot.jortho.jorthodictionaries;

import java.util.Properties;

/**
 * BookGenerator class for the Dutch language
 * 
 * @author Marco Wayop
 */
public class BookGenerator_nl extends BookGenerator {

    /**
     * Override to accept also declination
     */
    @Override
    protected boolean isValidWord( String word ) {
        if( super.isValidWord( word ) ) {
            return true;
        }
        return word.endsWith( "/vervoeging" );
    }

    @Override
    boolean isValidLanguage( String word, String wikiText ) {
        boolean isNlWord = wikiText.indexOf( "{{=nld=}}" ) >= 0 && super.isValidWord( word );

        addTemplate( word, wikiText, "-nlnoun-", 4 ); // http://nl.wiktionary.org/wiki/Sjabloon:-nlnoun-

        addTemplate( word, wikiText, "-nlstam-", 7 ); // http://nl.wiktionary.org/wiki/Sjabloon:-nlstam-
        addTemplate( word, wikiText, "-nlverb-", 18 ); // http://nl.wiktionary.org/wiki/Sjabloon:-nlverb-
        addTemplate( word, wikiText, "-nlverb-onp-", 7 ); // http://nl.wiktionary.org/wiki/Sjabloon:-nlverb-onp-
        addTemplate( word, wikiText, "-nlverb-reflex-", 11 ); //http://nl.wiktionary.org/wiki/Sjabloon:-nlverb-reflex-
        
        if( isNlWord ){
            addTemplate( word, wikiText, "adjcomp", 6 ); // http://nl.wiktionary.org/wiki/Sjabloon:adjcomp         
            addTemplate( word, wikiText, "adjcompp", 9 ); // http://nl.wiktionary.org/wiki/Sjabloon:adjcompp         
        }

        return isNlWord;
    }

    /**
     * Add the words for a template if the template is in the wiki text
     * @param word the lemma word
     * @param wikiText the wiki text
     * @param template the template to search
     * @param count the maximum count of words in the template
     */
    private void addTemplate( String word, String wikiText, String template, int count ) {
        Properties props = BookUtils.parseRule( wikiText, template, 0 );
        if( props != null ) {
            addDeclination( word, props, count );
        }
    }

    /**
     * Add declination words from a used template
     * @param root the lemma word
     * @param props the parsed value from the used template 
     * @param count the maximum count of words in the template
     */
    private void addDeclination( String root, Properties props, int count ) {
        for( int i = 1; i <= count; i++ ) {
            String declination = props.getProperty( String.valueOf( i ) );
            if( declination == null ) {
                return;
            }
            declination = declination.replace( "{{pn}}", root );
            declination = declination.replace( "<br>", " " );
            declination = declination.replaceAll( "<br\\s*?/>", " " );
            declination = declination.replace( "<small>", "" );
            declination = declination.replace( "</small>", "" );
            declination = declination.replace( ",", " " );
            declination = declination.replace( "/", " " );
            declination = removeQuotes( declination );
            String[] words = declination.split( "\\s+" );
            for( String word : words ) {
                word = removeQuotes( word );
                if( super.isValidWord( word ) ) {
                    addWord( word );
//                } else {
//                    if( word.length() > 0 && !word.equals( "-" ) && !word.equals( "[1]" ) && !word.equals( "[2]" )
//                                    && !word.equals( "1" ) && !word.equals( "2" ) && !word.equals( "3" ) ) {
//                        System.err.println( word );
//                    }
                }
            }
        }
    }

    /**
     * Remove some thing of quotes and formating from a word or word phrase
     * @param word the word or word phrase
     * @return
     */
    private String removeQuotes( String word ) {
        if( word.length() <= 2 ) {
            return word;
        }
        word = word.replaceAll( "\\[\\[(.*?)\\]\\]", "$1" );
        char ch1 = word.charAt( 0 );
        char ch2 = word.charAt( word.length() - 1 );
        if( (ch1 == '\'' && ch2 == '\'') || (ch1 == '(' && ch2 == ')') ) {
            return removeQuotes( word.substring( 1, word.length() - 1 ) );
        }
        return word;
    }
}
