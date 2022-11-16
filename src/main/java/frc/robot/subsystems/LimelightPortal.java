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
import frc.robot.DaphneTwoConstants;

public class LimelightPortal extends SubsystemBase {
  /**
   * Creates a new LimelightPortal.
   */
  public LimelightPortal() {



  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run


    NetworkTable shooterLL = NetworkTableInstance.getDefault().getTable("limelight-shooter"); // 10.55.7.11
    NetworkTableEntry shX = shooterLL.getEntry("tx");
    NetworkTableEntry shY = shooterLL.getEntry("ty");
    NetworkTableEntry shA = shooterLL.getEntry("ta");
  }

  public void printLoc()
  {
    NetworkTable shooterLL = NetworkTableInstance.getDefault().getTable("limelight-shooter"); // 10.55.7.11
    NetworkTableEntry shX = shooterLL.getEntry("tx");
    NetworkTableEntry shY = shooterLL.getEntry("ty");
    NetworkTableEntry shA = shooterLL.getEntry("ta");

    double x = shX.getDouble(0.0);
    double y = shY.getDouble(0.0);
    double a = shA.getDouble(0.0);


    System.out.println("x="+x+",y="+y+",a="+a+" ,distance="+getDistance());
  }

  public double getX()
  {
    NetworkTable shooterLL = NetworkTableInstance.getDefault().getTable("limelight-shooter"); // 10.55.7.11
    NetworkTableEntry shX = shooterLL.getEntry("tx");
    NetworkTableEntry shY = shooterLL.getEntry("ty");
    NetworkTableEntry shA = shooterLL.getEntry("ta");

    double x = shX.getDouble(0.0);
    //double y = shY.getDouble(0.0);
    //double a = shA.getDouble(0.0);

    return x;
  }

  public double getDistance()
  {
    NetworkTable shooterLL = NetworkTableInstance.getDefault().getTable("limelight-shooter"); // 10.55.7.11
    NetworkTableEntry shX = shooterLL.getEntry("tx");
    NetworkTableEntry shY = shooterLL.getEntry("ty");
    NetworkTableEntry shA = shooterLL.getEntry("ta");

    //double x = shX.getDouble(0.0);
    double y = shY.getDouble(0.0);
    //double a = shA.getDouble(0.0);

    double distance = (DaphneTwoConstants.VISION_TARGET_HEIGHT - DaphneTwoConstants.LL_HEIGHT) /
                Math.tan(Math.toRadians(DaphneTwoConstants.LL_MOUNT_ANGLE)+Math.toRadians(y));

    return distance;
  }
}
