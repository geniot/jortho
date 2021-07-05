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

import java.util.Hashtable;

import io.github.geniot.jortho.jorthodictionaries.BookGenerator_ru.Template;

/**
 * All String include kyrillic charcters. If you have not a kyrillic keyboard then only copy and paste it.
 * Do not enter any string with a latin keyboard
 * 
 * @author Volker Berlin
 */
public class BookGenerator_ru_templates extends BookGenerator {


    private Hashtable<String, Template> templates = new Hashtable<String, Template>();
    private final BookGenerator_ru generator;
    
    BookGenerator_ru_templates(BookGenerator_ru generator){
        this.generator = generator;
    }
    
    Hashtable<String, Template> getTemplates() {
        return templates;
    }
    
    /**
     * We search only templates and not valid rules.
     */
    @Override
    protected boolean isValidWord( String word ) {
        return word.startsWith( "Шаблон:" );
    }
    
    /**
     * Because it are templates this is ever false.
     */
    @Override
    boolean isValidLanguage( String word, String wikiText ) {

        //The list of templates can be found at:
        //http://ru.wiktionary.org/wiki/%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD%D1%8B_%D1%81%D0%BB%D0%BE%D0%B2%D0%BE%D0%B8%D0%B7%D0%BC%D0%B5%D0%BD%D0%B5%D0%BD%D0%B8%D0%B9/%D0%A4%D0%B0%D0%BC%D0%B8%D0%BB%D0%B8%D0%B8
        Template template = findRules( word, wikiText );
        if(template.getRuleCount() > 0 ){
            templates.put( template.getName(), template );
            System.out.print(".");
        }
        return false;
    }
    
    /**
     * Create a template and add all rules.
     * @param word
     * @param wikiText
     * @return
     */
    private Template findRules( String word, String wikiText ) {
        int idxStart = wikiText.indexOf( "{{{" );
        int idxEnd = wikiText.indexOf( "}}}", idxStart );
        Template template = generator.new Template(word);
        while( idxStart >= 0 && idxEnd >= 0 ) {
            String rule = wikiText.substring( idxStart+3, idxEnd );
            
            //ignore cascading rules
            boolean validRule = rule.length() > 0;
            for(int i=0; i<rule.length(); i++){
                char c = rule.charAt( i );
                if(c != ' ' && !Character.isLetterOrDigit( c ) ){
                    validRule = false;
                    break;
                }
            }
            if(validRule){
                String ending = findEnding(wikiText, idxEnd+3);
                if(ending.length() > 0){
                    template.addEnding( rule, ending );
                }
            }
            
            idxStart = wikiText.indexOf( "{{{", idxEnd );
            idxEnd = wikiText.indexOf( "}}}", idxStart );
        }
        return template;
    }

    /**
     * Find a single ending to the current rule. 
     */
    private String findEnding( String wikiText, int idx){
        int i;
        StringBuilder ending = new StringBuilder();
        for( i=idx; i<wikiText.length(); i++){
            char c = wikiText.charAt( i );
            switch(c){
                case '\u0301':
                    continue;
                default:
                    if(!Character.isLetter( c )){
                        return ending.toString();
                    }
            }
            ending.append( c );
        }
        return ending.toString();
    }


}
