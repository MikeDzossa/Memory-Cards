import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

public class Exit {
	private Image[] yes = new Image[2];
	private Image[] no = new Image[2];
	private boolean close;
	private int mov;
	
	public Exit() throws SlickException {
		yes[0]= new Image("images\\yes0.png");
		yes[1]= new Image("images\\yes1.png");
		no[0]= new Image("images\\no1.png");
		no[1]= new Image("images\\no0.png");
		close=false;
		mov=Tableau.getMove();
	}
	
	public void dessinerExit(Graphics g, GameContainer gc) {
		//Dessiner la fenetre de dialogue pour confirmer le choix
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0+mov, 0, 660, 750);
		g.setColor(new Color(255, 249, 215));
		g.fillRoundRect(80+mov, 250, 500, 300, 50);
		
		//Ecriture de texte sur la fenetre
		java.awt.Font font = new Font("Verdana", Font.BOLD, 40);
		TrueTypeFont ttf = new TrueTypeFont(font, true);
		ttf.drawString(102.0f+mov, 320.0f, "Continuer et quitter", Color.darkGray);
		ttf.drawString(330.0f+mov, 380.0f, "?", Color.darkGray);
		
		Input inp= gc.getInput();
		int x=inp.getMouseX();
		int y=inp.getMouseY();
		//
		if(x<=95+118+mov && x>=95+mov && y>=460 && y<=460+60)
			g.drawImage(yes[1], 90+mov, 452);
		else
			g.drawImage(yes[0], 95+mov, 460);
		
		if(x<=575+mov && x>=575-118+mov && y>=460 && y<=460+60)
			g.drawImage(no[1], 575-124+mov, 452);
		else
			g.drawImage(no[0], 575-118+mov, 460);
	}
	
	public void clicExit(int x, int y) {
		//Recupere le clic pour valider la sortie ou l'annuler et revenir au menu
		if(x<=95+118+mov && x>=95+mov && y>=460 && y<=460+60) {
			close=true;
			if(Sounds.volume) Sounds.click.play();
		}
		else if(x<=575+mov && x>=575-118+mov && y>=460 && y<=460+60) {
			Menu.selection=0;	
			if(Sounds.volume) Sounds.click.play();
		}
	}
	
	//retourne la valeur de l'atribut close
	public boolean close() {
		return close;
	}
}
