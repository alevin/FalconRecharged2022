/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class TestbedContainer {

  private final XboxController mXboxController;
  private final XboxController mXboxController2;  //operator controller

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public TestbedContainer() {
    // create all the subsystems needed in this robot

    // create the input controllers
    mXboxController = new XboxController(0);
    mXboxController2 = new XboxController(1);

    // setup any default commands
    //colorPanelSpinner.setDefaultCommand(new SpinnerCommand(colorPanelSpinner, mXboxController2));

    // configure the buttons
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link JoystickButton}.
   */
  @SuppressWarnings("unused")
  private void configureButtonBindings() {
    // create these button objects for ease of use later

    // buttons on the main drive controller
    JoystickButton buttonA = new JoystickButton(mXboxController, XboxController.Button.kA.value);
    JoystickButton buttonX = new JoystickButton(mXboxController, XboxController.Button.kX.value);
    JoystickButton buttonB = new JoystickButton(mXboxController, XboxController.Button.kB.value);
    JoystickButton buttonY = new JoystickButton(mXboxController, XboxController.Button.kY.value);
    JoystickButton leftBumper = new JoystickButton(mXboxController, XboxController.Button.kLeftBumper.value);
    JoystickButton rightBumper = new JoystickButton(mXboxController, XboxController.Button.kRightBumper.value);
    JoystickButton back = new JoystickButton(mXboxController, XboxController.Button.kBack.value);
    JoystickButton start = new JoystickButton(mXboxController, XboxController.Button.kStart.value);

    // buttons on the secondary controller ( or operator controller )
    JoystickButton buttonA_2 = new JoystickButton(mXboxController2, XboxController.Button.kA.value);
    JoystickButton buttonX_2 = new JoystickButton(mXboxController2, XboxController.Button.kX.value);
    JoystickButton buttonB_2 = new JoystickButton(mXboxController2, XboxController.Button.kB.value);
    JoystickButton buttonY_2 = new JoystickButton(mXboxController2, XboxController.Button.kY.value);
    JoystickButton leftBumper_2 = new JoystickButton(mXboxController2, XboxController.Button.kLeftBumper.value);
    JoystickButton rightBumper_2 = new JoystickButton(mXboxController2, XboxController.Button.kRightBumper.value);
    
    // hook up button presses to the commands we want
  }

  public Command getAutonomousCommand() {
    return null;
  }
}