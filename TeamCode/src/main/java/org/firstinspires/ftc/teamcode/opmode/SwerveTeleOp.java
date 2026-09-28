package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.controllers.swerve.SwerveDrive;

@TeleOp(name="Swwrve TeleOp")
public class SwerveTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        // Create your swerve drive using the repo's constructor
        SwerveDrive drive = new SwerveDrive(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            // Gamepad input
            double x = gamepad1.left_stick_x;     // strafe
            double y = -gamepad1.left_stick_y;    // forward/back
            double turn = gamepad1.right_stick_x; // rotation

            // Build a PoseVelocity2d for SwerveDrive
            PoseVelocity2d command = new PoseVelocity2d(
                    new Vector2d(x, y),
                    turn
            );

            // Send to swerve controller
            drive.setDrivePowers(command);

            // Telemetry
            telemetry.addData("Drive X", x);
            telemetry.addData("Drive Y", y);
            telemetry.addData("Turn", turn);

            telemetry.update();
        }
    }
}
