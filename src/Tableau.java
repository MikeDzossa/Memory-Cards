import java.awt.Font;
import java.io.IOException;

import org.newdawn.slick.Color;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;


public class Tableau {
	private static int mov=0;
	private int win;
	private int[] types= {1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8};
	private int[] caseSelectionnee= new int[2];
	private int[] deuxiemeCase= new int[2];
	private boolean selection1, selection2, pause, reset, toMenu;
	private Case cases[][]=new Case[4][4];
	private Carte cartes[][]= new Carte[4][4];
	private Image pauseButton;
	private Image[] back_btn = new Image[2];
	private Image[] play_btn = new Image[2];
	private Image[] restart_btn = new Image[2];
	
	private static int[] pauseButtonXtics= {
			595, 30, 50, 50}; ;// x, y, w, l
	public static int tentatives;
	public static boolean init=false;
	
	public Tableau() throws SlickException {
		for(int i=0; i<5; i++)
			shuffle();
		
		for(int l=0, k=0, y=95; l<cases.length; l++, y+=160) 
			for(int c=0, x=11+mov; c<cases.length; c++, x+=160, k++) {
				cases[l][c]=new Case(x,y);
				cartes[l][c]=new Carte(x+7,y+7,types[k]);
			}
		
		caseSelectionnee[0]=caseSelectionnee[1]=-1;
		deuxiemeCase[0]=deuxiemeCase[1]=-1;
		tentatives=win=0;
		
		pauseButton=new Image("images\\pause.png");	
		play_btn[0]= new Image("images\\play_btn.png");
		play_btn[1]= new Image("images\\play_btn2.png");
		back_btn[0]= new Image("images\\back_btn.png");
		back_btn[1]= new Image("images\\back_btn2.png");
		restart_btn[0]= new Image("images\\restart_btn.png");
		restart_btn[1]= new Image("images\\restart_btn2.png");
		
		selection1=selection2=pause=reset=toMenu=false;
		init=true; 
		
	}
	
	public static void setMove(int Mov) {
		Tableau.mov=Mov;
	}
	
	public static int getMove() {
		return Tableau.mov;
	}
	
	private void shuffle() {
		int max=16;
		for(int i=1; i<types.length; i++) {
			int elmt = (int)(Math.random()*max), x;
			x=types[elmt];
			types[elmt]=types[max-1];
			types[max-1]=x;
			max--;
		}
	}
	
	public boolean premiereSelection() {
		return selection1;
	}
	
	public boolean deuxiemeSelection() {
		return selection2;
	}
	
	public boolean getPauseStatus() {
		return pause;
	}
	
	public boolean reset() {
		return reset;
	}
	
	public boolean toMenu() {
		return toMenu;
	}
	
	public void dessinerTableau(Graphics g, GameContainer gc) {
		g.setColor(new Color(0, 128, 153));
		g.fillRect(0+mov, 0, MainMemory.dimension[0], 90);
		g.setColor(new Color(0,119,179));
		g.fillRect(0+mov, 90, MainMemory.dimension[0], MainMemory.dimension[1]-90);
		for(int l=0; l<this.cartes.length; l++) 
			for(int c=0; c<this.cartes.length; c++)
				if(this.cartes[l][c]!=null)
					this.cartes[l][c].dessinerCarte(g);
				else 
					this.cases[l][c].dessinerEmplacement(g);
		g.drawImage(pauseButton, pauseButtonXtics[0], pauseButtonXtics[1]);
	}
	
	//Retourne le couple [i][j] de la case correspondant à la position de la souris
	public int[] quelleCase(int x, int y) { 
		// Initialise la case à [-1][-1]
		int[] ici= {-1,-1}; 
		for(int l=0; l<cases.length; l++) {
			for(int c=0; c<cases.length; c++) {
				if(x<=this.cases[l][c].getX1()+this.cases[l][c].getL() && x>=this.cases[l][c].getX1()) ici[1]=c;
				if(y<=this.cases[l][c].getY1()+this.cases[l][c].getL() && y>=this.cases[l][c].getY1()) ici[0]=l;
			}
		}
		return ici;
	}
	
	public void dessinerHover(Graphics g, GameContainer gc) {
		Input inp= gc.getInput();
		int x_mouse=inp.getMouseX();
		int y_mouse=inp.getMouseY();
		int l=quelleCase(x_mouse, y_mouse)[0];
		int c=quelleCase(x_mouse, y_mouse)[1];
		if(c<cases.length && c>=0 && l<cases.length && l>=0 && cartes[l][c]!=null) {
			cases[l][c].dessinerCase(g);
		}
		if(x_mouse>=pauseButtonXtics[0] && x_mouse<=pauseButtonXtics[0]+pauseButtonXtics[2] && y_mouse>=pauseButtonXtics[1] && y_mouse<=pauseButtonXtics[1]+pauseButtonXtics[3]) {
			g.setColor(new Color(128,255,255,80)); 
			g.fillRect(pauseButtonXtics[0], pauseButtonXtics[1], pauseButtonXtics[2], pauseButtonXtics[3]);
		}
	}
	
	public void clicCase(int x, int y) {
		int l=quelleCase(x, y)[0], c=quelleCase(x, y)[1];
		//Si le curseur de la souris est sur une case valide du tableau
		if(l!=-1&&c!=-1)
			//Si la case cliqué contient une carte
			if(cartes[l][c]!=null && caseSelectionnee[0]==-1 &&  caseSelectionnee[1]==-1)
			{
				caseSelectionnee=quelleCase(x, y); 
				if(Sounds.volume) Sounds.click.play();
				cartes[caseSelectionnee[0]][caseSelectionnee[1]].toggleSelection();
				cartes[caseSelectionnee[0]][caseSelectionnee[1]].toggleHide(); //new
				selection1=true;
			}	
	}
	
	public void clicDeuxieme(int x, int y) {
		int l=quelleCase(x, y)[0], c=quelleCase(x, y)[1];
			if(l==caseSelectionnee[0] && c==caseSelectionnee[1]) //Si la case cliqué est la case précédemment selectionné
			{	
//				cartes[caseSelectionnee[0]][caseSelectionnee[1]].toggleSelection();
//				caseSelectionnee[0]=caseSelectionnee[1]=-1; 
//				selection1=false;
			}
			else if(caseSelectionnee[0]!=-1 && caseSelectionnee[1]!=-1 && deuxiemeCase[0]==-1 && deuxiemeCase[1]==-1)
				if(l!=-1&&c!=-1 && cartes[l][c]!=null) {
					deuxiemeCase=quelleCase(x, y);
					cartes[deuxiemeCase[0]][deuxiemeCase[1]].toggleSelection();	
					cartes[deuxiemeCase[0]][deuxiemeCase[1]].toggleHide();
			//		cartes[caseSelectionnee[0]][caseSelectionnee[1]].toggleHide();
					selection2=true;
					if(Sounds.volume) Sounds.click.play();
			}
	}
	
	public boolean memeType() {
		return (cartes[deuxiemeCase[0]][deuxiemeCase[1]].getType()==cartes[caseSelectionnee[0]][caseSelectionnee[1]].getType());
	}
	
	public void comparaison() throws InterruptedException {
		if(memeType()) {
			cartes[deuxiemeCase[0]][deuxiemeCase[1]]=null;
			cartes[caseSelectionnee[0]][caseSelectionnee[1]]=null;
			if(Sounds.volume) Sounds.found.play();
		}
		else {
			cartes[deuxiemeCase[0]][deuxiemeCase[1]].toggleHide();
			cartes[deuxiemeCase[0]][deuxiemeCase[1]].toggleSelection();
			cartes[caseSelectionnee[0]][caseSelectionnee[1]].toggleHide();
			cartes[caseSelectionnee[0]][caseSelectionnee[1]].toggleSelection();
			if(Sounds.volume) Sounds.mismarch.play();
		}
		caseSelectionnee[0]=caseSelectionnee[1]=-1;
		deuxiemeCase[0]=deuxiemeCase[1]=-1;
		selection1=selection2=false;
		tentatives++;
		
	}
	
	public String tentatives() {
		return "Tentatives : "+tentatives;
	}
	
	public void afficherTentatives(Graphics g) {
		java.awt.Font font = new Font("Verdana", Font.BOLD, 32);
		TrueTypeFont ttf = new TrueTypeFont(font, true);
		ttf.drawString(32.0f+mov, 40.0f, tentatives(), Color.green);
	}
	
	public boolean victoire() {
		boolean isNull=true;
		for(int l=0; l<cartes.length; l++)
			for(int c=0; c<cartes.length; c++)
				if(cartes[l][c]!=null) {
					isNull=false;
					break;
				}	
		return isNull;
	}
	
	public void afficherVictoire(Graphics g) throws IOException {
		g.setColor(Color.gray);
		g.fillRoundRect(20+mov, 250, 660-40, 250, 50);
		g.setColor(Color.white);
		g.drawRoundRect(20+mov, 250, 660-40, 250, 50);
		g.drawRoundRect(21+mov, 251, 660-41, 250-1, 50);
		g.drawRoundRect(22+mov, 252, 660-42, 250-2, 50);
		java.awt.Font font = new Font("Verdana", Font.BOLD, 40);
		TrueTypeFont ttf = new TrueTypeFont(font, true);
		ttf.drawString(100.0f+mov, 320.0f, "Vous avez gagne en", Color.cyan);
		ttf.drawString(200.0f+mov, 380.0f, tentatives+ " tentatives", Color.cyan);
		if (win==0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(Sounds.volume) Sounds.end.play();
			Scores.enregistrerScore();
			win++;
		}
	}
	
	public void clicPause(int x_mouse, int y_mouse) {
		if(x_mouse>=pauseButtonXtics[0] && x_mouse<=pauseButtonXtics[0]+pauseButtonXtics[2] && y_mouse>=pauseButtonXtics[1] && y_mouse<=pauseButtonXtics[1]+pauseButtonXtics[3]) {
			pause=true;
			if(Sounds.volume) Sounds.click.play();
		}
	}
	
	public void dessinerPause(Graphics g, GameContainer gc) {
		int hover=0;
		java.awt.Font font = new Font("Verdana", Font.BOLD, 30);
		TrueTypeFont ttf = new TrueTypeFont(font, true);
		Input inp= gc.getInput();
		int x=inp.getMouseX();
		int y=inp.getMouseY();
		
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0+mov, 0, 660, 750);
		g.setColor(new Color(0, 64, 128, 200));
		g.fillRoundRect(80+mov, 250, 500, 300, 50);
		if(x<=140+80+mov && x>=140+mov && y>=320 && y<=320+80) {
			g.drawImage(play_btn[1], 140+mov, 320); hover=1;
		}
		else g.drawImage(play_btn[0], 140+mov, 320);
		
		if(x<=140+160+80+mov && x>=140+160+mov && y>=320 && y<=320+80) {
			g.drawImage(restart_btn[1], 140+160+mov, 320); hover=2;
		}
		else g.drawImage(restart_btn[0], 140+160+mov, 320);
		
		if(x<=140+80+320+mov && x>=140+320+mov && y>=320 && y<=320+80) {
			g.drawImage(back_btn[1], 140+320+mov, 320); hover=3;
		}
		else g.drawImage(back_btn[0], 140+320+mov, 320);
		
		ttf.drawString(105.0f+mov, 420.0f, "Continue", hover==1?new Color(230,115,0):new Color(219,218,230));
		ttf.drawString(280.0f+mov, 420.0f, "Restart", hover==2?new Color(230,115,0):new Color(219,218,230));
		ttf.drawString(460.0f+mov, 420.0f, "Menu", hover==3?new Color(230,115,0):new Color(219,218,230));
	}
	
	public void clicPauseMenu(int x, int y, GameContainer gc) throws SlickException {
		if(x<=140+80+mov&& x>=140+mov && y>=320 && y<=320+80) {
			pause=false;
			if(Sounds.volume) Sounds.click.play();
		}
		
		else if(x<=140+160+80+mov && x>=140+160+mov && y>=320 && y<=320+80) {
			reset=true;
			if(Sounds.volume) Sounds.click.play();
		}
		else if(x<=140+80+320+mov && x>=140+320+mov && y>=320 && y<=320+80) {
			toMenu=true; reset=true; Menu.selection=0;
			
			if(Sounds.volume) Sounds.click.play();
		}
	}
	
	public static void offsetButton() {
		pauseButtonXtics[0]+=mov;
	}
	
}
