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
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

/**
 * All String include kyrillic charcters. If you have not a kyrillic keyboard then only copy and paste it.
 * Do not enter any string with a latin keyboard
 * @author Volker Berlin
 */
public class BookGenerator_ru extends BookGenerator {

    private Hashtable<String, Template> templates = new Hashtable<String, Template>();

    /**
     * Need 2 run. First are parsed the templates. Then start the real parsing.
     */
    @Override
    void start( File file ) throws Exception {
        BookGenerator_ru_templates templateGenerator = new BookGenerator_ru_templates(this);
        templateGenerator.start( file );
        templates = templateGenerator.getTemplates();
        System.out.println("\nTemplate Count: "+templates.size());
        
        super.start( file );
    }
    
    @Override
    boolean isValidLanguage( String word, String wikiText ) {
        if( wikiText.indexOf( "{{-ru-}}" ) < 0 && wikiText.indexOf( "{{-ru-|" ) < 0 ) {
            return false;
        }

        //The list of templates can be found at
        //http://ru.wiktionary.org/wiki/%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD%D1%8B_%D1%81%D0%BB%D0%BE%D0%B2%D0%BE%D0%B8%D0%B7%D0%BC%D0%B5%D0%BD%D0%B5%D0%BD%D0%B8%D0%B9/%D0%A4%D0%B0%D0%BC%D0%B8%D0%BB%D0%B8%D0%B8
        findRuleAndAddWords( word, wikiText );

        return true;
    }
    
    /**
     * Find the placholder of templates and add the words that was build with this templates.
     */
    private void findRuleAndAddWords( String word, String wikiText ) {
        int idxStart = wikiText.indexOf( "{{" );
        int idxEnd = wikiText.indexOf( "}}", idxStart );
        while( idxStart >= 0 && idxEnd >= 0 ) {
            Properties props = parseRule( wikiText, idxStart + 2, idxEnd );
            String ruleName = props.getProperty( "0" );
            if( ruleName != null ) {
                Template template = templates.get( ruleName );
                if( template != null ) {
                    template.addWords( word, props );
                }
            }

            idxStart = wikiText.indexOf( "{{", idxEnd );
            idxEnd = wikiText.indexOf( "}}", idxStart );
        }
    }


    /**
     * Read the inforamtions of the template placeholder
     */
    private Properties parseRule( String wikiText, int idxStart, int idxEnd ) {
        Properties props = new Properties();
        
        String[] tokens = wikiText.substring( idxStart, idxEnd ).split( "\\|" );
        for(int i=0; i<tokens.length; i++){
            String value = tokens[i].trim();
            int idx = value.indexOf( '=' );
            if(idx > 0){
                String name = value.substring( 0, idx );
                value = value.substring( idx+1 );
                props.setProperty( name, normalizeString( value ) );
            } else {
                props.setProperty( String.valueOf( i ), value );
            }
        }
        return props;
    }
    
    private String normalizeString( String str ){
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<str.length(); i++ ){
            char c = str.charAt( i );
            switch(c){
                case '\u0301':
                case '-':
                    continue;
            }
            builder.append( c );
        }
        return builder.toString();
    }

    /**
     * Described the needed informations of a template.
     */
    public class Template{
        private Hashtable<String, String[]> rules = new Hashtable<String, String[]>();
        private String templateName;
        
        Template( String templateName ) {
            this.templateName = templateName;
        }

        public void addWords( String word, Properties props ) {
            for( Map.Entry<String, String[]> rule : rules.entrySet() ){
                String root = props.getProperty( rule.getKey() );
                if(root != null){
                    String[] endings = rule.getValue();
                    for( String ending : endings){
                        addWord(root+ending);
                    }
                }
            }
        }
        
        /**
         * get the count of found rules in the template. Only templates with rules make sence.
         */
        int getRuleCount() {
            return rules.size();
        }
        
        /**
         * Get the name of the template without the prefix "Шаблон:"
         */
        String getName(){
            return templateName.substring( 7 );
        }

        /**
         * Add a single ending to a rule.
         */
        void addEnding( String rule, String ending ) {
            String[] endings = rules.get( rule );
            if( endings == null ) {
                endings = new String[1];
            } else {
                for( String e : endings ) {
                    if( e.equals( ending ) ) {
                        //Ending already in list
                        return;
                    }
                }
                String[] temp = new String[endings.length + 1];
                System.arraycopy( endings, 0, temp, 0, endings.length );
                endings = temp;
            }
            endings[endings.length - 1] = ending;
            rules.put( rule, endings );
        }

        /**
         * Create a Java source code representation of this template. Currently obsolet.
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append( "//" ).append( templateName ).append( '\n' );
            builder.append( "template = new Template();\n" );
            for( Map.Entry<String, String[]> rule : rules.entrySet() ) {
                builder.append( "template.addRule( \"" ).append( rule.getKey() ).append( '\"' );
                String[] endings = rule.getValue();
                for( String ending : endings ) {
                    builder.append( ", \"" ).append( ending ).append( "\"" );
                }
                builder.append( " );\n" );
            }
            builder.append( "templates.put( \"" ).append( getName() ).append( "\", template );\n" );
            builder.append( "\n" );
            return builder.toString();
        }
    }
}
