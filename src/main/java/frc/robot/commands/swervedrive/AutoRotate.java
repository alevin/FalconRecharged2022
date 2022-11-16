/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;

public class AutoRotate extends CommandBase {
  /**
   * Creates a new AutoRotate.
   */
  private double angle;
  private double initAngle;  // do these need to instance variables?
  private double currAngle;
  private double targetAngle;
  
  SwerveDriveSubsystem swerveDriveSubsystem;

  public AutoRotate(SwerveDriveSubsystem swerveDriveSubsystem, double angle) { //in degrees
    this.swerveDriveSubsystem = swerveDriveSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(swerveDriveSubsystem);
    this.angle = angle;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initAngle = swerveDriveSubsystem.getGyroAngle();
    targetAngle = initAngle + angle;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currAngle = swerveDriveSubsystem.getGyroAngle();
    swerveDriveSubsystem.holonomicDrive(0, 0, Math.signum(angle)*-.2); //forward, strafe, rotation, why -.2
    SmartDashboard.putNumber("init angle", initAngle);
    SmartDashboard.putNumber("curr angle", (currAngle));
    SmartDashboard.putNumber("target angle", (targetAngle));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // swerveDriveSubsystem.getSwerveModule(0).setTargetAngle(0);
    // swerveDriveSubsystem.getSwerveModule(1).setTargetAngle(0);
    // swerveDriveSubsystem.getSwerveModule(2).setTargetAngle(180);
    // swerveDriveSubsystem.getSwerveModule(3).setTargetAngle(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(currAngle - (targetAngle)) < 5;
  }
}
