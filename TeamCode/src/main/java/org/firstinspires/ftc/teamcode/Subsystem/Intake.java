package org.firstinspires.ftc.teamcode.Subsystem;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.teamcode.Util.IDs.INTAKE1_MOTOR_ID;
import static org.firstinspires.ftc.teamcode.Util.IDs.INTAKE2_MOTOR_ID;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake{
    private DcMotorEx intake1;
    private DcMotorEx intake2;
    public void init(HardwareMap hardwareMap){
        this.intake1 = hardwareMap.get(DcMotorEx.class, INTAKE1_MOTOR_ID );
        this.intake2 = hardwareMap.get(DcMotorEx.class, INTAKE2_MOTOR_ID );

        this.intake1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.intake2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.intake1.setDirection(FORWARD);
        this.intake2.setDirection(REVERSE);

        this.intake1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.intake2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runIntake(double motorSpeeds){
        this.intake1.setVelocity(motorSpeeds);
        this.intake2.setVelocity(motorSpeeds);
    }
}