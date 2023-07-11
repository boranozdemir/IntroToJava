import java.util.*;

public class MineSweeper {
        
    public static void main(String[] args) {
        String boardsize;
        System.out.println("::WELCOME::");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the sizes of the board(m x n):");
        boardsize = scanner.nextLine();
        System.out.print("Please select the difficulty (E, M, H) : ");
        String diff = scanner.nextLine();
        String[][] board = boardCreator(boardsize);
        int[][] minemap=mapCreator(boardsize);
        minemap = mineReplacor(minemap,diff);
        neighbourMine(minemap);
        print(minemap);

        while(minenumber(minemap, diff)<freecellnum(board)){
            print(board);
            System.out.print("Please make your move: ");
            String move = scanner.nextLine();
            if(move.charAt(move.length()-1)=='F'){
                int row = setFlagMove(move)[0];
                int col = setFlagMove(move)[1];
                if(board[row][col]=="F"){
                    System.out.println("Cell is already flagged.");
                    continue;
                }
                if(board[row][col]!="F"&&board[row][col]!="o"){
                    System.out.println("Opened cells can not be flagged.");
                    continue;
                }
                else{
                    setFlag(board, move);
                    continue;
                }
            }
            if(move.charAt(move.length()-1)=='U'){
                int rowu = setUnFlagMove(move)[0];
                int colu = setUnFlagMove(move)[1];
                if(board[rowu][colu]=="o"){
                    System.out.println("Cell is already unflagged.");
                    continue;
                }
                if(board[rowu][colu]!="o"&&board[rowu][colu]!="F"){
                    System.out.println("Only flagged cells can be unflagged.");
                    continue;
                }
                else{
                    unFlag(board, move);
                    continue;
                }
            }
            int row = move(move)[0];
            int col = move(move)[1];
            if(board[row][col]=="F"){
                System.out.println("Flagged cells can not be opened!");
                continue;
            }
            if(board[row][col]!="o"){
                System.out.println("Cell is already open.");
                continue;
            }
            if(isMine(board,minemap, move)){
                break;
            }
            openCell(minemap, board, row, col);
            if (minenumber(minemap, diff)==freecellnum(board)){
                print(board);
                System.out.println("Congratulations, you won.");
            }
        }
        }
    
    public static String[][] boardCreator(String boardsize){
            String m_valueString, n_valueString;
            m_valueString= boardsize.substring(0, boardsize.indexOf(" "));
            n_valueString= boardsize.substring(boardsize.indexOf("x")+2,boardsize.length());
            int m,n;
            m = Integer.parseInt(m_valueString);
            n = Integer.parseInt(n_valueString);
            String[][] board = new String [m][n];
            for(int i=0; i<board.length;i++){
                for(int j=0; j<board[0].length;j++){
                    board[i][j]="o";
                }
            }
        return board;
    }
    
    public static int[][] mapCreator(String boardsize){
        String m_valueString, n_valueString;
        m_valueString= boardsize.substring(0, boardsize.indexOf(" "));
        n_valueString= boardsize.substring(boardsize.indexOf("x")+2,boardsize.length());
        int m,n;
        m = Integer.parseInt(m_valueString);
        n = Integer.parseInt(n_valueString);
        int[][] map = new int [m][n];
        for(int i=0; i<map.length;i++){
            for(int j=0; j<map[0].length;j++){
                map[i][j]=0;
            }
        }
    return map;
    }
    
    public static int[][] mineReplacor(int[][] minemap,String difficulty){
        Random random = new Random();
        int randRow, randCol, mineNumber, count=0;
        if(difficulty.equals("E")){
            mineNumber=(minemap.length)*(minemap[0].length)*15/100;
            while(count!=mineNumber){
                randRow = random.nextInt(minemap.length);
                randCol = random.nextInt(minemap[0].length);
                if(minemap[randRow][randCol]!=-1){
                    minemap[randRow][randCol]=-1;
                    count++;
                }

            }
            }
        if(difficulty.equals("M")){
            mineNumber=(minemap.length)*(minemap[0].length)*25/100;
            while(count!=mineNumber){
                randRow = random.nextInt(minemap.length);
                randCol = random.nextInt(minemap[0].length);
                if(minemap[randRow][randCol]!=-1){
                    minemap[randRow][randCol]=-1;
                    count++;
                }
            }
        }
        if(difficulty.equals("H")){
            mineNumber=(minemap.length)*(minemap[0].length)*40/100;
            while(count!=mineNumber){
                randRow = random.nextInt(minemap.length);
                randCol = random.nextInt(minemap[0].length);
                if(minemap[randRow][randCol]!=-1){
                    minemap[randRow][randCol]=-1;
                    count++;
                }
            }
        }
        return minemap;
    }
    
    public static int minenumber(int[][] minemap,String difficulty){
        int mineNumber=0;
        if(difficulty.equals("E"))
            mineNumber=(minemap.length)*(minemap[0].length)*15/100;
        if(difficulty.equals("M"))
            mineNumber=(minemap.length)*(minemap[0].length)*25/100;   
        if(difficulty.equals("H"))
            mineNumber=(minemap.length)*(minemap[0].length)*40/100;
        return mineNumber;
    }
    
    public static int freecellnum(String[][] board){
        int freecells=0;
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]=="o")
                    freecells++;
            }
        }
        return freecells;
    }
    
    public static void print(int[][] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]+"  ");
            }
            System.out.println();
        }
    }
    
    public static void print(String[][] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    public static int[] move(String move){
        String row,col;
        int rowint, colint;
        row = move.substring(0, move.indexOf(","));
        col = move.substring(move.indexOf(",")+1,move.length());
        rowint = Integer.parseInt(row)-1;
        colint = Integer.parseInt(col)-1;
        int[] movearray = {rowint,colint};
        return movearray;
    
    }
    
    public static boolean isMine(String[][] board,int[][] map, String move){
        Boolean minecell=false;
        int[] movearray = move(move);
        if(map[movearray[0]][movearray[1]]!=-1){
            return minecell;
        }
        else{
            minecell=true;
            lostGameBoard(map, board);
            System.out.println("You lost, better luck next time.");
            return minecell;
        }
    }
    
    public static void neighbourMine(int[][] map){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j]!=-1){
                    int minenumber=0;
                    if (j<(map[0].length-1)&&map[i][j+1]==-1)
                        minenumber++;
                    if((j>0)&&map[i][j-1]==-1)
                        minenumber++;
                    if(i<(map.length-1)&&map[i+1][j]==-1)
                        minenumber++;
                    if((i>0)&&map[i-1][j]==-1)     
                        minenumber++;
                    if(j<(map[0].length-1)&&i<(map.length-1)&&map[i+1][j+1]==-1)     
                        minenumber++;
                    if(j<(map[0].length-1)&&(i>0)&&map[i-1][j+1]==-1)     
                        minenumber++;
                    if(i<(map.length-1)&&(j>0)&&map[i+1][j-1]==-1)     
                        minenumber++;
                    if((i>0)&&(j>0)&&map[i-1][j-1]==-1)     
                        minenumber++;
                    map[i][j]=minenumber;    
                }
        }
    }
    }
    
    public static void openCell(int[][] map,String[][] board,int rowint, int colint){
        if(isValid(map, rowint, colint)){
            if(map[rowint][colint]!=-1&&map[rowint][colint]!=0)
                board[rowint][colint]=Integer.toString(map[rowint][colint]);
                if((map[rowint][colint]!=-1)&&(map[rowint][colint]==0)){
                    if(board[rowint][colint]=="-")
                        return;
                    board[rowint][colint]="-";
                    openCell(map, board, rowint+1, colint);
                    openCell(map, board, rowint+1, colint-1);
                    openCell(map, board, rowint, colint-1);
                    openCell(map, board, rowint-1, colint-1);
                    openCell(map, board, rowint-1, colint);
                    openCell(map, board, rowint-1, colint+1);
                    openCell(map, board, rowint, colint+1);
                    openCell(map, board, rowint+1, colint+1);
                }
        }
    }

    public static boolean isValid(int[][] map, int row, int col){
        boolean result = false;
        if (row >= 0 && row < map.length &&col >= 0 && col < map[row].length){
            result=true;
            return result;    
        }
        else
            return result;    
    }

    public static void setFlag(String[][] map, String move){
        String row,col;
        int rowint, colint;
        row = move.substring(0, move.indexOf(","));
        col = move.substring(move.indexOf(",")+1,move.length()-1);
        rowint = Integer.parseInt(row)-1;
        colint = Integer.parseInt(col)-1;
        map[rowint][colint]="F";
    }

    public static int[] setFlagMove(String move){
        String row,col;
        int rowint, colint;
        row = move.substring(0, move.indexOf(","));
        col = move.substring(move.indexOf(",")+1,move.length()-1);
        rowint = Integer.parseInt(row)-1;
        colint = Integer.parseInt(col)-1;
        int[] movearray = {rowint,colint};
        return movearray;
    
    }

    public static void unFlag(String[][] board, String move){
        String row,col;
        int rowint, colint;
        row = move.substring(0, move.indexOf(","));
        col = move.substring(move.indexOf(",")+1,move.length()-1);
        rowint = Integer.parseInt(row)-1;
        colint = Integer.parseInt(col)-1;
        board[rowint][colint]="o";
    }

    public static int[] setUnFlagMove(String move){
        String row,col;
        int rowint, colint;
        row = move.substring(0, move.indexOf(","));
        col = move.substring(move.indexOf(",")+1,move.length()-1);
        rowint = Integer.parseInt(row)-1;
        colint = Integer.parseInt(col)-1;
        int[] movearray = {rowint,colint};
        return movearray;
    
    }

    public static void lostGameBoard(int[][] map, String[][] board){
        String [][] loserboard = new String[map.length][map[0].length];
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(board[i][j]=="o"&&map[i][j]==-1){
                    loserboard[i][j]="X";
                    System.out.print(loserboard[i][j]+" ");
                }
                if(board[i][j]=="o"&&map[i][j]!=-1){
                    System.out.print(board[i][j]+" ");
                }
                if(board[i][j]=="-"){
                    System.out.print("-"+" ");
                }
                if(board[i][j]=="F"){
                    if(map[i][j]==-1)
                        System.out.print("X"+" ");
                    else
                        System.out.print(map[i][j]+" ");
                }
                if(board[i][j]!="o"&&board[i][j]!="-"&&board[i][j]!="F"){
                    System.out.print(map[i][j]+" ");
                }

            }
            System.out.println();
        }
    }

}
