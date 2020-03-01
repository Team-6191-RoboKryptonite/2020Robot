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
  int pos = 0;
  /**
   * Creates a new Baseket.
   */
  public Baseket(){
  }

  public void ArmmoveWithStick(boolean up, boolean down, double speed, double downSpeed){
    if(up){
      intakeArm.set(speed);
      }else if(down){
      intakeArm.set(downSpeed * -1);
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

  public void ArmPosWithLimitSwitch(double axis, boolean move, boolean button,  double speed, double downSpeed, boolean lowPos){
    


    if(button){
      pos ++;
    }
    SmartDashboard.putNumber("IntakePosition", pos % 2);
    if(move){

      if(axis < -0.3){

        if(!limitSwitch_3.get()){
          intakeArm.stopMotor();
        }else if(pos % 2 == 0){
          intakeArmMove(limitSwitch_2.get(), speed);
        }else if(pos % 2 == 1){
          intakeArmMove(limitSwitch_3.get(), speed);
        }else{
          intakeArm.set(speed);
        }
  
      }else if(axis > 0.3){
  
        if(!limitSwitch_1.get()){
          intakeArm.stopMotor();
        }else if(pos % 2 == 0){
          intakeArmMove(limitSwitch_2.get(), downSpeed * -1);
        }else if(pos % 2 == 1){
          intakeArmMove(limitSwitch_3.get(), downSpeed * -1);
        }else{
          intakeArm.set(downSpeed * -1);
        }
  
      }else{
        intakeArm.stopMotor();
      }

    }else if(lowPos){
      intakeArmMove(limitSwitch_1.get(), -0.1);
    }else{
        intakeArm.stopMotor();
    }  
  }
  //Function use in Arm Pos
  public void intakeArmMove(boolean move, double speed){
    if(move){
      intakeArm.set(speed);
    }else{
      intakeArm.stopMotor();
    }
  }


  public void showLimSwi(){
    SmartDashboard.putBoolean("limitSwitch1", limitSwitch_1.get());
    SmartDashboard.putBoolean("limitSwitch2", limitSwitch_2.get());
    SmartDashboard.putBoolean("limitSwitch3", limitSwitch_3.get());

  }

  public void LowPos(boolean button){
    
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
