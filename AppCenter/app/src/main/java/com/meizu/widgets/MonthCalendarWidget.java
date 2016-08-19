package com.meizu.widgets;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

/**
 * Created by huangzhihao on 15-6-1.
 */
public class MonthCalendarWidget extends AppWidgetProvider {

    private static final String ACTION_PREVIOUS_MONTH
            = "com.example.android.monthcalendarwidget.action.PREVIOUS_MONTH";
    private static final String ACTION_NEXT_MONTH
            = "com.example.android.monthcalendarwidget.action.NEXT_MONTH";
    private static final String ACTION_RESET_MONTH
            = "com.example.android.monthcalendarwidget.action.RESET_MONTH";

    private static final String PREF_MONTH = "month";
    private static final String PREF_YEAR = "year";

    public MonthCalendarWidget() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            drawWiget(context,appWidgetId);
        }
    }

    private void drawWiget(Context context, int appWidgetId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Resources resources = context.getResources();


    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}
