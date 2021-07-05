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

import java.util.Properties;
import java.util.StringTokenizer;

/**
 * 
 * @author Volker Berlin
 */
public class BookGenerator_es extends BookGenerator {

    @Override
    boolean isValidLanguage( String word, String wikiText ) {
        if( wikiText.indexOf( "{{ES}}" ) < 0 && wikiText.indexOf( "{{ES|" ) < 0 ) {
            return false;
        }

        //the follow rules can be found at http://es.wiktionary.org/wiki/Categor%C3%ADa:ES:Plantillas_de_flexi%C3%B3n

        //http://es.wiktionary.org/wiki/Categor%C3%ADa:ES:Plantillas_de_flexi%C3%B3n_de_adjetivos
        findRuleAndAddWords( word, wikiText, "inflect.es.adj.-ón", null, new String[] { "ón", "ona", "ones", "onas" } ); //redirect to inflect.es.adj.agudo-cons
        findRuleAndAddWords( word, wikiText, "inflect.es.adj.agudo-cons", null, new String[] { "ón", "ona", "ones", "onas" } );
        findRuleAndAddWords( word, wikiText, "inflect.es.adj.ad-lib", null, new String[] { "" }, new String[] { "" }, new String[] { "" }, new String[] { "" } );
        findRuleAndAddWords( word, wikiText, "inflect.es.adj.no-género", new String[] { "s" } );
        findRuleAndAddWords( word, wikiText, "inflect.es.adj.reg", null, new String[] { "o", "os", "a", "as" } );
        findRuleAndAddWords( word, wikiText, "inflect.es.adj.reg-cons", new String[] { "es", "a", "as" } );

        //http://es.wiktionary.org/wiki/Categor%C3%ADa:ES:Plantillas_de_flexi%C3%B3n_de_sustantivos
        findRuleAndAddWords( word, wikiText, "inflect.es.sust.-ón", null, new String[] { "ón", "ones" } ); //redirect to inflect.es.sust.agudo-cons
        findRuleAndAddWords( word, wikiText, "inflect.es.sust.agudo-cons", null, new String[] { "ón", "ones" } );
        //inflect.es.sust.invariante nothing
        //inflect.es.sust.plur.tantum nothing
        findRuleAndAddWords( word, wikiText, "inflect.es.sust.reg", new String[] { "s" } );
        findRuleAndAddWords( word, wikiText, "inflect.es.sust.reg-cons", new String[] { "es" } );
        //{{inflect.es.sust.sing.tantum}} nothing

        //http://es.wiktionary.org/wiki/Categor%C3%ADa:ES:Plantillas_de_flexi%C3%B3n_de_verbos
        findRuleAndAddWords( word, wikiText, "es.v.conj.ar", null, new String[] { "ar", "ado", "ando", "o", "as", "ás", "a", "amos", "áis",
                        "an", "aba", "abas", "ábamos", "abais", "aban", "é", "aste", "ó", "asteis", "aron", "aré", "arás", "ará", "aremos",
                        "aréis", "arán", "aría", "arías", "aríamos", "aríais", "arían", "e", "es", "és", "emos", "éis", "en", "ara",
                        "aras", "áramos", "arais", "aran", "ase", "ases", "ásemos", "aseis", "asen", "are", "ares", "áremos", "areis",
                        "aren", "á", "emos", "ad" } );
        findRuleAndAddWords( word, wikiText, "es.v.conj.2.ar", null, new String[] { "ar", "ado", "ando", "ás", "amos", "áis", "aba",
                        "abas", "ábamos", "abais", "aban", "é", "aste", "ó", "asteis", "aron", "aré", "arás", "ará", "aremos", "aréis",
                        "arán", "aría", "arías", "aríamos", "aríais", "arían", "és", "emos", "éis", "ara", "aras", "áramos", "arais",
                        "aran", "ase", "ases", "ásemos", "aseis", "asen", "are", "ares", "áremos", "areis", "aren", "á", "emos", "ad" }, new String[] {
                        "o", "as", "a", "an", "e", "es", "en" } );
        findRuleAndAddWords( word, wikiText, "es.v.conj.car", null, new String[] { "car", "cado", "cando", "co", "cas", "cás", "ca",
                        "camos", "cáis", "can", "caba", "cabas", "cábamos", "cabais", "caban", "qué", "caste", "có", "casteis", "caron",
                        "caré", "carás", "cará", "caremos", "caréis", "carán", "caría", "carías", "caríamos", "caríais", "carían", "que",
                        "ques", "qués", "quemos", "quéis", "quen", "cara", "caras", "cáramos", "carais", "caran", "case", "acses",
                        "cásemos", "caseis", "casen", "care", "cares", "cáremos", "careis", "caren", "cá", "quemos", "cad" } );
        findRuleAndAddWords( word, wikiText, "es.v.conj.cuar", null, new String[] { "cuar", "cuado", "cuando", "cuo", "cúo", "cuas",
                        "cúas", "cua", "cúa", "cuamos", "cuáis", "cuan", "cúan", "cuaba", "cuabas", "cuábamos", "cuabais", "cuaban", "cué",
                        "cuaste", "cuó", "cuasteis", "cuaron", "cuaré", "cuarás", "cuará", "cuaremos", "cuaréis", "cuarán", "cuaría",
                        "cuarías", "cuaríamos", "cuaríais", "cuarían", "cue", "cúe", "cues", "cúes", "cuemos", "cuéis", "cuen", "cúen",
                        "cuara", "cuaras", "cuáramos", "cuarais", "cuaran", "cuase", "cuases", "cuásemos", "cuaseis", "cuasen", "cuare",
                        "cuares", "cuáremos", "cuareis", "cuaren", "cua", "cúa", "cuemos", "cuad" } );
        //TODO es.v.conj.dar (0)
        findRuleAndAddWords( word, wikiText, "es.v.conj.gar", null, new String[] { "gar", "gado", "gando", "go", "gas", "gás", "ga",
                        "gamos", "gáis", "gan", "gaba", "gabas", "gábamos", "gabais", "gaban", "gué", "gaste", "gó", "gasteis", "garon",
                        "garé", "garás", "gará", "garemos", "garéis", "garán", "garía", "garías", "garíamos", "garíais", "garían", "gue",
                        "gues", "gués", "guemos", "guéis", "guen", "gara", "garas", "gáramos", "garais", "garan", "gase", "gases",
                        "gásemos", "gaseis", "gasen", "gare", "gares", "gáremos", "gareis", "garen", "gá", "guemos", "gad" } );
        //TODO es.v.conj.guar (12)
        findRuleAndAddWords( word, wikiText, "es.v.conj.-ie-ue-.ar", null, new String[] { "ar", "ado", "ando", "ás", "amos", "áis", "aba",
                        "abas", "ábamos", "abais", "aban", "é", "aste", "ó", "asteis", "aron", "aré", "arás", "ará", "aremos", "aréis",
                        "arán", "aría", "arías", "aríamos", "aríais", "arían", "és", "emos", "éis", "ara", "aras", "áramos", "arais",
                        "aran", "ase", "ases", "ásemos", "aseis", "asen", "are", "ares", "áremos", "areis", "aren", "á", "emos", "ad" }, new String[] {
                        "o", "as", "a", "an", "e", "es", "en" } );
        //TODO es.v.conj.-ie-ue-.gar (23)
        //TODO es.v.conj.-ie-ue-.zar (8)
        //TODO es.v.conj.izar (7)
        findRuleAndAddWords( word, wikiText, "es.v.conj.zar", null, new String[] { "zar", "zado", "zando", "zo", "zas", "zás", "za",
                        "zamos", "záis", "zan", "zaba", "zabas", "zábamos", "zabais", "zaban", "cé", "zaste", "zó", "zasteis", "zaron",
                        "zaré", "zarás", "zará", "azremos", "zaréis", "zarán", "zaría", "zarías", "zaríamos", "zaríais", "zarían", "ce",
                        "ces", "cés", "cemos", "céis", "cen", "zara", "zaras", "záramos", "zarais", "zaran", "zase", "zases", "zásemos",
                        "zaseis", "zasen", "zare", "zares", "záremos", "zareis", "zaren", "zá", "cemos", "zad" } );
        //TODO es.v.conj.andar (1)

        es_v_conj_er( wikiText );
        es_v_conj_ie_ue_er( wikiText );
        es_v_conj_cer( wikiText );
        es_v_conj_zc_cer( wikiText );
        es_v_conj_eer( wikiText );
        es_v_conj_ger( wikiText );
        es_v_conj_caer( wikiText );
        es_v_conj_hacer( wikiText );
        es_v_conj_poner( wikiText );
        es_v_conj_tener( wikiText );
        es_v_conj_traer( wikiText );

        es_v_conj_ir( wikiText );
        es_v_conj_cir( wikiText );
        es_v_conj_zc_cir( wikiText );
        es_v_conj_decir( wikiText );
        es_v_conj_gir( wikiText );
        es_v_conj_ie_i_ue_ir( wikiText );
        es_v_conj_nir( wikiText );
        es_v_conj_seguir( wikiText );
        es_v_conj_uir( wikiText );

        return true;
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.-ie-ue-.er&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_ie_ue_er( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.-ie-ue-.er", 0 );

        if( props != null ) {
            String nexo = props.getProperty( "nexo", "" );
            String w2 = props.getProperty( "2", "{{{2}}}" );
            boolean pronominal = props.getProperty( "pronominal", "" ).length() > 0;

            //presente de indicativo
            props.setProperty( "i.p.1s", w2 + nexo + "o" );
            props.setProperty( "i.p.2s", w2 + nexo + "es" );
            props.setProperty( "i.p.3s", w2 + nexo + "e" );
            props.setProperty( "i.p.3p", w2 + nexo + "en" );

            //presente de subjuntivo
            props.setProperty( "s.p.1s", w2 + nexo + "a" );
            props.setProperty( "s.p.2s", w2 + nexo + "as" );
            props.setProperty( "s.p.3s", w2 + nexo + "a" );
            props.setProperty( "s.p.3p", w2 + nexo + "an" );

            //imperativo
            props.setProperty( "im.2s", pronominal ? (props.getProperty( "3", "{{{3}}}" ) + nexo + "ete") : (w2 + nexo + "e") );

            es_v_conj_er( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.er&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_er( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.er", 0 );
        if( props != null ) {
            es_v_conj_er( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.cer&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_cer( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.cer", 0 );
        if( props != null ) {
            props.put( "nexo", "c" );
            es_v_conj_er( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.zc.cer&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_zc_cer( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.zc.cer", 0 );
        if( props != null ) {
            String w1 = props.getProperty( "1", "{{{1}}}" );
            String w2 = props.getProperty( "2", "" );

            props.put( "irregular", "x" );
            if( props.getProperty( "nexo", "" ).length() == 0 ) {
                props.put( "nexo", "c" );
            }

            //presente de indicativo
            props.put( "i.p.1s", w1 + "zco" );

            //presente de subjuntivo
            props.put( "s.p.1s", w1 + "zca" );
            props.put( "s.p.2s", w1 + "zcas" );
            props.put( "s.p.2s2", w1 + "zcás" );
            props.put( "s.p.3s", w1 + "zca" );
            props.put( "s.p.1p", w1 + "zcamos" );
            props.put( "s.p.2p", w1 + "zcáis" );
            props.put( "s.p.3p", w1 + "zcan" );
            if( w2.length() > 0 ) {
                props.put( "im.3s", w2 + "zcase" );
                props.put( "im.1p", w1 + "zcámonos" );
                props.put( "im.3p", w2 + "zcanse" );
            } else {
                props.put( "im.3s", w1 + "zcase" );
                props.put( "im.1p", w1 + "zcamos" );
                props.put( "im.3p", w1 + "zcan" );
            }

            es_v_conj_er( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.eer&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_eer( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.eer", 0 );
        if( props != null ) {
            String w1 = props.getProperty( "1", "{{{1}}}" );

            props.put( "irregular", "x" );

            props.put( "i.pp.1s", w1 + "í" );
            props.put( "i.pp.2s", w1 + "íste" );
            props.put( "i.pp.3s", w1 + "yó" );
            props.put( "i.pp.1p", w1 + "jimos" );
            props.put( "i.pp.2p", w1 + "ísteis" );
            props.put( "i.pp.3p", w1 + "yeron" );

            //subjuntivo

            props.put( "s.pi.1s", w1 + "yera" );
            props.put( "s.pi.2s", w1 + "yeras" );
            props.put( "s.pi.3s", w1 + "yera" );
            props.put( "s.pi.1p", w1 + "yéramos" );
            props.put( "s.pi.2p", w1 + "yerais" );
            props.put( "s.pi.3p", w1 + "yeran" );

            props.put( "s.pi2.1s", w1 + "yese" );
            props.put( "s.pi2.2s", w1 + "yeses" );
            props.put( "s.pi2.3s", w1 + "yese" );
            props.put( "s.pi2.1p", w1 + "yésemos" );
            props.put( "s.pi2.2p", w1 + "yeseis" );
            props.put( "s.pi2.3p", w1 + "yesen" );

            props.put( "s.f.1s", w1 + "yere" );
            props.put( "s.f.2s", w1 + "yeres" );
            props.put( "s.f.3s", w1 + "yere" );
            props.put( "s.f.1p", w1 + "yéremos" );
            props.put( "s.f.2p", w1 + "yereis" );
            props.put( "s.f.3p", w1 + "yeren" );

            es_v_conj_er( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.ger&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_ger( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.ger", 0 );
        if( props != null ) {
            props.put( "nexo", "g" );

            es_v_conj_er( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.caer&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_caer( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.caer", 0 );
        if( props != null ) {
            String w1 = props.getProperty( "1", "{{{1}}}" );
            boolean pronominal = props.getProperty( "pronominal", "" ).length() > 0;

            props.put( "irregular", "x" );

            //primera persona singular presente
            props.put( "i.p.1s", w1 + "caigo" );

            props.put( "i.pp.3s", w1 + "cayó" );
            props.put( "i.pp.3p", w1 + "cayeron" );

            //subjuntivo

            props.put( "s.p.1s", w1 + "caiga" );
            props.put( "s.p.2s", w1 + "caigas" );
            props.put( "s.p.2s2", w1 + "caigás" );
            props.put( "s.p.3s", w1 + "caiga" );
            props.put( "s.p.1p", w1 + "caigamos" );
            props.put( "s.p.2p", w1 + "caigáis" );
            props.put( "s.p.3p", w1 + "caigan" );

            props.put( "s.pi.1s", w1 + "cayera" );
            props.put( "s.pi.2s", w1 + "cayeras" );
            props.put( "s.pi.3s", w1 + "cayera" );
            props.put( "s.pi.1p", w1 + "cayéramos" );
            props.put( "s.pi.2p", w1 + "cayerais" );
            props.put( "s.pi.3p", w1 + "cayeran" );

            props.put( "s.pi2.1s", w1 + "cayese" );
            props.put( "s.pi2.2s", w1 + "cayeses" );
            props.put( "s.pi2.3s", w1 + "cayese" );
            props.put( "s.pi2.1p", w1 + "cayésemos" );
            props.put( "s.pi2.2p", w1 + "cayeseis" );
            props.put( "s.pi2.3p", w1 + "cayesen" );

            props.put( "s.f.1s", w1 + "cayere" );
            props.put( "s.f.2s", w1 + "cayeres" );
            props.put( "s.f.3s", w1 + "cayere" );
            props.put( "s.f.1p", w1 + "cayéremos" );
            props.put( "s.f.2p", w1 + "cayereis" );
            props.put( "s.f.3p", w1 + "cayeren" );

            //imperativo
            if( pronominal ) {
                props.put( "im.3s", w1 + "cáigase" );
                props.put( "im.1p", w1 + "caigámonos" );
                props.put( "im.3p", w1 + "cáiganse" );
            } else {
                props.put( "im.3s", w1 + "caiga" );
                props.put( "im.1p", w1 + "caigamos" );
                props.put( "im.3p", w1 + "caigan" );
            }

            es_v_conj_er( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.hacer&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_hacer( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.hacer", 0 );
        if( props != null ) {
            String w1 = props.getProperty( "1", "{{{1}}}" );
            boolean pronominal = props.getProperty( "pronominal", "" ).length() > 0;

            props.put( "irregular", "x" );
            props.put( "1", w1 + "ac" );
            props.put( "2", w1 + "ác" );

            props.put( "i.p.1s", w1 + "ago" );

            props.put( "i.pp.1s", w1 + "ice" );
            props.put( "i.pp.2s", w1 + "iciste" );
            props.put( "i.pp.3s", w1 + "izo" );
            props.put( "i.pp.1p", w1 + "icimos" );
            props.put( "i.pp.2p", w1 + "icisteis" );
            props.put( "i.pp.3p", w1 + "icieron" );

            props.put( "i.f.1s", w1 + "aré" );
            props.put( "i.f.2s", w1 + "arás" );
            props.put( "i.f.3s", w1 + "ará" );
            props.put( "i.f.1p", w1 + "aremos" );
            props.put( "i.f.2p", w1 + "aréis" );
            props.put( "i.f.3p", w1 + "arán" );

            props.put( "i.c.1s", w1 + "aría" );
            props.put( "i.c.2s", w1 + "arías" );
            props.put( "i.c.3s", w1 + "aría" );
            props.put( "i.c.1p", w1 + "aríamos" );
            props.put( "i.c.2p", w1 + "aríais" );
            props.put( "i.c.3p", w1 + "arían" );

            //subjuntivo

            props.put( "s.p.1s", w1 + "aga" );
            props.put( "s.p.2s", w1 + "agas" );
            props.put( "s.p.2s2", w1 + "agás" );
            props.put( "s.p.3s", w1 + "aga" );
            props.put( "s.p.1p", w1 + "agamos" );
            props.put( "s.p.2p", w1 + "agáis" );
            props.put( "s.p.3p", w1 + "agan" );

            props.put( "s.pi.1s", w1 + "iciera" );
            props.put( "s.pi.2s", w1 + "icieras" );
            props.put( "s.pi.3s", w1 + "iciera" );
            props.put( "s.pi.1p", w1 + "iciéramos" );
            props.put( "s.pi.2p", w1 + "icierais" );
            props.put( "s.pi.3p", w1 + "icieran" );

            props.put( "s.pi2.1s", w1 + "iciese" );
            props.put( "s.pi2.2s", w1 + "icieses" );
            props.put( "s.pi2.3s", w1 + "iciese" );
            props.put( "s.pi2.1p", w1 + "iciésemos" );
            props.put( "s.pi2.2p", w1 + "icieseis" );
            props.put( "s.pi2.3p", w1 + "iciesen" );

            props.put( "s.f.1s", w1 + "iciere" );
            props.put( "s.f.2s", w1 + "icieres" );
            props.put( "s.f.3s", w1 + "iciere" );
            props.put( "s.f.1p", w1 + "iciéremos" );
            props.put( "s.f.2p", w1 + "iciereis" );
            props.put( "s.f.3p", w1 + "icieren" );

            //imperativo
            if( pronominal ) {
                props.put( "im.2s", w1 + "azte" );
                props.put( "im.3s", w1 + "ágase" );
                props.put( "im.1p", w1 + "agámonos" );
                props.put( "im.3p", w1 + "áganse" );
            } else {
                props.put( "im.2s", w1 + "az" );
                props.put( "im.3s", w1 + "aga" );
                props.put( "im.1p", w1 + "agamos" );
                props.put( "im.3p", w1 + "agan" );
            }

            es_v_conj_er( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.poner&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_poner( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.poner", 0 );
        if( props != null ) {
            String w1 = props.getProperty( "1", "{{{1}}}" );
            boolean pronominal = props.getProperty( "pronominal", "" ).length() > 0;

            props.put( "irregular", "x" );
            props.put( "1", w1 + "pon" );
            props.put( "2", w1 + "pón" );

            props.put( "i.p.1s", w1 + "pongo" );
            props.put( "i.pp.1s", w1 + "puse" );
            props.put( "i.pp.2s", w1 + "pusiste" );
            props.put( "i.pp.3s", w1 + "puso" );
            props.put( "i.pp.1p", w1 + "pusimos" );
            props.put( "i.pp.2p", w1 + "pusisteis" );
            props.put( "i.pp.3p", w1 + "pusieron" );

            props.put( "i.f.1s", w1 + "pondré" );
            props.put( "i.f.2s", w1 + "pondrás" );
            props.put( "i.f.3s", w1 + "pondrá" );
            props.put( "i.f.1p", w1 + "pondremos" );
            props.put( "i.f.2p", w1 + "pondréis" );
            props.put( "i.f.3p", w1 + "pondrán" );

            props.put( "i.c.1s", w1 + "pondría" );
            props.put( "i.c.2s", w1 + "pondrías" );
            props.put( "i.c.3s", w1 + "pondría" );
            props.put( "i.c.1p", w1 + "pondríamos" );
            props.put( "i.c.2p", w1 + "pondríais" );
            props.put( "i.c.3p", w1 + "pondrían" );

            props.put( "s.p.1s", w1 + "ponga" );
            props.put( "s.p.2s", w1 + "pongas" );
            props.put( "s.p.2s2", w1 + "pongás" );
            props.put( "s.p.3s", w1 + "ponga" );
            props.put( "s.p.1p", w1 + "pongamos" );
            props.put( "s.p.2p", w1 + "pongáis" );
            props.put( "s.p.3p", w1 + "pongan" );
            props.put( "s.pi.1s", w1 + "pusiera" );
            props.put( "s.pi.2s", w1 + "pusieras" );
            props.put( "s.pi.3s", w1 + "pusiera" );
            props.put( "s.pi.1p", w1 + "pusiéramos" );
            props.put( "s.pi.2p", w1 + "pusierais" );
            props.put( "s.pi.3p", w1 + "pusieran" );
            props.put( "s.pi2.1s", w1 + "pusiese" );
            props.put( "s.pi2.2s", w1 + "pusieses" );
            props.put( "s.pi2.3s", w1 + "pusiese" );
            props.put( "s.pi2.1p", w1 + "pusiésemos" );
            props.put( "s.pi2.2p", w1 + "pusieseis" );
            props.put( "s.pi2.3p", w1 + "pusiesen" );
            props.put( "s.f.1s", w1 + "pusiere" );
            props.put( "s.f.2s", w1 + "pusieres" );
            props.put( "s.f.3s", w1 + "pusiere" );
            props.put( "s.f.1p", w1 + "pusiéremos" );
            props.put( "s.f.2p", w1 + "pusiereis" );
            props.put( "s.f.3p", w1 + "pusieren" );

            //imperativo
            if( pronominal ) {
                props.put( "im.2s", w1 + "ponte" );
                addWord( w1 + "pónete" );
                props.put( "im.3s", w1 + "póngase" );
                props.put( "im.1p", w1 + "pongámonos" );
                props.put( "im.3p", w1 + "pónganse" );
            } else {
                props.put( "im.2s", w1 + "pón" );
                addWord( w1 + "pone" );
                props.put( "im.3s", w1 + "ponga" );
                props.put( "im.1p", w1 + "pongamos" );
                props.put( "im.3p", w1 + "pongan" );
            }

            es_v_conj_er( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.tener&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_tener( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.tener", 0 );
        if( props != null ) {
            String w1 = props.getProperty( "1", "{{{1}}}" );
            boolean pronominal = props.getProperty( "pronominal", "" ).length() > 0;

            props.put( "irregular", "x" );
            props.put( "1", w1 + "ten" );
            props.put( "2", w1 + "tén" );

            props.put( "i.p.1s", w1 + "tengo" );
            props.put( "i.p.2s", w1 + "tienes" );
            props.put( "i.p.3s", w1 + "tiene" );
            props.put( "i.p.3p", w1 + "tienen" );

            props.put( "i.pp.1s", w1 + "tuve" );
            props.put( "i.pp.2s", w1 + "tuviste" );
            props.put( "i.pp.3s", w1 + "tuvo" );
            props.put( "i.pp.1p", w1 + "tuvimos" );
            props.put( "i.pp.2p", w1 + "tuvisteis" );
            props.put( "i.pp.3p", w1 + "tuvieron" );

            props.put( "i.f.1s", w1 + "tendré" );
            props.put( "i.f.2s", w1 + "tendrás" );
            props.put( "i.f.3s", w1 + "tendrá" );
            props.put( "i.f.1p", w1 + "tendremos" );
            props.put( "i.f.2p", w1 + "tendréis" );
            props.put( "i.f.3p", w1 + "tendrán" );

            props.put( "i.c.1s", w1 + "tendría" );
            props.put( "i.c.2s", w1 + "tendrías" );
            props.put( "i.c.3s", w1 + "tendría" );
            props.put( "i.c.1p", w1 + "tendríamos" );
            props.put( "i.c.2p", w1 + "tendríais" );
            props.put( "i.c.3p", w1 + "tendrían" );

            //subjuntivo

            props.put( "s.p.1s", w1 + "tenga" );
            props.put( "s.p.2s", w1 + "tengas" );
            props.put( "s.p.2s2", w1 + "tengás" );
            props.put( "s.p.3s", w1 + "tenga" );
            props.put( "s.p.1p", w1 + "tengamos" );
            props.put( "s.p.2p", w1 + "tengáis" );
            props.put( "s.p.3p", w1 + "tengan" );

            props.put( "s.pi.1s", w1 + "tuviera" );
            props.put( "s.pi.2s", w1 + "tuvieras" );
            props.put( "s.pi.3s", w1 + "tuviera" );
            props.put( "s.pi.1p", w1 + "tuviéramos" );
            props.put( "s.pi.2p", w1 + "tuvierais" );
            props.put( "s.pi.3p", w1 + "tuvieran" );

            props.put( "s.pi2.1s", w1 + "tuviese" );
            props.put( "s.pi2.2s", w1 + "tuvieses" );
            props.put( "s.pi2.3s", w1 + "tuviese" );
            props.put( "s.pi2.1p", w1 + "tuviésemos" );
            props.put( "s.pi2.2p", w1 + "tuvieseis" );
            props.put( "s.pi2.3p", w1 + "tuviesen" );

            props.put( "s.f.1s", w1 + "tuviere" );
            props.put( "s.f.2s", w1 + "tuvieres" );
            props.put( "s.f.3s", w1 + "tuviere" );
            props.put( "s.f.1p", w1 + "tuviéremos" );
            props.put( "s.f.2p", w1 + "tuviereis" );
            props.put( "s.f.3p", w1 + "tuvieren" );

            //imperativo
            if( pronominal ) {
                props.put( "im.2s=", w1 + "ténte" );
                props.put( "im.3s", w1 + "téngase" );
                props.put( "im.1p", w1 + "tengámonos" );
                props.put( "im.3p", w1 + "ténganse" );
            } else {
                props.put( "im.2s=", w1 + "tén" );
                props.put( "im.3s", w1 + "tenga" );
                props.put( "im.1p", w1 + "tengamos" );
                props.put( "im.3p", w1 + "tengan" );
            }

            es_v_conj_er( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.traer&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_traer( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.traer", 0 );
        if( props != null ) {
            String w1 = props.getProperty( "1", "{{{1}}}" );
            boolean pronominal = props.getProperty( "pronominal", "" ).length() > 0;

            props.put( "irregular", "x" );
            props.put( "1", w1 + "tra" );
            props.put( "2", w1 + "trá" );

            props.put( "i.p.1s", w1 + "traigo" );

            props.put( "i.pp.1s", w1 + "traje" );
            props.put( "i.pp.2s", w1 + "trajiste" );
            props.put( "i.pp.3s", w1 + "trajo" );
            props.put( "i.pp.1p", w1 + "trajimos" );
            props.put( "i.pp.2p", w1 + "trajisteis" );
            props.put( "i.pp.3p", w1 + "trajeron" );

            //subjuntivo

            props.put( "s.p.1s", w1 + "traiga" );
            props.put( "s.p.2s", w1 + "traigas" );
            props.put( "s.p.2s2", w1 + "traigás" );
            props.put( "s.p.3s", w1 + "traiga" );
            props.put( "s.p.1p", w1 + "traigamos" );
            props.put( "s.p.2p", w1 + "traigáis" );
            props.put( "s.p.3p", w1 + "traigan" );

            props.put( "s.pi.1s", w1 + "trajera" );
            props.put( "s.pi.2s", w1 + "trajeras" );
            props.put( "s.pi.3s", w1 + "trajera" );
            props.put( "s.pi.1p", w1 + "trajéramos" );
            props.put( "s.pi.2p", w1 + "trajerais" );
            props.put( "s.pi.3p", w1 + "trajeran" );

            props.put( "s.pi2.1s", w1 + "trajese" );
            props.put( "s.pi2.2s", w1 + "trajeses" );
            props.put( "s.pi2.3s", w1 + "trajese" );
            props.put( "s.pi2.1p", w1 + "trajésemos" );
            props.put( "s.pi2.2p", w1 + "trajeseis" );
            props.put( "s.pi2.3p", w1 + "trajesen" );

            props.put( "s.f.1s", w1 + "trajere" );
            props.put( "s.f.2s", w1 + "trajeres" );
            props.put( "s.f.3s", w1 + "trajere" );
            props.put( "s.f.1p", w1 + "trajéremos" );
            props.put( "s.f.2p", w1 + "trajereis" );
            props.put( "s.f.3p", w1 + "trajeren" );

            //imperativo
            if( pronominal ) {
                props.put( "im.3s", w1 + "tráigase" );
                props.put( "im.1p", w1 + "traigámonos" );
                props.put( "im.3p", w1 + "tráiganse" );
            } else {
                props.put( "im.3s", w1 + "traiga" );
                props.put( "im.1p", w1 + "traigamos" );
                props.put( "im.3p", w1 + "traigan" );
            }

            es_v_conj_er( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.er&action=edit
     * @param props parsed parameters, can not be null
     */
    private void es_v_conj_er( Properties props ) {
        String w1 = props.getProperty( "1", "{{{1}}}" );
        String nexo = props.getProperty( "nexo", "" );
        String part = props.getProperty( "part" );
        String part2 = props.getProperty( "part2" );
        boolean impersonal = props.getProperty( "impersonal", "" ).length() > 0; //plural or singular
        boolean impersonalPlural = "plural".equalsIgnoreCase( props.getProperty( "impersonal", "" ) );

        boolean pronominal = props.getProperty( "pronominal", "" ).length() > 0;

        addWord( w1 + nexo + "er" + (pronominal ? "se" : "") );
        addWord( w1 + nexo + "ido" );
        addWord( w1 + nexo + (pronominal ? "iéndose" : "iendo") ); // Simples Gerundio
        if( part != null ) {
            addWord( part );
        }
        if( part2 != null ) {
            addWord( part2 );
        }

        //indicativo presente
        if( !impersonal ) {
            addWord( props.getProperty( "i.p.1s", w1 + nexo + "o" ) );
            addWord( props.getProperty( "i.p.2s", w1 + nexo + "es" ) );
            addWord( props.getProperty( "i.p.2s2", w1 + nexo + "és" ) );
        }
        addWord( props.getProperty( "i.p.3s", w1 + nexo + "e" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "i.p.1p", w1 + nexo + "emos" ) );
            addWord( props.getProperty( "i.p.2p", w1 + nexo + "éis" ) );
        }
        addWord( props.getProperty( "i.p.3p", w1 + nexo + "en" ) );

        //imperfecto
        if( !impersonal ) {
            addWord( props.getProperty( "i.pi.1s", w1 + nexo + "ía" ) );
            addWord( props.getProperty( "i.pi.2s", w1 + nexo + "ías" ) );
        }
        addWord( props.getProperty( "i.pi.3s", w1 + nexo + "ía" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "i.pi.1p", w1 + nexo + "íamos" ) );
            addWord( props.getProperty( "i.pi.2p", w1 + nexo + "íais" ) );
        }
        addWord( props.getProperty( "i.pi.3p", w1 + nexo + "ían" ) );

        //indefinido
        if( !impersonal ) {
            addWord( props.getProperty( "i.pp.1s", w1 + nexo + "í" ) );
            addWord( props.getProperty( "i.pp.2s", w1 + nexo + "iste" ) );
        }
        addWord( props.getProperty( "i.pp.3s", w1 + nexo + "ió" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "i.pp.1p", w1 + nexo + "imos" ) );
            addWord( props.getProperty( "i.pp.2p", w1 + nexo + "isteis" ) );
        }
        addWord( props.getProperty( "i.pp.3p", w1 + nexo + "ieron" ) );

        //futuro
        if( !impersonal ) {
            addWord( props.getProperty( "i.f.1s", w1 + nexo + "eré" ) );
            addWord( props.getProperty( "i.f.2s", w1 + nexo + "erás" ) );
        }
        addWord( props.getProperty( "i.f.3s", w1 + nexo + "erá" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "i.f.1p", w1 + nexo + "eremos" ) );
            addWord( props.getProperty( "i.f.2p", w1 + nexo + "eréis" ) );
        }
        addWord( props.getProperty( "i.f.3p", w1 + nexo + "erán" ) );

        //condicional
        if( !impersonal ) {
            addWord( props.getProperty( "i.c.1s", w1 + nexo + "ería" ) );
            addWord( props.getProperty( "i.c.2s", w1 + nexo + "erías" ) );
        }
        addWord( props.getProperty( "i.c.3s", w1 + nexo + "ería" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "i.c.1p", w1 + nexo + "eríamos" ) );
            addWord( props.getProperty( "i.c.2p", w1 + nexo + "eríais" ) );
        }
        addWord( props.getProperty( "i.c.3p", w1 + nexo + "erían" ) );

        //subjuntivo presente
        if( !impersonal ) {
            addWord( props.getProperty( "s.p.1s", w1 + nexo + "a" ) );
            addWord( props.getProperty( "s.p.2s", w1 + nexo + "as" ) );
            addWord( props.getProperty( "s.p.2s2", w1 + nexo + "ás" ) );
        }
        addWord( props.getProperty( "s.p.3s", w1 + nexo + "a" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "s.p.1p", w1 + nexo + "amos" ) );
            addWord( props.getProperty( "s.p.2p", w1 + nexo + "áis" ) );
        }
        addWord( props.getProperty( "s.p.3p", w1 + nexo + "an" ) );

        //imperfecto (-iera)
        if( !impersonal ) {
            addWord( props.getProperty( "s.pi.1s", w1 + nexo + "iera" ) );
            addWord( props.getProperty( "s.pi.2s", w1 + nexo + "ieras" ) );
        }
        addWord( props.getProperty( "s.pi.3s", w1 + nexo + "iera" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "s.pi.1p", w1 + nexo + "iéramos" ) );
            addWord( props.getProperty( "s.pi.2p", w1 + nexo + "ierais" ) );
        }
        addWord( props.getProperty( "s.pi.3p", w1 + nexo + "ieran" ) );

        //imperfecto (-iese)
        if( !impersonal ) {
            addWord( props.getProperty( "s.pi2.1s", w1 + nexo + "iese" ) );
            addWord( props.getProperty( "s.pi2.2s", w1 + nexo + "ieses" ) );
        }
        addWord( props.getProperty( "s.pi2.3s", w1 + nexo + "iese" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "s.pi2.1p", w1 + nexo + "iésemos" ) );
            addWord( props.getProperty( "s.pi2.2p", w1 + nexo + "ieseis" ) );
        }
        addWord( props.getProperty( "s.pi2.3p", w1 + nexo + "iesen" ) );

        //futuro
        if( !impersonal ) {
            addWord( props.getProperty( "s.f.1s", w1 + nexo + "iere" ) );
            addWord( props.getProperty( "s.f.2s", w1 + nexo + "ieres" ) );
        }
        addWord( props.getProperty( "s.f.3s", w1 + nexo + "iere" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "s.f.1p", w1 + nexo + "iéremos" ) );
            addWord( props.getProperty( "s.f.2p", w1 + nexo + "iereis" ) );
        }
        addWord( props.getProperty( "s.f.3p", w1 + nexo + "ieren" ) );

        //imperativo
        if( !impersonalPlural ) {
            addWord( props.getProperty( "im.2s", w1 + nexo + "e" ) );
            addWord( props.getProperty( "im.2s2", w1 + nexo + "é" ) );
            addWord( props.getProperty( "im.2p", w1 + nexo + "ed" ) );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.ir&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_ir( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.ir", 0 );
        if( props != null ) {
            es_v_conj_ir( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.cir&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_cir( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.cir", 0 );
        if( props != null ) {
            props.put( "nexo", "c" );

            es_v_conj_ir( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.zc.cir&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_zc_cir( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.zc.cir", 0 );
        if( props != null ) {
            String w1 = props.getProperty( "1", "{{{1}}}" );
            String w2 = props.getProperty( "2", "" );

            props.put( "irregular", "x" );
            props.put( "nexo", "c" );

            props.put( "i.p.1s", w1 + "zco" );
            //presente de subjuntivo
            props.put( "s.p.1s", w1 + "zca" );
            props.put( "s.p.2s", w1 + "zcas" );
            props.put( "s.p.2s2", w1 + "zcás" );
            props.put( "s.p.3s", w1 + "zca" );
            props.put( "s.p.1p", w1 + "zcamos" );
            props.put( "s.p.2p", w1 + "zcáis" );
            props.put( "s.p.3p", w1 + "zcan" );

            //imperativo
            if( w2.length() > 0 ) {
                props.put( "im.3s", w2 + "zcase" );
                props.put( "im.1p", w1 + "zcámonos" );
                props.put( "im.3p", w2 + "zcanse" );
            } else {
                props.put( "im.3s", w1 + "zca" );
                props.put( "im.1p", w1 + "zcamos" );
                props.put( "im.3p", w1 + "zcan" );
            }

            es_v_conj_ir( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.decir&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_decir( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.decir", 0 );
        if( props != null ) {
            String w1 = props.getProperty( "1", "{{{1}}}" );
            String w2 = props.getProperty( "2", "" );
            boolean pronominal = props.getProperty( "pronominal", "" ).length() > 0;

            props.put( "irregular", "si" );
            props.put( "1", w1 + "dec" );
            if( pronominal ) {
                props.put( "2", w1 + "díg" );
            }

            props.put( "i.p.1s", w1 + "digo" );
            props.put( "i.p.2s", w1 + "dices" );
            props.put( "i.p.3s", w1 + "dice" );
            props.put( "i.p.3p", w1 + "dicen" );

            props.put( "i.pp.1s", w1 + "dije" );
            props.put( "i.pp.2s", w1 + "dijiste" );
            props.put( "i.pp.3s", w1 + "dijo" );
            props.put( "i.pp.1p", w1 + "dijimos" );
            props.put( "i.pp.2p", w1 + "dijisteis" );
            props.put( "i.pp.3p", w1 + "dijeron" );

            props.put( "i.f.1s", w1 + "diré" );
            props.put( "i.f.2s", w1 + "dirás" );
            props.put( "i.f.3s", w1 + "dirá" );
            props.put( "i.f.1p", w1 + "diremos" );
            props.put( "i.f.2p", w1 + "diréis" );
            props.put( "i.f.3p", w1 + "dirán" );

            props.put( "i.c.1s", w1 + "diría" );
            props.put( "i.c.2s", w1 + "dirías" );
            props.put( "i.c.3s", w1 + "diría" );
            props.put( "i.c.1p", w1 + "diríamos" );
            props.put( "i.c.2p", w1 + "diríais" );
            props.put( "i.c.3p", w1 + "dirían" );

            props.put( "s.p.1s", w1 + "diga" );
            props.put( "s.p.2s", w1 + "digas" );
            props.put( "s.p.2s2", w1 + "digás" );
            props.put( "s.p.3s", w1 + "diga" );
            props.put( "s.p.1p", w1 + "digamos" );
            props.put( "s.p.2p", w1 + "digáis" );
            props.put( "s.p.3p", w1 + "digan" );

            props.put( "s.pi.1s", w1 + "dijera" );
            props.put( "s.pi.2s", w1 + "dijeras" );
            props.put( "s.pi.3s", w1 + "dijera" );
            props.put( "s.pi.1p", w1 + "dijéramos" );
            props.put( "s.pi.2p", w1 + "dijerais" );
            props.put( "s.pi.3p", w1 + "dijeran" );

            props.put( "s.pi2.1s", w1 + "dijese" );
            props.put( "s.pi2.2s", w1 + "dijeses" );
            props.put( "s.pi2.3s", w1 + "dijese" );
            props.put( "s.pi2.1p", w1 + "dijésemos" );
            props.put( "s.pi2.2p", w1 + "dijeseis" );
            props.put( "s.pi2.3p", w1 + "dijesen" );

            props.put( "s.f.1s", w1 + "dijere" );
            props.put( "s.f.2s", w1 + "dijeres" );
            props.put( "s.f.3s", w1 + "dijere" );
            props.put( "s.f.1p", w1 + "dijéremos" );
            props.put( "s.f.2p", w1 + "dijereis" );
            props.put( "s.f.3p", w1 + "dijeren" );

            props.put( "im.2s", w1 + "di" );
            props.put( "im.1p", w1 + "digamos" );

            es_v_conj_ir( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.gir&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_gir( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.gir", 0 );
        if( props != null ) {
            props.put( "nexo", "g" );

            es_v_conj_ir( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.-ie-i-ue-u-.ir&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_ie_i_ue_ir( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.-ie-i-ue-u-.ir", 0 );
        if( props != null ) {
            es_v_conj_ie_i_ue_ir( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.-ie-i-ue-u-.ir&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_ie_i_ue_ir( Properties props ) {
        String w2 = props.getProperty( "2", "{{{2}}}" );
        String w3 = props.getProperty( "3", "{{{3}}}" );
        String w4 = props.getProperty( "4", "{{{4}}}" );
        String nexo = props.getProperty( "nexo", "" );
        boolean pronominal = props.getProperty( "pronominal", "" ).length() > 0;

        props.put( "irregular", "x" );

        //presente de indicativo
        props.put( "i.p.1s", w2 + nexo + "o" );
        props.put( "i.p.2s", w2 + nexo + "es" );
        props.put( "i.p.3s", w2 + nexo + "e" );
        props.put( "i.p.3p", w2 + nexo + "en" );

        //pretérito perfecto simple de indicativo
        props.put( "i.pp.3s", w3 + nexo + "ió" );
        props.put( "i.pp.3p", w3 + nexo + "ieron" );

        //presente de subjuntivo
        props.put( "s.p.1s", w2 + nexo + "a" );
        props.put( "s.p.2s", w2 + nexo + "as" );
        props.put( "s.p.2s2", w3 + nexo + "ás" );
        props.put( "s.p.3s", w2 + nexo + "a" );
        props.put( "s.p.1p", w3 + nexo + "amos" );
        props.put( "s.p.2p", w3 + nexo + "áis" );
        props.put( "s.p.3p", w2 + nexo + "an" );

        //subjuntivo imperfecto (-iera) 
        props.put( "s.pi.1s", w3 + nexo + "iera" );
        props.put( "s.pi.2s", w3 + nexo + "ieras" );
        props.put( "s.pi.3s", w3 + nexo + "iera" );
        props.put( "s.pi.1p", w3 + nexo + "iéramos" );
        props.put( "s.pi.2p", w3 + nexo + "ierais" );
        props.put( "s.pi.3p", w3 + nexo + "ieran" );

        //subjuntivo imperfecto (-iese)
        props.put( "s.pi2.1s", w3 + nexo + "iese" );
        props.put( "s.pi2.2s", w3 + nexo + "ieses" );
        props.put( "s.pi2.3s", w3 + nexo + "iese" );
        props.put( "s.pi2.1p", w3 + nexo + "iésemos" );
        props.put( "s.pi2.2p", w3 + nexo + "ieseis" );
        props.put( "s.pi2.3p", w3 + nexo + "iesen" );

        //subjuntivo futuro (-iere)
        props.put( "s.f.1s", w3 + nexo + "iere" );
        props.put( "s.f.2s", w3 + nexo + "ieres" );
        props.put( "s.f.3s", w3 + nexo + "iere" );
        props.put( "s.f.1p", w3 + nexo + "iéremos" );
        props.put( "s.f.2p", w3 + nexo + "iereis" );
        props.put( "s.f.3p", w3 + nexo + "ieren" );

        //imperativo
        if( pronominal ) {
            props.put( "im.2s", w4 + nexo + "ete]]|[[{{{2}}}{{{nexo|}}}e" );
            props.put( "im.3s", w4 + nexo + "ase]]|[[{{{3}}}{{mutacion.es.nexo.ei|{{{nexo|}}}}}a" );
            props.put( "im.1p", w3 + nexo + "ámonos]]|[[{{{2}}}{{mutacion.es.nexo.ei|{{{nexo|}}}}}amos" );
            props.put( "im.3p", w4 + nexo + "anse]]|[[{{{3}}}{{mutacion.es.nexo.ei|{{{nexo|}}}}}an" );
        } else {
            props.put( "im.2s", w2 + nexo + "e" );
            props.put( "im.3s", w3 + nexo + "a" );
            props.put( "im.1p", w2 + nexo + "amos" );
            props.put( "im.3p", w3 + nexo + "an" );
        }

        es_v_conj_ir( props );

    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.ñir&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_nir( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.ñir", 0 );
        if( props != null ) {
            String w1 = props.getProperty( "1", "{{{1}}}" );

            props.put( "irregular", "x" );

            //pretérito indefinido
            props.put( "i.pp.3s", w1 + "ó" );
            props.put( "i.pp.3p", w1 + "eron" );

            //subjuntivo imperfecto (-iera) 
            props.put( "s.pi.1s", w1 + "era" );
            props.put( "s.pi.2s", w1 + "eras" );
            props.put( "s.pi.3s", w1 + "era" );
            props.put( "s.pi.1p", w1 + "éramos" );
            props.put( "s.pi.2p", w1 + "erais" );
            props.put( "s.pi.3p", w1 + "eran" );

            //subjuntivo imperfecto (-iese) 
            props.put( "s.pi2.1s", w1 + "ese" );
            props.put( "s.pi2.2s", w1 + "eses" );
            props.put( "s.pi2.3s", w1 + "ese" );
            props.put( "s.pi2.1p", w1 + "ésemos" );
            props.put( "s.pi2.2p", w1 + "eseis" );
            props.put( "s.pi2.3p", w1 + "esen" );

            //subjuntivo futuro (-iere) 
            props.put( "s.f.1s", w1 + "ere" );
            props.put( "s.f.2s", w1 + "eres" );
            props.put( "s.f.3s", w1 + "ere" );
            props.put( "s.f.1p", w1 + "éremos" );
            props.put( "s.f.2p", w1 + "ereis" );
            props.put( "s.f.3p", w1 + "eren" );

            es_v_conj_ir( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.seguir&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_seguir( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.seguir", 0 );
        if( props != null ) {
            String w1 = props.getProperty( "1", "{{{1}}}" );

            props.put( "1", w1 + "se" );
            props.put( "2", w1 + "si" );
            props.put( "3", w1 + "si" );
            props.put( "4", w1 + "sí" );
            props.put( "nexo", w1 + "gu" );

            es_v_conj_ie_i_ue_ir( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.uir&action=edit
     * @param wikiText the completely wiki text
     */
    private void es_v_conj_uir( String wikiText ) {
        Properties props = BookUtils.parseRule( wikiText, "es.v.conj.uir", 0 );
        if( props != null ) {
            String w1 = props.getProperty( "1", "{{{1}}}" );
            String w2 = props.getProperty( "2", "{{{2}}}" );
            boolean pronominal = props.getProperty( "pronominal", "" ).length() > 0;

            props.put( "irregular", "x" );

            //presente de indicativo
            props.put( "i.p.1s", w1 + "yoo" );
            props.put( "i.p.2s", w1 + "yeses" );
            props.put( "i.p.2s2", w1 + "ís" );
            props.put( "i.p.3s", w1 + "yee" );
            props.put( "i.p.2p", w1 + "ís" );
            props.put( "i.p.3p", w1 + "yen" );

            //pretérito perfecto simple (indefinido)
            props.put( "i.pp.1s", w1 + "í" );
            props.put( "i.pp.3s", w1 + "yó" );
            props.put( "i.pp.3p", w1 + "yeron" );

            //presente de subjuntivo
            props.put( "s.p.1s", w1 + "ya" );
            props.put( "s.p.2s", w1 + "yas" );
            props.put( "s.p.2s2", w1 + "yás" );
            props.put( "s.p.3s", w1 + "ya" );
            props.put( "s.p.1p", w1 + "yamos" );
            props.put( "s.p.2p", w1 + "yáis" );
            props.put( "s.p.3p", w1 + "yan" );

            //subjuntivo imperfecto (-iera) 
            props.put( "s.pi.1s", w1 + "yera" );
            props.put( "s.pi.2s", w1 + "yeras" );
            props.put( "s.pi.3s", w1 + "yera" );
            props.put( "s.pi.1p", w1 + "yéramos" );
            props.put( "s.pi.2p", w1 + "yerais" );
            props.put( "s.pi.3p", w1 + "yeran" );

            //subjuntivo imperfecto (-iese) 
            props.put( "s.pi2.1s", w1 + "yese" );
            props.put( "s.pi2.2s", w1 + "yeses" );
            props.put( "s.pi2.3s", w1 + "yese" );
            props.put( "s.pi2.1p", w1 + "yésemos" );
            props.put( "s.pi2.2p", w1 + "yeseis" );
            props.put( "s.pi2.3p", w1 + "yesen" );

            //subjuntivo futuro (-iere) 
            props.put( "s.f.1s", w1 + "yere" );
            props.put( "s.f.2s", w1 + "yeres" );
            props.put( "s.f.3s", w1 + "yere" );
            props.put( "s.f.1p", w1 + "yéremos" );
            props.put( "s.f.2p", w1 + "yereis" );
            props.put( "s.f.3p", w1 + "yeren" );

            //imperativo
            if( pronominal ) {
                props.put( "im.2s", w2 + "yete" );
                props.put( "im.2s2", w1 + "ite" );
                props.put( "im.3s", w2 + "yase" );
                props.put( "im.1p", w1 + "yámonos" );
                props.put( "im.3p", w2 + "yanse" );
            } else  {
                props.put( "im.2s", w1 + "ye" );
                props.put( "im.2s2", w1 + "í" );
                props.put( "im.3s", w1 + "ya" );
                props.put( "im.1p", w1 + "yamos" );
                props.put( "im.3p", w2 + "yan" );
           }
            
           es_v_conj_ir( props );
        }
    }

    /**
     * http://es.wiktionary.org/w/index.php?title=Plantilla:es.v.conj.ir&action=edit
     * @param props parsed parameters, can not be null
     */
    private void es_v_conj_ir( Properties props ) {
        String w1 = props.getProperty( "1", "{{{1}}}" );
        String nexo = props.getProperty( "nexo", "" );
        String part = props.getProperty( "part" );
        String part2 = props.getProperty( "part2" );
        boolean impersonal = props.getProperty( "impersonal", "" ).length() > 0; //plural or singular
        boolean impersonalPlural = "plural".equalsIgnoreCase( props.getProperty( "impersonal", "" ) );

        boolean pronominal = props.getProperty( "pronominal", "" ).length() > 0;

        addWord( w1 + nexo + "ir" + (pronominal ? "se" : "") );
        addWord( w1 + nexo + "ito" );
        addWord( w1 + nexo + (pronominal ? "iéndose" : "iendo") ); // Simples Gerundio
        if( part != null ) {
            addWord( part );
        }
        if( part2 != null ) {
            addWord( part2 );
        }

        //indicativo presente
        if( !impersonal ) {
            addWord( props.getProperty( "i.p.1s", w1 + nexo + "o" ) );
            addWord( props.getProperty( "i.p.2s", w1 + nexo + "es" ) );
            addWord( props.getProperty( "i.p.2s2", w1 + nexo + "ís" ) );
        }
        addWord( props.getProperty( "i.p.3s", w1 + nexo + "e" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "i.p.1p", w1 + nexo + "imos" ) );
            addWord( props.getProperty( "i.p.2p", w1 + nexo + "ís" ) );
        }
        addWord( props.getProperty( "i.p.3p", w1 + nexo + "en" ) );

        //imperfecto
        if( !impersonal ) {
            addWord( props.getProperty( "i.pi.1s", w1 + nexo + "ía" ) );
            addWord( props.getProperty( "i.pi.2s", w1 + nexo + "ías" ) );
        }
        addWord( props.getProperty( "i.pi.3s", w1 + nexo + "ía" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "i.pi.1p", w1 + nexo + "íamos" ) );
            addWord( props.getProperty( "i.pi.2p", w1 + nexo + "íais" ) );
        }
        addWord( props.getProperty( "i.pi.3p", w1 + nexo + "ían" ) );

        //indefinido
        if( !impersonal ) {
            addWord( props.getProperty( "i.pp.1s", w1 + nexo + "í" ) );
            addWord( props.getProperty( "i.pp.2s", w1 + nexo + "iste" ) );
        }
        addWord( props.getProperty( "i.pp.3s", w1 + nexo + "ió" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "i.pp.1p", w1 + nexo + "imos" ) );
            addWord( props.getProperty( "i.pp.2p", w1 + nexo + "isteis" ) );
        }
        addWord( props.getProperty( "i.pp.3p", w1 + nexo + "ieron" ) );

        //futuro
        if( !impersonal ) {
            addWord( props.getProperty( "i.f.1s", w1 + nexo + "iré" ) );
            addWord( props.getProperty( "i.f.2s", w1 + nexo + "irás" ) );
        }
        addWord( props.getProperty( "i.f.3s", w1 + nexo + "irá" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "i.f.1p", w1 + nexo + "iremos" ) );
            addWord( props.getProperty( "i.f.2p", w1 + nexo + "iréis" ) );
        }
        addWord( props.getProperty( "i.f.3p", w1 + nexo + "irán" ) );

        //condicional
        if( !impersonal ) {
            addWord( props.getProperty( "i.c.1s", w1 + nexo + "iría" ) );
            addWord( props.getProperty( "i.c.2s", w1 + nexo + "irías" ) );
        }
        addWord( props.getProperty( "i.c.3s", w1 + nexo + "iría" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "i.c.1p", w1 + nexo + "iríamos" ) );
            addWord( props.getProperty( "i.c.2p", w1 + nexo + "iríais" ) );
        }
        addWord( props.getProperty( "i.c.3p", w1 + nexo + "irían" ) );

        //subjuntivo presente
        if( !impersonal ) {
            addWord( props.getProperty( "s.p.1s", w1 + nexo + "a" ) );
            addWord( props.getProperty( "s.p.2s", w1 + nexo + "as" ) );
            addWord( props.getProperty( "s.p.2s2", w1 + nexo + "ás" ) );
        }
        addWord( props.getProperty( "s.p.3s", w1 + nexo + "a" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "s.p.1p", w1 + nexo + "amos" ) );
            addWord( props.getProperty( "s.p.2p", w1 + nexo + "áis" ) );
        }
        addWord( props.getProperty( "s.p.3p", w1 + nexo + "an" ) );

        //imperfecto (-iera)
        if( !impersonal ) {
            addWord( props.getProperty( "s.pi.1s", w1 + nexo + "iera" ) );
            addWord( props.getProperty( "s.pi.2s", w1 + nexo + "ieras" ) );
        }
        addWord( props.getProperty( "s.pi.3s", w1 + nexo + "iera" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "s.pi.1p", w1 + nexo + "iéramos" ) );
            addWord( props.getProperty( "s.pi.2p", w1 + nexo + "ierais" ) );
        }
        addWord( props.getProperty( "s.pi.3p", w1 + nexo + "ieran" ) );

        //imperfecto (-iese)
        if( !impersonal ) {
            addWord( props.getProperty( "s.pi2.1s", w1 + nexo + "iese" ) );
            addWord( props.getProperty( "s.pi2.2s", w1 + nexo + "ieses" ) );
        }
        addWord( props.getProperty( "s.pi2.3s", w1 + nexo + "iese" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "s.pi2.1p", w1 + nexo + "iésemos" ) );
            addWord( props.getProperty( "s.pi2.2p", w1 + nexo + "ieseis" ) );
        }
        addWord( props.getProperty( "s.pi2.3p", w1 + nexo + "iesen" ) );

        //futuro
        if( !impersonal ) {
            addWord( props.getProperty( "s.f.1s", w1 + nexo + "iere" ) );
            addWord( props.getProperty( "s.f.2s", w1 + nexo + "ieres" ) );
        }
        addWord( props.getProperty( "s.f.3s", w1 + nexo + "iere" ) );
        if( !impersonal ) {
            addWord( props.getProperty( "s.f.1p", w1 + nexo + "iéremos" ) );
            addWord( props.getProperty( "s.f.2p", w1 + nexo + "iereis" ) );
        }
        addWord( props.getProperty( "s.f.3p", w1 + nexo + "ieren" ) );

        //imperativo
        if( !impersonalPlural ) {
            addWord( props.getProperty( "im.2s", w1 + nexo + "e" ) );
            addWord( props.getProperty( "im.2s2", w1 + nexo + "í" ) );
            addWord( props.getProperty( "im.2p", w1 + nexo + "id" ) );
        }
    }

    private void findRuleAndAddWords( String word, String wikiText, String rule, String[] endingName ) {
        findRuleAndAddWords( word, wikiText, rule, endingName, null );
    }

    private void findRuleAndAddWords( String word, String wikiText, String rule, String[] endingName, String[] ending1 ) {
        findRuleAndAddWords( word, wikiText, rule, endingName, ending1, null );
    }

    private void findRuleAndAddWords( String word, String wikiText, String rule, String[] endingName, String[] ending1, String[] ending2 ) {
        findRuleAndAddWords( word, wikiText, rule, endingName, ending1, ending2, null, null );
    }

    private void findRuleAndAddWords( String word, String wikiText, String rule, String[] endingName, String[] ending1, String[] ending2, String[] ending3, String[] ending4 ) {
        int idx = wikiText.indexOf( "{{" + rule + "}}" );
        if( idx < 0 ) {
            idx = wikiText.indexOf( "{{" + rule + "|" );
            if( idx < 0 ) {
                return;
            }
        }
        if( endingName != null ) {
            for( String ending : endingName ) {
                addWord( word + ending );
            }
        }
        if( ending1 == null ) {
            return;
        }
        idx += rule.length() + 3;
        int idx2 = wikiText.indexOf( "}}", idx );
        String params = wikiText.substring( idx, idx2 );

        StringTokenizer tok = new StringTokenizer( params, "|" );
        if( tok.hasMoreElements() ) {
            String first = tok.nextToken().trim();
            if( ending1 != null ) {
                for( String ending : ending1 ) {
                    addWord( first + ending );
                }
            }

            if( tok.hasMoreElements() ) {
                String second = tok.nextToken().trim();
                if( ending2 != null ) {
                    for( String ending : ending2 ) {
                        addWord( second + ending );
                    }
                }

                if( tok.hasMoreElements() ) {
                    String third = tok.nextToken().trim();
                    if( ending3 != null ) {
                        for( String ending : ending3 ) {
                            addWord( third + ending );
                        }
                    }

                    if( tok.hasMoreElements() ) {
                        String four = tok.nextToken().trim();
                        if( ending4 != null ) {
                            for( String ending : ending4 ) {
                                addWord( four + ending );
                            }
                        }
                    }
                }

            }
        }
    }

}
