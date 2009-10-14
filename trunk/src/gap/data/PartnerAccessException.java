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
package gap.data;

/**
 * Thrown from {@link Store} for violations of the {@link
 * PartnerReadWrite} access control policy.
 */
public class PartnerAccessException
    extends java.security.AccessControlException
{

    public PartnerAccessException(){
        super("Requires partner status.");
    }
    public PartnerAccessException(Kind kind){
        super(kind+" requires partner status.");
    }
    public PartnerAccessException(String kind){
        super(kind+" requires partner status.");
    }

}
