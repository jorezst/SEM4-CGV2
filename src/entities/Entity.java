package entities;

import org.lwjgl.util.vector.Vector3f;

import java.util.Random;

import main.GameLoop;
import models.TexturedModel;

public class Entity {
	
	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	private float borderX = 120;
	private float borderY = 66;
	private float distance;
	private float angle = 90;
	private String tag;
	private int kills;
	

	Random random = new Random();
	
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, String tag) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.tag = tag;
		if (tag.equals("fly")) {
			GameLoop.changeFlyCounter(1);
			distance = 0.4f;
		}
	       
        if (tag.equals("frog")) {
        	GameLoop.changeFrogCounter(1);
        	distance = 0.07f;
        }
        
       
    	int ran = random.nextInt(361);
        if(angle + ran > 360) {
			angle -= ran;
			this.increaseRotation(0,ran,0);
		}
		else {
			angle += ran;
			this.increaseRotation(0,-ran,0);
		}
	        
	}
	
	
	public void move() {
		double bogenmass = Math.toRadians(angle);
        double dx = distance * Math.cos(bogenmass);
        double dy = distance * Math.sin(bogenmass);
        
        if (random.nextInt(61) == 60) {
        	int ran = random.nextInt(46 + 46) -46;
            if(angle + ran > 360) {
    			angle -= ran;
    			this.increaseRotation(0,ran,0);
    		}
    		else {
    			angle += ran;
    			this.increaseRotation(0,-ran,0);
    		}
        }
        
        if (this.position.x+dx > borderX || this.position.x+dx < -borderX) {
			if(angle + 180 > 360) {
				angle -= 180;
				this.increaseRotation(0,180,0);
			}
			else {
				angle += 180;
				this.increaseRotation(0,-180,0);
			}
		}
		else {
			this.position.x+=dx;
		}
        if (this.position.y+dy > borderY || this.position.y+dy < -borderY) {
        	if(angle + 180 > 360) {
				angle -= 180;
				this.increaseRotation(0,180,0);
			}
			else {
				angle += 180;
				this.increaseRotation(0,-180,0);
			}
		}
		else {
			this.position.y+=dy;
		}
	}
	
	public void increasePosition(float dx, float dy, float dz) {
		if (this.position.x+dx > borderX || this.position.x+dx < -borderX) {
			this.increaseRotation(0, 180, 0);
		}
		else {
			this.position.x+=dx;
		}
		if (this.position.y+dy > borderY || this.position.y+dy < -borderY) {
			this.increaseRotation(0, 180, 0);
		}
		else {
			this.position.y+=dy;
		}
	}
	
	
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX+=dx;
		this.rotY+=dy;
		this.rotZ+=dz;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public int getKills() {
		return kills;
	}


	public void setKills(int kills) {
		this.kills = kills;
	}


	public void increaseKills(int killInc) {
		this.kills+=killInc;
	}
	
	
	
}
