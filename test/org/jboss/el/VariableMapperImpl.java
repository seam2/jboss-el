package org.jboss.el;

import java.util.HashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.el.VariableMapper;

public class VariableMapperImpl extends VariableMapper {
	
	private final Map vars = new HashMap();

	public ValueExpression resolveVariable(String variable) {
		return (ValueExpression) this.vars.get(variable);
	}

	public ValueExpression setVariable(String variable,
			ValueExpression expression) {
		return (ValueExpression) this.vars.put(variable, expression);
	}

}
