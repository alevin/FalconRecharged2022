/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.ReadLimelight;

import frc.robot.commands.AutoPaths.AutoPath1;
import frc.robot.commands.AutoPaths.AutoPath2;
import frc.robot.commands.AutoPaths.OneCycleAuto;
import frc.robot.commands.AutoPaths.SensorTest;
import frc.robot.commands.swervedrive.*;
import frc.robot.subsystems.Drive.SwerveDriveModule;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.LimelightPortal;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;
import frc.robot.utility.TrajectoryMaker;
import frc.robot.utility.TriggerAxisButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class DaphneTwoContainer {
  // The robot's subsystems and commands are defined here...

  private final XboxController mXboxController;
  private final XboxController mXboxController2;  //operator controller

  private final SwerveDriveSubsystem swerveDriveSubsystem;
  //private final ColorPanelSpinner colorPanelSpinner;
  //private final ColorSensor colorSensor; isnt installed on robot.
  private final Limelight limelight;
  //private final Compressor compressor;
  //private final ClimberTalon climberT;
  private final LimelightPortal limeL;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public DaphneTwoContainer() {
    // create all the subsystems needed in this robot
    SwerveDriveModule m0 = new SwerveDriveModule(0, new TalonSRX(DaphneTwoConstants.ANGLE2_TALON), new TalonFX(DaphneTwoConstants.DRIVE2_TALON), 128); //real:390 practice: 212
    SwerveDriveModule m1 = new SwerveDriveModule(1, new TalonSRX(DaphneTwoConstants.ANGLE1_TALON), new TalonFX(DaphneTwoConstants.DRIVE1_TALON), 340); //real:293 practice: 59
    SwerveDriveModule m2 = new SwerveDriveModule(2, new TalonSRX(DaphneTwoConstants.ANGLE3_TALON), new TalonFX(DaphneTwoConstants.DRIVE3_TALON), 96); //real:298 practice: 56
    SwerveDriveModule m3 = new SwerveDriveModule(3, new TalonSRX(DaphneTwoConstants.ANGLE4_TALON), new TalonFX(DaphneTwoConstants.DRIVE4_TALON), 164); //real: 355 practice: 190 // ````````````````````````````````````````````````````````````````````````````````````````````````````````357

    swerveDriveSubsystem = new SwerveDriveSubsystem(m0, m1, m2, m3);
    swerveDriveSubsystem.zeroGyro();
    //colorPanelSpinner = new ColorPanelSpinner();
    //colorSensor = new ColorSensor(); isnt installed on robot.
    limelight = new Limelight(swerveDriveSubsystem);
    //compressor = null; //new Compressor();
    //climberT = new ClimberTalon();
    limeL = new LimelightPortal();

    // create the input controllers
    mXboxController = new XboxController(0);
    mXboxController2 = new XboxController(1);

    // setup any default commands
    swerveDriveSubsystem.setDefaultCommand(new HolonomicDriveCommand(swerveDriveSubsystem, mXboxController));

    // configure the buttons
    //configureButtonBindingsForAuto();
    configureButtonsForPowerPort();
  }

  public void configureButtonsForPowerPort() {
    // JoystickButton buttonA = new JoystickButton(mXboxController, XboxController.Button.kA.value);
    // JoystickButton buttonX = new JoystickButton(mXboxController, XboxController.Button.kX.value);
    // JoystickButton buttonB = new JoystickButton(mXboxController, XboxController.Button.kB.value);
    // JoystickButton buttonY = new JoystickButton(mXboxController, XboxController.Button.kY.value);
    // JoystickButton leftBumper = new JoystickButton(mXboxController, XboxController.Button.kLeftBumper.value);

    JoystickButton rightBumper = new JoystickButton(mXboxController, XboxController.Button.kRightBumper.value);
    JoystickButton back = new JoystickButton(mXboxController, XboxController.Button.kBack.value);

    // JoystickButton start = new JoystickButton(mXboxController, XboxController.Button.kStart.value);
    // JoystickButton stickLeft = new JoystickButton(mXboxController, XboxController.Button.kLeftStick.value);
    // JoystickButton stickRight = new JoystickButton(mXboxController, XboxController.Button.kRightStick.value);

    

    back.whileHeld(new ZeroNavX(swerveDriveSubsystem));
    rightBumper.whenPressed(new ToggleFieldOrientedCommand(swerveDriveSubsystem));
    //start.whenPressed(new InstantCommand(() -> {shooterMotor.setMotorRPM(0);}, shooterMotor)); 

    //buttonY.whenPressed(new ToggleConveyorIntake(intake, -1));
    //toggle shooter
    //buttonB.whenPressed(new InstantCommand(() -> shooterMotor.toggleShooter(-DaphneTwoConstants.GREEN_RPM), shooterMotor)); //change 1000 rpm later
    // int inputRPM = (int) SmartDashboard.getNumber("Input Shooter RPM", 0);
    //buttonB.whenPressed(new ToggleIntake(intake));
   // buttonB.whenPressed(new InstantCommand((DaphneTwoConstants.GREEN_RPM) -> toggleShooter() //looking for something that doesn't take parameters  
    //buttonX.whenPressed(new ToggleClimberGearLock(climberT)); 
    //buttonA.whenPressed(new MoveClimberArm(climberT, 1000));
    
   
    // JoystickButton buttonA2 = new JoystickButton(mXboxController2, XboxController.Button.kA.value);
    // JoystickButton buttonB2 = new JoystickButton(mXboxController2, XboxController.Button.kB.value);
    // JoystickButton buttonX2 = new JoystickButton(mXboxController2, XboxController.Button.kX.value);
    // JoystickButton buttonY2 = new JoystickButton(mXboxController2, XboxController.Button.kY.value);
    // JoystickButton leftBumper2 = new JoystickButton(mXboxController2, XboxController.Button.kLeftBumper.value);
    // JoystickButton rightBumper2 = new JoystickButton(mXboxController2, XboxController.Button.kRightBumper.value);
    // TriggerAxisButton LeftTrigger2 = new TriggerAxisButton(mXboxController2, XboxController.Axis.kLeftTrigger.value);
    // TriggerAxisButton RightTrigger2 = new TriggerAxisButton(mXboxController2, XboxController.Axis.kRightTrigger.value);

     }
  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindingsForAuto() {
    JoystickButton buttonA = new JoystickButton(mXboxController, XboxController.Button.kA.value);
    JoystickButton buttonX = new JoystickButton(mXboxController, XboxController.Button.kX.value);
    JoystickButton buttonB = new JoystickButton(mXboxController, XboxController.Button.kB.value);
    JoystickButton buttonY = new JoystickButton(mXboxController, XboxController.Button.kY.value);
    JoystickButton leftBumper = new JoystickButton(mXboxController, XboxController.Button.kLeftBumper.value);
    JoystickButton rightBumper = new JoystickButton(mXboxController, XboxController.Button.kRightBumper.value);
    JoystickButton back = new JoystickButton(mXboxController, XboxController.Button.kBack.value);
    JoystickButton start = new JoystickButton(mXboxController, XboxController.Button.kStart.value);
    JoystickButton stickLeft = new JoystickButton(mXboxController, XboxController.Button.kLeftStick.value);
    JoystickButton stickRight = new JoystickButton(mXboxController, XboxController.Button.kRightStick.value);

/*
    JoystickButton buttonA_2 = new JoystickButton(mXboxController2, XboxController.Button.kA.value);
    JoystickButton buttonX_2 = new JoystickButton(mXboxController2, XboxController.Button.kX.value);
    JoystickButton buttonB_2 = new JoystickButton(mXboxController2, XboxController.Button.kB.value);
    JoystickButton buttonY_2 = new JoystickButton(mXboxController2, XboxController.Button.kY.value);
    JoystickButton leftBumper_2 = new JoystickButton(mXboxController2, XboxController.Button.kLeftBumper.value);
    JoystickButton rightBumper_2 = new JoystickButton(mXboxController2, XboxController.Button.kRightBumper.value);
    */
    
    //buttonX.whileHeld(new IntakeSpeed(-0.8));
    //buttonA.whenPressed(new ToggleIntake());  //testing inline
    /*new JoystickButton(m_driverController, Button.kB.value)
        .whenPressed(new InstantCommand(m_hatchSubsystem::releaseHatch, m_hatchSubsystem));*/
    /*
    The following is an example of an inline command.  No need to create a CommandBase Subclass for simple commands
    */
    buttonX.whenPressed(new ToggleFieldOrientedCommand(swerveDriveSubsystem));
    //buttonB.whileHeld(new IntakeSpeed(intake,-.5)); //while b is held down intake runs

    // Command unoCycle = new OneCycleAuto(swerveDriveSubsystem, shooterMotor);
    // buttonB.whenPressed(unoCycle);
    //buttonB.whenPressed(new Autonomous(swerveDriveSubsystem, path2Meters.getTrajectory(), path2Meters.getAngle(), true));
    //leftBumper.whileHeld(new ConveyorSpeed( conveyorT, -.7));
    //leftBumper.whileHeld(new SetShooterSpeed(shooterMotor, 6000));
    back.whileHeld(new ZeroNavX(swerveDriveSubsystem));
    //buttonX.whileHeld(new ConveyorSpeed( conveyorT, -.5));
   // buttonX.whenPressed(new ToggleClimberGearLock(climberT)); 
    //rightBumper.whenPressed(new AutoShoot(conveyorT, shooterMotor, false, DaphneTwoConstants.GREEN_RPM, DaphneTwoConstants.CONVEYOR_UNLOADS_SPEED));
    

    //start.whileHeld(new ReadLimelight(limeL));
    //start.whenPressed(new RotateWithLimelight(limeL, swerveDriveSubsystem));
    //start.whenPressed(new TurnToZeroLimelight(0, swerveDriveSubsystem, limeL));
    //leftBumper.whenPressed(new TurnToAngleProfiled(45, swerveDriveSubsystem));
    //rightBumper.whenPressed(new TurnToAngleProfiled(-45, swerveDriveSubsystem));
    //start.whenPressed(new AlignToTargetLimelight( swerveDriveSubsystem, limeL));
    //start.whenPressed(new AutoPath1(swerveDriveSubsystem));
    //start.whenPressed(new GalacticSearchTest(swerveDriveSubsystem, intake));
    /*start.whenPressed(new ConditionalCommand,
      new AutoPath1(swerveDriveSubsystem), 
      new AutoPath2(swerveDriveSubsystem), 
      ()->fifty50()));*/
    //start.whenPressed(new GalacticSearch(swerveDriveSubsystem, intake, conveyorT));
    // TrajectoryMaker path = TrajectoryHelper.createTest4Meters();
    // TrajectoryMaker path = TrajectoryHelper.createTest3Meters();
   
    //  TrajectoryMaker path = TrajectoryHelper.createBarrel();


   TrajectoryMaker path00 = TrajectoryHelper.createBounce00();
   TrajectoryMaker path01 = TrajectoryHelper.createBounce01();
   TrajectoryMaker path10 = TrajectoryHelper.createBounce10();
   TrajectoryMaker path11 = TrajectoryHelper.createBounce11();
   TrajectoryMaker path20 = TrajectoryHelper.createBounce20();
   TrajectoryMaker path21 = TrajectoryHelper.createBounce21();
   TrajectoryMaker path30 = TrajectoryHelper.createBounce30();
   TrajectoryMaker path31 = TrajectoryHelper.createBounce31();

  //  TrajectoryMaker path2 = TrajectoryHelper.createLeg1();
  //  TrajectoryMaker path3 = TrajectoryHelper.createLeg2();
  //  TrajectoryMaker path4 = TrajectoryHelper.createLeg3();
  //  TrajectoryMaker path5 = TrajectoryHelper.createLeg4();

    //TrajectoryMaker Start_B3 = TrajectoryHelper.Start_to_B3();
    //TrajectoryMaker B3_Finish = TrajectoryHelper.B3_to_Finish();
    //TrajectoryMaker _B3 = TrajectoryHelper.Start_to_B3();

    

  
    //start.whenPressed(bounceCommand.withTimeout(60));

    //buttonX.whenPressed(autoCommand2.withTimeout(60));

    //Command autoCommand2 = new Autonomous(swerveDriveSubsystem, path2.getTrajectory(), path2.getAngle());
    //Command autoCommand3 = new Autonomous(swerveDriveSubsystem, path3.getTrajectory(), path3.getAngle());
    //Command autoCommand4 = new Autonomous(swerveDriveSubsystem, path4.getTrajectory(), path4.getAngle());
    //Command autoCommand5 = new Autonomous(swerveDriveSubsystem, path5.getTrajectory(), path5.getAngle());

    // buttonY.whenPressed(autoCommand2.withTimeout(60));
    // buttonX.whenPressed(autoCommand3.withTimeout(60));
    // buttonA.whenPressed(autoCommand4.withTimeout(60));
    // buttonB.whenPressed(autoCommand5.withTimeout(60));

    //TrajectoryMaker path4 = TrajectoryHelper.createDriveForward();
    //Command autoCommand4 = new Autonomous(swerveDriveSubsystem, path4.getTrajectory(), path4.getAngle(), true);
    //Command galacticSearchCommand = new SequentialCommandGroup(autoCommand4, galacticSearch);
    //start.whenPressed(galacticSearch);//autoCommand.withTimeout(60)


  /*
    start.whenPressed(new ConditionalCommand(
      new AutoPath1(swerveDriveSubsystem), 
      new AutoPath2(swerveDriveSubsystem), 
      ()->fifty50()));
      */
  }

  // public boolean fifty50()
  // {
  //   boolean out = Math.random() < 0.5;
  //   System.out.println("********************** left " + out);
  //   return out;
  // }



  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //How can we change this to select the auto routine from the dashboard?
    //return new AutoPath1(swerveDriveSubsystem);
    
    //int path = 1; // for one cycle auto: 0, anything else is starting Left
    int delay = 0;

    //if(path == 1) {
      return new OneCycleAuto(swerveDriveSubsystem, DaphneTwoConstants.GREEN_RPM, delay);
      
    //}
    
    
  
  }

}