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
import frc.robot.subsystems.Climb;

public class GeneratorSwitch extends CommandBase {

  private final Climb m_subsystem;
  private final Joystick m_joystick;
  /**
   * Creates a new GeneratorSwitch.
   */
  public GeneratorSwitch(Climb subsystem, Joystick joystick) {

    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem = subsystem;
    m_joystick = joystick;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //m_subsystem.liftMove(m_joystick.getRawButton(Constants.button_LB) && m_joystick.getRawAxis(Constants.trigger_l) > 0.3, 0.3);
    m_subsystem.liftReleaze(m_joystick.getRawButtonPressed(Constants.button_RB));
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
