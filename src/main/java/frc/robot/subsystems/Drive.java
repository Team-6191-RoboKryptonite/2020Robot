/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
  private final WPI_TalonSRX wheel_l_1 = new WPI_TalonSRX(Constants.wheel_l_1Port);
  private final WPI_TalonSRX wheel_l_2 = new WPI_TalonSRX(Constants.wheel_l_2Port);
  private final WPI_TalonSRX wheel_r_1 = new WPI_TalonSRX(Constants.wheel_r_1Port);
  private final WPI_TalonSRX wheel_r_2 = new WPI_TalonSRX(Constants.wheel_r_2Port);
  private final DifferentialDrive chassis = new DifferentialDrive(wheel_l_1, wheel_r_1);
  
  
  /**
   * Creates a new Drive.
   */
  public Drive() {

  }

  public void Init(){
    wheel_l_1.setInverted(false);
    wheel_l_2.setInverted(false);
    wheel_r_1.setInverted(false);
    wheel_r_2.setInverted(false);
    wheel_l_2.follow(wheel_l_1);
    wheel_r_2.follow(wheel_r_1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
