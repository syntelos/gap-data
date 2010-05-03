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
package gap;

/**
 * The gap permission types.
 * 
 * Note that admin access control is marked on classes with the {@link
 * gap.data.AdminReadWrite} interface.  The admin permission is an aid
 * to avoid admin access exceptions from the store for marked data
 * classes.
 * 
 * @author jdp
 */
public enum Permissions {
    /**
     * Public may not be logged- in.
     */
    Public,
    /**
     * Members are logged- in.
     */
    Members,
    /**
     * Admins are configured in the appengine dashboard as the
     * developers of the appengine application.
     */
    Admin;
}
