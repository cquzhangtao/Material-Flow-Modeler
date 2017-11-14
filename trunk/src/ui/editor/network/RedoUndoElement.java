package ui.editor.network;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RedoUndoElement implements Serializable{
	Method method;
	Object object;
	Object[]parameters;
	
	public RedoUndoElement(String methodStr,Object object,Object[] parameters){
	
		this.object=object;
		this.parameters=parameters;
		Class[]types=new Class[parameters.length];
		for(int i=0;i<types.length;i++){
			types[i]=parameters[i].getClass();
		}
		try {
			this.method=object.getClass().getDeclaredMethod(methodStr, types);
			this.method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doMethod(){
		if(method==null){
			return;
		}
		try {
			method.invoke(object, parameters);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
