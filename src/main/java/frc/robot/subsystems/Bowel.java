/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Bowel extends SubsystemBase {

  private final WPI_TalonSRX bowel_t = new WPI_TalonSRX(Constants.bowel_tPort);
  private final WPI_TalonSRX bowel_d = new WPI_TalonSRX(Constants.bowel_dPort);
  private final Ultrasonic ultra = new Ultrasonic(Constants.ultra_pingChannel, Constants.ultra_echoChannel);
  
  /**
   * Creates a new Bowel.
   */
  public Bowel() {
    bowel_d.setInverted(true);

  }

  public void BowelByJoystick(boolean down, boolean top, double topSpeed, double downSpeed){
    if(down){
      bowel_d.set(downSpeed);
    }else if(top){
      bowel_t.set(topSpeed);
    }else{
      bowel_t.set(0);
      bowel_d.set(0);
    }
  }

  public void ultrasonicControl(double distance, double speed){
    if(ultra.getRangeMM() > distance){
      bowel_t.set(speed);
    }else{
      bowel_t.stopMotor();
    }
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
