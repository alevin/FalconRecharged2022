package frc.robot.commands.swervedrive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Drive.*;

//JMH
/**
 * A command that will turn the robot to the specified angle.
 */
public class TurnToAngle extends PIDCommand {
  SwerveDriveSubsystem swerveDriveSubsystem;
  /**
   * Turns to robot to the specified angle.
   *
   * @param targetAngleDegrees The angle to turn to
   * @param drive              The drive subsystem to use
   */
  public TurnToAngle(double targetAngleDegrees,  SwerveDriveSubsystem drive) {
    super(
        new PIDController(Constants.ANGLE_PID_P,Constants.ANGLE_PID_I, Constants.ANGLE_PID_D),  //need to tune this better.
        
        // Close loop on heading
        drive::getGyroAngle2,
        // Set reference to target
        targetAngleDegrees,  
        // Pipe output to turn robot
        output -> drive.holonomicDrive(0, 0, output),
        // Require the drive
        drive);
    swerveDriveSubsystem = drive;
    // Set the controller to be continuous (because it is an angle controller)
    getController().enableContinuousInput(-180, 180);
    
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController().setTolerance(Constants.TURN_TOLERANCE, 10);
  }

  
  public void execute() {
    // TODO Auto-generated method stub
    super.execute();
    System.out.println("angle: " + swerveDriveSubsystem.getGyroAngle2());
  }
  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atSetpoint();
  }


}