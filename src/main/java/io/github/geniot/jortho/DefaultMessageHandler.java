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
 *  Created on 02.12.2011
 */
package io.github.geniot.jortho;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Default implementation of the message handler provider that shows the messages in
 * Swing dialogs and printing stacktraces to the console
 * 
 * @author Jesper Nielsen, jesniels@gmail.com
 */
public class DefaultMessageHandler implements MessageHandler {

	// The frame that should own the Swing message dialogs (to avoid them shown behind and locking)
	private JFrame ownerFrame;
	
	/**
	 * Constructor for the message handler.
	 * 
	 * @param ownerFrame The frame that should own the Swing message dialogs (to avoid them shown behind and locking)
	 */
	public DefaultMessageHandler( JFrame ownerFrame ) {
		this.ownerFrame = ownerFrame;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void handleError( String title, String errorText, Throwable throwable) {
		throwable.printStackTrace();
		
		JOptionPane.showMessageDialog( ownerFrame, throwable.toString(), title, JOptionPane.ERROR_MESSAGE );
	}

    /**
     * {@inheritDoc}
     */
	public void handleException( Throwable throwable) {
		throwable.printStackTrace();
	}

    /**
     * {@inheritDoc}
     */
	public void handleInformation(Container parent, String title, String info) {
        JOptionPane.showMessageDialog( parent, info, title, JOptionPane.INFORMATION_MESSAGE );
	}
}
