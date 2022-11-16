/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;

public class ZeroNavX extends InstantCommand {

  private SwerveDriveSubsystem swerveDriveSubsystem;
  public ZeroNavX(SwerveDriveSubsystem swerveDriveSubsystem) {
    addRequirements(swerveDriveSubsystem);
    this.swerveDriveSubsystem = swerveDriveSubsystem;
  }

  @Override
  public void initialize() {
    swerveDriveSubsystem.zeroGyro();
    swerveDriveSubsystem.resetAllEncoders();
  }

}
