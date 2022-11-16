/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int CONVEYOR_SPARK = 8;
    public static final int CONVEYOR_TALON = 8; //change id
    public static final int INTAKE_SPARK = 9;
    public static final int CLIMBER1_TALON = 11;
    public static final int CLIMBER2_TALON = 10; 
    public static final int INTAKEFORWARD_SOLENOID = 4;
    public static final int INTAKEREVERSE_SOLENOID = 5;
    public static final int INTAKEFORWARD_SOLENOID2 = 6;
    public static final int INTAKEREVERSE_SOLENOID2 = 7;
    public static final int CLIMBERFORWARD_SOLENOID = 2;
    public static final int CLIMBERREVERSE_SOLENOID = 3;
    public static final int COLORPANELFORWARD_SOLENOID = 0;
    public static final int COLORPANELREVERSE_SOLENOID = 1;
    public static final int SHOOTER1_TALON = 35;
    public static final int SHOOTER2_TALON = 36;
    public static final int SPINNER_SPARK = 51;
    /*
    Physical Controller Number
    */
    public static final double GEAR_RATIO = 7.0;
    //public static final double WHEEL_SIZE = 3.838;
    public static final double WHEEL_SIZE = 4;  //  wheel size from the doc for HiGrip wheels

    public static final double TICKS_PER_INCH = (2048*GEAR_RATIO)/(WHEEL_SIZE*Math.PI);
    public static final boolean FORWARD = true;
    public static final double SPINNER_POSITION_PERCENT = .6;
    public static final double SPINNER_SPEED = 0.2; //only being used in SpinToMid right now

    public static final double MOD_TO_CENTER = 0.28575; // distance in meter from center point to each swerve module

    public static final double MAX_ANGLE_VELOCITY = 90;
    public static final double MAX_ANGLE_ACCELERATION = 180;

    public static final double MAX_DISTANCE_VELOCITY = 96; //24 inches per second
    public static final double MAX_DISTANCE_ACCELERATION = 96;
    public static final double DISTANCE_TOLERANCE = 6;  //inches
    public static final double DISTANCE_PID_P = 0.03;
    public static final double DISTANCE_PID_I = 0.00;
    public static final double DISTANCE_PID_D = 0.00237;

    public static final double TURN_TOLERANCE = 1.5;
    public static final double ANGLE_PID_P = 0.04;
    public static final double ANGLE_PID_I = 0.001;
    public static final double ANGLE_PID_D = 0.00237; //0.00237 is default
}
