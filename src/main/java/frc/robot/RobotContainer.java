/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Timer;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  private final Joystick m_stick_Drive = new Joystick(Constants.joystick_Drive);
  private final Joystick m_stick_Control = new Joystick(Constants.joystick_Control);
  


  // The robot's subsystems and commands are defined here...
  //private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Bowel m_bowel = new Bowel();
  private final ControlPanel m_controlPanel = new ControlPanel();
  private final Chassis m_drive = new Chassis();
  //private final Climb m_lift = new Climb();
  private final Shooter m_shooter = new Shooter();
  //private final Baseket m_baseket = new Baseket();
  

  //private final AutonomousCommand m_autoCommand = new AutonomousCommand(m_shooter, m_drive);
  private final BowelWithJoystick m_bowelWithJoystick = new BowelWithJoystick(m_bowel, m_stick_Control);
  private final DriveWithJoystick m_driveWithJoystick = new DriveWithJoystick(m_drive, m_stick_Drive);
  //private final GeneratorSwitch m_generatorSwitch = new GeneratorSwitch(m_lift, m_stick_Control);
  private final PanelContorllerWithJoystick m_panelContorllerWithJoystick = new PanelContorllerWithJoystick(m_controlPanel, m_stick_Control);
  private final ShooterWithJoystick m_shooterWithJoystick = new ShooterWithJoystick(m_shooter, m_stick_Control);
  //private final BaseketWithJoystick m_baseketWithButton = new BaseketWithJoystick(m_baseket, m_stick_Control);
  private final AutonomousGroup m_autonomousGroup = new AutonomousGroup(m_shooter, m_drive, m_bowel);

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_drive.setDefaultCommand(m_driveWithJoystick);
    m_bowel.setDefaultCommand(m_bowelWithJoystick);
    m_shooter.setDefaultCommand(m_shooterWithJoystick);
    m_controlPanel.setDefaultCommand(m_panelContorllerWithJoystick);
    //m_lift.setDefaultCommand(m_generatorSwitch);
    //m_baseket.setDefaultCommand(m_baseketWithButton);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autonomousGroup;
  }
}
