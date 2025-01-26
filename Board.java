import javax.swing.*;  
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Board {
    final int DIM = 4;
    public Integer[][] board =  new Integer[DIM][DIM];

    private static int score=0;
    private boolean won=false;

    public Board() {
        generateRandom();
    }

    public Board(Board other) {
      for(int i=0;i<DIM;i++) {
        for(int j=0;j<DIM;j++) {
          board[i][j]=other.board[i][j];
        }
      }
    }
    public void right() { 
        horizontalSlide(1);
        horizontalCombine(1);
        horizontalSlide(1);
        horizontalCombine(1);
        horizontalSlide(1);
    }
    public void left() {
        horizontalSlide(-1);
        horizontalCombine(-1);
        horizontalSlide(-1);
        horizontalCombine(-1);
        horizontalSlide(-1);
        
    }
    public void up() {
        verticalSlide(-1);
        verticalCombine(-1);
        verticalSlide(-1);
        verticalCombine(-1);
        verticalSlide(-1);
    }
    public void down(){
        verticalSlide(1);
        verticalCombine(1);
        verticalSlide(1);
        verticalCombine(1);
        verticalSlide(1);
    }

    
    public void horizontalSlide(int direction) {
      for(int row=0; row<DIM; row++) {
            for(int col=0; col<DIM; col++) {
                if(board[row][col]!=null) {
                    horizontalSlideHelper(row, col, direction);
                }
            }
        }
    }

    private void horizontalSlideHelper(int row, int col, int direction) {
        if(direction==-1 && col==0) {return;}
        if(direction==1&&col==board.length-1) {return;}
        Integer next = board[row][col+direction];
        if(next==null) {
            board[row][col+direction]=board[row][col];
            board[row][col]=null;
            horizontalSlideHelper(row, col+direction, direction);
        }
    }

                  
    public void verticalSlide(int direction) {
        for(int row=0; row<DIM; row++) {
              for(int col=0; col<DIM; col++) {
                  if(board[row][col]!=null) {
                      verticalSlideHelper(row, col, direction);
                  }
              }
          }
      }
    private void verticalSlideHelper(int row, int col, int direction) {
        if(direction==-1 && row==0) {return;}
        if(direction==1&&row==DIM-1) {return;}
        Integer next = board[row+direction][col];
        if(next==null) {
            board[row+direction][col]=board[row][col];
            board[row][col]=null;
            verticalSlideHelper(row+direction, col, direction);
        }
    }

    public void horizontalCombine(int direction) {
        if(direction==-1) {
            for(int row=0; row<DIM;row++){
                for(int col=0;col<DIM;col++) {
                    horizontalCombineHelper(row, col,direction);
                }
            }
        }
        if(direction==1) {
            for(int row=0; row<DIM;row++){
                for(int col=DIM-1;col>-1;col--) {
                    horizontalCombineHelper(row, col,direction);
                }
            }
        }
    }

    private void horizontalCombineHelper(int row, int col, int direction) {
        if((col==0) && (direction==1))return;
        if((col==DIM-1) && (direction==-1)) return;
        Integer val = board[row][col];
        if(val!=null) {
            for(int i=1; i<DIM-1; i++) {
                Integer next = board[row][col-direction];
                    if(next!=null) {
                        if( val.equals(next)) {
                            board[row][col]*=2;
                            score+=val*2;
                            board[row][col-direction]=null;
                            horizontalSlide(direction);
                            if(val*2==2048) gameWon();
                            return;
                        }
                    }
            }
        }
    }


    public void verticalCombine(int direction) {
        if(direction==-1) {
            for(int row=0;row<DIM;row++) {
                for(int i=0;i<DIM;i++) {
                    verticalCombineHelper(row, i,direction);
                }
            }
        }
        if(direction==1) {
            for(int row=DIM-1; row>-1;row--){
                for(int i=0;i<DIM;i++) {
                    verticalCombineHelper(row, i,direction);
                }
            }
        }
    }

    private void verticalCombineHelper(int row, int col, int direction) {
        if((row==0) && (direction==-1))return;
        if((row==DIM-1) && (direction==1)) return;
        Integer val = board[row][col];
        if(val!=null) {
            for(int i=1; i<DIM-1; i++) {
                Integer next = board[row+direction][col];
                    if(next!=null) {
                        if(val.equals(next)) {
                            board[row][col]*=2;
                            score+=val*2;
                            board[row+direction][col]=null;
                            verticalSlide(direction);
                            if(val*2==2048) gameWon();
                            return;
                        }
                    }
            }
        }
    }
    public String toString() {
        String str = "";
        for(int i=0; i<DIM; i++) {
            for(int j=0; j<DIM; j++) {
                if(board[i][j]!=null) {
                    str+=board[i][j]+", ";
                } else {
                    str+="0, ";
                }  
            }
            str+="\n";
        }
        return str;
    }

    public boolean isFull() {
        boolean matchesExist=false;
        for(int row=0;row<DIM;row++) {
            for(int col=0;col<DIM;col++) {
               if(board[row][col]==null) return false; // empty space exists
               Integer val = board[row][col];
               try {
               if(val.equals(board[row-1][col])) matchesExist=true;
               if(val.equals(board[row+1][col])) matchesExist=true;
               if(val.equals(board[row][col-1])) matchesExist=true;
               if(val.equals(board[row][col+1])) matchesExist=true;
               } catch (IndexOutOfBoundsException e) {}
            }
        }
        return !matchesExist;
    }

    public void generateRandom() {
        if(!(noEmptySpacesLeft())) {
        try {
            Random r = new Random();
            Integer val = (r.nextInt(2))*2 + 2;
            int row = r.nextInt(3);
            int col = r.nextInt(3);
            if(board[row][col]!=null) throw new IllegalStateException();
            board[row][col]=val;
        } catch (IllegalStateException e) {
            generateRandom();
        }
    }
    }

    public void gameWon() {
      won = true;


    }
    public Integer[][] board(){
        return this.board;
    }
    public int score() {
        return Board.score;
    }

    public boolean noEmptySpacesLeft() {
        for(Integer[] row: this.board) {
            for(Integer i: row) {
                if(i==null) {return false;}
            }
        }
        return true;
    }

    public static void main(String[] args) {
        
    }
}