/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.hal.DigitalGlitchFilterJNI;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Baseket extends SubsystemBase {
  private final WPI_VictorSPX  intake = new WPI_VictorSPX(Constants.intakePort);
  private final WPI_VictorSPX  intakeArm = new WPI_VictorSPX(Constants.intakeArmPort);
  private final DigitalInput limitSwitch_1 = new DigitalInput(Constants.limitSwitch_1Channel);
  private final DigitalInput limitSwitch_2 = new DigitalInput(Constants.limitSwitch_2Channel);
  private final DigitalInput limitSwitch_3 = new DigitalInput(Constants.limitSwitch_3Channel);
  /**
   * Creates a new Baseket.
   */
  public Baseket(){
  }

  public void moveWithStick(boolean up, boolean down, double speed){
    if(up){
      intakeArm.set(speed);
      }else if(down){
      intakeArm.set(speed * -1);
      }else{
      intakeArm.stopMotor();
    }

  }

  public void intakeWithButton(boolean out, boolean in, double speed){
      if(out){
        intake.set(speed);
        }else if(in){
        intake.set(speed * -1);
        }else{
        intake.stopMotor();
      }
  }

  public void ArmPosWithLimitSwitch(double axis, boolean move, boolean button,  double speed){
    int pos = 0;


    if(button){
      pos ++;
    }
    SmartDashboard.putNumber("Position", pos % 3);
    if(move){

      if(axis > 0.3){

        if(limitSwitch_3.get()){
          intakeArm.stopMotor();
        }else if(pos % 3 == 0){
          intakeArmMove(limitSwitch_1.get(), speed * axis);
        }else if(pos % 3 == 1){
          intakeArmMove(limitSwitch_2.get(), speed * axis);
        }else if(pos % 3 == 2){
          intakeArmMove(limitSwitch_3.get(), speed * axis);
        }else{
          intakeArm.set(speed * axis);
        }
  
      }else if(axis < -0.3){
  
        if(limitSwitch_1.get()){
          intakeArm.stopMotor();
        }else if(pos % 3 == 0){
          intakeArmMove(limitSwitch_3.get(), speed * axis);
        }else if(pos % 3 == 1){
          intakeArmMove(limitSwitch_2.get(), speed * axis);
        }else if(pos % 3 == 2){
          intakeArmMove(limitSwitch_1.get(), speed * axis);
        }else{
          intakeArm.set(speed * axis);
        }
  
      }else{
        intakeArm.stopMotor();
      }

    }else{
        intakeArm.stopMotor();
    }  
  }

  public void intakeArmMove(boolean move, double speed){
    if(move){
      intakeArm.set(speed);
    }else{
      intakeArm.stopMotor();
    }
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
