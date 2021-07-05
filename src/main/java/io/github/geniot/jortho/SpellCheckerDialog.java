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
 *  Created on 10.11.2005
 */
package io.github.geniot.jortho;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * The Dialog for continues checking the orthography.
 * @author Volker Berlin
 */
class SpellCheckerDialog extends JDialog implements ActionListener {

    private JTextComponent jText;
    private io.github.geniot.jortho.Dictionary dictionary;
    private Tokenizer tok;
    private boolean isDictionaryModify;
    private final SpellCheckerOptions options;
    

    final private JLabel notFound = Utils.getLabel( null );
    final private JTextField word = Utils.getTextField(); 
    final private JList suggestionsList = Utils.getList();
    
    final private JButton ignore      = Utils.getButton( "ignore" );
    final private JButton ignoreAll   = Utils.getButton( "ignoreAll" );
    final private JButton addToDic    = Utils.getButton( "addToDictionary" );
    final private JButton editDic     = Utils.getButton( "editDictionary" );
    final private JButton change      = Utils.getButton( "change" );
    final private JButton changeAll   = Utils.getButton( "changeAll" );
    final private JButton about       = Utils.getButton( "about" );
    final private JButton close       = Utils.getButton( "close" );
    
    /** List of ignore all words */  
    final private ArrayList<String> ignoreWords = new ArrayList<String>();
    /** Map of change all words */
    final private HashMap<String,String> changeWords = new HashMap<String,String>();

    SpellCheckerDialog(Dialog owner) throws HeadlessException {
        this(owner, false, null);
    }


    SpellCheckerDialog(Dialog owner, boolean modal, SpellCheckerOptions options){
        super(owner, modal);
        this.options = options == null ? SpellChecker.getOptions() : options;
        init();
    }


    SpellCheckerDialog(Frame owner){
        this(owner, false, null);
    }
    

    SpellCheckerDialog(Frame owner, boolean modal, SpellCheckerOptions options){
        super(owner, modal);
        this.options = options == null ? SpellChecker.getOptions() : options;
        init();
    }

    final private void init(){
        Utils.setDialogIcon( this );
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        Container cont = getContentPane();
        cont.setLayout(new GridBagLayout());
        Insets insetL = new Insets(8,8,0,8);
        Insets insetR = new Insets(8,0,0,8);
        
        cont.add( Utils.getLabel(Utils.getResource("notInDictionary")+":"), new GridBagConstraints( 1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST ,GridBagConstraints.NONE, insetL, 0, 0));
        
        notFound.setForeground(Color.RED);
        notFound.setText("xxxxxxxxxx");
        cont.add( notFound, new GridBagConstraints( 2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST ,GridBagConstraints.NONE, insetL, 0, 0));
        
        cont.add( word, new GridBagConstraints( 1, 2, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST ,GridBagConstraints.HORIZONTAL, insetL, 0, 0));
        
        cont.add( Utils.getLabel(Utils.getResource("suggestions")+":"), new GridBagConstraints( 1, 3, 2, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST ,GridBagConstraints.NONE, insetL, 0, 0));
        JScrollPane scrollPane = new JScrollPane(suggestionsList);
        cont.add( scrollPane, new GridBagConstraints( 1, 4, 2, 6, 1.0, 1.0, GridBagConstraints.NORTHWEST ,GridBagConstraints.BOTH, new Insets(8,8,8,8), 0, 0));
        
        cont.add( ignore,       new GridBagConstraints( 3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST ,GridBagConstraints.HORIZONTAL, insetR, 0, 0));
        cont.add( ignoreAll,    new GridBagConstraints( 3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST ,GridBagConstraints.HORIZONTAL, insetR, 0, 0));
        cont.add( addToDic,     new GridBagConstraints( 3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST ,GridBagConstraints.HORIZONTAL, insetR, 0, 0));
        cont.add( editDic,      new GridBagConstraints( 3, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST ,GridBagConstraints.HORIZONTAL, insetR, 0, 0));
        cont.add( change,       new GridBagConstraints( 3, 5, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST ,GridBagConstraints.HORIZONTAL, insetR, 0, 0));
        cont.add( changeAll,    new GridBagConstraints( 3, 6, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST ,GridBagConstraints.HORIZONTAL, insetR, 0, 0));
        cont.add( about,        new GridBagConstraints( 3, 7, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST ,GridBagConstraints.HORIZONTAL, insetR, 0, 0));
        cont.add( close,        new GridBagConstraints( 3, 8, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST ,GridBagConstraints.HORIZONTAL, insetR, 0, 0));
        cont.add( Utils.getLabel( null ), new GridBagConstraints( 3, 9, 1, 1, 0.0, 1.0, GridBagConstraints.NORTHWEST ,GridBagConstraints.HORIZONTAL, insetR, 0, 0));
        
        ignore.addActionListener(this);
        ignoreAll.addActionListener(this);
        addToDic.addActionListener(this);
        editDic.addActionListener(this);
        change.addActionListener(this);
        changeAll.addActionListener(this);
        about.addActionListener(this);
        close.addActionListener(this);
        
        //ESCAPE Taste
        close.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0, false ), "ESCAPE" );
        close.getActionMap().put( "ESCAPE", new AbstractAction() {
            public void actionPerformed( ActionEvent e ) {
                dispose();
            }
        } );
        
        word.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent ev){
                // disable "Add To Dictionary" if word was changed, not this word would added else the original misspelled word
                addToDic.setEnabled( false );
            }
            public void insertUpdate(DocumentEvent ev){
                // disable "Add To Dictionary" if word was changed, not this word would added else the original misspelled word
                addToDic.setEnabled( false );
            }
            public void removeUpdate(DocumentEvent ev){
                // disable "Add To Dictionary" if word was changed, not this word would added else the original misspelled word
                addToDic.setEnabled( false );
            }
        });
        
        suggestionsList.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent ev){
                // Update the word field if a suggestion is click
                if( !ev.getValueIsAdjusting() && suggestionsList.getSelectedIndex() >= 0 ){
                    word.setText( (String)suggestionsList.getSelectedValue() );
                    addToDic.setEnabled( true );
                }
            }
        });
        
        suggestionsList.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                if( e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 ) {
                    // Change the word on a double click
                    if( suggestionsList.getSelectedIndex() >= 0 ) {
                        word.setText( (String)suggestionsList.getSelectedValue() );
                        actionPerformed( new ActionEvent( suggestionsList, 0, null ) );
                    }
                }
            }
        } );

        boolean isUserDictionary = SpellChecker.getUserDictionaryProvider() != null;
        addToDic.setEnabled( isUserDictionary );
        editDic.setEnabled( isUserDictionary );
        pack();
    }
    
    
    public void show(JTextComponent jTextComponent, Dictionary dic, Locale loc ) {
        this.jText = jTextComponent;
        this.dictionary = dic;
        change.requestFocus();
        setTitle( Utils.getResource("spelling") + ": " + loc.getDisplayLanguage() );

        tok = new Tokenizer( jTextComponent, dic, loc, options );
        
        if( searchNext() ){
            // if the JTextComponent is large and has a scrollpane then Java use the bounds
            // and not the visible rect. This is bad
            Container parent = jTextComponent;
            while( parent != null && !(parent instanceof JScrollPane) ) {
                if( parent instanceof JComponent ) {
                    JComponent jcomp = (JComponent)parent;
                    if( jcomp.getVisibleRect().height == jcomp.getBounds().height ) {
                        break;
                    }
                }
                if( parent.getParent() != null ) {
                    parent = parent.getParent();
                } else {
                    break;
                }
            }
            
            setLocationRelativeTo( parent );
            setVisible( true );
        }
    }
    
    /**
     * Search the next misspelling word. If found it then refresh the dialog with the new information.
     * ignoreWords and changeWords will handle automatically.
     * @return true, if found a spell error.
     */
    private boolean searchNext() {
        String wordStr;
        while( true ) {
            wordStr = tok.nextInvalidWord();
            if( wordStr == null ) {
                dispose();
                String title = SpellChecker.getApplicationName();
                if(title == null){
                    title = this.getTitle();
                }
                SpellChecker.getMessageHandler().handleInformation( getParent(), title, Utils.getResource( "msgFinish" ) );
                return false;
            }
            if( ignoreWords.contains( wordStr ) ) {
                continue;
            }
            String changeTo = changeWords.get( wordStr );
            if( changeTo != null ) {
                replaceWord( wordStr, changeTo );
                continue;
            }
            break;
        }
        word.setText( wordStr );
        notFound.setText( wordStr );

        List<Suggestion> list = dictionary.searchSuggestions( wordStr );
        
        boolean needCapitalization = tok.isFirstWordInSentence() && Utils.isFirstCapitalized( wordStr );

        Vector<String> suggestionsVector = new Vector<String>();
        for( int i = 0; i < list.size() && i < options.getSuggestionsLimitDialog(); i++ ) {
            Suggestion sugestion = list.get( i );
            String newWord = sugestion.getWord();
            if( needCapitalization ) {
                newWord = Utils.getCapitalized( newWord );
            }
            if( i == 0 )
                word.setText( newWord );
            suggestionsVector.add( newWord );
        }
        suggestionsList.setListData( suggestionsVector );
        
        addToDic.setEnabled( true );
        return true;
    }
    
    
    public void actionPerformed( ActionEvent ev ) {
        Object source = ev.getSource();
        if( source == ignore ) {
            searchNext();
        } else if( source == about ) {
            new AboutDialog( this ).setVisible( true );
        } else if( source == close ) {
            dispose();
        } else{
            String newWord = word.getText();
            String oldWord = notFound.getText();
            if( source == ignoreAll ) {
                ignoreWords.add( oldWord );
                searchNext();
            } else if( source == addToDic ) {
                UserDictionaryProvider provider = SpellChecker.getUserDictionaryProvider();
                if( provider != null ) {
                    provider.addWord( oldWord );
                }
                dictionary.add( oldWord );
                dictionary.trimToSize();
                isDictionaryModify = true;
                searchNext();
            } else if( source == editDic ) {
                new DictionaryEditDialog( this ).setVisible( true );
            } else if( source == change || source == suggestionsList ) {
                replaceWord( oldWord, newWord );
                searchNext();
            } else if( source == changeAll ) {
                changeWords.put( oldWord, newWord );
                replaceWord( oldWord, newWord );
                searchNext();
            }
        }
    }
    
    private void replaceWord( String oldWord, String newWord ) {
        jText.setSelectionStart( tok.getWordOffset() );
        jText.setSelectionEnd( tok.getWordOffset() + oldWord.length() );
        jText.replaceSelection( newWord );
        tok.updatePhrase();
    }


    @Override
    public void dispose(){
        super.dispose();
        if( isDictionaryModify ){
            AutoSpellChecker.refresh( jText );
        }
    }
}
