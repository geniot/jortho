/*
 *  JOrtho
 *
 *  Copyright (C) 2005-2011 by i-net software
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
 *  Created on 6.12.2011
 */
package io.github.geniot.jortho;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 * Interface used by other application to provide custom UI components
 * with added functionality if needed.
 *
 * @author Jesper Nielsen, jesniels@gmail.com
 */
public interface CustomUIProvider {

    /**
     * Creates a JButton.
     * @param resource The resource text for this button
     * @return A JButton that can be used by the spell checker
     */
    JButton getButton( String resource );

    /**
     * Creates a JTextField.
     *
     * @return A JTextField that can be used by the spell checker
     */
    JTextField getTextField();


    /**
     * Creates a JLabel.
     *
     * @param text The text for the Label
     * @return A JLabel that can be used by the spell checker
     */
    JLabel getLabel( String text );

    /**
     * Creates a JList.
     *
     * @return A JList that can be used by the spell checker
     */
    JList getList();
}
