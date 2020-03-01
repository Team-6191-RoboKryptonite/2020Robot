/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Shooter;


public class AutonomousCommand extends CommandBase {
  /**
   * Creates a new AutonomousCommand.
   */
  private final Shooter m_subsystem_s;
  private final Chassis m_subsystem_c;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public AutonomousCommand(Shooter subsystem_s, Chassis subsystem_c) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem_s = subsystem_s;
    m_subsystem_c = subsystem_c;
    addRequirements(subsystem_s, subsystem_c);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    

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
