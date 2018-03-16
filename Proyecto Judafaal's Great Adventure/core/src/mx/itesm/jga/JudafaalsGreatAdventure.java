package mx.itesm.jga;

import com.badlogic.gdx.Game;


public class JudafaalsGreatAdventure extends Game {

	@Override
	public void create () {
        //Pone Pantalla inicial.
        setScreen(new MenuJudafaals(this)); // Solo objetos de Game pueden correr esto.
	}

}
