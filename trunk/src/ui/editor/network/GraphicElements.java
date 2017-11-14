package ui.editor.network;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class GraphicElements extends HashMap<String, DefaultGraphicElement> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RedoUndoPairs redoUndoPairs;
	private double scale = 1;
	private double scalestep = 0.1;

	public GraphicElements() {
		redoUndoPairs = new RedoUndoPairs();
	}

	public void redo() {
		redoUndoPairs.redo();
	}

	public void undo() {
		redoUndoPairs.undo();
	}

	public void add(DefaultGraphicElement element) {
		add(element, false);
	}

	public void add(DefaultGraphicElement element, Boolean undoredo) {
		if (this.containsValue(element)) {
			return;
		}

		String id = element.getUUID();
		int index = 0;
		while (this.containsKey(id) && index < 1000) {
			id = UUID.randomUUID().toString();
			index++;
		}
		if (this.containsKey(id)) {
			return;
		}
		element.setGUId(id);
		this.put(id, element);
		if (element.getGraphicType() == DefaultGraphicElement.LINK) {
			element.getPredecessors().get(0).addSuccessor(element);
		}
		if (!undoredo) {
			redoUndoPairs.add(this, "add", "remove", new Object[] { element,
					true }, new Object[] { element });
		}

	}

	public List<DefaultGraphicElement> find(Point2D point) {
		List<DefaultGraphicElement> elements = new ArrayList<DefaultGraphicElement>();
		for (DefaultGraphicElement ele : this.values()) {
			if (ele.getShape() != null && ele.getShape().contains(point)) {
				elements.add(ele);
			}
		}
		return elements;
	}

	public List<DefaultGraphicElement> find(Rectangle2D rect) {
		List<DefaultGraphicElement> elements = new ArrayList<DefaultGraphicElement>();
		for (DefaultGraphicElement ele : this.values()) {
			if (ele.getShape() != null
					&& rect.contains(ele.getShape().getBounds2D())) {
				elements.add(ele);
			}
		}
		return elements;
	}

	private Set<DefaultGraphicElement> remove(DefaultGraphicElement element) {
		Set<DefaultGraphicElement> others = new HashSet<DefaultGraphicElement>();
		super.remove(element.getUUID());

		if (element.getGraphicType() != DefaultGraphicElement.LINK) {
			for (int i = 0; i < element.getSuccessors().size(); i++) {
				others.add(element.getSuccessors().get(i));
				super.remove(element.getSuccessors().get(i).getUUID());
			}
			for (int i = 0; i < element.getPredecessors().size(); i++) {
				others.add(element.getPredecessors().get(i));
				super.remove(element.getPredecessors().get(i).getUUID());
			}

		} else {
			element.getPredecessors().get(0).removeSuccessor(element);
			element.getSuccessors().get(0).removePredecessor(element);
		}
		return others;
	}

	public void remove(HashSet<DefaultGraphicElement> elements) {
		remove(elements, false);
	}

	public void remove(HashSet<DefaultGraphicElement> elements, Boolean undoredo) {
		DefaultGraphicElement[] eles = elements.toArray(new DefaultGraphicElement[0]);
		Set<DefaultGraphicElement> removedElements = new HashSet<DefaultGraphicElement>();
		for (int i = 0; i < elements.size(); i++) {
			removedElements.add(eles[i]);
			removedElements.addAll(this.remove(eles[i]));
		}
		if (!undoredo) {
			redoUndoPairs.add(this, "remove", "add", new Object[] {
					new HashSet<DefaultGraphicElement>(elements), true },
					new Object[] { removedElements });
		}

	}

	public void add(HashSet<DefaultGraphicElement> elements) {
		for (DefaultGraphicElement ele : elements) {
			super.put(ele.getUUID(), ele);
			if (ele.getGraphicType() == DefaultGraphicElement.LINK) {
				ele.getPredecessors().get(0).addSuccessor(ele);
				ele.addSuccessor(ele.getSuccessors().get(0));

			} else {

			}
		}
	}

	public DefaultGraphicElement addLink(DefaultGraphicElement start, DefaultGraphicElement end) {
		DefaultGraphicElement element = new DefaultGraphicElement();
		element.setGraphicType(DefaultGraphicElement.LINK);
		start.addSuccessor(element);
		element.addSuccessor(end);
		this.add(element);
		return element;
	}
	
	public double zoomAll(Double width, Double height) {
		return zoomAll(width,height,false);
	}

	public double zoomAll(Double width, Double height, Boolean undoredo) {
		if (size() == 0) {
			return 1;
		}
		double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
		int margine = 20;
		for (DefaultGraphicElement ele : this.values()) {
			if (ele.getShape() != null && ele.getGraphicType() != DefaultGraphicElement.LINK) {

				if (ele.getShape().getBounds2D().getX() - margine < minX) {
					minX = ele.getShape().getBounds2D().getX() - margine;
				}

				if (ele.getShape().getBounds2D().getX()
						+ ele.getShape().getBounds2D().getWidth() + margine > maxX) {
					maxX = ele.getShape().getBounds2D().getX()
							+ ele.getShape().getBounds2D().getWidth() + margine;
				}

				if (ele.getShape().getBounds2D().getY() - margine < minY) {
					minY = ele.getShape().getBounds2D().getY() - margine;
				}

				if (ele.getShape().getBounds2D().getY()
						+ ele.getShape().getBounds2D().getHeight() + margine > maxY) {
					maxY = ele.getShape().getBounds2D().getY()
							+ ele.getShape().getBounds2D().getHeight()
							+ margine;
				}

			}
		}
		double sc = Math.min(width / (maxX - minX), height / (maxY - minY));
		AffineTransform transform = new AffineTransform();
		transform.translate(
				-minX * sc + (width - (maxX - minX) * sc) / 2, -minY
						* sc + (height - (maxY - minY) * sc) / 2);
		transform.scale(sc, sc);
		

		for (DefaultGraphicElement ele : this.values()) {
			if (ele.getShape() != null && ele.getGraphicType() != DefaultGraphicElement.LINK) {
				ele.transform(transform);
			}
		}
		
		try {
			AffineTransform tran = transform.createInverse();
			if (!undoredo) {
				redoUndoPairs.add(this, "zoomAll", "reverse", new Object[] {
						width,height, true },
						new Object[] { tran ,scale});
			}
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		scale=scale*sc;

		return sc;
	}
	
	public void reverse(AffineTransform tran,Double s){
		scale=s;
		for (DefaultGraphicElement ele : this.values()) {
			if (ele.getShape() != null && ele.getGraphicType() != DefaultGraphicElement.LINK) {
				ele.transform(tran);
			}
		}
	}

	public void scale(Boolean zoomIn, Point2D.Double center) {
		scale(zoomIn, center, false);
	}

	public void scale(Boolean zoomIn, Point2D.Double center, Boolean undoredo) {
		if (size() == 0) {
			return;
		}
		int flag=zoomIn?1:-1;
		
		scale = scale * (1 + flag*scalestep);
	
		
		for (DefaultGraphicElement ele : this.values()) {
			if (ele.getShape() != null && ele.getGraphicType() != DefaultGraphicElement.LINK) {
				ele.scale(1 + flag*scalestep, center);
			}
		}
		if (!undoredo) {
			redoUndoPairs.add(this, "scale",
					new Object[] { zoomIn, center, true }, new Object[] {
							!zoomIn, center, true });
		}
	}

	public void moveElements(HashSet<DefaultGraphicElement> elements,
			Point2D.Double endPoint, Point2D.Double startPoint) {
		moveElements(elements, endPoint, startPoint, false);

	}

	public void moveElements(HashSet<DefaultGraphicElement> elements,
			Point2D.Double endPoint, Point2D.Double startPoint, Boolean undoredo) {
		for (DefaultGraphicElement ele : elements) {
			if (ele.getGraphicType() != DefaultGraphicElement.LINK) {
				ele.move(endPoint, startPoint);
			}
		}
		if (!undoredo) {
			redoUndoPairs.add(this, "moveElements", new Object[] {
					new HashSet<DefaultGraphicElement>(elements), endPoint,
					startPoint, true }, new Object[] {
					new HashSet<DefaultGraphicElement>(elements), startPoint,
					endPoint, true });
		}
	}

	public void moveAllElements(Point2D.Double endPoint,
			Point2D.Double startPoint) {
		moveAllElements(endPoint, startPoint, false);
	}

	public void moveAllElements(Point2D.Double endPoint,
			Point2D.Double startPoint, Boolean undoredo) {
		for (DefaultGraphicElement ele : this.values()) {
			if (ele.getGraphicType() != DefaultGraphicElement.LINK) {
				ele.move(endPoint, startPoint);
			}
		}
		if (!undoredo) {
			redoUndoPairs.add(this, "moveAllElements", new Object[] { endPoint,
					startPoint, true }, new Object[] { startPoint, endPoint,
					true });
		}
	}

	public List<DefaultGraphicElement> intersection(List<DefaultGraphicElement> list1,
			Set<DefaultGraphicElement> list2) {
		List<DefaultGraphicElement> list = new ArrayList<DefaultGraphicElement>();

		for (DefaultGraphicElement t : list1) {
			if (list2.contains(t)) {
				list.add(t);
			}
		}

		return list;
	}

	public Set<DefaultGraphicElement> paste(HashSet<DefaultGraphicElement> copiedElement) {
		return paste(copiedElement, false);
	}

	public Set<DefaultGraphicElement> paste(HashSet<DefaultGraphicElement> copiedElement,
			Boolean undoredo) {
		Map<DefaultGraphicElement, DefaultGraphicElement> oldnew = new HashMap<DefaultGraphicElement, DefaultGraphicElement>();
		HashSet<DefaultGraphicElement> newr = new HashSet<DefaultGraphicElement>();
		for (DefaultGraphicElement ele : copiedElement) {
			if (ele.getGraphicType() != DefaultGraphicElement.LINK) {
				DefaultGraphicElement newE = new DefaultGraphicElement(ele);
				newE.removeAllPredecessors();
				newE.removeAllSuccessors();
				add(newE, undoredo);
				oldnew.put(ele, newE);
				newr.add(newE);

				if (newE.getShape() != null) {
					newE.move(50);
				}

			}
		}
		for (DefaultGraphicElement ele : copiedElement) {
			if (ele.getGraphicType() == DefaultGraphicElement.LINK) {
				DefaultGraphicElement news = oldnew.get(ele.getPredecessors().get(0));
				DefaultGraphicElement newe = oldnew.get(ele.getSuccessors().get(0));
				if (news != null && newe != null) {
					newr.add(addLink(news, newe));
				}
			}
		}
		if (!undoredo) {
			redoUndoPairs.add(this, "paste", "remove", new Object[] {
					new HashSet<DefaultGraphicElement>(copiedElement), true },
					new Object[] { new HashSet<DefaultGraphicElement>(newr), true });
		}
		return newr;

	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public void addLink(String start, String end) {
		// TODO Auto-generated method stub
		addLink(this.get(start),this.get(end));
	}

}
