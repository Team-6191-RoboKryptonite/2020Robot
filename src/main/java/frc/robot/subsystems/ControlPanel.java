/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ControlPanel extends SubsystemBase {

  private final WPI_TalonSRX hand = new WPI_TalonSRX(Constants.handPort);
  private final Solenoid handOpen = new Solenoid(Constants.switchOnHandPort);

  //Declaration for ColorSensorV3
  
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(Constants.i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();

  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  //NetworkTable table;
  int colorNow;
  int rotation;
  boolean colorChange;
  String colorString;
  ColorMatchResult match;
  /**
   * Creates a new ControlPanel.
   */
  public ControlPanel() {

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget); 
    SmartDashboard.putNumber("Color(R1 Y2 G3 B4)", colorNow);
    colorNow = 0;
    rotation = 0;
    colorChange = false;
    SmartDashboard.putNumber("Color(R1 Y2 G3 B4)", colorNow);
    SmartDashboard.putBoolean("Change", colorChange);
    SmartDashboard.putNumber("Rotation", rotation);

  }

  public void LazySusanWithJoystick(double Speed){
    if(Speed > 0.3 || Speed < -0.3){
      hand.set(0.5);
    }
  }

  public void MovePosition(boolean move1,boolean move2, double speed){

    
    double colorint = SmartDashboard.getNumber("Color(R1 Y2 G3 B4)", colorNow);
     
    if(colorint == 1 && colorString == "Red"){
      hand.set(0);
    }else if(colorint == 2 && colorString == "Yellow" && match.confidence > 0.95){
      hand.set(0);
    }else if(colorint == 3 && colorString == "Green" && match.confidence > 0.92){
      hand.set(0);
    }else if(colorint == 4 && colorString == "Blue" && match.confidence > 0.90){
      hand.set(0);
      }else{
      hand.set(speed);
    }
  }

  public void MoveRotation(int speed){
  if(rotation >= 8){
      hand.stopMotor();
  }else{
    hand.set(speed);
  }

  colorChange = colorString == "Blue" && match.confidence > 0.90;

  if(colorChange && colorString!="Blue"){
    rotation++;
    colorChange = false;
  }else{
    hand.stopMotor();
    rotation = 0;
  }

  SmartDashboard.putBoolean("Change", colorChange);
  SmartDashboard.putNumber("Rotation", rotation);


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
