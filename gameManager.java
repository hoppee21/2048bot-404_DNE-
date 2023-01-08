package ml2048II;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import ml2048I.board;

class operate implements Comparable<operate>
{
	
	String operateDir;
	int weight = 0;
	public operate(String o, int w) 
	{
		weight = w;
		operateDir = o;
		
	}
	public String toString() 
	{
		return "operateDir: " + operateDir + " Weight: " + weight +"\n";
	}
	@Override
	public int compareTo(operate o) {
		// TODO Auto-generated method stub
		return -((Integer)weight).compareTo((Integer)o.weight);
	}
	

	}

public class gameManager {
	
	
	static int theScore;
	static ArrayList<operate> moves = new ArrayList<>();

	
	public static void main(String[]args) throws Exception 
	{
		
		
		
		int trialTimes = 100;
		int[] maxSet = new int[trialTimes];
		int winCount = 0;
		for(int z = 0; z < trialTimes; z ++) {
			//here
			int leftweight = 0, rightweight = 100, upweight = 10, downweight = 90;
			int moveCount = 0;
			String operation = "";
			board theboard = new board(new int[4][4]);
			
			theboard.generateNum();
			theboard.generateNum();
			theboard.generateNum();
			theboard.generateNum();
			theboard.generateNum();
			
			//fix it to fit new weight calculator
			/*
			leftweight = wc.calcWeight(theboard.boardVal, "left");
			rightweight = wc.calcWeight(theboard.boardVal, "right");
			upweight = wc.calcWeight(theboard.boardVal, "up");
			downweight = wc.calcWeight(theboard.boardVal, "down");*/
			
			
			//String[] moves = new String[4];
			moves = new ArrayList<>();
			moves.add(new operate("up", upweight));
			moves.add(new operate("left", leftweight));
			moves.add(new operate("right", rightweight));
			moves.add(new operate("down", downweight));
			Collections.sort(moves);
			System.out.println(moves);
			
			leftweight = calcweightVerII(theboard.boardVal, "left",1);
			rightweight = calcweightVerII(theboard.boardVal, "right",1);
			upweight = calcweightVerII(theboard.boardVal, "up",1);
			downweight = calcweightVerII(theboard.boardVal, "down",1);
			
			
		
			BufferedWriter out = null;
			out = new BufferedWriter(new FileWriter("succeedOutput.txt"));
			while(moveCount < 3000 && !theboard.isLost()) 
			{
				
	
				for(operate i : moves) 
				{
					System.out.println(i.operateDir + "weight: " + i.weight);
				}
				
				moveCount ++;
				boolean operated = false;
				for(int i = 0; i < moves.size(); i ++) 
				{
					
					System.out.println(moves.get(i).operateDir);
					operated = theboard.move(moves.get(i).operateDir, true);
					if(operated) 
					{
						//here output to file
						String str = "";
						for(int x = 0; x < theboard.boardVal.length; x ++) 
						{
							for(int j = 0; j < theboard.boardVal.length; j ++) 
							{
								str += theboard.boardVal[x][j];
								str += ",";
								
								
							}
						}
						//System.out.println("Triggered!");
						str += moves.get(i).operateDir;
						
						str += "\n";
						System.out.println(str);
						out.write(str);
						
						
						//System.out.println("triggered");
						
						//here real operate
						BufferedWriter op = null;
						op = new BufferedWriter(new FileWriter("dir.txt"));
						op.write(moves.get(i).operateDir);
						if(op!= null)op.close();
						theboard.move(moves.get(i).operateDir, false);
						break;
					}else 
					{
						System.out.println("Not work");
					}
				}
				
				
				theScore = theboard.score;
				
				//fixme: refresh the board if actual individual process
				boolean needReadFromWeb = true;
				if(needReadFromWeb) 
				{
					
					theboard = new board(readMatFromFileInt("data"));
				}
				//ver II
				/*
				for(operate i : moves) 
				{
					i.weight = calcweightVerII(theboard.boardVal, i.operateDir,0);
				}
				Collections.sort(moves);
				*/
				//ver III
				
				moves = calcweightVerIII(theboard.boardVal, 2);
				
			
				//ver IV
				
				
				
				
				Collections.sort(moves);
				
				
				
				System.out.println(theboard);
				
				
				if(!operated) 
				{
					System.out.println("Not operated");
				}
				
				
				
			}
			maxSet[z] = theboard.boardMaxVal();
			if(maxSet[z] >= 2048) 
			{
				winCount ++;
			}
			if(out != null) {out.close();}
			
		}
		for(int z = 0; z < trialTimes; z ++) 
		{
			System.out.print(maxSet[z] + " ");
		}
		System.out.println(winCount);
		
		
	}
	public static int[][] prime(int[][] mat)
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
	
	
	public static int[][] lineInv(int[][] mat)
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
	
	//ver2
	public static int calcweightVerII(int[][] boardValue,String dir, int iter) 
	{
		if(dir.equals("up") || dir.equals("down")) 
		{
			boardValue = prime(boardValue);
		}
		if(dir.equals("right") || dir.equals("down")) 
		{
			boardValue = lineInv(boardValue);
		}
		if(iter == 0) 
		{
			
			simulateBoard s = new simulateBoard(boardValue);
			if(s.move("left", true)) 
			{
				
				s = s.moveEstimation("left", false);
				//int zerosWeight = theScore /18;
				int zerosWeight = s.boardMaxVal() / 3;
				int FlatnessWeight = -2;
				int lostWeight = 0;
				s.generateNum();
				if(s.isLost()) 
				{
					//System.out.println("triggered");
					lostWeight = -10000000;
				}
				
				//System.out.println("zeros: " + s.isLost());
				return zerosWeight * s.getZeros()+FlatnessWeight*s.calcFlatness() + lostWeight;
			}
			else 
			{
				return -439600;
			}
			
		}
		else 
		{
			//case losing
			int maxreturner = -999999999;
			simulateBoard s = new simulateBoard(boardValue);
			if(s.move("left", true)) 
			{
				s.move("left", false);
				while(s.zeros != 0) 
				{
					s.generateNum();
				}
				
			}
			else 
			{
				//case not worked
				return -500000000;
			}
			s.move(dir, false);
			//get from 4 dirs
			for(int i = 0; i < moves.size(); i ++) 
			{
				boolean operated = false;
				
				operated = s.move(moves.get(i).operateDir, true);
				if(operated) 
				{
					//System.out.println("triggered");
					//s.move(moves.get(i).operateDir, false);//not this
					maxreturner = Math.max(maxreturner, calcweightVerII(s.boardVal, moves.get(i).operateDir, iter-1));
				}
				//calcweightVerII(s., dir, iter-1)
			}
			return maxreturner;
			
		}
		
		
	}
	
	//ver3
	//here, we ignore random numbers among iters.
	public static ArrayList<operate> calcweightVerIII(int[][] boardValue, int iter) 
	{
		
		ArrayList<operate> returner = new ArrayList<>();
	
		if(iter == 0) 
		{
			returner.add(new operate("up",calcweightVerII(boardValue, "up",0) ));
			returner.add(new operate("left", calcweightVerII(boardValue, "left",0)));
			returner.add(new operate("right", calcweightVerII(boardValue, "right",0)));
			returner.add(new operate("down", calcweightVerII(boardValue, "down",0)));
		}
		else 
		{
			returner.add(new operate("up",0));
			returner.add(new operate("left", 0));
			returner.add(new operate("right", 0));
			returner.add(new operate("down", 0));
			for(int i = 0; i < returner.size(); i ++) 
			{
				simulateBoard s = new simulateBoard(boardValue);
				boolean op = s.move(returner.get(i).operateDir, true);
				if(op) 
				{
					s = s.moveEstimation(returner.get(i).operateDir, false);
					//int[] zerosINS = s.getZerosPosition();
					int randomizedMin = Integer.MAX_VALUE;
					for(int j = 0; j < s.zeros; j ++) 
					{
						simulateBoard sClone = new simulateBoard(s.boardVal);
						sClone.generateNum(j,2);
						ArrayList<operate> recursiveOutput = calcweightVerIII(sClone.boardVal, iter - 1);
						int operatedMax = recursiveOutput.get(0).weight;
						//System.out.println("Val:" + randomizedMin + " and  " + operatedMax + "with iter " + iter);
						randomizedMin = Math.min(randomizedMin, operatedMax);			
						
					}
					returner.get(i).weight = randomizedMin;
				}
				else 
				{
					returner.get(i).weight = -220000;
				}
				
			}
			
		}
		Collections.sort(returner);
		//System.out.println(returner);
		return returner;
		
	}
	
	
	public static ArrayList<operate> calcweightVerIV(int[][] boardValue)
	{
		
		ArrayList<operate> returner = new ArrayList<>();
		Collections.sort(returner);
		return returner;
	}
	public static int[][] toLog2(int[][] mat)
	{
		int[][] returner = new int[4][4];
		
		for(int i = 0; i < returner.length; i ++) 
		{
			for(int j = 0; j < returner[0].length; j ++) 
			{
				if(mat[i][j] != 0) returner[i][j] = (int) (Math.log(mat[i][j]) / Math.log(2));;
			}
		}
		
		return returner;
	}
	public static int[][] readMatFromFileInt(String name) throws IOException
	{
		BufferedReader in = null;
		in = new BufferedReader(new FileReader(name + ".txt"));
		String line;
		ArrayList<String> readData = new ArrayList<String>();
		while ((line = in.readLine()) != null) {
			readData.add(line);
		}
		int rows = readData.size();
		int[][] returner = new int[rows][];
		int count = 0;
		for(String s : readData) 
		{
			String[] oneRow = s.split(",");
			returner[count] = new int[oneRow.length];
			for(int i = 0; i < oneRow.length; i ++) 
			{
				returner[count][i] = Integer.parseInt(oneRow[i]);
			}
			count ++;
			
		}
		//int cols = 
		return returner;
	}
}
