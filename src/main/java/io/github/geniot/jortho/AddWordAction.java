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
 * Created on 10.06.2010
 */
package io.github.geniot.jortho;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.text.JTextComponent;

public class AddWordAction extends AbstractAction {

    private String         word;

    private JTextComponent jText;

    /**
     * Create a action to add a word to the current user dictionary.
     * 
     * @param jText
     *            component that need refresh after adding to remove the red zigzag line
     * @param word
     *            the word that can be added
     */
    public AddWordAction( JTextComponent jText, String word ) {
        this( jText, word, Utils.getResource( "addToDictionary" ) );
    }

    /**
     * Create a action to add a word to the current user dictionary.
     * 
     * @param jText
     *            component that need refresh after adding to remove the red zigzag line
     * @param word
     *            the word that can be added
     * @param label
     *            the display text of the action
     */
    public AddWordAction( JTextComponent jText, String word, String label ) {
        super( label );
        this.word = word;
        this.jText = jText;
    }

    /**
     * Add the word to the current user directory.
     */
    public void actionPerformed( ActionEvent arg0 ) {
        UserDictionaryProvider provider = SpellChecker.getUserDictionaryProvider();
        if( provider != null ) {
            provider.addWord( word );
        }
        Dictionary dictionary = SpellChecker.getCurrentDictionary();
        dictionary.add( word );
        dictionary.trimToSize();
        AutoSpellChecker.refresh( jText );
    }

}
