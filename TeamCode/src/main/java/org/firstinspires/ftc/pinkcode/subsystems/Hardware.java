package org.firstinspires.ftc.pinkcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class Hardware {
    public DcMotor testMotor;
    
    public DcMotor frontLeft;
    public DcMotor frontRight;

    public DcMotor backLeft;
    public DcMotor backRight;

    public DcMotorEx leftEncoder;
    public DcMotorEx rightEncoder;
    public DcMotorEx centerEncoder;

    public WebcamName webcamFront;

    public Hardware(HardwareMap map) {
        frontLeft = map.dcMotor.get("fl");
        frontRight = map.dcMotor.get("fr");
        backRight = map.dcMotor.get("br");
        backLeft = map.dcMotor.get("bl");

        leftEncoder = map.get(DcMotorEx.class, "left-encoder");
        rightEncoder = map.get(DcMotorEx.class, "right-encoder");
        centerEncoder = map.get(DcMotorEx.class, "center-encoder");

        webcamFront = map.get(WebcamName.class, "webcam");

        configureMotors();
    }

    private void configureMotors() {
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
