package org.firstinspires.ftc.teamcode.Subsystem;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.teamcode.Util.IDs.INTAKE1_MOTOR_ID;
import static org.firstinspires.ftc.teamcode.Util.IDs.INTAKE2_MOTOR_ID;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake{
    private DcMotorEx Intake1;
    private DcMotorEx Intake2;
    public void init(HardwareMap hardwareMap){
        this.Intake1 = hardwareMap.get(DcMotorEx.class, INTAKE1_MOTOR_ID );
        this.Intake2 = hardwareMap.get(DcMotorEx.class, INTAKE2_MOTOR_ID );

        this.Intake1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.Intake2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.Intake1.setDirection(FORWARD);
        this.Intake2.setDirection(REVERSE);

        this.Intake1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.Intake2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}