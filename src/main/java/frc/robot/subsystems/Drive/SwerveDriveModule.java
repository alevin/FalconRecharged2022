/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.Drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.swervedrive.SwerveModuleCommand;
import frc.robot.commands.swervedrive.Autonomous;

public class SwerveDriveModule extends SubsystemBase {
    /**
     * Creates a new SwerveDriveModule.
     */


    private double mLastError = 0, mLastTargetAngle = 0;

    private final int mModuleNumber;

    private final double mZeroOffset;
    private final TalonSRX mAngleMotor;
    public final TalonFX mDriveMotor;

    public SwerveDriveModule(final int moduleNumber, final TalonSRX angleMotor, final TalonFX driveMotor, final double zeroOffset) {
        mModuleNumber = moduleNumber;

        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;
        mZeroOffset = zeroOffset;
        mDriveMotor.setSelectedSensorPosition(0);

        angleMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
        angleMotor.setSensorPhase(true);

        angleMotor.config_kP(0, 19.0, 0);   // 20.2
        angleMotor.config_kI(0, 0.04, 0);  //0.001
        angleMotor.config_kD(0, 60, 0);     //60

        angleMotor.config_kP(1, 19, 0);
        angleMotor.config_kI(1, 0.04, 0);
        angleMotor.config_kD(1, 60, 0);

        
        angleMotor.setNeutralMode(NeutralMode.Brake);
        angleMotor.set(ControlMode.Position, 0);
        angleMotor.configNeutralDeadband(0.07);
        driveMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0); // ADD TO CANSPARKMAX LATER***

        driveMotor.setNeutralMode(NeutralMode.Brake);
        driveMotor.config_kF(0, 0.0018, 0); //0.0018
        driveMotor.config_kP(0, 0.001, 0); // 0.02 //0.001-Auto
        driveMotor.config_kI(0, 0, 0); // 0.000001
        driveMotor.config_kD(0, 0, 0); // 0.0065

        driveMotor.config_kF(1, 0.0015, 0); //0.0018
        driveMotor.config_kP(1, 0.0015, 0); // 0.02 //0.001-Auto
        driveMotor.config_kI(1, 0, 0); // 0.000001
        driveMotor.config_kD(1, 0, 0);

        // Set amperage limits
        angleMotor.configContinuousCurrentLimit(30, 0);
        angleMotor.configPeakCurrentLimit(30, 0);
        angleMotor.configPeakCurrentDuration(100, 0);
        angleMotor.enableCurrentLimit(true);

        // driveMotor.setSmartCurrentLimit(15); No clue how to set Current Limit...
        // driveMotor.setSecondaryCurrentLimit(15, 0); //config`SupplyCurrentLimit
        // (SupplyCurrentLimitConfiguration currLimitCfg, int timeoutMs)?
        // driveMotor.enableCurrentLimit(true); try using pheonix tuner

        setDefaultCommand(new SwerveModuleCommand(this));

    }
    /*
    Daphne one values
        public SwerveDriveModule(int moduleNumber, TalonSRX angleMotor, TalonFX driveMotor, double zeroOffset) {
        mModuleNumber = moduleNumber;

        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;
        mZeroOffset = zeroOffset;
        mDriveMotor.setSelectedSensorPosition(0);

        angleMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
        angleMotor.setSensorPhase(true);
        angleMotor.config_kP(0, 19, 0);
        angleMotor.config_kI(0, 0.04, 0);
        angleMotor.config_kD(0, 60, 0);

        angleMotor.config_kP(1, 19, 0);
        angleMotor.config_kI(1, 0.04, 0);
        angleMotor.config_kD(1, 60, 0);

        angleMotor.setNeutralMode(NeutralMode.Brake);
        angleMotor.set(ControlMode.Position, 0);
        angleMotor.configNeutralDeadband(0.07);
        driveMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0); // ADD TO CANSPARKMAX LATER***

        driveMotor.setNeutralMode(NeutralMode.Brake);
        driveMotor.config_kF(0, 0.0018, 0); //0.0018
        driveMotor.config_kP(0, 0.001, 0); // 0.02 //0.001-Auto
        driveMotor.config_kI(0, 0, 0); // 0.000001
        driveMotor.config_kD(0, 0, 0); // 0.0065

        driveMotor.config_kF(1, 0.0, 0); //0.0018
        driveMotor.config_kP(1, 0.0019, 0); // 0.02 //0.001-Auto
        driveMotor.config_kI(1, 0, 0); // 0.000001
        driveMotor.config_kD(1, 0, 0);

        // Set amperage limits
        angleMotor.configContinuousCurrentLimit(30, 0);
        angleMotor.configPeakCurrentLimit(30, 0);
        angleMotor.configPeakCurrentDuration(100, 0);
        angleMotor.enableCurrentLimit(true);

        // driveMotor.setSmartCurrentLimit(15); No clue how to set Current Limit...
        // driveMotor.setSecondaryCurrentLimit(15, 0); //config`SupplyCurrentLimit
        // (SupplyCurrentLimitConfiguration currLimitCfg, int timeoutMs)?
        // driveMotor.enableCurrentLimit(true); try using pheonix tuner

        setDefaultCommand(new SwerveModuleCommand(this));

    }
     */

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
    
    public TalonSRX getAngleMotor() {
        return mAngleMotor;
    }

    /**
     * Get the current angle of the swerve module
     *
     * @return An angle in the range [0, 360)
     */
    public double getCurrentAngle() {
        double angle = mAngleMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0);
        angle -= mZeroOffset;
        angle %= 360;
        if (angle < 0)
            angle += 360;

        return angle;
    }

    public TalonFX getDriveMotor() {
        return mDriveMotor;
    }

    public void setTargetAngle(double targetAngle, boolean isAuto) {

        mLastTargetAngle = targetAngle;

        targetAngle %= 360;

        SmartDashboard.putNumber("Module Target Angle " + mModuleNumber, targetAngle % 360);

        targetAngle += mZeroOffset;

        final double currentAngle = mAngleMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0);
        double currentAngleMod = currentAngle % 360;
        //if (currentAngleMod < 0)
           // currentAngleMod += 360;

        double delta = currentAngleMod - targetAngle;

        if (delta > 180) {
            targetAngle += 360;
        } else if (delta < -180) {
            targetAngle -= 360;
        }
        if (!isAuto) {
            
            delta = currentAngleMod - targetAngle;
            if (delta > 90 || delta < -90) {
                if (delta > 90)
                    targetAngle += 180;
                else if (delta < -90)
                    targetAngle -= 180;
                mDriveMotor.setInverted(false);
            } else {
                mDriveMotor.setInverted(true);
            }
        }

        targetAngle += currentAngle - currentAngleMod;

        final double currentError = mAngleMotor.getClosedLoopError(0);
        mLastError = currentError;
        targetAngle *= 1024.0 / 360.0;
        mAngleMotor.set(ControlMode.Position, targetAngle);

    }

    public void setTargetDistance(final double distance) { // inches NEED TO TEST
        // TalonFX’s integrated sensor has a native resolution of 2048 units per
        // rotation regardless of which class is used.
        mDriveMotor.set(ControlMode.Position, distance);
    }

    public void setTargetSpeed(final double speed) {
        mDriveMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void setMeterSpeed(final double speed) // in meters per sec
    {
        mDriveMotor.set(TalonFXControlMode.Velocity, speed / Autonomous.SPEEDCONSTANT);
    }

    public void resetEncoder() {
        mDriveMotor.setSelectedSensorPosition(0);
    }

    public double getTargetAngle() {
        return mLastTargetAngle;
    }

    public double encoderTicksToInches(final double ticks) {
        double ticksToInches = ticks / Constants.TICKS_PER_INCH;
        return ticksToInches;  
    }

    public double getPosition() {
        return (double) mDriveMotor.getSelectedSensorPosition();
    }

    public void setPIDSlot(final int slot) {
        mAngleMotor.selectProfileSlot(slot, 0);
    }
    public void setDrivePIDSlot(int slot)   {
        mDriveMotor.selectProfileSlot(slot, 0);
    }

    public double getInches() {
        return encoderTicksToInches(mDriveMotor.getSelectedSensorPosition());
    }

    public void printTick() {
        SmartDashboard.putNumber("Ticks" + mModuleNumber, mDriveMotor.getSelectedSensorPosition());
    }
}
