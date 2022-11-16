/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;

import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;
import frc.robot.utility.MathUtils;

public class HolonomicDriveCommand extends CommandBase {
  /**
   * Creates a new HolonomicDriveCommand.
   */
  private final SwerveDriveSubsystem mDrivetrain;
  private final XboxController mXboxController;

	public HolonomicDriveCommand(SwerveDriveSubsystem drivetrain, XboxController mXboxController) {
		mDrivetrain = drivetrain;
		addRequirements(drivetrain);
		this.mXboxController = mXboxController;
	}

	@Override
	public void execute() {
		if(mDrivetrain.getIsAuto())
		{
			mDrivetrain.setFieldOriented(false);
		}
		
		double forward = mXboxController.getLeftY(); //real: positive
		double rotation = mXboxController.getLeftTriggerAxis() 
			- mXboxController.getRightTriggerAxis(); //trigger values are between 0 and 1, left is -1 and right is +1
		double strafe = mXboxController.getLeftX(); //real: pos

		forward = MathUtils.deadband(forward, 0.175, mDrivetrain.isFieldOriented());
		strafe = MathUtils.deadband(strafe, 0.175, mDrivetrain.isFieldOriented());
		rotation = MathUtils.deadband(rotation, 0.1, mDrivetrain.isFieldOriented());

		
		mDrivetrain.swapPIDSlot(0);
		mDrivetrain.holonomicDrive(forward, -strafe, rotation);
	}

	@Override
	public void end(boolean interrupted) {
		mDrivetrain.stopDriveMotors();
	}
}
