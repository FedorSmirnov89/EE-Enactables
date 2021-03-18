package at.uibk.dps.ee.enactables.modules;

import org.opt4j.core.config.annotations.File;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.start.Constant;
import at.uibk.dps.ee.enactables.decorators.DecoratorTimingLogFactory;
import at.uibk.dps.ee.guice.modules.FunctionModule;
import ch.qos.logback.classic.util.ContextInitializer;

/**
 * The {@link FunctionsModule} defines the dynamic aspects of the configuration
 * of functions.
 * 
 * @author Fedor Smirnov
 */
public class FunctionsModule extends FunctionModule {

  @Order(1)
  @Info("If checked, the execution time of functions will be logged.")
  public boolean logTime;

  @Order(2)
  @Info("The path to the logback configuration file.")
  @File
  @Required(property = "logTime")
  public String pathToLoggingConfiguration = "./logging/config/logback.xml";

  @Order(3)
  @Constant(value = "prio", namespace = DecoratorTimingLogFactory.class)
  @Required(property = "logTime")
  @Info("Decorators with lower prio are applied later.")
  public int timeLoggingDecoratorPriority = 10;

  @Override
  protected void config() {

    if (logTime) {
      // configure the location of the logback config file
      System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, pathToLoggingConfiguration);
      // add the function wrapper
      addFunctionDecoratorFactory(DecoratorTimingLogFactory.class);
    }

  }

  public String getPathToLoggingConfiguration() {
    return pathToLoggingConfiguration;
  }

  public void setPathToLoggingConfiguration(String pathToLoggingConfiguration) {
    this.pathToLoggingConfiguration = pathToLoggingConfiguration;
  }

  public boolean isLogTime() {
    return logTime;
  }

  public void setLogTime(boolean logTime) {
    this.logTime = logTime;
  }

  public int getTimeLoggingDecoratorPriority() {
    return timeLoggingDecoratorPriority;
  }

  public void setTimeLoggingDecoratorPriority(int timeLoggingDecoratorPriority) {
    this.timeLoggingDecoratorPriority = timeLoggingDecoratorPriority;
  }
}