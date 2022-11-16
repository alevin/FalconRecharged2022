package frc.robot.commands.swervedrive;

import frc.robot.subsystems.Drive.HolonomicDrivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AdjustFieldOrientedAngleCommand extends CommandBase {
	public static final double ADJUSTMENT_AMOUNT = 5;

	private final HolonomicDrivetrain mDrivetrain;
	private final boolean mIncreaseAngle;

	public AdjustFieldOrientedAngleCommand(HolonomicDrivetrain drivetrain, boolean increaseAngle) {
		mDrivetrain = drivetrain;
		mIncreaseAngle = increaseAngle;
	}

	@Override
	public void execute() {
		if (mIncreaseAngle) {
			mDrivetrain.setAdjustmentAngle(mDrivetrain.getAdjustmentAngle() + ADJUSTMENT_AMOUNT);
		} else {
			mDrivetrain.setAdjustmentAngle(mDrivetrain.getAdjustmentAngle() - ADJUSTMENT_AMOUNT);
		}
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
