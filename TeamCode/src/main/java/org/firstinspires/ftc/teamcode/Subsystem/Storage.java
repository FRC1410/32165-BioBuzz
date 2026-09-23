package org.firstinspires.ftc.teamcode.Subsystem;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static org.firstinspires.ftc.teamcode.Util.IDs.STORAGE_MOTOR_ID;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Storage {
    private DcMotorEx storage1;

    public void init(HardwareMap hardwareMap) {
        this.storage1 = hardwareMap.get(DcMotorEx.class, STORAGE_MOTOR_ID);

        this.storage1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.storage1.setDirection(FORWARD);

        this.storage1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runStorage(double motorSpeeds) {
        this.storage1.setVelocity(motorSpeeds);
    }
}