/*
 *  JOrtho
 *
 *  Copyright (C) 2005-2013 by i-net software
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
 * Created on 15.02.2013
 */
package io.github.geniot.jortho;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * A small about dialog
 */
class AboutDialog extends JDialog {

    /**
     * Create a new instance
     * @param parent
     */
    AboutDialog( JDialog parent ) {
        super( parent, Utils.getResource( "about" ), true );
        Utils.setDialogIcon( this );
        setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        Container content = getContentPane();
        content.setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.insets = new Insets( 5, 5, 5, 5 );
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        String licensee = null;
        String license = null;
        URL url = getClass().getResource( "/jortho-license.properties" );
        if( url != null ) {
            Properties props = new Properties();
            try {
                props.load( url.openStream() );
                licensee = props.getProperty( "Licensee" );
                license = props.getProperty( "License" );
            } catch( IOException ex ) {
                ex.printStackTrace();
            }
        }

        if( licensee != null && licensee.length() > 0 && "Commercial License".equalsIgnoreCase( license ) ) {
            // Commercial License
            content.add( Utils.getLabel( "JOrtho - Commercial License" ), gbc );
            content.add( Utils.getLabel( "Licensee: " + licensee ), gbc );
            content.add( Utils.getLabel( "2005 - 2013 \u00a9 i-net software, Berlin, Germany" ), gbc );
        } else {
            // GPL License
            content.add( Utils.getLabel( "JOrtho - GNU General Public License (GPL)" ), gbc );

            content.add( Utils.getLabel( "2005 - 2013 \u00a9 i-net software, Berlin, Germany" ), gbc );
            JLabel link = Utils.getLabel( "<html><u>http://www.inetsoftware.de/other-products/jortho</u></html>" );
            content.add( link, gbc );
            link.addMouseListener( new MouseAdapter() {

                @Override
                public void mouseClicked( MouseEvent e ) {
                    try {
                        Desktop.getDesktop().browse( new URI( "http://www.inetsoftware.de/other-products/jortho" ) );
                    } catch( Exception ex ) {
                        ex.printStackTrace();
                    }
                }
            } );
        }
        pack();
        setLocationRelativeTo( parent );
    }
}
