/*
 *  JOrtho
 *
 *  Copyright (C) 2005-2010 by i-net software
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
 * Created on 03.11.2005
 */
package io.github.geniot.jortho.jorthodictionaries;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Volker Berlin
 */
public class BookGenerator_de extends BookGenerator {

    public BookGenerator_de(){
        addWord( "Ihnen" );
        addWord( "Ihrer" );
        addWord( "Ihre" );
        addWord( "Ihres" );
        addWord( "Ihrem" );
        addWord( "Ihren" );
        addWord( "Sie" );
        addWord( "Du" );
    }
    
    @Override
    protected boolean isValidWord(String word){
        if(super.isValidWord( word )){
            return true;
        }
        return word.endsWith( "(Deklination)" ) || word.endsWith( "(Konjugation)" ) || word.equals( "Wiktionary:Wunschliste" );
    }
    
    @Override
    boolean isValidLanguage(String word, String wikiText) {   
        wikiText = removeHtmlFormating(wikiText);
        if( word.equals( "Wiktionary:Wunschliste" ) ) {
            addLinkWords( word, wikiText, "Wunschliste" );
            return false;
        }
        int idxGerman = wikiText.indexOf("{{Sprache|Deutsch}}");
        if( idxGerman < 0 || word.endsWith( "(Deklination)" ) || word.endsWith( "(Konjugation)" )){
            /*
            if(wikiText.indexOf("{{Sprache|") >= 0){
                return false;
            }
            if(wikiText.indexOf("{{Schweizer Schreibweise|") >= 0){
                return false;
            }
            if(wikiText.indexOf("{{Alte Schreibweise|") >= 0){
                return false;
            }
            if(wikiText.indexOf("{{anpassen}}") >= 0){
                return false;
            }
            if(wikiText.toUpperCase().indexOf("#REDIRECT") >= 0){
                return false;
            }*/
            Properties props = BookUtils.parseRule(wikiText,"Deklinationsseite Adjektiv", 0);
            if(props != null){
                addDeklinationAdjektiv(props);
                // The declinations are valid, but the lemma (word) not.
                return false;
            }
            
            props = BookUtils.parseRule( wikiText, "Deutsch Verb unregelmäßig", 0 );
            if(props != null){
                addKonjugationVerbUnregular( props );
                // The declinations are valid, but the lemma (word) not.
                return false;
            }
            
            props = BookUtils.parseRule( wikiText, "Deutsch Verb schwach untrennbar", 0 );
            if(props != null){
                addKonjugationVerbSchwachUntrennbar( props, false );
                // The declinations are valid, but the lemma (word) not.
                return false;
            }
            
            props = BookUtils.parseRule( wikiText, "Deutsch Verb schwach untrennbar reflexiv", 0 );
            if(props != null){
                addKonjugationVerbSchwachUntrennbar( props, true );
                // The declinations are valid, but the lemma (word) not.
                return false;
            }
            
            props = BookUtils.parseRule( wikiText, "Deutsch Verb schwach trennbar", 0 );
            if(props != null){
                addKonjugationVerbSchwachTrennbar( props, false );
                // The declinations are valid, but the lemma (word) not.
                return false;
            }
            
            props = BookUtils.parseRule( wikiText, "Deutsch Verb schwach trennbar reflexiv", 0 );
            if(props != null){
                addKonjugationVerbSchwachTrennbar( props, true );
                // The declinations are valid, but the lemma (word) not.
                return false;
            }
            
            props = BookUtils.parseRule( wikiText, "Deutsch Verb schwach doppelt trennbar", 0 );
            if(props != null){
                addKonjugationVerbSchwachDoppeltTrennbar( props );
                // The declinations are valid, but the lemma (word) not.
                return false;
            }

//            if( word.endsWith( "(Konjugation)" ) && wikiText.indexOf( "Deutsch" ) > 0 ){
//                System.err.println();
//            }
            
            return false;
        }
        do{
            String chapter = getChapter(wikiText, idxGerman);
            searchFlexion( word, chapter );
            idxGerman = wikiText.indexOf(chapter) + chapter.length();
            //should not occur but it occur
            idxGerman = wikiText.indexOf("{{Sprache|Deutsch}}", idxGerman );
        }while(idxGerman > 0);
        return true;
    }
    
    private final void searchFlexion( String word, String wikiText ){
        int idx = wikiText.indexOf("{{Wortart|Verb|Deutsch}}");
        while(idx > 0){
            //ascertain flexion of the verbs
            String table = getTable( wikiText, "Verb-Tabelle", idx );
            if(table.length() > 0 && searchWordAndAdd( word, table, "Gegenwart_ich=", 0)){
                searchWordAndAdd( word, table, "Gegenwart_du=", 0);
                searchWordAndAdd( word, table, "Gegenwart_er, sie, es=", 0);
                searchWordAndAdd( word, table, "1.Vergangenheit_ich=", 0);
                searchWordAndAdd( word, table, "Partizip II=", 0);
                searchWordAndAdd( word, table, "Konjunktiv II_ich=", 0);
                searchWordAndAdd( word, table, "Befehl_du=", 0);
                searchWordAndAdd( word, table, "Befehl_ihr=", 0);
            }
            int lastIdx = idx+1;
            idx = wikiText.indexOf("{{Wortart|Verb|Deutsch}}", lastIdx);
        }
        
        
        idx = wikiText.indexOf("{{Wortart|Substantiv}}");
        if(idx <0){
            idx = wikiText.indexOf("{{Wortart|Substantiv|Deutsch}}");
        }
        while(idx > 0){
            String chapter = getChapter(wikiText, idx);
            if( !addDeklinationSubstTable( chapter, 0, word ) ){
                // no Deklination found
            }
            int lastIdx = idx+1;
            idx = wikiText.indexOf("{{Wortart|Substantiv}}", lastIdx);
            if(idx <0){
                idx = wikiText.indexOf("{{Wortart|Substantiv|Deutsch}}", lastIdx);
            }
        }
        
        
        idx = wikiText.indexOf("{{Wortart|Adjektiv|Deutsch}}");
        while(idx > 0){
            // finding the conjugation of the Adjective 
            Properties props = BookUtils.parseRule( wikiText, "Deutsch Adjektiv Übersicht", idx);
            if(props != null){
                String grundform = props.getProperty( "Positiv" );
                addFormatedWordPhrase(word, "Positiv", grundform );
                addFormatedWordPhrase(word, "Komparativ", props.getProperty( "Komparativ" ) );
                addFormatedWordPhrase(word, "Superlativ", props.getProperty( "Superlativ" ) );
                if(grundform == null || !super.isValidWord(grundform)){
                    grundform = word;
                }
                if(!grundform.endsWith( "e" )){
                    addDeklinationAdjektiv(grundform);
                }
            }
            int lastIdx = idx+1;
            idx = wikiText.indexOf("{{Wortart|Adjektiv|Deutsch}}", lastIdx);
        }
        
        
        if( wikiText.indexOf( "{{Wortart|Partizip I|Deutsch}}" ) >= 0 ) {
            addDeklinationAdjektiv( word );
        }

        if( wikiText.indexOf( "{{Wortart|Partizip II|Deutsch}}" ) >= 0 ) {
            addDeklinationAdjektiv( word );
        }

        if( wikiText.indexOf( "{{Wortart|Indefinitpronomen|Deutsch}}" ) >= 0 ) {
            addIndefinitpronomen( word, wikiText );
        }
        
        searchExtendsWords( word, wikiText, "{{Synonyme}}" );
        searchExtendsWords( word, wikiText, "{{Unterbegriffe}}" );
        searchExtendsWords( word, wikiText, "{{Abgeleitete Begriffe}}" );
    }
    
    /**
     * Get the chapter on the current position. If a next chapter line is not find
     * then the completely wikiText is return.
     * @param wikiText
     * @param headerIdx position in the header line
     * @return a chapter or all text.
     */
    private String getChapter(String wikiText, int headerIdx){
        int startIdx = wikiText.lastIndexOf('\n', headerIdx) + 1;
        int endIdx = wikiText.indexOf('\n', headerIdx);
        if(endIdx == -1){
            return wikiText.substring(startIdx);
        }
        String header = wikiText.substring(startIdx, endIdx).trim();
        int prefixSize = 0;
        while(header.length() > prefixSize && header.charAt(prefixSize) == '='){
            prefixSize++;
        }
        if(prefixSize == 0){
            return wikiText.substring(startIdx);
        }
        String marker = header.substring(0,prefixSize);
        if(!header.endsWith(marker)){
            return wikiText.substring(startIdx);
        }

        Pattern pattern = Pattern.compile( "^" + marker + "[^=].*[^=]" + marker + "\\s*$", Pattern.MULTILINE );
        Matcher matcher = pattern.matcher( wikiText );

        if( matcher.find( endIdx ) ) {
            String chapter = wikiText.substring(startIdx, matcher.start() ).trim();
            if(header.equals(chapter)){
                return wikiText.substring(startIdx); //there is some things wrong in the structure
            }
            return chapter;
        }
            
        return wikiText.substring(startIdx);
    }
    
    /**
     * Liefert einen Substring mit der aktuellen Konjugation/Flexion Tabelle des Wikitextes
     */
    private String getTable( String wikiText, String tableName, int fromIndex ){
        int start = wikiText.indexOf( "{{" + tableName, fromIndex);
        if(start > 0){
            final int length = wikiText.length();
            int braces = 0;
            for(int end = start; end<length; end++){
                
                switch(wikiText.charAt( end )){
                    case '{':
                        braces++;
                        break;
                    case '}':
                        if(--braces == 0){
                            return wikiText.substring( start, end+1 );
                        }
                        break;
                }
            }
        }
        return "";
    }
    
    /**
     * Die Flexionen und Konjugationen eines Wortes sind durch bestimmte Phrasen gemarked.
     * @param baseWord Hauptwort, Name des Artikels
     * @param wikiText der gesamte Wikiartikel in Wikisyntax 
     * @param marker die Phrase, die eine bestimmte Konjugation markiert
     * @param fromIndex Startposition ab der die Phrase gesucht werden soll
     * @return true, wenn ein richtiges Wort gefunden werden konnte.
     */
    private final boolean searchWordAndAdd(String baseWord, String wikiText, String marker, int fromIndex){
        int idx1 = wikiText.indexOf( marker, fromIndex);
        if(idx1>0){
            idx1 += marker.length();
            int idx2 = indexOf( wikiText, new char[]{'|', '<', '}', '{'}, idx1);
            if(idx2>0){
                String word = wikiText.substring( idx1, idx2).trim();
                addFormatedWordPhrase( baseWord, marker, word );
                return true;
            }else{
                System.out.println("End not found for marker '" + marker + "' for base word '" + baseWord + "'");
            }
        }else{
            System.out.println("Marker '" + marker + "' was not found for base word '" + baseWord + "'");
        }
        return false;
    }
    
    
    private boolean searchExtendsWords(String baseWord, String wikiText, String marker){
        int idx1 = wikiText.indexOf( marker );
        if(idx1 >= 0){
            idx1 += marker.length();
            int idx2 = indexOf( wikiText, new char[]{'{', '='}, idx1);
            if(idx2<0){
                idx2 = wikiText.length();
            }
            String extendsWords = wikiText.substring( idx1, idx2 ); 
            addLinkWords( baseWord, extendsWords, marker );
            return true;
        }
        return false;
    }
    
    /**
     * Add all valid word that are in links (double brackets like [[word]]).
     * @param baseWord
     * @param wikiText the searching text
     * @param marker
     */
    private void addLinkWords( String baseWord, String wikiText, String marker ){
        int idx3 = wikiText.indexOf( "[[" );
        while(idx3 > 0){
            int idx4 = wikiText.indexOf( "]]", idx3 );
            if(idx4 > 0){
                if(idx3+2 < idx4){//leere Werte die vom Template entstehen, aus Performance gleich skippen 
                    String word = wikiText.substring( idx3+2, idx4 );
                    if (word.startsWith("WikiSaurus:")) {
                        word = word.substring(11).trim();
                    }
                    if (word.startsWith("Thesaurus:")) {
                        word = word.substring(10).trim();
                    }
                    if( word.indexOf( '|' ) > 0){
                        word = word.substring( 0, word.indexOf( '|' ) );
                    }
                    if(!addWordPhrase(word)){
                        System.out.println("Invalid Extend Word '" + word + "' for marker '" + marker + "' for base word '" + baseWord + "'");
                    }
                }
                idx3 = wikiText.indexOf( "[[", idx4 );
            }else{
                idx3 = -1;
            }
        }
    }
    
    /**
     * Add a formated word phrase like it used in some format tables.
     * @param baseWord the lemma word, for debugging
     * @param key the key of the format table, for debugging
     * @param phrase the word or phrase, can also be null or empty
     */
    private final void addFormatedWordPhrase( String baseWord, String key, String phrase ){
        if( phrase == null ) {
            return;
        }
        if( phrase.endsWith( "!" ) || phrase.endsWith("’")) {
            phrase = phrase.substring( 0, phrase.length() - 1 );
        }
        if( phrase.length() <= 1 ) {
            //empty Entry
            return;
        }
        if( phrase.equals( "{{fehlend}}" ) || phrase.equals( "---" ) || phrase.equals( "--" ) || phrase.equals("{{center")) {
            return;
        }
        int idx3 = phrase.indexOf( "(" );
        int idx4 = phrase.indexOf( ")", idx3 );
        if( idx3 >= 0 && idx4 > 0 ) {
            String word1 = phrase.substring( 0, idx3 );
            String word2 = phrase.substring( idx4 + 1 );
            String word3 = word1.length() + word2.length() > 0 ? word1 + word2 : phrase.substring( idx3 + 1, idx4 );
            if( addWordPhrase( word3 ) ) {
                addWordPhrase( word1 + phrase.substring( idx3 + 1, idx4 ) + word2 );
                return;
            } else {
                System.out.println( "Invalid Word '" + phrase + "' for marker '" + key + "' for base word '"
                                + baseWord + "'" );
            }
        } else {
            if( addWordPhrase( phrase ) ) {
                return;
            } else {
                System.out.println( "Invalid Word '" + phrase + "' for marker '" + key + "' for base word '"
                                + baseWord + "'" );
            }
        }
    }
    
    /**
     * Substantive sind alle mit Artikel abgelegt und einige Verben zerfallen bei der Konjugation 
     */
    private final boolean addWordPhrase(String phrase){
    	boolean isValid = true;
    	// recognize optional [e] or (e) and add both variants
    	int index = phrase.indexOf("[e]");
    	if (index > 0 && Character.isLetter(phrase.charAt(index-1))) {
    		for (int i = 0; i < 2; i++) {
        		addWordPhrase(phrase.substring(0, index) + (i==0?"e":"") + phrase.substring(index + 3));
			}
    	}
    	else {
	        String[] words = phrase.split( "(\\s|/|,|!)+" );
	        for( int i = 0; i < words.length; i++ ) {
	            String word = words[i];
	            if(word.length()>0){
	                if(super.isValidWord(word)){
	                    addWord(word);
	                }else{
	                    isValid = false;
	                }
	            }
	        }
    	}
        return isValid;
    }

    /**
     * Implementation of the template http://de.wiktionary.org/wiki/Vorlage:Deklinationsseite_Adjektiv
     * A sample can see at http://de.wiktionary.org/w/index.php?title=hoch_%28Deklination%29&action=edit
     * @param wikiText
     * @param idx
     */
    private void addDeklinationAdjektiv( Properties props ) {
        addDeklinationAdjektiv( props.getProperty("Positiv-Stamm") );
        addDeklinationAdjektiv( props.getProperty("Komparativ-Stamm") );
        addDeklinationAdjektiv( props.getProperty("Superlativ-Stamm") );
    }
    
    /**
     * Add the Adjective Declination for the given word root.
     * @param wordStamm the word root, can be null
     */
    private void addDeklinationAdjektiv( String wordStamm ) {
        if( wordStamm != null && super.isValidWord( wordStamm )) {
            addWord( wordStamm + "e" ); 
            addWord( wordStamm + "er" );
            addWord( wordStamm + "es" );
            addWord( wordStamm + "en" );
            addWord( wordStamm + "em" );
        }
    }
    
    /**
     * Add the declination of pronomen
     * @param word the lemma word
     * @param wikiText the wiki text
     */
    private void addIndefinitpronomen( String word, String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "Pronomina-Tabelle", 0 );
        if( props != null ){
            Set<Map.Entry<Object,Object>> entries = props.entrySet();
            for( Map.Entry<Object,Object> entry : entries ) {
                addFormatedWordPhrase( word, (String)entry.getKey(), (String)entry.getValue() );
            }
        }
    }
    
    /**
     * Implementation of the templates
     * http://de.wiktionary.org/wiki/Vorlage:Deutsch_Substantiv_Übersicht
     */
    private boolean addDeklinationSubstTable( String wikiText, int fromIndex, String baseWord ) {
        Properties props = BookUtils.parseRule( wikiText, "Deutsch Substantiv Übersicht", fromIndex );
        if( props != null ) {
            if( props.remove( "Bild" ) != null || props.remove( "Bild 1" ) != null ) {
                props.remove( "1" );
                props.remove( "2" );
                props.remove( "3" );
            }
            if( props.remove( "Bild 2" ) != null ) {
                props.remove( "4" );
                props.remove( "5" );
                props.remove( "6" );
            }
            if( props.remove( "Bild 3" ) != null ) {
                props.remove( "7" );
                props.remove( "8" );
                props.remove( "9" );
            }
            if( props.remove( "Bild 4" ) != null ) {
                props.remove( "10" );
                props.remove( "11" );
                props.remove( "12" );
            }
            if( props.remove( "Bild 5" ) != null ) {
                props.remove( "13" );
                props.remove( "14" );
                props.remove( "15" );
            }
            for( Entry entry : props.entrySet() ) {
                String word = (String)entry.getValue();
                addFormatedWordPhrase( baseWord, (String)entry.getKey(), word );
            }

            return true;
        }

        return false;
    }

    private String removeHtmlFormating( String word ) {
        int idx1 = word.indexOf( '<' );
        while( idx1 >= 0 ) {
            int idx2 = word.indexOf( '>', idx1 );
            if( idx2 > 0 ) {
                String html = word.substring(idx1+1, idx2).toLowerCase().trim();
                if(html.equals("br") || html.equals("p") || html.startsWith( "br " ) || html.startsWith( "p " ) ){
                    html = " ";
                }else{
                    html = "";
                }
                word = word.substring( 0, idx1 ) + html + word.substring( idx2 + 1 );
                idx1 = word.indexOf( '<' );
            } else {
                idx1 = -1;
            }
        }
        for (String blank : new String[]{"&nbsp;", "&#32;"}) {
        	idx1 = word.indexOf( blank );
        	while( idx1 >= 0 ) {
        		word = word.substring( 0, idx1 ) + " " + word.substring( idx1 + blank.length() );
        		idx1 = word.indexOf( blank );
        	}
        }
        return word;
    }

    /**
     * Implementation of the template http://de.wiktionary.org/wiki/Vorlage:Deutsch_Verb_unregelm%C3%A4%C3%9Fig
     */
    private void addKonjugationVerbUnregular( Properties props ) {
        String vorsilbe = props.getProperty( "1", "" );
        String stamm = props.getProperty( "2" );
        
        // Partizipien
        addWord( vorsilbe + stamm + "end" );        
        if( "ja".equals( props.getProperty( "gerund" ) ) ){
            String gerund = vorsilbe.length() > 0 ? vorsilbe + "zu" + stamm : stamm;
            addWord( gerund + "nder" );
            addWord( gerund + "nde" );
            addWord( gerund + "ndes" );
            addWord( gerund + "nden" );
            addWord( gerund + "ndem" );            
        }
        
        //Präsens
        if( stamm != null && super.isValidWord( stamm ) ) {
            addWord( stamm + "e" );
            addWord( stamm + "st" );
            addWord( stamm + "t" );
            addWord( stamm + "en" );
            addWord( stamm + "est" );
            addWord( stamm + "et" );
            if( vorsilbe.length() > 0 ){
                //Nebensatz
                addWord( vorsilbe + stamm );
                addWord( vorsilbe + stamm + "e" );
                addWord( vorsilbe + stamm + "st" );
                addWord( vorsilbe + stamm + "t" );
                addWord( vorsilbe + stamm + "en" );
                addWord( vorsilbe + stamm + "est" );
                addWord( vorsilbe + stamm + "et" );
            }
        }

        //Präteritum
        stamm = props.getProperty( "3" );
        if( stamm != null && super.isValidWord( stamm ) ) {
            addWord( stamm );
            addWord( stamm + "st" );
            addWord( stamm + "en" );
            addWord( stamm + "t" );
            if( vorsilbe.length() > 0 ){
                //Nebensatz
                addWord( vorsilbe + stamm );
                addWord( vorsilbe + stamm + "st" );
                addWord( vorsilbe + stamm + "en" );
                addWord( vorsilbe + stamm + "t" );
            }
        }

        //Präteritum Konjunktiv
        stamm = props.getProperty( "4" );
        if( stamm != null && super.isValidWord( stamm ) ) {
            addWord( stamm + "e" );
            addWord( stamm + "est" );
            addWord( stamm + "en" );
            addWord( stamm + "et" );
            if( vorsilbe.length() > 0 ){
                //Nebensatz
                addWord( vorsilbe + stamm );
                addWord( vorsilbe + stamm + "est" );
                addWord( vorsilbe + stamm + "en" );
                addWord( vorsilbe + stamm + "et" );
            }
        }

        // Partizip Perfect / Partizip 2
        stamm = props.getProperty( "5" );
        if( stamm != null && super.isValidWord( stamm ) ) {
            addWord( vorsilbe + stamm );
            addDeklinationAdjektiv( vorsilbe + stamm );
        }
    }
    
    /**
     * Implementation of the template http://de.wiktionary.org/wiki/Vorlage:Deutsch_Verb_schwach_untrennbar
     * Implementation of the template http://de.wiktionary.org/wiki/Vorlage:Deutsch_Verb_schwach_untrennbar_reflexiv
     */
    private void addKonjugationVerbSchwachUntrennbar( Properties props, boolean reflexiv ) {
        // Partizip Perfect / Partizip 2
        String partizip2 = props.getProperty( "6" );
        if( partizip2 != null && super.isValidWord( partizip2 ) ) {
            addWord( partizip2 );
            addDeklinationAdjektiv( partizip2);
        }

        String stamm4 = props.getProperty( "1" ) + props.getProperty( "2" ) + props.getProperty( "3" ) + props.getProperty( "4" );
        if( super.isValidWord( stamm4 ) ) {
            // Partizipien
            addWord( stamm4 + "nd" );
            if( !reflexiv ){
                addWord( stamm4 + "nder" );
                addWord( stamm4 + "nde" );
                addWord( stamm4 + "ndes" );
                addWord( stamm4 + "nden" );
                addWord( stamm4 + "ndem" );
            }
            
            String stamm3 = props.getProperty( "1" ) + props.getProperty( "2" ) + props.getProperty( "3" );
            //Präsens
            addWord( stamm4 );
            addWord( stamm3 + "st" );
            addWord( stamm3 + "t" );
            addWord( stamm3 + "est" );
            addWord( stamm3 + "e" );
            addWord( stamm3 + "et" );
            //Präterium
            addWord( stamm3 + "te" );
            addWord( stamm3 + "test" );
            addWord( stamm3 + "ten" );
            addWord( stamm3 + "tet" );
        }
    }
    
    /**
     * Implementation of the template http://de.wiktionary.org/wiki/Vorlage:Deutsch_Verb_schwach_trennbar
     * Implementation of the template http://de.wiktionary.org/wiki/Vorlage:Deutsch_Verb_schwach_trennbar_reflexiv
     */
    private void addKonjugationVerbSchwachTrennbar( Properties props, boolean reflexiv ) {
        // Partizip Perfect / Partizip 2
        String partizip2 = props.getProperty( "7" );
        if( partizip2 != null && super.isValidWord( partizip2 ) ) {
            addWord( partizip2 );
            addDeklinationAdjektiv( partizip2);
        }
        
        String vorsilbe = props.getProperty( "1" );
        String stamm4 = props.getProperty( "2" ) + props.getProperty( "3" ) + props.getProperty( "4" );
        String stamm5 = stamm4 + props.getProperty( "5" );
        String all4 = vorsilbe + stamm4;
        String all5 = vorsilbe + stamm5;
        
        addWord( vorsilbe + "zu" + stamm5 + "n" );
        // Partizipien
        addWord( all5 + "nd" );
        if( !reflexiv ){
            addWord( vorsilbe + "zu" + stamm5 + "nder" );
            addWord( vorsilbe + "zu" + stamm5 + "nde" );
            addWord( vorsilbe + "zu" + stamm5 + "ndes" );
            addWord( vorsilbe + "zu" + stamm5 + "nden" );
            addWord( vorsilbe + "zu" + stamm5 + "ndem" );
        }
        //Präsens
        addWord( stamm5 );
        addWord( stamm4 + "st" );
        addWord( stamm4 + "t" );
        addWord( stamm4 + "est" );
        addWord( stamm4 + "e" );
        addWord( stamm5 + "n" );
        addWord( stamm4 + "et" );
        addWord( all5 );
        addWord( all4 + "st" );
        addWord( all4 + "t" );
        addWord( all4 + "est" );
        addWord( all4 + "e" );
        addWord( all5 + "n" );
        addWord( all4 + "et" );
        //Präterium
        addWord( stamm4 + "te" );
        addWord( stamm4 + "test" );
        addWord( stamm4 + "ten" );
        addWord( stamm4 + "tet" );
        addWord( all4 + "te" );
        addWord( all4 + "test" );
        addWord( all4 + "ten" );
        addWord( all4 + "tet" );
    }
    
    /**
     * Implementation of the template http://de.wiktionary.org/wiki/Vorlage:Deutsch_Verb_schwach_doppelt_trennbar
     */
    private void addKonjugationVerbSchwachDoppeltTrennbar( Properties props ) {
        // Partizip Perfect / Partizip 2
        String partizip2 = props.getProperty( "8" );
        if( partizip2 != null && super.isValidWord( partizip2 ) ) {
            addWord( partizip2 );
            addDeklinationAdjektiv( partizip2);
        }
        
        String vorsilbe = props.getProperty( "1" ) + props.getProperty( "2" );;
        String stamm5 = props.getProperty( "3" ) + props.getProperty( "4" ) + props.getProperty( "5" );
        String stamm6 = stamm5 + props.getProperty( "6" );
        String all5 = vorsilbe + stamm5;
        String all6 = vorsilbe + stamm6;
        
        addWord( vorsilbe + "zu" + stamm6 + "n" );
        // Partizipien
        addWord( all6 + "nd" );
        addWord( vorsilbe + "zu" + stamm6 + "nder" );
        addWord( vorsilbe + "zu" + stamm6 + "nde" );
        addWord( vorsilbe + "zu" + stamm6 + "ndes" );
        addWord( vorsilbe + "zu" + stamm6 + "nden" );
        addWord( vorsilbe + "zu" + stamm6 + "ndem" );
        //Präsens
        addWord( stamm6 );
        addWord( stamm5 + "st" );
        addWord( stamm5 + "t" );
        addWord( stamm5 + "est" );
        addWord( stamm5 + "e" );
        addWord( stamm6 + "n" );
        addWord( stamm5 + "et" );
        addWord( all6 );
        addWord( all5 + "st" );
        addWord( all5 + "t" );
        addWord( all5 + "est" );
        addWord( all5 + "e" );
        addWord( all6 + "n" );
        addWord( all5 + "et" );
        //Präterium
        addWord( stamm5 + "te" );
        addWord( stamm5 + "test" );
        addWord( stamm5 + "ten" );
        addWord( stamm5 + "tet" );
        addWord( all5 + "te" );
        addWord( all5 + "test" );
        addWord( all5 + "ten" );
        addWord( all5 + "tet" );
    }
}
