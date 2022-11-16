/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.LimelightPortal;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AlignToTargetLimelight extends SequentialCommandGroup {
  /**
   * Creates a new AlignToTargetLimelight.
   */
  public AlignToTargetLimelight(SwerveDriveSubsystem drive, LimelightPortal limeL) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new TurnToZeroLimelight(0, drive, limeL),
          new GoToDistanceLimelight(120, drive, limeL),
          new TurnToZeroLimelight(0, drive, limeL));
  }
}
