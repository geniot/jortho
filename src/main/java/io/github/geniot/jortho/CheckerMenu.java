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
 *  Created on 07.11.2005
 */
package io.github.geniot.jortho;

import java.awt.event.*;

import javax.swing.*;

/**
 * @author Volker Berlin
 */
class CheckerMenu extends JMenu implements HierarchyListener {
    
    private final CheckerListener listener;

    CheckerMenu(SpellCheckerOptions options){
        super( Utils.getResource("spelling"));
        listener = new CheckerListener(this, options);
        super.addHierarchyListener(this);
    }


    public void hierarchyChanged(HierarchyEvent ev) {
        // If this sub menu is added to a parent
        // then an Listener is added to request show popup events of the parent
        if(ev.getChangeFlags() == HierarchyEvent.PARENT_CHANGED && ev.getChanged() == this){
            JPopupMenu parent = (JPopupMenu)getParent();
            if(parent != null){
                parent.addPopupMenuListener(listener);
            }else{
                ((JPopupMenu)ev.getChangedParent()).removePopupMenuListener(listener);
            }
        }
    }
}
