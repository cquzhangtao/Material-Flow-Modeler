package ui.editor.util;

import java.util.List;

import basic.entity.IEntity;

public class EditorUtilities {
	public static String getListString(List list){
		String str="";
		for(Object obj:list){
			str+=((IEntity)obj).getName()+" ,";
		}
		if(!str.isEmpty()){
			str=str.substring(0,str.length()-2);
		}
		return str;
	}
}
