/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utility;

import java.util.ArrayList;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;

/**
 * Add your docs here.
 */
public class TrajectoryMaker {

private Pose2d start;
private Pose2d end;
private double angle;
private boolean isHyp;  //what does this mean?
private TrajectoryConfig config;
private Trajectory trajectory;
private ArrayList<Translation2d> listOfPoints;

private static double MAX_VELOCITY = 10; //Meters per second
private static double MAX_ACCELERATION = 3.5; // Meters per second squared
private static double MAX_CENTRIPETAL = 3.5;


public TrajectoryMaker(Pose2d start, Pose2d end, boolean isHyp) {
    this.start = start;
    this.end = end;
    this.isHyp = isHyp;
    angle = 0;
    config = new TrajectoryConfig(0.5, 0.5);  //make into constants
    config.setStartVelocity(0);
    config.setEndVelocity(0);
    config.setReversed(false);

    config.addConstraint(new CentripetalAccelerationConstraint(0.5));

    listOfPoints = new ArrayList<Translation2d>();
    trajectory = createTrajectory();
}
public TrajectoryMaker(Pose2d start, Pose2d end, ArrayList<Translation2d> points) {
    this.start = start;
    this.end = end;
    this.isHyp = false;
    angle = 0;
    config = new TrajectoryConfig(0.5,0.5); //slower for Bounce
    //config = new TrajectoryConfig(10, 3);  // (TrajectoryConfig​(double maxVelocityMetersPerSecond, double maxAccelerationMetersPerSecondSq))
    config.setStartVelocity(0);
    config.setEndVelocity(0);
    config.setReversed(false);
    config.addConstraint(new CentripetalAccelerationConstraint(0.5));
    listOfPoints = points;
    trajectory = createTrajectory();
}

public TrajectoryMaker(Pose2d start, Pose2d end, ArrayList<Translation2d> points, boolean isReversed) {
    this.start = start;
    this.end = end;
    this.isHyp = false;
    angle = 0;
    //config = new TrajectoryConfig(1, 1); //slower for Bounce
    config = new TrajectoryConfig(1,1);  // (TrajectoryConfig​(double maxVelocityMetersPerSecond, double maxAccelerationMetersPerSecondSq))
    config.setStartVelocity(0);
    config.setEndVelocity(0);
    config.setReversed(isReversed);
    config.addConstraint(new CentripetalAccelerationConstraint(0.5));
    listOfPoints = points;
    trajectory = createTrajectory();
}


private Trajectory createTrajectory()
{
    SmartDashboard.putBoolean("isHyp", isHyp);
    if(isHyp) 
    {   

        double x_Dis = end.getTranslation().getX() - start.getTranslation().getX();
        double y_Dis = end.getTranslation().getY() - start.getTranslation().getY();
        double distance = start.getTranslation().getDistance(end.getTranslation());
        angle =  90 - Math.toDegrees(Math.atan(x_Dis/y_Dis)); // because coordinate system is different
        if(y_Dis < 0) angle += 180;
        Pose2d endPos = new Pose2d(new Translation2d(distance,0), new Rotation2d(0));
        SmartDashboard.putNumber("angle", angle);
        SmartDashboard.putNumber("distance", distance);
        return TrajectoryGenerator.generateTrajectory(start, listOfPoints , endPos, config);
    }
    else
    {
        angle = 0;
        return TrajectoryGenerator.generateTrajectory(start, listOfPoints , end, config);
    }

}

public double getAngle() {
    return angle;
}

public Trajectory getTrajectory() {
    return trajectory;
}

}
