package ui.editor.network;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import basic.entity.GraphicNetworkTreeNodeEntity;


import ui.editor.listener.GraphicElementMouseListener;
import ui.utilities.ImageUtilities;

public class GraphicalPanel extends JPanel implements KeyListener,
		MouseListener, MouseMotionListener, MouseWheelListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private GraphicElements graphicalElements;
	private Action action = Action.SELECT;
	private boolean startDrawingLink = false;
	private boolean startDrawingPath = false;
	private DefaultGraphicElement startElement;
	private Rectangle2D tempEndShape;
	private HashSet<DefaultGraphicElement> selectedElements;
	private Point2D selectedPoint;
	private PathLine pathLine;
	private Point2D tempPoint;
	private Rectangle2D choosingRectangle;
	private boolean showGrid = true;
	private int gridSize = 20;
	private boolean fillPath = true;
	private Color pathColor = Color.lightGray;
	private int pathWidth = 5;
	private Color backGround = Color.white;
	private GraphicPanelParameters parameters;
	private GraphicPanelParameter currentParameter;
	private boolean showLabel = true;

	private HashSet<DefaultGraphicElement> copiedElement;
	private Point2D prePoint;
	private List<GraphicElementMouseListener> elementMouseListeners;

	public GraphicalPanel(GraphicElements graphicalElements,
			GraphicPanelParameters parameters) {
		super();
		this.parameters = parameters;
		this.graphicalElements = graphicalElements;
		elementMouseListeners=new ArrayList<GraphicElementMouseListener> ();
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.setOpaque(false);
		this.setFocusable(true);
		selectedElements = new HashSet<DefaultGraphicElement>();
	}
	public void addGraphicElementMouseListener(GraphicElementMouseListener l){
		elementMouseListeners.add(l);
	}

	private void drawElement(DefaultGraphicElement element, Graphics2D g2) {
		if (element.getGraphicType() != DefaultGraphicElement.LINK) {
			if (element.hasImage()) {
				Rectangle2D rect = element.getShape().getBounds2D();
				Image bimg = null;// = element.getImage();
				URL url = this.getClass().getResource(element.getImage());
				try {
					bimg=ImageIO.read(url);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				g2.drawImage(bimg, (int) rect.getX(), (int) rect.getY(),
						(int) rect.getWidth(), (int) rect.getHeight(), null);
				if (selectedElements.contains(element)) {
					g2.setStroke(new BasicStroke(1));

					g2.draw(element.getShape());
				}
			} else {
				if (selectedElements.contains(element)) {
					g2.setStroke(new BasicStroke(2));
					g2.draw(element.getShape());
				} else {
					g2.setStroke(new BasicStroke(0.5f));
				}
				if (fillPath) {
					g2.setColor(pathColor);
					g2.fill(element.getShape());
					g2.setColor(Color.black);
				} else {
					g2.draw(element.getShape());
				}

			}
			if (element.getGraphicType() == DefaultGraphicElement.NODE && showLabel) {
				Font font = g2.getFont();
				Rectangle2D rect = font.getStringBounds(element.getName(),
						g2.getFontRenderContext());
				g2.drawString(element.getName(), (int) (element.getShape()
						.getBounds2D().getX() + (element.getShape()
						.getBounds2D().getWidth() - rect.getWidth()) / 2),
						(int) element.getShape().getBounds2D().getY()
								+ (int) element.getShape().getBounds2D()
										.getHeight() + (int) rect.getHeight());
			}
		} else {
			if (selectedElements.contains(element)) {
				g2.setStroke(new BasicStroke(2));
			} else {
				g2.setStroke(new BasicStroke(0.5f));
			}
			Line2D line = getLine(element.getPredecessors().get(0).getShape(),
					element.getSuccessors().get(0).getShape());
			g2.draw(line);
			g2.draw(createArrowShape(line));
			BasicStroke stroke = new BasicStroke(5);
			element.setShape(stroke.createStrokedShape(line));
		}
		g2.dispose();
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY));

		drawBackground((Graphics2D) g2.create());
		if (showGrid) {
			drawGrid((Graphics2D) g2.create());
		}
		for (DefaultGraphicElement element : graphicalElements.values()) {
			drawElement(element, (Graphics2D) g2.create());
		}

		g2.setStroke(new BasicStroke(1));
		if (startDrawingLink && startElement != null && tempEndShape != null) {
			Line2D line = getLine(startElement.getShape(), tempEndShape);
			g2.draw(line);
			g2.draw(createArrowShape(line));
		} else if (startDrawingPath && pathLine != null) {

			if (fillPath) {
				g2.setColor(pathColor);
				g2.fill(pathLine);
				g2.setColor(Color.black);
			} else {
				g2.draw(pathLine);
			}
			if (tempPoint != null) {
				PathLine line = new PathLine(pathWidth * graphicalElements.getScale());
				line.moveTo(pathLine.getEndPoint());
				line.lineTo(tempPoint);

				if (fillPath) {
					g2.setColor(pathColor);
					g2.fill(line);
					g2.setColor(Color.black);
				} else {
					g2.draw(line);
				}
			}
		} else if (choosingRectangle != null) {
			Stroke dashed = new BasicStroke(0.3f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
			g2.setStroke(dashed);
			g2.draw(choosingRectangle);
		} else {

		}
	}

	private void drawGrid(Graphics2D g2) {

		g2.setColor(new Color(240, 240, 240));
		for (int i = 0; i < this.getWidth(); i = i + gridSize) {
			for (int j = 0; j < this.getHeight(); j = j + gridSize) {
				g2.drawLine(i, 0, i, this.getHeight());
				g2.drawLine(0, j, this.getWidth(), j);
			}
		}
		g2.setColor(Color.black);
		Rectangle2D rect = new Rectangle2D.Double(
				this.getBounds().getWidth() / 2 - 1.5, this.getBounds()
						.getHeight() / 2 - 1.5, 3, 3);
		g2.fill(rect);
		g2.dispose();
	}

	private void drawBackground(Graphics2D g2) {
		g2.setColor(backGround);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.dispose();
	}

	private Line2D getLine(Shape startShape, Shape endShape) {
		Point2D startP = new Point2D.Double();
		Point2D endP = new Point2D.Double();
		if (startShape instanceof PathLine) {
			PathLine path = (PathLine) startShape;
			startP.setLocation(path.getEndPoint());

		} else {
			Rectangle2D rect = startShape.getBounds2D();
			startP.setLocation(rect.getX() + rect.getWidth(), rect.getY()
					+ rect.getHeight() / 2);
		}

		if (endShape instanceof PathLine) {
			PathLine path = (PathLine) endShape;
			endP.setLocation(path.getStartPoint());

		} else {
			Rectangle2D rect = endShape.getBounds2D();
			endP.setLocation(rect.getX(), rect.getY() + rect.getHeight() / 2);
		}

		Line2D line = new Line2D.Double(startP, endP);
		return line;

	}

	private Point2D getPoint(Shape shape, boolean inOrOut) {
		Point2D startP = new Point2D.Double();
		if (!inOrOut) {
			if (shape instanceof PathLine) {
				PathLine path = (PathLine) shape;
				startP.setLocation(path.getEndPoint());

			} else {
				Rectangle2D rect = shape.getBounds2D();
				startP.setLocation(rect.getX() + rect.getWidth(), rect.getY()
						+ rect.getHeight() / 2);
			}
		} else {
			if (shape instanceof PathLine) {
				PathLine path = (PathLine) shape;
				startP.setLocation(path.getStartPoint());

			} else {
				Rectangle2D rect = shape.getBounds2D();
				startP.setLocation(rect.getX(), rect.getY() + rect.getHeight()
						/ 2);
			}
		}

		return startP;

	}

	public Shape createArrowShape(Line2D line) {
		Path2D arrowPolygon = new Path2D.Double();
		arrowPolygon.moveTo(5, -5);
		arrowPolygon.lineTo(0, 0);
		arrowPolygon.lineTo(5, 5);
		Point2D midPoint = midpoint(line.getP1(), line.getP2());
		double rotate = Math.atan2(line.getP1().getY() - line.getP2().getY(),
				line.getP1().getX() - line.getP2().getX());
		AffineTransform transform = new AffineTransform();
		transform.translate(midPoint.getX(), midPoint.getY());
		transform.rotate(rotate);
		return transform.createTransformedShape(arrowPolygon);
	}

	private Point2D midpoint(Point2D p1, Point2D p2) {
		return new Point2D.Double((int) ((p1.getX() + p2.getX()) / 2.0),
				(int) ((p1.getY() + p2.getY()) / 2.0));
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (action == Action.SELECT) {
			requestFocus();
			if (selectedElements.size() > 0 && choosingRectangle == null) {
				this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				graphicalElements.moveElements(selectedElements, new Point2D.Double(e.getPoint().getX(),e.getPoint().getY()),
						new Point2D.Double(selectedPoint.getX(),selectedPoint.getY()));
				selectedPoint = e.getPoint();
				this.repaint();
			} else if (this.choosingRectangle != null) {
				this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				choosingRectangle.setRect(choosingRectangle.getX(),
						choosingRectangle.getY(), e.getPoint().getX()
								- choosingRectangle.getX(), e.getPoint().getY()
								- choosingRectangle.getY());
				this.repaint();
			}
		} else if (action == Action.PAN) {
			graphicalElements.moveAllElements(new Point2D.Double(e.getPoint().getX(),e.getPoint().getY()),
					new Point2D.Double(selectedPoint.getX(),selectedPoint.getY()));
			selectedPoint = e.getPoint();
			this.repaint();

		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (action == Action.ADD_LINE) {
			List<DefaultGraphicElement> eles = graphicalElements.find(e.getPoint());
			if (startDrawingLink) {

				tempEndShape = new Rectangle2D.Double();
				tempEndShape.setRect(e.getPoint().getX() - 1, e.getPoint()
						.getY() - 1, 2, 2);

				Point2D p1 = getPoint(startElement.getShape(), false);
				this.repaint(offsetRectangle(
						createRectFromTwoPoints(p1, e.getPoint()), 5)
						.getBounds());
				this.repaint(offsetRectangle(
						createRectFromTwoPoints(p1, prePoint), 5).getBounds());
				// this.repaint();
				prePoint = e.getPoint();
			}
			if (eles.size() > 0 && eles.get(0).getGraphicType() != DefaultGraphicElement.LINK) {
				this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			} else {
				this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
		if (action == Action.ADD_PATH) {
			if (startDrawingPath) {
				tempPoint = e.getPoint();

				this.repaint(offsetRectangle(
						createRectFromTwoPoints(prePoint,
								pathLine.getEndPoint()), 5).getBounds());
				this.repaint(offsetRectangle(
						createRectFromTwoPoints(tempPoint,
								pathLine.getEndPoint()), 5).getBounds());
				// this.repaint();
				prePoint = e.getPoint();
			}
		}

	}

	private Rectangle2D createRectFromTwoPoints(Point2D point1, Point2D point2) {
		double x, y, w, h;
		w = point1.getX() - point2.getX();
		h = point1.getY() - point2.getY();
		x = w < 0 ? point1.getX() : point2.getX();

		y = h < 0 ? point1.getY() : point2.getY();

		return new Rectangle2D.Double(x, y, Math.abs(w), Math.abs(h));
	}

	private Rectangle2D offsetRectangle(Rectangle2D rect, double offset) {
		Rectangle2D rectNew = new Rectangle2D.Double();
		rectNew.setRect(rect.getX() - offset, rect.getY() - offset,
				rect.getWidth() + 2 * offset, rect.getHeight() + 2 * offset);
		return rectNew;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		List<DefaultGraphicElement> eles = graphicalElements.find(e.getPoint());
		requestFocus();
		if(e.getClickCount()==2&&eles.size()>0){
			for(GraphicElementMouseListener l:elementMouseListeners){
				l.mouseClicked(eles.get(0).getGraphicNetworkTreeNodeEntity());
			}
			return;
		}
		prePoint = e.getPoint();
		if (action == Action.ADD_NODE) {
			if (eles.size() == 0) {
				DefaultGraphicElement element = new DefaultGraphicElement();
				element.setGraphicType(DefaultGraphicElement.NODE);
				Rectangle2D rect = new Rectangle2D.Double();
				rect.setRect(e.getPoint().getX() - currentParameter.getWidth()
						* graphicalElements.getScale() / 2,
						e.getPoint().getY() - currentParameter.getHeight()
								* graphicalElements.getScale() / 2, currentParameter.getWidth()
								* graphicalElements.getScale(), currentParameter.getHeight() * graphicalElements.getScale());
				element.setShape(rect);
				element.setName(currentParameter.getName()+currentParameter.getIndex());
//				URL url = this.getClass().getResource(currentParameter.getImage());
//				try {
//					element.setImage(ImageIO.read(url));
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				element.setImage(currentParameter.getImage());
				graphicalElements.add(element);
				createRelatedObject(element);
			
				
				this.repaint(offsetRectangle(element.getShape().getBounds2D(),
						20).getBounds());
			}
		} else if (action == Action.ADD_LINE) {
			if (eles.size() != 0
					&& eles.get(0).getGraphicType() != DefaultGraphicElement.LINK) {
				if (!startDrawingLink) {
					startElement = eles.get(0);
					startDrawingLink = true;
				} else {

					graphicalElements.addLink(startElement, eles.get(0));

					startDrawingLink = false;
					this.repaint();
				}
			} else {
				if (startDrawingLink) {
					startDrawingLink = false;
					this.repaint();
				}
			}

		} else if (action == Action.ADD_PATH) {
			if (eles.size() != 0) {
				if (!startDrawingPath) {
					startElement = eles.get(0);
					startDrawingPath = true;
					pathLine = new PathLine(pathWidth * graphicalElements.getScale());

					pathLine.moveTo(getPoint(startElement.getShape(), false));
					// requestFocus();
				} else {
					startDrawingPath = false;
					pathLine.lineTo(getPoint(eles.get(0).getShape(), true));
					DefaultGraphicElement element = new DefaultGraphicElement();
					element.setGraphicType(DefaultGraphicElement.PATH);
					element.setShape(pathLine);
					element.setName(currentParameter.getName()+currentParameter.getIndex());
					graphicalElements.add(element);

					if (startElement != null) {
						graphicalElements.addLink(startElement, element);
					}
					graphicalElements.addLink(element, eles.get(0));
					createRelatedObject(element);
					this.repaint();

				}
			} else {

				if (!startDrawingPath) {
					startElement = null;
					startDrawingPath = true;
					pathLine = new PathLine(pathWidth * graphicalElements.getScale());
					pathLine.moveTo(e.getPoint());
				} else {
					pathLine.lineTo(e.getPoint());
					this.repaint();
				}
			}
		}
		if (action == Action.SELECT) {

			if (eles.size() > 0) {

				if (e.isControlDown()) {
					if (selectedElements.contains(eles.get(0))) {
						selectedElements.remove(eles.get(0));
					} else {
						selectedElements.add(eles.get(0));
					}
				} else {
					selectedElements.clear();
					selectedElements.add(eles.get(0));
				}
				this.repaint();
			} else {
				selectedElements.clear();
				selectedPoint = null;
				this.repaint();
			}

		}

	}

	private void createRelatedObject(DefaultGraphicElement element) {
		try {
			GraphicNetworkTreeNodeEntity ele = (GraphicNetworkTreeNodeEntity) currentParameter.getObjectClass().getConstructor().newInstance();
			ele.setName(element.getName());
			element.setGraphicNetworkTreeNodeEntity(ele);
			fireElementChanged(ele);
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (action == Action.SELECT) {
			copiedElement = null;
			List<DefaultGraphicElement> eles = graphicalElements.find(e.getPoint());
			if (eles.size() > 0) {

				if (!selectedElements.contains(eles.get(0))
						&& !e.isControlDown()) {
					selectedElements.clear();
					selectedElements.add(eles.get(0));
				}

				this.repaint();
			} else {
				choosingRectangle = new Rectangle2D.Double();
				choosingRectangle.setRect(e.getPoint().getX(), e.getPoint()
						.getY(), 0, 0);
			}
			selectedPoint = new Point2D.Double(e.getPoint().getX(), e
					.getPoint().getY());

		} else if (action == Action.PAN) {
			selectedPoint = new Point2D.Double(e.getPoint().getX(), e
					.getPoint().getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (action == Action.SELECT) {

			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			if (this.choosingRectangle != null) {
				List<DefaultGraphicElement> eles = graphicalElements
						.find(choosingRectangle);
				selectedElements.addAll(eles);
				choosingRectangle = null;
				requestFocus();
				repaint();
			}

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (action == Action.ADD_PATH) {
			if (startDrawingPath && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				onPathEnd();
			}
		} else if (action == Action.SELECT) {
			if (e.getKeyCode() == KeyEvent.VK_DELETE) {
				if (selectedElements.size() > 0) {
					graphicalElements.remove(selectedElements);
					selectedElements.clear();
					this.repaint();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()) {
				copiedElement = new HashSet<DefaultGraphicElement>(selectedElements);
			} else if (e.getKeyCode() == KeyEvent.VK_V && e.isControlDown()) {
				if (copiedElement != null && copiedElement.size() > 0) {
					selectedElements.clear();
					selectedElements.addAll(graphicalElements
							.paste(copiedElement));
					copiedElement = new HashSet<DefaultGraphicElement>(
							selectedElements);
					this.repaint();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_A && e.isControlDown()) {
				for (DefaultGraphicElement ge : graphicalElements.values()) {
					selectedElements.add(ge);
				}
				this.repaint();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Action old = action;
		if (parameters.containsKey(e.getActionCommand())) {
			this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			action = parameters.get(e.getActionCommand()).getAction();
			currentParameter = parameters.get(e.getActionCommand());
		}

		else if (Action.valueOf(e.getActionCommand()) == Action.DELETE) {
			if (selectedElements.size() > 0) {
				graphicalElements.remove(selectedElements);
				selectedElements.clear();
				this.repaint();
			}
		}else if (Action.valueOf(e.getActionCommand()) == Action.UNDO) {
			graphicalElements.undo();
			this.repaint();
		} else if (Action.valueOf(e.getActionCommand()) == Action.REDO) {
			graphicalElements.redo();
			this.repaint();
		} 
		else if (Action.valueOf(e.getActionCommand()) == Action.COPY) {
			copiedElement = new HashSet<DefaultGraphicElement>(selectedElements);
		} else if (Action.valueOf(e.getActionCommand()) == Action.PASTE) {
			if (copiedElement != null && copiedElement.size() > 0) {
				selectedElements.clear();
				selectedElements.addAll(graphicalElements.paste(copiedElement));
				copiedElement = new HashSet<DefaultGraphicElement>(selectedElements);
				this.repaint();
			}
		}

		else if (Action.valueOf(e.getActionCommand()) == Action.LABEL_SWITCH) {
			showLabel = !showLabel;
			this.repaint();
		} else if (Action.valueOf(e.getActionCommand()) == Action.GRID_SWITCH) {
			showGrid = !showGrid;
			this.repaint();
		} else if (Action.valueOf(e.getActionCommand()) == Action.ZOOM_IN) {
			
			Point2D.Double center = new Point2D.Double(
					this.getBounds().getWidth() / 2, this.getBounds()
							.getHeight() / 2);

			graphicalElements.scale(true, center);
			this.repaint();
		} else if (Action.valueOf(e.getActionCommand()) == Action.ZOOM_OUT) {
			Point2D.Double center = new Point2D.Double(
					this.getBounds().getWidth() / 2, this.getBounds()
							.getHeight() / 2);
			
			graphicalElements.scale(false, center);
			this.repaint();
		} else if (Action.valueOf(e.getActionCommand()) == Action.ZOOM_ALL) {

			 graphicalElements.zoomAll(Double.valueOf(this.getWidth()),
					 Double.valueOf(this.getHeight()));
			this.repaint();
		} else if (Action.valueOf(e.getActionCommand()) == Action.PAN) {
			action = Action.valueOf(e.getActionCommand());

			this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		} else if (Action.valueOf(e.getActionCommand()) == Action.SELECT) {
			action = Action.valueOf(e.getActionCommand());
			this.requestFocus();
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else if (Action.valueOf(e.getActionCommand()) == Action.ADD_LINE) {
			action = Action.valueOf(e.getActionCommand());
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

		if (old == Action.SELECT && action != Action.SELECT) {
			selectedElements.clear();
			this.repaint();
		}

		if (old == Action.ADD_PATH && old != action && startDrawingPath) {
			onPathEnd();
		}

	}

	public void onPathEnd() {
		startDrawingPath = false;
		DefaultGraphicElement element = new DefaultGraphicElement();
		element.setGraphicType(DefaultGraphicElement.PATH);
		element.setShape(pathLine);
		element.setName(currentParameter.getName()+currentParameter.getIndex());
		graphicalElements.add(element);
		if (startElement != null) {
			graphicalElements.addLink(startElement, element);
		}
		createRelatedObject(element);

		this.repaint();
	}
	private void fireElementChanged(Object object){
		for(GraphicElementMouseListener l:elementMouseListeners){
			l.elementChanged(object);
		}
	}

}
