package org.jboss.el;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
//import ognl.Ognl;
import org.jboss.el.beans.Employee;

import org.jboss.el.beans.Example;

import junit.framework.TestCase;

public class TestEvaluation extends TestCase {
    
    private ExpressionFactory factory = null;
    private ExpressionFactory sun = null;
    private ELContextImpl context = null;
    
    public void testListeners() throws Exception {
        for (int i = 0; i < 5; i++) {
            evalMethod("#{company.departments.{x|x.employees.{x|x.sayHello}}}", new Class[] { String.class},  "Holden");
        }
    }
    
    public void testPerformance() throws Exception {
        ValueExpression ve = this.factory.createValueExpression(this.context, "#{company.departments.{x|x.employees.{x|x.lastName}}}", Object.class);
        //Object ognl = Ognl.parseExpression("company.departments.{employees.{lastName}}");
        
        Map ctx = new HashMap();
        ctx.put("company", Example.createCompany());
        
        int runs = 10000;
        Object value = ve.getValue(this.context);
        long now = System.currentTimeMillis();
        for (int i = 0; i < runs; i++) {
            value = ve.getValue(this.context);
        }
        System.out.println("New [" + ((System.currentTimeMillis() - now)/((double) runs)) + "] " + value);
        
        //value = Ognl.getValue(ognl, ctx);
        //now = System.currentTimeMillis();
        //for (int i = 0; i < runs; i++) {
        //    value = Ognl.getValue(ognl, ctx);
        //}
        System.out.println("Ognl [" + ((System.currentTimeMillis() - now)/((double) runs)) + "] " + value);
    }
    
    public void testSetters() throws Exception {
        for (int i = 0; i < 5; i++) {
            //evalSetter("#{company.departments.{x|x.employees}.{x|x.lastName}}", "Hookom");
            evalSetter("#{company.departments.{x|x.employees.{x|x.lastName}}}", "Holden");
        }
    }
    
    public void testEvaluation() throws Exception {
        for (int i = 0; i < 5; i++) {
            eval("#{company.departments}");
            eval("#{company.getDepartments()}");
            eval("#{company.departments.{x|x.employees}}");
            eval("#{company.departments.{x|x.employees.{x|x}}}");
            eval("#{company.departments.{x|x.director}.{x|x.firstName}}");
            eval("#{company.departments.{x|x.employees.{x|x.lastName}}}");
            eval("#{company.departments.{x|x.employees.{x|x.sayHello(name)}}}");
        }
    }
    
    public void testMethodExpressions() throws Exception {
        for (int i = 0; i < 5; i++) {
            evalMethod("#{company.departments[0].employees[0].sayHello(name)}");
            evalMethod("#{company.departments[0].employees[1].sayHello(name)}");
            evalMethod("#{company.departments[1].employees[0].sayHello(name)}");
            evalMethod("#{company.departments[1].employees[1].sayHello(5)}");
        }
    }
        
    public void evalSetter(String expr, Object value) {
        int runs = 10000;
        
        ValueExpression ve = this.factory.createValueExpression(this.context, expr, String.class);
        ve.getValue(this.context);
        long now = System.currentTimeMillis();
        for (int i = 0; i < runs; i++) {
            ve.setValue(this.context, value);
        }
        System.out.println("New [" + ((System.currentTimeMillis() - now)/((double) runs)) + "] " +  expr + " " + ve.getValue(this.context));
    }
    
    public void evalMethod(String expr) throws Exception {
        this.evalMethod(expr, new Class[0]);
    }

    public void evalMethod(String expr, Class[] types, Object... args) throws Exception {
        int runs = 10000;
        
        MethodExpression me = this.factory.createMethodExpression(this.context, expr, String.class, types);
        Object out = me.invoke(this.context, args);
        long now = System.currentTimeMillis();
        for (int i = 0; i < runs; i++) {
            out = me.invoke(this.context, args);
        }
        System.out.println("New [" + ((System.currentTimeMillis() - now)/((double) runs)) + "] " +  expr + " " + out);
    }
    
    
    public void eval(String expr) throws Exception {
        int runs = 10000;
        
        ValueExpression ve = this.factory.createValueExpression(this.context, expr, Object.class);
        Object value = ve.getValue(this.context);
        long now = System.currentTimeMillis();
        for (int i = 0; i < runs; i++) {
            value = ve.getValue(this.context);
        }
        System.out.println("New [" + ((System.currentTimeMillis() - now)/((double) runs)) + "] " +  expr + " " + value);
    }
    
    public void setUp() throws Exception {
        super.setUp();
        this.factory = new ExpressionFactoryImpl();
        //this.sun = new com.sun.el.ExpressionFactoryImpl();
        this.context = new ELContextImpl();
        this.context.setVar("company", Example.createCompany());
        this.context.setVar("name", "Jacob");
        System.out.println("\n===============================================\n");
    }
    
}
