package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.Util.ControlScheme;
import org.firstinspires.ftc.teamcode.Vision.Vision;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

@TeleOp
public class Robot extends OpMode {

    private final ControlScheme controlScheme = new ControlScheme();
    private final Drivetrain drivetrain = new Drivetrain();

    public Vision vision;

    @Override
    public void init() {
        controlScheme.initDriver(gamepad1);
        this.drivetrain.init(hardwareMap);
        vision = new Vision(hardwareMap);
    }

    @Override
    public void loop() {
        this.drivetrain.mechanumDrive(
            controlScheme.DRIVE_STRAFE.get(),
            controlScheme.DRIVE_FB.get(),
            controlScheme.DRIVE_ROTATE.get(),
            controlScheme.DRIVE_SLOW_MODE.get()
        );

        AprilTagSingleDetection det = vision.getDetection();
        if (det != null) {
            telemetry.addData("Vision Detection: ", det.id);
        }
    }
}