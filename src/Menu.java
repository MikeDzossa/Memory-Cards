import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Menu {
	public static int selection;
	private static int[] volXtics = {230+Tableau.getMove(), 650, 80, 80};
	private static int[] settingXtics = {380+Tableau.getMove(), 660, 80, 80};

	private SpriteSheet movingBrain;
	private Animation animateBrain, load;
	
	
	private int mov=Tableau.getMove();
	public boolean focus;
	private Case cases[]=new Case[4];
	private Image background, logo1, logo2, setting;
	private Image[] newGame = new Image[2];
	private Image[] scores = new Image[2];
	private Image[] help = new Image[2];
	private Image[] exit = new Image[2];
	private Image[] vol = new Image[2];
	private int timer, timePassed;
	
	public Menu() throws SlickException {
		for(int l=0, y=240; l<cases.length; l++, y+=100)  
			cases[l]=new Case(91+mov,y,80,480);
		
		newGame[0]= new Image("images\\new_game_off.png");
		newGame[1]= new Image("images\\new_game_on.png");	
		scores[0]= new Image("images\\score_off.png");	
		scores[1]= new Image("images\\score_on.png");
		help[0]= new Image("images\\help_off.png");
		help[1]= new Image("images\\help_on.png");
		exit[0]= new Image("images\\exit_off.png");
		exit[1]= new Image("images\\exit_on.png");
		background= new Image("images\\menu_bg.png");
		logo1= new Image("images\\logo.png");
		logo2= new Image("images\\menu.png");
		vol[1]= new Image("images\\vol-.png");
		vol[0]= new Image("images\\vol+.png");
		setting= new Image("images\\setting.png");
		selection=0;
		
		focus=false;
		
		movingBrain= new SpriteSheet("images\\menu_bg_animate.png", 660, 750);
		animateBrain= new Animation(movingBrain, 200);
		
		Image[] imgLoad= new Image[70];
		for(int i=1; i<=imgLoad.length; i++) {
			imgLoad[i-1]=new Image("load/frame ("+i+").jpg");
			System.out.println(i+1);
		}
		
		load=new Animation(imgLoad, 100);
		timer=3000;
		timePassed=0; 
	}
	
	private boolean volume(int x, int y) {
		return (x>=volXtics[0] && x<=volXtics[0]+volXtics[2] && y>=volXtics[1] && y<=volXtics[1]+volXtics[3]);
	}
	
	private boolean setting(int x, int y) {
		return (x>=settingXtics[0]-10 && x<=settingXtics[0]-10+settingXtics[2] && y>=settingXtics[1]-10 && y<=settingXtics[1]-10+settingXtics[3]);
	}
	
	public void dessinerMenu(Graphics g, GameContainer gc) {
			g.drawImage(background,0+mov, 0);
			animateBrain.draw(0+mov, 0);
			g.setColor(new Color(255, 243, 227, 100));
			g.fillRect(0+mov, 0, 660, 750);
			g.drawImage(logo1, 40+mov, 10);
			g.drawImage(logo2, 200+mov, 120);
			g.drawImage(newGame[0],cases[0].getX1(), cases[0].getY1());
			g.drawImage(scores[0],cases[1].getX1(), cases[1].getY1());
			g.drawImage(help[0],cases[2].getX1(), cases[2].getY1());
			g.drawImage(exit[0],cases[3].getX1(), cases[3].getY1());
			g.setColor(new Color(255, 243, 227));
			g.fillRoundRect(volXtics[0], volXtics[1], volXtics[2], volXtics[3], 50);
			g.drawImage(vol[Sounds.volume?0:1], volXtics[0], volXtics[1]);
			g.fillRoundRect(settingXtics[0]-10, settingXtics[1]-10, settingXtics[2], settingXtics[3], 50);
			g.drawImage(setting, settingXtics[0], settingXtics[1]);
	}
	
	//Retourne le couple [i][j] de la case correspondant à la position de la souris
	public int quelleCase(int x, int y) { 
		 // Initialise la case à [-1][-1]
		int ici= -1;
		for(int l=0; l<cases.length; l++)
			if(y<=this.cases[l].getY1()+80 && y>=this.cases[l].getY1()) ici=l;

		return (x>=91+mov && x<=91+480+mov)?ici:-1;
	}
	
	public void dessinerHoverMenu(Graphics g, GameContainer gc) {
		Input inp= gc.getInput();
		int x_mouse=inp.getMouseX();
		int y_mouse=inp.getMouseY();
		int l=quelleCase(x_mouse, y_mouse);
		if(l>=0 || volume(x_mouse, y_mouse) || setting(x_mouse, y_mouse)) {
			if(!focus && Sounds.volume) Sounds.hover.play();
			if(l==0)  g.drawImage(newGame[1],cases[l].getX1(), cases[l].getY1());
			else if(l==1) g.drawImage(scores[1],cases[l].getX1(), cases[l].getY1());
			else if(l==2)  g.drawImage(help[1],cases[l].getX1(), cases[l].getY1());
			else if(l==3)  g.drawImage(exit[1],cases[l].getX1(), cases[l].getY1());
			else if(volume(x_mouse, y_mouse)) {
				g.setColor(new Color(255,189,141));
				g.fillRoundRect(volXtics[0], volXtics[1], volXtics[2], volXtics[3], 50);
				g.drawImage(vol[Sounds.volume?0:1], volXtics[0], volXtics[1]);
			}
			else if (setting(x_mouse, y_mouse)) {
				g.setColor(new Color(255,189,141));
				g.fillRoundRect(settingXtics[0]-10, settingXtics[1]-10, settingXtics[2], settingXtics[3], 50);
				g.drawImage(setting, settingXtics[0], settingXtics[1]);
			}
			focus=true;
		}
		else focus=false;
	}
	
	public void clicCase(int x, int y) {
		int l=quelleCase(x, y);
		//Si le curseur de la souris est sur une option du menu 
		if(l!=-1) {
			selection=l+1;	
			if(Sounds.volume) Sounds.choice.play();
		}
		if(volume(x, y)) Sounds.toggleVolume();
		else if(setting(x,y)) selection = 5;
	}
	
	//Synchronise l'animation avec le taux  de rafraichissement
	public void updateAnimateBackground(int delta) {
		animateBrain.update(delta);
	}
	
	//Vérifie le temps de chargement avant l'affichage du menu
	public boolean loading(int delta) {
		if(timePassed>=timer) {
			return false;
		}
		timePassed+=delta;
		if(timePassed>timer-100)
			load.stopAt(35);
		else load.update(delta);
		return true;			
	}
	
	//dessine le chargement
	public void drawLoad() {
		load.draw(0+mov, 45);
	}
}
