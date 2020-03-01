/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ControlPanel;

public class PanelContorllerWithJoystick extends CommandBase {

  private ControlPanel m_subsystem; 
  private Joystick m_stick;
  /**
   * Creates a new PanelContorller.
   */
  public PanelContorllerWithJoystick(ControlPanel subsystem, Joystick stick) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem = subsystem;
    m_stick = stick;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_subsystem.LazySusanWithJoystick(0.3, m_stick.getRawButton(Constants.button_LB));
    m_subsystem.HandOut(m_stick.getRawButton(Constants. button_Y));
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
