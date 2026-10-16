package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.robotcore.hardware.Gamepad;
import java.util.function.Supplier;

public class ControlScheme {
    public Supplier<Float> DRIVE_STRAFE;
    public Supplier<Float> DRIVE_FB;
    public Supplier<Float> DRIVE_ROTATE;
    public Supplier<Boolean> DRIVE_SLOW_MODE;

    public Supplier<Double> SHOOT_TURRET;
    public Supplier<Double> SPIN_TURRET_X;
    public Supplier<Double> SPIN_TURRET_Y;

    public Supplier<Double> TURRET_HOOD_ANGLE;

    public void initDriver(Gamepad gamepad1) {
        DRIVE_STRAFE = () -> gamepad1.left_stick_x;
        DRIVE_FB = () -> gamepad1.left_stick_y;
        DRIVE_ROTATE = () -> gamepad1.right_stick_x;
        DRIVE_SLOW_MODE = () -> gamepad1.b;
    }

    public void initOperator(Gamepad gamepad2) {
        SHOOT_TURRET  = () -> (double) gamepad2.right_trigger;
        SPIN_TURRET_X = () -> (double) gamepad2.right_stick_x;
        SPIN_TURRET_Y = () -> (double) gamepad2.right_stick_y;
        TURRET_HOOD_ANGLE = () -> (double) gamepad2.left_trigger;
    }

}
