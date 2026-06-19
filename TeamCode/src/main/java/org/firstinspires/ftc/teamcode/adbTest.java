package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "ADB Mecanum")
public class adbTest extends LinearOpMode{

    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor liftMotor;
    private double frontLeftPower;
    private double backLeftPower;
    private double frontRightPower;
    private double backRightPower;



    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        long lastPressed = System.currentTimeMillis();

        waitForStart();
        while (opModeIsActive()) {
            double x   = gamepad1.left_stick_x;
            double y   = gamepad1.left_stick_y;
            double rot = gamepad1.right_stick_x;

            frontLeftPower  = y - x + rot;
            frontRightPower = y + x - rot;
            backLeftPower   = y + x + rot;
            backRightPower  = y - x - rot;

            double maxPower = Math.max(frontLeftPower,Math.max(frontRightPower,Math.max(backLeftPower,backRightPower)));

            if (maxPower >= 1.0d){
                frontLeftPower  /= maxPower;
                frontRightPower /= maxPower;
                backLeftPower   /= maxPower;
                backRightPower  /= maxPower;
            }
            double mult = 0.75;
            backLeft.setPower(backLeftPower*mult);
            backRight.setPower(backRightPower*mult);

            frontLeft.setPower(frontLeftPower*mult);
            frontRight.setPower(frontRightPower*mult);



            if (gamepad1.dpad_up){
                lastPressed = System.currentTimeMillis();
                liftMotor.setPower(1);

            }
            else if (gamepad1.dpad_down){
                lastPressed = System.currentTimeMillis();
                liftMotor.setPower(-1);

            }
            else if (System.currentTimeMillis() - lastPressed > 200){
                liftMotor.setPower(0);
            }

        }
    }
}
