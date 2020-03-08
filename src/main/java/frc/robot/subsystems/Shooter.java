/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants;
import frc.robot.Gains;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

  private final WPI_TalonSRX shooter_t = new WPI_TalonSRX(Constants.shooter_topPort);
  private final WPI_TalonSRX shooter_d = new WPI_TalonSRX(Constants.shooter_downPort);
  private final WPI_TalonSRX bowel_t = new WPI_TalonSRX(Constants.bowel_tPort);
  private final WPI_TalonSRX bowel_d = new WPI_TalonSRX(Constants.bowel_dPort);
  private final WPI_TalonSRX chassisIntake = new WPI_TalonSRX(Constants.chassisIntakePort);
  private final Ultrasonic ultra = new Ultrasonic(Constants.ultra_pingChannel, Constants.ultra_echoChannel);
  
  

  /**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
	private final int kSlotIdx = 0;

	/**
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	private final int kPIDLoopIdx = 0;

	/**
	 * Set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
  private final int kTimeoutMs = 30;

	/**
	 * PID Gains may have to be adjusted based on the responsiveness of control loop.
     * kF: 1023 represents output value to Talon at 100%, 7200 represents Velocity units at 100% output
     * 0.025, 0.001, 20,
	 * 	                                    			  kP   kI   kD   kF          Iz    PeakOut */
  private final Gains kGains_Velocit = new Gains( 0.02, 0, 0, 1023.0/28800.0,  300,  1.00);
  private boolean shooterOn = false;
  private boolean pos1 = false;
  private boolean pos2 = false;
  private boolean pos3 = false;
  private boolean pos4 = false;

  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    /* Factory Default all hardware to prevent unexpected behaviour */
    shooter_d.configFactoryDefault();
    shooter_t.configFactoryDefault();

		/* Config sensor used for Primary PID [Velocity] */
    shooter_d.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                                            kPIDLoopIdx, 
                                            kTimeoutMs);
    shooter_t.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                                            kPIDLoopIdx, 
                                            kTimeoutMs);                                        
    /**
		 * Phase sensor accordingly. 
     * Positive Sensor Reading should match Green (blinking) Leds on Talon
    */
    shooter_d.setSensorPhase(true);
    shooter_t.setSensorPhase(false);
    shooter_d.setInverted(true);
    shooter_t.setInverted(true);

		/* Config the peak and nominal outputs */
    shooter_d.configNominalOutputForward(0, kTimeoutMs);
    shooter_t.configNominalOutputForward(0, kTimeoutMs);

    shooter_d.configNominalOutputReverse(0, kTimeoutMs);
    shooter_t.configNominalOutputReverse(0, kTimeoutMs);

    shooter_d.configPeakOutputForward(1, kTimeoutMs);
    shooter_t.configPeakOutputForward(1, kTimeoutMs);

    shooter_d.configPeakOutputReverse(-1, kTimeoutMs);
    shooter_t.configPeakOutputReverse(-1, kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */
		shooter_d.config_kF(kPIDLoopIdx, kGains_Velocit.kF, kTimeoutMs);
		shooter_d.config_kP(kPIDLoopIdx, kGains_Velocit.kP, kTimeoutMs);
		shooter_d.config_kI(kPIDLoopIdx, kGains_Velocit.kI, kTimeoutMs);
    shooter_d.config_kD(kPIDLoopIdx, kGains_Velocit.kD, kTimeoutMs);
    shooter_t.config_kF(kPIDLoopIdx, kGains_Velocit.kF, kTimeoutMs);
		shooter_t.config_kP(kPIDLoopIdx, kGains_Velocit.kP, kTimeoutMs);
		shooter_t.config_kI(kPIDLoopIdx, kGains_Velocit.kI, kTimeoutMs);
    shooter_t.config_kD(kPIDLoopIdx, kGains_Velocit.kD, kTimeoutMs);
    
    bowel_d.setInverted(false);
    bowel_t.setInverted(true);
    chassisIntake.setInverted(false);
    ultra.setAutomaticMode(true);

  }

  public void setPercentaheOutput(double topSpeed, double downSpeed, boolean button){
    if(button){
      shooter_d.set(ControlMode.PercentOutput, downSpeed);
      shooter_t.set(ControlMode.PercentOutput, topSpeed);
    }else{
      shooter_d.set(ControlMode.PercentOutput, 0);
      shooter_t.set(ControlMode.PercentOutput, 0);
    }
    
  }
  public void showEncoderPos(){

    int shooterdown = shooter_d.getSelectedSensorPosition();
    int shooterup = shooter_t.getSelectedSensorPosition();

    SmartDashboard.putNumber("shooter down", ToDeg(shooterdown));
    SmartDashboard.putNumber("shooter top", ToDeg(shooterup));

  }

  public void velocityClosedLoop(boolean button, double speed_d, double speed_t){

    double targetVelocity_UnitsPer100ms_d = speed_d * 4096 / 600;
    double targetVelocity_UnitsPer100ms_t = speed_t * 4096 / 600;
    if(button){
      shooter_d.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms_d);
      shooter_t.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms_t);
      shooterOn = true;
    
    }else{
      shooter_d.set(0);
      shooter_t.set(0);
      shooterOn = false;
    
    }
  }
  public void velocityClosedLoop_read(){
    double motorOutput_d = shooter_d.getMotorOutputPercent();
    double motorOutput_t = shooter_t.getMotorOutputPercent();
    double velocity_d = shooter_d.getSelectedSensorVelocity();
    double velocity_t = shooter_t.getSelectedSensorVelocity();

    /* Append more signals to print when in speed mode. */
    
    SmartDashboard.putNumber("RPM_d", velocity_d * 600 / 4096);
    SmartDashboard.putNumber("RPM_t", velocity_t * 600 / 4096);
    SmartDashboard.putNumber("output Percent_d", motorOutput_d);
    SmartDashboard.putNumber("output Percent_t", motorOutput_t);

  }

  public void shooterChooser(boolean pos1B, boolean pos2B, boolean pos3B, boolean pos4B, boolean button){

    if(pos1B){
      pos1 = true;
      pos2 = false;
      pos3 = false;
      pos4 = false;
    }else if(pos2B){
      pos1 = false;
      pos2 = true;
      pos3 = false;
      pos4 = false;
    }else if(pos3B){
      pos1 = false;
      pos2 = false;
      pos3 = true;
      pos4 = false;
    }else if(pos4B){
      pos1 = false;
      pos2 = false;
      pos3 = false;
      pos4 = true;
    }else{

    }

    if(pos1){
      velocityClosedLoop(button,  1850, 2400);
    }else if(pos2){
      velocityClosedLoop(button,  1000, 1000);
    }else if (pos3){
      velocityClosedLoop(button,  2630, 1030);
    }else if (pos4){
      velocityClosedLoop(button,  850, 3550);
    }else{
      setPercentaheOutput(0, 0, false);
    }
    SmartDashboard.putBoolean("pos right", pos1);
    SmartDashboard.putBoolean("pos left", pos2);
    SmartDashboard.putBoolean("pos down", pos3);
    SmartDashboard.putBoolean("pos up", pos4);
  }

  public void autoShoot(){

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

  public void BowelByJoystick(double down, double top, double topSpeed, double downSpeed, boolean move){
    if(move){
      if(down > 0.3 || down < -0.3){
        bowel_d.set(downSpeed * down);
      }else{
        bowel_d.set(0);
      } 
      
      if(top > 0.3 || top < -0.3){
        bowel_t.set(topSpeed * top);
      }else{
        bowel_t.set(0);
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
  
  public void ultrasonicControl( double down, double top, double topSpeed, double downSpeed, boolean move,  double distance){
    if(move){
      
      if(ultra.getRangeMM() < distance && !shooterOn){
        bowel_d.set(0.3);
      }else if(down > 0.3 || down < -0.3){
        bowel_d.set(downSpeed * down);
      }else{
        bowel_d.set(0);
      } 
      
      if(top > 0.3 || top < -0.3){
        bowel_t.set(topSpeed * top);
      }else{
        bowel_t.set(0);
      }
    }else{
      bowel_t.set(0);
      bowel_d.set(0);
    }
    
  }

  public void showUltrasonic(double distance){
    SmartDashboard.putNumber("ultra", ultra.getRangeMM());
    SmartDashboard.putBoolean("topLimit", ultra.getRangeMM() > distance || shooterOn);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
