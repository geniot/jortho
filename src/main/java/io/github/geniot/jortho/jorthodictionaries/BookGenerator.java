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
 * Created on 02.11.2005
 */
package io.github.geniot.jortho.jorthodictionaries;

import java.io.*;
import java.lang.reflect.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.*;


/**
 * How to use
 * Download the latest Wiktionary file "pages_articles.xml".
 * It is typical compressed. The position changed. I found it last at:
 * <ul>
 * <li>http://dumps.wikimedia.org/arwiktionary/latest/arwiktionary-latest-pages-articles.xml.bz2
 * <li>http://dumps.wikimedia.org/dewiktionary/latest/dewiktionary-latest-pages-articles.xml.bz2
 * <li>http://dumps.wikimedia.org/enwiktionary/latest/enwiktionary-latest-pages-articles.xml.bz2
 * <li>http://dumps.wikimedia.org/eswiktionary/latest/eswiktionary-latest-pages-articles.xml.bz2
 * <li>http://dumps.wikimedia.org/frwiktionary/latest/frwiktionary-latest-pages-articles.xml.bz2
 * <li>http://dumps.wikimedia.org/itwiktionary/latest/itwiktionary-latest-pages-articles.xml.bz2
 * <li>http://dumps.wikimedia.org/nlwiktionary/latest/nlwiktionary-latest-pages-articles.xml.bz2
 * <li>http://dumps.wikimedia.org/plwiktionary/latest/plwiktionary-latest-pages-articles.xml.bz2
 * <li>http://dumps.wikimedia.org/ruwiktionary/latest/ruwiktionary-latest-pages-articles.xml.bz2
 * </ul>
 *
 * 
 * start the Generator with follow command line:<br>
 * java -Xmx256m com.inet.spell.wiktionary.BookGenerator de folder with file
 * @author Volker
 */
public abstract class BookGenerator {

    private final Book book;

    
    public static void main(String[] args) throws Exception {
        String languagesList = (args.length>0) ? args[0] : "en";
        String dirName  = (args.length>1) ? args[1].replace( '\\', '/' ) : "";
        if(dirName.length() > 0 && !dirName.endsWith( "/" )){
            dirName += '/';
        }
        String[] languages = languagesList.split(",");
        for(int i = 0; i < languages.length; i++){
            String language = languages[i];
            
            String filename = dirName + language+"wiktionary-latest-pages-articles.xml";
            File file = new File(filename);
            BookGenerator generator = (BookGenerator)Class.forName( BookGenerator.class.getName()+"_" + language ).newInstance();
            generator.start(file);
            generator.save(language);
            
            generator.createPackage( language );
        }
    }
    
    BookGenerator(){
        this(new Book());
    }
    
    BookGenerator(Book book){
        this.book = book;
    }
    
    /**
     * Beginn des einlesend der Daten von dem XML stream
     * @param stream Daten im XML format
     */
    void start(File file) throws Exception{
        InputStream stream = new FileInputStream(file);
        System.out.println("=== Start Parsing XML stream ===");
        new Parser(this, stream);

        stream.close();
    }
    
    
    final void save(String language) throws Exception{
        File dictFile = new File("dictionary_"+language+".ortho");
        OutputStream dict = new FileOutputStream(dictFile);
        dict = new BufferedOutputStream(dict);
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        dict = new DeflaterOutputStream(dict, deflater);
        dict = new BufferedOutputStream(dict);
        PrintStream dictPs = new PrintStream(dict, false, "UTF8");
        
        OutputStream txt = new FileOutputStream("IncludedWords.txt");
        txt = new BufferedOutputStream(txt);
        PrintStream ps = new PrintStream(txt, false, "UTF8");
        
        //Save as word list
        String[] words = book.getWords();
        Arrays.sort( words );
        for(int i=0; i<words.length; i++){
            ps.print( words[i] +'\n' );
            dictPs.print( words[i] +'\n' );
        }
        ps.close();
        dictPs.close();
        saveStatistics(dictFile);
    }
    
    /**
     * Create statistics data and save it in statistics.txt
     * @param dictFile the created ortho file.
     * @throws Exception if an error occur
     */
    private final void saveStatistics(File dictFile) throws Exception{
        String statistics = "";
        statistics += "Total Wiktionary Title count: "+book.getTitleCount()+"\r\n";
        statistics += "Language Title count: "+book.getLanguageTitleCount()+"\r\n";
        statistics += "Word count in dictionary: "+book.getWordCount()+"\r\n";
        statistics += "Char count in dictionary: "+book.getCharCount()+"\r\n";
        statistics += "Dictionary size on disk (bytes): " + dictFile.length()+"\r\n";
        
        // we use reflection, because the methods are not public and should not be public
        Class clazz = Class.forName("com.inet.jortho.DictionaryFactory");
        Constructor constructor = clazz.getConstructor();
        constructor.setAccessible(true);
        Object factory = constructor.newInstance();
        Method loadWordList = clazz.getDeclaredMethod( "loadWordList", URL.class );
        loadWordList.setAccessible(true);
        loadWordList.invoke(factory, dictFile.toURL());
        Method create = clazz.getDeclaredMethod( "create" );
        create.setAccessible(true);
        Object dictionary = create.invoke( factory );
        Method getDataSize = dictionary.getClass().getDeclaredMethod( "getDataSize" );
        getDataSize.setAccessible(true);
        Integer size = (Integer)getDataSize.invoke( dictionary );
        statistics += "Dictionary size in memory (bytes): " + size+"\r\n";
        
        System.out.println(statistics);
        FileOutputStream out = new FileOutputStream("statistics.txt");
        out.write( statistics.getBytes() );
        out.close();
    }
    
    /**
     * Generate the distribution package 
     * @throws Exception 
     */
    private final void createPackage(String language) throws Exception{
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream("dictionary_"+language+"_" + new SimpleDateFormat("yyyy_MM").format( new Date() )+ ".zip"));
        
        out.setLevel( Deflater.BEST_COMPRESSION );
        addFileToZip( out, "license.txt", false );
        addFileToZip( out, "dictionary_"+language+".ortho", true );
        addFileToZip( out, "statistics.txt", true );
        addFileToZip( out, "IncludedWords.txt", true );
        
        out.close();
    }
    
    private final void addFileToZip(ZipOutputStream out, String filename, boolean delete) throws Exception{
        File file = new File(filename);
        FileInputStream fin = new FileInputStream( file );
        ZipEntry entry = new ZipEntry(filename);
        entry.setTime( file.lastModified() );
        out.putNextEntry( entry );
        byte[] buffer = new byte[16384];
        int count;
        while((count = fin.read(buffer)) > 0){
            out.write(  buffer, 0, count );
        }
        out.closeEntry();
        fin.close();
        if(delete){
            file.delete();
        }
    }
    
    /**
     * Help function for parsing the Wiktinary formats.
     * @param string zu durchsuchender String
     * @param chars the searching charchters, can not be empty
     * @param fromIndex Startposition der Suche. Index beginnt bei 0.
     * @return erstes vorkommen einer der Zeichen in chars oder -1, wenn nicht gefunden.
     */
    protected final int indexOf(String string, char[] chars, int fromIndex){
        for(; fromIndex < string.length(); fromIndex++){
            char c = string.charAt(fromIndex);
            for(int i=0; i<chars.length; i++){
                if(c == chars[i]) return fromIndex;
            }
        }
        return -1;
    }
    
    
    /**
     * Check if the word is valid word. This exclude help pages and some phrases.
     * It should be call ever before addWord(String)
     * @param word the to check
     * @return true, if the word is valid
     */
    protected boolean isValidWord(String word){
        if( word == null ){
            return false;
        }
        final int length = word.length();
        if(length <= 1) return false;
        int last = length - 1;
        for( int i = last; i >= 0; i-- ) {
            char ch = word.charAt( i );
            if( Character.isLetter( ch ) ) {
                continue;
            }
            if( ch == '\'' ) {
                if( i == last && word.charAt( 0 ) == '\'' ) {
                    return false;
                }
                if( i > 0 && word.charAt( i - 1 ) == '\'' ) {
                    return false;
                }
                continue;
            }

            //Bei Abkürzungen einen Punkt am Ende
            if( ch == '.' && i == last ) {
                continue;
            }

            //Bindestriche nur in der Wortmitte
            if( ch == '-' && i != 0 && i != last ) {
                continue;
            }

            return false;
        }
        return true;
    }    
    
    /**
     * Add a word to the tree.
     * @param word can not be null
     */
    final protected void addWord(String word){
        book.addWord( word );
    }
    
    /**
     * Get the resulting book for the current generator.
     * @return the book
     */
    Book getBook(){
        return book;
    }
    
    /**
     * Check if a word is a valid word of the current language.
     * With function getBook().addWord() you can add additional Flexion of the word.
     * The current word self does not need added. 
     * @param word the test word
     * @param wikiText die decription from Wiktionary
     * @return true if valid
     */
    abstract boolean isValidLanguage(String word, String wikiText); 
}
