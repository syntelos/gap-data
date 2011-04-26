/*
 * Gap Data
 * Copyright (C) 2009 John Pritchard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package gap.service;

import gap.data.Kind;

/**
 * Serializable selection set for servlet session.
 * 
 * @see gap.Request
 * @author jdp
 */
public final class Selection
    extends gap.util.ListPrimitiveKey
{
    private final static String ClassName = Selection.class.getName();

    public final static String SessionKey(Kind kind){
        return ClassName+'#'+kind.name;
    }


    public final String sessionKey;


    public Selection(Kind kind){
        super(kind,KeySet);
        this.sessionKey = SessionKey(kind);
    }

}
