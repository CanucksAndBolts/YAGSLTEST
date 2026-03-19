// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class agitator extends SubsystemBase {
  private SparkMax advancer = new SparkMax(Constants.advancerID, MotorType.kBrushless);
  private SparkMax agitator = new SparkMax(Constants.agitatorID, MotorType.kBrushless);
  private SparkMax backupShooter = new SparkMax(Constants.ShooterID, MotorType.kBrushless);

  public void advance(double dutyCycle) {advancer.set(dutyCycle);}

  public void agitate (double dutyCycle) {agitator.set(dutyCycle);}

  public void shoot (double dutyCycle) {backupShooter.set(dutyCycle);}

  /** Creates a new agitator. */
  public agitator() {
    advancer.configure(new SparkMaxConfig(), com.revrobotics.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    agitator.configure(new SparkMaxConfig(), com.revrobotics.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    backupShooter.configure(new SparkMaxConfig(), com.revrobotics.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
