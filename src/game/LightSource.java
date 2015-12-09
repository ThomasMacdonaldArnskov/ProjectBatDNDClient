package game;

import game.map.BattleMap;
import org.newdawn.slick.Color;

public class LightSource {

    /**********************************
     * VARIABLES
     **********************************/

    private float _xPos, _yPos;
    private float _lightStrength;
    private Color _color;
    private boolean enabled;

    /**********************************
     * CONSTRUCTORS
     **********************************/

    //CONSTRUCTOR FOR FLOAT VALUES
    public LightSource(float xPos, float yPos, float lightStrength, Color color) {

        this._xPos = xPos;
        this._yPos = yPos;
        this._lightStrength = lightStrength;
        this._color = color;
        this.enabled = true;
    }

    //CONSTRUCTOR FOR INT VALUES
    public LightSource(int xPos, int yPos, float lightStrength, Color color) {

        this._xPos = (float) xPos / (float) BattleMap.SPRITESIZE;
        this._yPos = (float) yPos / (float) BattleMap.SPRITESIZE;
        this._lightStrength = lightStrength;
        this._color = color;

    }

    public void setEnabled(boolean bo) {
        enabled = bo;
    }

    public boolean isEnabled() {
        return enabled;
    }

    /**********************************
     * METHODS
     **********************************/

    //SETS THE LOCATION OF A LIGHT
    public void setLightLocation(float x, float y) {
        this._xPos = x;
        this._yPos = y;
    }

    //OVERLOADED FOR INT USE
    public void setLightLocation(int x, int y) {
        this._xPos =x /  (float)BattleMap.SPRITESIZE - _lightStrength;
        this._yPos = y / (float) BattleMap.SPRITESIZE - _lightStrength;
    }

    //GET THE EFFECT AT A SPECIFIC LOCATION
    public float[] getLightEffectAtLocation(float x, float y) {

        float dx = x - _xPos;
        float dy = y - _yPos;
        float effect = 1 - (((dx * dx) + (dy * dy)) / (_lightStrength * _lightStrength));

        if (effect < 0) {
            effect = 0;
        }

        float[] effectArr = new float[3];
        effectArr[0] = _color.r * effect;
        effectArr[1] = _color.g * effect;
        effectArr[2] = _color.b * effect;

        return effectArr;
    }
}
