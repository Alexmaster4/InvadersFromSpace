package net.fiveotwo.invaders.android;

/* Codigo de la presentacion de desarrollo multiplataforma con PlayN, COECYS USAC agosto 2012
 * @ Bryan Alvarado, 502Studios
 */
import net.fiveotwo.invaders.core.Utilities.Accel;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class AndroidAccelerometer implements SensorEventListener,Accel {

	private float x,y,z;
    private final SensorManager mSensorManager;
    private final Sensor mAccelerometer;
	
    public AndroidAccelerometer(Activity act){
    	mSensorManager = (SensorManager) act.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		x = arg0.values[0];
		y = arg0.values[1];
		z = arg0.values[2];
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public float getZ() {
		return z;
	}

	@Override
	public void start() {
		  mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void pause() {
		mSensorManager.unregisterListener(this);
		
	}

	@Override
	public float[] getValues() {
		float[] ret = {x,y,z};
		return ret;
	}

}
