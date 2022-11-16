/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;

public class Limelight extends SubsystemBase  {
  /**
   * Creates a new Limelight.
   */

  public static double intakeX;
  public static double intakeY;
  public static double intakeA;
  public static double shooterX;
  public static double shooterY;
  public static double shooterA;
  
  private SendableChooser<Integer> intakeMode;

  public NetworkTable intakeLL = NetworkTableInstance.getDefault().getTable("10.55.7.21"); // 10.55.7.21
  public NetworkTable shooterLL = NetworkTableInstance.getDefault().getTable("limelight-shooter"); // 10.55.7.11
  public NetworkTableEntry inX = intakeLL.getEntry("tx");
  public NetworkTableEntry inY = intakeLL.getEntry("ty");
  public NetworkTableEntry inA = intakeLL.getEntry("ta");
  public NetworkTableEntry shX = shooterLL.getEntry("tx");
  public NetworkTableEntry shY = shooterLL.getEntry("ty");
  public NetworkTableEntry shA = shooterLL.getEntry("ta");

  private double xErr;
  private double rErr;

  private double prevXErr;
  private double prevRErr;
  SwerveDriveSubsystem swerveDriveSubsystem;

  public Limelight(SwerveDriveSubsystem swerveDriveSubsystem) {
    intakeX = inX.getDouble(0.0);
    intakeY = inY.getDouble(0.0);
    intakeA = inA.getDouble(0.0);
    shooterA = shA.getDouble(0.0);
    shooterX = shX.getDouble(0.0);
    shooterY = shY.getDouble(0.0);
    intakeMode = new SendableChooser<Integer>();
    intakeMode.setDefaultOption("Vison Mode", 0);
    intakeMode.addOption("Cam Mode", 1);
    SmartDashboard.putData(intakeMode);
    xErr = Double.MIN_VALUE;
    rErr = Double.MIN_VALUE;
    prevXErr = Double.MIN_VALUE;
    prevRErr = Double.MIN_VALUE;
    this.swerveDriveSubsystem = swerveDriveSubsystem;
  }

  public void setIntakeCam(int x)
  {
    if(x == 1)
    {
      intakeLL.getEntry("ledMode").setDouble(1);
      intakeLL.getEntry("camMode").setDouble(x);
    }
    else
    {
      intakeLL.getEntry("ledMode").setDouble(3);
      intakeLL.getEntry("camMode").setDouble(x);
    }
  }

  public void resetErrs()
  {
    xErr = Double.MIN_VALUE;
    rErr = Double.MIN_VALUE;
    prevXErr = Double.MIN_VALUE;
    prevRErr = Double.MIN_VALUE;
  }

  public void align() // unfinished
  {
    updateErrors();
    double strafe = (xErr * .15);
    double rotation = (rErr * .05);
  // private double kP = 0.05; // .06
  // private double kI = 0.00138; 
  // private double kD = 0.001;
  // private double rkP = 0.0038;
    if(!isAligned())
    {
      swerveDriveSubsystem.holonomicDrive(0, strafe, rotation);
    }
    else
    {
      swerveDriveSubsystem.holonomicDrive(0, 0, 0);
    }
      
  }

  private void updateErrors() {
    prevRErr = rErr;
    prevXErr = xErr;
    rErr = getAngleErr();
    xErr = shooterX;
  }

  public boolean isAligned()
  {
      return angleAligned() && strafeAligned();
  }

  private boolean strafeAligned() {
    return Math.abs(shooterX) < 1;
  }

  private boolean angleAligned() {
    return getAngleErr() < 1;
  }

  public double getAngleErr() {
    double angleErr;
    double moddedGyro = (swerveDriveSubsystem.getGyroAngle() % 360);
    if(moddedGyro < 0) {
      if(Math.abs((0 - moddedGyro - 360)) < Math.abs((0 - moddedGyro))) {
        angleErr = (0 - moddedGyro - 360);
      }
      else {
        angleErr = 0 - moddedGyro;
      }
    }
    else {
      if(Math.abs((0 - moddedGyro + 360)) < Math.abs((0 - moddedGyro))) {
        angleErr = (0 - moddedGyro + 360);
      }
      else {
        angleErr = 0 - moddedGyro;
      }
    }
    SmartDashboard.putNumber("Absolute Angle", angleErr);
    return angleErr;
  }

  @Override
  public void periodic() {
    
    setIntakeCam(intakeMode.getSelected());
  }
}
