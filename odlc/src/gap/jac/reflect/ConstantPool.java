/*
 * Copyright 1999-2006 Sun Microsystems, Inc.  All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc., 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * "CLASSPATH" EXCEPTION TO THE GPL
 * 
 * Certain source files distributed by Sun Microsystems, Inc.  are subject to
 * the following clarification and special exception to the GPL, but only where
 * Sun has expressly included in the particular source file's header the words
 * "Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the LICENSE file that accompanied this code."
 * 
 *   Linking this library statically or dynamically with other modules is making
 *   a combined work based on this library.  Thus, the terms and conditions of
 *   the GNU General Public License cover the whole combination.
 * 
 *   As a special exception, the copyright holders of this library give you
 *   permission to link this library with independent modules to produce an
 *   executable, regardless of the license terms of these independent modules,
 *   and to copy and distribute the resulting executable under terms of your
 *   choice, provided that you also meet, for each linked independent module,
 *   the terms and conditions of the license of that module.  An independent
 *   module is a module which is not derived from or based on this library.  If
 *   you modify this library, you may extend this exception to your version of
 *   the library, but you are not obligated to do so.  If you do not wish to do
 *   so, delete this exception statement from your version.
 */
package gap.jac.reflect;

import java.lang.reflect.*;

/** Provides reflective access to the constant pools of classes.
    Currently this is needed to provide reflective access to annotations
    but may be used by other internal subsystems in the future. */

public class ConstantPool {
  // Number of entries in this constant pool (= maximum valid constant pool index)
  public int      getSize()                      { return getSize0            (constantPoolOop);        }
  public Class    getClassAt         (int index) { return getClassAt0         (constantPoolOop, index); }
  public Class    getClassAtIfLoaded (int index) { return getClassAtIfLoaded0 (constantPoolOop, index); }
  // Returns either a Method or Constructor.
  // Static initializers are returned as Method objects.
  public Member   getMethodAt        (int index) { return getMethodAt0        (constantPoolOop, index); }
  public Member   getMethodAtIfLoaded(int index) { return getMethodAtIfLoaded0(constantPoolOop, index); }
  public Field    getFieldAt         (int index) { return getFieldAt0         (constantPoolOop, index); }
  public Field    getFieldAtIfLoaded (int index) { return getFieldAtIfLoaded0 (constantPoolOop, index); }
  // Fetches the class name, member (field, method or interface
  // method) name, and type descriptor as an array of three Strings
  public String[] getMemberRefInfoAt (int index) { return getMemberRefInfoAt0 (constantPoolOop, index); }
  public int      getIntAt           (int index) { return getIntAt0           (constantPoolOop, index); }
  public long     getLongAt          (int index) { return getLongAt0          (constantPoolOop, index); }
  public float    getFloatAt         (int index) { return getFloatAt0         (constantPoolOop, index); }
  public double   getDoubleAt        (int index) { return getDoubleAt0        (constantPoolOop, index); }
  public String   getStringAt        (int index) { return getStringAt0        (constantPoolOop, index); }
  public String   getUTF8At          (int index) { return getUTF8At0          (constantPoolOop, index); }

  //---------------------------------------------------------------------------
  // Internals only below this point
  //

  static {
      Reflection.registerFieldsToFilter(ConstantPool.class, new String[] { "constantPoolOop" });
  }

  // HotSpot-internal constant pool object (set by the VM, name known to the VM)
  private Object constantPoolOop;

  private native int      getSize0            (Object constantPoolOop);
  private native Class    getClassAt0         (Object constantPoolOop, int index);
  private native Class    getClassAtIfLoaded0 (Object constantPoolOop, int index);
  private native Member   getMethodAt0        (Object constantPoolOop, int index);
  private native Member   getMethodAtIfLoaded0(Object constantPoolOop, int index);
  private native Field    getFieldAt0         (Object constantPoolOop, int index);
  private native Field    getFieldAtIfLoaded0 (Object constantPoolOop, int index);
  private native String[] getMemberRefInfoAt0 (Object constantPoolOop, int index);
  private native int      getIntAt0           (Object constantPoolOop, int index);
  private native long     getLongAt0          (Object constantPoolOop, int index);
  private native float    getFloatAt0         (Object constantPoolOop, int index);
  private native double   getDoubleAt0        (Object constantPoolOop, int index);
  private native String   getStringAt0        (Object constantPoolOop, int index);
  private native String   getUTF8At0          (Object constantPoolOop, int index);
}
