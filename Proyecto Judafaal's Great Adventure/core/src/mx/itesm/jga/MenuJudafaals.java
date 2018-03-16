package mx.itesm.jga;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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

class MenuJudafaals extends Pantalla{

    private final JudafaalsGreatAdventure jga;

    // Creación de escenas.
    private Stage escenaMenu;

    // Texturas
    private Texture fondoMenu;
    private TextureRegionDrawable texturaPlay;
    private TextureRegionDrawable texturaPlayOnClick;
    private TextureRegionDrawable texturaAyuda;
    private TextureRegionDrawable texturaAyudaOnClick;
    private TextureRegionDrawable texturaMas;
    private TextureRegionDrawable texturaMasOnClick;
    private TextureRegionDrawable texturaSettings;
    private TextureRegionDrawable texturaSettingsOnClick;

    // Creación música de fondo.
    private Music musicaFondo;

    public MenuJudafaals(JudafaalsGreatAdventure judafaalsGreatAdventure) {

        this.jga = judafaalsGreatAdventure;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearMenu();
        crearMusica();
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private void crearMusica() {
        float volumen = 0.5f;
        musicaFondo = Gdx.audio.newMusic(Gdx.files.getFileHandle("Musica/message.mp3", Files.FileType.Internal));
        musicaFondo.setVolume(volumen);
        musicaFondo.play();
        musicaFondo.setLooping(true);
    }

    // Método que crea el menú del juego.
    private void crearMenu() {
        escenaMenu = new Stage(vista);

        // Botón Play.
        ImageButton btnPlay = new ImageButton(texturaPlay, texturaPlayOnClick);
        btnPlay.setPosition(ANCHO/2 - 95, ALTO/2 - 85);
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                jga.setScreen(new MenuNiveles(jga));
                musicaFondo.dispose();
            }

        });
        escenaMenu.addActor(btnPlay);

        // Botón Ayuda.
        ImageButton btnAyuda = new ImageButton(texturaAyuda, texturaAyudaOnClick);
        btnAyuda.setPosition(ANCHO - 240, ALTO/2 - 350);
        btnAyuda.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
               // jga.setScreen(new PantallaAyuda(jga));
                musicaFondo.dispose();
            }
        });
        escenaMenu.addActor(btnAyuda);

        // Botón Settings.
        ImageButton btnSettings = new ImageButton(texturaSettings, texturaSettingsOnClick);
        btnSettings.setPosition(0, Pantalla.ALTO - 128);
        btnSettings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                jga.setScreen(new PantallaSettings(jga));
                musicaFondo.dispose();
            }
        });
        escenaMenu.addActor(btnSettings);

        // Botón Más.
        ImageButton btnMas = new ImageButton(texturaMas, texturaMasOnClick);
        btnMas.setPosition(ANCHO/24 - 50, ALTO/2 - 350);
        btnMas.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                jga.setScreen(new PantallaMas(jga));
                musicaFondo.dispose();
            }
        });
        escenaMenu.addActor(btnMas);
    }

    private void cargarTexturas() {
        fondoMenu = new Texture("Fondos/Pantalla principal.jpg");
        texturaPlay = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BotonPlay.png")));
        texturaPlayOnClick = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BotonPlayPado.png")));
        texturaAyuda = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BotonAyuda3.2.png")));
        texturaAyudaOnClick = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BotonAyudaPado.png")));
        texturaMas = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BotonMas.png")));
        texturaMasOnClick = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BotonMasPado.png")));
        texturaSettings = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/ajustes.png")));
        texturaSettingsOnClick = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/ajustesOnClick.png")));
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        // Dibuja el fondo que previamente fue cargado.
        batch.begin();
        batch.draw(fondoMenu,0,0);
        batch.end();
        escenaMenu.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        fondoMenu.dispose();
        escenaMenu.dispose();
        batch.dispose();
    }

}
