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
  private final WPI_TalonSRX chassisIntake = new WPI_TalonSRX(Constants.chassisIntakePort);
  private final Ultrasonic ultra = new Ultrasonic(Constants.ultra_pingChannel, Constants.ultra_echoChannel);
  
  /**
   * Creates a new Bowel.
   */
  public Bowel() {
    bowel_d.setInverted(false);
    bowel_t.setInverted(true);
    chassisIntake.setInverted(true);

  }

  public void BowelByJoystick(double down, double top, double topSpeed, double downSpeed, boolean move){
    if(move){
      if(down > 0.3 || down < -0.3){
        bowel_d.set(downSpeed * down);
      }else if(top > 0.3 || top < -0.3){
        bowel_t.set(topSpeed * top);
      }else{
        bowel_t.set(0);
        bowel_d.set(0);
      }
    }else{
      bowel_t.set(0);
      bowel_d.set(0);
    }
    
  }

  public void moveChassisIntake(boolean buttonIn, boolean buttonOut, double speed){
    if(buttonIn){
      chassisIntake.set(speed);
    }else if(buttonOut){
      chassisIntake.set(-speed);
    }else{
      chassisIntake.set(0);
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
