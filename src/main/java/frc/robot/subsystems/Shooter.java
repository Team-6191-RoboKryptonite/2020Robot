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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

  private final WPI_TalonSRX shooter_t = new WPI_TalonSRX(Constants.shooter_topPort);
  private final WPI_TalonSRX shooter_d = new WPI_TalonSRX(Constants.shooter_downPort);

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
  private final Gains kGains_Velocit = new Gains( 0.001, 0, 0, 1023.0/28800.0,  300,  1.00);


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

    /**
		 * Phase sensor accordingly. 
     * Positive Sensor Reading should match Green (blinking) Leds on Talon
    */
    shooter_d.setSensorPhase(false);
    shooter_t.setSensorPhase(true);

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

  public void velocityClosedLoop(boolean button){
    double targetVelocity_UnitsPer100ms = 1000.0 * 4096 / 600;
    if(button){
      /* 500 RPM in either direction */
		shooter_d.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
    }
	  
		
    
  }
  public void velocityClosedLoop_read(){
    double motorOutput = shooter_d.getMotorOutputPercent();
    double velocity = shooter_d.getSelectedSensorVelocity();
    double targetVelocity_UnitsPer100ms = 500.0 * 4096 / 600;

			/* Append more signals to print when in speed mode. */
			SmartDashboard.putNumber("err:", shooter_d.getClosedLoopError(0));
      SmartDashboard.putNumber("trg:", targetVelocity_UnitsPer100ms);
      SmartDashboard.putNumber("RPM", velocity * 600 /4096);
      SmartDashboard.putNumber("vel", velocity);
      SmartDashboard.putNumber("output Percent", motorOutput);

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
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
