import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.random.*;
public class Game {

    Board GameBoard;
    Board previous;
    JPanel GamePanel;
    JLabel moves;
    JLabel score;
    protected int numMoves;
    
    public void wonGame() {
      JFrame a = new JFrame();
      JLabel c = new JLabel("You Won! \n Score: " + GameBoard.score());
      JButton b = new JButton("Continue Playing");
      a.add(c);
      a.add(b);
      a.setAlwaysOnTop(true);
      a.setVisible(true);
    }

    public JPanel renderBoard() {
       
        GamePanel.removeAll();
        moves.setText(""+numMoves);
        GamePanel.setLayout(new GridLayout(4,4));
        GamePanel.setBackground(Color.gray);
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                JLabel element = new JLabel(GameBoard.board[i][j]+"", SwingConstants.CENTER);
                element.setBackground(Color.RED);
                GamePanel.add(element);
            }
        }
        GamePanel.setPreferredSize(new Dimension(200,200));
        moves.setVisible(false);
        return GamePanel;
    }

    public void randGen() {
      Random r = new Random();
      int rand = r.nextInt(3);
      if(rand==1) {
        GameBoard.generateRandom();
      }
    }
   

    Game() {
       
        GameBoard= new Board();
        JFrame frame = new JFrame();
        JButton undo = new JButton("Undo One Move");
        JButton reset = new JButton("Reset and Randomize");
        undo.setEnabled(false);
       
        moves = new JLabel();
        score = new JLabel("Score: 0");
        GamePanel = new JPanel();
        GamePanel=renderBoard();
        GamePanel.setFocusable(true);
        score.setFont(new Font("Verdana", Font.BOLD, 18));
        JLabel title = new JLabel("2048");
        title.setFont(new Font("Verdana", Font.BOLD, 48));
    

        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 2, 2, 2);
        c.gridx=2;
        c.gridy=2;
        frame.add(undo,c);
        c.gridx=2;
        c.gridy=3;
        frame.add(reset,c);
        c.gridx = 0;
        c.gridy = 0;
        frame.add(title, c);
        c.gridx = 1;
        frame.add(score, c);
        c.gridx=1;
        c.gridy=1;
        frame.add(moves, c);
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 90;
        c.ipady = 90;
        
        GamePanel = renderBoard();
        previous = new Board(GameBoard);
        frame.add(GamePanel,c);
 
        frame.setFocusable(true);
        
        
        undo.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
              if(previous!=null) {
                GameBoard.board = previous.board;
                System.out.println(GameBoard.toString());
                numMoves++;
                frame.remove(GamePanel); 
                GamePanel = renderBoard();
                GamePanel.setFocusable(true);
                frame.setFocusable(true);
                frame.add(GamePanel,c);
                previous=null;
                undo.setEnabled(false);
              }
          }
      });
        reset.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
             // if(previous!=null) {
                
                GameBoard = new Board();
                score.setText("Score: 0");
                numMoves++;
                frame.remove(GamePanel); 
                GamePanel = renderBoard();
                frame.add(GamePanel,c);
                //numMoves--;
                //previous=null;
             // }
          }
      });
        frame.addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());
                int keyCode = e.getKeyCode();  // Get the key code
                previous = new Board(GameBoard);
                if (keyCode == KeyEvent.VK_UP) {
                    GameBoard.up();   
                  //  GameScore=(int)(((double) GameBoard.score()) + 1);
                    numMoves++;
                   
                  
                    
                   // System.out.println(GameBoard.toString());
                   // System.out.println("Up arrow key pressed");
                } 
                
                if (keyCode == KeyEvent.VK_DOWN) {
                    GameBoard.down();
                
                   // GameScore=GameBoard.score();
                    numMoves++;
                   
                   // System.out.println(GameBoard.toString());
                   // System.out.println("Down arrow key pressed");
                } 
                
                if (keyCode == KeyEvent.VK_RIGHT) {
                   // frame.setForeground(Color.blue);
                    GameBoard.right();
                  //  System.out.println(GameBoard.toString());
                  //  System.out.println("Right arrow key pressed");
                   // GameScore=GameBoard.score()+1;
                    numMoves++;
                  
                }  
                
                if (keyCode == KeyEvent.VK_LEFT) {
                    GameBoard.left();
                   // System.out.println(GameBoard.toString());
                   // System.out.println("Left arrow key pressed"); 
                 //   GameScore=GameBoard.score()+1;
                    numMoves++;
                  
                } 
                
                if (keyCode == KeyEvent.VK_ENTER) {
                    GameBoard.generateRandom();
                   // System.out.println(GameBoard.toString());
                   // System.out.println("Enter key pressed"); 
                    //GameScore=GameBoard.score();
                  //   GameScore=GameBoard.score()+1;
                     numMoves++;
                  
                }
             //   GameScore=GameBoard.score();
                frame.remove(GamePanel); 
                GamePanel = renderBoard();
                frame.add(GamePanel,c);
                if(!(GameBoard.equals(previous))) {
                  undo.setEnabled(true);
                 // undo.setBackground();
                  randGen();
                }
                frame.remove(GamePanel); 
                GamePanel = renderBoard();
                frame.add(GamePanel,c);
                score.setText("Score: " + GameBoard.score());
                if(GameBoard.won) {
                  wonGame();
                }
            }
          
        });
       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
        
    }
    

    public static void main(String[] args) {
        new Game();
    }
   
}
