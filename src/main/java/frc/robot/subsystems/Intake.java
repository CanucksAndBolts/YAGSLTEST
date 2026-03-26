// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */

  private final SparkMax intake = new SparkMax(Constants.intakeID, MotorType.kBrushless);
  private final PneumaticHub pch = new PneumaticHub(Constants.pchID);
  private final DoubleSolenoid left = pch.makeDoubleSolenoid(0, 1);
  

  public Intake() {
    intake.configure(new SparkMaxConfig(), com.revrobotics.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void extend() {
    left.toggle();
  }

  public void spin(double dutyCycle){intake.set(dutyCycle);}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
