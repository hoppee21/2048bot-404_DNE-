package ml2048II;

public class simulateBoard extends board{
	
	public int[] _zerosPosition;
	
	public simulateBoard(int[][] boardVal) 
	{
		
		if(boardVal.length != 4 || boardVal[0].length != 4) 
		{
			System.out.println("invalid board!");
		}
		else 
		{
			this.boardVal = boardVal;
		}
		zeros = 0;
		for(int i = 0; i < 4; i ++) 
		{
			for(int j = 0; j < 4; j ++) 
			{
				if(boardVal[i][j] == 0) 
				{
					zeros ++;
				}
				
			}
		}
	}
	
	//有序性
	//fixme someday, but not today
	public int calcSeqnency() 
	{
		int returner = 0;
		return returner;
	}
	
	//平滑性: 0 most flat
	public int calcFlatness() 
	{
		int returner = 0;
		
		for(int i = 0; i < 4; i ++) 
		{
			for(int j = 1; j < 4; j ++) 
			{
				returner += Math.abs(boardVal[i][j] - boardVal[i][j - 1]);
			}
		}
		for(int j = 0; j < 4; j ++) 
		{
			for(int i = 1; i < 4; i ++) 
			{
				returner += Math.abs(boardVal[i][j] - boardVal[i-1][j]);
			}
		}
		
		return returner;
	}
	//零数量
	public int getZeros() 
	{
		return zeros;
	}
	
	public int[] getZerosPosition() 
	{
		_zerosPosition = new int[zeros];
		int count = 0;
		for(int i = 0; i < 4; i ++) 
		{
			for(int j = 0; j < 4; j++) 
			{
				if(boardVal[i][j] == 0) 
				{
					_zerosPosition[count] = i * 4 + j;
					count ++;
				}
			}
		}
		return _zerosPosition;
	}
	
	public simulateBoard moveEstimation(String direction, boolean isEstimate) 
	{
		isEstimate = false;
		boolean moved = false;
		
		int[][] sto = new int[4][4];
		
		for(int i = 0; i < 4; i ++) 
		{
			for(int j = 0; j < 4; j ++) 
			{
				sto[i][j] = boardVal[i][j];
			}
		}
		
		if(direction.equals("up") || direction.equals("down")) 
		{
			sto = prime(sto);
		}
		if(direction.equals("down")|| direction.equals("right")) 
		{
			sto = lineInv(sto);
		}
		for(int i = 0; i < 4; i ++) 
		{
			sto[i] = combine(sto[i], isEstimate);
		}
		if(direction.equals("down")|| direction.equals("right")) 
		{
			sto = lineInv(sto);
		}
		if(direction.equals("up") || direction.equals("down")) 
		{
			sto = prime(sto);
		}
		//moved = !checkIdenticalMat(sto, boardVal);
			
		return new simulateBoard(sto);
		
	}
	
	
	
	public static void main(String[]args) 
	{
		
	}
	

}
