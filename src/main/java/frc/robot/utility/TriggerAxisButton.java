package frc.robot.utility;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;
import static edu.wpi.first.wpilibj.util.ErrorMessages.requireNonNullParam;

public class TriggerAxisButton extends Button {

    private final GenericHID m_joystick;
  private final int m_AxisNumber;

  /**
   * Creates a joystick button for triggering commands.
   *
   * @param joystick The GenericHID object that has the button (e.g. Joystick, KinectStick, etc)
   * @param buttonNumber The button number (see {@link GenericHID#getRawButton(int) }
   */
  public TriggerAxisButton(GenericHID joystick, int AxisNumber) {
    requireNonNullParam(joystick, "joystick", "TriggerAxisButton");

    m_joystick = joystick;
    m_AxisNumber = AxisNumber;
  }

  /**
   * Gets the value of the joystick button.
   *
   * @return The value of the joystick button
   */
  @Override
  public boolean get() {
    return Math.abs(m_joystick.getRawAxis(m_AxisNumber)) > 0.2;
  }
}