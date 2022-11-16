package frc.robot.utility;

public class MathUtils
{
    public static double deadband(double input, double limit, boolean isFieldOriented) {
        if (!isFieldOriented) {
            if(Math.abs(input) < 0.1) {
                return 0;
            }
            else {
                return input;
            }
        }
        if (Math.abs(input) < limit) {
            return 0;
        }
        return input;
    }
    // public static double deadband(double input) {
        
    //     if (Math.abs(input) < 0.20) 
    //         return 0;
    //     return input;
    // }

    public static double deadband(double input, double limit) {
        if (Math.abs(input) < limit) 
            return 0;
    return input;
    }

    public static final double INCHES_PER_METER = 39.37;

    public static double inchesToMeters(double inches)
    {
        return inches / INCHES_PER_METER;
    }
}