// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Pounds;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Seconds;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import yams.telemetry.SmartMotorControllerTelemetry;
import yams.gearing.GearBox;
import yams.gearing.MechanismGearing;
import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.velocity.FlyWheel;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.local.SparkWrapper;

public class Shooter extends SubsystemBase {



  private SmartMotorControllerConfig smcConfig = new SmartMotorControllerConfig(this)
  .withControlMode(ControlMode.CLOSED_LOOP)
  .withClosedLoopController(50,0,0,DegreesPerSecond.of(90),DegreesPerSecondPerSecond.of(45))
  .withTelemetry("ShooterMotor", TelemetryVerbosity.HIGH)
  .withGearing(new MechanismGearing(GearBox.fromReductionStages(3)))
  .withMotorInverted(false)
  .withIdleMode(MotorMode.COAST)
  .withStatorCurrentLimit(Amps.of(40))
  .withClosedLoopRampRate(Seconds.of(.25))
  .withOpenLoopRampRate(Seconds.of(.25));

  private SparkMax spark = new SparkMax(Constants.ShooterID, MotorType.kBrushless);
  

  private SmartMotorController sparkSMC = new SparkWrapper(spark,DCMotor.getNEO(1), smcConfig);

  private final FlyWheelConfig shooterConfig = new FlyWheelConfig(sparkSMC)
  .withDiameter(Inches.of(Constants.flywheelDiameter))
  .withMass(Pounds.of(Constants.flywheelWeight))
  .withUpperSoftLimit(RPM.of(Constants.rpmLimit))
  .withTelemetry("Shooter", TelemetryVerbosity.HIGH);

  private FlyWheel shooter = new FlyWheel(shooterConfig);
  /** Creates a new Shooter. */

  public AngularVelocity getVelocity() {return shooter.getSpeed();}

  public Command setVelocity(AngularVelocity speed) {return shooter.setSpeed(speed);}
  
  public Command set(double dutyCycle) {return shooter.set(dutyCycle);}


  public Shooter() {
  spark.configure(new SparkMaxConfig(), com.revrobotics.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    shooter.updateTelemetry();
    // This method will be called once per scheduler run
  }

  public void simulationPeriodic(){
    shooter.simIterate();
  }
}
