package models;

public class RawModel {
	
	private int VaoID;
	private int vertexCount;
	
	public RawModel(int vaoID, int vertexCount) {
		this.VaoID=vaoID;
		this.vertexCount=vertexCount;
	}

	public int getVaoID() {
		return VaoID;
	}


	public int getVertexCount() {
		return vertexCount;
	}


	
}
