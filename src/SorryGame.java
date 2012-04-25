/*
 * SRGUI.java
 * This class will handle all visual components and movements of images.
 * It is well separated from the game logic and interacts with SRGameBoard through SRDriver.
 * 
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * This class will handle all visual components and movements of images. It is
 * well separated from the game logic and interacts with SRGameBoard through
 * SRDriver.
 * 
 * @author Sam Brown, Caleb Cousins, Taylor Krammen, and Yucan Zhang
 * 
 */
public class SorryGame extends JApplet {
	
	//debug
	private static boolean debug = true;

	private static final int delayLength = 100;
	
	// Variables declaration
	//private JFrame frame;
	private JLabel authorNameLabel;
	private JScrollPane infoAreaPane;
	private JTextArea infoArea;
	private javax.swing.JPanel computerTypeChoicePane;
    private javax.swing.JLabel computerTypeQuestionLabel;
	private JButton startNewGameBtn, quitBtn, statisticsBtn, niceComputerBtn, meanComputerBtn;
	private JLabel gameBoardImageLabel;
	private JLayeredPane gameBoardLayeredPane;
	private JButton redPawn[] = new JButton[4];
	private JButton yellowPawn[] = new JButton[4];
	private JPanel redHomePane;
	private JPanel transparentButtonPane;
	private JPanel transparentSquarePane[] = new JPanel[225];
	private JButton outerTrackButtons[] = new JButton[56];
	private JPanel redStartPane;
	private JPanel redStartSquarePane[] = new JPanel[4];
	private JPanel yellowStartPane;
	private JPanel yellowStartSquarePane[] = new JPanel[4];
	private JPanel yellowHomePane;
	private JButton yellowHomeButton;
	private JPanel redSafetyZonePane, yellowSafetyZonePane;
	private JPanel redSafetyZoneSquarePane[] = new JPanel[5];
	private JPanel yellowSafetyZoneSquarePane[] = new JPanel[5];
	private JButton yellowSafetyZoneButtons[] = new JButton[5];
	private int outerTrackPaneIndex[] = new int[56];
	private GridLayout transparentSquarePaneLayout[] = new GridLayout[56];
	private GridLayout redSafetyZoneSquarePaneLayout[] = new GridLayout[5];
	private GridLayout yellowSafetyZoneSquarePaneLayout[] = new GridLayout[5];
	private GroupLayout redStartSquarePaneLayout[] = new GroupLayout[4];
	private GroupLayout yellowStartSquarePaneLayout[] = new GroupLayout[4];
	private JPanel drawCardPane, displayCardPane;
	private JButton drawCardBtn;
	private JLabel displayCardLabel;
	private String currentCardImage;
	private Object[] options = {"Nice", "Mean"};  
	private int computerType;
	
	public int selectedSquareIndex;
	public boolean pawnSelected;
	public boolean squareBtnSelected;
	public boolean successfulMove;
	public boolean cardDrawn;
	public boolean started;
	
	private final int NICE_COMPUTER = 0;
	private final int MEAN_COMPUTER = 1;
	
	private final int COMPUTER = 0;
	private final int USER = 1;		
	
	private int currentPlayer;
	private SRCard currentCard;
	private int currentPawnIndex;
	
	private SRGameBoard board;
	private SRComputer computer;
	// End of variables declaration


	public void init() {
		    //Execute a job on the event-dispatching thread:
		    //creating this applet's GUI.
		    //try {
		    //    javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
		    //        public void run() {
		    //           playGame();
		    //        }
		    //    });
		    //} catch (Exception e) {
		    //    System.err.println("playGame didn't successfully complete");
		    //}

		playGame();
	}
	
	/**
	 * Creates new form SRGUI
	 */
	public void playGame() {
		initComponents();
		board = new SRGameBoard();  
        /***************************************************************************************/
 		//computerType = NICE_COMPUTER;
		started = false;
 		pawnSelected = false;
  		currentPawnIndex = -1;
 		squareBtnSelected = false;
 		cardDrawn = false;
 		successfulMove = false;
 		selectedSquareIndex = -2;
        
 		boolean continued = false;
 		/***************************************************************************************/
        
        //the default first player is the user
  		currentPlayer = USER;
  		int numMoves = 0;
  		
  		computer = new SRComputer();
  		
  		do{}
  		while(!started);
  		/*//initialize the computer type based on the user input
  		if (gameGUI.getComputerType() == NICE_COMPUTER) {
  			currentComp = new SRNiceComputer();
  		}
  		else if (gameGUI.getComputerType() == MEAN_COMPUTER) {
  			currentComp = new SRMeanComputer();
  		}*/
  		
  		//if not won, take turns to play the game
  		do {
  			//System.out.println("new card drawn");
  			
  			if (currentPlayer == COMPUTER) {
  				  				
  				computerPlay();
  				if (currentCard.getcardNum() == 2)
  					continued = computerPlay();
  				currentPlayer = USER;
  			}
  			else if (currentPlayer == USER) {
  				
  				//System.out.println("computer played");
  				continued = userPlay();
  				//for (int i=0;i<card.rules.length; i++){
  				//	if card.rules[i].numTurns > 1
  				//		userPlay()
  				//}
  				if (currentCard.getcardNum() == 2)
  					continued = userPlay();
  				
  				currentPlayer = COMPUTER;
  			}
  			try {
  				Thread.sleep(delayLength);
  			} catch (InterruptedException e) {
  				e.printStackTrace();
  			}

  		} while(continued && !board.hasWon(currentPlayer));
  		
  		showCongratulationDialog();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		// set up the frame
		//frame = new JFrame();
		//setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
		setName("SORRY! Game - SB, CC, TK, YZ Group - CS205 Final Project");
		setFont(new Font("Comic Sans MS", 0, 12));
		setPreferredSize(new Dimension(781, 580));
		/***************************************************************************************/

		// create authour information.
		// It's not displayed in current window, because the window has to fit
		// the 800*600 screen.
		authorNameLabel = new JLabel();
		authorNameLabel.setFont(new Font("Comic Sans MS", 0, 12));
		authorNameLabel.setText("SORRY! Game - SB, CC, TK, YZ");
		authorNameLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		/***************************************************************************************/

		// create text field for displaying tooltip or help information
		infoArea = new JTextArea();
		infoAreaPane = new JScrollPane();
		infoAreaPane.setPreferredSize(new Dimension(200, 400));
		infoArea.setColumns(10);
		infoArea.setRows(5);
		infoAreaPane.setViewportView(infoArea);
		/***************************************************************************************/

		// create the button for starting a new game
		startNewGameBtn = new JButton();
		startNewGameBtn.setFont(new Font("Comic Sans MS", 0, 12));
		startNewGameBtn.setText("Start a new game");
		startNewGameBtn.setActionCommand("start");
		startNewGameBtn.setMaximumSize(new Dimension(150, 35));
		startNewGameBtn.setMinimumSize(new Dimension(150, 35));
		startNewGameBtn.setPreferredSize(new Dimension(150, 35));
		startNewGameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				startNewGameBtnActionPerformed(evt);
			}
		});
		/***************************************************************************************/

		// create the button for quiting the game
		quitBtn = new JButton();
		quitBtn.setFont(new Font("Comic Sans MS", 0, 12));
		quitBtn.setText("Quit");
		quitBtn.setActionCommand("quit");
		quitBtn.setMaximumSize(new Dimension(80, 35));
		quitBtn.setMinimumSize(new Dimension(80, 35));
		quitBtn.setPreferredSize(new Dimension(80, 35));
		quitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				quitBtnActionPerformed(evt);
			}
		});
		/***************************************************************************************/

		//create the button for checking statistics
		statisticsBtn = new JButton();
		statisticsBtn.setFont(new Font("Comic Sans MS", 0, 12));
        statisticsBtn.setText("Statistics");
        statisticsBtn.setActionCommand("statistics");
        statisticsBtn.setMaximumSize(new java.awt.Dimension(90, 35));
        statisticsBtn.setMinimumSize(new java.awt.Dimension(90, 35));
        statisticsBtn.setPreferredSize(new java.awt.Dimension(90, 35));
        statisticsBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		statisticsBtnActionPerformed(evt);
        	}
        });
		/***************************************************************************************/

        computerTypeChoicePane = new JPanel();
        computerTypeChoicePane.setBorder(BorderFactory.createTitledBorder(null, "Choose computer type", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Comic Sans MS", 0, 10)));

        computerTypeQuestionLabel = new JLabel();
        computerTypeQuestionLabel.setFont(new Font("Comic Sans MS", 0, 12)); 
        computerTypeQuestionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        computerTypeQuestionLabel.setText("<html>Which type of computer you want to chanllenge?<html>");
        
        niceComputerBtn = new JButton();
        niceComputerBtn.setFont(new Font("Comic Sans MS", 0, 12));
        niceComputerBtn.setText("Nice");
        niceComputerBtn.setToolTipText("Click to choose a nice computer");
        niceComputerBtn.setActionCommand("0");
        niceComputerBtn.setPreferredSize(new java.awt.Dimension(61, 27));
        niceComputerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                niceComputerBtnActionPerformed(evt);
            }
        });
        niceComputerBtn.setEnabled(false);
        
        meanComputerBtn = new JButton();
        meanComputerBtn.setFont(new Font("Comic Sans MS", 0, 12));
        meanComputerBtn.setText("Mean");
        meanComputerBtn.setToolTipText("Click to choose a mean computer");
        meanComputerBtn.setActionCommand("1");
        meanComputerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meanComputerBtnActionPerformed(evt);
            }
        });
        meanComputerBtn.setEnabled(false);
        
        GroupLayout computerTypeChoicePaneLayout = new GroupLayout(computerTypeChoicePane);
        computerTypeChoicePane.setLayout(computerTypeChoicePaneLayout);
        computerTypeChoicePaneLayout.setHorizontalGroup(
            computerTypeChoicePaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(computerTypeChoicePaneLayout.createSequentialGroup()
                .addGroup(computerTypeChoicePaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(computerTypeChoicePaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(niceComputerBtn)
                        .addGap(18, 18, 18)
                        .addComponent(meanComputerBtn))
                    .addGroup(computerTypeChoicePaneLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(computerTypeQuestionLabel, GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        computerTypeChoicePaneLayout.setVerticalGroup(
            computerTypeChoicePaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(computerTypeChoicePaneLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(computerTypeQuestionLabel, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(computerTypeChoicePaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(niceComputerBtn)
                    .addComponent(meanComputerBtn))
                .addContainerGap(20, Short.MAX_VALUE))
        );
		/***************************************************************************************/

        // central layered panel holds the game board picture and all components
		gameBoardLayeredPane = new JLayeredPane();
		gameBoardLayeredPane.setPreferredSize(new Dimension(525, 525));

		// set up the game board image as the background
		gameBoardImageLabel = new JLabel();
		gameBoardImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameBoardImageLabel.setIcon(new ImageIcon("C:/Users/xsong/Sorry/SRgameBoardImage.jpg")); // NOI18N
		gameBoardImageLabel.setPreferredSize(new Dimension(525, 525));
		gameBoardImageLabel.setBounds(0, 0, 525, 525);
		gameBoardLayeredPane.add(gameBoardImageLabel,
				JLayeredPane.DEFAULT_LAYER);
		/***************************************************************************************/

		// the panel holds all track squares with transparent buttons
		transparentButtonPane = new JPanel();
		transparentButtonPane.setBounds(0, 0, 525, 525);
		gameBoardLayeredPane.add(transparentButtonPane,
				JLayeredPane.PALETTE_LAYER);
		transparentButtonPane.setOpaque(false);
		transparentButtonPane.setLayout(new GridLayout(0, 15));
		for (int i = 0; i < 225; i++) {
			transparentSquarePane[i] = new JPanel();
			transparentSquarePane[i].setOpaque(false);
			// transparentSquarePane[i].setBorder(BorderFactory.createRaisedBevelBorder());
			transparentButtonPane.add(transparentSquarePane[i]);
		}
		/***************************************************************************************/

		// set up buttons on outer track.
		for (int i = 0; i < 56; i++) {
			outerTrackButtons[i] = new JButton();
			outerTrackButtons[i].setOpaque(false);
			outerTrackButtons[i].setMaximumSize(new Dimension(35, 35));
			outerTrackButtons[i].setMinimumSize(new Dimension(35, 35));
			outerTrackButtons[i].setPreferredSize(new Dimension(35, 35));
			outerTrackButtons[i].setActionCommand("" + i);
			outerTrackButtons[i].addActionListener(new SquareButtonListener());
			outerTrackButtons[i].setEnabled(false);
		}
		for (int i = 0; i < 15; i++) {
			transparentSquarePaneLayout[i] = new GridLayout();
			transparentSquarePane[i].setLayout(transparentSquarePaneLayout[i]);
			transparentSquarePane[i].add(outerTrackButtons[i]);
			outerTrackPaneIndex[i] = i;
		}
		for (int i = 15, j = 29; i < 29 && j < 225; i++, j += 15) {
			transparentSquarePaneLayout[i] = new GridLayout();
			transparentSquarePane[j].setLayout(transparentSquarePaneLayout[i]);
			transparentSquarePane[j].add(outerTrackButtons[i]);
			outerTrackPaneIndex[i] = j;
		}
		for (int i = 29, j = 223; i < 43 && j > 209; i++, j--) {
			transparentSquarePaneLayout[i] = new GridLayout();
			transparentSquarePane[j].setLayout(transparentSquarePaneLayout[i]);
			transparentSquarePane[j].add(outerTrackButtons[i]);
			outerTrackPaneIndex[i] = j;
		}
		for (int i = 43, j = 195; i < 56 && j > 14; i++, j -= 15) {
			transparentSquarePaneLayout[i] = new GridLayout();
			transparentSquarePane[j].setLayout(transparentSquarePaneLayout[i]);
			transparentSquarePane[j].add(outerTrackButtons[i]);
			outerTrackPaneIndex[i] = j;
		}
		// end of creating outer track buttons
		/***************************************************************************************/

		// set the layout for the frame, add and align all the components.
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		authorNameLabel,
																		GroupLayout.PREFERRED_SIZE,
																		185,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(286,
																		286,
																		286))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		gameBoardLayeredPane,
																		GroupLayout.PREFERRED_SIZE,
																		525,
																		GroupLayout.PREFERRED_SIZE)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										infoAreaPane,
																										GroupLayout.PREFERRED_SIZE,
																										200,
																										GroupLayout.PREFERRED_SIZE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(64,
																										64,
																										64)
																								.addComponent(
																										quitBtn,
																										GroupLayout.PREFERRED_SIZE,
																										80,
																										GroupLayout.PREFERRED_SIZE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(53,
																										53,
																										53)
																                                .addComponent(
																                                		statisticsBtn, 
																                                		GroupLayout.PREFERRED_SIZE, 
																                                		100,		
																                                		GroupLayout.PREFERRED_SIZE))
																                .addGroup(
																						layout.createSequentialGroup()
																								.addGap(30,
																										30,
																										30)
																								.addComponent(
																										startNewGameBtn,
																										GroupLayout.PREFERRED_SIZE,
																										150,
																										GroupLayout.PREFERRED_SIZE))
													                            .addGroup(
													                            		layout.createSequentialGroup()
													                            				.addGap(8, 8, 8)
													                            				.addComponent(computerTypeChoicePane, 
													                            						GroupLayout.PREFERRED_SIZE, 
													                            						190, 
													                            						GroupLayout.PREFERRED_SIZE)
													                            				.addContainerGap())))
																.addGap(22, 22, 22))));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(authorNameLabel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										0,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		infoAreaPane,
																		GroupLayout.PREFERRED_SIZE,
																		150,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18,
																		18)
																.addComponent(
																		quitBtn,
																		GroupLayout.PREFERRED_SIZE,
																		35,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18,
																		18)
																.addComponent(
																		statisticsBtn,
																		GroupLayout.PREFERRED_SIZE, 
																		35, 
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18,
																		18)
																.addComponent(
																		startNewGameBtn,
																		GroupLayout.PREFERRED_SIZE,
																		35,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(30, 30,
																		30)
																.addComponent(
																		computerTypeChoicePane, 
																		GroupLayout.PREFERRED_SIZE, 
																		GroupLayout.DEFAULT_SIZE, 
																		GroupLayout.PREFERRED_SIZE))
																.addComponent(
																		gameBoardLayeredPane,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		525,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(30, 30, 30))
								.addContainerGap(0, Short.MAX_VALUE)));
		/***************************************************************************************/
		/* end of setting up main components */
		/***************************************************************************************/

		// set up the red pawns
		for (int i = 0; i < 4; i++) {
			redPawn[i] = new JButton();
			redPawn[i].setIcon(new ImageIcon("C:/Users/xsong/Sorry/redPawn.jpg")); // NOI18N
			redPawn[i].setActionCommand("" + i);
			redPawn[i].setDisabledIcon(new ImageIcon("C:/Users/xsong/Sorry/redPawn.jpg"));
			redPawn[i].setMaximumSize(new Dimension(35, 35));
			redPawn[i].setMinimumSize(new Dimension(35, 35));
			redPawn[i].setPreferredSize(new Dimension(35, 35));
		}
		/***************************************************************************************/

		// set up the start panel for the red player
		redStartPane = new JPanel();
		redStartPane.setMaximumSize(new Dimension(70, 70));
		redStartPane.setMinimumSize(new Dimension(70, 70));
		redStartPane.setOpaque(false);
		redStartPane.setPreferredSize(new Dimension(70, 70));
		redStartPane.setLayout(new GridLayout(0, 2));
		redStartPane.setBounds(122, 45, 70, 70);
		gameBoardLayeredPane.add(redStartPane, JLayeredPane.MODAL_LAYER);
		for (int i = 0; i < 4; i++) {
			redStartSquarePane[i] = new JPanel();
			redStartSquarePane[i].setOpaque(false);
			redStartSquarePaneLayout[i] = new GroupLayout(redStartSquarePane[i]);
			redStartSquarePane[i].setLayout(redStartSquarePaneLayout[i]);
			redStartSquarePaneLayout[i]
					.setHorizontalGroup(redStartSquarePaneLayout[i]
							.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(
									redStartSquarePaneLayout[i]
											.createSequentialGroup()
											.addComponent(redPawn[i],
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addGap(0, 0, Short.MAX_VALUE)));
			redStartSquarePaneLayout[i]
					.setVerticalGroup(redStartSquarePaneLayout[i]
							.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(
									redStartSquarePaneLayout[i]
											.createSequentialGroup()
											.addComponent(redPawn[i],
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addGap(0, 0, Short.MAX_VALUE)));

			redStartPane.add(redStartSquarePane[i]);
		}
		/***************************************************************************************/

		// set up the home panel for the red player
		redHomePane = new JPanel();
		redHomePane.setOpaque(false);
		redHomePane.setLayout(new FlowLayout(FlowLayout.CENTER));
		redHomePane.setBounds(50, 190, 90, 90);
		gameBoardLayeredPane.add(redHomePane, JLayeredPane.MODAL_LAYER);
		/***************************************************************************************/

		// set up the safety zone panel for the red player
		redSafetyZonePane = new JPanel();
		redSafetyZonePane.setMaximumSize(new Dimension(35, 165));
		redSafetyZonePane.setMinimumSize(new Dimension(35, 165));
		redSafetyZonePane.setOpaque(false);
		redSafetyZonePane.setLayout(new GridLayout(0, 1));
		redSafetyZonePane.setBounds(70, 35, 35, 165);
		gameBoardLayeredPane.add(redSafetyZonePane, JLayeredPane.MODAL_LAYER);
		// set up the red safety zone squares
		for (int i = 0; i < 5; i++) {
			redSafetyZoneSquarePane[i] = new JPanel();
			redSafetyZoneSquarePane[i].setMaximumSize(new Dimension(35, 35));
			redSafetyZoneSquarePane[i].setMinimumSize(new Dimension(35, 35));
			redSafetyZoneSquarePane[i].setOpaque(false);
			redSafetyZoneSquarePane[i].setPreferredSize(new Dimension(35, 35));

			redSafetyZoneSquarePaneLayout[i] = new GridLayout();
			redSafetyZoneSquarePane[i].setLayout(redSafetyZoneSquarePaneLayout[i]);

			redSafetyZonePane.add(redSafetyZoneSquarePane[i]);
		}
		/***************************************************************************************/
		/* end of setting up components specific for red pawns */
		/***************************************************************************************/

		// set up the yellow pawns
		for (int i = 0; i < 4; i++) {
			yellowPawn[i] = new JButton();
			yellowPawn[i].setIcon(new javax.swing.ImageIcon("C:/Users/xsong/Sorry/yellowPawn.jpg")); // NOI18N
			yellowPawn[i].setActionCommand("" + (4 + i));
			yellowPawn[i].setDisabledIcon(new ImageIcon("C:/Users/xsong/Sorry/yellowPawn.jpg"));
			yellowPawn[i].setMaximumSize(new java.awt.Dimension(35, 35));
			yellowPawn[i].setMinimumSize(new java.awt.Dimension(35, 35));
			yellowPawn[i].setPreferredSize(new java.awt.Dimension(35, 35));
			yellowPawn[i].addActionListener(new PawnButtonListener());
		}
		/***************************************************************************************/

		// set up the start panel for the yellow player
		yellowStartPane = new JPanel();
		yellowStartPane.setMaximumSize(new Dimension(70, 70));
		yellowStartPane.setMinimumSize(new Dimension(70, 70));
		yellowStartPane.setOpaque(false);
		yellowStartPane.setPreferredSize(new Dimension(70, 70));
		yellowStartPane.setLayout(new GridLayout(0, 2));
		yellowStartPane.setBounds(330, 410, 70, 70);
		gameBoardLayeredPane.add(yellowStartPane, JLayeredPane.MODAL_LAYER);
		for (int i = 0; i < 4; i++) {
			yellowStartSquarePane[i] = new JPanel();
			yellowStartSquarePane[i].setOpaque(false);
			yellowStartSquarePaneLayout[i] = new GroupLayout(
					yellowStartSquarePane[i]);
			yellowStartSquarePane[i].setLayout(yellowStartSquarePaneLayout[i]);
			yellowStartSquarePaneLayout[i]
					.setHorizontalGroup(yellowStartSquarePaneLayout[i]
							.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(
									yellowStartSquarePaneLayout[i]
											.createSequentialGroup()
											.addComponent(yellowPawn[i],
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addGap(0, 0, Short.MAX_VALUE)));
			yellowStartSquarePaneLayout[i]
					.setVerticalGroup(yellowStartSquarePaneLayout[i]
							.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(
									yellowStartSquarePaneLayout[i]
											.createSequentialGroup()
											.addComponent(yellowPawn[i],
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addGap(0, 0, Short.MAX_VALUE)));

			yellowStartPane.add(yellowStartSquarePane[i]);
		}
		/***************************************************************************************/

		// set up the home panel for the yellow player
		yellowHomePane = new JPanel();
		yellowHomePane.setOpaque(false);
		yellowHomePane.setLayout(new FlowLayout(FlowLayout.CENTER));
		yellowHomePane.setBounds(390, 240, 90, 90);
		gameBoardLayeredPane.add(yellowHomePane, JLayeredPane.MODAL_LAYER);

		yellowHomeButton = new JButton();
		yellowHomeButton.setOpaque(false);
		yellowHomeButton.setMaximumSize(new Dimension(35, 35));
		yellowHomeButton.setMinimumSize(new Dimension(35, 35));
		yellowHomeButton.setPreferredSize(new Dimension(35, 35));
		yellowHomeButton.setActionCommand("" + 67); // track square index
		yellowHomeButton.addActionListener(new SquareButtonListener());
		yellowHomeButton.setEnabled(false);
		/***************************************************************************************/

		// set up the safety zone panel for the yellow player
		yellowSafetyZonePane = new JPanel();
		yellowSafetyZonePane.setMaximumSize(new Dimension(35, 165));
		yellowSafetyZonePane.setMinimumSize(new Dimension(35, 165));
		yellowSafetyZonePane.setOpaque(false);
		yellowSafetyZonePane.setLayout(new GridLayout(0, 1));
		yellowSafetyZonePane.setBounds(420, 325, 35, 165);
		gameBoardLayeredPane
				.add(yellowSafetyZonePane, JLayeredPane.MODAL_LAYER);
		// Set up the yellow safety zone squares
		for (int i = 4; i >= 0; i--) {
			yellowSafetyZoneSquarePane[i] = new JPanel();
			yellowSafetyZoneSquarePane[i].setMaximumSize(new Dimension(35, 35));
			yellowSafetyZoneSquarePane[i].setMinimumSize(new Dimension(35, 35));
			yellowSafetyZoneSquarePane[i].setOpaque(false);
			yellowSafetyZoneSquarePane[i]
					.setPreferredSize(new Dimension(35, 35));

			yellowSafetyZoneSquarePaneLayout[i] = new GridLayout();
			yellowSafetyZoneSquarePane[i].setLayout(yellowSafetyZoneSquarePaneLayout[i]);

			yellowSafetyZonePane.add(yellowSafetyZoneSquarePane[i]);
		}
		// set up the safety zone buttons for the yellow player
		for (int i = 0; i < 5; i++) {
			yellowSafetyZoneButtons[i] = new JButton();
			yellowSafetyZoneButtons[i].setOpaque(false);
			yellowSafetyZoneButtons[i].setMaximumSize(new Dimension(35, 35));
			yellowSafetyZoneButtons[i].setMinimumSize(new Dimension(35, 35));
			yellowSafetyZoneButtons[i].setPreferredSize(new Dimension(35, 35));
			yellowSafetyZoneButtons[i].setActionCommand("" + (i + 62));
			yellowSafetyZoneButtons[i]
					.addActionListener(new SquareButtonListener());
			yellowSafetyZoneButtons[i].setEnabled(false);
		}
		for (int i = 0; i < 5; i++) {
			yellowSafetyZoneSquarePane[i].add(yellowSafetyZoneButtons[i]);
		}
		/***************************************************************************************/
		/* end of setting up components specific for yellow pawns */
		/***************************************************************************************/

		
		// set up the panel for displaying the draw_a_card button
		drawCardPane = new JPanel();
		drawCardBtn = new JButton();
		drawCardPane.setMinimumSize(new java.awt.Dimension(90, 125));
		drawCardPane.setPreferredSize(new java.awt.Dimension(90, 125));
		drawCardPane.setBounds(150, 280, 90, 125);
		gameBoardLayeredPane.add(drawCardPane, JLayeredPane.MODAL_LAYER);
		// create the drawCardBtn for drawing a card
		drawCardBtn.setIcon(new ImageIcon("C:/Users/xsong/Sorry/drawACard.jpg"));
		drawCardBtn.setToolTipText("Click to draw a card.");
		drawCardBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		drawCardBtn.setMaximumSize(new Dimension(90, 125));
		drawCardBtn.setMinimumSize(new Dimension(90, 125));
		drawCardBtn.setPreferredSize(new Dimension(90, 125));
		drawCardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				drawCardBtnActionPerformed(evt);
			}
		});
		// set up the layout of drawCardPane and add the button into the pane
		GroupLayout drawCardPaneLayout = new GroupLayout(drawCardPane);
		drawCardPane.setLayout(drawCardPaneLayout);
		drawCardPaneLayout.setHorizontalGroup(drawCardPaneLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						drawCardPaneLayout
								.createSequentialGroup()
								.addComponent(drawCardBtn,
										GroupLayout.PREFERRED_SIZE, 90,
										GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)));
		drawCardPaneLayout.setVerticalGroup(drawCardPaneLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						drawCardPaneLayout
								.createSequentialGroup()
								.addComponent(drawCardBtn,
										GroupLayout.PREFERRED_SIZE, 125,
										GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)));
		/***************************************************************************************/

		// set up the label and panel for displaying the newly drawn card.
		displayCardPane = new JPanel();
		displayCardLabel = new JLabel();
		displayCardPane.setBounds(270, 100, 120, 164);
		displayCardPane.setOpaque(false);
		gameBoardLayeredPane.add(displayCardPane, JLayeredPane.MODAL_LAYER);
		currentCardImage = "";
		displayCardLabel
				.setToolTipText("This is the card displaying the moves and rules");
		displayCardLabel.setMaximumSize(new Dimension(120, 164));
		displayCardLabel.setMinimumSize(new Dimension(120, 164));
		displayCardLabel.setPreferredSize(new Dimension(120, 164));
		displayCardLabel.setOpaque(false);
		GroupLayout displayCardPaneLayout = new GroupLayout(displayCardPane);
		displayCardPane.setLayout(displayCardPaneLayout);
		displayCardPaneLayout.setHorizontalGroup(displayCardPaneLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(displayCardLabel, GroupLayout.DEFAULT_SIZE, 120,
						Short.MAX_VALUE));
		displayCardPaneLayout.setVerticalGroup(displayCardPaneLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(displayCardLabel, GroupLayout.DEFAULT_SIZE, 164,
						Short.MAX_VALUE));
		/***************************************************************************************/

		//pack();
	}// end of initComponents()


	public void resetComponents() {
		// set up the frame
		//frame = new JFrame();
		//setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		/*setVisible(true);
		setName("SORRY! Game - SB, CC, TK, YZ Group - CS205 Final Project");
		setFont(new Font("Comic Sans MS", 0, 12));
		setPreferredSize(new Dimension(781, 580));*/
		/***************************************************************************************/

		// create author information.
		// It's not displayed in current window, because the window has to fit
		// the 800*600 screen.
		/*authorNameLabel.setFont(new Font("Comic Sans MS", 0, 12));
		authorNameLabel.setText("SORRY! Game - SB, CC, TK, YZ");
		authorNameLabel.setVerticalTextPosition(SwingConstants.BOTTOM);*/
		/***************************************************************************************/

		// create text field for displaying tooltip or help information
		/*infoAreaPane = new JScrollPane();
		infoAreaPane.setPreferredSize(new Dimension(200, 400));
		infoArea.setColumns(10);
		infoArea.setRows(5);
		infoAreaPane.setViewportView(infoArea);*/
		/***************************************************************************************/

		// create the button for starting a new game
		/*startNewGameBtn.setFont(new Font("Comic Sans MS", 0, 12));
		startNewGameBtn.setText("Start a new game");
		startNewGameBtn.setActionCommand("start");
		startNewGameBtn.setMaximumSize(new Dimension(150, 35));
		startNewGameBtn.setMinimumSize(new Dimension(150, 35));
		startNewGameBtn.setPreferredSize(new Dimension(150, 35));
		startNewGameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				startNewGameBtnActionPerformed(evt);
			}
		});*/
		/***************************************************************************************/

		// create the button for quiting the game
		/*quitBtn.setFont(new Font("Comic Sans MS", 0, 12));
		quitBtn.setText("Quit");
		quitBtn.setActionCommand("quit");
		quitBtn.setMaximumSize(new Dimension(80, 35));
		quitBtn.setMinimumSize(new Dimension(80, 35));
		quitBtn.setPreferredSize(new Dimension(80, 35));
		quitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				quitBtnActionPerformed(evt);
			}
		});*/
		/***************************************************************************************/

		//create the button for checking statistics
		/*statisticsBtn.setFont(new Font("Comic Sans MS", 0, 12));
        statisticsBtn.setText("Statistics");
        statisticsBtn.setActionCommand("statistics");
        statisticsBtn.setMaximumSize(new java.awt.Dimension(90, 35));
        statisticsBtn.setMinimumSize(new java.awt.Dimension(90, 35));
        statisticsBtn.setPreferredSize(new java.awt.Dimension(90, 35));
        statisticsBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		statisticsBtnActionPerformed(evt);
        	}
        });*/
		/***************************************************************************************/
		/*
        computerTypeChoicePane.setBorder(BorderFactory.createTitledBorder(null, "Choose computer type", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Comic Sans MS", 0, 10)));

        computerTypeQuestionLabel.setFont(new Font("Comic Sans MS", 0, 12)); 
        computerTypeQuestionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        computerTypeQuestionLabel.setText("<html>Which type of computer you want to chanllenge?<html>");
        
        niceComputerBtn.setFont(new Font("Comic Sans MS", 0, 12));
        niceComputerBtn.setText("Nice");
        niceComputerBtn.setToolTipText("Click to choose a nice computer");
        niceComputerBtn.setActionCommand("0");
        niceComputerBtn.setPreferredSize(new java.awt.Dimension(61, 27));
        niceComputerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                niceComputerBtnActionPerformed(evt);
            }
        });
        niceComputerBtn.setEnabled(false);
        
        meanComputerBtn.setFont(new Font("Comic Sans MS", 0, 12));
        meanComputerBtn.setText("Mean");
        meanComputerBtn.setToolTipText("Click to choose a mean computer");
        meanComputerBtn.setActionCommand("1");
        meanComputerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meanComputerBtnActionPerformed(evt);
            }
        });
        meanComputerBtn.setEnabled(false);
        
        GroupLayout computerTypeChoicePaneLayout = new GroupLayout(computerTypeChoicePane);
        computerTypeChoicePane.setLayout(computerTypeChoicePaneLayout);
        computerTypeChoicePaneLayout.setHorizontalGroup(
            computerTypeChoicePaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(computerTypeChoicePaneLayout.createSequentialGroup()
                .addGroup(computerTypeChoicePaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(computerTypeChoicePaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(niceComputerBtn)
                        .addGap(18, 18, 18)
                        .addComponent(meanComputerBtn))
                    .addGroup(computerTypeChoicePaneLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(computerTypeQuestionLabel, GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        computerTypeChoicePaneLayout.setVerticalGroup(
            computerTypeChoicePaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(computerTypeChoicePaneLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(computerTypeQuestionLabel, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(computerTypeChoicePaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(niceComputerBtn)
                    .addComponent(meanComputerBtn))
                .addContainerGap(20, Short.MAX_VALUE))
        );*/
		/***************************************************************************************/
		/*
        // central layered panel holds the game board picture and all components
		gameBoardLayeredPane.setPreferredSize(new Dimension(525, 525));

		// set up the game board image as the background
		gameBoardImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameBoardImageLabel.setIcon(new ImageIcon("C:/Users/xsong/Sorry/SRgameBoardImage.jpg")); // NOI18N
		gameBoardImageLabel.setPreferredSize(new Dimension(525, 525));
		gameBoardImageLabel.setBounds(0, 0, 525, 525);
		gameBoardLayeredPane.add(gameBoardImageLabel,
				JLayeredPane.DEFAULT_LAYER);
		/***************************************************************************************/
		/*
		// the panel holds all track squares with transparent buttons
		transparentButtonPane.setBounds(0, 0, 525, 525);
		gameBoardLayeredPane.add(transparentButtonPane,
				JLayeredPane.PALETTE_LAYER);
		transparentButtonPane.setOpaque(false);
		transparentButtonPane.setLayout(new GridLayout(0, 15));
		for (int i = 0; i < 225; i++) {
			transparentSquarePane[i].setOpaque(false);
			// transparentSquarePane[i].setBorder(BorderFactory.createRaisedBevelBorder());
			transparentButtonPane.add(transparentSquarePane[i]);
		}
		/***************************************************************************************/
		
		// set up buttons on outer track.
		/*for (int i = 0; i < 56; i++) {
			outerTrackButtons[i].setOpaque(false);
			outerTrackButtons[i].setMaximumSize(new Dimension(35, 35));
			outerTrackButtons[i].setMinimumSize(new Dimension(35, 35));
			outerTrackButtons[i].setPreferredSize(new Dimension(35, 35));
			outerTrackButtons[i].setActionCommand("" + i);
			outerTrackButtons[i].addActionListener(new SquareButtonListener());
			outerTrackButtons[i].setEnabled(false);
		}*/
		for (int i = 0; i < 15; i++) {
			//transparentSquarePane[i].setLayout(transparentSquarePaneLayout[i]);
			transparentSquarePane[i].add(outerTrackButtons[i]);
			outerTrackPaneIndex[i] = i;
		}
		for (int i = 15, j = 29; i < 29 && j < 225; i++, j += 15) {
			//transparentSquarePane[j].setLayout(transparentSquarePaneLayout[i]);
			transparentSquarePane[j].add(outerTrackButtons[i]);
			outerTrackPaneIndex[i] = j;
		}
		for (int i = 29, j = 223; i < 43 && j > 209; i++, j--) {
			//transparentSquarePane[j].setLayout(transparentSquarePaneLayout[i]);
			transparentSquarePane[j].add(outerTrackButtons[i]);
			outerTrackPaneIndex[i] = j;
		}
		for (int i = 43, j = 195; i < 56 && j > 14; i++, j -= 15) {
			//transparentSquarePane[j].setLayout(transparentSquarePaneLayout[i]);
			transparentSquarePane[j].add(outerTrackButtons[i]);
			outerTrackPaneIndex[i] = j;
		}
		// end of creating outer track buttons
		/***************************************************************************************/

		// set the layout for the frame, add and align all the components.
		/*GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		authorNameLabel,
																		GroupLayout.PREFERRED_SIZE,
																		185,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(286,
																		286,
																		286))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		gameBoardLayeredPane,
																		GroupLayout.PREFERRED_SIZE,
																		525,
																		GroupLayout.PREFERRED_SIZE)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										infoAreaPane,
																										GroupLayout.PREFERRED_SIZE,
																										200,
																										GroupLayout.PREFERRED_SIZE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(64,
																										64,
																										64)
																								.addComponent(
																										quitBtn,
																										GroupLayout.PREFERRED_SIZE,
																										80,
																										GroupLayout.PREFERRED_SIZE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(53,
																										53,
																										53)
																                                .addComponent(
																                                		statisticsBtn, 
																                                		GroupLayout.PREFERRED_SIZE, 
																                                		100,		
																                                		GroupLayout.PREFERRED_SIZE))
																                .addGroup(
																						layout.createSequentialGroup()
																								.addGap(30,
																										30,
																										30)
																								.addComponent(
																										startNewGameBtn,
																										GroupLayout.PREFERRED_SIZE,
																										150,
																										GroupLayout.PREFERRED_SIZE))
													                            .addGroup(
													                            		layout.createSequentialGroup()
													                            				.addGap(8, 8, 8)
													                            				.addComponent(computerTypeChoicePane, 
													                            						GroupLayout.PREFERRED_SIZE, 
													                            						190, 
													                            						GroupLayout.PREFERRED_SIZE)
													                            				.addContainerGap())))
																.addGap(22, 22, 22))));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(authorNameLabel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										0,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		infoAreaPane,
																		GroupLayout.PREFERRED_SIZE,
																		150,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18,
																		18)
																.addComponent(
																		quitBtn,
																		GroupLayout.PREFERRED_SIZE,
																		35,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18,
																		18)
																.addComponent(
																		statisticsBtn,
																		GroupLayout.PREFERRED_SIZE, 
																		35, 
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18,
																		18)
																.addComponent(
																		startNewGameBtn,
																		GroupLayout.PREFERRED_SIZE,
																		35,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(30, 30,
																		30)
																.addComponent(
																		computerTypeChoicePane, 
																		GroupLayout.PREFERRED_SIZE, 
																		GroupLayout.DEFAULT_SIZE, 
																		GroupLayout.PREFERRED_SIZE))
																.addComponent(
																		gameBoardLayeredPane,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		525,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(30, 30, 30))
								.addContainerGap(0, Short.MAX_VALUE)));
		/***************************************************************************************/
		/* end of setting up main components */
		/***************************************************************************************/

		// set up the red pawns
		for (int i = 0; i < 4; i++) {
			redPawn[i].setIcon(new ImageIcon("C:/Users/xsong/Sorry/redPawn.jpg")); // NOI18N
			redPawn[i].setActionCommand("" + i);
			redPawn[i].setDisabledIcon(new ImageIcon("C:/Users/xsong/Sorry/redPawn.jpg"));
			redPawn[i].setMaximumSize(new Dimension(35, 35));
			redPawn[i].setMinimumSize(new Dimension(35, 35));
			redPawn[i].setPreferredSize(new Dimension(35, 35));
		}
		/***************************************************************************************/

		/*
		// set up the start panel for the red player
		redStartPane.setMaximumSize(new Dimension(70, 70));
		redStartPane.setMinimumSize(new Dimension(70, 70));
		redStartPane.setOpaque(false);
		redStartPane.setPreferredSize(new Dimension(70, 70));
		redStartPane.setLayout(new GridLayout(0, 2));
		redStartPane.setBounds(122, 45, 70, 70);
		gameBoardLayeredPane.add(redStartPane, JLayeredPane.MODAL_LAYER);
		for (int i = 0; i < 4; i++) {
			redStartSquarePane[i].setOpaque(false);
			redStartSquarePaneLayout[i] = new GroupLayout(redStartSquarePane[i]);
			redStartSquarePane[i].setLayout(redStartSquarePaneLayout[i]);
			redStartSquarePaneLayout[i]
					.setHorizontalGroup(redStartSquarePaneLayout[i]
							.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(
									redStartSquarePaneLayout[i]
											.createSequentialGroup()
											.addComponent(redPawn[i],
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addGap(0, 0, Short.MAX_VALUE)));
			redStartSquarePaneLayout[i]
					.setVerticalGroup(redStartSquarePaneLayout[i]
							.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(
									redStartSquarePaneLayout[i]
											.createSequentialGroup()
											.addComponent(redPawn[i],
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addGap(0, 0, Short.MAX_VALUE)));

			redStartPane.add(redStartSquarePane[i]);
		}
		/***************************************************************************************/

		/*
		// set up the home panel for the red player
		redHomePane.setOpaque(false);
		redHomePane.setLayout(new FlowLayout(FlowLayout.CENTER));
		redHomePane.setBounds(50, 190, 90, 90);
		gameBoardLayeredPane.add(redHomePane, JLayeredPane.MODAL_LAYER);
		/***************************************************************************************/

		/*
		// set up the safety zone panel for the red player
		redSafetyZonePane.setMaximumSize(new Dimension(35, 165));
		redSafetyZonePane.setMinimumSize(new Dimension(35, 165));
		redSafetyZonePane.setOpaque(false);
		redSafetyZonePane.setLayout(new GridLayout(0, 1));
		redSafetyZonePane.setBounds(70, 35, 35, 165);
		gameBoardLayeredPane.add(redSafetyZonePane, JLayeredPane.MODAL_LAYER);
		// set up the red safety zone squares
		for (int i = 0; i < 5; i++) {
			redSafetyZoneSquarePane[i].setMaximumSize(new Dimension(35, 35));
			redSafetyZoneSquarePane[i].setMinimumSize(new Dimension(35, 35));
			redSafetyZoneSquarePane[i].setOpaque(false);
			redSafetyZoneSquarePane[i].setPreferredSize(new Dimension(35, 35));

			redSafetyZoneSquarePane[i].setLayout(redSafetyZoneSquarePaneLayout[i]);

			redSafetyZonePane.add(redSafetyZoneSquarePane[i]);
		}
		/***************************************************************************************/
		/* end of setting up components specific for red pawns */
		/***************************************************************************************/

		// set up the yellow pawns
		for (int i = 0; i < 4; i++) {
			yellowPawn[i].setIcon(new javax.swing.ImageIcon("C:/Users/xsong/Sorry/yellowPawn.jpg")); // NOI18N
			yellowPawn[i].setActionCommand("" + (4 + i));
			yellowPawn[i].setDisabledIcon(new ImageIcon("C:/Users/xsong/Sorry/yellowPawn.jpg"));
			yellowPawn[i].setMaximumSize(new java.awt.Dimension(35, 35));
			yellowPawn[i].setMinimumSize(new java.awt.Dimension(35, 35));
			yellowPawn[i].setPreferredSize(new java.awt.Dimension(35, 35));
			yellowPawn[i].addActionListener(new PawnButtonListener());
		}
		/***************************************************************************************/

		
		// set up the start panel for the yellow player
		yellowStartPane.setMaximumSize(new Dimension(70, 70));
		yellowStartPane.setMinimumSize(new Dimension(70, 70));
		yellowStartPane.setOpaque(false);
		yellowStartPane.setPreferredSize(new Dimension(70, 70));
		yellowStartPane.setLayout(new GridLayout(0, 2));
		yellowStartPane.setBounds(330, 410, 70, 70);
		gameBoardLayeredPane.add(yellowStartPane, JLayeredPane.MODAL_LAYER);
		for (int i = 0; i < 4; i++) {
			yellowStartSquarePane[i].setOpaque(false);
			//yellowStartSquarePaneLayout[i] = new GroupLayout(
			//		yellowStartSquarePane[i]);
			//yellowStartSquarePane[i].setLayout(yellowStartSquarePaneLayout[i]);
			yellowStartSquarePaneLayout[i]
					.setHorizontalGroup(yellowStartSquarePaneLayout[i]
							.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(
									yellowStartSquarePaneLayout[i]
											.createSequentialGroup()
											.addComponent(yellowPawn[i],
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addGap(0, 0, Short.MAX_VALUE)));
			yellowStartSquarePaneLayout[i]
					.setVerticalGroup(yellowStartSquarePaneLayout[i]
							.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(
									yellowStartSquarePaneLayout[i]
											.createSequentialGroup()
											.addComponent(yellowPawn[i],
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addGap(0, 0, Short.MAX_VALUE)));

			//yellowStartPane.add(yellowStartSquarePane[i]);
		}
		/***************************************************************************************/

		// set up the home panel for the yellow player
		yellowHomePane.setOpaque(false);
		yellowHomePane.setLayout(new FlowLayout(FlowLayout.CENTER));
		yellowHomePane.setBounds(390, 240, 90, 90);
		//gameBoardLayeredPane.add(yellowHomePane, JLayeredPane.MODAL_LAYER);

		yellowHomeButton.setOpaque(false);
		yellowHomeButton.setMaximumSize(new Dimension(35, 35));
		yellowHomeButton.setMinimumSize(new Dimension(35, 35));
		yellowHomeButton.setPreferredSize(new Dimension(35, 35));
		yellowHomeButton.setActionCommand("" + 67); // track square index
		//yellowHomeButton.addActionListener(new SquareButtonListener());
		yellowHomeButton.setEnabled(false);
		/***************************************************************************************/

		/*
		// set up the safety zone panel for the yellow player
		yellowSafetyZonePane.setMaximumSize(new Dimension(35, 165));
		yellowSafetyZonePane.setMinimumSize(new Dimension(35, 165));
		yellowSafetyZonePane.setOpaque(false);
		yellowSafetyZonePane.setLayout(new GridLayout(0, 1));
		yellowSafetyZonePane.setBounds(420, 325, 35, 165);
		gameBoardLayeredPane
				.add(yellowSafetyZonePane, JLayeredPane.MODAL_LAYER);
		// Set up the yellow safety zone squares
		for (int i = 4; i >= 0; i--) {
			yellowSafetyZoneSquarePane[i] = new JPanel();
			yellowSafetyZoneSquarePane[i].setMaximumSize(new Dimension(35, 35));
			yellowSafetyZoneSquarePane[i].setMinimumSize(new Dimension(35, 35));
			yellowSafetyZoneSquarePane[i].setOpaque(false);
			yellowSafetyZoneSquarePane[i]
					.setPreferredSize(new Dimension(35, 35));

			yellowSafetyZoneSquarePaneLayout[i] = new GridLayout();
			yellowSafetyZoneSquarePane[i].setLayout(yellowSafetyZoneSquarePaneLayout[i]);

			yellowSafetyZonePane.add(yellowSafetyZoneSquarePane[i]);
		}
		// set up the safety zone buttons for the yellow player
		for (int i = 0; i < 5; i++) {
			yellowSafetyZoneButtons[i] = new JButton();
			yellowSafetyZoneButtons[i].setOpaque(false);
			yellowSafetyZoneButtons[i].setMaximumSize(new Dimension(35, 35));
			yellowSafetyZoneButtons[i].setMinimumSize(new Dimension(35, 35));
			yellowSafetyZoneButtons[i].setPreferredSize(new Dimension(35, 35));
			yellowSafetyZoneButtons[i].setActionCommand("" + (i + 62));
			yellowSafetyZoneButtons[i]
					.addActionListener(new SquareButtonListener());
			yellowSafetyZoneButtons[i].setEnabled(false);
		}
		for (int i = 0; i < 5; i++) {
			yellowSafetyZoneSquarePane[i].add(yellowSafetyZoneButtons[i]);
		}
		/***************************************************************************************/
		/* end of setting up components specific for yellow pawns */
		/***************************************************************************************/

		/*
		// set up the panel for displaying the draw_a_card button
		drawCardPane.setMinimumSize(new java.awt.Dimension(90, 125));
		drawCardPane.setPreferredSize(new java.awt.Dimension(90, 125));
		drawCardPane.setBounds(150, 280, 90, 125);
		gameBoardLayeredPane.add(drawCardPane, JLayeredPane.MODAL_LAYER);
		// create the drawCardBtn for drawing a card
		drawCardBtn.setIcon(new ImageIcon("C:/Users/xsong/Sorry/drawACard.jpg"));
		drawCardBtn.setToolTipText("Click to draw a card.");
		drawCardBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		drawCardBtn.setMaximumSize(new Dimension(90, 125));
		drawCardBtn.setMinimumSize(new Dimension(90, 125));
		drawCardBtn.setPreferredSize(new Dimension(90, 125));
		drawCardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				drawCardBtnActionPerformed(evt);
			}
		});
		// set up the layout of drawCardPane and add the button into the pane
		GroupLayout drawCardPaneLayout = new GroupLayout(drawCardPane);
		drawCardPane.setLayout(drawCardPaneLayout);
		drawCardPaneLayout.setHorizontalGroup(drawCardPaneLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						drawCardPaneLayout
								.createSequentialGroup()
								.addComponent(drawCardBtn,
										GroupLayout.PREFERRED_SIZE, 90,
										GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)));
		drawCardPaneLayout.setVerticalGroup(drawCardPaneLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						drawCardPaneLayout
								.createSequentialGroup()
								.addComponent(drawCardBtn,
										GroupLayout.PREFERRED_SIZE, 125,
										GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)));
		/***************************************************************************************/

		/*
		// set up the label and panel for displaying the newly drawn card.
		displayCardPane.setBounds(270, 100, 120, 164);
		displayCardPane.setOpaque(false);
		gameBoardLayeredPane.add(displayCardPane, JLayeredPane.MODAL_LAYER);
		currentCardImage = "";
		displayCardLabel
				.setToolTipText("This is the card displaying the moves and rules");
		displayCardLabel.setMaximumSize(new Dimension(120, 164));
		displayCardLabel.setMinimumSize(new Dimension(120, 164));
		displayCardLabel.setPreferredSize(new Dimension(120, 164));
		displayCardLabel.setOpaque(false);
		GroupLayout displayCardPaneLayout = new GroupLayout(displayCardPane);
		displayCardPane.setLayout(displayCardPaneLayout);
		displayCardPaneLayout.setHorizontalGroup(displayCardPaneLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(displayCardLabel, GroupLayout.DEFAULT_SIZE, 120,
						Short.MAX_VALUE));
		displayCardPaneLayout.setVerticalGroup(displayCardPaneLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(displayCardLabel, GroupLayout.DEFAULT_SIZE, 164,
						Short.MAX_VALUE));
		/***************************************************************************************/

		//pack();
		
	}
	
	
	/**
	 * This method lights up the possible destination squares with orange. If
	 * the square is occupied by a pawn (should not be a yellow pawn because the
	 * logic is handled by the SRGameBoard and SRDriver), the square will be
	 * lighted up with red.
	 * 
	 * @param squareIndexArray
	 *            The index array representing the position of squares,
	 *            designated by the SRDriver
	 */
	public void squareLightUp(int[] squareIndexArray) {
		
		int buttonNum = squareIndexArray.length;

		
		for (int i = 0; i < buttonNum; i++) {
			
			// when the squares are on the outer track
			if (squareIndexArray[i] >= 0 && squareIndexArray[i] < 56) {

				//check whether one of the squares are occupied by a red pawn.
				boolean occupiedByRedPawn = false;
				for (int j=0;j<4; j++){
					if (board.pawns[j].getTrackIndex() == squareIndexArray[i]) {
						if(SorryGame.debug) {
							System.out.println("bumpSquareLightUp() should be called now");
						}
						occupiedByRedPawn = true;
					}
				}
				if (occupiedByRedPawn) {
					transparentSquarePane[outerTrackPaneIndex[squareIndexArray[i]]]
							.removeAll();
					transparentSquarePane[outerTrackPaneIndex[squareIndexArray[i]]]
							.add(outerTrackButtons[squareIndexArray[i]]);
					outerTrackButtons[squareIndexArray[i]].setIcon(new ImageIcon(
							"C:/Users/xsong/Sorry/bumpButton.jpg"));
					outerTrackButtons[squareIndexArray[i]].setEnabled(true);
					outerTrackButtons[squareIndexArray[i]].setOpaque(true);
					outerTrackButtons[squareIndexArray[i]]
							.setToolTipText("Click the square to bump the red pawn");
				}
				else {
					transparentSquarePane[outerTrackPaneIndex[squareIndexArray[i]]]
							.add(outerTrackButtons[squareIndexArray[i]]);
					outerTrackButtons[squareIndexArray[i]].setIcon(new ImageIcon(
							"C:/Users/xsong/Sorry/squareButton.jpg"));
					outerTrackButtons[squareIndexArray[i]].setEnabled(true);
					outerTrackButtons[squareIndexArray[i]].setOpaque(true);
					outerTrackButtons[squareIndexArray[i]]
							.setToolTipText("Click the square to move the pawn");
				}
			}
			// when the squares are on the yellow safety zone
			else if (squareIndexArray[i] >= 62 && squareIndexArray[i] < 67) {
				yellowSafetyZoneButtons[squareIndexArray[i] - 62]
						.setIcon(new ImageIcon("C:/Users/xsong/Sorry/squareButton.jpg"));
				yellowSafetyZoneButtons[squareIndexArray[i] - 62]
						.setEnabled(true);
				yellowSafetyZoneButtons[squareIndexArray[i] - 62]
						.setToolTipText("Click the square to move the pawn.");
			}
			// when the squares are on the yellow Home
			else if (squareIndexArray[i] == 67) {
				yellowHomeButton.setIcon(new ImageIcon("C:/Users/xsong/Sorry/squareButton.jpg"));
				yellowHomeButton.setEnabled(true);
				yellowHomeButton.setOpaque(true);
				yellowHomeButton
						.setToolTipText("Click the squre to move pawn home");
				yellowHomePane.add(yellowHomeButton);
			}
			// only the squares accessible to the yellow pawns can be lighted up
			// all the other squares are not allowed (should be handled by the
			// logic, so this is also a test/debug helper
			else {
				System.out.println("ERROR: " + squareIndexArray[i]
						+ " wrong squares to light up!!!!");
			}
		}
		repaint();
		revalidate();
	}// end of squareLightUp()

	/**
	 * This method moves a pawn from current place to the destination square.
	 * 
	 * @param pawnNum
	 *            designates which pawn will be moved
	 * @param trackSquareIndex
	 *            represents the index of destination square
	 * @return true if the pawn has been successfully moved.
	 */
	public void movePawn(int pawnNum, int previousSquareIndex, int destinationSquareIndex) {
		// a flag shows whether the movement is successful or not
		// int stores the previous and destination position of the pawn
		int destinationSquarePaneIndex, previousSquarePaneIndex;
		
		/************************************************/
		/**************** move a red pawn *****************/
		if (pawnNum >= 0 && pawnNum < 4) {
			
			
			/************ remove the red pawn from previous location ************/
			// the pawn was previously on the outer track
			if (previousSquareIndex >= 0 && previousSquareIndex < 56) {
				previousSquarePaneIndex = outerTrackPaneIndex[previousSquareIndex];
				transparentSquarePane[previousSquarePaneIndex]
						.remove(redPawn[pawnNum]);
				//outerTrackButtons[previousPosition] = new JButton();
				
				
				outerTrackButtons[previousSquareIndex].setOpaque(true);
				outerTrackButtons[previousSquareIndex].setEnabled(false);
				
				transparentSquarePane[previousSquarePaneIndex]
						.add(outerTrackButtons[previousSquareIndex]);
			}
			// the pawn was previously in the red safety zone
			else if (previousSquareIndex >= 56 && previousSquareIndex < 61) {
				previousSquarePaneIndex = previousSquareIndex - 56;
				redSafetyZoneSquarePane[previousSquarePaneIndex]
						.remove(redPawn[pawnNum]);
			}
			// the pawn was previously at start
			else if (previousSquareIndex == -1) {
				previousSquarePaneIndex = pawnNum;
				redStartSquarePane[previousSquarePaneIndex]
						.remove(redPawn[pawnNum]);
			}
			// the pawn was either in the home square or in an invalid position
			// This situation should not occur if the game running correctly. So
			// it is also a debug/test tool.
			else
				System.out
						.println("ERROR: This pawn is in the home or an invalid position. Current position: "
								+ board.pawns[pawnNum].getTrackIndex());
			// end of removing the red pawn from previous location

			/************ move the red pawn to the destination square ************/			
			// change the selected pawn (grey) back to red color
			redPawn[pawnNum].setIcon(new ImageIcon("C:/Users/xsong/Sorry/redPawn.jpg"));

			// move the pawn to a square on the outer track
			if (destinationSquareIndex >= 0 && destinationSquareIndex < 56) {
				destinationSquarePaneIndex = outerTrackPaneIndex[destinationSquareIndex];
				transparentSquarePane[destinationSquarePaneIndex]
						.remove(outerTrackButtons[destinationSquareIndex]);
				transparentSquarePane[destinationSquarePaneIndex].add(redPawn[pawnNum]);
				
				successfulMove = true;
			}
			// move red pawn to the red safety zone
			else if (destinationSquareIndex >= 56 && destinationSquareIndex < 61) {
				destinationSquarePaneIndex = destinationSquareIndex - 62;
				redSafetyZoneSquarePane[destinationSquarePaneIndex]
						.remove(yellowSafetyZoneButtons[destinationSquarePaneIndex]);
				redSafetyZoneSquarePane[destinationSquarePaneIndex].add(redPawn[pawnNum]);
				
				successfulMove = true;
			}
			// move red pawn to its home
			else if (destinationSquareIndex == 61) {
				redHomePane.add(redPawn[pawnNum]);
				redPawn[pawnNum].setEnabled(false);
				successfulMove = true;
			}
			// move red pawn back to start
			else if (destinationSquareIndex == -1) {
				destinationSquarePaneIndex = pawnNum;
				redStartSquarePaneLayout[destinationSquarePaneIndex]
						.setHorizontalGroup(redStartSquarePaneLayout[destinationSquarePaneIndex]
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addGroup(
										redStartSquarePaneLayout[destinationSquarePaneIndex]
												.createSequentialGroup()
												.addComponent(
														redPawn[destinationSquarePaneIndex],
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addGap(0, 0, Short.MAX_VALUE)));
				redStartSquarePaneLayout[destinationSquarePaneIndex]
						.setVerticalGroup(redStartSquarePaneLayout[destinationSquarePaneIndex]
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addGroup(
										redStartSquarePaneLayout[destinationSquarePaneIndex]
												.createSequentialGroup()
												.addComponent(
														redPawn[destinationSquarePaneIndex],
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addGap(0, 0, Short.MAX_VALUE)));
				successfulMove = true;
			}
			// cannot move red pawn to the yellow safety zone or yellow home.
			// Debug/test tool.
			else {
				System.out
						.println("ERROR: Red pawn can not move to the designated square!!!");
				successfulMove = false;
			}
			// end of adding a red pawn to the destination square
		}
		// end of moving a red pawn

		/************************************************/
		/**************** move a yellow pawn *****************/
		else if (pawnNum >= 4 && pawnNum < 8) {

			/************ remove the yellow pawn from previous location ************/
			// the pawn was previously on the outer track
			if (previousSquareIndex >= 0 && previousSquareIndex < 56) {
				previousSquarePaneIndex = outerTrackPaneIndex[previousSquareIndex];
				transparentSquarePane[previousSquarePaneIndex]
						.remove(yellowPawn[pawnNum - 4]);
				if (SorryGame.debug) {
					System.out.println("outerTrackButtons added back is : " + outerTrackButtons[previousSquareIndex].getActionCommand());
				}
								
				outerTrackButtons[previousSquareIndex].setOpaque(true);
				outerTrackButtons[previousSquareIndex].setEnabled(false);
				
				transparentSquarePane[previousSquarePaneIndex]
						.add(outerTrackButtons[previousSquareIndex]);
			}
			// the pawn was previously in the yellow safety zone
			else if (previousSquareIndex >= 62 && previousSquareIndex < 67) {
				previousSquarePaneIndex = previousSquareIndex - 62;
				yellowSafetyZoneSquarePane[previousSquarePaneIndex]
						.remove(yellowPawn[pawnNum - 4]);
				yellowSafetyZoneSquarePane[previousSquarePaneIndex]
						.add(yellowSafetyZoneButtons[previousSquarePaneIndex]);
				yellowSafetyZoneButtons[previousSquarePaneIndex]
						.setIcon(null);
				yellowSafetyZoneButtons[previousSquarePaneIndex]
						.setOpaque(false);
				yellowSafetyZoneButtons[previousSquarePaneIndex]
						.setEnabled(false);
			}
			// the yellow pawn was previously at start
			else if (previousSquareIndex == -1) {
				previousSquarePaneIndex = pawnNum - 4;
				yellowStartSquarePane[previousSquarePaneIndex]
						.remove(yellowPawn[pawnNum - 4]);
			}
			// the pawn was either in the home square or in an invalid position
			// This situation should not occur if the game running correctly.
			// Debug/test tool
			else {
				System.out
						.println("ERROR: This pawn is in the home or an invalid position. Current position: "
								+ previousSquareIndex);
				successfulMove = false;
			}
			// end of removing the yellow pawn from previous location

			/************ move the yellow pawn to the destination square ************/
			// change the selected pawn (grey) back to yellow color
			yellowPawn[pawnNum - 4].setIcon(new ImageIcon("C:/Users/xsong/Sorry/yellowPawn.jpg"));

			// move the pawn to a square on the outer track
			if (destinationSquareIndex >= 0 && destinationSquareIndex < 56) {
				destinationSquarePaneIndex = outerTrackPaneIndex[destinationSquareIndex];
				transparentSquarePane[destinationSquarePaneIndex]
						.remove(outerTrackButtons[destinationSquareIndex]);
				transparentSquarePane[destinationSquarePaneIndex]
						.add(yellowPawn[pawnNum - 4]);

				successfulMove = true;
			}
			// move yellow pawn to the yellow safety zone
			else if (destinationSquareIndex >= 62 && destinationSquareIndex < 67) {
				destinationSquarePaneIndex = destinationSquareIndex - 62;
				yellowSafetyZoneSquarePane[destinationSquarePaneIndex]
						.remove(yellowSafetyZoneButtons[destinationSquarePaneIndex]);
				yellowSafetyZoneSquarePane[destinationSquarePaneIndex]
						.add(yellowPawn[pawnNum - 4]);
				
				successfulMove = true;
			}
			// move yellow pawn to its home
			else if (destinationSquareIndex == 67) {
				yellowHomePane.remove(yellowHomeButton);
				yellowHomeButton.setIcon(null);
				yellowHomeButton.setEnabled(false);
				yellowHomePane.add(yellowPawn[pawnNum - 4]);
				yellowPawn[pawnNum - 4].setEnabled(false);
				successfulMove = true;
			}
			// move yellow pawn back to start
			else if (destinationSquareIndex == -1) {
				destinationSquarePaneIndex = pawnNum - 4;
				yellowStartSquarePaneLayout[destinationSquarePaneIndex]
						.setHorizontalGroup(yellowStartSquarePaneLayout[destinationSquarePaneIndex]
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addGroup(
										yellowStartSquarePaneLayout[destinationSquarePaneIndex]
												.createSequentialGroup()
												.addComponent(
														yellowPawn[destinationSquarePaneIndex],
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addGap(0, 0, Short.MAX_VALUE)));
				yellowStartSquarePaneLayout[destinationSquarePaneIndex]
						.setVerticalGroup(yellowStartSquarePaneLayout[destinationSquarePaneIndex]
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addGroup(
										yellowStartSquarePaneLayout[destinationSquarePaneIndex]
												.createSequentialGroup()
												.addComponent(
														yellowPawn[destinationSquarePaneIndex],
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addGap(0, 0, Short.MAX_VALUE)));
				successfulMove = true;
			}
			// cannot move yellow pawn to the red safety zone
			else {
				System.out
						.println("ERROR: Yellow pawn can not move to the designated square!!!");
				successfulMove = false;
			}// end of adding the yellow pawn to the destination square

		}
		// end of moving the yellow pawn

		// This is a debug tool if a wrong pawn number is passed to the method
		else {
			System.out.println("ERROR: The pawn number is out of range!!!!!!!");
			successfulMove = false;
		}

		clearTrack();
		repaint();
		//revalidate();
		
	}// end of movePawn()

	/**
	 * This class implements the ActionListener interface and defines the action
	 * will be performed in response to the events occurred on the yellow pawns
	 * 
	 * @author Sam Brown, Caleb Cousins, Taylor Krammen, and Yucan Zhang
	 * 
	 */
	class PawnButtonListener implements ActionListener {
		/**
		 * This method overrides the actionPerformed() method in the
		 * ActionListener interface
		 * 
		 * @param e
		 *            the event occurred on the button of a pawn
		 */
		public void actionPerformed(ActionEvent e) {
			clearTrack();
			if ( Arrays.asList(yellowPawn).contains(e.getSource())) {
				int pawnNum = Integer.parseInt(e.getActionCommand());
				selectPawn(pawnNum);
				
				if(SorryGame.debug) {
					System.out.println("e.getActionCommand() = " + currentPawnIndex);
				}
			}
			
	 		squareBtnSelected = false;
	 		selectedSquareIndex = -2;
	 		successfulMove = false;
				
			repaint();
			revalidate();
		}// end of actionPerformed() handler

	}// end of PawnButtonListener class

	/**
	 * This method selects the designated pawn and un-check all the other pawns,
	 * to make sure only one pawn is selected at a time
	 * 
	 * @param pawnNum
	 *            the selected pawn index
	 */
	public void selectPawn(int pawnNum) {
		clearTrack();
		
		// a red pawn is selected by the computer, but should not by the user
		if (pawnNum >= 0 && pawnNum < 4) {
			redPawn[pawnNum].setIcon(new ImageIcon("C:/Users/xsong/Sorry/selectedPawn.jpg"));
			for (int i = 0; i < 4; i++) {
				if (i != pawnNum)
					redPawn[i].setIcon(new ImageIcon("C:/Users/xsong/Sorry/redPawn.jpg"));
			}
			pawnSelected = true;
			currentPawnIndex = pawnNum;
		}
		// a yellow pawn is selected by the user
		else if (pawnNum >= 4 && pawnNum < 8) {
			yellowPawn[pawnNum - 4].setIcon(new ImageIcon("C:/Users/xsong/Sorry/selectedPawn.jpg"));
			for (int i = 0; i < 4; i++) {
				if (i != (pawnNum - 4)) {
					yellowPawn[i].setIcon(new ImageIcon("C:/Users/xsong/Sorry/yellowPawn.jpg"));
					yellowPawn[i].setEnabled(false);
				}
			}
			pawnSelected = true;
			currentPawnIndex = pawnNum;
		}
		else {
			pawnSelected = false;
			currentPawnIndex = -1;
			for (int i = 0; i < 4; i++) {
				yellowPawn[i].setIcon(new ImageIcon("C:/Users/xsong/Sorry/yellowPawn.jpg"));
			}
		}
		
		repaint();
		revalidate();
	}// end of selectPawn()

	/**
	 * This class implements the ActionListener interface and defines the action
	 * will be performed after the events occurred on the square buttons
	 * (indicating the destination squares where a yellow pawn can move to)
	 * 
	 * @author Sam Brown, Caleb Cousins, Taylor Krammen, and Yucan Zhang
	 * 
	 */
	class SquareButtonListener implements ActionListener {
		/**
		 * This method overrides the actionPerformed() method in the
		 * ActionListener interface
		 * 
		 * @param e
		 *            the event occurred on the square buttons
		 */
		public void actionPerformed(ActionEvent e) {
			if ( Arrays.asList(outerTrackButtons).contains(e.getSource()) || 
					Arrays.asList(yellowSafetyZoneButtons).contains(e.getSource()) ||
					e.getSource() == yellowHomeButton) {
				selectSquare(Integer.parseInt(e.getActionCommand()));
			}
			else {
				selectSquare(-2);
			}
			
	 		successfulMove = false;

			if (SorryGame.debug) {
				System.out.println("e.getActionCommand() = " + selectedSquareIndex);
			}

			
		}// end of actionPerformed() handler
	}// end of SquareButtonListener class
	
	public void selectSquare(int squareIndex) {
		if (squareIndex > 0) {
			squareBtnSelected = true;
		}
		else {
			squareBtnSelected = false;
		}
		selectedSquareIndex = squareIndex;
		if(SorryGame.debug) {
			System.out.println("selected square index is " + selectedSquareIndex);
		}
	}

	/**
	 * This method is called by the ActionListener of the startNewGameBtn,
	 * defines the action will be performed after the events occurred on the
	 * startNewGameBtn.
	 * 
	 * @param evt
	 *            event occurred on the startNewGameBtn
	 */
	private void startNewGameBtnActionPerformed(ActionEvent evt) {
		if (evt.getSource() == startNewGameBtn) {
			started = false;
			niceComputerBtn.setEnabled(true);
			meanComputerBtn.setEnabled(true);
		}
	}// end of startNewGameBtnActionPerformed() handler

	
	/**
	 * This method is called by the ActionListener of the quitBtn, defines the
	 * action will be performed after the events occurred on the quitBtn.
	 * 
	 * @param evt
	 *            event occurred on the quitBtn
	 */
	private void quitBtnActionPerformed(ActionEvent evt) {
		if (evt.getSource() == quitBtn) {
			int option = JOptionPane
					.showConfirmDialog(
							null,
							"Would you like to see the statistics before leaving the game?",
							"Show statistics", JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == JOptionPane.YES_OPTION) {

			} else if (option == JOptionPane.NO_OPTION) {
				resetComponents();
			}
		}
	}// end of quitBtnActionPerformed() handler

	/**
	 * This method is called by the ActionListener of the statisticsBtn, defines the
	 * action will be performed after the events occured on the statisticsBtn.
	 * 
	 * @param evt 
	 * 			  event occured on the statisticsBtn
	 */
	private void statisticsBtnActionPerformed(ActionEvent evt) {
		if(evt.getSource() == statisticsBtn) {
			
		}
	}// end of statisticsBtnActionPerformed() handler
	
	
	/**
	 * This method is called by the ActionListener of the niceComputerBtn, defines the
	 * action will be performed after the events occured on the niceComputerBtn.
	 * 
	 * @param evt
	 */
	private void niceComputerBtnActionPerformed(ActionEvent evt) {
		computerType = NICE_COMPUTER;
		niceComputerBtn.setEnabled(false);
		meanComputerBtn.setEnabled(false);
		started = true;
	}
	
	/**
	 * This method is called by the ActionListener of the meanComputerBtn, defines the
	 * action will be performed after the events occured on the meanComputerBtn.
	 * 
	 * @param evt
	 */
	private void meanComputerBtnActionPerformed(ActionEvent evt) {
		computerType = MEAN_COMPUTER;
		niceComputerBtn.setEnabled(false);
		meanComputerBtn.setEnabled(false);
		started = true;
	}
	
	
	
	/**
	 * This method is called by the ActionListener of the drawCardBtn, defines
	 * the action will be performed after the events occurred on the
	 * drawCardBtn.
	 * 
	 * @param evt
	 *            event occurred on the drawCardBtn
	 */
	private void drawCardBtnActionPerformed(ActionEvent evt) {
		if (evt.getSource() == drawCardBtn) {
			cardDrawn = true;
			displayCard(currentCardImage);
			drawCardBtn.setEnabled(false);

			pawnSelected = false;
	  		currentPawnIndex = -1;
	 		squareBtnSelected = false;
	 		selectedSquareIndex = -2;
	 		successfulMove = false;
		}
	}// end of drawCardBtnActionPerformed() handler

	/**
	 * This method gets the current displayed drawn card image
	 * 
	 * @return the currentCardImage
	 */
	public String getCurrentCardImage() {
		return currentCardImage;
	}// end of getCurrentCardImage()

	/**
	 * This method sets the current drawn card image which will be displayed on
	 * the screen
	 * 
	 * @param currentCardImage
	 *            the currentCardImage to set
	 */
	public void setCurrentCardImage(String currentCardImage) {
		this.currentCardImage = currentCardImage;
	}// end of setCurrentCardImage()

	/**
	 * This method returns the selected pawn number
	 * 
	 * @return the selectedPawnNum
	 */
	public int getCurrentPawnIndex() {
		return currentPawnIndex;
	}// end of getSelectedPawnNum()

	/**
	 * This function returns the computer type the user selected, 
	 * 0 = nice computer
	 * 1 = mean computer
	 * 
	 * @return the computerType
	 */
	public int getComputerType() {
		return computerType;
	}


	/**
	 * This method displays the drawn card image on the gameboard
	 * 
	 * @param cardImage
	 *            the image of the card which will be displayed
	 */
	public void displayCard(String cardImage) {
		displayCardLabel.setVisible(true);
		displayCardLabel.setIcon(new ImageIcon("C:/Users/xsong/Sorry/" + cardImage));
		repaint();
		revalidate();
	}// end of displayCard()
	
	public void clearCardDisplay() {
		displayCardLabel.setVisible(false);
	}

	/**
	 * This method sets all yellow pawn buttons enabled or disabled.
	 * 
	 * @param b
	 *            sets enabled if true and not on home
	 */
	public void setYellowPawnEnabled(boolean b) {
		if (b) {
			for (int i = 0; i < 4; i++) {
				if (!board.pawns[i].isOnHome()) {
					if (currentCard.getcardNum() != 1 && currentCard.getcardNum() != 2) {
						if(board.pawns[i+4].getTrackIndex() == -1)
							yellowPawn[i].setEnabled(false);
						else
							yellowPawn[i].setEnabled(true);
					}
					else {
						boolean entranceBlocked = false;
						for(int j = 0; j < 4 && !entranceBlocked; j++) {
							if(board.pawns[j+4].getTrackIndex()==32) {
								entranceBlocked = true;
								if(SorryGame.debug) {
									System.out.println("entranceBlocked = " + entranceBlocked);
								}
							}
						}
						
						if( entranceBlocked && board.pawns[i+4].getTrackIndex() == -1)
							yellowPawn[i].setEnabled(false);
						else
							yellowPawn[i].setEnabled(true);
					}

					yellowPawn[i].setToolTipText("Click the pawn to select.");
				}
			}
		} else {
			for (int i = 0; i < 4; i++) {
				yellowPawn[i].setEnabled(false);
				yellowPawn[i].setToolTipText(null);
			}
		}
	}// end of setYellowPawnEnabled()


	/**
	 * This method removes all lightened up squares from the track squares.
	 */
	public void clearTrack() {
		for (int i = 0; i < 56; i++) {
			outerTrackButtons[i].setIcon(null);
			outerTrackButtons[i].setEnabled(false);
			outerTrackButtons[i].setOpaque(false);
		}
		for (int i = 0; i < 5; i++) {
			redSafetyZoneSquarePane[i].removeAll();
			yellowSafetyZoneButtons[i].setIcon(null);
			yellowSafetyZoneButtons[i].setEnabled(false);
			yellowSafetyZoneButtons[i].setOpaque(false);
		}
		yellowHomePane.remove(yellowHomeButton);
		repaint();
		revalidate();
	}
	
	public int showCongratulationDialog() {
		return JOptionPane.showConfirmDialog(null, 
				"Congratulations! You WIN!/n/n/nClick OK to continue", "You win!", JOptionPane.OK_OPTION);
	}

	/**
	 * This is the tester method of SRGUI class.
	 * 
	 * @param args
	 *            the command line arguments
	 */
	/*public static void main(String args[]) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SorryGame();
			}
		});
		
		new SorryGame();
	}// end of the main()
*/

	public void start() {
		
	}
	
	public void stop() {
		
	}
	
	public void destroy() {
		
	}

	public boolean computerPlay() {
		int numMoves = 0;
		int previousSquareIndex;
		
		if(currentPlayer == COMPUTER) {
			squareBtnSelected = false;
			pawnSelected = false;
			selectedSquareIndex = -2;
			currentPawnIndex = -1;
	 		cardDrawn = false;	 		
			successfulMove = false;
			
			drawCardBtn.setEnabled(false);
			setYellowPawnEnabled(false);

			currentCard = board.drawCard();
			displayCard(currentCard.getPictureName());

			//check whether all red pawns are on start
			boolean isAllPawnOnStart = true;
			for (int i = 0; i < 4; i++ ) {
				if(board.pawns[i].getTrackIndex() != -1)
					isAllPawnOnStart = false;
			}
			
			if (isAllPawnOnStart && currentCard.getcardNum() != 1 && currentCard.getcardNum() != 2) {
				try {
					Thread.sleep(delayLength);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				clearCardDisplay();
				return true;
			}
			
			currentPawnIndex = board.pawns[0].id;
			previousSquareIndex = board.pawns[currentPawnIndex].getTrackIndex();

			int location = computer.findMove(board, currentCard);
			numMoves = board.movePawnTo(board.pawns[currentPawnIndex], location);
			
			
			//currentPawn = computer.selectPawn(board, currentCard);
			//movePawn(currentPawn.getID(), computer.findMove(board, currentCard));
			
			try {
				Thread.sleep(delayLength);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			selectPawn(currentPawnIndex);
			if(SorryGame.debug) {
				System.out.println("red pawn " + currentPawnIndex+" is moving to " + location);
			}
			if (numMoves > 0) {
				try {
					Thread.sleep(delayLength);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				movePawn(currentPawnIndex, previousSquareIndex, board.pawns[currentPawnIndex].getTrackIndex());
			}
			
			try {
				Thread.sleep(delayLength);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			clearCardDisplay();
		} 
		
		return successfulMove;
	}
	
	
	public boolean userPlay() {
		int numMoves = 0;
		int previousSquareIndex;
		
		squareBtnSelected = false;
		pawnSelected = false;
		selectedSquareIndex = -2;
		currentPawnIndex = -1;
 		cardDrawn = false;
		successfulMove = false;
		
		currentCard = board.drawCard();
		setCurrentCardImage(currentCard.getPictureName());
		drawCardBtn.setEnabled(true);
		
		do{}
		while (!cardDrawn);
		
		//check whether all pawns are on start
		boolean isAllPawnOnStart = true;
		for (int i = 4; i < 8; i++ ) {
			if(board.pawns[i].getTrackIndex() != -1)
				isAllPawnOnStart = false;
		}
		
		if (isAllPawnOnStart && currentCard.getcardNum() != 1 && currentCard.getcardNum() != 2) {
			
			try {
				Thread.sleep(delayLength);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			clearCardDisplay();
			return true;
		}

		setYellowPawnEnabled(true);
		
		do{}
		while (!pawnSelected);
		
		if(SorryGame.debug) {
			System.out.println("currently selected pawn is" + currentPawnIndex);
		}
		
		previousSquareIndex = board.pawns[currentPawnIndex].getTrackIndex();
		int[] movesFound = board.findMoves(board.pawns[currentPawnIndex], currentCard);
		if(SorryGame.debug) {
			for (int i = 0; i < movesFound.length; i++)
				System.out.print("found moves: " + movesFound[i] + ", ");
			System.out.println();
		}
		if (movesFound.length > 0)
			squareLightUp(movesFound);
		else
			return true;
		
		do{}
		while (!squareBtnSelected);
		
		numMoves = board.movePawnTo(board.pawns[currentPawnIndex], selectedSquareIndex);
		movePawn(currentPawnIndex, previousSquareIndex, selectedSquareIndex);
		
		if (SorryGame.debug){
			System.out.println("numMoves = " + numMoves);
		}
			
		if( successfulMove && board.pawns[currentPawnIndex].getTrackIndex() != selectedSquareIndex) {
			successfulMove = false;			
			int newLocation = board.pawns[currentPawnIndex].getTrackIndex();
			try {
				Thread.sleep(delayLength);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			movePawn(currentPawnIndex, selectedSquareIndex, newLocation);
			
			if (SorryGame.debug){
				System.out.println("pawn slided to the new location");
			}
			
		}
			
		if (successfulMove && currentCard.getcardNum() == 7 && numMoves < 7) {
			
			clearTrack();

			SRRule secondMoveRule = new SRRule(7-numMoves);
			pawnSelected = false;
			squareBtnSelected = false;
			selectedSquareIndex = -2;
			currentPawnIndex = -1;
			successfulMove = false;
			
			setYellowPawnEnabled(true);

			do{}
			while (!pawnSelected);
			
			previousSquareIndex = board.pawns[currentPawnIndex].getTrackIndex();
			movesFound = board.findMoves(board.pawns[currentPawnIndex], secondMoveRule);
			if (movesFound.length > 0)
				squareLightUp(movesFound);
			else
				return true;
			
			do{}
			while (!squareBtnSelected);
			
			if(SorryGame.debug) {
				System.out.println("currently selected square is " + selectedSquareIndex);
			}
			numMoves = board.movePawnTo(board.pawns[currentPawnIndex], selectedSquareIndex);
			movePawn(currentPawnIndex, previousSquareIndex, selectedSquareIndex);
			
			if (SorryGame.debug){
				System.out.println("numMoves = " + numMoves);
			}
			
			if( successfulMove && board.pawns[currentPawnIndex].getTrackIndex() != selectedSquareIndex) {
				successfulMove = false;
				int newLocation = board.pawns[currentPawnIndex].getTrackIndex();
				try {
					Thread.sleep(delayLength);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				movePawn(currentPawnIndex, selectedSquareIndex, newLocation);
				
				if (SorryGame.debug){
					System.out.println("pawn slided to the new location");
				}
				
			}
			
		}

		        
		try {
			Thread.sleep(delayLength);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		clearCardDisplay();

		return true;
		//System.out.println("pawn is now on square "+board.pawns[currentPawnIndex].getTrackIndex());
	}

	public void setCurrentPlayer(int playerID) {
		currentPlayer = playerID;
	}

}// end of SRGUI class
