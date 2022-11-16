/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;

public class ToggleFieldOrientedCommand extends InstantCommand {
  SwerveDriveSubsystem swerveDriveSubsystem;
  /**
   * Creates a new ToggleFieldOriented.
   */
  public ToggleFieldOrientedCommand(SwerveDriveSubsystem swerveDriveSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(swerveDriveSubsystem);
    this.swerveDriveSubsystem = swerveDriveSubsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    swerveDriveSubsystem.setFieldOriented(!swerveDriveSubsystem.isFieldOriented());
  }

}
