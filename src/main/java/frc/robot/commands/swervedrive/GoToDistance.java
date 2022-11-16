package frc.robot.commands.swervedrive;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.subsystems.Drive.*;


/**
 * A command that will turn the robot to the specified angle.
 */
public class GoToDistance extends ProfiledPIDCommand {
    SwerveDriveSubsystem swerveDriveSubsystem;
    /**
     * Goes a specific distance.
     *
     * @param distance in inches 
     * @param drive              The drive subsystem to use
     */

    public GoToDistance(double distance, SwerveDriveSubsystem drive) {
    super(
        new ProfiledPIDController(0.04,0.0, 0.00237, //need to tune this better
            new TrapezoidProfile.Constraints(30,60)),  
        // Close loop on distance
        drive::getInches ,
        // Set reference to target
        distance,  
        // Pipe output to drive robot
        (output,setpoint) -> drive.holonomicDrive(output, 0, 0),
        // Require the drive
        drive);
    swerveDriveSubsystem = drive;
    // Set the controller to be continuous (because it is an angle controller)
    //getController().enableContinuousInput(-180, 180);
    //drive.resetAllEncoders();
    
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController().setTolerance(6, 10);
  }

  
  public void execute() {
    // TODO Auto-generated method stub
    super.execute();
    System.out.println("distance: " 
        + swerveDriveSubsystem.getInches());
  }
  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atGoal();
  }


}