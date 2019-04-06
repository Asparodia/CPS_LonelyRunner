package bridge.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import bridge.services.BridgeControllerService;
import bridge.services.CarSensorServerService;
import bridge.services.RequireBridgeControllerService;
import bridge.services.RequireCarSensorServerService;
import bridge.services.TrafficLightService;

public class BridgeSimulator implements
		/* require */
		RequireCarSensorServerService, RequireBridgeControllerService {

	private Map<String, CarSensorServerService> sensors;
	private BridgeControllerService controller;

	private Random rand;

	public BridgeSimulator() {
		sensors = new HashMap<String, CarSensorServerService>();
		controller = null;
		rand = null;
	}

	public void init(long seed) {
		rand = new Random(seed);
	}

	@Override
	public void bindCarSensorServerService(CarSensorServerService service) {
		sensors.put(service.getName(), service);
	}

	@Override
	public void bindBridgeControllerService(BridgeControllerService service) {
		controller = service;
	}

	public void validateComponent() {
		if (controller == null) {
			throw new Error("Missing bridge controller component");
		}
		if (!sensors.containsKey("InIsland")) {
			throw new Error("Missing in island sensor");
		}
		if (!sensors.containsKey("OutIsland")) {
			throw new Error("Missing out island sensor");
		}
		if (!sensors.containsKey("InMainland")) {
			throw new Error("Missing in mainland sensor");
		}
		if (!sensors.containsKey("OutMainland")) {
			throw new Error("Missing out mainland sensor");
		}
	}

	public void stepRandom(TrafficLightService islandLight, TrafficLightService mainLandLight) {
		if (rand.nextBoolean() && controller.canEnter() && islandLight.isGreen()) {
			sensors.get("InIsland").activate();

		}

		if (rand.nextBoolean() && controller.canEnter() && mainLandLight.isGreen()) {
			sensors.get("InMainland").activate();
		}
		if (rand.nextBoolean() && controller.canLeave()) {
			sensors.get("OutIsland").activate();
		}
		if (rand.nextBoolean() && controller.canLeave()) {
			sensors.get("OutMainland").activate();
		}
	}

	public void simulateRandom(int nbSteps,TrafficLightService inIslandLight, TrafficLightService inMainlandLight) {
		inIslandLight.switchOn();
		inMainlandLight.switchOn();
//		inMainlandLight.changeGreen();
//		inIslandLight.changeRed();
		for (int i = 0; i < nbSteps; i++) {
			stepRandom(inIslandLight,inMainlandLight);
			controller.control();
		}
	}

	public void simulate(int nbSteps, Bridge b, TrafficLightService inIslandLight, TrafficLightService inMainlandLight) {
		
		inIslandLight.switchOn();
		inMainlandLight.switchOn();
		inMainlandLight.changeGreen();
		for (int i = 0; i < nbSteps; i++) {
			step(b, inIslandLight, inMainlandLight);
			controller.control();
		}

	}

	public void step(Bridge b, TrafficLightService inIslandLight, TrafficLightService inMainlandLight) {
		if (b.getNbCars() > 0) {
			if (b.getNbIn() > 0) {
				int sortie = rand.nextInt(b.getLimit() - b.getNbIn() + 1);
				for (int i = 0; i < sortie; i++) {
					
					sensors.get("InIsland").activate();
				}
			}
			if (b.getNbOut() > 0) {
				int sortie = rand.nextInt(b.getLimit() - b.getNbOut() + 1);
				for (int i = 0; i < sortie; i++)
					sensors.get("OutMainland").activate();
			}
		}
		
		if (inMainlandLight.isGreen()) {
			System.out.println("iiiiii");
			if (b.getNbIn() < b.getLimit()) {
				int entree = rand.nextInt(b.getLimit() - b.getNbIn() + 1);
				if (b.getLimit() < b.getNbIn() + entree)
					entree = b.getLimit() - b.getNbIn();
				for (int i = 0; i < entree; i++)
					sensors.get("InMainland").activate();
			}
		}
		if (inIslandLight.isGreen()) {
			
			if (b.getNbOut() < b.getLimit()) {
				int entree = rand.nextInt(b.getLimit() - b.getNbOut() + 1);
				if (b.getLimit() < b.getNbOut() + entree)
					entree = b.getLimit() - b.getNbOut();
				for (int i = 0; i < entree; i++)
					sensors.get("InIsland").activate();
			}
		}
		
	}
}
