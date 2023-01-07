package ml2048II;

public class board {
	
	public int[][] boardVal = new int[4][4];
	public int score = 0;
	public int markedi = 0, markedj = 0;
	public int zeros = 16;
	public boolean lost = false;
	
	
	
	public board(int[][] boardVal) 
	{
		if(boardVal.length != 4 || boardVal[0].length != 4) 
		{
			System.out.println("invalid board!");
		}
		else 
		{
			this.boardVal = boardVal;
		}
		
	}	
	public board() 
	{
		boardVal = new int[4][4];
	}
	public int boardMaxVal() 
	{
		int returner = 0;
		for(int i = 0; i < 4; i ++) 
		{
			for(int j = 0; j < 4; j ++) 
			{
				if(boardVal[i][j] > returner) 
				{
					returner = boardVal[i][j];
				}
				
			}
		}
		return returner;
	}
	//get and set board numbers
	public int[][] getBoardVal() 
	{
		return boardVal;
	}
	public void setBoardVal(int[][] boardVal) 
	{
		if(boardVal.length != 4 || boardVal[0].length != 4) 
		{
			System.out.println("invalid board!");
		}
		else 
		{
			this.boardVal = boardVal;
		}
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
	
	public void generateNum(int pos) 
	{
		if(zeros == 0) 
		{
			lost = isLost();
			
		}
		
		zeros --;
		int place = pos;
		double toGen = Math.random();
		for(int i = 0; i < 4; i ++) 
		{
			for(int j = 0; j < 4; j ++) 
			{
				if(boardVal[i][j] == 0) 
				{
					if(place == 0) 
					{
						if(toGen < 0.9) 
						{
							boardVal[i][j] =2;
						}
						else 
						{
							boardVal[i][j] =4;
						}
						markedi = i;
						markedj = j;
						return;
					}else 
					{
						place --;
					}
				}
			}
			
		}
	}
	public void generateNum(int pos, int gen) 
	{
		if(zeros == 0) 
		{
			lost = isLost();
			
		}
		
		zeros --;
		int place = pos;
		
		for(int i = 0; i < 4; i ++) 
		{
			for(int j = 0; j < 4; j ++) 
			{
				if(boardVal[i][j] == 0) 
				{
					if(place == 0) 
					{
						
						boardVal[i][j] = gen;
						
						
						markedi = i;
						markedj = j;
						return;
					}else 
					{
						place --;
					}
				}
			}
			
		}
	}

	
	public void generateNum() 
	{
		
		if(zeros == 0) 
		{
			lost = isLost();
			
		}
		int e = zeros;
		zeros --;
		int place = (int) (Math.random() * e);
		
		
		
		for(int i = 0; i < 4; i ++) 
		{
			for(int j = 0; j < 4; j ++) 
			{
				if(boardVal[i][j] == 0) 
				{
					if(place == 0) 
					{
						boardVal[i][j] =2;
						markedi = i;
						markedj = j;
						return;
					}else 
					{
						place --;
					}
				}
			}
			
		}
		
	}
	public boolean isLost() {
		
		if(zeros <= 0) 
		{
			//System.out.println("triggered");
			return(!move("up", true) && !move("down", true) && !move("left", true) && !move("right", true));
		}else 
		{
			return false;
		}
		
		
		
	}
	
	public boolean move(String direction, boolean isEstimate) 
	{
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
		
		
		//moved = !sto.equals(boardVal);
		moved = !checkIdenticalMat(sto, boardVal);
		/*
		for(int i = 0; i < 4; i ++) 
		{
			for(int j = 0; j < 4; j ++) 
			{
				System.out.print(sto[i][j]);
			}
		}
		System.out.println("");
		*/
		if(!isEstimate && moved) 
		{
			boardVal = sto;
			generateNum();
		}
	
		
		
		return moved;
		
	}
	
	public boolean checkIdenticalMat(int[][] mat1, int[][] mat2) 
	{
		for(int i = 0; i < mat1.length; i ++) 
		{
			for(int j = 0; j < mat2.length; j ++) 
			{
				if(mat1[i][j] != mat2[i][j]) 
				{
					
					return false;
				}
			}
		}
		return true;	
	}
	
	public int[][] prime(int[][] mat)
	{
		int[][] returner = new int[mat[0].length][mat.length];
		
		for(int i = 0; i < returner.length; i ++) 
		{
			for(int j = 0; j < returner[0].length; j ++) 
			{
				returner[j][i] = mat[i][j];
			}
		}
		
		return returner;
	}
	
	
	public int[][] lineInv(int[][] mat)
	{
		int[][] returner = new int[mat[0].length][mat.length];
		
		for(int i = 0; i < returner.length; i ++) 
		{
			for(int j = 0; j < returner[0].length; j ++) 
			{
				returner[i][returner[0].length - 1 - j] = mat[i][j];
			}
		}
		
		return returner;
	}
	
	
	public int[] moveZeroToLast(int[] arr) 
	{
		int[] returner = new int[arr.length];
		int count = 0;
		for(int i = 0; i < arr.length; i ++) 
		{
			if(arr[i] != 0) 
			{
				returner[count] = arr[i];
				count ++;
			}
		}
		return returner;
	}
	//left
	public int[] combine(int[] arr, boolean isEstimate) 
	{
		int[] returner = moveZeroToLast(arr);
		for(int i = 0; i < arr.length - 1; i ++) 
		{
			if(returner[i] != 0 && returner[i] == returner[i + 1]) 
			{
				//System.out.println("triggered");
				returner[i] *= 2;
				
				returner[i +1] = 0;
				if(!isEstimate) 
				{
					zeros ++;
					score += returner[i];
				}
				
			}
		}
		
		returner = moveZeroToLast(returner);
		return returner;
	}
	


	
	public String toString() 
	{
		String returner = "";
		for(int i = 0; i < 4; i ++) 
		{
			for(int j = 0; j < 4; j ++) 
			{
				if(i == markedi && j == markedj) 
				{	
					returner += "{";
				}else 
				{
					returner += "[";
				}
				returner += boardVal[i][j];
				
				if(i == markedi && j == markedj) 
				{	
					returner += "}";
				}else 
				{
					returner += "]";
				}
			}
			returner += "\n";
		}
		returner += "score: ";
		returner += score;
		returner += "zeros: ";
		returner += zeros;
		
		return returner;
	}
	

}
