package org.firstinspires.ftc.teamcode.Swerve;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.RoadRunner.Drawing;
import org.firstinspires.ftc.teamcode.controllers.InstanceTelemetry;
import org.firstinspires.ftc.teamcode.controllers.swerve.SwerveDrive;
import org.firstinspires.ftc.teamcode.controllers.swerve.locate.RobotPosition;
import org.firstinspires.ftc.teamcode.controllers.swerve.wheelunit.ServoCoaxialWheel;

@TeleOp
public class SwerveProtoType extends LinearOpMode {

    // Variables to track button release state
    private boolean lastA = false;
    private boolean lastB = false;
    private boolean lastX = false;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry = InstanceTelemetry.init(telemetry);

        SwerveDrive swerveDrive = new SwerveDrive(hardwareMap);
        swerveDrive.swerveController.setAutoLockHeading(false);

        waitForStart();

        while (opModeIsActive()) {
            // Update the localizer pose estimation every loop iteration
            swerveDrive.updatePoseEstimate();

            // Drive inputs
            swerveDrive.swerveController.gamepadInput(
                    gamepad1.left_stick_x,
                    -gamepad1.left_stick_y,
                    -gamepad1.right_stick_x
            );

            // Handle button toggle checks (Release Detection)
            if (lastA && !gamepad1.a) {
                swerveDrive.swerveController.setAutoLockHeading(!swerveDrive.swerveController.getAutoLockHeading());
            }
            if (lastB && !gamepad1.b) {
                swerveDrive.swerveController.exchangeNoHeadMode();
            }
            if (lastX && !gamepad1.x) {
                swerveDrive.swerveController.resetNoHeadModeStartError();
            }

            // Save state for next loop iteration
            lastA = gamepad1.a;
            lastB = gamepad1.b;
            lastX = gamepad1.x;

            // Telemetry logging
            for (int index = 0; index < swerveDrive.swerveController.wheelUnits.length; index++) {
                telemetry.addData(index + "Heading", swerveDrive.swerveController.wheelUnits[index].getHeading());
                telemetry.addData(index + "Speed", swerveDrive.swerveController.wheelUnits[index].getSpeed());
                telemetry.addData(index + "angularVelocity", swerveDrive.swerveController.wheelUnits[index].getAngularVelocity());

                if (swerveDrive.swerveController.wheelUnits[index] instanceof ServoCoaxialWheel) {
                    ServoCoaxialWheel wheel = (ServoCoaxialWheel) swerveDrive.swerveController.wheelUnits[index];
                    telemetry.addData(index + "motorVelocity", wheel.motorVelocity);
                    telemetry.addData(index + "outputVoltage", wheel.outputVoltage);
                }
            }

            telemetry.addData("AutoLockHeading", swerveDrive.swerveController.getAutoLockHeading());
            telemetry.addData("NoHeadMode", swerveDrive.swerveController.getUseNoHeadMode());
            telemetry.addData("x,y", RobotPosition.getInstance().getData().getPosition(DistanceUnit.INCH).toString());
            telemetry.addData("heading", RobotPosition.getInstance().getData().headingRadian);
            telemetry.addData("targetHeading", swerveDrive.swerveController.getHeadingLockRadian());
            telemetry.update();

            // Render position onto FTC Dashboard canvas
            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), RobotPosition.getInstance().getData().getPose2d());
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
        }
    }
}