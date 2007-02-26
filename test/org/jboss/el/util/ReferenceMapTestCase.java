/*
 * ReferenceMapTestCase.java
 *
 * Created on December 16, 2006, 4:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jboss.el.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author jhook
 */
public class ReferenceMapTestCase extends TestCase {
    
    public static class Foo {
        private final int id;
        private static int uid = 0;
        public Foo() { this.id = uid++; }
        public Foo(int id) { this.id = id; }
        public String toString() {
            return "Foo[" + this.id + "]";
        }
        public boolean equals(Object obj) {
            return obj instanceof Foo && ((Foo) obj).id == this.id;
        }
        public int hashCode() { return this.id; }

		protected void finalize() throws Throwable {
			//System.out.println("Finalizing: " + this);
			super.finalize();
		}
        
    }
    
    public static class Bar {
        private final int id;
        private static int uid = 0;
        public Bar() { this.id = uid++; }
        public Bar(int id) { this.id = id; }
        public String toString() {
            return "Bar[" + this.id + "]";
        }

		protected void finalize() throws Throwable {
			//System.out.println("Finalizing: " + this);
			super.finalize();
		}
    }
    
    public void testReferences() throws Exception {
        
        ReferenceCache<Foo,Bar> map = new ReferenceCache<Foo,Bar>(ReferenceCache.Type.Weak, ReferenceCache.Type.Weak) {
            protected Bar create(Foo key) {
                //System.out.println("Create called: " + key);
                //throw new UnsupportedOperationException();
                //return new Bar(key.id);
            	return null;
            }
        };
        
        //map = new HashMap<Foo,Bar>();
        
        Collection<Foo> keys = new ArrayList<Foo>();
        for (int i = 0; i < 1000; i++) {
            keys.add(new Foo(i));
        }
        
        for (Foo f : keys) {
            map.put(f, new Bar(f.id));
        }
        
        System.out.println("\n----------------\n" + map.size() + "\n-----------------------------\n");
        
        for (Foo f : keys) {
            assertNotNull("Key not null " + f, map.get(f));
        }
        
        // comment and uncomment this line
        keys.clear();
        keys = null;
        
        System.gc();
        System.gc();
        
        System.out.println(map.size());
        
        keys = new ArrayList<Foo>();
        for (int i = 0; i < 1000; i++) {
            keys.add(new Foo(i));
        }
        
        for (Foo f : keys) {
            assertNull("Key null " + f, map.get(f));
        }
        
        
    }
    
}
