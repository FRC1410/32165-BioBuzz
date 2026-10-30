package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystem.Turret;
import org.firstinspires.ftc.teamcode.Util.ControlScheme;
import org.firstinspires.ftc.teamcode.Util.RobotStates;

@TeleOp
public class Robot extends OpMode {

    private final ControlScheme controlScheme = new ControlScheme();
//    private final Drivetrain drivetrain = new Drivetrain();

    private final Turret turret = new Turret();

    @Override
    public void init() {
//        controlScheme.initDriver(gamepad1);
        controlScheme.initOperator(gamepad2);
//        this.drivetrain.init(hardwareMap);
        this.turret.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
//        this.drivetrain.mechanumDrive(
//            controlScheme.DRIVE_STRAFE.get(),
//            controlScheme.DRIVE_FB.get(),
//            controlScheme.DRIVE_ROTATE.get(),
//            controlScheme.DRIVE_SLOW_MODE.get()
//        );

        telemetry.addData("Loop begin", 0);

        this.turret.setTargetPos(
            (Math.atan2(
                controlScheme.SPIN_TURRET_Y.get(), controlScheme.SPIN_TURRET_X.get()
            ) / (Math.pow(360, 2))) // Div by 360 to turn degrees into revolutions
        );

        this.turret.goToTargetPos();

        if (controlScheme.SHOOT_TURRET_FULL.get()) {
            this.turret.shootTurret(RobotStates.Turret.TurretSpeeds.FULL);
        } else if (controlScheme.SHOOT_TURRET_HALF.get()) {
            this.turret.shootTurret(RobotStates.Turret.TurretSpeeds.HALF);
        } else if (controlScheme.SHOOT_TURRET_STOP.get()) {
            this.turret.shootTurret(RobotStates.Turret.TurretSpeeds.STOP);
        }

        if (controlScheme.TURRET_HOOD_HIGH.get()) {
            this.turret.setHoodServoPos(RobotStates.Turret.HoodStates.HIGH);
        } else if (controlScheme.TURRET_HOOD_HALF.get()) {
            this.turret.setHoodServoPos(RobotStates.Turret.HoodStates.HALF);
        } else if (controlScheme.TURRET_HOOD_LOW.get()) {
            this.turret.setHoodServoPos(RobotStates.Turret.HoodStates.LOW);
        }

        telemetry.addData("Loop end", 0);

    }
}