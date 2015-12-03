import org.newdawn.slick.Color;

public class LightSource {

    private float _xPos, _yPos;
    private float _lightStrength;
    private Color _color;

    public LightSource(float xPos, float yPos, float lightStrength, Color color) {

        this._xPos = xPos;
        this._yPos = yPos;
        this._lightStrength = lightStrength;
        this._color = color;
    }

    public void setLightLocation(float x, float y) {
        this._xPos = x;
        this._yPos = y;
    }

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
