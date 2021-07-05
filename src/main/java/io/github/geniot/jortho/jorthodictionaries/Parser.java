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

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import io.github.geniot.jortho.Utils;


/**
 * This class parse the XML file from a dump of the Wiktionary.
 * 
 * @author Volker Berlin
 */
public class Parser extends DefaultHandler{

    
    private final BookGenerator generator;
    
    private final int NONE  = 0;
    private final int TITLE = 1;
    private final int TEXT = 2;
    
    
    private int currentTag;
    private final StringBuilder data = new StringBuilder();
    private String word;
    private String text;
    
    Parser(BookGenerator generator, InputStream stream) throws Exception{
        this.generator = generator;
        
        System.setProperty("entityExpansionLimit", "100000000");
        //InputSource input = new InputSource(stream); 
        InputSource input = new InputSource( new InputStreamReader(stream, "utf8") ); // hack for bug http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=7156085
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        ParserAdapter pa = new ParserAdapter(sp.getParser());
        pa.setContentHandler(this);
        pa.parse(input);
    }
    
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        switch(currentTag){
            case TITLE:
            case TEXT:
                data.append( ch, start, length );
                break;
        }
    }

    
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        data.setLength( 0 );
        if("title".equals(localName)){
            currentTag = TITLE;
        }else
        if("text".equals(localName)){
            currentTag = TEXT;
        }
    }
    
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        switch(currentTag){
            case TITLE:
                word = data.toString();
                word = Utils.replaceUnicodeQuotation( word );
                if(!generator.isValidWord(word)){
                    word = null;
                }
                break;
            case TEXT:
                text = data.toString();
                break;
            default:
                if("page".equals(localName)){
                    if(word != null){
                        try{
                            generator.getBook().incTitleCount();
                            text = Utils.replaceUnicodeQuotation( text );
                            if(generator.isValidLanguage(word, text)){
                                generator.getBook().incLanguageTitleCount();
                                generator.addWord(word);
                            }
                        }catch(Throwable th){
                            th.printStackTrace();
                        }
                    }
                }
        }
        currentTag = NONE;
    }

}
