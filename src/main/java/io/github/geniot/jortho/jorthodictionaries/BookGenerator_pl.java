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
 * Created on 13.12.2007
 */
package io.github.geniot.jortho.jorthodictionaries;

import java.io.File;

/**
 * 
 * @author Volker Berlin
 */
public class BookGenerator_pl extends BookGenerator {

    @Override
    void start( File file ) throws Exception {
        //because the polish dictionary include no conjugation we read also the english dictionary
        String path = file.getPath().replace( "plwiktionary", "enwiktionary" );
        File fileEnglisch = new File(path);
        BookGenerator english = new BookGenerator_pl_Engish(getBook());
        english.start( fileEnglisch );
        
        super.start( file );
    }
    
    @Override
    boolean isValidLanguage( String word, String wikiText ) {
        if( wikiText.indexOf( "{{język polski}}" ) < 0 && wikiText.indexOf( "{{Język polski}}" ) < 0) {
            return false;
        }

        int idx = wikiText.indexOf( "{{odmiana}}" );
        if( idx > 0 ) {
            odmiana( idx + "{{odmiana}}".length(), wikiText );
        }
        return true;
    }

    /** 
     * Flections
     * 
     * @param idx start of the flextion
     */
    private void odmiana( int idx, String wikiText ) {
        // sample
        // {{odmiana}} {{M}}: {{m}} któr|y, {{f}} ~a, {{n}} ~e; {{lm m}} ~zy, {{lm nm}} ~e; {{D}}: ~ego, ~ej, ~ego, ~ych, ~ych; {{C}}: ~emu, ~ej, ~emu, ~ym, ~ym; {{B}}: ~ego, ~ą, ~y, ~ych, ~e; {{N}}: ~ym, ~ą, ~ym, ~ymi, ~ymi; {{Ms}}: ~ym, ~ej, ~ym, ~ych, ~ych; {{W}} ~y, ~a, ~e/~o, ~zy, ~e

        String wordRoot = null;
        boolean newWordRoot1 = false;
        boolean newWordRoot2 = false;
        StringBuilder builder = new StringBuilder();
        boolean wasNewline = false;
        int bracket = 0;
        
        while( idx < wikiText.length() && !wasNewline) {
            char ch = wikiText.charAt( idx++ );
            if( ch == '\n'){
                wasNewline = true;
            }
            
            if( newWordRoot1 ){
                newWordRoot2 = true;
            }
            
            if( Character.isWhitespace( ch ) && bracket == 0) {
                // multiple whitespace
                if( builder.length() == 0 ) {
                    continue;
                }

                String word = builder.toString();
                builder.setLength( 0 );
                
                // start a new grammatic sequence
                if( word.equals( "lp" ) || word.equals( "lm" )
                                || word.equals( "{{lp}}" ) || word.equals( "{{lm}}" )
                                || word.equals( "''lp''" ) || word.equals( "''lm''" )) {
                    newWordRoot1 = true;
                    continue;
                }

                // remove the delimiter
                if( word.charAt( word.length()-1 ) == ','){
                    word = word.substring( 0, word.length()-1 );
                    if( word.length() == 0 ){
                        continue;
                    }
                }
                if( word.charAt( word.length()-1 ) == ';'){
                    word = word.substring( 0, word.length()-1 );
                    newWordRoot1 = true;
                    if( word.length() == 0 ){
                        continue;
                    }
                }
                if( word.charAt( word.length()-1 ) == '!'){
                    word = word.substring( 0, word.length()-1 );
                    if( word.length() == 0 ){
                        continue;
                    }
                }
                                
                // skip grammatic comments
                if( word.equals( "{{m}}" ) || word.equals( "{{f}}" ) || word.equals( "{{n}}" ) 
                                || word.equals( "{{mosob}}" ) || word.equals( "{{nmosob}}" ) || word.equals( "{{nieodm}}" ) 
                                || isCaseLabel( word ) ) {
                    continue;
                }

                // set the new word root if needed
                if( wordRoot == null || newWordRoot2 ) {
                    newWordRoot1 = newWordRoot2 = false;
                    int pipeIdx = word.indexOf( '|' );
                    String possibleRoot = (pipeIdx >= 0) ? word.substring( 0, pipeIdx ) : word;
                    if( isValidWord( possibleRoot ) ) {
                        wordRoot = possibleRoot;
                        if(pipeIdx >= 0){
                            word = wordRoot + word.substring( pipeIdx + 1 );
                        }
                        if( isValidWord( word ) ) {
                            addWord( word );
                        } else {
                            System.err.println( word );
                            System.err.print( "" );
                        }
                        continue;
                    } else {
                        if( wordRoot == null ){
                            System.err.println( "Wordroot: "+word );
                            continue;
                        }
                    }
                }
                word = word.replace( "~|", wordRoot );
                word = word.replace( "~", wordRoot );
                word = word.replace( "-", wordRoot );
                if( isValidWord( word ) ) {
                    addWord( word );
                } else {
                    if( !isAlternateWords( word ) ) {
                        System.err.println( word );
                        System.err.print( "" );
                    }
                }
                continue;
            }
            
            switch(ch){
                case '{':
                case '[':
                case '(':
                    bracket++;
                    break;
                case '}':
                case ']':
                case ')':
                    bracket--;
                    break;
            }
            
            builder.append( ch );
        }
    }
    
    /**
     * Check if the word include 2 worde with alternate writting. If yes then both will be added.
     * 
     * @param word
     *            a paosble worde alternative
     * @return true, if there are 2 valid words.
     */
    private boolean isAlternateWords( String word ) {
        String[] words = word.split( "/" );
        if( words.length > 1 ) {
            for( String w : words ){
                if( !isValidWord( w )){
                    return false;
                }
            }
            for( String w : words ){
                addWord( w );
            }
            return true;
        }
        return false;
    }
    
    /**
     * Test if it is a case label like (1), (1.2) or (3.1) 
     * @param word
     * @return
     */
    private boolean isCaseLabel( String word ){
        return word.startsWith( "(" ) && word.length() > 1 && Character.isDigit( word.charAt( 1 ) );
    }
}
