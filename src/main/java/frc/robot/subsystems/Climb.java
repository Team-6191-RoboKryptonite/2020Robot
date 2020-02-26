/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  private final WPI_TalonSRX lift = new WPI_TalonSRX(Constants.climb1Port);
  private final WPI_TalonSRX lift2 = new WPI_TalonSRX(Constants.climb2Port);
  private final Solenoid liftBraker = new Solenoid(Constants.liftBrakerPort);
  /**
   * Creates a new Lift.
   */
  public Climb() {

  }

  public void liftMove(double winch,boolean braker, double mult){
    liftBraker.set(braker);
    lift.set(winch * mult);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
