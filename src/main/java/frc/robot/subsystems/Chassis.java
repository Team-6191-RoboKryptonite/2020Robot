/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.Constants;
import frc.robot.Gains;
import frc.robot.RobotContainer;
import frc.robot.commands.DriveWithJoystick;

public class Chassis extends SubsystemBase {
  private final WPI_TalonSRX wheel_l_1 = new WPI_TalonSRX(Constants.wheel_l_1Port);
  private final WPI_VictorSPX wheel_l_2 = new WPI_VictorSPX(Constants.wheel_l_2Port);
  private final WPI_TalonSRX wheel_r_1 = new WPI_TalonSRX(Constants.wheel_r_1Port);
  private final WPI_VictorSPX wheel_r_2 = new WPI_VictorSPX(Constants.wheel_r_2Port);
  private final DifferentialDrive m_chassis = new DifferentialDrive(wheel_l_1, wheel_r_1);
  private final AHRS ahrs = new AHRS(SerialPort.Port.kMXP, SerialDataType.kProcessedData, (byte)50);
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
  private final  int kTimeoutMs = 30;
  private final int kIzone = 0;
  private final double kPeakOutput = 0;
  private final Gains kGains = new Gains(0.2, 0.0, 0.0, 0.35, 0, 0);

  private double errA, errC;



  
  /**
   * Creates a new Drive.
   */
  public Chassis() {
    wheel_l_1.setInverted(false);
    wheel_l_2.setInverted(false);
    wheel_r_1.setInverted(false);
    wheel_r_2.setInverted(false);
    wheel_l_2.follow(wheel_l_1);
    wheel_r_2.follow(wheel_r_1);
    wheel_l_1.setSensorPhase(false);
    wheel_r_1.setSensorPhase(true);

    wheel_r_1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,	kPIDLoopIdx, kTimeoutMs);
    wheel_l_1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx, kTimeoutMs);
    wheel_r_1.getSensorCollection().setQuadraturePosition(0, kTimeoutMs);
    wheel_l_1.getSensorCollection().setQuadraturePosition(0, kTimeoutMs);

    /* set deadband to super small 0.001 (0.1 %).
      The default deadband is 0.04 (4 %) */
    wheel_l_1.configNeutralDeadband(0.001, kTimeoutMs);
    wheel_r_1.configNeutralDeadband(0.001, kTimeoutMs);


    /* Set relevant frame periods to be at least as fast as periodic rate */
    wheel_l_1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
    wheel_l_1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);

    wheel_r_1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
    wheel_r_1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);

    /* Set the peak and nominal outputs */
    wheel_l_1.configNominalOutputForward(0, kTimeoutMs);
    wheel_l_1.configNominalOutputReverse(0, kTimeoutMs);

    wheel_r_1.configNominalOutputForward(0, kTimeoutMs);
    wheel_r_1.configNominalOutputReverse(0, kTimeoutMs);

    wheel_l_1.configPeakOutputForward(1, kTimeoutMs);
    wheel_l_1.configPeakOutputReverse(-1, kTimeoutMs);

    wheel_r_1.configPeakOutputForward(1, kTimeoutMs);
    wheel_r_1.configPeakOutputReverse(-1, kTimeoutMs);

    /* Set Motion Magic gains in slot0 - see documentation */
    wheel_l_1.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
    wheel_l_1.config_kF(kSlotIdx, kGains.kF, kTimeoutMs);
    wheel_l_1.config_kP(kSlotIdx, kGains.kP, kTimeoutMs);
    wheel_l_1.config_kI(kSlotIdx, kGains.kI, kTimeoutMs);
    wheel_l_1.config_kD(kSlotIdx, kGains.kD, kTimeoutMs);

    wheel_r_1.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
    wheel_r_1.config_kF(kSlotIdx, kGains.kF, kTimeoutMs);
    wheel_r_1.config_kP(kSlotIdx, kGains.kP, kTimeoutMs);
    wheel_r_1.config_kI(kSlotIdx, kGains.kI, kTimeoutMs);
    wheel_r_1.config_kD(kSlotIdx, kGains.kD, kTimeoutMs);
    /* Set acceleration and vcruise velocity - see documentation */
    wheel_l_1.configMotionCruiseVelocity(4200, kTimeoutMs);
    wheel_l_1.configMotionAcceleration(2000, kTimeoutMs);

    wheel_r_1.configMotionCruiseVelocity(4200, kTimeoutMs);
    wheel_r_1.configMotionAcceleration(2000, kTimeoutMs);

    /* Zero the sensor once on robot boot up */
    wheel_l_1.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs);
    wheel_r_1.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs);


    /* initalo gyro*/
    ahrs.enableLogging(true);
    }

  /**
   * Function for drive, use curvatureDrive
	 * @param SpeedF Axis to go ahead.
   * @param SpeedB Axis to go Back.
   * @param Rotation Axis to turn around.
   * @param isQuickTurn set true if you want to Quick.
   * @param mult the limit of speed.
   * @param turn the cargo and panel side inversed.
	 */
  public void driveChassis(double SpeedF, double SpeedB, double Rotation, double mult){


    if(SpeedF > 0.3 && SpeedB == 0){
      m_chassis.curvatureDrive(SpeedF * mult, Rotation * mult, false);
    }else if(SpeedF == 0 && SpeedB > 0.3){
      m_chassis.curvatureDrive(SpeedB * mult * -1 , Rotation * mult, false);
    }else if(Rotation > 0.3 || Rotation < -0.3){
      m_chassis.curvatureDrive(0, Rotation * mult, true); 
    }else{
      m_chassis.stopMotor();
    }
    SmartDashboard.putNumber("speed", SpeedF);

  }


  /**
   * 
   */
  public void showEncoderPos(){

    int rightPos = wheel_r_1.getSelectedSensorPosition();
    int leftPos = wheel_l_1.getSelectedSensorPosition();
    int err = rightPos - leftPos;

    SmartDashboard.putNumber("Right Pos", ToDeg(rightPos));
    SmartDashboard.putNumber("Left Pos", ToDeg(leftPos));
    SmartDashboard.putNumber("Error", err);
    double velocity_r = wheel_r_1.getSelectedSensorVelocity();
    double velocity_l = wheel_l_1.getSelectedSensorVelocity();

    /* Append more signals to print when in speed mode. */
    
    SmartDashboard.putNumber("RPM_r", velocity_r * 600 / 4096);
    SmartDashboard.putNumber("RPM_l", velocity_l * 600 / 4096);
    SmartDashboard.putNumber("RPM_rr", velocity_r);
    SmartDashboard.putNumber("RPM_ll", velocity_l);

  }
  public void showGyro(){
     /* Display 6-axis Processed Angle Data                                      */
    SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
    SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating()); 
    SmartDashboard.putNumber(   "IMU_TotalYaw",         ahrs.getAngle());
    SmartDashboard.putNumber(   "IMU_YawRateDPS",       ahrs.getRate());
     
  }

  public void DriveStraightByEnc(double SpeedF, double SpeedB, double Rotation, double mult){

    int rightPos = wheel_r_1.getSelectedSensorPosition();
    int leftPos = wheel_l_1.getSelectedSensorPosition();
    double err;
    if(Rotation == 0){

      err = (rightPos -leftPos) * 0.002;

      if(err > 1){
        err = 1;
      }else if(err < -1){
        err = -1;
      }else{}

      if(SpeedF > 0.3 && SpeedB == 0){
        m_chassis.curvatureDrive(SpeedF * mult, err * 0.8, false);
      }else if(SpeedF == 0 && SpeedB > 0.3){
        m_chassis.curvatureDrive(SpeedB * mult * -1 , err * 0.8, false);
      }else{}

    }else{
      if(SpeedF > 0.3 && SpeedB == 0){
        m_chassis.curvatureDrive(SpeedF * mult, Rotation * mult, false);
      }else if(SpeedF == 0 && SpeedB > 0.3){
        m_chassis.curvatureDrive(SpeedB * mult * -1 , Rotation * mult, false);
      }else if(Rotation > 0.3 || Rotation < -0.3){
        m_chassis.curvatureDrive(0, Rotation * mult, true); 
      }else{
        m_chassis.stopMotor();
      }
      ZeroEnc();

    }
  }

  /**
	 * @param units CTRE mag encoder sensor units 
	 * @return degrees rounded to tenths.
	 */
	double ToDeg(int units) {
		double deg = units * 360.0 / 4096.0;

		/* truncate to 0.1 res */
		deg *= 10;
		deg = (int) deg;
		deg /= 10;

		return deg;
  }

  

  public void ZeroEnc(){
    wheel_l_1.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs);
    wheel_r_1.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs);
  }

  
  /**
   * Drive foward by Motion Magic
   * @param distance Rotation of motor
   */
  public boolean MotionMagicFoward(int distance){
    

    wheel_l_1.set(ControlMode.MotionMagic, distance * 4096);
    wheel_r_1.set(ControlMode.MotionMagic, distance * 4096);

    if(wheel_l_1.getSelectedSensorPosition() <= distance * 4096){
      return true;
    }else{
      return false;
    }
  }

  public boolean EncCheck(int distance){
    if(wheel_l_1.getSelectedSensorPosition() <= distance * 4096){
      return true;
    }else{
      return false;
    }
  };


  public void AutoTank(double speedL, double speedR){
    m_chassis.tankDrive(speedL, speedR);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
