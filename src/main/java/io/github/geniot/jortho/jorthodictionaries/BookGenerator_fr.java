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

/**
 * 
 * @author Volker Berlin
 */
public class BookGenerator_fr extends BookGenerator {

    /**
     * Create a new BookGenerator and add some special words.
     */
    public BookGenerator_fr() {
        // Compound words with a single character
        addWord( "m'a" );
        addWord( "m'y" );
        addWord( "n'a" );
        addWord( "n'y" );
        addWord( "l'a" );
        addWord( "l'y" );
        addWord( "qu'a" );
        addWord( "qu'y" );
        addWord( "s'y" );
    }
    
    @Override
    boolean isValidLanguage( String word, String wikiText ) {
        if( wikiText.indexOf( "{{langue|fr}}" ) < 0 && wikiText.indexOf("{{=fr=}}") < 0){
            return false;
        }

        return true;
    }

}
