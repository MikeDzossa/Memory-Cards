import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

public class Help {
	//
	private static final int[] BACK_COOR={480+Tableau.getMove(),670,168,80};
	
	private Image help, logo, back, background, back_hover;
	private String message[]= new String[4]; 
	private int mov;
	
	public Help() throws SlickException {
		//Initialiser les images d'arriere-plan et le message à afficher
		logo= new Image("images\\logo.png");
		help= new Image("images\\help.png");
		message[0]="L'objectif est de";
		message[1]= "retrouver tous les couples";
		message[2]="de cartes avec le minimum";
		message[3]="de tentatives";
		back= new Image("images\\back.png");
		background= new Image("images\\menu_bg.png");
		back_hover= new Image("images\\backh.png");
		mov=Tableau.getMove();
	}
	
	public void dessinerHelp(Graphics g, GameContainer gc) {
		//dessine l'arriereplan et affiche le message d'aide
		g.setColor(new Color(128,255,255,80));
		g.drawImage(background,0+mov, 0);
		g.drawImage(logo, 40+mov, 10);
		g.drawImage(help, 200+mov, 160);
		Input inp= gc.getInput();
		int x=inp.getMouseX();
		int y=inp.getMouseY();
		if(x<=BACK_COOR[0]+BACK_COOR[2] && x>=BACK_COOR[0] && y>=BACK_COOR[1] && y<=BACK_COOR[1]+BACK_COOR[3])
			g.drawImage(back_hover, BACK_COOR[0], BACK_COOR[1]);
		else
			g.drawImage(back, BACK_COOR[0], BACK_COOR[1]);
		afficherAide(g);
	}
	
	public void afficherAide(Graphics g) {
		//Affiche le texte du message d'aide
		java.awt.Font font = new Font("Verdana", Font.BOLD, 38);
		TrueTypeFont ttf = new TrueTypeFont(font, true);
		ttf.drawString(150.0f+mov, 280.0f, message[0], Color.white);
		ttf.drawString(55.0f+mov, 380.0f, message[1], Color.white);
		ttf.drawString(50.0f+mov, 480.0f, message[2], Color.white);
		ttf.drawString(200.0f+mov, 580.0f, message[3], Color.white);
	}
	
	public void clicCase(int x, int y) {
		//recupere le clic de la souris pour sortir de l'aide
		if(x<=BACK_COOR[0]+BACK_COOR[2] && x>=BACK_COOR[0] && y>=BACK_COOR[1] && y<=BACK_COOR[1]+BACK_COOR[3]) {
			Menu.selection=0;	
			if(Sounds.volume) Sounds.click.play();
		}
	}
	
}
