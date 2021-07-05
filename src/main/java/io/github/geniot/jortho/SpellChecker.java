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
 *  Created on 05.12.2007
 */
package io.github.geniot.jortho;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.WeakHashMap;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 * This class is the major class of the spell checker JOrtho (Java Orthography Checker). 
 * In the most cases this is the only class that you need to add spell checking to your application.
 * First you need to do a one-time registration of your dictionaries. In standalone applications this can
 * look like:
 * <code>
 * SpellChecker.registerDictionaries( new URL("file", null, ""), "en,de", "de" );
 * </code>
 * and in an applet this will look like:
 * <code>
 * SpellChecker.registerDictionaries( getCodeBase(), "en,de", "en" );
 * </code>
 * After this you can register your text component that should have the spell checker features
 * (Highlighter, context menu, spell checking dialog). 
 * This looks like:<code>
 * JTextPane text = new JTextPane();
 * SpellChecker.register( text );
 * </code>
 * @author Volker Berlin
 */
public class SpellChecker {
    
    private final static ArrayList<LanguageAction> languages = new ArrayList<LanguageAction>();
    private static Dictionary currentDictionary;
    private static Locale currentLocale;
    private static UserDictionaryProvider userDictionaryProvider;
    private static CustomDictionaryProvider customDictionaryProvider;
    private final static java.util.Map<LanguageChangeListener, Object> listeners = Collections.synchronizedMap( new WeakHashMap<LanguageChangeListener, Object>() );
    private static String applicationName;
    private static final SpellCheckerOptions globalOptions = new SpellCheckerOptions();
    private static MessageHandler messageHandler = new DefaultMessageHandler( null );
    private static CustomUIProvider customUIProvider;
    
    /**
     * Duplicate of Action.SELECTED_KEY since 1.6
     */
    static final String SELECTED_KEY = "SwingSelectedKey";
    
    /**
     * There is no instance needed of SpellChecker. All methods are static.
     */
    private SpellChecker(){/*nothing*/}
    
    /**
     * Sets the UserDictionaryProvider. This is needed if the user should be able to add their own words.
     * This method must be called before {@link #registerDictionaries(URL, String, String)}.
     * 
     * @param userDictionaryProvider the new UserDictionaryProvider or null
     * @see #setCustomDictionaryProvider(CustomDictionaryProvider)
     * @see #getUserDictionaryProvider()
     * @see #registerDictionaries(URL, String, String)
     */
    public static void setUserDictionaryProvider( UserDictionaryProvider userDictionaryProvider ) {
        SpellChecker.userDictionaryProvider = userDictionaryProvider;
    }

    /**
     * Gets the currently set UserDictionaryProvider. If none has been set then null is returned.
     * 
     * @see #setUserDictionaryProvider(UserDictionaryProvider)
     */
    public static UserDictionaryProvider getUserDictionaryProvider() {
        return SpellChecker.userDictionaryProvider;
    }
    
    /**
     * Set a CustomDictionaryProvider. This can be used to add an expert dictionary
     * like a medical dictionary or a chemical dictionary. 
     * 
     * @param customDictionaryProvider the new CustomDictionaryProvider or null
     * @see #setUserDictionaryProvider(UserDictionaryProvider)
     * @see #registerDictionaries(URL, String, String)
     */
    public static void setCustomDictionaryProvider( CustomDictionaryProvider customDictionaryProvider ) {
        SpellChecker.customDictionaryProvider = customDictionaryProvider;
    }
    
    /**
     * Gets the currently set CustomDictionaryProvider. If none has been set then null is returned.
     * 
     * @see #setCustomDictionaryProvider(CustomDictionaryProvider)
     */
    public static CustomDictionaryProvider getCustomDictionaryProvider() {
        return SpellChecker.customDictionaryProvider;
    }
    
    /**
     * Set the message handler used for handling errors and information messages. 
     * 
     * @param messageHandler the new MessageHandler or null (DefaultMessageHandler will be used)
     */
    public static void setMessageHandler( MessageHandler messageHandler ) {
        if( messageHandler == null ) {
            throw new IllegalArgumentException();
        }
        SpellChecker.messageHandler = messageHandler;
    }

    /**
     * Gets the currently set message handler. 
     * 
     * @see #setMessageHandler(MessageHandler)
     * @return the message handler, never null
     */
    public static MessageHandler getMessageHandler() {
        return SpellChecker.messageHandler;
    }

    /**
     * Set a CustomUIProvider. This can be used to add an expert UI provider
     * that constructs advances UI components for an application that needs
     * common components that are more than just default Swing components
     *
     * @param customUIProvider the new CustomUIProvider or null
     */
    public static void setCustomUIProvider( CustomUIProvider customUIProvider ) {
        SpellChecker.customUIProvider = customUIProvider;
    }

    /**
     * Gets the currently set CustomUIProvider. If none has been set then null is returned.
     *
     * @see #setCustomUIProvider(CustomUIProvider)
     */
    public static CustomUIProvider getCustomUIProvider() {
        return SpellChecker.customUIProvider;
    }

    /**
     * Registers the available dictionaries. The dictionaries' URLs must have the form "dictionary_xx.xxxxx" and must be
     * relative to the baseURL. The available languages and extension of the dictionaries is load from a configuration file.
     * The configuration file must also relative to the baseURL and must be named dictionaries.cnf, dictionaries.properties or
     * dictionaries.txt. If the dictionary of the active Locale does not exist, the first dictionary is loaded. There is
     * only one dictionary loaded in memory at a given time. 
     * You can download the dictionary files from http://sourceforge.net/projects/jortho/files/Dictionaries/
     * The configuration file has a Java Properties format. Currently there are the follow options:
     * <ul>
     * <li>languages</li>
     * <li>extension</li>
     * </ul>
     * <b>Samples:</b> <code>
     * // Load the configuration and dictionaries from the current working directory and use the current locale or the first language as default 
     * SpellChecker.registerDictionaries( null, null );
     * 
     * // Load the configuration and dictionaries from the sub directory "dict"
     * SpellChecker.registerDictionaries( new URL( "file", null, "dict" ), null );
     * 
     * // Load the configuration and dictionaries from a web server and activate English as language 
     * SpellChecker.registerDictionaries( new URL( "http://MyWebServer/dictionries/" ), "en" );
     * 
     * // Load the configuration and dictionaries from the same web location like the applet and use the German dictionary as default 
     * SpellChecker.registerDictionaries( myApplet.getCodeBase(), "de" );
     * 
     * // Sample content from a file dictionaries.cnf
     * extension=.ortho
     * languages=de,en,it,fr,es,ru
     * </code>
     * 
     * @param baseURL
     *            the base URL where the dictionaries and configuration file can be found. If null then first in the classloader root is searched.
     *            After it the URL("file", null, "") is used which is equals to the current working directory.
     * @param activeLocale
     *            the locale that should be loaded and made active. If null or empty then the default locale is used.
     * @see #setUserDictionaryProvider(UserDictionaryProvider)
     * @see #registerDictionaries(URL, String, String)
     * @see #registerDictionaries(URL, String, String, String)           
     */
    public static void registerDictionaries( URL baseURL, String activeLocale ) {
        if( baseURL == null ){
            try {
                baseURL = SpellChecker.class.getResource( "/dictionaries.cnf" );
                if( baseURL != null ) {
                    baseURL = new URL( baseURL, "." );
                } else {
                    baseURL = new URL( "file", null, "" );
                }
            } catch( MalformedURLException e ) {
                // should never occur because the URL is valid
            	SpellChecker.getMessageHandler().handleException( e );
            }
        }
        InputStream input;
        try {
            input = new URL( baseURL, "dictionaries.cnf" ).openStream();
        } catch( Exception e1 ) {
            try {
                input = new URL( baseURL, "dictionaries.properties" ).openStream();
            } catch( Exception e2 ) {
                try {
                    input = new URL( baseURL, "dictionaries.txt" ).openStream();
                } catch( Exception e3 ) {
                    System.err.println( "JOrtho configuration file not found!" );
                	SpellChecker.getMessageHandler().handleException( e1 );
                	SpellChecker.getMessageHandler().handleException( e2 );
                	SpellChecker.getMessageHandler().handleException( e3 );
                    return;
                }
            }
        }
        Properties props = new Properties();
        try {
            props.load( input );
        } catch( IOException e ) {
        	SpellChecker.getMessageHandler().handleException( e );
            return;
        }
        String availableLocales = props.getProperty( "languages" );
        String extension = props.getProperty( "extension", ".ortho" );
        registerDictionaries( baseURL, availableLocales, activeLocale, extension );
    }

    /**
     * Registers the available dictionaries. The dictionaries' URLs must have the form "dictionary_xx.ortho" and must be
     * relative to the baseURL. If the dictionary of the active Locale does not exist, the first dictionary is loaded.
     * There is only one dictionary loaded in memory at a given time.
     * You can download the dictionary files from http://sourceforge.net/projects/jortho/files/Dictionaries/
     * 
     * <p><b>Samples:</b> <code>
     * // Load the dictionaries from the current working directory and use the current locale or the first language as default 
     * SpellChecker.registerDictionaries( null, "de,en", null );
     * 
     * // Load the dictionaries from the sub directory "dict"
     * SpellChecker.registerDictionaries( new URL( "file", null, "dict" ), "de,en", null );
     * 
     * // Load the dictionaries from a web server and activate English as language 
     * SpellChecker.registerDictionaries( new URL( "http://MyWebServer/dictionries/" ), "de,en", "en" );
     * 
     * // Load the dictionaries from the same web location like the applet and use the German dictionary as default 
     * SpellChecker.registerDictionaries( myApplet.getCodeBase(), "de,en", "de" );
     * 
     * </code>
     * 
     * @param baseURL
     *            the base URL where the dictionaries can be found. If null then URL("file", null, "") is used.
     * @param availableLocales
     *            a comma separated list of locales
     * @param activeLocale
     *            the locale that should be loaded and made active. If null or empty then the default locale is used.
     * @see #setUserDictionaryProvider(UserDictionaryProvider)
     * @see #registerDictionaries(URL, String)
     * @see #registerDictionaries(URL, String, String, String)
     */
    public static void registerDictionaries( URL baseURL, String availableLocales, String activeLocale ) {
        registerDictionaries( baseURL, availableLocales, activeLocale, ".ortho" );
    }

    /**
     * Registers the available dictionaries. The dictionaries' URLs must have the form "dictionary_xx.xxxxx" and must be
     * relative to the baseURL. The extension can be set via parameter.
     * If the dictionary of the active Locale does not exist, the first dictionary is loaded.
     * There is only one dictionary loaded in memory at a given time.
     * You can download the dictionary files from http://sourceforge.net/projects/jortho/files/Dictionaries/
     * 
     * <p><b>Samples:</b> <code>
     * // Load the dictionaries from the current working directory 
     * // and use the current locale or the first language as default.
     * // The dictionaries must be named dictionary_de.ortho and dictionary_en.ortho
     * SpellChecker.registerDictionaries( null, "de,en", null, ".ortho" );
     * 
     * // Load the dictionaries from the sub directory "dict"
     * // and use the current locale or the first language as default.
     * // The dictionaries must be named dict/dictionary_de.ortho and dict/dictionary_en.ortho
     * SpellChecker.registerDictionaries( new URL( "file", null, "dict" ), "de,en", null, ".ortho" );
     * 
     * // Load the dictionaries from a web server and activate English as language 
     * // The dictionaries must be named http://MyWebServer/dictionries/dictionary_de.bin and http://MyWebServer/dictionries/dictionary_en.bin
     * SpellChecker.registerDictionaries( new URL( "http://MyWebServer/dictionries/" ), "de,en", "en", ".bin" );
     * 
     * // Load the configuration from the same web location like the applet and use the German dictionary as default 
     * // The dictionaries must be named dictionary_de.bin and dictionary_en.bin in the codebase
     * SpellChecker.registerDictionaries( myApplet.getCodeBase(), "de,en", "de", ".bin" );
     * 
     * </code>
     * 
     * @param baseURL
     *            the base URL where the dictionaries can be found. If null then URL("file", null, "") is used.
     * @param availableLocales
     *            a comma separated list of locales
     * @param activeLocale
     *            the locale that should be loaded and made active. If null or empty then the default locale is used.
     * @param extension
     *            the file extension of the dictionaries. Some web server like the IIS6 does not support the default ".ortho".
     * @see #setUserDictionaryProvider(UserDictionaryProvider)
     * @see #registerDictionaries(URL, String)
     * @see #registerDictionaries(URL, String, String)
     */
    public static void registerDictionaries( URL baseURL, String availableLocales, String activeLocale, String extension ) {
        if( baseURL == null ){
            try {
                baseURL = new URL("file", null, "");
            } catch( MalformedURLException e ) {
                // should never occur because the URL is valid
            	SpellChecker.getMessageHandler().handleException( e );
            }
        }
        if( activeLocale == null ) {
            activeLocale = "";
        }
        activeLocale = activeLocale.trim();
        if( activeLocale.length() == 0 ) {
            activeLocale = Locale.getDefault().getLanguage();
        }
        
        boolean activeSelected = false;
        for( String locale : availableLocales.split( "," ) ) {
            locale = locale.trim().toLowerCase();
            if(locale.length() > 0){
                LanguageAction action = new LanguageAction( baseURL, new Locale( locale ), extension );
                languages.remove( action );
                languages.add( action );
                if( locale.equals( activeLocale ) ) {
                    action.actionPerformed( null );
                    activeSelected = true;
                }
            }
        }
        // if nothing selected then select the first entry
        if( !activeSelected && languages.size() > 0 ) {
            LanguageAction action = languages.get( 0 );
            action.actionPerformed( null );
        }
        
        //sort the display names in order of the current language 
        Collections.sort( languages );
    }
    
    /**
     * Activate the spell checker for the given <code>JTextComponent</code>. The call is equal to register( text,
     * true, true ).
     * 
     * @param text
     *            the JTextComponent
     * @throws NullPointerException
     *             if text is null
     */
    public static void register( final JTextComponent text) throws NullPointerException{
        register( text, true, true, true, true );
    }

    /**
     * Activates the spell checker for the given <code>JTextComponent</code>. You do not need to unregister if the
     * JTextComponent is not needed anymore.
     * 
     * @param text
     *            the JTextComponent
     * @param hasPopup
     *            if true, the JTextComponent is to have a popup menu with the menu item "Orthography" and "Languages".
     * @param submenu
     *            if true, the popup has a sub menu           
     * @param hasShortKey
     *            if true, pressing the F7 key will display the spell check dialog.
     * @param hasAutoSpell
     *            if true, the JTextComponent has a auto spell checking.
     * @throws NullPointerException
     *             if text is null
     */
    public static void register( final JTextComponent text, boolean hasPopup, boolean submenu, boolean hasShortKey, boolean hasAutoSpell ) throws NullPointerException {
        if( hasPopup ) {
            enablePopup( text, true, submenu );
        }
        if( hasShortKey ) {
            enableShortKey( text, true );
        }
        if( hasAutoSpell ) {
            enableAutoSpell( text, true );
        }
    }
    
    /**
     * Removes all spell checker features from the JTextComponent. This does not need to be called
     * if the text component is no longer needed.
     * @param text the JTextComponent
     */
    public static void unregister( JTextComponent text ){
        enableShortKey( text, false );
        enablePopup( text, false, false );
        enableAutoSpell( text, false );
    }
    
    /**
     * Enable or disable the F7 key. Pressing the F7 key will display the spell check dialog. This also
     * register an Action with the name "spell-checking".
     * @param text the JTextComponent that should change
     * @param enable true, enable the feature.
     */
    public static void enableShortKey( final JTextComponent text, boolean enable ){
        enableShortKey( text, enable, null );
    }
    
    /**
     * Enable or disable the F7 key. Pressing the F7 key will display the spell check dialog. This also
     * register an Action with the name "spell-checking".
     * @param text the JTextComponent that should change
     * @param enable true, enable the feature.
     * @param options override the default options for this menu.
     */
    public static void enableShortKey( final JTextComponent text, boolean enable, final SpellCheckerOptions options ){
        if( enable ){
            text.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_F7, 0 ), "spell-checking" );
            text.getActionMap().put( "spell-checking", new AbstractAction(){
                public void actionPerformed( ActionEvent e ) {
                    showSpellCheckerDialog( text, options );
                }
            });
        }else{
            text.getActionMap().remove( "spell-checking" ); 
        }
    }
    
    /**
     * Show the Spell Checker dialog for the given JTextComponent. It will be do nothing if
     * the JTextComponent is not editable or there are no dictionary loaded.
     * The action for this method can you receive via:
     * <code>
     * Action action = text.getActionMap().get("spell-checking");
     * </code>
     * The action is only available if you have enable the short key (F7).
     * @param text JTextComponent to check
     * @param options override the default options for this menu.
     */
    public static void showSpellCheckerDialog( final JTextComponent text, SpellCheckerOptions options ) {
        if( !text.isEditable() ) {
            // only editable text component have spell checking
            return;
        }
        Dictionary dictionary = currentDictionary;
        if( dictionary != null ) {
            Window parent = SwingUtilities.getWindowAncestor( text );
            SpellCheckerDialog dialog;
            if( parent instanceof Frame ) {
                dialog = new SpellCheckerDialog( (Frame)parent, true, options );
            } else {
                dialog = new SpellCheckerDialog( (Dialog)parent, true, options );
            }
            dialog.show( text, dictionary, currentLocale );
        }
    }
    
    /**
     * Enable or disable the popup menu with the menu item "Orthography" and "Languages" or only suggestion. 
     * @param text the JTextComponent that should change
     * @param submenu true, menu item "Orthography" and "Languages"; false, only suggestions
     * @param enable true, enable the feature.
     */
    public static void enablePopup( JTextComponent text, boolean enable, boolean submenu ){
        if( enable ){
            final JPopupMenu menu;
            if( submenu ){
                menu = new JPopupMenu();
                menu.add( createCheckerMenu() );
                menu.add( createLanguagesMenu() );
            } else {
                menu = createCheckerPopup();
            }
            text.addMouseListener( new PopupListener(menu) );
        } else {
            for(MouseListener listener : text.getMouseListeners()){
                if(listener instanceof PopupListener){
                    text.removeMouseListener( listener );
                }
            }
        }
    }
    
    /**
     * Enable or disable the auto spell checking feature (red zigzag line) for a text component.
     * If you change the document then you need to reenable it.
     * 
     * @param text
     *            the JTextComponent that should change
     * @param enable
     *            true, enable the feature.
     */
    public static void enableAutoSpell( JTextComponent text, boolean enable ){
        enableAutoSpell( text, enable, null );
    }

    /**
     * Enable or disable the auto spell checking feature (red zigzag line) for a text component. If you change the
     * document then you need to reenable it.
     * 
     * @param text
     *            the JTextComponent that should change
     * @param enable
     *            true, enable the feature.
     * @param options
     *            override the default options for this menu.
     */
    public static void enableAutoSpell( JTextComponent text, boolean enable, SpellCheckerOptions options ){
        if( enable ){
            new AutoSpellChecker( text, options );
        } else {
            AutoSpellChecker.disable( text );
        }
    }
    
    /**
     * Adds a LanguageChangeListener. You do not need to remove it if the LanguageChangeListener is not needed
     * anymore. You need a hard reference to the listener because the SpellChecker hold only a WeakReference.
     * 
     * @param listener
     *            listener to add
     * @see LanguageChangeListener
     */
    public static void addLanguageChangeLister(LanguageChangeListener listener){
        listeners.put( listener, null );
    }
    
    /**
     * Removes the LanguageChangeListener.
     * @param listener listener to remove
     */
    public static void removeLanguageChangeLister(LanguageChangeListener listener){
        listeners.remove( listener );
    }
    
    /**
     * Helper method to fire an Language change event.
     */
    private static void fireLanguageChanged( Locale oldLocale ) {
        LanguageChangeEvent ev = new LanguageChangeEvent( currentLocale, oldLocale );
        
        Object[] list;
        synchronized( listeners ) {
            list = listeners.keySet().toArray();
        }
        for( Object listener : list ) {
            ((LanguageChangeListener)listener).languageChanged( ev );
        }
    }
    
    /**
     * Creates a menu item "Orthography" (or the equivalent depending on the user language) with a
     * sub-menu that includes suggestions for a correct spelling.
     * You can use this to add this menu item to your own popup.
     * @return the new menu.
     */
    public static JMenu createCheckerMenu(){
        return createCheckerMenu( null );
    }
    
    /**
     * Creates a menu item "Orthography" (or the equivalent depending on the user language) with a
     * sub-menu that includes suggestions for a correct spelling.
     * You can use this to add this menu item to your own popup.
     * @param options override the default options for this menu.
     * @return the new menu.
     */
    public static JMenu createCheckerMenu(SpellCheckerOptions options){
        return new CheckerMenu(options);
    }
    
    /**
     * Create a dynamic JPopupMenu with a list of suggestion. You can use the follow code sequence:<pre><code>
     * JPopupMenu popup = SpellChecker.createCheckerPopup();
     * text.addMouseListener( new PopupListener(popup) );
     * </code></pre>
     * @return the new JPopupMenu.
     * @see #createCheckerMenu()
     */
    public static JPopupMenu createCheckerPopup(){
        return createCheckerPopup( null );
    }
    
    /**
     * Create a dynamic JPopupMenu with a list of suggestion. You can use the follow code sequence:<pre><code>
     * JPopupMenu popup = SpellChecker.createCheckerPopup( null );
     * text.addMouseListener( new PopupListener(popup) );
     * </code></pre>
     * @return the new JPopupMenu.
     * @see #createCheckerMenu(SpellCheckerOptions)
     */
    public static JPopupMenu createCheckerPopup(SpellCheckerOptions options){
        return new CheckerPopup(options);
    }
    
    /**
     * Creates a menu item "Languages" (or the equivalent depending on the user language) with a sub-menu
     * that lists all available dictionary languages. 
     * You can use this to add this menu item to your own popup or to your menu bar.
     * <code>
     * JPopupMenu popup = new JPopupMenu();
     * popup.add( SpellChecker.createLanguagesMenu() );
     * </code>
     * @return the new menu.
     */
    public static JMenu createLanguagesMenu(){
        return createLanguagesMenu( null );
    }
    
    /**
     * Creates a menu item "Languages" (or the equivalent depending on the user language) with a sub-menu
     * that lists all available dictionary languages. 
     * You can use this to add this menu item to your own popup or to your menu bar.
     * <code>
     * JPopupMenu popup = new JPopupMenu();
     * popup.add( SpellChecker.createLanguagesMenu() );
     * </code>
     * @param options override the default options for this menu.
     * @return the new menu.
     */
    public static JMenu createLanguagesMenu(SpellCheckerOptions options){
        JMenu menu = new JMenu(Utils.getResource("languages"));
        ButtonGroup group = new ButtonGroup();
        menu.setEnabled( languages.size() > 0 );
        
        for(LanguageAction action : languages){
            JRadioButtonMenuItem item = new JRadioButtonMenuItem( action );
            //Hack that all items of the action have the same state.
            //http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4133141
            item.setModel( new ActionToggleButtonModel(action) );
            menu.add( item );
            group.add( item );
        }
        
        if(options == null ){
            options = SpellChecker.getOptions();
        }
        
        if(languages.size() > 0 && options.isLanguageDisableVisible()){
        	menu.addSeparator();
            JRadioButtonMenuItem item = new JRadioButtonMenuItem( DisableLanguageAction.instance );
            item.setModel( new ActionToggleButtonModel(DisableLanguageAction.instance) );
            menu.add( item );
            group.add( item );
        }
        
        return menu;
    }
    
    private static class ActionToggleButtonModel extends JToggleButton.ToggleButtonModel{
        private final AbtsractLanguageAction action;
        
        ActionToggleButtonModel(AbtsractLanguageAction action){
            this.action = action;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSelected() {
            return Boolean.TRUE.equals(action.getValue(SELECTED_KEY));
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void setSelected( boolean b ) {
            // copy from super.setSelected
            ButtonGroup group = getGroup();
            if (group != null) {
                // use the group model instead
                group.setSelected(this, b);
                b = group.isSelected(this);
            }

            if (isSelected() == b) {
                return;
            }

            action.setSelected( b );

            // Send ChangeEvent
            fireStateChanged();

            // Send ItemEvent
            fireItemStateChanged(
                    new ItemEvent(this,
                                  ItemEvent.ITEM_STATE_CHANGED,
                                  this,
                                  this.isSelected() ?  ItemEvent.SELECTED : ItemEvent.DESELECTED));

        }
    }
    
    /**
     * Base class for languages change actions. This class has a static state to solv
     * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4133141
     */
    private static abstract class AbtsractLanguageAction extends AbstractAction{
        
        // the current active (selected) LanguageAction
        private static AbtsractLanguageAction currentAction;
        
        public AbtsractLanguageAction( String name ) {
            super(name);
        }

        /**
         * Selects or deselects the menu item.
         * 
         * @param b
         *            true selects the menu item, false deselects the menu item.
         */
        public void setSelected( boolean b ) {
            if( b ) {
                // because there are some problems with multiple ButtonGroups that we duplicate some of the logic here
                if( currentAction != null && currentAction != this ) {
                    currentAction.setSelected( false );
                }
                currentAction = this;
            }
            putValue( SELECTED_KEY, Boolean.valueOf( b ) );
        }

    }

    /**
     * Action for disable all dictionary language.
     */
    private static class DisableLanguageAction extends AbtsractLanguageAction{
    	static DisableLanguageAction instance = new DisableLanguageAction();

        private DisableLanguageAction() {
            super(Utils.getResource("disable"));
        }
        
        public void actionPerformed( ActionEvent ev ) {
            if( !isEnabled() ) {
                //because multiple MenuItems share the same action that
                //also the event occur multiple time
                return;
            }
            setEnabled( false );
            setSelected( true );
            try {
                currentDictionary = null;
                Locale oldLocale = currentLocale;
                currentLocale = null;
                fireLanguageChanged( oldLocale );
            } finally {
                setEnabled( true );
            }
        }
    }
    
    /**
     * Action for change the current dictionary language.
     */
    private static class LanguageAction extends AbtsractLanguageAction implements Comparable<LanguageAction>{
        
        private final URL baseURL;
        private final Locale locale;
        private String extension;
        
        LanguageAction(URL baseURL, Locale locale, String extension){
            super( locale.getDisplayLanguage() );
            this.baseURL = baseURL;
            this.locale = locale;
            this.extension = extension;
        }

        public void actionPerformed( ActionEvent ev ) {
            if( !isEnabled() ){
                //because multiple MenuItems share the same action that
                //also the event occur multiple time
                return;
            }
            setEnabled( false );
            setSelected( true );
            
            Thread thread = new Thread( new Runnable() {
                public void run() {
                    try {
                        DictionaryFactory factory = new DictionaryFactory();
                        try {
                            factory.loadWordList( new URL( baseURL, locale + File.separator + "dictionary_" + locale + extension ) );
                        } catch( Exception ex ) {
                        	SpellChecker.getMessageHandler().handleError( ex.toString(), "Error", ex );
                        }
                         try {
                            CustomDictionaryProvider provider = userDictionaryProvider;
                            if( provider != null ) {
                                Iterator<String> userWords = provider.getWords( locale );
                                if( userWords != null ) {
                                    factory.loadWords( userWords );
                                }
                            }
                            provider = customDictionaryProvider;
                            if( provider != null ) {
                                Iterator<String> userWords = provider.getWords( locale );
                                if( userWords != null ) {
                                    factory.loadWords( userWords );
                                }
                            }
                        } catch( Exception ex ) {
                        	SpellChecker.getMessageHandler().handleError( ex.toString(), "Error", ex );
                        }
                        Locale oldLocale = locale;
                        currentDictionary = factory.create();
                        factory = null; // make memory faster free
                        currentLocale = locale;
                        fireLanguageChanged( oldLocale );
                    } finally {
                        setEnabled( true );
                    }
                }
            });
            thread.setPriority( Thread.NORM_PRIORITY );
            thread.setDaemon( true );
            thread.start();
        }
        
        @Override
        public boolean equals(Object obj){
            if(obj instanceof LanguageAction){
                return locale.equals( ((LanguageAction)obj).locale );
            }
            return false;
        }
        
        @Override
        public int hashCode(){
            return locale.hashCode();
        }

        /**
         * Sort the displaynames in the order of the current language
         */
        public int compareTo( LanguageAction obj ) {
            return toString().compareTo( obj.toString() );
        }
    }

    /**
     * Get the current <code>Dictionary</code>. The current dictionary will be set if the user one select or on calling <code>registerDictionaries</code>.
     * @return the current <code>Dictionary</code> or null if not set.
     * @see #registerDictionaries(URL, String, String)
     */
    static Dictionary getCurrentDictionary() {
        return currentDictionary;
    }

    /**
     * Gets the current <code>Locale</code>. The current Locale will be set if the user selects
     * one, or when calling <code>registerDictionaries</code>.
     * @return the current <code>Locale</code> or null if none is set.
     * @see #registerDictionaries(URL, String, String)
     * @see #isDictionaryLoaded()
     */
    public static Locale getCurrentLocale() {
        return currentLocale;
    }
    
    /**
     * Set the current <code>Locale</code>. The call is asynchronous.
     * @param locale the new locale, must be registered.
     * @throws IllegalArgumentException if the locale was not registered as available.
     * @see #registerDictionaries(URL, String, String)
     * @see #isDictionaryLoaded()
     * @see #getCurrentLocale()
     */
    public static void setCurrentLocale( Locale locale ) throws IllegalArgumentException {
        if( locale.equals( currentLocale ) ) {
            return;
        }
        for( LanguageAction language : languages ){
            if( language.locale.equals( locale ) ){
                language.actionPerformed( null );
                return;
            }
        }
        throw new IllegalArgumentException( "Not registered locale: " + locale );
    }
    
    /**
     * If currently a Dictionary is loaded.
     * @return true, if a dictionary is loaded and include at minimum one word. 
     */
    public static boolean isDictionaryLoaded(){
        return currentDictionary != null && currentDictionary.getDataSize() > 1;
    }
    
    /**
     * Set the title of your application. This valuse is used as title for info boxes (JOptionPane).
     * If not set then the translated "Spelling" is used.
     */
    public static void setApplicationName( String name ){
        applicationName = name;
    }

    /**
     * Get the title of your application.
     */
    public static String getApplicationName(){
        return applicationName;
    }
 
    /**
     * Get the default SpellCheckerOptions. This object is a singleton. That there is no set method.
     * @return the default SpellCheckerOptions
     */
    public static SpellCheckerOptions getOptions(){
        return globalOptions;
    }
}
