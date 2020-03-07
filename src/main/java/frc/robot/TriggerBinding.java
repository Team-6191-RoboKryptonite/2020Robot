/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class TriggerBinding extends Trigger {
    Joystick m_joystick;
    int m_axis;

    public TriggerBinding(Joystick joystick, int axis){
        m_joystick = joystick;
        m_axis = axis;

    }
    @Override
    public boolean get() {
      // This returns whether the trigger is active
      return m_joystick.getRawAxis(m_axis) > 0.2;

    }
  }