/*
 * Security.java
 *
 * Created on December 9, 2006, 2:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jboss.el.beans;

import java.util.Arrays;

/**
 *
 * @author jhook
 */
public class Security {
    
    /** Creates a new instance of Security */
    public Security() {
    }
    
    public static final boolean hasRole(Object in, String... match) {
        System.out.println(Arrays.toString(match));
        return true;
    }
    
    public static final boolean hasOneRole(String s) {
    	System.out.println(s);
    	return true;
    }
    
    public boolean matchRole(String... match) {
        System.out.println(Arrays.toString(match));
        return true;
    }
}
