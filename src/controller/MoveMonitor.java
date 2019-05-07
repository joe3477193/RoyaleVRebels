package controller;

public class MoveMonitor {

	public String moveType;
	public int fromTileRow;
	public int fromTileCol;
	public int tooTileRow;
	public int tooTileCol;
	
	 public MoveMonitor(String moveType, int fromTileRow, int fromTileCol, int tooTileRow, int tooTileCol) {
		this.moveType = moveType;
		this.fromTileRow = fromTileRow;
		this.fromTileCol = fromTileCol;
		this.tooTileRow = tooTileRow;
		this.tooTileCol = tooTileCol;
	 }
	 
	 
}
