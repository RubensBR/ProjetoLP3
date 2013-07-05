package br.com.busaojp.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ActivityUtil {

	//Somente métodos estáticos
	private ActivityUtil() {} 
	
	//Vai para outra activity
	public static void mudarActivity(Activity activity, Class<? extends Activity> activityClass) {
		Intent newActivity = new Intent(activity, activityClass);
		activity.startActivity(newActivity);
	}
	
	public static void mudarActivity(Activity activity, Class<? extends Activity> activityClass, Bundle parametros) {
		Intent newActivity = new Intent(activity, activityClass);
		newActivity.putExtras(parametros);
		activity.startActivity(newActivity);
	}
	
}
