/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Bowel;
import frc.robot.subsystems.Shooter;

public class AutoShoot extends CommandBase {
  /**
   * Creates a new AutoShooter.
   */

  private final Shooter m_subsystem_s;
  private final Bowel m_subsystem_b;
  public final Timer m_timer = new Timer();
  public boolean finish = false;

  public AutoShoot(Shooter subsystem_s, Bowel subsystem_b) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem_s = subsystem_s;
    m_subsystem_b = subsystem_b;
    addRequirements(m_subsystem_s, m_subsystem_b);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.reset();
    m_timer.start();
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("Timer", m_timer.get());
    if(m_timer.get() < 3){
      m_subsystem_s.velocityClosedLoop(true, 850, 3550);
      finish = false;
    }else if(m_timer.get() < 6){
      m_subsystem_b.BowelByJoystick(-1, -1, 0.5, 0.5, true);
      finish = false;
    }else{
      m_subsystem_s.setPercentaheOutput(0, 0, true);
      m_subsystem_b.BowelByJoystick(1, 1, 0, 0, true);
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
