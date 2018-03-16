package mx.itesm.jga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by fcamp on 15/03/2018.
 */

public class Personaje {
    // Variables de instancia
    private static final float FUERZA_GRAVEDAD = 9.81f;
    private static final float VELOCIDAD_Y = 400;
    private Animation animationPersonaje;
    private float x;
    private float y;
    private float timerAnimation = 0;
    private EstadoPersonaje estadoPersonaje;

    public Personaje(Texture textura){
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] frames = region.split(195, 81);
        animationPersonaje = new Animation(0.1f, frames[0][0], frames[0][1]);
        x = 0;
        y = Pantalla.ALTO/2;
        animationPersonaje.setPlayMode(Animation.PlayMode.LOOP);
        estadoPersonaje = EstadoPersonaje.VIVO;
    }

    public void render(SpriteBatch batch){
        timerAnimation += Gdx.graphics.getDeltaTime();
        TextureRegion frame = (TextureRegion) animationPersonaje.getKeyFrame(timerAnimation);
        batch.draw(frame, x, y);

    }

    // Métodos seters que permiten cambiar el eje x y y del personaje.
    public void setY(float y){
        this.y = y;
    }
    public void setX(float x){
        this.x = x;
    }

    // Métodos getters que permiten obtener el eje x y y del personaje.
    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }

    // Método que regresan la anchura del personaje.
    public float getWidth(){
        return ((TextureRegion) animationPersonaje.getKeyFrame(0)).getRegionWidth();
    }

    // Método que regresa la altura del personaje.
    public float getHeight(){
        return ((TextureRegion) animationPersonaje.getKeyFrame(0)).getRegionHeight();
    }

    public void actualizarPersonaje(float delta){

    }

    enum EstadoPersonaje{
        VIVO,
        MUERTO
    }
}
