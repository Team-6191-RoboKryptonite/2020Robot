/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  private final WPI_TalonSRX lift = new WPI_TalonSRX(Constants.climb1Port);
  private final WPI_TalonSRX lift2 = new WPI_TalonSRX(Constants.climb2Port);
  private final Solenoid liftkey = new Solenoid(Constants.liftBrakerPort);
  int liftkeyCount = 0;
  /**
   * Creates a new Lift.
   */
  public Climb() {
    lift.setInverted(true);
    lift2.setInverted(true);
    lift2.follow(lift);
    liftkeyCount = 0;
  }

  public void liftMove(boolean winch, double speed){
    if(winch){
      lift.set(speed);
    }else{
      lift.stopMotor();
    }
  }

  public void liftReleaze(boolean button){
    if(button){
      liftkeyCount++;
    }
    if(liftkeyCount% 2 == 0){
      liftkey.set(false);
    }else{
      liftkey.set(true);
    }
    SmartDashboard.putNumber("liftkey", liftkeyCount);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
