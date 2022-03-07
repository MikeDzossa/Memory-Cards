import java.io.IOException;


import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;



public class Gamebox extends BasicGame {
	private Tableau tab;
	private Menu menu;
	private Help help;
	private Scores score;
	private Exit exit;
	private Settings setting;
	private int timer, timepassed;
	private boolean load;
	
	

	
	public Gamebox(String title) {
		super(title);
		Tableau.offsetButton();
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		// TODO Auto-generated method stub
		menu= new Menu();
		help= new Help();
		Sounds.initialise();
		exit=new Exit();
		setting= new Settings();
		tab=new Tableau();
		score=new Scores();
		Tableau.init=false;
		timer=1500;
		timepassed=0;
		load=true;
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		switch(Menu.selection) {
			case 0:
				if(!load) {
					menu.dessinerMenu(g, gc);
					menu.dessinerHoverMenu(g, gc);
				}
				else 
					menu.drawLoad();
				break;
				
			case 1:
				tab.dessinerTableau(g,gc);
				tab.dessinerHover(g, gc);
				tab.afficherTentatives(g);
				if(tab.victoire())
					try {
						tab.afficherVictoire(g);
					} catch (IOException e) {
						e.printStackTrace();
					}
				if(tab.getPauseStatus()) tab.dessinerPause(g, gc);
				break;
				
			case 2:
				try {
					score.dessinerScores(g, gc);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
			case 3:
				help.dessinerHelp(g, gc);
				break;
			
			case 4:
				menu.dessinerMenu(g, gc);
				exit.dessinerExit(g, gc);
				break;
			
			case 5: 
				menu.dessinerMenu(g, gc);
				setting.dessinerSetting(g, gc);
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input inp = gc.getInput();
		switch(Menu.selection) {
			case 0:
				load=menu.loading(delta);
				if(!load) {
					if(inp.isMousePressed(Input.MOUSE_LEFT_BUTTON))
						menu.clicCase(inp.getMouseX(), inp.getMouseY());
					menu.updateAnimateBackground(delta);
				}
				break;
				
			case 1:
				if(!Tableau.init) tab=new Tableau();
				if(tab.getPauseStatus()) {
					if(inp.isMousePressed(Input.MOUSE_LEFT_BUTTON))
						tab.clicPauseMenu(inp.getMouseX(), inp.getMouseY(), gc);
				}
				else {
					
					if(tab.deuxiemeSelection()) {
						if(!waiting())
							try {
								tab.comparaison();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						else 
							timepassed+=delta;
					}
					else {
						if(inp.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
							if(tab.victoire()) {
								Tableau.init=false;
								menu= new Menu();
							}
							else {
								if(!tab.premiereSelection()) tab.clicCase(inp.getMouseX(), inp.getMouseY());
								else tab.clicDeuxieme(inp.getMouseX(), inp.getMouseY());
								tab.clicPause(inp.getMouseX(), inp.getMouseY());
							}		
						}
					}
				}
				if(tab.reset()) Tableau.init=false;
				if(tab.toMenu()) menu= new Menu();
				break;
				
			case 2:
				if(inp.isMousePressed(Input.MOUSE_LEFT_BUTTON))
					score.clicCase(inp.getMouseX(), inp.getMouseY());
				break;
			
			case 3:
				if(inp.isMousePressed(Input.MOUSE_LEFT_BUTTON))
					help.clicCase(inp.getMouseX(), inp.getMouseY());
				break;
			
			case 4:
				if(exit.close()) gc.exit();
				if(inp.isMousePressed(Input.MOUSE_LEFT_BUTTON))
					exit.clicExit(inp.getMouseX(), inp.getMouseY());	
				break;
			
			case 5:
				if(inp.isMousePressed(Input.MOUSE_LEFT_BUTTON))
					setting.clicCase(inp.getMouseX(), inp.getMouseY());
				break;
		}
			
	}
	
	private boolean waiting() {
		if(timepassed>timer) {
			timepassed = 0;
			return false;
		}
		return true;
	}

}
