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
 * Created on 25.02.2008
 */
package io.github.geniot.jortho;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;

/**
 * Is used from CheckerMenu and CheckerPopup to handle the user events.
 * @author Volker Berlin
 */
public class CheckerListener implements PopupMenuListener, LanguageChangeListener {

    private final JComponent          menu;

    private Dictionary                dictionary;

    private Locale                    locale;

    private final SpellCheckerOptions options;

    /**
     * Create a PopupMenuListener
     * 
     * @param menu
     *            a JMenu or JPopuup
     * @param options
     *            current spell checker options
     */
    public CheckerListener( JComponent menu, SpellCheckerOptions options ) {
        this.menu = menu;
        this.options = options == null ? SpellChecker.getOptions() : options;
        SpellChecker.addLanguageChangeLister( this );
        dictionary = SpellChecker.getCurrentDictionary();
        locale = SpellChecker.getCurrentLocale();
    }

    /**
     * {@inheritDoc}
     */
    public void popupMenuCanceled( PopupMenuEvent e ) {
        /* empty */
    }

    /**
     * {@inheritDoc}
     */
    public void popupMenuWillBecomeInvisible( PopupMenuEvent e ) {
        /* empty */
    }

    /**
     * {@inheritDoc}
     */
    public void popupMenuWillBecomeVisible( PopupMenuEvent ev ) {
        if( dictionary == null) {
            menu.setEnabled( false );
            return;
        }

        JPopupMenu popup = (JPopupMenu)ev.getSource();

        Component invoker = popup.getInvoker();
        if( invoker instanceof JTextComponent ) {
            final JTextComponent jText = (JTextComponent)invoker;
            if( !jText.isEditable() ) {
                // Suggestions only for editable text components
                menu.setEnabled( false );
                return;
            }
            try {
                int offs = getCursorPosition( jText );
                if( offs < 0 ) {
                    // occur if there nothing under the mouse pointer
                    menu.setEnabled( false );
                    return;
                }
                
                // get the word from current position
                final int begOffs = Utilities.getWordStart( jText, offs );
                final int endOffs = Utilities.getWordEnd( jText, offs );
                final String word = jText.getText( begOffs, endOffs - begOffs );

                //find the first invalid word from current position, use the Tokenizer that it is ever compatible with the red zigzag line
                Tokenizer tokenizer = new Tokenizer( jText, dictionary, locale, offs, options );
                String invalidWord;
                do {
                    invalidWord = tokenizer.nextInvalidWord();
                } while( tokenizer.getWordOffset() < begOffs );
                menu.removeAll();

                if( !word.equals( invalidWord ) ) {
                    // the current word is not invalid
                    menu.setEnabled( false );
                    return;
                }

                List<Suggestion> list = dictionary.searchSuggestions( word );

                //Disable then menu item if there are no suggestions
                menu.setEnabled( list.size() > 0 );

                boolean needCapitalization = tokenizer.isFirstWordInSentence() && Utils.isFirstCapitalized( word );

                addSuggestionMenuItem( jText, begOffs, endOffs, list, needCapitalization );
                addMenuItemAddToDictionary( jText, word, list.size() > 0 );
            } catch( BadLocationException ex ) {
            	SpellChecker.getMessageHandler().handleException( ex );
            }
        }
    }
    
    /**
     * Get the cursor position for the popup menu
     * 
     * @param jText
     *            current JTextComponent
     * @return the current position
     * @throws BadLocationException
     *             should never occur
     */
    protected int getCursorPosition( JTextComponent jText ) throws BadLocationException {
        Caret caret = jText.getCaret();
        int offs;
        Point p = jText.getMousePosition();
        if( p != null ) {
            // use position from mouse click and not from editor cursor position 
            offs = jText.viewToModel( p );
            // calculate rectangle of line
            int startPos = Utilities.getRowStart( jText, offs );
            int endPos = Utilities.getRowEnd( jText, offs );
            Rectangle bounds = jText.modelToView( startPos ).union( jText.modelToView( endPos ) );
            if( !bounds.contains( p ) ){
                return -1; // mouse is outside of text
            }
        } else {
            offs = Math.min( caret.getDot(), caret.getMark() );
        }
        Document doc = jText.getDocument();
        if( offs > 0 && (offs >= doc.getLength() || Character.isWhitespace( doc.getText( offs, 1 ).charAt( 0 ) )) ) {
            // if the next character is a white space then use the word on the left site
            offs--;
        }
        return offs;
    }
    
    /**
     * Add menu items to the with suggestions to the menu.
     * 
     * @param jText
     *            current JTextComponent
     * @param begOffs
     *            offset of the current word in the JTextComponent, need for replacement
     * @param endOffs
     *            end of the current word in the JTextComponent, need for replacement
     * @param list
     *            a list with suggestions
     * @param needCapitalization
     *            if the first letter of the suggestion should capitalized
     */
    protected void addSuggestionMenuItem( final JTextComponent jText, final int begOffs, final int endOffs, List<Suggestion> list, boolean needCapitalization ) {
        for( int i = 0; i < list.size() && i < options.getSuggestionsLimitMenu(); i++ ) {
            Suggestion sugestion = list.get( i );
            String sugestionWord = sugestion.getWord();
            if( needCapitalization ) {
                sugestionWord = Utils.getCapitalized( sugestionWord );
            }
            JMenuItem item = new JMenuItem( sugestionWord );
            menu.add( item );
            final String newWord = sugestionWord;
            item.addActionListener( new ActionListener() {

                public void actionPerformed( ActionEvent e ) {
                    jText.setSelectionStart( begOffs );
                    jText.setSelectionEnd( endOffs );
                    jText.replaceSelection( newWord );
                }

            } );
        }
    }
    
    /**
     * Add the menu item "Add to Dictionary" at the end of the menu if a user dictionary is available.
     * 
     * @param jText
     *            current JTextComponent
     * @param word
     *            current word, which can be add
     * @param addSeparator
     *            true, add a separator before the menu item
     */
    protected void addMenuItemAddToDictionary( JTextComponent jText, String word, boolean addSeparator ) {
        UserDictionaryProvider provider = SpellChecker.getUserDictionaryProvider();
        if( provider == null ) {
            return;
        }
        JMenuItem addToDic = new JMenuItem( new AddWordAction( jText, word ) );
        if( addSeparator ) {
            if( menu instanceof JMenu ) {
                ((JMenu)menu).addSeparator();
            } else if( menu instanceof JPopupMenu ) {
                ((JPopupMenu)menu).addSeparator();
            }
        }
        menu.add( addToDic );
        menu.setEnabled( true );
    }

    /**
     * {@inheritDoc}
     */
    public void languageChanged( LanguageChangeEvent ev ) {
        dictionary = SpellChecker.getCurrentDictionary();
        locale = SpellChecker.getCurrentLocale();
    }
}
