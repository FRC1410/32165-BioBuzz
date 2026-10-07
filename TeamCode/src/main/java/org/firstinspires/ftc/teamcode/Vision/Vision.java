package org.firstinspires.ftc.teamcode.Vision;

import static org.firstinspires.ftc.teamcode.Vision.VisionConstants.*;

import com.qualcomm.robotcore.hardware.HardwareMap;

import android.util.Size;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.ArrayList;

public class Vision {
    private final VisionPortal vision_portal;
    private final AprilTagProcessor aprilTag;
    private ArrayList<AprilTagSingleDetection> detections;

    /**
     * Vision subclass using the trigonometric math to find position based on april tag data
     * @param hardwareMap from the Robot.java class
     */
    public Vision(HardwareMap hardwareMap) {
        try {
            VisionConstants.initConstants();
            // this defines all the wanted keys for us
            AprilTagLibrary.Builder tagLib = new AprilTagLibrary.Builder();
            for (int tagID : new int[]{0}){
                tagLib.addTag(tagID, "TagID"+tagID, TAG_WIDTH, DistanceUnit.INCH);
            }
            // This is the camera data, and where we get said data.
            this.aprilTag = new AprilTagProcessor.Builder()
                    .setTagLibrary(tagLib.build())
                    .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                    .setDrawAxes(true)
                    .setDrawCubeProjection(true)
                    .setDrawTagOutline(true)
                    .setDrawTagID(true)
                    .setOutputUnits(DistanceUnit.INCH, AngleUnit.RADIANS) // in radians to work better with java trig stuff
                    .build();
            // This is the connection to the physical camera, including the id
            VisionPortal.Builder vision_portal_builder = new VisionPortal.Builder();
            vision_portal_builder.setCamera(hardwareMap.get(WebcamName.class,"WEBCAM_ID")); // TODO: put in IDs class
            vision_portal_builder.setCameraResolution(new Size(640, 480));
            vision_portal_builder.addProcessor(aprilTag);

            vision_portal = vision_portal_builder.build();
            update();
        } catch (Exception e) {
            throw new RuntimeException("Camera init failed: " + e.getMessage());
        }
    }

    /**
     * Returns the calculated pose of the bot based on angle data.
     * (0, 0) is the bottom left of the field
     * @param aRobot Pitch up from robot
     * @param aTag Pitch from tag
     * @param dist Dist cam to tag
     * @param yaw Yaw to tag
     * @param sideTop Whether the tag is on the top of the field side
     * @return double[] (x, y)
     */
    private double[] findPoseFromMeasurement
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
                ((armLength * Math.sin(a) * tagHeight) / (Math.sin(a) * tagHeight))
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

        double xChange = Math.cos(yaw/2) * ((robotToCentre * Math.cos(yaw)) / (Math.sin(90-(yaw/2))));
        double yChange = Math.cos(yaw/2) * ((robotToCentre * Math.sin(yaw)) / (Math.sin(90-(yaw/2))));
        if (sideTop) {
            xChange *= -1;
            yChange *= -1;
        }
        x += xChange;
        y += yChange;

        return new double[]{x, y};
    }

    /**
     * Returns the pose of the robot based on a tag input
     * (0, 0) is the bottom left of the field
     * @param detection AprilTagSingleDetection to calculate position from
     * @return double[] (x, y)
     */
    public double[] getPoseFromTag(AprilTagSingleDetection detection) {
        return findPoseFromMeasurement(
                cameraPitch,
                detection.ftcPose.pitch,
                detection.ftcPose.y,
                detection.ftcPose.yaw,
                idPivotMap.get(detection.id).isOnBlueHive()
        );
    }

    /**
     * Updates camera readings
     * @return boolean whether there are new detections
     */
    public boolean update() {
        boolean hasNewDetections = !(aprilTag.getFreshDetections() == null);
        detections.clear();
        for (AprilTagDetection detection : aprilTag.getDetections()) {
            if (detection instanceof AprilTagSingleDetection) {
                detections.add((AprilTagSingleDetection) detection);
            }
        }
        return hasNewDetections;
    }

    /**
     * Gets the pose in inches
     * (0, 0) is the bottom left of the field
     * @return double[] (x, y)
     */
    public double[] getPose() {
        if (update()) {
            return getPoseFromTag(detections.get(0));
        } else {
            return new double[]{-1, -1};
        }
    }
}
