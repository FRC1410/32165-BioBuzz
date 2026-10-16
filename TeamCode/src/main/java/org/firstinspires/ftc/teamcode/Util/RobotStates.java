package org.firstinspires.ftc.teamcode.Util;

public class RobotStates {
    public interface Drivetrain {
        enum DrivetrainSpeeds {
            FULL_SPEED,
            HALF_SPEED,
            SLOWMODE
        }
    }

    public interface Turret {
        enum TurretSpeeds {
            FULL,
            HALF,
            STOP
        }
        enum HoodStates {
            UP,
            HALFWAY,
            LOW
        }
    }
}
