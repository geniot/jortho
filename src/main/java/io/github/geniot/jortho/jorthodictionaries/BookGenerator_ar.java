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
 * The Arabic words can be garbage the view in Eclispe and other eiditors. With Notpad++ it is possible to view it correctly.
 * 
 * @author Volker Berlin
 */
public class BookGenerator_ar extends BookGenerator {

    @Override
    void start( File file ) throws Exception {
        //because the arabic dictionary is so small we scan also the english dictionary for arabic words
        String path = file.getPath().replace( "arwiktionary", "enwiktionary" );
        File fileEnglisch = new File(path);
        super.start( fileEnglisch );
        
        super.start( file );
    }
    
    @Override
    boolean isValidLanguage( String word, String wikiText ) {
        if(wikiText.indexOf("{{عربية}}") < 0){
            if( !isOnlyArabicCharacters( word ) ) {
                return false;
            }
        }

        return true;
    }

    private boolean isOnlyArabicCharacters( String word ) {
        for( int i = 0; i < word.length(); i++ ) {
            char ch = word.charAt( i );
            if( Character.UnicodeBlock.of( ch ) != Character.UnicodeBlock.ARABIC ) {
                return false;
            }
        }
        return true;
    }
}
