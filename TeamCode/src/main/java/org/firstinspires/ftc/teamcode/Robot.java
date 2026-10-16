package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystem.Turret;
import org.firstinspires.ftc.teamcode.Util.ControlScheme;

@TeleOp
public class Robot extends OpMode {

    private final ControlScheme controlScheme = new ControlScheme();
    private final Drivetrain drivetrain = new Drivetrain();

    private final Turret turret = new Turret();

    @Override
    public void init() {
        controlScheme.initDriver(gamepad1);
        this.drivetrain.init(hardwareMap);
        this.turret.init(hardwareMap);
    }

    @Override
    public void loop() {
        this.drivetrain.mechanumDrive(
            controlScheme.DRIVE_STRAFE.get(),
            controlScheme.DRIVE_FB.get(),
            controlScheme.DRIVE_ROTATE.get(),
            controlScheme.DRIVE_SLOW_MODE.get()
        );

        this.turret.shootTurret(controlScheme.SHOOT_TURRET.get());
        this.turret.setTargetPos(
            (Math.atan2(
                controlScheme.SPIN_TURRET_Y.get(), controlScheme.SPIN_TURRET_X.get()
            ) / 360) // Div by 360 to turn degrees into revolutions
        );
    }
}