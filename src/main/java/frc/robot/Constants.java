/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    //MortorControllor Port

    //the port 1~10 is for Talon SRX
    //the port 11~16 is for Victor SPX
    public final static int wheel_r_1Port = 6;
    public final static int wheel_r_2Port = 12;
    public final static int wheel_l_1Port = 7;
    public final static int wheel_l_2Port = 13;
    public final static int shooter_topPort = 4;
    public final static int shooter_downPort = 1;
    public final static int bowel_tPort = 9;
    public final static int bowel_dPort = 2;
    public final static int climb1Port = 10;
    public final static int climb2Port = 5;
    public final static int handPort = 11;
    public final static int chassisIntakePort = 8;
    public final static int intakePort = 15;
    public final static int intakeArmPort = 16;

    //Sensor Channel
    public final static I2C.Port i2cPort = I2C.Port.kOnboard;
    public final static int ultra_pingChannel = 5;
    public final static int ultra_echoChannel = 6;
    //limitSwitchNumber From Low To High
    public final static int limitSwitch_1Channel = 8;
    public final static int limitSwitch_2Channel = 7;
    public final static int limitSwitch_3Channel = 9;
    public final static int limitSwitch_4Channel = 6;

    //Pneumatics Port
    public final static int liftBrakerPort = 5; 
    public final static int switchOnHandPort = 4;

    //Joystick Number
    public final static int joystick_Drive = 0;
    public final static int joystick_Control = 1;

    //Joystick Button
    public final static int axis_l_x = 0;
    public final static int axis_l_y = 1;
    public final static int axis_r_x = 4;
    public final static int axis_r_y = 5;
    public final static int trigger_l = 2;
    public final static int trigger_r = 3;
    public final static int button_A = 1;
    public final static int button_B = 2;
    public final static int button_X = 3;
    public final static int button_Y = 4;
    public final static int button_LB = 5;
    public final static int button_RB = 6;
    public final static int button_Axis_l = 9;
    public final static int button_Axis_r = 10;
    public final static int POV_up = 0;
    public final static int POV_upRight = 45;
    public final static int POV_Right = 90;
    public final static int POV_downRight = 135;
    public final static int POV_down = 180;
    public final static int POV_downleft = 225;
    public final static int POV_left = 270;
    public final static int POV_upLeft = 315;



}