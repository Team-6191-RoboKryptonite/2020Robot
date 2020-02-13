/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    //MortorControllor Port
    public final static int wheel_r_1Port = 0;
    public final static int wheel_r_2Port = 1;
    public final static int wheel_l_1Port = 2;
    public final static int wheel_l_2Port = 3;
    public final static int shooter_upPort = 4;
    public final static int shooter_downPort = 5;
    public final static int bowel_1Port = 6;
    public final static int bowel_2Port = 7;
    public final static int liftPort = 8;
    public final static int handPort = 9;

    //Pneumatics Port
    public final static int liftBrakerPort = 1; 
    public final static int switchOnHandPort = 2;

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

}