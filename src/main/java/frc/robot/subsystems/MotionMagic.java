/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//This code is for Testing

package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotionMagic extends SubsystemBase {
  /**
   * Creates a new MotionMagic.
   */
  private final WPI_TalonSRX wheel_r_1 = new WPI_TalonSRX(Constants.wheel_r_1Port);
  private final WPI_TalonSRX wheel_r_2 = new WPI_TalonSRX(Constants.wheel_r_2Port);
  private final WPI_TalonSRX wheel_l_1 = new WPI_TalonSRX(Constants.wheel_l_1Port);
  private final WPI_TalonSRX wheel_l_2 = new WPI_TalonSRX(Constants.wheel_r_2Port);
  //private final DifferentialDrive m_Drive = new DifferentialDrive(wheel_r_1, wheel_l_1);

  private final int kSlotIdx = 0;
  /**
  * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
  * now we just want the primary one.
  */
  private final int kPIDLoopIdx = 0;

  /**
  * set to zero to skip waiting for confirmation, set to nonzero to wait and
  * report to DS if action fails.
  */
  private  int kTimeoutMs = 30;
  private  double kP = 0.0;
  private  double kI = 0.0;
  private  double kD = 0.0;
  private  double kF = 0.0;
  private  int kIzone = 0;
  private  double kPeakOutput = 0;


  /**
   * Initialize for Using Motion Magic
   */
  public void Init(){

    wheel_l_1.setInverted(false);
    wheel_l_2.setInverted(false);
    wheel_r_1.setInverted(false);
    wheel_r_2.setInverted(false);
    wheel_r_2.follow(wheel_r_1);
    wheel_l_2.follow(wheel_l_1);
		wheel_l_1.configFactoryDefault();

		/* Configure Sensor Source for Pirmary PID */
		wheel_l_1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx,
				kTimeoutMs);
		/* set deadband to super small 0.001 (0.1 %).
			The default deadband is 0.04 (4 %) */
    wheel_l_1.configNeutralDeadband(0.001, kTimeoutMs);

		wheel_l_1.setSensorPhase(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		wheel_l_1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
		wheel_l_1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);

		/* Set the peak and nominal outputs */
		wheel_l_1.configNominalOutputForward(0, kTimeoutMs);
		wheel_l_1.configNominalOutputReverse(0, kTimeoutMs);
		wheel_l_1.configPeakOutputForward(1, kTimeoutMs);
		wheel_l_1.configPeakOutputReverse(-1, kTimeoutMs);

		/* Set Motion Magic gains in slot0 - see documentation */
		wheel_l_1.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
		wheel_l_1.config_kF(kSlotIdx, kF, kTimeoutMs);
		wheel_l_1.config_kP(kSlotIdx, kP, kTimeoutMs);
		wheel_l_1.config_kI(kSlotIdx, kI, kTimeoutMs);
		wheel_l_1.config_kD(kSlotIdx, kD, kTimeoutMs);

		/* Set acceleration and vcruise velocity - see documentation */
		wheel_l_1.configMotionCruiseVelocity(15000, kTimeoutMs);
		wheel_l_1.configMotionAcceleration(6000, kTimeoutMs);

		/* Zero the sensor once on robot boot up */
		wheel_l_1.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs);
  }
  
  /**
   * Set the Gais of Motion Magic
   * @param _kP
   * @param _kI
   * @param _kD
   * @param _kF
   * @param _kIzone
   * @param _kPeakOutput
   */
  public void Gains(double _kP, double _kI, double _kD, double _kF, int _kIzone, double _kPeakOutput){

  /**
	* Which PID slot to pull gains from. Starting 2018, you can choose from
	* 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	* configuration.
	*/
		kP = _kP;
		kI = _kI;
		kD = _kD;
		kF = _kF;
		kIzone = _kIzone;
    kPeakOutput = _kPeakOutput;
    
  }
  
  /**
   * Drive foward by Motion Magic
   * @param distance Rotation of motor
   */
  public void MotionMagicFoward(int distance){
    wheel_l_1.set(ControlMode.MotionMagic, distance * 4096);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
