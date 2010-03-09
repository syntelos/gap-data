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

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Text;

/**
 * A data bean for programming, interface for compilation.
 * 
 * @see Resource
 * @see ToolFunction
 * @see gap.jela.JelaToolFunction
 * @author jdp
 */
public interface Tool
    extends Resource
{

    public boolean hasJavaMethodName(boolean inheritable);
    public String getJavaMethodName(boolean inheritable);
    public void setJavaMethodName(String name);

    public boolean hasJavaMethodSource(boolean inheritable);
    public Text getJavaMethodSource(boolean inheritable);
    public void setJavaMethodSource(Text text);

}
