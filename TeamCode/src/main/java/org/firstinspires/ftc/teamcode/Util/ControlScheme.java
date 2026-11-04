package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.robotcore.hardware.Gamepad;
import java.util.function.Supplier;

public class ControlScheme {
    public Supplier<Float> DRIVE_STRAFE;
    public Supplier<Float> DRIVE_FB;
    public Supplier<Float> DRIVE_ROTATE;
    public Supplier<Boolean> DRIVE_SLOW_MODE;

    public Supplier<Double> INTAKE_SPEED;

    public void initDriver(Gamepad gamepad1) {
        DRIVE_STRAFE = () -> gamepad1.left_stick_x;
        DRIVE_FB = () -> gamepad1.left_stick_y;
        DRIVE_ROTATE = () -> gamepad1.right_stick_x;
        DRIVE_SLOW_MODE = () -> gamepad1.b;

        INTAKE_SPEED = () -> (double) gamepad1.right_trigger;
    }

}
