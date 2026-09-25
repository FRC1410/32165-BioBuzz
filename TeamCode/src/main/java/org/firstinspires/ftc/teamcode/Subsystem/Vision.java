package org.firstinspires.ftc.teamcode.Subsystem;

import org.firstinspires.ftc.vision.VisionPortal;

public class Vision {
    private double tagToCenter = 14.267; // In inches
    private double centerToPivot = 0;
    private VisionPortal vision_portal;



    public Vision() {

    }

    private double[] findDistX
        (
            double aRobot, // Pitch up from robot
            double aTag, // Pitch from tag
            double dist, // Dist cam to tag
            double yaw, // Yaw
            boolean sideTop
        )
    {
        double a = aRobot + aTag - 90; // This is the angle of the triangle opposite to ours top angle
        double tagHeight = Math.sin(aRobot) * dist;
        double robotToCentre =
                ((tagToCenter * Math.sin(a) * tagHeight) / (Math.sin(a) * tagHeight))
                        + Math.cos(aRobot) * dist;
        double x = 84;
        double y = 72;

        // Scoot away from center in y-axis
        if (sideTop) {
            y += robotToCentre;
        }
        else{
            y -= robotToCentre;
        }

        x += Math.cos(yaw/2) * ((robotToCentre * Math.cos(yaw)) / (Math.sin(90-(yaw/2))));
        y += Math.cos(yaw/2) * ((robotToCentre * Math.sin(yaw)) / (Math.sin(90-(yaw/2))));

        return new double[]{x, y};
    }
}
