package org.firstinspires.ftc.pinkcode.subsystems;

import com.qualcomm.hardware.broadcom.BroadcomColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LED;

import org.firstinspires.ftc.pinkcode.util.Encoder;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

/**
 * Hardware handles finding and creating all the physical IO for the robot.
 * This class should never configure IO by itself that should be left for subsystems/opmodes to deal with.
 */
public class Hardware {
    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;

    public DcMotorEx backLeft;
    public DcMotorEx backRight;

    public Encoder leftEncoder;
    public Encoder rightEncoder;
    public Encoder centerEncoder;

    public DcMotorEx testMotor;

    public WebcamName webcamFront;


    public Hardware(HardwareMap map) {
        // Commented out all the devices to allow for easy tensorflow model testing.
//        frontLeft = map.get(DcMotorEx.class, "front-left");
//        frontRight = map.get(DcMotorEx.class, "front-right");
//        backRight = map.get(DcMotorEx.class, "back-right");
//        backLeft = map.get(DcMotorEx.class, "back-left");

//        testMotor = map.get(DcMotorEx.class, "test-motor");
//
//        leftEncoder = new Encoder(map.get(DcMotorEx.class, "left-encoder"));
//        rightEncoder = new Encoder(map.get(DcMotorEx.class, "right-encoder"));
//        centerEncoder = new Encoder(map.get(DcMotorEx.class, "center-encoder"));

        webcamFront = map.get(WebcamName.class, "webcam");
    }
}
