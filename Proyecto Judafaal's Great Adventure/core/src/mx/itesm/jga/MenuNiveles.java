package mx.itesm.jga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


/**
 * Created by fcamp on 14/03/2018.
 */

class MenuNiveles extends Pantalla {

    private JudafaalsGreatAdventure jga;

    // Texturas
    private Texture fondoMenuNiveles;
    private Texture naveFondo;
    private TextureRegionDrawable texturaPrimerNivel;
    private TextureRegionDrawable texturaSegundoNivel;
    private TextureRegionDrawable texturaTecerNivel;
    private TextureRegionDrawable texturaBack;
    private TextureRegionDrawable texturaBackOnClick;

    // Escenas
    private Stage escenaMenuNivel;

    public MenuNiveles(JudafaalsGreatAdventure judafaalsGreatAdventure){
        this.jga = judafaalsGreatAdventure;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearMenu();
        Gdx.input.setInputProcessor(escenaMenuNivel);
    }

    private void crearMenu() {
        escenaMenuNivel = new Stage(vista);

        // Botón primer nivel.
        ImageButton btnPrimerNivel = new ImageButton(texturaPrimerNivel);
        btnPrimerNivel.setPosition(ANCHO/2 - btnPrimerNivel.getWidth()/2, ALTO/2 - btnPrimerNivel.getHeight());
        btnPrimerNivel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                jga.setScreen(new PrimerNivel(jga));
            }
        });
        escenaMenuNivel.addActor(btnPrimerNivel);

        // Botón nivel dos.
        ImageButton btnSegundoNivel = new ImageButton(texturaSegundoNivel);
        btnSegundoNivel.setPosition(ANCHO/2 - btnSegundoNivel.getWidth()/2, ALTO/2 - (btnSegundoNivel.getHeight() * 2));
        btnSegundoNivel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //jga.setScreen(new SegundoNivel(jga));
            }
        });
        escenaMenuNivel.addActor(btnSegundoNivel);

        // Botón nivel tres.
        ImageButton btnTercerNivel = new ImageButton(texturaTecerNivel);
        btnTercerNivel.setPosition(ANCHO/2 - btnTercerNivel.getWidth()/2, ALTO/2 - (btnTercerNivel.getHeight() * 3));
        btnTercerNivel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //jga.setScreen(new TercerNivel(jga));
            }
        });
        escenaMenuNivel.addActor(btnTercerNivel);

        // Botón home que va al menú principal.
        ImageButton btnBack = new ImageButton(texturaBack, texturaBackOnClick);
        btnBack.setPosition(25, ALTO - 25 - btnBack.getHeight());
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                jga.setScreen(new MenuJudafaals(jga));
            }
        });
        escenaMenuNivel.addActor(btnBack);

    }

    private void cargarTexturas() {
        fondoMenuNiveles =  new Texture("Fondos/Pantalla principal.jpg");
        naveFondo = new Texture("Fondos/Naveinicio.png");
        texturaPrimerNivel = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BotonNivelUno.png")));
        texturaSegundoNivel = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BotonNivelDos.png")));
        texturaTecerNivel = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BotonNivelTres.png")));
        texturaBack = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/backButton.png")));
        texturaBackOnClick = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/backButtonOnClick.png")));
    }

    @Override
    public void render(float delta) {
        borrarPantalla();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(fondoMenuNiveles, 0,0);
        batch.draw(naveFondo, ANCHO/2 - naveFondo.getWidth()/2, ALTO/2 - naveFondo.getHeight()/2 - 174);
        batch.end();
        escenaMenuNivel.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        escenaMenuNivel.dispose();
        batch.dispose();
        naveFondo.dispose();
    }
}
