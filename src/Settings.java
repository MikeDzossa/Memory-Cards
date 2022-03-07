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

public class Settings {
	private final static int[] BUTTON= {105+Tableau.getMove(), 155, 80, 80};  
	
	private Image back, back_hover;
	private static final int[] BACK_COOR={260+Tableau.getMove(),670,168,80};
	private static int numTheme=1;
	private static int numVerso=0;
	private Case theme, verso;
	private Image[] left = new Image[2];
	private Image[] right = new Image[2];
	private int mov=Tableau.getMove();

	
	public Settings() throws SlickException {
		try {
			LireSetting();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image img1 =new Image("images\\setting\\pack"+numTheme+".jpg");
		Image img2 =new Image("images\\setting\\vers"+numVerso+".png");
		back= new Image("images\\back.png");
		back_hover= new Image("images\\backh.png");
		theme=new Case(205+mov, 70, 250, 250);
		verso=new Case(205+mov, 400, 250, 250);
		theme.setImg(img1);
		verso.setImg(img2);
		left[0]= new Image("images\\setting\\left0.png");
		left[1]= new Image("images\\setting\\left1.png");
		right[0]= new Image("images\\setting\\right0.png");
		right[1]= new Image("images\\setting\\right1.png");
		
	}
	
	
	public void dessinerSetting(Graphics g, GameContainer gc) {
		//Dessine la ppage options à l'écran
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0+mov, 0, 660, 750);
		
		g.setColor(new Color(255, 249, 215));
		g.fillRoundRect(80+mov, 10, 500, 650, 50);
		
		java.awt.Font font = new Font("Verdana", Font.BOLD, 40);
		TrueTypeFont ttf = new TrueTypeFont(font, true);
		
		ttf.drawString(260.0f+mov, 20.0f, "Theme", Color.darkGray);
		ttf.drawString(270.0f+mov, 350.0f, "Verso", Color.darkGray);
		
		dessinerCase(g, verso);
		dessinerCase(g, theme);
		
		dessinerButtons(g, gc);

	}
	
	public void clicCase(int x, int y) throws SlickException {
		//recupere le clic pour changer le theme des cartes ou le verso et sauvegarde la nouvelle configuration dans un fichier
		if(x<=BACK_COOR[0]+BACK_COOR[2] && x>=BACK_COOR[0] && y>=BACK_COOR[1] && y<=BACK_COOR[1]+BACK_COOR[3]) {
			Menu.selection=0;
			try {
				enregistrerSetting();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(Sounds.volume) Sounds.click.play();
		}
		
		else if(x<=BUTTON[0]+BUTTON[2]+290+80 && x>=BUTTON[0]+290+80 && y>=BUTTON[1]+355 && y<=BUTTON[1]+BUTTON[3]+335) {
			numVerso=(numVerso==2)?0:numVerso+1;
			Carte.setBackpack(numVerso);
			if(Sounds.volume) Sounds.click.play();
			Image img1 =new Image("images\\setting\\vers"+numVerso+".png");
			verso.setImg(img1);
		}
		
		else if(x<=BUTTON[0]+BUTTON[2] && x>=BUTTON[0] && y>=BUTTON[1]+335 && y<=BUTTON[1]+BUTTON[3]+335) {
			numVerso=(numVerso==0)?2:numVerso-1;
			Carte.setBackpack(numVerso);
			if(Sounds.volume) Sounds.click.play();
			Image img1 =new Image("images\\setting\\vers"+numVerso+".png");
			verso.setImg(img1);
		}
		
		else if(x<=BUTTON[0]+BUTTON[2]+290+80 && x>=BUTTON[0]+290+80 && y>=BUTTON[1] && y<=BUTTON[1]+BUTTON[3]) {
			numTheme=(numTheme==3)?1:numTheme+1;
			Carte.setPack(numTheme);
			if(Sounds.volume) Sounds.click.play();
			Image img1 =new Image("images\\setting\\pack"+numTheme+".jpg");
			theme.setImg(img1);
		}
		
		else if(x<=BUTTON[0]+BUTTON[2] && x>=BUTTON[0] && y>=BUTTON[1] && y<=BUTTON[1]+BUTTON[3]) {
			numTheme=(numTheme==1)?3:numTheme-1;
			Carte.setPack(numTheme);
			if(Sounds.volume) Sounds.click.play();
			Image img1 =new Image("images\\setting\\pack"+numTheme+".jpg");
			theme.setImg(img1);
		}
		
	}
	
	public void dessinerCase(Graphics g, Case c) {
		//Dessine la carte et son cadre à son emplacement x,y sur l'écran
		g.drawImage(c.getImg(), c.getX1(), c.getY1());
		g.setColor(Color.darkGray);
		for(int i=-2; i<=3; i++)
			g.drawRoundRect(c.getX1()+i, c.getY1()+i, c.getW()-2*i, c.getL()-2*i, 5);
	}
	
	public void dessinerButtons(Graphics g, GameContainer gc) {
		Input inp= gc.getInput();
		int x=inp.getMouseX();
		int y=inp.getMouseY();
		
		if(x<=BACK_COOR[0]+BACK_COOR[2] && x>=BACK_COOR[0] && y>=BACK_COOR[1] && y<=BACK_COOR[1]+BACK_COOR[3])
			g.drawImage(back_hover, BACK_COOR[0], BACK_COOR[1]);
		else
			g.drawImage(back, BACK_COOR[0], BACK_COOR[1]);
		
		if(x<=BUTTON[0]+BUTTON[2] && x>=BUTTON[0] && y>=BUTTON[1] && y<=BUTTON[1]+BUTTON[3])
			g.drawImage(left[1], BUTTON[0], BUTTON[1]);
		else
			g.drawImage(left[0], BUTTON[0], BUTTON[1]);
		
		if(x<=BUTTON[0]+BUTTON[2]+290+80 && x>=BUTTON[0]+290+80 && y>=BUTTON[1] && y<=BUTTON[1]+BUTTON[3])
			g.drawImage(right[1], BUTTON[0]+290+80, BUTTON[1]);
		else
			g.drawImage(right[0], BUTTON[0]+290+80, BUTTON[1]);
		
		if(x<=BUTTON[0]+BUTTON[2] && x>=BUTTON[0] && y>=BUTTON[1]+335 && y<=BUTTON[1]+BUTTON[3]+335)
			g.drawImage(left[1], BUTTON[0], BUTTON[1]+335);
		else
			g.drawImage(left[0], BUTTON[0], BUTTON[1]+335);
		
		if(x<=BUTTON[0]+BUTTON[2]+290+80 && x>=BUTTON[0]+290+80 && y>=BUTTON[1]+355 && y<=BUTTON[1]+BUTTON[3]+335)
			g.drawImage(right[1], BUTTON[0]+290+80, BUTTON[1]+335);
		else
			g.drawImage(right[0], BUTTON[0]+290+80, BUTTON[1]+335);
	}
	
	public static void enregistrerSetting() throws IOException {
		//Enregistre les parametrages dans le fichier setting
			 FileWriter fw = new FileWriter("settings.ini");
			 fw.flush();
			 String set=numVerso+""+numTheme;
		     fw.write(set);
		     fw.close();
	    
	}
	
	public static void LireSetting() throws IOException {
		//Recupere les parametres dans le fichier setting
		  FileReader file = new FileReader("settings.ini");
	      BufferedReader buffer = new BufferedReader(file);
	      String set = buffer.readLine();
	      buffer.close();
	      int temp=Integer.valueOf(set);
	      if(temp/10>=0 && temp/10<=2 && temp%10<=3 && temp%10>=1) {
		      numVerso=temp/10;
		      numTheme=temp%10;
		      Carte.setPack(numTheme);
		      Carte.setBackpack(numVerso);
	      }
	}
}
