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

/**
 * Interface to be implemented by a user of the spellchecker that provides the ability to
 * show/log errors and information messages as required by applications using the dictionary
 * 
 * @author Jesper Nielsen, jesniels@gmail.com
 */
public interface MessageHandler {

    /**
     * Show an error .
     * 
     * @param title The title of the error message
     * @param errorText The error text 
     * @param throwable The exception causing the error, can not be null
     */
    void handleError( String title, String errorText, Throwable throwable );

    /**
     * handle an exception however it should be handled
     * 
     * @param throwable The exception causing the error, can not be null
     */
    void handleException( Throwable throwable );

    /**
     * Show information
     * 
     * @param parent frame (based on the spell checker dialog parent frame)
     * @param title The title of the information message
     * @param info The information message from the dictionary
     */
    void handleInformation( Container parent, String title, String info );

}
