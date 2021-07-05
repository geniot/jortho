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
 * Created on 04.11.2005
 */
package io.github.geniot.jortho.jorthodictionaries;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * Convert a plain txt file with UTF8 encoding to a dictionary file.
 */
public class WordList2Dictionary {

    /**
     * The manin method
     * 
     * @param args
     *            command line arguments
     * @throws IOException
     *             if any IO error occur
     */
    public static void main( String[] args ) throws IOException {
        if( args.length != 2 ) {
            System.out.println("Convert a plain txt file with UTF8 encoding to a dictionary file.");
            System.out.println( "  Usage:" );
            System.out.println( "\tjava com.inet.jorthodictionaries.WordList2Dictionary <txt file> <dic file>" );
            System.out.println( "\t<txt file>\ttext file with words in UTF8 coding, every word is in its own line" );
            System.out.println( "\t<dic file>\toutput file name of the created dictionary" );
            System.exit( 1 );
        }

        //Create the output stream
        File dictFile = new File( args[1] );
        OutputStream dict = new FileOutputStream( dictFile );
        dict = new BufferedOutputStream( dict );
        Deflater deflater = new Deflater();
        deflater.setLevel( Deflater.BEST_COMPRESSION );
        dict = new DeflaterOutputStream( dict, deflater );
        dict = new BufferedOutputStream( dict );
        PrintStream dictPs = new PrintStream( dict, false, "UTF8" );

        //create the input stream
        File txtFile = new File( args[0] );
        FileInputStream fis = new FileInputStream( txtFile );
        Reader reader = new InputStreamReader( fis, "UTF8" );
        BufferedReader txt = new BufferedReader( reader );

        //read all the words
        ArrayList<String> wordList = new ArrayList<String>();
        String word = txt.readLine();
        while( word != null ) {
            wordList.add( word );
            word = txt.readLine();
        }

        //Save dictionary
        String[] words = wordList.toArray( new String[wordList.size()] );
        Arrays.sort( words );
        for( int i = 0; i < words.length; i++ ) {
            dictPs.print( words[i] + '\n' );
        }
        dictPs.close();
    }

}
