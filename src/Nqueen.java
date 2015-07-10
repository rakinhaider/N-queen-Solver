import java.util.Scanner;

public class Nqueen {

	public static int[][] grid;
	public static boolean[] assigned;
	public static int countAll=0;
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter N:");
		int N = /*Integer.parseInt(args[0]);*/sc.nextInt();
		int[] X = new int[N + 1];
		grid = new int[N + 1][N + 1];
		assigned=new boolean[N+1];
		System.out.println("First valid n-queen assignment.");
		placeNQueens(X, N, 1);
		
		int count = placeNQueensToGetNumber(new int[N + 1], N, 1);
		System.out.println("Number of valid n-queen assignment.");
		System.out.println(count);
		System.out.println("All valid n-queen assignment.");
		placeNQueensAll(new int[N + 1], N, 1);

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[i][j] = 0;
			}
		}
		//countAll=0;
		System.out.println("All valid n-queen assignment with forward checking.");
		placeNQueensFCMRV(X,N);
	}

	private static boolean placeNQueensFCMRV(int[] X, int N) {
		// TODO Auto-generated method stub
		if (selectRowMRV()==0) {
			/*countAll++;
			System.out.print("Solution "+countAll +" :");
			*/for (int integer : X) {
				if(integer==0)continue;
				System.out.print(integer+ " ");
			}
			System.out.println();
			return true;
		}

		int i = selectRowMRV();
		assigned[i]=true;
		for (int j = 1; j < X.length; j++) {
			if (grid[i][j] == 0) {
				X[i] = j;
				
				boolean flag = forwardCheck(X, i, j);
				if (!flag)
				{
					undoForwarCheck(X,i,j);
					continue;
				}
				boolean success = placeNQueensFCMRV(X, N);
				/*if (success)
				{
					undoForwarCheck(X,i,j);
					return true;
				}
*/				undoForwarCheck(X,i,j);
			}
		}
		assigned[i]=false;
		return false;
	}

	private static void undoForwarCheck(int[] x, int i, int j) {
		for (int k = 1; k < grid.length; k++) {
			grid[k][j]--;
		}

		for (int k = 1; k < grid.length; k++) {
			grid[i][k]--;
		}

		for (int k = 1; k < grid.length; k++) {
			int col = j + (i - k);
			if (col > 0 && col <grid.length) {
				grid[k][col]--;
			}
			
		}

		for (int k = 1; k < grid.length; k++) {
			int col = j - (i - k);
			if (col > 0 && col <grid.length) {
				grid[k][col]--;
			}
		}
	}

	private static boolean forwardCheck(int[] X, int i, int j) {

		for (int k = 1; k < grid.length; k++) {
			grid[k][j]++;
		}

		for (int k = 1; k < grid.length; k++) {
			grid[i][k]++;
		}

		for (int k = 1; k < grid.length; k++) {
			int col = j + (i - k);
			if (col > 0 && col <grid.length) {
				grid[k][col]++;
			}
			
		}

		for (int k = 1; k < grid.length; k++) {
			int col = j - (i - k);
			if (col > 0 && col <grid.length) {
				grid[k][col]++;
			}
		}
		
		for (int k = 1; k < grid.length; k++) {
			boolean found = false;
			for (int l = 1; l < grid.length; l++) {
				if (grid[k][l] == 0 || X[k] == l)
					found = true;
			}
			if (!found)
				return false;
		}
		return true;
	}

	private static int selectRowMRV() {
		// TODO Auto-generated method stub
		int minCount=grid.length+1;
		int selectedRow=0;
		for (int i = 1; i < grid.length; i++) {
			int count=0;
			for (int j = 1; j < grid.length; j++) {
				if(grid[i][j]==0)count++;
			}
			if(minCount>count && !assigned[i] )
			{
				minCount=count;
				selectedRow=i;
			}
		}
		return selectedRow;
	}

	private static boolean placeNQueensAll(int[] X, int N, int i) {
		// TODO Auto-generated method stub
		if (i == N + 1) {
			/*countAll++;
			System.out.print("Solution "+countAll +" :");
			*/for (int j : X) {
				if (j == 0)
					continue;
				System.out.print(j + " ");
			}
			System.out.println();
			return true;
		}

		for (int j = 1; j <= N; j++) {

			X[i] = j;
			boolean flag = checkConstraints(X, N, i);
			if (!flag)
				continue;
			boolean success = placeNQueensAll(X, N, i + 1);
			// if(success)return success;
		}

		return false;

	}

	private static int placeNQueensToGetNumber(int[] X, int N, int i) {
		// TODO Auto-generated method stub

		if (i == N + 1)
			return 1;

		int success = 0;
		for (int j = 1; j <= N; j++) {

			X[i] = j;
			boolean flag = checkConstraints(X, N, i);
			if (!flag)
				continue;
			success += placeNQueensToGetNumber(X, N, i + 1);
			// if(success)return success;
		}

		return success;
	}

	private static boolean placeNQueens(int[] X, int N, int i) {
		// TODO Auto-generated method stub

		if (i == N + 1) {
			for (int j : X) {
				if (j == 0)
					continue;
				System.out.print(j + " ");
			}
			System.out.println();
			return true;
		}

		for (int j = 1; j <= N; j++) {

			X[i] = j;
			boolean flag = checkConstraints(X, N, i);
			if (!flag)
				continue;
			boolean success = placeNQueens(X, N, i + 1);
			if (success)
				return success;
		}

		return false;

	}

	private static boolean checkConstraints(int[] A, int N, int r) {

		for (int i = 1; i <= r - 1; i++) {
			if (A[i] == A[r] || A[i] == (A[r] + (r - i))
					|| A[i] == (A[r] - (r - i)))
				return false;
		}

		return true;
	}

}
