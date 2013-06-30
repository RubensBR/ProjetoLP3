package br.com.busaojp.utils;

import android.app.Activity;
import android.content.Intent;

public class ActivityUtil {

	//Somente métodos estáticos
	private ActivityUtil() {} 
	
	//Vai para outra activity
	public static void mudarActivity(Activity activity, Class<? extends Activity> activityClass) {
		Intent newActivity = new Intent(activity, activityClass);
		activity.startActivity(newActivity);
	}
}
