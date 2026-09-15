package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.robotcore.hardware.Gamepad;
import java.util.function.Supplier;

public class ControlScheme {
    public static Supplier<Float> DRIVE_STRAFE;
    public static Supplier<Float> DRIVE_FB;
    public static Supplier<Float> DRIVE_ROTATE;
    public static Supplier<Boolean> DRIVE_SLOW_MODE;

    public void initDriver(Gamepad gamepad1) {
        DRIVE_STRAFE = () -> gamepad1.left_stick_x;
        DRIVE_FB = () -> gamepad1.left_stick_y;
        DRIVE_ROTATE = () -> gamepad1.right_stick_x;
        DRIVE_SLOW_MODE = () -> gamepad1.b;
    }

}
