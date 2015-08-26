package model.border;

import model.Dock;
import model.Ship;
import ch.judos.generic.data.geometry.Angle;
import ch.judos.generic.data.geometry.DirectedPoint;
import ch.judos.generic.data.geometry.PointI;

/**
 * a border that is not applied on the map but used for drawing path for boats leaving a dock
 * 
 * @since 18.05.2015
 * @author Julian Schelker
 */
public class DockLeaveBorder extends RestrictionBorder {

	public static DockLeaveBorder createFromDock(Dock d) {
		DirectedPoint center = d.getPoint();
		DirectedPoint centerFront = center.move(50);
		//seen relative from inside dock
		DirectedPoint rightFront = centerFront.turnClockwise(Angle.A_90).move(50);
		DirectedPoint leftFront = centerFront.turnCounterClockwise(Angle.A_90).move(50);
		
		return new DockLeaveBorder(rightFront.getPoint(),leftFront.getPoint());
	}

	private DockLeaveBorder(PointI start,PointI end) {
		super(start,end);
	}
	
	@Override
	public boolean shipIsAllowedToCross(Ship ship) {
		return true;
	}

}
