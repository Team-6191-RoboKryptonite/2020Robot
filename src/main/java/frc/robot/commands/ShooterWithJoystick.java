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
import frc.robot.subsystems.Shooter;

public class ShooterWithJoystick extends CommandBase {

  private final Shooter m_subsystem;
  private final Joystick m_stick;
  /**
   * Creates a new ShooterWithJoystick.
   */
  public ShooterWithJoystick(Shooter subsystem, Joystick stick){
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
    m_subsystem.setPercentaheOutput(0.5, 0.7, m_stick.getRawButton(Constants.button_A));
    m_subsystem.showEncoderPos();
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
