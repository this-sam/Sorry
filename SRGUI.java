/*
 * SRGUI.java
 * This class will handle all visual components and movements of images.
 * It is well separated from the game logic and interacts with SRGameBoard through SRDriver.
 * 
 */

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * This class will handle all visual components and movements of images.
 * It is well separated from the game logic and interacts with SRGameBoard through SRDriver.
 * 
 * @author Sam Brown, Caleb Cousins, Taylor Krammen, and Yucan Zhang
 *
 */
public class SRGUI extends JFrame {
	
	// Variables declaration
    private JLabel authorNameLabel;
    private JScrollPane infoAreaPane;
    private JTextArea infoArea;
    private JButton startNewGameBtn, quitBtn;
    private JLabel gameBoardImageLabel;
    private JLayeredPane gameBoardLayeredPane;
    private JButton redPawn[] = new JButton[4];
    private JButton yellowPawn[] = new JButton[4];
    private JPanel redHomePane;
    private JPanel transparentButtonPane;
    private JPanel transparentSquarePane[] = new JPanel[225];
    private JButton outerTrackButtons[] = new JButton[56];
    private boolean occupiedTrackSquare[] = new boolean[68];
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
    private int selectedPawnNum;
    private int outerTrackPaneIndex[] = new int[56];
    private GroupLayout transparentSquarePaneLayout[] = new GroupLayout[56];
    private GroupLayout redSafetyZoneSquarePaneLayout[] = new GroupLayout[5];
    private GroupLayout yellowSafetyZoneSquarePaneLayout[] = new GroupLayout[5];
    private GroupLayout redStartSquarePaneLayout[] = new GroupLayout[4];
    private GroupLayout yellowStartSquarePaneLayout[] = new GroupLayout[4];
    private int pawnPositions[] = new int[8];
    private JPanel drawCardPane, displayCardPane;
    private JButton drawCardBtn;
    private JLabel displayCardLabel;
    private String currentCardImage;
    // End of variables declaration
    
    
    /**
     * Creates new form SRGUI
     */
    public SRGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {
    	//set up the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("SORRY! Game - SB, CC, TK, YZ Group - CS205 Final Project");
        setFont(new Font("Comic Sans MS", 0, 12));
        setPreferredSize(new Dimension(781, 580));
        /***************************************************************************************/
        
        
        //create authour information. 
        //It's not displayed in current window, because the window has to fit the 800*600 screen.
	    authorNameLabel = new JLabel();
	    authorNameLabel.setFont(new Font("Comic Sans MS", 0, 12));
        authorNameLabel.setText("SORRY! Game - SB, CC, TK, YZ");
        authorNameLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        /***************************************************************************************/

        
        //create text field for displaying tooltip or help information
        infoArea = new JTextArea();
        infoAreaPane = new JScrollPane();
        infoAreaPane.setPreferredSize(new Dimension(200, 400));
        infoArea.setColumns(10);
        infoArea.setRows(5);
        infoAreaPane.setViewportView(infoArea);
        /***************************************************************************************/

        
        //create the button for starting a new game
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

        
        //create the button for quiting the game
        quitBtn = new JButton();
        quitBtn.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); 
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

        
        //central layered panel holds the game board picture and all components
        gameBoardLayeredPane = new JLayeredPane();
        gameBoardLayeredPane.setPreferredSize(new Dimension(525, 525));

        //set up the game board image as the background
        gameBoardImageLabel = new JLabel();
        gameBoardImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameBoardImageLabel.setIcon(new ImageIcon("SRgameBoardImage.jpg")); // NOI18N
        gameBoardImageLabel.setPreferredSize(new Dimension(525, 525));
        gameBoardImageLabel.setBounds(0, 0, 525, 525);
        gameBoardLayeredPane.add(gameBoardImageLabel, JLayeredPane.DEFAULT_LAYER);
        /***************************************************************************************/

        
        //the panel holds all track squares with transparent buttons
        transparentButtonPane = new JPanel();
        transparentButtonPane.setBounds(0, 0, 525, 525);
        gameBoardLayeredPane.add(transparentButtonPane, JLayeredPane.PALETTE_LAYER);
        transparentButtonPane.setOpaque(false);
        transparentButtonPane.setLayout(new GridLayout(0, 15));
        for (int i = 0; i < 225; i++) {
        	transparentSquarePane[i] = new JPanel();
        	transparentSquarePane[i].setOpaque(false);
        	//transparentSquarePane[i].setBorder(BorderFactory.createRaisedBevelBorder());
        	transparentButtonPane.add(transparentSquarePane[i]);
        }
        /***************************************************************************************/

        
        //set up buttons on outer track.
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
            transparentSquarePaneLayout[i] = new GroupLayout(transparentSquarePane[i]);
            transparentSquarePane[i].setLayout(transparentSquarePaneLayout[i]);
            transparentSquarePaneLayout[i].setHorizontalGroup(
            		transparentSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
            		.addGroup(transparentSquarePaneLayout[i].createSequentialGroup()
		                .addComponent(outerTrackButtons[i])
		                .addGap(0, 0, Short.MAX_VALUE))
	                );
            transparentSquarePaneLayout[i].setVerticalGroup(
            		transparentSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(transparentSquarePaneLayout[i].createSequentialGroup()
	                    .addComponent(outerTrackButtons[i])
	                    .addGap(0, 0, Short.MAX_VALUE))
            		);
            outerTrackPaneIndex[i] = i;
        }
        for (int i = 15, j = 29; i < 29 && j < 225; i++, j += 15) {
        	transparentSquarePaneLayout[i] = new GroupLayout(transparentSquarePane[j]);
            transparentSquarePane[j].setLayout(transparentSquarePaneLayout[i]);
            transparentSquarePaneLayout[i].setHorizontalGroup(
            		transparentSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
            		.addGroup(transparentSquarePaneLayout[i].createSequentialGroup()
		                .addComponent(outerTrackButtons[i])
		                .addGap(0, 0, Short.MAX_VALUE))
	                );
            transparentSquarePaneLayout[i].setVerticalGroup(
            		transparentSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(transparentSquarePaneLayout[i].createSequentialGroup()
	                    .addComponent(outerTrackButtons[i])
	                    .addGap(0, 0, Short.MAX_VALUE))
            		);
            outerTrackPaneIndex[i] = j;
        }
        for (int i = 29, j = 223; i < 43 && j > 209; i++, j--) {
        	transparentSquarePaneLayout[i] = new GroupLayout(transparentSquarePane[j]);
            transparentSquarePane[j].setLayout(transparentSquarePaneLayout[i]);
            transparentSquarePaneLayout[i].setHorizontalGroup(
            		transparentSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
            		.addGroup(transparentSquarePaneLayout[i].createSequentialGroup()
		                .addComponent(outerTrackButtons[i])
		                .addGap(0, 0, Short.MAX_VALUE))
	                );
            transparentSquarePaneLayout[i].setVerticalGroup(
            		transparentSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(transparentSquarePaneLayout[i].createSequentialGroup()
	                    .addComponent(outerTrackButtons[i])
	                    .addGap(0, 0, Short.MAX_VALUE))
            		);
            outerTrackPaneIndex[i] = j;
        }
        for (int i = 43, j = 195; i < 56 && j > 14; i++, j -= 15) {
        	transparentSquarePaneLayout[i] = new GroupLayout(transparentSquarePane[j]);
            transparentSquarePane[j].setLayout(transparentSquarePaneLayout[i]);
            transparentSquarePaneLayout[i].setHorizontalGroup(
            		transparentSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
            		.addGroup(transparentSquarePaneLayout[i].createSequentialGroup()
		                .addComponent(outerTrackButtons[i])
		                .addGap(0, 0, Short.MAX_VALUE))
	                );
            transparentSquarePaneLayout[i].setVerticalGroup(
            		transparentSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(transparentSquarePaneLayout[i].createSequentialGroup()
	                    .addComponent(outerTrackButtons[i])
	                    .addGap(0, 0, Short.MAX_VALUE))
            		);
            outerTrackPaneIndex[i] = j;
        }
        //end of creating outer track buttons
        /***************************************************************************************/

        
        //set the layout for the frame, add and align all the components.
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(authorNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(286, 286, 286))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(gameBoardLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(infoAreaPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(30, 30, 30)
                                    .addComponent(startNewGameBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(64, 64, 64)
                                    .addComponent(quitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(22, 22, 22))))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(authorNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(infoAreaPane, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(startNewGameBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(quitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(gameBoardLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(0, Short.MAX_VALUE))
            );   
        /***************************************************************************************/
        /* end of setting up main components */
        /***************************************************************************************/

        //set up the red pawns
        for (int i = 0; i < 4; i++) {
        	redPawn[i] = new JButton();
        	redPawn[i].setIcon(new ImageIcon("redPawn.jpg")); // NOI18N
        	redPawn[i].setActionCommand("" + i);
        	redPawn[i].setDisabledIcon(new ImageIcon("redPawn.jpg"));
        	redPawn[i].setMaximumSize(new Dimension(35, 35));
        	redPawn[i].setMinimumSize(new Dimension(35, 35));
        	redPawn[i].setPreferredSize(new Dimension(35, 35));
        }
        /***************************************************************************************/

        
        //set up the start panel for the red player
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
        	redStartSquarePaneLayout[i].setHorizontalGroup(
        			redStartSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(redStartSquarePaneLayout[i].createSequentialGroup()
                        .addComponent(redPawn[i], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                );
        	redStartSquarePaneLayout[i].setVerticalGroup(
        			redStartSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(redStartSquarePaneLayout[i].createSequentialGroup()
                        .addComponent(redPawn[i], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                );
        	
            redStartPane.add(redStartSquarePane[i]);
        }
        /***************************************************************************************/

        
        //set up the home panel for the red player
        redHomePane = new JPanel();
        redHomePane.setOpaque(false);
        redHomePane.setLayout(new FlowLayout(FlowLayout.CENTER));
        redHomePane.setBounds(50, 190, 90, 90);
        gameBoardLayeredPane.add(redHomePane, JLayeredPane.MODAL_LAYER);
        /***************************************************************************************/

        
        //set up the safety zone panel for the red player
        redSafetyZonePane = new JPanel();
        redSafetyZonePane.setMaximumSize(new Dimension(35, 165));
        redSafetyZonePane.setMinimumSize(new Dimension(35, 165));
        redSafetyZonePane.setOpaque(false);
        redSafetyZonePane.setLayout(new GridLayout(0, 1));
        redSafetyZonePane.setBounds(70, 35, 35, 165);
        gameBoardLayeredPane.add(redSafetyZonePane, JLayeredPane.MODAL_LAYER);
        //set up the red safety zone squares
        for (int i = 0; i < 5; i++) {
        	redSafetyZoneSquarePane[i] = new JPanel();
        	redSafetyZoneSquarePane[i].setMaximumSize(new Dimension(35, 35));
        	redSafetyZoneSquarePane[i].setMinimumSize(new Dimension(35, 35));
        	redSafetyZoneSquarePane[i].setOpaque(false);
        	redSafetyZoneSquarePane[i].setPreferredSize(new Dimension(35, 35));
        	
        	redSafetyZoneSquarePaneLayout[i] = new GroupLayout(redSafetyZoneSquarePane[i]);
        	redSafetyZoneSquarePane[i].setLayout(redSafetyZoneSquarePaneLayout[i]);
        	redSafetyZoneSquarePaneLayout[i].setHorizontalGroup(
        		redSafetyZoneSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 35, Short.MAX_VALUE)
            );
        	redSafetyZoneSquarePaneLayout[i].setVerticalGroup(
        		redSafetyZoneSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 165, Short.MAX_VALUE)
            );
        	
        	redSafetyZonePane.add(redSafetyZoneSquarePane[i]);
        }
        /***************************************************************************************/
        /* end of setting up components specific for red pawns */
        /***************************************************************************************/

        
        //set up the yellow pawns
        for (int i = 0; i < 4; i++) {
        	yellowPawn[i] = new JButton();
        	yellowPawn[i].setIcon(new javax.swing.ImageIcon("yellowPawn.jpg")); // NOI18N
        	yellowPawn[i].setActionCommand("" + (4+i));
        	yellowPawn[i].setDisabledIcon(new ImageIcon("yellowPawn.jpg"));
        	yellowPawn[i].setMaximumSize(new java.awt.Dimension(35, 35));
        	yellowPawn[i].setMinimumSize(new java.awt.Dimension(35, 35));
        	yellowPawn[i].setPreferredSize(new java.awt.Dimension(35, 35));
        	yellowPawn[i].addActionListener(new PawnButtonListener());
        }
        selectedPawnNum = -1;// the player can only select yellow pawns.
        /***************************************************************************************/

        //set up the start panel for the yellow player
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
        	yellowStartSquarePaneLayout[i] = new GroupLayout(yellowStartSquarePane[i]);
        	yellowStartSquarePane[i].setLayout(yellowStartSquarePaneLayout[i]);
        	yellowStartSquarePaneLayout[i].setHorizontalGroup(
        			yellowStartSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(yellowStartSquarePaneLayout[i].createSequentialGroup()
                        .addComponent(yellowPawn[i], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                );
        	yellowStartSquarePaneLayout[i].setVerticalGroup(
        			yellowStartSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(yellowStartSquarePaneLayout[i].createSequentialGroup()
                        .addComponent(yellowPawn[i], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                );
        	
        	yellowStartPane.add(yellowStartSquarePane[i]);
        }
        /***************************************************************************************/

        
        //set up the home panel for the yellow player
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
        yellowHomeButton.setActionCommand("" + 67); //track square index
        yellowHomeButton.addActionListener(new SquareButtonListener());
        yellowHomeButton.setEnabled(false);
        /***************************************************************************************/

        
        //set up the safety zone panel for the yellow player
        yellowSafetyZonePane = new JPanel();
        yellowSafetyZonePane.setMaximumSize(new Dimension(35, 165));
        yellowSafetyZonePane.setMinimumSize(new Dimension(35, 165));
        yellowSafetyZonePane.setOpaque(false);
        yellowSafetyZonePane.setLayout(new GridLayout(0, 1));
        yellowSafetyZonePane.setBounds(420, 325, 35, 165);
        gameBoardLayeredPane.add(yellowSafetyZonePane, JLayeredPane.MODAL_LAYER);
        //Set up the yellow safety zone squares
        for (int i = 4; i >= 0; i--) {
        	yellowSafetyZoneSquarePane[i] = new JPanel();
        	yellowSafetyZoneSquarePane[i].setMaximumSize(new Dimension(35, 35));
        	yellowSafetyZoneSquarePane[i].setMinimumSize(new Dimension(35, 35));
        	yellowSafetyZoneSquarePane[i].setOpaque(false);
        	yellowSafetyZoneSquarePane[i].setPreferredSize(new Dimension(35, 35));
        	
        	yellowSafetyZoneSquarePaneLayout[i] = new GroupLayout(yellowSafetyZoneSquarePane[i]);
        	yellowSafetyZoneSquarePane[i].setLayout(yellowSafetyZoneSquarePaneLayout[i]);
        	yellowSafetyZoneSquarePaneLayout[i].setHorizontalGroup(
        		yellowSafetyZoneSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 35, Short.MAX_VALUE)
            );
        	yellowSafetyZoneSquarePaneLayout[i].setVerticalGroup(
        		yellowSafetyZoneSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 165, Short.MAX_VALUE)
            );
        	
            yellowSafetyZonePane.add(yellowSafetyZoneSquarePane[i]);
        }
        //set up the safety zone buttons for the yellow player
        for (int i = 0; i < 5; i++) {
        	yellowSafetyZoneButtons[i] = new JButton();
        	yellowSafetyZoneButtons[i].setOpaque(false);
        	yellowSafetyZoneButtons[i].setMaximumSize(new Dimension(35, 35));
        	yellowSafetyZoneButtons[i].setMinimumSize(new Dimension(35, 35));
        	yellowSafetyZoneButtons[i].setPreferredSize(new Dimension(35, 35));
        	yellowSafetyZoneButtons[i].setActionCommand("" + (i+62));
        	yellowSafetyZoneButtons[i].addActionListener(new SquareButtonListener());
        	yellowSafetyZoneButtons[i].setEnabled(false);
        }
        for (int i = 0; i < 5; i++) {
        	yellowSafetyZoneSquarePaneLayout[i].setHorizontalGroup(
        			yellowSafetyZoneSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
            		.addGroup(yellowSafetyZoneSquarePaneLayout[i].createSequentialGroup()
		                .addComponent(yellowSafetyZoneButtons[i])
		                .addGap(0, 0, Short.MAX_VALUE))
	                );
        	yellowSafetyZoneSquarePaneLayout[i].setVerticalGroup(
        			yellowSafetyZoneSquarePaneLayout[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(yellowSafetyZoneSquarePaneLayout[i].createSequentialGroup()
	                    .addComponent(yellowSafetyZoneButtons[i])
	                    .addGap(0, 0, Short.MAX_VALUE))
            		);        
        }
        /***************************************************************************************/
        /* end of setting up components specific for yellow pawns */
        /***************************************************************************************/

        //a boolean array keeps track of which squares are occupied by pawns (in case of bumping)
        for (int i = 0; i < 68; i++) {
        	occupiedTrackSquare[i] = false;
        }
        /***************************************************************************************/

        //an int array keeps track of which square a pawn is on
        //easier to remove them from previous position
        for (int i = 0; i < 8; i++) {
        	pawnPositions[i] = -1;
        }
        /***************************************************************************************/

        //set up the panel for displaying the draw_a_card button
        drawCardPane = new JPanel();
        drawCardBtn = new JButton();
        drawCardPane.setMinimumSize(new java.awt.Dimension(90, 125));
        drawCardPane.setPreferredSize(new java.awt.Dimension(90, 125));
        drawCardPane.setBounds(150, 280, 90, 125);
        gameBoardLayeredPane.add(drawCardPane, JLayeredPane.MODAL_LAYER);
        //create the drawCardBtn for drawing a card
        drawCardBtn.setIcon(new ImageIcon("drawACard.jpg")); 
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
        //set up the layout of drawCardPane and add the button into the pane
        GroupLayout drawCardPaneLayout = new GroupLayout(drawCardPane);
        drawCardPane.setLayout(drawCardPaneLayout);
        drawCardPaneLayout.setHorizontalGroup(
            drawCardPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(drawCardPaneLayout.createSequentialGroup()
                .addComponent(drawCardBtn, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        drawCardPaneLayout.setVerticalGroup(
            drawCardPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(drawCardPaneLayout.createSequentialGroup()
                .addComponent(drawCardBtn, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        /***************************************************************************************/
        
        //set up the label and panel for displaying the newly drawn card.
        displayCardPane = new JPanel();
        displayCardLabel = new JLabel();
        displayCardPane.setBounds(270, 100, 120, 164);
        displayCardPane.setOpaque(false);
        gameBoardLayeredPane.add(displayCardPane, JLayeredPane.MODAL_LAYER);
        currentCardImage = "";
        displayCardLabel.setToolTipText("This is the card displaying the moves and rules");
        displayCardLabel.setMaximumSize(new Dimension(120, 164));
        displayCardLabel.setMinimumSize(new Dimension(120, 164));
        displayCardLabel.setPreferredSize(new Dimension(120, 164));
        displayCardLabel.setOpaque(false);
        GroupLayout displayCardPaneLayout = new GroupLayout(displayCardPane);
        displayCardPane.setLayout(displayCardPaneLayout);
        displayCardPaneLayout.setHorizontalGroup(
            displayCardPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(displayCardLabel, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );
        displayCardPaneLayout.setVerticalGroup(
            displayCardPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(displayCardLabel, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
        );
        /***************************************************************************************/ 
               
        pack();
    }// end of initComponents()
    

    /**
     * This method lights up the possible destination squares with orange.
     * If the square is occupied by a pawn (should not be a yellow pawn because the logic is handled by the SRGameBoard and SRDriver),
     * the square will be lighted up with red.
     * 
     * @param squareIndexArray The index array representing the position of squares, designated by the SRDriver
     */
    public void squareLightUp(int[] squareIndexArray) {
        int buttonNum = squareIndexArray.length;
      
        for (int i = 0; i < buttonNum; i++) {
        	//when the squares are on the outer track
        	if ( squareIndexArray[i] >= 0 && squareIndexArray[i] < 56) {
	        	outerTrackButtons[squareIndexArray[i]].setIcon(new ImageIcon("squareButton.jpg"));
	        	outerTrackButtons[squareIndexArray[i]].setEnabled(true);
	        	outerTrackButtons[squareIndexArray[i]].setToolTipText("Click the square to move the pawn");
        	}
        	//when the squares are on the yellow safety zone
        	else if (squareIndexArray[i] >= 62 && squareIndexArray[i] < 67) {
        		yellowSafetyZoneButtons[squareIndexArray[i]-62].setIcon(new ImageIcon("squareButton.jpg"));
        		yellowSafetyZoneButtons[squareIndexArray[i]-62].setEnabled(true);
        		yellowSafetyZoneButtons[squareIndexArray[i]-62].setToolTipText("Click the square to move the pawn.");
        	}
        	//when the squares are on the yellow Home
        	else if (squareIndexArray[i] == 67) {
        		yellowHomeButton.setIcon(new ImageIcon("squareButton.jpg"));
        		yellowHomeButton.setEnabled(true);
        		yellowHomeButton.setToolTipText("Click the squre to move pawn home");
        		yellowHomePane.add(yellowHomeButton);
        	}
        	//only the squares accessible to the yellow pawns can be lighted up
        	//all the other squares are not allowed (should be handled by the logic, so this is also a test/debug helper
        	else {
        		System.out.println("ERROR: " + squareIndexArray[i] + " wrong squares to light up!!!!");
        	}
        }
        repaint();
        revalidate();
    }//end of squareLightUp()

    
    /**
     * This method lights up the square with red
     * 
     * @param squareIndex Represents the position of the square that needs to be lightened up
     */
    public void bumpedSquareLightUp(int squareIndex) {
    	//here is just to double check whether this square is occupied by a pawn
    	//It should be handled by the logic in the first place
    	if (occupiedTrackSquare[squareIndex]) {
			//First, remove the pawn from the square
    		transparentSquarePane[outerTrackPaneIndex[squareIndex]].removeAll();
			transparentSquarePaneLayout[squareIndex].setHorizontalGroup(
            		transparentSquarePaneLayout[squareIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
            		.addGroup(transparentSquarePaneLayout[squareIndex].createSequentialGroup()
		                .addComponent(outerTrackButtons[squareIndex])
		                .addGap(0, 0, Short.MAX_VALUE))
	                );
            transparentSquarePaneLayout[squareIndex].setVerticalGroup(
            		transparentSquarePaneLayout[squareIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(transparentSquarePaneLayout[squareIndex].createSequentialGroup()
	                    .addComponent(outerTrackButtons[squareIndex])
	                    .addGap(0, 0, Short.MAX_VALUE))
            		);
            //Then add the bumpBotton image back to the square
			outerTrackButtons[squareIndex].setIcon(new ImageIcon("bumpButton.jpg"));
			outerTrackButtons[squareIndex].setEnabled(true);
    	}
    	
    	repaint();
    	revalidate();
    }//end of bumpedSquareLightUp()
    
    
    /**
     * This method moves a pawn from current place to the destination square.
     * 
     * @param pawnNum designates which pawn will be moved
     * @param trackSquareIndex represents the index of destination square
     * @return true if the pawn has been successfully moved.
     */
    public boolean movePawn(int pawnNum, int trackSquareIndex) {
    	//a flag shows whether the movement is successful or not
    	boolean successfulMovement;
    	//int stores the previous and destination position of the pawn
    	int destinationSquarePaneIndex, previousSquarePaneIndex;
    	
    	
    	/************************************************/
    	/****************move a red pawn*****************/
    	if (pawnNum > 0 && pawnNum < 4) {
    		
    		/************remove the red pawn from previous location************/
        	//the pawn was previously on the outer track
    		if(pawnPositions[pawnNum] >=0 && pawnPositions[pawnNum] < 56) {
    	    	previousSquarePaneIndex = outerTrackPaneIndex[pawnPositions[pawnNum]];
    			transparentSquarePane[previousSquarePaneIndex].remove(redPawn[pawnNum]);
    			outerTrackButtons[pawnPositions[pawnNum]].setIcon(null);
    			outerTrackButtons[pawnPositions[pawnNum]].setOpaque(false);
    			outerTrackButtons[pawnPositions[pawnNum]].setEnabled(false);
    			transparentSquarePaneLayout[pawnPositions[pawnNum]].setHorizontalGroup(
	            		transparentSquarePaneLayout[pawnPositions[pawnNum]].createParallelGroup(GroupLayout.Alignment.LEADING)
	            		.addGroup(transparentSquarePaneLayout[pawnPositions[pawnNum]].createSequentialGroup()
			                .addComponent(outerTrackButtons[pawnPositions[pawnNum]])
			                .addGap(0, 0, Short.MAX_VALUE))
		                );
	            transparentSquarePaneLayout[pawnPositions[pawnNum]].setVerticalGroup(
	            		transparentSquarePaneLayout[pawnPositions[pawnNum]].createParallelGroup(GroupLayout.Alignment.LEADING)
	                    .addGroup(transparentSquarePaneLayout[pawnPositions[pawnNum]].createSequentialGroup()
		                    .addComponent(outerTrackButtons[pawnPositions[pawnNum]])
		                    .addGap(0, 0, Short.MAX_VALUE))
	            		);
    			occupiedTrackSquare[pawnPositions[pawnNum]] = false;
    		}
			// the pawn was previously in the red safety zone
    		else if (pawnPositions[pawnNum] >=56 && pawnPositions[pawnNum] < 61) {
    			previousSquarePaneIndex = pawnPositions[pawnNum] - 56;
    			redSafetyZoneSquarePane[previousSquarePaneIndex].remove(redPawn[pawnNum]); 
    			occupiedTrackSquare[pawnPositions[pawnNum]] = false;
    		}
			// the pawn was previously at start
    		else if (pawnPositions[pawnNum] == -1) {
    			previousSquarePaneIndex = pawnNum;
    			redStartSquarePane[previousSquarePaneIndex].remove(redPawn[pawnNum]);
    		}
    		// the pawn was either in the home square or in an invalid position 
    		//This situation should not occur if the game running correctly. So it is also a debug/test tool.
    		else 
				System.out.println("ERROR: This pawn is in the home or an invalid position. Current position: " + pawnPositions[pawnNum]);
    		//end of removing the red pawn from previous location
    		
    		
    		/************move the red pawn to the destination square************/
    		//move the pawn to a square on the outer track
    		if (trackSquareIndex >= 0 && trackSquareIndex < 56) {
    			destinationSquarePaneIndex = outerTrackPaneIndex[trackSquareIndex];
	    		transparentSquarePane[destinationSquarePaneIndex].remove(outerTrackButtons[trackSquareIndex]);
	    		transparentSquarePaneLayout[trackSquareIndex].setHorizontalGroup(
	            		transparentSquarePaneLayout[trackSquareIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
	            		.addGroup(transparentSquarePaneLayout[trackSquareIndex].createSequentialGroup()
			                .addComponent(redPawn[pawnNum])
			                .addGap(0, 0, Short.MAX_VALUE))
		                );
	            transparentSquarePaneLayout[trackSquareIndex].setVerticalGroup(
	            		transparentSquarePaneLayout[trackSquareIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
	                    .addGroup(transparentSquarePaneLayout[trackSquareIndex].createSequentialGroup()
		                    .addComponent(redPawn[pawnNum])
		                    .addGap(0, 0, Short.MAX_VALUE))
	            		);
	            occupiedTrackSquare[trackSquareIndex] = true;
	            pawnPositions[pawnNum] = trackSquareIndex;
	            successfulMovement = true;
    		}
    		//move red pawn to the red safety zone
    		else if ( trackSquareIndex >= 56 && trackSquareIndex < 61) {
    			destinationSquarePaneIndex = trackSquareIndex - 62;
    			redSafetyZoneSquarePane[destinationSquarePaneIndex].remove(yellowSafetyZoneButtons[destinationSquarePaneIndex]);
    			redSafetyZoneSquarePaneLayout[destinationSquarePaneIndex].setHorizontalGroup(
    					redSafetyZoneSquarePaneLayout[destinationSquarePaneIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
    					.addGroup(redSafetyZoneSquarePaneLayout[destinationSquarePaneIndex].createSequentialGroup()
    							.addComponent(redPawn[pawnNum])
    							.addGap(0, 0, Short.MAX_VALUE))
    					);
    			redSafetyZoneSquarePaneLayout[destinationSquarePaneIndex].setVerticalGroup(
    					redSafetyZoneSquarePaneLayout[destinationSquarePaneIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
    					.addGroup(redSafetyZoneSquarePaneLayout[destinationSquarePaneIndex].createSequentialGroup()
    							.addComponent(redPawn[pawnNum])
    							.addGap(0, 0, Short.MAX_VALUE))
    					);   
                occupiedTrackSquare[trackSquareIndex] = true;
                pawnPositions[pawnNum] = trackSquareIndex;
	            successfulMovement = true;
    		}
    		//move red pawn to its home
    		else if (trackSquareIndex == 61) {
    			redHomePane.add(redPawn[pawnNum]);
    			redPawn[pawnNum].setEnabled(false);
    			pawnPositions[pawnNum] = -2;
	            successfulMovement = true;
    		}
    		//move red pawn back to start
    		else if (trackSquareIndex == -1) {
    			destinationSquarePaneIndex = pawnNum;
    			redStartSquarePaneLayout[destinationSquarePaneIndex].setHorizontalGroup(
    					redStartSquarePaneLayout[destinationSquarePaneIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(redStartSquarePaneLayout[destinationSquarePaneIndex].createSequentialGroup()
                            .addComponent(redPawn[destinationSquarePaneIndex], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                    );
    			redStartSquarePaneLayout[destinationSquarePaneIndex].setVerticalGroup(
    					redStartSquarePaneLayout[destinationSquarePaneIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(redStartSquarePaneLayout[destinationSquarePaneIndex].createSequentialGroup()
                            .addComponent(redPawn[destinationSquarePaneIndex], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                    );
	            successfulMovement = true;
    		}
    		//cannot move red pawn to the yellow safety zone or yellow home. Debug/test tool.
    		else {
    			System.out.println("ERROR: Red pawn can not move to the designated square!!!");
	            successfulMovement = false;
    		}
    		//end of adding a red pawn to the destination square
    	}
    	//end of moving a red pawn
    	
    	
    	/************************************************/
    	/****************move a yellow pawn*****************/
    	else if (pawnNum >= 4 && pawnNum < 8) {
    		
    		/************remove the yellow pawn from previous location************/
    		//the pawn was previously on the outer track
    		if(pawnPositions[pawnNum] >=0 && pawnPositions[pawnNum] < 56) {
    	    	previousSquarePaneIndex = outerTrackPaneIndex[pawnPositions[pawnNum]];
    			transparentSquarePane[previousSquarePaneIndex].remove(yellowPawn[pawnNum-4]);
    			outerTrackButtons[pawnPositions[pawnNum]].setIcon(null);
    			outerTrackButtons[pawnPositions[pawnNum]].setOpaque(false);
    			outerTrackButtons[pawnPositions[pawnNum]].setEnabled(false);
    			transparentSquarePaneLayout[pawnPositions[pawnNum]].setHorizontalGroup(
	            		transparentSquarePaneLayout[pawnPositions[pawnNum]].createParallelGroup(GroupLayout.Alignment.LEADING)
	            		.addGroup(transparentSquarePaneLayout[pawnPositions[pawnNum]].createSequentialGroup()
			                .addComponent(outerTrackButtons[pawnPositions[pawnNum]])
			                .addGap(0, 0, Short.MAX_VALUE))
		                );
	            transparentSquarePaneLayout[pawnPositions[pawnNum]].setVerticalGroup(
	            		transparentSquarePaneLayout[pawnPositions[pawnNum]].createParallelGroup(GroupLayout.Alignment.LEADING)
	                    .addGroup(transparentSquarePaneLayout[pawnPositions[pawnNum]].createSequentialGroup()
		                    .addComponent(outerTrackButtons[pawnPositions[pawnNum]])
		                    .addGap(0, 0, Short.MAX_VALUE))
	            		);
    			occupiedTrackSquare[pawnPositions[pawnNum]] = false;
    		}
    		// the pawn was previously in the yellow safety zone
    		else if (pawnPositions[pawnNum] >=62 && pawnPositions[pawnNum] < 67) {
    			previousSquarePaneIndex = pawnPositions[pawnNum] - 62;
    			yellowSafetyZoneSquarePane[previousSquarePaneIndex].remove(yellowPawn[pawnNum-4]);
    			yellowSafetyZoneButtons[previousSquarePaneIndex].setIcon(null);
    			yellowSafetyZoneButtons[previousSquarePaneIndex].setOpaque(false);
    			yellowSafetyZoneButtons[previousSquarePaneIndex].setEnabled(false);
    			yellowSafetyZoneSquarePaneLayout[previousSquarePaneIndex].setHorizontalGroup(
            			yellowSafetyZoneSquarePaneLayout[previousSquarePaneIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
                		.addGroup(yellowSafetyZoneSquarePaneLayout[previousSquarePaneIndex].createSequentialGroup()
    		                .addComponent(yellowSafetyZoneButtons[previousSquarePaneIndex])
    		                .addGap(0, 0, Short.MAX_VALUE))
    	                );
            	yellowSafetyZoneSquarePaneLayout[previousSquarePaneIndex].setVerticalGroup(
            			yellowSafetyZoneSquarePaneLayout[previousSquarePaneIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(yellowSafetyZoneSquarePaneLayout[previousSquarePaneIndex].createSequentialGroup()
    	                    .addComponent(yellowSafetyZoneButtons[previousSquarePaneIndex])
    	                    .addGap(0, 0, Short.MAX_VALUE))
                		);  
    			occupiedTrackSquare[pawnPositions[pawnNum]] = false;
    		}
    		//the yellow pawn was previously at start
    		else if (pawnPositions[pawnNum] == -1) {
    			previousSquarePaneIndex = pawnNum - 4;
    			yellowStartSquarePane[previousSquarePaneIndex].remove(yellowPawn[pawnNum-4]);
    		}
    		// the pawn was either in the home square or in an invalid position 
    		// This situation should not occur if the game running correctly. Debug/test tool
    		else {
    			System.out.println("ERROR: This pawn is in the home or an invalid position. Current position: " + pawnPositions[pawnNum]);
	            successfulMovement = false;
    		}
    		//end of removing the yellow pawn from previous location
    		
    		
    		/************move the yellow pawn to the destination square************/
    		//change the selected pawn (grey) back to yellow color
    		yellowPawn[pawnNum-4].setIcon(new ImageIcon("yellowPawn.jpg"));

    		//move the pawn to a square on the outer track
    		if (trackSquareIndex >= 0 && trackSquareIndex < 56) {
    			destinationSquarePaneIndex = outerTrackPaneIndex[trackSquareIndex];
	    		transparentSquarePane[destinationSquarePaneIndex].remove(outerTrackButtons[trackSquareIndex]);
	    		transparentSquarePaneLayout[trackSquareIndex].setHorizontalGroup(
	            		transparentSquarePaneLayout[trackSquareIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
	            		.addGroup(transparentSquarePaneLayout[trackSquareIndex].createSequentialGroup()
			                .addComponent(yellowPawn[pawnNum-4])
			                .addGap(0, 0, Short.MAX_VALUE))
		                );
	            transparentSquarePaneLayout[trackSquareIndex].setVerticalGroup(
	            		transparentSquarePaneLayout[trackSquareIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
	                    .addGroup(transparentSquarePaneLayout[trackSquareIndex].createSequentialGroup()
		                    .addComponent(yellowPawn[pawnNum-4])
		                    .addGap(0, 0, Short.MAX_VALUE))
	            		);
	            occupiedTrackSquare[trackSquareIndex] = true;
	            pawnPositions[pawnNum] = trackSquareIndex;
	            successfulMovement = true;
    		}
    		//move yellow pawn to the yellow safety zone
    		else if ( trackSquareIndex >= 62 && trackSquareIndex < 67) {
    			destinationSquarePaneIndex = trackSquareIndex - 62;
    			yellowSafetyZoneSquarePane[destinationSquarePaneIndex].remove(yellowSafetyZoneButtons[destinationSquarePaneIndex]);
    			yellowSafetyZoneSquarePaneLayout[destinationSquarePaneIndex].setHorizontalGroup(
    					yellowSafetyZoneSquarePaneLayout[destinationSquarePaneIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
    					.addGroup(yellowSafetyZoneSquarePaneLayout[destinationSquarePaneIndex].createSequentialGroup()
    							.addComponent(yellowPawn[pawnNum-4])
    							.addGap(0, 0, Short.MAX_VALUE))
    					);
    			yellowSafetyZoneSquarePaneLayout[destinationSquarePaneIndex].setVerticalGroup(
    					yellowSafetyZoneSquarePaneLayout[destinationSquarePaneIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
    					.addGroup(yellowSafetyZoneSquarePaneLayout[destinationSquarePaneIndex].createSequentialGroup()
    							.addComponent(yellowPawn[pawnNum-4])
    							.addGap(0, 0, Short.MAX_VALUE))
    					);   
                occupiedTrackSquare[trackSquareIndex] = true;
                pawnPositions[pawnNum] = trackSquareIndex;
	            successfulMovement = true;
    		}
    		//move yellow pawn to its home
    		else if (trackSquareIndex == 67) {
    			yellowHomePane.remove(yellowHomeButton);
    			yellowHomeButton.setIcon(null);
    			yellowHomeButton.setEnabled(false);
    			yellowHomePane.add(yellowPawn[pawnNum-4]);
    			yellowPawn[pawnNum-4].setEnabled(false);
    			pawnPositions[pawnNum] = -2;
	            successfulMovement = true;
    		}
    		//move yellow pawn back to start
    		else if (trackSquareIndex == -1) {
    			destinationSquarePaneIndex = pawnNum - 4;
    			yellowStartSquarePaneLayout[destinationSquarePaneIndex].setHorizontalGroup(
            			yellowStartSquarePaneLayout[destinationSquarePaneIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(yellowStartSquarePaneLayout[destinationSquarePaneIndex].createSequentialGroup()
                            .addComponent(yellowPawn[destinationSquarePaneIndex], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                    );
            	yellowStartSquarePaneLayout[destinationSquarePaneIndex].setVerticalGroup(
            			yellowStartSquarePaneLayout[destinationSquarePaneIndex].createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(yellowStartSquarePaneLayout[destinationSquarePaneIndex].createSequentialGroup()
                            .addComponent(yellowPawn[destinationSquarePaneIndex], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                    );
	            successfulMovement = true;
    		}
    		//cannot move yellow pawn to the red safety zone
    		else {
    			System.out.println("ERROR: Yellow pawn can not move to the designated square!!!");
	            successfulMovement = false;
    		}
    		// end of adding the yellow pawn to the destination square
    	}
    	//end of moving the yellow pawn
    	
    	//This is a debug tool if a wrong pawn number is passed to the method
    	else {
    		System.out.println("ERROR: The pawn number is out of range!!!!!!!");
            successfulMovement = false;
    	}
    	    		
    	repaint();
        revalidate();
        
        return successfulMovement;
    }//end of movePawn()
    
    
	/**
	 * This class implements the ActionListener interface 
	 * and defines the action will be performed in response to the events 
	 * occurred on the yellow pawns
	 * 
	 * @author Sam Brown, Caleb Cousins, Taylor Krammen, and Yucan Zhang
	 *
	 */
    class PawnButtonListener implements ActionListener {
    	/**
    	 * This method overrides the actionPerformed() method in the ActionListener interface
    	 * 
    	 * @param e the event occurred on the button of a pawn
    	 */
        public void actionPerformed(ActionEvent e) {
            if (Arrays.asList(yellowPawn).contains(e.getSource())) {
                if (e.getActionCommand().equals("4")) {
                	selectPawn(4);
                	selectedPawnNum = 4;
                }
                else if (e.getActionCommand().equals("5")) {
                	selectPawn(5);
                	selectedPawnNum = 5;
                }
                else if (e.getActionCommand().equals("6")) {
                	selectPawn(6);
                	selectedPawnNum = 6;
                }
                else if (e.getActionCommand().equals("7")) {
                	selectPawn(7);
                	selectedPawnNum = 7;
                }
                else {
                	for (int i = 0; i < 4; i++)
                		yellowPawn[i].setIcon(new ImageIcon("yellowPawn.jpg"));
                	selectedPawnNum = -1;
                }
            }
            repaint();
            revalidate();
        }// end of actionPerformed() handler
        
    }//end of PawnButtonListener class
    
    
    /**
     * This class implements the ActionListener interface and defines the action will be performed after the events 
     * occurred on the square buttons (indicating the destination squares where a yellow pawn can move to)
     * 
     * @author Sam Brown, Caleb Cousins, Taylor Krammen, and Yucan Zhang
     *
     */
    class SquareButtonListener implements ActionListener {
    	/**
    	 * This method overrides the actionPerformed() method in the ActionListener interface
    	 * 
    	 * @param e the event occurred on the square buttons
    	 */
    	public void actionPerformed(ActionEvent e) {
    		int trackButtonIndex = Integer.parseInt(e.getActionCommand());
    		boolean successfulMove = false;
    		
    		System.out.println(e.getActionCommand());
    		
    		//first check if a pawn has been selected
    		if (selectedPawnNum > 0) {
    			
    			//check the source of the event
    			if (Arrays.asList(outerTrackButtons).contains(e.getSource()) ||
    				Arrays.asList(yellowSafetyZoneButtons).contains(e.getSource()) ||
    				e.getSource() == yellowHomeButton) {
    				
    				/*check whether the square is occupied by a pawn, if yes, bump the pawn home
    				 *it assumes the pawn occupying here is a red pawn
    				 *the situation where a square is occupied by a yellow pawn should be handled by the game logic
    				 *The square will be avoided by calculating the destination squares.
    				 */
	    			if (occupiedTrackSquare[trackButtonIndex]) {
	    				for (int i = 0; i < 4; i++) {
	    					if (pawnPositions[i] == trackButtonIndex) {
	    						successfulMove = movePawn(i, -1);
	    					}
	    				}
	    			}
	    			
	    			//the square is not occupied, move the selected pawn here
	    			else
	    				successfulMove = movePawn(selectedPawnNum, trackButtonIndex);
	    			
	    			if (successfulMove) {
		    			//all the lightened squares should be cleared after the pawn is moved
		    			clearTrack();
		    			
		    			//no pawn is remained selected after a successful movement of a pawn
		    			selectedPawnNum = -1;
	    			}
    			}
    			else
    				System.out.println("unknow source of event");
    		}
    		else
    			System.out.println("Haven't chosen a pawn");
    	}// end of actionPerformed() handler
    }//end of SquareButtonListener class

    
    /**
     * This method is called by the ActionListener of the startNewGameBtn,
     * defines the action will be performed after the events occurred on the startNewGameBtn.
     * 
     * @param evt event occurred on the startNewGameBtn
     */
    private void startNewGameBtnActionPerformed(ActionEvent evt) {
    	if(evt.getSource() == startNewGameBtn) {
    		int option = JOptionPane.showConfirmDialog(null, 
    				"Would you like to see the statistics before starting a new game?", 
    				"Show statistics", JOptionPane.YES_NO_CANCEL_OPTION);
    		if (option == JOptionPane.YES_OPTION) {
    			
    		}
    		else if (option == JOptionPane.NO_OPTION) {
    			this.dispose();
    			new SRGUI();
    		}
    	}
    }//end of startNewGameBtnActionPerformed() handler
    
    /**
     * This method is called by the ActionListener of the quitBtn,
     * defines the action will be performed after the events occurred on the quitBtn.
     * 
     * @param evt event occurred on the quitBtn
     */
	private void quitBtnActionPerformed(ActionEvent evt) {
		if(evt.getSource() == quitBtn) {
			int option = JOptionPane.showConfirmDialog(null,
			        "Would you like to see the statistics before leaving the game?", 
			        "Show statistics", JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				
			}
			else if (option == JOptionPane.NO_OPTION) {
				System.exit(0);
			}
		}
	}//end of quitBtnActionPerformed() handler
	
	
	/**
     * This method is called by the ActionListener of the drawCardBtn,
     * defines the action will be performed after the events occurred on the drawCardBtn.
     * 
     * @param evt event occurred on the drawCardBtn
     */
	private void drawCardBtnActionPerformed(ActionEvent evt) {
		if (evt.getSource() == drawCardBtn) {
			displayCard(currentCardImage);
		}
	}// end of drawCardBtnActionPerformed() handler
	
	/**
	 * This method gets the current displayed drawn card image
	 * 
	 * @return the currentCardImage
	 */
	public String getCurrentCardImage() {
		return currentCardImage;
	}//end of getCurrentCardImage()

	
	/**
	 * This method sets the current drawn card image which will be displayed on the screen
	 * 
	 * @param currentCardImage the currentCardImage to set
	 */
	public void setCurrentCardImage(String currentCardImage) {
		this.currentCardImage = currentCardImage;
	}//end of setCurrentCardImage()
	
	
	/**
	 * This method displays the drawn card image on the gameboard
	 * 
	 * @param cardImage the image of the card which will be displayed
	 */
	private void displayCard(String cardImage) {
		displayCardLabel.setIcon(new ImageIcon(cardImage));
		repaint();
		revalidate();
	}// end of displayCard()
	
    
	/**
	 * This method selects the designated pawn and un-check all the other pawns,
	 * to make sure only one pawn is selected at a time
	 * 
	 * @param pawnNum the selected pawn index
	 */
    public void selectPawn(int pawnNum) {
    	//a red pawn is selected by the computer, but should not by the user
        if (pawnNum >=0 && pawnNum < 4) {
        	redPawn[pawnNum].setIcon(new ImageIcon("selectedPawn.jpg"));
        	for (int i = 0; i < 4; i++) {
        		if (i != pawnNum)
        			redPawn[i].setIcon(new ImageIcon("redPawn.jpg"));
        	}
        }
        //a yellow pawn is selected by the user
        else if (pawnNum >= 4 && pawnNum < 8) {
        	yellowPawn[pawnNum-4].setIcon(new ImageIcon("selectedPawn.jpg"));
        	for (int i = 0; i < 4; i++) {
        		if (i != (pawnNum-4))
        			yellowPawn[i].setIcon(new ImageIcon("yellowPawn.jpg"));
        	}
        }
        repaint();
        revalidate();
    }//end of selectPawn()
    
    
    /**
     * This method sets all yellow pawn buttons enabled or disabled.
     * 
     * @param b sets enabled if true
     */
    public void setYellowPawnEnabled (boolean b) {
    	if (b) {
    		for (int i = 0; i < 4; i++) {
    			if (pawnPositions[i] != -2) {
    				yellowPawn[i].setEnabled(true);
    				yellowPawn[i].setToolTipText("Click the pawn to select.");
    			}
    		}
    	}
    	else {
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
    	for(int i = 0; i < 56; i++) {
    		outerTrackButtons[i].setIcon(null);
    		outerTrackButtons[i].setEnabled(false);
    		outerTrackButtons[i].setOpaque(false);
    	}
    	for(int i = 0; i < 5; i++) {
    		redSafetyZoneSquarePane[i].removeAll();
    		yellowSafetyZoneButtons[i].setIcon(null);
    		yellowSafetyZoneButtons[i].setEnabled(false);
    		yellowSafetyZoneButtons[i].setOpaque(false);
    	}
    	yellowHomePane.remove(yellowHomeButton);
    	repaint();
    	revalidate();
    }
    
    /**
     * This is the tester method of SRGUI class.
     * 
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    	EventQueue.invokeLater(new Runnable() {
            public void run() {
            	//creates a new window
                SRGUI game = new SRGUI();
                
                //print out the size of the frame (check whether it fits the screen)
                System.out.println(game.getSize());
                
                //move a yellow pawn to home
                game.movePawn(4,67);
                
                //light up a bunch of squares.
                int[] squares = {45, 30, 1, 5, 66, 67, 60,61, 62, 63, 64, 65, 77};
                game.squareLightUp(squares);
                
                //try to move a yellow pawn to red safety zone
                game.movePawn(5, 63);
                
                //tests the setYellowPawnEnabled method
                //game.setYellowPawnEnabled(false);
                
                //try to move a red pawn to different places
                //game.movePawn(1, 67);
                //game.movePawn(3, 45);
                //game.movePawn(4, 67);
                //game.movePawn(7, 58);
                //game.movePawn(2, 61);
                //game.movePawn(2, 61);
                //game.movePawn(3, 61);
                //game.movePawn(1, 61);
                
                //move a red pawn to a square and test the bumpedSquareLightUP() method
                game.movePawn(1, 45);
                game.bumpedSquareLightUp(45);
                
                //setCurrentCardImage and test the event handling of the drawCardBtn
                game.setCurrentCardImage("card2.jpg");
                
                //all the other methods can be tested directly from the GUI frame
            }
        });
    }//end of the main()
    
    
}//end of SRGUI class
