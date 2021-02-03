package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.utility.BlockEnactableTest.BlockEnactableMock;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections.CollectionOperation;
import net.sf.opendse.model.Task;

public class SplitEnactableTest {

	protected static class SplitEnactableMock extends CollOperEnactable {
		protected SplitEnactableMock(Set<EnactableStateListener> stateListeners, Set<String> inputKeys,
				Task functionNode, JsonObject input) {
			super(stateListeners, inputKeys, functionNode);
			this.jsonInput = input;
		}
	}
	
	
	@Test
	public void testSplit3uneven() {
		JsonObject input = new JsonObject();
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		// create the input collection
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(1));
		array.add(new JsonPrimitive(2));
		array.add(new JsonPrimitive(3));
		array.add(new JsonPrimitive(4));
		array.add(new JsonPrimitive(5));
		array.add(new JsonPrimitive(6));
		array.add(new JsonPrimitive(7));
		
		String someKey = "someKey";
		input.add(someKey, array);

		// create the task and annotate it with the subcollection
		String overlapSrc = "producer/output";
		String subCollString = overlapSrc;
		String dataId = "dataId";
		Task task = PropertyServiceFunctionUtilityCollections.createCollectionOperation(dataId, subCollString,
				CollectionOperation.Split);

		// enter the info on the stride into the input object
		String splitKey = overlapSrc;
		JsonElement replNum = new JsonPrimitive(3);
		input.add(splitKey, replNum);
		
		CollOperEnactable tested = new BlockEnactableMock(stateListeners, inputKeys, task, input);
		
		try {
			tested.atomicPlay();
		} catch (StopException e) {
			fail();
		}
		
		JsonObject jsonResult = tested.getResult();
		JsonElement result = jsonResult.get(someKey);
		assertTrue(result.isJsonArray());
		JsonArray resultArray = result.getAsJsonArray();
		assertEquals(3, resultArray.size());
		JsonElement elementFirst = resultArray.get(0);
		JsonElement elementSecond = resultArray.get(1);
		JsonElement elementThird = resultArray.get(2);
		assertTrue(elementFirst.isJsonArray());
		assertTrue(elementSecond.isJsonArray());
		assertTrue(elementThird.isJsonArray());
		JsonArray arrayFirst = elementFirst.getAsJsonArray();
		JsonArray arraySecond = elementSecond.getAsJsonArray();
		JsonArray arrayThird = elementThird.getAsJsonArray();
		
		assertEquals(3, arrayFirst.size());
		assertEquals(1, arrayFirst.get(0).getAsInt());
		assertEquals(2, arrayFirst.get(1).getAsInt());
		assertEquals(3, arrayFirst.get(2).getAsInt());
		
		assertEquals(3, arraySecond.size());
		assertEquals(4, arraySecond.get(0).getAsInt());
		assertEquals(5, arraySecond.get(1).getAsInt());
		assertEquals(6, arraySecond.get(2).getAsInt());
		
		assertEquals(1, arrayThird.size());
		assertEquals(7, arrayThird.get(0).getAsInt());
	}
	
	@Test
	public void testSplit3even() {
		JsonObject input = new JsonObject();
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		// create the input collection
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(1));
		array.add(new JsonPrimitive(2));
		array.add(new JsonPrimitive(3));
		array.add(new JsonPrimitive(4));
		array.add(new JsonPrimitive(5));
		array.add(new JsonPrimitive(6));
		
		String someKey = "someKey";
		input.add(someKey, array);

		// create the task and annotate it with the subcollection
		String overlapSrc = "producer/output";
		String subCollString = overlapSrc;
		String dataId = "dataId";
		Task task = PropertyServiceFunctionUtilityCollections.createCollectionOperation(dataId, subCollString,
				CollectionOperation.Split);

		// enter the info on the stride into the input object
		String splitKey = overlapSrc;
		JsonElement replNum = new JsonPrimitive(3);
		input.add(splitKey, replNum);
		
		CollOperEnactable tested = new BlockEnactableMock(stateListeners, inputKeys, task, input);
		
		try {
			tested.atomicPlay();
		} catch (StopException e) {
			fail();
		}
		
		JsonObject jsonResult = tested.getResult();
		JsonElement result = jsonResult.get(someKey);
		assertTrue(result.isJsonArray());
		JsonArray resultArray = result.getAsJsonArray();
		assertEquals(3, resultArray.size());
		JsonElement elementFirst = resultArray.get(0);
		JsonElement elementSecond = resultArray.get(1);
		JsonElement elementThird = resultArray.get(2);
		assertTrue(elementFirst.isJsonArray());
		assertTrue(elementSecond.isJsonArray());
		assertTrue(elementThird.isJsonArray());
		JsonArray arrayFirst = elementFirst.getAsJsonArray();
		JsonArray arraySecond = elementSecond.getAsJsonArray();
		JsonArray arrayThird = elementThird.getAsJsonArray();
		
		assertEquals(2, arrayFirst.size());
		assertEquals(1, arrayFirst.get(0).getAsInt());
		assertEquals(2, arrayFirst.get(1).getAsInt());
		
		assertEquals(2, arraySecond.size());
		assertEquals(3, arraySecond.get(0).getAsInt());
		assertEquals(4, arraySecond.get(1).getAsInt());
		
		assertEquals(2, arrayThird.size());
		assertEquals(5, arrayThird.get(0).getAsInt());
		assertEquals(6, arrayThird.get(1).getAsInt());
	}
}
