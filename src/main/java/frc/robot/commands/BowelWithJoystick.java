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
import frc.robot.subsystems.Bowel;

public class BowelWithJoystick extends CommandBase {
  private final Bowel m_subsystem;
  private final Joystick m_stick;
  /**
   * Creates a new BowelWithJoystick.
   */
  public BowelWithJoystick(Bowel subsystem, Joystick joystick) {
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
    m_subsystem.BowelByJoystick(m_stick.getRawAxis(Constants.axis_l_y), m_stick.getRawAxis(Constants.axis_r_y)
                                , 0.5, 0.5, !m_stick.getRawButton(Constants.button_LB));
    m_subsystem.moveChassisIntake(m_stick.getRawButton(Constants.button_B) && !m_stick.getRawButton(Constants.button_LB)
                                  ,m_stick.getRawButton(Constants.button_X) && !m_stick.getRawButton(Constants.button_LB)
                                  , 0.7);
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
