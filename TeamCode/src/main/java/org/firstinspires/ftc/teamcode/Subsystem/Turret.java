package org.firstinspires.ftc.teamcode.Subsystem;

import static org.firstinspires.ftc.teamcode.Util.Constants.THROUGH_BORE_TICKS_PER_REVOLUTION;
import static org.firstinspires.ftc.teamcode.Util.Constants.Turret.*;
import static org.firstinspires.ftc.teamcode.Util.IDs.Turret.*;
import static org.firstinspires.ftc.teamcode.Util.Tuning.Turret.*;
import static org.firstinspires.ftc.teamcode.Util.RobotStates.Turret.*;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Util.PIDController;

public class Turret {
    private DcMotorEx spinMotor;
    private DcMotorEx shootMotor;
    private Servo hoodServo;
    private PIDController controller = new PIDController(TURRET_SPIN_P, TURRET_SPIN_I, TURRET_SPIN_D);
    private double targetPos;

    public void init(HardwareMap hardwareMap) {
        spinMotor = hardwareMap.get(DcMotorEx.class, TURRET_SPIN_MOTOR_ID);
        shootMotor = hardwareMap.get(DcMotorEx.class, TURRET_SHOOT_MOTOR_ID);

        spinMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shootMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        spinMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shootMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        spinMotor.setDirection(SPIN_MOTOR_DIRECTION);
        shootMotor.setDirection(SHOOT_MOTOR_DIRECTION);

        spinMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shootMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        hoodServo = hardwareMap.get(Servo.class, HOOD_SERVO_ID);

        hoodServo.setDirection(HOOD_SERVO_DIRECTION);

        targetPos = 0;
    }

    /**
     * Set the speed of the turret spin
     * @param speed speed of turret in turret revolutions per second
     */
    public void spinTurret(double speed) {
        spinMotor.setVelocity(speed * THROUGH_BORE_TICKS_PER_REVOLUTION);
    }

    /**
     * Set the speed to the shooting motor
     * @param speed speed (FULL, HALF, or STOP)
     */
    public void shootTurret(TurretSpeeds speed) {
        switch (speed){
            case FULL -> shootMotor.setVelocity(TURRET_MAX_SPEED);
            case HALF -> shootMotor.setVelocity(TURRET_MAX_SPEED / 2);
            case STOP -> shootMotor.setVelocity(0);
        }

    }

    /**
     * Sets the target position for the turret
     * @param _targetPos the target position, in turret revolutions
     */
    public void setTargetPos(double _targetPos) {
        targetPos = _targetPos * TICKS_PER_TURRET_REVOLUTION;
    }

    /**
     * Gets the target position
     * @return target position in turret revolutions
     */
    public double getTargetPos() {
        return targetPos / TICKS_PER_TURRET_REVOLUTION;
    }

    /**
     * Gets the position of the spinning motor
     * @return position of spinning motor in turret revolutions
     */
    public double getSpinPos() {
        return ((double) spinMotor.getCurrentPosition()) / TICKS_PER_TURRET_REVOLUTION;
    }

    /**
     * Have the spin motor go to the set target position
     */
    public void goToTargetPos() {
        controller.setIntegral(Math.min(controller.getIntegral(), TICKS_PER_TURRET_REVOLUTION * 3));
        double calc = controller.calculate(targetPos, spinMotor.getCurrentPosition());

        this.spinMotor.setVelocity(calc);
    }

    /**
     * Sets the position for the servo to go to
     * @param state state for the hood to go to (UP, HALFWAY, or LOW)
     */
    public void setHoodServoPos(HoodStates state) {
        switch (state) {
            case UP -> hoodServo.setPosition(1);
            case HALFWAY -> hoodServo.setPosition(0.5);
            case LOW -> hoodServo.setPosition(0.1);
        }
    }
}
