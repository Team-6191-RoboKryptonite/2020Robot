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
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Chassis;
import java.util.function.DoubleSupplier;

public class DriveWithJoystick extends CommandBase {
  /**
   * Creates a new Chassis.
   */
  private final Chassis m_subsystem;
  private final Joystick m_joystick;
  /**
   * @param subsystem The subsystem used by this command.
   */
  public DriveWithJoystick(Chassis subsystem, Joystick joystick) {
    m_subsystem = subsystem;
    m_joystick = joystick;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_subsystem.ZeroEnc();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_subsystem.driveChassis(m_joystick.getRawAxis(Constants.trigger_r), 
                             m_joystick.getRawAxis(Constants.trigger_l), 
                             m_joystick.getRawAxis(Constants.axis_l_x),
                             0.6);
    //m_subsystem.showEncoderPos();
    //m_subsystem.showGyro();
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
