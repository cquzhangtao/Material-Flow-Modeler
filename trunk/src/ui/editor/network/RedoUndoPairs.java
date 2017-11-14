package ui.editor.network;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class RedoUndoPairs extends ArrayList<RedoUndoPair> implements Serializable{
	private int index = 0;

	public void redo() {
		if (index < this.size()) {
			this.get(index).redo();
			index++;
		}
	}

	public void undo() {
		if (index > 0) {
			this.get(index-1).undo();
			index--;
		}
	}

	public void add(Object object, String method, Object[] redoParas,
			Object[] undoParas) {

		add(object, method, method, redoParas, undoParas);

	}

	public void add(Object object, String redoMethod, String undoMethod,
			Object[] redoParas, Object[] undoParas) {
		RedoUndoPair pair = new RedoUndoPair();
		pair.setReDo(new RedoUndoElement(redoMethod, object, redoParas));
		pair.setUnDo(new RedoUndoElement(undoMethod, object, undoParas));
		
		this.add(index,pair);
		index++;
	}

}
