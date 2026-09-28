package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Angle Sensor Test")
public class AngleSensorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        SimpleAngleSensor fl = new SimpleAngleSensor(hardwareMap, "frontLeftAngle", 0.0);
        SimpleAngleSensor fr = new SimpleAngleSensor(hardwareMap, "frontRightAngle", 0.0);
        SimpleAngleSensor bl = new SimpleAngleSensor(hardwareMap, "backLeftAngle", 0.0);
        SimpleAngleSensor br = new SimpleAngleSensor(hardwareMap, "backRightAngle", 0.0);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("FL Voltage", fl.getVoltage());
            telemetry.addData("FL Radian", fl.getRadian());

            telemetry.addData("FR Voltage", fr.getVoltage());
            telemetry.addData("FR Radian", fr.getRadian());

            telemetry.addData("BL Voltage", bl.getVoltage());
            telemetry.addData("BL Radian", bl.getRadian());

            telemetry.addData("BR Voltage", br.getVoltage());
            telemetry.addData("BR Radian", br.getRadian());

            telemetry.update();
        }
    }
}
