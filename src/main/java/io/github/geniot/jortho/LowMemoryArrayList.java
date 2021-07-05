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
 * Created on 01.10.2010
 */
package io.github.geniot.jortho;

/**
 * An ArrayList that need only a minimum of memory size. The list will be need on reading the dictionary with a very
 * large count.
 * 
 * @author Volker Berlin
 */
class LowMemoryArrayList<E> {

    /**
     * The array buffer into which the elements of the ArrayList are stored. The capacity of the ArrayList is the length
     * of this array buffer.
     */
    private transient E[] elementData;

    /**
     * Returns the number of elements in this list.
     * 
     * @return the number of elements in this list.
     */
    public int size() {
        return elementData == null ? 0 : elementData.length;
    }

    /**
     * Returns the element at the specified position in this list.
     * 
     * @param index
     *            index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException
     *             if the index is out of range
     */
    public E get( int index ) {
        return elementData[index];
    }

    /**
     * Appends the specified element to the end of this list.
     * 
     * @param o
     *            element to be appended to this list.
     */
    public void add( E o ) {
        int size = size();
        Object tempData[] = new Object[size + 1];
        if( size > 0 ) {
            System.arraycopy( elementData, 0, tempData, 0, size );
        }
        elementData = (E[])tempData;
        elementData[size] = o;
    }

    /**
     * Inserts the specified element at the specified position in this list. Shifts the element currently at that
     * position (if any) and any subsequent elements to the right (adds one to their indices).
     * 
     * @param index
     *            index at which the specified element is to be inserted
     * @param element
     *            element to be inserted
     * @throws IndexOutOfBoundsException
     *             if the index is out of range
     */
    public void add( int index, E element ) {
        int size = size();
        Object tempData[] = new Object[size + 1];
        if( size > 0 ) {
            System.arraycopy( elementData, 0, tempData, 0, index );
            System.arraycopy( elementData, index, tempData, index + 1, size - index );
        }
        elementData = (E[])tempData;
        elementData[index] = element;
    }

}
