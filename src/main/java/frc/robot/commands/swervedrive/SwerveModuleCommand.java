/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.SwerveDriveModule;

public class SwerveModuleCommand extends CommandBase {
  private SwerveDriveModule mDriveModule;

	public SwerveModuleCommand(final SwerveDriveModule driveModule) {
		this.mDriveModule = driveModule;

		addRequirements(driveModule);
	}

	@Override
	public void execute() {
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
