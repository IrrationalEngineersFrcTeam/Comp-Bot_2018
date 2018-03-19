                      package org.usfirst.frc.team6239.robot.swervedrive;

import org.usfirst.frc.team6239.robot.Robot;

/**
 * Created by Eric Engelhart on 3/17/2017.
 */
public class SwerveDrive {

	public final double L = 23.5;
	public final double W = 25;
	
	public double orientationOffset;
	public static boolean isFieldCentric = true;
	double angleToDiagonal;
	
	WheelDrive frontRight, frontLeft, backLeft, backRight;
	WheelDrive[] wheelArray1, wheelArray2;
	public SwerveDrive(WheelDrive frontRight, WheelDrive frontLeft, WheelDrive backLeft, WheelDrive backRight) {
		this.frontRight = frontRight;
		this.frontLeft = frontLeft;
		this.backLeft = backLeft;
		this.backRight = backRight;
		this.angleToDiagonal = Math.toDegrees(Math.atan2(L, W));
		this.frontRight.setRAngle(-(90 - angleToDiagonal));
		this.frontLeft.setRAngle(-(angleToDiagonal + 90));
		this.backRight.setRAngle(-(angleToDiagonal + 270));
		this.backLeft.setRAngle(-(270 - angleToDiagonal));


		this.wheelArray1 = new WheelDrive[] { this.frontLeft, this.backRight };
		this.wheelArray2 = new WheelDrive[] { this.backLeft, this.frontRight };








	}
	
	public void drive (double y1, double x1, double x2, double angle) {


		
	double translationalXComponent = -x1;
		double translationalYComponent = y1;
		double translationalMagnitude;
		double translationalAngle;

		double rAxis = x2;
		double rotateXComponent;
		double rotateYComponent;
		double fastestSpeed = 0;

		// Deadband
		if (Math.abs(x1) < 0.1) {
			translationalXComponent = 0;
			x1 = 0;
		}

		if (Math.abs(y1) < 0.1) {
			translationalYComponent = 0;
			y1 = 0;
		}

		if (Math.abs(x2) < 0.1) {
			rAxis = 0;
			x2 = 0;
		}
		//System.out.println(this.isFieldCentric);
		if (this.isFieldCentric == true) {
			
			orientationOffset = Robot.navX.getYaw() ;
			if (orientationOffset < 0) {
				double x = orientationOffset+ 360 ;
				orientationOffset = x;
			}
			if(orientationOffset >= 360){ orientationOffset = 0;}
			
		} if (this.isFieldCentric == false) {
			orientationOffset = 0;
			//System.out.println("or:  "+orientationOffset);
		}
		

		translationalYComponent *= -1;
		

	orientationOffset = orientationOffset % 360;

		double rotationMagnitude = Math.abs(rAxis);

		translationalMagnitude = Math.sqrt(Math.pow(translationalYComponent, 2) + Math.pow(translationalXComponent, 2));
		translationalAngle = Math.toDegrees(Math.atan2(translationalYComponent, translationalXComponent));

		translationalAngle -= orientationOffset; 

	translationalAngle = translationalAngle % 360;
		if (translationalAngle < 0) {
			translationalAngle += 360;
		}

		translationalYComponent = Math.sin(Math.toRadians(translationalAngle)) * translationalMagnitude; 

		translationalXComponent = Math.cos(Math.toRadians(translationalAngle)) * translationalMagnitude; 


		for (WheelDrive wheel : wheelArray1) {

			rotateXComponent = Math.cos(Math.toRadians(wheel.getRAngle())) * rotationMagnitude; 

			rotateYComponent = Math.sin(Math.toRadians(wheel.getRAngle())) * rotationMagnitude; 


			if (rAxis > 0) {
				rotateXComponent = -rotateXComponent;
				rotateYComponent = -rotateYComponent;
			}

			wheel.setSpeed(Math.sqrt(Math.pow(rotateXComponent + translationalXComponent, 2)
					+ Math.pow((rotateYComponent + translationalYComponent), 2)));

			wheel.setAngle((Math.toDegrees(Math.atan2((-rotateYComponent + translationalYComponent),
					(rotateXComponent + translationalXComponent)))));

			if (wheel.getSpeed() > fastestSpeed) {

				fastestSpeed = wheel.getSpeed();
			}
		}
		for (WheelDrive wheel : wheelArray2) {

			rotateXComponent = Math.cos(Math.toRadians(wheel.getRAngle())) * rotationMagnitude; 
			rotateYComponent = Math.sin(Math.toRadians(wheel.getRAngle())) * rotationMagnitude; 


			if (rAxis > 0) {
				rotateXComponent = -rotateXComponent;
				rotateYComponent = -rotateYComponent;
			}

			wheel.setSpeed(Math.sqrt(Math.pow(rotateXComponent + translationalXComponent, 2)
					+ Math.pow((rotateYComponent + translationalYComponent), 2)));

			wheel.setAngle((Math.toDegrees(Math.atan2((rotateYComponent + translationalYComponent),
					(-rotateXComponent + translationalXComponent)))));


			if (wheel.getSpeed() > fastestSpeed) {
				fastestSpeed = wheel.getSpeed();
			}

		}

		if (fastestSpeed > 1) { 
			for (WheelDrive wheel : wheelArray1) {
				wheel.setSpeed(wheel.getSpeed() / fastestSpeed);
			}
			for (WheelDrive wheel : wheelArray2) {
				wheel.setSpeed(wheel.getSpeed() / fastestSpeed);
			}
		}


	    
	}
	
	public void setFieldCentric(){
		this.isFieldCentric = true;
	}
	public void setRobotCentric(){
		this.isFieldCentric = false;
		
	}
	public boolean getFieldCentric(){
		return this.isFieldCentric;
	}
}
