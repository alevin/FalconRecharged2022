package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.utility.MathUtils;
import frc.robot.utility.TrajectoryMaker;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class TrajectoryHelper {
    //for one cycle auto
    public static double[][] lineToTargetZone = {
        {0, 0},
        {90, 0}
    };



    public static double[][] targetZoneToLine = {
        {90, 0},
        {-2, 0},
        {0, 2},
    };

    // public static double[][] targetZoneToTrench = {
    //     {90, 0},
    //     {-86.62, 66.91}  
    // };
    
    // public static double[][] trenchRunPickUp = {
    //     {-86.62, 66.91},
    //     {-196.62, 66.91}
    // };

    // for one cycle auto starting two feet to the left
    public static double[][] lineLeftToTargetZone = {
        {0, 0},
        {90, 40}
    };

    public static double[][] targetToLine = {
        {90, 24},
        {-2, 24},
        {0, 26}
    };

    public static double[][] test4Meters= {
        {0,0},
        {157.48,0}, // roughly equal to 4 meters
    };

    public static double[][] test3Meters= {
        {0,0},
        {117.7721,0}, // roughly equal to 3 meters
    };

    public static double[][] test2Meters= {
        {0,0},
        {78.7402,0}, // roughly equal to 2 meters
    };

    public static double[][] test1Meter= {
        {0,0},
        {39.3701,0}, // roughly equal to 2 meters
    };

public static double[][] testStep= {
    // Slalom 2021.02.15 001
    {30,150},
    {60,160},
    {90,120},
    {120,85},
    {150,80},
    {180,80},
    {225,80},
    {255,90},
    {270,120},
    {285,155},
    {310,160},
    {330,150},
    {340,120},
    {330,85},
    {300,75},
    {280,95},
    {270,120},
    {265,140},
    {240,155},
    {210,160},
    {180,160},
    {150,160},
    {120,155},
    {105,145},
    {90,120},
    {80,95},
    {60,80},
    {30,90},
    };


    public static double[][] test2MetersAndBack = {
        {0,0},
        {78.7402,0}, // roughly equal to 2 meters
        {78.7402,78.7402},
        {0,78.7402},
        {0,0}
    };

    public static double[][] test2MetersTriangle= {
        {0,0},
        {78.7402,0}, // roughly equal to 2 meters
        {78.7402,78.7402}, // roughly equal to 2 meters
        {0,0}
    };

    public static double[][] slalom = { 
        // {30,150}, //1
        // {80,145},
        // {95,90},
        // {235,90},
        // {240,155}, //5 old: {250, 145}
        // {305,155}, //old: {300, 145}
        // {305,90},
        // {255,90},
        // {230,155}, //old: {230, 145}
        // {95,155}, //10 old: {90, 145}
        // {60,90},
        // {25,90},
        {30,150}, //1
        {90,153},
        {105,85},
        {235,85},
        {250,155}, //5 old: {250, 145}
        {315,155}, //old: {300, 145}
        {315,85},
        {245,80},
        {220,155}, //old: {230, 145}
        {80,150}, //10 old: {90, 145}
        {65,75},
        {20,75},
               };

    public static double[][] leg1 = {
        {0,0},
        {60,0},
    };

    public static double[][] leg2 = {
        {60,0},
        {60,60},
    };

    public static double[][] leg3 = {
        {60,60},
        {0,60},
    };

    public static double[][] leg4 = {
        {0,60},
        {0,0},
    };

     public static double[][] bounce1Relative = {
        {0, 0},
        {30, 0}
     };

     public static double[][] test = {
         {95, 30},
         {110, 30}
     };

     public static double[][] bounce00 = {
        {30, 90},
        {37, 90},
     };

     public static double[][] bounce01 = {
        {37, 90},         //1
        {90, 77},
        {95,30}, //3
     };

     public static double[][] bounce10 = {
        {95, 30},
        {95, 37},
     };
     public static double[][] bounce11 = {
        {95, 37},//3
        {100, 85},
        {125,100}, // 100, 90
        {135,145},//5
        {175,135}, //6
        {190,25},
        // {190,130},//8
        // {255,130},
        // {275,30},
        // {270,85}, //11
        // {330,85},
     };

     public static double[][] bounce20 = {
         {185, 25},
         {185, 32},
     };
     public static double[][] bounce21 = {
        {185, 32},
        {185,135},//8
        {265,135},
        {275,25},
     };

     public static double[][] bounce30 = {
        {277,25},
        {277, 32},
     };

     public static double[][] bounce31 = {
        {277, 32},
        {285,102}, //11
        {330,102}
     };
    
     public static double[][] barrel0 = {
         {30, 90},
         {37, 90}
     };
     public static double[][] barrel = {
        {37,90}, //1
        {60,90}, 
        {120,90},
        {150,90},
        {180,110}, //5
        {180,140},
        {150,150},
        {110,140},
        {110,80},
        {205,110}, //10
        {260,90},
        //{270,60},
        {270,30}, //12
        {190,30},
        {190,80},//14
        {230,120},
        {250,135},
        {300,140},
        {310,80},
        {80,70},
        {45,65}, //19
                };

        private static double[][] driveForward = {
            {12,60},
            {15,60},
        };

    public static double GLOBAL_SCALE = 0.827;

    /**
     * translateAndScale takes an array of integer coordinates in 2-d space, and scales them to meters, and applies a scale in additinoos
     * Omits the first and last points
     * @param pointsArray  arrray of more than two X,Y coordinates
     * @param scale  resize the entire grid
     * @return
     */
    public static ArrayList<Translation2d> translateAndScale(double[][] pointsArray, double scale) {
        ArrayList<Translation2d> points = new ArrayList<>(pointsArray.length-1);
        // translate all the points to the initial coordinate
        double initialX = pointsArray[0][0];
        double initialY = pointsArray[0][1];

        for ( int i = 1; i < pointsArray.length; i++) {
            // also; convert from inches to meters
            // translate to 0,0
            double x = pointsArray[i][0];
            double y = pointsArray[i][1];
            x = x - initialX; // translate points to be relative to starting point
            y = y - initialY;
            x = MathUtils.inchesToMeters(x);  // convert to metric
            y = MathUtils.inchesToMeters(y);
            x = x * scale;  // apply extra scale
            y = y * scale;
            points.add(new Translation2d(x, y));
        }
        return points;
    }

    public static TrajectoryMaker createTrajectory(double [][] inputPoints, double scale, double startOrientation, double endOrientation, boolean isReversed) // for bounce
    {
        ArrayList<Translation2d> points = translateAndScale(inputPoints, scale);  // make .2 for Hajel's garage.  Turns the 30 foot field to 6 feet
        Pose2d initialPose = new Pose2d(0, 0, new Rotation2d(startOrientation));
        Translation2d lastPoint = points.remove(points.size()-1);  // remove last point in array
        Pose2d endPose = new Pose2d(lastPoint.getX(), lastPoint.getY(), new Rotation2d(endOrientation));

        return new TrajectoryMaker(initialPose, endPose, points, isReversed);
    }

    
    public static TrajectoryMaker createTrajectory(double [][] inputPoints, double scale) // for slalom and barrel
    {
        ArrayList<Translation2d> points = translateAndScale(inputPoints, scale);  // make .2 for Hajel's garage.  Turns the 30 foot field to 6 feet
        Pose2d initialPose = new Pose2d(0, 0, new Rotation2d(0));
        Translation2d lastPoint = points.remove(points.size()-1);  // remove last point in array
        Pose2d endPose = new Pose2d(lastPoint.getX(), lastPoint.getY(), new Rotation2d(180));

        return new TrajectoryMaker(initialPose, endPose, points, false);
    }

    

    public static TrajectoryMaker createTrajectory(double [][] inputPoints)
    {
        return createTrajectory(inputPoints, GLOBAL_SCALE);
    }

    public static TrajectoryMaker createLineToTargetZone() {
        return createTrajectory(lineToTargetZone, GLOBAL_SCALE, 0, 0, false);
    }

    // public static TrajectoryMaker createTargetZoneToTrench() {
    //     return createTrajectory(targetZoneToTrench, GLOBAL_SCALE, 0, 0, true);
    // }

    // public static TrajectoryMaker createTrenchRunPickUp() {
    //     return createTrajectory(trenchRunPickUp, GLOBAL_SCALE, 0, 0, true);
    // }

    public static TrajectoryMaker createTargetZoneToLine() {
        return createTrajectory(targetZoneToLine, GLOBAL_SCALE, 0, Math.PI - 0.5, true);
    }

    public static TrajectoryMaker createLineLeftToTargetZone() {
        return createTrajectory(lineLeftToTargetZone, GLOBAL_SCALE, 0, 0, false); 
    }

    public static TrajectoryMaker createTargetZoneToLineLeft() {
        return createTrajectory(targetToLine, GLOBAL_SCALE, 0, Math.PI - 0.5, true);
    }

    public static TrajectoryMaker createDriveForward() // test path going only 4 meters forward
    {
        return createTrajectory(driveForward, GLOBAL_SCALE, 0, 0, false);
    }   

    public static TrajectoryMaker createTest() // test path going only 4 meters forward
    {
        return createTrajectory(test, GLOBAL_SCALE, 0, 0, true);
    }   

    public static TrajectoryMaker createTest4Meters() // test path going only 4 meters forward
    {
        return createTrajectory(test4Meters, GLOBAL_SCALE);
    }   

    public static TrajectoryMaker createTest3Meters() // test path going 2 meters forward
    {
        return createTrajectory(test3Meters, GLOBAL_SCALE);
    }

    public static TrajectoryMaker createTest2MetersAndBack() // test path going 2 meters forward
    {
        return createTrajectory(test2MetersAndBack, GLOBAL_SCALE, 0, 0, false);
    }

    public static TrajectoryMaker createTestStep() // test path going forward
    {
        return createTrajectory(testStep, GLOBAL_SCALE);
    }
    
    public static TrajectoryMaker createSlalom()
    {
        return createTrajectory(slalom, GLOBAL_SCALE, 0, Math.PI, false);
    }
    
    public static TrajectoryMaker createBounce00()
    {
        return createTrajectory(bounce00, GLOBAL_SCALE, 0, 0, false); //Math.toRadians(-42.3)
    }


    public static TrajectoryMaker createBounce01()

    {
        return createTrajectory(bounce01, GLOBAL_SCALE, 0, 3 * Math.PI / 2, false);
    }
    
    public static TrajectoryMaker createBounce10()
    {
        return createTrajectory(bounce10, GLOBAL_SCALE, 3 * Math.PI / 2, 3 * Math.PI / 2, true); //Math.toRadians(60.85)
    }
    
    public static TrajectoryMaker createBounce11()
    {
        return createTrajectory(bounce11, GLOBAL_SCALE, 3 * Math.PI / 2, Math.PI / 2, true);
    }

    public static TrajectoryMaker createBounce20()
    {
        return createTrajectory(bounce20, GLOBAL_SCALE, Math.PI / 2, Math.PI / 2, false); //Math.toRadians(45)
    }

    public static TrajectoryMaker createBounce21()
    {
        return createTrajectory(bounce21, GLOBAL_SCALE, Math.PI / 2, 3 * Math.PI / 2, false);
    }
    
    public static TrajectoryMaker createBounce30()
    {
        return createTrajectory(bounce30, GLOBAL_SCALE, 3 * Math.PI / 2, 3 * Math.PI / 2, true);
    }

    public static TrajectoryMaker createBounce31()
    {
        return createTrajectory(bounce31, GLOBAL_SCALE, 3 * Math.PI / 2, Math.PI, true);
    }

    //go forward, turn 90 degrees right
    public static TrajectoryMaker createLeg1()
    {
        return createTrajectory(leg1, GLOBAL_SCALE, 0, Math.PI / 2, false);
    }

    //go forward, turn 90 degrees right
    public static TrajectoryMaker createLeg2()
    {
        return createTrajectory(leg2, GLOBAL_SCALE, Math.PI / 2, Math.PI, false);
    }

    public static TrajectoryMaker createLeg3()
    {
        return createTrajectory(leg3, GLOBAL_SCALE, Math.PI, 3*Math.PI / 2, false);
    }

    public static TrajectoryMaker createLeg4()
    {
        return createTrajectory(leg4, GLOBAL_SCALE, 3*Math.PI / 2, 0, false);
    }


    public static TrajectoryMaker createBarrel0()
    {
        return createTrajectory(barrel0, GLOBAL_SCALE, 0, 0, false);
    }

    public static TrajectoryMaker createBarrel()
    {
        return createTrajectory(barrel, GLOBAL_SCALE, 0, 3 * Math.PI / 2, false);
    }

    // Need better documentation here.  What are these doing?  Are the units in meters?
    public static TrajectoryMaker createfrontScorePath()
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(3, 0, new Rotation2d(0)), true);
    }
    public static TrajectoryMaker createTrenchToTargetDiagonal()
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(0.25, -0.25, new Rotation2d(0)), true);//8,-1.6
    }
    public static TrajectoryMaker createTargetToFrontOfTrench()
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(-0.25, 0.25, new Rotation2d(0)), true);// -4.2,1.6
    }
    public static TrajectoryMaker createTrenchForward() //Assuming facing forward
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(0.25, 0, new Rotation2d(0)), true);//2
    }

    public static TrajectoryMaker createForwardPath() //For Testing Purposes
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(.75, 0, new Rotation2d(0)), true);
    }
    public static TrajectoryMaker createZagPath() //For Testing Purposes
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(1, 0.5, new Rotation2d(0)), true);
    }
    public static TrajectoryMaker createForwardPath2() //For Testing Purposes
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(2, 0, new Rotation2d(0)), true);
    }
    public static TrajectoryMaker createToPortPath() //For Testing Purposes
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(0.25, 0, new Rotation2d(0)), true);//3
    }

    public static TrajectoryMaker createPortToFrontofTrench()
    {
        ArrayList<Translation2d> points = new ArrayList<Translation2d>();
        points.add(new Translation2d(-1.5, 2.3));
        points.add(new Translation2d(-3, 2.3));
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(-5.3, 2.3, new Rotation2d(180)), points );
    }

    public static TrajectoryMaker createTestMultiPath()
    {
        ArrayList<Translation2d> points = new ArrayList<Translation2d>();
        points.add(new Translation2d(1,-0.5));
        points.add(new Translation2d(1,0.5));
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(2, 0, new Rotation2d(Math.PI)), points );
    }
    public static TrajectoryMaker createMoveDownTrench()
    {
        return new TrajectoryMaker(new Pose2d(0,0,new Rotation2d(0)), new Pose2d(3, 0, new Rotation2d(0)), true);
    }

    public static TrajectoryMaker createMoveToPort()
    {
        ArrayList<Translation2d> points = new ArrayList<Translation2d>();
        points.add(new Translation2d(1.524, 2.286));
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(3.048, 4.572, new Rotation2d(0)), points );
    }


    public static TrajectoryMaker createAutonomousPath1() // Init Line (Start on Left) to Port Test
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(2, 0, new Rotation2d(0)), true);
    }

    public static TrajectoryMaker createAutonomousPath2() //Test 2 Electric Bugaloo
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(0, -1, new Rotation2d(0)), true);
    }

    public static TrajectoryMaker createSidePath() //Test Path
    {
        //return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(2.77, 0.2, new Rotation2d(0)), true);
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(0, -2, new Rotation2d(0)), true);
    }

    public static TrajectoryMaker createDiagonalPath() //Test Path
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(1, 1, new Rotation2d(0)), true);
    }
    public static TrajectoryMaker createBackwardPath() //Test Path
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(2.77, 0, new Rotation2d(0)), true);
    }
    public static TrajectoryMaker createForwardPath3() //Test Path
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(2, 0, new Rotation2d(0)), true);
    }
    public static TrajectoryMaker createForwardPath4() //Test Path
    {
        return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(-4.75, 0, new Rotation2d(0)), true);
    }

	public static TrajectoryMaker test2MetersAndBack() {
		return null;
	}
}
