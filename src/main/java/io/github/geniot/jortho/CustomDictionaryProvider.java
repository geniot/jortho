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
 * Created on 04.04.2009
 */
package io.github.geniot.jortho;

import java.util.Iterator;
import java.util.Locale;



/**
 * Interface to be implemented by an additional dictionary. This can be used to add an expert
 * dictionary like a medical dictionary or a chemical dictionary.
 * @author Volker Berlin
 */
public interface CustomDictionaryProvider{

    /**
     * Gets the list of custom words for this locale. The implementation can decide if there is a
     * custom dictionary for each available language or only one. This method is called before the
     * language change event is fired if the language is changed and the dictionary is loaded.
     * 
     * @param locale The locale of the selected language.
     * @return A list of words delimited with line breaks. It can be null.
     */
    Iterator<String> getWords(Locale locale);
}
