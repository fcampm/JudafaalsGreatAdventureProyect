package mx.itesm.jga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by fcamp on 14/03/2018.
 */

class PrimerNivel extends Pantalla {

    private JudafaalsGreatAdventure jga;
    private static final float ANCHO_MAPA = 5120;

    // Elementos del TiledMap a usar.
    private OrthogonalTiledMapRenderer render;
    private TiledMap mapa;

    // Texturas a usar.
    private Texture texturaPersonajeNave;
    private TextureRegionDrawable texturaBotonPausa;
    private TextureRegionDrawable texturaHome;
    private TextureRegionDrawable texturaHomeOnClick;
    private TextureRegionDrawable texturaReanudar;
    private TextureRegionDrawable texturaReanudarOnClick;

    // Escenas a usar.
    private Stage escenaPausa;
    private Stage escenaJuego;

    // Creación de una segunda cámara.
    private OrthographicCamera camara2;
    private Viewport vista2;

    // Personaje principal que usará el jugador.
    private Personaje personaje;

    private InputProcessor inputProcessor;

    // Estados del juego como JUGANDO, PAUSA, etc.
    private EstadoJuego estado;

    public PrimerNivel(JudafaalsGreatAdventure judafaalsGreatAdventure) {
        this.jga = judafaalsGreatAdventure;
    }

    @Override
    public void show() {
        crearCamara();
        cargarTexturas();
        cargarMapa();
        cargarPersonaje();
        cargarInputProcesor();
        estado = EstadoJuego.JUGANDO;

    }

    private void crearCamara() {
        camara2 = new OrthographicCamera(ANCHO, ALTO);
        camara2.position.set(ANCHO/2, ALTO/2, 0);
        camara2.update();
        vista2 = new StretchViewport(ANCHO, ALTO, camara2);
    }

    private void cargarPersonaje() {
        personaje = new Personaje(texturaPersonajeNave);
    }

    private void cargarInputProcesor() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        // Escena Juego con su botón de pausa.
        escenaJuego = new Stage(vista2);
        ImageButton btnPausa = new ImageButton(texturaBotonPausa);
        btnPausa.setPosition(ANCHO/2 - btnPausa.getWidth()/2, ALTO - btnPausa.getHeight() - 15);
        btnPausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pause();
            }
        });
        escenaJuego.addActor(btnPausa);
        inputMultiplexer.addProcessor(escenaJuego);

        // Escena Pausa con su botón de reanudar y home.
        escenaPausa = new Stage(vista2);
        ImageButton btnHome = new ImageButton(texturaHome, texturaHomeOnClick);
        btnHome.setPosition(ANCHO/2 - btnHome.getWidth()/2 - 200, ALTO/2 - btnHome.getHeight()/2);
        btnHome.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                jga.setScreen(new MenuNiveles(jga));
            }
        });
        escenaPausa.addActor(btnHome);

        ImageButton btnReaundar = new ImageButton(texturaReanudar, texturaReanudarOnClick);
        btnReaundar.setPosition(ANCHO/2 - btnReaundar.getWidth() + 200, ALTO/2 - btnReaundar.getHeight()/2);
        btnReaundar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resume();
            }
        });
        escenaPausa.addActor(btnReaundar);

        Gdx.input.setInputProcessor(inputMultiplexer);
        inputProcessor = inputMultiplexer;
    }

    private void cargarMapa() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("PrimerNivel/prueba1.tmx", TiledMap.class);
        manager.finishLoading();
        mapa = manager.get("PrimerNivel/prueba1.tmx");
        render = new OrthogonalTiledMapRenderer(mapa);
    }

    private void cargarTexturas() {
        texturaBotonPausa = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/pause.png")));
        texturaPersonajeNave = new Texture("PrimerNivel/naveFrames.png");
        texturaHome = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/homeAzul.png")));
        texturaHomeOnClick = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/homeAzulOnClick.png")));
        texturaReanudar = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BotonPlay.png")));
        texturaReanudarOnClick = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BotonPlayPado.png")));
    }

    @Override
    public void render(float delta) {
        if(estado != EstadoJuego.PAUSADO){
            actualizarPantalla();
        }
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        render.setView(camara);
        render.render();
        batch.begin();
        personaje.render(batch);
        batch.end();
        if(estado == EstadoJuego.JUGANDO){
            escenaJuego.draw();
        }

        else{
            escenaPausa.draw();
        }
    }

    private void actualizarPantalla() {
        actualizarCamara();
        actualizarPersonaje();
    }

    private void actualizarPersonaje() {
        personaje.setX(personaje.getX() + 5);
    }

    private void actualizarCamara() {
        // La posición de la cámara siempre seguirá al personaje tomando en cuenta la mitad de la pantalla.
        float posicionX = personaje.getX();
        if (posicionX < ANCHO/2 ) {
            camara.position.set(ANCHO/2, ALTO/2, 0);
        } else if (posicionX > ANCHO_MAPA - ANCHO/2) {
            camara.position.set(ANCHO_MAPA-ANCHO/2,camara.position.y,0);
        } else {    // En 'medio' del mapa
            camara.position.set(posicionX,camara.position.y,0);
        }
        camara.update();
    }

    @Override
    public void pause() {
        estado = EstadoJuego.PAUSADO;
        Gdx.input.setInputProcessor(escenaPausa);
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(inputProcessor);
        estado = EstadoJuego.JUGANDO;
    }

    @Override
    public void dispose() {
        escenaPausa.dispose();
        escenaJuego.dispose();
        batch.dispose();
        mapa.dispose();
        texturaPersonajeNave.dispose();
    }

    enum EstadoJuego{
        JUGANDO,
        PAUSADO
    }
}
