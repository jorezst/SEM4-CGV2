package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class GameLoop {

	public static int flyCounter = 0;
	public static int frogCounter = 0;
	private static float z = 25;

	public static void main(String[] args) {

		DisplayManager.createDisplay();

		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		float[] vertices = {
				-0.5f, -0.5f, 0.5f,
				0.5f, -0.5f, 0.5f,
				0.5f, 0.5f, 0.5f,
				-0.5f, 0.5f, 0.5f,
				-0.5f, -0.5f, -0.5f,
				0.5f, -0.5f, -0.5f,
				0.5f, 0.5f, -0.5f,
				-0.5f, 0.5f, -0.5f
		};
		
		int[] indices = {
				1, 2, 3, // Front face
				1, 3, 4,
				2, 6, 7, // Right face
				2, 7, 3,
				6, 5, 8, // Back face
				6, 8, 7,
				5, 1, 4, // Left face
				5, 4, 8,
				4, 3, 7, // Top face
				4, 7, 8,
				5, 6, 2, // Bottom face
				5, 2, 1
		};
		
		float[] textureCoords = {
				0.0f, 0.0f, // Front face
				1.0f, 0.0f,
				1.0f, 1.0f,
				0.0f, 1.0f,
				1.0f, 0.0f, // Right face
				1.0f, 1.0f,
				0.0f, 1.0f,
				0.0f, 0.0f,
				0.0f, 1.0f, // Back face
				1.0f, 1.0f,
				1.0f, 0.0f,
				0.0f, 0.0f,
				0.0f, 1.0f, // Left face
				1.0f, 1.0f,
				1.0f, 0.0f,
				0.0f, 0.0f,
				0.0f, 0.0f, // Top face
				1.0f, 0.0f,
				1.0f, 1.0f,
				0.0f, 1.0f,
				0.0f, 1.0f, // Bottom face
				1.0f, 1.0f,
				1.0f, 0.0f,
				0.0f, 0.0f

		};
		
		float[] normals =  {
			0.0f, 0.0f, 1.0f, // Front face
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, -1.0f, // Back face
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			1.0f, 0.0f, 0.0f, // Right face
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f, // Left face
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f, // Top face
			0.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, -1.0f, 0.0f, // Bottom face
			0.0f, -1.0f, 0.0f,
			0.0f, -1.0f, 0.0f,
			0.0f, -1.0f, 0.0f
		};

		RawModel rawModelFrog = OBJLoader.loadObjModel("frog", loader);
		RawModel rawModelFly = OBJLoader.loadObjModel("fly", loader);
		//RawModel rawModelFly = loader.loadToVAO(vertices, textureCoords, normals, indices);
		TexturedModel staticModelFrog = new TexturedModel(rawModelFrog,
				new ModelTexture(loader.loadTexture("green")));
		TexturedModel staticModelFly = new TexturedModel(rawModelFly, new ModelTexture(loader.loadTexture("grey")));
		ModelTexture textureFrog = staticModelFrog.getTexture();
		ModelTexture textureFly = staticModelFly.getTexture();
		textureFrog.setShineDamper(10);
		textureFrog.setReflectivity(1);
		textureFly.setShineDamper(10);
		textureFly.setReflectivity(1);


		List<Entity> allFrogs = new CopyOnWriteArrayList<Entity>();
		List<Entity> allFlies = new CopyOnWriteArrayList<Entity>();
		Random random = new Random();

		Timer timer = new Timer();
		Camera camera = new Camera();
		
		TimerTask counter = new TimerTask() {
			@Override
			public void run() {
				System.out.println("Frogs: " + allFrogs.size() + " | Flies: "+ allFlies.size());
			}
		};
		timer.schedule(counter, 2000, 2000);
		
		TimerTask taskFrog = new TimerTask() {
			@Override
			public void run() {
				List<Entity> removalList = new ArrayList<>();
					for (Entity frog : allFrogs) {
						if (frog.getKills() < 2) {
							removalList.add(frog);
						} else {
							allFrogs.add(new Entity(staticModelFrog,
									new Vector3f(random.nextFloat(120 + 120) - 120, random.nextFloat(66 + 66) - 66, z),
									270f, 0f, 180f, 1f, new String("frog")));
							frog.setKills(0);
						}
					}
				allFrogs.removeAll(removalList);
			}
		};
		timer.schedule(taskFrog, 7000, 7000);

		TimerTask taskFly = new TimerTask() {
			@Override
			public void run() {
				if (allFlies.size() < 250) {
					allFlies.add(new Entity(staticModelFly,
							new Vector3f(random.nextFloat(120 + 120) - 120, random.nextFloat(66 + 66) - 66, z), 270f,
							0f, 180f, 0.5f, new String("fly")));
				}

			}
		};
		timer.schedule(taskFly, 300, 300);
		

		for (int i = 0; i < 10; i++) {
			allFrogs.add(new Entity(staticModelFrog,
					new Vector3f(random.nextFloat(120 + 120) - 120, random.nextFloat(66 + 66) - 66, z), 270f, 0f, 180f,
					1f, new String("frog")));
		}

		for (int i = 0; i < 100; i++) {
			allFlies.add(new Entity(staticModelFly,
					new Vector3f(random.nextFloat(120 + 120) - 120, random.nextFloat(66 + 66) - 66, z), 270f, 0f, 180f,
					0.5f, new String("fly")));
		}

		while (!Display.isCloseRequested()) {
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadviewMatrix(camera);
			for (Entity frog : allFrogs) {
				renderer.render(frog, shader);
				frog.move();
			}
			for (Entity fly : allFlies) {
				renderer.render(fly, shader);
				fly.move();
			}
			collisionCheck(allFrogs, allFlies);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		timer.cancel();
		timer.purge();
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

	public static void collisionCheck(List<Entity> allFrogs, List<Entity> allFlies) {
		List<Entity> removalList = new ArrayList<>();
		for (Entity frog : allFrogs) {
			if (frog.getTag().equals("frog")) {
				Vector3f frogPosition = frog.getPosition();

				for (Entity fly : allFlies) {
					if (fly.getTag().equals("fly")) {
						Vector3f flyPosition = fly.getPosition();
						float distance = calculateDistance(frogPosition, flyPosition);
						if (distance <= 5) {
							removalList.add(fly);
							frog.increaseKills(1);
						}
					}
				}
				allFlies.removeAll(removalList);
			}
		}
	}

	private static float calculateDistance(Vector3f positionA, Vector3f positionB) {
		float dx = positionB.x - positionA.x;
		float dy = positionB.y - positionA.y;
		float dz = positionB.z - positionA.z;

		return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	public static void changeFlyCounter(int increase) {
		flyCounter += increase;
	}

	public static void changeFrogCounter(int increase) {
		frogCounter += increase;
	}

}
