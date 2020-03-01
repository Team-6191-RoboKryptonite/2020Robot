/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Baseket;

public class BaseketWithJoystick extends CommandBase {
  private final Baseket m_subsystem;
  private final Joystick m_stick;

  /**
   * Creates a new BaseketWithButton.
   */
  public BaseketWithJoystick(Baseket subsystem, Joystick joystick) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem = subsystem;
    m_stick = joystick;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // m_subsystem.ArmmoveWithStick(m_stick.getRawAxis(Constants.axis_r_y) > 0.3 && m_stick.getRawButton(Constants.button_LB),
    //                              m_stick.getRawAxis(Constants.axis_r_y) < -0.3 && m_stick.getRawButton(Constants.button_LB), 0.3);
    m_subsystem.intakeWithButton(m_stick.getRawButton(Constants.button_LB) && m_stick.getRawAxis(Constants.axis_l_y) < -0.3
                                 , m_stick.getRawButton(Constants.button_LB) && m_stick.getRawAxis(Constants.axis_l_y) > 0.3, 0.5);
    m_subsystem.ArmPosWithLimitSwitch(m_stick.getRawAxis(Constants.axis_r_y), m_stick.getRawButton(Constants.button_LB)
                                 , m_stick.getRawButtonPressed(Constants.button_A), 0.3, 0.1, m_stick.getRawButton(Constants.button_X) && !m_stick.getRawButton(Constants.button_LB));
    m_subsystem.showLimSwi();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
