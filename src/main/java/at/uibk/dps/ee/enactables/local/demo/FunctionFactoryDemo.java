package at.uibk.dps.ee.enactables.local.demo;

import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import net.sf.opendse.model.Task;
import java.util.Set;
import com.google.inject.Inject;
import at.uibk.dps.ee.enactables.FunctionFactory;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.core.function.FunctionDecoratorFactory;
import at.uibk.dps.ee.enactables.EnactmentMode;
import at.uibk.dps.ee.enactables.FactoryInputUser;
import at.uibk.dps.ee.enactables.local.ConstantsLocal.LocalCalculations;

/**
 * The {@link FunctionFactoryDemo} provides the enactment functions modeling
 * local operations.
 * 
 * @author Fedor Smirnov
 *
 */
public class FunctionFactoryDemo extends FunctionFactory<FactoryInputUser, EnactmentFunction> {

  /**
   * Injection constructor.
   * 
   * @param decoratorFactories the factories for the decorators which are used to
   *        wrap the created functions
   */
  @Inject
  public FunctionFactoryDemo(final Set<FunctionDecoratorFactory> decoratorFactories) {
    super(decoratorFactories);
  }

  @Override
  protected EnactmentFunction makeActualFunction(final FactoryInputUser input) {
    final Task task = input.getTask();
    final LocalCalculations localCalcs =
        LocalCalculations.valueOf(PropertyServiceFunctionUser.getTypeId(task));
    switch (localCalcs) {
      case Addition:
        return new Addition(LocalCalculations.Addition.name(), EnactmentMode.Local.name(),
            task.getId());
      case Subtraction:
        return new Subtraction(LocalCalculations.Subtraction.name(), EnactmentMode.Local.name(),
            task.getId());
      case SumCollection:
        return new SumCollection(LocalCalculations.SumCollection.name(), EnactmentMode.Local.name(),
            task.getId());
      case SplitArray:
        return new SplitArray(LocalCalculations.SplitArray.name(), EnactmentMode.Local.name(),
            task.getId());
      default:
        throw new IllegalArgumentException("Unknown local function " + localCalcs.name()
            + " specified for function " + task.getId());
    }
  }
}
