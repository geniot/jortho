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
 * Created on 22.01.2009
 */
package io.github.geniot.jortho.jorthodictionaries;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Volker Berlin
 */
public class BookGenerator_pl_Engish extends BookGenerator {

    BookGenerator_pl_Engish( Book book ) {
        super( book );
    }

    @Override
    boolean isValidLanguage( String word, String wikiText ) {
        if( wikiText.indexOf( "==Polish==" ) < 0 ) {
            return false;
        }

        conjugationAiUje( wikiText );
        conjugationApAm_Asz( wikiText );
        conjugationAiAm_Asz( wikiText );
        conjugationAi( wikiText );
        declensionNoun( wikiText );
        declensionNounCja( wikiText );
        declensionNounF( wikiText );
        declensionNounIa( wikiText );
        declensionAdjI( wikiText );
        declensionAdjKi( wikiText );
        declensionAdjY( wikiText );
        declensionAdjIa( wikiText );
        declensionAdj( wikiText );

        return true;

    }

    /**
     * Scanning for template {{pl-conj-ai-uję|x} it is define at
     * http://en.wiktionary.org/w/index.php?title=Template:pl-conj-ai-uję
     */
    private void conjugationAiUje( String wikiText ) {
        String[] suffixe =
                        { "ować", "uję", "ujemy", "ujesz", "ujecie", "uje", "ują", "owałem", "owałam", "owaliśmy",
                                        "owałyśmy", "owałeś", "owałaś", "owaliście", "owałyście", "ował", "owała",
                                        "owało", "owali", "owały", "owałbym", "owałabym", "owalibyśmy", "owałybyśmy",
                                        "owałbyś", "owałabyś", "owalibyście", "owałybyście", "owałby", "owałaby",
                                        "owałoby", "owaliby", "owałyby", "uj", "ujcie", "ujący", "ująca", "ujące",
                                        "ujący", "ując", "owano", "owanie" };
        templateWithRoots( wikiText, "pl-conj-ai-uję", suffixe );
    }

    /**
     * http://en.wiktionary.org/wiki/Template:pl-conj-ap-am,asz
     */
    private void conjugationApAm_Asz( String wikiText ) {
        String[] suffixe =
                        { "ć", "m", "my", "sz", "cie", "", "ją", "łem", "łam", "liśmy", "łyśmy", "łeś", "łaś",
                                        "liście", "łyście", "ł", "ła", "ło", "li", "ły", "łbym", "łabym", "libyśmy",
                                        "łybyśmy", "łbyś", "łabyś", "libyście", "łybyście", "łby", "łaby", "łoby",
                                        "liby", "łyby", "jmy", "j", "jcie" };

        templateWithRoots( wikiText, "pl-conj-ap-am,asz", suffixe );
    }

    /**
     * http://en.wiktionary.org/wiki/Template:pl-conj-ai-am,asz
     */
    private void conjugationAiAm_Asz( String wikiText ) {
        String[] suffixe =
                        { "ć", "m", "my", "sz", "cie", "", "ją", "łem", "łam", "liśmy", "łyśmy", "łeś", "łaś",
                                        "liście", "łyście", "ł", "ła", "ło", "li", "ły", "będę", "ł", "ła", "li", "ły",
                                        "ł", "ła", "li", "ły", "ł", "ła", "ło", "li", "ły", "łbym", "łabym", "libyśmy",
                                        "łybyśmy", "łbyś", "łabyś", "libyście", "łybyście", "łby", "łaby", "łoby",
                                        "liby", "łyby", "my", "j", "jcie", "ją", "jący", "jąca", "jące", "jący",
                                        "jące", "jąc", "no", "nie"

                        };
        templateWithRoots( wikiText, "pl-conj-ai-am,asz", suffixe );
    }

    /**
     * http://en.wiktionary.org/wiki/Template:pl-conj-ai
     */
    private void conjugationAi( String wikiText ) {
        String[] suffixeRoot1 = { "" };
        String[] suffixeRoot2 = { "" };
        String[] suffixeRoot3 = { "my", "sz", "cie", "" };
        String[] suffixeRoot4 = { "", "cy", "ca", "ce", "c" };
        String[] suffixeRoot5 = { "łem", "łeś", "ł", "łbym", "łbyś", "łby" };
        String[] suffixeRoot6 =
                        { "łam", "łyśmy", "łaś", "łyście", "ła", "ło", "ły", "łabym", "łybyśmy", "łabyś", "łybyście",
                                        "łaby", "łoby", "łyby" };
        String[] suffixeRoot7 = { "liśmy", "liście", "li", "libyśmy", "libyście", "liby" };
        String[] suffixeRoot8 = { "my", "", "cie" };

        templateWithRoots( wikiText, "pl-conj-ai", suffixeRoot1, suffixeRoot2, suffixeRoot3, suffixeRoot4, suffixeRoot5, suffixeRoot6, suffixeRoot7, suffixeRoot8 );
    }

    /**
     * http://en.wiktionary.org/wiki/Template:pl-decl-noun
     */
    private void declensionNoun( String wikiText ) {
        templateWithWordList( wikiText, "pl-decl-noun" );
    }

    /**
     * http://en.wiktionary.org/wiki/Template:pl-decl-noun-cja
     */
    private void declensionNounCja( String wikiText ) {
        String[] suffixe =
                        { "ja", "je", "ji", "ji", "yj", "ji", "jom", "ję", "je", "ją", "jami", "ji", "jach", "jo", "je" };
        templateWithRoots( wikiText, "pl-decl-noun-cja", suffixe );
    }

    /**
     * http://en.wiktionary.org/w/index.php?title=Template:pl-decl-noun-f
     */
    private void declensionNounF( String wikiText ) {
        //TODO there are some some special conditional cases
        String[] suffixe = { "ba", "ca", "cha", "cza",/* "da", (excluding '''-zda''')*/
        "dza", "dża", "fa", "ga", "ka", "la", "ła", "ma", /*"na", (excluding '''-sna''' and '''-zna''')*/
        "pa", "ra", "sa", "sza", /*"ta", (excluding '''-sta''')*/
        "wa", "za", "ża" };
        templateWithRoots( wikiText, "pl-decl-noun-f", suffixe );
    }

    /**
     * http://en.wiktionary.org/wiki/Template:pl-decl-noun-ia
     */
    private void declensionNounIa( String wikiText ) {
        //TODO there are some some special conditional cases
        String[] suffixe = { "ia", "ie", "ii", "iom", "ię", "ie", "ią", "iami", "iach", "io", "ie" };
        templateWithRoots( wikiText, "pl-decl-noun-ia", suffixe );
    }
    
    /**
     * http://en.wiktionary.org/wiki/Template:pl-decl-adj-i
     */
    private void declensionAdjI( String wikiText ) {
        String[] suffixeRoot1 =
                        { "i", "ie", "a", "ie", "iego", "ie", "ich", "iemu", "im", "iego", "i", "ie", "ą", "ich", "ie",
                                        "im", "imi", "ie", "ich"

                        };
        String[] suffixeRoot2 = { "" };
        templateWithRoots( wikiText, "pl-decl-adj-i", suffixeRoot1, suffixeRoot2 );
    }

    /**
     * http://en.wiktionary.org/wiki/Template:pl-decl-adj-ki
     */
    private void declensionAdjKi( String wikiText ) {
        String[] suffixe =
                        { "ki", "kie", "ka", "cy", "kie", "kiego", "kiej", "kich", "kiemu", "kim", "kiego", "ki",
                                        "kie", "ką", "kich", "kie", "kim", "kimi", "kiej", "kich"

                        };
        templateWithRoots( wikiText, "pl-decl-adj-ki", suffixe );
    }

    /**
     * http://en.wiktionary.org/wiki/Template:pl-decl-adj-y
     */
    private void declensionAdjY( String wikiText ) {
        String[] suffixeRoot1 =
                        { "y", "e", "a", "e", "ego", "ej", "ych", "emu", "ym", "ego", "y", "e", "ą", "ych", "e", "ym",
                                        "ymi", "ej", "ych" };
        String[] suffixeRoot2 = { "" };
        templateWithRoots( wikiText, "pl-decl-adj-y", suffixeRoot1, suffixeRoot2 );
    }

    /**
     * http://en.wiktionary.org/wiki/Template:pl-decl-adj-ia
     */
    private void declensionAdjIa( String wikiText ) {
        String[] suffixeRoot1 =
                        { "i", "ie", "ia", "ie", "iego", "iej", "ich", "iemu", "im", "iego", "i", "ie", "ią", "ich",
                                        "ie", "im", "imi", "iej", "ich" };
        String[] suffixeRoot2 = { "" };
        templateWithRoots( wikiText, "pl-decl-adj-y", suffixeRoot1, suffixeRoot2 );
    }

    /**
     * http://en.wiktionary.org/wiki/Template:pl-decl-adj
     */
    private void declensionAdj( String wikiText ) {
        templateWithWordList( wikiText, "pl-decl-adj" );
    }

    /**
     * Creating words with a template that has one or more root words.
     * 
     * @param wikiText
     * @param templateId
     * @param suffixe
     */
    private void templateWithRoots( String wikiText, String templateId, String[]... suffixes ) {
        int idx = findTemplate( wikiText, templateId );
        if( idx < 0 ) {
            return;
        }
        int end = wikiText.indexOf( '}', idx );
        if( end < 0 ) {
            return;
        }
        String wordRootsStr = wikiText.substring( idx, end );
        String[] wordRoots = wordRootsStr.split( "\\|" );
        for( int w = 0; w < wordRoots.length; w++ ) {
            String wordRoot = wordRoots[w];
            wordRoot = wordRoot.trim();
            if( !isValidWord( wordRoot ) ) {
                continue;
            }
            if( suffixes.length <= w ) {
                continue;
            }
            String[] suffixe = suffixes[w];
            for( int i = 0; i < suffixe.length; i++ ) {
                addWord( wordRoot + suffixe[i] );
            }
        }
    }

    private void templateWithWordList( String wikiText, String templateId ) {
        int idx = findTemplate( wikiText, templateId );
        if( idx < 0 ) {
            return;
        }
        int endIdx = wikiText.indexOf( '}', idx );
        if( endIdx < 0 ) {
            return;
        }

        String wordStr = wikiText.substring( idx, endIdx );
        String[] words = wordStr.split( "\\|" );
        for( int w = 0; w < words.length; w++ ) {
            String word = words[w];
            word = word.trim();
            if( !isValidWord( word ) ) {
                continue;
            }
            addWord( word );
        }
    }

    /**
     * Find a template name in the wiki text. the problem are possible whitespaces.
     * 
     * @param wikiText
     * @param tempalateName
     * @return the index after the first | or -1.
     */
    private int findTemplate( String wikiText, String tempalateName ) {
        //find {{  tempalateName  |
        Pattern pattern = Pattern.compile( "\\{\\{\\s*" + tempalateName + "\\s*\\|" );
        Matcher matcher = pattern.matcher( wikiText );

        if( matcher.find() ) {
            return matcher.end();
        }

        return -1;
    }

}
