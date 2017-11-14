package ui.editor.network;

import java.io.Serializable;

public class RedoUndoPair implements Serializable{
	RedoUndoElement reDo;
	RedoUndoElement unDo;
	public RedoUndoElement getReDo() {
		return reDo;
	}
	public RedoUndoElement getUnDo() {
		return unDo;
	}
	public void setReDo(RedoUndoElement reDo) {
		this.reDo = reDo;
	}
	public void setUnDo(RedoUndoElement unDo) {
		this.unDo = unDo;
	}
	
	public void redo(){
		reDo.doMethod();
	}
	
	public void undo(){
		unDo.doMethod();
	}
	
	

}
