package org.usfirst.frc.team6239.robot.commands;

import org.usfirst.frc.team6239.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class liftcommand extends Command {

	//TODO PG27 Gearbox
	
	public liftcommand() {
		requires(Robot.liftsub);

	}

	protected void initialize() {
			
	}

	
	protected void execute() {
			Robot.liftsub.liftup();
	}


	protected void end() {
		Robot.liftsub.stop();
	}

	
	protected void interrupted() {
		end();
	}

	protected boolean isFinished() {
		
		return false;
	}

}
