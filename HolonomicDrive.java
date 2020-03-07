package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name = "Mecanum Drive", group = "TeleOp")
class MecanumDrive extends LinearOpMode
{
    //motoare
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;

    //Variabile
    float leftStickY;
    float leftStickX;
    float rightStickX;
    float FL_power_raw;
    float FR_power_raw;
    float RL_power_raw;
    float RR_power_raw;
    float FL_power;
    float FR_power;
    float RL_power;
    float RR_power;

    @Override
    public void runOpMode()
    {
        //Init
        runInitSequence();

        waitForStart();

        while (opModeIsActive())
        {
            controls();
        }
    }

    public void runInitSequence()
    {

        frontLeft = hardwareMap.dcMotor.get("FL");
        frontRight = hardwareMap.dcMotor.get("FR");
        backLeft = hardwareMap.dcMotor.get("BL");
        backRight = hardwareMap.dcMotor.get("BR");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void controls()
    {
        holonomicFormula();
        setDriveChainPower();
    }

    public void getJoyValues()
    {
        leftStickY = gamepad1.left_stick_y;
        leftStickX = gamepad1.left_stick_x;
        rightStickX = gamepad1.right_stick_x;
    }

    public void holonomicFormula()
    {
        getJoyValues();

        FL_power_raw = leftStickY - leftStickX - rightStickX;
        FR_power_raw = leftStickY + leftStickX + rightStickX;
        RL_power_raw = leftStickY + leftStickX - rightStickX;
        RR_power_raw = leftStickY - leftStickX + rightStickX;

        FL_power = Range.clip(FL_power_raw, -1, 1);
        FR_power = Range.clip(FR_power_raw, -1, 1);
        RL_power = Range.clip(RL_power_raw,-1 ,1);
        RR_power = Range.clip(RR_power_raw, -1, 1);
    }

    public void setDriveChainPower()
    {
        frontLeft.setPower(FL_power);
        frontRight.setPower(FR_power);
        backLeft.setPower(RL_power);
        backRight.setPower(RR_power);
    }
}