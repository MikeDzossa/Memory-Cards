import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

public class Scores {
	//Coordonnées des buttons reinitialisé et retour
	private static final int[] BACK_COOR={480+Tableau.getMove(),670,168,80};
	private static final int[] RESET_COOR={20+Tableau.getMove(),670,168,80};
	
	private Image score, logo, back, background, back_hover, reset, reset_hover;
	private String message;
	private int mov;
	
	
	public Scores() throws SlickException {
		logo= new Image("images\\logo.png");
		score= new Image("images\\score.png");
		message= " tentative(s)";
		back= new Image("images\\back.png");
		background= new Image("images\\menu_bg.png");
		back_hover= new Image("images\\backh.png");
		reset=new Image("images\\reset.png");
		reset_hover=new Image("images\\reseth.png");
		mov=Tableau.getMove();
	}
	
	
	//Affiche l'ariere plan de la page meilleur score
	public void dessinerScores(Graphics g, GameContainer gc) throws IOException {
		g.setColor(new Color(128,255,255,80));
		g.drawImage(background,0+mov, 0);
		g.drawImage(logo, 40+mov, 10);
		g.drawImage(score, 80+mov, 160);
		Input inp= gc.getInput();
		int x=inp.getMouseX();
		int y=inp.getMouseY();
		if(x<=BACK_COOR[0]+BACK_COOR[2] && x>=BACK_COOR[0] && y>=BACK_COOR[1] && y<=BACK_COOR[1]+BACK_COOR[3])
			g.drawImage(back_hover, BACK_COOR[0], BACK_COOR[1]);
		else
			g.drawImage(back, BACK_COOR[0], BACK_COOR[1]);
		if(x<=RESET_COOR[0]+RESET_COOR[2] && x>=RESET_COOR[0] && y>=RESET_COOR[1] && y<=RESET_COOR[1]+RESET_COOR[3])
			g.drawImage(reset_hover, RESET_COOR[0], RESET_COOR[1]);
		else
			g.drawImage(reset, RESET_COOR[0], RESET_COOR[1]);
		afficherScore(g);
		
	}
	
	
	//Methode d'affichage du meilleur score
	public void afficherScore(Graphics g) throws IOException {
		java.awt.Font font = new Font("Verdana", Font.BOLD, 40);
		TrueTypeFont ttf = new TrueTypeFont(font, true);
		ttf.drawString((Integer.valueOf(LireScore())==0)?110.0f+mov:190.0f+mov, 380.0f,(Integer.valueOf(LireScore())==0)?"Aucune"+message:LireScore()+message, Color.white);

	}
	
	//Recupere le clic pour reinitialiser le score ou retourner au menu
	public void clicCase(int x, int y) {
		if(x<=BACK_COOR[0]+BACK_COOR[2] && x>=BACK_COOR[0] && y>=BACK_COOR[1] && y<=BACK_COOR[1]+BACK_COOR[3]) {
			Menu.selection=0;
			if(Sounds.volume) Sounds.click.play();
		}
		
		else if(x<=RESET_COOR[0]+RESET_COOR[2] && x>=RESET_COOR[0] && y>=RESET_COOR[1] && y<=RESET_COOR[1]+RESET_COOR[3]) {
			try {
				resetScore();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(Sounds.volume) Sounds.click.play();
		}
	}
	
	
	//Enregistre le meilleur score dans le fichier à la fin de la partie
	public static void enregistrerScore() throws IOException {
		if(Tableau.tentatives<Integer.parseInt(LireScore()) || Integer.parseInt(LireScore())==0) {
			 FileWriter fw = new FileWriter("scores.bat");
			 fw.flush();
		     fw.write(String.valueOf(Tableau.tentatives));
		     fw.close();
		} 
	}
	
	//Lis le meilleur score du fichier scores.bat
	public static String LireScore() throws IOException {
		  FileReader file = new FileReader("scores.bat");
	      BufferedReader buffer = new BufferedReader(file);
	      String highscore = buffer.readLine();
	      buffer.close();
	      return highscore;
	     
	     
	}
	
	//Reinitialise le meilleur score
	public static void resetScore() throws IOException {
		 FileWriter fw = new FileWriter("scores.bat");
		 fw.flush();
	     fw.write("0");
	     fw.close();
	} 
	
	
	
}
