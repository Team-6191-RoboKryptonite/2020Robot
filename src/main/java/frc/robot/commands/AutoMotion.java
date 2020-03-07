/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;

public class AutoMotion extends CommandBase {
  /**
   * Creates a new AutoMotion.
   */
  private final Chassis m_subsystem;
  private boolean finish; 

  public AutoMotion(Chassis subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem = subsystem;
    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_subsystem.ZeroEnc();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
    m_subsystem.showEncoderPos();

    if(m_subsystem.EncCheck(1)){
      m_subsystem.AutoTank(0.5, -0.5);
      finish = false;

    }else if(m_subsystem.EncCheck(3)){
      m_subsystem.AutoTank(0.5, 0.5);
      finish = false;
    }else{
      finish = true;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finish;
  }
}
