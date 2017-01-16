package com.github.kreatures.swarm.components;

import java.util.Collection;

public class SwarmComponentDefaultFilter implements SwarmComponentFilter {

	public SwarmComponentDefaultFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T extends SwarmComponents> T filter(Collection<T> components, int id) {
		
		T t=null;
		
		for(T obj:components){
			if(obj.getIdentity()==id){
				t=obj;
				break;
			}
		}
		return t;
	}

}
