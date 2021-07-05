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
 * Created on 22.01.2009
 */
package io.github.geniot.jortho.jorthodictionaries;

import java.util.HashSet;

/**
 * This is the container for the found words.
 * 
 * @author Volker Berlin
 */
class Book {

    private int charCount;
    private HashSet list = new HashSet();
    private int titleCount;
    private int titleCountLanguage;

    /**
     * Add a word to the list
     * @param word can not be null
     */
    final protected void addWord(String word){
        if(list.add( word )){            
            if(list.size() % 1000 == 0){
                System.out.println("Word count:"+list.size());
            }
            charCount += word.length();
        }
    }
    
    /**
     * Increment the Count of Wiktionary article
     */
    void incTitleCount(){
        titleCount++;
    }
    
    /**
     * Increment the Count of Wiktionary article in the target language 
     */
    void incLanguageTitleCount(){
        titleCountLanguage++;
    }
    
    /**
     * Get total count of parsed Wiktionary titles.
     * @return the count
     */
    int getTitleCount(){
        return titleCount;
    }
    
    /**
     * Get count of the parsed Wiktionary titles with the right language.
     * @return the count
     */
    int getLanguageTitleCount(){
        return titleCountLanguage;
    }
    
    /**
     * Get the current Word Count in the dictionary. 
     * @return
     */
    int getWordCount(){
        return list.size();
    }

    /**
     * The count of the characters of all words
     * @return the count
     */
    int getCharCount(){
        return charCount;
    }
    
    /**
     * The list of the words
     * @return the list
     */
    String[] getWords(){
        return (String[])list.toArray(new String[list.size()]);
    }
}
