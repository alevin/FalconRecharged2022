/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
//import edu.wpi.first.wpilibj.trajectory.*;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;
import frc.robot.Constants;


public class Autonomous extends CommandBase {
  /**
   * Creates a new Autonomous.
   * 
   * Contains the code for Autonomous. Includes setup for Trajectory, odometry, kinematics. 
   * Test with 2 meter forward distance resulted in a ~16% error in distance. too far
   * Test with 4 meter forward distance resulted in a ~21% error in distance. too far
   */
  
  private RamseteController controller;
  private Trajectory trajectory;
  private SwerveDriveOdometry odometry;
  private SwerveDriveKinematics kinematics;
  private SwerveDriveSubsystem drivetrain;
  private Timer time;
  private double initGyro;
  private static double firstGyroAngle;
  private boolean firstAutoPath;
  private double angle;
  private static double endOrientation;

  //Speed constant calulated using 19251 as ticks/rev, 0.3048 ft to m conversion, 2pi*(1/6) is rev tp ft conversion
  public static final double SPEEDCONSTANT = (2*Math.PI*(1.0/6)*0.3048)/19251; //used to swtich from ticks to meters
  public double initPos[];


  public Autonomous(SwerveDriveSubsystem swerveDriveSubsystem, Trajectory trajectory, double angle) {  //what is the angle parameter here?
    // Use addRequirements() here to declare subsystem dependencies.
    // drivetrain = swerveDriveSubsystem;
    // this.trajectory = trajectory;
    // addRequirements(drivetrain);
    // time = new Timer();
    // initPos = new double[4];
    // this.angle = angle;
    this(swerveDriveSubsystem, trajectory, angle, true);
  }

  public Autonomous(SwerveDriveSubsystem swerveDriveSubsystem, Trajectory trajectory, double angle, boolean firstAutoPath) {  //what is the angle parameter here?
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain = swerveDriveSubsystem;
    this.trajectory = trajectory;
    addRequirements(drivetrain);
    time = new Timer();
    initPos = new double[4];
    this.angle = angle; //set to current gyro or expected angle or angle of modules
    this.firstAutoPath = firstAutoPath;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    controller = new RamseteController(2.5, 1);

     

    if(Math.abs(angle) <=45) {
      kinematics = new SwerveDriveKinematics(
      new Translation2d(Constants.MOD_TO_CENTER, Constants.MOD_TO_CENTER), //(+,+)
      new Translation2d(Constants.MOD_TO_CENTER, -Constants.MOD_TO_CENTER), //(+,-)
      new Translation2d(-Constants.MOD_TO_CENTER, -Constants.MOD_TO_CENTER), //(-,-)
      new Translation2d(-Constants.MOD_TO_CENTER, Constants.MOD_TO_CENTER)); //(-,+)\
    }
    else if(Math.abs(angle - 180) <= 45)  {
      kinematics = new SwerveDriveKinematics(
      new Translation2d(-Constants.MOD_TO_CENTER, -Constants.MOD_TO_CENTER), //(+,+)
      new Translation2d(-Constants.MOD_TO_CENTER, Constants.MOD_TO_CENTER), //(+,-)
      new Translation2d(Constants.MOD_TO_CENTER, Constants.MOD_TO_CENTER), //(-,-)
      new Translation2d(Constants.MOD_TO_CENTER, -Constants.MOD_TO_CENTER));
    }
    else if(Math.abs(angle - 90) <= 45)  {
      kinematics = new SwerveDriveKinematics(
      new Translation2d(Constants.MOD_TO_CENTER, -Constants.MOD_TO_CENTER), //(+,+)
      new Translation2d(-Constants.MOD_TO_CENTER, -Constants.MOD_TO_CENTER), //(+,-)
      new Translation2d(-Constants.MOD_TO_CENTER, Constants.MOD_TO_CENTER), //(-,-)
      new Translation2d(Constants.MOD_TO_CENTER, Constants.MOD_TO_CENTER));
    }
    else if(Math.abs(angle-270) <= 45) {
      kinematics = new SwerveDriveKinematics(
      new Translation2d(-Constants.MOD_TO_CENTER, Constants.MOD_TO_CENTER), //(+,+)
      new Translation2d(Constants.MOD_TO_CENTER, Constants.MOD_TO_CENTER), //(+,-)
      new Translation2d(Constants.MOD_TO_CENTER, -Constants.MOD_TO_CENTER), //(-,-)
      new Translation2d(-Constants.MOD_TO_CENTER, -Constants.MOD_TO_CENTER));
    }
    //odometry = new SwerveDriveOdometry(kinematics,new Rotation2d(Math.toRadians(0)));
    double angleOfRobotOrientation = drivetrain.getGyroAngle();
    odometry = new SwerveDriveOdometry(kinematics, new Rotation2d(Math.toRadians(angleOfRobotOrientation)), new Pose2d(0, 0, new Rotation2d(Math.toRadians(angleOfRobotOrientation))));
    //something about new pose 0,0 , init starting pose?
    
    //odometry.resetPosition(new Pose2d(0, 0, new Rotation2d(0)), new Rotation2d(Math.toRadians(0)));
    time.start();
    boolean isAuto = drivetrain.getIsAuto();
    drivetrain.setFieldOriented(false);
    drivetrain.setIsAuto(true);
    drivetrain.swapPIDSlot(1);
    drivetrain.swapDrivePIDSlot(1);
    // drivetrain.getSwerveModule(0).setTargetAngle(angle, isAuto);
    // drivetrain.getSwerveModule(1).setTargetAngle(angle, isAuto);
    // drivetrain.getSwerveModule(2).setTargetAngle(180+angle, isAuto);  //what is up with this module?
    // drivetrain.getSwerveModule(3).setTargetAngle(angle, isAuto);
    drivetrain.getSwerveModule(0).getDriveMotor().setInverted(true);
    drivetrain.getSwerveModule(1).getDriveMotor().setInverted(true);
    drivetrain.getSwerveModule(2).getDriveMotor().setInverted(true);
    drivetrain.getSwerveModule(3).getDriveMotor().setInverted(true);
    initPos[0] = angle;
    initPos[1] = angle;
    initPos[2] = angle;
    initPos[3] = angle;
    //drivetrain.zeroGyro(); //comment out maybe, if we don't do relative
    // if(firstAutoPath) {
    //   initGyro = drivetrain.getGyroAngle();
    //   firstGyroAngle = initGyro;
    // }
    // else {
    //   initGyro = firstGyroAngle;
    // }
    if(firstAutoPath) {
      firstGyroAngle = drivetrain.getGyroAngle();
      
    }
    initGyro = firstGyroAngle;
    SmartDashboard.putNumber("Init Gyro", initGyro);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("time: " + time.get());

    //10* is to give odometry speed in m/s instead of m/100ms
    odometry.update(new Rotation2d(Math.toRadians(drivetrain.getGyroAngle() - initGyro)), //substracting idk, get rid of ???
      new SwerveModuleState(10*drivetrain.getSwerveModule(0).getDriveMotor().getSelectedSensorVelocity()*SPEEDCONSTANT, new Rotation2d(Math.toRadians(drivetrain.getSwerveModule(0).getCurrentAngle()-initPos[0]))),
      new SwerveModuleState(10*drivetrain.getSwerveModule(1).getDriveMotor().getSelectedSensorVelocity()*SPEEDCONSTANT, new Rotation2d(Math.toRadians(drivetrain.getSwerveModule(1).getCurrentAngle()-initPos[1]))),
      new SwerveModuleState(10*drivetrain.getSwerveModule(2).getDriveMotor().getSelectedSensorVelocity()*SPEEDCONSTANT, new Rotation2d(Math.toRadians(drivetrain.getSwerveModule(2).getCurrentAngle()-initPos[2]))),
      new SwerveModuleState(10*drivetrain.getSwerveModule(3).getDriveMotor().getSelectedSensorVelocity()*SPEEDCONSTANT, new Rotation2d(Math.toRadians(drivetrain.getSwerveModule(3).getCurrentAngle()-initPos[3]))));
    SmartDashboard.putNumber("gyro - init", drivetrain.getGyroAngle()-initGyro);
    Trajectory.State goal = trajectory.sample(time.get()); // sample the trajectory at 3.4 seconds from the beginning
    SmartDashboard.putString("current Goal", goal.poseMeters.toString());
    ChassisSpeeds adjustedSpeeds = controller.calculate(odometry.getPoseMeters(), goal);
    SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(adjustedSpeeds);

    //Instead of this, just pass the state to each module?
    boolean isAuto = drivetrain.getIsAuto();

    drivetrain.getSwerveModule(0).setMeterSpeed(moduleStates[0].speedMetersPerSecond);
    drivetrain.getSwerveModule(0).setTargetAngle(moduleStates[0].angle.getDegrees()+initPos[0], isAuto);
    drivetrain.getSwerveModule(1).setMeterSpeed(moduleStates[1].speedMetersPerSecond);
    drivetrain.getSwerveModule(1).setTargetAngle(moduleStates[1].angle.getDegrees()+initPos[1], isAuto);
    drivetrain.getSwerveModule(2).setMeterSpeed(moduleStates[2].speedMetersPerSecond);
    drivetrain.getSwerveModule(2).setTargetAngle(moduleStates[2].angle.getDegrees()+initPos[2], isAuto);
    drivetrain.getSwerveModule(3).setMeterSpeed(moduleStates[3].speedMetersPerSecond);
    drivetrain.getSwerveModule(3).setTargetAngle(moduleStates[3].angle.getDegrees()+initPos[3], isAuto);

    SmartDashboard.putNumber("Speed 0", 10*drivetrain.getSwerveModule(0).getDriveMotor().getSelectedSensorVelocity()*SPEEDCONSTANT);
    SmartDashboard.putNumber("Angle 0 Expected", moduleStates[0].angle.getDegrees()+initPos[0]);
    SmartDashboard.putNumber("Speed 1", moduleStates[1].speedMetersPerSecond);
    SmartDashboard.putNumber("Angle 1 Expected", moduleStates[1].angle.getDegrees()+initPos[1]);
    SmartDashboard.putNumber("Speed 2", moduleStates[2].speedMetersPerSecond);
    SmartDashboard.putNumber("Angle 2 Expected", moduleStates[2].angle.getDegrees()+initPos[2]);
    SmartDashboard.putNumber("Speed 3", moduleStates[3].speedMetersPerSecond);
    SmartDashboard.putNumber("Angle 3 Expected", moduleStates[3].angle.getDegrees()+initPos[3]);
    SmartDashboard.putNumber("Angle 0 Actual", drivetrain.getSwerveModule(0).getCurrentAngle());
    SmartDashboard.putNumber("Angle 1 Actual", drivetrain.getSwerveModule(1).getCurrentAngle());
    SmartDashboard.putNumber("Angle 2 Actual", drivetrain.getSwerveModule(2).getCurrentAngle());
    SmartDashboard.putNumber("Angle 3 Actual", drivetrain.getSwerveModule(3).getCurrentAngle());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println(trajectory.getTotalTimeSeconds());
    drivetrain.swapPIDSlot(0);
    drivetrain.swapDrivePIDSlot(0);
    drivetrain.setFieldOriented(true);
    drivetrain.setIsAuto(false);
    time.reset();
    System.out.println("AutoEnded");
    //odometry.resetPosition(new Pose2d(0, 0, new Rotation2d(0)), new Rotation2d(Math.toRadians(0)));
    endOrientation = drivetrain.getGyroAngle()-initGyro;
  }

  public static double getEndOrientation() {
    return endOrientation;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Pose2d currPos = odometry.getPoseMeters();
    Pose2d tarPos = trajectory.getStates().get(trajectory.getStates().size()-1).poseMeters;
    double posDif = currPos.getTranslation().getDistance(tarPos.getTranslation());
    double rotDif = Math.abs((currPos.getRotation().minus(tarPos.getRotation())).getDegrees());
    SmartDashboard.putString("StartPos", trajectory.getStates().get(0).poseMeters.toString());
    SmartDashboard.putString("CurPos", currPos.toString());
    SmartDashboard.putString("tarPos", tarPos.toString());
    SmartDashboard.putNumber("posDif", posDif);
    SmartDashboard.putNumber("rotDif", rotDif);

    return trajectory.getTotalTimeSeconds() < time.get(); // review -Riley "Change once we account for slip.""
  }
}
